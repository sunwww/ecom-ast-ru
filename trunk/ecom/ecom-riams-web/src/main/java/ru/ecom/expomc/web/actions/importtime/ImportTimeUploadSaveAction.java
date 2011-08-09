package ru.ecom.expomc.web.actions.importtime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.ejb.services.importservice.IImportService;
import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.expomc.ejb.services.importservice.ImportFileResult;
import ru.ecom.web.util.FileUploadUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.commons.auth.ILoginInfo;

/**
 * Сохранения файла и собственно импорт
 */
public class ImportTimeUploadSaveAction extends BaseAction {

    private final static Log LOG = LogFactory.getLog(ImportTimeUploadSaveAction.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final ImportTimeUploadForm form = (ImportTimeUploadForm) aForm;
        final IImportService service = Injection.find(aRequest).getService(IImportService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;

        final String filename = FileUploadUtil.writeFile(form.getFile()) ;
//        File tempFile = File.createTempFile("import","dbf") ;
//        writeFile(form.getFile().getInputStream(), tempFile.getAbsolutePath());


        final ImportFileForm timeForm = new ImportFileForm();
        timeForm.setActualDateFrom(form.getActualDateFrom());
        timeForm.setActualDateTo(form.getActualDateTo());
        timeForm.setImportFormat(form.getFormat());
        timeForm.setComment(form.getComment());

        final long monitorId = monitorService.createMonitor() ;
        if (CAN_TRACE) LOG.trace("monitorId = " + monitorId);
        new Thread() {
            public void run() {
                try {
                    if (CAN_TRACE) LOG.trace("Improting file " + filename);
                    ImportFileResult result = service.importFile(
                    		form.getFile().getFileName(), monitorId, filename, timeForm) ;
                } catch (ImportException e) {
                    throw new IllegalStateException(e) ;
                }
            }
        }.start() ;

        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
//        return new ActionForward("/ecom_monitor.do?id="+monitorId,true) ; //ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), form.getDocument());
    }


//    private static void writeFile(InputStream aInputStream, String aFilename) throws IOException {
//        int count = 0 ;
//        FileOutputStream out = new FileOutputStream(aFilename);
//        byte[] buf = new byte[8192] ;
//        while ( (count=aInputStream.read(buf)) > 0) {
//            out.write(buf, 0, count) ;
//        }
//        out.close() ;
//
//    }
}
