package ru.ecom.jaas.web.action.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class RoleUserEditAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService") ;
        long roleId = getLongId(aRequest, "Идентификатор пользователя") ;
        String roleInfo = service.getRoleInfo(roleId) ;
        aRequest.setAttribute("roleInfo", roleInfo) ;
        aRequest.setAttribute("users", service.listUsersByRole(roleId));
        aRequest.setAttribute("toAdd", service.listUsersToAdd(roleId));
        return aMapping.findForward("success") ;

    }
}