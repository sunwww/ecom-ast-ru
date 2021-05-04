package ru.ecom.ejb.services.entityform.jsinterceptor;

import ru.ecom.ejb.util.injection.EjbInjection;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

public class JavaScriptFormInterceptorContext {

	public JavaScriptFormInterceptorContext(EntityManager aManager, SessionContext aContext
			, EjbInjection aInjection) {
		manager = aManager ;
		sessionContext = aContext ;
		injection = aInjection ;
		username = sessionContext.getCallerPrincipal().getName() ;
	}
	
	public EntityManager getManager() {
		return manager ;
	}
	
	public SessionContext getSessionContext() {
		return sessionContext ;
	}
	/** Пользователь */
	public String getUsername() {return username;}
	public void setUsername(String aUsername) {username = aUsername;}

	/** Пользователь */
	private String username;
	
	public Object serviceInvoke(String aServiceName, String aMethodName) {
		return injection.invoke(aServiceName, aMethodName, new Object[]{}) ;
	}
	
	public Object serviceInvoke(String aServiceName, String aMethodName, Object aArg0) {
		return injection.invoke(aServiceName, aMethodName, new Object[]{aArg0}) ;
	}

	public Object serviceInvoke(String aServiceName, String aMethodName
			, Object aArg0, Object aArg1) {
		return injection.invoke(aServiceName, aMethodName, new Object[]{aArg0, aArg1}) ;
	}

	public Object serviceInvoke(String aServiceName, String aMethodName
			, Object aArg0, Object aArg1, Object aArg3) {
		return injection.invoke(aServiceName, aMethodName, new Object[]{aArg0, aArg1, aArg3}) ;
	}

	public Object serviceInvoke(String aServiceName, String aMethodName
			, Object aArg0, Object aArg1, Object aArg3, Object aArg4) {
		return injection.invoke(aServiceName, aMethodName
				, new Object[]{aArg0, aArg1, aArg3, aArg4}) ;
	}
	
	
	private final EntityManager manager ;
	private final SessionContext sessionContext ;
	private final EjbInjection injection ;
	
}
