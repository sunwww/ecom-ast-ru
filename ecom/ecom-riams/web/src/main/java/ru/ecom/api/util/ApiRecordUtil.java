package ru.ecom.api.util;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.api.record.IApiRecordService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.nuzmsh.util.date.AgeUtil;

import java.sql.Date;

public class ApiRecordUtil {
    private static final Logger LOG = Logger.getLogger(ApiRecordUtil.class);
    private static final String ERRORSERVICESTREAM = "WRONG_SERVICESTREAM";
    private static final String ERRORWORKFUNCTION = "WRONG_DOCTOR";
    private static final String ERRORVISITDATE = "WRONG_VISITDATE";

    private static final String BAD_WORKFUNCTION_TEXT = "Неверное значение параметра 'Должность'";
    private static final String BAD_SERVICE_STREAM_TEXT = "Неверное значение параметра 'Поток обслуживания'";

    private static final int MAX_AGE = 122; //Предельный возраст для записи пациента

    /**
     * Текст с сообщением об ошибке.
     *
     * @param errorText Текст ошибки
     * @param errorCode Код ошибки
     * @return Текст
     */
    private static String getErrorJson(String errorText, String errorCode) {
        return getErrorJsonObj(errorText, errorCode).toString();
    }

    public static JSONObject getErrorJsonObj(String errorText, String errorCode) {
        return createJson(null, null, errorCode, errorText);
    }

    /**
     * Запись пациента на прием
     *
     * @param calendarTimeId    ИД времени записи
     * @param patientLastname   Фамилия пациента
     * @param patientFirstname  Имя пациента
     * @param patientMiddlename Отчество пациента
     * @param patientBirthday   Дата рождения пациента
     * @param comment           Примечание при записи
     * @param phoneNumber       Телефон пациента
     * @param apiRecordService  колхооз
     * @param recordType        Тип записи
     * @return Строка с информацией о записи пациента *
     */
    public static String recordPatient(Long calendarTimeId, String patientLastname, String patientFirstname, String patientMiddlename, Date patientBirthday
            , String comment, String phoneNumber, IApiRecordService apiRecordService, String recordType) {
        try {
            if (AgeUtil.calcAgeYear(patientBirthday, new Date(System.currentTimeMillis())) > MAX_AGE) {
                return getErrorJson("Запись пациента старше " + MAX_AGE + " лет невозможна", "TOO_OLD");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage() + ", " + patientBirthday);
            return getErrorJson("Проверьте дату рождения пациента", "TOO_YOUNG");
        }
        return apiRecordService.recordPatient(calendarTimeId, patientLastname, patientFirstname, patientMiddlename, patientBirthday, comment, phoneNumber, recordType);
    }

    /**
     * Формируем нужный sqlAdd в зависимости от источника оплаты
     *
     * @param serviceStreamCode Поток обслуживания
     * @return условие для поиска данных
     */
    private static String getServiceStreamSqlAdd(String serviceStreamCode) {
        StringBuilder sqlAppend = new StringBuilder();
        if (serviceStreamCode == null) {
            LOG.error("Не указан поток обслуживания");
            return null;
        }
        if (serviceStreamCode.equals("OMC")) {
            sqlAppend.append(" and wct.reservetype_id is null");
        } else if (serviceStreamCode.equals("CHARGED")) {
            sqlAppend.append(" and vrt.code='PAYMENT'");
        } else if (serviceStreamCode.equals("TELE_OMC")) {//Удаленная консультация по ОМС
            sqlAppend.append(" and vrt.code='TELE_OMC'");
        } else if (serviceStreamCode.equals("TELE_CHARGED")) {//Удаленная консультация платно
            sqlAppend.append(" and vrt.code='TELE_CHARGED'");
        } else {
            LOG.error("Неправильный источник оплаты: >" + serviceStreamCode + "<");
            return null;
        }
        return sqlAppend.toString();
    }

    /**
     * Болванка для поиска специальностей/врачей/дат/времен
     *
     * @param selectSql          Поля для отображения
     * @param whereSql           Условие для поиска
     * @param orderBySql         Текст для сортировки
     * @param groupBySql         Группировка
     * @param jsonFields         Названия полей для отображения
     * @param maxResultRowsCount Ограничение на кол-во строк
     * @param webQueryService    *
     * @return Найденные данные в формате json *colhoz
     */

    private static String getData(String selectSql, String whereSql, String orderBySql, String groupBySql, String[] jsonFields, Integer maxResultRowsCount, IWebQueryService webQueryService) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ") // vwf.id as id ,vwf.name as name")
                .append(selectSql)
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
                .append(whereSql);
        if (orderBySql != null) {
            sql.append(" group by ").append(groupBySql);
        }
        sql.append(" having count(wct.id)>0");
        if (orderBySql != null) {
            sql.append(" order by ").append(orderBySql);
        }
        String jsonData = webQueryService.executeNativeSqlGetJSON(jsonFields, sql.toString(), maxResultRowsCount);
        return createJson("data", jsonData).toString();
    }

    private static JSONObject createJson(String elementName, String elementData) {
        return createJson(elementName, elementData, null, null);

    }

    /**
     * Формируем объект
     *
     * @param elementName Имя элемента
     * @param elementData Содержимое элемента
     * @param errorCode   Код ошибки
     * @param errorName   Имя ошибки
     * @return Json объект
     */
    private static JSONObject createJson(String elementName, String elementData, String errorCode, String errorName) {
        JSONObject ret = new JSONObject();
        if (elementName != null) {
            ret.put(elementName, elementData != null ? new JSONArray(elementData) : new JSONArray());
        }
        ret.put("status", errorCode != null ? "error" : "ok");
        if (errorCode != null) {
            ret.put("error_code", errorCode);
            ret.put("error_name", errorName);
        }
        return ret;
    }

    /**
     * Получаем список специальностей со свободными временами по переданному потоку обслуживания
     *
     * @param serviceStream   Поток обслуживания
     * @param departmentId    Отделение для поиска
     * @param webQueryService Сервис для запросов
     * @return Список специальностей
     */

    public String getSpecializations(String serviceStream, String departmentId, IWebQueryService webQueryService) {
        serviceStream = getServiceStreamSqlAdd(serviceStream);
        if (serviceStream == null) {
            return getErrorJson(BAD_SERVICE_STREAM_TEXT, ERRORSERVICESTREAM);
        }
        String[] jsonFields = {"specialization_id", "specialization_name"};
        String selectSql = "vwf.id as id, vwf.name as name";
        String groupBy = "vwf.id, vwf.name";
        String orderBy = "vwf.name";
        if (departmentId != null && !departmentId.equals("")) {
            serviceStream += " and mlGr.id=" + departmentId;
        }
        return getData(selectSql, serviceStream, orderBy, groupBy, jsonFields, 100, webQueryService);
    }

    /**
     * Выбор отделения, по которому в дальнейшем будет идти поиск специалистов
     *
     * @param serviceStream   Поток обслуживания
     * @param webQueryService sql сервис
     * @return Список отделений, в которые можно записаться
     */

    public String getDepartments(String serviceStream, IWebQueryService webQueryService) {
        serviceStream = getServiceStreamSqlAdd(serviceStream);
        String[] jsonFields = {"lpuId", "lpuName"};
        String selectSql = "mlGr.id as id, mlGr.name as name";
        String groupBy = "mlGr.id, mlGr.name";
        String orderBy = "mlGr.name";
        return getData(selectSql, serviceStream != null ? serviceStream : "", orderBy, groupBy, jsonFields, 100, webQueryService);
    }

    /**
     * Получаем список услуг с ценами по умолчанию для выбранного врача
     *
     * @param workfunctionId  ИД специалиста по котороу нужно выводить услуги (*странно что String, а не Long)
     * @param webQueryService sql сервис
     * @return Список услуг
     */
    private String getDefaultMedServicesAndPrices(String workfunctionId, IWebQueryService webQueryService) {
        if (workfunctionId == null || workfunctionId.equals("")) {
            return getErrorJson(BAD_WORKFUNCTION_TEXT, ERRORWORKFUNCTION);
        } else {
            String[] jsonFields = {"msName", "ppCost"};
            String selectSql = "select distinct ms.name,pp.cost from priceposition pp" +
                    " left join pricelist pl on pl.id=pp.pricelist_id" +
                    " left join pricemedservice pms on pms.priceposition_id=pp.id" +
                    " left join medservice ms on ms.id=pms.medservice_id" +
                    " left join WorkFunctionService wfs on wfs.medService_id=ms.id" +
                    " left join WorkFunction wf on wf.id=wfs.workfunction_id" +
                    " where wf.id=" + workfunctionId + " and (ms.startDate is null or current_date  >=ms.startDate)" +
                    " and (ms.finishDate is null or ms.finishDate>=current_date)" +
                    " and ms.isShowSiteAsDefault=true" +
                    " and pl.isdefault=true order by pp.cost";
            return webQueryService.executeNativeSqlGetJSON(jsonFields, selectSql, 30);
        }
    }

    /**
     * Получаем спсиок рабочих функций врачей по потоку обслуживания и специальности
     *
     * @param serviceStream              Поток обслуживания
     * @param vocWorkfunctionId          ИД специальности
     * @param departmentId               ИД отделения
     * @param isWantedDefaultMedServices хз
     * @param webQueryService            sql сервис
     * @return Список доступных докторов
     */

    public String getDoctors(String serviceStream, String vocWorkfunctionId, String departmentId, String isWantedDefaultMedServices, IWebQueryService webQueryService) {
        boolean isCharged = serviceStream.equals("CHARGED");
        serviceStream = getServiceStreamSqlAdd(serviceStream);
        if (serviceStream == null) {
            return getErrorJson(BAD_SERVICE_STREAM_TEXT, ERRORSERVICESTREAM);
        } else if (vocWorkfunctionId == null) {
            return getErrorJson(BAD_WORKFUNCTION_TEXT, ERRORWORKFUNCTION);
        }
        String selectSql = " wf.id as id, case when wf.dtype='PersonalWorkFunction' then vwf.name else '' end as wfName, case when wf.dtype='PersonalWorkFunction' then p.lastname||' '||p.firstname||' '|| coalesce(p.middlename,'') "
                + "else wf.groupname end as doctorName";
        String groupBySql = "wf.id,wf.dtype, vwf.name, p.lastname ,p.firstname,p.middlename,wf.groupname";
        String orderBySql = "p.lastname ,p.firstname,p.middlename";
        String[] jsonFields = {"workfunction_id", "workfunction_name", "doctor_name"};

        String sqlAdd = serviceStream + " and vwf.id=" + vocWorkfunctionId;
        if (departmentId != null && !departmentId.equals("")) {
            sqlAdd += " and mlGr.id=" + departmentId;
        }
        String data = getData(selectSql, sqlAdd, orderBySql, groupBySql, jsonFields, 100, webQueryService);
        if (isWantedDefaultMedServices == null || !isCharged) {
            return data;
        } else { //только для платных
            JSONObject ret = new JSONObject();
            JSONArray dataBody = new JSONArray();
            JSONArray arr = new JSONObject(data).getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                obj.put("listMedServices", new JSONArray(getDefaultMedServicesAndPrices(obj.getString("workfunction_id"), webQueryService)));
                dataBody.put(obj);
            }
            ret.put("data", dataBody);
            return ret.toString();
        }
    }

    /**
     * Возвращает свободные даты для выбранного врача (либо специальности, если врач не указан)
     *
     * @param workfunctionId    ИД доктора
     * @param vocWorkfunctionId ИД специальности
     * @param serviceStreamCode Поток обслуживания
     * @param departmentId      ИД отделения
     * @param webQueryService   sql сервис
     * @return Список свободных дат
     */

    public String getFreeCalendarDaysByWorkFunction(String workfunctionId, String vocWorkfunctionId, String serviceStreamCode, String departmentId, IWebQueryService webQueryService) {
        serviceStreamCode = getServiceStreamSqlAdd(serviceStreamCode);
        if (serviceStreamCode == null) {
            return getErrorJson(BAD_SERVICE_STREAM_TEXT, ERRORSERVICESTREAM);
        }
        StringBuilder sqlAdd = new StringBuilder();

        String[] jsonFields = {"calendarDay_id", "calendarDate", "prettyCalendarDate"};
        if (workfunctionId != null && !workfunctionId.equals("")) { //Ищем свободные времена по конкретной раб. функции
            sqlAdd.append(" and wf.id=").append(workfunctionId);
        } else { //Ищем свободные времена по любой раб. функции указанной специальности
            if (vocWorkfunctionId != null) {
                sqlAdd.append(" and vwf.id=").append(vocWorkfunctionId);
                if (departmentId != null && !departmentId.equals("")) {
                    sqlAdd.append(" and mlGr.id=").append(departmentId);
                }
            } else {
                return getErrorJson(BAD_WORKFUNCTION_TEXT, ERRORWORKFUNCTION);
            }
        }
        sqlAdd.append(" and (wcd.calendardate>current_date or wcd.calendardate=current_date and wct.timeFrom>current_time)")
                .append(serviceStreamCode);

        return getData(" max(wcd.id) as id, to_char(wcd.calendardate,'yyyy-MM-dd') as calendarDate, to_char(wcd.calendardate,'dd.MM.yyyy') as prettyCalendarDate" //Ищем свободные времена по конкретной раб. функции //Ищем свободные времена по любой раб. функции указанной специальности
                , sqlAdd.toString(), "wcd.calendardate", "wcd.calendardate", jsonFields, 30, webQueryService);
    }

    /**
     * Находим свободные времени либо по должности (все неврологи), либо по кокретной рабочей функции (невролог Иванов)
     *
     * @param calendarDayId     ИД дня
     * @param vocWorkfunctionId ИД должности
     * @param calendarDate      Дата приема
     * @param serviceStreamCode Поток обслуживания
     * @param departmentId      ИД отделения
     * @param webQueryService   sql сервис
     * @return Список свободных времен
     */
    public String getFreeCalendarTimesByCalendarDate(String calendarDayId, String vocWorkfunctionId, String calendarDate, String serviceStreamCode, String departmentId, IWebQueryService webQueryService) {
        serviceStreamCode = getServiceStreamSqlAdd(serviceStreamCode);
        if (serviceStreamCode == null) {
            return getErrorJson(BAD_SERVICE_STREAM_TEXT, ERRORSERVICESTREAM);
        }
        StringBuilder sqlAdd = new StringBuilder();
        String groupBySql = "wct.id, wct.timeFrom";
        String orderBySql = "wct.timeFrom";
        String[] jsonFields = {"calendarTime_id", "calendarTime"};
        if (calendarDayId != null && !calendarDayId.equals("")) { //Ищем по конкретному специалисту
            sqlAdd.append(" and wcd.id=").append(calendarDayId);
        } else if (vocWorkfunctionId != null && !vocWorkfunctionId.equals("") && calendarDate != null) { //Ищем по всем врачам выбранной специальности
            sqlAdd.append(" and wvf.id=").append(vocWorkfunctionId).append(" and wcd.calendardate = to_date('yyyy-MM-dd','").append(calendarDate).append("')");
            if (departmentId != null && !departmentId.equals("")) {
                sqlAdd.append(" and mlGr.id=").append(departmentId);
            }
        } else {
            return getErrorJson("Неверное значение параметра 'Дата приема'", ERRORVISITDATE);
        }

        sqlAdd.append(" and case when wcd.calendardate=current_date and wct.timeFrom>current_time then 1 when wcd.calendardate>current_date then 1 else 0 end =1").append(serviceStreamCode);
        return getData("wct.id as id, cast(wct.timefrom as varchar(5)) as calendarTime" //Ищем по конкретному специалисту //Ищем по всем врачам выбранной специальности
                , sqlAdd.toString(), orderBySql, groupBySql, jsonFields, 50, webQueryService);
    }

    /**
     * Аннулируем направление (соответствие по пациенту и времени
     *
     * @param calendarTimeId  ИД времени
     * @param lastname        Фамилия пациента
     * @param firstname       Имя пациента
     * @param middlename      Отчяество пациента
     * @param birthday        Дата рождения пациента
     * @param webQueryService sql сервис
     * @return Информация об аннулировании записи
     */

    public String annulRecord(Long calendarTimeId, String lastname, String firstname, String middlename, Date birthday, IApiRecordService webQueryService) {
        return webQueryService.annulRecord(calendarTimeId, lastname, firstname, middlename, birthday);
    }
//TODO проверить, если не планируется использовать - убрать!
   /* public String getRecordInformation(String aWorkcalendarTimeId, IWebQueryService aService) {
        // LOG.info("sql="+sql);
        String[] jsonFields = {"workcalendartime_id", "workCalendarTimeId", "patientInfo", "calendarDate", "timeFrom", "doctorName", "lpuCode", "patientUID", "medcaseId", "patientId", "serviceStream", "state", "recordComment"};
        String sql = "select wct.id as id,wct.id as id2, case when pat.id is not null then pat.patientinfo else wct.prepatientinfo end as patientInfo" +
                ",wcd.calendardate as calendarDate" +
                ",cast(wct.timefrom as varchar(5)) as timeFrom" +
                ",case when wpat.id is not null then vwf.name|| ' '|| wpat.lastname||' '||wpat.firstname||' '||wpat.middlename else wf.groupname end as doctorName" +
                ",'AMOKB' as lpuCode, pesa.externalcode as patientUID" +
                ",pat.id as patientId" +
                ",wct.medcase_id as medcaseId" +
                ",case when wct.reserveType_id is not null then vsrt.code  else 'OMC' end as serviceStream" +
                ",case when wct.medcase_id is null then 'PRERECORD'" +
                " else case when vis.noactuality='1' then 'ANNUL' when vis.datestart is not null then 'MADE' else 'RECORD' end end as recordState" +
                ", case when vsrt.id is not null then coalesce(vsrt.recordComment,'') else 'Вам необходимо подойти за 10 минут до приема в регистратуру для оформления мед. документации(здание КДЦ, первый этаж)' end as recordComment" +
                " from workcalendartime wct" +
                " left join medcase vis on vis.id=wct.medcase_id" +
                " left join vocservicereservetype vsrt on vsrt.id=wct.reserveType_id" +
                " left join patient pat on pat.id=wct.prepatient_id" +
                " left join patientexternalserviceaccount pesa on pesa.patient_id=pat.id and pesa.dateto is null" +
                " left join workcalendarday wcd on wcd.id=wct.workCalendarDay_id" +
                " left join workcalendar wc on wc.id=wcd.workcalendar_id" +
                " left join workfunction wf on wf.id=wc.workFunction_id" +
                " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
                " left join Worker w on w.id=wf.worker_id" +
                " left join Patient wpat on wpat.id=w.person_id" +
                " where wct.id=" + Long.valueOf(aWorkcalendarTimeId);
        String ret = aService.executeNativeSqlGetJSON(jsonFields, sql, 1);
        try {
            JSONArray arr = new JSONArray(ret);
            if (arr.length() > 0) {
                JSONObject obj = arr.getJSONObject(0);
                obj.put("status", "ok");
                LOG.info("Пациент успешно записан" + obj.toString());
                return obj.toString();
            }
        } catch (JSONException e) {
            LOG.error(e.getMessage(), e);
        }
        return getErrorJson("Не удалось вернуть информацию о записи", "JSON_COMMON");
    }*/
}
