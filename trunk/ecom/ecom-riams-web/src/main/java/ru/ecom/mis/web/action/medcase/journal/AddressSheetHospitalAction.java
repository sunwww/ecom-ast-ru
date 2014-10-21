package ru.ecom.mis.web.action.medcase.journal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.web.util.ActionUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class AddressSheetHospitalAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        AdmissionJournalForm form = (AdmissionJournalForm) aForm;
        form.validate(aMapping, aRequest) ;
        String typeDate =ActionUtil.updateParameter("AddressSheetHospital","typeDate","1", aRequest) ;
        String typeStatus =ActionUtil.updateParameter("AddressSheetHospital","typeStatus","1", aRequest) ;
        String typeView =ActionUtil.updateParameter("AddressSheetHospital","typeView","1", aRequest) ;
        if (typeDate!=null && typeDate.equals("2")) {
        	aRequest.setAttribute("dateSearch","dateFinish") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате выписки") ;
        } else {
        	aRequest.setAttribute("dateSearch","dateStart") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате поступления") ;
        }
		
        try {
	        String dateBegin = DateFormat.formatToJDBC(form.getDateBegin()) ;
	        
	        aRequest.setAttribute("dateBegin", dateBegin) ;
	        if (form.getDateEnd()!=null && !form.getDateEnd().equals("")) {
	        	String dateEnd = DateFormat.formatToJDBC(form.getDateEnd()) ;
	        	aRequest.setAttribute("dateEnd", dateEnd) ;
	        } else {
	        	aRequest.setAttribute("dateEnd", "") ;
	        }
        } catch (Exception e) {
        	System.out.println(e.getMessage()) ;
        }
        if (form.getHospType()!=null
        		&&!form.getHospType().equals(Long.valueOf(0))) {
        	aRequest.setAttribute("hospType", " and m.hospType_id='"+form.getHospType()+"'") ;
        } else {
        	aRequest.setAttribute("hospType", "") ;
        }
        if (form.getPigeonHole()!=null
        		&&!form.getPigeonHole().equals(Long.valueOf(0))) {
        	aRequest.setAttribute("pigeonHole", " and dep.pigeonHole_id='"+form.getPigeonHole()+"'") ;
        } else {
        	aRequest.setAttribute("pigeonHole", "") ;
        }
        if (form.getRegistrationType()!=null
        		&&!form.getRegistrationType().equals(Long.valueOf(0))) {
        	aRequest.setAttribute("department", " and m.department_id='"+form.getRegistrationType()+"'") ;
        } else {
        	aRequest.setAttribute("department", "") ;
        }
        if (typeStatus!=null && typeStatus.equals("2")) {
        	aRequest.setAttribute("status", " and m.kinsman_id is not null") ;
        	aRequest.setAttribute("printStatus", " представители") ;
        } else if (typeStatus!=null && typeStatus.equals("3")) {
        	aRequest.setAttribute("status", " and m.kinsman_id is null") ;
        	aRequest.setAttribute("printStatus", " без представителей") ;
        } else {
        	aRequest.setAttribute("printStatus", "") ;
        	aRequest.setAttribute("status", "") ;
        }
        return aMapping.findForward("success");
    }
}