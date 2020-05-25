package ru.ecom.api.util;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.record.IApiRecordService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.nuzmsh.util.date.AgeUtil;

import java.sql.Date;

public class ApiRecordUtil {
    private static final Logger LOG = Logger.getLogger(ApiRecordUtil.class);
    private static final String ERRORSERVICESTREAM="WRONG_SERVICESTREAM";
    private static final String ERRORWORKFUNCTION="WRONG_DOCTOR";
    private static final String ERRORVISITDATE="WRONG_VISITDATE";

    /** Формируем json с ошибкой */
    public static String getErrorJson(String aReasonText, String aCode) {
            return getErrorJsonObj(aReasonText, aCode).toString();
    }
    public static JSONObject getErrorJsonObj(String aReasonText, String aCode) {
        JSONObject err = createJson(null,null,aCode,aReasonText);
        LOG.error("ERROR_JSON "+err);
            return err;
    }

    /**
     * Список специальностей со свободными временами по переданному потоку обслуживания
     * @param aServiceStream
     * @return
     */
    public String getSpecializations(String aServiceStream, IWebQueryService aService)  {return getSpecializations( aServiceStream, null, aService);}
    public String getSpecializations(String aServiceStream, String aLpuId, IWebQueryService aService)  {
        aServiceStream=getServiceStreamSqlAdd(aServiceStream);
        if (aServiceStream==null) {
            return getErrorJson("Неверное значение параметра 'Поток обслуживания'",ERRORSERVICESTREAM);
        }
        String[] jsonFields = {"specialization_id", "specialization_name"};
        String selectSql="vwf.id as id, vwf.name as name";
        String groupBy="vwf.id, vwf.name";
        String orderBy="vwf.name";
        if (aLpuId!=null&&!aLpuId.equals("")) {
            aServiceStream+=" and mlGr.id="+aLpuId;
        }
        return getData(selectSql,aServiceStream,orderBy,groupBy,jsonFields,100, aService);
    }

    /** Выбор отделения, по которому в дальнейшем будет идти поиск специалистов */
    public String getDepartments(String aServiceStream, IWebQueryService aService) {
        aServiceStream=getServiceStreamSqlAdd(aServiceStream);
        String[] jsonFields = {"lpuId", "lpuName"};
        String selectSql="mlGr.id as id, mlGr.name as name";
        String groupBy="mlGr.id, mlGr.name";
        String orderBy="mlGr.name";
        return getData(selectSql,aServiceStream!=null?aServiceStream:"",orderBy,groupBy,jsonFields,100, aService);
    }

    /**
     * Получаем список услуг с ценами по умолчанию для выбранного врача
     * @param aWorkfunctionId
     * @return
     */
    private String getDefaultMedServicesAndPrices(String aWorkfunctionId, IWebQueryService aService) {
        if (aWorkfunctionId==null || aWorkfunctionId.equals(""))
            return getErrorJson("Неверное значение параметра 'Должность'",ERRORWORKFUNCTION);
        else {
            String[] jsonFields = {"msName", "ppCost"};
            String selectSql = "select distinct ms.name,pp.cost from priceposition pp" +
                    " left join pricelist pl on pl.id=pp.pricelist_id" +
                    " left join pricemedservice pms on pms.priceposition_id=pp.id" +
                    " left join medservice ms on ms.id=pms.medservice_id" +
                    " left join WorkFunctionService wfs on wfs.medService_id=ms.id" +
                    " left join WorkFunction wf on wf.id=wfs.workfunction_id" +
                    " where wf.id=" + aWorkfunctionId + " and (ms.startDate is null or current_date  >=ms.startDate)" +
                    " and (ms.finishDate is null or ms.finishDate>=current_date)" +
                    " and ms.isShowSiteAsDefault=true" +
                    " and pl.isdefault=true order by pp.cost";
            return aService.executeNativeSqlGetJSON(jsonFields, selectSql,30);
        }
    }

    /**
     * Получаем спсиок рабочих функций врачей по потоку обслуживания и специальности
     * @param aServiceStream
     * @param aVocWorkfunctionId
     * @return
     */
    public String getDoctors (String aServiceStream, String aVocWorkfunctionId, IWebQueryService aService) {return getDoctors(aServiceStream,aVocWorkfunctionId,null,null,aService);}
    public String getDoctors (String aServiceStream, String aVocWorkfunctionId, String aLpuId, String aWantDefMedServices,  IWebQueryService aService) {
        Boolean isCharged = aServiceStream.equals("CHARGED");
        aServiceStream=getServiceStreamSqlAdd(aServiceStream);
        if (aServiceStream==null) {
            return getErrorJson("Неверное значение параметра 'Поток обслуживания'",ERRORSERVICESTREAM);
        } else if (aVocWorkfunctionId==null) {
            return getErrorJson("Неверное значение параметра 'Должность'",ERRORWORKFUNCTION);
        }
        String selectSql=" wf.id as id, case when wf.dtype='PersonalWorkFunction' then vwf.name else '' end as wfName, case when wf.dtype='PersonalWorkFunction' then p.lastname||' '||p.firstname||' '|| coalesce(p.middlename,'') "
                +"else wf.groupname end as doctorName";
        String groupBySql="wf.id,wf.dtype, vwf.name, p.lastname ,p.firstname,p.middlename,wf.groupname";
        String orderBySql="p.lastname ,p.firstname,p.middlename";
        String[] jsonFields = {"workfunction_id","workfunction_name","doctor_name"};

        String sqlAdd = aServiceStream+" and vwf.id="+aVocWorkfunctionId;
        if (aLpuId!=null && !aLpuId.equals("")) {
            sqlAdd+=" and mlGr.id="+aLpuId;
        }
        String data = getData(selectSql,sqlAdd,orderBySql,groupBySql,jsonFields,100,aService);
        if (aWantDefMedServices==null || !isCharged)
            return data;
        else { //только для платных
            JSONObject ret = new JSONObject();
            JSONArray dataBody = new JSONArray();
            JSONArray arr = new JSONObject(data).getJSONArray("data");
            for (int i=0; i<arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                obj.put("listMedServices",new JSONArray(getDefaultMedServicesAndPrices(obj.getString("workfunction_id"),aService)));
                dataBody.put(obj);
            }
            ret.put("data",dataBody);
            return ret.toString();
        }
    }

    /**
     * Возвращает свободные даты для выбранного врача (либо специальности, если врач не указан)
     * @param aWorkfunctionId
     * @param aVocWorkfunctionId
     * @param aServiceStream
     * @return
     */
    public String getFreeCalendarDaysByWorkFunction(String aWorkfunctionId,String aVocWorkfunctionId, String aServiceStream, IWebQueryService aService) {
        return getFreeCalendarDaysByWorkFunction(aWorkfunctionId,aVocWorkfunctionId, aServiceStream,null, aService);
    }
    public String getFreeCalendarDaysByWorkFunction(String aWorkfunctionId,String aVocWorkfunctionId, String aServiceStream,String aLpuId, IWebQueryService aService) {
        aServiceStream = getServiceStreamSqlAdd(aServiceStream);
        if (aServiceStream==null) {
            return getErrorJson("Неверное значение параметра 'Поток обслуживания'",ERRORSERVICESTREAM);
        }
        StringBuilder sqlAdd = new StringBuilder();

        String[] jsonFields = {"calendarDay_id","calendarDate", "prettyCalendarDate"};
        if (aWorkfunctionId!=null&&!aWorkfunctionId.equals("")) { //Ищем свободные времена по конкретной раб. функции
            sqlAdd.append(" and wf.id=").append(aWorkfunctionId);
        } else { //Ищем свободные времена по любой раб. функции указанной специальности
            if (aVocWorkfunctionId!=null) {
                sqlAdd.append(" and vwf.id=").append(aVocWorkfunctionId);
                if (aLpuId!=null&&!aLpuId.equals("")) {
                    sqlAdd.append(" and mlGr.id=").append(aLpuId);
                }
            } else {
                return getErrorJson("Неверное значение параметра 'Должность'",ERRORWORKFUNCTION);
            }
        }
        sqlAdd.append(" and (wcd.calendardate>current_date or wcd.calendardate=current_date and wct.timeFrom>current_time)")
                .append(aServiceStream);

        return getData(" max(wcd.id) as id, to_char(wcd.calendardate,'yyyy-MM-dd') as calendarDate, to_char(wcd.calendardate,'dd.MM.yyyy') as prettyCalendarDate" //Ищем свободные времена по конкретной раб. функции //Ищем свободные времена по любой раб. функции указанной специальности
                ,sqlAdd.toString(),"wcd.calendardate","wcd.calendardate",jsonFields,30,aService);
    }

/** Находим свободные времени либо по должности (все неврологи), либо по кокретной рабочей функции (невролог Иванов)*/
    public String getFreeCalendarTimesByCalendarDate(String aCalendarDayId, String aVocWorkfunctionId, String aCalendarDate, String aServiceStream, IWebQueryService aService) {return getFreeCalendarTimesByCalendarDate(aCalendarDayId, aVocWorkfunctionId,  aCalendarDate, aServiceStream, null,  aService) ;}
    public String getFreeCalendarTimesByCalendarDate(String aCalendarDayId, String aVocWorkfunctionId, String aCalendarDate, String aServiceStream, String aLpuId, IWebQueryService aService) {
        aServiceStream = getServiceStreamSqlAdd(aServiceStream);
        if (aServiceStream==null) {
            return getErrorJson("Неверное значение параметра 'Поток обслуживания'",ERRORSERVICESTREAM);
        }
        StringBuilder sqlAdd = new StringBuilder();
        String groupBySql = "wct.id, wct.timeFrom";
        String orderBySql = "wct.timeFrom";
        String[] jsonFields = {"calendarTime_id","calendarTime"};
        if (aCalendarDayId!=null&&!aCalendarDayId.equals("")) { //Ищем по конкретному специалисту
            sqlAdd.append(" and wcd.id=").append(aCalendarDayId);
        } else if (aVocWorkfunctionId!=null && !aVocWorkfunctionId.equals("") && aCalendarDate!=null) { //Ищем по всем врачам выбранной специальности
                sqlAdd.append(" and wvf.id=").append(aVocWorkfunctionId).append(" and wcd.calendardate = to_date('yyyy-MM-dd','").append(aCalendarDate).append("')");
            if (aLpuId!=null&&!aLpuId.equals("")) {
                sqlAdd.append(" and mlGr.id=").append(aLpuId);
            }
            } else {
                return getErrorJson("Неверное значение параметра 'Дата приема'",ERRORVISITDATE);
            }

        sqlAdd.append(" and case when wcd.calendardate=current_date and wct.timeFrom>current_time then 1 when wcd.calendardate>current_date then 1 else 0 end =1").append(aServiceStream);
        return getData("wct.id as id, cast(wct.timefrom as varchar(5)) as calendarTime" //Ищем по конкретному специалисту //Ищем по всем врачам выбранной специальности
                ,sqlAdd.toString(),orderBySql,groupBySql,jsonFields,50,aService);
    }

/**
 *
 String recordPatient(Long aCalendarTimeId, String aPatientLastname, String aPatientFirstname, String aPatientMiddlename, Date aPatientBirthday, String aPatientGUID, String aComment);
 String annulRecord(Long aCalendarTimeId, String aLastname, String aFirstname, String aMiddlename, Date aBirthday, String aPatientGUID);
 * */
public static String recordPatient(Long aCalendarTimeId, String aPatientLastname, String aPatientFirstname, String aPatientMiddlename, Date aPatientBirthday
        , String aComment, String aPhone, IApiRecordService apiRecordService, String aRecordType) {
    try {
        if (AgeUtil.calcAgeYear(aPatientBirthday,new Date(System.currentTimeMillis()))>122) {
            return getErrorJson("Запись пациента старше 122 лет невозможна","TOO_OLD");
        }
    } catch (Exception e) {
        return getErrorJson("Проверьте дату рождения пациента","TOO_YOUNG");
    }
    return apiRecordService.recordPatient(aCalendarTimeId,aPatientLastname,aPatientFirstname,aPatientMiddlename,aPatientBirthday,aComment,aPhone, aRecordType);
}

    /** Аннулируем направление (соответствие по пациенту и времени*/
    public String annulRecord(Long aCalendarTimeId, String aLastname, String aFirstname, String aMiddlename, Date aBirthday, IApiRecordService aService){
            return aService.annulRecord(aCalendarTimeId,aLastname,aFirstname,aMiddlename,aBirthday);
        }

    public String getRecordInformation (String aWorkcalendarTimeId, IWebQueryService aService) {
        StringBuilder sql = new StringBuilder();
        sql.append("select wct.id as id,wct.id as id2, case when pat.id is not null then pat.patientinfo else wct.prepatientinfo end as patientInfo")
           .append(",wcd.calendardate as calendarDate")
           .append(",cast(wct.timefrom as varchar(5)) as timeFrom")
           .append(",case when wpat.id is not null then vwf.name|| ' '|| wpat.lastname||' '||wpat.firstname||' '||wpat.middlename else wf.groupname end as doctorName")
           .append(",'AMOKB' as lpuCode, pesa.externalcode as patientUID")
           .append(",pat.id as patientId")
           .append(",wct.medcase_id as medcaseId")
           .append(",case when wct.reserveType_id is not null then vsrt.code  else 'OMC' end as serviceStream")
           .append(",case when wct.medcase_id is null then 'PRERECORD'")
                .append(" else case when vis.noactuality='1' then 'ANNUL' when vis.datestart is not null then 'MADE' else 'RECORD' end end as recordState")
           .append(", case when vsrt.id is not null then coalesce(vsrt.recordComment,'') else 'Вам необходимо подойти за 10 минут до приема в регистратуру для оформления мед. документации(здание КДЦ, первый этаж)' end as recordComment")
           .append(" from workcalendartime wct")
           .append(" left join medcase vis on vis.id=wct.medcase_id")
           .append(" left join vocservicereservetype vsrt on vsrt.id=wct.reserveType_id")
           .append(" left join patient pat on pat.id=wct.prepatient_id")
           .append(" left join patientexternalserviceaccount pesa on pesa.patient_id=pat.id and pesa.dateto is null")
           .append(" left join workcalendarday wcd on wcd.id=wct.workCalendarDay_id")
           .append(" left join workcalendar wc on wc.id=wcd.workcalendar_id")
           .append(" left join workfunction wf on wf.id=wc.workFunction_id")
           .append(" left join vocworkfunction vwf on vwf.id=wf.workfunction_id")
           .append(" left join Worker w on w.id=wf.worker_id")
           .append(" left join Patient wpat on wpat.id=w.person_id")
           .append(" where wct.id=").append(Long.valueOf(aWorkcalendarTimeId));
       // LOG.info("sql="+sql);
        String[] jsonFields = {"workcalendartime_id","workCalendarTimeId", "patientInfo","calendarDate","timeFrom","doctorName","lpuCode","patientUID","medcaseId","patientId","serviceStream","state","recordComment"};
        String ret = aService.executeNativeSqlGetJSON(jsonFields,sql.toString(),1);
        try {
            JSONArray arr = new JSONArray(ret);
            if (arr.length()>0){
                JSONObject obj =arr.getJSONObject(0);
                obj.put("status","ok");
                LOG.info("Пациент успешно записан"+ obj.toString());
                return obj.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getErrorJson("Не удалось вернуть информацию о записи","JSON_COMMON");
    }

    /**
     * Формируем нужный sqlAdd в зависимости от источника оплаты
     * @param aServiceStreamCode
     * @return
     */
    private static String getServiceStreamSqlAdd(String aServiceStreamCode) {
        StringBuilder sqlAppend = new StringBuilder();
        if (aServiceStreamCode==null) {LOG.error("Не указан поток обслуживания");return null;}
        if (aServiceStreamCode.equals("OMC")) {
            sqlAppend.append(" and wct.reservetype_id is null");
        } else if (aServiceStreamCode.equals("CHARGED")) {
            sqlAppend.append(" and vrt.code='PAYMENT'");
        } else if (aServiceStreamCode.equals("TELE_OMC")) {//Удаленная консультация по ОМС
            sqlAppend.append(" and vrt.code='TELE_OMC'");
        } else if (aServiceStreamCode.equals("TELE_CHARGED")) {//Удаленная консультация платно
            sqlAppend.append(" and vrt.code='TELE_CHARGED'");
        } else {
                LOG.error("Неправильный источник оплаты: >" + aServiceStreamCode+"<");
            return null;
        }
        return sqlAppend.toString();
    }

    /** Болванка для поиска специальностей/врачей/дат/времен */
    private static String getData(String aSelect, String aWhereAdd, String aOrderBy, String aGroupBy, String[] aJsonFields,Integer aMaxResult, IWebQueryService aService){
        StringBuilder sql = new StringBuilder();
        sql.append("select ") // vwf.id as id ,vwf.name as name")
                .append(aSelect)
                .append(" from WorkFunction as wf")
                .append(" left join Worker as w on w.id=wf.worker_id")
                .append(" left join Patient as p on p.id=w.person_id")
                .append(" inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
                .append(" left join WorkCalendar wc on wc.workFunction_id=wf.id")
                .append(" left join workcalendarday wcd on wcd.workcalendar_id=wc.id")
                .append(" left join workcalendartime wct on wct.workCalendarDay_id=wcd.id")
                .append(" left join VocServiceReserveType vrt on vrt.id=wct.reservetype_id")
                .append(" left join mislpu mlGr on mlGr.id=coalesce(wf.lpu_id,w.lpu_id)")
                .append(" where wc.id is not null")
                .append(" and wcd.calendardate>=current_date")
                .append(" and wf.group_id is null ")
                .append(" and (wf.archival is null or wf.archival='0')")
                .append(" and (wcd.isDeleted is null or wcd.isDeleted='0')")
                .append(" and (wct.isDeleted is null or wct.isDeleted='0')")
                .append(" and (wf.isnoviewremoteuser  ='0' or wf.isnoviewremoteuser is null)")
                .append(" and wct.medCase_id is null and wct.prepatient_id is null ")
                .append(" and (wct.prepatientinfo is null or wct.prepatientinfo='') ")
                .append(aWhereAdd);
        if (aOrderBy!=null) {sql.append(" group by ").append(aGroupBy);}
        sql.append(" having count(wct.id)>0");
        if (aOrderBy!=null) {sql.append(" order by ").append(aOrderBy);}
        String jsonData = aService.executeNativeSqlGetJSON(aJsonFields,sql.toString(),aMaxResult);
        return createJson("data",jsonData).toString();
    }

    public static JSONObject createJson(String aElementName, String aJsonData) {
        return createJson(aElementName,aJsonData,null,null);

    }
    public static JSONObject createJson(String aElementName, String aJsonData, String aErrorCode, String aErrorName) {
        JSONObject ret = new JSONObject();
        if (aElementName!=null) {
            ret.put(aElementName, aJsonData!=null ? new JSONArray(aJsonData) : new JSONArray());
        }
        ret.put("status",aErrorCode!=null ? "error" : "ok");
        if (aErrorCode!=null) {
            ret.put("error_code",aErrorCode);
            ret.put("error_name",aErrorName);
       }
        return ret;
    }
}
