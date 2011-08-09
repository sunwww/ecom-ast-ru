package ru.ecom.web.actions.entity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.StrutsConfigUtil;

/**
 * Просмотр по потомкам
 */
public class EntitySubclassViewAction extends AbstractEntityAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IEntityForm form = castEntityForm(aForm, aMapping);
        IEntityFormService service = EntityInjection.find(aRequest).getEntityFormService();
        Long id = Long.parseLong(aRequest.getParameter("id")) ;


        IEntityForm loadedForm = service.loadBySubclass(form.getClass(), id) ;
        String name = theStrutsConfigUtil.findFormNameByClass(loadedForm.getClass(), aRequest) ;

        StringBuilder sb = new StringBuilder("/entityView-");
        sb.append(name.substring(0, name.length()-"Form".length())) ;
        sb.append(".do?id=") ;
        sb.append(id) ;
        return new ActionForward(sb.toString(), true);
    }

    private final StrutsConfigUtil theStrutsConfigUtil = new StrutsConfigUtil() ;

}
