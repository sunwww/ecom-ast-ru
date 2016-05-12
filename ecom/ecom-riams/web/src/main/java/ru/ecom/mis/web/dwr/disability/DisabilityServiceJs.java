package ru.ecom.mis.web.dwr.disability;

import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;

/**
 * Сервис для работы с нетрудоспособностью
 * @author stkacheva
 *
 */
public class DisabilityServiceJs {
	public String getPrefixForLN(HttpServletRequest aRequest) throws NamingException {
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select ml.prefixForLN from secuser su left join workfunction wf on wf.secuser_id=su.id left join worker w on w.id=wf.worker_id left join mislpu ml on ml.id=w.lpu_id where su.login='").append(login).append("' and ml.prefixForLN is not null and ml.prefixForLN!=''") ;
		IWebQueryService service = Injection.find(aRequest) .getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql(sql.toString(),1) ;
		if (l.isEmpty()) {
			return "" ;
		} else {
			return ""+l.iterator().next().get1() ;
		}
	}
	public String analyseExportLN(String aFileName, HttpServletRequest aRequest) throws NamingException {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.analyseExportLN(aFileName);
	}
	public String getCodeByReasonClose(Long aReason, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest) .getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql("select id,coalesce(codef,'') from VocDisabilityDocumentCloseReason where id='"+aReason+"'",1) ;
		if (l.isEmpty()) {
			return null ;
		} else {
			return ""+l.iterator().next().get2() ;
		}
	}
	public String getMaxDateToByDisDocument(Long aDisDocument, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest) .getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql("select to_char(max(dr.dateTo),'dd.mm.yyyy'),max(case when dr.dateto is null then dr.id else null end) from DisabilityRecord dr where disabilityDocument_id='"+aDisDocument+"'",1) ;
		if (l.isEmpty()) {
			return null ;
		} else {
			WebQueryResult wqr = l.iterator().next() ; 
			return wqr.get2()==null?""+wqr.get1():null ;
		}
	}
	public String closeDisabilityDocument(Long aDocId, Long aReasonId,String aSeries,String aNumber,String aOtherCloseDate,HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.closeDisabilityDocument(aDocId, aReasonId,aSeries,aNumber,aOtherCloseDate) ;
	}
	public String exportLNByDate(String aDateStart, String aDateFinish, String aLpu, String aWorkFunction, String aPacketNumber, String aDateType, HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		
		return service.exportLNByDate(aDateStart, aDateFinish,  aLpu, aWorkFunction,  aPacketNumber, aDateType );
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
	public Long createWorkComboDocument(Long aDocId,String aJob, String aSeries, String aNumber, Long aVocCombo, HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.createWorkComboDocument(aDocId, aJob, aSeries, aNumber, aVocCombo) ;
	}
}
