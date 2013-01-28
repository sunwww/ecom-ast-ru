package ru.ecom.template.web.dwr;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;

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
        	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        	StringBuilder sql = new StringBuilder() ;
        	sql.append("select em.id,em.dischargeEpicrisis from MedCase em where em.id=").append(aId).append(" and dtype='HospitalMedCase' and dischargeEpicrisis is not null") ;
        	Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
        	return list.isEmpty()?"":""+list.iterator().next().get2() ;
        }
    }
    public String listProtocolsByUsername(String aSmoId,String aFunctionTemp, String aFunctionProt,String aVersion,HttpServletRequest aRequest) throws NamingException, JspException {
		StringBuilder sql = new StringBuilder() ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sql.append("select tp.id as tid,case when su.login!='").append(login).append("' then '(общ) ' else '' end || tp.title as ttile") ; 
		sql.append(" from TemplateProtocol tp");
		sql.append(" left join SecUser su on tp.username=su.login");
		sql.append(" left join templateprotocol_secgroup tg on tp.id=tg.templateprotocol_id");
		sql.append(" left join SecGroup_secUser gu on gu.secgroup_id=tg.secgroups_id");
		sql.append(" left join SecUser gsu on gsu.id=gu.secUsers_id");
		sql.append(" where su.login='").append(login).append("' or gsu.login='").append(login).append("'");
		sql.append(" group by tp.id,tp.title,su.login");
		sql.append(" order by tp.title") ;
		//sql.append("select t.id as tid,t.title as ttile") ;
		//sql.append(" from TemplateProtocol t") ;
 
		//sql.append(" where t.username='").append(login).append("'") ;
		//sql.append(" order by t.title") ;
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		res.append("<table>");
		res.append("<tr><td colspan='3'><h2>Выбор осуществляется двойным нажатием мыши</h2></td></tr>") ;
		res.append("<tr><td colspan='2' valign='top'>") ;
		res.append("<h2>Список своих шаблонов</h2>") ;
		res.append("</td><td colspan='1' valign='top'><h2>Список протоколов по пациенту</h2></td></tr><tr><td valign='top'>") ;
		res.append("<h2>шаблоны</h2>") ;
		res.append("<ul>");
		for (WebQueryResult wqr:list) {
			res.append("<li class='liTemp' onclick=\"").append(aFunctionTemp).append("('")
			.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionTemp).append("('")
			.append(wqr.get1()).append("',1)\">") ;
			res.append(wqr.get2()) ;
			res.append("</li>") ;
		}
		res.append("</ul></td>") ;
		Long patient = null ;
		if (aVersion!=null && aVersion.equals("Visit")) {
			res.append("<td valign='top' style='padding-left:20px'>");
			res.append("<h2>заключения</h2>") ;
			res.append("<ul>");
			sql = new StringBuilder() ;
			sql.append("select d.id,  case when m.dtype='Visit' then 'Поликл.' when m.dtype='DepartmentMedCase' then 'СЛО' when m.dtype='HospitalMedCase' then 'СЛС' when m.dtype='ServiceMedCase' then 'Услуга' when m.dtype='HospitalMedCase' then 'Приемное отделение' else '' end||' '||to_char(m.dateStart,'DD.MM.YYYY')||' '||coalesce(vwf.name,vwf1.name),m.patient_id as pid")  
				.append(" from  Diary as d")   
				.append(" left join MedCase as m on m.id=d.medCase_id")         
				.append(" left join MedCase m1 on m1.patient_id=m.patient_id")
				.append(" left join WorkFunction wf1 on wf1.id=m.ownerFunction_id")        
				.append(" left join WorkFunction wf on wf.id=m.workFunctionExecute_id")        
				.append(" left join Worker w1 on w1.id=wf1.worker_id")
				.append(" left join Worker w on w.id=wf.worker_id")
				.append(" left join VocWorkFunction vwf1 on vwf1.id=wf1.workFunction_id")         
				.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")         
				.append(" left join Patient p on p.id=w.person_id ")
				.append(" where  d.dtype='Protocol'")
				//.append(" and m.dtype='Visit'") 
				//.append(" and m1.dtype='Visit'")  
				.append(" and d.username='").append(login).append("'  and m1.id='")
				.append(aSmoId)
				.append("'  order by m.dateStart desc") ;
			list.clear() ;
			list = service.executeNativeSql("select mc.id,mc.patient_id from medcase mc where mc.id="+aSmoId,1);
			if (list.size()>0) patient = ConvertSql.parseLong(list.iterator().next().get2()) ;
			list.clear() ;
			list = service.executeNativeSql(sql.toString(),10);
			for (WebQueryResult wqr:list) {
				res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
				.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
				.append(wqr.get1()).append("',1)\">") ;
				res.append(wqr.get2()) ;
				res.append("</li>") ;
			}
			res.append("</ul></td>");
		} else if (aVersion!=null && aVersion.equals("Ticket")) {
			res.append("<td valign='top' style='padding-left:20px'><ul>");
			sql = new StringBuilder() ;
			sql.append("select d.id,  to_char(t.date,'DD.MM.YYYY')||' '||vwf.name")  
				.append(" from  Diary as d")   
				.append("  left join Ticket as t on t.id=d.ticket_id left join Medcard m on m.id=t.medcard_id left join Ticket t1 on t1.medcard_id=t.medcard_id left join WorkFunction wf on wf.id=t.workFunction_id left join Worker w on w.id=wf.worker_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Patient p on p.id=w.person_id  ")
				.append(" where  t1.id='").append(aSmoId)
				.append("' and d.username='").append(login).append("' and d.dtype='Protocol'")
				.append("   order by t.date desc") ;
			list.clear() ;
			list = service.executeNativeSql("select t.id,m.person_id from ticket t left join medcard m on m.id=t.medcard_id where t.id="+aSmoId,1);
			if (list.size()>0) patient = ConvertSql.parseLong(list.iterator().next().get2()) ;
			list.clear() ;
			list = service.executeNativeSql(sql.toString(),10);
			boolean isStart = true ;
			for (WebQueryResult wqr:list) {
				if (isStart) {
					patient = ConvertSql.parseLong(wqr.get3()) ;
					isStart=false ;
				}
				res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
				.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
				.append(wqr.get1()).append("',1)\">") ;
				res.append(wqr.get2()) ;
				res.append("</li>") ;
			}
			res.append("</ul></td>");
			//res.append("<td valign='top' style='padding-left:20px'>");
			
		}
		System.out.println(patient) ;
		if (patient!=null) {
			res.append("<td valign='top' style='padding-left:20px'><table>");
			res.append("<tr>");
			res.append("<td valign='top'><h2>Поликлиника</h2>");
			if (aVersion!=null && aVersion.equals("Visit")) {
				sql=new StringBuilder() ;
				sql.append("select d.id,to_char(d.dateRegistration,'dd.mm.yyyy') as dateReg,vwf.name||' '||wp.lastname as spec,substring(d.record,1,150)||'...' as rec from diary d") ;
				sql.append(" left join MedCase smo on smo.id=d.medcase_id");
				sql.append(" left join WorkFunction wf on wf.id=d.specialist_id");
				sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
				sql.append(" left join worker w on wf.worker_id=w.id");
				sql.append(" left join patient wp on wp.id=w.person_id");
				sql.append(" where smo.patient_id='").append(patient).append("'");
				sql.append(" and (smo.dtype='Visit')");
				sql.append(" order by d.dateRegistration desc");
				sql.append(" ") ;
				res.append("<ul>");
				list.clear() ;
				list = service.executeNativeSql(sql.toString(),10);
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append(wqr.get2()).append(" ").append(wqr.get3()).append("<br>").append(wqr.get4()) ;
					res.append("</li>") ;
				}
				res.append("</ul>");
				
			} else if (aVersion!=null && aVersion.equals("Ticket")) {
				sql=new StringBuilder() ;
				sql.append("select d.id,to_char(d.dateRegistration,'dd.mm.yyyy') as dateReg,vwf.name||' '||wp.lastname as spec,substring(d.record,1,150)||'...' as rec from diary d") ;
				sql.append(" left join Ticket smo on smo.id=d.ticket_id");
				sql.append(" left join Medcard mc on mc.id=smo.medcard_id") ;
				sql.append(" left join WorkFunction wf on wf.id=d.specialist_id");
				sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
				sql.append(" left join worker w on wf.worker_id=w.id");
				sql.append(" left join patient wp on wp.id=w.person_id");
				sql.append(" where mc.person_id='").append(patient).append("'");
				sql.append(" order by d.dateRegistration desc");
				sql.append(" ") ;
				res.append("<ul>");
				list.clear() ;
				list = service.executeNativeSql(sql.toString(),10);
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append(wqr.get2()).append(" ").append(wqr.get3()).append("<br>").append(wqr.get4()) ;
					res.append("</li>") ;
				}
				res.append("</ul>");
			}
			res.append("</td>") ;
		if (aVersion!=null && aVersion.equals("Visit")) {
			res.append("<td valign='top' style='padding-left:20px'>");
			res.append("<h2>Cтационар выписки</h2>") ;
			sql = new StringBuilder() ;
			sql.append("select sls.id,to_char(sls.dateStart,'dd.mm.yyyy')||'-'||to_char(sls.dateFinish,'dd.mm.yyyy') as dateReg")
				.append(",vwf.name||' '||wp.lastname as spec,substring(sls.dischargeEpicrisis,1,150)||'...' as rec from MedCase sls") ;
			sql.append(" left join MedCase slo on sls.id=slo.parent_id");
			sql.append(" left join WorkFunction wf on wf.id=slo.ownerFunction_id");
			sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
			sql.append(" left join worker w on wf.worker_id=w.id");
			sql.append(" left join patient wp on wp.id=w.person_id");
			sql.append(" where sls.patient_id='").append(patient).append("'");
			sql.append(" and (sls.dtype='HospitalMedCase') and sls.dateFinish is not null and sls.dischargeEpicrisis is not null and slo.dateFinish is not null");
			sql.append(" order by sls.dateStart desc");
			sql.append(" ") ;
			res.append("<ul>");
			list.clear() ;
			list = service.executeNativeSql(sql.toString(),10);
			for (WebQueryResult wqr:list) {
				res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("Discharge('")
				.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("Discharge('")
				.append(wqr.get1()).append("',1)\">") ;
				res.append(wqr.get2()).append(" ").append(wqr.get3()).append("<br>").append(wqr.get4()) ;
				res.append("</li>") ;
			}
			res.append("</ul>");
			res.append("<h2>Cтационар осмотры</h2>") ;
			sql=new StringBuilder() ;
			sql.append("select d.id,to_char(d.dateRegistration,'dd.mm.yyyy') as dateReg,vwf.name||' '||wp.lastname as spec,substring(d.record,1,150)||'...' as rec from diary d") ;
			sql.append(" left join MedCase smo on smo.id=d.medcase_id");
			sql.append(" left join WorkFunction wf on wf.id=d.specialist_id");
			sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id");
			sql.append(" left join worker w on wf.worker_id=w.id");
			sql.append(" left join patient wp on wp.id=w.person_id");
			sql.append(" where smo.patient_id='").append(patient).append("'");
			sql.append(" and (smo.dtype='HospitalMedCase' or smo.dtype='DepartmentMedCase')");
			sql.append(" order by d.dateRegistration desc");
			sql.append(" ") ;
			res.append("<ul>");
			list.clear() ;
			list = service.executeNativeSql(sql.toString(),10);
			for (WebQueryResult wqr:list) {
				res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("('")
				.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("('")
				.append(wqr.get1()).append("',1)\">") ;
				res.append(wqr.get2()).append(" ").append(wqr.get3()).append("<br>").append(wqr.get4()) ;
				res.append("</li>") ;
			}
			res.append("</ul>");

			res.append("</td>") ;
			boolean external = RolesHelper.checkRoles("/Policy/Mis/MedCase/Document/External/View", aRequest) ;
			if (external) {
				res.append("<td valign='top' style='padding-left:20px'>");
				res.append("<h2>Лаборатория</h2>") ;
				sql = new StringBuilder() ;
				sql.append("select em.id")
					.append("	,'ФИО: '||em.PatientLastname||' '||PatientFirstname||' '")
					.append("	||em.PatientMiddlename")
					.append("	||' '||to_char(em.PatientBirthday,'dd.mm.yyyy') as fio,")	
					.append("'ЛПУ: '||OrderLpu")
					.append("||'\nВрач: '||Orderer")
					.append("||'\nДата направления: '||to_char(em.OrderDate,'dd.mm.yyyy')")
					.append("	as orderInfo")
					.append("	,substring(em.comment,1,150)||'...' from Document em") 
					.append("	where em.patient_id='").append(patient).append("' and em.dtype='ExternalMedservice' order by em.OrderDate desc") ;
				list.clear() ;
				list = service.executeNativeSql(sql.toString(),10);
				for (WebQueryResult wqr:list) {
					res.append("<li class='liTemp' onclick=\"").append(aFunctionProt).append("External('")
					.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionProt).append("External('")
					.append(wqr.get1()).append("',1)\">") ;
					res.append("").append(wqr.get2()).append("<br><pre>").append(wqr.get3()).append("<br>").append(wqr.get4()).append("</pre>") ;
					res.append("</li>") ;
				}
				res.append("</td>") ;
			}
		}
			res.append("</tr>") ;
			res.append("</table></td>");
		}
		res.append("</tr></table>") ;
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
    public void saveParametersByMedService(long aProtocol, String[] aAdds, String[] aRemoves, HttpServletRequest aRequest) throws NamingException {
    	long[] adds = TemplateSaveAction.getLongs(aAdds);
    	long[] removes = TemplateSaveAction.getLongs(aRemoves);
    	IDiaryService service = (IDiaryService) Injection.find(aRequest).getService("DiaryService");
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
