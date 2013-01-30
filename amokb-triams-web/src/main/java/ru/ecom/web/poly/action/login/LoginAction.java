package ru.ecom.web.poly.action.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.web.login.LoginForm;
import ru.ecom.web.login.LoginInfo;
import ru.nuzmsh.web.struts.BaseAction;

public class LoginAction extends BaseAction {


	public ActionForward myExecute(ActionMapping aMapping
            , ActionForm aForm
            , HttpServletRequest aRequest
            , HttpServletResponse aResponse) throws Exception {

        HttpSession session = aRequest.getSession(true) ;
        if(session!=null) {
            LoginInfo loginInfo = LoginInfo.find(session) ;
            if(loginInfo!=null) {
                LoginForm form = (LoginForm) aForm ;
                form.setUsername(loginInfo.getUsername());
            }
        }
        
        return aMapping.findForward("success") ;
    }

}
