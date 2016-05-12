package ru.ecom.expomc.web.actions.registry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.registry.IImportRegistryService;
import ru.ecom.expomc.ejb.services.registry.PreImportResult;
import ru.ecom.web.util.FormFileUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Подготовка к импорту
 */
public class PreImportRegistrySaveAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IImportRegistryService service = (IImportRegistryService) Injection.find(aRequest).getService("ImportRegistryService") ;
        PreImportRegistryForm form = (PreImportRegistryForm) aForm ;

        PreImportResult result = service.preImportRegistry(form.getFormat()
                , FormFileUtil.writeFile(form.getFile()));
        aRequest.getSession(true).setAttribute("result",result);
        return aMapping.findForward("success") ;
    }
}
