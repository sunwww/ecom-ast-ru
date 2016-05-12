package ru.ecom.expomc.web.actions.filltime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.monitor.MonitorId;
import ru.ecom.expomc.ejb.uc.filltime.service.IFillTimeService;
import ru.ecom.expomc.web.actions.importtime.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Запуск заполнения
 */
public class ExecuteFillTimeServiceAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final IFillTimeService service = (IFillTimeService)Injection.find(aRequest).getService(IFillTimeService.class);
        
        final MonitorId monitor = monitorService.createMonitorId() ;
        
        final long fillTimeId = getLongId(aRequest, "Идентификатор заполнения") ;
        new Thread() {
            public void run() {
                try {
                    service.fill(fillTimeId, monitor);
                } catch (Exception e) {
                    throw new IllegalStateException(e) ;
                }
            }
        }.start() ;
        return new MonitorActionForward(monitor, aMapping.findForward(SUCCESS)) ;
	
	}
}
