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
	
	public String exportLNByDate(String aDateStart, String aDateFinish, String aSocCode, String aSocPhone, String aSocEmail, String aOgrnCode, String aPacketNumber, HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		
		return service.exportLNByDate(aDateStart, aDateFinish,  aSocCode,  aSocPhone,  aSocEmail,  aOgrnCode, aPacketNumber );
	}
	
	public String exportLNByNumber (String aNumber,HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		
		return service.exportLNByNumber(aNumber);
	}
	
	public String getDataByClose(Long aDocId,HttpServletRequest aRequest) throws Exception {
		System.out.println("doc="+aDocId) ;
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.getDataByClose(aDocId) ;
	}
	public Long createDuplicateDocument(Long aDocId,Long aReasonId, String aSeries, String aNumber,Long aWorkFunction2,String aJob, Boolean aUpdateJob, HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.createDuplicateDocument(aDocId, aReasonId, aSeries, aNumber,aWorkFunction2,aJob,aUpdateJob) ;
	}
	public Long createWorkComboDocument(Long aDocId,String aJob, String aSeries, String aNumber, HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.createWorkComboDocument(aDocId, aJob, aSeries, aNumber) ;
	}
}
