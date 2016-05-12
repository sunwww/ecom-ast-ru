package ru.ecom.jaas.web.action.service;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.jaas.ejb.service.ISecPolicyImportService;
import ru.ecom.web.util.Injection;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 16.10.2006
 * Time: 2:52:11
 * To change this template use File | Settings | File Templates.
 */
public class ServiceImportRolesAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ServiceImportRolesForm form = (ServiceImportRolesForm) aForm ;
        ISecPolicyImportService service = (ISecPolicyImportService) Injection.find(aRequest).getService("SecPolicyImportService") ;
        aRequest.setAttribute("service", service);


        LinkedList<String> list = new LinkedList<String>();
        Properties prop = new Properties();
        prop.load(form.getFile().getInputStream());
        String pols = prop.getProperty(form.getUserInFile()) ;
        if(pols==null) {
            throw new IllegalStateException("Пользователя "+form.getUserInFile()+" нет") ;
        }
        StringTokenizer st = new StringTokenizer(pols, " \n,\t");
        while(st.hasMoreTokens()) {
            list.add(st.nextToken()) ;
        }

        aRequest.setAttribute("form2", form);
        aRequest.setAttribute("list", list);


         return aMapping.findForward("success") ;
    }
}
