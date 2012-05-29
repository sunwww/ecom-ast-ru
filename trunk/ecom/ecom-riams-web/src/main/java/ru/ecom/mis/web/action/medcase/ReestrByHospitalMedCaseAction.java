package ru.ecom.mis.web.action.medcase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.util.ActionUtil;
import ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class ReestrByHospitalMedCaseAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AdmissionJournalForm form = (AdmissionJournalForm)aForm;
		
    	String typeDate =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeDate","1", aRequest) ;
		String typeEmergency =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeEmergency","3", aRequest) ;
		String typeHour =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeHour","3", aRequest) ;
		//String emer= request.getParameter("emergancyIs") ;
		if (form!=null && form.getDateBegin()!=null && !form.getDateBegin().equals("")) {
			if (typeEmergency!=null && typeEmergency.equals("1")) {
				aRequest.setAttribute("emerIs"," and m.emergency='1'") ;
				aRequest.setAttribute("emerInfo","экстренно") ;
			} else if (typeEmergency!=null && typeEmergency.equals("2")) {
				aRequest.setAttribute("emerIs"," and (m.emergency is null or m.emergency = '0')") ;
				aRequest.setAttribute("emerInfo","планово") ;
			} else {
				aRequest.setAttribute("emerIs","") ;
				aRequest.setAttribute("emerInfo","все") ;
			}
			String dateI = null ;
			String timeI = null ;
			if (typeDate!=null && typeDate.equals("1")) {
	    		//aRequest.setAttribute("dateIs"," and m.dateStart between to_date('"+form.getDateBegin()+"','dd.mm.yyyy') and to_date('"+form.getDateBegin()+"','dd.mm.yyyy') ") ;
				dateI = "dateStart" ; timeI = "entranceTime" ;
	    		aRequest.setAttribute("dateInfo","поступившим") ;
	    	} else if (typeDate!=null && typeDate.equals("2")) {
	    		dateI = "dateFinish" ; timeI = "dischargeTime" ;
	    		aRequest.setAttribute("dateInfo","выписанным") ;
	    	} else {
	    		aRequest.setAttribute("dateI", "dateStart") ;
	    		aRequest.setAttribute("period"," and m.dateFinish is null ") ;
	    		aRequest.setAttribute("period1",null) ;
	    		aRequest.setAttribute("dateInfo","состоящим") ;
	    	}
			String period ;
			String date = form.getDateBegin() ;
			Date dat = DateFormat.parseDate(date) ;
		    Calendar cal = Calendar.getInstance() ;
		    cal.setTime(dat) ;
		    cal.add(Calendar.DAY_OF_MONTH, 1) ;
		    SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy") ;
		    String date1=format.format(cal.getTime()) ;
		    if (dateI!=null) {
		    	aRequest.setAttribute("dateI", dateI) ;
		    	if (typeHour!=null && typeHour.equals("1")) {
		    		
					period = " and ((m."+dateI+"= to_date('"+date+"','dd.mm.yyyy') and m."+timeI+">= cast('08:00' as time) )"
							+" or (m."+dateI+"= to_date('"+date1+"','dd.mm.yyyy') and m."+timeI+"< cast('08:00' as time) )"
					+")" ;
		    		aRequest.setAttribute("period",period) ;
		    		aRequest.setAttribute("hourInfo"," (8 часов)") ;
		    	} else if (typeHour!=null && typeHour.equals("2")) {
					period = " and ((m."+dateI+"= to_date('"+date+"','dd.mm.yyyy') and m."+timeI+">= cast('09:00' as time) )"
							+" or (m."+dateI+"= to_date('"+date1+"','dd.mm.yyyy') and m."+timeI+"< cast('09:00' as time) )"
					+")" ;
		    		aRequest.setAttribute("period",period) ;
		    		aRequest.setAttribute("hourInfo"," (9 часов)") ;
		    	} else {
		    		period = " and m."+dateI+"= to_date('"+date+"','dd.mm.yyyy')" ;
		    		aRequest.setAttribute("period",period) ;
		    		aRequest.setAttribute("hourInfo","") ;
		    	}
				aRequest.setAttribute("period1",period) ;
		    }
		    
		}

		return aMapping.findForward("success") ;

    }
}