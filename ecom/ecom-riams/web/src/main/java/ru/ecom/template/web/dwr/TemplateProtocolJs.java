package ru.ecom.template.web.dwr;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.diary.ejb.service.protocol.IDiaryService;
import ru.ecom.diary.ejb.service.template.ITemplateProtocolService;
import ru.ecom.diary.web.action.protocol.template.TemplateSaveAction;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 25.12.2006
 * Time: 17:27:00
 * To change this template use File | Settings | File Templates.
 */
public class TemplateProtocolJs {
    private static final Logger LOG = Logger.getLogger(TemplateProtocolJs.class);

    public Long getPatientSex(Long aPatientId, HttpServletRequest aRequest) throws NamingException {
    	return Long.valueOf(Injection.find(aRequest).getService(IWebQueryService.class).executeNativeSql("select sex_id from patient where id ="+aPatientId).iterator().next().get1().toString());
	}
	public String getSummaryBallsByNewCard (Long aCardTemplate, String aParams, HttpServletRequest aRequest) throws NamingException {
		if (aParams==null||aParams.equals("")) {aParams="0";}
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder res = new StringBuilder();
		String sql = "select pg.id, pg.name,cast(coalesce(sum(uv.cntBall),0) as decimal(11,2))" +
				" from  parameterbyform pf" +
				" left join parameter p on p.id=pf.parameter_id" +
				" left join parametergroup pg on pg.id=p.group_id" +
				" left join userdomain ud on ud.id=p.valuedomain_id " +
				" left join uservalue uv on uv.domain_id=ud.id and uv.id in ("+aParams+")" +
				" where pf.assessmentcard="+aCardTemplate+
				" group by pg.id, pg.name";
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (!list.isEmpty()) {
			for (WebQueryResult r: list) {
				if (res.length()>0) {
					res.append("@");
				}
				res.append(r.get1()).append("#").append(r.get2()).append("#").append(r.get3());
			}
		}
		return res.toString();
		
	}
	public String getParameterBallId(Long aParameterId, HttpServletRequest aRequest) throws NamingException {
		if (aParameterId==null||aParameterId.equals(0L)) {return "";}
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> res = service.executeNativeSql("select coalesce(cast(cntBall as decimal(11,2)),0) as cntBall, coalesce(comment,'') as comment from uservalue  where id = "+aParameterId);
		if (!res.isEmpty()) {
			WebQueryResult r =res.iterator().next(); 
			return r.get1().toString()+"#"+r.get2().toString();
		}
		return "";
		
	}
	public String getTemplateParametersList (Long aTemplateId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder ret = new StringBuilder();
		String sql = "select p.id, coalesce(p.code,'_id'||p.id) as f1_code ,p.name as f2_name" +
				 " from parameter p" +
				 " left join parameterbyform pbf on pbf.parameter_id = p.id" +
				 " where pbf.template_id ="+aTemplateId;
		
		Collection<WebQueryResult> res = service.executeNativeSql(sql);
		if (!res.isEmpty()) {
			for (WebQueryResult r: res) {
				if (ret.length()>0) {ret.append("#");}
				ret.append("${").append(r.get2()).append("}\t").append("Значение поля '").append(r.get3()).append("'#");
				ret.append("${").append(r.get2()).append("Name}\t").append("Название поля '").append(r.get3()).append("'");
				
			}
		}
		
		return ret.length()>0?ret.toString():"";
		
	}
	public String getProtocolTemplatesPrintForms(Long aProtocolId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder ret = new StringBuilder();
		String sql = "select ud.id, ud.name, ud.filename" +
				" from userdocument ud" + 
				" left join templateprotocol tp on tp.id = ud.template"+
				" left join diary d on d.templateprotocol = tp.id"+
				" where d.id="+aProtocolId;
		Collection<WebQueryResult> res = service.executeNativeSql(sql);
		if (!res.isEmpty()) {
			for (WebQueryResult r: res) {
				if (ret.length()>0) {ret.append("#");}
				ret.append(r.get1()).append("@").append(r.get2()).append("@").append(r.get3());
				
			}
		}
		return ret.length()>0?ret.toString():"";
	}
/*	public String saveParametersByProtocol (Long aSmoId,Long aProtocolId, String aParams, HttpServletRequest aRequest) throws NamingException {
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class);
		
		return service.saveParametersByProtocol(aSmoId,aProtocolId, aParams, username);
	}*/
	public String getTemplateDisableEdit (Long aTemplateId, HttpServletRequest aRequest) throws NamingException {
		if (aTemplateId==null||aTemplateId.equals(0L)) return "0";
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		return service.executeNativeSql("select case when disableEdit='1' then '1' else '0' end" +
				" from templateprotocol where id = "+aTemplateId).iterator().next().get1().toString();
	}
	public void changeTypeByParameter(Long aParam, Long aType, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("update Parameter set type='"+aType+"' where id="+aParam) ;
	}

	public String getParameterAndPermissionByTemplate(Long aProtocolId, Long aTemplateId, HttpServletRequest aRequest) throws NamingException {
		if (aTemplateId==null || aTemplateId.equals(0L)) return "{}";
			JSONObject parameters = new JSONObject(getParameterByObject(aProtocolId, aTemplateId,"Template", aRequest));
			parameters.put("disableEditProtocol",getTemplateDisableEdit(aTemplateId, aRequest));
		return parameters.toString();
	}

	public String getParameterByObject(Long aProtocolId, Long aTemplateId, String aObjectName, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		Collection<WebQueryResult> lwqr = new LinkedList<>();
		String fieldName = "";
		 if (aObjectName.equals("AssessmentCard")){
			fieldName = "pf.assessmentCard";
		}
		Long wfId = 0L;
		String wfName = "" ;
		if (aProtocolId!=null && !aProtocolId.equals(0L)) {
			if (aObjectName.equals("Template")) {
				fieldName = "d.id";
			} 
			//TODO TYPE6+7
			sql.append("select p.id as p1id,p.name as p2name") ;
			sql.append(" , p.shortname as p3shortname,p.type as p4type") ;
			sql.append(" , p.minimum as p5minimum, p.normminimum as p6normminimum") ;
			sql.append(" , p.maximum as p7maximum, p.normmaximum as p8normmaximum") ;
			sql.append(" , p.minimumbd as p9minimumbd, p.normminimumbd as p10normminimumbd") ;
			sql.append(" , p.maximumbd as p11maximumbd, p.normmaximumbd as p12normmaximumbd") ;
			sql.append(" , vmu.id as v13muid,vmu.name as v14muname") ;
			sql.append(" , vd.id as v15did,vd.name as v16dname") ;
			sql.append(" ,p.cntdecimal as p17cntdecimal") ;
			sql.append(" , ''||p.id||case when  p.type in ('2','7') then 'Name'  when p.type='6' then '_'||(select min(uv.id) from uservalue uv where uv.domain_id=vd.id) else '' end as p18enterid") ;
			sql.append(" , case when p.type in ('3','5','8')  then pf.valueText") ;
			sql.append(" when p.type ='4' then case when pf.valuetext is null or pf.valuetext='' then replace(''||round(pf.valueBD,cast(p.cntdecimal as int)),'.',',') else pf.valuetext end"); 
			sql.append(" when p.type ='1' then to_char(round(pf.valueBD,case when p.cntdecimal is null then 0 else cast(p.cntdecimal as int) end),'fm99990') ");
			sql.append(" when p.type ='6' then pf.ListValues ");
			sql.append(" when p.type ='7' then ''||pf.valueVoc_id ");
			sql.append(" when p.type='2' then ''||pf.valueVoc_id end as p19val") ;
			sql.append(" ,vv.name as d20val4v") ;
			sql.append(" ,pg.id as f21GroupId");
			sql.append(" ,pg.name as f22GroupIName");
			sql.append(" ,case when p.type='7' then pf.valueText else '' end as f23AddVal");
			sql.append(" from FormInputProtocol pf") ;
			sql.append(" left join Diary d on pf.docProtocol_id = d.id") ;
			sql.append(" left join parameter p on p.id=pf.parameter_id") ;
			sql.append(" left join parametergroup pg on pg.id=p.group_id");
			sql.append(" left join userDomain vd on vd.id=p.valueDomain_id") ;
			sql.append(" left join userValue vv on vv.id=pf.valueVoc_id") ;
			sql.append(" left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id") ;
			sql.append(" where ").append(fieldName).append("='").append(aProtocolId).append("'") ;
			sql.append(" order by pf.position") ;
			lwqr = service.executeNativeSql(sql.toString()) ;
			
		} 
		if (lwqr.isEmpty()) {
			if (aObjectName.equals("Template")) {
				fieldName = "tp.id";
			}
			sql = new StringBuilder() ;
			sql.append("select distinct p.id as p1id,p.name as p2name") ;
			sql.append(" , p.shortname as p3shortname,p.type as p4type") ;
			sql.append(" , p.minimum as p5minimum, p.normminimum as p6normminimum") ;
			sql.append(" , p.maximum as p7maximum, p.normmaximum as p8normmaximum") ;
			sql.append(" , p.minimumbd as p9minimumbd, p.normminimumbd as p10normminimumbd") ;
			sql.append(" , p.maximumbd as p11maximumbd, p.normmaximumbd as p12normmaximumbd") ;
			sql.append(" , vmu.id as v13muid,vmu.name as v14muname") ;
			sql.append(" , vd.id as v15did,vd.name as v16dname") ;
			sql.append(" ,p.cntdecimal as p17cntdecimal") ;
			sql.append(" , ''||p.id||case when p.type in ('2','7') then 'Name' when p.type='6' then '_'||(select min(uv.id) from uservalue uv where uv.domain_id=vd.id) else '' end as p18enterid") ;
			sql.append(" , case when p.type in ('3','5','8')  then p.valueTextDefault else '' end as p19valuetextdefault") ;
			sql.append(" ,cast('' as varchar) as f20defvalue");
			sql.append(" ,pg.id as f21GroupId");
			sql.append(" ,pg.name as f22GroupIName");
			sql.append(" ,case when p.type='7' then p.valueTextDefault else '' end as f23AddVal");
			sql.append(" ,case when (uv.usebydefault) then uv.id else null end as f24_usebydefault");
			sql.append(" ,case when (uv.usebydefault) then uv.name else null end as f25_usedefval");
			sql.append(" ,pf.position as f26"); //необходимо, т.к. находится в order by при distinct
			sql.append(" ,p.forSex_id as f27_sexId");
			sql.append(" from parameterbyform pf") ;
			sql.append(" left join templateprotocol tp on pf.template_id = tp.id") ;
			sql.append(" left join parameter p on p.id=pf.parameter_id") ;
			sql.append(" left join parametergroup pg on pg.id=p.group_id");
			sql.append(" left join userDomain vd on vd.id=p.valueDomain_id") ;
			sql.append(" left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id") ;
			sql.append(" left join userValue uv on uv.domain_id=vd.id and uv.usebydefault") ;
			sql.append(" where ").append(fieldName).append("='").append(aTemplateId).append("'") ;
			sql.append(" order by pf.position") ;
			lwqr = service.executeNativeSql(sql.toString()) ;
		}
		JSONArray errArr = new JSONArray();
		JSONObject js = new JSONObject();
		js.put("workFunction",wfId);
		js.put("workFunctionName",wfName);
		JSONArray params = new JSONArray();
			boolean firstPassed = false ;
			boolean firstError = false ;
			String[][] props = {{"1","id"},{"2","name"},{"3","shortname"}
			,{"4","type"},{"5","min"},{"6","nmin"},{"7","max"},{"8","nmax"}
			,{"9","minbd"},{"10","nminbd"},{"11","maxbd"},{"12","nmaxbd"}
			,{"13","unitid"},{"14","unitname"}
			,{"15","vocid"},{"16","vocname"},{"17","cntdecimal"}
			,{"18","idEnter"},{"19","value"},{"20","valueVoc"}
			,{"21","groupId"}, {"22","groupName"},{"23","addValue"},{"24","usebydefault"},{"25","usedefval"},{"27","sex"}
			} ;
			for(WebQueryResult wqr : lwqr) {
				JSONObject parJs = new JSONObject();
				boolean isError = false ;
				String parType =String.valueOf(wqr.get4());
				String vocId = String.valueOf(wqr.get15()) ;
				if ((parType.equals("2")||parType.equals("6")||parType.equals("7")) && vocId.equals("")) {
					isError = true ;
				}
				try {
					for(String[] prop : props) {
						Object value = PropertyUtil.getPropertyValue(wqr, prop[0]) ;
						String strValue = value!=null?value.toString():"";
						parJs.put(prop[1],strValue);
					}

					JSONArray vocArr = new JSONArray();
					if (parType.equals("6")) {
						List<Object[]> vocVals = service.executeNativeSqlGetObj("select id,name from UserValue where domain_id=" + vocId + " order by id") ;
						String valList = ""+(wqr.get19()!=null?wqr.get19():"");
						for (Object[] vocVal : vocVals) {
							JSONObject voc = new JSONObject();
							voc.put("id",vocVal[0]);
							voc.put("name",vocVal[1]);
							voc.put("checked",valList.contains("," + vocVal[0] + ",") ?"1":"0");
							vocArr.put(voc);
						}
					}
					parJs.put("voc",vocArr);
					
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
				if (isError) {
					errArr.put(parJs);
				} else {
					params.put(parJs);
				}
			}
			js.put("params",params);
			js.put("template",aTemplateId);
			js.put("protocol",aProtocolId);
			js.put("errors",errArr);
			return js.toString();
		
	}

	//Milamesher changed
	public String getDtypeMedCase(Long aIdMedCase, HttpServletRequest aRequest) throws NamingException {
		JSONObject res = new JSONObject();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select ms.dtype,vss.code from MedCase ms,vocservicestream vss where  ms.id="+aIdMedCase+" and vss.id=ms.servicestream_id") ;
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next();
			res.put("msDtype",wqr.get1());
			res.put("vssCode",wqr.get2());
		}
		return res.toString();
	}

	public String getText(String aId, HttpServletRequest aRequest) throws NamingException {
		if (StringUtil.isNullOrEmpty(aId)) {
			return "" ;
		} else {
			ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class) ;
			return service.getTextTemplate(Long.parseLong(aId)) ;
		}
	}
	public String getTextExternal(String aId, HttpServletRequest aRequest) throws NamingException {
		if (StringUtil.isNullOrEmpty(aId)) {
			return "" ;
		} else {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> list = service.executeNativeSql("select em.id,to_char(em.orderDate,'dd.mm.yyyy')||' '||em.comment as comment from Document em where em.id=" + aId + " and dtype='ExternalMedservice'",1) ;
			return list.isEmpty() ? "" : ""+list.iterator().next().get2() ;
		}
	}
    public String getTextDischarge(String aId, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aId)) {
            return "" ;
        } else {
        	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
        	return service.getDischargeEpicrisis(Long.parseLong(aId)) ;
        }
    }

    //категории шаблонов
    public String listCategProtocolsByUsername(String aSmoId,String aType, String aFunction, String diaryDate, HttpServletRequest aRequest) throws NamingException {
		String diarySql ;
		if (diaryDate!=null && !diaryDate.equals("")) {
			diarySql=" and d.dateRegistration=to_date('"+diaryDate+"','dd.mm.yyyy')";
		} else {
			diarySql="";
		}
    	StringBuilder sql = new StringBuilder() ;
    	String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder res = new StringBuilder() ;
    	Collection<WebQueryResult> list ;
    	Long patient = null ;
    	res.append("<table>");
    	//res.append("<tr><td colspan='3'><h2>Выбор осуществляется двойным нажатием мыши</h2></td></tr>") ;
    	res.append("<tr><td colspan='2' valign='top'>") ;
    	if ("temp".equals(aType)) {
    		sql.append(" select coalesce(tc.id,-1) as tid") ;
    		sql.append(" , coalesce(tc.name,'без категории')  as ttile") ; 
    		sql.append(" from TemplateCategory tc ") ;
    		sql.append(" left join TemplateProtocol_TemplateCategory tptc on tc.id=tptc.categories_id") ;
    		sql.append(" where tc.parent_id ") ;
    		String name_categ = null ;
			if (aSmoId!=null && !aSmoId.equals("") && !aSmoId.equals("0")) {
				if (aSmoId.equals("-1")) {sql.append(" is null ");}
				else {
					sql.append("='").append(aSmoId).append("'");
		    		list = service.executeNativeSql("select tc.parent_id from templatecategory tc where tc.id ="+aSmoId+" and tc.parent_id is not null");
		    		if (!list.isEmpty()) name_categ = ""+list.iterator().next().get1() ;
				}
			} else {
				sql.append(" is null ");
			}

    		sql.append(" group by tc.id,tc.name") ;
    		sql.append(" order by tc.name") ;
    		
    		res.append("<h2>Список категорий ");
    		res.append("<a href='javascript:void(0)' onclick='").append(aFunction).append("(\"temp\")'>В начало</a>") ;
    		list = service.executeNativeSql(sql.toString());
    		res.append("</h2>") ;
    		res.append("</td>") ;
    		res.append("</tr><tr><td colspan='2' valign='top'>") ;
    		res.append("<ul>");
    		if (name_categ!=null) {
    			res.append("<a href='javascript:void(0)' onclick='").append(aFunction).append("All(\"temp\",\"").append(name_categ).append("\")'>...</a>") ;
    		} else {
    			if (aSmoId!=null && !aSmoId.equals("") && !aSmoId.equals("0")) res.append("<a href='javascript:void(0)' onclick='").append(aFunction).append("(\"temp\")'>...</a>") ;
    		}
    		for (WebQueryResult wqr:list) {
    			res.append("<li class='liTemp' onclick=\"").append(aFunction).append("All('").append(aType).append("','")
    			.append(wqr.get1()).append("')\" ondblclick=\"").append(aFunction).append("All('").append(aType).append("','")
    			.append(wqr.get1()).append("')\">") ;
    			res.append(wqr.get2()) ;
    			res.append("</li>") ;
    		}
    		res.append("</ul></td>") ;
		} else if ("my".equals(aType)) {
    		sql.append(" select coalesce(tc.id,-1) as tid") ;
    		sql.append(" , coalesce(tc.name||'('||list(distinct case when su.login!='").append(login).append("' then 'общ' else 'свои' end)||')' ,'без категории')  as ttile") ; 
    		sql.append(" from TemplateProtocol tp ") ;
    		sql.append(" left join SecUser su on tp.username=su.login") ; 
    		sql.append(" left join templateprotocol_secgroup tg on tp.id=tg.templateprotocol_id") ; 
    		sql.append(" left join SecGroup_secUser gu on gu.secgroup_id=tg.secgroups_id ") ;
    		sql.append(" left join SecUser gsu on gsu.id=gu.secUsers_id ") ;
    		sql.append(" left join TemplateProtocol_TemplateCategory tptc on tptc.protocols_id=tp.id") ;
    		sql.append(" left join TemplateCategory tc on tc.id=tptc.categories_id") ;
    		sql.append(" where su.login='").append(login).append("' or gsu.login='").append(login).append("'") ; 
    		sql.append(" group by tc.id,tc.name") ;
    		sql.append(" order by tc.name") ;
    		list = service.executeNativeSql(sql.toString());
    		res.append("<h2>Список категорий</h2>") ;
    		res.append("</td>") ;
    		res.append("</tr><tr><td colspan='2' valign='top'>") ;
    		res.append("<ul>");
    		for (WebQueryResult wqr:list) {
    			res.append("<li class='liTemp' onclick=\"").append(aFunction).append("('").append(aType).append("','")
    			.append(wqr.get1()).append("')\" ondblclick=\"").append(aFunction).append("('").append(aType).append("','")
    			.append(wqr.get1()).append("')\">") ;
    			res.append(wqr.get2()) ;
    			
    		}
    		res.append("</ul></td>") ;
    	} else if (aSmoId!=null && !aSmoId.equals("") && !aSmoId.equals("0")){
    		list = service.executeNativeSql("select mc.id,mc.patient_id from medcase mc where mc.id="+aSmoId,1);
    		if (!list.isEmpty()) patient = ConvertSql.parseLong(list.iterator().next().get2()) ;

    		if ("mydiary".equals(aType) && patient!=null) {
    			res.append("<h2>заключения</h2>") ;
    			sql = new StringBuilder() ;
    			sql.append("select to_char(d.dateRegistration,'DD.MM.YYYY'),  list(distinct " +
    					"case when m.dtype='Visit' or m.dtype='ShortMedCase' then 'Поликл.' " +
    					"when m.dtype='DepartmentMedCase' then 'СЛО' " +
    					"when m.dtype='HospitalMedCase' then 'СЛС' " +
    					"when m.dtype='ServiceMedCase' then 'Услуга' " +
    					"when m.dtype='HospitalMedCase' then 'Приемное отделение' " +
    					"else '' end) as listtype")  
    			.append(" from  MedCase as m ")   
    			.append(" left join Diary as d on m.id=d.medCase_id")         
    			.append(" where   m.patient_id='").append(patient)
    			.append("' and upper(d.dtype)='PROTOCOL'")
				.append(diarySql)
    			.append("    group by d.dateRegistration,d.username having upper(d.username)='").append(login.toUpperCase()).append("'") 
    			.append("  order by d.dateRegistration desc") ;
    			list = service.executeNativeSql(sql.toString(),30);
        		res.append("<ul>");
        		for (WebQueryResult wqr:list) {
        			res.append("<li class='liTemp' onclick=\"").append(aFunction).append("('").append(aType).append("','")
        			.append(wqr.get1()).append("')\" ondblclick=\"").append(aFunction).append("('").append(aType).append("','")
        			.append(wqr.get1()).append("')\">") ;
        			res.append(wqr.get1()).append(" ").append(wqr.get2()) ;
        			res.append("</li>") ;
        		}
    			res.append("</ul></td>");
    		} else if ("polyc".equals(aType) && patient!=null) {
    			res.append("<h2>Поликлиника</h2>");
    			sql=new StringBuilder() ;
    			sql.append("select vwf.id,vwf.name from MedCase smo ") ;
    			sql.append(" left join diary d on smo.id=d.medcase_id");
    			sql.append(" left join WorkFunction wf on wf.id=d.specialist_id");
    			sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
    			sql.append(" left join worker w on wf.worker_id=w.id");
    			sql.append(" left join patient wp on wp.id=w.person_id");
    			sql.append(" where smo.patient_id='").append(patient).append("'");
    			sql.append(" and upper(smo.dtype) in ('VISIT' , 'SHORTMEDCASE') and upper(d.dtype)='PROTOCOL'")
				.append(diarySql);
    			sql.append(" group by vwf.id,vwf.name");
    			sql.append(" order by vwf.name");
    			sql.append(" ") ;
    			list = service.executeNativeSql(sql.toString(),30);
        		res.append("<ul>");
        		for (WebQueryResult wqr:list) {
        			res.append("<li class='liTemp' onclick=\"").append(aFunction).append("('").append(aType).append("','")
        			.append(wqr.get1()).append("')\" ondblclick=\"").append(aFunction).append("('").append(aType).append("','")
        			.append(wqr.get1()).append("')\">") ;
    				res.append(wqr.get2());
    				res.append("</li>") ;
    			}
    			res.append("</ul>");
    			
    			
    		} else if ("hospit".equals(aType) && patient!=null) {
    			res.append("<h2>Cтационар осмотры</h2>") ;
    			sql=new StringBuilder() ;
    			sql.append("select vwf.id,vwf.name from MedCase smo") ;
    			sql.append(" left join diary d on smo.id=d.medcase_id");
    			sql.append(" left join WorkFunction wf on wf.id=d.specialist_id");
    			sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
    			sql.append(" left join worker w on wf.worker_id=w.id");
    			sql.append(" left join patient wp on wp.id=w.person_id");
    			sql.append(" where smo.patient_id='").append(patient).append("'");
    			sql.append(" and upper(smo.dtype) in ('HOSPITALMEDCASE','DEPARTMENTMEDCASE') and upper(d.dtype)='PROTOCOL'");
    			sql.append(" group by vwf.id,vwf.name");
    			sql.append(" order by vwf.name");
    			sql.append(" ") ;
    			list = service.executeNativeSql(sql.toString(),30);
        		res.append("<ul>");
        		for (WebQueryResult wqr:list) {
        			res.append("<li class='liTemp' onclick=\"").append(aFunction).append("('").append(aType).append("','")
        			.append(wqr.get1()).append("')\" ondblclick=\"").append(aFunction).append("('").append(aType).append("','")
        			.append(wqr.get1()).append("')\">") ;
    				res.append(wqr.get2());
    				res.append("</li>") ;
    			}
    			res.append("</ul>");
    			
    		} else if ("disch".equals(aType) && patient!=null) {
    			res.append("<h2>Cтационар выписки</h2>") ;
    			sql = new StringBuilder() ;
    			sql.append("select ml.id,ml.name as rec from MedCase sls") ;
    			sql.append(" left join MedCase slo on sls.id=slo.parent_id");
    			sql.append(" left join MisLpu ml on ml.id=slo.department_id");
    			sql.append(" where sls.patient_id='").append(patient).append("'");
    			sql.append(" and upper(sls.dtype)='HOSPITALMEDCASE' and sls.dateFinish is not null and slo.dateFinish is not null");
    			sql.append(" group by ml.id,ml.name");
    			sql.append(" order by ml.name");
    			sql.append(" ") ;
    			list = service.executeNativeSql(sql.toString(),30);
        		res.append("<ul>");
        		for (WebQueryResult wqr:list) {
        			res.append("<li class='liTemp' onclick=\"").append(aFunction).append("('").append(aType).append("','")
        			.append(wqr.get1()).append("')\" ondblclick=\"").append(aFunction).append("('").append(aType).append("','")
        			.append(wqr.get1()).append("')\">") ;
    				res.append(wqr.get2()).append(" ") ;
    				res.append("</li>") ;
    			}
    			res.append("</ul>");
    			
    		}
    	}
    	res.append("</td></tr></table>") ;
    	return res.toString() ;
    }
    public String listProtocolsByUsername(String aSmoId,String aParent,String aType,String aFunctionTemp, String aFunctionProt,String aSearchText,
										  String diaryDate, HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		String diarySql ;
		if (diaryDate!=null && !diaryDate.equals("")) {
			diarySql=" and d.dateRegistration=to_date('"+diaryDate+"','dd.mm.yyyy')";
		} else {
			diarySql="";
		}

		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list ;
		Long patient = null ;
		res.append("<table>");
		res.append("<tr><td colspan='2' valign='top'>") ;
		if ("temp".equals(aType)) { //TODO слишком долгий запрос! Переделать! //все шаблоны
			sql.append("select tp.id as tid,case when su.login!='").append(login).append("' then '(общ) ' else '' end || tp.title as ttile")
			.append(" ,(select count(*) from parameterByForm where template_id=tp.id) as cntInput") ; 
			sql.append(" from TemplateProtocol tp");
			sql.append(" left join SecUser su on tp.username=su.login");
			sql.append(" left join templateprotocol_secgroup tg on tp.id=tg.templateprotocol_id");
			sql.append(" left join SecGroup_secUser gu on gu.secgroup_id=tg.secgroups_id");
			sql.append(" left join SecUser gsu on gsu.id=gu.secUsers_id");
			sql.append(" left join TemplateProtocol_TemplateCategory tptc on tptc.protocols_id=tp.id");
			sql.append(" where ");
			String name_cat = "" ; 
			boolean isGo = true;
			if (aParent!=null && !aParent.equals("") && !aParent.equals("0")) {
				if (aParent.equals("-1")) {
					name_cat="без категории" ;
					if (aSearchText!=null && aSearchText.length()>0) {
						sql.append("upper(tp.title) like '%").append(aSearchText.toUpperCase()).append("%'");
					} else {
						sql.append("tptc.categories_id is null ");
						isGo=false ;
					}
					
				} else{
					sql.append("tptc.categories_id='").append(aParent).append("'");
					if (aSearchText!=null && aSearchText.length()>1) {
						sql.append(" and upper(tp.title) like '%").append(aSearchText.toUpperCase()).append("%'");
					}
					list=service.executeNativeSql("select name from templatecategory where id=" + aParent) ;
					if (!list.isEmpty()) name_cat=""+list.iterator().next().get1() ;
				}
				name_cat = name_cat+"<form action='javascript:"+aFunctionProt+"Search(\""+aType+"\",\""+aParent+"\")'>" ;
				name_cat = name_cat+"<input type='text' id='fldSearch"+aFunctionProt+"' name='fldSearch"+aFunctionProt+"' value='"+(aSearchText!=null?aSearchText:"")+"'>" ;
				name_cat = name_cat+"<input  type='submit' value='Поиск' onclick='"+aFunctionProt+"Search(\""+aType+"\",\""+aParent+"\")'>" ;
				name_cat = name_cat+"</form>" ;
			} else {sql.append("tptc.categories_id is null ");}
			sql.append(" group by tp.id,tp.title,su.login");
			sql.append(" order by tp.title") ;
			LOG.info("long sql = "+sql);
			if (isGo) {
				if (aSearchText!=null && aSearchText.length()>0) {
					
					list = service.executeNativeSql(sql.toString(),30);
				} else {
					list = service.executeNativeSql(sql.toString());
				
				}
				res.append("<h2>Список шаблонов категории: ").append(name_cat).append("</h2>") ;
				res.append("</td>") ;
				res.append("</tr><tr><td colspan='2' valign='top'>") ;
				res.append("<ul>");
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionTemp).append("('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionTemp).append("('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append(wqr.get2()) ;
					if (wqr.get3()!=null&&!(""+wqr.get3()).equals("0")) {
						res.append("<input type=\"button\" onclick=\"showTemplateForm('").append(wqr.get1()).append("')\" value=\"Ф\">");
						}
					res.append("</li>") ;
				}
				res.append("</ul></td>") ;
			} else {
				res.append("<h2>").append(name_cat).append("</h2>") ;
				res.append("</td>") ;
				res.append("</tr><tr><td colspan='2' valign='top'>") ;
				res.append("<ul>");
				
				res.append("</ul></td>") ;
			}
		} else if ("my".equals(aType)) { //свои шаблоны
			sql.append("select tp.id as tid,case when su.login!='").append(login).append("' then '(общ) ' else '' end || tp.title as ttile" +
					" ,(select count(*) from parameterByForm where template_id=tp.id) as cntInput") ; 
			sql.append(" from TemplateProtocol tp");
			sql.append(" left join SecUser su on tp.username=su.login");
			sql.append(" left join templateprotocol_secgroup tg on tp.id=tg.templateprotocol_id");
			sql.append(" left join SecGroup_secUser gu on gu.secgroup_id=tg.secgroups_id");
			sql.append(" left join SecUser gsu on gsu.id=gu.secUsers_id");
			sql.append(" left join TemplateProtocol_TemplateCategory tptc on tptc.protocols_id=tp.id");
			sql.append(" where (su.login='").append(login).append("' or gsu.login='").append(login).append("')");
			
			String name_cat = "" ; 
			if (aParent==null) aParent="0" ;
			if (!aParent.equals("") && !aParent.equals("0")) {
				sql.append(" and tptc.categories_id");
				if (aParent.equals("-1")) {
					name_cat="без категории" ;
					sql.append(" is null ");
				} else {
					sql.append("='").append(aParent).append("'");
					list=service.executeNativeSql("select name from templatecategory where id="+aParent) ;
					if (!list.isEmpty()) name_cat=""+list.iterator().next().get1() ;
				}
			} 
			if (aSearchText!=null && aSearchText.length()>0) {
				sql.append("and upper(tp.title) like '%").append(aSearchText.toUpperCase()).append("%'");
			}

			sql.append(" group by tp.id,tp.title,su.login");
			sql.append(" order by tp.title") ;
			list = service.executeNativeSql(sql.toString());
			name_cat = name_cat+"<form action='javascript:"+aFunctionProt+"Search(\""+aType+"\",\""+aParent+"\")'>" ;
			name_cat = name_cat+"<input type='text' id='fldSearch"+aFunctionProt+"' name='fldSearch"+aFunctionProt+"' value='"+(aSearchText!=null?aSearchText:"")+"'>" ;
			name_cat = name_cat+"<input  type='submit' value='Поиск' onclick='"+aFunctionProt+"Search(\""+aType+"\",\""+aParent+"\")'>" ;
			name_cat = name_cat+"</form>" ;
			res.append("<h2>Список своих шаблонов").append(" КАТЕГОРИИ: ").append(name_cat).append(" </h2>") ;
			res.append("</td>") ;
			res.append("</tr><tr><td colspan='2' valign='top'>") ;
			res.append("<ul>");
			for (WebQueryResult wqr:list) {
				res.append("<li class='liTemp' onclick=\"").append(aFunctionTemp).append("('")
				.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionTemp).append("('")
				.append(wqr.get1()).append("',1)\">") ;
				res.append(wqr.get2()) ;
				if (wqr.get3()!=null&&!(""+wqr.get3()).equals("0")) {
				res.append("<input type=\"button\" onclick=\"showTemplateForm('").append(wqr.get1()).append("')\" value=\"Ф\">");
				}
				res.append("</li>");
			}
			res.append("</ul></td>") ;
		} else {
			list = service.executeNativeSql("select mc.id,mc.patient_id from medcase mc where mc.id="+aSmoId,1);
			if (!list.isEmpty()) patient = ConvertSql.parseLong(list.iterator().next().get2()) ;

			if ("mydiary".equals(aType)  && patient!=null) { //свои заключения по пациенту //ищем по дате
				res.append("<h2>заключения</h2>") ;
				res.append("<ul>");
				sql = new StringBuilder() ;
				sql.append("select d.id,  case when m.dtype='Visit' then 'Поликл.' when m.dtype='DepartmentMedCase' then 'СЛО' when m.dtype='HospitalMedCase' then 'СЛС' when m.dtype='ServiceMedCase' then 'Услуга' when m.dtype='HospitalMedCase' then 'Приемное отделение' else '' end||' '||to_char(m.dateStart,'DD.MM.YYYY')||' '||coalesce(vwf.name,vwf1.name),m.patient_id as pid")  
					.append(" from MedCase as m")   
					.append(" left join  Diary as d on m.id=d.medCase_id")         
					.append(" left join WorkFunction wf1 on wf1.id=m.ownerFunction_id")        
					.append(" left join WorkFunction wf on wf.id=m.workFunctionExecute_id")        
					.append(" left join Worker w1 on w1.id=wf1.worker_id")
					.append(" left join Worker w on w.id=wf.worker_id")
					.append(" left join VocWorkFunction vwf1 on vwf1.id=wf1.workFunction_id")         
					.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")         
					.append(" left join Patient p on p.id=w.person_id ")
					.append(" where  m.patient_id='").append(patient).append("' and upper(d.dtype)='PROTOCOL'")
					.append(diarySql);

					sql.append(" group by d.id,vwf.name,vwf1.name,m.dtype,m.datestart,m.patient_id,d.dateregistration,d.username having upper(d.username)='").append(login.toUpperCase()).append("'  order by d.dateRegistration desc") ;
				list = service.executeNativeSql(sql.toString(),diarySql.equals("") ? 10 : 100); //все дневники по дате
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append(wqr.get2()) ;
					res.append("</li>") ;
				}
				res.append("</ul></td>");
			} else if ("polyc".equals(aType) && patient!=null) { //все заключения по пациенту (поликлиника)
				res.append("<h2>Поликлиника</h2>");
					sql=new StringBuilder() ;
					sql.append("select d.id,to_char(d.dateRegistration,'dd.mm.yyyy') as dateReg,vwf.name||' '||wp.lastname as spec,substring(d.record,1,150)||'...' as rec ") ;
					sql.append(" from MedCase smo") ;
					sql.append(" left join diary d on smo.id=d.medcase_id");
					sql.append(" left join WorkFunction wf on wf.id=d.specialist_id");
					sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
					sql.append(" left join worker w on wf.worker_id=w.id");
					sql.append(" left join patient wp on wp.id=w.person_id");
					sql.append(" where smo.patient_id='").append(patient).append("'");
					sql.append(" and upper(smo.dtype) in ('VISIT' ,'SHORTMEDCASE') and upper(d.dtype)='PROTOCOL'");
					if (aParent!=null && !aParent.equals("") && !aParent.equals("0")) {
						sql.append(" and vwf.id");
						if (aParent.equals("-1")) {sql.append(" is null ");}else{sql.append("='").append(aParent).append("'");}
					}
					sql.append(diarySql);
					sql.append(" order by d.dateRegistration desc");
					sql.append(" ") ;
					res.append("<ul>");
					list = service.executeNativeSql(sql.toString(),diarySql.equals("") ? 10 : 100);
					for (WebQueryResult wqr:list) {
						res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
						.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
						.append(wqr.get1()).append("',1)\">") ;
						res.append(wqr.get2()).append(" ").append(wqr.get3()).append("<br>").append(wqr.get4()) ;
						res.append("</li>") ;
					}
					res.append("</ul>");
					
				
			} else if ("hospit".equals(aType) && patient!=null) { //осмотры врачей в стационаре
				res.append("<h2>Cтационар осмотры</h2>") ;
				sql=new StringBuilder() ;
				sql.append("select d.id,to_char(d.dateRegistration,'dd.mm.yyyy') as dateReg,vwf.name||' '||wp.lastname as spec,substring(d.record,1,150)||'...' as rec from diary d") ;
				sql.append(" left join MedCase smo on smo.id=d.medcase_id");
				sql.append(" left join WorkFunction wf on wf.id=d.specialist_id");
				sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
				sql.append(" left join worker w on wf.worker_id=w.id");
				sql.append(" left join patient wp on wp.id=w.person_id");
				sql.append(" where smo.patient_id='").append(patient).append("'");
				sql.append(" and upper(smo.dtype) in ('HOSPITALMEDCASE','DEPARTMENTMEDCASE') and upper(d.dtype)='PROTOCOL'");
				if (aParent!=null && !aParent.equals("") && !aParent.equals("0")) {
					sql.append(" and vwf.id");
					if (aParent.equals("-1")) {sql.append(" is null ");}else{sql.append("='").append(aParent).append("'");}
				}
				sql.append(diarySql);
				sql.append(" order by d.dateRegistration desc");
				sql.append(" ") ;
				res.append("<ul>");
				list = service.executeNativeSql(sql.toString(),10);
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append(wqr.get2()).append(" ").append(wqr.get3()).append("<br>").append(wqr.get4()) ;
					res.append("</li>") ;
				}
				res.append("</ul>");
				
			} else if ("disch".equals(aType) && patient!=null) { //выписки (госпитализации
				res.append("<h2>Cтационар выписки</h2>") ;
				sql = new StringBuilder() ;
				sql.append("select sls.id,to_char(sls.dateStart,'dd.mm.yyyy')||'-'||to_char(sls.dateFinish,'dd.mm.yyyy') as dateReg")
					.append(",vwf.name||' '||wp.lastname as spec,substring(sls.dischargeEpicrisis,1,150)||'...' as rec from MedCase sls") ;
				sql.append(" left join MedCase slo on sls.id=slo.parent_id and slo.dtype='DepartmentMedCase'");
				sql.append(" left join WorkFunction wf on wf.id=slo.ownerFunction_id");
				sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
				sql.append(" left join worker w on wf.worker_id=w.id");
				sql.append(" left join patient wp on wp.id=w.person_id");
				sql.append(" where sls.patient_id='").append(patient).append("'");
				sql.append(" and upper(sls.dtype)='HOSPITALMEDCASE' and sls.dateFinish is not null and slo.dateFinish is not null");
				if (aParent!=null && !aParent.equals("") && !aParent.equals("0")) {
					sql.append(" and slo.department_id");
					if (aParent.equals("-1")) {sql.append(" is null ");}else{sql.append("='").append(aParent).append("'");}
				}

				sql.append(" order by sls.dateStart desc");
				sql.append(" ") ;
				res.append("<ul>");
				list = service.executeNativeSql(sql.toString(),10);
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("Discharge('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("Discharge('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append(wqr.get2()).append(" ").append(wqr.get3()) ;
					res.append("</li>") ;
				}
				res.append("</ul>");
				
			}
		}
		res.append("</td></tr></table>") ;
		return res.toString() ;
	}
    public String getPreviousText(String aId, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aId)) {
            return "" ;
        } else {
            ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class) ;
            return service.getTextByProtocol(Long.parseLong(aId)) ;
        }
    }
    public void saveParametersByMedService(long aProtocol, String aAdds, String[] aRemoves, HttpServletRequest aRequest) throws NamingException {
    	saveParametersByMedServiceByType("template_id",aProtocol, aAdds, aRemoves, aRequest); 
    } 
    public void saveParametersByMedServiceByType(String aIdFieldName, long aProtocol, String aAdds, String[] aRemoves, HttpServletRequest aRequest) throws NamingException {
    	String[] ad = aAdds.split(",") ;
    	long[] adds = TemplateSaveAction.getLongs(ad);
    	long[] removes = TemplateSaveAction.getLongs(aRemoves);
    	IDiaryService service = (IDiaryService) Injection.find(aRequest).getService("DiaryService");
    	service.saveParametersByTemplateProtocol(aIdFieldName,aProtocol, adds, removes) ;
    }
    public Long getCountSymbolsInProtocol(long aVisit,  HttpServletRequest aRequest) throws NamingException {
    	 ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class) ;
    	 return service.getCountSymbolsInProtocol(aVisit) ;
    }
    public static String getUsername(HttpServletRequest aRequest) {
    	 LoginInfo loginInfo = LoginInfo.find(aRequest.getSession()) ;
    	return loginInfo!=null ? loginInfo.getUsername() : "" ;
    }
	/**
	 * Проверить, может ли пользователь редактировать протокол (может, если он и есть создатель дневника).
	 *
	 * @param aUserCreate Создатель дневника
	 * @param aRequest HttpServletRequest
	 * @return boolean true - может, false - не может
	 */
    public boolean isCanEditProtocol(String aUserCreate, HttpServletRequest aRequest) {
    	return (getUsername(aRequest).equals(aUserCreate));
    }
	/**
	 * Получает единственное возможное значение разрешения по коду, в случае, если оно - единственное в справочнике
	 *
	 * @param objectPermission Объект, на который даётся разрешение
	 * @param aRequest HttpServletRequest
	 * @return String Json с результатом или пустой
	 */
	public String getDefaultValueForPermission(String objectPermission, HttpServletRequest aRequest) throws NamingException {
		JSONObject ret = new JSONObject();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select case when \n" +
				"(select count(distinct vp.id) from vocPermission vp\n" +
				"left join vocobjectpermission vop on vop.code=vp.objectcode \n" +
				"where vop.id=vopmain.id)=1\n" +
				"then vp.id||'#'||vp.name\n" +
				"end from vocobjectpermission vopmain\n" +
				"left join vocpermission vp on vopmain.code=vp.objectcode \n" +
				"where vopmain.id="+objectPermission);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next();
			if (wqr.get1()!=null) {
				String[] vals = wqr.get1().toString().split("#") ;
				ret.put("id",vals[0]);
				ret.put("name",vals[1]);
			}
		}
		return ret.toString();
	}

	/**
	 * Получает период редактирования по умолчанию. Выписка - период госпитализации, дневник - дата регистрации
	 *
	 * @param objectCode Тип объекта (протокол/выписка)
	 * @param objectPermission Объект, на который даётся разрешение
	 * @param aRequest HttpServletRequest
	 * @return String Json с результатом или пустой
	 */
	public String getPeriodForPermission(String objectCode,String objectPermission, HttpServletRequest aRequest) throws NamingException {
		JSONObject ret = new JSONObject();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder();
		sql.append("select case when vop.code='Protocol' then")
				.append(" to_char(d.dateRegistration,'dd.mm.yyyy')||'#'||to_char(d.dateRegistration,'dd.mm.yyyy')")
				.append("||'#'||su.id||'#'||d.username||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename")
				.append(" end from Diary d")
				.append(" left join vocobjectpermission vop on vop.id=").append(objectCode)
				.append(" left join secuser su on su.login=d.username")
				.append(" left join workfunction wf on wf.secuser_id=su.id")
				.append(" left join worker w on w.id = wf.worker_id")
				.append(" left join patient wp on wp.id=w.person_id")
				.append(" where d.id=").append(objectPermission);
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		if (list.isEmpty()) {
			sql = new StringBuilder();
			sql.append("select case when vop.code='DischargeMedCase' then")
					.append(" to_char(mc.dateStart,'dd.mm.yyyy')||'#'||to_char(mc.dateFinish,'dd.mm.yyyy')")
					.append("||'#'||su.id||'#'||mc.editusername||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename")
					.append(" end from Medcase mc")
					.append(" left join vocobjectpermission vop on vop.id=").append(objectCode)
					.append(" left join secuser su on su.login=mc.editusername")
					.append(" left join workfunction wf on wf.secuser_id=su.id")
					.append(" left join worker w on w.id = wf.worker_id")
					.append(" left join patient wp on wp.id=w.person_id")
					.append(" where mc.id=").append(objectPermission);
			list = service.executeNativeSql(sql.toString());
		}
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next();
			if (wqr.get1()!=null) {
				String[] vals = wqr.get1().toString().split("#") ;
				ret.put("dateStart",vals[0]);
				ret.put("dateFinish",vals[1]);
				ret.put("suId",vals[2]);
				ret.put("suLogin",vals[3]);
			}
		}
		return ret.toString();
	}
	public static Long parseLong(Object aValue) {
		if (aValue==null) return null ;
		if (aValue instanceof Integer) {
			
			return Long.valueOf((Integer) aValue) ;
		}
		if(aValue instanceof BigInteger) {
			BigInteger bigint = (BigInteger) aValue ;
			
			return bigint.longValue() ;
		} 
		if (aValue instanceof Number) {
			Number number = (Number) aValue ;
			return number.longValue() ;
		}
		if (aValue instanceof String) {
			return Long.valueOf((String) aValue);
		}
		return null ;
	}
	/**
	 * Получить риск по баллам
	 * @param aCardTypeId Тип карты оценки
	 * @param val Long Результат (баллы)
	 * @return String
	 */
	public String getRisk(String aCardTypeId, BigDecimal val, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection<WebQueryResult> res = service.executeNativeSql
				("select name from assessment" +
						" where "+ val + " between minball and maxball and assessmentcard=" + aCardTypeId);
		return !res.isEmpty()? res.iterator().next().get1().toString() : "";
	}

	/**
	 * Получить текст протокола
	 * @param aProtocolId Diary.id
	 * @return String
	 */
	public String getBasisProtocolRecord(Long aProtocolId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection<WebQueryResult> res = service.executeNativeSql("select record from diary where id = " + aProtocolId);
		return !res.isEmpty()? res.iterator().next().get1().toString() : "";
	}

	/**
	 * Сохранить текст протокола с печатными символами
	 * @param aProtocolId Diary.id
	 * @param record Текст
	 */
	public void saveRecordUnprint(Long aProtocolId, String record, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		service.executeUpdateNativeSql("update diary set record='" + record + "' where id = " + aProtocolId);
		//отметка о редактировании
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
        service.executeUpdateNativeSql("update diary set editusername='" + username + "',editdate=current_date,edittime=current_time where id = " + aProtocolId);
	}
}
