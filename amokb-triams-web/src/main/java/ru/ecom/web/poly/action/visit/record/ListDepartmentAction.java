package ru.ecom.web.poly.action.visit.record;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.poly.action.visit.prerecord.PreRecordAction;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ListDepartmentAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	StringBuilder sql = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean remoteUser = true ;
		
		String addParam=PreRecordAction.saveData(aRequest) ;
		sql.append("select coalesce(m1.id,m2.id)||'&department='||coalesce(m1.id,m2.id)||'").append(addParam).append("' mlid,coalesce(m1.name,m2.name) as mlname") ;
		sql.append(" from VocWorkFunction vwf ") ;
		sql.append(" left join WorkFunction wf on vwf.id=wf.workFunction_id") ; 
		sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id") ; 
		sql.append(" left join Worker w on w.id=wf.worker_id") ; 
		sql.append(" left join MisLpu m2 on m2.id=w.lpu_id") ; 
		sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id") ;
		sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id") ;
 
		sql.append(" where wcd.calendarDate>=Current_date");
		if (remoteUser) sql.append(" and (wf.DTYPE='PersonalWorkFunction' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0')) and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0')") ;
		sql.append(" group by coalesce(m1.id,m2.id),coalesce(m1.name,m2.name)") ;
		sql.append(" order by coalesce(m1.name,m2.name)") ;
		
		StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		aRequest.setAttribute("dep listFunctions", list) ;
        return aMapping.findForward("success") ;

    }
}