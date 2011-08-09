package ru.ecom.web.actions.parententity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.ejb.services.entityform.ParentUtil;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.StrutsFormUtil;

/**
 * Удаление формы
 */
public class DeleteAction extends ru.ecom.web.actions.entity.DeleteAction {


    protected Long save(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IEntityFormService service = EntityInjection.find(aRequest).getEntityFormService();
        IEntityForm form = theStrutsFormUtil.getEntityForm(aForm,aMapping)  ;
        
        form = castEntityForm((ActionForm)form, aMapping) ;

        Long id = Long.parseLong(aRequest.getParameter("id")) ;

        IEntityForm loadedForm 
        	= isMap(form) 
        	? service.load(getFormClassname(aMapping), id) 
        	: service.load(form.getClass(), id) ;
        	
        BeanUtils.copyProperties(aForm, loadedForm);
        
        
        Long  parentId ;
        if(isMap(form)) {
        	IEntityForm mapForm = (IEntityForm) Thread.currentThread().getContextClassLoader().loadClass(
        		getFormClassname(aMapping)).newInstance();
        	BeanUtils.copyProperties(mapForm, loadedForm) ;
        	parentId = (Long) ParentUtil.getParentIdValue(mapForm) ;
        } else {
           parentId = (Long) ParentUtil.getParentIdValue(loadedForm) ;
        }
        	 

        super.myExecute(aMapping, aForm,aRequest, aResponse) ;

        return parentId;
    }

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        Long parentId = save(aMapping, aForm, aRequest, aResponse);

        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), parentId);
    }

    StrutsFormUtil theStrutsFormUtil = new StrutsFormUtil();
}
