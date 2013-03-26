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
		String[] idS = idString.split(":" ) ;
		
		
		//int ind3 = parent.indexOf(":",ind2) ;
		System.out.println("idS="+idS.length);
		
		//System.out.println("ind3="+ind3);
		System.out.println("parent lpuAndDate="+idString) ;
		String bedFund =idS[0] ;
		System.out.println("bedFund="+bedFund) ;
		String dateBegin = idS[1] ;
		System.out.println("dateBegin="+dateBegin) ;
		String dateEnd = idS[2] ;
		System.out.println("dateEnd="+dateEnd) ;
		String dateType = idS[3] ;
		System.out.println("dateType="+dateType) ;
		String typePat = idS[4] ;
		System.out.println("patType="+typePat);
		String addStatus = idS[5] ;
		System.out.println("addStatus="+addStatus);
		if (addStatus.equals("")) {
			aRequest.setAttribute("addStatus", "and p.additionStatus_id is null") ;
		} else if (addStatus.equals("-")) {
			aRequest.setAttribute("addStatus", "") ;
		} else {
			aRequest.setAttribute("addStatus", "and p.additionStatus_id = "+addStatus) ;
		}
		String servStream=idS[6] ;
		if (!servStream.equals("")&&!servStream.equals("null") &&!servStream.equals("0")) {
			aRequest.setAttribute("servStream", " and m.serviceStream_id="+servStream) ;
		}
		String dep = idS[7] ;
		if (!dep.equals("")&&!dep.equals("null") &&!dep.equals("0")) {
			aRequest.setAttribute("dep", " and m.department_id="+dep) ;
		}		
		
		if (typePat.equals("2")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, true, "m.Datestart", "p", "pvss", "pmp","ok")) ;
			aRequest.setAttribute("infoTypePat", " (по иногородним)") ;
		} else if (typePat.equals("1")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, false, "m.Datestart", "p", "pvss", "pmp","ok")) ;
			aRequest.setAttribute("infoTypePat", " (по региональным)") ;
		} else if (typePat.equals("3")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("add", HospitalLibrary.getSqlGringo(true, "ok")) ;
			aRequest.setAttribute("infoTypePat", "Поиск по иностранцам") ;
		} else {
			aRequest.setAttribute("add", "") ;
			aRequest.setAttribute("infoTypePat", " (по всем)") ;
		}
		aRequest.setAttribute("dateType", dateType);
		aRequest.setAttribute("dateEnd", dateEnd);
		aRequest.setAttribute("dateBegin", dateBegin);
		if (bedFund.indexOf(",")==-1) {
			aRequest.setAttribute("bedFund", " and m.bedfund_id="+bedFund);
		} else {
			aRequest.setAttribute("bedFund", " and (m.bedfund_id="+bedFund.replaceAll(",", " or m.bedfund_id=")+")") ;
		}
		String dateInfo = "поступления" ;
		if (dateType.equals("m.dateFinish")) {
			dateInfo="выписки" ;
		}
		aRequest.setAttribute("info", "Поиск по дате "+dateInfo+" за период "+dateBegin+"-"+dateEnd);
		
		return aMapping.findForward("success");
	}
}