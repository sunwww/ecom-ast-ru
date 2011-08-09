package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class TicketByNonresidentDataAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
		String idString = aRequest.getParameter("id") ;
		int ind1 = idString.indexOf(":");
		String date =idString.substring(0,ind1) ;
		String typePat = idString.substring(ind1+1) ;
		System.out.println(date) ;
		System.out.println(typePat) ;
		
        aRequest.setAttribute("list", service.findTicketByNonresidentByDate(typePat,date));
		return aMapping.findForward("success");
	}

}