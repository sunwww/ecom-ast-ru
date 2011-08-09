package ru.ecom.ejb.services.entityform.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;

public interface IFormInterceptor {
	void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) ;

}
