package ru.ecom.ejb.services.quickquery;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.sun.accessibility.internal.resources.accessibility_zh_CN;

public class QuickQueryContext {

	public QuickQueryContext(EntityManager aManager, SessionContext aSessionContext) {
		theManager = aManager ;
		theContext = aSessionContext;
	}
	
	/** Entity Manager */
	public EntityManager getManager() {
		return theManager;
	}

	public SessionContext getSessionContext() {
		return theContext ;
	}

	private final SessionContext theContext;	/** Entity Manager */
	private final EntityManager theManager;
}
