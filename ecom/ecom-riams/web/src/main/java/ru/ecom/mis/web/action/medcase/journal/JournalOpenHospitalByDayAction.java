package ru.ecom.mis.web.action.medcase.journal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class JournalOpenHospitalByDayAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        //AdmissionJournalForm form = (AdmissionJournalForm) aForm;
        //form.validate(aMapping, aRequest) ;
        /*IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
//        IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
        aRequest.setAttribute("list"
                , service.findPatient(form.getLpu(), form.getLpuArea(), form.getLastname()));
        */
        /*if (form.getDischargeIs()!=null && form.getDischargeIs()==true) {
        	aRequest.setAttribute("dateSearch","dateFinish") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате выписки") ;
        } else {
        	aRequest.setAttribute("dateSearch","dateStart") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате поступления") ;
        }*/
    	//String idString = aRequest.getParameter("id") ;
    	//System.out.println("userprincepal="+aRequest.getUserPrincipal()) ;
        //IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	 //aRequest.setAttribute("list", service.findOpenHospitalByDate(idString));
        return aMapping.findForward("success");
    }
}