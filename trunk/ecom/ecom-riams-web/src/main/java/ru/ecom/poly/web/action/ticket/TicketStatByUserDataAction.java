package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class TicketStatByUserDataAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
		String idString = aRequest.getParameter("id") ;

		
		int ind1 = idString.indexOf(":");
		int ind2 = idString.indexOf(":",ind1+1);
		//int ind3 = parent.indexOf(":",ind2) ;
		System.out.println("ind1="+ind1);
		System.out.println("ind2="+ind2);
		//System.out.println("ind3="+ind3);
		System.out.println("parent lpuAndDate="+idString) ;
		String date =idString.substring(0,ind1) ;
		System.out.println("parent date="+date) ;
		String username = idString.substring(ind1+1,ind2) ;
		System.out.println("parent username="+username) ;
		String datest = idString.substring(ind2+1) ;
		System.out.println("parent datest="+datest) ;
		
		aRequest.setAttribute("stat_user", username) ;
		if (datest.equals("dateCreate")) {
			aRequest.setAttribute("stat_date", "по дате создания "+date) ;
		} else {
			aRequest.setAttribute("stat_date", "по дате приема "+date) ;
		}
		
		
        aRequest.setAttribute("list", service.findStatTicketByDateAndUsername(datest, date, username));
		return aMapping.findForward("success");
	}

}