package ru.ecom.jaas.web.action.policy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.ejb.service.ISecService;
import ru.ecom.jaas.web.action.role.RolePoliciesSaveAction;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ExportPolicyAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	ExportPolicyForm form =(ExportPolicyForm)aForm ;
    	ISecService service = (ISecService)Injection.find(aRequest).getService("SecService");
    	long[] policies = RolePoliciesSaveAction.getLongs(form.getPolicies().split(","));
    	System.out.println("policies="+form.getPolicies()) ;
    	System.out.println() ;
        String filename = service.exportPolicy(policies);
        return new ActionForward("../rtf/"+filename,true) ;
    }
}
