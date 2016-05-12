package ru.ecom.poly.web.action.ticket;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.poly.ejb.form.TicketForm;
import ru.ecom.web.util.Injection;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 29.01.2007
 * Time: 8:02:14
 * To change this template use File | Settings | File Templates.
 */
public class CloseTicketAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
         TicketForm form = (TicketForm) aForm;
         form.validate(aMapping, aRequest) ;
         ITicketService service = Injection.find(aRequest).getService(ITicketService.class);

         service.closeTicket(form.getId());
         return aMapping.findForward("success");
    }
}
