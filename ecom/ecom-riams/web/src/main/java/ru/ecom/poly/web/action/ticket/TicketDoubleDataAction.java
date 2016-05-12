package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class TicketDoubleDataAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		//ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
		String idString = aRequest.getParameter("id") ;
		int ind1 = idString.indexOf(":");
		int ind2 = idString.indexOf(":",ind1+1);
		String medcard =idString.substring(0,ind1) ;
		//t.medcard_id||'#'||t.date||'#'||t.workFunction_id
		String date = idString.substring(ind1+1,ind2) ;
		String workfunction = idString.substring(ind2+1) ;
		System.out.println(date) ;
		System.out.println(workfunction) ;
		System.out.println(medcard) ;
		aRequest.setAttribute("date", date) ;
		aRequest.setAttribute("workfunction", workfunction);
		aRequest.setAttribute("medcard", medcard) ;
        //aRequest.setAttribute("list", service.findTicketByNonresidentByDate(typePat,date));
		return aMapping.findForward("success");
	}

}