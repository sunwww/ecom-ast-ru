package ru.ecom.mis.web.dwr.expert2;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.expert2.service.IExpert2Service;
import ru.ecom.expert2.service.IExpert2XmlService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Expert2ServiceJs {

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
                    .append(aEntryListId).append(" and err.errorcode='").append(aErrorCode).append("' and err.isDeleted='0')");

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

        System.out.println("SQL for update field = "+sql);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        service.executeUpdateNativeSql(sql.toString());
        return "1_Успешно!";
    }

    public String   makeMPFIle (Long aEntryListId, String aType, String aBillNumber, String aBillDate, Long aEntryId, Boolean calcAllListEntry, HttpServletRequest aRequest) throws NamingException, ParseException {
        //System.out.println(aEntryListId+"#"+aType+"#"+aBillNumber+"#"+aBillDate+"#"+ aEntryId);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        if (aEntryId!=null) {
            aBillNumber=aBillNumber!=null?aBillNumber:"TEST";
            aBillDate=aBillDate!=null&&!aBillDate.equalsIgnoreCase("")?aBillDate:"24.12.1986";
        }
        IExpert2XmlService service = Injection.find(aRequest).getService(IExpert2XmlService.class);
        return service.makeMPFIle(aEntryListId,aType, aBillNumber,new java.sql.Date(format.parse(aBillDate).getTime()),aEntryId,calcAllListEntry);
    }
    public void checkListEntry(Long aListEntryId, boolean forceUpdateKsg, String aParams, HttpServletRequest aRequest) throws NamingException {
        System.out.println("start checkEntryList "+forceUpdateKsg);
        Injection.find(aRequest).getService(IExpert2Service.class).checkListEntry(aListEntryId,forceUpdateKsg,aParams);
        System.out.println("finish checkEntryList ");
    }
    public void checkEntry (Long aEntryId, boolean forceUpdateKsg, HttpServletRequest aRequest) throws NamingException {
        System.out.println("start checkEntry ");
        Injection.find(aRequest).getService(IExpert2Service.class).makeCheckEntry(aEntryId,forceUpdateKsg);
        System.out.println("finish checkEntry ");
    }

    public void addMedHelpProfileBedType (Long aMedHelpId, Long aBedTypeId,HttpServletRequest aRequest ) throws NamingException {
        Injection.find(aRequest).getService(IExpert2Service.class).addMedHelpProfileBedType(aMedHelpId,aBedTypeId);
    }

    public boolean saveBillDateAndNumber(Long aListEntryId, String aType, String aServiceStream, String aOldBillNumber, String aOldBillDate,String aBillNumber, String aBillDate, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (aType==null||aType.trim().equals("")) {return false;}
        if (aServiceStream==null||aServiceStream.trim().equals("")) {return false;}
        if (aBillNumber==null||aBillNumber.trim().equals("")) {return false;}

        String sql = "update e2entry set billNumber='"+aBillNumber+"', billDate=to_date('"+aBillDate+"','dd.MM.yyyy')" +
                " where listEntry_id="+aListEntryId+" and entryType='"+aType+"' and serviceStream='"+aServiceStream+"'";
        if (aOldBillDate!=null&&!aOldBillDate.equals("")) {sql+=" and billDate=to_date('"+aOldBillDate+"','dd.MM.yyyy')";}
        if (aOldBillNumber!=null&&!aOldBillNumber.equals("")) {sql+=" and billNumber='"+aOldBillNumber+"'";}
        System.out.println("sql="+sql);
        service.executeUpdateNativeSql(sql);
        return true;
    }

    public boolean unionMedCase (Long aListEntryId, Long aHospitalMedCase, Long aPatientId, String aEntryType, HttpServletRequest aRequest) throws NamingException {
        IExpert2Service service = Injection.find(aRequest).getService(IExpert2Service.class);
        service.testUnionMecCase(aListEntryId,aHospitalMedCase,aPatientId, aEntryType);
        return true;
    }

    public void addMedServiceToEntry(Long aEntryId, Long aMedServiceId, HttpServletRequest aRequest) {
        System.out.println("addMedServiceToEntry JS DEPRECATED");
    }

}
