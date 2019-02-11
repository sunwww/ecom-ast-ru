package ru.ecom.ejb.services.monitor;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Управление монитором
 */
@Stateless
@Remote(IRemoteMonitorService.class)
@Local(ILocalMonitorService.class)
public class MonitorServiceBean implements IRemoteMonitorService, ILocalMonitorService {
    public long createMonitor() {
        return theHolder.createNextId();
    }
    
    public MonitorId createMonitorId() {
    	return new MonitorId(createMonitor());
    }

    public RemoteMonitorStatus getMonitorStatus(long aMonitorId) {
        checkExists(aMonitorId) ;
        LocalMonitorStatus status = theHolder.getMonitor(aMonitorId);
        return new RemoteMonitorStatus(
                status.getName()
                , status.getText()
                , status.getMaximum()
                , status.getCurrent()
                , status.isFinished()
                , status.getFinishParameters()
        ) ;
    }

    public void cancel(long aMonitorId) {
        checkExists(aMonitorId);
        theHolder.getMonitor(aMonitorId).setCancelled(true);
    }

    private void checkExists(long aMonitorId) {
        if(!theHolder.isExists(aMonitorId)) {
            throw new IllegalArgumentException("Ожидание монитора с идентификатором "+aMonitorId) ;
        }
    }
    
	public IMonitor acceptMonitor(long aMonitorId, String aMessage) {
        if(theHolder.isExists(aMonitorId)) {
        	throw new IllegalArgumentException("Монитор с идентификатором "+aMonitorId+" уже существует") ;
        }
		LocalAcceptedMonitor monitor = new LocalAcceptedMonitor(aMessage) ;
		theHolder.putMonitor(aMonitorId, monitor) ;
		return monitor;
	}
    
    public IMonitor startMonitor(long aMonitorId, String aMonitorName, double aMaximum) {
        LocalMonitorStatus monitor = new LocalMonitorStatus(aMonitorName, aMaximum);
        monitor.setText("Starting service ...");
        if(theHolder.isExists(aMonitorId) 
        		&& !(theHolder.getMonitor(aMonitorId) instanceof LocalAcceptedMonitor)) {
            throw new IllegalArgumentException("Монитор с идентификатором "+aMonitorId+" уже запущен") ;
        }
        theHolder.putMonitor(aMonitorId, monitor);
        return monitor;
    }

    public IMonitor getMonitor(long aMonitorId) {
        checkExists(aMonitorId) ;
        return theHolder.getMonitor(aMonitorId);
    }

    private final MonitorHolder theHolder = MonitorHolder.getInstance();

	public long subTask(IMonitor aMonitor, String aName) {
		return theHolder.createNextId() ;
	}

	public IMonitor acceptMonitor(MonitorId aMonitorId, String aText) {
		return acceptMonitor(aMonitorId.getMonitorId(), aText) ;
	}

	public IMonitor getMonitor(MonitorId aMonitorId) {
		return getMonitor(aMonitorId.getMonitorId());
	}

	public IMonitor startMonitor(MonitorId aMonitorId, String aMonitorName, double aMaximum) {
		return startMonitor(aMonitorId.getMonitorId(), aMonitorName, aMaximum);
	}

	public MonitorId subTaskId(IMonitor aMonitor, String aName) {
		return new MonitorId(subTask(aMonitor, aName)) ;
	}

}
