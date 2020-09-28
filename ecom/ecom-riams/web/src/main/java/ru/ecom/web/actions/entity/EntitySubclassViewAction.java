package ru.ecom.web.actions.entity;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.StrutsConfigUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String shortP = aRequest.getParameter("short") ;
        StringBuilder sb = new StringBuilder("/entityView-");
        sb.append(name, 0, name.length()-"Form".length()) ;
        sb.append(".do?id=") ;
        sb.append(id) ;
        if (shortP!=null) {
        	sb.append("&short=Short") ;
        }
        return new ActionForward(sb.toString(), true);
    }

    private final StrutsConfigUtil theStrutsConfigUtil = new StrutsConfigUtil() ;

}
