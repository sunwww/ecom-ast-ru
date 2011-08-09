package ru.ecom.expomc.ejb.services.form.check;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.expomc.ejb.domain.check.Check;
import ru.ecom.expomc.ejb.domain.check.CheckProperty;

/**
 */
public class AfterLoadInterceptor implements IFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
        Check check = (Check) aEntity ;
        CheckForm form = (CheckForm) aForm ;
        StringBuilder sb = new StringBuilder();
        for (CheckProperty property : check.getProperties()) {
            sb.append(property.getProperty()) ;
            sb.append("=") ;
            sb.append(property.getValue()) ;
            sb.append(", ") ;
        }
        form.setProperties(sb.toString());
    }
}
