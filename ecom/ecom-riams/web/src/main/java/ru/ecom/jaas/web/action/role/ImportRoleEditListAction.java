package ru.ecom.jaas.web.action.role;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import ru.ecom.jaas.ejb.service.ISecService;
import ru.ecom.jaas.ejb.service.PolicyForm;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;
public class ImportRoleEditListAction extends BaseAction{
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	StringBuilder ret = new StringBuilder() ;
    	ISecService service = (ISecService) Injection.find(aRequest).getService("SecService");
    	List<PolicyForm> list = new LinkedList<PolicyForm> () ;
    	try {
	    	if (aForm!=null) {
	    		ExportPolicyForm form = (ExportPolicyForm)aForm ; 
	    		System.out.println("file="+form.getFile().getContentType()+" "+form.getFile().getInputStream()) ;

	    		InputStream in = null;
	            try {
	            	in =form.getFile().getInputStream() ;
	               	Document doc = new SAXBuilder().build(in);
	                Element parConfigElement = doc.getRootElement();
	                System.out.println(new StringBuilder().append("		root=").append(parConfigElement).toString());
	                Long i=Long.valueOf(0);
	                for (Object o : parConfigElement.getChildren()) {
	                	System.out.println("role") ;
	                    Element parElement = (Element) o;
	                    if("role".equals(parElement.getName())) {
	                    	
	                    	String key =parElement.getAttributeValue("key");
	                    	String name = parElement.getAttributeValue("name");
	                    	String comment = parElement.getAttributeValue("comment");
	                    	PolicyForm role = new PolicyForm(key,name,comment) ;
	                    	System.out.println("key="+key) ;
	                    	System.out.println("name="+name) ;
	                    	System.out.println("comment="+comment) ;
	                    	if (service.findRole(role)!=null) {
	                    		role.setIsExist(true) ;
	                    	} else {
	                    		role.setIsExist(false);
	                    	}
	                    	System.out.println("role="+key) ;
	                    	role.setIdInFile(++i) ;
	                    	list.add(role) ;
	                    } 
	                }
	            }catch(Exception e) {
	            	System.out.println(e);
	            } 
	            finally {
	                in.close();
	            }
	    	}
	    	
	    	
	    	
    	} catch(Exception e) {
    		System.out.println(e);
    	}
    	aRequest.setAttribute("roles",list);
    	return aMapping.findForward("success") ;
    }
}