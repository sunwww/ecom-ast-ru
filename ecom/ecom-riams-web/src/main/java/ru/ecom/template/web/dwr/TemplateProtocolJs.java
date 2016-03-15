package ru.ecom.template.web.dwr;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.ecom.diary.ejb.service.protocol.IDiaryService;
import ru.ecom.diary.ejb.service.template.ITemplateProtocolService;
import ru.ecom.diary.web.action.protocol.template.TemplateSaveAction;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 25.12.2006
 * Time: 17:27:00
 * To change this template use File | Settings | File Templates.
 */
public class TemplateProtocolJs {
	
	public String createProtocolDrForCreateParam(Long aSmoId, Long aTemplate, HttpServletRequest aRequest) {
		return "" ;
	}
	
	public String getDtypeMedCase(Long aIdMedCase, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select dtype from MedCase where id="+aIdMedCase) ;
		if (list.isEmpty()) {
			return null ;
		} else {
			return new StringBuilder().append(list.iterator().next().get1()).toString() ;
		}
		
	}
	/** Получить список параметров с номерами полей по шаблону */
	public String getParameterByTemplate(Long aIdTemp, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		List<Object[]> list = service.executeNativeSqlGetObj("select id,name,code from VocSex") ;
		
		return "";
	}
	public String getText(String aId, HttpServletRequest aRequest) throws NamingException {
		if (StringUtil.isNullOrEmpty(aId)) {
			return "" ;
		} else {
			ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class) ;
			return service.getTextTemplate(Long.valueOf(aId)) ;
		}
	}
	public String getTextExternal(String aId, HttpServletRequest aRequest) throws NamingException {
		if (StringUtil.isNullOrEmpty(aId)) {
			return "" ;
		} else {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("select em.id,to_char(em.orderDate,'dd.mm.yyyy')||' '||em.comment as comment from Document em where em.id=").append(aId).append(" and dtype='ExternalMedservice'") ;
			Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
			return list.isEmpty()?"":""+list.iterator().next().get2() ;
		}
	}
    public String getTextDischarge(String aId, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aId)) {
            return "" ;
        } else {
        	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
        	return service.getDischargeEpicrisis(Long.valueOf(aId)) ;
        }
    }
    public String listCategProtocolsByUsername(String aSmoId,String aType, String aFunction,HttpServletRequest aRequest) throws NamingException, JspException {
    	StringBuilder sql = new StringBuilder() ;
    	String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder res = new StringBuilder() ;
    	Collection<WebQueryResult> list ;
    	Long patient = null ;
    	res.append("<table>");
    	//res.append("<tr><td colspan='3'><h2>Выбор осуществляется двойным нажатием мыши</h2></td></tr>") ;
    	res.append("<tr><td colspan='2' valign='top'>") ;
    	if (aType!=null && aType.equals("temp")) {
    		sql.append(" select coalesce(tc.id,-1) as tid") ;
    		sql.append(" , coalesce(tc.name,'без категории')  as ttile") ; 
    		sql.append(" from TemplateCategory tc ") ;
    		sql.append(" left join TemplateProtocol_TemplateCategory tptc on tc.id=tptc.categories_id") ;
    		sql.append(" where tc.parent_id ") ;
    		String name_categ = null ;
			if (aSmoId!=null && !aSmoId.equals("") && !aSmoId.equals("0")) {
				if (aSmoId.equals("-1")) {sql.append(" is null ");}
				else{sql.append("='").append(aSmoId).append("'");
		    		StringBuilder sql1 = new StringBuilder() ;
		    		sql1.append("select tc.parent_id from templatecategory tc where tc.id =").append(aSmoId).append(" and tc.parent_id is not null") ;
		    		list = service.executeNativeSql(sql1.toString());
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
    	} else if (aType!=null && aType.equals("my")) {
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
    			res.append("</li>") ;
    		}
    		res.append("</ul></td>") ;
    	} else {
    		list = service.executeNativeSql("select mc.id,mc.patient_id from medcase mc where mc.id="+aSmoId,1);
    		if (list.size()>0) patient = ConvertSql.parseLong(list.iterator().next().get2()) ;
    		list.clear() ;
    		
    		if (aType!=null && aType.equals("mydiary")  && patient!=null) {
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
    			.append("    group by d.dateRegistration,d.username having upper(d.username)='").append(login.toUpperCase()).append("'") 
    			.append("  order by d.dateRegistration desc") ;
    			list=null ;
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
    		} else if (aType!=null && aType.equals("polyc") && patient!=null) {
    			res.append("<h2>Поликлиника</h2>");
    			sql=new StringBuilder() ;
    			sql.append("select vwf.id,vwf.name from MedCase smo ") ;
    			sql.append(" left join diary d on smo.id=d.medcase_id");
    			sql.append(" left join WorkFunction wf on wf.id=d.specialist_id");
    			sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
    			sql.append(" left join worker w on wf.worker_id=w.id");
    			sql.append(" left join patient wp on wp.id=w.person_id");
    			sql.append(" where smo.patient_id='").append(patient).append("'");
    			sql.append(" and upper(smo.dtype) in ('VISIT' , 'SHORTMEDCASE') and upper(d.dtype)='PROTOCOL'");
    			sql.append(" group by vwf.id,vwf.name");
    			sql.append(" order by vwf.name");
    			sql.append(" ") ;
    			list=null ;
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
    			
    			
    		} else if (aType!=null && aType.equals("hospit") && patient!=null) {
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
    			list=null;
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
    			
    		} else if (aType!=null && aType.equals("disch") && patient!=null) {
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
    			list=null;
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
    public String listProtocolsByUsername(String aSmoId,String aParent,String aType,String aFunctionTemp, String aFunctionProt,String aSearchText,HttpServletRequest aRequest) throws NamingException, JspException {
		StringBuilder sql = new StringBuilder() ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list ;
		Long patient = null ;
		res.append("<table>");
		//res.append("<tr><td colspan='3'><h2>Выбор осуществляется двойным нажатием мыши</h2></td></tr>") ;
		res.append("<tr><td colspan='2' valign='top'>") ;
		if (aType!=null && aType.equals("temp")) {
			sql.append("select tp.id as tid,case when su.login!='").append(login).append("' then '(общ) ' else '' end || tp.title as ttile") ; 
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
					StringBuilder sql1 = new StringBuilder() ;
					sql1.append("select name from templatecategory where id=").append(aParent) ;
					list=service.executeNativeSql(sql1.toString()) ;
					if (!list.isEmpty()) name_cat=""+list.iterator().next().get1() ;
				}
				name_cat = name_cat+"<form action='javascript:"+aFunctionProt+"Search(\""+aType+"\",\""+aParent+"\")'>" ;
				name_cat = name_cat+"<input type='text' id='fldSearch"+aFunctionProt+"' name='fldSearch"+aFunctionProt+"' value='"+(aSearchText!=null?aSearchText:"")+"'>" ;
				name_cat = name_cat+"<input  type='submit' value='Поиск' onclick='"+aFunctionProt+"Search(\""+aType+"\",\""+aParent+"\")'>" ;
				name_cat = name_cat+"</form>" ;
			} else {sql.append("tptc.categories_id is null ");}
			sql.append(" group by tp.id,tp.title,su.login");
			sql.append(" order by tp.title") ;
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
		} else if (aType!=null && aType.equals("my")) {
			sql.append("select tp.id as tid,case when su.login!='").append(login).append("' then '(общ) ' else '' end || tp.title as ttile") ; 
			sql.append(" from TemplateProtocol tp");
			sql.append(" left join SecUser su on tp.username=su.login");
			sql.append(" left join templateprotocol_secgroup tg on tp.id=tg.templateprotocol_id");
			sql.append(" left join SecGroup_secUser gu on gu.secgroup_id=tg.secgroups_id");
			sql.append(" left join SecUser gsu on gsu.id=gu.secUsers_id");
			sql.append(" left join TemplateProtocol_TemplateCategory tptc on tptc.protocols_id=tp.id");
			sql.append(" where (su.login='").append(login).append("' or gsu.login='").append(login).append("')");
			
			String name_cat = "" ; 
			if (aParent==null) aParent="0" ;
			if (aParent!=null && !aParent.equals("") && !aParent.equals("0")) {
				sql.append(" and tptc.categories_id");
				if (aParent.equals("-1")) {
					name_cat="без категории" ;
					sql.append(" is null ");
				} else {
					sql.append("='").append(aParent).append("'");
					StringBuilder sql1 = new StringBuilder() ;
					sql1.append("select name from templatecategory where id=").append(aParent) ;
					list=service.executeNativeSql(sql1.toString()) ;
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
			res.append("<h2>Список своих шаблонов").append(name_cat!=null&&!name_cat.equals("")?" КАТЕГОРИИ: "+name_cat:"").append(" </h2>") ;
			res.append("</td>") ;
			res.append("</tr><tr><td colspan='2' valign='top'>") ;
			res.append("<ul>");
			for (WebQueryResult wqr:list) {
				res.append("<li class='liTemp' onclick=\"").append(aFunctionTemp).append("('")
				.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionTemp).append("('")
				.append(wqr.get1()).append("',1)\">") ;
				res.append(wqr.get2()) ;
				res.append("</li>") ;
			}
			res.append("</ul></td>") ;
		} else {
			list = service.executeNativeSql("select mc.id,mc.patient_id from medcase mc where mc.id="+aSmoId,1);
			if (list.size()>0) patient = ConvertSql.parseLong(list.iterator().next().get2()) ;
			list.clear() ;

			if (aType!=null && aType.equals("mydiary")  && patient!=null) {
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
					.append(" where  m.patient_id='").append(patient).append("' and upper(d.dtype)='PROTOCOL'") ;
					if (aParent!=null && !aParent.equals("") && !aParent.equals("0")) {
						sql.append(" and d.dateRegistration");
						if (aParent.equals("-1")) {sql.append(" is null ");}else{sql.append("=to_date('").append(aParent).append("','dd.mm.yyyy')");}
					}
					sql.append(" group by d.id,vwf.name,vwf1.name,m.dtype,m.datestart,m.patient_id,d.dateregistration,d.username having upper(d.username)='").append(login.toUpperCase()).append("'  order by d.dateRegistration desc") ;
				list=null ;
				list = service.executeNativeSql(sql.toString(),10);
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append(wqr.get2()) ;
					res.append("</li>") ;
				}
				res.append("</ul></td>");
			} else if (aType!=null && aType.equals("polyc") && patient!=null) {
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
					sql.append(" order by d.dateRegistration desc");
					sql.append(" ") ;
					res.append("<ul>");
					list=null ;
					list = service.executeNativeSql(sql.toString(),10);
					for (WebQueryResult wqr:list) {
						res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
						.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
						.append(wqr.get1()).append("',1)\">") ;
						res.append(wqr.get2()).append(" ").append(wqr.get3()).append("<br>").append(wqr.get4()) ;
						res.append("</li>") ;
					}
					res.append("</ul>");
					
				
			} else if (aType!=null && aType.equals("hospit") && patient!=null) {
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
				sql.append(" order by d.dateRegistration desc");
				sql.append(" ") ;
				res.append("<ul>");
				list=null;
				list = service.executeNativeSql(sql.toString(),10);
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append(wqr.get2()).append(" ").append(wqr.get3()).append("<br>").append(wqr.get4()) ;
					res.append("</li>") ;
				}
				res.append("</ul>");
				
			} else if (aType!=null && aType.equals("disch") && patient!=null) {
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
				list=null;
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
            return service.getTextByProtocol(Long.valueOf(aId)) ;
        }
    }
    public void saveParametersByMedService(long aProtocol, String aAdds, String[] aRemoves, HttpServletRequest aRequest) throws NamingException {
    	String[] ad = aAdds.split(",") ;
    	long[] adds = TemplateSaveAction.getLongs(ad);
    	long[] removes = TemplateSaveAction.getLongs(aRemoves);
    	IDiaryService service = (IDiaryService) Injection.find(aRequest).getService("DiaryService");
    	System.out.println("adds->"+aAdds+"--"+aAdds.split(",").length+" adds="+adds.length) ;
    	service.saveParametersByTemplateProtocol(aProtocol, adds, removes) ;
    }
    public Long getCountSymbolsInProtocol(long aVisit,  HttpServletRequest aRequest) throws NamingException {
    	 ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class) ;
    	 return service.getCountSymbolsInProtocol(aVisit) ;
    }
    public static String getUsername(HttpServletRequest aRequest) {
    	 LoginInfo loginInfo = LoginInfo.find(aRequest.getSession()) ;
    	return loginInfo!=null?loginInfo.getUsername():"" ;
    }
    public boolean isCanEditProtocol(Long aIdProt, String aUserCreate, HttpServletRequest aRequest) throws NamingException {
    	if (getUsername(aRequest).equals(aUserCreate)) {
    		return true ;
    	}
    	IScriptService service = (IScriptService)Injection.find(aRequest).getService("ScriptService") ;
    	
    	HashMap param = new HashMap() ;
		param.put("obj","Protocol") ;
		param.put("permission" ,"editOtherUser") ;
		param.put("id", aIdProt) ;
		
    	Object res = service.invoke("WorkerService", "checkPermission", new Object[]{param});
    	//System.out.println("res="+res) ;
    	long res1 =  parseLong(res);
    	if (res1==0) {
			 //return false;
		} else {
			return true ;
		}
    	return false ;
    }
	public static Long parseLong(Object aValue) {
		Long ret =null;
		if (aValue==null) return ret ;
		if (aValue instanceof Integer) {
			
			return Long.valueOf((Integer) aValue) ;
		}
		if(aValue instanceof BigInteger) {
			BigInteger bigint = (BigInteger) aValue ;
			
			return bigint!=null?bigint.longValue() : null;
		} 
		if (aValue instanceof Number) {
			Number number = (Number) aValue ;
			return number!=null?number.longValue() : null ;
		}
		if (aValue instanceof String) {
			return Long.valueOf((String) aValue);
		}
		return ret ;
	}
}
