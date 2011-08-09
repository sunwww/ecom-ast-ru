package ru.ecom.web.actions.formcustomize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.struts.forms.customize.FormCustomizeServiceHolder;
import ru.nuzmsh.web.struts.forms.customize.FormElementInfo;
import ru.nuzmsh.web.struts.forms.customize.IFormCustomizeService;

/**
 * Редактирование формы
 */
public class FormCustomizeSaveAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping
            , ActionForm aForm
            , HttpServletRequest aRequest
            , HttpServletResponse aResponse) throws Exception {

        FormCustomizeForm form = (FormCustomizeForm) aForm ;
        FormElementInfo info = new FormElementInfo(form.getElementName());
        IFormCustomizeService service = FormCustomizeServiceHolder.getService() ;

        info.setLabel(form.getNewLabel());
        info.setDefaultValue(form.getDefault());
        info.setVisible(convertFromLogicBoolean(form.getVisible()));

        service.saveFormElementInfo(null, form.getFormName(), info) ;

        return new ActionForward(form.getNextUrl(), true) ;
    }

    private static Boolean convertFromLogicBoolean(String aStr) {
        Boolean ret ;
        if(StringUtil.isNullOrEmpty(aStr)) {
            ret = null ;
        } else {
            ret = aStr.trim().equals("1") ;
        }
        return ret ;
    }
}