package ru.ecom.mis.web.action.medcase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.web.action.JaasUtil;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class PoliciesAddAction extends BaseAction {

	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		IHospitalMedCaseService service = (IHospitalMedCaseService) Injection.find(aRequest).getService("HospitalMedCaseService");
		long sslId = Long.parseLong(aRequest.getParameter("sslId")) ;
		serviceDo(service, sslId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")));
		return new ActionForward(aMapping.findForward("success").getPath()+"?id="+sslId, true);
	}
	void serviceDo(IHospitalMedCaseService aService, long aSslId, long [] aPolicies) {
		aService.addPolicies(aSslId, aPolicies);
	}

}
