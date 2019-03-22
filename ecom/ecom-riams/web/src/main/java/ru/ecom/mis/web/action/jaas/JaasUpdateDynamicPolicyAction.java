package ru.ecom.mis.web.action.jaas;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Обновление политик
 */
public class JaasUpdateDynamicPolicyAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
//        final IJaasMisService service = Injection.find(aRequest).getService(IJaasMisService.class) ;
//
//        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
//        final long monitorId = monitorService.createMonitor();
//
//        new Thread() {
//            public void run() {
//                service.updateJaasPolicy(monitorId);
//            }
//        }.start() ;
//        return new MonitorActionForward(monitorId,new ActionForward(aMapping.findForward(SUCCESS).getPath()+getLongId(aRequest, "Идентификатор")));
    	Injection.find(aRequest).getService(IAddressPointService.class).refresh();
    	return aMapping.findForward(SUCCESS);
    }
}
