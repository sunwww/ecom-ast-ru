package ru.ecom.expomc.web.actions.export;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.file.IJbossGetFileService;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.ejb.services.exportservice.ExportForm;
import ru.ecom.expomc.ejb.services.exportservice.IExportService;
import ru.ecom.expomc.web.actions.importtime.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Показывает формочку для экспорта
 */
public class ExportSaveAction extends BaseAction {
    private static final Logger LOG = Logger.getLogger(ExportSaveAction.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;


    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final IExportService service = Injection.find(aRequest).getService(IExportService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final IJbossGetFileService fileService = Injection.find(aRequest).getService(IJbossGetFileService.class) ;

        //final long mкonitorId = monitorService.createMonitor() ;
        final long fileId = fileService.register() ;
        final long monitorId = monitorService.createMonitor();
        final ExportForm form = (ExportForm) aForm ;
        new Thread() {
            public void run() {
                try {
                    if (CAN_TRACE) LOG.info("monitor = " + monitorId);
                    service.export(monitorId, fileId, form);
                } catch (Exception e) {
                    throw new IllegalStateException(e) ;
                }
            }
        }.start() ;
        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;

//        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    }


}
