package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class TicketBySpecialistDataAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
		String idString = aRequest.getParameter("id") ;
		int ind1 = idString.indexOf(":");
		int ind2 = idString.indexOf(":",ind1+1);
		String date =idString.substring(0,ind1) ;
		String typePat = idString.substring(ind1+1,ind2) ;

		System.out.println(date) ;
		System.out.println(typePat) ;

		if (date.indexOf(".")>0) {
			String spec = idString.substring(ind2+1) ;
			System.out.println(spec) ;
			aRequest.setAttribute("list", service.findTicketBySpecialistByDate(typePat,date,spec));
		} else {
			int ind3 = idString.indexOf(":",ind2+1);
			int ind4 = idString.indexOf(":",ind3+1);
			String spec = idString.substring(ind2+1,ind3) ;
			String begin= idString.substring(ind3+1,ind4) ;
			String end = idString.substring(ind4+1);
			System.out.println(spec) ;
			aRequest.setAttribute("spec", spec) ;
			aRequest.setAttribute("mkb", date);
			aRequest.setAttribute("type", typePat) ;
			aRequest.setAttribute("dateBegin", begin);
			aRequest.setAttribute("dateEnd", end) ;
		
			if (typePat.equals("2")) {
				aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.person_id,t.date)>0") ;
				aRequest.setAttribute("infoTypePat", "Поиск по иногородним") ;
			} else if (typePat.equals("1")){
				aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.person_id,t.date)=0") ;
				aRequest.setAttribute("infoTypePat", "Поиск по региональным") ;
			} else {
				aRequest.setAttribute("add", "") ;
				aRequest.setAttribute("infoTypePat", "Поиск по всем") ;
			}
		}
		return aMapping.findForward("success");
	}

}