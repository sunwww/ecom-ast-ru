package ru.ecom.poly.ejb.form.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.protocol.Protocol;
import ru.ecom.poly.ejb.form.protocol.ProtocolForm;

public class ProtocolSaveInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(ProtocolSaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();


	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		Protocol protocol=(Protocol)aEntity;
    	String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    	protocol.setUsername(username);
    	List<WorkFunction> listwf =  aContext.getEntityManager().createQuery("from WorkFunction where secUser.login = :login")
		.setParameter("login", username).getResultList() ;
		if (listwf.size()==0) {
			new IllegalArgumentException(
					"Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)"
					);
		} else {
			protocol.setSpecialist(listwf.get(0)) ;
		}
		
	}

}
