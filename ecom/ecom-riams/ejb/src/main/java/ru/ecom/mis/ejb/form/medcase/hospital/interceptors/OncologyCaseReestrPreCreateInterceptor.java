package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;

import javax.persistence.EntityManager;

public class OncologyCaseReestrPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
//		OncologyCaseReestrForm form = (OncologyCaseReestrForm)aForm ;
    	MedCase parent = manager.find(MedCase.class, aParentId) ;
        //Запрет на создание в СЛО и СЛС, если случай закрыт. Админ может создавать.
		if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut") &&
				(parent instanceof HospitalMedCase)) {
        	MedCase hmc = (parent instanceof DepartmentMedCase)? parent.getParent() : parent;
        	if (hmc.getDateFinish()!=null) throw new IllegalStateException("Пациент выписан. Нельзя добавлять онкологический случай в закрытый СЛС!");
		}
    }
}