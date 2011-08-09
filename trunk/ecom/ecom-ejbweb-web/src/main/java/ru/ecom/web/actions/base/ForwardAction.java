package ru.ecom.web.actions.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.web.struts.BaseAction;

/**
 * @author esinev
 * Date: 10.08.2006
 * Time: 16:20:15
 */
public class ForwardAction extends BaseAction {
    public ActionForward myExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return actionMapping.findForward("success") ;
    }
}
