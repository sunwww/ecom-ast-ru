package ru.ecom.json.action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveJsonAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
      /*  JSONObject object = aRequest.getAttribute("asd");
        ISecService service = (ISecService) Injection.find(aRequest).getService("SecService") ;
        aRequest.setAttribute("roles", service.listRoles());*/
        return aMapping.findForward("success") ;

    }
}
