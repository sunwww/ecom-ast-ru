package ru.ecom.mis.web.action.medcase;

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
import ru.ecom.expomc.web.actions.importtime.ImportTimeUploadForm;
import ru.ecom.expomc.web.actions.importtime.ImportTimeUploadSaveAction;
import ru.ecom.expomc.web.actions.importtime.MonitorActionForward;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm;
import ru.ecom.web.util.FileUploadUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class HospitalReportRefreshAction extends BaseAction {

    private final static Log LOG = LogFactory.getLog(ImportTimeUploadSaveAction.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final AdmissionJournalForm form = (AdmissionJournalForm) aForm;
        final IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        if (form!=null && form.getDateBegin()!=null && form.getDateBegin()!=null
        		&& form.getDateEnd()!=null && form.getDateEnd()!=null) {
	        final long monitorId = monitorService.createMonitor() ;
	        new Thread() {
	            public void run() {
	            	service.refreshReportByPeriod(form.getDateBegin(), form.getDateEnd(),monitorId) ;
	            }
	        }.start() ;
	
	        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
        } else {
        	return aMapping.findForward("success") ;
        }
    }


}
