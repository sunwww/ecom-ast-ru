package ru.ecom.ejb.services.quickquery;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;


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
