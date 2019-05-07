package ru.ecom.ejb.services.monitor;

/**
 *
 */
public interface IRemoteMonitorService {

    /**
     * Создание нового ионитора задачи
     */
    long createMonitor() ;
    
    MonitorId createMonitorId() ;

    RemoteMonitorStatus getMonitorStatus(long aMonitorId) ;

    void cancel(long aMonitorId) ;

    String getAllMonitors ();
}
