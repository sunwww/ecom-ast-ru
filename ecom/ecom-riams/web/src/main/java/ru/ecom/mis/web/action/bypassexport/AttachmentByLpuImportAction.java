package ru.ecom.mis.web.action.bypassexport;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.ejb.services.form.importformat.IImportFormatService;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.web.util.FileUploadUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AttachmentByLpuImportAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	final AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
    	final long fileId = 0;
    	 IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
	     
    	final long monitorId = monitorService.createMonitor();
    	if (form!=null ) {
    		FormFile formFile = form.getAttachmentFile();
    	
    		if (formFile!=null&&!formFile.equals("")) {
    			final IImportFormatService service = Injection.find(aRequest).getService(IImportFormatService.class);
    			 final String originalFileName = form.getAttachmentFile().getFileName(); 
    		     final String tempFileName = FileUploadUtil.writeFile(formFile) ;
    		     final ImportFileForm timeForm = new ImportFileForm();
    		        timeForm.setActualDateFrom(form.getPeriod());
    		        timeForm.setActualDateTo(form.getPeriod());
    		        timeForm.setImportFormat(form.getImportFormat());
    		        timeForm.setComment("AUTOIMPORT, Дата импорта = "+DateFormat.formatToDate(new java.util.Date()));
    		        
    		    //   new Thread() {
    		    //       public synchronized void run() {
    		                try {
    		                	service.importFile(monitorId,fileId , tempFileName, originalFileName, timeForm);
    		                   } catch (Exception e) {
    		                	   e.printStackTrace();
    		                    throw new IllegalStateException(e) ;
    		                }
    		     //       }

    		    //    }.start();
    		        aRequest.setAttribute("impResult", "Файл успешно проимпортирован!");
    		} 
    	}
        return aMapping.findForward("success") ;
    }
}