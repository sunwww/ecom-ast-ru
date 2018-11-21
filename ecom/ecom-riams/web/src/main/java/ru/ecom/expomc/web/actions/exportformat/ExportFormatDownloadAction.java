package ru.ecom.expomc.web.actions.exportformat;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.file.IJbossGetFileService;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.ejb.services.exportformat.IExportFormatService;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @user ikouzmin 08.03.2007 16:53:11
 */
public class ExportFormatDownloadAction  extends BaseAction {
    private final static Logger LOG = Logger.getLogger(ExportFormatDownloadAction.class) ;
    private final static boolean CAN_TRACE = LOG.isDebugEnabled() ;



    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        final HttpSession session = aRequest.getSession() ;
        final LoginInfo loginInfo = LoginInfo.find(session) ;
        if (!loginInfo.isUserInRole("/Policy/Exp/ExportFormat/Exec")) {
            throw new Exception("Access denied");
        }


        final IExportFormatService service = Injection.find(aRequest).getService(IExportFormatService.class) ;
        final IJbossGetFileService fileService = Injection.find(aRequest).getService(IJbossGetFileService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;

        final long id = getLongId(aRequest, "Идентификатор формата") ;
        final long monitorId = monitorService.createMonitor();

        final long fileId = fileService.register() ;
        LOG.info("export-fileid:"+fileId);

        new Thread() {
            public void run() {
                service.setMaxRecords(0);
                service.exportAsXml(new Long(id),fileId,monitorId);
            }
        }.start() ;

        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    }
}
