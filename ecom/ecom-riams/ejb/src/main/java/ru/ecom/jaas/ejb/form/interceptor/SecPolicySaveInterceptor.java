package ru.ecom.jaas.ejb.form.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.form.SecPolicyForm;
import ru.ecom.jaas.ejb.form.SecRoleForm;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;

public class SecPolicySaveInterceptor implements IFormInterceptor {

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
			Collection<SecPolicy> c = new ArrayList<>() ;
			c.add(classif) ;
			role.setSecPolicies(c) ;
			manager.persist(role) ;	
		}
	}
}