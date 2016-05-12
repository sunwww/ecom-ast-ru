package ru.ecom.expomc.web.actions.importtime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.web.struts.BaseAction;

/**
 * Подгововка к импорту
 */
public class ImportTimeUploadEditAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ImportTimeUploadForm form = (ImportTimeUploadForm) aForm ;
        form.setDocument(getLongId(aRequest, "Нет идентификатора документа"));
        return aMapping.findForward("success");
    }
}
