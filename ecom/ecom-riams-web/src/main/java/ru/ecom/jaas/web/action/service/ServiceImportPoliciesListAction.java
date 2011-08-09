package ru.ecom.jaas.web.action.service;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.jaas.ejb.service.ISecPolicyImportService;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.Injection;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 16.10.2006
 * Time: 2:25:32
 * To change this template use File | Settings | File Templates.
 */
public class ServiceImportPoliciesListAction extends BaseAction {
	
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ServiceImportPolicesListForm form = (ServiceImportPolicesListForm) aForm ;
        final ISecPolicyImportService service = (ISecPolicyImportService) Injection.find(aRequest).getService("SecPolicyImportService") ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        
        final Collection<String> policies = new LinkedList<String>() ;
        LineNumberReader in = new LineNumberReader(new InputStreamReader(form.getFile().getInputStream())) ;
        String line ;
        while ( (line=in.readLine())!=null) {
        	policies.add(line) ;
        }
        
        final long monitorId = monitorService.createMonitor() ;
        new Thread() {
            public void run() {
            	service.importPolicies(monitorId, policies, null, null ) ;
            }
        }.start() ;
        
        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    }
}
