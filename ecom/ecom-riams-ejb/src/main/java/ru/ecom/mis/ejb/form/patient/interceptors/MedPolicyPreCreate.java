package ru.ecom.mis.ejb.form.patient.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.MedPolicyForm;

public class MedPolicyPreCreate implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	MedPolicyForm form = (MedPolicyForm) aForm ;
    	Patient pat = manager.find(Patient.class, aParentId) ;
    	
    	if (pat!=null) {
    		form.setLastname(pat.getLastname()) ;
    		form.setFirstname(pat.getFirstname()) ;
    		form.setMiddlename(pat.getMiddlename()) ;
    	} else {
    		throw new IllegalStateException("Невозможно добавить полис. Сначала надо определить персону!!!") ;
    	}
    }
}
