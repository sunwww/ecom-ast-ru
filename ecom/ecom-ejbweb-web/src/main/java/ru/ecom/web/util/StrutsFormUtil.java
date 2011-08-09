package ru.ecom.web.util;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;

/**
 *
 */
public class StrutsFormUtil {

    public IEntityForm getEntityForm(ActionForm aForm, ActionMapping aMapping) {
        if(aForm==null) throw new IllegalArgumentException("Нет формы в Action "+aMapping.getPath()) ;
        if(!(aForm instanceof IEntityForm)) throw new IllegalArgumentException("У формы "+aForm.getClass().getName()+" должен быть интерфейс IEntityForm") ;
        return (IEntityForm) aForm ;
    }
}
