package ru.ecom.expomc.web.actions.regcreate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.file.IJbossGetFileService;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.ejb.services.registry.CreateRegistryForm;
import ru.ecom.expomc.ejb.services.registry.print.IRegistryPrintService;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Создание реестра
 */
public class RegCreateSaveAction extends BaseAction {
	
	private final static Log LOG = LogFactory.getLog(RegCreateSaveAction.class);
//	private final static boolean CAN_TRACE = LOG.isTraceEnabled();
	
    public ActionForward myExecute(ActionMapping aMapping, final ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {

        final IRegistryPrintService service = Injection.find(aRequest).getService(IRegistryPrintService.class) ;
        final IJbossGetFileService fileService = Injection.find(aRequest).getService(IJbossGetFileService.class) ;
        final IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;

        final long monitorId = monitorService.createMonitor();
        final long fileId = fileService.register() ;
        
        aRequest.getSession().setAttribute("next_url", "exp_reg.do") ;
        new Thread() {
            public void run() {
                try {
                    service.printReestr(monitorId, fileId, (CreateRegistryForm)aForm);
                } catch (Exception e) {
                	LOG.error("Ошибки при печати реестра", e) ;
                }
            }
        }.start() ;

        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    }
    

    public ActionForward _myExecute(ActionMapping aMapping, final ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	final long HOUR24 = 123 ;
    	final long DS = 123 ;
    	
		CreateRegistryForm form = (CreateRegistryForm)aForm ;
		form.setTemplate("registry.xls") ;
		form.setFormat(30) ;
		form.setBillDate("05.04.2007") ;
		
		// КРУГЛ
		form.setTimeImport(HOUR24) ;
		//      РОСНО
		form.setCompany("10") ;
		p(HOUR24, DS, 142,143, 97, aMapping, aForm, aRequest, aResponse) ;
		p(HOUR24, DS, 144,145, 97, aMapping, aForm, aRequest, aResponse) ;
		p(HOUR24, DS, 146,147, 97, aMapping, aForm, aRequest, aResponse) ;
    	p(HOUR24, DS, 148,149, 97, aMapping, aForm, aRequest, aResponse) ;
    	
    	return aMapping.findForward("success") ;
    }


	private void p(long aKrug, long aDs, int aRegNumber, int aRegNumberDs, int aBillNumber, ActionMapping aMapping, ActionForm aForm, HttpServletRequest request, HttpServletResponse response) {
		CreateRegistryForm form = (CreateRegistryForm)aForm ;
		form.setTimeImport(aKrug) ;
		
	}
}
