package ru.ecom.poly.web.action.ticketexport;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.web.util.Injection;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.ejb.services.file.IJbossGetFileService;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;

import ru.ecom.poly.ejb.services.ITicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 29.01.2007
 * Time: 8:02:14
 * To change this template use File | Settings | File Templates.
 */
public class TicketExcelExportAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

		final ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;
        final IJbossGetFileService fileService = Injection.find(aRequest).getService(IJbossGetFileService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;

        final long id = getLongId(aRequest, "Идентификатор талона");
        final long monitorId = monitorService.createMonitor();
      
        final long fileId = fileService.register() ;
        new Thread() {
            public void run() {
                service.printTicket(monitorId, id, fileId);
            }
        }.start() ;

        return new MonitorActionForward(fileId, aMapping.findForward("success")) ;
    }
}