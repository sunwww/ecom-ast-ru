package ru.ecom.mis.web.action.medcase;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.web.actions.importtime.MonitorActionForward;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HospitalReportRefreshAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final AdmissionJournalForm form = (AdmissionJournalForm) aForm;
        final IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        System.out.println(form.getRefreshType()) ;
        if (form!=null && form.getDateBegin()!=null && !form.getDateBegin().equals("")
        		&& form.getDateEnd()!=null && !form.getDateEnd().equals("")) {

        	final long monitorId = monitorService.createMonitor() ;
	        new Thread() {
	            public void run() {
	            	if (form.getRefreshType()!=null && 
	            			(form.getRefreshType().equals("")||form.getRefreshType().equals("REFRESH_36"))) {
	            		service.refreshReportByPeriod(form.getDateBegin(), form.getDateEnd(),monitorId) ;
	            	} else if (form.getRefreshType()!=null && 
	            			form.getRefreshType().equals("REFRESH_COMP_TREATMENT")) {
	            		service.refreshCompTreatmentReportByPeriod(form.getDateBegin(), form.getDateEnd(),monitorId) ;
	            	}
	            }
	        }.start() ;
	        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
        } else {
        	return aMapping.findForward("success") ;
        }
    }
}
