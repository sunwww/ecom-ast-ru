package ru.ecom.web.actions.entity;

import static ru.ecom.web.util.EntityInjection.find;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.ejb.services.entityform.MapEntityForm;
import ru.ecom.web.util.ForwardUtil;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.web.messages.InfoMessage;

/**
 * Сохранение формы
 */
public class SaveAction extends AbstractEntityAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        IEntityFormService service = find(aRequest).getEntityFormService();
        IEntityForm form = castEntityForm(aForm, aMapping) ;
        BaseValidatorForm validatorForm = (BaseValidatorForm) aForm ;

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


        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), id) ;
    }
}
