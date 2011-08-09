package ru.ecom.web.actions.parententity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.ParentUtil;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.ForwardUtil;

/**
 * Список всех значений
 */
public class ListRedirectAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();

        IEntityForm form = castEntityForm(aForm, aMapping) ;

        Long id = Long.parseLong(aRequest.getParameter("id")) ;
        IEntityForm loadedForm = service.load(form.getClass(), id) ;

        Long  parentId = (Long) ParentUtil.getParentIdValue(loadedForm) ;

//        String path = "/entityParentList-"+aRequest.getParameter("parent")+".do?id="+parentId ;
//        return new ActionForward(path, true) ;
        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), parentId);
    }
}
