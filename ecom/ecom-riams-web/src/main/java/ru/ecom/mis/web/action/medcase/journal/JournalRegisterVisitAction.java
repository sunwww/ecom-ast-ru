package ru.ecom.mis.web.action.medcase.journal;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.mis.web.action.util.ActionUtil;
import ru.ecom.poly.web.action.ticket.JournalBySpecialistForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class JournalRegisterVisitAction  extends BaseAction {

	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		JournalBySpecialistForm form = (JournalBySpecialistForm)aRequest.getSession().getAttribute("poly_journalBySpecForm");
		String args =form.getBeginDate()+":"+form.getFinishDate()
		+":"+form.getSpecialist()+":"+form.getRayon()
		+":"+form.getPrimaryInYear() +":" +form.getNumberInJournal() +":"
		;
		aRequest.setAttribute("beginDate", form.getBeginDate()) ;
		aRequest.setAttribute("finishDate", form.getFinishDate()) ;
		aRequest.setAttribute("specialist", form.getSpecialist()) ;
		aRequest.setAttribute("rayon", form.getRayon()) ;
		aRequest.setAttribute("primaryInYear", form.getPrimaryInYear()) ;
		String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", aRequest) ;
		System.out.println("args="+args) ;
		if (form.getOrderBySpecialist()!=null &&
				form.getOrderBySpecialist().equals(Boolean.TRUE)) {
			args=args+"workFunction_id" ;
			aRequest.setAttribute("order", "Сортировка по специалисту") ;
		}  else {
			aRequest.setAttribute("order", "Сортировка по времени приема") ;
			args=args+"dateStart,timeExecute" ;
		}
		args=args+":"+form.getWorkFunction()+":"+form.getLpu()+":"+form.getServiceStream()+":"+typeDtype ;
		aRequest.setAttribute("func", form.getWorkFunction()) ;
		if (form!=null) {
			IScriptService script = Injection.find(aRequest).getService(IScriptService.class) ; 
			aRequest.setAttribute("listRegisterVisit",script.invoke("SmoVisitService","journalRegisterVisitByMap", 
				new Object[]{args})) ;
		} else {
			aRequest.setAttribute("listRegisterVisit",new java.util.ArrayList()) ;
		}
		
		return aMapping.findForward("success") ;
	}
	

}
