package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.oncological.ejb.form.OncologyCaseReestrForm;

/**
 * Created by Milamesher on 06.03.2019.
 */
public class OncologyCaseReestrSaveInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        //Запрет на ред-е в СЛО и СЛС, если случай закрыт. Админ может редактировать.
        OncologyCaseReestrForm form = (OncologyCaseReestrForm) aForm;
        MedCase parent = aContext.getEntityManager().find(MedCase.class, form.getMedCase());
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/Oncology/Case/EditOncoAfterOut") &&
                (parent instanceof HospitalMedCase)) {
            MedCase hmc = (parent instanceof DepartmentMedCase) ? parent.getParent() : parent;
            if (hmc.getDateFinish() != null)
                throw new IllegalStateException("Пациент выписан. Нельзя редактировать онкологический случай в закрытом СЛС!");
        }
    }
}