package ru.ecom.mis.web.dwr.disability;

import java.text.ParseException;
import java.util.*;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateConverter;

/**
 * Сервис для работы с нетрудоспособностью
 * @author stkacheva
 *
 */
public class DisabilityServiceJs {

	public String getLNNumberRange (Long aCount, HttpServletRequest aRequest) throws NamingException {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
		return service.getLNNumberRange(aCount);
	}
	public String exportDisabilityDocument (Long aDocumentId, HttpServletRequest aRequest) throws NamingException {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
		//ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class);
		String ret = service.exportDisabilityDocument(aDocumentId);

		return ret;
	}
//Milamesher 0408
public String annulDisabilityDocument(Long aDocumentId, String aReasonAnnulId, String textReason, String snils, HttpServletRequest aRequest)  throws NamingException {
	IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
	return service.annulDisabilityDocument(aDocumentId,aReasonAnnulId,textReason,snils);
}
	public String getExportJournalById (Long aDocumentId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest) .getService(IWebQueryService.class) ;

		StringBuilder ret = new StringBuilder();
		String sql="select result, to_char(requestdate,'dd.MM.yyyy') as f2_date, cast (requesttime as varchar(5)) as f3_time from exportfsslog where disabilitydocument='"+aDocumentId+"' order by requestdate desc , requesttime desc ";
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (!list.isEmpty()) {
			ret.append("<table class=\"tabview sel tableArrow\"><tr>");
			ret.append("<td>Результат</td><td>Дата отправки</td><td>Время отправки</td>");
			ret.append("</tr>");
			for (WebQueryResult r: list) {
				ret.append("<tr><td>").append(r.get1()).append("</td><td>").append(r.get2()).append("</td><td>").append(r.get3()).append("</td></tr>");
			}
			ret.append("</table>");
		}
		return ret.length()>0?ret.toString():null;
	}

	/**
	 * Возвращаем любой свободный номер больничного листа, или номер, резерв которого закончился (прошел час с момента резервирования)
	 * @param aRequest
	 * @return
	 * @throws NamingException
	 * @throws ParseException
	 */
	public String getFreeNumberForDisabilityDocument(HttpServletRequest aRequest) throws NamingException, ParseException {
		IWebQueryService service = Injection.find(aRequest) .getService(IWebQueryService.class) ;
		String ret = "";
		Collection<WebQueryResult> list = service.executeNativeSql("select number as f1,to_char(reservedate,'dd.MM.yyyy') as f2_date, cast(reservetime as varchar(5)) as f3_time from ElectronicDisabilityDocumentNumber where disabilitydocument_id is null ");
		if (list.isEmpty()) {
		} else {
			Date currentDate = new Date();
			Calendar cal = new GregorianCalendar();

			for (WebQueryResult r: list) {
				if (r.get2()==null||r.get2().toString().trim().equals("")) {
					ret = r.get1().toString();
					break;
				} else {
					Date reserveDate = DateConverter.createDateTime(r.get2().toString(),r.get3().toString());
					cal.setTime(reserveDate);
					cal.add(Calendar.HOUR,1);
					if (currentDate.getTime()>cal.getTime().getTime()) { //Если за 1 час больничный лист не оформили, забераем его себе.
					ret = r.get1().toString();
					break;
					}

				}
			}
			if (ret!=null&&!ret.equals("")) {
				service.executeUpdateNativeSql("update ElectronicDisabilityDocumentNumber set reservedate = current_date, reservetime = current_time where number ='"+ret+"' ");
			}
		}
		return ret;
	}
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
	public Long createWorkComboDocument(Long aDocId,String aJob, String aSeries, String aNumber, Long aVocCombo, Long aPrevDocument, HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class) ;
		return service.createWorkComboDocument(aDocId, aJob, aSeries, aNumber, aVocCombo, aPrevDocument) ;
	}
	//Milamesher 0308 - получить список причин аннулирования
	public String getReasonsfOfAnnulSheets(HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String query="SELECT code,name from vocannulreason";
		Collection<WebQueryResult> list = service.executeNativeSql(query);
		StringBuilder res = new StringBuilder() ;
		if (list.size()>0) {
			for (WebQueryResult wqr : list) {
				res.append(wqr.get1()).append("#").append(wqr.get2()).append("!");
			}
		}
		else {
			res.append("##");
			}
		return res.toString();
	}
	//Milamesher 0308 - отметить аннулирование
	public String setAnnulDisabilitySheet(Long aDocId,String comment,String code,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String sql = "SELECT number from electronicdisabilitydocumentnumber WHERE disabilitydocument_id='" + aDocId + "'";
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (list.size() > 0) {
			StringBuilder res = new StringBuilder();
			WebQueryResult wqr = list.iterator().next() ;
			service.executeUpdateNativeSql("UPDATE electronicdisabilitydocumentnumber SET annuldate=current_date, comment='" + comment + "', annulreason_id=(SELECT id FROM vocannulreason WHERE code='" + code + "') WHERE disabilitydocument_id='" + aDocId + "'");
			service.executeUpdateNativeSql("UPDATE disabilitydocument SET noactuality=true WHERE number='" + wqr.get1().toString() + "'");
			res.append(wqr.get1().toString()).append('#');
			list = service.executeNativeSql("select p.snils from patient p\n" +
					"inner join disabilitydocument d on d.patient_id=p.id\n" +
					"inner join electronicdisabilitydocumentnumber e on e.disabilitydocument_id=d.id where e.number='" + wqr.get1().toString() + "'");
			wqr = list.iterator().next() ;
			if (list.size() > 0) res.append(wqr.get1().toString());
			else return "Не найден СНИЛС у пациента!";
			return res.toString();
		}
		else return "Такого электронного ЛН нет!";
	}
}