package ru.ecom.mis.ejb.service.sync.shubinok;

/**
 * Синхронизация с базой П.Г.Шибинка
 */
public interface ISyncShubinokService {
    void sync(long aMonitorId, long aTimeId) ;
    void syncPatientByFond(long aMonitorId, long aTimeId) ;
}
