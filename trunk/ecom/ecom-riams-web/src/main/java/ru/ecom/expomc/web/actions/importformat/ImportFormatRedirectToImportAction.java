/**
 * @author ikouzmin 09.03.2007 10:16:34
 */

package ru.ecom.expomc.web.actions.importformat;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.expomc.ejb.services.form.importformat.ImportFormatForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImportFormatRedirectToImportAction extends BaseAction  {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        long id = getLongId(aRequest, "Формат");
        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService() ;
        ImportFormatForm form = service.load(ImportFormatForm.class, id) ;

        ActionForward forward = new ActionForward(
                aMapping.findForward("success").getPath()+"?id="+form.getDocument()+"&format="+id, true
        );
        return forward;

    }
}
