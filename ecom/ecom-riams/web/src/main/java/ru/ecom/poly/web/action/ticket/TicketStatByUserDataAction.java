package ru.ecom.poly.web.action.ticket;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TicketStatByUserDataAction extends BaseAction {

    @Override
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
        String idString = aRequest.getParameter("id");


        int ind1 = idString.indexOf(":");
        int ind2 = idString.indexOf(":", ind1 + 1);
        String date = idString.substring(0, ind1);
        String username = idString.substring(ind1 + 1, ind2);
        String datest = idString.substring(ind2 + 1);

        aRequest.setAttribute("stat_user", username);
        if (datest.equals("dateCreate")) {
            aRequest.setAttribute("stat_date", "по дате создания " + date);
        } else {
            aRequest.setAttribute("stat_date", "по дате приема " + date);
        }
        aRequest.setAttribute("list", service.findStatTicketByDateAndUsername(datest, date, username));
        return aMapping.findForward(SUCCESS);
    }

}