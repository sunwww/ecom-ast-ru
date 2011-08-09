package ru.ecom.mis.web.action.bypassexport;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.mis.ejb.service.bypass.IBypassService;
import ru.ecom.web.util.Injection;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.ejb.services.file.IJbossGetFileService;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экспорт в Excel
 */
public class BypassAreaExcelExportAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        final IBypassService service = Injection.find(aRequest).getService(IBypassService.class) ;
        final IJbossGetFileService fileService = Injection.find(aRequest).getService(IJbossGetFileService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;

        final long id = getLongId(aRequest, "Идентификатор участка") ;
        final long monitorId = monitorService.createMonitor();
        final String type = aRequest.getParameter("type") ;

        final long fileId = fileService.register() ;
        new Thread() {
            public void run() {
                if("area".equals(type)) {
                    service.printByArea(monitorId, id, fileId);
                } else {
                    service.printByAreaAddress(monitorId, id, fileId);
                }
            }
        }.start() ;

        return new MonitorActionForward(fileId, aMapping.findForward("success")) ;
    }
}

