package ru.ecom.expomc.web.actions.checkproperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.form.check.ChecksTableForm;
import ru.ecom.expomc.ejb.services.form.check.IFormCheckService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Таблица проверок
 */
public class TableCheckPropertiesEditAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IFormCheckService service = Injection.find(aRequest).getService(IFormCheckService.class) ;

        long formatId = getLongId(aRequest, "Формат");
        ChecksTableForm form = service.loadFormChecksTableForm(formatId);

        aRequest.setAttribute("exp_checksTableForm", form);

        return aMapping.findForward("success") ;
    }
}
