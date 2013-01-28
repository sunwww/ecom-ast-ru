package ru.ecom.web.poly.visit;

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

public class ListVocWorkFunctionAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	StringBuilder sql = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean remoteUser = true ;
		//boolean aManyIs = false ;
		String depId= aRequest.getParameter("department") ;
		String addParam=PreRecordAction.saveData(aRequest) ;
		sql.append("select vwf.id||'&vocWorkFunction='||vwf.id||'").append(addParam).append("' as vwfid,vwf.code as vwfcode,vwf.name as vwfname") ;
		sql.append(" from VocWorkFunction vwf ") ;
		sql.append(" left join WorkFunction wf on vwf.id=wf.workFunction_id") ; 
		sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id") ; 
		sql.append(" left join Worker w on w.id=wf.worker_id") ; 
		sql.append(" left join MisLpu m2 on m2.id=w.lpu_id") ; 
		sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id") ;
		sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id") ;
 
		sql.append(" where wcd.calendarDate>=Current_date");
		if (remoteUser) sql.append(" and (wf.DTYPE='PersonalWorkFunction' and m2.id='").append(depId).append("' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and m1.id='").append(depId).append("' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0')) and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0')") ;
		sql.append(" group by vwf.id,vwf.code,vwf.name") ;
		sql.append(" order by vwf.name") ;
		
		StringBuilder res = new StringBuilder() ;
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