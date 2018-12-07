package ru.ecom.ejb.services.entityform.jsinterceptor;

import ru.ecom.ejb.util.injection.EjbInjection;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

public class JavaScriptFormInterceptorContext {

	public JavaScriptFormInterceptorContext(EntityManager aManager, SessionContext aContext
			, EjbInjection aInjection) {
		theManager = aManager ;
		theSessionContext = aContext ;
		theInjection = aInjection ;
		theUsername = theSessionContext.getCallerPrincipal().getName() ;
	}
	
	public EntityManager getManager() {
		return theManager ;
	}
	
	public SessionContext getSessionContext() {
		return theSessionContext ;
	}
	/** Пользователь */
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Пользователь */
	private String theUsername;
	
//	public Object serviceInvoke(String aServiceName, String aMethodName, Object[] args) {
//		return theInjection.invoke(aServiceName, aMethodName, args) ;
//	}

	public Object serviceInvoke(String aServiceName, String aMethodName) {
		return theInjection.invoke(aServiceName, aMethodName, new Object[]{}) ;
	}
	
	public Object serviceInvoke(String aServiceName, String aMethodName, Object aArg0) {
		return theInjection.invoke(aServiceName, aMethodName, new Object[]{aArg0}) ;
	}

	public Object serviceInvoke(String aServiceName, String aMethodName
			, Object aArg0, Object aArg1) {
		return theInjection.invoke(aServiceName, aMethodName, new Object[]{aArg0, aArg1}) ;
	}

	public Object serviceInvoke(String aServiceName, String aMethodName
			, Object aArg0, Object aArg1, Object aArg3) {
		return theInjection.invoke(aServiceName, aMethodName, new Object[]{aArg0, aArg1, aArg3}) ;
	}

	public Object serviceInvoke(String aServiceName, String aMethodName
			, Object aArg0, Object aArg1, Object aArg3, Object aArg4) {
		return theInjection.invoke(aServiceName, aMethodName
				, new Object[]{aArg0, aArg1, aArg3, aArg4}) ;
	}
	
	
	private final EntityManager theManager ;
	private final SessionContext theSessionContext ;
	private final EjbInjection theInjection ;
	
}
