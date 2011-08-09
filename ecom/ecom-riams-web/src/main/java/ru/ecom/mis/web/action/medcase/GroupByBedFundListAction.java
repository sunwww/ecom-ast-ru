package ru.ecom.mis.web.action.medcase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class GroupByBedFundListAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String typePat =ActionUtil.updateParameter("GroupByBedFund","typePatient","3", aRequest) ;
		ActionUtil.updateParameter("GroupByBedFund","period","2", aRequest) ;
		String typeDate = ActionUtil.updateParameter("GroupByBedFund","typeDate","2", aRequest) ;
		
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

