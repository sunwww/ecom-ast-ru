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
        PrescriptionForm form = (PrescriptionForm) aForm;
        if (pl==null || pl.getMedCase() == null ) return;
        form.setServiceStream(pl.getServiceStream().getId());
        form.setMedcaseType(pl.getMedCase() instanceof HospitalMedCase ? "HOSPITAL" : "POLYCLINIC");
    }
}