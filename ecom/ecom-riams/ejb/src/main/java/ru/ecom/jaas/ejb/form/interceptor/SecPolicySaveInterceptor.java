package ru.ecom.jaas.ejb.form.interceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.form.SecPolicyForm;
import ru.ecom.jaas.ejb.form.SecRoleForm;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ListPersist;
import sun.awt.windows.ThemeReader;

public class SecPolicySaveInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(SecPolicySaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	SecPolicy classif = (SecPolicy)aEntity ;
    	SecPolicyForm frm = (SecPolicyForm)aForm ;
		EntityManager manager = aContext.getEntityManager() ;
		if (frm.getIsCreateRole()!=null&&frm.getIsCreateRole().equals(Boolean.TRUE)) {
			SecRoleForm roleForm = frm.getRoleForm() ;
			SecRole role = new SecRole() ;
			if (roleForm.getName()==null || roleForm.getName().trim().equals("")) {
				role.setName(frm.getName()) ;
			} else {
				role.setName(roleForm.getName()) ;
			}
			if (roleForm.getKey()==null||roleForm.getKey().trim().equals("")) {
				role.setKey(frm.getKey()) ;
			} else {
				role.setKey(roleForm.getKey()) ;
			}
			java.util.Date dat = new java.util.Date() ;
			role.setCreateDate(new java.sql.Date(dat.getTime())) ;
			role.setCreateTime(new java.sql.Time(dat.getTime())) ;
			role.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
			Collection<SecPolicy> c = new ArrayList<SecPolicy>() ;
			c.add(classif) ;
			role.setSecPolicies(c) ;
			manager.persist(role) ;	
		}
	}
}