package ru.ecom.mis.web.dwr.expert2;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.expert2.domain.E2Bill;
import ru.ecom.expert2.service.IExpert2AlkonaService;
import ru.ecom.expert2.service.IExpert2Service;
import ru.ecom.expert2.service.IExpert2XmlService;
import ru.ecom.expert2.service.IFinanceService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;

import static ru.nuzmsh.util.BooleanUtils.isTrue;
import static ru.nuzmsh.util.StringUtil.isNotEmpty;
import static ru.nuzmsh.util.StringUtil.isNullOrEmpty;

public class Expert2ServiceJs {
    private static final Logger LOG = Logger.getLogger(Expert2ServiceJs.class);

    //выписки по заполнению в алькону
    public void exportHospLeaveToAlkona(Long entryListId, HttpServletRequest request) throws NamingException {
        Injection.find(request).getService(IExpert2AlkonaService.class).exportHospLeaveToAlkona(entryListId);
    }

    //Выписку одного случая в Алькону
    public String exportHospLeaveEntryToAlkona(Long entryId, HttpServletRequest request) throws NamingException {
        String ret = Injection.find(request).getService(IExpert2AlkonaService.class).exportHospLeaveEntryToAlkona(entryId);
        LOG.info(ret);
        return ret;
    }

    //госпитализации по заполнению в алькону
    public void exportHospToAlkona(Long entryListId, Boolean isEmergency, HttpServletRequest request) throws NamingException {
        Injection.find(request).getService(IExpert2AlkonaService.class).exportHospToAlkona(entryListId, isEmergency);
    }

    //Одну госпитализацию в Алькону
    public String exportHospEntryToAlkona(Long entryId, HttpServletRequest request) throws NamingException {
        return Injection.find(request).getService(IExpert2AlkonaService.class).exportHospEntryToAlkona(entryId);
    }

    public static String getMedcaseCost(Long medcaseId, HttpServletRequest request) throws NamingException {
        return Injection.find(request).getService(IExpert2Service.class).getMedcaseCost(medcaseId);
    }

    public void deleteAllDrugByCancer(Long aCancerEntryId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("delete from e2cancerdrugdate where drug_id in (select id from E2CancerDrug where  cancerEntry_id=" + aCancerEntryId + ")");
        service.executeUpdateNativeSql("delete from E2CancerDrug where cancerEntry_id=" + aCancerEntryId);
    }

    public void addDeleteEntryFactor(Long entryId, Long vocFactorId, Boolean needDelete, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (isTrue(needDelete)) {
            service.executeUpdateNativeSql("delete from e2entry_factor where entry_id =" + entryId + " and factor_id= " + vocFactorId + "");
        } else {
            service.executeUpdateNativeSql("insert into e2entry_factor (entry_id, factor_id) values (" + entryId + "," + vocFactorId + ")");
        }
    }

    public String getDefaultLpuOmcCode(HttpServletRequest aRequest) throws NamingException {
        return Injection.find(aRequest).getService(IWebQueryService.class)
                .executeNativeSql("select coalesce(omccode,'0') from softconfig sc" +
                        " left join mislpu ml on ml.id=cast(sc.keyvalue as int)" +
                        " where sc.key='DEFAULT_LPU' ").iterator().next().get1().toString();
    }

    public String fixSomeErrors(Long aListEntryId, String aErrorCode, HttpServletRequest aRequest) throws NamingException, SQLException {
        if ("223".equals(aErrorCode)) {
            LOG.info("Проверяем по базе ТФОМС и проставляем действующие полиса на дату начала случая");
            return Injection.find(aRequest).getService(IExpert2Service.class)
                    .fixFondAnswerError(aListEntryId, aErrorCode);
        } else if ("CV_DEATH".equals(aErrorCode)) {
            LOG.info("Перенос диагнозов из реанимации в род. отделение");
            String sql = "update EntryDiagnosis ed set entry_id =child.parentEntry_id from " +
                    " e2entry child where child.listEntry_id=" + aListEntryId + " and (child.isDeleted is null or child.isDeleted='0') and child.serviceStream='COMPLEXCASE'" +
                    " and child.parentEntry_id is not null and ed.entry_id=child.id";
            Injection.find(aRequest).getService(IWebQueryService.class)
                    .executeUpdateNativeSql(sql);
            return "All OK";
        } else if ("AUTOSMO".equals(aErrorCode)) { //изменить код страх компании на СОГАЗ
            int insuranceCode = 30002; //СОГАЗ
            LOG.info("Заменяем пустой код страховой компании на СОГАЗ " + insuranceCode);
            Injection.find(aRequest).getService(IWebQueryService.class)
                    .executeUpdateNativeSql("update e2entry set smocode = '" + insuranceCode + "' where listentry_id=" + aListEntryId + " and (smocode is null or smocode='')" +
                            " and (isDeleted is null or e.isDeleted='0') " +
                            " and (isForeign is null or isForeign is false) ");
            return "Страховая компания заменена на " + insuranceCode;
        } else {
            return "Я не понял что мне делать!";
        }
    }

    public void deleteAllDeletedEntries(HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        LOG.info("All entries = " + service.executeNativeSql("select count(id) from e2entry").iterator().next().get1().toString());
        service.executeUpdateNativeSql("delete from e2entry where isDeleted='1'");
        LOG.info("All entries2 = " + service.executeNativeSql("select count(id) from e2entry").iterator().next().get1().toString());
        service.executeUpdateNativeSql("delete from e2listentry where isDeleted='1'");
    }

    public void makeOncologyCase(Long listEntryId, String jsonData, String defectCode, HttpServletRequest request) throws NamingException {
        Injection.find(request).getService(IExpert2Service.class).makeOncologyCase(listEntryId, jsonData, defectCode);
    }

    public String exportToCentralSegment(Long listEntryId, String historyNumbers, HttpServletRequest request) throws NamingException {
        return Injection.find(request).getService(IExpert2XmlService.class)
                .exportToCentralSegment(listEntryId, historyNumbers);
    }

    public String splitLongCase(Long entryId, HttpServletRequest request) throws NamingException {
        return Injection.find(request).getService(IExpert2Service.class)
                .splitLongCase(entryId);
    }

    /*Меняем кардиологию на сосуды *только для АМОКБ */
    public String changeToSosud(Long entryId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        try {
            WebQueryResult webQueryResult = service.executeNativeSql("select coalesce(voc.code,'') from e2entry e left join voce2medhelpprofile voc on voc.id=e.medhelpprofile_id where e.id=" + entryId).iterator().next();
            if ("29".equals(webQueryResult.get1().toString())) {
                service.executeUpdateNativeSql("delete from entrymedservice where entry_id =" + entryId);
                service.executeUpdateNativeSql("insert into entrymedservice (medservice_id, entry_id, servicedate) (select (select max(id) from vocmedservice where code='A16.12.028' and finishdate is null),e.id,e.startDate from e2entry e where e.id =" + entryId + ")");
                service.executeUpdateNativeSql("update e2entry set medhelpprofile_id=258,fonddoctorspec_id=133, fonddoctorspecv021_id=65, doctorworkfunction='64' where id = " + entryId);
                return "Успешно сделано!";
            } else {
                return "Профиль - не кардиологический, ничего менять не буду!";
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return "Ошибка: " + e.getMessage();
        }


    }

    public String splitForeignOtherBill(Long listEntryId, String billNumber, String billDateString, String territories, HttpServletRequest requst) throws NamingException, ParseException {
        Date billDate = DateFormat.parseSqlDate(billDateString);
        return Injection.find(requst).getService(IExpert2Service.class).splitForeignOtherBill(listEntryId, billNumber, billDate, territories);
    }

    public void fillDirectDatePlanHosp(Long listEntryId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update e2entry set directDate = startDate where listentry_id=" + listEntryId +
                " and entryType='HOSPITAL' and directDate is null and (isEmergency is null or isEmergency='0') and (isDeleted is null or isDeleted='0')");
        service.executeUpdateNativeSql("update e2entry set planHospDate = startDate where listentry_id=" + listEntryId +
                " and entryType in ('VMP','HOSPITAL') and planHospDate is null and (isEmergency is null or isEmergency='0') and (isDeleted is null or isDeleted='0')");

    }

    public String fillAggregateTable(String aType, String aStartDate, String aFinishDate, String aServiceStream, HttpServletRequest aRequest) throws NamingException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        java.sql.Date startDate = new java.sql.Date(format.parse(aStartDate).getTime());
        java.sql.Date finishDate = new java.sql.Date(format.parse(aFinishDate).getTime());
        return Injection.find(aRequest).getService(IFinanceService.class)
                .fillAggregateTable(aType, startDate, finishDate, aServiceStream);
    }

    /***/
    public void splitFinancePlan(String aType, String aYear, HttpServletRequest aRequest) throws NamingException {
        Injection.find(aRequest).getService(IFinanceService.class)
                .splitFinancePlan(aType, aYear);
    }

    /**
     * Копируем финансовый план на несколько месяцев (MM.yyyy)
     */
    public void copyFinancePlanNextMonth(String aCurrentMonth, String aStartMonth, String aFinishMonth, HttpServletRequest aRequest) throws NamingException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM.yyyy");
        Date planDate = new java.sql.Date(format.parse(aCurrentMonth).getTime());
        Date startDate = new java.sql.Date(format.parse(aStartMonth).getTime());
        Date finishDate = new java.sql.Date(format.parse(aFinishMonth).getTime());
        LOG.info(Injection.find(aRequest).getService(IFinanceService.class)
                .copyFinancePlanNextMonth(null, planDate, startDate, finishDate));

    }

    /**
     * Пересчитать заполнение (удаляем существующие записи и формируем новые в существующее заполнение)
     */
    public long refillListEntry(Long aListEntryId, HttpServletRequest aRequest) throws NamingException {
        final long monitorId = createMonitor(aRequest);
        Injection.find(aRequest).getService(IExpert2Service.class).reFillListEntry(aListEntryId, monitorId);
        return monitorId;
    }

    /**
     * Журнал сформированных пакетов/счетов
     */
    public String getPacketJournalByBillNumber(String aBillNumber, String aBillDate, HttpServletRequest aRequest) throws NamingException {
        StringBuilder sql = new StringBuilder("select billNumber, to_char(billDate,'dd.MM.yyyy') as billdate,  filename, to_char(createdate,'dd.MM.yyyy')||' '||cast(createtime as varchar(5)) as createdatetime" +
                " from E2ExportPacketJournal");

        if (isNotEmpty(aBillNumber)) {
            sql.append(" where billNumber='").append(aBillNumber).append("'");
            if (isNotEmpty(aBillDate)) {
                sql.append(" and billDate=to_date('").append(aBillDate).append("','dd.MM.yyyy')");
            }
        }
        sql.append(" order by createdate desc, createtime desc");
        return Injection.find(aRequest).getService(IWebQueryService.class).executeNativeSqlGetJSON(new String[]{"billNumber", "billDate", "filename", "createDate"}, sql.toString(), 10);
    }

    /**
     * Объединить заполнение (перенос записей из старого в новое и пометка старого как удаленного
     */
    public void unionListEntries(Long oldListEntryId, Long newListEntryId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String sql = "update e2entry set listentry_id=" + newListEntryId + " where listentry_id=" + oldListEntryId + " and (isDeleted is null or isDeleted='0')";
        service.executeUpdateNativeSql(sql);
        service.executeUpdateNativeSql("update e2listentry set isDeleted='1' where id=" + oldListEntryId);
    }


    /**
     *
     */
    public void addHospitalMedCaseToList(String historyNumbers, Long listEntryId, HttpServletRequest request) throws NamingException, SQLException {
        Injection.find(request).getService(IExpert2Service.class).addHospitalMedCaseToList(historyNumbers, listEntryId);
    }

    /**
     * Отмечаем записи в заполнении как повторную подачу
     */
    public void markAsReSend(Long listEntryId, boolean isReSend, HttpServletRequest request) throws NamingException {
        Injection.find(request).getService(IWebQueryService.class).executeUpdateNativeSql("update e2entry set prnov='" + (isReSend ? "1" : "0") + "' where listEntry_id=" + listEntryId + " and (isDeleted is null or isDeleted='0')");
    }

    /**
     * Добавляем диагноз и услугу к случаю
     */
    public Boolean addDiagnosisAndServiceToEntry(Long entryId, String someData, HttpServletRequest aRequest) throws NamingException {
        return Injection.find(aRequest).getService(IExpert2Service.class).addDiagnosisAndServiceToEntry(entryId, someData);
    }

    public void exportErrorsNewListEntry(Long listEntryId, String errorCodes, String sanctionDopCodes, HttpServletRequest request) throws NamingException {
        Injection.find(request).getService(IExpert2Service.class)
                .exportErrorsNewListEntry(listEntryId, errorCodes != null ? errorCodes.split(",") : new String[0]
                        , sanctionDopCodes != null ? sanctionDopCodes.split(",") : new String[0]);
    }

    /**
     * Выгрузить дефекты в новое заполнение
     */
    public boolean exportDefectNewListEntry(Long listEntryId, HttpServletRequest request) throws NamingException {
        return Injection.find(request).getService(IExpert2Service.class).exportDefectNewListEntry(listEntryId);
    }

    /**
     * Закрыть заполнение
     */
    public void closeListEntry(Long listEntryId, boolean isClosing, HttpServletRequest request) throws NamingException {
        Injection.find(request).getService(IWebQueryService.class).executeUpdateNativeSql("update e2listentry  set isClosed='" + (isClosing ? "1" : "0") + "' where id=" + listEntryId);
    }

    public String replaceFieldByError(Long entryListId, String errorCode, String fldName, String aOldValue, String newValue, HttpServletRequest request) throws NamingException {
        if (isNullOrEmpty(fldName) || isNullOrEmpty(newValue) || isNullOrEmpty(aOldValue)) {
            return "неуспешно!";
        }
        StringBuilder sql = new StringBuilder();
        String fieldName;
        sql.append("update e2entry e set ");
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        switch (fldName) {
            case "OTHER_FOR_ERROR": //Изменить поток обслуживания по дефекту(поток : дефект/номер_счета
                String[] vals = newValue.split("/");
                String sanctionCode = vals[0].trim();
                String bill = vals.length > 1 ? vals[1].trim() : null;
                sql.append("servicestream ='").append(aOldValue).append("'" +
                        " from e2entrysanction es" +
                        " where es.entry_id = e.id and es.dopcode ='").append(sanctionCode).append("' and e.listentry_id =")
                .append(entryListId);
                if (bill != null) {
                    sql.append(" and e.billnumber ='").append(bill).append("'");
                }
                return "Изменен поток обслуживания по дефекту "
                        + sanctionCode
                        + (bill != null ? " и счету " + bill : " ")
                        + ": " + service.executeUpdateNativeSql(sql.toString());
            case "SERVICESTREAM":
                fieldName = "serviceStream";
                break;
            case "SNILS_DOCTOR":
                fieldName = "doctorSnils";
                //Обновляем СНИЛС в мед услугах по заполнению
                service.executeUpdateNativeSql("update entrymedservice ems set doctorsnils = '" + newValue + "' " +
                        " from e2entry e where e.listentry_id = " + entryListId + " and (e.isDeleted is null or e.isDeleted='0') and ems.entry_id = e.id and ems.doctorsnils = '" + aOldValue + "'");
                break;
            case "MEDSERVICE_CODE":  //замена услуг в заполнении
                String sql1 = "update entrymedservice ems set medservice_id=" +
                        " (select max(id) from vocmedservice where code='" + newValue + "' and finishDate is null) from e2entry e where e.listEntry_id=" + entryListId +
                        " and (e.isDeleted is null or e.isDeleted='0') and ems.entry_id = e.id and ems.medservice_id in (select id from vocmedservice where code ='" + aOldValue + "')";
                service.executeUpdateNativeSql(sql1);
                LOG.info("Услуга " + aOldValue + " заменена на " + newValue);
                return "Услуга " + aOldValue + " заменена на " + newValue;

            case "SNILS_REPLACE_STRING":
            case "MEDSERVICE_REPLACE_STRING":
                try {
                    WebQueryResult wqr = getConfigString(fldName, request);
                    String[] replaceString;
                    if (wqr != null) {
                        replaceString = wqr.get1().toString().split(";");
                        for (String p : replaceString) {
                            String[] pair = p.trim().split(":");
                            String stringFrom = pair[0].trim();
                            String stringTo = pair[1].trim();
                            replaceFieldByError(entryListId, errorCode, fldName.equals("SNILS_REPLACE_STRING") ? "SNILS_DOCTOR" : "MEDSERVICE_CODE", stringFrom, stringTo, request);
                        }
                    } else {
                        replaceString = new String[0];
                    }
                    return fldName + " заменены: " + Arrays.toString(replaceString);
                } catch (Exception e) {
                    return "Не удалось заменить " + fldName + " = " + e;
                }
            case "DEPARTMENT_BY_SERVICE":
                WebQueryResult wqr = getConfigString(fldName, request); //profileCode:newDepCode:oldDepCode, profileCode:newDepCode:oldDepCode
                if (wqr != null) {
                    String con = wqr.get1().toString();
                    String[] configs = con.split(",");
                    for (String config : configs) {
                        String[] triple = config.trim().split(":");
                        String profileCode = triple[0];
                        String newDepartmentCode = triple[1];
                        String oldDepartmentCode = triple.length > 2 ? triple[2] : null;
                        service.executeUpdateNativeSql("update e2entry e set departmentCode = '" +
                                newDepartmentCode + "' where listEntry_id=" +
                                entryListId + " and medhelpprofile_id=(select max(id) from voce2medhelpprofile where code ='" +
                                profileCode + "')" + (oldDepartmentCode == null ? "" : " and departmentCode='" + oldDepartmentCode + "'"));
                    }
                    return "Всё заменено согласно настройкам: " + con;
                }

                return "Не найдено настройки с именем " + fldName;
            case "DEPARTMENT_ADDRESS_BY_SERVICE":
                WebQueryResult wqr2 = getConfigString(fldName, request); //profileCode:newDepAddressCode:oldDepAddressCode, profileCode:newDepAddressCode:oldDepAddressCode
                if (wqr2 != null) {
                    String con = wqr2.get1().toString();
                    String[] configs = con.split(",");
                    for (String config : configs) {
                        String[] triple = config.trim().split(":");
                        String profileCode = triple[0];
                        String newDepartmentAddressCode = triple[1];
                        String oldDepartmentAddressCode = triple.length > 2 ? triple[2] : null;
                        service.executeUpdateNativeSql("update e2entry e set departmentCode = '" +
                                newDepartmentAddressCode.substring(0, 8) + "', departmentAddressCode='" + newDepartmentAddressCode + "' where listEntry_id=" +
                                entryListId + " and medhelpprofile_id in (select id from voce2medhelpprofile where code ='" +
                                profileCode + "')" + (oldDepartmentAddressCode == null ? "" : " and departmentAddressCode='" + oldDepartmentAddressCode + "'"));
                    }
                    return "Всё заменено согласно настройкам: " + con;
                }

                return "Не найдено настройки с именем " + fldName;
            default:
                return "BAD_FIELD_NAME!";
        }
        sql.append(fieldName).append("='").append(newValue).append("'");
        if (isNotEmpty(errorCode)) { //меняем записи по ошибке
            sql.append(" where id in (select distinct entry_id from e2entryerror err where err.listentry_id=")
                    .append(entryListId).append(" and err.errorcode='").append(errorCode).append("' and (err.isDeleted is null or err.isDeleted='0') )");

            sql.append(" and ").append(fieldName).append(" ='").append(aOldValue).append("'");
        } else { //Меняем записи по заполнению
            sql.append(" where listEntry_id=").append(entryListId).append(" and ").append(fieldName).append("='").append(aOldValue).append("'");
        }
        sql.append(" and (isDeleted is null or isDeleted='0')");
        service.executeUpdateNativeSql(sql.toString());
        return "1_Успешно!";
    }

    /**
     * Создаем монитор
     */
    private long createMonitor(HttpServletRequest aRequest) throws NamingException {
        return ((IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService"))
                .createMonitor();
    }

    public long makeMPFIle(final Long entryListId, final String type, String billNumber, String billDate
            , final Long entryId, final Boolean calcAllListEntry
            , final String version, final String fileType, HttpServletRequest request) throws NamingException {
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyyHH:mm");
        if (entryId != null) {
            billNumber = billNumber != null ? billNumber : "TEST";
            billDate = isNotEmpty(billDate) ? billDate : "24.12.1986";
        }
        final IExpert2XmlService service = Injection.find(request).getService(IExpert2XmlService.class);

        final String finalBillNumber = billNumber;
        Date dateBillDate;
        try {
            dateBillDate = new Date(format.parse(billDate + "15:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            dateBillDate = null;

        }
        final Date finalBillDate = dateBillDate;
        final long monitorId = createMonitor(request);
        new Thread(() -> service.makeMPFIle(entryListId, type, finalBillNumber, finalBillDate, entryId, calcAllListEntry, monitorId, version, fileType)).start();

        return monitorId;
    }

    public long checkListEntry(final Long listEntryId, final boolean forceUpdateKsg, final String params, HttpServletRequest request) throws NamingException {
        LOG.info("start checkEntryList " + forceUpdateKsg);
        final long monitorId = createMonitor(request);
        final IExpert2Service service = Injection.find(request).getService(IExpert2Service.class);
        new Thread(() -> service.checkListEntry(listEntryId, forceUpdateKsg, params, monitorId)).start();
        return monitorId;
    }

    public void checkEntry(Long aEntryId, boolean forceUpdateKsg, HttpServletRequest aRequest) throws NamingException {
        Injection.find(aRequest).getService(IExpert2Service.class).makeCheckEntry(aEntryId, forceUpdateKsg);
    }

    public void saveBillDateAndNumber(Long listEntryId, String entryType, String serviceStream, String oldBillNumber, String oldBillDateTo, String billNumber
            , String billDate, String isForeign, String comment, String fileType, String addGroupField, HttpServletRequest request) throws NamingException, ParseException {
        if (isNullOrEmpty(entryType) || isNullOrEmpty(serviceStream)) {
            return;
        }

        StringBuilder sql = new StringBuilder();
        if (isNullOrEmpty(billNumber)) { //Удалить информацию о номере счета.
            if (isNullOrEmpty(oldBillDateTo) || isNullOrEmpty(oldBillNumber)) {
                return;
            }
            sql.append("update e2entry set bill_id=null, billNumber='', billDate=null");
        } else {
            Date sqlBillDate = DateFormat.parseSqlDate(billDate);
            E2Bill bill = Injection.find(request).getService(IExpert2Service.class)
                    .getBillEntryByDateAndNumber(billNumber, sqlBillDate, comment);
            sql.append("update e2entry set bill_id=").append(bill.getId()).append(", billNumber='").append(billNumber)
                    .append("', billDate=to_date('").append(billDate).append("','dd.MM.yyyy') ");

        }
        sql.append(" where listEntry_id=")
                .append(listEntryId).append(" and entryType='").append(entryType).append("' and serviceStream='")
                .append(serviceStream).append("' and isForeign='").append(isForeign).append("' and fileType='").append(fileType).append("'")
                .append(" and addGroupFld='").append(addGroupField != null ? addGroupField : "").append("'");
        if (isNotEmpty(oldBillDateTo)) {
            sql.append(" and billDate=to_date('").append(oldBillDateTo).append("','dd.MM.yyyy')");
        } else {
            sql.append(" and billDate is null");
        }
        sql.append(" and billNumber='").append(oldBillNumber == null ? "" : oldBillNumber).append("'").append(" and (isDeleted is null or isDeleted='0')");
        Injection.find(request).getService(IWebQueryService.class)
                .executeUpdateNativeSql(sql.toString());
    }

    public void cleanAllErrorsByList(Long entryListId, HttpServletRequest request) throws NamingException {
        Injection.find(request).getService(IWebQueryService.class)
                .executeUpdateNativeSql("delete from e2entryerror where listentry_id=" + entryListId);
    }

    public void setEntryMedServiceDate(Long medServiceId, String serviceDate, HttpServletRequest request) throws NamingException {
        Injection.find(request).getService(IWebQueryService.class)
                .executeUpdateNativeSql("update entrymedservice set serviceDate=to_date('" + serviceDate + "','dd.MM.yyyy') where id=" + medServiceId);
    }

    private WebQueryResult getConfigString(String configValue, HttpServletRequest request) throws NamingException {
        Collection<WebQueryResult> list = Injection.find(request).getService(IWebQueryService.class)
                .executeNativeSql("select value from Expert2Config where code='" + configValue + "'");
        return list.isEmpty() ? null : list.iterator().next();


    }

}
