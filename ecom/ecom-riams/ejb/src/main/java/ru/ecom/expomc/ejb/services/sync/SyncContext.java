package ru.ecom.expomc.ejb.services.sync;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;

/**
 * Синхронизация
 */
public class SyncContext {


    public SyncContext(long aMonitorId, ImportTime aImportTime, EntityManager aManager) {
        monitorId = aMonitorId;
        importTime = aImportTime;
        entityManager = aManager;
    }
    /**  */
    public EntityManager getEntityManager() { return entityManager ; }

    /**  */
    private final EntityManager entityManager ;
    /** Монитор */
    public ILocalMonitorService getMonitorService() { return monitorService ; }
    public void setMonitorService(ILocalMonitorService aMonitorService) { monitorService = aMonitorService ; }

    /** Монитор */
    private ILocalMonitorService monitorService ;
    /** Монитор */
    public long getMonitorId() { return monitorId ; }

    /** Импорт */
    public ImportTime getImportTime() { return importTime ; }

    /** Импорт */
    private final ImportTime importTime ;

    /** Монитор */
    private final long monitorId ;
}
