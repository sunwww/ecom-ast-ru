package ru.ecom.mis.web.dwr.disability;

import java.text.ParseException;
import java.util.*;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
	public static Logger log = Logger.getLogger(DisabilityServiceJs.class);

	/**
	 *
	 * @param aDisabilityDocumentId - ID документа нетрудоспособности
	 * @param aNumber - Номер документа нетрудоспособности
	 * @param aRequest
	 * @return
	 */
	public Long getElectronicDisabilityNumber(Long aDisabilityDocumentId, String aNumber, HttpServletRequest aRequest) throws NamingException {

		String documentInfo = getElectronicDisabilityDocumentInfo(aDisabilityDocumentId,aNumber,aRequest);
		if (documentInfo!=null) {
			return Long.valueOf(documentInfo.split("#")[0]);
		}
		return null;
	}
	public String getStringFromWebQueryResult(WebQueryResult wqr, String aDelimiter) {
		if (wqr==null||wqr.get1()==null) {return null;}
		StringBuilder sb = new StringBuilder();
		//while (true) {
			//sb.append(wqr.get+i())
		//}
		return "";
	}
	public String getElectronicDisabilityDocumentInfo(Long aDisabilityDocumentId, String aNumber, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder sb = new StringBuilder();
		sb.append("select el.id, el.number, coalesce(to_char(el.createdate,'dd.MM.yyyy'),'') as createDate, coalesce(to_char(el.exportDate,'dd.MM.yyyy'),'') as exportDate" +
				", coalesce(to_char(el.annuldate,'dd.MM.yyyy'),'') as annulDate from electronicdisabilitydocumentnumber el where ");
		if (aDisabilityDocumentId!=null&&aDisabilityDocumentId>0) {
			sb.append(" el.disabilitydocument_id=").append(aDisabilityDocumentId);
		} else if (aNumber!=null&&!aNumber.trim().equals("")){
			sb.append(" el.number = '").append(aNumber).append("'");
		}
		log.info(sb);
		Collection<WebQueryResult> list = service.executeNativeSql(sb.toString());
		if (list.size()>0) {
			WebQueryResult wqr = list.iterator().next();
			return wqr.get1()+"#"+wqr.get2()+"#"+wqr.get3()+"#"+wqr.get4()+"#"+wqr.get5();
		} else {
			return null;
		}

	}
	/**
	 * Получение списка номеров ЭЛН
	 * @param aCount - количество запрашиваемых номеров
	 * @param aRequest
	 * @return
	 * @throws NamingException
	 */
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

	/**
	 * Просмотр либо импорт ЭЛН с ФСС
	 * @param aDisabilityDocumentNumber - номер документа
	 * @param aPatientId - ИД пациента
	 * @param aMethod - метод(просмотреть или импортировать)
	 * @param aRequest
	 * @return
	 * @throws NamingException
	 */
	public String importDisabilityDocument(String aDisabilityDocumentNumber, Long aPatientId, String aMethod, HttpServletRequest aRequest) throws NamingException {
		String snils = getSnils(aPatientId,aRequest);
		if (snils.length()>0) {
			IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
			return service.importDisabilityDocument(aDisabilityDocumentNumber,snils,aPatientId, aMethod);
		}
		return "У пациента не указан СНИЛС!";

	}

	//Milamesher 0308 - отметить аннулирование
	public String setAnnulDisabilityDocument(Long aDocumentId,String aAnnulText,String aAnnulCode,HttpServletRequest aRequest) throws NamingException {
		String ret = null;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String sql = "SELECT number from electronicdisabilitydocumentnumber WHERE disabilitydocument_id='" + aDocumentId + "'";
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (list.size() > 0) {
			IDisabilityService disService = Injection.find(aRequest).getService(IDisabilityService.class);
			String number =  list.iterator().next().get1().toString() ;
			String snils = null;
			list = service.executeNativeSql("select p.snils from patient p\n" +
					" left join disabilitydocument d on d.patient_id=p.id\n" +
					" where d.id='" + aDocumentId+ "'");
			if (list.size() > 0) {
				snils=list.iterator().next().get1().toString();
			}
			ret = disService.annulDisabilityDocument(Long.valueOf(number),aAnnulCode,aAnnulText,snils);
			if (ret!=null&&!ret.equals("")) { //Если сервис успешно аннулировал запись
				service.executeUpdateNativeSql("UPDATE electronicdisabilitydocumentnumber SET annuldate=current_date, comment='" + aAnnulText + "', annulreason_id=(SELECT id FROM vocannulreason WHERE code='" + aAnnulCode + "') WHERE disabilitydocument_id='" + aDocumentId + "'");
				service.executeUpdateNativeSql("UPDATE disabilitydocument SET noactuality='1', status_id=(select id from VocDisabilityStatus where code='1_ELN')  WHERE id='" + aDocumentId+ "'");
			}
		}
		else {
			ret= "Такого электронного ЛН нет!";
		}
		return  ret;
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
	public String getFreeNumberForDisabilityDocument(HttpServletRequest aRequest) throws NamingException, ParseException {
		return getFreeNumberForDisabilityDocumentReloaded(0,aRequest);
	}
	/**
	 * Возвращаем любой свободный номер больничного листа, или номер, резерв которого закончился (прошел час с момента резервирования)
	 * @param aRequest
	 * @return
	 * @throws NamingException
	 * @throws ParseException
	 */
	public String getFreeNumberForDisabilityDocumentReloaded(int aCount, HttpServletRequest aRequest) throws NamingException, ParseException {
		if (aCount>2) { //Если за 3 раза не удалось вернуть номер, выходим
			return "Произошла непредвиденная ошибка, обратитесь к разработчикам";
		}
		aCount++;
		IWebQueryService service = Injection.find(aRequest) .getService(IWebQueryService.class) ;
		String ret = "";
		Collection<WebQueryResult> list = service.executeNativeSql("select number as f1,to_char(reservedate,'dd.MM.yyyy') as f2_date, cast(reservetime as varchar(5)) as f3_time from ElectronicDisabilityDocumentNumber where disabilitydocument_id is null ");
		boolean needGetNewNumber = true;
		if (!list.isEmpty()) {
			Date currentDate = new Date();
			Calendar cal = new GregorianCalendar();

			for (WebQueryResult r: list) {
				if (r.get2()==null||r.get2().toString().trim().equals("")) {
					ret = r.get1().toString();
					needGetNewNumber=false;
					break;
				} else {
					Date reserveDate = DateConverter.createDateTime(r.get2().toString(),r.get3().toString());
					cal.setTime(reserveDate);
					cal.add(Calendar.HOUR,1);
					if (currentDate.getTime()>cal.getTime().getTime()) { //Если за 1 час больничный лист не оформили, забераем его себе.
					ret = r.get1().toString();
					needGetNewNumber=false;
					break;
					}
				}
			}
			if (ret!=null&&!ret.equals("")) {
				service.executeUpdateNativeSql("update ElectronicDisabilityDocumentNumber set reservedate = current_date, reservetime = current_time where number ='"+ret+"' ");
			}
		}
		if (needGetNewNumber) {
			log.warn("Не найдено свободных номеров ЭЛН, запрашиваем один номер");
			getLNNumberRange(Long.valueOf(1),aRequest);
			return getFreeNumberForDisabilityDocumentReloaded(aCount,aRequest);
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
	private String getDateGoToWork(Long aDisDocument, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest) .getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql("select max(dateto)+1 from disabilityrecord where disabilitydocument_id ="+aDisDocument,1) ;
		if (l.isEmpty()) {
			return null ;
		} else {
			WebQueryResult wqr = l.iterator().next() ;
			return wqr.get1().toString();
		}
	}
	public String closeDisabilityDocument(Long aDocId, Long aReasonId,String aSeries,String aNumber,String aOtherCloseDate,HttpServletRequest aRequest) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
		String dateGoToWork = getDateGoToWork(aDocId,aRequest);
		return service.closeDisabilityDocument(aDocId, aReasonId,aSeries,aNumber,aOtherCloseDate,dateGoToWork) ;
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
	public String getSnils(Long aPatinetId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String snils="";
		Collection<WebQueryResult> list = service.executeNativeSql("select snils from patient where id="+aPatinetId);
		if (list.size() > 0) {
			snils=list.iterator().next().get1().toString();
		}

		String str[];
		str = snils.split("-");
		snils = str[0] + str[1] + str[2];
		str = snils.split(" ");
		snils = str[0] + str[1];
		return snils;
	}

}