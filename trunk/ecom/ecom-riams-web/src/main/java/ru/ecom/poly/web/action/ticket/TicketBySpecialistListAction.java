package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.patient.HospitalLibrary;
import ru.ecom.mis.web.action.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class TicketBySpecialistListAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String typePat =ActionUtil.updateParameter("TicketBySpecialist","typePatient","3", aRequest) ;
		ActionUtil.updateParameter("TicketBySpecialist","period","2", aRequest) ;
		
		if (typePat.equals("2")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, true, "t.date", "p", "pvss", "pmp")) ;
			aRequest.setAttribute("infoTypePat", " (по иногородним)") ;
		} else if (typePat.equals("1")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, false, "t.date", "p", "pvss", "pmp")) ;
			aRequest.setAttribute("infoTypePat", " (по региональным)") ;
		} else {
			aRequest.setAttribute("add", "") ;
			aRequest.setAttribute("infoTypePat", " (по всем)") ;
		}

		/*if (typePat.equals("2")) {
			aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.person_id,t.date)>0") ;
			aRequest.setAttribute("add1", "and $$isForeignPatient^ZExpCheck(m1.person_id,t1.date)>0") ;
			aRequest.setAttribute("infoTypePat", "Поиск по иногородним") ;
		} else if (typePat.equals("1")){
			aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.person_id,t.date)=0") ;
			aRequest.setAttribute("add1", "and $$isForeignPatient^ZExpCheck(m1.person_id,t1.date)=0") ;
			aRequest.setAttribute("infoTypePat", "Поиск по региональным") ;
		} else {
			aRequest.setAttribute("add", "") ;
			aRequest.setAttribute("add1", "") ;
			aRequest.setAttribute("infoTypePat", "Поиск по всем") ;
		}*/
		
		return aMapping.findForward("success");
	}
	

}
