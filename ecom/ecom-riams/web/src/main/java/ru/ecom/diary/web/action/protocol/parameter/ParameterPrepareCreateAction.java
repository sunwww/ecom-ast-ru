package ru.ecom.diary.web.action.protocol.parameter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.diary.ejb.form.protocol.parameter.ParameterForm;
import ru.ecom.diary.ejb.service.protocol.ParameterPage;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.mis.ejb.service.diary.IParameterService;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParameterPrepareCreateAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();
        IEntityForm form = castEntityForm(aForm, aMapping) ;
        Long parentId = Long.parseLong(aRequest.getParameter("id")) ;
        Long typeId = Long.parseLong(aRequest.getParameter("type")) ;
        BaseValidatorForm validatorForm = (BaseValidatorForm) aForm ;
        IParameterService servicepar = Injection.find(aRequest).getService(IParameterService.class) ;

        IEntityForm loadedForm = 
    		isMap(form) 
    		? service.prepareToCreate(getFormClassname(aMapping), parentId)
    		: service.prepareToCreate(form.getClass(), parentId) ;		
        
        BeanUtils.copyProperties(aForm, loadedForm);

        validatorForm.setTypeCreate();
        ParameterForm parForm = (ParameterForm) aForm ;
        parForm.setCode(servicepar.checkOrCreateCode(parForm.getCode(),""+ parForm.getId()));
        parForm.setTypeCreate();
        parForm.setType(typeId);
        BeanUtils.copyProperties(aForm, parForm);
		try {
			ParameterPage page = servicepar.loadParameter(parForm,parForm.getType()) ;
			aRequest.setAttribute("paramform",page.getFormData());
			aRequest.setAttribute("paramscript",page.getJavaContext());
		}catch(Exception e){
			aRequest.setAttribute("paramform","");
			aRequest.setAttribute("paramscript","");
		}

        return aMapping.findForward(SUCCESS) ;
    }
}
