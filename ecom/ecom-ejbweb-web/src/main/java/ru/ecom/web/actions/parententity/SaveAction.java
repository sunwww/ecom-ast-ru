package ru.ecom.web.actions.parententity;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.web.util.StrutsFormUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.util.PropertyUtil;

/**
 * Сохранение формы
 */
public class SaveAction extends ru.ecom.web.actions.entity.SaveAction {


    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        super.myExecute(aMapping, aForm, aRequest, aResponse);
        return createParentActionForward(aMapping, aMapping.findForward("success"), aForm);
    }


    protected static ActionForward createParentActionForward(ActionMapping aMapping, ActionForward aForward, ActionForm aForm) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StrutsFormUtil theStrutsFormUtil = new StrutsFormUtil();
        IEntityForm form = theStrutsFormUtil.getEntityForm(aForm, aMapping);
        Class formClass = aForm.getClass();


        Parent parent = (Parent) formClass.getAnnotation(Parent.class);


        if (parent == null) {
            StringBuilder sb = new StringBuilder();
            for (Annotation annotation : formClass.getAnnotations()) {
                sb.append(annotation);
                sb.append(", ");
            }
            throw new IllegalArgumentException("Нет аннотации Parent в форме " + formClass + ". Доступные аннотации: " + sb);
        } else {
            Object id = PropertyUtil.getPropertyValue(aForm, parent.property());
            String path = aForward.getPath();
//            if(path.indexOf("?id=")<0) {
            path = path + "?id=" + id;
//            }
            return new ActionForward(path, true);
        }

    }


}
