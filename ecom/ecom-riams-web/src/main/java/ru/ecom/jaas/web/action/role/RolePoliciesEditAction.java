package ru.ecom.jaas.web.action.role;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.web.util.Injection;
import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.jaas.ejb.service.CheckNode;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Просмотр политик у роли
 */
public class RolePoliciesEditAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService") ;
        CheckNode root = service.loadPoliciesByRole(getLongId(aRequest, "Идентификатор роли")) ;
//        CheckNodesUtil.removeUnchecked(root);
        aRequest.setAttribute("policies", root);

        return aMapping.findForward("success") ;
    }


    public static void printNode(JspWriter out, CheckNode aNode) throws IOException {
        if(aNode==null) throw new IllegalArgumentException("aNode==null") ;
        for (CheckNode node : aNode.getChilds()) {
            outNode(out, "root", node) ;
            printNodes(out, node);
        }
    }

    private static void printNodes(JspWriter out, CheckNode aNode) throws IOException {
        for (CheckNode node : aNode.getChilds()) {
            outNode(out, aNode,node);
            printNodes(out, node);
        }
    }

    private static void outNode(JspWriter out, CheckNode aParent, CheckNode aNode) throws IOException {
        outNode(out, "checkNode_"+aParent.getId(), aNode) ;
    }

    private static void outNode(JspWriter out, String aNodeVariableName, CheckNode aNode) throws IOException {
//        System.out.println("aNode.getChilds().size() = " + aNode.getChilds().size());
        boolean visible = (aNode.getChilds()!=null && aNode.getChilds().size()==1) || aNode.getChecked() ;
        out.println("var checkNode_"+aNode.getId()+" =  createNode("+aNodeVariableName+", '"+aNode.getName()+"', "+aNode.getChecked()
                +", "+visible
                + ", "+aNode.getId()
                +") ;") ;
    }


}
