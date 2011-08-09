package ru.ecom.jaas.web.action.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.ejb.service.ISecService;
import ru.ecom.jaas.web.action.JaasUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ExportRoleAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecService service = (ISecService) Injection.find(aRequest).getService("SecService") ;
        String filename=serviceDo(service, JaasUtil.convertToLongs(aRequest.getParameterValues("id")));
        return new ActionForward("../rtf/"+filename,true) ;
    }

    String serviceDo(ISecService service, long[] aRoles) throws ParserConfigurationException, TransformerException {
        return service.exportRoles(aRoles);
    }
}
