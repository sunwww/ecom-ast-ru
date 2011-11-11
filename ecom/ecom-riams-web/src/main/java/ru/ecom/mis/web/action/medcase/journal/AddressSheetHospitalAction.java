package ru.ecom.mis.web.action.medcase.journal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class AddressSheetHospitalAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        AdmissionJournalForm form = (AdmissionJournalForm) aForm;
        form.validate(aMapping, aRequest) ;
        if (form.getDischargeIs()!=null && form.getDischargeIs()==true) {
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
        return aMapping.findForward("success");
    }
}