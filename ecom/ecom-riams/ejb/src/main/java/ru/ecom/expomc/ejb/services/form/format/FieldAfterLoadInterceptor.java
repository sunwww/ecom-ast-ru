package ru.ecom.expomc.ejb.services.form.format;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.expomc.ejb.domain.format.Field;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 01.09.2006
 * Time: 4:22:50
 * To change this template use File | Settings | File Templates.
 */
public class FieldAfterLoadInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
        FieldForm form = (FieldForm) aForm ;
        Field field = (Field) aEntity ;
        form.setDbfInfo(field.getDbfInfo());
    }
}
