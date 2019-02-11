package ru.ecom.expomc.web.actions.messages;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.expomc.ejb.uc.findpolicy.IFindPolicyService;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Просмотр сообщения
 */
public class AcceptPolicyAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	IFindPolicyService findService = Injection.find(aRequest).getService(IFindPolicyService.class) ;
    	//findService.restore() ;
    	long nextId = findService.acceptPolicy(aRequest.getParameter("id")) ;
        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), nextId) ;
    }
}
