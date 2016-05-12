package ru.ecom.expomc.web.actions.checkproperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.check.ICheckService;
import ru.ecom.expomc.ejb.services.form.check.CheckPropertyForm;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Список установленных свойст
 */
public class CheckPropertySaveAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ICheckService service = Injection.find(aRequest).getService(ICheckService.class) ;
        CheckPropertyForm form = (CheckPropertyForm) aForm ;
        service.saveForm(form);
        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"),form.getCheck()) ;
    }
}
