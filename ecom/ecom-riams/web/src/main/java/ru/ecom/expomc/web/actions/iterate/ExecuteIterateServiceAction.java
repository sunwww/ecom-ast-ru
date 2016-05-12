package ru.ecom.expomc.web.actions.iterate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.monitor.MonitorId;
import ru.ecom.expomc.ejb.uc.iterate.service.IIterateService;
import ru.ecom.expomc.web.actions.importtime.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ExecuteIterateServiceAction extends BaseAction {
	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final IIterateService service = Injection.find(aRequest).getService(IIterateService.class);
        
        final MonitorId monitor = monitorService.createMonitorId() ;
        
        final long iterateId = getLongId(aRequest, "Идентификатор перебора") ;
        new Thread() {
            public void run() {
                try {
                    service.executeIterate(monitor, iterateId);
                } catch (Exception e) {
                    throw new IllegalStateException(e) ;
                }
            }
        }.start() ;
        return new MonitorActionForward(monitor, aMapping.findForward(SUCCESS)) ;
	
	}
}
