package ru.ecom.jaas.web.action.secuser;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.jaas.ejb.service.ISecUserService;
import ru.ecom.jaas.web.action.JaasUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class UserRoleAddAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecUserService service = (ISecUserService) Injection.find(aRequest).getService("SecUserService") ;
        long userId = Long.parseLong(aRequest.getParameter("userId")) ;
        serviceDo(service, userId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")));
        return new ActionForward(aMapping.findForward("success").getPath()+"?id="+userId, true) ;
    }

    void serviceDo(ISecUserService service, long aUserId, long[] aRoles) {
        service.addRoles(aUserId, aRoles);
    }
}
