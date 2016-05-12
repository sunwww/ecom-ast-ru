package ru.ecom.expomc.web.actions.sync;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.ejb.services.sync.ISyncService;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 *
 */
public class SyncAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final long timeId = getLongId(aRequest, "Импорт id");
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final ISyncService service = Injection.find(aRequest).getService(ISyncService.class) ;

        final long monitorId = monitorService.createMonitor();
        new Thread() {
            public void run() {
                service.sync(monitorId, timeId);
            }
        }.start() ;
        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    }
}
