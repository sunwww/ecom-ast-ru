package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;

/**
 * Created by Milamesher on 06.03.2019.
 */
public class SurgicalOperationViewInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        //Запрет на ред-е в СЛО и СЛС, если случай закрыт. Админ может редактировать.
        SurgicalOperationForm form = (SurgicalOperationForm) aForm;
        MedCase parent = aContext.getEntityManager().find(MedCase.class, form.getMedCase());
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut") &&
                (parent instanceof DepartmentMedCase || parent instanceof HospitalMedCase)) {
            MedCase hmc = (parent instanceof DepartmentMedCase) ? parent.getParent() : parent;
            if (hmc.getDateFinish() != null) {
                form.getPage();
                form.setTypeViewOnly();
            }
        }
    }
}