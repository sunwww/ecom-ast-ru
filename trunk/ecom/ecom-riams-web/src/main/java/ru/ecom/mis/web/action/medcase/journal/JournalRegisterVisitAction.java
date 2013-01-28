package ru.ecom.mis.web.action.medcase.journal;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.script.IScriptService;
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
		
		System.out.println("args="+args) ;
		if (form.getOrderBySpecialist()!=null &&
				form.getOrderBySpecialist().equals(Boolean.TRUE)) {
			args=args+"workFunction_id" ;
			aRequest.setAttribute("order", "Сортировка по специалисту") ;
		}  else {
			aRequest.setAttribute("order", "Сортировка по времени приема") ;
			args=args+"dateStart,timeExecute" ;
		}
		args=args+":"+form.getWorkFunction()+":"+form.getLpu()+":"+form.getServiceStream() ;
		aRequest.setAttribute("func", form.getWorkFunction()) ;
		if (form!=null) {
			IScriptService script = Injection.find(aRequest).getService(IScriptService.class) ; 
			aRequest.setAttribute("listRegisterVisit",script.invoke("SmoVisitService","journalRegisterVisitByMap", 
				new Object[]{args})) ;
		} else {
			aRequest.setAttribute("listRegisterVisit",new java.util.ArrayList()) ;
		}
		
		/*
		//var ret = new java.util.ArrayList() ;
		String[] obj = args.split(":") ;
		SimpleDateFormat FORMAT_1 = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
		SimpleDateFormat FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat FORMAT_3 = new java.text.SimpleDateFormat("hh:mm") ;
		String startDate = FORMAT_1.format( FORMAT_2.parse(obj[0])) ;
		String finishDate = FORMAT_1.format( FORMAT_2.parse(obj[1])) ;
		Long spec = form.getSpecialist() ;
		Long rayon = form.getRayon() ;
		Long primary = form.getPrimaryInYear() ;
		int sn = Integer.valueOf(obj[5]) ;
		String order = obj[6]!=null?obj[6]:"dateStart,timeExecute" ;
		if (+sn<1) sn=1 ;
		
		String sql = "where t.dtype='Visit' and patient_id is not null and t.dateStart between '"+startDate+"' and '"+finishDate+"'" ;
		if (spec!=null && (spec>0)) {
			sql = sql + " and t.workFunctionExecute_id='"+spec+"'" ;
		}
			
		if (primary!=null && (+primary>0)) {
			sql = sql+" and t.hospitalization_id='"+primary+"'" ;
		}
		if (rayon!=null && (+rayon>0)) {
			sql = "left join patient p on p.id = t.patient_id "+sql+" and p.rayon_id='"+rayon+"'" ;
		}
		sql = "select t.id from Medcase t "+sql +" and (t.noActuality is null or cast(t.noActuality as integer)=0) order by "+order;

		*/
		
		return aMapping.findForward("success") ;
	}
	

}
