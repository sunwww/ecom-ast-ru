package ru.ecom.mis.web.action.synclpufond;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.web.util.Injection;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class SyncLpuFondAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final long timeId = getLongId(aRequest, "Импорт id");
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final ISyncLpuFondService service = Injection.find(aRequest).getService(ISyncLpuFondService.class) ;

        final long monitorId = monitorService.createMonitor();
        new Thread() {
            public void run() {
                service.sync(monitorId, timeId);
            }
        }.start() ;
        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    }
}
