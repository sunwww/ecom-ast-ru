package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class TicketOpenListAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
		String idString = aRequest.getParameter("id") ;
        aRequest.setAttribute("list", service.findOpenTicketByDate(idString));
		return aMapping.findForward("success");
	}

}
