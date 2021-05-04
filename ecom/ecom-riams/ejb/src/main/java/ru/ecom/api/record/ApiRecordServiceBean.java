package ru.ecom.api.record;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.licence.ExternalDocument;
import ru.ecom.mis.ejb.domain.licence.voc.VocExternalDocumentType;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWayOfRecord;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Сервис для работы с записью на прием
 */

@Stateless
@Local(IApiRecordService.class)
@Remote(IApiRecordService.class)
public class ApiRecordServiceBean implements IApiRecordService {
    private static final Logger LOG = Logger.getLogger(ApiRecordServiceBean.class);
    private static final String ANNUL_ERROR = "ANNUL_ERROR";
    @Resource
    SessionContext context;
    private @PersistenceContext
    EntityManager manager;

    private Patient getPatientByFIO(String aLastname, String aFirstname, String aMiddlename, Date aBirthdate) {
        return getPatientByGuidOrFio(aLastname, aFirstname, aMiddlename, aBirthdate);
    }

    /**
     * Поиск пациента по ФИО или ГУИД личного кабинета
     */
    private Patient getPatientByGuidOrFio(String aLastname, String aFirstname, String aMiddlename, Date aBirthdate) {
        StringBuilder findSql = new StringBuilder();
        findSql.append("select id from patient where upper(lastname)=upper('").append(aLastname).append("')")
                .append(" and upper(firstname)=upper('").append(aFirstname).append("')");
        if (!StringUtil.isNullOrEmpty(aMiddlename)) {
            findSql.append(" and upper(middlename)=upper('").append(aMiddlename).append("')");
        }
        findSql.append(" and birthday=to_date('").append(DateFormat.formatToDate(aBirthdate)).append("','dd.MM.yyyy')");
        List<BigInteger> list = manager.createNativeQuery(findSql.toString()).getResultList();
        if (list.size() != 1) {
            LOG.warn("search patient sql = " + findSql + " .found = " + list.size());
            return null;
        }
        return manager.find(Patient.class, list.get(0).longValue());
    }

    /**
     * Запись пациента с промеда
     * Находим подходящее время
     */
    public String recordPromedPatient(String aPromedDoctorId, String aLastname, String aFirstname, String aMiddlename, Date aBirthdate, Date aCalendarDate, Time aCalendarTime, String aPhone) {
        List<WorkFunction> workFunctions = manager.createQuery("from WorkFunction where promedCodeWorkstaff=:promedCode")
                .setParameter("promedCode", aPromedDoctorId).getResultList();
        if (workFunctions.size() != 1) {
            LOG.error("Невозможно записать пациента, т.к. по коду " + aPromedDoctorId + " найдено " + workFunctions.size() + " рабочих функций");
            return getErrorJson("По коду " + aPromedDoctorId + " не найдено рабочей функции", "NO_PROMED_DOCTOR");
        }
        WorkFunction doctor = workFunctions.get(0);
        LOG.info(DateFormat.formatToTime(aCalendarTime) + "=====");
        List<BigInteger> workCalendarTimeList = manager.createNativeQuery("select wct.id from workfunction wf" +
                " left join workcalendar wc on wf.id = wc.workfunction_id" +
                " left join workcalendarday wcd on wc.id = wcd.workcalendar_id" +
                " left join workcalendartime wct on wcd.id = wct.workcalendarday_id" +
                " left join vocservicereservetype vsrt on wct.reservetype_id = vsrt.id " +
                " where wf.id=:doctorId and wcd.calendardate=:calendarDate and wct.timefrom =:timeFrom " +
                " and vsrt.code='PROMED'" +
                " and (wcd.isDeleted is null or wcd.isDeleted='0') and (wct.isDeleted is null or wct.isDeleted='0')" +
                " and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='')"
        ).setParameter("doctorId", doctor).setParameter("calendarDate", aCalendarDate).setParameter("timeFrom", aCalendarTime).getResultList();
        if (workCalendarTimeList.isEmpty()) {
            workCalendarTimeList = manager.createNativeQuery("select wct.id from workfunction wf" +
                    " left join workcalendar wc on wf.id = wc.workfunction_id" +
                    " left join workcalendarday wcd on wc.id = wcd.workcalendar_id" +
                    " left join workcalendartime wct on wcd.id = wct.workcalendarday_id" +
                    " left join vocservicereservetype vsrt on wct.reservetype_id = vsrt.id " +
                    " where wf.id=:doctorId and wcd.calendardate=:calendarDate " +
                    " and vsrt.code='PROMED' and wct.timefrom between '" + DateFormat.formatToTime(aCalendarTime) + "' -interval '2 minutes' and '" + DateFormat.formatToTime(aCalendarTime) + "' +interval '2 minutes'" +
                    " and (wcd.isDeleted is null or wcd.isDeleted='0') and (wct.isDeleted is null or wct.isDeleted='0')" +
                    " and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='')"
            ).setParameter("doctorId", doctor).setParameter("calendarDate", aCalendarDate).getResultList();
            if (workCalendarTimeList.isEmpty()) {
                LOG.error("Не найдено подходящего времени");
                return getErrorJson("Не найдено свободных времен к врачу: " + doctor.getWorkFunctionInfo() + " на " + aCalendarDate + " " + aCalendarTime, "NO_PROMED_FREE_TIME");
            }
        }
        return recordPatient(workCalendarTimeList.get(0).longValue(), aLastname, aFirstname, aMiddlename, aBirthdate, null, null, aPhone);
    }

    /**
     * Запись пациента на прием (если у резерва разрешено записывать без предварит. направление, создаем направление
     *
     * @param aCalendarTimeId    ИД записи
     * @param aPatientLastname   Фамилия пациента
     * @param aPatientFirstname  Имя пациента
     * @param aPatientMiddlename Отчество пациента
     * @param aPatientBirthday   Дата рождения пациента
     * @return Информация о записи
     */
    public String recordPatient(Long aCalendarTimeId, String aPatientLastname, String aPatientFirstname, String aPatientMiddlename, Date aPatientBirthday, String aComment, String aPhone, String aRecordType) {
        long currentDateLong = System.currentTimeMillis();
        try {
            if (AgeUtil.calcAgeYear(aPatientBirthday, new Date(currentDateLong)) > 122) {
                LOG.warn("Пациент старше 122 лет. Это маловероятно");
                return getErrorJson("Запись пациента старше 122 лет невозможна", "TOO_OLD");
            }
        } catch (Exception e) {
            return getErrorJson("Проверьте дату рождения пациента", "TOO_YOUNG");
        }
        WorkCalendarTime wct = manager.find(WorkCalendarTime.class, aCalendarTimeId);
        if (wct == null) {
            LOG.error("Не найдено время");
            return getErrorJson("Неизвестная ошибка при записи, обратитесь к программистам", "SOME_UNKNOWN_ERROR");
        }

        Patient patient;
        if (!StringUtil.isNullOrEmpty(aPatientLastname) && !StringUtil.isNullOrEmpty(aPatientFirstname)) { //Запись через сайт или другие источники.
            patient = getPatientByFIO(aPatientLastname, aPatientFirstname, aPatientMiddlename, aPatientBirthday);
        } else {
            return getErrorJson("Необходимо указать ФИО пациента", "NO_PATIENT");
        }
        String prePatientInfo = aPatientLastname + " " + aPatientFirstname + " " + (aPatientMiddlename != null ? aPatientMiddlename : "") + " " + DateFormat.formatToDate(aPatientBirthday) + (aPhone != null ? " тел." + aPhone : "");

        if (wct.getPrePatient() != null
                || wct.getMedCase() != null
                || !StringUtil.isNullOrEmpty(wct.getPrePatientInfo())) {
            LOG.error("На это время невозможно записаться");
            return getErrorJson("Невозможно записаться на время - время уже занято", "BUSY_TIME");
        }
        Date currentDate = new Date(currentDateLong);
        Time currentTime = new Time(currentDateLong);
        String username = "ApiClient " + context.getCallerPrincipal().getName();
        StringBuilder recordLogInfo = new StringBuilder().append("Записан пациент через API. Пользователь: ").append(username).append(", дата создания:")
                .append(currentDate).append(" время:").append(currentTime).append(", WCT_ID").append(aCalendarTimeId).append(". Дата записи:").append(wct.getWorkCalendarDay().getCalendarDate())
                .append(", время:").append(wct.getTimeFrom());
        if (wct.getReserveType() != null && Boolean.TRUE.equals(wct.getReserveType().getIsNoPreRecord())
                && patient != null) { //Создаем визит
            VocServiceStream serviceStream = getServiceStreamByWorkCalendarTime(wct);
            Visit visit = new Visit();
            visit.setUsername(username);
            visit.setCreateDate(currentDate);
            visit.setCreateTime(currentTime);
            visit.setNoActuality(false);
            visit.setServiceStream(serviceStream);
            visit.setDatePlan(wct.getWorkCalendarDay());
            visit.setTimePlan(wct);
            visit.setWorkFunctionPlan(wct.getWorkCalendarDay().getWorkCalendar().getWorkFunction());
            //visit.setWorkPlaceType();
            visit.setOrderWorkFunction(null);
            visit.setPatient(patient);
            manager.persist(visit);
            wct.setMedCase(visit);
            recordLogInfo.append("Пациент: ").append(patient.getPatientInfo()).append(". Создан визит c ИД:").append(visit.getId());
        } else { //Создаем предварительную запись
            wct.setCreatePreRecord(username);
            wct.setCreateDatePreRecord(currentDate);
            wct.setCreateTimePreRecord(currentTime);
            if (aRecordType != null) {
                try {
                    wct.setWayOfRecord((VocWayOfRecord) manager.createQuery("from VocWayOfRecord where code=:way").setParameter("way", aRecordType).getResultList().get(0));
                } catch (Exception e) {
                    wct.setWayOfRecord(null);
                    LOG.error("Не смог определить способ записи по коду: " + aRecordType);
                }

            } else {
                wct.setWayOfRecord(null);
            }
            if (patient != null) {
                wct.setPrePatient(patient);
            } else {
                wct.setPrePatientInfo(prePatientInfo.toUpperCase());
            }
            recordLogInfo.append(". Пациент ").append(prePatientInfo.toUpperCase());
            wct.setPatientComment(aComment);
            recordLogInfo.append(".Создана предварительная запись");
        }
        manager.persist(wct);
        manager.persist(new ApiRecordJournal(recordLogInfo.toString()));
        JSONObject ret = new JSONObject(createJson("successCalendarTimeId", wct.getId() + "", null, null));
        ret.put("patientInfo", patient != null ? patient.getPatientInfo() : prePatientInfo);
        ret.put("recordDate", DateFormat.formatToDate(wct.getWorkCalendarDay().getCalendarDate()));
        ret.put("recordTime", DateFormat.formatToTime(wct.getTimeFrom()));
        String doctorInfo;
        WorkFunction wf = wct.getWorkCalendarDay().getWorkCalendar().getWorkFunction();
        if (wf instanceof PersonalWorkFunction) {
            doctorInfo = wf.getWorkFunctionInfo();
        } else {
            doctorInfo = wf.getInfo();
        }
        ret.put("recordDoctor", doctorInfo);
        return ret.toString();
    }

    /**
     * Аннулирование записи на прием ( если не создан визит)
     *
     * @param aCalendarTimeId
     * @param aLastname
     * @param aFirstname
     * @param aMiddlename
     * @return
     */
    public String annulRecord(Long aCalendarTimeId, String aLastname, String aFirstname, String aMiddlename, Date aBirthday) {
        Patient patient;
       /* if (aPatientGUID!=null) { //Аннулируем по ГУИД
            patient=getPatientByGuid(aPatientGUID);
        } else */
        if (aLastname != null) {
            patient = getPatientByFIO(aLastname, aFirstname, aMiddlename, aBirthday);
        } else {
            return getErrorJson("При аннулировании необходимо указать GUID или ФИО + ДР пациента", "WRONG_PAR");
        }
        WorkCalendarTime wct = manager.find(WorkCalendarTime.class, aCalendarTimeId);
        if (patient != null && wct != null && wct.getPrePatient() != null) { //Аннулируем если только точно нашли пациента!
            if (wct.getMedCase() != null && wct.getMedCase().getDateStart() != null) {
                return getErrorJson("Ошибка аннулирования записи, визит уже оформлен", ANNUL_ERROR);
            }
            if (patient.getId() == wct.getPrePatient().getId() || (wct.getMedCase() != null && wct.getMedCase().getPatient().getId() == patient.getId())) {
                //аннулируем, если только нашли соответствие. В целях безопасности - не будем аннулировать предварительные направления с неидентифицированными пациентами
                if (wct.getMedCase() != null) {
                    Visit visit = (Visit) wct.getMedCase();
                    visit.setNoActuality(true);
                    visit.setVisitResult(null);
                    visit.setTimePlan(null);
                    manager.persist(visit);
                    wct.setMedCase(null);
                } else {
                    wct.setPrePatientInfo(null);
                    wct.setPrePatient(null);
                }
                manager.persist(wct);
                return new JSONObject().put("status", "ok").put("info", "Запись успешно аннулирована").put("calendarTimeId", "" + wct.getId()).toString();
            }
        } else {
            return getErrorJson("Ошибка аннулирования записи, возможно, пациент не идентифицирован ", ANNUL_ERROR);
        }
        return getErrorJson("Неизвестная ошибка аннулирования записи, возможно, визит уже оформлен", ANNUL_ERROR);

    }

    /**
     * Сохраняем внешний документ
     */
    public Long saveExternalDodument(String aFilename, String base64String, Long aPatientId, Long aMedcaseId, Long aCalendartimeId, String aDocumentType) {
        //LOG.info("record File "+aFilename+"<>"+aPatientId+"<>"+aMedcaseId+"<>"+aCalendartimeId+"<>"+aDebug);
        aFilename = saveFile(aFilename, base64String);
        if (aFilename != null) { //Файл сохранен в нужную папку, создаем "сущность"
            long currentDateLong = System.currentTimeMillis();
            String username = "ApiClient " + context.getCallerPrincipal().getName();
            ExternalDocument document = new ExternalDocument();
            document.setCreateDate(new Date(currentDateLong));
            document.setCreateTime(new Time(currentDateLong));
            document.setCreateUsername(username);
            document.setReferenceTo(aFilename);
            if (aPatientId != null) {
                document.setPatient(manager.find(Patient.class, aPatientId));
            }
            if (aMedcaseId != null) {
                document.setMedCase(manager.find(MedCase.class, aMedcaseId));
            }
            if (aCalendartimeId != null) {
                document.setCalendarTime(manager.find(WorkCalendarTime.class, aCalendartimeId));
            }
            if (!StringUtil.isNullOrEmpty(aDocumentType)) {
                List<VocExternalDocumentType> list = manager.createQuery(" from VocExternalDocumentType where code=:code").setParameter("code", aDocumentType).getResultList();
                if (!list.isEmpty()) {
                    document.setType(list.get(0));
                }
            }
            manager.persist(document);

            return document.getId();
        }
        return null;
    }

    /**
     * Сохраняем файл/изображение
     */
    public String saveFile(String aFilename, String base64String) {
        String rootFolder = getJbossConfigValue("tomcat.image.dir", "/opt/tomcat/webapps/docmis");
        if (aFilename == null) {
            return null;
        }
        File file = new File(rootFolder + aFilename);
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file))) {
            aFilename = getMedosFilename(aFilename, null, rootFolder);
            byte[] base64EncodedData = Base64.decodeBase64(base64String.getBytes());
            writer.write(base64EncodedData);
            writer.flush();
            return aFilename;
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    /**
     * Формируем имя файла в соответствии с иерархией, принятой в медосе
     */
    private String getMedosFilename(String aFilename, String aDocumentType, String rootFolder) {
        String[] filenames = aFilename.split("\\.");
        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
        StringBuilder newFilename = new StringBuilder().append("/orig")
                .append("/").append(aDocumentType != null ? aDocumentType : "other")
                .append("/").append(format.format(currentDate));
        if (!new File(rootFolder + newFilename.toString()).exists()) {
            new File(rootFolder + newFilename.toString()).mkdirs();
        }
        newFilename.append("/").append(currentDate.getTime()).append("-").append("iBolit")
                .append(".").append(filenames[filenames.length - 1]);
        LOG.info("orig filename = " + aFilename + ", new filename = " + newFilename.toString());
        return newFilename.toString();


    }

    private String getJbossConfigValue(String aConfigName, String aDefaultValue) {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get(aConfigName, aDefaultValue);

    }

    private String getErrorJson(String aReasonText, String aCode) {
        String err = createJson(null, null, aCode, aReasonText);
        LOG.error("ERROR_JSON " + err);
        return err;
    }

    private String createJson(String aElementName, String aJsonData, String aErrorCode, String aErrorName) {
        JSONObject ret = new JSONObject();
        if (aElementName != null) {
            ret.put(aElementName, aJsonData);
        }
        ret.put("status", aErrorCode != null ? "error" : "ok");
        if (aErrorCode != null) {
            ret.put("error_code", aErrorCode);
            ret.put("error_name", aErrorName);
        }
        return ret.toString();
    }

    /**
     * Находим подходящий поток обслуживания по типу резерва
     */
    private VocServiceStream getServiceStreamByWorkCalendarTime(WorkCalendarTime aWct) {
        String sql = null;
        VocServiceStream vocServiceStream = null;
        if (aWct.getReserveType() == null || StringUtil.isNullOrEmpty(aWct.getReserveType().getServiceStreams())) {
            sql = "code='OBLIGATORYINSURANCE'";
        } else {
            String[] ids = aWct.getReserveType().getServiceStreams().split(",");
            if (ids.length > 1) {
                sql = "id=" + ids[1];
            }

        }
        if (sql != null) {
            vocServiceStream = (VocServiceStream) manager.createQuery("from VocServiceStream where " + sql).getSingleResult();
        }
        return vocServiceStream;
    }
}
