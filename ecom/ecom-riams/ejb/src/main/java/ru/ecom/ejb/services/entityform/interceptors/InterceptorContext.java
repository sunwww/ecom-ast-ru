package ru.ecom.ejb.services.entityform.interceptors;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import java.lang.annotation.ElementType;

/**
 *
 */
public class InterceptorContext {

    public InterceptorContext(EntityManager aEntityManager, SessionContext aSessionContext) {
    	this(aEntityManager, aSessionContext, ElementType.TYPE) ;
    }
    
    public InterceptorContext(EntityManager aEntityManager, SessionContext aSessionContext, ElementType aTarget) {
        entityManager = aEntityManager ;
        sessionContext = aSessionContext ;
        target = aTarget ;
    }

    /** SessionContext */
    public SessionContext getSessionContext() { return sessionContext ; }


    /** EntityManager */
    public EntityManager getEntityManager() { return entityManager ; }

    public ElementType getTarget() {
    	return target ;
    }
    
    private final ElementType target ;
    /** EntityManager */
    private final EntityManager entityManager ;
    /** SessionContext */
    private final SessionContext sessionContext ;
}
