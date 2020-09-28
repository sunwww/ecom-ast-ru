package ru.ecom.mis.web.action.medcase;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

/**
 *
 */
public class PatientSearchRedirectAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        HttpSession session = aRequest.getSession(true) ;

        Object o = session.getAttribute("patientSearchResult") ;
        if(o==null) o= new LinkedList() ;
        aRequest.setAttribute("list",o);

//        session.removeAttribute("patientSearchResult");
        return aMapping.findForward(SUCCESS) ;
    }
}
