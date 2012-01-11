package ru.ecom.mis.web.action.contract;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.contract.IContractService;
import ru.ecom.mis.web.dwr.contract.ContractServiceJs;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ContractAccountMedServiceSaveAction extends BaseAction {
	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		IContractService service = Injection.find(aRequest).getService(IContractService.class) ;
		String idS ;
		idS = aRequest.getParameter("id");
		int cntRec = Integer.valueOf(aRequest.getParameter("cnt"));
		Long account = ConvertSql.parseLong(idS) ;
		for(int i=1; i<=cntRec;i++){
			String price = aRequest.getParameter("cost"+i) ;
			BigDecimal cost = new BigDecimal(price);
			Integer cnt = Integer.valueOf(aRequest.getParameter("count"+i)) ;
			Long priceMedService = ConvertSql.parseLong(aRequest.getParameter("service"+i)) ;
			if(cnt!=null && cnt>Long.valueOf(0) 
					&&priceMedService!=null && priceMedService>Long.valueOf(0)
					)  {
				System.out.println("---add account="+account+"----service="+ priceMedService+"----cnt="+ cnt+"---cost="+ cost) ;
				service.addMedServiceByAccount(account, priceMedService, cnt, cost) ;
			}
		}
		
		return aMapping.findForward(SUCCESS);
	}
}