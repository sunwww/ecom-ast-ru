package ru.ecom.expomc.ejb.services.sync;

/**
 */
public interface ISyncService {
    void sync(long aMonitorId, long aTimeId) ;
    void syncByEntity(Long aMonitorId, String aEntity) ;
}
