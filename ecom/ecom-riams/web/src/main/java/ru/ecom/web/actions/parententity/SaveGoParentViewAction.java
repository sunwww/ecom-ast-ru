package ru.ecom.web.actions.parententity;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.StrutsConfigUtil;
import ru.ecom.web.util.StrutsFormUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Сохранение или создание с последующим переходом на родительский просмотр
 */
public class SaveGoParentViewAction extends ru.ecom.web.actions.entity.SaveAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        super.myExecute(aMapping, aForm, aRequest, aResponse);
        
        return ForwardUtil.createGoParentForward((ActionForm)castEntityForm(aForm, aMapping), aMapping, aRequest, theStrutsFormUtil, theStrutsConfigUtil) ;
    }

    final StrutsConfigUtil theStrutsConfigUtil = new StrutsConfigUtil();
    final StrutsFormUtil theStrutsFormUtil = new StrutsFormUtil();
}
