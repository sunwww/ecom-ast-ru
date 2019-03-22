package ru.ecom.expomc.web.actions.checkproperty;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.expomc.ejb.services.check.ICheckService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Список установленных свойст
 */
public class CheckPropertyListAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	list(aRequest) ;
        return aMapping.findForward(SUCCESS) ;
    }
    public static void list(HttpServletRequest aRequest) throws Exception {
        ICheckService service = Injection.find(aRequest).getService(ICheckService.class) ;
        aRequest.setAttribute("list", service.listProperties(getLongId(aRequest, "Идентификатор проверки")));
    }
}
