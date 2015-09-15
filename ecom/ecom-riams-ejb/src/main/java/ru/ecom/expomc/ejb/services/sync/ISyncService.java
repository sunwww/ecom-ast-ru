package ru.ecom.expomc.ejb.services.sync;

/**
 */
public interface ISyncService {
    public void sync(long aMonitorId, long aTimeId) ;
    public void syncByEntity(Long aMonitorId, String aEntity) ;
}
