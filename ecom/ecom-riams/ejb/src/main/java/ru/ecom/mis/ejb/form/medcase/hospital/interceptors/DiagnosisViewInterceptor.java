package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.DiagnosisForm;

/**
 * Created by Milamesher on 06.03.2019.
 */
public class DiagnosisViewInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        //Запрет на ред-е в СЛО и СЛС, если случай закрыт. Админ может редактировать.
        DiagnosisForm form = (DiagnosisForm) aForm;
        MedCase parent = aContext.getEntityManager().find(MedCase.class, form.getMedCase());
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut") &&
                parent instanceof HospitalMedCase) {
            MedCase mc = (parent instanceof DepartmentMedCase) ? parent.getParent() : parent;
            if (mc.getDateFinish() != null
                    //в будущем убрать
                    && !checkEditAllAfterOutForDead((HospitalMedCase) parent, aContext)) {
                form.getPage();
                form.setTypeViewOnly();
            }
        }
    }

    //если роль + результат госп. - смерть
    private boolean checkEditAllAfterOutForDead(HospitalMedCase hmc, InterceptorContext aContext) {
        return aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAllAfterOut")
                && hmc.getResult() != null && "11".equals(hmc.getResult().getCode());
    }
}