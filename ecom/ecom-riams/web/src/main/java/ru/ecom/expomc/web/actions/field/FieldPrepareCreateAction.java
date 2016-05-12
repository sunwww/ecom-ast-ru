package ru.ecom.expomc.web.actions.field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.form.format.FieldForm;
import ru.ecom.expomc.ejb.services.form.format.IFormatService;
import ru.ecom.web.actions.parententity.PrepareCreateAction;
import ru.ecom.web.util.Injection;

/**
 * Подготовка к созданию
 */
public class FieldPrepareCreateAction extends PrepareCreateAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        
        ActionForward fw = super.myExecute(aMapping, aForm, aRequest, aResponse);
        IFormatService service = Injection.find(aRequest).getService(IFormatService.class);

        FieldForm form = (FieldForm) aForm ;
        form.setDocument(service.getDocumentForFormat(form.getFormat()));
        
        return fw ;
    }

}
