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
import ru.nuzmsh.web.util.StringSafeEncode;

/**
 * Редактирование формы
 */
public class FormCustomizeEditAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping
            , ActionForm aForm
            , HttpServletRequest aRequest
            , HttpServletResponse aResponse) throws Exception {

        FormCustomizeForm form = (FormCustomizeForm) aForm ;
        IFormCustomizeService service = FormCustomizeServiceHolder.getService() ;
        if(service==null) throw new IllegalStateException("Нет сервиса IFormCustomizeService") ;
        FormElementInfo info = service.findFormElementInfo(null, form.getFormName(), form.getElementName()) ;

        form.setOrigLabel(theStringSafeEncode.decode(form.getOrigLabel()));
        if(StringUtil.isNullOrEmpty(form.getNextUrl())) {
            form.setNextUrl(aRequest.getHeader("referer"));
        }

        if(info!=null) {
            form.setNewLabel(info.getLabel());
            form.setDefault(info.getDefaultValue());
            form.setVisible(convertBooleanToLogicString(info.isVisible()));
        }

        return aMapping.findForward(SUCCESS) ;
    }

    private String convertBooleanToLogicString(Boolean aBoolean) {
        String ret ;
        if(aBoolean==null) {
            ret = "" ;
        } else {
            ret = aBoolean ? "1" : "2" ;
        }
        return ret ;
    }

    private final StringSafeEncode theStringSafeEncode = new StringSafeEncode();

}