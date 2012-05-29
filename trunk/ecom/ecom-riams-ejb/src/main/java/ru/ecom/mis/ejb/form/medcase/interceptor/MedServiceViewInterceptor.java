package ru.ecom.mis.ejb.form.medcase.interceptor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;

public class MedServiceViewInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(MedServiceViewInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	MedServiceForm frm = (MedServiceForm) aForm ;
    	EntityManager manager = aContext.getEntityManager() ;
    	//frm.setVocWorkFunctions(ListPersist.getArrayJson("WorkFunctionService", "medService_id", frm.getId(), "vocWorkFunction_id", manager)) ;
	}
}