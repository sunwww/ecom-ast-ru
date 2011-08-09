package ru.ecom.jaas.ejb.form.interceptor;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.form.SecPolicyForm;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ListPersist;

public class SecPolicySaveInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(SecPolicySaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	SecPolicy classif = (SecPolicy)aEntity ;
    	SecPolicyForm frm = (SecPolicyForm)aForm ;
		//EntityManager manager = aContext.getEntityManager() ;
		Long id = classif.getId() ;
		ListPersist.saveArrayJson("SecRole_SecPolicy", id, frm.getRoleList(),"secPolicies_id","SecRole_id", aContext.getEntityManager()) ;
		
		
	}
}