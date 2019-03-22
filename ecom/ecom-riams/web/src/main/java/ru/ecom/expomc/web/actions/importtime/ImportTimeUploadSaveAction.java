package ru.ecom.expomc.web.actions.importtime;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.ejb.services.importservice.IImportService;
import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.web.util.FileUploadUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Сохранения файла и собственно импорт
 */
public class ImportTimeUploadSaveAction extends BaseAction {

    private static final Logger LOG = Logger.getLogger(ImportTimeUploadSaveAction.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;


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
        if (CAN_TRACE) LOG.info("monitorId = " + monitorId);
        new Thread() {
            public void run() {
                try {
                    if (CAN_TRACE) LOG.info("Importing file " + filename);
                     service.importFile(form.getFile().getFileName(), monitorId, filename, timeForm) ;
                } catch (ImportException e) {
                    throw new IllegalStateException(e) ;
                }
            }
        }.start() ;

        return new MonitorActionForward(monitorId, aMapping.findForward(SUCCESS)) ;
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
