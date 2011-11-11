package ru.ecom.diary.web.action.protocol.parameter;

import static ru.ecom.web.util.EntityInjection.find;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.diary.ejb.form.protocol.parameter.ParameterForm;
import ru.ecom.diary.ejb.service.protocol.IParameterService;
import ru.ecom.diary.ejb.service.protocol.ParameterPage;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.ejb.services.entityform.MapEntityForm;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.Injection;
import ru.ecom.web.util.StrutsConfigUtil;
import ru.ecom.web.util.StrutsFormUtil;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.web.messages.InfoMessage;

public class ParameterSaveAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

    	//System.out.println(1);
    	IEntityForm form = castEntityForm(aForm, aMapping) ;
    	IEntityFormService service = find(aRequest).getEntityFormService();
        BaseValidatorForm validatorForm = (BaseValidatorForm) aForm ;
        ActionErrors errors = validatorForm.validate(aMapping, aRequest) ;
        
        //System.out.println(2);
    	System.out.println(errors);
    	try {
    		IParameterService service1 = Injection.find(aRequest).getService(IParameterService.class) ;
    		ParameterForm parForm = (ParameterForm) aForm ;
			ParameterPage page = service1.loadParameter(parForm,parForm.getType(),errors) ;
			aRequest.setAttribute("paramform",page.getFormData());
			aRequest.setAttribute("paramscript",page.getJavaContext());
		}catch(Exception e){
			aRequest.setAttribute("paramform","");
			aRequest.setAttribute("paramscript","");
		}
    	//System.out.println(3);
		
        if (errors!=null && !errors.isEmpty()) {
        	return aMapping.findForward("success_file") ;
        }
        //System.out.println(4);
        IEntityForm formAdapted = form;
        if(isMap(form)) {
        	formAdapted = new MapEntityForm() ;
        	MapEntityForm mef = (MapEntityForm) formAdapted ;
        	mef.setStrutsFormName("$$map$$"+aMapping.getName()) ;
        	BeanUtils.copyProperties(formAdapted, form) ;
        }
        
        long id ;
        if(validatorForm.isTypeCreate()) {
                id = service.create(formAdapted);
                new InfoMessage(aRequest, "Создано новое") ;
        } else {
            service.save(formAdapted);
            id = (Long)PropertyUtil.getPropertyValue(form, "id") ;
            new InfoMessage(aRequest, "Сохранено") ;
        }
       // return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), id) ;
        return ForwardUtil.createGoParentForward((ActionForm)castEntityForm(aForm, aMapping), aMapping, aRequest, theStrutsFormUtil, theStrutsConfigUtil);
        
        //return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), 1) ;
    }
    StrutsConfigUtil theStrutsConfigUtil = new StrutsConfigUtil();
    StrutsFormUtil theStrutsFormUtil = new StrutsFormUtil();
}