package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.HitechMedicalCaseForm;

public class HitechMedicalCasePreCreateInterceptor  implements IParentFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		HitechMedicalCaseForm form = (HitechMedicalCaseForm ) aForm;
		EntityManager manager = aContext.getEntityManager();
		MedCase slo = manager.find(MedCase.class, aParentId);
		if (slo!=null) {
			form.setFinanceSource(slo.getServiceStream().getId());
		}
		
	}

}
