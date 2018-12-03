package ru.ecom.mis.web.dwr.expert2;

import org.json.JSONException;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.expert2.domain.E2Bill;
import ru.ecom.expert2.service.IExpert2Service;
import ru.ecom.expert2.service.IExpert2XmlService;
import ru.ecom.expert2.service.IFinanceService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Expert2ServiceJs {

    public String getMedcaseCost(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        return Injection.find(aRequest).getService(IExpert2Service.class).getMedcaseCost(aMedcaseId);
    }

    public String getEntryJson(Long aEntryId, HttpServletRequest aRequest) throws NamingException {
        System.out.println("HELLO "+aEntryId);
        return Injection.find(aRequest).getService(IExpert2Service.class).getEntryJson(aEntryId);
    }

    public String splitForeignOtherBill(Long aListEntryId, String aBillNumber, String aBillDate, String aTerritories, HttpServletRequest aRequest) throws NamingException, ParseException {
        Date billDate = DateFormat.parseSqlDate(aBillDate);
        return Injection.find(aRequest).getService(IExpert2Service.class).splitForeignOtherBill(aListEntryId,aBillNumber,billDate,aTerritories);
    }

    public void fillDirectDatePlanHosp(Long aListEntryId, HttpServletRequest aRequest) throws NamingException {
        String sql = "update e2entry set directDate = startDate where listentry_id="+aListEntryId+" " +
                " and entryType='HOSPITAL' and directDate is null and (isEmergency is null or isEmergency='0') and (isDeleted is null or isDeleted='0')";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql(sql);
        sql = "update e2entry set planHospDate = startDate where listentry_id="+aListEntryId+" " +
                " and entryType in ('VMP','HOSPITAL') and planHospDate is null and (isEmergency is null or isEmergency='0') and (isDeleted is null or isDeleted='0')";
        service.executeUpdateNativeSql(sql);

    }

   public String fillAggregateTable(String aType, String aStartDate, String aFinishDate, String aServiceStream, HttpServletRequest aRequest) throws NamingException, JSONException, ParseException {
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
        System.out.println("Copy plans!");
        System.out.println(service.copyFinancePlanNextMonth(null,planDate,startDate,finishDate));

    }
    /** Пересчитать заполнение (удаляем существующие записи и формируем новые в существующее заполнение) */
    public void refillListEntry(Long aListEntryId, HttpServletRequest aRequest) throws NamingException {
        Injection.find(aRequest).getService(IExpert2Service.class).reFillListEntry(aListEntryId);
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

    /** Выгрузить дефекты в новое заполнение */
    public boolean exportDefectNewListEntry (Long alistEntryId, HttpServletRequest aRequest) throws NamingException {
       return   Injection.find(aRequest).getService(IExpert2Service.class).exportDefectNewListEntry(alistEntryId);
    }

    /** Закрыть заполнение */
    public void closeListEntry(Long aListEntryId, Boolean aClose,  HttpServletRequest aRequest) throws NamingException {
        Injection.find(aRequest).getService(IWebQueryService.class).executeUpdateNativeSql("update e2listentry  set isClosed='"+(aClose?"1":"0")+"' where id="+aListEntryId);
    }

    public String replaceFieldByError(Long aEntryListId, String aErrorCode, String aFieldName, String aOldValue, String aNewValue, HttpServletRequest aRequest) throws NamingException {
        if (aFieldName==null||aNewValue==null) {return "неуспешно!";}
        StringBuilder sql = new StringBuilder();
        String fieldName;
        sql.append("update e2entry e set ");
        //System.out.println(aFieldName);
        if (aFieldName.equals("SERVICESTREAM")) {
            fieldName="serviceStream";
            //sql.append();
        } else if (aFieldName.equals("SNILS_DOCTOR")) {
            fieldName="doctorSnils";
        }else {
            return "BAD_FIELD_NAME!";
        }
        sql.append(fieldName).append("='").append(aNewValue).append("'");
        if (aErrorCode!=null&&!aErrorCode.equals("")) { //меняем записи по ошибке

            sql.append(" where id in (select distinct entry_id from e2entryerror err where err.listentry_id=")
                    .append(aEntryListId).append(" and err.errorcode='").append(aErrorCode).append("' and (err.isDeleted is null or err.isDeleted='0') )");

            if (aOldValue!=null&&!aOldValue.equals("")) {
                sql.append(" and ").append(fieldName).append(" ='").append(aOldValue).append("'");
            }
        } else { //Меняем записи по заполнению
            if (aOldValue!=null&&!aOldValue.equals("")) {
                sql.append(" where listEntry_id=").append(aEntryListId).append(" and ").append(fieldName).append("='").append(aOldValue).append("'");
            } else {
                return "Необходимо указать старое значение поля! ";
            }

        }
        sql.append(" and (isDeleted is null or isDeleted='0')");
        System.out.println("SQL for update field = "+sql);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        service.executeUpdateNativeSql(sql.toString());
        return "1_Успешно!";
    }

    public long makeMPFIle (final Long aEntryListId,final  String aType, String aBillNumber, String aBillDate,final  Long aEntryId,final  Boolean calcAllListEntry, final String aVersion, HttpServletRequest aRequest) throws NamingException {
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        if (aEntryId!=null) {
            aBillNumber=aBillNumber!=null?aBillNumber:"TEST";
            aBillDate=aBillDate!=null&&!aBillDate.equalsIgnoreCase("")?aBillDate:"24.12.1986";
        }
        final IExpert2XmlService service = Injection.find(aRequest).getService(IExpert2XmlService.class);
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final long monitorId = monitorService.createMonitor();
        final String finalBillNumber = aBillNumber;
        final String finalBillDate = aBillDate;
        new Thread(() -> {
            Date finalDate = null;
            try {finalDate = new Date(format.parse(finalBillDate).getTime());} catch (Exception e) {}
                service.makeMPFIle(aEntryListId,aType, finalBillNumber,finalDate,aEntryId,calcAllListEntry, monitorId,aVersion);
        }).start();

        return monitorId;
    }
    public long checkListEntry(final Long aListEntryId, final boolean forceUpdateKsg, final String aParams, HttpServletRequest aRequest) throws NamingException {
        System.out.println("start checkEntryList "+forceUpdateKsg);
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final long monitorId = monitorService.createMonitor();
        final IExpert2Service service = Injection.find(aRequest).getService(IExpert2Service.class);
        new Thread(()-> {
                System.out.println("start check new Thread");
                service.checkListEntry(aListEntryId, forceUpdateKsg, aParams, monitorId);
                System.out.println("finish checkEntryList ");

            }).start();
        return monitorId;
    }
    public void checkEntry (Long aEntryId, boolean forceUpdateKsg, HttpServletRequest aRequest) throws NamingException {
        System.out.println("start checkEntry "+aEntryId);
        Injection.find(aRequest).getService(IExpert2Service.class).makeCheckEntry(aEntryId,forceUpdateKsg);
        System.out.println("finish checkEntry ");
    }

    public void addMedHelpProfileBedType (Long aMedHelpId, Long aBedTypeId, Long aBedSubTypeId, HttpServletRequest aRequest ) throws NamingException {
        Injection.find(aRequest).getService(IExpert2Service.class).addMedHelpProfileBedType(aMedHelpId,aBedTypeId, aBedSubTypeId);
    }

    public boolean saveBillDateAndNumber(Long aListEntryId, String aType, String aServiceStream, String aOldBillNumber, String aOldBillDate,String aBillNumber, String aBillDate, String isForeign, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        IExpert2Service expert2Service= Injection.find(aRequest).getService(IExpert2Service.class);
        String sql ;
        if (aType==null||aType.trim().equals("")) {return false;}
        if (aServiceStream==null||aServiceStream.trim().equals("")) {return false;}

        if (aBillNumber==null||aBillNumber.trim().equals("")) { //Удалить информацию о номере счета.
            sql = "update e2entry set bill_id=null, billNumber='', billDate=null where listEntry_id="+aListEntryId+" and entryType='"+aType+"' and serviceStream='"+aServiceStream+"' and isForeign='"+isForeign+"'";
            if (aOldBillDate!=null&&!aOldBillDate.equals("")) {sql+=" and billDate=to_date('"+aOldBillDate+"','dd.MM.yyyy')";} else {return false;}
            if (aOldBillNumber!=null&&!aOldBillNumber.equals("")) {sql+=" and billNumber='"+aOldBillNumber+"'";}else {return false;}
        } else {
            E2Bill bill = expert2Service.getBillEntryByDateAndNumber(aBillNumber,aBillDate);
            sql = "update e2entry set bill_id="+bill.getId()+", billNumber='"+aBillNumber+"', billDate=to_date('"+aBillDate+"','dd.MM.yyyy')" +
                    " where listEntry_id="+aListEntryId+" and entryType='"+aType+"' and serviceStream='"+aServiceStream+"' and isForeign='"+isForeign+"'";
            if (aOldBillDate!=null&&!aOldBillDate.equals("")) {sql+=" and billDate=to_date('"+aOldBillDate+"','dd.MM.yyyy')";} else {sql+=" and billDate is null";}
            if (aOldBillNumber==null) {aOldBillNumber="";}
            sql+=" and billNumber='"+aOldBillNumber+"'";
        }
        sql+=" and (isDeleted is null or isDeleted='0')";
        service.executeUpdateNativeSql(sql);
        return true;
    }

    public boolean unionMedCase (Long aListEntryId, Long aHospitalMedCase, Long aPatientId, String aEntryType,boolean isGroupSpo, HttpServletRequest aRequest) throws NamingException {
        IExpert2Service service = Injection.find(aRequest).getService(IExpert2Service.class);
        service.testUnionMecCase(aListEntryId,aHospitalMedCase,aPatientId, aEntryType, isGroupSpo);
        return true;
    }

    public void addMedServiceToEntry(Long aEntryId, Long aMedServiceId, HttpServletRequest aRequest) {
        System.out.println("addMedServiceToEntry JS DEPRECATED");
    }

}
