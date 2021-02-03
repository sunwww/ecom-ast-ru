package ru.ecom.poly.web.action.ticket;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TicketStatByUserAction extends BaseAction {

    @Override
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        String dateChange = ActionUtil.updateParameter("TicketStatByUser", "dateChange", "1", aRequest);
        ActionUtil.updateParameter("TicketStatByUser", "period", "2", aRequest);
        if (dateChange.equals("2")) {
            aRequest.setAttribute("dateSearch", "\"date\"");
            aRequest.setAttribute("dateSearch1", "date");
        } else {
            aRequest.setAttribute("dateSearch", "dateCreate");
            aRequest.setAttribute("dateSearch1", "dateCreate");
        }
        if (RolesHelper.checkRoles("/Policy/Poly/Ticket/ShowInfoAllUsers", aRequest)) {
            aRequest.setAttribute("add", "");
        } else {
            if (aRequest.getSession(true) != null) {
                aRequest.setAttribute("add", "and  usernameCreate='" + LoginInfo.find(aRequest.getSession(true)).getUsername() + "'");
            } else {
                aRequest.setAttribute("add", "and  usernameCreate is null");
            }
        }
        return aMapping.findForward(SUCCESS);
    }

}
