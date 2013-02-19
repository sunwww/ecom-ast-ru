package ru.ecom.web.poly.actions.visit.prerecord;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ListWorkFunctionAction   extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	StringBuilder sql = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean remoteUser = true ;
		boolean aManyIs = false ;
		String vocWorkFunction= aRequest.getParameter("vocWorkFunction") ;
		String department= aRequest.getParameter("department") ;
		String addParam=PreRecordAction.saveData(aRequest) ;
		sql.append("select wc.id||'&workCalendar='||wc.id||'&year='||to_char(min(wcd.calendarDate),'yyyy')||'&month='||to_char(min(wcd.calendarDate),'mm')||'").append(addParam).append("' as wcid").append(" , coalesce(wp.lastname||' '||wp.firstname||' '||coalesce(wp.middlename,''),wf.groupName) || case when wf.comment is not null and wf.comment!='' then ' (<font color=red>'||upper(wf.comment)||')</font>' else '' end ||' '||to_char(min(wcd.calendarDate),'dd.mm.yyyy') as wfInfo") ;
		sql.append(" ,to_char(min(wcd.calendarDate),'dd.mm.yyyy') as CDdate") ;
		sql.append(" ,to_char(min(wcd.calendarDate),'yyyy') as CDyear");
		sql.append(" ,to_char(min(wcd.calendarDate),'mm') as CDmonth ");
		//sql.append(" ,case when wf.dtype='PersonalWorkFunction' then m2.name else m1.name end as lpu") ;
		sql.append(" from WorkFunction wf") ;
		sql.append(" left join Worker w on w.id=wf.worker_id");
		sql.append(" left join Patient wp on wp.id=w.person_id");
		sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id");
		sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id");
		sql.append(" left join WorkCalendarTime wct on wct.workCalendarDay_id=wcd.id");
		sql.append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id") ;
		sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id") ; 
		sql.append(" left join MisLpu m2 on m2.id=w.lpu_id") ; 
		
		sql.append(" where wf.workFunction_id='").append(vocWorkFunction).append("'");
		sql.append(" and wcd.calendarDate>=CURRENT_DATE");
		sql.append(" and wct.medCase_id is null") ;
		if (remoteUser) {
			sql.append(" and (wf.DTYPE='PersonalWorkFunction' and m2.id='").append(department).append("' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and m1.id='").append(department).append("' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0'))") ;
			sql.append(" and (vsrt.isViewRemoteUser is null or vsrt.isViewRemoteUser='0')");
		}
		if (!remoteUser) sql.append(" and (wf.DTYPE='PersonalWorkFunction' and m2.id='").append(department).append("' or wf.dtype='GroupWorkFunction' and m1.id='").append(department).append("' )") ;
		sql.append(" group by wc.id,wp.lastname,wp.firstname,wp.middlename,wf.groupName,wf.comment");
		sql.append(" order by wf.groupName,wp.lastname,wp.firstname,wp.middlename") ;
 
		
		
			//Collection<WebQueryResult> listLpu = service.executeNativeSql(sql.toString(),50);
		
		//StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		/*res.append("<form name='frmFunctions' id='frmFunctions' action='javascript:step3()'><ul id='listFunctions'>") ;
		res.append("<li class='title'>Специалисты</li>");
		for (WebQueryResult wqr:list) {
			
			if (aManyIs) {
				res.append("<li onclick=\"if (this.childNodes[1].checked) {this.childNodes[1].checked=false;}else{this.childNodes[1].checked=true} step3('")
				.append(wqr.get1()).append("#").append(wqr.get3()).append("')\">") ;
				res.append(" <input style='display:none' readOnly='true' class='");
				res.append("radio") ;
			} else {
				res.append("<li onclick=\"this.childNodes[1].checked='checked';step3('")
				.append(wqr.get1()).append("#").append(wqr.get3()).append("')\">") ;
				res.append(" <input class='");
				res.append("radio") ;
			}
			res.append("' type='");
			if (aManyIs) {
				res.append("checkbox") ;
			} else {
				res.append("radio") ;
			}
			res.append("' name='rdFunction' id='rdFunction' value='")
			.append(wqr.get1()).append("#").append(wqr.get3()).append("'>") ;
			res.append(wqr.get3()) ;
			res.append("</li>") ;
		}
		res.append("</ul></form>") ;*/
		aRequest.setAttribute("listFunctions", list) ;
        return aMapping.findForward("success") ;

    }
}