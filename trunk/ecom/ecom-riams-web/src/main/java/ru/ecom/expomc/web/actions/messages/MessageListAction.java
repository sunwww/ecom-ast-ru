package ru.ecom.expomc.web.actions.messages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.messages.ICheckMessageService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 26.08.2006
 * Time: 0:33:26
 * To change this template use File | Settings | File Templates.
 */
public class MessageListAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ICheckMessageService service = Injection.find(aRequest).getService(ICheckMessageService.class) ;

        aRequest.setAttribute("list", service.listMessage(getLongId(aRequest, "Списка по времени")));
        
        return aMapping.findForward("success");
    }
}
