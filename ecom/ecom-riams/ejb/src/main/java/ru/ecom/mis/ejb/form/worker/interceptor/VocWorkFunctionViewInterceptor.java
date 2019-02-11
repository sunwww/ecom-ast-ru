package ru.ecom.mis.ejb.form.worker.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;

public class VocWorkFunctionViewInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
   // 	VocWorkFunctionForm frm = (VocWorkFunctionForm) aForm ;
    //	EntityManager manager = aContext.getEntityManager() ;
    	//frm.setMedServices(ListPersist.getArrayJson("WorkFunctionService", "vocWorkFunction_id", frm.getId(), "medService_id", manager)) ;
	}
}