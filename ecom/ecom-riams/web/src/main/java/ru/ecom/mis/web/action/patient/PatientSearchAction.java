package ru.ecom.mis.web.action.patient;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class PatientSearchAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        PatientSearchForm form = (PatientSearchForm) aForm;
        form.validate(aMapping, aRequest) ;
        IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
        aRequest.setAttribute("infoparam", 
        		"lpu="+form.getLpu()+
        		"&lpuArea="+form.getLpuArea()+
        		"&lastname="+form.getLastname()+
        		"&year="+form.getYear()
        		) ;
        if (!form.getLastname().equals("") && form.getLastname().length()>3) {
        	WebQueryResult wqr = service.findPatient(form.getLpu(), form.getLpuArea(), form.getLastname(),form.getYear()
            		, aRequest.getParameter("next") == null || aRequest.getParameter("next").equals("100")
            		,aRequest.getParameter("idna")) ;
        	aRequest.setAttribute("list_1" , wqr.get1());
            aRequest.setAttribute("list_2" , wqr.get2());
            aRequest.setAttribute("list_3" , wqr.get3());

        }
        return aMapping.findForward(SUCCESS);
    }
}
