package ru.ecom.mis.web.action.patient;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VocOrgSearchAction extends BaseAction{
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        VocOrgSearchForm form = (VocOrgSearchForm) aForm;
        form.validate(aMapping, aRequest) ;
        IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
        aRequest.setAttribute("list"
                , service.findOrg(form.getFondNumber().toUpperCase(), form.getOldFondNumber().toUpperCase(), form.getName().toUpperCase()));
        return aMapping.findForward(SUCCESS);
    }
}
