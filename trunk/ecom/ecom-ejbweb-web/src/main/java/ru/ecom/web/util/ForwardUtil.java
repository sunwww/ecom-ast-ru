package ru.ecom.web.util;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.ParentUtil;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.util.PropertyUtil;

/**
 * @author esinev
 * Date: 22.08.2006
 * Time: 12:08:17
 */
public class ForwardUtil {

    public static ActionForward createIdRedirectForward(ActionForward aFw, HttpServletRequest aRequest) {
        String id = aRequest.getParameter("id") ;
        return new ActionForward(aFw.getPath()+"?id="+id, true);
    }

    public static ActionForward createIdRedirectForward(ActionForward aFw, long aId) {
        return new ActionForward(aFw.getPath()+"?id="+aId, true);
    }

    public static ActionForward createGoParentForward(ActionForm aForm, ActionMapping aMapping, HttpServletRequest aRequest, StrutsFormUtil aStrutsFormUtil, StrutsConfigUtil aStrutsConfigUtil) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    	IEntityForm form = aStrutsFormUtil.getEntityForm(aForm, aMapping);
    	form = AbstractEntityAction.castEntityForm(aForm, aMapping);
    	
    	Parent parent = form.getClass().getAnnotation(Parent.class) ;
    	if(parent==null) {
    		throw new IllegalArgumentException("Нет аннотации Parent у класса "+form.getClass()) ;
    	}
    	Class parentForm = ParentUtil.getParentFormClass((Class<IEntityForm>) form.getClass()) ; //parent.parentForm() ;
    	if(parentForm==null || parentForm.equals(Void.class)) {
    		throw new IllegalArgumentException("Не заполнен атрибут parentForm в аннотации Parent у класса "+form.getClass()) ;
    	}
    	String parentFormName = aStrutsConfigUtil.findFormNameByClass(parentForm,aRequest) ;
    	parentFormName = parentFormName.substring(0, parentFormName.length() - "Form".length());
    	String action = parentForm.getAnnotation(Parent.class)!=null ? "entityParentView" : "entityView" ;
    	Object id = PropertyUtil.getPropertyValue(form, parent.property()) ;
    	
    	return new ActionForward(new StringBuilder().append(action).append('-').append(parentFormName).append(".do?id=").append(id).toString(), true) ;
    	
    }
    public static ActionForward createGoSubclassForward(ActionForm aForm, ActionMapping aMapping, HttpServletRequest aRequest, StrutsFormUtil aStrutsFormUtil, StrutsConfigUtil aStrutsConfigUtil) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        IEntityForm form = aStrutsFormUtil.getEntityForm(aForm, aMapping);
        form = AbstractEntityAction.castEntityForm(aForm, aMapping);
        
        Parent parent = form.getClass().getAnnotation(Parent.class) ;
        if(parent==null) {
            throw new IllegalArgumentException("Нет аннотации Parent у класса "+form.getClass()) ;
        }
        Class parentForm = ParentUtil.getParentFormClass((Class<IEntityForm>) form.getClass()) ; //parent.parentForm() ;
        if(parentForm==null || parentForm.equals(Void.class)) {
            throw new IllegalArgumentException("Не заполнен атрибут parentForm в аннотации Parent у класса "+form.getClass()) ;
        }
        String parentFormName = aStrutsConfigUtil.findFormNameByClass(parentForm,aRequest) ;
        parentFormName = parentFormName.substring(0, parentFormName.length() - "Form".length());
        String action = "entitySubclassView";
        Object id = PropertyUtil.getPropertyValue(form, parent.property()) ;

        return new ActionForward(new StringBuilder().append(action).append('-').append(parentFormName).append(".do?id=").append(id).toString(), true) ;

    }

}
