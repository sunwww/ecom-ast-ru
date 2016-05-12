package ru.ecom.jaas.web.action.secuser;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.jaas.ejb.service.ISecUserService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class UserRoleEditAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecUserService service = (ISecUserService) Injection.find(aRequest).getService("SecUserService") ;
        long userId = getLongId(aRequest, "Идентификатор пользователя") ;
        boolean isAddSystem = RolesHelper.checkRoles("/Policy/Jaas/SecRole/EditSystem", aRequest);
        aRequest.setAttribute("roles", service.listUserRoles(userId,isAddSystem));
        aRequest.setAttribute("toAdd", service.listRolesToAdd(userId,isAddSystem));
        return aMapping.findForward("success") ;

    }
}
