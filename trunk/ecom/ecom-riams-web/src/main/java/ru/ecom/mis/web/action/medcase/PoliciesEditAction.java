package ru.ecom.mis.web.action.medcase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class PoliciesEditAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IHospitalMedCaseService service = (IHospitalMedCaseService) Injection.find(aRequest).getService("HospitalMedCaseService") ;
        long sslId = getLongId(aRequest, "Идентификатор случая") ;
        aRequest.setAttribute("policies", service.listPolicies(sslId));
        aRequest.setAttribute("toAdd",  service.listPoliciesToAdd(sslId));
        return aMapping.findForward("success") ;
	}

}
