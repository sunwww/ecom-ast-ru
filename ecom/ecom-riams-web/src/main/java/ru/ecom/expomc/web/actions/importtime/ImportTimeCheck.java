package ru.ecom.expomc.web.actions.importtime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.expomc.ejb.services.check.CheckDocumentDataException;
import ru.ecom.expomc.ejb.services.check.ICheckService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Проверить
 */
public class ImportTimeCheck extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final ICheckService service = Injection.find(aRequest).getService(ICheckService.class);
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;

        final long monitorId = monitorService.createMonitor();
        final long id = getLongId(aRequest, "Документ по дате") ;

        new Thread() {
            public void run() {
                try {
                    service.checkDocumentData(monitorId, id);
                } catch (CheckDocumentDataException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }.start() ;

        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
//        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), id);
    }
}
