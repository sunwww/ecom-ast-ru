package ru.ecom.ejb.services.quickquery;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;


public class QuickQueryContext {

	public QuickQueryContext(EntityManager aManager, SessionContext aSessionContext) {
		manager = aManager ;
		context = aSessionContext;
	}
	
	/** Entity Manager */
	public EntityManager getManager() {
		return manager;
	}

	public SessionContext getSessionContext() {
		return context ;
	}

	private final SessionContext context;	/** Entity Manager */
	private final EntityManager manager;
}
