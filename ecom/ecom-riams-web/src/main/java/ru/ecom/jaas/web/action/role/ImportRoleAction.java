package ru.ecom.jaas.web.action.role;

import java.io.InputStream;
import java.util.ArrayList;
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

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.jaas.ejb.service.ISecService;
import ru.ecom.jaas.ejb.service.ImportRoles;
import ru.ecom.jaas.ejb.service.PolicyForm;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ImportRoleAction extends BaseAction{
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	
    	final boolean clear  ;
        final ISecService service = (ISecService) Injection.find(aRequest).getService("SecService") ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final List<ImportRoles> listRoles = new LinkedList<ImportRoles>() ;
        ArrayList<Long> ids = RolePoliciesSaveAction.getLongsArray(aRequest.getParameterValues("id")) ;
        System.out.println("Impotr roles...");
        if (aForm!=null) {
        	ExportPolicyForm form = (ExportPolicyForm)aForm ;
        	clear = form.getClear()!=null &&form.getClear()==true?true:false ;
        	InputStream in = null;
    		System.out.println("file="+form.getFile().getContentType()+" "+form.getFile().getInputStream()) ;
            try {
            	//in =file.getInputStream()  ;
            	in =form.getFile().getInputStream() ;
            	 
            	//System.out.println(new StringBuilder().append("		file=").append(in).toString());
               	Document doc = new SAXBuilder().build(in);
                Element parConfigElement = doc.getRootElement();
                System.out.println(new StringBuilder().append("		root=").append(parConfigElement).toString());
                Long i =Long.valueOf(1) ;
                for (Object o : parConfigElement.getChildren()) {
                    Element parElement = (Element) o;
                    if("role".equals(parElement.getName()) && ids.contains(i++)) {
                    	String key =parElement.getAttributeValue("key");
                    	String name = parElement.getAttributeValue("name");
                    	String comment = parElement.getAttributeValue("comment");
                    	
                    	final PolicyForm exp = new PolicyForm(key,name,comment) ;
                    	System.out.println("role [ key="+key+", name="+name+", comment= "+comment+"]") ;
                    	
                    	final List<PolicyForm> list = new LinkedList<PolicyForm> () ;
                    	for (Object parPol: parElement.getChildren()) {
                    		Element polElement = (Element) parPol ;
    	                    if("policy".equals(polElement.getName())) {
    	                    	String pol_key =polElement.getAttributeValue("key");
    	                    	String pol_name = polElement.getAttributeValue("name");
    	                    	String pol_comment = polElement.getAttributeValue("comment");
    	                    	PolicyForm policy = new PolicyForm(pol_key,pol_name,pol_comment) ;
    	                    	list.add(policy) ;
    	                    	System.out.println("\t policy [ key="+pol_key+", name="+pol_name+", comment= "+pol_comment+"]") ;
    	                    }
                    	}
                    	ImportRoles impRole = new ImportRoles() ;
                    	impRole.setRole(exp) ;
                    	impRole.setPolicies(list) ;
                    	listRoles.add(impRole) ;
                    } 
                }
            }catch(Exception e) {
            	System.out.println(e.getMessage());
            } 
            finally {
                in.close();
            }
            in.close() ;
    	} else {
    		clear = false ;
    	}
    	
    	final long monitorId = monitorService.createMonitor() ;
        new Thread() {
            public void run() {
            	service.importRoles(monitorId, clear, listRoles) ;
            }
        }.start() ;
        
        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    	
    }
}