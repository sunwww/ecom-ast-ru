package ru.ecom.web.actions.entity;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShortViewAction extends EditAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ActionForward fw = super.myExecute(aMapping,aForm, aRequest, aResponse) ;
        BaseValidatorForm form = (BaseValidatorForm) aForm ;
        form.setTypeViewOnly();
        return fw;
    }
}