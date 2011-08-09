package ru.ecom.jaas.web.action.policy;

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

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.jaas.ejb.service.ISecService;
import ru.ecom.jaas.ejb.service.PolicyForm;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ImportPolicyAction extends BaseAction {
	
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ExportPolicyForm form = (ExportPolicyForm) aForm ;
        final ISecService service = (ISecService) Injection.find(aRequest).getService("SecService") ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        
        final List<PolicyForm> policies = new LinkedList<PolicyForm>() ;
        InputStream in = null;
        try {
        	in = form.getFile().getInputStream() ;
            System.out.println(new StringBuilder().append("		file=").append(in).toString());
           	Document doc = new SAXBuilder().build(in);
            Element parConfigElement = doc.getRootElement();
            for (Object o : parConfigElement.getChildren()) {
                Element parElement = (Element) o;
                if("policy".equals(parElement.getName())) {
                	String key =parElement.getAttributeValue("key");
                	String name = parElement.getAttributeValue("name");
                	String comment = parElement.getAttributeValue("comment");
                	PolicyForm policy = new PolicyForm(key,name,comment) ;
                    policies.add(policy);
                } 
            }
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        } 
        finally {
            in.close();
        }
        
        final long monitorId = monitorService.createMonitor() ;
        new Thread() {
            public void run() {
            	service.importPolicies(monitorId, policies ) ;
            }
        }.start() ;
        
        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    }
}
