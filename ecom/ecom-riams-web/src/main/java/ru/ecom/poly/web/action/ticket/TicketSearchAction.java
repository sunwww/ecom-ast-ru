package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 29.01.2007
 * Time: 9:02:24
 * To change this template use File | Settings | File Templates.
 */
public class TicketSearchAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        SearchForm form = (SearchForm) aForm;
        //form.validate(aMapping, aRequest);
        ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
        aRequest.setAttribute("list", service.findTicketsByNumber(form.getNumberTicket() ));

        return aMapping.findForward("success");
    }
}
