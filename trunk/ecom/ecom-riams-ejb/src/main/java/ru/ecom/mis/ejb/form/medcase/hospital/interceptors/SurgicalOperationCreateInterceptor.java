package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;

public class SurgicalOperationCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	MedCase parentSSL = manager.find(MedCase.class, aParentId) ;
    	SurgicalOperationForm form=(SurgicalOperationForm)aForm;
    	
    	if (parentSSL!=null && parentSSL instanceof HospitalMedCase) {
    		HospitalMedCase hosp = (HospitalMedCase) parentSSL ;
    		if (hosp.getDateFinish()!=null && hosp.getDischargeTime()!=null) {
    			throw new IllegalStateException("Нельзя добавить хирургическую операцию в закрытый случай стационарного лечения (ССЛ) !!!") ;
    		}
    		if (hosp.getDepartment()!=null) form.setDepartment(hosp.getDepartment().getId()) ;
    	} else if (parentSSL!=null && parentSSL instanceof DepartmentMedCase){
    		DepartmentMedCase slo = (DepartmentMedCase) parentSSL ;
    		if (slo.getDepartment()!=null) form.setDepartment(slo.getDepartment().getId()) ;
    	} else {
    		throw new IllegalStateException("Невозможно добавить хирургическую операцию. Сначала надо определить  случай стационарного лечения (ССЛ)") ;
    	}
    	
		if (parentSSL.getLpu()!=null) form.setLpu(parentSSL.getLpu().getId());
    	
    }
}
