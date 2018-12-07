package ru.ecom.ejb.services.entityjslistener;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

public class ListenerContext {
	
	protected ListenerContext(EntityManager aManager, SessionContext aContext) {
		theManager = aManager ;
		theSessionContext = aContext ;
	}
	
	public EntityManager getManager() {
		return theManager ;
	}
	
	public SessionContext getSessionContext() {
		return theSessionContext ;
	}
	
	private final EntityManager theManager ;
	private final SessionContext theSessionContext ;

}
