package ru.ecom.ejb.services.monitor;

import java.io.Serializable;

/**
 * Идентификатор монитора
 */
public class MonitorId implements Serializable {

	public MonitorId(long aMonitorId) {
		theMonitorId = aMonitorId ;
	}
	
	public long getMonitorId() {
		return theMonitorId ;
	}
	
	private final long theMonitorId ;
}
