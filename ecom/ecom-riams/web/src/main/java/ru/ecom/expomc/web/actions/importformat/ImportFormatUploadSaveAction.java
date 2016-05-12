/**
 * Импорт XML-файла
 *
 * @author ikouzmin 08.03.2007 21:06:26
 */
package ru.ecom.expomc.web.actions.importformat;

import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.commons.auth.ILoginInfo;
import ru.ecom.expomc.web.actions.importtime.MonitorActionForward;

import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.form.importformat.IImportFormatService;
import ru.ecom.web.util.Injection;
import ru.ecom.web.util.FileUploadUtil;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.file.IJbossGetFileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImportFormatUploadSaveAction extends BaseAction {

    private final static Log LOG = LogFactory.getLog(ImportFormatUploadSaveAction.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public ActionForward myExecute(final ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {


        final HttpSession session = aRequest.getSession() ;
        final LoginInfo loginInfo = LoginInfo.find(session) ;
        if (!loginInfo.isUserInRole("/Policy/Exp/ImportFormat/Exec")) {
            throw new Exception("Access denied");
        }


        final ImportFormatUploadForm form = (ImportFormatUploadForm) aForm;
        final IImportFormatService service = Injection.find(aRequest).getService(IImportFormatService.class);

        FormFile formFile = form.getFile();
        final String originalFileName = formFile.getFileName(); 
        final String tempFileName = FileUploadUtil.writeFile(formFile) ;

    

        final IJbossGetFileService fileService = Injection.find(aRequest).getService(IJbossGetFileService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;

        final long monitorId = monitorService.createMonitor();

        long fileId = 0;


        final ImportFileForm timeForm = new ImportFileForm();
        timeForm.setActualDateFrom(form.getActualDateFrom());
        timeForm.setActualDateTo(form.getActualDateTo());
        timeForm.setImportFormat(form.getFormat());
        timeForm.setComment(form.getComment());
        boolean isLogging = form.getViewLog();

        service.setDebug(form.getDebug());
        service.setUpdateModifiedOnly(form.getUpdateModifiedOnly());
        service.setVerifyAfterSave(form.getVerifyAfterSave());

        if (isLogging) {
            fileId = fileService.register();
            LOG.info("export-fileid:"+fileId);
            //service.setLogFileId(fileId);
        }

        LOG.info("monitorId = " + monitorId);

        //aRequest.getSession().setAttribute("logfile",service.getLogFileName());

        final long fileId1 = fileId;

        new Thread() {
            public synchronized void run() {
                try {
                    service.importFile(monitorId, fileId1, tempFileName,originalFileName, timeForm) ;
                    LOG.info("filename:"+fileService.getRelativeFilename(fileId1)+":");
                } catch (ImportException e) {
                    throw new IllegalStateException(e) ;
                }
            }

        }.start();
        
        return new MonitorActionForward(monitorId, aMapping.findForward(isLogging?"viewlog":"success")) ;
    }

}
