package ru.ecom.jaas.ejb.form.interceptor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.jaas.ejb.form.SecPolicyForm;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ListPersist;

public class SecPolicyViewInterceptor   implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(SecPolicyViewInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	SecPolicyForm frm = (SecPolicyForm) aForm ;
    	EntityManager manager = aContext.getEntityManager() ;
    	frm.setRoleList(ListPersist.getArrayJson("SecRole_SecPolicy", "secPolicies_id", frm.getId(), "SecRole_id", manager)) ;
	}

}
