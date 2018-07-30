package ru.ecom.web.actions.base;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginAction extends Action {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
                                   HttpServletRequest aRequest, HttpServletResponse aResponse)
            throws Exception {
        return aMapping.findForward("success");
    }
}
