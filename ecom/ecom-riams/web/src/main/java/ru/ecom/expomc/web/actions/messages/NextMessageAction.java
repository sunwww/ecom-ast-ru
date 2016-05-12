package ru.ecom.expomc.web.actions.messages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.messages.ICheckMessageService;
import ru.ecom.expomc.ejb.uc.findpolicy.IFindPolicyService;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Просмотр сообщения
 */
public class NextMessageAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	IFindPolicyService findService = Injection.find(aRequest).getService(IFindPolicyService.class) ;
    	long checkId = findService.getNextMessageId(Long.parseLong(aRequest.getParameter("id"))) ;
        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), checkId) ;
    }
}
