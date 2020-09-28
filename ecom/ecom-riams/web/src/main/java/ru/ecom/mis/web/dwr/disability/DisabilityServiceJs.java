package ru.ecom.mis.web.dwr.disability;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import static ru.ecom.api.util.ApiUtil.cretePostRequest;
import static ru.ecom.api.util.ApiUtil.creteGetRequest;

/**
 * Сервис для работы с нетрудоспособностью
 */
public class DisabilityServiceJs {

    private static final Logger LOG = Logger.getLogger(DisabilityServiceJs.class);

    /**
     * Открепить ЭЛН.
     *
     * @param disabilityDocumentId DisabilityDocument.id;
     * @param aRequest             HttpServletRequest
     * @return String with status of process
     * @throws NamingException
     */
    public String unattachEln(Long disabilityDocumentId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        Collection<WebQueryResult> list = service.executeNativeSql("select exportdate " +
                "from electronicdisabilitydocumentnumber where disabilitydocument_id=" + disabilityDocumentId);
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            if (wqr.get1() != null && !wqr.get1().toString().equals("")) {
                return "Документ уже экспортирован! Невозможно его отвязать";
            } else {

                service.executeUpdateNativeSql("update electronicdisabilitydocumentnumber " +
                        "set disabilitydocument_id=null " +
                        "where disabilitydocument_id = " + disabilityDocumentId);

                service.executeUpdateNativeSql("update disabilitydocument " +
                        "set number='0'" +
                        "where id = " + disabilityDocumentId);
                return "Номер отвязан";
            }
        }
        return "Что-то пошло не так. Крайне высока вероятность того, что это не ЭЛН";
    }

    /**
     * Получить номер ЭЛН по DisabilityDocument.id и DisabilityDocument.number.
     *
     * @param aDisabilityDocumentId DisabilityDocument.id
     * @param aNumber               DisabilityDocument.number
     * @param aRequest              HttpServletRequest
     * @return Long document info
     */
    public Long getElectronicDisabilityNumber(Long aDisabilityDocumentId, String aNumber, HttpServletRequest aRequest)
            throws NamingException {

        String documentInfo = getElectronicDisabilityDocumentInfo(aDisabilityDocumentId, aNumber, aRequest);
        if (documentInfo != null) {
            return Long.valueOf(documentInfo.split("#")[0]);
        }
        return null;
    }

    /**
     * Получить информацию об ЭЛН.
     *
     * @param aDisabilityDocumentId DisabilityDocument.id
     * @param aNumber               DisabilityDocumentId.number
     * @param aRequest              HttpServletRequest
     * @return String with information
     * @throws NamingException
     */
    public String getElectronicDisabilityDocumentInfo(Long aDisabilityDocumentId, String aNumber, HttpServletRequest aRequest)
            throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sb = new StringBuilder();
        sb.append("select el.id, el.number, coalesce(to_char(el.createdate,'dd.MM.yyyy'),'') as createDate," +
                " coalesce(to_char(el.exportDate,'dd.MM.yyyy'),'') as exportDate" +
                ", coalesce(to_char(el.annuldate,'dd.MM.yyyy'),'') as annulDate from electronicdisabilitydocumentnumber el where ");

        if (aDisabilityDocumentId != null && aDisabilityDocumentId > 0) {
            sb.append(" el.disabilitydocument_id=").append(aDisabilityDocumentId);
        } else if (aNumber != null && !aNumber.trim().equals("")) {
            sb.append(" el.number = '").append(aNumber).append("'");
        }
        LOG.info(sb);
        Collection<WebQueryResult> list = service.executeNativeSql(sb.toString());
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            return wqr.get1() + "#" + wqr.get2() + "#" + wqr.get3() + "#" + wqr.get4() + "#" + wqr.get5();
        } else {
            return null;
        }
    }

    /**
     * Получение списка номеров ЭЛН.
     *
     * @param aCount   Количество запрашиваемых номеров
     * @param aRequest HttpServletRequest
     * @return String status
     * @throws NamingException
     */
    public String getLNNumberRange(Long aCount, HttpServletRequest aRequest) throws NamingException {
        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
        return service.getLNNumberRange(aCount);
    }

    /**
     * Экспорт ЛН.
     *
     * @param aDocumentId DisabilityDocument.id
     * @param aRequest    HttpServletRequest
     * @return String status
     * @throws NamingException
     */
    @Deprecated
    public String exportDisabilityDocument(Long aDocumentId, HttpServletRequest aRequest) throws NamingException {
        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
        return service.exportDisabilityDocument(aDocumentId);
    }

    /**
     * Получить ЭЛН в JSON.
     *
     * @param aDocumentId DisabilityDocument.id
     * @param aRequest    HttpServletRequest
     * @return JSON in String
     * @throws NamingException
     * @throws SQLException
     * @throws JSONException
     */
    public String exportDisabilityDoc(String aDocumentId, HttpServletRequest aRequest) throws NamingException, SQLException, JSONException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        String sql1 = "select" +
                " dd.issuedate as ISSUEDATE," +
                " dd.id as DDID," +
                " dd.patient_id as DD_PAT," +
                " dc.patient_id as DC_PAT," +
                " p.snils as SNILS," +
                " p.lastname as SURNAME," +
                " p.firstname as NAME," +
                " p.middlename as PATRONIMIC" +
                /*
                Если отпуск по бер и родам и продолжение
                 и текущий ЛН в нашем лпу
                 и первичный в нашем лпу, то 0 (не нужно выгужать признак "Состоит на учете в службе занятости"
                * */
                " ,case when dc.placementservice is null or dc.placementservice ='0'" +
                " or (vdr.code='9' and vddp.code='2' and anlpu.id is null and anlpuprev.id is null )" +
                " then '0' else '1' end as BOZ_FLAG" +
                " ,dd.job as LPU_EMPLOYER" +
                " ,case when (dd.workcombotype_id is null) then '1' else '0' end as LPU_EMPL_FLAG" +
                " ,dd.number as LN_CODE" +
                " ,case when dd.pervelnnumber is not null then dd.pervelnnumber else (select dd2.number from disabilitydocument dd2 where dd2.id = dd.prevdocument_id) end as PREV_LN" +
                " ,case when (vddp.code ='2') then '0' else '1' end as PRIMARY_FLAG" +
                " ,case when dd.elnduplicate is not null or dd.elnduplicate = '1' then '1' else case when (select count(a.id) from disabilitydocument a where a.duplicate_id=dd.id) >0 then '1' else '0'end  end as DUPLICATE_FLAG" +
                " ,dd.issuedate as LN_DATE" +
                " ,case when dd.anotherlpu_id is not null then dd.anotherlpuname else lpu.name end as LPU_NAME" +
                " ,case when dd.anotherlpu_id is not null then dd.anotherlpuaddress else lpu.printaddress end as LPU_ADDRESS" +
                " ,case when dd.anotherlpu_id is not null then dd.anotherlpuogrn else ''||lpu.ogrn end as LPU_OGRN" +
                " ,p.birthday as BIRTHDAY" +
                " ,case when sex.omccode = '1' then '0' else '1' end as GENDER" +
                " ,vdr.codef as REASON1" +
                " ,vdr2.code as REASON2" +
                " ,vdr3.codef as REASON3" +
                " ,mkb.code as DIAGNOS" +
                " ,dd.mainworkdocumentnumber as PARENT_CODE" +
                " ,dd.sanatoriumdatefrom as DATE1" +
                " ,dd.sanatoriumdateto as DATE2" +
                " ,dd.sanatoriumticketnumber as VOUCHER_NO" +
                " ,dd.sanatoriumogrn as VOUCHER_OGRN" +
                " ,case when p1.id is not null and p1.id!=p.id then to_char(p1.birthday,'yyyy-MM-dd') else to_char(p12.birthday,'yyyy-MM-dd') end as SERV1_AGE" +
                " ,case when p1.id is not null and p1.id!=p.id then vkr1.code else vkr1.oppositeRoleCode end as SERV1_RELATION_CODE" +
                " ,case when p1.id is not null and p1.id!=p.id then p1.lastname||' '||p1.firstname||' '||p1.middlename else p12.lastname||' '||p12.firstname||' '||p12.middlename end as SERV1_FIO" +
                " ,case when p2.id is not null and p2.id!=p.id then to_char(p2.birthday,'yyyy-MM-dd') else to_char(p22.birthday,'yyyy-MM-dd') end as SERV2_AGE" +
                " ,case when p2.id is not null and p2.id!=p.id then vkr2.code else vkr2.oppositeRoleCode end as SERV2_RELATION_CODE" +
                " ,case when p2.id is not null and p2.id!=p.id then p2.lastname||' '||p2.firstname||' '||p2.middlename else p22.lastname||' '||p22.firstname||' '||p22.middlename end as SERV2_FIO" +
                " ,case when vdr.codef= '05'" +
                " then case when dc.earlypregnancyregistration='1' then '1' else '0' end" +
                " else 'null' end as PREGN12W_FLAG" +
                " ,dd.hospitalizedfrom as HOSPITAL_DT1" +
                " ,dd.hospitalizedto as HOSPITAL_DT2" +
                " ,vddcr.name as CLOSE_REASON" +
                " ,mss.assignmentdate as MSE_DT1" +
                " ,mss.registrationdate as MSE_DT2" +
                " ,mss.examinationdate as MSE_DT3" +
                " ,vi.code as MSE_INVALID_GROUP" +
                " ,dd.status_id as LN_STATE" +
                " ,rvr.datefrom as HOSPITAL_BREACH_DT" +
                " ,vrvr.codef as HOSPITAL_BREACH_CODE" +
                " ,coalesce(vddcr.codef,'') as MSE_RESULT" +
                " ,dd.otherclosedate as other_state_dt" +
                " ,dd3.number as NEXT_LN_CODE" +
                " ,Case when dd.isClose = '1' then '1' else '0' end as IS_CLOSE" +
                " ,dd.lnhash as LN_HASH " +
                " from disabilitydocument dd" +
                " left join vocdisabilitydocumentclosereason vddcr on vddcr.id = dd.closereason_id" +
                " left join disabilitydocument dd3 on dd3.prevdocument_id=dd.id" +
                " left join disabilitydocument dd4 on dd4.id=dd.prevdocument_id" +
                " left join regimeviolationrecord rvr on rvr.disabilitydocument_id = dd.id" +
                " left join vocregimeviolationtype vrvr on vrvr.id = rvr.regimeviolationtype_id" +
                " left join disabilitycase dc on dc.id=dd.disabilitycase_id" +
                " left join patient p on p.id=dc.patient_id" +
                " left join vocdisabilitydocumentprimarity vddp on vddp.id=dd.primarity_id" +
                " left join vocsex sex on sex.id=p.sex_id" +
                " left join vocdisabilityreason vdr on vdr.id=dd.disabilityreason_id" +
                " left join vocdisabilityreason2 vdr2 on vdr2.id=dd.disabilityreason2_id" +
                " left join vocdisabilityreason vdr3 on vdr3.id=dd.disabilityreasonchange_id" +
                " left join vocidc10 mkb on mkb.id=dd.idc10final_id" +
                " left join kinsman k1 on k1.id=dc.nursingperson1_id" +
                " left join vockinsmanrole vkr1 on vkr1.id=k1.kinsmanrole_id" +
                " left join patient p1 on p1.id=k1.kinsman_id" +
                " left join patient p12 on p12.id=k1.person_id" +
                " left join kinsman k2 on k2.id=dc.nursingperson2_id" +
                " left join vockinsmanrole vkr2 on vkr2.id=k2.kinsmanrole_id" +
                " left join patient p2 on p2.id=k2.kinsman_id" +
                " left join patient p22 on p22.id=k2.person_id" +
                " left join medsoccommission mss on mss.disabilitydocument_id=dd.id" +
                " left join vocinvalidity vi on vi.id=mss.invalidity_id" +
                " left join mislpu lpu on lpu.id=1" +
                " left join mislpu anlpu on anlpu.id = dd.anotherlpu_id" +
                " left join mislpu anlpuprev on anlpuprev.id = dd4.anotherlpu_id" +
                " where" +
                " p.snils is not null and p.snils != ''" +
                " and dd.id =" + aDocumentId;

        String sql2 = "select" +
                " dd.id as DDID" +
                " ,disrec.datefrom as TREAT_DT1 " +
                " ,disrec.dateto as TREAT_DT2" +
                " ,case when disrec.docrole is null then vwf.name else disrec.docrole end as TREAT_DOCTOR_ROLE" +
                " ,case when disrec.docname is null then docname.lastname ||' '|| docname.firstname ||' '|| docname.middlename else disrec.docname end as TREAT_DOCTOR " +
                " ,case when disrec.vkrole is null then vwf2.name else disrec.vkrole end as TREAT_CHAIRMAN_ROLE" +
                " ,case when disrec.vkname is null then vkname.lastname ||' '|| vkname.firstname ||' '|| vkname.middlename else disrec.vkname end as TREAT_CHAIRMAN" +
                " ,disrec.isexport as isexport" +
                " ,dsdoc.signaturevalue as signdoc" +
                " ,dsdoc.certificate as certdoc" +
                " ,dsdoc.digestvalue as digdoc" +
                " ,dsdoc.counter as counterdoc" +
                " ,dsvk.signaturevalue as signvk" +
                " ,dsvk.certificate as certvk" +
                " ,dsvk.digestvalue as digvk" +
                " ,dsvk.counter as countervk" +
                " ,dsvk.signatureType as typesignvk" +
                " ,dsdoc.signatureType as typesigndoc" +
                " from disabilitydocument dd" +
                " left join disabilitycase dc on dc.id=dd.disabilitycase_id " +
                " left join patient p on p.id=dc.patient_id left join disabilityrecord disrec on disrec.disabilitydocument_id = dd.id" +
                " left join workfunction wf on wf.id = disrec.workfunction_id " +
                " left join worker w on w.id = wf.worker_id" +
                " left join patient docname on docname.id = w.person_id " +
                " left join VocWorkFunction vwf on vwf.id = wf.workFunction_id" +
                " left join workfunction wf2 on wf2.id = disrec.workfunctionadd_id" +
                " left join worker w2 on w2.id = wf2.worker_id" +
                " left join patient vkname on vkname.id = w2.person_id" +
                " left join VocWorkFunction vwf2 on vwf2.id = wf2.workFunction_id" +
                " left join disabilitysign dsvk on dsvk.externalid = disrec.id and dsvk.noactual = '0' and dsvk.code = 'vk'" +
                " left join disabilitysign dsdoc on dsdoc.externalid = disrec.id and dsdoc.noactual = '0' and dsdoc.code = 'doc'" +
                " where dd.id = " + aDocumentId + "" +
                " order by treat_dt1 asc";

        String close = " select " +
                " certificate as certclose," +
                " digestvalue as digclose," +
                " counter as counterclose," +
                " signaturevalue as signclose," +
                " (dr.dateto+1) as returndt," +
                " signatureType as typesignclose" +
                " from disabilitysign ds " +
                " left join disabilityrecord dr on dr.disabilitydocument_id = ds.disabilitydocumentid_id" +
                " where ds.id = " +
                " (select max(id) from  disabilitysign where disabilitydocumentid_id =" + aDocumentId + " and noactual=false and code='close')" +
                " and dr.dateto = (select max(dateto) from disabilityrecord  where disabilitydocument_id = " + aDocumentId + ")";

        JSONObject body = new JSONObject(service.executeSqlGetJsonObject(sql1));
        JSONArray arr = new JSONArray(service.executeSqlGetJson(sql2, 10));

        body.put("treats", arr);
        arr = new JSONArray(service.executeSqlGetJson(close, 10));
        body.put("close", arr);

        int code = 0;
        JsonParser parser = new JsonParser();
        JsonObject jparsr = parser.parse(body.toString()).getAsJsonObject();
        JsonArray treats = jparsr.getAsJsonArray("treats");

        String isclose = "0";
        if (jparsr.has("is_close")) {
            isclose = jparsr.get("is_close").toString();
        }

        for (JsonElement el : treats) {
            JsonObject jtreat = el.getAsJsonObject();
            if (!jtreat.has("signdoc")) {
                code = 1;
            }
            if (jtreat.has("treat_chairman_role") && !jtreat.has("signvk")) {
                code = 2;
            }
        }

        JsonArray closes = jparsr.getAsJsonArray("close");
        if (isclose.equals("1") && closes.size() == 0) {
            code = 3;
        }
        String json = "";

        if (code == 0) {

            IDisabilityService service1 = Injection.find(aRequest).getService(IDisabilityService.class);
            String endpoint = service1.getSoftConfigValue("FSS_PROXY_SERVICE", "null");

            json = cretePostRequest(endpoint, "api/export/exportDisabilityDocument", body.toString(), "application/json");
            saveLog(json, aRequest);
        } else if (code == 1) {
            json = new JSONObject()
                    .put("code", "1")
                    .put("error", "Не найдена подпись врача в периоде").toString();
        } else if (code == 2) {
            json = new JSONObject()
                    .put("code", "2")
                    .put("error", "Не найдена подпись вк в периоде").toString();
        } else if (code == 3) {
            json = new JSONObject()
                    .put("code", "3")
                    .put("error", "Не найдена подпись врача в закрытии").toString();
        }

        return json;
    }

    /**
     * Сохранить лог экспорта.
     *
     * @param json     json
     * @param aRequest HttpServletRequest
     * @throws NamingException
     */
    private void saveLog(String json, HttpServletRequest aRequest) throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        StringBuilder message = new StringBuilder();

        String elnumber = obj.get("lncode").getAsString();

        Collection<WebQueryResult> list = service.executeNativeSql("select dd.id from disabilitydocument dd where dd.number = '" + elnumber + "'");
        if (!list.isEmpty() && list.size() == 1) {
            String disdocId = list.iterator().next().get1().toString();
            ExportFSSLog exportFSSLog = new ExportFSSLog();
            exportFSSLog.setDisabilityDocument(Long.valueOf((disdocId)));
            exportFSSLog.setDisabilityNumber(elnumber);
            exportFSSLog.setStatus(obj.get("status").getAsString());
            exportFSSLog.setRequest_id(obj.get("requestId").getAsString());

            message.append(obj.get("message").getAsString());
            if (obj.has("errors")) {
                JsonArray errors = obj.getAsJsonArray("errors");
                for (JsonElement err : errors) {
                    JsonObject error = err.getAsJsonObject();
                    message.append(error.get("errmess").getAsString());
                }
            }
            exportFSSLog.setRequestDate(new java.sql.Date(System.currentTimeMillis()));
            exportFSSLog.setRequestTime(new java.sql.Time(System.currentTimeMillis()));
            exportFSSLog.setResult(message.toString());
            exportFSSLog.setRequestType("prParseFilelnlpu");

            IApiService persist = Injection.find(aRequest).getService(IApiService.class);
            persist.persistEntity(exportFSSLog);

            if (obj.get("status").getAsString().equals("1")) {
                updateInformationELN(disdocId, obj.get("hash").getAsString(), obj.get("lnstate").getAsString(), aRequest);
            }
        } else {
            LOG.error("Не найден ЭЛН для логирования...");
        }
    }

    /**
     * Обновить информацию ЭЛН после экспорта.
     *
     * @param aDocumentId DisabilityDocument.id
     * @param hash        DisabilityDocument.lnhash
     * @param code        VocDisabilityDocumentExportStatus.code
     * @param aRequest    HttpServletRequest
     * @throws NamingException
     */
    private void updateInformationELN(String aDocumentId, String hash, String code, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update disabilitydocument set lnhash = '" + hash + "' where id = " + aDocumentId);
        service.executeUpdateNativeSql("update disabilitysign set export = true where disabilitydocumentid_id = " + aDocumentId);
        service.executeUpdateNativeSql("update disabilityrecord set isexport = true where disabilitydocument_id = " + aDocumentId);
        String id = "";
        Collection<WebQueryResult> list = service.executeNativeSql("select id from vocdisabilitydocumentexportstatus where code='" + code + "'");
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) {
                id = wqr.get1().toString();
            }
        }
        service.executeUpdateNativeSql("update electronicdisabilitydocumentnumber set exportdate='" + new java.sql.Date(System.currentTimeMillis()) + "'," +
                "status_id=" + id + ", lasthash='" + hash + "'," +
                "exporttime='" + new java.sql.Time(System.currentTimeMillis()) + "' where disabilitydocument_id = " + aDocumentId);
    }


    /**
     * Просмотр либо импорт ЭЛН с ФСС.
     *
     * @param aDisabilityDocumentNumber DisabilityDocument.number
     * @param aPatientId                Patient.id
     * @param aMethod                   метод(просмотреть или импортировать)
     * @param aRequest                  HttpServletRequest
     * @return String status
     * @throws NamingException
     */
    @Deprecated
    public String importDisabilityDocument(String aDisabilityDocumentNumber,
                                           Long aPatientId,
                                           String aMethod,
                                           HttpServletRequest aRequest) throws NamingException {
        String snils = getSnils(aPatientId, aRequest);
        if (snils.length() > 0) {
            IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
            return service.importDisabilityDocument(aDisabilityDocumentNumber, snils, aPatientId, aMethod);
        }
        return "У пациента не указан СНИЛС!";
    }

    /**
     * Отменить аннулирование.
     *
     * @param aDocumentId
     * @param aAnnulText
     * @param aAnnulCode
     * @param aRequest
     * @return
     * @throws NamingException
     */
    public String setAnnulDisabilityDocument(Long aDocumentId, String aAnnulText, String aAnnulCode, HttpServletRequest aRequest) throws NamingException {
        String ret;
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "SELECT eln.id as elnId, eln.number as numb, pat.snils as snils " +
                " from electronicdisabilitydocumentnumber eln " +
                " left join disabilitydocument dd on dd.id=eln.disabilitydocument_id" +
                " left join disabilitycase dc on dc.id=dd.disabilitycase_id" +
                " left join patient pat on pat.id=dc.patient_id" +
                " where eln.disabilitydocument_id=" + aDocumentId + " and eln.number = dd.number";
        Collection<WebQueryResult> list = service.executeNativeSql(sql);
        if (list.size()==1) {
            WebQueryResult wqr = list.iterator().next();
            IDisabilityService disService = Injection.find(aRequest).getService(IDisabilityService.class);
            String number = wqr.get2().toString();
            String snils = wqr.get3().toString();
            String elnId = wqr.get1().toString();
            if (snils == null || "".equals(snils)) return "У пациента не указан СНИЛС, аннулирование невозможно!";
            ret = disService.annulDisabilityDocument(Long.valueOf(number), aAnnulCode, aAnnulText, snils);
            if (ret != null && !ret.equals("")) { //Если сервис успешно аннулировал запись
                service.executeUpdateNativeSql("UPDATE electronicdisabilitydocumentnumber SET annuldate=current_date, comment='" + aAnnulText + "', annulreason_id=(SELECT id FROM vocannulreason WHERE code='" + aAnnulCode + "') WHERE id = " + elnId );
                service.executeUpdateNativeSql("UPDATE disabilitydocument SET noactuality='1', status_id=(select id from VocDisabilityStatus where code='1_ELN')  WHERE id="+ aDocumentId );
            }
        } else {
            ret = list.size()+": ЭЛН с таким номером не найдено, либо номер ЭЛН != номер документа";
        }
        return ret;
    }

    public String getExportJournalById(Long aDocumentId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        StringBuilder ret = new StringBuilder();
        String sql = "select result, to_char(requestdate,'dd.MM.yyyy') as f2_date, cast (requesttime as varchar(5)) as f3_time from exportfsslog where disabilitydocument='" + aDocumentId + "' order by requestdate desc , requesttime desc ";
        Collection<WebQueryResult> list = service.executeNativeSql(sql);
        if (!list.isEmpty()) {
            ret.append("<table class=\"tabview sel tableArrow\"><tr>");
            ret.append("<td>Результат</td><td>Дата отправки</td><td>Время отправки</td>");
            ret.append("</tr>");
            for (WebQueryResult r : list) {
                ret.append("<tr><td>").append(r.get1()).append("</td><td>").append(r.get2()).append("</td><td>").append(r.get3()).append("</td></tr>");
            }
            ret.append("</table>");
        }
        return ret.length() > 0 ? ret.toString() : null;
    }

    public String getFreeNumberForDisabilityDocument(HttpServletRequest aRequest) throws NamingException, ParseException {
        return getFreeNumberForDisabilityDocumentReloaded(0, aRequest);
    }

    /**
     * Возвращаем любой свободный номер больничного листа, или номер, резерв которого закончился
     * (прошел час с момента резервирования).
     *
     * @param aCount   количество попыток
     * @param aRequest HttpServletRequest
     * @return String status
     * @throws NamingException
     * @throws ParseException
     */
    private String getFreeNumberForDisabilityDocumentReloaded(int aCount, HttpServletRequest aRequest)
            throws NamingException, ParseException {

        if (aCount > 2) { //Если за 3 раза не удалось вернуть номер, выходим
            return "Не удалось получить номер более 3х раз, обратитесь к разработчикам";
        }
        aCount++;
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select number as f1,to_char(reservedate,'dd.MM.yyyy') as f2_date, cast(reservetime as varchar(5)) as f3_time, id as f4_id from ElectronicDisabilityDocumentNumber where disabilitydocument_id is null order by number");
        if (!list.isEmpty()) {
            Date currentDate = new Date();
            Calendar cal = new GregorianCalendar();
            for (WebQueryResult r : list) {
                String id = r.get4().toString();
                String number = r.get1().toString();
                String rDate = r.get2() == null ? "" : r.get2().toString().trim();
                if (rDate.equals("")) {
                    service.executeUpdateNativeSql("update ElectronicDisabilityDocumentNumber set reservedate = current_date, reservetime = current_time where id =" + id);
                    return number;
                } else {
                    cal.setTime(DateConverter.createDateTime(rDate, r.get3().toString()));
                    cal.add(Calendar.HOUR, 1);
                    if (currentDate.getTime() > cal.getTime().getTime()) { //Если за 1 час больничный лист не оформили, забераем его себе.
                        service.executeUpdateNativeSql("update ElectronicDisabilityDocumentNumber set reservedate = current_date, reservetime = current_time where id =" + id);
                        return number;
                    }
                }
            }
        }
        LOG.warn("Не найдено свободных номеров ЭЛН, запрашиваем один номер");
        getLNNumberRange(1L, aRequest);
        return getFreeNumberForDisabilityDocumentReloaded(aCount, aRequest);
    }

    /**
     * @param aRequest
     * @return
     * @throws NamingException
     */
    public String getPrefixForLN(HttpServletRequest aRequest) throws NamingException {
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        StringBuilder sql = new StringBuilder();
        sql.append("select ml.prefixForLN from secuser su left join workfunction wf on wf.secuser_id=su.id left join worker w on w.id=wf.worker_id left join mislpu ml on ml.id=w.lpu_id where su.login='").append(login).append("' and ml.prefixForLN is not null and ml.prefixForLN!=''");
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql(sql.toString(), 1);
        if (l.isEmpty()) {
            return "";
        } else {
            return "" + l.iterator().next().get1();
        }
    }

    /**
     * @param aReason
     * @param aRequest
     * @return
     * @throws NamingException
     */
    public String getCodeByReasonClose(Long aReason, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select id,coalesce(codef,'') from VocDisabilityDocumentCloseReason where id='" + aReason + "'", 1);
        if (l.isEmpty()) {
            return null;
        } else {
            return "" + l.iterator().next().get2();
        }
    }

    /**
     * @param aDisDocument
     * @param aRequest
     * @return
     * @throws NamingException
     */
    public String getMaxDateToByDisDocument(Long aDisDocument, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select to_char(max(dr.dateTo),'dd.mm.yyyy'),max(case when dr.dateto is null then dr.id else null end) from DisabilityRecord dr where disabilityDocument_id='" + aDisDocument + "'", 1);
        if (l.isEmpty()) {
            return null;
        } else {
            WebQueryResult wqr = l.iterator().next();
            return wqr.get2() == null ? "" + wqr.get1() : null;
        }
    }

    /**
     * Получить дату выхода на работу.
     *
     * @param aDisDocument
     * @param aRequest
     * @return
     * @throws NamingException
     */
    private String getDateGoToWork(Long aDisDocument, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select to_char(max(dateto)+1,'yyyy-MM-dd') from disabilityrecord where disabilitydocument_id =" + aDisDocument, 1);
        return l.isEmpty() ? null : l.iterator().next().get1().toString();
    }

    /**
     * Закрыть ЛН.
     *
     * @param aDocId
     * @param aReasonId
     * @param aSeries
     * @param aNumber
     * @param aOtherCloseDate
     * @param aRequest
     * @return
     * @throws Exception
     */
    public String closeDisabilityDocument(Long aDocId, Long aReasonId, String aSeries, String aNumber,
                                          String aOtherCloseDate, HttpServletRequest aRequest) throws Exception {
        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
        String dateGoToWork = getDateGoToWork(aDocId, aRequest);
        return service.closeDisabilityDocument(aDocId, aReasonId, aSeries, aNumber, aOtherCloseDate, dateGoToWork);
    }


    /**
     * Получить данные для закрытия ЛН.
     *
     * @param aDocId   DisabilityDocument.id
     * @param aRequest HttpServletRequest
     * @return String
     * @throws Exception
     */
    public String getDataByClose(Long aDocId, HttpServletRequest aRequest) throws Exception {
        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
        return service.getDataByClose(aDocId);
    }

    /**
     * Сделать дубликат.
     *
     * @param disabilityDocumentId DisabilityDocument.id
     * @param aReasonId            DisabilityDocument.disabilityreason_id
     * @param aSeries              DisabilityDocument.series
     * @param aNumber              DisabilityDocument.number
     * @param aWorkFunction2       DisabilityDocument.workfunction_id
     * @param aJob                 DisabilityDocument.job
     * @param aUpdateJob           DisabilityDocument.job
     * @param aRequest             HttpServletRequest
     * @return new DisabilityDocument.id
     * @throws Exception
     */
    public Long createDuplicateDocument(Long disabilityDocumentId, Long aReasonId, String aSeries, String aNumber,
                                        Long aWorkFunction2, String aJob, Boolean aUpdateJob, HttpServletRequest aRequest)
            throws Exception {

        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
        return service.createDuplicateDocument(disabilityDocumentId, aReasonId, aSeries, aNumber, aWorkFunction2, aJob, aUpdateJob);
    }

    /**
     * @param aDocId
     * @param aJob
     * @param aSeries
     * @param aNumber
     * @param aVocCombo
     * @param aPrevDocument
     * @param aRequest
     * @return
     * @throws Exception
     */
    public Long createWorkComboDocument(Long aDocId, String aJob, String aSeries, String aNumber,
                                        Long aVocCombo, Long aPrevDocument, HttpServletRequest aRequest) throws Exception {

        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
        return service.createWorkComboDocument(aDocId, aJob, aSeries, aNumber, aVocCombo, aPrevDocument);
    }

    /**
     * Получить список причин аннулирвания ЛН.
     *
     * @param aRequest HttpServletRequest
     * @return String with result
     * @throws NamingException
     */
    public String getReasonsfOfAnnulSheets(HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = "SELECT code,name from vocannulreason";
        Collection<WebQueryResult> list = service.executeNativeSql(query);
        StringBuilder res = new StringBuilder();
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) {
                res.append(wqr.get1()).append("#").append(wqr.get2()).append("!");
            }
        } else {
            res.append("##");
        }

        return res.toString();
    }

    /**
     * Получение СНИЛС по пациенту.
     *
     * @param aPatinetId Patient.id
     * @param aRequest   HttpServletRequest
     * @return String со СНИЛС
     * @throws NamingException
     */
    public String getSnils(Long aPatinetId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String snils = "";
        Collection<WebQueryResult> list = service.executeNativeSql("select snils from patient where id=" + aPatinetId);
        if (!list.isEmpty()) {
            snils = list.iterator().next().get1().toString();
        }

        if (!snils.equals("")) {
            String[] str;
            str = snils.split("-");
            snils = str[0] + str[1] + str[2];
            str = snils.split(" ");
            snils = str[0] + str[1];
        }

        return snils;
    }

    /**
     * Удаление ЭЦП с больничного.
     *
     * @param disabilityRecordId   DisabilityRecord.id
     * @param disabilityDocumentId DisabilityDocument.id
     * @param code                 Код типа подписи (Врач/ВК/Врач в закрытии периода)
     * @param aRequest             HttpServletRequest
     * @return String with status
     * @throws NamingException
     */
    public String deleteSign(Long disabilityRecordId, Long disabilityDocumentId, String code, HttpServletRequest aRequest)
            throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        if (!code.equals("close")) {
            String sqlRequest = "select id from disabilitysign  where disabilitydocumentid_id="
                    + disabilityDocumentId + " and code='" + code + "'" + " and externalId=" + disabilityRecordId +
                    " and (select isexport" +
                    " from disabilityrecord dr" +
                    " left join disabilitydocument dd on dr.disabilitydocument_id=dd.id" +
                    " left join disabilitysign dsdoc on dsdoc.disabilitydocumentid_id = dd.id and dsdoc.externalid = dr.id" +
                    " where dd.id=" + disabilityDocumentId + " and dsdoc.code = '" + code + "'" + " and dr.id=" + disabilityRecordId +
                    " limit 1)=false";

            Collection<WebQueryResult> requestResult = service.executeNativeSql(sqlRequest, 1);
            if (requestResult.isEmpty()) {
                return "Подписи не найдены либо период уже выгружен (в этом случае подписи удалять нельзя)!";
            } else {
                service.executeUpdateNativeSql("delete from disabilitysign  where disabilitydocumentid_id="
                        + disabilityDocumentId + " and code='" + code + "'" + " and externalId=" + disabilityRecordId + "" +
                        " and (select isexport" +
                        " from disabilityrecord dr" +
                        " left join disabilitydocument dd on dr.disabilitydocument_id=dd.id" +
                        " left join disabilitysign dsdoc on dsdoc.disabilitydocumentid_id = dd.id and dsdoc.externalid = dr.id" +
                        " where dd.id=" + disabilityDocumentId + " and dsdoc.code = '" + code + "'" + " and dr.id=" + disabilityRecordId + " limit 1)=false");
                return "Удалено.";
            }
        } else {
            Collection<WebQueryResult> l = service.executeNativeSql("select * from disabilitydocument dd" +
                    " left join disabilitysign dsdoc on dsdoc.disabilitydocumentid_id = dd.id" +
                    " where dd.id=" + disabilityDocumentId + " and dsdoc.code = 'close' and dd.closeexport=false", 1);
            if (l.isEmpty()) {
                return "Подписи не найдены либо ЭЛН уже выгружен (в этом случае подписи удалять нельзя)!";
            } else {
                service.executeUpdateNativeSql("delete from disabilitysign" +
                        " where id=ANY(select dsdoc.id from disabilitydocument dd" +
                        " left join disabilitysign dsdoc on dsdoc.disabilitydocumentid_id = dd.id" +
                        " where dd.id=" + disabilityDocumentId + " and dsdoc.code = 'close' and dd.closeexport=false)");

                return "Удалено.";
            }
        }
    }

    /**
     * Показать все случаи нетрудоспособности по Disabilitydocument.id.
     *
     * @param documentId DisabilityDocument.id
     * @param aRequest   HttpServletRequest
     * @return String with json
     * @throws NamingException
     * @throws SQLException
     */
    public String getDisCasesInJson(String documentId, HttpServletRequest aRequest) throws NamingException, SQLException {
        IWebQueryService service = Injection.find(aRequest, null).getService(IWebQueryService.class);
        return service.executeSqlGetJson("select dc.id" +
                " , to_char(min(dr.datefrom),'dd.mm.yyyy') as mindatefrom" +
                " , case when" +
                " count(case when dr.dateto is null then '1' else null end)>0" +
                " then '-' else to_char(max(dr.dateto),'dd.mm.yyyy') end maxdateto" +
                " , case when" +
                " count(case when dr.dateto is null then '1' else null end)>0" +
                " then null " +
                " else" +
                " case when max(dr.dateto)=min(dr.datefrom) then '1' else max(dr.dateto)-min(dr.datefrom)+1 end end" +
                " from disabilitycase dc" +
                " left join disabilitydocument dd on dd.disabilitycase_id=dc.id" +
                " left join disabilityrecord dr on dr.disabilitydocument_id=dd.id" +
                " where dc.patient_id=(select distinct _dc.patient_id from disabilitycase _dc" +
                " left join disabilitydocument _dd on _dd.disabilitycase_id=_dc.id" +
                " where _dd.id='" + documentId + "')  and dd.disabilitycase_id<>(select disabilitycase_id from disabilitydocument where id='" + documentId + "')" +
                " group by dc.id" +
                " order by dc.id");
    }

    /**
     * Перенос ЛН в другой СНТ.
     *
     * @param documentId DisabilityDocument.id
     * @param newCaseId  DisabilityCase.id
     * @param aRequest   HttpServletRequest
     * @return String with status
     * @throws NamingException
     */
    public String moveToNewDisCase(String documentId, String newCaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest, null).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update disabilitydocument set disabilitycase_id='" + newCaseId + "' where id='" + documentId + "'");
        return "ЛН перенесён!";
    }

    /**
     * Получить список экспертов для врачебной комиссии.
     *
     * @param aRequest HttpServletRequest
     * @return String with result
     * @throws NamingException
     */
    public String getDefaultKer(HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest, null).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select ex.id, case when ex.code is not null then ex.code||' '||ex.name else 'null '||ex.name end " +
                "from vocExpertComposition ex left join softconfig sc  on ex.id=cast(sc.keyvalue as integer) where sc.key='defaultKER'");
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            return wqr.get1() + "#" + wqr.get2();
        } else return "";
    }

    /**
     * Проверить, есть ли подпись ВК.
     *
     *@param documentId DisabilityDocument.id
     * @param aRequest HttpServletRequest
     * @return Boolean true если есть ВК
     * @throws NamingException
     */
    public Boolean getIfDisDocHasVK(Long documentId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest, null).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select id from disabilityrecord where disabilitydocument_id="+documentId+" and workfunctionadd_id is not null");
        return !list.isEmpty();
    }

    /**
     * Обновить лист нетрудоспособности: ФИО+ДР, место работы, выгружен период/нет, хэш.
     *
     *@param documentId DisabilityDocument.id
     * @param aRequest HttpServletRequest
     * @return Boolean true если обновлено, false - если не удалось обновить
     * @throws NamingException
     */
    public Boolean updateEln(Long documentId, HttpServletRequest aRequest) throws NamingException, ParserConfigurationException, IOException, SAXException {
        IDisabilityService serviceDis = Injection.find(aRequest).getService(IDisabilityService.class);
        IWebQueryService serviceSql = Injection.find(aRequest, null).getService(IWebQueryService.class);

        String endpoint = serviceDis.getSoftConfigValue("FSS_PROXY_SERVICE", "null");
        String ogrn_lpu = serviceDis.getSoftConfigValue("ogrn_lpu", "null");

        Collection<WebQueryResult> list = serviceSql.executeNativeSql("select dd.number,replace(replace(p.snils,'-',''),' ','')" +
                " from patient p" +
                " left join disabilitycase dc on p.id=dc.patient_id" +
                " left join disabilitydocument dd on dc.id=dd.disabilitycase_id" +
                " where dd.id="+documentId);
        if (!list.isEmpty() && endpoint!=null && ogrn_lpu!=null) {
            String eln = list.iterator().next().get1()!=null? list.iterator().next().get1().toString() : "";
            String snils = list.iterator().next().get2()!=null? list.iterator().next().get2().toString() : "";
            Map<String,String> params = new HashMap<>();
            params.put("ogrn",ogrn_lpu);
            params.put("eln",eln);
            params.put("snils",snils);
            String xml = creteGetRequest(endpoint,  "api/import", "application/json", params);

            parseDisabilityXML(xml,documentId,aRequest);
            return true;
        }
        return false;
    }

    /**
     * Распарсить xml с информацией о листке нетрудоспособности и актуализировать данные.
     *
     * @param xml xml для парсера
     * @param documentId DisabilityDocument.id
     * @param aRequest HttpServletRequest
     * @throws NamingException
     */
    private void parseDisabilityXML(String xml,Long documentId, HttpServletRequest aRequest) throws NamingException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        Document document = builder.parse(is);
        String surName = document.getElementsByTagName("ns1:SURNAME").item(0).getTextContent();
        String name = document.getElementsByTagName("ns1:NAME").item(0).getTextContent();
        String patronimic = document.getElementsByTagName("ns1:PATRONIMIC").item(0).getTextContent();
        String hash = document.getElementsByTagName("ns1:LN_HASH").item(0).getTextContent();
        String job = document.getElementsByTagName("ns1:LPU_EMPLOYER").item(0).getTextContent();
        String bday = document.getElementsByTagName("ns1:BIRTHDAY").item(0).getTextContent();

        IWebQueryService serviceSql = Injection.find(aRequest, null).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("update disabilitydocument")
                .append(" set lnhash='").append(hash).append("'")
                .append(",job='").append(job).append("'")
                .append(" where id = ").append(documentId);
        serviceSql.executeUpdateNativeSql(sql.toString());
        sql = new StringBuilder();
        sql.append("update patient set")
                .append(" firstname='").append(name).append("'")
                .append(" ,lastname='").append(surName).append("'")
                .append(" ,middlename='").append(patronimic).append("'")
                .append(" ,birthday=to_date('").append(bday).append("','yyyy-mm-dd')")
                .append(" where id=(select p.id")
                .append(" from patient p")
                .append(" left join disabilitycase dc on p.id=dc.patient_id")
                .append(" left join disabilitydocument dd on dc.id=dd.disabilitycase_id")
                .append(" where dd.id=").append(documentId).append(")");
        serviceSql.executeUpdateNativeSql(sql.toString());
        //update disabilityrecord export
        NodeList nodeList = document.getElementsByTagName("ns1:TREAT_PERIOD");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node period = nodeList.item(i);

            NodeList children = period.getChildNodes();
            String datefrom="",dateto="";
            for (int j=0; j<children.getLength(); j++) {
                if (children.item(j).getNodeName().equals("ns1:TREAT_DT1"))
                    datefrom = children.item(j).getTextContent();
                if (children.item(j).getNodeName().equals("ns1:TREAT_DT2"))
                    dateto = children.item(j).getTextContent();
            }
            if (!datefrom.equals("") && !dateto.equals("")) {
                sql = new StringBuilder();
                sql.append("update disabilityrecord")
                        .append(" set isexport=true")
                        .append(" where disabilitydocument_id=").append(documentId)
                        .append(" and datefrom=to_date('").append(datefrom)
                        .append("','yyyy-mm-dd') and dateto=to_date('").append(dateto).append("','yyyy-mm-dd')");
                serviceSql.executeUpdateNativeSql(sql.toString());
            }
        }
    }
}
