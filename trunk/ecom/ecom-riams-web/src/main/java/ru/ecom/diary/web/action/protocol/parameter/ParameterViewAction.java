package ru.ecom.diary.web.action.protocol.parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.diary.ejb.form.protocol.parameter.ParameterForm;
import ru.ecom.diary.ejb.service.protocol.IParameterService;
import ru.ecom.diary.ejb.service.protocol.ParameterPage;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;

public class ParameterViewAction extends ParameterEditAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        /*BaseValidatorForm form = (BaseValidatorForm) aForm ;
        form.setTypeViewOnly();*/
		IParameterService service = Injection.find(aRequest).getService(IParameterService.class) ;
		 IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
	        IEntityForm form = castEntityForm(aForm, aMapping) ;
	        

	        Long id = Long.parseLong(aRequest.getParameter("id")) ;
	        IEntityForm loadedForm = 
	        	isMap(form) 
	        	? entityService.load(getFormClassname(aMapping), id) 
	        	: entityService.load(form.getClass(), id) ;

	        BeanUtils.copyProperties(aForm, loadedForm);
		ParameterForm parForm = (ParameterForm) aForm ;
		parForm.setTypeViewOnly();
		try {
			ParameterPage page = service.loadParameter(parForm,parForm.getType()) ;
			aRequest.setAttribute("paramform",page.getFormData());
			aRequest.setAttribute("paramscript",page.getJavaContext());
		}catch(Exception e){
			aRequest.setAttribute("paramform","");
			aRequest.setAttribute("paramscript","");
		}
		 return aMapping.findForward("success_file") ;
    }
}
