package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;

public class SurgicalOperationCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	HospitalMedCase parentSSL = manager.find(HospitalMedCase.class, aParentId) ;
    	SurgicalOperationForm form=(SurgicalOperationForm)aForm;
    	if (parentSSL!=null) {
    		if (parentSSL.getDateFinish()!=null) {
    			throw new IllegalStateException("Нельзя добавить хирургическую операцию в закрытый случай стационарного лечения (ССЛ) !!!") ;
    		}
    	} else {
    		throw new IllegalStateException("Невозможно добавить хирургическую операцию. Сначала надо определить  случай стационарного лечения (ССЛ)") ;
    	}
		if (parentSSL.getLpu()!=null) form.setLpu(parentSSL.getLpu().getId());
    	
    }
}
