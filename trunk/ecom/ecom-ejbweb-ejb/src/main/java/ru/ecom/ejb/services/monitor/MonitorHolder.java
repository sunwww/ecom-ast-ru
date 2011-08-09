package ru.ecom.ejb.services.monitor;

import java.util.HashMap;

/**
 * Мониторы
 */
public class MonitorHolder {
    private static MonitorHolder INSTANCE = new MonitorHolder();


    private MonitorHolder() {
    }


    static MonitorHolder getInstance() {
        return INSTANCE ;
    }

    synchronized long createNextId() {
        theLastId++ ;
        return theLastId ;
    }

    void putMonitor(long aMonitorId, LocalMonitorStatus aMonitor) {
        theHash.put(aMonitorId, aMonitor) ;
    }

    LocalMonitorStatus getMonitor(long aMonitorId) {
        return theHash.get(aMonitorId);
    }

    boolean isExists(long aMonitorId) {
        return theHash.get(aMonitorId)!=null ;
    }
    
    private long theLastId = 0 ;
    private final HashMap<Long, LocalMonitorStatus> theHash = new HashMap<Long, LocalMonitorStatus>();
}
