package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;

/**
 * Created by Milamesher on 08.08.2019.
 */
public class EdkcProtocolPreCreateInterceptor implements IParentFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        if (aContext.getEntityManager().createNativeQuery("select id from observationsheet where id='"
                + aParentId + "' and finishDate is null ").getResultList().isEmpty())
            throw new IllegalStateException("Нельзя добавлять протоколы в закрытый лист наблюдения!");
    }
}
