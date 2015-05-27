package ru.ecom.diary.web.action.protocol.template;

import java.io.IOException;
import java.util.Collection;

import javax.naming.NamingException;
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
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;


public class TemplateEditAction extends BaseAction{
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
	    
    	IDiaryService service = (IDiaryService) Injection.find(aRequest).getService("DiaryService") ;
        IWebQueryService wservice = Injection.find(aRequest).getService(IWebQueryService.class) ;
        Long temp = getLongId(aRequest, "Идентификатор мед.услуги") ;
        Collection<WebQueryResult> l = wservice.executeNativeSql("select pf.parameter_id,p.name||' ('||case when p.type='1' then 'Числовой' when p.type='4' then 'Числовой с плавающей точкой зн.'||p.cntDecimal when p.type='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when p.type='3' then 'Текстовое поле' when p.type='5' then 'Текстовое поле с ограничением' else 'неизвестный' end||') - '||coalesce(vmu.name,'') from ParameterByForm pf left join Parameter p on p.id=pf.parameter_id left join userDomain vd on vd.id=p.valueDomain_id left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id where pf.template_id='"+temp+"' order by pf.position") ;  
        CheckNode root = service.loadParametersByMedService(temp) ;
//        CheckNodesUtil.removeUnchecked(root);
        aRequest.setAttribute("params", root);
        aRequest.setAttribute("params_table", l) ;
        return aMapping.findForward("success") ;
    }
    public static void getParams(HttpServletRequest aRequest) throws NamingException {
    	IDiaryService service = (IDiaryService) Injection.find(aRequest).getService("DiaryService") ;
        IWebQueryService wservice = Injection.find(aRequest).getService(IWebQueryService.class) ;
        Long temp = getLongId(aRequest, "Идентификатор мед.услуги") ;
        Collection<WebQueryResult> l = wservice.executeNativeSql("select pf.parameter_id,p.name||' ('||case when p.type='1' then 'Числовой' when p.type='4' then 'Числовой с плавающей точкой зн.'||p.cntDecimal when p.type='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when p.type='3' then 'Текстовое поле' when p.type='5' then 'Текстовое поле с ограничением' else 'неизвестный' end||') - '||coalesce(vmu.name,'') from ParameterByForm pf left join Parameter p on p.id=pf.parameter_id left join userDomain vd on vd.id=p.valueDomain_id left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id where pf.template_id='"+temp+"' order by pf.position") ;  
        CheckNode root = service.loadParametersByMedService(temp) ;
//        CheckNodesUtil.removeUnchecked(root);
        aRequest.setAttribute("params", root);
        aRequest.setAttribute("params_table", l) ;
    }

	
	public static void printNode(JspWriter out, CheckNode aNode) throws IOException {
        if(aNode!=null) {//throw new IllegalArgumentException("aNode==null") ;
        for (CheckNode node : aNode.getChilds()) {
            outNode(out, "root", node) ;
            printNodes(out, node);
        }
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
