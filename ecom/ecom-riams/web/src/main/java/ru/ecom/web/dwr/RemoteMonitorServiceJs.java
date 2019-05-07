package ru.ecom.web.dwr;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.monitor.RemoteMonitorStatus;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;


/**
 * 
 */
public class RemoteMonitorServiceJs {
     private static final Logger LOG = Logger.getLogger(RemoteMonitorServiceJs.class) ;


    public RemoteMonitorStatus getMonitorStatus(HttpServletRequest aRequest, long aMonitorId) throws NamingException {
        RemoteMonitorStatus status ;
        try {
            IRemoteMonitorService service = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
            status = service.getMonitorStatus(aMonitorId);
        } catch (IllegalArgumentException e) {
            status = new RemoteMonitorStatus("Ожидание монитора","", 0,0,false, "");
            LOG.warn(e) ;
        }
        return status ;
    }

    public void cancel(HttpServletRequest aRequest, long aMonitorId) throws NamingException {
        IRemoteMonitorService service = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        service.cancel(aMonitorId);
    }

    //все активные мониторы
    public String getAllMonitors(HttpServletRequest aRequest) throws NamingException {
        IRemoteMonitorService service = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        return service.getAllMonitors();

    }

}
