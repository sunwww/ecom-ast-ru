package ru.ecom.expomc.web.actions.messages;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.messages.ICheckMessageService;
import ru.ecom.expomc.ejb.uc.findpolicy.IFindPolicyService;
import ru.ecom.expomc.ejb.uc.findpolicy.PolicyRow;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Просмотр сообщения
 */
public class MessageViewAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ICheckMessageService service = Injection.find(aRequest).getService(ICheckMessageService.class) ;

//        aRequest.setAttribute("list", service.listMessage(getLongId(aRequest, "Списка по времени")));
        long id = getLongId(aRequest, "Идентификатор сообщения") ;
        Object entity = service.loadEntityByMessage(id) ;

        aRequest.setAttribute("entity", entity);
        aRequest.setAttribute("changes", service.listChanges(id));
        if(entity instanceof RegistryEntry) {
        	RegistryEntry e = (RegistryEntry) entity ; 
        	IFindPolicyService findService = Injection.find(aRequest).getService(IFindPolicyService.class) ; 
        	aRequest.setAttribute("policySuggest" 
        			, findService.findPolicy(e.getLastname(), e.getFirstname(), e.getMiddlename(), e.getBirthDate(), e.getPatientSnils(),id)) ;
        } else {
        	aRequest.setAttribute("policySuggest", new ArrayList<PolicyRow>()) ;
        }
        return aMapping.findForward("success");
    }
}
