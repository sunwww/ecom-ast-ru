package ru.ecom.expomc.web.actions.importdata;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.form.impdoc.IImportDataService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Объединение данных
 */
public class ImportDataAppendDataAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IImportDataService service = Injection.find(aRequest).getService(IImportDataService.class);

        //super.getStringArray(aRequest, aParamName, aParameterTitle)
        //aRequest.setAttribute("list", service.listAll(Long.parseLong(aRequest.getParameter("id"))));
        service.join(getLongArray(aRequest, "id", "Импортированные данные")) ;
        return aMapping.findForward("success") ;
    }
}
