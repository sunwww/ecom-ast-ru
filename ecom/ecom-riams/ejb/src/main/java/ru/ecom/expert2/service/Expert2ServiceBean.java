package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import org.jdom.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.sequence.service.SequenceHelper;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.monitor.MonitorHolder;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.Expert2FondUtil;
import ru.ecom.expert2.domain.*;
import ru.ecom.expert2.domain.voc.*;
import ru.ecom.expert2.domain.voc.federal.*;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.expomc.ejb.services.form.importformat.MyMonitor;
import ru.ecom.mis.ejb.domain.directory.Entry;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.date.AgeUtil;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jdom.Document;
import org.jdom.Element;
import ru.nuzmsh.util.format.DateConverter;
import ru.nuzmsh.util.format.DateFormat;

@Stateless
@Local(IExpert2Service.class)
@Remote(IExpert2Service.class)
public class Expert2ServiceBean implements IExpert2Service {
private Boolean isCheckIsRunning = false;
    private final Logger log = Logger.getLogger(Expert2ServiceBean.class);
    private final String HOSPITALTYPE="HOSPITAL";
    private final String HOSPITALPEREVODTYPE="HOSPITALPEREVOD";
    private final String POLYCLINICTYPE="POLYCLINIC";
    private final String VMPTYPE="VMP";
    private final String EXTDISPTYPE="EXTDISP";
    private final String POLYCLINICKDOTYPE="POLYCLINICKDO";

    /** Находим или создаем счет*/
    public E2Bill getBillEntryByDateAndNumber(String aBillNumber, String aBillDate) {return getBillEntryByDateAndNumber(aBillNumber,aBillDate,theManager);}
    public E2Bill getBillEntryByDateAndNumber(String aBillNumber, String aBillDate, EntityManager aManager) {
        E2Bill bill = null;
        String sql = "select id from e2bill where billNumber=:number and billDate=to_date(:date,'dd.MM.yyyy') ";
        List<BigInteger> list = aManager.createNativeQuery(sql).setParameter("number",aBillNumber).setParameter("date",aBillDate).getResultList();
        if (list.isEmpty()) { //Создаем новый счет. статус - черновик
            try {
                bill = new E2Bill();
                bill.setBillNumber(aBillNumber);
                bill.setBillDate(DateFormat.parseSqlDate(aBillDate,"dd.MM.yyyy"));
                bill.setStatus((VocE2BillStatus)getActualVocByClassName(VocE2BillStatus.class,null,"code='DRAFT'"));
                aManager.persist(bill);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (list.size()>1) {
            log.error("Найдено более 1 счета с указанным номером и датой!!");
        } else {
            bill = aManager.find(E2Bill.class,list.get(0).longValue());
        }

        return bill;
    }

    /** Клонируем запись*/
    private E2Entry cloneEntity(E2Entry aSourceObject) {
        try {
        Method[] methodList = aSourceObject.getClass().getMethods();
            E2Entry newEntity = new E2Entry();
            //Object newEntity = aClass.newInstance();
            for (Method setterMethod: methodList) {
                if (setterMethod.getName().startsWith("set")) {
                    if (setterMethod.getName().equals("setId")||setterMethod.getName().startsWith("setId")) {continue;}
                    if (setterMethod.isAnnotationPresent(OneToMany.class)) {continue;}
                        String propertyName = PropertyUtil.getPropertyName(setterMethod);
                        try {
                            Object val = PropertyUtil.getPropertyValue(aSourceObject,propertyName);
                            PropertyUtil.setPropertyValue(newEntity,propertyName,val);
                        } catch (Exception e) {
                        }
                }
            }
            return newEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    /** Экспорт дефектов из заполнения в новое заполнение */
    public boolean exportDefectNewListEntry(Long aListEntryId) {
        try {
        E2ListEntry currentListEntry= theManager.find(E2ListEntry.class,aListEntryId);
        E2ListEntry newListEntry = new E2ListEntry(currentListEntry, "Дефекты_"+currentListEntry.getName());
        theManager.persist(newListEntry);
        List<E2Entry> list = theManager.createQuery("from E2Entry where listEntry_id=:id and isDefect='1' and (isDeleted is null or isDeleted='0')").setParameter("id",aListEntryId).getResultList();
        log.info("creating defects... defect list size = "+list.size());

            for (E2Entry entry: list) {
                E2Entry newEntry = cloneEntity(entry);
            if (newEntry==null) {continue;}
            newEntry.setListEntry(newListEntry);
            theManager.persist(newEntry);
            cloneDiagnosis(entry,newEntry);
            cloneMedService(entry,newEntry);
            cloneChildEntries(entry,newEntry);
            }
            log.info("Finish create defects!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /** Клонируем дочерние случаи */
    private void cloneChildEntries(E2Entry aOldEntry, E2Entry aNewEntry) {
        List<E2Entry> children = theManager.createQuery("from E2Entry where parentEntry_id=:id").setParameter("id",aOldEntry.getId()).getResultList();
        for (E2Entry child: children) {
            E2Entry newChild = cloneEntity(child);
            newChild.setParentEntry(aNewEntry);
            newChild.setListEntry(aNewEntry.getListEntry());
            List<E2Entry> subChildren = theManager.createQuery("from E2Entry where parentEntry_id=:id").setParameter("id",child.getId()).getResultList();
            if (subChildren.size()>0) {
                theManager.persist(newChild);
                for (E2Entry subChild: subChildren) {cloneChildEntries(subChild,newChild);}
            }
            theManager.persist(newChild);
        }
    }
    /** Клонируем диагнозы */
    private void cloneDiagnosis (E2Entry aOldEntry, E2Entry aNewEntry) {
        List<EntryDiagnosis> list = theManager.createQuery("from EntryDiagnosis where entry_id=:id").setParameter("id",aOldEntry.getId()).getResultList();
        for (EntryDiagnosis ed: list) {
            theManager.persist(new EntryDiagnosis(aNewEntry,ed));
        }
    }
    /** Клонируем диагнозы */
    private void cloneMedService (E2Entry aOldEntry, E2Entry aNewEntry) {
        List<EntryMedService> list = theManager.createQuery("from EntryMedService where entry_id=:id").setParameter("id",aOldEntry.getId()).getResultList();
        for (EntryMedService ed: list) {
            theManager.persist(new EntryMedService(aNewEntry,ed));
        }
    }

    private Long toLong(String aString) {
        return aString!=null&&!aString.trim().equals("")?Long.valueOf(aString.trim()):null;
    }
    public Boolean addDiagnosisAndServiceToEntry(Long aEntryId, String aData)  {
        E2Entry entry = theManager.find(E2Entry.class,aEntryId);
        try {
            JSONObject ds = new JSONObject(aData);

            if (ds.has("DiagnosisMkbId")&&isNotNull(ds.getString("DiagnosisMkbId"))) {

                Long diagnosisIs = toLong(ds.getString("DiagnosisMkbId"));
                VocDiagnosisRegistrationType registrationType=theManager.find(VocDiagnosisRegistrationType.class,ds.getLong("DiagnosisRegistrationType"));
                VocIdc10 mkb=theManager.find(VocIdc10.class,diagnosisIs);
                VocPriorityDiagnosis priorityDiagnosis=theManager.find(VocPriorityDiagnosis.class,ds.getLong("DiagnosisPriority"));
                String dopMkb = ds.getString("DiagnosisDopMkb");
                String illnessPrimary = ds.getString("DiagnosisIllnesPrimary");
                EntryDiagnosis ed = new EntryDiagnosis(entry,mkb,registrationType,priorityDiagnosis,dopMkb,illnessPrimary);
                theManager.persist(ed);
                if(ds.has("DiagnosisMainMkb")) {
                    entry.setMainMkb(mkb.getCode());
                    theManager.persist(entry);
                }
            }

            if (ds.has("DiagnosisMedService")&&isNotNull(ds.getString("DiagnosisMedService"))) {
                Long medserviceId= toLong(ds.getString("DiagnosisMedService"));
                VocMedService vocMedService = theManager.find(VocMedService.class,medserviceId);
                EntryMedService ems = new EntryMedService(entry,vocMedService);
                theManager.persist(ems);
                if (ds.has("DiagnosisIsMainService")) {
                    entry.setMainService(vocMedService.getCode());
                    theManager.persist(entry);
                }
            }
            return true;
        } catch (Exception e) {
            log.error("ERROR_PARSING_JSON:"+aData);
            e.printStackTrace();
            return false;
        }


    }

    /** Объединяем СЛС с родами */
    private void unionChildBirthHospital(List<Object[]> entriesIds) {
     //   log.warn("unionChildBirth1");
        E2Entry mainEntry = null; //theManager.find(E2Entry.class,Long.valueOf(entriesIds.get(0)[0].toString()));
        //Логика такая: если в патологии
        //вот тут мы добавляем проверку на роды: если была в патологии больше 5 дней - не объединяем случай со следующим
        Long patEntryid=null, rodEntryId=null;
        for(int i=0;i<entriesIds.size();i++) {
            boolean isBreak = false;
            E2Entry currentEntry = theManager.find(E2Entry.class,Long.valueOf(entriesIds.get(i)[0].toString()));
            if (mainEntry==null) {mainEntry=currentEntry;}
            if (currentEntry.getHelpKind().equals("14")) { //Если главый случай - патология беременности
                patEntryid=currentEntry.getId();
            //    log.warn(i+"patologiya found");
                VocE2FondV009 perevodResult =(VocE2FondV009)getActualVocByClassName(VocE2FondV009.class,currentEntry.getFinishDate()," code='104'"); //TODO Колхоз - исправить
                if (isNotNull(currentEntry.getCalendarDays())&&currentEntry.getCalendarDays()>5) { //Если длительность случая - больше пяти дней - не объединяемъ
            //        log.warn(i+"patologiya >5 days.");
                    mainEntry.setFondResult(perevodResult); //TODO Колхоз - исправить
                    mainEntry.setIsUnion(true);
                    makeCheckEntry(currentEntry,false,false);
                    mainEntry=null; //считаем что главный случай = текущий случай
                    isBreak=true;
                } else if (isNotNull(currentEntry.getCalendarDays())&&currentEntry.getCalendarDays()>1) { //Если длительность случая - не менее 2х дней - проверяем диагнозы
             //       log.warn(i+"patologiya >1 days.");
                    String[] mkbs = {"O14.1","O34.2","O36.3","O36.4","O42.2"}; //диагнозы, при которых можно подать случай
                    String mainMkb =currentEntry.getMainMkb();
                    if (isNotNull(mainMkb)) {
                        for (String mkb: mkbs) {
                            if (mainMkb.equals(mkb)) {
                                mainEntry.setFondResult(perevodResult);
                                mainEntry.setIsUnion(true);
                                makeCheckEntry(currentEntry,false,false);
                                mainEntry=null;
                                isBreak=true;
                                continue;
                            }
                        }
                    }
                }
            } else {
                if (currentEntry.getDepartmentId()==203) {rodEntryId=currentEntry.getId();} //Находим родовое отделение
            }
            if (isBreak)continue;
            if (patEntryid!=null&&rodEntryId!=null) { //Переносим операции из паталогии в роды
                theManager.createNativeQuery("update entrymedservice set entry_id=:rod where entry_id=:pat")
                        .setParameter("rod",rodEntryId).setParameter("pat",patEntryid).executeUpdate();
                patEntryid=null;rodEntryId=null;
            }
            //if(currentEntry==null) {log.error(i+"currentE = null "+entriesIds.get(i)[0]);}
            //if(mainEntry==null) {log.error(i+"mainE = null"+entriesIds.get(i)[0]);}
            if (currentEntry.getId()==mainEntry.getId()) {continue;}
            //Отделение - точно не патология беременности, её можно объединять!
            if(isNotNull(mainEntry.getNewbornAmount())) {currentEntry.setNewbornAmount(mainEntry.getNewbornAmount());} //Переносим информация о детях из родового отделения в неродовое
            unionEntries(currentEntry,mainEntry); //считаем что последнее СЛО будет главным
            mainEntry=currentEntry;
        }


        setRightParent(mainEntry.getListEntry().getId(),mainEntry.getExternalParentId());
        makeCheckEntry(mainEntry,false, true);
    }

    /** Удаляем дубли по поликлинике
     * дублем считаем повторное посещение про оодному профилю мед. помощи за исключением мобильной поликлиники, НМП и КДО
     * */
    private void deletePolyclinicDoubles(Long aListEntryId) {
        log.info(aListEntryId+" deletingDoubles...");
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("select max(e2.id), e2.externalpatientid , medhelpprofile_id,startdate, servicestream from e2entry e2 where e2.listentry_id =:listId")
                .append(" and e2.entryType='POLYCLINIC' and (e2.isDeleted is null or e2.isDeleted='0') and (e2.isUnion is null or e2.isUnion='0') and e2.serviceStream!='COMPLEXCASE'")
                .append(" and (e2.isMobilePolyclinic is null or e2.isMobilePolyclinic='0') and (e2.isEmergency is null or e2.isEmergency='0')")
                .append(" and (e2.isDiagnosticSpo is null or e2.isDiagnosticSpo='0') ")
                .append(" group by e2.externalpatientid , medhelpprofile_id, startdate, servicestream")
                .append(" having count(e2.id)>1");
        List<Object[]> list = theManager.createNativeQuery(searchSql.toString()).setParameter("listId",aListEntryId).getResultList();
        log.info("list for delete double = "+list.size());
        if (list.size()>0) {
            for (Object[] o:list) {
                theManager.createNativeQuery("update e2entry set isDeleted='1' where id="+o[0].toString()).executeUpdate();
            }
            deletePolyclinicDoubles(aListEntryId);
        }
    }
    /** Формирует КДО из СПО */
    private void unionPolyclinicKdoMedCase(Long aListEntryId, List<E2Entry> aEntryList) {
        //Находим СПО, находим визит к "главному специалисту", создаем копию, визиты - входящие в компл. случай
        //у всех комп. случаев убираем услуги
        StringBuilder sql = new StringBuilder();
        sql.append("select e2.externalparentid, list(''||e2.id) from e2entry e2 where e2.listentry_id=").append(aListEntryId)
           .append(" and (e2.isDeleted is null or e2.isDeleted='0') and (e2.isUnion is null or e2.isUnion='0') and e2.serviceStream!='COMPLEXCASE'")
           .append(" group by e2.externalparentid");
        List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList();

        log.info("union_kdo.list="+list.size()+", sql = "+sql.toString()+", id="+aListEntryId);
        if (list.isEmpty()) {
            log.error("KDO_LIST_IS_EMPTY");
            list = theManager.createNativeQuery("select *  from e2entry where id="+(aEntryList!=null?aEntryList.get(0).getId():0)).getResultList();
            log.info("ENTRY MUST BE! id1="+aEntryList.get(0).getId()+", size = "+list.size());
            for (Object[] o:list) {
                log.info("o="+o[0]+" : " +o[1]+" : "+o[2]);
            }
            return;
        }
        for (Object[] spo: list) {
            E2Entry mainEntry=null;
            String[] ids = spo[1].toString().split(",");
            E2Entry currentEntry = null;
            for (String id:ids) {
                Long currentId = toLong(id);
                currentEntry = theManager.find(E2Entry.class,currentId);
                if (currentEntry.getFondDoctorSpec()!=null&&currentEntry.getFondDoctorSpec().getIsKdoChief()!=null&&currentEntry.getFondDoctorSpec().getIsKdoChief()){
                    log.info("KDO = found main entry");
                    //Нашли визит главного специалиста в КДО
                    mainEntry = cloneEntity(currentEntry);
                    theManager.persist(mainEntry);
                    cloneMedService(currentEntry,mainEntry);
                    cloneDiagnosis(currentEntry,mainEntry);
                    break;
                }
            }
            if (mainEntry!=null) {
                for (String id:ids) {
                    E2Entry entry = theManager.find(E2Entry.class,toLong(id));
                    entry.setServiceStream(E2Enumerator.COMPLEXSERVICESTREAM);
                    entry.setIsUnion(true);
                    entry.setParentEntry(mainEntry);
                    theManager.persist(entry);
                }
            } else {
                theManager.persist(new E2EntryError(currentEntry,"NO_MAINCASE_KDO"));
                log.error("Не удалось найти главный случай в КДО");
            }


        }

    }
    /** Склеивание поликлинических визитов*/
    private void unionPolyclinicMedCase(Long aListEntryId, Long aPatientId) {
        /** Объединяем  */
    StringBuilder searchSql = new StringBuilder();
    searchSql.append("select e2.externalpatientid , medhelpprofile_id, servicestream,substring(e2.mainmkb,1,1), list(e2.id||'') from e2entry e2 where e2.listentry_id =:listId")
        .append(" and e2.entryType='POLYCLINIC'")
        .append(aPatientId!=null&&aPatientId>0?" and e2.externalpatientid="+aPatientId:"")
        .append(" and (e2.isDeleted is null or e2.isDeleted='0') and (e2.isUnion is null or e2.isUnion='0') and e2.serviceStream!='COMPLEXCASE'")
        .append(" and (e2.isMobilePolyclinic is null or e2.isMobilePolyclinic='0') and (e2.isEmergency is null or e2.isEmergency='0')")
        .append(" and (e2.isDiagnosticSpo is null or e2.isDiagnosticSpo='0') ")
        .append(" group by e2.externalpatientid , medhelpprofile_id, servicestream,substring(e2.mainmkb,1,1)")
        .append(" having count(e2.id)>1");

        List<Object[]> list = theManager.createNativeQuery(searchSql.toString()).setParameter("listId",aListEntryId).getResultList();
        log.info("sql = "+searchSql+", size = "+list.size());
        for (Object[] spo: list){
            //Создаем новую запись, все существущие помечаем как COMPLEXCASE
            String[] ids = spo[4].toString().split(",");
            E2Entry mainEntry = null;
            for (String idd: ids) {
                Long id = Long.valueOf(idd.trim());
                E2Entry entry = theManager.find(E2Entry.class,id);
                if (mainEntry==null) {
                    mainEntry=cloneEntity(entry);
                    createDiagnosis(mainEntry);

                }
                mainEntry = unionPolyclinic(mainEntry,entry);
            }
            makeCheckEntry(mainEntry,false,true);
        }
    }
    /** Физическое объединение случая*/
    private E2Entry unionPolyclinic(E2Entry aMainMedcase, E2Entry aSecondMedcase) {
        E2Entry mainEntry, secondaryEntry;//mainEntry - latest entry
        if (aMainMedcase.getStartDate().getTime()>aSecondMedcase.getStartDate().getTime()){
            aMainMedcase.setStartDate(aSecondMedcase.getStartDate());
        }
        if (aMainMedcase.getFinishDate().getTime()<aSecondMedcase.getFinishDate().getTime()){
            aMainMedcase.setFinishDate(aSecondMedcase.getFinishDate());
        }
        aSecondMedcase.setParentEntry(aMainMedcase);
        aSecondMedcase.setServiceStream("COMPLEXCASE");
        aSecondMedcase.setIsUnion(true);
        theManager.persist(aMainMedcase);
        theManager.persist(aSecondMedcase);
        return aMainMedcase;

    }
    public void testUnionMecCase (Long aListEntryId, Long aHospitalMedcaseId, Long aPatientId, String aEntryType) {
        if (aEntryType.equals(POLYCLINICTYPE)&&aPatientId!=null) {
            log.info("testUnionPolyclinicMedCase");
            unionPolyclinicMedCase(aListEntryId,aPatientId);
        } else if (aHospitalMedcaseId!=null) {
            log.info("testUnionHospitalMedCase");
            unionHospitalMedCase(aListEntryId, aHospitalMedcaseId);
        }
    }
    /** Объединеяем все записи по СЛС *Логика объединения тут */
    private void unionHospitalMedCase (Long aListEntryId, Long aHospitalMedCaseId) {

      //  log.warn("union "+aListEntryId +",hosp="+aHospitalMedCaseId);
        /**
         * Если у двух случаев равный КЗ, берем данным (врач, отделение, койки) последнего случая
         * Если классы МКБ совпадают, берем СЛО с наибольшим КЗ, из второго СЛО добавляем дни и услуги.
         *      Второе СЛО помечаем как "200 входит в комплексный случай и ставим у него parentEntry  главного случая
         */
        // if (!aMainEntry.getExternalParentId().equals(aEntry.getExternalParentId())) { throw new IllegalStateException("Невозможно объединить случаи из разных СЛС");}
        //объединяем все СЛО внутри СЛС
        if (aListEntryId==null||aHospitalMedCaseId==null) {throw new IllegalStateException("Необходимо указать ИД заполнения и ИД госпитализации");}
        /*List<Object[]> entriesIds = theManager.createNativeQuery("select id, entryType, departmentId from e2entry e " +
                " where e.listentry_id= "+aListEntryId+" and externalparentid=" + aHospitalMedCaseId + "and serviceStream!='COMPLEXCASE' and (isUnion is null or isUnion='0') and (isDeleted is null or isDeleted='0') order by startdate, starttime").getResultList(); //Все СЛО по госпитализации, кроме уже объединенных
                */
        List<Object[]> entriesIds = theManager.createNativeQuery("select id, entryType, departmentId from e2entry e " +
                " where e.listentry_id= "+aListEntryId+" and externalparentid=" + aHospitalMedCaseId + " and (isDeleted is null or isDeleted='0') order by startdate, starttime").getResultList(); //Все СЛО по госпитализации, кроме уже объединенных
        E2Entry mainEntry = null;
        E2Entry currentEntry;
        if (entriesIds.size() > 1) { //Работаем если только найдено больше 1 СЛО
         //   E2Entry lastEntry = theManager.find(E2Entry.class,Long.valueOf(entriesIds.get(entriesIds.size()-1)[0].toString())); //Находим последнее СЛО
          //  log.warn("union = SLS = "+aHospitalMedCaseId+", size = "+entriesIds.size());
    //Цикл только для ВМП
            for (Object[] o: entriesIds) {
                if (isNotNull(o[1])&&o[1].toString().equals(VMPTYPE)) { //Если в госпитализации есть случай ВМП, делаем его главным, остальные - неглавные.
                    mainEntry = theManager.find(E2Entry.class,Long.valueOf(o[0].toString()));
                    mainEntry.setStartDate(mainEntry.getHospitalStartDate());
                    mainEntry.setFinishDate(mainEntry.getHospitalFinishDate());
                    mainEntry.setIsUnion(true);

                    theManager.createNativeQuery("update e2entry set serviceStream=:serviceStream, parentEntry_id=:parent, isunion='1' where listentry_id=:listEntry and externalparentId=:hospitalId and id!=:id")
                            .setParameter("serviceStream",E2Enumerator.COMPLEXSERVICESTREAM)
                            .setParameter("parent",mainEntry.getId()).setParameter("listEntry",aListEntryId).setParameter("hospitalId",aHospitalMedCaseId)
                            .setParameter("id",mainEntry.getId()).executeUpdate(); //Все остальные записи помечаем как входящие в случай ВМП

                    log.info("Найден случай в ВМП, помечаем его как главный");
                    theManager.persist(mainEntry);
                    return;
                }
                if (isNotNull(o[2].toString())&&o[2].toString().equals("203")) { //В СЛС есть родовое отделение - запускаем функция по объединению родов!
                    unionChildBirthHospital(entriesIds);
                    return;

                }
            }

        //На этом этапе мы уверены, что ВМП в случае у нас нет, случай не содержит родов
            //VocE2FondV009 perevodResult =
        for (Object[] objects: entriesIds) {

            Long entryId = Long.valueOf(objects[0].toString());
          //  log.warn("unionCnt = "+entryId);
            if (mainEntry == null) { //находим первую запись, считаем её главной
            //    log.warn("unionCntFirst = "+entryId);
                mainEntry = theManager.find(E2Entry.class, entryId);
                continue;
            }
            currentEntry = theManager.find(E2Entry.class,entryId);
          //  log.warn("unionCntNotFirst = "+entryId);
            if (currentEntry.getNoOmcDepartment()!=null&&currentEntry.getNoOmcDepartment()) { //Если реанимация - смело объединаем с главным случаем.
           //     log.warn("unionCntNotFirstReanimation = "+entryId);
                mainEntry.setReanimationEntry(currentEntry);
                unionEntries(mainEntry,currentEntry);
            } else { //например - кардиология - сосуд. хирургия (вторая - главная
                //Если
                if (isDiagnosisGroupAreEquals(mainEntry,currentEntry)) { //Если классы МКБ сходятся
                    if (mainEntry.getCost()==null&&currentEntry.getCost()==null) {
                        log.error("Невозможно объеинить случаи: нет цены ни в одном из случаев");
                        continue;
                    } else if (mainEntry.getDepartmentId()==currentEntry.getDepartmentId()) { //Если ИД отделения равны - не учитываем цену
                        unionEntries(currentEntry,mainEntry); //последнее отделение - главное
                    } else {
                        if (currentEntry.getCost()==null|| (mainEntry.getCost()!=null&&mainEntry.getCost().compareTo(currentEntry.getCost())>0)) { //Если у первого случая цена больше второго, первый - главный.
                            unionEntries(mainEntry,currentEntry);
                        } else {
                            unionEntries(currentEntry, mainEntry); //Если цена текущего случая больше или равно главному случаю, то текущий случай становится главный
                            mainEntry = currentEntry;
                            continue;
                        }
                    }

                } else { //Если классы МКБ не сходятся, текущее СЛО становится главным
                    String ss = mainEntry.getBedSubType(); //Текущему случаю ставим результат - перевод на другой профиль коек
                    mainEntry.setFondResult((VocE2FondV009)getActualVocByClassName(VocE2FondV009.class,null," code='"+ss+"04'")); //TODO Колхоз - исправить
                    theManager.persist(mainEntry);
                    mainEntry = currentEntry;
                    continue;
                }
            }
        }
        if (entriesIds.size()>2) {setRightParent(aListEntryId,aHospitalMedCaseId);}

    }

    }
    /** Делаем правильных родителей для комплексных случаев */
    private void setRightParent(Long aListEntryId, Long aHospitalMedCaseId) { //находим все "комплексные" случаи, у которых есть дочерние случаи и делаем дочерние случаи дочерними случаями родителя "комплексного" случая
        StringBuilder sql = new StringBuilder();
        sql.append("select eChild.id, e.parentEntry_id ")
        .append(" from e2entry e")
        .append(" left join e2entry eChild on eChild.parentEntry_id=e.id")
        .append(" where e.listentry_id=").append(aListEntryId).append(" and e.externalParentId=").append(aHospitalMedCaseId)
        .append(" and eChild.id is not null and e.serviceStream='COMPLEXCASE' and (e.isDeleted is null or e.isDeleted='0')");
        List<Object[]> res = theManager.createNativeQuery(sql.toString()).getResultList();
        if (res.isEmpty()) {return;}
        for (Object[] o:res) {
            String sqlU  = "update e2entry set parentEntry_id="+o[1].toString()+" where id="+o[0].toString();
            log.warn("set parent="+sqlU);
            theManager.createNativeQuery(sqlU).executeUpdate();
        }
        setRightParent(aListEntryId,aHospitalMedCaseId);
    }

    /** тупо объединяем, не думаем */
    public void unionEntries(E2Entry aMainEntry, E2Entry aNotMainEntry) { //Функционал по объединению случаев
         if (aMainEntry==null||aNotMainEntry==null) {throw new IllegalStateException("UNOIN = entry=null");}
        if (aMainEntry.getStartDate().getTime()>aNotMainEntry.getStartDate().getTime()||(aMainEntry.getStartDate().getTime()==aNotMainEntry.getStartDate().getTime()&&aMainEntry.getStartTime().after(aNotMainEntry.getStartTime()))) {
            //Если дата+время начала главного случая позднее даты и время начала неглавного случая
            aMainEntry.setStartDate(aNotMainEntry.getStartDate());//Дата начала главного случая = дате начала текущего случая

            aMainEntry.setStartTime(aNotMainEntry.getStartTime());//Время начала главного случая = времени окончания начала текущего случая
        } else if (aNotMainEntry.getFinishDate().getTime()>aMainEntry.getFinishDate().getTime()||(aNotMainEntry.getFinishDate().getTime()==aMainEntry.getFinishDate().getTime()&&aNotMainEntry.getFinishTime().after(aMainEntry.getFinishTime()))) {
            //Если дата и время окончания неглавного случая позже даты и время окончания главного случая
            aMainEntry.setFinishDate(aNotMainEntry.getFinishDate());//Дата окончания главного случая = дате окончания текущего случая
            aMainEntry.setFinishTime(aNotMainEntry.getFinishTime());//Время окончания главного случая = времени окончания текущего случая
        } else {
            //В случаях, если есть ВМП в не первом случае, возможно и такое (текущий СЛО находится между датами С и ПО главного случая. Пропускаем...
        }
        if (aNotMainEntry.getReanimationEntry()!=null) {aMainEntry.setReanimationEntry(aNotMainEntry);} //Если в объединяемом случае была реанимация - она будет в главном случае
       // theManager.createNativeQuery("update entrydiagnosis set entry_id=:mainId where entry_id=:notMainId").setParameter("mainId", aMainEntry.getId()).setParameter("notMainId",aNotMainEntry.getId()).executeUpdate(); //убрано 03-04-2018
        //log.warn("move services size="+theManager.createNativeQuery("update entrymedservice set entry_id=:mainId where entry_id=:notMainId").setParameter("mainId", aMainEntry.getId()).setParameter("notMainId",aNotMainEntry.getId()).executeUpdate());
        List<EntryMedService> serviceList =theManager.createQuery("from EntryMedService where entry_id=:id").setParameter("id",aNotMainEntry.getId()).getResultList();// aNotMainEntry.getMedServices();

         if (serviceList==null||serviceList.isEmpty()) {
         } else {
             int i=0;
             for (EntryMedService service: serviceList) {
                 i++;
                 service.setEntry(aMainEntry);
                 theManager.persist(service);
             }
         }

        //Удаляем дубли диагнозов по случаю
        /*
        try {
            List<String> dss = theManager.createNativeQuery("select list(''||id) from entrydiagnosis  " +
                    " where entry_id =:id group by mkb_id, registrationtype_id, priority_id,illnessprimary" +
                    " having count(mkb_id)>1").setParameter("id",aMainEntry.getId()).getResultList();
            if(dss.size()>0) {
                for (String ds: dss) {
                    theManager.createNativeQuery("delete from entrydiagnosis where id in ("+ds.substring(ds.indexOf(",")+1)+")").executeUpdate();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        aMainEntry.setIsUnion(true);
        aNotMainEntry.setIsUnion(true);
        aNotMainEntry.setParentEntry(aMainEntry);
        aNotMainEntry.setServiceStream("COMPLEXCASE");
        theManager.persist(aMainEntry);
        theManager.persist(aNotMainEntry);
    }

    /** Сравнимаем группы МКБ диагнозов для определения возможности склеивания СЛО */
    private boolean isDiagnosisGroupAreEquals(E2Entry aFirstEntry, E2Entry aSecondEntry) {
        List<EntryDiagnosis> firstDiagnosisList = theManager.createQuery("from EntryDiagnosis where entry_id=:id and registrationType.code='4' and priority.code='1' ").setParameter("id", aFirstEntry.getId()).getResultList();
        List<EntryDiagnosis> secondDiagnosisList = theManager.createQuery("from EntryDiagnosis where entry_id=:id and registrationType.code='4' and priority.code='1' ").setParameter("id", aSecondEntry.getId()).getResultList();
        if(firstDiagnosisList.isEmpty()) {
            E2EntryError error = new E2EntryError(aFirstEntry,"NO_DIAGNOSIS");
            theManager.persist(error);
            log.error("Не найдено основного клинического диагноза по случаю NO_MAIN_DIAGNOSIS");
            return false;
        } else if(secondDiagnosisList.isEmpty()) {
            E2EntryError error = new E2EntryError(aSecondEntry, "NO_DIAGNOSIS");
            theManager.persist(error);
            log.error("Не найдено основного клинического диагноза по случаю NO_MAIN_DIAGNOSIS");
            return false;
        }
        if (firstDiagnosisList.get(0).getMkb().getCode().charAt(0)==secondDiagnosisList.get(0).getMkb().getCode().charAt(0)) {
            //Если классы МКБ равны
            return true;
        } else {
            return false;
        }


    }
    /** Переводим дату в строку в формате yyyy-DD-mm*/
    private String dateToString(Date aDate) {
        return dateToString(aDate,  "yyyy-MM-dd");
    }

    /** Переводим дату в строку в заданном формате */
    private String dateToString(Date aDate, String aFormat) {

        SimpleDateFormat format = new SimpleDateFormat(aFormat);
        return format.format(aDate);
    }


    /** Проверяем, является ли объект NULL либо пустой строкой */
    public boolean isNotNull(Object aField) {
        if (aField == null) return false;
        if (aField instanceof String) {
            String ss = (String) aField;
            return ss != null && !ss.trim().equals("");
        } else if (aField instanceof Boolean) {
            return aField!=null?(Boolean) aField:false;
        } else if (aField instanceof Long) {
            return ((Long) aField) > 0L;
        } else if (aField instanceof java.sql.Date) {
            return aField!=null;
        } else if (aField instanceof BigDecimal) {
            return aField != null&&((BigDecimal) aField).compareTo(new BigDecimal(0))==1;
        }else {
            throw new IllegalStateException("Нет преобразования для объекта " + aField);
        }
    }

       private String getWorkDir() {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    }

    /** Получение значения из справочника V002 по профилю коек */
    private static HashMap<String, VocE2MedHelpProfile> bedTypes = new HashMap<String, VocE2MedHelpProfile>();
    private void setProfileByBedType(E2Entry aEntry) {
        String bedType=aEntry.getHelpKind();
        String key = bedType;
        String sqlAdd="";
        VocE2MedHelpProfile profile;
        if (bedType.equals("0M")&&aEntry.getDepartmentId()==266) {
            //Если неврология 2 и профиль  - неврологические = профиль = 310
            key=bedType+"#"+aEntry.getDepartmentId();
            if (!bedTypes.containsKey(key)) {
                profile = getActualVocByClassName(VocE2MedHelpProfile.class,null,"profilek='310'");
                bedTypes.put(key,profile);
            }

        } else if (bedType.equals("01")&&isNotNull(aEntry.getDepartmentId())&&aEntry.getDepartmentId()==262) {
            //Если кардиология2 и профиль  - кардиологический профиль = 309
            key=bedType+"#"+aEntry.getDepartmentId();
            if (!bedTypes.containsKey(key)) {
                profile = getActualVocByClassName(VocE2MedHelpProfile.class,null,"profilek='309'");
                bedTypes.put(key,profile);
            }
        } else  {
            if(!bedTypes.containsKey(key)) { //Если нет в карте - запускаем поиск
                List<BigInteger> list = theManager.createNativeQuery("select v.id from vocbedtype vbt left join e2medhelpprofilebedtype mhpbt on mhpbt.bedtype_id=vbt.id " +
                        " left join voce2medhelpprofile v on v.id= mhpbt.profile_id where vbt.omccode=:code and v.id is not null").setParameter("code", bedType).getResultList();
                if (list.isEmpty()) {
                    E2EntryError error = new E2EntryError(aEntry, "NO_PROFILE");
                    theManager.persist(error);
                    log.error("Не найдено профиля мед. помощи для коек с типом NO_PROFILE_FOR_BED " + bedType);
                } else if (list.size() > 1) {
                    E2EntryError error = new E2EntryError(aEntry, "TOO_MANY_PROFILE");
                    theManager.persist(error);
                    log.error("Найдно больше 1 соответствия с профилем мед. помощи для коек с типом TO_MANY_PROFILE_FOR_BED " + bedType);
                } else {
                    profile = theManager.find(VocE2MedHelpProfile.class,list.get(0).longValue());
                    bedTypes.put(key,profile);
                }

            }
        }

         profile = bedTypes.get(key);
        if (isNotNull(aEntry.getVMPKind())) {
            if (profile!=null&&profile.getCode().equals("29")) { // TODO Если ВМП в кардиологических койках, меняем профиль коек на 81
                key = "VMP#" + profile.getCode();
                if (!bedTypes.containsKey(key)) {
                    profile = getActualVocByClassName(VocE2MedHelpProfile.class,null,"profilek='81'");
                    bedTypes.put(key,profile);
                } else {
                    profile=bedTypes.get(key);
                }
            } else if (aEntry.getVMPKind().equals("27.00.18.001")) {
                key="VMP#KIND"+aEntry.getVMPKind();
                if (!bedTypes.containsKey(key)) {
                    profile = getActualVocByClassName(VocE2MedHelpProfile.class,null,"profilek='55'");
                    bedTypes.put(key,profile);
                } else {
                    profile=bedTypes.get(key);
                }
            }

        }
        aEntry.setMedHelpProfile(profile); //null так null
       theManager.persist(aEntry);

    }

    /** Добавляем соответствие между профилем мед. помощи и профилем койки */
    //Для ServiceJS------------------------------------------------------------
    public void addMedHelpProfileBedType(Long aMedHelpId, Long aBedTypeId) {
        VocBedType bedType = theManager.find(VocBedType.class, aBedTypeId);
        if (theManager.createNativeQuery("select e. id from E2MedHelpProfileBedType e left join vocbedType vbt on vbt.id=e.bedtype_id " +
                " where e.profile_id=:profile and (e.bedtype_id=:bedTypeId or vbt.omcCode=:omcCode)").setParameter("profile",aMedHelpId).setParameter("bedTypeId", aBedTypeId)
                .setParameter("omcCode", bedType.getOmcCode()).getResultList().size() > 0) {
            log.warn("Нельзя прикрепить койку, т.к. койка с таким кодом уже прикреплена");
            return;
        }
        E2MedHelpProfileBedType mhbt = new E2MedHelpProfileBedType();
        mhbt.setBedType(bedType);
        mhbt.setProfile(theManager.find(VocE2MedHelpProfile.class, aMedHelpId));
        theManager.persist(mhbt);
    }


    public void addHospitalMedCaseToList(String aHistoryNumber, Long aListEntryId) throws SQLException, NamingException {
        if (isNotNull(aHistoryNumber)) {
            fillListEntry(theManager.find(E2ListEntry.class, aListEntryId), aHistoryNumber);
        }
    }

    /** Переформировывание заполнения */
    public void reFillListEntry(Long aListEntryId)  {
        E2ListEntry list =theManager.find(E2ListEntry.class,aListEntryId);
        //String ids = (String) theManager.createNativeQuery("select list(''||id) from e2entry where listEntry_id =:id").setParameter("id",aListEntryId).getSingleResult();
        theManager.createNativeQuery("delete from e2entryerror where listentry_id=:id").setParameter("id",aListEntryId).executeUpdate();
        theManager.createNativeQuery("update e2entry set isDeleted='1' where listEntry_id =:id").setParameter("id",aListEntryId).executeUpdate();
        try {
            list.setCheckDate(null);
            list.setCheckTime(null);
            theManager.persist(list);
            fillListEntry(list,null);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запускаем процесс формирования заполнения
     *
     * @param aListEntry - Заполнение
     * @throws NamingException
     * @throws SQLException
     */

    public void fillListEntry(E2ListEntry aListEntry, String aHistoryNumbers) throws NamingException, SQLException {
        String listEntryType = aListEntry.getEntryType() != null ? aListEntry.getEntryType().getCode() : null;
        //TODO Прогресс бар формирования записей

        if (listEntryType == null) {
            log.error("Не указан тип заполнения NO_ENTRYLIST_TYPE");
            return;
        }

        String resourceName;
        if (listEntryType.equals(EXTDISPTYPE)) {
            resourceName = "ExtDisp.sql";
        } else if (listEntryType.equals(HOSPITALTYPE)) {
            resourceName = "Hospital.sql";
        } else if (listEntryType.equals(POLYCLINICTYPE)) {
            resourceName = "Visit.sql";
        } else if (listEntryType.equals(HOSPITALPEREVODTYPE)) {
            resourceName = "HospitalPerevod.sql";
        } else if (listEntryType.equals(POLYCLINICKDOTYPE)) {
            resourceName="VisitKdo.sql";
        } else {
            log.error("Неизвесный тип заполнения");
            throw new IllegalStateException("Неизвестный тип заполнения!!");
        }
        String searchSql = getFileAsSTring(resourceName);
        if (searchSql == null) {
            log.error("NO SQL FILE FOUND");
            throw new IllegalStateException("Не удалось обнаружить файл с запросом! "+resourceName);
        }

        StringBuilder sqlHistory = new StringBuilder();
        if (isNotNull(aHistoryNumbers)) {
            String[] histories = aHistoryNumbers.split(",");
            sqlHistory.append(" and ss.code in (");
            boolean isFirst = true;
            for (String history: histories) {
                if (!isFirst) {sqlHistory.append(",");} else {isFirst=false;}
                sqlHistory.append("'").append(history).append("'");
            }
            sqlHistory.append(")");
        } else {
            sqlHistory.append("");
        }
        while(searchSql.indexOf("##dateStart##")>-1) {searchSql=searchSql.replace("##dateStart##", toSQlDateString(aListEntry.getStartDate())); }
        while(searchSql.indexOf("##dateEnd##")>-1) {searchSql=searchSql.replace("##dateEnd##", toSQlDateString(aListEntry.getFinishDate()));}
        searchSql+=sqlHistory.toString();
        log.info("SQL = " + searchSql);

        Statement statement = createStatement();
        ResultSet foundCases = statement.executeQuery(searchSql);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("listEntry", aListEntry);

        try {
            createEntriesByEntryList(foundCases, paramMap, listEntryType, aListEntry); //Создаем записи по заполнению


        } catch (ParseException e) {
            log.error("can't parse data");
            e.printStackTrace();
        }
    }

    /** Базовая точка для выполнения всех проверок внутри заполнения */
    public void checkListEntry(Long aListEntryId, boolean updateKsgIfExist, String aParams, long aMonitorId) {checkListEntry(theManager.find(E2ListEntry.class,aListEntryId),updateKsgIfExist, aParams,aMonitorId );}
    private void checkListEntry(E2ListEntry aListEntry, final boolean updateKsgIfExist, String aParams, long aMonitorId) {
        if (aListEntry.getIsClosed()!=null&&aListEntry.getIsClosed()) {log.warn("Заполнение закрыто, проверка невозможна");throw new IllegalStateException("Заполнение закрыто, проверка невозможна");}
        try {
            java.util.Date startStartDate = new java.util.Date();
            log.info("start checkListEntry");
            if (isCheckIsRunning!=null&&isCheckIsRunning) {
                log.warn("Проверка уже запущена, ничего не проверяем");
                return;
            }
            isCheckIsRunning=true;

            //TODO прогресс-бар формирования проверки

            cleanAllMaps(); //очистим карты перед началом новой проверки
            if (aListEntry.getCheckDate()==null) { //Запускаем первичную проверку всех записей
                checkListEntryFirst(aListEntry,null, aMonitorId);
                return;
            }
            StringBuilder sql = new StringBuilder();
            sql.append("select id from E2Entry where listEntry_id=:id and (isDeleted is null or isDeleted='0')");
            StringBuilder sqlAdd = new StringBuilder();
            if (isNotNull(aParams)) {
                log.warn(aParams);
              String[] params = aParams.split("&") ;
              for(String par:params) {
                  if (par.contains("=")) {
                      String parr[] = par.split("=");
                      if (parr.length<2||parr[1]==null||parr[1].trim().equals("")) {continue;}
                      if (parr[0].toUpperCase().indexOf("DATE")>-1) { //ищем дату
                          sql.append(" and ").append(parr[0]).append("=").append("to_date('"+parr[1]+"','dd.MM.yyyy') ");
                      } else { //ищем строку
                          sql.append(" and upper(").append(parr[0]).append(")=").append("'"+parr[1].toUpperCase()+"' ");
                      }
                  }
              }
            }
            theManager.createNativeQuery("delete from e2entryerror  where listentry_id=:id").setParameter("id",aListEntry.getId()).executeUpdate(); //Все ошибки удалим
            List<BigInteger> list = theManager.createNativeQuery(sql.toString()).setParameter("id",aListEntry.getId()).getResultList();

            if (list.isEmpty()) {log.warn("Случаев для проверки не найдено "+sql.toString());return;}
            IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Расчет цены случаев в звполнении") ;
            monitor = theMonitorService.startMonitor(aMonitorId,"Пересчет случаев в заполнении",list.size());
            monitor.advice(1);
            /* сначала найдем КСГ (для правильного объединения
            потом  производим объединение случаев, потом (только для ОМС) расчитываем поля фонда, считаем цену
            */
            String listEntryCode =aListEntry.getEntryType().getCode();
            if (listEntryCode.equals(HOSPITALTYPE)||listEntryCode.equals(HOSPITALPEREVODTYPE)) {
                int i=0;
            //    Thread[] threads = new Thread[3];
//Добавить
                for(BigInteger bi : list) {
                    i++;
                    final E2Entry entry = theManager.find(E2Entry.class,bi.longValue());
                 //   boolean isCheckIsStarted = false;
                //    while (!isCheckIsStarted) {
                  //      for (Thread thread: threads) {
                  //          if (thread ==null||!thread.isAlive())  {
                   //             thread = new Thread() {public synchronized void run(){
                                    makeCheckEntry(entry,updateKsgIfExist, true);
                     //           }};
                         //       thread.start();
                   //             isCheckIsStarted=true;
                      //          break;


                  //      Thread.sleep(1000);
                 //   }
                    if (i%100==0) {log.info("process hosp ... makeCheckEntry.... "+i);
                    monitor.setText("Расчитано "+i+ " записей (СТАЦИОНАР)");
                    }
                    //Теперь снова находим КСГ, расчитываем цену и коэффициенты
//                    makeCheckEntry(theManager.find(E2Entry.class,bi.longValue()),updateKsgIfExist);
                }
                monitor.setText("Идет процесс удаление дублей");
                checkDoubles(aListEntry);
            } else if (listEntryCode.equals(POLYCLINICTYPE)||listEntryCode.equals(POLYCLINICKDOTYPE)) {
                Long listEntryId = aListEntry.getId();
               if (listEntryCode.equals(POLYCLINICTYPE)){
                   monitor.setText("Удаление дублей в поликлинике");
                   deletePolyclinicDoubles(listEntryId );
                   monitor.setText("Склеивание случаев поликлинического обслуживания");
                   unionPolyclinicMedCase(listEntryId ,null);
               } else if (listEntryCode.equals(POLYCLINICKDOTYPE)) {
                   monitor.setText("Формируем случаи КДО");
                   unionPolyclinicKdoMedCase(listEntryId,null);
               }

                int i=0;
                for(BigInteger bi : list) {
                    i++;
                    if (i%100==0) {
                        log.info("process pol ... checking entry.... "+i);
                        monitor.setText("Расчет цены случая (поликлиника)");
                    }
                    //Теперь снова находим КСГ, расчитываем цену и коэффициенты
                    makeCheckEntry(theManager.find(E2Entry.class,bi.longValue()),updateKsgIfExist, true);
                }
            }
            Long currentTime = System.currentTimeMillis();
            monitor.setText("Закончена проверка! Время выполнения проверки (минут) = "+((currentTime-startStartDate.getTime()))/60000);
            monitor.finish("Закончена проверка! Время выполнения проверки (минут) = "+((currentTime-startStartDate.getTime()))/60000);
            log.info("Время выполнения проверки (минут) = "+((currentTime-startStartDate.getTime()))/60000);
            isCheckIsRunning=false;
            aListEntry.setCheckDate(new java.sql.Date(currentTime));
            aListEntry.setCheckTime(new java.sql.Time(currentTime));
            theManager.persist(aListEntry);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("check list entry finished!");
    }
    public void makeCheckEntry(Long aEntryId, boolean updateKsgIfExist) {
        if (!theManager.createNativeQuery("select e.id from e2entry e left join e2listentry el on el.id=e.listEntry_id where e.id=:id and el.isClosed='1'").setParameter("id",aEntryId).getResultList().isEmpty()) {throw new IllegalStateException("Заполнение закрыто, проверка невозможна");}
        cleanAllMaps();
        makeCheckEntry(theManager.find(E2Entry.class, aEntryId),  updateKsgIfExist, true);
    }

    /** Запустить проверку случая (расчет КСГ, цены, полей для xml) */
    final public void makeCheckEntry(E2Entry aEntry, boolean updateKsgIfExist, boolean checkErrors) {
        long bedDays = AgeUtil.calculateDays(aEntry.getStartDate(), aEntry.getFinishDate());
        long calendarDays = bedDays>0?bedDays+1:1;
        if (aEntry.getEntryType().equals(HOSPITALTYPE)&&isNotNull(aEntry.getBedSubType())&&aEntry.getBedSubType().equals("2")) {
            bedDays++;
        }
        try {
            setEntrySubType(aEntry);
            aEntry.setBedDays(bedDays > 0 ? bedDays : 1L);
            aEntry.setIsChild(AgeUtil.calcAgeYear(aEntry.getBirthDate(),aEntry.getStartDate())<18?true:false);
            aEntry.setCalendarDays(calendarDays);
            getBestKSG(aEntry, updateKsgIfExist); //Находим КСГ
            calculateFondField(aEntry);
            calculateEntryPrice(aEntry);
            if (checkErrors) checkErrors(aEntry);
            theManager.persist(aEntry);
        } catch (Exception e) {e.printStackTrace();}
    }

    /** Найдем подтип случая (посещение, обращение, НМП */
    HashMap<String, VocE2EntrySubType> entrySubTypeHashMap = new HashMap<String, VocE2EntrySubType>();
    private void setEntrySubType(E2Entry aEntry){
        String code;
        String entryType=aEntry.getEntryType();
        if (entryType.equals(HOSPITALTYPE)) {
            if (isNotNull(aEntry.getVMPKind())) {
                code="VMP";
            } else if (aEntry.getBedSubType().equals("1")) {
                code="ALLTIMEHOSP";
            } else if (aEntry.getBedSubType().equals("2")){
                code="DAYTIMEHOSP";
            } else {
                code="UNKNOWNTIMEHOSP";
            }
        } else if (entryType.equals(POLYCLINICTYPE)||entryType.equals(POLYCLINICKDOTYPE)) {
            String workPlace = aEntry.getWorkPlace();
            Boolean isMobilePolyclinic = isNotNull(aEntry.getIsMobilePolyclinic());
            if (isNotNull(aEntry.getIsDiagnosticSpo())) {
                code = "POL_KDO";
            } else {
                if (isNotNull(aEntry.getIsEmergency())) { // Случай НМП
                    code = "POL_EMERG";
                } else if (aEntry.getStartDate().getTime() == aEntry.getFinishDate().getTime()) { //разовый случай
                    String mainMkb = aEntry.getMainMkb();
                    if (isNotNull(mainMkb)) {
                        code = mainMkb.startsWith("Z") ? "VISIT_PROF" : "VISIT_ILL";
                    } else {
                        List<String> mkbs = findDiagnosisCodes(theManager.createQuery("from EntryDiagnosis where entry_id=:id").setParameter("id", aEntry.getId()).getResultList(), null, "1");
                        boolean isProf = false;
                        for (String mkb : mkbs) {
                            if (mkb.startsWith("Z")) {
                                isProf = true;
                                break;
                            }
                        }
                        code = isProf ? "VISIT_PROF" : "VISIT_ILL";
                    }
                } else { //Обращение
                    code = "POL_LONGCASE";
                }
                if (isMobilePolyclinic) {
                    code = "MOBILE_" + code;
                } else {
                    code = "CONS_" + code; //Для АМОКБ, сделать высчитываемым TODO
                }
                code += "_" + (workPlace != null ? workPlace : "");
            }
        } else if (entryType.equals(VMPTYPE)) {
            code = "STAC_VMP";
        } else {log.error(aEntry.getId()+" Неизвестный тип записи для проставление подтипа. CANT_SET_SUBTYPE: "+entryType); return;}


        if (code!=null){
            if(!entrySubTypeHashMap.containsKey(code)) {
                entrySubTypeHashMap.put(code,(VocE2EntrySubType) getEntityByCode(code, VocE2EntrySubType.class,true));
            }
            aEntry.setSubType(entrySubTypeHashMap.get(code));
        }
        theManager.persist(aEntry);
    }

    private void saveErrors(List<E2EntryError> errors) {
        if (errors!=null&&!errors.isEmpty()) {
            for(E2EntryError e: errors)
            theManager.persist(e);
        }

    }
    /** Проверка на дубли с другими заполнениями */
    private void checkDoubles(E2ListEntry aListEntry) {
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct new.id ")
                .append(" from e2entry  new")
                .append(" left join e2entry old on old.historyNumber=new.historyNumber and old.listentry_id!=:listEntryId and (old.isDeleted is null or old.isDeleted='0') and (old.doNotSend is null or old.doNotSend='0')")
                .append(" left join e2listentry listOld on listOld.id=old.listentry_id")
                .append(" left join e2bill bill on bill.id=old.bill_id")
                .append(" left join voce2billstatus bs on bs.id=bill.status_id")
                .append(" where new.listentry_id=:listEntryId and (new.isDeleted is null or new.isDeleted='0') and (new.doNotSend is null or new.doNotSend='0') and (listOld.isDeleted is null or listOld.isDeleted='0')")
                .append(" and new.startDate<old.finishDate and old.servicestream!='COMPLEXCASE' and new.servicestream!='COMPLEXCASE'")
                .append(" and old.medhelpprofile_id=new.medhelpprofile_id and bs.code='PAID'");
        List<BigInteger> list = theManager.createNativeQuery(sql.toString()).setParameter("listEntryId",aListEntry.getId()).getResultList();
        for(BigInteger id: list) {
            theManager.persist(new E2EntryError(theManager.find(E2Entry.class,id.longValue()),"DOUBLE_WITH_PREVIOUS Дубль с пред. заполнением!!"));
        }
    }
    /** Проверяем на пересечение случаев лечения в поликлинике при нахождении в это время в стационаре */
    private void checkVisitInStac(E2ListEntry aListEntry) {

    }
    private void checkErrors(E2Entry aEntry) {
        List<E2EntryError> errors = new ArrayList<E2EntryError>();
    if (isNotNull(aEntry.getServiceStream())&&aEntry.getServiceStream().equals("OBLIGATORYINSURANCE")) { //Проверка только для ОМС *05.06.2018
        //Дата выписки не входит в период
        if (aEntry.getFinishDate().getTime()>aEntry.getListEntry().getFinishDate().getTime()
                ||aEntry.getFinishDate().getTime()<aEntry.getListEntry().getStartDate().getTime()) {
            errors.add(new E2EntryError(aEntry,E2EntryErrorCode.DISCHARGE_DATE_NOT_IN_PERIOD));
        }
        if (aEntry.getEntryType().equals(HOSPITALTYPE)&& aEntry.getKsg()==null){
            errors.add(new E2EntryError(aEntry,E2EntryErrorCode.NO_KSG));
        }

        if (errors.size()>0){saveErrors(errors);}
    }

    }

    /**
     * Расчет цены случая //TODO
     *
     * @param aEntry
     */
    public void calculateEntryPrice(E2Entry aEntry) {
        String entryType = aEntry.getEntryListType(); //Переделать на aEntry.getEntryType();
        if (entryType.equals(HOSPITALTYPE)||entryType.equals(HOSPITALPEREVODTYPE)) {
             calculateHospitalEntryPrice(aEntry);
        } else if (entryType.equals(POLYCLINICTYPE)||entryType.equals(POLYCLINICKDOTYPE)) {
             calculatePolyclinicEntryPrice(aEntry);
        } else if (entryType.equals(EXTDISPTYPE)) {
             calculateExtDispEntryPrice(aEntry);
        } else {
            throw new IllegalStateException("Неизвестный тип реестра");
        }
    }

    /** Получить сущность по коду (в основном для справочников) */
    private <T> T getEntityByCode(String aCode, Class aClass, Boolean needCreate) {
        List<T> list = theManager.createQuery("from " + aClass.getName() + " where code=:code").setParameter("code", aCode).getResultList();
        T ret = !list.isEmpty() ? list.get(0) : null;
        if (list.isEmpty() && needCreate) {
            try {
                ret = (T) aClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (ret instanceof VocBaseEntity|| ret instanceof VocMedService) { //Проверить
                try {
                    Method setCodeMethod = ret.getClass().getMethod("setCode",new Class[]{String.class});
                    setCodeMethod.invoke(ret,aCode);
                    log.info("create new voc("+aClass.getName()+").set code = "+aCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            theManager.persist(ret);
            log.warn("crate new voc+"+ret);
        }
        return ret;
    }


    HashMap<String, Object > diagnosisMap = new HashMap<String, Object>();
    private List<EntryDiagnosis> getDiagnosis(E2Entry aEntry) {return   theManager.createQuery("from EntryDiagnosis where entry_id=:id").setParameter("id", aEntry.getId()).getResultList();}
    /** Создаем список диагнозов из строки с диагнозами +устанавливаем основной диагноз */ //делаем разово
    private void createDiagnosis(E2Entry aEntry) {
        try {
            String diagnosisList = aEntry.getDiagnosisList();
            //theManager.createNativeQuery("delete from EntryDiagnosis where entry_id=:id").setParameter("id", aEntry.getId()).executeUpdate();
            if (diagnosisList != null && !diagnosisList.trim().equals("")) { //Создаем диагнозы для каждой записи
                String[] diagnosiss = diagnosisList.split(",");
                String mkb, regType,priority;
             ///   String mainMkb = null;
                boolean foundClinical=false, foundDischarge = false;
                List<Long> clinicalIds = new ArrayList<Long>();
                for (String ds : diagnosiss) { //(0)МКБ#(1)приоритет#(2)первичность#(3)острота#(4)тип_регистрации#(5)ДТП#(6)характер_заболевания#(7)доп_код_ожоги
                    String[] data = ds.split("#",-1);
                    mkb = data[0];
                    regType = data[4];
                    priority=data[1];
                    boolean isClinical=false,isDischarge=false;
                    if (!diagnosisMap.containsKey("MKB_"+mkb)) {
                        diagnosisMap.put("MKB_"+mkb, getEntityByCode(mkb, VocIdc10.class, false));
                    }
                    if (!diagnosisMap.containsKey("REGTYPE_"+regType)) {
                        diagnosisMap.put("REGTYPE_"+regType, getEntityByCode(regType, VocDiagnosisRegistrationType.class, false));
                        if (regType.equals("3")) {foundDischarge=true;isDischarge=true;}
                        else if (regType.equals("4")) {foundClinical =true;isClinical=true;}
                    }
                    if (!diagnosisMap.containsKey("PRIORITY_"+priority)) {
                        diagnosisMap.put("PRIORITY_"+priority,  getEntityByCode(priority, VocPriorityDiagnosis.class, false));
                    }
                    if (foundDischarge&&isClinical) {foundClinical=false;continue;} //Если нашли клинический и выписной - не создаем клинический

                    EntryDiagnosis diagnosis = new EntryDiagnosis();
                    diagnosis.setEntry(aEntry);
                    VocIdc10 vocIdc10 =(VocIdc10) diagnosisMap.get("MKB_"+mkb);
                    if (vocIdc10.getCode().indexOf(".")==-1&&(vocIdc10.getIsPermitWithoutDot()==null||!vocIdc10.getIsPermitWithoutDot())) { //Если диагноз без расшифровки и он не разрешен к использованию без уточнения
                        theManager.persist(new E2EntryError(aEntry,"DIAGNOSIS_WITHOUT_UTOCHNENIE"));
                    }
                    diagnosis.setMkb(vocIdc10);
                    diagnosis.setRegistrationType((VocDiagnosisRegistrationType) diagnosisMap.get("REGTYPE_"+regType));
                    diagnosis.setPriority((VocPriorityDiagnosis) diagnosisMap.get("PRIORITY_"+priority));
                    diagnosis.setIllnessPrimary(data[6]);
                  //  log.warn("entryId = "+aEntry.getId()+"found diagnosis "+(diagnosis.getRegistrationType()!=null)+"#"+diagnosis.getRegistrationType().getCode()+"#"+diagnosis.getPriority().getCode());
                    if (!isNotNull(aEntry.getMainMkb())&&diagnosis.getRegistrationType()!=null && diagnosis.getRegistrationType().getCode().equals("4")&&diagnosis.getPriority()!=null&&diagnosis.getPriority().getCode().equals("1")) {
                        aEntry.setMainMkb(mkb);
                        theManager.persist(aEntry);
                    } else if (aEntry.getEntryType().equals(POLYCLINICTYPE)&&diagnosis.getPriority()!=null&&diagnosis.getPriority().getCode().equals("1")) {

                        aEntry.setMainMkb(mkb);
                        theManager.persist(aEntry);
                    }
                    if (isNotNull(data[7])) diagnosis.setDopMkb(data[7]);
                    theManager.persist(diagnosis);
                    if (isClinical) {clinicalIds.add(diagnosis.getId());}
                }
                if (foundClinical&&foundDischarge) { //Если нашли и клинический и выписной диагноз - удаляем клинический.
                    for (Long id:clinicalIds) {
                        theManager.createNativeQuery("delete from EntryDiagnosis where id=:id").setParameter("id",id).executeUpdate();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Map<String, VocMedService> serviceList = new HashMap<String, VocMedService>();
    private List<EntryMedService> getServices(E2Entry aEntry) {return theManager.createQuery("from EntryMedService where entry_id=:id").setParameter("id", aEntry.getId()).getResultList();}

    /** Создание списка услуг по записи + делаем главную услугу */ //делаем разово
    private void createServices(E2Entry aEntry) {
        if (getServices(aEntry).size()>0) {return ;}
        try {
            JSONArray services;
            String operationJson = aEntry.getOperationList();
            if (isNotNull(operationJson)) {
                services = new JSONArray(operationJson);
                //Делаем проверку на роды. Если отделение - родовое, диагнозы входят в список, то создаем услуги
                if (isNotNull(aEntry.getDepartmentId())&& aEntry.getDepartmentId()==203) {
                    if (isNotNull(aEntry.getMainMkb())) {
                        String patologicDs = "O60.1,O60.2,O84.0";
                        String fiziologicDs = "O80.0,O80.1";
                        if (patologicDs.indexOf(aEntry.getMainMkb())>-1) { //Если подходящий диагноз по патологическим родам
                            if (operationJson.indexOf("B01.001.006")==-1){ //И в списке услуг нет нужной услуги - добавим её
                                JSONObject pat = new JSONObject();
                                pat.put("serviceCode","B01.001.006");
                                services.put(pat);
                            }
                        } else if (fiziologicDs.indexOf(aEntry.getMainMkb())>-1) {
                            if (operationJson.indexOf("B01.001.009")==-1){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("serviceCode","B01.001.009");
                                services.put(jsonObject);
                            }
                        }
                    }
                }
    /*    if (aEntry.getNoOmcDepartment()!=null&&aEntry.getNoOmcDepartment()) { //Пока уберем, будем поле добавлять при формировании счета
            services.add("B03.003.005");  //Если был в реанимации - добавляем услугу
        }*/
                if (services != null&&services.length()>0) {
                    if (services.length()==1) { //Если ТОЛЬКО одна услуга/операция в СЛО, её и считаем главной
                        aEntry.setMainService(services.getJSONObject(0).getString("serviceCode")); theManager.persist(aEntry);
                    }
                    String dateFrormat ="yyyy-MM-dd";
                    for (int i=0;i<services.length();i++) {
                        JSONObject service = services.getJSONObject(i);
                        if (!service.has("serviceCode")) {continue;}
                        String code = service.getString("serviceCode");
                        //Добавляем услугу, если только она используется в ОМС
                        VocMedService vms = null;
                        if (!serviceList.containsKey(code)) {
                            vms =  (VocMedService) getEntityByCode(code, VocMedService.class, false);
                            if (!isNotNull(vms.getIsOmc())) {vms=null;}
                            serviceList.put(code,vms);
                        } else {
                            vms =serviceList.get(code);
                        }
                        if (vms!=null){
                            EntryMedService ms =new EntryMedService(aEntry,vms);
                            String serviceDate =service.has("serviceDate")?service.getString("serviceDate"):null;
                            if (isNotNull(serviceDate)) {ms.setServiceDate(DateFormat.parseSqlDate(serviceDate,dateFrormat));}
                            String workfunction = service.has("workfunctionCode")?service.getString("workfunctionCode"):null;
                            if (isNotNull(workfunction)) {
                                VocE2FondV015 doctor = null;
                                String key = "DOCTOR#"+workfunction;
                                if (!resultMap.containsKey(key)) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("select  v015.id from E2FondMedSpecLink link left join VocE2FondV015 v015 on v015.id=link.medSpec_id where link.medosWorkFunction='")
                                            .append(workfunction).append("'");
                                    List<BigInteger> list = theManager.createNativeQuery(sb.toString()).getResultList();
                                    if (!list.isEmpty()) {
                                        doctor=theManager.find(VocE2FondV015.class,list.get(0).longValue());
                                    }
                                } else {
                                    doctor = (VocE2FondV015)resultMap.get(key);
                                }
                                if (doctor!=null) ms.setSpeciality(doctor);
                            }
                            theManager.persist(ms);
                        }
                    }
                }
            }


            if(isNotNull(aEntry.getServices())) { //Данные об услугах добавляем в список операций. нах разнообразие!
                services =new JSONArray(aEntry.getServices());
                if (services!=null&&services.length()>0) {
                    String dateFrormat ="yyyy-MM-dd";
                    for (int i=0;i<services.length();i++) {
                        JSONObject service = services.getJSONObject(i);
                        if (!service.has("serviceCode")) {continue;}
                        String code = service.getString("serviceCode");
                        if (!serviceList.containsKey(code)) {
                            serviceList.put(code, (VocMedService) getEntityByCode(code, VocMedService.class, false));
                        }
                        EntryMedService ms =new EntryMedService(aEntry,serviceList.get(code));
                        String serviceDate =service.has("serviceDate")?service.getString("serviceDate"):null;
                        if (isNotNull(serviceDate)) {ms.setServiceDate(DateFormat.parseSqlDate(serviceDate,dateFrormat));}
                        String workfunction = service.has("workfunctionCode")?service.getString("workfunctionCode"):null;
                        if (isNotNull(workfunction)) {
                            VocE2FondV015 doctor = null;
                            String key = "DOCTOR#"+workfunction;
                            if (!resultMap.containsKey(key)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("select  v015.id from E2FondMedSpecLink link left join VocE2FondV015 v015 on v015.id=link.medSpec_id where link.medosWorkFunction='")
                                        .append(workfunction).append("'");
                                List<BigInteger> list = theManager.createNativeQuery(sb.toString()).getResultList(); //Находим исход случая (V015 PRVS)
                                if (!list.isEmpty()) {
                                    doctor=theManager.find(VocE2FondV015.class,list.get(0).longValue());
                                }
                            } else {
                                doctor = (VocE2FondV015)resultMap.get(key);
                            }
                            if (doctor!=null) ms.setSpeciality(doctor);
                        }
                        if (service.has("diagnosisCode")) {
                            String mkb = service.getString("diagnosisCode");
                            if(isNotNull(mkb)) {ms.setMkb((VocIdc10) getEntityByCode(mkb, VocIdc10.class, false));}
                        }
                        if (service.has("extDispServiceCode")) {
                            String serviceCode =service.getString("extDispServiceCode");
                            if (isNotNull(serviceCode)) {ms.setExtDispService((VocE2ExtDispService)getEntityByCode(serviceCode,VocE2ExtDispService.class,false));}
                        }

                        theManager.persist(ms);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /** Создаение полиса для случая */ //запускаем только один раз
    private void makeMedPolicy(E2Entry aEntry) {
        if (isNotNull(aEntry.getMedPolicyNumber())) {return;} //Если номер полиса проставлен - выходим
        String[] policyData;
        String serviceStream = aEntry.getServiceStream();
        if (isNotNull(aEntry.getPolicyMedcaseString())) { //Если к случаю прикреплен полис - используем его
            policyData = aEntry.getPolicyMedcaseString().split("#",-1);
        } else if (isNotNull(aEntry.getPolicyKinsmanString())) { //Если в случае нет полиса, но есть представитель, берем его полис
            policyData = aEntry.getPolicyKinsmanString().split("#",-1);
        } else if (isNotNull(aEntry.getPolicyPatientString())) {
            policyData = aEntry.getPolicyPatientString().split("#",-1);
        } else {
            if (serviceStream==null||serviceStream.equals("OBLIGATORYINSURANCE")){
                E2EntryError error = new E2EntryError(aEntry,"NO_MED_POLICY");theManager.persist(error);
            }
            return;
        }
        if (policyData.length < 20) {
            log.error("Неверно сформирована строка с данными о мед. полисе. BAD_MEDPOLICY_STRING " + policyData.toString());
            return;
        }
        aEntry.setCommonNumber(policyData[0]);
        aEntry.setInsuranceCompanyTerritory(policyData[1]);
        aEntry.setInsuranceCompanyCode(policyData[2]);
        aEntry.setInsuranceCompanyName(policyData[3]);
        aEntry.setInsuranceCompanyOgrn(policyData[5]);

        if (!isNotNull(policyData[19])) {
            E2EntryError error = new E2EntryError(aEntry,"NO_MEDPOLICY_TYPE");theManager.persist(error);
        }
        aEntry.setMedPolicyType(policyData[19]);
        aEntry.setMedPolicySeries(policyData[15]);
        aEntry.setMedPolicyNumber(policyData[13]);


      //  log.info("Данные полиса сохранены");
        theManager.persist(aEntry);

    }
    /** Находим, является ли КСГ политравмой */
    private static final String[] politravmaMainList = {"S02.7","S12.7","S22.1","S27.7","S29.7","S31.7","S32.7","S36.7","S38.1","S39.6","S39.7","S37.7","S42.7","S49.7","T01.1","T01.8","T01.9","T02.0","T02.1","T02.2","T02.3","T02.4","T02.5","T02.6","T02.7","T02.8","T02.9","T04.0","T04.1","T04.2","T04.3","T04.4","T04.7","T04.8","T04.9","T05.0","T05.1","T05.2","T05.3","T05.4","T05.5","T05.6","T05.8","T05.9","T06.0","T06.1","T06.2","T06.3","T06.4","T06.5","T06.8","T07"};
    private static final String[] politravmaSeconaryList = {"J94.2","J94.8","J94.9","J93","J93.0","J93.1","J93.8","J93.9","J96.0","N17","T79.4","R57.1","R57.8"};
private VocKsg getPolitravmaKsg(List<String> aMainDisagnosisList, List<String> aOtherDisagnosisList) {
    boolean foundMain=false, foundOther=false;
    for (String main: aMainDisagnosisList) {
        for(String d:politravmaMainList) {
            if (main.equals(d)) {
                foundMain=true;
                break;
            }
        }
    }

    if (foundMain) { //Если нашли главный диагноз, ищем сопутствующий
        for (String other: aOtherDisagnosisList) {
            for(String d:politravmaSeconaryList) {
                if (other.equals(d)) {
                    foundOther=true;
                    break;
                }
            }
        }

    }
    if (foundMain&&foundOther) {
        return getActualVocByClassName(VocKsg.class,null,"code='233'");
    }
    return null;

}
    /** Нахождение КСГ с бОльшим коэффициентом трудозатрат для случая */
    HashMap<String, List<Object>> ksgMap = new HashMap<String, List<Object>>();
    public VocKsg getBestKSG(E2Entry aEntry, boolean updateKsgIfExist) {
        if (aEntry.getEntryType()==null||!aEntry.getEntryType().equals(HOSPITALTYPE)) {return null;}
        if (!updateKsgIfExist&&aEntry.getKsg()!=null) {return aEntry.getKsg();} //Не проверяем КСГ у записей с уже найденным КСГ
        if (aEntry.getIsManualKsg() != null && aEntry.getIsManualKsg()) { return aEntry.getKsg();} //Если стоит признак ручного ввода КСГ, не расчитываем КСГ
        try {
            List<EntryDiagnosis> diagnosisList = getDiagnosis(aEntry);

            if (diagnosisList.isEmpty()) {
                E2EntryError error = new E2EntryError(aEntry,"NO_DIAGNOSIS");
                theManager.persist(error);
            }

            String bedType = aEntry.getBedSubType();
            String dopmkb = null;
            Boolean isCancer=false;
            for (EntryDiagnosis ed: diagnosisList) {
                if(isNotNull(ed.getDopMkb())) {
                    dopmkb=ed.getDopMkb();
                }
                if (ed.getMkb().getCode().startsWith("C")) {
                    isCancer=true;
                }
            }

            List<String> mainDiagnosis = findDiagnosisCodes(diagnosisList, "3","1"); //Тип регистрации:1,2-при поступлении, 3  - выписной, 4 = клинический
            if (mainDiagnosis.isEmpty()) { //Нет выписного диагноза - возьмем клинический
                mainDiagnosis= findDiagnosisCodes(diagnosisList, "4","1");
            }
            if (mainDiagnosis.isEmpty()&&isNotNull(aEntry.getMainMkb())) {
                mainDiagnosis.add(aEntry.getMainMkb());
            }

            if (bedType.equals("1")) { //Проверим - является ли сочетание диагноза политравмой
                VocKsg politravma = getPolitravmaKsg(mainDiagnosis,findDiagnosisCodes(diagnosisList,"4","34"));
                if (politravma!=null) {
                    aEntry.setKsg(politravma);
                    theManager.persist(aEntry);
                    return politravma;
                }
            }

            String ksgAge = findKSGAge(aEntry.getHospitalStartDate(), aEntry.getBirthDate());
            List<String> serviceCodes = new ArrayList<String>();

            //Если оказано несколько услуг, ищем по всем услугам
            List<EntryMedService> serviceList =  getServices(aEntry);
            for (EntryMedService ms : serviceList) {
                serviceCodes.add(ms.getMedService().getCode());
            }


            StringBuilder sqlHeader = new StringBuilder();
            StringBuilder sql = new StringBuilder();
            sqlHeader.append("select gkp.id as id  from GrouperKSGPosition gkp")
                    .append(" left join grouperksg gk on gk.id=gkp.ksggrouper_id")
                    .append(" left join vocbedsubtype vbst on vbst.id=gk.bedtype_id")
                    .append(" where gk.isActive='1' and vbst.code='").append(bedType).append("'");
            sql.append(sqlHeader);
            if (!mainDiagnosis.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String d : mainDiagnosis) {
                    if (sb.length() != 0) {
                        sb.append(",");
                    }
                    sb.append("'").append(d).append("'");
                }
                sql.append(" and ((gkp.mainmkb is null or gkp.mainmkb ='') or gkp.mainmkb in (").append(sb).append(")").append(isCancer?" or gkp.mainmkb='C.'":"").append(")");
            } else {
                sql.append(" and (gkp.mainmkb is null or gkp.mainmkb ='')");
            }
            sql.append(" and ((gkp.anothermkb is null or gkp.anothermkb ='') ").append(isNotNull(dopmkb)?" or gkp.anothermkb='"+dopmkb+"')":")"); //Ищем по доп. коду

       StringBuilder serviceSql = new StringBuilder();
            if (!serviceCodes.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String d : serviceCodes) {
                    if (sb.length() != 0) {
                        sb.append(",");
                    }
                    sb.append("'").append(d).append("'");
                }
                serviceSql.append(" and ((gkp.servicecode is null or gkp.servicecode='') or gkp.servicecode in (").append(sb).append("))");
            } else {
                serviceSql.append(" and (gkp.servicecode is null or gkp.servicecode='')");
            }
            sql.append(serviceSql);
         //   log.info("sql for best KSG = "+sql.toString());
            List<Object> results;
            String key = mainDiagnosis.hashCode()+"#SQL#"+sql.toString().hashCode(); //bedType+"#"+aEntry.getMainMkb()+"#"+(dopmkb!=null?dopmkb:"");
         //   log.warn("sql for ksg = "+sql.toString());
            if (!ksgMap.containsKey(key)) {
              //  log.info(key+" not found new sql ="+sql);
                results = theManager.createNativeQuery(sql.toString()).getResultList();
               ksgMap.put(key,results);
                if (results.isEmpty()) {
                    log.warn(key+" Не смог найти КСГ для случая с ИД="+aEntry.getId()+" по запросу: "+sql);
                }
            } else {
                results=ksgMap.get(key);
            }
            double maxKZ = 0.0;
            int maxWeight = 0;
            int weight;
            GrouperKSGPosition pos = null;
            boolean duration = aEntry.getCalendarDays() > 3 ? false : true;
            GrouperKSGPosition therapicKsgPosition = null,surgicalKsgPosition=null ;
            for (Object o : results) {
                GrouperKSGPosition ksg = theManager.find(GrouperKSGPosition.class, Long.valueOf(o.toString()));
                weight = 0; //Вес найденного КСГ
                if (isNotNull(ksg.getDopPriznak())&&isNotNull(aEntry.getDopKritKSG())&&ksg.getDopPriznak().equals(aEntry.getDopKritKSG())) {weight=6;} else if (isNotNull(ksg.getDopPriznak())) {continue;}
                if (isNotNull(ksg.getSex())&&ksg.getSex().equals(aEntry.getSex())) {} else if (isNotNull(ksg.getSex())) {continue;}
                if (mainDiagnosis.contains(ksg.getMainMKB())) {
                    if (!isNotNull(ksg.getServiceCode())) { //Находим терапевтичесое КСГ
                        therapicKsgPosition=(therapicKsgPosition!=null&&therapicKsgPosition.getKSGValue().getKZ()>ksg.getKSGValue().getKZ())?therapicKsgPosition:ksg;
                    }

                } else if (isCancer&&ksg.getMainMKB().equals("C.")) {} else if (isNotNull(ksg.getMainMKB())) {continue;}

                if (serviceCodes.contains(ksg.getServiceCode())) {
                    surgicalKsgPosition=(surgicalKsgPosition!=null&&surgicalKsgPosition.getKSGValue().getKZ()>ksg.getKSGValue().getKZ())?surgicalKsgPosition:ksg;
                    //Если коронарография и у нас есть диагноз, берем дешевое КСГ! 09-02-2018
                    if (isNotNull(ksg.getServiceCode())&&ksg.getServiceCode().equals("A16.12.033")) {
                        if (mainDiagnosis.contains(ksg.getMainMKB())) {weight=5;}
                    }

                } else if (isNotNull(ksg.getServiceCode())) {continue;}
                if (ksg.getAge() != null && ksgAge.indexOf(""+ksg.getAge())>-1) {} else if (ksg.getAge()!=null) {continue;}
                if (ksg.getDuration() != null  && duration) {weight=6;} else if (ksg.getDuration() != null ) {continue;}
                if(ksg.getKSGValue().getCode().equals("5")) {weight=7;} //Если нашли 5 КСГ, то берем его! //TODO 01-03-2018
                if(ksg.getKSGValue().getCode().equals("4")) {weight=6;} //Если нашли 4 КСГ, то берем его! //TODO 08-02-2018
            /* Логика с весом записи
                if (mainDiagnosis.contains(ksg.getMainMKB())) {weight++;} else if (isNotNull(ksg.getMainMKB())) {weight=-5;}
                if (serviceCodes.contains(ksg.getServiceCode())) {weight=weight+5;} else if (isNotNull(ksg.getServiceCode())) {weight=-5;} //Если есть услуга, считаем что эта позиция - главнее!  //weight++;}
                if (ksg.getAge() != null && ksg.getAge().equals(ksgAge)) {weight++;} else if (ksg.getAge()!=null) {weight=-5;}
                if (ksg.getDuration() != null  && duration) {weight++;} else if (ksg.getDuration() != null ) {weight=-5;}
                */
                    double currentKZ = ksg.getKSGValue().getKZ();
                    if (weight>maxWeight|| (weight == maxWeight && currentKZ > maxKZ)) {
                        //  log.warn("if happens"+ksg.getId());
                        //  log.info("maxksg = " + currentKZ);
                        maxWeight=weight;
                        maxKZ = currentKZ;
                        pos = ksg;
                    }
                }
                if (therapicKsgPosition!=null&&surgicalKsgPosition!=null) { //Если мы нашли хирургическое КСГ, но есть и терапевтическое, то проверим на исключения
                    GrouperKSGPosition exc =  checkIsKsgException(surgicalKsgPosition,therapicKsgPosition);
                    if (exc!=null){pos=exc;}
                }

            if (pos != null)  {
                VocKsg ksg = pos.getKSGValue();
                aEntry.setKsgPosition(pos);
                if (isNotNull(pos.getMainMKB()))aEntry.setMainMkb(pos.getMainMKB());
                if (isNotNull(pos.getServiceCode()))aEntry.setMainService(pos.getServiceCode());
                aEntry.setKsg(ksg);
                theManager.persist(aEntry);
                return ksg;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    /** Проверяем, является ли пара КСГ исключением из случая*/
    private final String[] ksgExceptions = {"9#11","9#12","10#11","18#76","18#77","179#173","300#301","207#301","242#245","244#35","271#256"}; //терапевтическая#Хирургическая
    private GrouperKSGPosition checkIsKsgException(GrouperKSGPosition aSurgicalKsgPosition, GrouperKSGPosition aTherapicalKsgPosition) {
        String key = aTherapicalKsgPosition.getKSGValue().getCode()+"#"+aSurgicalKsgPosition.getKSGValue().getCode();
      //  log.warn("ekseption.sql="+key);
        for (String str:ksgExceptions) {
            if (str.equals(key)) { //Если есть хир. КСГ, подаем его
                return aSurgicalKsgPosition;
            }
        }
    return null;
    }

    /**  Нахождение возраста для КСГ */
    private String findKSGAge(Date aStartDate, Date aBirthDate) { //Расчет возраста КСГ

        long dayDiff = (AgeUtil.calculateDays(aBirthDate, aStartDate) + 1);
        String ret = "";
        if (dayDiff >= 0 && dayDiff <= 28) {  // от 0 до 28 = 1
            ret+="1";
        }
        if (dayDiff >= 29 && dayDiff <= 90) {// от 29 до 90 = 2
            ret+="2";
        }
        if (dayDiff >= 91 && dayDiff <= 365) {// от 91 до 365 (от 91 дня до 1 года) = 3
            ret+="3";
        }
        if (dayDiff >= 0 && dayDiff <= 730) {// от 0 до 730 = (0 до 2х лет) 4
            ret+="4";
        }
        if (dayDiff >= 0 && dayDiff <= 6570) {// от 0 до 6570 (от 0 до 18 лет) = 5
            ret+="5";
        }
        if (dayDiff > 6570) {// >6570  (больше 18 лет) = 6
            ret+="6";
        }
        return ret;
    }

    /** Получение всех диагнозов из списка по коду регистрации */
    private List<String> findDiagnosisCodes(List<EntryDiagnosis> aList, String aRegType, String aPriority) {
        //Тип регистрации - направительный(2), выписной(3), клинический(4)
        // Приорите - основной(1), сопутствующий(3)
        if (aList.isEmpty()) {return new ArrayList<String>();}
        List<String> diagnosisList = new ArrayList<String>();
 if (aRegType==null&&aPriority==null) {return null;}
        for (EntryDiagnosis diagnosis : aList) {
            if ( //проверить!
                    (aRegType==null||(aRegType!=null&&aRegType.indexOf(diagnosis.getRegistrationType().getCode())>-1))
                    &&(aPriority==null||aPriority!=null&&aPriority.indexOf(diagnosis.getPriority().getCode())>-1)
                ) {
                diagnosisList.add(diagnosis.getMkb().getCode());
            }
        }
        return diagnosisList;
    }


    /** Получаем базовый тариф по случаю +ВМП */
    private HashMap<String, BigDecimal> tariffMap = new HashMap<String, BigDecimal>();
    private BigDecimal calculateTariff(E2Entry aEntry) {
        String key, sqlAdd;
        String entryType=aEntry.getEntryType();
        if (entryType.equals(HOSPITALTYPE)) {
            if (isNotNull(aEntry.getVMPKind())) { //Если в СЛО есть ВМП, цена = ВМП
                return aEntry.getCost();
            }
            String bedSubType = aEntry.getBedSubType();
            key = HOSPITALTYPE+"#"+bedSubType;
            sqlAdd = "stacType_id=" + bedSubType + "";

        } else if (entryType.equals(POLYCLINICTYPE)||entryType.equals(POLYCLINICKDOTYPE)) {
            String tariffCode=aEntry.getSubType()!=null?aEntry.getSubType().getTariffCode():"_NULLENTRYSUBTYPE_";
                key = POLYCLINICTYPE+"#"+tariffCode+"";
                sqlAdd=" type.code='"+tariffCode+"' ";

        }  else {
            log.error("Не могу расчитать тариф для записи с типом: CANT_CALC_TARIFF_"+entryType);
            key="__";
            sqlAdd=null;
        }

        if (!tariffMap.containsKey(key)) {
            VocE2BaseTariff tariff = getActualVocByClassName(VocE2BaseTariff.class, aEntry.getFinishDate(), sqlAdd);
            if (tariff == null) {
               // throw new IllegalStateException("Не удалось найти тариф по случаю с ИД = " + aEntry.getId()+". Необходимо установить тариф с кодом: "+key);
            } else {
                tariffMap.put(key,tariff.getValue());
            }
        }

        return tariffMap.get(key);

    }

    /**
     * Считаем коэффицинет уровня сложности отделения (КУСмо)
     * Если в не находим коэффиционта, берем по умолчанию 1
     * @param aEntry
     * @return
     */
    private HashMap<String, BigDecimal> cusmoMap = new HashMap<String, BigDecimal>();
    private BigDecimal calculateCusmo(E2Entry aEntry) {
        if (aEntry.getMedHelpProfile()==null) {log.error("Не указан профиль мед. помощи, невозможно найти КУСмо! NO_PROFILE_FOR_CUSMO"); return null;}
        // log.warn("calculateCusmo +"+aEntry.getId());
        String key;
        if (aEntry.getBedSubType() != null && aEntry.getBedSubType().equals("2")) { //Для дневного стационара возвращаем жестко зашитый коэффициент
            key=E2Enumerator.DAYTIMEHOSP;
            if (!cusmoMap.containsKey(key)) {
                String ret = getExpertConfigValue("DAYTIMEHOSP_CUSMO");
                cusmoMap.put(key,new BigDecimal(ret));
            }
        } else {
            key = aEntry.getMedHelpProfile().getId()+"#"+aEntry.getDepartmentId();
            if (!cusmoMap.containsKey(key)) {
                StringBuilder sqlAdd = new StringBuilder();
                sqlAdd.append("select distinct cusmo.id from VocCoefficient cusmo")
                        .append(" where cusmo.helpProfile_id='").append(aEntry.getMedHelpProfile().getId()).append("' and cusmo.dtype='VocCoefficientLpuLevel' and (cusmo.department_id is null");
                if (aEntry.getDepartmentId() != null && aEntry.getDepartmentId() > 0L) {
                    sqlAdd.append(" or cusmo.department_id='").append(aEntry.getDepartmentId()).append("'");
                }
                sqlAdd.append(")")
                        .append(" and '").append(aEntry.getFinishDate()).append("' between cusmo.startDate and coalesce(cusmo.finishDate,current_date)");
                VocCoefficientLpuLevel cusmo = getActualVocBySqlString(VocCoefficientLpuLevel.class, sqlAdd.toString());
                cusmoMap.put(key, cusmo!=null?cusmo.getValue():null);
            }
        }
        return cusmoMap.get(key);
    }


    /**
     * Считаем цену (тариф, итоговый коэффициент) стационарного случая
     *
     * @param aEntry
     * @return
     */
    HashMap<String, BigDecimal> hospitalCostMap = new HashMap<String, BigDecimal>();
    private void calculateHospitalEntryPrice(E2Entry aEntry) {
        try {
            String key;
            if (isNotNull(aEntry.getVMPKind())) { //Если есть ВМП - цена случая = цене метода ВМП
                key = "VMP#"+aEntry.getVMPKind();
                BigDecimal cost;
              //  log.info("calc VMP-1");
                if (!hospitalCostMap.containsKey(key)) {
                    List<BigDecimal> costs = theManager.createNativeQuery("select cost from vockindhighcare where code=:code and cost is not null").setParameter("code", aEntry.getVMPKind()).getResultList();
                    if (costs.size()>0) {
                        cost = costs.get(0);
                        hospitalCostMap.put(key,cost);
                    } else {
                        E2EntryError error = new E2EntryError(aEntry,"NO_VMP_METHOD_COST");
                        theManager.persist(error);
                        return;
                    }
                } else {cost=hospitalCostMap.get(key);}
                aEntry.setCost(cost);
                aEntry.setBaseTarif(cost);
                aEntry.setTotalCoefficient(new BigDecimal(1.0));
                aEntry.setCostFormulaString("tarif=T ("+cost+")");
                theManager.persist(aEntry);
            } else { //Если не ВМП, считаем цену как обычно
                VocKsg aKsg = aEntry.getKsg();
                if (aKsg==null){log.warn(aEntry.getId()+"_NO_KSG");return;} //Нет КСГ - нечего расчитывать всё остальное
                BigDecimal kz, tarif, cusmo, kslp, km, kpr, kuksg ;
                kz = BigDecimal.valueOf(aEntry.getKsg().getKZ());
                kuksg = getActualKsgUprCoefficient(aKsg,aEntry.getFinishDate());
                tarif = calculateTariff(aEntry);
                cusmo = calculateCusmo(aEntry);
                km = calculateKm();
                kslp = calculateResultDifficultyCoefficient(aEntry);
                kpr = calculateNoFullMedCaseCoefficient(aEntry);
                if (tarif == null || kz == null ||kuksg==null || cusmo == null || km == null || kslp == null || kpr == null ) {
                    String err = "Для случая с ИД=" + aEntry.getId() + " не удалось расчитать цену: Тариф=" + tarif + ", КЗ=" + kz + ", КУксг="+kuksg+", КУСмо=" + cusmo + ", КМ=" + km + ", КСЛП=" + kslp + ", Кпр=" + kpr ;
                    aEntry.setCostFormulaString(err);
                    log.error(err);
                    theManager.persist(new E2EntryError(aEntry,"NO_COST"));
                } else {
                    String costFormula = "Тариф=" + tarif + ", КЗ=" + kz + ", КУксг="+kuksg+", КУСмо=" + cusmo + ", КМ=" + km + ", КСЛП=" + kslp + ", Кпр=" + kpr ;
                    aEntry.setCostFormulaString(costFormula);
                    BigDecimal totalCoefficient = kz.multiply(kuksg).multiply(cusmo).multiply(kslp).multiply(km).multiply(kpr).setScale(12,RoundingMode.HALF_UP); //Округляем до 2х знаков
                    BigDecimal cost = tarif.multiply(totalCoefficient).setScale(2,RoundingMode.HALF_UP);
                    aEntry.setBaseTarif(tarif);
                    aEntry.setTotalCoefficient(totalCoefficient);
                    aEntry.setCost(cost);
                }
        }
            theManager.persist(aEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  return aEntry.getCost();
    }
    /** Нахождение актуального управленческого коэффициента по КСГ и дате визита*/
    private HashMap<String,E2KsgCoefficientHistory> ksgCoefficientMap = new HashMap<String, E2KsgCoefficientHistory>();
    private BigDecimal getActualKsgUprCoefficient(VocKsg aKsg, Date aFinishDate) {
        E2KsgCoefficientHistory coefficientHistory;
                String sql = "select id from E2KsgCoefficientHistory where ksg_id=:ksg and to_date('"+aFinishDate+"','yyyy-MM-dd') between startDate and coalesce(finishDate, current_date)";
                String key = "KSG#COEFF#"+sql.hashCode();
                if (ksgCoefficientMap.containsKey(key)) {
                    coefficientHistory=ksgCoefficientMap.get(key);
                } else {
                    List<Long> list = theManager.createQuery(sql).setParameter("ksg",aKsg.getId()).getResultList();
                    if (list.size()==0||list.size()>1) {
                        log.error("Больше 1 коэффициента КСГ(MORE_1_KSG_COEFFICIENT) "+sql);
                        return null;
                    }
                    coefficientHistory = theManager.find(E2KsgCoefficientHistory.class,list.get(0));
                }
                return coefficientHistory.getValue();

    }
    /** Расчитываем коэффициент сложности лечения пациента */
    private HashMap<String,VocE2CoefficientPatientDifficulty> difficultyHashMap = new HashMap<String, VocE2CoefficientPatientDifficulty>();
    private void calculatePatientDifficulty(E2Entry aEntry) {
        if (aEntry.getKsg()==null||aEntry.getEntryType().equals(VMPTYPE)) {return;}
        if(aEntry.getEntryType().equals(HOSPITALTYPE)&&!aEntry.getBedSubType().equals("1")) { return;}

        //Удалим старые значения КСЛП //TODO оптимизировать!
        if (theManager.createNativeQuery("select id from E2CoefficientPatientDifficultyEntryLink where entry_id=:id").setParameter("id",aEntry.getId()).getResultList().size()>0) {
            theManager.createNativeQuery("delete from E2CoefficientPatientDifficultyEntryLink where entry_id=:id").setParameter("id",aEntry.getId()).executeUpdate();
        }

        //Сложность лечения пациента //TODO нужны справочники
        /**
       +  1*	Сложность лечения пациента, связанная с возрастом (госпитализация детей до 1 года). Кроме КСГ, относящихся к профилю «неонатология» (107-113)	1,1
       +  2*	Сложность лечения пациента, связанная с возрастом (госпитализация детей от 1 до 4)	1,1
         3	Необходимость предоставления спального места и питания законному представителю (дети до 4) 	1,05
       +  4*	Сложность лечения пациента, связанная с возрастом (лица старше 75 лет) 	1,08
         5	Необходимость предоставления спального места и питания законному представителю пациента возраста старше 75 лет с индексом Бартела ≤ 60 баллов (для осуществления ухода) при наличии медицинских показаний 	1,02
         6*	Наличие у пациента тяжелой сопутствующей патологии, осложнений заболеваний, сопутствующих заболеваний, влияющих на сложность лечения пациента. Таблица 'Диагнозы КСЛП' 	1,1
         7	Необходимость развертывания индивидуального поста по медицинским показаниям 	1,1
         8	Необходимость предоставления спального места и питания законному представителю ребенка после достижения им возраста 4 лет при наличии медицинских показаний 	1,05
         9	Проведение в рамках одной госпитализации в полном объеме нескольких видов противоопухолевого лечения, относящихся к разным КСГ (перечень возможных сочетаний КСГ представлен в Инструкции) 	1,3
     +    9*	Сверхдлительные сроки госпитализации, обусловленные медицинскими показаниями	Согласно формуле
         10*	Проведение сочетанных хирургических вмешательств. Таблица 'Сочетанные операции' 	1,2
         11*	Проведение однотипных операций на парных органах. Таблица 'Парные операции' 	1,2
         * - используется в АМОКБ
         */
        List<String> ret = new ArrayList<String>(); //Тут все сложности
        long ageDays = (AgeUtil.calculateDays(aEntry.getBirthDate(), aEntry.getStartDate()) + 1);
        if (ageDays<366&& (aEntry.getKsg()==null||!aEntry.getKsg().getProfile().equals("17"))) { //Если меньше года и КСГ не указано либо оно не "неонатологическое"
            ret.add("1");
        } else if (ageDays>365&& ageDays<1462) { //Если возраст между годом и 4 годами
            ret.add("2");
        } else if (ageDays>27394) { //Если возраст более 75 лет
            ret.add("4");
        }
        Date actualDate = aEntry.getFinishDate();
        //6
        List<EntryDiagnosis> list = theManager.createQuery("from EntryDiagnosis where entry_id=:id ").setParameter("id",aEntry.getId()).getResultList();
        List<String> anotherMkb = findDiagnosisCodes(list,null,"3");
        for(String ds: anotherMkb) {
            if (ds.startsWith("E10")) {
                ret.add("6");
                break;
            }
        }

        //12 Парные операции
        List<String> pairOperation = theManager.createNativeQuery("select ''||id from VocPairOperations where medservice_id in (select ems.medservice_id from entrymedservice ems " +
                " where entry_id=:id and coalesce(finishDate,current_date)>=:actualDate  group by ems.medservice_id having count (ems.medservice_id)>1) ")
                .setParameter("id", aEntry.getId())
                .setParameter("actualDate",actualDate)
                .getResultList();
        if (!pairOperation.isEmpty()) {
            ret.add("11");
        }

        List<EntryMedService> serviceList = getServices(aEntry);
        if (!serviceList.isEmpty()) {
            StringBuilder sbb = new StringBuilder();
            for (EntryMedService ems: serviceList) {
                if (sbb.length()>0) {sbb.append(",");}
                sbb.append(ems.getMedService().getId());
            }
            if (!theManager.createNativeQuery("select id from VocCombinedOperations where medservice1_id in ("+sbb+") and medservice2_id in ("+sbb+") and coalesce(finishDate,current_date)>=:actualDate ")
                    .setParameter("actualDate",actualDate)
                    .getResultList().isEmpty()
               ) {
                ret.add("10");
            }
        }

        E2CoefficientPatientDifficultyEntryLink link;
        //calc 10
        long sluchDuration = aEntry.getBedDays()!=null?aEntry.getBedDays():1;
        long maxDuration = aEntry.getKsg().getLongKsg()!=null&&aEntry.getKsg().getLongKsg()?45:30; //TODO вынести в настройки
        if (sluchDuration>maxDuration) { //Если случай лечения больше 30 (45) дней, ищем "10" коэффициент
            BigDecimal value =new BigDecimal(1).add((new BigDecimal(sluchDuration-maxDuration).divide(new BigDecimal(maxDuration),12,RoundingMode.HALF_UP))
                    .multiply(new BigDecimal(aEntry.getReanimationEntry()!=null?0.4:0.25))); //TODO вынести в настройки
            link = new E2CoefficientPatientDifficultyEntryLink();
            link.setEntry(aEntry);
            link.setDifficulty((VocE2CoefficientPatientDifficulty)getActualVocByClassName(VocE2CoefficientPatientDifficulty.class,null," code='9'"));
            link.setValue(value.setScale(2,RoundingMode.HALF_UP));
            theManager.persist(link);
        }

        //Пришло время сохранять все сложности пациента
        if (ret.size()>0) {
            //theManager.createNativeQuery("delete from E2CoefficientPatientDifficultyEntryLink where entry_id=:id").setParameter("id",aEntry.getId()).executeUpdate();

            for (String code:ret) {
                if (!difficultyHashMap.containsKey(code)) {
                    difficultyHashMap.put(code,(VocE2CoefficientPatientDifficulty)getActualVocByClassName(VocE2CoefficientPatientDifficulty.class,null," code='"+code+"'"));
                }
                VocE2CoefficientPatientDifficulty difficulty = difficultyHashMap.get(code);
                link = new E2CoefficientPatientDifficultyEntryLink();
                link.setEntry(aEntry);
                link.setDifficulty(difficulty);
                link.setValue(difficulty.getValue());
                theManager.persist(link);
            }
        }
    }
    /** Считаем коэффициент сложности лечения пациента */
    private BigDecimal calculateResultDifficultyCoefficient(E2Entry aEntry) {
        BigDecimal one = new BigDecimal(1);
        if(aEntry.getEntryType().equals(HOSPITALTYPE)&&!aEntry.getBedSubType().equals("1")) { return one;}
      //  log.info("start calc difficult");
        List<E2CoefficientPatientDifficultyEntryLink> list = theManager.createQuery(" from E2CoefficientPatientDifficultyEntryLink where entry=:entry").setParameter("entry",aEntry).getResultList();
        BigDecimal ret = new BigDecimal(1);

        if (!list.isEmpty()) { //Нет КСЛП - возвращаем 1.
            if (list.size()==1) { //Если один - возвращаем его.
                ret = list.get(0).getValue();
            } else {
                boolean first = true;
                for (E2CoefficientPatientDifficultyEntryLink link: list) { //Если несколько, возвращаем К1+(К2-1)+(Кн-1)
                    if (first){
                       ret = link.getValue();
                        first=false;
                    } else {
                        ret=ret.add(link.getValue()).add(one.negate());
                    }
                }
            }
        }
        if (ret==null) log.info("finish calc difficult.id="+aEntry.getId()+", value is NULL!! = ");
        return ret;
    }

    /** Расчет цены поликлинического случая */
    private HashMap<String,VocE2PolyclinicCoefficient> polyclinicCasePrice = new HashMap<String, VocE2PolyclinicCoefficient>();
    private void calculatePolyclinicEntryPrice(E2Entry aEntry) { //TODO реализовать!!!
        BigDecimal one = new BigDecimal(1);
        VocE2EntrySubType subType =aEntry.getSubType();
        String tariffCode = subType.getTariffCode();
        if (tariffCode ==null) {log.warn("Cant calc polyclinic price");return;}
       Long profileId = aEntry.getMedHelpProfile()!=null?aEntry.getMedHelpProfile().getId():null;
        if (profileId==null) {log.error("Нет профиля для определения цены");return;}
        String key;
        String sql = "";
        VocE2PolyclinicCoefficient coefficient = null;
        //находим Кз
        if (isNotNull(aEntry.getIsDiagnosticSpo())) { //Находим Кз обращения
            key="KZ#KDO#";
        } else {
            if (isNotNull(aEntry.getIsEmergency())) { //ytn Rp
                key="KZ#EMERGENCY##";
            } else {
                key = "KZ#" + profileId + "#" + tariffCode;
            }
        }
        sql ="profile_id="+profileId+" and entryType.tariffCode='"+tariffCode+"'";
        key+=sql;
        if (!polyclinicCasePrice.containsKey(key)) {
            //log.info("FIND_KZ. key = "+key);
            coefficient= getActualVocByClassName(VocE2PolyclinicCoefficient.class,aEntry.getFinishDate(),sql);
            if (coefficient==null){log.warn("НЕ смоег найти коэффициента: "+sql);}
            polyclinicCasePrice.put(key,coefficient);
        }

        coefficient = polyclinicCasePrice.get(key);
        BigDecimal kz = coefficient!=null?coefficient.getValue():one;

        //Находим Кп/Кпд
         sql = "";
        if (isNotNull(aEntry.getIsDiagnosticSpo())) { //находим КДО
            sql+=" and isDiagnosticSpo='1'";
        } else if (isNotNull(aEntry.getIsEmergency())) { //Неотложна
            key="KP#EMERGENCY##";
            sql+=" and 1=2";
        } else { //поликлиника (мобильная ., консультативная)
            if (isNotNull(aEntry.getIsMobilePolyclinic())) {
                sql += " and isMobilePolyclinic='1'";
            }
            if (isNotNull(subType.getIsConsultation())) {
                sql += " and isConsultation='1'";
            }
        }
            sql="profile_id="+profileId+sql+" and entryType is null "+sql;
            key ="KP#"+sql; //Находим коэффициент для конс. поликлиники
            if (!polyclinicCasePrice.containsKey(key)) {
                coefficient=getActualVocByClassName(VocE2PolyclinicCoefficient.class,aEntry.getFinishDate(),sql);
                polyclinicCasePrice.put(key,coefficient);
            }
        coefficient = polyclinicCasePrice.get(key);
        BigDecimal kp = coefficient!=null?coefficient.getValue():one;

        BigDecimal tariff = calculateTariff(aEntry);
        BigDecimal km = calculateKm();
        String costFormula = "Тариф=" + tariff + ", КЗ=" +kz + ", Кп(Кпд)="+kp+", КМ=" + km;;
        if (tariff==null||kz==null||km==null) {
            log.warn("Не удалось расчитать цену случая");
        } else {
            BigDecimal coeff = kz.multiply(kp).multiply(km);
            BigDecimal cost = tariff.multiply(coeff); //
            aEntry.setTotalCoefficient(coeff);
            aEntry.setCost(cost);
            aEntry.setBaseTarif(tariff);
        }
        aEntry.setCostFormulaString(costFormula);
        theManager.persist(aEntry);
    }

    private BigDecimal calculateExtDispEntryPrice(E2Entry aEntry) { //TODO реализовать!!!
        return new BigDecimal(0.3);
    }

    /**
     * Создаем записи в заполнении из sql запроса
     *
     * @param aResultSet
     * @param aParamMap
     * @throws ParseException
     */
    private HashMap<String, Method> methodMap = new HashMap<String, Method>();
    private void createEntriesByEntryList(ResultSet aResultSet, Map<String, Object> aParamMap, String aEntryListCode, E2ListEntry aListEntry) throws ParseException { //Сохраняем сущности
        try {
            ResultSetMetaData metaData = aResultSet.getMetaData();

            int rowsLength = metaData.getColumnCount();
            String[] fields = new String[rowsLength];
            int[] types = new int[rowsLength];
            String defaultOkato = getExpertConfigValue("DEFAULT_OKATO","12401000000");
            for (int i = 0; i < rowsLength; i++) { //Для каждой строки находим геттер
                fields[i] = metaData.getColumnName(i + 1);
                types[i] = metaData.getColumnType(i + 1);
            }
            E2Entry entity;
            List<E2Entry> entries = new ArrayList<E2Entry>();
            int j=0;
            while (aResultSet.next()) { // Для каждой строки (кортежа)
                j++;
                if (j%100==0) {log.info("Creating "+j+" records");}
                entity = new E2Entry();
                Class aClass = entity.getClass();
                for (int i = 0; i < rowsLength; i++) { //Для каждого столбца находим геттер
                    String key = "GETTER#"+fields[i];
                    Method getterMethod = null;
                    if (!methodMap.containsKey(key)) {
                        try {
                            getterMethod = PropertyUtil.getGetterMethodIgnoreCase(aClass, fields[i]);
                            methodMap.put(key,getterMethod);
                        } catch (Exception e) { log.warn("Не найдено поле с именем "+fields[i]);    methodMap.put(key,null);continue;
                        }
                    } else {getterMethod=methodMap.get(key);}

                    if (getterMethod == null) {continue;} //нет геттера - нет сеттера!
                    key = "SETTER#"+fields[i];
                    Method setterMethod;
                    if (methodMap.containsKey(key)) {
                        setterMethod=methodMap.get(key);
                    } else {
                        setterMethod= PropertyUtil.getSetterMethod(aClass, getterMethod);
                        methodMap.put(key,setterMethod);
                    }
                    Object value = convertResultSetValue(types[i], getterMethod.getReturnType(), aResultSet.getObject(fields[i]));
                    setterMethod.invoke(entity, value);
                }

                for (Map.Entry<String, Object> entry : aParamMap.entrySet()) { //Дополняем объект данными НЕ из запроса
                    String key = "GETTER#"+entry.getKey();
                    Method getterMethod;
                    if (methodMap.containsKey(key)) {
                        getterMethod=methodMap.get(key);
                    } else {
                        getterMethod = PropertyUtil.getGetterMethodIgnoreCase(aClass, entry.getKey());
                        methodMap.put(key,getterMethod);
                    }

                    if (getterMethod != null) {
                        key = "SETTER#"+entry.getKey();
                        Method setterMethod;
                        if (methodMap.containsKey(key)) {
                            setterMethod=methodMap.get(key);
                        } else {
                            setterMethod =  PropertyUtil.getSetterMethod(aClass, getterMethod);
                            methodMap.put(key,setterMethod);
                        }
                        setterMethod.invoke(entity, entry.getValue());
                    } else {
                        log.error("Невозможно получить объект из карты с ключем = " + entry.getKey());
                    }
                }
                if (!isNotNull(entity.getOkatoReg())) { entity.setOkatoReg(defaultOkato);} //Устанавливаем ОКАТО регистрации по умолчанию
                if (!isNotNull(entity.getOkatoReal())) { entity.setOkatoReal(defaultOkato);}//Устанавливаем ОКАТО проживания по умолчанию
                theManager.persist(entity);
                setEntryType(entity,aEntryListCode);
                makeMedPolicy(entity); //Запускаем один лишь раз
                createDiagnosis(entity); //Запускаем один лишь раз
                createServices(entity); //Запускаем один лишь раз
                //makeCheckEntry(entity);
                theManager.persist(entity);
                entries.add(entity);
            }
            //checkListEntryFirst(aListEntry, entries); //Проверку будем запускать отдельно.
            log.info("Success!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /** При формировании заполнения выполняем расчет КСГ, объединение случаев, повторное нахождение КСГ */
    private void checkListEntryFirst(E2ListEntry aListEntry, List<E2Entry> aEntryList, long aMonitorId) {
        Long listEntryId = aListEntry.getId();
        try {
            java.util.Date startStartDate = new java.util.Date();
            log.info("start checkListEntryFirst. id="+listEntryId);
            if (isCheckIsRunning!=null&&isCheckIsRunning) {
                log.warn("Проверка уже запущена, ничего не проверяем ALREADY_STARTED");
                //  return;
            }
            isCheckIsRunning=true;
//            StringBuilder sql = new StringBuilder();
         //   sql.append("from E2Entry where listEntry_id=:id and (isDeleted is null or isDeleted='0')");
        //    StringBuilder sqlAdd = new StringBuilder();

          //  theManager.createNativeQuery("update e2entryerror set isdeleted='1' where listentry_id=:id").setParameter("id",aListEntry.getId()).executeUpdate(); //Все ошибки удалим
            if (aEntryList==null) {
                aEntryList = theManager.createQuery("from E2Entry where listEntry=:list and (isDeleted is null or isDeleted='0')").setParameter("list",aListEntry).getResultList();
            }
            if (aEntryList.isEmpty()) {log.warn("Случаев для проверки не найдено NO_CASES ");return;}
            /* сначала найдем КСГ (для правильного объединения
            потом  производим объединение случаев, потом (только для ОМС) расчитываем поля фонда, считаем цену
            */

            String listEntryCode =aListEntry.getEntryType().getCode();
            IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Расчет цены случаев в звполнении") ;
            monitor = theMonitorService.startMonitor(aMonitorId,"Пересчет случаев в заполнении",aEntryList.size());
            monitor.advice(1);
            if (listEntryCode.equals(HOSPITALTYPE)||listEntryCode.equals(HOSPITALPEREVODTYPE)) {
                int i=0;
                log.info("Приступаем к нахождению лучшего КСГ. START_FIND_BESK_KSG. list_size="+aEntryList.size());
                monitor.setText("Приступаем к нахождению лучшего КСГ. START_FIND_BESK_KSG. list_size="+aEntryList.size());
                StringBuilder entriesId = new StringBuilder();
                for (E2Entry entry : aEntryList) { //Найдем лучшее КСГ
                    entriesId.append(",").append(entry.getId());
                    i++;
                    if (i%100==0) {
                        log.info("process ... cheking entry first.... "+i);
                        monitor.setText("Проверяем стационар. Проверено случаев: "+i);
                    }
                    makeCheckEntry(entry,false, false);
                }
                log.info("Закончили первичную проверку случаев.FINISH_FIRST_CHECK");
                monitor.setText("Закончили первичную проверку случаев.FINISH_FIRST_CHECK");

                //теперь объединим все случаи объединим все случаи (только для стационара)
                List<BigInteger> hospitalIds = theManager.createNativeQuery("select externalparentid from e2entry " +
                        " where id in ("+entriesId.substring(1).toString()+") and listentry_id=" + listEntryId  + " and  (isUnion is null or isUnion='0') group by externalparentid having count(externalparentid)>1").getResultList();//Находис все СЛС, в которых больше 1 СЛО
//log.info("union host. List size MUST_BE>0 : "+hospitalIds.size());
                i=0;
                log.info("Приступаем к объединению случаев. START_UNION");
                monitor.setText("Приступаем к объединению случаев. START_UNION");
                for (BigInteger hospId : hospitalIds) {
                    i++;
                    if (i%100==0) {log.info("process ... union medcases.... "+i);}
                    unionHospitalMedCase(listEntryId , hospId.longValue());
                }
                log.info("Объединение случаев завершено.FINISH_UNION");
                log.info("Проверяем КСГ после объединения случаев.2ND_CHECK_KSG");
                monitor.setText("Проверяем КСГ после объединения случаев.2ND_CHECK_KSG");
                i=0;
                for(E2Entry entry : aEntryList) {
                    i++;
                    if (i%100==0) {
                        log.info("process ... find best ksg_2.... "+i);
                        monitor.setText("Находим лучшее КСГ после объединения случае. Проверено: "+i);
                    }
                    //Теперь снова находим КСГ, расчитываем цену и коэффициенты
                    if (!entry.getServiceStream().equals("COMPLEXCASE")) {
                        makeCheckEntry(entry,true, true);
                    }
                }
            } else if (listEntryCode.equals(POLYCLINICTYPE)||listEntryCode.equals(POLYCLINICKDOTYPE)) {
                if (listEntryCode.equals(POLYCLINICTYPE)) {
                    monitor.setText("Удаляем дубли в поликлинике");
                    deletePolyclinicDoubles(listEntryId ); //Удалим дубли при первой проверке
                }
                int i=0;
                log.info("POL_START_Поликлиника. Приступаем к нахождению цены и проставлению полей фонда.");
                monitor.setText("POL_START_Поликлиника. Приступаем к нахождению цены и проставлению полей фонда.");

                for(E2Entry entry : aEntryList) {
                    i++;
                    if (i%100==0) {
                        log.info("process ... checking entry.... "+i);
                        monitor.setText("Проверяем записи по поликлинике: "+i);
                    }

                        makeCheckEntry(entry,false, true);

                }
                log.info("Поликлиника. Закончили нахождение цены и проставление полей фонда."+((System.currentTimeMillis()-startStartDate.getTime()))/60000);
                log.info("Поликлиника. приступаем к объединению случаев.");
                monitor.setText("Поликлиника. приступаем к объединению случаев.");
                if (listEntryCode.equals(POLYCLINICTYPE)) {
                    unionPolyclinicMedCase(listEntryId ,null);
                } else if (listEntryCode.equals(POLYCLINICKDOTYPE)) {
                    monitor.setText("Объединяем КДО");
                    log.info("size KDO = "+theManager.createNativeQuery("select id from e2entry where listentry_id="+aListEntry.getId()+" and (isDeleted is null or isDeleted='0')").getResultList().size());
                    unionPolyclinicKdoMedCase(listEntryId,aEntryList);
                }

            }
            log.info("Время выполнения проверки (минут) TOTAL_TIME = "+((System.currentTimeMillis()-startStartDate.getTime()))/60000);
            isCheckIsRunning=false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long currentTime = System.currentTimeMillis();
        aListEntry.setCheckDate(new java.sql.Date(currentTime));
        aListEntry.setCheckTime(new java.sql.Time(currentTime));
        theManager.persist(aListEntry);
    }

    /** Проставляем тип записи (стационар, ВМП, поликлиника, подушевое финансирование, __ИНОГОРОДНИЕ__ */
    private void setEntryType(E2Entry aEntry, String aCode) {
        if (aCode.equals(HOSPITALTYPE)&&isNotNull(aEntry.getVMPKind())) {
            aCode=VMPTYPE;
        } else if (aCode.equals(HOSPITALPEREVODTYPE)) {
            aCode=HOSPITALTYPE;
        } else if (aCode.equals(EXTDISPTYPE)) {
            aCode=EXTDISPTYPE;
        } else if (aCode.equals(POLYCLINICKDOTYPE)) {
            aCode=POLYCLINICKDOTYPE;
        }
        if (isNotNull(aEntry.getInsuranceCompanyCode())) {aCode+="_INOG";} //Если код страх. компании не пустой - иногородний.
        aEntry.setEntryType(aCode.toUpperCase());
        theManager.persist(aEntry);
    }
    /** Получаем значение из настроек экспертизы по коду */
    private String getExpertConfigValue(String aParameterName) {return getExpertConfigValue(aParameterName,null);}
    private String getExpertConfigValue(String aParameterName, String aDefaultValue) {
        List<Object> ret = theManager.createNativeQuery("select value from Expert2Config where code=:code AND (isDeleted is null or isDeleted='0')").setParameter("code", aParameterName).getResultList();
        if (ret.isEmpty()) {
            if (aDefaultValue==null) {
                throw new IllegalStateException("Не удалось найти настройку с кодом: " + aParameterName);
            } else {
                log.warn("Не удалось найти настройку с ключем "+aParameterName+", возвращаю значение по умолчанию");
                return aDefaultValue;
            }
        }
        return ret.get(0).toString();
    }

    /**
     * Получаем тип объекта по его sql типу
     *
     * @param aType       - SQL тип столбца
     * @param aOuterClass - Выходной класс
     * @param aValue      - Значение
     * @return
     * @throws ParseException
     */
    private Object convertResultSetValue(int aType, Class aOuterClass, Object aValue) throws ParseException {
        Class aClass;
        switch (aType) {
            case Types.VARCHAR: //12
                aClass = String.class;
                break;
            case Types.DATE: //91
                aClass = java.sql.Date.class;
                break;
            case Types.INTEGER: //4
                aClass = Integer.class;
                break;
            case Types.TIME: //92
                aClass = java.sql.Time.class;
                break;
            case Types.BIGINT: // -5
                aClass = Long.class;
                break;
            case Types.BIT:
                aClass=Boolean.class;
                break;
            case Types.BOOLEAN:
                aClass = Boolean.class;
                break;
            case Types.NUMERIC:
                aClass=BigDecimal.class;
                break;
            default:
                throw new IllegalStateException("can't find preobrazovanie for type "+aType);
                //System.out.println("can't find java type");
                //aClass = null;
                //break;
        }
        if (aClass != null) {
            return PropertyUtil.convertValue(aClass, aOuterClass, aValue);
        }
        return null;
    }

    private String getFileAsSTring(String aFilename) {
        String url = getJbossConfigValue("jboss.userdocument.dir", "/opt/jboss-4.0.4.GAi-postgres/server/default/data");
        File resourceFile = new File(url + "/" + aFilename);
        log.warn("SQL file = " + resourceFile);
        if (resourceFile.exists() && resourceFile.isFile()) {

            try {
                BufferedReader reader = new BufferedReader(new FileReader(resourceFile));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(" ").append(line);
                    line = reader.readLine();
                }
                reader.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Не удалось обнаружить файл!");
            }
        } else {
            log.error("NO_FILE_ Не удалось найти ресурс с именем: " + aFilename + "  в папке " + url);
        }
        return null;
    }

    private Statement createStatement() throws NamingException, SQLException {

        DataSource ds = ApplicationDataSourceHelper.getInstance().findDataSource();
        return ds.getConnection().createStatement();
    }

    private String toSQlDateString(Date aDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return " to_date('" + format.format(aDate) + "','dd.MM.yyyy') ";
    }

    private <T> T getActualVocBySqlString(Class aClass, String aSql) {
        List<Object> list = theManager.createNativeQuery(aSql).getResultList();
        if (list.isEmpty()) {
            log.error("Не удалось найти действующее значение=0 справочника " + aClass.getCanonicalName() + " с условием " + aSql);
            return null;
            //throw new IllegalStateException("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием " + aSql);
        }
        if (list.size() > 1) {
            log.error(" с условием " + aSql + " найдено несколько>1 действующих значений справочника " + aClass.getCanonicalName());
            return null;
            //throw new IllegalStateException(" с условием " + aSql + " найдено несколько действующих значений справочника " + aClass.getCanonicalName());
        }
        return (T) theManager.find(aClass, Long.valueOf(list.get(0).toString()));

    }

    private <T> T getActualVocByClassName(Class aClass, Date aActualDate, String aSqlAdd) {
        String sql = " from "+aClass.getName()+" where ";
        List<T> list;
        if (aActualDate!=null) {
            sql+=" :actualDate between startDate and coalesce(finishDate,current_date)";
            if (isNotNull(aSqlAdd)) {
                sql+=" and "+aSqlAdd;
            }
            list = theManager.createQuery(sql).setParameter("actualDate",aActualDate).getResultList();
            if (list.isEmpty()){

                list = theManager.createQuery("from " + aClass.getName() + " where finishDate is null" + (aSqlAdd != null ?" and "+ aSqlAdd : "")).getResultList();
            }
        } else if (isNotNull(aSqlAdd)){
            sql+=aSqlAdd;
            log.warn(theManager+"***"+sql);
         list = theManager.createQuery(sql).getResultList();
        } else {
            throw new IllegalStateException("Необходимо указать дату актуальности либо другое условие");
        }
        if (list.isEmpty()) {
                log.error("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием "+sql);
                return null;
                //throw new IllegalStateException("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием "+sql);
        } else if (list.size() > 1) {
            log.error("Найдено несколько действующих значений справочника " + aClass.getCanonicalName()+" с условием "+sql);
            return null;
            //throw new IllegalStateException("Найдено несколько действующих значений справочника " + aClass.getCanonicalName()+" с условием "+sql);
        }
        return list.get(0);

    }

    private String getJbossConfigValue(String aConfigName, String aDefaultValue) {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        String res = config.get(aConfigName, aDefaultValue);
        return res;

    }

    /** Считаем районнный коэффициент  (Км) */

    private BigDecimal calculateKm() {
        //String ret = getExpertConfigValue("RAYON_COEFFICIENT"); //Коэффициент зашит в настройках ЛПУ
        //return BigDecimal.valueOf(Double.valueOf(ret));
        return new BigDecimal(1); //TODO пока оставим так для производительности
    }

     /** Расчет причины неполной оплаты случая (коэффициент прерванного случая) */

    /**
     * Указывается причина, по которой услуга не оказана или оказана не в полном объёме.
     1 – документированный отказ больного,
     2 – медицинские противопоказания,
     3 – прочие причины (умер, переведён в другое отделение и пр.)
     4 – ранее проведённые услуги в пределах установленных сроков.
     */

    private BigDecimal calculateNoFullMedCaseCoefficient (E2Entry aEntry) { //Считаем коэффициент Кпр.+    //  log.info("start calculateNoFullMedCaseCoefficient");
        String  npl = aEntry.getNotFullPaymentReason();
        BigDecimal ret = new BigDecimal(1); //По умолчанию - полный случай
        //     String[] resultData = aEntry.getResult().split("#",-1);
        boolean isPrerSluch = false;
        // boolean shortCase = false;
        if (aEntry.getFondResult() == null) {
            return ret;
        }
        String result = aEntry.getFondResult().getCode();
        if (aEntry.getDepartmentId() == 182 && !result.equals("107") && !result.equals("108")) { //Если патология, если НЕ выписан по желанию ЛПУ или желанию пациента, Кпр=1
            return ret;
        }
        String deadResult = "105,106,205,206";
        String otherLpuResult = "102,103,202,203";
        String lpuLikeResult = "108,208";
        String patientLikeResult = "107,207";
        String ksgFullPaymentChilds="107,108,109,110,111,112,113"; //при переводе детей в другое ЛПУ не считаем прерванным случаем *30-05-2018
        String notFullReason = aEntry.getNotFullPaymentReason();

        if (otherLpuResult.indexOf(result) > -1) { //Переведен в другой стационар
            if (aEntry.getKsg()!=null&&ksgFullPaymentChilds.indexOf(aEntry.getKsg().getCode())>-1) {
                isPrerSluch = false;
            } else {
                isPrerSluch = true;
            }
        } else if (lpuLikeResult.indexOf(result) > -1) { //выписан по желанию ЛПУ
            isPrerSluch = true;
            //    npl="3";
        } else if (patientLikeResult.indexOf(result) > -1) { //выписан по желанию пациента
            isPrerSluch = true;
           //    npl="1";
        } else if (deadResult.indexOf(result) > -1) { //Смерть пациента
            isPrerSluch = true;
        }  //else
        if (aEntry.getCalendarDays() < 4) {  //Если длительность случая менее 4 дней. //28-02-2018 4 целых дня.
                isPrerSluch = true;
        }
        if (isPrerSluch&&aEntry.getKsg() != null && isNotNull(aEntry.getKsg().getIsFullPayment())) { //Если У КСГ признак полной оплаты - то это не прер. случай
            isPrerSluch = false;
        }
        boolean surgicalKsg = false;
        if (isPrerSluch) {
            // если прерванный случай - ставим причину неполной оплаты
            ret = calculateBasePrerSluchCoefficient(aEntry); //Прерванным случаям коэффициент = 0.3
            GrouperKSGPosition pos =aEntry.getKsgPosition();
            if ((pos!=null&&isNotNull(pos.getServiceCode())||isNotNull(aEntry.getDopKritKSG()))) { //Если у КСГ признак "операционного", либо есть доп. критерий КСГ
                surgicalKsg=true;
            //    if ((aEntry.getOperationList()!=null&&aEntry.getOperationList().indexOf(pos.getServiceCode())>-1)
             //           ||(isNotNull(aEntry.getMainService())&&aEntry.getMainService().equals(pos.getServiceCode()))){ //Если услуга есть в группировщике //TODO упростить!
              //      if ((pos.getKSGValue().getIsOperation()!=null&&pos.getKSGValue().getIsOperation())||isNotNull(aEntry.getDopKritKSG())) { //Если операция или есть доп. критерий КСГ
                        if (aEntry.getCalendarDays()<4) { //операция участвует в нахождении КСГ, случай короткий, коэф. 0.9
                            ret = new BigDecimal(0.9);
                        } else { //операция участвует в нахождении КСГ, случай больше 3х дней, коэф. 1
                            ret = new BigDecimal(1); //0.9
                        //   if (!npl.equals("1")) {npl="0";} //Если пациент выписался по собств. желанию, то всегда NPL = 1
                        }
                //    }
                }
                if (aEntry.getKsg().getCode().equals("233")) { //Если политравма и есть любая операция, то Кпр=1 *07.05.2018
                    if (theManager.createNativeQuery("select id from entrymedservice where entry_id=:id").setParameter("id",aEntry.getId()).getResultList().size()>0) {
                        ret = new BigDecimal(1);
                    }
                }
     //       }
        }
        if (isNotNull(npl)&&!npl.equals("0")) {
            ret=surgicalKsg?new BigDecimal(0.9):new BigDecimal(0.3);
        }

       if (!isNotNull(npl)) aEntry.setNotFullPaymentReason("0");
        aEntry.setIsBreakedCase(isPrerSluch);
        theManager.persist(aEntry);
     //   log.info("finish calculateNoFullMedCaseCoefficient");
        return ret.setScale(2, RoundingMode.HALF_UP);
    }
    private BigDecimal calculateBasePrerSluchCoefficient(E2Entry aEntry) { //TODO = переделать нормально
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        BigDecimal ret = new BigDecimal(0.3);
        try {
            if (aEntry.getFinishDate().getTime()>=format.parse("01.03.2018").getTime()) {
                ret = new BigDecimal(0.5);
            }
        } catch (ParseException e) { e.printStackTrace();}
        return ret;
    }
    private HashMap<String, Object> resultMap = new HashMap<String, Object>(); //результат госпитализации
    private void calculateFondField(E2Entry aEntry){calculateFondField(aEntry,false);}
    private void calculateFondField(E2Entry aEntry, boolean  forceUpdate) { //Расчитываем поля для подачи в ОМС RSLT, ISHOD, PRVS, IDSP
        String key;
        StringBuilder sb;
        String entryType = aEntry.getEntryType();
        String bedSubType = aEntry.getBedSubType();
        List<BigInteger> list;
        Date actualDate = aEntry.getFinishDate();
        boolean stacCase = entryType.equals(HOSPITALTYPE)||entryType.equals(VMPTYPE)?true:false;
        boolean vmpCase = entryType.equals(VMPTYPE)?true:false;
        boolean polyclinicCase = entryType.equals(POLYCLINICTYPE)||entryType.equals(POLYCLINICKDOTYPE)?true:false;
        boolean polyclinicKdoCase = entryType.equals(POLYCLINICKDOTYPE)?true:false;
        boolean extDispCase = entryType.equals(EXTDISPTYPE)?true:false;
        if (!isNotNull(aEntry.getResult())) {theManager.persist(new E2EntryError(aEntry,"NO_RESULT"));return;}
        String[] dischargeData = aEntry.getResult().split("#", -1); //vho.code||'#'||vrd.code||'#'||vhr.code

        if (stacCase) {   //Расчет профиля мед. помощи по профилю коек для стационара
            if (aEntry.getMedHelpProfile()==null||forceUpdate) {
                setProfileByBedType(aEntry);
            }
        }
        //Расчитывает Специальность лечащего врача/ врача, закрывшего талон (v015)
        if (aEntry.getFondDoctorSpec() == null || forceUpdate) {
            if (stacCase && aEntry.getMedHelpProfile() != null && aEntry.getMedHelpProfile().getMedSpec() != null) {  /* от 09-02-2018 Если у профиля мед. помощи указана специальность врача, указываем ее. Только для стационара */
                aEntry.setFondDoctorSpec(aEntry.getMedHelpProfile().getMedSpec());
            } else {
                String doctorWorkFunction =aEntry.getDoctorWorkfunction();
                key = "DOCTOR#" + doctorWorkFunction;
                if (vmpCase) {
                    if (aEntry.getMedHelpProfile() != null && aEntry.getMedHelpProfile().getProfileK().equals("81") && doctorWorkFunction.equals("26")) {
                        key += "#VMP";
                        if (!resultMap.containsKey(key)) {resultMap.put(key, getActualVocByClassName(VocE2FondV015.class, actualDate , "code='45'"));}
                    }
                }
                if (!resultMap.containsKey(key)) {
                    sb = new StringBuilder();
                    sb.append("select  v015.id from E2FondMedSpecLink link left join VocE2FondV015 v015 on v015.id=link.medSpec_id where link.medosWorkFunction='")
                            .append(doctorWorkFunction).append("'");
                    list = theManager.createNativeQuery(sb.toString()).getResultList(); //Находим исход случая (V015 PRVS)
                    if (list.isEmpty()) {
                        log.error("can't find DOCTOR = " + doctorWorkFunction + "____  find sql string = " + sb);
                    }
                    resultMap.put(key, list.isEmpty() ? null : theManager.find(VocE2FondV015.class, list.get(0).longValue()));
                }
                aEntry.setFondDoctorSpec((VocE2FondV015) resultMap.get(key));
            }
        }

        if (stacCase) { //Заполняем поля для стационара

            String reasonDischarge = dischargeData[1];
            String hospResult = dischargeData[2];
            String hospOutCome = dischargeData[0];

            //Результат лечения (RSLT)
            if (aEntry.getFondResult() == null || forceUpdate) {
                key = "HOSP#RESULT#" + reasonDischarge + "#" + hospResult + "#" + hospOutCome + "#" + bedSubType;
                if (!resultMap.containsKey(key)) {
                    sb = new StringBuilder();
                    sb.append("select v009.id from E2FondResultLink link left join VocE2FondV009 v009 on v009.id=link.result_id where ").append(addSql("link.medosReasonDischarge", reasonDischarge))
                            .append(" and ").append(addSql("link.medosHospResult", hospResult))
                            .append(" and ").append(addSql("link.medosHospOutcome", hospOutCome))
                            .append(" and v009.usl='").append(bedSubType).append("'");
                    list = theManager.createNativeQuery(sb.toString()).getResultList(); //Находим результат обращения (V009 RSLT)
                    if (list.isEmpty()) {
                        sb = new StringBuilder().append("select v009.id from E2FondResultLink link left join VocE2FondV009 v009 on v009.id=link.result_id where ").append(addSql("link.medosReasonDischarge", reasonDischarge))
                                .append(" and v009.usl='").append(bedSubType).append("'");
                        list = theManager.createNativeQuery(sb.toString()).getResultList(); //Находим результат обращения (V009 RSLT)
                        if (list.isEmpty()) {
                            log.error("can't find RSLT = " + aEntry.getResult() + "____ result find sql string = " + sb);
                        }
                    }
                    resultMap.put(key, list.isEmpty() ? null : theManager.find(VocE2FondV009.class, list.get(0).longValue()));
                }
                aEntry.setFondResult((VocE2FondV009) resultMap.get(key));
            }

            //calculateFondISHOD VocE2FondV012
            if (aEntry.getFondIshod() == null || forceUpdate) {
                key = "HOSP#ISHOD#" + hospResult + "#" + bedSubType; //Добавим справочник исходов, если в карте нет исхода - добавим
                if (!resultMap.containsKey(key)) {
                    sb = new StringBuilder();
                    sb.append("select v012.id from E2FondIshodLink link left join VocE2FondV012 v012 on v012.id=link.ishod_id where link.medosHospResult='")
                            .append(hospResult).append("' and link.bedSubType='").append(bedSubType).append("'");
                    list = theManager.createNativeQuery(sb.toString()).getResultList(); //Находим исход случая (V012 ISHOD)
                    if (list.isEmpty()) {
                        log.error("can't find ISHOD = " + aEntry.getResult() + "____ ishod find sql string = " + sb);
                    }
                    resultMap.put(key, list.isEmpty() ? null : theManager.find(VocE2FondV012.class, list.get(0).longValue()));
                }
                aEntry.setFondIshod((VocE2FondV012) resultMap.get(key));
            }
            calculatePatientDifficulty(aEntry); //Запустим расчет сложности лечения пациента



            //Вид медицинской помощи (для расчета нужен профиль МП
            String v008Code = entryType.equals(VMPTYPE)?"32":"31";
            if (aEntry.getBedSubType()!=null&&aEntry.getBedSubType().equals("2")&& (aEntry.getDepartmentId()!=null&&aEntry.getDepartmentId()==382L)) {
                v008Code=aEntry.getMedHelpProfile().getProfileK().equals("97")?"12":"13"; // TODO = переделать
            }
            key = "V008#"+v008Code;
            if (!resultMap.containsKey(key)) {
                resultMap.put(key,getActualVocByClassName(VocE2FondV008.class, actualDate,"code='"+v008Code+"'"));
            }
            aEntry.setMedHelpKind((VocE2FondV008)resultMap.get(key));


            //Способ оплаты мед. помощи IDSP //TODO Пока считаем только круглосуточный и дневной стационар
            String idspCode = bedSubType.equals("1")?"33":"43"; //TODO реализовать нормально
            if (entryType.equals(VMPTYPE)) {idspCode="13";} //Если ВМП, то способ оплаты = 13
            key = "IDSP#"+idspCode;
            if (!resultMap.containsKey(key)) {
                resultMap.put(key, getActualVocByClassName(VocE2FondV010.class,actualDate ," code='"+idspCode+"'"));
            }
            aEntry.setIDSP((VocE2FondV010)resultMap.get(key));

            //Условия оказания мед. помощи (V006)
            String v006Code = null;
            if (aEntry.getBedSubType()!=null&&aEntry.getBedSubType().equals("1")) {
                v006Code="101";
            } else if (aEntry.getBedSubType()!=null&&aEntry.getBedSubType().equals("2")) {
                v006Code=aEntry.getDepartmentId()!=null&&aEntry.getDepartmentId()==382L?"202":"201"; // TODO = переделать
            }
            if (v006Code!=null) {
                key = "V006#"+v006Code;
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key,getActualVocByClassName(VocE2FondV006.class, actualDate,"code='"+v006Code+"'"));
                }
                aEntry.setMedHelpUsl((VocE2FondV006)resultMap.get(key));
            } else {
                E2EntryError error = new E2EntryError(aEntry,"NO_USL_OKAZANIYA");theManager.persist(error);
            }



        } else if (polyclinicCase) { //Заполняем поля для пол-ки
            String resultCode=dischargeData[0], ishodCode=dischargeData[1];
            //Результат
            if (aEntry.getFondResult()==null||forceUpdate) {
                key = "VISIT#RESULT#"+resultCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key,getActualVocByClassName(VocE2FondV009.class, actualDate, "code='"+resultCode+"'"));}
                aEntry.setFondResult((VocE2FondV009)resultMap.get(key));
            }

            //Исход
            if (aEntry.getFondIshod()==null||forceUpdate) {
                key = "VISIT#ISHOD#" + ishodCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key, getActualVocByClassName(VocE2FondV012.class, actualDate, "code='" + ishodCode + "'"));}
                aEntry.setFondIshod((VocE2FondV012) resultMap.get(key));
            }

            //Профиль мед. помощи
            if (aEntry.getMedHelpProfile()==null||forceUpdate) {
                if (aEntry.getFondDoctorSpec()!=null) { //Обновляем профиль мед. помощи по профилю врача
                    aEntry.setMedHelpProfile(aEntry.getFondDoctorSpec().getPolicProfile());
                }
            }

            //Вид медицинской помощи
            if (aEntry.getMedHelpKind()==null||forceUpdate) {
                String v008Code = "13"; //первичная специализированная медико-санитарная помощь
                key = "V008#"+v008Code;
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key,getActualVocByClassName(VocE2FondV008.class, actualDate,"code='"+v008Code+"'"));
                }
                aEntry.setMedHelpKind((VocE2FondV008)resultMap.get(key));
            }


            //Условия оказания мед. помощи (V006)
        //    if (aEntry.getMedHelpUsl()==null||forceUpdate) {
                VocE2EntrySubType entrySubType =aEntry.getSubType();
                if (entrySubType==null) {
                    theManager.persist(new E2EntryError(aEntry,"NO_ENTRY_SUBTYPE"));
                } else {
                    aEntry.setMedHelpUsl(entrySubType.getUslOk());
                }
         //   }

            //Способ оплаты медицинской помощи
            if (aEntry.getIDSP()==null||forceUpdate) {
                String idspCode;
                if (polyclinicKdoCase) {
                   idspCode="30";
                } else {
                    if (isNotNull(aEntry.getIsEmergency())) { //Экстренно? тоды неотложная помощь
                        idspCode="41";
                    } else {
                        idspCode=aEntry.getStartDate().getTime()==aEntry.getFinishDate().getTime()?"29":"30";
                    }
                }

                key = "IDSP#"+idspCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key, getActualVocByClassName(VocE2FondV010.class,actualDate ," code='"+idspCode+"'"));}
                aEntry.setIDSP((VocE2FondV010)resultMap.get(key));
            }

        } else if (extDispCase) { // TODО реализовать для ДД
            //_vidpom

            //for_pom

            //vbr

            //профиль_К

            //<DS1_PR>1</DS1_PR>

            //Result <RSLT>
            if (aEntry.getFondResult()==null||forceUpdate) {
                String resultCode ="#"+aEntry.getResult()+"#";
                /* в зависимости от типа ДД, группа здоровья */
                key = "EXTDISP#RESULT"+resultCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key,getActualVocByClassName(VocE2FondV009.class, actualDate, "extDispCodes like '%"+resultCode+"%'"));}
                aEntry.setFondResult((VocE2FondV009)resultMap.get(key));
            }


            //Исход
            if (aEntry.getFondIshod()==null||forceUpdate) {
                String ishodCode = "306";
                key = "EXTDISP#ISHOD#" + ishodCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key, getActualVocByClassName(VocE2FondV012.class, actualDate, "code='" + ishodCode + "'"));}
                aEntry.setFondIshod((VocE2FondV012) resultMap.get(key));
            }


            //Профиль мед. помощи
            if (aEntry.getMedHelpProfile()==null||forceUpdate) {
                if (aEntry.getFondDoctorSpec()!=null) { //Обновляем профиль мед. помощи по профилю врача
                    aEntry.setMedHelpProfile(aEntry.getFondDoctorSpec().getPolicProfile());
                }
            }

            //Вид медицинской помощи
            if (aEntry.getMedHelpKind()==null||forceUpdate) {
                String v008Code = "12"; //ПЕРВИЧНАЯ ВРАЧЕБНАЯ МЕДИКО-САНИТАРНАЯ ПОМОЩЬ
                key = "V008#"+v008Code;
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key,getActualVocByClassName(VocE2FondV008.class, actualDate,"code='"+v008Code+"'"));
                }
                aEntry.setMedHelpKind((VocE2FondV008)resultMap.get(key));
            }


            //Условия оказания мед. помощи (V006)
            //    if (aEntry.getMedHelpUsl()==null||forceUpdate) {
            VocE2EntrySubType entrySubType =aEntry.getSubType();
            if (entrySubType==null) {
                theManager.persist(new E2EntryError(aEntry,"NO_ENTRY_SUBTYPE"));
            } else {
                aEntry.setMedHelpUsl(entrySubType.getUslOk());
            }

            //Способ оплаты медицинской помощи
            if (aEntry.getIDSP()==null||forceUpdate) {
                String idspCode="11";
                key = "IDSP#"+idspCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key, getActualVocByClassName(VocE2FondV010.class,actualDate ," code='"+idspCode+"'"));}
                aEntry.setIDSP((VocE2FondV010)resultMap.get(key));
            }

        } else {
            //   usl="4"; //скорая помощь
        }









    theManager.persist(aEntry);
    }

    public  String addSql(String aField, String aValue) {
        return  isNotNull(aValue)?" ("+aField+" ='"+aValue+"')":" ("+aField+" is null or "+aField+"='')";
    }
    private void cleanAllMaps(){
        entrySubTypeHashMap = new HashMap<String, VocE2EntrySubType>();
      //  diagnosisMap = new HashMap<String, Object>();
       // serviceList = new HashMap<String, VocMedService>();
        ksgMap = new HashMap<String, List<Object>>();
        tariffMap = new HashMap<String, BigDecimal>();
      //  cusmoMap = new HashMap<String, BigDecimal>();
      //  hospitalCostMap = new HashMap<String, BigDecimal>();
        difficultyHashMap = new HashMap<String, VocE2CoefficientPatientDifficulty>();
        polyclinicCasePrice = new HashMap<String, VocE2PolyclinicCoefficient>();
     //   resultMap = new HashMap<String, Object>(); //результат госпитализации
    }
    private @PersistenceContext EntityManager theManager;
    private @EJB ILocalMonitorService theMonitorService;
}
