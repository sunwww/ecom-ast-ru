package ru.ecom.mis.ejb.form.medcase.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;

import javax.persistence.EntityManager;
import java.util.List;

public class MedServiceSaveInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	MedService ms = (MedService)aEntity ;
    	MedServiceForm form = (MedServiceForm)aForm ;
		EntityManager manager = aContext.getEntityManager() ;
		
		if(form.getVocMedServiceIsCreate()!=null&&form.getVocMedServiceIsCreate()) {

			VocMedService vms = new VocMedService() ;
			List<VocMedService> list = manager.createQuery("from VocMedService where code=:code").setParameter("code", form.getCode()).getResultList() ;
			if (list.isEmpty()) {
				String role="/Policy/Mis/VocMedService/Create";
				if (aContext.getSessionContext().isCallerInRole(role)) {
					vms.setName(form.getName()) ;
					vms.setCode(form.getCode()) ;
					vms.setComment("autogenerate") ;
					manager.persist(vms) ;
				} else {
					throw new IllegalArgumentException(". невозможно создать внешнюю услугу: нет политики !"+role ) ;
				}
			} else {
				vms = list.get(0) ;
			}
			ms.setVocMedService(vms) ;
			try {
				manager.persist(vms) ;
			} catch (Exception e) {
				throw new IllegalStateException(e.getMessage());
			}
						
			
		}
	}
}