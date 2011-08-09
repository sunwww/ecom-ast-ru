package ru.ecom.ejb.services.monitor;

/**
 *
 */
public interface ILocalMonitorService {

	/**
	 * Монитор принят и ждет запуска 
	 * @param aMonitorId
	 */
	IMonitor acceptMonitor(long aMonitorId, String aText) ;
	IMonitor acceptMonitor(MonitorId aMonitorId, String aText) ;
	
	IMonitor startMonitor(long aMonitorId, String aMonitorName, double aMaximum) ;
    IMonitor startMonitor(MonitorId aMonitorId, String aMonitorName, double aMaximum) ;

    IMonitor getMonitor(long aMonitorId) ;
    IMonitor getMonitor(MonitorId aMonitorId) ;
    
    long subTask(IMonitor aMonitor, String aName) ;
    MonitorId subTaskId(IMonitor aMonitor, String aName) ;
}
