package ru.ecom.jaas.web.action.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.ejb.service.ISecService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ExportRoleEditAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	ISecService service = (ISecService) Injection.find(aRequest).getService("SecService") ;
    	aRequest.setAttribute("roles", service.listRoles());
        return aMapping.findForward("success") ;

    }
}