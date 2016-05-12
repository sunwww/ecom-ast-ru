package ru.ecom.miniejb;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 */
public class ThreadLocalContext {

    /** Контекст сервиса */
    public SessionContext getSessionContext() { return theSessionContext ; }
    public void setSessionContext(SessionContext aSessionContext) {
//        if(theSessionContext!=null) throw new IllegalStateException("Контекст сервиса SessionContext уже установлен") ;
        if(theSessionContext==null) theSessionContext = aSessionContext ;
    }

    /** Контекст сервиса */
    private SessionContext theSessionContext ;

    public void setFactory(EntityManagerFactory aFactory) {
        theFactory  = aFactory ;
    }


    public EntityManager getManager() {
        if(theManager==null) {
            theManager = theFactory.createEntityManager();
            theManager.getTransaction().begin();
        }
        return theManager ;
    }

    public void close() {
        theSessionContext = null ;
        if(theManager!=null) {
            theManager.getTransaction().commit();
            theManager.close();
        }
    }

    public EntityManagerFactory getFactory() {
        if(theFactory==null) throw new IllegalArgumentException("Не установлен EntityManagerFactory") ;
        return theFactory;
    }

    private EntityManager theManager ;
    private EntityManagerFactory theFactory ;

}
