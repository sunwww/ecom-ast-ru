package ru.ecom.mis.web.action.medcase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class ResultPatientListAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String typeView =ActionUtil.updateParameter("ResultPatientList","typeView","2", aRequest) ;
		String typePat =ActionUtil.updateParameter("ResultPatientList","typePatient","3", aRequest) ;
		String typeDischargePatientIs =ActionUtil.updateParameter("ResultPatientList","typeDischargePatientIs","3", aRequest) ;
		String typePatIs =ActionUtil.updateParameter("ResultPatientList","typePatientIs","1", aRequest) ;
		String typeEmergency =ActionUtil.updateParameter("ResultPatientList","typeEmergency","3", aRequest) ;
		String typeOperation =ActionUtil.updateParameter("ResultPatientList","typeOperation","3", aRequest) ;
		//ActionUtil.updateParameter("DeathPatient","period","2", aRequest) ;
		String typeDate = ActionUtil.updateParameter("ResultPatientList","typeDate","2", aRequest) ;
		String typeDuration = ActionUtil.updateParameter("ResultPatientList","typeDuration","3", aRequest) ;
		//Проживание пациента
		if (typePat.equals("1")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("addPat", " and (ok.id is not null and ok.voc_code!='643') ") ;
			aRequest.setAttribute("addPat1", " and (ok1.id is not null and ok1.voc_code!='643') ") ;
			aRequest.setAttribute("infoTypePat", "Поиск иностранцев ") ;
		} else if (typePat.equals("2")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("addPat", " and (adr.addressId is not null and adr.kladr not like '30%')   and (ok.id is null or ok.voc_code='643')") ;
			aRequest.setAttribute("addPat1", " and (adr1.addressId is not null and adr1.kladr not like '30%')  and (ok1.id is null or ok1.voc_code='643')") ;
			aRequest.setAttribute("infoTypePat", "Поиск проживающих в других регионах") ;
		} else {
			aRequest.setAttribute("addPat", "") ;
			aRequest.setAttribute("addPat1", "") ;
			aRequest.setAttribute("infoTypePat", "Поиск по всем") ;
		}
		if (typeDuration.equals("1")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("addDuration", " and sum(	  case when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart) end)>3 ") ;
			aRequest.setAttribute("addDuration1", " and (	  case when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart) end)>3 ") ;
			aRequest.setAttribute("infoTypeDuration", ", госпитализированных более 3х к.дней ") ;
		} else if (typeDuration.equals("2")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("addDuration", " and sum(	  case when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart) end)<=3 ") ;
			aRequest.setAttribute("addDuration1", " and (	  case when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart) end)<=3 ") ;
			aRequest.setAttribute("infoTypeDuration", ", госпитализарованных до 3х к.дней включительно ") ;
		} else {
			aRequest.setAttribute("addDuration", "") ;
			aRequest.setAttribute("infoTypeDuration", "") ;
		}
		
		// Показания к поступления
		if (typeEmergency.equals("1")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("addEmergency", " and hmc.emergency='1' ") ;
			aRequest.setAttribute("infoTypeEmergency", ", поступивших экстренно") ;
		} else if (typeEmergency.equals("2")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("addEmergency", " and (hmc.emergency is null or hmc.emergency='0')") ;
			aRequest.setAttribute("infoTypeEmergency", ", поступивших планово") ;
		} else {
			aRequest.setAttribute("addEmergency", "") ;
			aRequest.setAttribute("infoTypeEmergency", "") ;
		}
		// Была ли операция
		if (typeOperation.equals("1")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("addOperation", " (count(soHosp.id)>0 or count (soDep.id)>0) ") ;
			aRequest.setAttribute("hav", "having") ;
			aRequest.setAttribute("infoTypeOperation", ", были операций") ;
		} else if (typeOperation.equals("2")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("addOperation", " (count(soHosp.id)=0 and count (soDep.id)=0) ") ;
			aRequest.setAttribute("hav", "having") ;
			aRequest.setAttribute("infoTypeOperation", ", без операций") ;
		} else {
			aRequest.setAttribute("addOperation", "") ;
			aRequest.setAttribute("infoTypeOperation", "") ;
		}
		
		if (typeDate.equals("2")) {
			aRequest.setAttribute("dateT","hmc.dateFinish") ;
			aRequest.setAttribute("dateT1","hmc1.dateFinish") ;
			aRequest.setAttribute("dateInfo", "Поиск по дате выписки") ;
		} else {
			aRequest.setAttribute("dateT","hmc.dateStart") ;
			aRequest.setAttribute("dateT1","hmc1.dateStart") ;
			aRequest.setAttribute("dateInfo", "поиск по дате поступления") ;
		}
		
		return aMapping.findForward("success");
	}
	
	

}