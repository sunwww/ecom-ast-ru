package ru.ecom.mis.web.action.medcase.journal;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.web.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdmissionJournalSearchAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        AdmissionJournalForm form = (AdmissionJournalForm) aForm;
        if (form!=null){
        	form.validate(aMapping, aRequest) ;
        }
        
        String typeDate =ActionUtil.updateParameter("AdmissionJournalSearch","typeDate","1", aRequest) ;
    	String typeDate1 =ActionUtil.updateParameter("AdmissionJournalSearch","typeDate1","2", aRequest) ;
		String typeEmergency =ActionUtil.updateParameter("AdmissionJournalSearch","typeEmergency","3", aRequest) ;
		String typeView =ActionUtil.updateParameter("AdmissionJournalSearch","typeView","1", aRequest) ;
		String typeView1 =ActionUtil.updateParameter("AdmissionJournalSearch","typeView1","1", aRequest) ;
		//String emer= request.getParameter("emergancyIs") ;
		if (form!=null && form.getDateBegin()!=null && !form.getDateBegin().equals("")) {
			if ("1".equals(typeEmergency)) {
				aRequest.setAttribute("emerIs"," and m.emergency='1'") ;
				aRequest.setAttribute("emerInfo","экстренно") ;
			} else if ("2".equals(typeEmergency)) {
				aRequest.setAttribute("emerIs"," and (m.emergency is null or m.emergency = '0')") ;
				aRequest.setAttribute("emerInfo","планово") ;
			} else {
				aRequest.setAttribute("emerIs","") ;
				aRequest.setAttribute("emerInfo","все") ;
			}
			String dateI;
			if ("1".equals(typeDate)) {
				dateI = "dateStart"  ;
				aRequest.setAttribute("dateI", dateI) ;
	    		aRequest.setAttribute("dateInfo","поступившим") ;
	    	} else if ("2".equals(typeDate)) {
	    		dateI = "dateFinish"  ;
	    		aRequest.setAttribute("dateI", dateI) ;
	    		aRequest.setAttribute("dateInfo","выписанным") ;
	    	} else {
	    		aRequest.setAttribute("dateI", "dateStart") ;
	    		aRequest.setAttribute("period"," and m.dateFinish is null ") ;
	    		aRequest.setAttribute("dateInfo","состоящим") ;
	    	}
		}
        return aMapping.findForward(SUCCESS);
    }
}
