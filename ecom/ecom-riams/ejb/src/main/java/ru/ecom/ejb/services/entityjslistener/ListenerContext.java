package ru.ecom.ejb.services.entityjslistener;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

public class ListenerContext {
	
	protected ListenerContext(EntityManager aManager, SessionContext aContext) {
		manager = aManager ;
		sessionContext = aContext ;
	}
	
	public EntityManager getManager() {
		return manager ;
	}
	
	public SessionContext getSessionContext() {
		return sessionContext ;
	}
	
	private final EntityManager manager ;
	private final SessionContext sessionContext ;

}
