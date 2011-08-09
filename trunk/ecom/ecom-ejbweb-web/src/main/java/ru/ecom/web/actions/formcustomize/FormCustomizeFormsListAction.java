package ru.ecom.web.actions.formcustomize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.struts.forms.customize.FormCustomizeServiceHolder;
import ru.nuzmsh.web.struts.forms.customize.IFormCustomizeService;

/**
 *
 */
public class FormCustomizeFormsListAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IFormCustomizeService service = FormCustomizeServiceHolder.getService() ;
        aRequest.setAttribute("list", service.listCustomizedForms()) ;
        return aMapping.findForward(SUCCESS) ;
    }

}
