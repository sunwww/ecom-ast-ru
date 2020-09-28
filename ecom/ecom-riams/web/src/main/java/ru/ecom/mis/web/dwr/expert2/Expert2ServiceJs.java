package ru.ecom.mis.web.dwr.expert2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.expert2.domain.E2Bill;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.service.IExpert2Service;
import ru.ecom.expert2.service.IExpert2XmlService;
import ru.ecom.expert2.service.IFinanceService;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expert2ServiceJs {
    private static final Logger LOG = Logger.getLogger(Expert2ServiceJs.class);

    public void deleteAllDrugByCancer(Long aCancerEntryId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("delete from e2cancerdrugdate where drug_id in (select id from E2CancerDrug where  cancerEntry_id="+aCancerEntryId+")");
        service.executeUpdateNativeSql("delete from E2CancerDrug where cancerEntry_id="+aCancerEntryId);
    }
    public void addDeleteEntryFactor (Long entryId, Long vocFactorId, Boolean needDelete, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (Boolean.TRUE.equals(needDelete)) {
            service.executeUpdateNativeSql("delete from e2entry_factor where entry_id ="+entryId+" and factor_id= "+vocFactorId+"");
        } else {
            service.executeUpdateNativeSql("insert into e2entry_factor (entry_id, factor_id) values ("+entryId+","+vocFactorId+")");
        }
    }

    public String getDefaultLpuOmcCode(HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return service.executeNativeSql("select coalesce(omccode,'0') from softconfig sc" +
                " left join mislpu ml on ml.id=cast(sc.keyvalue as int)" +
                " where sc.key='DEFAULT_LPU' ").iterator().next().get1().toString();
    }

    private List<Element> createXmlFromJson(JSONArray aJson, String[] aFlds, String aElementName) {
        List<Element> ret = new ArrayList<>();
        for (int i=0;i<aJson.length();i++) {
            JSONObject jso = aJson.getJSONObject(i);
            Element xml = new Element(aElementName);
            for (String key : aFlds) {
                xml.addContent(new Element(key).setText(jso.get(key.toLowerCase()).toString()));
            }
            ret.add(xml);
        }
        return ret;
    }
    private void putEl(Element aElement, String aName, String aValue) {
        aElement.addContent(new Element(aName).setText(aValue));
    }

    public String fixSomeErrors(Long aListEntryId, String aErrorCode, HttpServletRequest aRequest) throws NamingException, SQLException {
       if ("223".equals(aErrorCode)) {
            LOG.info("Проверяем по базе ТФОМС и проставляем действующие полиса на дату начала случая");
            IExpert2Service xmlService = Injection.find(aRequest).getService(IExpert2Service.class);
            return xmlService.fixFondAnswerError(aListEntryId,aErrorCode);
        }  else if ("CV_DEATH".equals(aErrorCode)) {
           LOG.info("Перенос диагнозов из реанимации в род. отделение");
           String sql = "update EntryDiagnosis ed set entry_id =child.parentEntry_id from " +
                   " e2entry child where child.listEntry_id="+aListEntryId+" and (child.isDeleted is null or child.isDeleted='0') and child.serviceStream='COMPLEXCASE'" +
                   " and child.parentEntry_id is not null and ed.entry_id=child.id";
           IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
           service.executeUpdateNativeSql(sql);
           return "All OK";

       } else {
            return "Я не понял что мне делать!";
        }
    }

    public void deleteAllDeletedEntries(HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        LOG.info("All entries = "+service.executeNativeSql("select count(id) from e2entry").iterator().next().get1().toString());
        service.executeUpdateNativeSql("delete from e2entry where isDeleted='1'");
        LOG.info("All entries2 = "+service.executeNativeSql("select count(id) from e2entry").iterator().next().get1().toString());
        service.executeUpdateNativeSql("delete from e2listentry where isDeleted='1'");
    }

    public void makeOncologyCase(Long aListEntryId, String aJson, String aDefectCode, HttpServletRequest aRequest) throws NamingException {
        IExpert2Service service = Injection.find(aRequest).getService(IExpert2Service.class);
        service.makeOncologyCase(aListEntryId, aJson, aDefectCode);
    }

    public String exportToCentralSegment(Long aListEntryId, String aHistoryNumbers, HttpServletRequest aRequest) throws NamingException {
        IExpert2XmlService service = Injection.find(aRequest).getService(IExpert2XmlService.class);
        return service.exportToCentralSegment(aListEntryId,aHistoryNumbers);
    }

    public String splitLongCase(Long aEntryId, HttpServletRequest aRequest) throws NamingException {
        IExpert2Service service = Injection.find(aRequest).getService(IExpert2Service.class);
        return service.splitLongCase(aEntryId);
    }

    /*Меняем кардиологию на сосуды *только для АМОКБ */
    public String changeToSosud(Long aEntryId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        try {
            WebQueryResult webQueryResult = service.executeNativeSql("select coalesce(voc.code,'') from e2entry e left join voce2medhelpprofile voc on voc.id=e.medhelpprofile_id where e.id="+aEntryId).iterator().next();
            if ("29".equals(webQueryResult.get1().toString())) {
                service.executeUpdateNativeSql("delete from entrymedservice where entry_id ="+aEntryId);
                service.executeUpdateNativeSql("insert into entrymedservice (medservice_id, entry_id, servicedate) (select (select max(id) from vocmedservice where code='A16.12.028' and finishdate is null),e.id,e.startDate from e2entry e where e.id ="+aEntryId+")");
                service.executeUpdateNativeSql("update e2entry set medhelpprofile_id=258,fonddoctorspec_id=133, fonddoctorspecv021_id=65, doctorworkfunction='64' where id = "+aEntryId);
                return "Успешно сделано!";
            } else {
                return "Профиль - не кардиологический, ничего менять не буду!";
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            return "Ошибка: "+e.getMessage();
        }


    }

    public Patient getTest(String aId, HttpServletRequest aRequest) {
        Patient patient = new Patient();
        patient.setLastname("IBANOV");

        return patient;
    }

    public static String getMedcaseCost(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        return Injection.find(aRequest).getService(IExpert2Service.class).getMedcaseCost(aMedcaseId);
    }

    public String getEntryJson(Long aEntryId, HttpServletRequest aRequest) throws NamingException {
        LOG.info("HELLO "+aEntryId);
        E2Entry entry = Injection.find(aRequest).getService(IExpert2Service.class).getEntryJson(aEntryId);
        try {
            LOG.warn("START ME");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            LOG.warn("START ME2");
            String s =gson.toJson(entry);
            LOG.warn("START ME3");
            return s;
        } catch (Exception e) {
            LOG.error(e);
            e.printStackTrace();
            return "NULL___";
        }

    }

    public String splitForeignOtherBill(Long aListEntryId, String aBillNumber, String aBillDate, String aTerritories, HttpServletRequest aRequest) throws NamingException, ParseException {
        Date billDate = DateFormat.parseSqlDate(aBillDate);
        return Injection.find(aRequest).getService(IExpert2Service.class).splitForeignOtherBill(aListEntryId,aBillNumber,billDate,aTerritories);
    }

    public void fillDirectDatePlanHosp(Long aListEntryId, HttpServletRequest aRequest) throws NamingException {
        String sql = "update e2entry set directDate = startDate where listentry_id="+aListEntryId+
                " and entryType='HOSPITAL' and directDate is null and (isEmergency is null or isEmergency='0') and (isDeleted is null or isDeleted='0')";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql(sql);
        sql = "update e2entry set planHospDate = startDate where listentry_id="+aListEntryId+
                " and entryType in ('VMP','HOSPITAL') and planHospDate is null and (isEmergency is null or isEmergency='0') and (isDeleted is null or isDeleted='0')";
        service.executeUpdateNativeSql(sql);

    }

   public String fillAggregateTable(String aType, String aStartDate, String aFinishDate, String aServiceStream, HttpServletRequest aRequest) throws NamingException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        java.sql.Date startDate = new java.sql.Date(format.parse(aStartDate).getTime());
        java.sql.Date finishDate = new java.sql.Date(format.parse(aFinishDate).getTime());
        return Injection.find(aRequest).getService(IFinanceService.class).fillAggregateTable(aType,startDate,finishDate,aServiceStream);
    }

    /***/
    public void splitFinancePlan(String aType, String aYear, HttpServletRequest aRequest) throws NamingException {
        IFinanceService service = Injection.find(aRequest).getService(IFinanceService.class);
        service.splitFinancePlan(aType,aYear);
    }
    /**  Копируем финансовый план на несколько месяцев (MM.yyyy)*/
    public void copyFinancePlanNextMonth(String aCurrentMonth, String aStartMonth, String aFinishMonth, HttpServletRequest aRequest) throws NamingException, ParseException {
        IFinanceService service = Injection.find(aRequest).getService(IFinanceService.class);
        SimpleDateFormat format = new SimpleDateFormat("MM.yyyy");
        Date planDate = new java.sql.Date(format.parse(aCurrentMonth).getTime());
        Date startDate = new java.sql.Date(format.parse(aStartMonth).getTime());
        Date finishDate = new java.sql.Date(format.parse(aFinishMonth).getTime());
        LOG.info(service.copyFinancePlanNextMonth(null,planDate,startDate,finishDate));

    }
    /** Пересчитать заполнение (удаляем существующие записи и формируем новые в существующее заполнение) */
    public long refillListEntry(Long aListEntryId, HttpServletRequest aRequest) throws NamingException {
        final long monitorId = createMonitor(aRequest);
        Injection.find(aRequest).getService(IExpert2Service.class).reFillListEntry(aListEntryId,monitorId);
        return monitorId;
    }

    /** Журнал сформированных пакетов/счетов */
    public String getPacketJournalByBillNumber(String aBillNumber, String aBillDate, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select billNumber, to_char(billDate,'dd.MM.yyyy') as billdate,  filename, to_char(createdate,'dd.MM.yyyy')||' '||cast(createtime as varchar(5)) as createdatetime" +
                " from E2ExportPacketJournal";

        if (!StringUtil.isNullOrEmpty(aBillNumber)) {
            sql+=" where billNumber='"+aBillNumber+"'";
            if (!StringUtil.isNullOrEmpty(aBillDate)) {
                sql+=" and billDate=to_date('"+aBillDate+"','dd.MM.yyyy')";
            }
        }
        sql +=" order by createdate desc, createtime desc";
    return service.executeNativeSqlGetJSON(new String[]{"billNumber","billDate","filename","createDate"},sql,10);
    }
    /** Объединить заполнение (перенос записей из старого в новое и пометка старого как удаленного */
    public void unionListEntries(Long aOldListEntryId, Long aNewListEntryId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "update e2entry set listentry_id="+aNewListEntryId+" where listentry_id="+aOldListEntryId+" and (isDeleted is null or isDeleted='0')";
        service.executeUpdateNativeSql(sql);
        sql = "update e2listentry set isDeleted='1' where id="+aOldListEntryId;
        service.executeUpdateNativeSql(sql);
    }


    /** */
    public void addHospitalMedCaseToList(String aHistoryNumbers, Long aListEntryId, HttpServletRequest aRequest) throws NamingException, SQLException {
         Injection.find(aRequest).getService(IExpert2Service.class).addHospitalMedCaseToList(aHistoryNumbers,aListEntryId);
    }

    /** *Отмечаем записи в заполнении как повторную подачу */
    public void markAsReSend(Long aListEntryId, Boolean aReSend, HttpServletRequest aRequest) throws NamingException  {
        Injection.find(aRequest).getService(IWebQueryService.class).executeUpdateNativeSql("update e2entry set prnov='"+(aReSend?"1":"0")+"' where listEntry_id="+aListEntryId+" and (isDeleted is null or isDeleted='0')");
    }

    /** Добавляем диагноз и услугу к случаю */
    public Boolean addDiagnosisAndServiceToEntry(Long aEntryId, String aData, HttpServletRequest aRequest) throws NamingException {
        return Injection.find(aRequest).getService(IExpert2Service.class).addDiagnosisAndServiceToEntry(aEntryId, aData);
    }

    public void exportErrorsNewListEntry(Long aListEntryId, String aErrorCodes, String aSanctionDopCodes, HttpServletRequest aRequest) throws NamingException {
        Injection.find(aRequest).getService(IExpert2Service.class)
                .exportErrorsNewListEntry(aListEntryId,aErrorCodes!=null ? aErrorCodes.split(",") : new String[0]
                        , aSanctionDopCodes!=null? aSanctionDopCodes.split(","): new String[0]);
    }

    /** Выгрузить дефекты в новое заполнение */
    public boolean exportDefectNewListEntry (Long alistEntryId, HttpServletRequest aRequest) throws NamingException {
       return Injection.find(aRequest).getService(IExpert2Service.class).exportDefectNewListEntry(alistEntryId);
    }

    /** Закрыть заполнение */
    public void closeListEntry(Long aListEntryId, Boolean aClose,  HttpServletRequest aRequest) throws NamingException {
        Injection.find(aRequest).getService(IWebQueryService.class).executeUpdateNativeSql("update e2listentry  set isClosed='"+(aClose?"1":"0")+"' where id="+aListEntryId);
    }

    public String replaceFieldByError(Long aEntryListId, String aErrorCode, String aFieldName, String aOldValue, String aNewValue, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aFieldName) ||StringUtil.isNullOrEmpty(aNewValue) || StringUtil.isNullOrEmpty(aOldValue)) {return "неуспешно!";}
        StringBuilder sql = new StringBuilder();
        String fieldName;
        sql.append("update e2entry e set ");
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (aFieldName.equals("SERVICESTREAM")) {
            fieldName="serviceStream";
            //sql.append();
        } else if (aFieldName.equals("SNILS_DOCTOR")) {
            fieldName="doctorSnils";
            //Обновляем СНИЛС в мед услугах по заполнению
            service.executeUpdateNativeSql("update entrymedservice ems set doctorsnils = '"+aNewValue+"' " +
                    " from e2entry e where e.listentry_id = "+aEntryListId+" and (e.isDeleted is null or e.isDeleted='0') and ems.entry_id = e.id and ems.doctorsnils = '"+aOldValue+"'");
        } else if ("MEDSERVICE_CODE".equals(aFieldName)) { //замена услуг в заполнении
            String sql1 = "update entrymedservice ems set medservice_id=" +
                    " (select max(id) from vocmedservice where code='"+aNewValue+"' and finishDate is null) from e2entry e where e.listEntry_id="+aEntryListId+
                    " and (e.isDeleted is null or e.isDeleted='0') and ems.entry_id = e.id and ems.medservice_id in (select id from vocmedservice where code ='"+aOldValue+"')";
                    service.executeUpdateNativeSql(sql1);
                    LOG.info("Услуга "+aOldValue+" заменена на "+aNewValue);
                    return "Услуга "+aOldValue+" заменена на "+aNewValue;

        } else if (aFieldName.equals("SNILS_REPLACE_STRING") || aFieldName.equals("MEDSERVICE_REPLACE_STRING")) {
            try {
                WebQueryResult wqr = service.executeNativeSql("select value from Expert2Config where code='"+aFieldName+"'").iterator().next();
                String[] replaceString = wqr.get1().toString().split(";");
                for (String p : replaceString) {
                    String[] pair = p.trim().split(":");
                    String stringFrom = pair[0].trim();
                    String stringTo = pair[1].trim();
                    replaceFieldByError(aEntryListId, aErrorCode,aFieldName.equals("SNILS_REPLACE_STRING") ? "SNILS_DOCTOR" : "MEDSERVICE_CODE",stringFrom,stringTo, aRequest);
                }
                return aFieldName+ " заменены: "+ Arrays.toString(replaceString);
            } catch (Exception e) {
                return "Не удалось заменить "+aFieldName+" = "+e;
            }
        } else {
            return "BAD_FIELD_NAME!";
        }
        sql.append(fieldName).append("='").append(aNewValue).append("'");
        if (!StringUtil.isNullOrEmpty(aErrorCode)) { //меняем записи по ошибке
            sql.append(" where id in (select distinct entry_id from e2entryerror err where err.listentry_id=")
                .append(aEntryListId).append(" and err.errorcode='").append(aErrorCode).append("' and (err.isDeleted is null or err.isDeleted='0') )");

            sql.append(" and ").append(fieldName).append(" ='").append(aOldValue).append("'");
        } else { //Меняем записи по заполнению
           sql.append(" where listEntry_id=").append(aEntryListId).append(" and ").append(fieldName).append("='").append(aOldValue).append("'");
        }
        sql.append(" and (isDeleted is null or isDeleted='0')");
      //  LOG.info("SQL for update field = "+sql);
        service.executeUpdateNativeSql(sql.toString());
        return "1_Успешно!";
    }

    /**Создаем монитор*/
        private long createMonitor(HttpServletRequest aRequest)throws NamingException {
            IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
            return monitorService.createMonitor();
        }

    public long makeMPFIle (final Long aEntryListId,final  String aType, String aBillNumber, String aBillDate
            ,final  Long aEntryId,final  Boolean calcAllListEntry
            , final String aVersion, final String aFileType, HttpServletRequest aRequest) throws NamingException {
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        if (aEntryId!=null) {
            aBillNumber=aBillNumber!=null?aBillNumber:"TEST";
            aBillDate=aBillDate!=null&&!aBillDate.equals("")?aBillDate:"24.12.1986";
        }
        final IExpert2XmlService service = Injection.find(aRequest).getService(IExpert2XmlService.class);

        final String finalBillNumber = aBillNumber;
        final String finalBillDate = aBillDate;
        final long monitorId = createMonitor(aRequest);
        new Thread(() -> {
            Date finalDate = null;
            try {finalDate = new Date(format.parse(finalBillDate).getTime());} catch (Exception e) {}
                service.makeMPFIle(aEntryListId,aType, finalBillNumber,finalDate,aEntryId,calcAllListEntry, monitorId,aVersion, aFileType);
        }).start();

        return monitorId;
    }
    public long checkListEntry(final Long aListEntryId, final boolean forceUpdateKsg, final String aParams, HttpServletRequest aRequest) throws NamingException {
        LOG.info("start checkEntryList "+forceUpdateKsg);
        final long monitorId = createMonitor(aRequest);
        final IExpert2Service service = Injection.find(aRequest).getService(IExpert2Service.class);
        new Thread(()-> service.checkListEntry(aListEntryId, forceUpdateKsg, aParams, monitorId)).start();
        return monitorId;
    }
    public void checkEntry(Long aEntryId, boolean forceUpdateKsg, HttpServletRequest aRequest) throws NamingException {
        Injection.find(aRequest).getService(IExpert2Service.class).makeCheckEntry(aEntryId,forceUpdateKsg);
    }

    public boolean saveBillDateAndNumber(Long aListEntryId, String aType, String aServiceStream, String aOldBillNumber, String aOldBillDate,String aBillNumber
            , String aBillDate, String isForeign, String aComment, String aFileType,String aAddGroupFld, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        IExpert2Service expert2Service= Injection.find(aRequest).getService(IExpert2Service.class);
        StringBuilder sql = new StringBuilder();
        if (aType==null||aType.trim().equals("")) {return false;}
        if (aServiceStream==null||aServiceStream.trim().equals("")) {return false;}
        if (StringUtil.isNullOrEmpty(aBillNumber)) { //Удалить информацию о номере счета.
            if (aOldBillDate == null || aOldBillDate.equals("")) {return false;}
            if (aOldBillNumber == null || aOldBillNumber.equals("")) {return false;}
            sql.append("update e2entry set bill_id=null, billNumber='', billDate=null");
        } else {
            E2Bill bill = expert2Service.getBillEntryByDateAndNumber(aBillNumber,aBillDate,aComment);
            sql.append("update e2entry set bill_id=").append(bill.getId()).append(", billNumber='").append(aBillNumber)
                    .append("', billDate=to_date('").append(aBillDate).append("','dd.MM.yyyy') ");

        }
         sql.append(" where listEntry_id=")
                 .append(aListEntryId).append(" and entryType='").append(aType).append("' and serviceStream='")
                 .append(aServiceStream).append("' and isForeign='").append(isForeign).append("' and fileType='").append(aFileType).append("'")
                 .append(" and addGroupFld='").append(aAddGroupFld!=null ? aAddGroupFld : "").append("'");
        if (!StringUtil.isNullOrEmpty(aOldBillDate)) {sql.append(" and billDate=to_date('").append(aOldBillDate).append("','dd.MM.yyyy')");} else {sql.append(" and billDate is null");}
        sql.append(" and billNumber='").append(aOldBillNumber == null ? "" : aOldBillNumber).append("'").append(" and (isDeleted is null or isDeleted='0')");
        service.executeUpdateNativeSql(sql.toString());
        return true;
    }

    public void cleanAllErrorsByList(Long aEntryList, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("delete from e2entryerror where listentry_id="+aEntryList);
    }

    public void setEntryMedServiceDate(Long aMedServiceId, String aServiceDate, HttpServletRequest aRequest ) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update entrymedservice set serviceDate=to_date('"+aServiceDate+"','dd.MM.yyyy') where id="+aMedServiceId);
    }
}
