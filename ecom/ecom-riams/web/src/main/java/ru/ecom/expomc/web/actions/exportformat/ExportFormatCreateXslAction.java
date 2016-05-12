/**
 *
 * @author ikouzmin 12.03.2007 2:41:24
 */
package ru.ecom.expomc.web.actions.exportformat;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.expomc.ejb.services.exportformat.IExportFormatService;
import ru.ecom.web.util.Injection;
import ru.ecom.web.login.LoginInfo;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportFormatCreateXslAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final IExportFormatService service = Injection.find(aRequest).getService(IExportFormatService.class) ;
        final long id = getLongId(aRequest, "Идентификатор формата") ;
        service.createStandardXsl(new Long(id));
        return aMapping.findForward("success");
    }
}
