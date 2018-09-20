package ru.ecom.mis.web.dwr.disability;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.IApiService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.disability.ExportFSSLog;
import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateConverter;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import static ru.ecom.api.util.ApiUtil.cretePostRequest;

/**
 * Сервис для работы с нетрудоспособностью
 * @author stkacheva
 *
 */
public class DisabilityServiceJs {
	public static Logger log = Logger.getLogger(DisabilityServiceJs.class);


	public String unattachEln(Long disabilityDocumentId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

		Collection<WebQueryResult> list = service.executeNativeSql("select exportdate " +
				"from electronicdisabilitydocumentnumber where disabilitydocument_id="+disabilityDocumentId);
		if (list.size()>0) {
			WebQueryResult wqr = list.iterator().next();
			if(wqr.get1()!=null && !wqr.get1().toString().equals("")){
				return "Документ уже экспортирован! Невозможно его отвязать";
			}else {

				service.executeUpdateNativeSql("update electronicdisabilitydocumentnumber " +
						"set disabilitydocument_id=null " +
						"where disabilitydocument_id = "+disabilityDocumentId);

				service.executeUpdateNativeSql("update disabilitydocument " +
						"set number='0'" +
						"where id = "+disabilityDocumentId);
				return "Номер отвязан";
			}
		}
		return "Что-то пошло не так. Крайне высока вероятность того, что это не ЭЛН";
	}

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

	@Deprecated
	public String exportDisabilityDocument (Long aDocumentId, HttpServletRequest aRequest) throws NamingException {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
		String ret = service.exportDisabilityDocument(aDocumentId);
		return ret;
	}

	public String exportDisabilityDoc(String aDocumentId, HttpServletRequest aRequest)
			throws NamingException, SQLException, JSONException {

		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

		String sql1 = "select\n" +
				"dd.issuedate as ISSUEDATE,\n" +
				"dd.id as DDID,\n" +
				"dd.patient_id as DD_PAT,\n" +
				"dc.patient_id as DC_PAT,\n" +
				"p.snils as SNILS,\n" +
				"p.lastname as SURNAME,\n" +
				"p.firstname as NAME,\n" +
				"p.middlename as PATRONIMIC\n" +
				",case when (dc.placementservice is not null or dc.placementservice ='1') then '1' else '0' end as BOZ_FLAG\n" +
				",dd.job as LPU_EMPLOYER\n" +
				",case when (dd.workcombotype_id is null) then '1' else '0' end as LPU_EMPL_FLAG\n" +
				",dd.number as LN_CODE\n" +
				",case when dd.pervelnnumber is not null then dd.pervelnnumber else (select dd2.number from disabilitydocument dd2 where dd2.id = dd.prevdocument_id) end as PREV_LN\n" +
				",case when (vddp.code ='2') then '0' else '1' end as PRIMARY_FLAG\n" +
				",case when dd.elnduplicate is not null or dd.elnduplicate = '1' then '1' else case when (select count(a.id) from disabilitydocument a where a.duplicate_id=dd.id) >0 then '1' else '0'end  end as DUPLICATE_FLAG\n" +
				",dd.issuedate as LN_DATE\n" +
				",case when dd.anotherlpu_id is not null then dd.anotherlpuname else lpu.name end as LPU_NAME\n" +
				",case when dd.anotherlpu_id is not null then dd.anotherlpuaddress else lpu.printaddress end as LPU_ADDRESS\n" +
				",case when dd.anotherlpu_id is not null then dd.anotherlpuogrn else ''||lpu.ogrn end as LPU_OGRN\n" +
				",p.birthday as BIRTHDAY\n" +
				",case when sex.omccode = '1' then '0' else '1' end as GENDER\n" +
				",vdr.codef as REASON1\n" +
				",vdr2.code as REASON2\n" +
				",vdr3.code as REASON3\n" +
				",mkb.code as DIAGNOS\n" +
				",dd.mainworkdocumentnumber as PARENT_CODE\n" +
				",dd.sanatoriumdatefrom as DATE1\n" +
				",dd.sanatoriumdateto as DATE2\n" +
				",dd.sanatoriumticketnumber as VOUCHER_NO\n" +
				",dd.sanatoriumogrn as VOUCHER_OGRN\n" +
				",case when p1.id is not null and p1.id!=p.id then to_char(p1.birthday,'yyyy-MM-dd') else to_char(p12.birthday,'yyyy-MM-dd') end as SERV1_AGE\n" +
				",case when p1.id is not null and p1.id!=p.id then vkr1.code else vkr1.oppositeRoleCode end as SERV1_RELATION_CODE\n" +
				",case when p1.id is not null and p1.id!=p.id then p1.lastname||' '||p1.firstname||' '||p1.middlename else p12.lastname||' '||p12.firstname||' '||p12.middlename end as SERV1_FIO\n" +
				",case when p2.id is not null and p2.id!=p.id then to_char(p2.birthday,'yyyy-MM-dd') else to_char(p22.birthday,'yyyy-MM-dd') end as SERV2_AGE\n" +
				",case when p2.id is not null and p2.id!=p.id then vkr2.code else vkr2.oppositeRoleCode end as SERV2_RELATION_CODE\n" +
				",case when p2.id is not null and p2.id!=p.id then p2.lastname||' '||p2.firstname||' '||p2.middlename else p22.lastname||' '||p22.firstname||' '||p22.middlename end as SERV2_FIO\n" +
				",case when dc.earlypregnancyregistration is null then 'null' else case when dc.earlypregnancyregistration = '0' then '0' else '1' end end as PREGN12W_FLAG\n" +
				",dd.hospitalizedfrom as HOSPITAL_DT1\n" +
				",dd.hospitalizedto as HOSPITAL_DT2\n" +
				",vddcr.name as CLOSE_REASON\n" +
				",mss.assignmentdate as MSE_DT1\n" +
				",mss.registrationdate as MSE_DT2\n" +
				",mss.examinationdate as MSE_DT3\n" +
				",vi.code as MSE_INVALID_GROUP\n" +
				",dd.status_id as LN_STATE\n" +
				",rvr.datefrom as HOSPITAL_BREACH_DT\n" +
				",vrvr.codef as HOSPITAL_BREACH_CODE\n" +
				",coalesce(vddcr.codef,'') as MSE_RESULT\n" +
				",dd.otherclosedate as other_state_dt\n" +
				",dd3.number as NEXT_LN_CODE\n" +
				",Case when dd.isClose = '1' then '1' else '0' end as IS_CLOSE\n" +
				",dd.lnhash as LN_HASH " +
				"from disabilitydocument dd\n" +
				"left join vocdisabilitydocumentclosereason vddcr on vddcr.id = dd.closereason_id\n" +
				"left join disabilitydocument dd3 on dd3.prevdocument_id=dd.id\n" +
				"left join regimeviolationrecord rvr on rvr.disabilitydocument_id = dd.id\n" +
				"left join vocregimeviolationtype vrvr on vrvr.id = rvr.regimeviolationtype_id\n" +
				"left join disabilitycase dc on dc.id=dd.disabilitycase_id\n" +
				"left join patient p on p.id=dc.patient_id\n" +
				"left join vocdisabilitydocumentprimarity vddp on vddp.id=dd.primarity_id\n" +
				"left join vocsex sex on sex.id=p.sex_id\n" +
				"left join vocdisabilityreason vdr on vdr.id=dd.disabilityreason_id\n" +
				"left join vocdisabilityreason2 vdr2 on vdr2.id=dd.disabilityreason2_id\n" +
				"left join vocdisabilityreason vdr3 on vdr3.id=dd.disabilityreasonchange_id\n" +
				"left join vocidc10 mkb on mkb.id=dd.idc10final_id\n" +
				"left join kinsman k1 on k1.id=dc.nursingperson1_id\n" +
				"left join vockinsmanrole vkr1 on vkr1.id=k1.kinsmanrole_id\n" +
				"left join patient p1 on p1.id=k1.kinsman_id\n" +
				"left join patient p12 on p12.id=k1.person_id\n" +
				"left join kinsman k2 on k2.id=dc.nursingperson2_id\n" +
				"left join vockinsmanrole vkr2 on vkr2.id=k2.kinsmanrole_id\n" +
				"left join patient p2 on p2.id=k2.kinsman_id\n" +
				"left join patient p22 on p22.id=k2.person_id\n" +
				"left join medsoccommission mss on mss.disabilitydocument_id=dd.id\n" +
				"left join vocinvalidity vi on vi.id=mss.invalidity_id\n" +
				"left join mislpu lpu on lpu.id=1\n" +
				"left join mislpu anlpu on anlpu.id = dd.anotherlpu_id\n" +
				"where\n" +
				"p.snils is not null and p.snils != ''\n" +
				"and dd.id ="+aDocumentId;

		String sql2 = "select\n" +
				"dd.id as DDID\n" +
				",disrec.datefrom as TREAT_DT1 \n" +
				",disrec.dateto as TREAT_DT2\n" +
				",case when disrec.docrole is null then vwf.name else disrec.docrole end as TREAT_DOCTOR_ROLE\n" +
				",case when disrec.docname is null then docname.lastname ||' '|| docname.firstname ||' '|| docname.middlename else disrec.docname end as TREAT_DOCTOR \n" +
				",case when disrec.vkrole is null then vwf2.name else disrec.vkrole end as TREAT_CHAIRMAN_ROLE\n" +
				",case when disrec.vkname is null then vkname.lastname ||' '|| vkname.firstname ||' '|| vkname.middlename else disrec.vkname end as TREAT_CHAIRMAN\n" +
				",disrec.isexport as isexport\n" +
				",dsdoc.signaturevalue as signdoc\n" +
				",dsdoc.certificate as certdoc\n" +
				",dsdoc.digestvalue as digdoc\n" +
				",dsdoc.counter as counterdoc\n" +
				",dsvk.signaturevalue as signvk\n" +
				",dsvk.certificate as certvk\n" +
				",dsvk.digestvalue as digvk\n" +
				",dsvk.counter as countervk\n" +
				"from disabilitydocument dd\n" +
				"left join disabilitycase dc on dc.id=dd.disabilitycase_id \n" +
				"left join patient p on p.id=dc.patient_id left join disabilityrecord disrec on disrec.disabilitydocument_id = dd.id\n" +
				"left join workfunction wf on wf.id = disrec.workfunction_id \n" +
				"left join worker w on w.id = wf.worker_id\n" +
				"left join patient docname on docname.id = w.person_id \n" +
				"left join VocWorkFunction vwf on vwf.id = wf.workFunction_id\n" +
				"left join workfunction wf2 on wf2.id = disrec.workfunctionadd_id\n" +
				"left join worker w2 on w2.id = wf2.worker_id\n" +
				"left join patient vkname on vkname.id = w2.person_id\n" +
				"left join VocWorkFunction vwf2 on vwf2.id = wf2.workFunction_id\n" +
				"left join disabilitysign dsvk on dsvk.externalid = disrec.id and dsvk.noactual = '0' and dsvk.code = 'vk'\n" +
				"left join disabilitysign dsdoc on dsdoc.externalid = disrec.id and dsdoc.noactual = '0' and dsdoc.code = 'doc'\n" +
				"where dd.id = "+aDocumentId+"\n" +
				"order by treat_dt1 asc";

		String close ="select \n" +
				"certificate as certclose,\n" +
				"digestvalue as digclose,\n" +
				"counter as counterclose,\n" +
				"signaturevalue as signclose,\n" +
				"(dr.dateto+1) as returndt\n" +
				"from disabilitysign ds \n" +
				"left join disabilityrecord dr on dr.disabilitydocument_id = ds.disabilitydocumentid_id\n" +
				"where \n" +
				"ds.id = (select max(id) from  disabilitysign where disabilitydocumentid_id ="+aDocumentId+" and noactual=false and code='close')\n" +
				"and dr.dateto =  (select max(dateto) from disabilityrecord  where disabilitydocument_id = "+aDocumentId+")";

		JSONObject body  = new JSONObject(service.executeSqlGetJsonObject(sql1));
		JSONArray arr = new JSONArray(service.executeSqlGetJson(sql2,10));

		body.put("treats",arr);
		arr = new JSONArray(service.executeSqlGetJson(close, 10));
		body.put("close", arr);

		int code=0;
		JsonParser parser =new JsonParser();
		JsonObject jparsr = parser.parse(body.toString()).getAsJsonObject();
		JsonArray treats = jparsr.getAsJsonArray("treats");

		String isclose="0";
		if(jparsr.has("is_close")){
			isclose =jparsr.get("is_close").toString();
		}

		for (JsonElement el : treats) {
			JsonObject jtreat = el.getAsJsonObject();
			if(!jtreat.has("signdoc")){
				code=1;
			}
			if(jtreat.has("treat_chairman_role") && !jtreat.has("signvk")){
				code=2;
			}
		}

		JsonArray closes = jparsr.getAsJsonArray("close");
		if(isclose.equals("1") && closes.size()==0){
			code=3;
		}
		String json="";

		if(code==0){

			IDisabilityService service1 = Injection.find(aRequest).getService(IDisabilityService.class);
			String endpoint = service1.getSoftConfigValue("FSS_PROXY_SERVICE", "null");

			json = cretePostRequest(endpoint, "api/export/exportDisabilityDocument", body.toString(), "application/json");
			saveLog(json,aRequest);
		}

		if(code==1){
			json= new JSONObject()
					.put("code","1")
					.put("error","Не найдена подпись врача в периоде").toString();
		}
		if(code==2){
			json= new JSONObject()
					.put("code","2")
					.put("error","Не найдена подпись вк в периоде").toString();
		}
		if(code==3){
			json= new JSONObject()
					.put("code","3")
					.put("error","Не найдена подпись врача в закрытии").toString();
		}
		return json;
	}

	private void saveLog(String json, HttpServletRequest aRequest) throws NamingException {

		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		JsonParser parser =new JsonParser();
		JsonObject obj = parser.parse(json).getAsJsonObject();
		String message="";

		String elnumber = obj.get("lncode").getAsString();
		String disdocId=null;
		Collection<WebQueryResult> list = service.executeNativeSql("select dd.id from disabilitydocument dd where dd.number = '"+elnumber+"'");
		if (!list.isEmpty()) {
			for (WebQueryResult wqr : list) {
				disdocId = wqr.get1().toString();
			}
		}
		ExportFSSLog exportFSSLog  =new ExportFSSLog();

		exportFSSLog.setDisabilityDocument(Long.valueOf((disdocId)));
		exportFSSLog.setDisabilityNumber(elnumber);
		exportFSSLog.setStatus(obj.get("status").getAsString());
		exportFSSLog.setRequest_id(obj.get("requestId").getAsString());

		message+=obj.get("message").getAsString();
		if(obj.has("errors")){
			JsonArray errors = obj.getAsJsonArray("errors");
			for (JsonElement err : errors) {
				JsonObject error = err.getAsJsonObject();
				message+=error.get("errmess").getAsString();
			}
		}
		exportFSSLog.setRequestDate(new java.sql.Date(System.currentTimeMillis()));
		exportFSSLog.setRequestTime(new java.sql.Time(System.currentTimeMillis()));
		exportFSSLog.setResult(message);
		exportFSSLog.setRequestType("prParseFilelnlpu");

		IApiService persist = Injection.find(aRequest).getService(IApiService.class);
		persist.persistEntity(exportFSSLog);

		if(obj.get("status").getAsString().equals("1")){
			updateInformationELN(disdocId, obj.get("hash").getAsString(), obj.get("lnstate").getAsString(), aRequest);
		}
	}

	private void updateInformationELN(String aDocumentId, String hash, String code, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service =Injection.find(aRequest).getService(IWebQueryService.class);
		service.executeUpdateNativeSql("update disabilitydocument set lnhash = '"+hash+"' where id = "+aDocumentId);
		service.executeUpdateNativeSql("update disabilitysign set export = true where disabilitydocumentid_id = "+aDocumentId);
		service.executeUpdateNativeSql("update disabilityrecord set isexport = true where disabilitydocument_id = "+aDocumentId);
		String id = "";
		Collection<WebQueryResult> list = service.executeNativeSql("select id from vocdisabilitydocumentexportstatus where code='"+code+"'");
		if (!list.isEmpty()) {
			for (WebQueryResult wqr : list) {
				id = wqr.get1().toString();
			}
		}
		service.executeUpdateNativeSql("update electronicdisabilitydocumentnumber set exportdate='"+new java.sql.Date(System.currentTimeMillis())+"'," +
				"status_id="+id+", lasthash='"+hash+"'," +
				"exporttime='"+new java.sql.Time(System.currentTimeMillis())+"' where disabilitydocument_id = "+aDocumentId);


	}

	@Deprecated
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
			list = service.executeNativeSql("select p.snils from patient p \n" +
					"left join disabilitycase dc on dc.patient_id = p.id \n" +
					"left join disabilitydocument d on dc.id=d.disabilitycase_id \n" +
					"where d.id=" + aDocumentId);
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
		Collection<WebQueryResult> l = service.executeNativeSql("select to_char(max(dateto)+1,'yyyy-MM-dd') from disabilityrecord where disabilitydocument_id ="+aDisDocument,1) ;
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

		if(!snils.equals("")){
			String str[];
			str = snils.split("-");
			snils = str[0] + str[1] + str[2];
			str = snils.split(" ");
			snils = str[0] + str[1];
		}
		return snils;
	}

}