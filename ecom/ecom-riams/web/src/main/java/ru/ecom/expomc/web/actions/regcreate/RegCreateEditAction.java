package ru.ecom.expomc.web.actions.regcreate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.registry.CreateRegistryForm;
import ru.ecom.expomc.ejb.services.registry.IImportRegistryService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class RegCreateEditAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
        IImportRegistryService service = (IImportRegistryService) Injection.find(aRequest).getService("ImportRegistryService") ;
        CreateRegistryForm form = (CreateRegistryForm)aForm ;
        form.setDocumentId(service.getRegistryDocumentId()) ;
        service.getRegistryDocumentId() ;
        return aMapping.findForward("success") ;
	}

}
