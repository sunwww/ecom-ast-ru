package ru.ecom.poly.web.action.ticket;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

import ru.nuzmsh.web.struts.BaseAction;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 29.01.2007
 * Time: 9:03:07
 * To change this template use File | Settings | File Templates.
 */
public class TicketSearchRedirectAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        HttpSession session = aRequest.getSession(true);

        Object o = session.getAttribute("ticketSearchResult");
        if (o == null)
            o = new LinkedList();

        aRequest.setAttribute("list", o);

        return aMapping.findForward("success");
    }
}
