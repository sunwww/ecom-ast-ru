package ru.ecom.jaas.web.action.policy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.web.struts.BaseAction;

public class ImportPolicyEditAction  extends BaseAction{
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
      /*
    	ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService") ;
        CheckNode root = service.loadPolicies() ;
//        CheckNodesUtil.removeUnchecked(root);
        aRequest.setAttribute("policies", root);
*/
        return aMapping.findForward("success") ;
    }
}