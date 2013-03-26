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
import ru.nuzmsh.util.query.ReportParamUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class ReestrByHospitalMedCaseAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AdmissionJournalForm form = (AdmissionJournalForm)aForm;
		
    	String typeDate =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeDate","1", aRequest) ;
		String typeEmergency =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeEmergency","3", aRequest) ;
		String typeHour =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeHour","3", aRequest) ;
		String typeView =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeView","1", aRequest) ;
	  	String typeDepartment=ActionUtil.updateParameter("ReestrByHospitalMedCase","typeDepartment","1", aRequest) ;
	 	String typePat=ActionUtil.updateParameter("ReestrByHospitalMedCase","typePatient","4", aRequest) ;
		
	  	
		//String emer= request.getParameter("emergancyIs") ;
		if (form!=null && form.getDateBegin()!=null && !form.getDateBegin().equals("")) {
			if (typePat.equals("3")) {
				//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
				aRequest.setAttribute("addPat", " and (ok.id is not null and ok.voc_code!='643') ") ;
				aRequest.setAttribute("infoTypePat", " иностранцам ") ;
			} else if (typePat.equals("2")){
				//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
				aRequest.setAttribute("addPat", " and (adr.addressId is not null and adr.kladr not like '30%')   and (ok.id is null or ok.voc_code='643')") ;
				aRequest.setAttribute("infoTypePat", " проживающим в других регионах") ;
			} else if (typePat.equals("1")){
				//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
				aRequest.setAttribute("addPat", " and (adr.addressId is not null and adr.kladr like '30%')   and (ok.id is null or ok.voc_code='643')") ;
				aRequest.setAttribute("infoTypePat", " региональным пациентам") ;
			} else {
				aRequest.setAttribute("addPat", "") ;
				aRequest.setAttribute("infoTypePat", "") ;
			}
			if (typeDepartment!=null&&typeDepartment.equals("2")) {
				aRequest.setAttribute("departmentFldIdSql","coalesce(slo.department_id,m.department_id)") ;
				aRequest.setAttribute("departmentFldNameSql","coalesce(sloml.name,ml.name)") ;
				aRequest.setAttribute("departmentFldAddSql"," and (slo.id is null or slo.transferDate is null) ") ;
			} else {
				aRequest.setAttribute("departmentFldIdSql","m.department_id") ;
				aRequest.setAttribute("departmentFldNameSql","ml.name") ;
			}
			
			aRequest.setAttribute("emerIs", ReportParamUtil.getEmergencySql(typeEmergency, "m")) ;
			aRequest.setAttribute( "emerInfo",ReportParamUtil.getEmergencyInfo(typeEmergency)) ;
	    	
			String dateI = null ;
			String timeI = null ;
			
			String period ="";
			
			String date = form.getDateBegin() ;
			Date dat = DateFormat.parseDate(date) ;
		    Calendar cal = Calendar.getInstance() ;
		    cal.setTime(dat) ;
		    cal.add(Calendar.DAY_OF_MONTH, 1) ;
		    SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy") ;
		    String date1=format.format(cal.getTime()) ;
		    String timeSql = null, timeInfo ="";
		    if (typeHour!=null && typeHour.equals("1")) {
				timeSql= "08:00" ;timeInfo="(8 часов)" ;
	    	} else if (typeHour!=null && typeHour.equals("2")) {
	    		timeSql= "08:00" ;timeInfo="(9 часов)" ;
	    	} 
		    if (typeDate!=null && typeDate.equals("1")) {
	    		//aRequest.setAttribute("dateIs"," and m.dateStart between to_date('"+form.getDateBegin()+"','dd.mm.yyyy') and to_date('"+form.getDateBegin()+"','dd.mm.yyyy') ") ;
				dateI = "dateStart" ; timeI = "entranceTime" ;
	    		aRequest.setAttribute("dateInfo","поступившим") ;
	    	} else if (typeDate!=null && typeDate.equals("2")) {
	    		dateI = "dateFinish" ; timeI = "dischargeTime" ;
	    		aRequest.setAttribute("dateInfo","выписанным") ;
	    	} else {
	    		aRequest.setAttribute("dateI", "dateStart") ;
	    		StringBuilder periodF = new StringBuilder() ;
    			periodF.append(" and (m.dateFinish is null ");
	    		periodF.append(" or ") ;
	    		periodF.append(ReportParamUtil.getDateFrom(false, "m.dateFinish", "m.dischargeTime", timeSql!=null?date1:date, timeSql)) ;
	    		periodF.append(" and ") ;
	    		periodF.append(ReportParamUtil.getDateTo(false, "m.dateStart", "m.entranceTime", timeSql!=null?date:date1, timeSql)) ;
	    		periodF.append(") ") ;
	    	
	    		aRequest.setAttribute("period",periodF.toString()) ;
	    		aRequest.setAttribute("period1",null) ;
	    		aRequest.setAttribute("dateInfo","состоящим") ;
	    	}
		    if (dateI!=null) {
		    	aRequest.setAttribute("dateI", dateI) ;
		    	period = ReportParamUtil.getPeriodByDate(true,false, "m."+dateI, "m."+timeI, date, date1, timeSql) ;
		    	aRequest.setAttribute("period",period) ;
		    	aRequest.setAttribute("hourInfo",timeInfo) ;
				aRequest.setAttribute("period1",period) ;
		    }
		    
		}

		return aMapping.findForward("success") ;

    }

}