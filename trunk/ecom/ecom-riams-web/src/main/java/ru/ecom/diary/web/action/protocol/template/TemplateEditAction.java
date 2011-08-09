package ru.ecom.diary.web.action.protocol.template;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.diary.ejb.service.protocol.IDiaryService;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNode;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNodeByGroup;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNodeByParameter;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;


public class TemplateEditAction extends BaseAction{
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
	    IDiaryService service = (IDiaryService) Injection.find(aRequest).getService("DiaryService") ;
        CheckNode root = service.loadParametersByMedService(getLongId(aRequest, "Идентификатор мед.услуги")) ;
//        CheckNodesUtil.removeUnchecked(root);
        aRequest.setAttribute("params", root);

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
//      System.out.println("aNode.getChilds().size() = " + aNode.getChilds().size());
      boolean visible = (aNode.getChilds()!=null && aNode.getChilds().size()==1) || aNode.getChecked() ;
      if (aNode instanceof CheckNodeByGroup) {
    	  
          out.println("var checkNode_"+aNode.getId()+" =  createNodeByGroup("+aNodeVariableName+", '"
        		  //+"<span class=\"group\">Group:</span>"
        		  +aNode.getName()+"', "+aNode.getChecked()
                  +", "+visible
                  + ", '"+aNode.getId()
                  +"') ;") ;
      } 
      
      if (aNode instanceof CheckNodeByParameter) {
    	  //CheckNodeByParameter param = (CheckNodeByParameter)aNode ;
          out.println("var checkNode_"+aNode.getId()+" =  createNode("+aNodeVariableName+", '"
        		  //+"<span class=\"param"+param.getTypeParameterId()+"\">Param:"
        		  +aNode.getName()+"', "+aNode.getChecked()
                  +", "+visible
                  + ", '"+aNode.getId()
                  +"') ;") ;
      }
      
  }

}
