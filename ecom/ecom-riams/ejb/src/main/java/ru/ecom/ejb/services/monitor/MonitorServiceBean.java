package ru.ecom.ejb.services.monitor;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.Map;

/**
 * Управление монитором
 */
@Stateless
@Remote(IRemoteMonitorService.class)
@Local(ILocalMonitorService.class)
public class MonitorServiceBean implements IRemoteMonitorService, ILocalMonitorService {
    public long createMonitor() {
        return holder.createNextId();
    }
    
    public MonitorId createMonitorId() {
    	return new MonitorId(createMonitor());
    }

    public RemoteMonitorStatus getMonitorStatus(long aMonitorId) {
        checkExists(aMonitorId) ;
        LocalMonitorStatus status = holder.getMonitor(aMonitorId);
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
        holder.getMonitor(aMonitorId).setCancelled(true);
    }

    private void checkExists(long aMonitorId) {
        if(!holder.isExists(aMonitorId)) {
            throw new IllegalArgumentException("Ожидание монитора с идентификатором "+aMonitorId) ;
        }
    }
    
	public IMonitor acceptMonitor(long aMonitorId, String aMessage) {
        if(holder.isExists(aMonitorId)) {
        	throw new IllegalArgumentException("Монитор с идентификатором "+aMonitorId+" уже существует") ;
        }
		LocalAcceptedMonitor monitor = new LocalAcceptedMonitor(aMessage) ;
		holder.putMonitor(aMonitorId, monitor) ;
		return monitor;
	}
    
    public IMonitor startMonitor(long aMonitorId, String aMonitorName, double aMaximum) {
        LocalMonitorStatus monitor = new LocalMonitorStatus(aMonitorName, aMaximum);
        monitor.setText("Starting service ...");
        if(holder.isExists(aMonitorId) 
        		&& !(holder.getMonitor(aMonitorId) instanceof LocalAcceptedMonitor)) {
            throw new IllegalArgumentException("Монитор с идентификатором "+aMonitorId+" уже запущен") ;
        }
        holder.putMonitor(aMonitorId, monitor);
        return monitor;
    }

    public IMonitor getMonitor(long aMonitorId) {
        checkExists(aMonitorId) ;
        return holder.getMonitor(aMonitorId);
    }
    public String getAllMonitors () {
        JSONArray ret = new JSONArray();
        for (Map.Entry<Long, LocalMonitorStatus> entry : holder.getAllMonitors().entrySet()) {
            LocalMonitorStatus monitorStatus = entry.getValue();
            ret.put(new JSONObject().put("id",entry.getKey()).put("name",monitorStatus.getName()).put("text",monitorStatus.getText())
                .put("finishText",monitorStatus.isFinished() ? monitorStatus.getFinishParameters() :"")
                .put("status",monitorStatus.isCancelled() ? "отменен" : monitorStatus.isFinished() ? "завершен" : "работает" ));
        }
        return ret.toString();
    }

    private final MonitorHolder holder = MonitorHolder.getInstance();

	public long subTask(IMonitor aMonitor, String aName) {
		return holder.createNextId() ;
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
