package ru.ecom.web.actions.entity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.StrutsFormUtil;

/**
 * Изменение формы
 */
public class EditAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IEntityFormService service = EntityInjection.find(aRequest).getEntityFormService();
        IEntityForm form = castEntityForm(aForm, aMapping) ;

        Long id = Long.parseLong(aRequest.getParameter("id")) ;
        IEntityForm loadedForm = 
        	isMap(form) 
        	? service.load(getFormClassname(aMapping), id) 
        	: service.load(form.getClass(), id) ;

        BeanUtils.copyProperties(aForm, loadedForm);

        return aMapping.findForward("success") ;
    }

    private StrutsFormUtil theStrutsFormUtil = new StrutsFormUtil();
}
