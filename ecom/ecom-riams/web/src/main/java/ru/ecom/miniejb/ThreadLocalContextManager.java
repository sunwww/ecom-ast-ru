package ru.ecom.miniejb;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 */
public class ThreadLocalContextManager {

    private final static ThreadLocal<ThreadLocalContext> THREAD  = new ThreadLocal<ThreadLocalContext>();

    public static void start() {
        if(THREAD.get()!=null) throw new IllegalStateException("Менеджер уже запущен") ;
        THREAD.set(new ThreadLocalContext());
    }

    public static void stop() {
        ctx().close() ;
        THREAD.remove() ;
    }

    public static EntityManagerFactory getFactory() {
        return ctx().getFactory() ;
    }
    public static EntityManager getManager() {
        return ctx().getManager() ;
    }

    public static void setSessionContext(SessionContext aSessionContext) {
        ctx().setSessionContext(aSessionContext) ;
    }

    public static SessionContext getSessionContext() {
        return ctx().getSessionContext() ;
    }

    private static ThreadLocalContext ctx() {
        return THREAD.get() ;
    }


    public static void setEntityManagerFactory(EntityManagerFactory aFactory) {
        ctx().setFactory(aFactory) ;
    }
}
