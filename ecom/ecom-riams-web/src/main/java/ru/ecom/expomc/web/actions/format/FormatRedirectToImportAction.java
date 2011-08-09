package ru.ecom.expomc.web.actions.format;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.expomc.ejb.services.form.format.FormatForm;
import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 */
public class FormatRedirectToImportAction extends BaseAction  {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        long id = getLongId(aRequest, "Формат");
        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService() ;
        FormatForm form = service.load(FormatForm.class, id) ;

        ActionForward forward = new ActionForward(
                aMapping.findForward("success").getPath()+"?id="+form.getDocument()+"&format="+id, true
        );
        return forward;

    }
}
