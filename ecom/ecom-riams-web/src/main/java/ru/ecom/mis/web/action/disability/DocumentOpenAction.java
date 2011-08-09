package ru.ecom.mis.web.action.disability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class DocumentOpenAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
        aRequest.setAttribute("list", service.findOpenDocumentGroupByDate());
		return aMapping.findForward("success");
	}
}
