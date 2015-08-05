package ru.ecom.mis.web.action.bypassexport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.expomc.ejb.services.form.importformat.IImportFormatService;
import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.FileUploadUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class AttachmentByLpuImportAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
        System.out.println("00000000000 = 01");
    	if (form!=null ) {
    		System.out.println("00000000000 = 02");	
    		FormFile formFile = form.getAttachmentFile();
    	
    		if (formFile!=null&&!formFile.equals("")) {
    			System.out.println("00000000000 = 03");
    			System.out.println("FormFile="+formFile.getFileSize());
    			final IImportFormatService service = Injection.find(aRequest).getService(IImportFormatService.class);
    			 final String originalFileName = formFile.getFileName(); 
    		     final String tempFileName = FileUploadUtil.writeFile(formFile) ;
    		     System.out.println("HELLOOOO "+tempFileName);
    		     IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
    		     final long monitorId = monitorService.createMonitor();
    		     final ImportFileForm timeForm = new ImportFileForm();
    		        timeForm.setActualDateFrom(form.getPeriod());
    		        timeForm.setActualDateTo(form.getPeriod());
    		        timeForm.setImportFormat(form.getImportFormat());
    		        timeForm.setComment("AUTOIMPORT, date="+form.getPeriod());
    		        new Thread() {
    		           public synchronized void run() {
    		                try {
    		                	service.setDebug(true);
    		                    service.setUpdateModifiedOnly(true);
    		                    service.setVerifyAfterSave(true);
    		                
    		                    service.importFile(monitorId, 0, tempFileName,originalFileName, timeForm) ;
    		                   } catch (ImportException e) {
    		                    throw new IllegalStateException(e) ;
    		                }
    		            }

    		        }.start();
    		        
    		}
    	
    		 
    		}
        return aMapping.findForward("success") ;
    }
}