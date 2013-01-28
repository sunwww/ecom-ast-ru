package ru.ecom.jaas.web.action.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class RoleUserEditAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService") ;
        long roleId = getLongId(aRequest, "Идентификатор пользователя") ;
        String roleInfo = service.getRoleInfo(roleId) ;
        boolean isAddSystem = RolesHelper.checkRoles("/Policy/Jaas/SecUser/EditSystem", aRequest);
        aRequest.setAttribute("roleInfo", roleInfo) ;
        aRequest.setAttribute("users", service.listUsersByRole(roleId,isAddSystem));
        aRequest.setAttribute("toAdd", service.listUsersToAdd(roleId,isAddSystem));
        return aMapping.findForward("success") ;

    }
}