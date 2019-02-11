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
        theEntityManager = aEntityManager ;
        theSessionContext = aSessionContext ;
        theTarget = aTarget ;
    }

    /** SessionContext */
    public SessionContext getSessionContext() { return theSessionContext ; }


    /** EntityManager */
    public EntityManager getEntityManager() { return theEntityManager ; }

    public ElementType getTarget() {
    	return theTarget ;
    }
    
    private final ElementType theTarget ;
    /** EntityManager */
    private final EntityManager theEntityManager ;
    /** SessionContext */
    private final SessionContext theSessionContext ;
}
