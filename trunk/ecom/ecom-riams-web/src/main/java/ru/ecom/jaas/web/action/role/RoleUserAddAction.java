package ru.ecom.jaas.web.action.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.jaas.web.action.JaasUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class RoleUserAddAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService") ;
        long roleId = Long.parseLong(aRequest.getParameter("roleId")) ;
        serviceDo(service, roleId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")));
        return new ActionForward(aMapping.findForward("success").getPath()+"?id="+roleId, true) ;
    }

    void serviceDo(ISecRoleService service, long aRoleId, long[] aUsers) {
        service.addUsersToRole(aRoleId, aUsers);
    }
}
