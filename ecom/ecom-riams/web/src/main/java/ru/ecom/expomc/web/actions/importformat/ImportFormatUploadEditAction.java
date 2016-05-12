/**
 * Подготовка к импорту XML документа
 *
 * @author ikouzmin 08.03.2007 20:44:26
 */
package ru.ecom.expomc.web.actions.importformat;

import ru.nuzmsh.web.struts.BaseAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImportFormatUploadEditAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ImportFormatUploadForm form = (ImportFormatUploadForm) aForm ;
        form.setDocument(getLongId(aRequest, "Нет идентификатора документа"));
        return aMapping.findForward("success");
    }
}
