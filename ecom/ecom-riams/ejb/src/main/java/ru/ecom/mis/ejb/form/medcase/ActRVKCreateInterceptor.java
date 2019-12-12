package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;

/**
 * Created by Milamesher on 06.12.2019.
 */
public class ActRVKCreateInterceptor  implements IFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        ActRVKVisitForm form=(ActRVKVisitForm)aForm ;
        if (!aContext.getEntityManager()
                .createNativeQuery("select a.id from actrvk a" +
                        " where a.medcase_id=:medCaseId or a.medcase_id=ANY(select id from medcase where" +
                        " parent_id=(select parent_id from medcase where id=:medCaseId))")
                .setParameter("medCaseId", form.getMedCase()).getResultList().isEmpty())
            throw new IllegalStateException("У этого пациента уже есть акт РВК в этом случае лечения!") ;
    }
}