package ru.ecom.web.actions.parententity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.StrutsConfigUtil;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 09.11.2006
 * Time: 23:16:17
 * To change this template use File | Settings | File Templates.
 */
public class DeleteGoParentViewAction extends DeleteAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        super.save(aMapping, aForm, aRequest, aResponse) ;
        return ForwardUtil.createGoParentForward((ActionForm)castEntityForm(aForm, aMapping), aMapping, aRequest, theStrutsFormUtil, theUtil);
    }

    private StrutsConfigUtil theUtil = new StrutsConfigUtil();
}
