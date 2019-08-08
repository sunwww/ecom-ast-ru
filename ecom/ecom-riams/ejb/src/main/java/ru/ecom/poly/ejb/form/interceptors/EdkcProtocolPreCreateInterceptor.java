package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.patient.ObservationSheet;

/**
 * Created by Milamesher on 08.08.2019.
 */
public class EdkcProtocolPreCreateInterceptor implements IParentFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        ObservationSheet parent = aContext.getEntityManager().find(ObservationSheet.class, aParentId);
        Object val = aContext.getEntityManager().createNativeQuery("select case when finishDate is null then '0' else '1' end from observationsheet where id='" + aParentId + "'").getSingleResult();
        if (String.valueOf(val).equals("1"))
            throw new IllegalStateException("Нельзя добавлять протоколы в закрытый лист наблюдения!");
    }
}
