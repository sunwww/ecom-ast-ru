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
        lastId++ ;
        return lastId ;
    }

    void putMonitor(long aMonitorId, LocalMonitorStatus aMonitor) {
        hash.put(aMonitorId, aMonitor) ;
    }

    LocalMonitorStatus getMonitor(long aMonitorId) {
        return hash.get(aMonitorId);
    }

    boolean isExists(long aMonitorId) {
        return hash.get(aMonitorId)!=null ;
    }

    HashMap<Long, LocalMonitorStatus> getAllMonitors() {
        return hash;

    }
    private long lastId = 0 ;
    private final HashMap<Long, LocalMonitorStatus> hash = new HashMap<>();
}
