package ru.ecom.mis.web.action.medcase.journal;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.poly.web.action.ticket.JournalBySpecialistForm;
import ru.ecom.web.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JournalRegisterVisitAction  extends BaseAction {

	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		JournalBySpecialistForm form = (JournalBySpecialistForm)aRequest.getSession().getAttribute("poly_journalBySpecForm");
		aRequest.setAttribute("beginDate", form.getBeginDate()) ;
		aRequest.setAttribute("finishDate", form.getFinishDate()) ;
		aRequest.setAttribute("specialist", form.getSpecialist()) ;
		aRequest.setAttribute("rayon", form.getRayon()) ;
		aRequest.setAttribute("primaryInYear", form.getPrimaryInYear()) ;
		ActionUtil.updateParameter("Form039Action","typeDtype","3", aRequest) ;
		if (form.getOrderBySpecialist()!=null &&
				form.getOrderBySpecialist().equals(Boolean.TRUE)) {
			aRequest.setAttribute("order", "Сортировка по специалисту") ;
		}  else {
			aRequest.setAttribute("order", "Сортировка по времени приема") ;
		}
		aRequest.setAttribute("func", form.getWorkFunction()) ;
		return aMapping.findForward(SUCCESS) ;
	}
	

}
