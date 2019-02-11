package ru.ecom.web.login;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 */
public class LoginExitAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        HttpSession session = aRequest.getSession(true) ;
        if(session!=null) {
            Enumeration en = session.getAttributeNames() ;
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                session.removeAttribute(key);
            }
        }
        return aMapping.findForward("success") ;
    }
}
