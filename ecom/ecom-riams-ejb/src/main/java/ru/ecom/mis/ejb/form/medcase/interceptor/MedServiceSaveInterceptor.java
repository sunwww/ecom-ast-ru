package ru.ecom.mis.ejb.form.medcase.interceptor;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.jaas.ejb.form.interceptor.SecPolicySaveInterceptor;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ListPersist;

public class MedServiceSaveInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(SecPolicySaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	MedService classif = (MedService)aEntity ;
    	MedServiceForm frm = (MedServiceForm)aForm ;
		//EntityManager manager = aContext.getEntityManager() ;
		Long id = classif.getId() ;
		//ListPersist.saveArrayJson("WorkFunctionService", id, frm.getVocWorkFunctions(),"medService_id","vocWorkFunction_id", aContext.getEntityManager()) ;
		
		
	}
}