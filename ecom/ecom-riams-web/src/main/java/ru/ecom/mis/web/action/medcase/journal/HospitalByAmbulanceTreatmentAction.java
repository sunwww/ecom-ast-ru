package ru.ecom.mis.web.action.medcase.journal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class HospitalByAmbulanceTreatmentAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String typePat =ActionUtil.updateParameter("HospitalByAmbulanceTreatment","typePatient","3", aRequest) ;
		ActionUtil.updateParameter("HospitalByAmbulanceTreatment","period","2", aRequest) ;
		String typeHospit = ActionUtil.updateParameter("HospitalByAmbulanceTreatment","typeHospit","3", aRequest) ;
		
		if (typePat.equals("2")) {
			aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("infoTypePat", "Поиск по иногородним") ;
		} else if (typePat.equals("1")){
			aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("infoTypePat", "Поиск по региональным") ;
		} else {
			aRequest.setAttribute("add", "") ;
			aRequest.setAttribute("infoTypePat", "Поиск по всем") ;
		}
		
		if (typeHospit.equals("1")) {
			aRequest.setAttribute("hospT","and m.deniedHospitalizating_id is not null") ;
			aRequest.setAttribute("hospInfo", "только случаи отказа от госпитализации") ;
		}  else if (typeHospit.equals("3")) {
			aRequest.setAttribute("hospT","") ;
			aRequest.setAttribute("hospInfo", "включая случаи отказа от госпитализации") ;
		} else {
			aRequest.setAttribute("hospT","and m.deniedHospitalizating_id is null") ;
			aRequest.setAttribute("hospInfo", "исключая случаи отказа от госпитализации") ;
		}
		
		return aMapping.findForward("success");
	}
	
	
}