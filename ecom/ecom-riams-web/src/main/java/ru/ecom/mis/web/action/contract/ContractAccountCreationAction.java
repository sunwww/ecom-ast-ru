package ru.ecom.mis.web.action.contract;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.contract.IContractService;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

public class ContractAccountCreationAction extends BaseAction {
	
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, final HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        final IContractService service = Injection.find(aRequest).getService(IContractService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final String accountNumber = aRequest.getParameter("accountNumber") ;
        final String dateFrom = aRequest.getParameter("dateFrom") ;
        final String dateTo = aRequest.getParameter("dateTo") ;
        final String medContart = aRequest.getParameter("medContart") ;
        
        
        final long monitorId = monitorService.createMonitor() ;
        new Thread() {
            public void run() {
            	service.accountCreation(monitorId, dateFrom, dateTo, ConvertSql.parseLong(medContart), accountNumber) ;
            	new InfoMessage(aRequest, "Обработка завершена") ;
            }
        }.start() ;
        
        return new MonitorActionForward(monitorId, new ActionForward("/entityView-contract_juridicalContract.do?id="+medContart+"&tmp=",false)) ;
    }
}