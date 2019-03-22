package ru.ecom.expomc.web.actions.regcreate;

import org.apache.log4j.Logger;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Создание реестра
 */
public class RegCreateSaveAction extends BaseAction {
	
	private static final Logger LOG = Logger.getLogger(RegCreateSaveAction.class);

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

        return new MonitorActionForward(monitorId, aMapping.findForward(SUCCESS)) ;
    }
}
