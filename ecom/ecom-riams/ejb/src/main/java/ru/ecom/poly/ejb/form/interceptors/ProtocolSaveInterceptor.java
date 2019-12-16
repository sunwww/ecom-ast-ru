package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.protocol.Protocol;

import java.util.List;

public class ProtocolSaveInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	String username = aContext.getSessionContext().getCallerPrincipal().getName() ;
    	List<WorkFunction> listwf =  aContext.getEntityManager().createQuery("from WorkFunction where secUser.login = :login")
		.setParameter("login", username).getResultList() ;
		if (listwf.isEmpty()) {
			throw new IllegalArgumentException(
					"Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответствия между рабочей функцией и пользователем (WorkFunction и SecUser)"
					);
		} else {
			Protocol protocol=(Protocol)aEntity;
			protocol.setUsername(username);
			protocol.setSpecialist(listwf.get(0)) ;
		}
	}
}