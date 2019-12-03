package ru.ecom.mis.ejb.form.prescription.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.form.prescription.PrescriptionForm;

import javax.persistence.EntityManager;

public class PrescriptionPreCreateInterceptor implements IParentFormInterceptor {

    @Override
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        //Проставим поток обслуживания родительского случая
        EntityManager manager = aContext.getEntityManager();
        PrescriptList pl = manager.find(PrescriptList.class,aParentId);
        if (pl==null || pl.getMedCase() == null ) return;
        PrescriptionForm form = (PrescriptionForm) aForm;
        if (pl.getServiceStream()!=null)
            form.setServiceStream(pl.getServiceStream().getId());
        else
            throw new IllegalStateException("Необходимо <a href='entityEdit-stac_sslAdmission.do?id="
                    +pl.getMedCase().getId()+"' target='_blank'>проставить поток обслуживания!</a>");
        form.setMedcaseType(pl.getMedCase() instanceof HospitalMedCase ? "HOSPITAL" : "POLYCLINIC");
        form.setMedcaseId(pl.getMedCase().getId());
        form.setAllowOnlyPaid(pl.getServiceStream().getIsPaidConfirmation()!=null && pl.getServiceStream().getIsPaidConfirmation());
        form.setPatient(pl.getMedCase().getPatient().getId());
    }
}