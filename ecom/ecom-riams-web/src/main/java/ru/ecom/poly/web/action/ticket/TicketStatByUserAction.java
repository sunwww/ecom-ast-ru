package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.util.ActionUtil;
import ru.ecom.web.login.LoginInfo;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class TicketStatByUserAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		//ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
		//String idString = aRequest.getParameter("id") ;
		//AdmissionJournalForm form = (AdmissionJournalForm) aForm ;
		String dateChange =ActionUtil.updateParameter("TicketStatByUser","dateChange","1", aRequest) ;
		ActionUtil.updateParameter("TicketStatByUser","period","2", aRequest) ;
		if (dateChange.equals("2")) {
			aRequest.setAttribute("dateSearch", "\"date\"") ;
			aRequest.setAttribute("dateSearch1", "date") ;
		}else {
			aRequest.setAttribute("dateSearch", "dateCreate") ;
			aRequest.setAttribute("dateSearch1", "dateCreate") ;
		}
		if (RolesHelper.checkRoles("/Policy/Poly/Ticket/ShowInfoAllUsers", aRequest)) {
			aRequest.setAttribute("add", "") ;
		} else {
			if (aRequest.getSession(true)!=null) {
				aRequest.setAttribute("add", new StringBuilder().append("and  usernameCreate='")
						.append(LoginInfo.find(aRequest.getSession(true)).getUsername()) 
						.append("'").toString()) ;
				
			} else {
				aRequest.setAttribute("add", new StringBuilder().append("and  usernameCreate is null").toString()) ;				
			}
		}
		return aMapping.findForward("success");
	}

}
