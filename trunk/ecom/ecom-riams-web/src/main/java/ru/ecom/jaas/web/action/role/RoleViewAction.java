package ru.ecom.jaas.web.action.role;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.web.actions.entity.EditAction;
import ru.ecom.web.util.Injection;
import ru.ecom.jaas.ejb.form.SecRoleForm;
import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.jaas.ejb.service.CheckNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class RoleViewAction extends EditAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        super.myExecute(aMapping, aForm, aRequest, aResponse) ;
        SecRoleForm form = (SecRoleForm) aForm ;
        form.setTypeViewOnly();
        /*

        ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService") ;
        CheckNode root = service.loadPoliciesByRole(getLongId(aRequest, "Идентификатор роли")) ;
        CheckNodesUtil.removeUnchecked(root);
        aRequest.setAttribute("policies", root);
        */

        return aMapping.findForward("success") ;
    }
}
