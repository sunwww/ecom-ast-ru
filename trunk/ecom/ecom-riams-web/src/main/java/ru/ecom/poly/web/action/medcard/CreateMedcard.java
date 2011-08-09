package ru.ecom.poly.web.action.medcard; 


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.ecom.poly.ejb.services.IMedcardService;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

public class  CreateMedcard extends AbstractEntityAction {
	  
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();
        IEntityForm form = castEntityForm(aForm, aMapping) ;
        Long parentId = Long.parseLong(aRequest.getParameter("id")) ;

        BaseValidatorForm validatorForm = (BaseValidatorForm) aForm ;

        IEntityForm loadedForm = service.prepareToCreate(form.getClass(), parentId) ;
        BeanUtils.copyProperties(aForm, loadedForm);

        validatorForm.setTypeCreate();
		
		MedcardForm mf = (MedcardForm)aForm;
		IMedcardService mdservice = Injection.find(aRequest).getService(IMedcardService.class);
		mf.setNumber(mdservice.getNewMedcardNumber());

        return aMapping.findForward("success") ;
    }
}
