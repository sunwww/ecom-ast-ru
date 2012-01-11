package ru.ecom.mis.web.dwr.contract;

import javax.servlet.http.HttpServletRequest;
import ru.ecom.mis.ejb.service.contract.IContractService;
import ru.ecom.web.util.Injection;

public class ContractServiceJs {
	public String getCostByPriceMedService(Long aMedService, HttpServletRequest aRequest) throws Exception {
		IContractService service = Injection.find(aRequest).getService(IContractService.class) ;
		return service.getCostByMedService(aMedService, "") ;
	}

}
