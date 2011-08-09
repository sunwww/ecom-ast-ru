package ru.ecom.web.actions.parententity;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.util.StringUtil;

/**
 * Список всех значений
 */
public class ListAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();
        IEntityForm form = castEntityForm(aForm, aMapping) ;
        String idString = aRequest.getParameter("id") ;
        if(StringUtil.isNullOrEmpty(idString)) {
            throw new IllegalArgumentException("Нет параметра id") ;
        }

        Long id = Long.parseLong(idString) ;

		boolean isMap = isMap(form);
		Collection list = isMap 
				? service.listAll(getFormClassname(aMapping), id) 
				: service.listAll(form.getClass(), id);

		aRequest.setAttribute("list"
				, isMap ? ru.ecom.web.actions.entity.ListAction.transormCollection(list, form.getClass()) : list);
        
        //aRequest.setAttribute("list", service.listAll(form.getClass(), id));

        return aMapping.findForward("success") ;
    }
}
