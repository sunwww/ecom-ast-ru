package ru.ecom.web.dwr;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.monitor.RemoteMonitorStatus;
import ru.ecom.web.util.Injection;

/**
 * 
 */
public class RemoteMonitorServiceJs {
     private final static Log LOG = LogFactory.getLog(RemoteMonitorServiceJs.class) ;
     private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


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

}
