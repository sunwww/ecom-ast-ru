package ru.ecom.mis.web.action.patient;

import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.util.StringUtil;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.web.util.Injection;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

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
//        IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
        aRequest.setAttribute("list"
                , service.findPatient(form.getLpu(), form.getLpuArea(), form.getLastname()));
        return aMapping.findForward("success");
    }
}
