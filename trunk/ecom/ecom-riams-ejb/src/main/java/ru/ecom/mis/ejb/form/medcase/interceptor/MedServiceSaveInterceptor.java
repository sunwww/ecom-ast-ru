package ru.ecom.mis.ejb.form.medcase.interceptor;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.jaas.ejb.form.interceptor.SecPolicySaveInterceptor;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ListPersist;

public class MedServiceSaveInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(SecPolicySaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	MedService ms = (MedService)aEntity ;
    	MedServiceForm form = (MedServiceForm)aForm ;
		EntityManager manager = aContext.getEntityManager() ;
		
		if(form.getVocMedServiceIsCreate()!=null&&form.getVocMedServiceIsCreate().equals(Boolean.TRUE)) {
			VocMedService vms = new VocMedService() ;
			List<VocMedService> list = manager.createQuery("from VocMedService where code=:code").setParameter("code", form.getCode()).getResultList() ;
			if (list.size()>0) {
				vms = list.get(0) ;
			} else {
				vms.setName(form.getName()) ;
				vms.setCode(form.getCode()) ;
				vms.setComment("autogenerate") ;
				manager.persist(vms) ;
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