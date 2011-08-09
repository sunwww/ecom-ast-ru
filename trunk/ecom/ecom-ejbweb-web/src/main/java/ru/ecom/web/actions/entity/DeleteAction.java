package ru.ecom.web.actions.entity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.web.messages.InfoMessage;

/**
 * Удаление формы
 */
public class DeleteAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IEntityFormService service = EntityInjection.find(aRequest).getEntityFormService();
        IEntityForm form = castEntityForm(aForm, aMapping) ;

        Long id = Long.parseLong(aRequest.getParameter("id")) ;
        
        if(!isMap(form)) {
        	service.delete(form.getClass(), id) ;
        } else {
        	service.delete(getFormClassname(aMapping), id);
        }
        
        
        new InfoMessage(aRequest, "Удалено") ;

        return aMapping.findForward("success") ;
    }
}
