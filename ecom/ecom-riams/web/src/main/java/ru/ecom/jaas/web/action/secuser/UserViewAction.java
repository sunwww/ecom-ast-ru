package ru.ecom.jaas.web.action.secuser;

import ru.ecom.web.actions.entity.EditAction;
import ru.ecom.web.util.Injection;
import ru.ecom.jaas.ejb.form.SecUserForm;
import ru.ecom.jaas.ejb.service.ISecUserService;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class UserViewAction extends EditAction {
    public ActionForward myExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        super.myExecute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
        ISecUserService service = (ISecUserService) Injection.find(httpServletRequest).getService("SecUserService") ;
        SecUserForm form = (SecUserForm) actionForm ;
        form.setTypeViewOnly();
        boolean isAddSystem = RolesHelper.checkRoles("/Policy/Jaas/SecRole/EditSystem", httpServletRequest);
        httpServletRequest.setAttribute("roles", service.listUserRoles(getLongId(httpServletRequest, "Идентификатор пользователя"),isAddSystem));
        return actionMapping.findForward("success") ;
    }
}
