package ru.ecom.ejb.services.monitor;

import java.io.Serializable;

/**
 * Идентификатор монитора
 */
public class MonitorId implements Serializable {

	public MonitorId(long aMonitorId) {
		monitorId = aMonitorId ;
	}
	
	public long getMonitorId() {
		return monitorId ;
	}
	
	private final long monitorId ;
}
