package ru.ecom.mis.ejb.service.sync.shubinok;

/**
 * Синхронизация с базой П.Г.Шибинка
 */
public interface ISyncShubinokService {
    public void sync(long aMonitorId, long aTimeId) ;
}
