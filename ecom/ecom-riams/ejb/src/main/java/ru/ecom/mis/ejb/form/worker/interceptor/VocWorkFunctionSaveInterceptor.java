package ru.ecom.mis.ejb.form.worker.interceptor;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.jaas.ejb.form.interceptor.SecPolicySaveInterceptor;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.ecom.mis.ejb.form.worker.VocWorkFunctionForm;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ListPersist;

public class VocWorkFunctionSaveInterceptor  implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(SecPolicySaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	VocWorkFunction classif = (VocWorkFunction)aEntity ;
    	VocWorkFunctionForm frm = (VocWorkFunctionForm)aForm ;
		//EntityManager manager = aContext.getEntityManager() ;
		Long id = classif.getId() ;
		//ListPersist.saveArrayJson("WorkFunctionService", id, frm.getMedServices(),"vocWorkFunction_id","medService_id", aContext.getEntityManager()) ;
		
		
	}
}