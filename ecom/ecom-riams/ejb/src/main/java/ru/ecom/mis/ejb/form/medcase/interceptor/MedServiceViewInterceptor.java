package ru.ecom.mis.ejb.form.medcase.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;

public class MedServiceViewInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    //	MedServiceForm frm = (MedServiceForm) aForm ;
   // 	EntityManager manager = aContext.getEntityManager() ;
    	//frm.setVocWorkFunctions(ListPersist.getArrayJson("WorkFunctionService", "medService_id", frm.getId(), "vocWorkFunction_id", manager)) ;
	}
}