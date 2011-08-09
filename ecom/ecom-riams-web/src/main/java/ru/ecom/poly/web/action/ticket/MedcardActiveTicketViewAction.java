package ru.ecom.poly.web.action.ticket;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.ecom.web.util.Injection;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 28.01.2007
 * Time: 19:29:31
 * To change this template use File | Settings | File Templates.
 */
public class MedcardActiveTicketViewAction extends BaseAction {


    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;

        MedcardForm medcardForm = (MedcardForm) aForm;
        aRequest.setAttribute("list", service.findActiveMedcardTickets(medcardForm.getId()));

        return aMapping.findForward("success");
    }
}
