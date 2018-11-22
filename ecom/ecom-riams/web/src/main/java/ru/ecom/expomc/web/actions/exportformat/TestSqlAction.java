/**
 * Тестирование запроса SQL
 * @author ikouzmin 01.03.2007 17:30:47
 */

package ru.ecom.expomc.web.actions.exportformat;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.expomc.ejb.services.exportformat.IExportFormatService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TestSqlAction extends BaseAction {
    private final static Logger LOG = Logger.getLogger(TestSqlAction.class) ;
    private final static boolean CAN_TRACE = LOG.isDebugEnabled() ;


    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final HttpSession session = aRequest.getSession() ;
        final LoginInfo loginInfo = LoginInfo.find(session) ;
        if (!loginInfo.isUserInRole("/Policy/Exp/ExportFormat/Edit")) {
            throw new Exception("Access denied");
        }

        IExportFormatService service = Injection.find(aRequest).getService(IExportFormatService.class);
        service.setMaxRecords(500);
        String xresult = service.getResultSet(new Long(aRequest.getParameter("id")));
        aRequest.setAttribute("xresult",xresult);
        return aMapping.findForward("success");
    }
}

