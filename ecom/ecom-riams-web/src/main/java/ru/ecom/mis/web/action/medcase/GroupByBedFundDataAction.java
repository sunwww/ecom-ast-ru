package ru.ecom.mis.web.action.medcase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.patient.HospitalLibrary;
import ru.nuzmsh.web.struts.BaseAction;

public class GroupByBedFundDataAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String idString = aRequest.getParameter("id") ;
		int ind1 = idString.indexOf(":");
		int ind2 = idString.indexOf(":",ind1+1);
		int ind3 = idString.indexOf(":",ind2+1);
		int ind4 = idString.indexOf(":",ind3+1);
		int ind5 = idString.indexOf(":",ind4+1);
		
		//int ind3 = parent.indexOf(":",ind2) ;
		System.out.println("ind1="+ind1);
		System.out.println("ind2="+ind2);
		System.out.println("ind3="+ind3);
		//System.out.println("ind3="+ind3);
		System.out.println("parent lpuAndDate="+idString) ;
		String bedFund =idString.substring(0,ind1) ;
		System.out.println("bedFund="+bedFund) ;
		String dateBegin = idString.substring(ind1+1,ind2) ;
		System.out.println("dateBegin="+dateBegin) ;
		String dateEnd = idString.substring(ind2+1,ind3) ;
		System.out.println("dateEnd="+dateEnd) ;
		String dateType = idString.substring(ind3+1,ind4) ;
		System.out.println("dateType="+dateType) ;
		String typePat = idString.substring(ind4+1,ind5) ;
		System.out.println("patType="+typePat);
		String addStatus = idString.length()==ind5?"":idString.substring(ind5+1) ;
		if (addStatus.equals("")) {
			aRequest.setAttribute("addStatus", "and p.additionStatus_id is null") ;
		} else {
			aRequest.setAttribute("addStatus", "and p.additionStatus_id = "+addStatus) ;
		}
		
		
		if (typePat.equals("2")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, true, "m.Datestart", "p", "pvss", "pmp")) ;
			aRequest.setAttribute("infoTypePat", " (по иногородним)") ;
		} else if (typePat.equals("1")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, false, "m.Datestart", "p", "pvss", "pmp")) ;
			aRequest.setAttribute("infoTypePat", " (по региональным)") ;
		} else {
			aRequest.setAttribute("add", "") ;
			aRequest.setAttribute("infoTypePat", " (по всем)") ;
		}
		aRequest.setAttribute("dateType", dateType);
		aRequest.setAttribute("dateEnd", dateEnd);
		aRequest.setAttribute("dateBegin", dateBegin);
		aRequest.setAttribute("bedFund", bedFund);
		String dateInfo = "поступления" ;
		if (dateType.equals("m.dateFinish")) {
			dateInfo="выписки" ;
		}
		aRequest.setAttribute("info", "Поиск по дате "+dateInfo+" за период "+dateBegin+"-"+dateEnd);
		
		return aMapping.findForward("success");
	}
}