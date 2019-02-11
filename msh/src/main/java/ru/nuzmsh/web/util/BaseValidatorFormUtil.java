package ru.nuzmsh.web.util;

import javax.servlet.jsp.PageContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.taglib.html.Constants;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

public class BaseValidatorFormUtil {

	public static BaseValidatorForm findBaseValidatorForm(PageContext aContext) {
		ActionForm form = getForm(aContext) ;
		if(form instanceof BaseValidatorForm) {
			return (BaseValidatorForm) form ;
		} else {
			return null ;
		}
	}
    public static boolean isViewOnly(PageContext aContext) {
        ActionForm form = getForm(aContext) ; 
        if(form instanceof BaseValidatorForm) {
            BaseValidatorForm bvForm = (BaseValidatorForm) form ;
            return bvForm.isViewOnly() ;
        }
        return false ;
    }

    public static ActionForm getForm(PageContext aContext) {
        ActionForm form = (ActionForm) aContext.getAttribute(Constants.BEAN_KEY, PageContext.REQUEST_SCOPE);
        return form ;
    }
}
