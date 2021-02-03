package ru.ecom.web.login;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 */
public class LoginAction extends BaseAction {


    public ActionForward myExecute(ActionMapping aMapping
            , ActionForm aForm
            , HttpServletRequest aRequest
            , HttpServletResponse aResponse) throws Exception {

        HttpSession session = aRequest.getSession(true);
        if (session != null) {
            LoginInfo loginInfo = LoginInfo.find(session);
            if (loginInfo != null) {
                LoginForm form = (LoginForm) aForm;
                form.setUsername(loginInfo.getUsername());
            }
        }
        if (aRequest.getParameter("next") == null && aRequest.getAttribute("next") != null) {
            LoginForm form = (LoginForm) aForm;
            form.setNext((String) aRequest.getAttribute("next"));
        }

        return aMapping.findForward(SUCCESS);
    }

}
