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
import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Подготовка к созданию
 */
public class PrepareCreateAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IEntityFormService service = EntityInjection.find(aRequest).getEntityFormService();
        IEntityForm form = castEntityForm(aForm, aMapping) ;
        BaseValidatorForm validatorForm = (BaseValidatorForm) aForm ;

        IEntityForm loadedForm = 
        		isMap(form) 
        		? service.prepareToCreate(getFormClassname(aMapping))
        		: service.prepareToCreate(form.getClass())		
        ;
        BeanUtils.copyProperties(aForm, loadedForm);

        validatorForm.setTypeCreate();

        return aMapping.findForward("success") ;
    }
}
