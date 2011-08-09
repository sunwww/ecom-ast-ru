package ru.ecom.expomc.web.actions.checkproperty;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.form.check.ChecksTableForm;
import ru.ecom.expomc.ejb.services.form.check.ChecksTableFormRow;
import ru.ecom.expomc.ejb.services.form.check.ChecksTableFormRowOn;
import ru.ecom.expomc.ejb.services.form.check.IFormCheckService;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Таблица проверок
 */
public class TableCheckPropertiesSaveAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IFormCheckService service = Injection.find(aRequest).getService(IFormCheckService.class) ;

        long formatId = Long.parseLong(aRequest.getParameter("format")) ;

        ChecksTableForm form = service.loadFormChecksTableForm(formatId);

        storeValues(aRequest, form);

        LinkedList<Long> deleted = service.save(form, formatId);
        service.deleteChecks(deleted);
        
        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), formatId) ;
    }


    private void storeValues(HttpServletRequest r, ChecksTableForm aForm) {
        for (ChecksTableFormRow row : aForm.getChecksTableFormRows()) {
            for (ChecksTableFormRowOn on : row.getOns()) {
                String key = row.getProperty()+"_"+on.getKey() ;
                String value = r.getParameter(key) ;
                on.setValue("on".equals(value))  ;
            }

        }
    }
}
