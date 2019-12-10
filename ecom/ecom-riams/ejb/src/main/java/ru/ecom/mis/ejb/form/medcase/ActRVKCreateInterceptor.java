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
                .createNativeQuery("select a.id from actrvk a " +
                        " left join medcase mc on mc.id=a.medcase_id and a.patient_id=mc.patient_id " +
                        " where (a.datefinish is null or a.datefinish>current_date) and medcase_id=:medCaseId")
                .setParameter("medCaseId", form.getMedCase()).getResultList().isEmpty())
            throw new IllegalStateException("У этого пациента уже есть открытый акт РВК!") ;
    }
}