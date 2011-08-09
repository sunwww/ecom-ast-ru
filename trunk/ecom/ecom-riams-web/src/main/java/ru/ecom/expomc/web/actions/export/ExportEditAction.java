package ru.ecom.expomc.web.actions.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.exportservice.ExportForm;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Показывает формочку для экспорта
 */
public class ExportEditAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ExportForm form = (ExportForm) aForm ;
        long documentId = getLongId(aRequest, "Идентификатор документа") ;
        form.setDocument(documentId);
        return aMapping.findForward("success");
    }
}
