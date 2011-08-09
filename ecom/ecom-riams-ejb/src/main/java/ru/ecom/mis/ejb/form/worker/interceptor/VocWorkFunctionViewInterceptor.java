package ru.ecom.mis.ejb.form.worker.interceptor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.jaas.ejb.form.interceptor.SecPolicyViewInterceptor;
import ru.ecom.mis.ejb.form.worker.VocWorkFunctionForm;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ListPersist;

public class VocWorkFunctionViewInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(SecPolicyViewInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	VocWorkFunctionForm frm = (VocWorkFunctionForm) aForm ;
    	EntityManager manager = aContext.getEntityManager() ;
    	//frm.setMedServices(ListPersist.getArrayJson("WorkFunctionService", "vocWorkFunction_id", frm.getId(), "medService_id", manager)) ;
	}
}