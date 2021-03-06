package ru.ecom.mis.web.action.medcase;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.patient.HospitalLibrary;
import ru.ecom.web.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class GroupByBedFundListAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String typePat =ActionUtil.updateParameter("GroupByBedFund","typePatient","4", aRequest) ;
		ActionUtil.updateParameter("GroupByBedFund","period","2", aRequest) ;
		String typeDate = ActionUtil.updateParameter("GroupByBedFund","typeDate","2", aRequest) ;
		String typeSwod = ActionUtil.updateParameter("GroupByBedFund","typeSwod","1", aRequest) ;
		String typeStatus = ActionUtil.updateParameter("GroupByBedFund","typeStatus","2", aRequest) ;
		String typeView = ActionUtil.updateParameter("GroupByBedFund","typeView","2", aRequest) ;
		String typeMKB = ActionUtil.updateParameter("DiagnosisBySlo","typeMKB","4", aRequest) ;
		
		//SimpleDateFormat FORMAT_2 = new SimpleDateFormat("yyyy") ;
    	
		if (typePat.equals("2")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, true, "m.Datestart", "p", "pvss", "pmp","ok")) ;
			aRequest.setAttribute("infoTypePat", "Поиск по иногородним") ;
		} else if (typePat.equals("1")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, false, "m.Datestart", "p", "pvss", "pmp","ok")) ;
			aRequest.setAttribute("infoTypePat", "Поиск по региональным") ;
		} else if (typePat.equals("3")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlGringo(true, "ok")) ;
			aRequest.setAttribute("infoTypePat", "Поиск по иностранцам") ;
		} else {
			aRequest.setAttribute("add", "") ;
			aRequest.setAttribute("infoTypePat", "Поиск по всем") ;
		}
		
		if (typeDate.equals("2")) {
			aRequest.setAttribute("dateT","m.dateFinish") ;
			aRequest.setAttribute("dateInfo", "Поиск по дате выписки") ;
		} else if (typeDate.equals("3")) {
			aRequest.setAttribute("dateT","m.transferDate") ;
			aRequest.setAttribute("dateInfo", "Поиск по дате перевода") ;
		} else {
			aRequest.setAttribute("dateT","m.dateStart") ;
			aRequest.setAttribute("dateInfo", "поиск по дате поступления") ;
		}
		
		
		
		return aMapping.findForward("success");
	}
	
	

}

