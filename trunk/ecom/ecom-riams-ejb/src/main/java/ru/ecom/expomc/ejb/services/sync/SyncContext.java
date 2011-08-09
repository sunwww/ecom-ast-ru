package ru.ecom.expomc.ejb.services.sync;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;

/**
 * Синхронизация
 */
public class SyncContext {


    public SyncContext(long aMonitorId, ImportTime aImportTime, EntityManager aManager) {
        theMonitorId = aMonitorId;
        theImportTime = aImportTime;
        theEntityManager = aManager;
    }

    /** Транзакции */
//    public UserTransaction getTransactionManager() { return theTransactionManager ; }
//    public void setTransactionManager(UserTransaction aTransactionManager) { theTransactionManager = aTransactionManager ; }

    /** Транзакции */
//    private UserTransaction theTransactionManager ;
    /**  */
    public EntityManager getEntityManager() { return theEntityManager ; }

    /**  */
    private final EntityManager theEntityManager ;
    /** Монитор */
    public ILocalMonitorService getMonitorService() { return theMonitorService ; }
    public void setMonitorService(ILocalMonitorService aMonitorService) { theMonitorService = aMonitorService ; }

    /** Монитор */
    private ILocalMonitorService theMonitorService ;
    /** Монитор */
    public long getMonitorId() { return theMonitorId ; }

    /** Импорт */
    public ImportTime getImportTime() { return theImportTime ; }

    /** Импорт */
    private final ImportTime theImportTime ;

    /** Монитор */
    private final long theMonitorId ;
}
