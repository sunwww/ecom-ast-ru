package ru.ecom.ejb.services.voc;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

public class VocContext {
	
	public VocContext(EntityManager aManager, SessionContext aContext) {
		theEntityManager = aManager ;
		theSessionContext = aContext ;
		
	}
	
	/**
     * EntityManager
     */
    public EntityManager getEntityManager() {
        return theEntityManager;
    }


	public SessionContext getSessionContext() {
		return theSessionContext ;
	}
	
    private final EntityManager theEntityManager;
    private final SessionContext theSessionContext ;
}
