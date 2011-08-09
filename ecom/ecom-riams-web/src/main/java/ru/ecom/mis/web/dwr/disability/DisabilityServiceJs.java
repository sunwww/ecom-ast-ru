package ru.ecom.mis.web.dwr.disability;

import javax.servlet.http.HttpServletRequest;

import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.util.Injection;

/**
 * Сервис для работы с нетрудоспособностью
 * @author stkacheva
 *
 */
public class DisabilityServiceJs {
	public String closeDisabilityDocument(Long aDocId, Long aReasonId,String aSeries,String aNumber,HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.closeDisabilityDocument(aDocId, aReasonId,aSeries,aNumber) ;
	}
	
	public String getDataByClose(Long aDocId,HttpServletRequest aRequest) throws Exception {
		System.out.println("doc="+aDocId) ;
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.getDataByClose(aDocId) ;
	}
}
