package ru.ecom.web.actions.parententity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Подготовка к созданию
 */
public class PrepareCreateAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();
        IEntityForm form = castEntityForm(aForm, aMapping) ;
        Long parentId = Long.parseLong(aRequest.getParameter("id")) ;

        BaseValidatorForm validatorForm = (BaseValidatorForm) aForm ;

        IEntityForm loadedForm = 
    		isMap(form) 
    		? service.prepareToCreate(getFormClassname(aMapping), parentId)
    		: service.prepareToCreate(form.getClass(), parentId) ;		
        
        BeanUtils.copyProperties(aForm, loadedForm);

        validatorForm.setTypeCreate();


        return aMapping.findForward("success") ;
    }
}
