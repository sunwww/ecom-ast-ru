package ru.ecom.ejb.services.script;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.jsinterceptor.JavaScriptFormInterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;

public class ScriptServiceContext extends JavaScriptFormInterceptorContext {

	public ScriptServiceContext(EntityManager aManager, SessionContext aContext, EjbInjection aInjection) {
		super(aManager, aContext, aInjection);
	}

	
}
