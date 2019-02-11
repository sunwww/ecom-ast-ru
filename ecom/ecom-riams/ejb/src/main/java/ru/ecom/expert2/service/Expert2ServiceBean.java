package ru.ecom.expert2.service;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.domain.*;
import ru.ecom.expert2.domain.voc.*;
import ru.ecom.expert2.domain.voc.federal.*;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.*;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.oncological.ejb.domain.OncologyCase;
import ru.ecom.oncological.ejb.domain.OncologyContra;
import ru.ecom.oncological.ejb.domain.OncologyDiagnostic;
import ru.ecom.oncological.ejb.domain.OncologyDirection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
@Local(IExpert2Service.class)
@Remote(IExpert2Service.class)
public class Expert2ServiceBean implements IExpert2Service {
    private Boolean isCheckIsRunning = false;
    private static final Logger LOG = Logger.getLogger(Expert2ServiceBean.class);
    private static final String KDPTYPE="POL_KDP";
    private static final String HOSPITALTYPE="HOSPITAL";
    private static final String HOSPITALPEREVODTYPE="HOSPITALPEREVOD";
    private static final String POLYCLINICTYPE="POLYCLINIC";
    private static final String VMPTYPE="VMP";
    private static final String EXTDISPTYPE="EXTDISP";
    private static final String POLYCLINICKDOTYPE="POLYCLINICKDO";
    private static final String COMPLEXSERVICESTREAM = "COMPLEXCASE";
    private static final SimpleDateFormat SQLDATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat MONTHYEARDATE = new SimpleDateFormat("yyyy-MM");
    private static final ArrayList<String> childBirthMkb = new ArrayList<>();

    public E2Entry getEntryJson(Long aEntryId) {
        return theManager.find(E2Entry.class,aEntryId);

    }

    private JSONObject getOKJson() {
            return new JSONObject().put("status","ok");
    }

    /** Присваеваем отдельный счет для определенных иногородних регионов */
    public String splitForeignOtherBill(Long aListEntryId, String aBillNumber, Date aBillDate, String aTerritories) {
        if (aTerritories==null || aTerritories.equals("")) {
            aTerritories="'08','05'";
        }
        LOG.info("Разделяем иногородних по территории"+aBillNumber+" "+aBillDate+" "+aTerritories);
        E2Bill bill = getBillEntryByDateAndNumber(aBillNumber,aBillDate);
        List<BigInteger> list = theManager.createNativeQuery("select id from e2entry where listentry_id=:listId and substring(insurancecompanycode ,0,3) in ("+aTerritories+") and (isdeleted is null or isdeleted='0') and (donotsend is null or donotsend='0') ")
                .setParameter("listId",aListEntryId).getResultList();
        LOG.info("split ="+list.size());
        for (BigInteger id:list) {
            E2Entry e = theManager.find(E2Entry.class,id.longValue());
            e.setBill(bill);
            e.setBillDate(aBillDate);
            e.setBillNumber(aBillNumber);
            theManager.persist(e);
        }
        try {
            return getOKJson().put("count",list.size()).toString();
        } catch (JSONException e) {
            LOG.error(e); return "";
        }

    }

    /** Находим или создаем счет*/
    public E2Bill getBillEntryByDateAndNumber(String aBillNumber, java.util.Date aBillDate) {
        SimpleDateFormat f= new SimpleDateFormat("dd.MM.yyy");
        return getBillEntryByDateAndNumber(aBillNumber, f.format(aBillDate));

    }
    public Long getBillIdByDateAndNumber(String aBillNumber, String aBillDate) {return  getBillEntryByDateAndNumber(aBillNumber,aBillDate).getId();}
    public E2Bill getBillEntryByDateAndNumber(String aBillNumber, String aBillDate) {
        E2Bill bill = null;
        String sql = "select id from e2bill where billNumber=:number and billDate=to_date(:date,'dd.MM.yyyy') ";
        List<BigInteger> list = theManager.createNativeQuery(sql).setParameter("number",aBillNumber).setParameter("date",aBillDate).getResultList();
        if (list.isEmpty()) { //Создаем новый счет. статус - черновик
            try {
                bill = new E2Bill();
                bill.setBillNumber(aBillNumber);
                bill.setBillDate(DateFormat.parseSqlDate(aBillDate,"dd.MM.yyyy"));
                bill.setStatus((VocE2BillStatus)getActualVocByClassName(VocE2BillStatus.class,null,"code='DRAFT'"));
                theManager.persist(bill);
            } catch (ParseException e) {
                e.printStackTrace();
                LOG.error(e);
            }
        } else if (list.size()>1) {
            LOG.error("Найдено более 1 счета с указанным номером и датой!!");
        } else {
            bill = theManager.find(E2Bill.class,list.get(0).longValue());
        }

        return bill;
    }

    /** Клонируем запись*/
    public E2Entry cloneEntity(E2Entry aSourceObject) {return cloneEntity(aSourceObject,null,false);}
    private E2Entry cloneEntity(E2Entry aSourceObject, E2Entry newEntry, boolean needPersist) {
        try {
            Method[] methodList = aSourceObject.getClass().getMethods();
            E2Entry newEntity ;
            if (newEntry!=null) {
                newEntity = newEntry;
            } else {
                newEntity = new E2Entry();
            }
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
            if (needPersist){theManager.persist(newEntity);}
            return newEntity;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
            return null;
        }

    }
    /** Экспорт дефектов из заполнения в новое заполнение */
    public boolean exportDefectNewListEntry(Long aListEntryId) {
        try {
            E2ListEntry currentListEntry= theManager.find(E2ListEntry.class,aListEntryId);
            E2ListEntry newListEntry = new E2ListEntry(currentListEntry, "Дефекты_"+currentListEntry.getName());
            newListEntry.setCheckDate(currentListEntry.getCheckDate());
            newListEntry.setCheckTime(currentListEntry.getCheckTime());
            theManager.persist(newListEntry);
            List<E2Entry> list = theManager.createQuery("from E2Entry where listEntry_id=:id and isDefect='1' and (isDeleted is null or isDeleted='0')").setParameter("id",aListEntryId).getResultList();
            LOG.info("creating defects... defect list size = "+list.size());
            //TODO Сделать копирование диагнозов у child entry в обращениях

            for (E2Entry entry: list) {
                E2Entry newEntry = cloneEntity(entry);
                if (newEntry==null) {continue;}
                newEntry.setListEntry(newListEntry);
                theManager.persist(newEntry);
                cloneDiagnosis(entry,newEntry);
                cloneMedService(entry,newEntry);
                cloneChildEntries(entry,newEntry);
            }
            LOG.info("Finish create defects!");
        } catch (Exception e) {
            LOG.error(e);
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
            if (!subChildren.isEmpty()) {
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

    /**Добавляем услугу и диагноз в случай */
    public Boolean addDiagnosisAndServiceToEntry(Long aEntryId, String aData)  {
        E2Entry entry = theManager.find(E2Entry.class,aEntryId);
        try {
            JSONObject ds = new JSONObject(aData);
            String key = "DiagnosisMkbId";

            if (ds.has(key) && isNotNull(ds.getString(key))) {
                Long diagnosisIs = toLong(ds.getString(key));
                VocDiagnosisRegistrationType registrationType=theManager.find(VocDiagnosisRegistrationType.class,ds.getLong("DiagnosisRegistrationType"));
                VocIdc10 mkb=theManager.find(VocIdc10.class,diagnosisIs);
                VocPriorityDiagnosis priorityDiagnosis=theManager.find(VocPriorityDiagnosis.class,ds.getLong("DiagnosisPriority"));
                String dopMkb = ds.getString("DiagnosisDopMkb");
                Long illnessPrimary = ds.getLong("DiagnosisIllnesPrimary");
                VocE2FondV027 primary = theManager.find(VocE2FondV027.class,illnessPrimary);
                EntryDiagnosis ed = new EntryDiagnosis(entry,mkb,registrationType,priorityDiagnosis,dopMkb,primary);
                theManager.persist(ed);
                if(ds.has("DiagnosisMainMkb")) {
                    entry.setMainMkb(mkb.getCode());
                    theManager.persist(entry);
                }
            }
            key = "DiagnosisMedService";

            if (ds.has(key) && isNotNull(ds.getString(key))) {
                Long medserviceId= toLong(ds.getString(key));
                String serviceDate = ds.getString("DiagnosisMedServiceDate");
                VocMedService vocMedService = theManager.find(VocMedService.class,medserviceId);
                EntryMedService ems = new EntryMedService(entry,vocMedService);
                if (isNotNull(serviceDate)) {
                    ems.setServiceDate(DateFormat.parseSqlDate(serviceDate));
                }
                theManager.persist(ems);
                if (ds.has("DiagnosisIsMainService")) {
                    entry.setMainService(vocMedService.getCode());
                    theManager.persist(entry);
                }
            }
            return true;
        } catch (Exception e) {
            LOG.error("ERROR_PARSING_JSON:"+aData);
            LOG.error(e);
            return false;
        }
    }

    /** Объединяем СЛС с родами */
    private void unionChildBirthHospital(List<E2Entry> entriesList) {

        /*
        27-12-2018 Получаем список в обратном порядке
        14-12-2018. Логика поменялась. Первым может быть как паталогия, так и родовое. а, может даже и обсервационное отделение.
        делаем так: берем СЛО. Если роды, то склеиваем со след. СЛО. если отделения след. СЛО = отделению пред СЛО (пред. родовому), то и их склеиваем.
         */
         //  LOG.warn("unionChildBirth1");
        E2Entry mainEntry = null; //theManager.find(E2Entry.class,Long.valueOf(entriesIds.get(0)[0].toString()));
        boolean childBirthFound=false;
        E2Entry childEntry = null;
        E2Entry patologyEntry = null;

        for(E2Entry entry: entriesList) {
            if (mainEntry==null) { //Последнее СЛО (99% обсервационное)
                mainEntry=entry;
            } else if (mainEntry.getNoOmcDepartment() || entry.getNoOmcDepartment()) {
                mainEntry = mainEntry.getNoOmcDepartment() ? unionEntries(entry,mainEntry) : unionEntries(mainEntry,entry);
            } else if (entry.getDepartmentId().equals(mainEntry.getDepartmentId())) { //Если реанимация или Отд-Реан-Отд, последнее главное
                mainEntry = unionEntries(mainEntry,entry);
            } else if (entry.getIsChildBirthDepartment()) {
                mainEntry = unionEntries(mainEntry,entry);
                if (!childBirthFound) { //Первое (последнее) родовое отделение
                    childBirthFound=true;
                 //   makeCheckEntry(mainEntry,false,false);
                    childEntry = mainEntry;
                    mainEntry = null;
                }
            } else {
                LOG.warn("Что-то необычное в родах, стоит обратить внимание "+entry.getId()+entry.getHelpKind());
            }
        }
        if (childBirthFound && mainEntry !=null && !childEntry.equals(mainEntry)) {
            patologyEntry = mainEntry;
        }

        if (patologyEntry !=null && childEntry!=null) {
                //Если текущий случай - обсервационное отделение. Допускаем что до него может быть только патология беременности(+роды). Все операции с пред. отделений переносим в это отделение.
                Long calendarDays = isNotNull(patologyEntry.getCalendarDays()) ? patologyEntry.getCalendarDays() : 0;
                if (!patologyEntry.getIsChildBirthDepartment()
                        && (calendarDays > 5 || (calendarDays > 1 && childBirthMkb.contains(patologyEntry.getMainMkb())))) { //Если длительность случая - больше пяти дней (или диагноз входит в список)- не объединяемъ
                    VocE2FondV009 perevodResult = getActualVocByClassName(VocE2FondV009.class, patologyEntry.getFinishDate(), " code='104'"); //TODO Колхоз - исправить
                    patologyEntry.setFondResult(perevodResult); //TODO Колхоз - исправить
                    patologyEntry.setIsUnion(true);
                    theManager.persist(childEntry);
                    theManager.persist(patologyEntry);
                    makeCheckEntry(childEntry, false, false);
                } else {
                    unionEntries(childEntry,patologyEntry);
                }
        } /* else {
            LOG.warn("Чтото непонятное, похоже нет патологии");
        }*/
    }

    /** Удаляем дубли по поликлинике
     * дублем считаем повторное посещение про оодному профилю мед. помощи за исключением мобильной поликлиники, НМП и КДО
     * */
    private void deletePolyclinicDoubles(Long aListEntryId) {
        deletePolyclinicDoubles(aListEntryId,false);
        deletePolyclinicDoubles(aListEntryId,true);
    }

    /**Удаляем дубли по поликлинике */
    private void deletePolyclinicDoubles(Long aListEntryId, boolean deleteEmergency) {
        LOG.info(aListEntryId+" deletingDoubles... emergency="+deleteEmergency);
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("select max(e2.id), e2.externalpatientid , medhelpprofile_id,startdate, servicestream from e2entry e2 where e2.listentry_id =:listId")
                .append(" and e2.entryType='POLYCLINIC' and (e2.isDeleted is null or e2.isDeleted='0') and (e2.isUnion is null or e2.isUnion='0') and e2.serviceStream!='COMPLEXCASE'")
                .append(" and (e2.isMobilePolyclinic is null or e2.isMobilePolyclinic='0') and (e2.isEmergency is null or e2.isEmergency='0')")
                .append(deleteEmergency?" and e2.isEmergency ='1'":" and (e2.isEmergency is null or e2.isEmergency='0')")

                .append(" and (e2.isDiagnosticSpo is null or e2.isDiagnosticSpo='0') and medhelpprofile_id is not null ")
                .append(" group by e2.externalpatientid , medhelpprofile_id, startdate, servicestream")
                .append(" having count(e2.id)>1");
        List<Object[]> list = theManager.createNativeQuery(searchSql.toString()).setParameter("listId",aListEntryId).getResultList();
        LOG.info("list for delete double = "+list.size());
        if (!list.isEmpty()) {
            for (Object[] o:list) {
                theManager.createNativeQuery("update e2entry set isDeleted='1' where id="+o[0].toString()).executeUpdate();
                /*
                E2Entry doubleEntry = theManager.find(E2Entry.class, Long.valueOf(o[0].toString()));
                doubleEntry.setIsDeleted(true);
                theManager.persist(doubleEntry);
                */
            }
            deletePolyclinicDoubles(aListEntryId, deleteEmergency);
        }
    }

    /** Проверяем поликлинические случаи на пересечение */
    private void deleteCrossSpo(E2ListEntry aListEntry) {
        String sql = "select e.id as eid" +
                " from e2entry e " +
                " left join e2entry olde on olde.externalpatientid=e.externalpatientid" +
                " where e.listentry_id=" +aListEntry.getId()+
                " and e.servicestream!='COMPLEXCASE'" +
                " and e.id!=olde.id" +
                " and e.serviceStream=olde.serviceStream" +
                " and olde.bill_id is not null" +
                " and e.medhelpprofile_id = olde.medhelpprofile_id" +
                " and e.entrytype = olde.entrytype" +
                " and e.startdate<=olde.finishdate" +
                " and (e.isdeleted is null or e.isdeleted='0')" +
                " and (e.doNotSend is null or e.doNotSend='0')" +
                " and (olde.isdeleted is null or olde.isdeleted='0')"+
                " and (olde.doNotSend is null or olde.doNotSend='0')";
        List<BigInteger> list = theManager.createNativeQuery(sql).getResultList();
        if (!list.isEmpty()) {
            LOG.warn("Найдено "+list.size()+" пересекающихся случаев");
            for (BigInteger id: list) {
                E2Entry entry = theManager.find(E2Entry.class, id.longValue());
                theManager.persist(new E2EntryError(entry,"CROSS_SPO"));
            }
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

        LOG.info("union_kdo.list="+list.size()+", sql = "+sql.toString()+", id="+aListEntryId);
        if (list.isEmpty()) {
            LOG.error("KDO_LIST_IS_EMPTY, может, уже склеивали");
            list = theManager.createNativeQuery("select *  from e2entry where id="+(aEntryList!=null?aEntryList.get(0).getId():0)).getResultList();
            LOG.info("ENTRY MUST BE! id1="+aEntryList.get(0).getId()+", size = "+list.size());
            for (Object[] o:list) {
                LOG.info("o="+o[0]+" : " +o[1]+" : "+o[2]);
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
                if (currentEntry.getFondDoctorSpec()!=null&&isNotNull(currentEntry.getFondDoctorSpec().getIsKdoChief())){
                    LOG.info("KDO = found main entry");
                    //Нашли визит главного специалиста в КДО
                    mainEntry = cloneEntity(currentEntry);
                    mainEntry.setIsUnion(true);
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
                LOG.error("Не удалось найти главный случай в КДО");
            }


        }

    }
    /** Склеивание поликлинических визитов*/
    private void unionPolyclinicMedCase(Long aListEntryId, Long aPatientId, boolean isGroupBySpo) {
        /** Объединяем
         * Считаем по профилю мед. помощи, потоку обслуживания, классу МКБ
         * */
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("select ")
                .append(isGroupBySpo?"e2.externalparentid, cast('' as varchar) as empty, cast('' as varchar) as empty":"e2.externalpatientid , medhelpprofile_id, servicestream")
                .append(",cast('' as varchar) as empty, list(e2.id||'') from e2entry e2 where e2.listentry_id =:listId") //Не учитываем диагноз *06.08.2018
                // searchSql.append("select e2.externalpatientid , medhelpprofile_id, servicestream,substring(e2.mainmkb,1,1), list(e2.id||'') from e2entry e2 where e2.listentry_id =:listId")
                .append(" and e2.entryType='POLYCLINIC'")
                .append(aPatientId!=null&&aPatientId>0?" and e2.externalpatientid="+aPatientId:"")
                .append(" and (e2.isDeleted is null or e2.isDeleted='0') and (e2.isUnion is null or e2.isUnion='0') and e2.serviceStream!='COMPLEXCASE'")
                .append(" and (e2.isMobilePolyclinic is null or e2.isMobilePolyclinic='0') and (e2.isEmergency is null or e2.isEmergency='0')")
                .append(" and (e2.isDiagnosticSpo is null or e2.isDiagnosticSpo='0') ")
                .append(" and e2.medhelpprofile_id is not null and e2.fondResult_id!=23")
                //.append(" group by e2.externalpatientid , medhelpprofile_id, servicestream,substring(e2.mainmkb,1,1)")
                .append(" group by ").append(isGroupBySpo?"e2.externalparentid":"e2.externalpatientid , medhelpprofile_id, servicestream")
                .append(" having count(e2.id)>1").append(isGroupBySpo?"":"and count(case when substring(e2.mainmkb,1,1)='Z'then 1 else null end)<count(e2.id)");

        List<Object[]> list = theManager.createNativeQuery(searchSql.toString()).setParameter("listId",aListEntryId).getResultList();
        LOG.info("sql = "+searchSql+", size = "+list.size());
        for (Object[] spo: list){
            //Создаем новую запись, все существущие помечаем как COMPLEXCASE
            String[] ids = spo[4].toString().split(",");
            E2Entry mainEntry = null;
            for (String idd: ids) {
                Long id = Long.valueOf(idd.trim());
                E2Entry entry = theManager.find(E2Entry.class,id);
                if (mainEntry==null) {
                    mainEntry=cloneEntity(entry,null, true);
                    createDiagnosis(mainEntry);
                }
                mainEntry = unionPolyclinic(mainEntry,entry);

            }

            makeCheckEntry(mainEntry,false,true);
        }
        if (isGroupBySpo) {
            checkDefectPolyclinicCrossSpo(aListEntryId);
        }

    }

    /**В случае группировки по СПО выполняем проверку на наличие в одном СПО визитов к врачам разных специальностей. Помечаем их как дефекты *26.10.2018 */
    private void checkDefectPolyclinicCrossSpo (Long aListEntryId) {
        LOG.info("Находим неправильные случаи с разными профилями врачей в одном СПО");
            String sql ="select list(e2.id||''),e2.externalparentid,list(''||e2.parententry_id), count(e2.id) from e2entry e2 \n" +
                "where e2.listentry_id=258 and (isdeleted is null or isdeleted='0') and e2.servicestream='COMPLEXCASE'\n" +
                "group by e2.externalparentid\n" +
                "having count(distinct e2.medhelpprofile_id)>1";
            //TODO
    }

    /** Физическое объединение случая*/
    private E2Entry unionPolyclinic(E2Entry aMainMedcase, E2Entry aSecondMedcase) {
      //  E2Entry mainEntry, secondaryEntry;//mainEntry - latest entry
        if (aMainMedcase.getStartDate().getTime()>aSecondMedcase.getStartDate().getTime()){
            aMainMedcase.setStartDate(aSecondMedcase.getStartDate());
            aMainMedcase.setStartTime(aSecondMedcase.getStartTime());
        }
        if (aMainMedcase.getFinishDate().getTime()<aSecondMedcase.getFinishDate().getTime()){ //неглавное - последнее СЛО
            aMainMedcase.setFinishDate(aSecondMedcase.getFinishDate());
            aMainMedcase.setFinishTime(aSecondMedcase.getFinishTime());
            aMainMedcase.setFondResult(aSecondMedcase.getFondResult());
            aMainMedcase.setFondIshod(aSecondMedcase.getFondIshod());
        }
        aSecondMedcase.setParentEntry(aMainMedcase);
        aSecondMedcase.setServiceStream(COMPLEXSERVICESTREAM);
        aSecondMedcase.setIsUnion(true);
        List<E2Entry> childs = theManager.createQuery(" from E2Entry where parentEntry=:entry").setParameter("entry",aSecondMedcase).getResultList();
        for (E2Entry child: childs) {
            child.setParentEntry(aMainMedcase);
            theManager.persist(child);
        }
        theManager.persist(aMainMedcase);
        theManager.persist(aSecondMedcase);
        return aMainMedcase;

    }
    public void testUnionMecCase (Long aListEntryId, Long aHospitalMedcaseId, Long aPatientId, String aEntryType, boolean isGroupSpo) {
        if (aEntryType.equals(POLYCLINICTYPE)&&aPatientId!=null) {
            LOG.info("testUnionPolyclinicMedCase");
            unionPolyclinicMedCase(aListEntryId,aPatientId, isGroupSpo);
        } else if (aHospitalMedcaseId!=null) {
            LOG.info("testUnionHospitalMedCase");
            unionHospitalMedCase(aListEntryId, aHospitalMedcaseId);
        }
    }
    /** Объединеяем все записи по СЛС *Логика объединения тут */
    private void unionHospitalMedCase (Long aListEntryId, Long aHospitalMedCaseId) {

        //  LOG.warn("union "+aListEntryId +",hosp="+aHospitalMedCaseId);
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
        List<E2Entry> entriesList = theManager.createQuery("from E2Entry where listEntry_id=:listEntryId and externalParentId=:externalParentId and (isDeleted is null or isDeleted='0') order by startdate, starttime")
                .setParameter("listEntryId",aListEntryId).setParameter("externalParentId",aHospitalMedCaseId).getResultList(); //Все СЛО по госпитализации, кроме уже объединенных
        E2Entry mainEntry = null;
     //   E2Entry currentEntry;
        if (entriesList.size() > 1) { //Работаем если только найдено больше 1 СЛО
            //  LOG.warn("union = SLS = "+aHospitalMedCaseId+", size = "+entriesIds.size());
            //Цикл только для ВМП
            E2Entry vmpEntry = null;
            for (E2Entry entry:entriesList) {
                if (VMPTYPE.equals(entry.getEntryType())) { //Если в госпитализации есть случай ВМП, делаем его главным, остальные - неглавные.
                    mainEntry = entry;
                    mainEntry.setStartDate(entry.getHospitalStartDate());
                    mainEntry.setFinishDate(entry.getHospitalFinishDate());
                    mainEntry.setIsUnion(true);
                    vmpEntry=mainEntry;

                    LOG.info("Найден случай в ВМП, помечаем его как главный "+mainEntry.getId());
                    theManager.persist(mainEntry);
                    //return;
                }
                if ("13".equals(entry.getHelpKind())) { //В СЛС есть обсервационное отделение - запускаем функция по объединению родов! *27-12-2018
                    entriesList = theManager.createQuery("from E2Entry where listEntry_id=:listEntryId and externalParentId=:externalParentId and (isDeleted is null or isDeleted='0') order by startdate desc , starttime desc")
                            .setParameter("listEntryId",aListEntryId).setParameter("externalParentId",aHospitalMedCaseId).getResultList(); //При склеивании родов склеиваем с конца
                    unionChildBirthHospital(entriesList);
                    return;
                }
            }
            if (vmpEntry!=null) {
                for (E2Entry entry:entriesList) {
                    if (entry.getId()==mainEntry.getId()) {continue;}
                    entry.setParentEntry(vmpEntry);
                    entry.setServiceStream(COMPLEXSERVICESTREAM);
                    entry.setIsUnion(true);
                    theManager.persist(entry);
                }
                return;
            }
            //На этом этапе мы уверены, что ВМП в случае у нас нет, случай не содержит родов
            //VocE2FondV009 perevodResult =
          //  int i=0;
            for (E2Entry entry:entriesList) {
            //    i++;
            //    Long entryId = Long.valueOf(objects[0].toString());
                //   LOG.warn(i+" unionCnt = "+entryId);
                if (mainEntry == null) { //находим первую запись, считаем её главной
                    //       LOG.warn(i+"unionCntFirst = "+entryId);
                    mainEntry = entry;
                } else if (isNotNull(entry.getNoOmcDepartment())) { //Если реанимация - смело объединаем с главным случаем.
                    //       LOG.warn(i+"попался случай с реанимацией = "+entryId);
                    mainEntry.setReanimationEntry(entry);
                    unionEntries(mainEntry,entry);
                    //        LOG.info(i+"Главный случай с реаним"+mainEntry.getId());
                } else { //например - кардиология - сосуд. хирургия (вторая - главная
                    //        LOG.info(i+"Случай - не реанимация!");
                    if (mainEntry.getDepartmentId().equals(entry.getDepartmentId())) { //Если ИД отделения равны - не учитываем цену
                        //            LOG.info("ИД Отделения равны, последний случай - главный");
                        unionEntries(entry,mainEntry); //последнее отделение - главное
                        mainEntry=entry;
                    } else if (isDiagnosisGroupAreEquals(mainEntry,entry)) { //Если классы МКБ сходятся
                        if (mainEntry.getCost()==null && entry.getCost()==null) {
                            LOG.error("Невозможно объеинить случаи: нет цены ни в одном из случаев");
                        } else {
                            if (entry.getCost()==null || (mainEntry.getCost()!=null && mainEntry.getCost().compareTo(entry.getCost())>0)) { //Если у первого случая цена больше второго, первый - главный.
                                unionEntries(mainEntry,entry);
                            } else {
                                unionEntries(entry, mainEntry); //Если цена текущего случая больше или равно главному случаю, то текущий случай становится главный
                                mainEntry = entry;
                            }
                        }
                    } else { //Если классы МКБ не сходятся, текущее СЛО становится главным
                        String ss = mainEntry.getBedSubType(); //Текущему случаю ставим результат - перевод на другой профиль коек
                        mainEntry.setFondResult(getActualVocByClassName(VocE2FondV009.class,mainEntry.getFinishDate()," code='"+ss+"04'")); //TODO Колхоз - исправить
                        theManager.persist(mainEntry);
                        if (entriesList.size()>2) {
                            setRightParent(mainEntry,null);
                        }
                        mainEntry = entry;
                    }
                }
            }
          //  if (entriesList.size()>2) {setRightParent(aListEntryId,aHospitalMedCaseId);}
        }
    }
    /**Делаем правильных родителей для поликлиники*/
    private void setRightParentPolyclinic(Long aMainEntryId, String ids) { //Пока не используем
        String sql = "select ''||subchild.id from e2entry child left join e2entry subchild on subchild.parententry_id = child.id where child.parententry_id in ("+ids+") ";
        List<String> list = theManager.createNativeQuery(sql).getResultList();
        if (!list.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Object o: list) {
                if (sb.length()>0) {sb.append(",");}
                sb.append(o);
                theManager.createNativeQuery("update e2entry set parentEntry_id="+aMainEntryId+" where id="+o).executeUpdate();
            }
            setRightParentPolyclinic(aMainEntryId,sb.toString());
        }

    }

    /*Делаем все дочерние случаи от дочернего случая дочерними случаями главного случая*/
    private void setRightParent (E2Entry aMainEntry, List<E2Entry> aChildEntries) {
        if (1==1) {LOG.info("NE nado "+aMainEntry.getId());return;}
        if (aChildEntries==null) {
            aChildEntries = theManager.createQuery("from E2Entry where parentEntry_id=:parent").setParameter("parent",aMainEntry.getId()).getResultList();
            LOG.info("sze = "+aChildEntries.size());
        }
        if (aChildEntries!=null && !aChildEntries.isEmpty()) {
            for (E2Entry childEntry : aChildEntries) {
                List<E2Entry> subChildren = theManager.createQuery("from E2Entry where parentEntry_id=:parent").setParameter("parent",childEntry.getId()).getResultList();
                if (!childEntry.getParentEntry().equals(aMainEntry)) {
                    childEntry.setParentEntry(aMainEntry);
                    theManager.persist(childEntry);
                }
                if (!subChildren.isEmpty()) {
                    LOG.info("subChild size = "+subChildren.size());
                    setRightParent(aMainEntry,subChildren);
                }


            }
        }
    }
    /** Делаем правильных родителей для комплексных случаев */
 /*   private void setRightParent(Long aListEntryId, Long aHospitalMedCaseId) { //находим все "комплексные" случаи, у которых есть дочерние случаи и делаем дочерние случаи дочерними случаями родителя "комплексного" случая
        StringBuilder sql = new StringBuilder();
        sql.append("select eChild.id, e.parentEntry_id ")
                .append(" from e2entry e")
                .append(" left join e2entry eChild on eChild.parentEntry_id=e.id")
                .append(" where e.listentry_id=").append(aListEntryId).append(" and e.externalParentId=").append(aHospitalMedCaseId)
                .append(" and eChild.id is not null and e.serviceStream='COMPLEXCASE' and (e.isDeleted is null or e.isDeleted='0')");
        List<Object[]> res = theManager.createNativeQuery(sql.toString()).getResultList();
        if (res.isEmpty()) {return;}
        for (Object[] o:res) {
            theManager.createNativeQuery("update e2entry set parentEntry_id="+o[1].toString()+" where id="+o[0].toString()).executeUpdate();
        }
        setRightParent(aListEntryId,aHospitalMedCaseId);
    }

*/

 /**переносим информацию об услугах из комплексного случая в главный*/
 private List<EntryMedService> moveMedServiceToMainEntry(E2Entry aNotMainEntry, E2Entry aMainEntry) {
     List<EntryMedService> serviceList1 =aNotMainEntry.getMedServices();

     if (serviceList1!=null && !serviceList1.isEmpty()) {
         for (EntryMedService ems:serviceList1) {
             ems.setEntry(aMainEntry);
             theManager.persist(ems);
         }
     }
     serviceList1.addAll(aMainEntry.getMedServices());
     aMainEntry.setMedServices(serviceList1);
     aNotMainEntry.setMedServices(new ArrayList<>());
     theManager.persist(aMainEntry);
     theManager.persist(aNotMainEntry);
     return serviceList1;
 }
    /** тупо объединяем, не думаем */
    private E2Entry unionEntries(E2Entry aMainEntry, E2Entry aNotMainEntry) { //Функционал по объединению случаев
        if (aMainEntry==null||aNotMainEntry==null) {throw new IllegalStateException("UNOIN = entry=null");}
        if (aMainEntry.getStartDate().getTime()>aNotMainEntry.getStartDate().getTime()
                ||(aMainEntry.getStartDate().getTime() == aNotMainEntry.getStartDate().getTime() && aMainEntry.getStartTime().after(aNotMainEntry.getStartTime()))) {
            //Если дата+время начала главного случая позднее даты и время начала неглавного случая
            aMainEntry.setStartDate(aNotMainEntry.getStartDate());//Дата начала главного случая = дате начала текущего случая
            aMainEntry.setStartTime(aNotMainEntry.getStartTime());//Время начала главного случая = времени окончания начала текущего случая
        } else if (aNotMainEntry.getFinishDate().getTime()>aMainEntry.getFinishDate().getTime()
                ||(aNotMainEntry.getFinishDate().getTime()==aMainEntry.getFinishDate().getTime() && aNotMainEntry.getFinishTime().after(aMainEntry.getFinishTime()))) {
            //Если дата и время окончания неглавного случая позже даты и время окончания главного случая
            aMainEntry.setFinishDate(aNotMainEntry.getFinishDate());//Дата окончания главного случая = дате окончания текущего случая
            aMainEntry.setFinishTime(aNotMainEntry.getFinishTime());//Время окончания главного случая = времени окончания текущего случая
        } else {
            LOG.warn("Что-то пошло не так. Если это не ВМП, то, видимо, программист что-то напутал...main = "+aMainEntry.getId()+", not main ="+aNotMainEntry.getId());
            //В случаях, если есть ВМП в не первом случае, возможно и такое (текущий СЛО находится между датами С и ПО главного случая. Пропускаем...
        }
        if (aNotMainEntry.getReanimationEntry()!=null) aMainEntry.setReanimationEntry(aNotMainEntry); //Если в объединяемом случае была реанимация - она будет в главном случае
        if(isNotNull(aNotMainEntry.getNewbornAmount())) aMainEntry.setNewbornAmount(aNotMainEntry.getNewbornAmount()); //Переносим информация о детях из родового отделения в неродовое
       aMainEntry.setMedServices(moveMedServiceToMainEntry(aNotMainEntry,aMainEntry));
     /*   List<E2Entry> childEntries =theManager.createQuery("from E2Entry where parentEntry=:entry").setParameter("entry",aNotMainEntry).getResultList();
        for (E2Entry childEntry: childEntries) {
            childEntry.setParentEntry(aMainEntry);
            theManager.persist(childEntry);
        }*/
        aMainEntry.setIsUnion(true);
        aNotMainEntry.setIsUnion(true);
        aNotMainEntry.setParentEntry(aMainEntry);
        aNotMainEntry.setServiceStream("COMPLEXCASE");
        theManager.persist(aMainEntry);
        theManager.persist(aNotMainEntry);
        return aMainEntry;
    }
    /** Сравнимаем группы МКБ диагнозов для определения возможности склеивания СЛО */
    private boolean isDiagnosisGroupAreEquals(E2Entry aFirstEntry, E2Entry aSecondEntry) {
        List<EntryDiagnosis> firstDiagnosisList = theManager.createQuery("from EntryDiagnosis where entry_id=:id and registrationType.code='4' and priority.code='1' ").setParameter("id", aFirstEntry.getId()).getResultList();
        List<EntryDiagnosis> secondDiagnosisList = theManager.createQuery("from EntryDiagnosis where entry_id=:id and registrationType.code='4' and priority.code='1' ").setParameter("id", aSecondEntry.getId()).getResultList();
        if(firstDiagnosisList.isEmpty() || secondDiagnosisList.isEmpty()) {
            E2EntryError error = new E2EntryError(firstDiagnosisList.isEmpty() ? aFirstEntry : aSecondEntry,"NO_DIAGNOSIS");
            theManager.persist(error);
            LOG.error("Не найдено основного клинического диагноза по случаю NO_MAIN_DIAGNOSIS");
            return false;
        }
        return  firstDiagnosisList.get(0).getMkb().getCode().charAt(0)==secondDiagnosisList.get(0).getMkb().getCode().charAt(0);
    }
    /** Переводим дату в строку в формате yyyy-DD-mm*/
    private String dateToString(Date aDate) {
        return SQLDATE.format(aDate);
    }

    /** Переводим дату в строку в заданном формате */
    private String dateToString(Date aDate, String aFormat) {

        SimpleDateFormat format = new SimpleDateFormat(aFormat);
        return format.format(aDate);
    }


    /** Проверяем, является ли объект NULL либо пустой строкой */
    private boolean isNotNull(Object aField) {
        if (aField == null) return false;
        if (aField instanceof String) {
            String ss = (String) aField;
            return !ss.trim().equals("");
        } else if (aField instanceof Boolean) {
            return (Boolean) aField;
        } else if (aField instanceof Long) {
            return ((Long) aField) > 0L;
        } else if (aField instanceof java.sql.Date) {
            return true;
        } else if (aField instanceof BigDecimal) {
            return ((BigDecimal) aField).compareTo(new BigDecimal(0))==1;
        }else {
            throw new IllegalStateException("Нет преобразования для объекта " + aField);
        }
    }

    private String getWorkDir() {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    }

    /** Получение значения из справочника V002 по профилю коек */
    private static final HashMap<String, VocE2MedHelpProfile> bedTypes = new HashMap<>();
    private VocE2MedHelpProfile getProfileByBedType(E2Entry aEntry) {
        String bedType=aEntry.getHelpKind();
        String bedSubType = aEntry.getBedSubType();

     //   String sqlAdd="";
        VocE2MedHelpProfile profile;
        Long departmentId = aEntry.getDepartmentId()!=null?aEntry.getDepartmentId():0L;
        Date actualDate = aEntry.getFinishDate();
        String key = bedType+"#"+bedSubType+"#"+departmentId;
        if ("0M".equals(bedType) && departmentId.equals(266L)) {
            //Если неврология 2 и профиль  - неврологические = профиль = 310
            if (!bedTypes.containsKey(key)) {
                profile = getActualVocByClassName(VocE2MedHelpProfile.class,actualDate,"profilek='310'");
                bedTypes.put(key,profile);
            }
        } else if ("01".equals(bedType) && departmentId.equals(262L)) {
            //Если кардиология2 и профиль  - кардиологический профиль = 309
            if (!bedTypes.containsKey(key)) {
                profile = getActualVocByClassName(VocE2MedHelpProfile.class,actualDate,"profilek='309'");
                bedTypes.put(key,profile);
            }
        } else  {
            if(!bedTypes.containsKey(key)) { //Если нет в карте - запускаем поиск
                List<BigInteger> list = theManager.createNativeQuery("select v.id from vocbedtype vbt left join e2medhelpprofilebedtype mhpbt on mhpbt.bedtype_id=vbt.id " +
                        " left join voce2medhelpprofile v on v.id= mhpbt.profile_id left join vocbedsubtype vbst on vbst.id=mhpbt.subtype_id where vbt.omccode=:code and v.id is not null and (mhpbt.subtype_id is null or vbst.code=:subTypeCode )")
                        .setParameter("code", bedType).setParameter("subTypeCode",bedSubType).getResultList();
                if (list.isEmpty()) {
                    theManager.persist(new E2EntryError(aEntry, "NO_PROFILE"));
                    LOG.error("Не найдено профиля мед. помощи для коек с типом NO_PROFILE_FOR_BED " + key);
                } else if (list.size() > 1) {
                    theManager.persist(new E2EntryError(aEntry, "TOO_MANY_PROFILE:" +key));
                    LOG.error("Найдно больше 1 соответствия с профилем мед. помощи для коек с типом TO_MANY_PROFILE_FOR_BED " + bedType);
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
        return profile; //null так null
    }

    /** Добавляем соответствие между профилем мед. помощи и профилем койки */
    //Для ServiceJS------------------------------------------------------------
    public void addMedHelpProfileBedType(Long aMedHelpId, Long aBedTypeId, Long aBedSubTypeId) {
        VocBedType bedType = theManager.find(VocBedType.class, aBedTypeId);
        if (!theManager.createNativeQuery("select e. id from E2MedHelpProfileBedType e left join vocbedType vbt on vbt.id=e.bedtype_id " +
                " where e.profile_id=:profile and (e.bedtype_id=:bedTypeId or vbt.omcCode=:omcCode)").setParameter("profile",aMedHelpId).setParameter("bedTypeId", aBedTypeId)
                .setParameter("omcCode", bedType.getOmcCode()).getResultList().isEmpty()) {
            LOG.warn("Нельзя прикрепить койку, т.к. койка с таким кодом уже прикреплена");
            return;
        }
        E2MedHelpProfileBedType mhbt = new E2MedHelpProfileBedType();
        mhbt.setBedType(bedType);
        mhbt.setProfile(theManager.find(VocE2MedHelpProfile.class, aMedHelpId));
        if (aBedSubTypeId!=null&&aBedSubTypeId>0L) {mhbt.setSubType(theManager.find(VocBedSubType.class,aBedSubTypeId));}
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
        LOG.info("start refill ListEntry_"+aListEntryId);
        try {
            list.setCheckDate(null);
            list.setCheckTime(null);
            theManager.persist(list);
            fillListEntry(list,null);
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            LOG.error(e);
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

        if (listEntryType == null) {
            LOG.error("Не указан тип заполнения NO_ENTRYLIST_TYPE");
            return;
        }

        String resourceName;
        switch (listEntryType) {
            case EXTDISPTYPE:
                resourceName = "ExtDisp.sql";
                break;
            case HOSPITALTYPE:
                resourceName = "Hospital.sql";
                break;
            case POLYCLINICTYPE:
                resourceName = "Visit.sql";
                break;
            case HOSPITALPEREVODTYPE:
                resourceName = "HospitalPerevod.sql";
                break;
            case POLYCLINICKDOTYPE:
                resourceName="VisitKdo.sql";
                break;
            case KDPTYPE:
                resourceName ="StacKdp.sql";
                break;
            default:
                LOG.error("Неизвесный тип заполнения");
                throw new IllegalStateException("Неизвестный тип заполнения!!");
        }

        String searchSql = getFileAsSTring(resourceName);
        if (searchSql == null) {
            LOG.error("NO SQL FILE FOUND");
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
        LOG.info("SQL = " + searchSql);
        Statement statement = createStatement();
        ResultSet foundCases = statement.executeQuery(searchSql);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("listEntry", aListEntry);
        aListEntry.setCreateDate(new java.sql.Date(System.currentTimeMillis()));
        aListEntry.setCreateTime(new java.sql.Time(System.currentTimeMillis()));
        theManager.persist(aListEntry);
        try {
            createEntriesByEntryList(foundCases, paramMap, listEntryType, aListEntry); //Создаем записи по заполнению
        } catch (ParseException e) {
            e.printStackTrace();
            LOG.error("can't parse data",e);
        } finally {
            statement.close();
        }

    }

    /** Базовая точка для выполнения всех проверок внутри заполнения */
    public void checkListEntry(Long aListEntryId, boolean updateKsgIfExist, String aParams, long aMonitorId) {checkListEntry(theManager.find(E2ListEntry.class,aListEntryId),updateKsgIfExist, aParams,aMonitorId );}
    private void checkListEntry(E2ListEntry aListEntry, final boolean updateKsgIfExist, String aParams, long aMonitorId) {
        if (aListEntry.getIsClosed()!=null&&aListEntry.getIsClosed()) {
            LOG.warn("Заполнение закрыто, проверка невозможна");
            throw new IllegalStateException("Заполнение закрыто, проверка невозможна");
        }
        try {
            Long startStartDate = System.currentTimeMillis();
            LOG.info("start checkListEntry");
            cleanAllMaps(); //очистим карты перед началом новой проверки
            if (null == aListEntry.getCheckDate()) { //Запускаем первичную проверку всех записей
                checkListEntryFirst(aListEntry,null, aMonitorId);
                return;
            }
            if (isCheckIsRunning) {
                LOG.warn("Проверка уже запущена, ничего не проверяем, RETURN");
                return;
            }
            isCheckIsRunning=true;
            StringBuilder sql = new StringBuilder();
            sql.append("select id from E2Entry where listEntry_id=:id and (isDeleted is null or isDeleted='0')");
          //  StringBuilder sqlAdd = new StringBuilder();
            if (isNotNull(aParams)) {
                LOG.warn(aParams);
                String[] params = aParams.split("&") ;
                for(String par:params) {
                    if (par.contains("=")) {
                        String[] parr = par.split("=");
                        if (parr.length<2 || parr[1]==null || parr[1].trim().equals("")) {continue;}
                        String fldName = parr[0], val=parr[1];
                        if (fldName.toUpperCase().indexOf("DATE")>-1) { //ищем дату
                            sql.append(" and ").append(fldName).append("=").append("to_date('"+val+"','dd.MM.yyyy') ");
                        } else if (fldName.startsWith("is")) { //Булево значение
                            sql.append(" and (").append(fldName).append(val.equals("1")?" ='1')":" is null or "+fldName+"='0')");
                        } else { //ищем строку
                            sql.append(" and upper(").append(fldName).append(")=").append("'"+val.toUpperCase()+"' ");
                        }
                    }
                }
            }
            theManager.createNativeQuery("delete from e2entryerror  where listentry_id=:id").setParameter("id",aListEntry.getId()).executeUpdate(); //Все ошибки удалим
            LOG.info("sql="+sql);
            List<BigInteger> list = theManager.createNativeQuery(sql.toString()).setParameter("id",aListEntry.getId()).getResultList();

            if (list.isEmpty()) {LOG.warn("Случаев для проверки не найдено "+sql.toString());return;}
            //IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Расчет цены случаев в звполнении") ;
           IMonitor monitor = theMonitorService.startMonitor(aMonitorId,"Пересчет случаев в заполнении",list.size());
            monitor.advice(1);
            /* сначала найдем КСГ (для правильного объединения
            потом  производим объединение случаев, потом (только для ОМС) расчитываем поля фонда, считаем цену
            */
            String listEntryCode =aListEntry.getEntryType().getCode();
            if (listEntryCode.equals(HOSPITALTYPE)||listEntryCode.equals(HOSPITALPEREVODTYPE)) {
                int i=0;
                //    Thread[] threads = new Thread[3];
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
                    if (i%100==0) {
                        if (isMonitorCancel(monitor,"Расчитано "+i+ " записей (СТАЦИОНАР)")) {
                            return;
                        }
                    }
                    //Теперь снова находим КСГ, расчитываем цену и коэффициенты
//                    makeCheckEntry(theManager.find(E2Entry.class,bi.longValue()),updateKsgIfExist);
                }
                monitor.setText("Идет процесс удаление дублей");
                checkDoubles(aListEntry);
            } else if (listEntryCode.equals(POLYCLINICTYPE) || listEntryCode.equals(POLYCLINICKDOTYPE) || listEntryCode.equals(KDPTYPE)) {
                Long listEntryId = aListEntry.getId();
                if (listEntryCode.equals(POLYCLINICTYPE)){
                    Boolean isGroupSpo = getExpertConfigValue("ISGROUPSPO","0").equals("1");
                    monitor.setText("Удаление дублей в поликлинике");
                    deletePolyclinicDoubles(listEntryId );
                    monitor.setText("Склеивание случаев поликлинического обслуживания");
                    unionPolyclinicMedCase(listEntryId ,null,isGroupSpo);
                } else if (listEntryCode.equals(KDPTYPE)) {
                    monitor.setText("Проверяем случаи КДП");
                    //unionPolyclinicKdoMedCase(listEntryId,null);
                }

                int i=0;
                for(BigInteger bi : list) {
                    i++;
                    if (i%100==0) {
                        LOG.info("process pol ... checking entry.... "+i);
                        monitor.setText("Расчет цены случая (поликлиника)");
                        if (monitor.isCancelled()) {
                            LOG.info("Проверка прервана пользователем");
                            monitor.setText("Проверка прервана пользователем");
                            return;
                        }
                    }
                    //Теперь снова находим КСГ, расчитываем цену и коэффициенты
                    makeCheckEntry(theManager.find(E2Entry.class,bi.longValue()),updateKsgIfExist, true);
                }
                monitor.setText("Приступаем к проверке перекрестных случаев.");
                deleteCrossSpo(aListEntry);
            }
            Long currentTime = System.currentTimeMillis();
            isMonitorCancel(monitor,"Закончена проверка! Время выполнения проверки (минут) = "+((currentTime-startStartDate))/60000);
            aListEntry.setCheckDate(new java.sql.Date(currentTime));
            aListEntry.setCheckTime(new java.sql.Time(currentTime));
            theManager.persist(aListEntry);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
            LOG.error("ERR"+e);
        }
        LOG.info("check list entry finished!");
        cleanAllMaps();
        isCheckIsRunning=false;
    }
    public void makeCheckEntry(Long aEntryId, boolean updateKsgIfExist) {
        if (!theManager.createNativeQuery("select e.id from e2entry e left join e2listentry el on el.id=e.listEntry_id where e.id=:id and el.isClosed='1'").setParameter("id",aEntryId).getResultList().isEmpty()) {throw new IllegalStateException("Заполнение закрыто, проверка невозможна");}
        cleanAllMaps();
        makeCheckEntry(theManager.find(E2Entry.class, aEntryId),  updateKsgIfExist, true);
    }

    /** Запустить проверку случая (расчет КСГ, цены, полей для xml) */
    private void makeCheckEntry(E2Entry aEntry, boolean updateKsgIfExist, boolean checkErrors) {
    //    LOG.warn("SIZE childs = "+aEntry.getChildEntryList().size()); setRightParent(aEntry,null);
        long bedDays = AgeUtil.calculateDays(aEntry.getStartDate(), aEntry.getFinishDate());
        long calendarDays = bedDays>0?bedDays+1:1;
        if (HOSPITALTYPE.equals(aEntry.getEntryType()) && "2".equals(aEntry.getBedSubType())) { //Для дневного стационара день поступления и день выписки - 2 дня
            bedDays++;
        }
        try {
            aEntry=setEntrySubType(aEntry);
            aEntry.setIsForeign(isNotNull(aEntry.getInsuranceCompanyCode()) && !aEntry.getInsuranceCompanyCode().startsWith("30"));
            aEntry.setBedDays(bedDays > 0 ? bedDays : 1L);
            aEntry.setIsChild(AgeUtil.calcAgeYear(aEntry.getBirthDate(),aEntry.getStartDate())<18);
            aEntry.setCalendarDays(calendarDays);
            getBestKSG(aEntry, updateKsgIfExist,true); //Находим КСГ
            calculateFondField(aEntry,updateKsgIfExist);
            aEntry = calculateEntryPrice(aEntry);
            if (checkErrors) checkErrors(aEntry);
            theManager.persist(aEntry);
        } catch (Exception e) {
            LOG.error("ERR=",e);
            throw new IllegalStateException("Какая-то ошибка, формировать ничего не будем!");
        }
    }

    /** Найдем подтип случая (посещение, обращение, НМП */
    private HashMap<String, VocE2EntrySubType> entrySubTypeHashMap = new HashMap<>();
    private E2Entry setEntrySubType(E2Entry aEntry){
        String code;
        String entryType=aEntry.getEntryType();
        if (entryType==null||entryType.equals("")) {entryType=aEntry.getListEntry().getEntryType().getCode();aEntry.setEntryType(entryType);}
        if (entryType.equals(HOSPITALTYPE)) {
            if (isNotNull(aEntry.getVMPKind())) {
                code="VMP";
            } else if (aEntry.getBedSubType().equals("1")) {
                code="ALLTIMEHOSP";
            } else if (aEntry.getBedSubType().equals("2")){
                if (aEntry.getDepartmentId().equals(382L)) { //Дневной стационар при АПУ *пока только для АМОКБ
                    code="POLDAYTIMEHOSP";
                } else { //Дневной стационар при стационаре
                    code="DAYTIMEHOSP";
                }
            } else {
                code="UNKNOWNTIMEHOSP";
            }
        } else if (entryType.equals(POLYCLINICTYPE) || entryType.equals(POLYCLINICKDOTYPE)) {
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
                code = (isMobilePolyclinic ? "MOBILE_" : "CONS_") +code;
                code += "_" + (workPlace != null ? workPlace : "NO_WORKPLACE");
            }
        } else if (entryType.equals(VMPTYPE)) {
            code = "STAC_VMP";
        } else if (entryType.equals(EXTDISPTYPE)){
            code=EXTDISPTYPE+"_"+aEntry.getExtDispType();
        } else if (entryType.equals(KDPTYPE)) {
            code=KDPTYPE;

        } else {
            LOG.error(aEntry.getId()+" Неизвестный тип записи для проставление подтипа. CANT_SET_SUBTYPE: "+entryType);
            return aEntry;
        }
        if(!entrySubTypeHashMap.containsKey(code)) {
            entrySubTypeHashMap.put(code,(VocE2EntrySubType) getEntityByCode(code, VocE2EntrySubType.class,false));
        }
        VocE2EntrySubType subType =entrySubTypeHashMap.get(code);
        if (subType == null) {
            throw new IllegalStateException("Не найдено вида случая с кодом: "+code);
        }
        aEntry.setSubType(subType);
        if (subType.getVidSluch()!=null) {
            aEntry.setVidSluch(subType.getVidSluch());
        } else {
            LOG.error("Не заполнен вид случая для записей с типом "+subType.getCode()+" "+subType.getName());
        }
        aEntry.setVisitPurpose(subType.getVisitPurpose()); //Цель посещения (V025)
        aEntry.setMedHelpUsl(subType.getUslOk()); //Условия оказания находим согласно подтипу записи (V006)
        aEntry.setIDSP(subType.getIdsp());
        return aEntry;
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
        sql.append(" select distinct new.id ")
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
//TODO проверка с пересечением поликлиники и стационара
    }
    private void checkErrors(E2Entry aEntry) {
        List<E2EntryError> errors = new ArrayList<>();
        if ("OBLIGATORYINSURANCE".equals(aEntry.getServiceStream())) { //Проверка только для ОМС *05.06.2018
            //Дата выписки не входит в период
            if (aEntry.getFinishDate().getTime()>aEntry.getListEntry().getFinishDate().getTime()
                    ||aEntry.getFinishDate().getTime()<aEntry.getListEntry().getStartDate().getTime()) {
                errors.add(new E2EntryError(aEntry,E2EntryErrorCode.DISCHARGE_DATE_NOT_IN_PERIOD));
            }
            if (aEntry.getEntryType().equals(HOSPITALTYPE)&& aEntry.getKsg()==null){
                errors.add(new E2EntryError(aEntry,E2EntryErrorCode.NO_KSG));
            }
            if (!errors.isEmpty()){saveErrors(errors);}
        }
    }

    /**
     * Расчет цены случая //TODO
     *
     * @param aEntry
     */
    public E2Entry calculateEntryPrice(E2Entry aEntry) {
        String entryType = aEntry.getEntryListType() !=null ? aEntry.getEntryListType() : aEntry.getEntryType(); //Переделать на aEntry.getEntryType();
        if (entryType.equals(HOSPITALTYPE) || entryType.equals(HOSPITALPEREVODTYPE)) {
            aEntry = calculateHospitalEntryPrice(aEntry);
        } else if (entryType.equals(POLYCLINICTYPE) || entryType.equals(POLYCLINICKDOTYPE) || entryType.equals(KDPTYPE)) {
            calculatePolyclinicEntryPrice(aEntry);
        } else if (entryType.equals(EXTDISPTYPE)) {
            calculateExtDispEntryPrice(aEntry);
        } else {
            throw new IllegalStateException("Неизвестный тип реестра : "+entryType);
        }
        return aEntry;
    }

    /** Получить сущность по коду (в основном для справочников) */
    private <T> T getEntityByCode(String aCode, Class aClass, Boolean needCreate) {
        if (aCode==null) {return null;}
        List<T> list = theManager.createQuery("from " + aClass.getName() + " where code=:code").setParameter("code", aCode).getResultList();
        T ret = !list.isEmpty() ? list.get(0) : null;
        if (list.isEmpty() && needCreate) {
            try {
                ret = (T) aClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                LOG.error(e);
            }
            if (ret instanceof VocBaseEntity|| ret instanceof VocMedService) { //Проверить
                try {
                    Method setCodeMethod = ret.getClass().getMethod("setCode",new Class[]{String.class});
                    setCodeMethod.invoke(ret,aCode);
                    LOG.info("create new voc("+aClass.getName()+").set code = "+aCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error(e);
                }
            }
            theManager.persist(ret);
            LOG.warn("crate new voc+"+ret);
        }
        return ret;
    }

    /*Находим онкологический случай по СЛС */
    private void findCancerEntry(E2Entry aEntry) {
        List<OncologyCase> oncologyCases = theManager.createQuery("from OncologyCase where medCase_id=:id").setParameter("id",aEntry.getExternalParentId()).getResultList();
        if (!oncologyCases.isEmpty()) {
            try {
                OncologyCase oncologyCase = oncologyCases.get(0);
                E2CancerEntry cancerEntry = new E2CancerEntry(aEntry);
                cancerEntry.setMaybeCancer(oncologyCase.getSuspicionOncologist());
                if (cancerEntry.getMaybeCancer()) { //Подозрение на онкологию, ищем только направления
                    List<OncologyDirection> directions = oncologyCase.getDirections();
                    if (!directions.isEmpty()) {
                        List<E2CancerDirection> list = new ArrayList<>();
                        for (OncologyDirection direction: directions) {
                            E2CancerDirection cancerDirection = new E2CancerDirection(cancerEntry);
                            cancerDirection.setDate(direction.getDate());
                            cancerDirection.setType(direction.getTypeDirection()!=null?direction.getTypeDirection().getCode():"");
                            cancerDirection.setMedService(direction.getMedService()!=null?direction.getMedService().getCode():"");
                            cancerDirection.setSurveyMethod(direction.getMethodDiagTreat()!=null?direction.getMethodDiagTreat().getCode():"");
                            cancerDirection.setDirectLpu(direction.getDirectLpu()!=null?direction.getDirectLpu().getCodef():"");
                            list.add(cancerDirection);
                        }
                        cancerEntry.setDirections(list);
                    }
                } else { //Случай онкологического лечения
                    cancerEntry.setOccasion(oncologyCase.getVocOncologyReasonTreat()!=null?oncologyCase.getVocOncologyReasonTreat().getCode():"");
                    cancerEntry.setStage(oncologyCase.getStad()!=null?oncologyCase.getStad().getCode():"");
                    cancerEntry.setTumor(oncologyCase.getTumor()!=null?oncologyCase.getTumor().getCode():"");
                    cancerEntry.setNodus(oncologyCase.getNodus()!=null?oncologyCase.getNodus().getCode():"");
                    cancerEntry.setMetastasis(oncologyCase.getMetastasis()!=null?oncologyCase.getMetastasis().getCode():"");
                    cancerEntry.setIsMetastasisFound(oncologyCase.getDistantMetastasis());
                    cancerEntry.setSod(oncologyCase.getSumDose());
                    cancerEntry.setConsiliumResult(oncologyCase.getConsilium()!=null?oncologyCase.getConsilium().getCode():"");
                    cancerEntry.setConsiliumDate(oncologyCase.getDateCons());
                    cancerEntry.setServiceType(oncologyCase.getTypeTreatment()!=null?oncologyCase.getTypeTreatment().getCode():"");
                    cancerEntry.setSurgicalType(oncologyCase.getSurgTreatment()!=null?oncologyCase.getSurgTreatment().getCode():"");
                    cancerEntry.setDrugLine(oncologyCase.getLineDrugTherapy()!=null?oncologyCase.getLineDrugTherapy().getCode():"");
                    cancerEntry.setDrugCycle(oncologyCase.getCycleDrugTherapy()!=null?oncologyCase.getCycleDrugTherapy().getCode():"");
                    cancerEntry.setRadiationTherapy(oncologyCase.getTypeRadTherapy()!=null?oncologyCase.getTypeRadTherapy().getCode():"");
                    List<OncologyContra> prots = oncologyCase.getContras();
                    if (!prots.isEmpty()) {
                        List<E2CancerRefusal> list = new ArrayList<>();
                        for (OncologyContra prot:prots) {
                            E2CancerRefusal refusal = new E2CancerRefusal(cancerEntry);
                            refusal.setCode(prot.getContraindicationAndRejection().getCode());
                            refusal.setDate(prot.getDate());
                            list.add(refusal);
                        }
                        cancerEntry.setRefusals(list);
                    }
                    try {
                        List<OncologyDiagnostic> diags  =oncologyCase.getDiagnostics();
                        if (!diags.isEmpty()) {
                            List<E2CancerDiagnostic> list = new ArrayList<>();
                            for (OncologyDiagnostic diag:diags) {
                                E2CancerDiagnostic cancerDiagnostic = new E2CancerDiagnostic(cancerEntry);
                                String diagnosticType = diag.getVocOncologyDiagType()!=null?diag.getVocOncologyDiagType().getCode():null;
                                if (diagnosticType !=null) {
                                    cancerDiagnostic.setType(diagnosticType);
                                    if (diagnosticType.equals("1")){
                                        cancerDiagnostic.setCode(diag.getHistiology().getCode());
                                        cancerDiagnostic.setResult(diag.getResultHistiology().getCode());
                                    } else if (diagnosticType.equals("2")) {
                                        cancerDiagnostic.setCode(diag.getMarkers().getCode());
                                        cancerDiagnostic.setResult(diag.getValueMarkers().getCode());
                                    }
                                    list.add(cancerDiagnostic);
                                }
                            }
                            cancerEntry.setDiagnostics(list);
                        }
                    } catch (Exception e) {
                        LOG.error("Ошибка при заполнении диагностиеческого блока! ",e);
                    }
                }
                theManager.persist(cancerEntry);
            } catch (Exception e) {LOG.error("Не смог создать раковый случай ",e);}
        }
    }

    private HashMap<String, Object > diagnosisMap = new HashMap<>();
    private List<EntryDiagnosis> getDiagnosis(E2Entry aEntry) {return   aEntry.getDiagnosis()!=null?aEntry.getDiagnosis():new ArrayList<>();}
    /** Создаем список диагнозов из строки с диагнозами +устанавливаем основной диагноз
     *  UPD 18-07-2018 Помечаем случай как раковый
     * */ //делаем разово
    private void createDiagnosis(E2Entry aEntry) {
        try {
            String diagnosisList = aEntry.getDiagnosisList();
            if (isNotNull(diagnosisList)) { //Создаем диагнозы для каждой записи
                JSONArray diagnosiss = new JSONArray(diagnosisList);

                String mkb, regType,priority;
                ///   String mainMkb = null;
                boolean foundClinical=false, foundDischarge = false;
                List<Long> clinicalIds = new ArrayList<>();
                boolean isCancer=false;
                for (int i=0;i<diagnosiss.length();i++) {
                    JSONObject ds = diagnosiss.getJSONObject(i);
                    mkb =ds.getString("mkb");
                    regType = ds.getString("registrationType");
                    priority=ds.getString("priority");
                    if (mkb.startsWith("C") && priority.equals("1")) { //Если основной диагноз начинается с С*
                        isCancer=true;
                        findCancerEntry(aEntry);
                    }
                    boolean isClinical=false;//,isDischarge=false;
                    if (!diagnosisMap.containsKey("MKB_"+mkb)) {
                        diagnosisMap.put("MKB_"+mkb, getEntityByCode(mkb, VocIdc10.class, false));
                    }
                    if (!diagnosisMap.containsKey("REGTYPE_"+regType)) {
                        diagnosisMap.put("REGTYPE_"+regType, getEntityByCode(regType, VocDiagnosisRegistrationType.class, false));
                        if (regType.equals("3")) {foundDischarge=true;}
                        else if (regType.equals("4")) {foundClinical =true;isClinical=true;}
                    }
                    if (!diagnosisMap.containsKey("PRIORITY_"+priority)) {
                        diagnosisMap.put("PRIORITY_"+priority,  getEntityByCode(priority, VocPriorityDiagnosis.class, false));
                    }
                    if (foundDischarge && isClinical) {foundClinical=false;continue;} //Если нашли клинический и выписной - не создаем клинический

                    EntryDiagnosis diagnosis = new EntryDiagnosis();
                    diagnosis.setEntry(aEntry);
                    VocIdc10 vocIdc10 =(VocIdc10) diagnosisMap.get("MKB_"+mkb);
                    if (vocIdc10.getCode().indexOf('.')==-1 && (vocIdc10.getIsPermitWithoutDot()==null || !vocIdc10.getIsPermitWithoutDot())) { //Если диагноз без расшифровки и он не разрешен к использованию без уточнения
                        theManager.persist(new E2EntryError(aEntry,"DIAGNOSIS_WITHOUT_UTOCHNENIE"));
                    }
                    diagnosis.setMkb(vocIdc10);
                    diagnosis.setRegistrationType((VocDiagnosisRegistrationType) diagnosisMap.get("REGTYPE_"+regType));
                    diagnosis.setPriority((VocPriorityDiagnosis) diagnosisMap.get("PRIORITY_"+priority));
                    diagnosis.setIllnessPrimary(ds.getString("illnessPrimary"));
                    if (ds.has("vocIllnessPrimary")) {
                        String vip = ds.getString("vocIllnessPrimary");
                        if (isNotNull(vip)) diagnosis.setVocIllnessPrimary(getEntityByCode(vip, VocE2FondV027.class,false));
                    }
                    //  LOG.warn("entryId = "+aEntry.getId()+"found diagnosis "+(diagnosis.getRegistrationType()!=null)+"#"+diagnosis.getRegistrationType().getCode()+"#"+diagnosis.getPriority().getCode());
                    if (!isNotNull(aEntry.getMainMkb())
                            && diagnosis.getRegistrationType()!=null && diagnosis.getRegistrationType().getCode().equals("4")
                            && diagnosis.getPriority()!=null && diagnosis.getPriority().getCode().equals("1")) {
                        aEntry.setMainMkb(mkb);
                        theManager.persist(aEntry);
                    } else if ((aEntry.getEntryType().equals(POLYCLINICTYPE) || aEntry.getEntryType().equals(KDPTYPE)) && diagnosis.getPriority()!=null && diagnosis.getPriority().getCode().equals("1")) {
                        aEntry.setMainMkb(mkb);
                        theManager.persist(aEntry);
                    }
                    String dopMkb = ds.getString("addMkb");
                    if (isNotNull(dopMkb)) diagnosis.setDopMkb(dopMkb);
                    theManager.persist(diagnosis);
                    if (isClinical) {clinicalIds.add(diagnosis.getId());}
                }
                if (isCancer){aEntry.setIsCancer(true);theManager.persist(aEntry);}
                if (foundClinical && foundDischarge) { //Если нашли и клинический и выписной диагноз - удаляем клинический.
                    for (Long id:clinicalIds) {
                        theManager.createNativeQuery("delete from EntryDiagnosis where id=:id").setParameter("id",id).executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //LOG.error(e);
        }
    }

    private Map<String, VocMedService> serviceList = new HashMap<>();

    /** Создание списка услуг по записи + делаем главную услугу */ //делаем разово
    private void createServices(E2Entry aEntry) {
        List<EntryMedService> servicesList = aEntry.getMedServices();
        if (servicesList!=null && !servicesList.isEmpty()) {return ;}
        try {
            String operationList = aEntry.getOperationList();
            JSONArray services;
            if (isNotNull(operationList)) {
                services= new JSONArray(operationList);
                //Делаем проверку на роды. Если отделение - обсервационное (27-12-2018), диагнозы входят в список, то создаем услуги
                if (isNotNull(aEntry.getDepartmentId()) && aEntry.getDepartmentId()==212) {
                    if (isNotNull(aEntry.getMainMkb())) {
                        String patologicDs = "O60.1,O60.2,O84.0";
                        String fiziologicDs = "O80.0,O80.1";
                        if (patologicDs.indexOf(aEntry.getMainMkb())>-1) { //Если подходящий диагноз по патологическим родам
                            if ("B01.001.006".indexOf(operationList) ==-1){ //И в списке услуг нет нужной услуги - добавим её
                                JSONObject pat = new JSONObject();
                                pat.put("serviceCode","B01.001.006");
                                pat.put("serviceDate",dateToString(aEntry.getStartDate()));
                                services.put(pat);
                            }
                        } else if (fiziologicDs.indexOf(aEntry.getMainMkb())>-1) {
                            if ("B01.001.009".indexOf(operationList) ==-1){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("serviceCode","B01.001.009");
                                jsonObject.put("serviceDate",dateToString(aEntry.getStartDate()));
                                services.put(jsonObject);
                            }
                        }
                    }
                }
                if (!services.isEmpty()) {

                    String dateFrormat ="yyyy-MM-dd";
                    for (int i=0;i<services.length();i++) {
                        JSONObject service = services.getJSONObject(i);
                        if (!service.has("serviceCode")) {continue;}

                        String code = service.getString("serviceCode");
                        if (services.length()==1) { //Если ТОЛЬКО одна услуга/операция в СЛО, её и считаем главной
                            aEntry.setMainService(code); theManager.persist(aEntry);
                        }
                        //Добавляем услугу, если только она используется в ОМС
                        VocMedService vms ;
                        String workfunction = service.has("workfunctionCode") ? service.getString("workfunctionCode") : null;
                        if (!serviceList.containsKey(code)) {
                            vms =  getEntityByCode(code, VocMedService.class, false);
                            if (vms!=null && !isNotNull(vms.getIsOmc())) {vms=null;}
                            serviceList.put(code,vms);
                        } else {
                            vms =serviceList.get(code);
                        }
                        if (vms!=null){
                            EntryMedService ms =new EntryMedService(aEntry,vms);
                            String serviceDate =service.has("serviceDate") ? service.getString("serviceDate") : null;
                            if (service.has("workfunctionSnils")) ms.setDoctorSnils(service.getString("workfunctionSnils"));
                            if (isNotNull(serviceDate)) {ms.setServiceDate(DateFormat.parseSqlDate(serviceDate,dateFrormat));}

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
            services =new JSONArray(aEntry.getServices());  //Данные об услугах добавляем в список операций. нах разнообразие!
            if (!services.isEmpty()) {
                String dateFrormat ="yyyy-MM-dd";
                for (int i=0;i<services.length();i++) {
                    JSONObject service = services.getJSONObject(i);
                    if (!service.has("serviceCode")) continue;
                    String code = service.getString("serviceCode");
                    if (StringUtil.isNullOrEmpty(code)) continue;
                    String workfunction = service.has("workfunctionCode")?service.getString("workfunctionCode"):null;
                    if ("FINDME_SYSTEM".equals(code)) { //Находим первичный прием по профилю специалиста
                        code = code+"#"+workfunction;
                        serviceList.put(code, findDefaultMedServiceByWorkfunction(workfunction));
                    }
                    if (!serviceList.containsKey(code)) {
                        serviceList.put(code, (VocMedService) getEntityByCode(code, VocMedService.class, false));
                    }
                    EntryMedService ms = new EntryMedService(aEntry,serviceList.get(code));
                    if (service.has("workfunctionSnils")) ms.setDoctorSnils(service.getString("workfunctionSnils"));
                    String serviceDate =service.has("serviceDate")?service.getString("serviceDate"):null;
                    if (isNotNull(serviceDate)) {ms.setServiceDate(DateFormat.parseSqlDate(serviceDate,dateFrormat));}
                    if (isNotNull(workfunction)) {
                        VocE2FondV015 doctor = null;
                        String key = "DOCTOR#"+workfunction;
                        if (!resultMap.containsKey(key)) {
                            List<BigInteger> list = theManager.createNativeQuery("select  v015.id from E2FondMedSpecLink link " +
                                    " left join VocE2FondV015 v015 on v015.id=link.medSpec_id where link.medosWorkFunction=:workfunction")
                                    .setParameter("workfunction",workfunction).getResultList(); //Находим исход случая (V015 PRVS)
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
                    // workfunctionSnils
                    theManager.persist(ms);
                    }
                }
        } catch (Exception e) {
            LOG.error("error creating service = "+aEntry.getOperationList()+"<>"+aEntry.getServices(),e);
        }
    }

    /*Нахождение услуги по умолчанию по специальности врача*/
    private VocMedService findDefaultMedServiceByWorkfunction(String aWorkfunctionCode) {
        String sql = "select v021.defaultmedservice_id from" +
                " E2FondMedSpecLink link " +
                " left join voce2fondv015 v015 on v015.id=link.medspec_id" +
                " left join voce2fondv021 v021 on v021.id=v015.medspecv021_id" +
                " where link.medosworkfunction = :workFunction and v021.defaultmedservice_id is not null";
        List<Object> list = theManager.createNativeQuery(sql).setParameter("workFunction",aWorkfunctionCode).getResultList();
        if (list.isEmpty()) {
            LOG.error("Не найдено услуги по умолчанию для профиля специальности: "+aWorkfunctionCode);
            return null;
        }
        return theManager.find(VocMedService.class,Long.valueOf(list.get(0).toString()));



    }

    /** Создаение полиса для случая */ //запускаем только один раз
    private void makeMedPolicy(E2Entry aEntry) {
        try {
            if (isNotNull(aEntry.getMedPolicyNumber())) {return;} //Если номер полиса проставлен - выходим
            String serviceStream = aEntry.getServiceStream();
            JSONArray medPolicy ;
            if (isNotNull(aEntry.getPolicyMedcaseString())) {
                medPolicy = new JSONArray(aEntry.getPolicyMedcaseString());
            } else {
                medPolicy = new JSONArray(aEntry.getPolicyKinsmanString());
            }

            if (medPolicy.isEmpty()) {
                medPolicy = new JSONArray(aEntry.getPolicyPatientString());
            }
            if (medPolicy.isEmpty()) {
                if (serviceStream==null||serviceStream.equals("OBLIGATORYINSURANCE")){
                    theManager.persist(new E2EntryError(aEntry,"NO_MED_POLICY"));
                }
                return;
            }
            JSONObject policy = medPolicy.getJSONObject(0);
            aEntry.setCommonNumber(policy.getString("commonNumber"));
            aEntry.setInsuranceCompanyTerritory(policy.getString("companyCity"));
            aEntry.setInsuranceCompanyCode(policy.getString("smoCode"));
            aEntry.setInsuranceCompanyName(policy.getString("companyName"));
            aEntry.setInsuranceCompanyOgrn(policy.getString("smoOgrn"));
            String polType = policy.getString("polType");
            aEntry.setMedPolicyType(isNotNull(polType)?polType:"3"); //По умолчанию ставим тип полиса - полис нового образца *28-08-2018
            aEntry.setMedPolicySeries(policy.getString("polSeries"));
            aEntry.setMedPolicyNumber(policy.getString("polNumber"));
            theManager.persist(aEntry);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Не смог получить данные полиса: "+aEntry.getId());
            LOG.error(e);
        }
    }
    /** Находим, является ли КСГ политравмой */
    private static final String[] politravmaMainList = {"S02.7","S12.7","S22.1","S27.7","S29.7","S31.7","S32.7","S36.7","S38.1","S39.6","S39.7","S37.7","S42.7","S49.7","T01.1","T01.8","T01.9","T02.0","T02.1","T02.2","T02.3","T02.4","T02.5","T02.6","T02.7","T02.8","T02.9","T04.0","T04.1","T04.2","T04.3","T04.4","T04.7","T04.8","T04.9","T05.0","T05.1","T05.2","T05.3","T05.4","T05.5","T05.6","T05.8","T05.9","T06.0","T06.1","T06.2","T06.3","T06.4","T06.5","T06.8","T07"};
    private static final String[] politravmaSeconaryList = {"J94.2","J94.8","J94.9","J93","J93.0","J93.1","J93.8","J93.9","J96.0","N17","T79.4","R57.1","R57.8"};
    private VocKsg getPolitravmaKsg(List<String> aMainDisagnosisList, List<String> aOtherDisagnosisList) {
        boolean foundMain=false, foundOther=false;
        for (String main: aMainDisagnosisList) {
            for(String d : politravmaMainList) {
                if (main.equals(d)) {
                    foundMain=true;
                    break;
                }
            }
        }

        if (foundMain) { //Если нашли главный диагноз, ищем сопутствующий
            for (String other: aOtherDisagnosisList) {
                for(String d : politravmaSeconaryList) {
                    if (other.equals(d)) {
                        foundOther=true;
                        break;
                    }
                }
            }

        }
        if (foundMain && foundOther) {
            return getActualVocByClassName(VocKsg.class,null,"code='st29.007'");
        }
        return null;
    }
    /** Нахождение КСГ с бОльшим коэффициентом трудозатрат для случая */
    private HashMap<String, List<BigInteger>> ksgMap = new HashMap<>();
    private VocKsg getBestKSG(E2Entry aEntry, boolean updateKsgIfExist, boolean needPersist) {
        if (aEntry.getEntryType()==null || !aEntry.getEntryType().equals(HOSPITALTYPE)) {return null;}
        if (!updateKsgIfExist && aEntry.getKsg()!=null) {return aEntry.getKsg();} //Не проверяем КСГ у записей с уже найденным КСГ
        if (isNotNull(aEntry.getIsManualKsg())) { return aEntry.getKsg();} //Если стоит признак ручного ввода КСГ, не расчитываем КСГ
        try {
            List<EntryDiagnosis> diagnosisList = getDiagnosis(aEntry);
            if (needPersist && diagnosisList.isEmpty()) {
                E2EntryError error = new E2EntryError(aEntry,"NO_DIAGNOSIS");
                theManager.persist(error);
            }

            String bedType = aEntry.getBedSubType();
            String dopmkb = null;
            boolean isCancer=false;
            for (EntryDiagnosis ed: diagnosisList) {
                if (ed.getPriority().getCode().equals("1")){
                    if(isNotNull(ed.getDopMkb())) {
                        dopmkb=ed.getDopMkb();
                    }
                    isCancer=ed.getMkb().getCode().startsWith("C") || ed.getMkb().getCode().startsWith("D");
                }
            }

            List<String> mainDiagnosis = findDiagnosisCodes(diagnosisList, "3","1"); //Тип регистрации:1,2-при поступлении, 3  - выписной, 4 = клинический
            if (mainDiagnosis.isEmpty()) { //Нет выписного диагноза - возьмем клинический
                mainDiagnosis= findDiagnosisCodes(diagnosisList, "4","1");
            }
            if (mainDiagnosis.isEmpty() && isNotNull(aEntry.getMainMkb())) {
                mainDiagnosis.add(aEntry.getMainMkb());
            }

            if (bedType.equals("1")) { //Проверим - является ли сочетание диагноза политравмой
                VocKsg politravma = getPolitravmaKsg(mainDiagnosis,findDiagnosisCodes(diagnosisList,"4","34"));
                if (politravma!=null) {
                    aEntry.setKsg(politravma);
                   if (needPersist) {
                       theManager.persist(aEntry);
                   }
                    return politravma;
                }
            }

            String ksgAge = findKSGAge(aEntry.getHospitalStartDate(), aEntry.getBirthDate());
            List<String> serviceCodes = new ArrayList<>();
            String cancerDiagnosis = null;
            String cancerDiagnosisSql = "";
            //Если оказано несколько услуг, ищем по всем услугам
            List<EntryMedService> serviceList1 =  aEntry.getMedServices();
            if (serviceList1!=null) {
                for (EntryMedService ms : serviceList1) {
                    serviceCodes.add(ms.getMedService().getCode());
                }
            }
            StringBuilder sql = new StringBuilder();
            sql.append("select gkp.id as id  from grouperksg gk")
                    .append(" left join vocbedsubtype vbst on vbst.id=gk.bedtype_id")
                    .append(" left join GrouperKSGPosition gkp on gkp.ksggrouper_id=gk.id")
                    .append(" where gk.year=:year and vbst.code='").append(bedType).append("'");
            if (!mainDiagnosis.isEmpty()) {

                StringBuilder sb = new StringBuilder();
                for (String d : mainDiagnosis) {
                    if (sb.length() != 0) {
                        sb.append(",");
                    }
                    sb.append("'").append(d).append("'");
                    if ((d.startsWith("C") && Integer.valueOf(d.substring(1,3))<81) || (d.startsWith("D") && Integer.valueOf(d.substring(1,3))<9)) cancerDiagnosis = d;
                }
                if (cancerDiagnosis!=null) { //Костыль по нахождению Д в интервале, TODO переделать на нормальное нахождение диагноза в интервале
                    if (cancerDiagnosis.startsWith("C")) {
                        cancerDiagnosisSql = " or gkp.mainMkb='C00-C80' or or gkp.mainmkb='C.'" ;
                        cancerDiagnosis="C00-C80";
                    } else {
                        cancerDiagnosisSql = "or gkp.mainMkb='D00-D08'";
                        cancerDiagnosis="D00-D08";
                    }

                }
                sql.append(" and ((gkp.mainmkb is null or gkp.mainmkb ='') or gkp.mainmkb in (").append(sb).append(")").append(cancerDiagnosisSql).append(")");
            } else {
                sql.append(" and (gkp.mainmkb is null or gkp.mainmkb ='')");
            }
            sql.append(" and ((gkp.anothermkb is null or gkp.anothermkb ='') ").append(isNotNull(dopmkb) ? " or gkp.anothermkb='"+dopmkb+"')" : ")"); //Ищем по доп. коду

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
           //    LOG.info("sql for best KSG = "+sql.toString());
            List<BigInteger> results;
            String key = mainDiagnosis.hashCode()+"#SQL#"+sql.toString().hashCode(); //bedType+"#"+aEntry.getMainMkb()+"#"+(dopmkb!=null?dopmkb:"");
            //   LOG.warn("sql for ksg = "+sql.toString());
            if (!ksgMap.containsKey(key)) {
                //  LOG.info(key+" not found new sql ="+sql);
                results = theManager.createNativeQuery(sql.toString()).setParameter("year",2019).getResultList();
                ksgMap.put(key,results);
                if (results.isEmpty()) {
                    LOG.warn(key+" Не смог найти КСГ для случая с ИД="+aEntry.getId()+" по запросу: "+sql);
                }
            } else {
                results=ksgMap.get(key);
            }
            double maxKZ = 0.0;
            int maxWeight = 0;
            int weight;
            GrouperKSGPosition pos = null;
            boolean duration = aEntry.getCalendarDays() <= 3;
            GrouperKSGPosition therapicKsgPosition = null;
            GrouperKSGPosition surgicalKsgPosition=null;
            GrouperKSGPosition cancerKsgPosition = null;
            List<GrouperKSGPosition> justServicePositions = new ArrayList<>(); //24.09.18 * Если нашли дорогие позиции КСГ, но выбраи более дешевое КСГ, сохраним информацию для эксперта Аношкина
            for (BigInteger o : results) {
                GrouperKSGPosition ksg = theManager.find(GrouperKSGPosition.class, o.longValue());
                VocKsg k = ksg.getKSGValue();
                weight = 0; //Вес найденного КСГ
                if (isNotNull(ksg.getDopPriznak()) && ksg.getDopPriznak().equals(aEntry.getDopKritKSG())) {weight=6;} else if (isNotNull(ksg.getDopPriznak())) {continue;}
                if (isNotNull(ksg.getSex()) && ksg.getSex().equals(aEntry.getSex())) {} else if (isNotNull(ksg.getSex())) {continue;}
                if (mainDiagnosis.contains(ksg.getMainMKB())) {
                    weight++;
                    if (!isNotNull(ksg.getServiceCode())) { //Находим терапевтичесое КСГ
                        therapicKsgPosition = therapicKsgPosition!=null && therapicKsgPosition.getKSGValue().getKZ()>ksg.getKSGValue().getKZ() ? therapicKsgPosition : ksg;
                    }

                } else if (isNotNull(ksg.getMainMKB()) && isCancer && ("C.".equals(ksg.getMainMKB()) || cancerDiagnosis.equals(ksg.getMainMKB()))) {
                    cancerKsgPosition=ksg;weight++; /*weight=5;*/
                } else if (isNotNull(ksg.getMainMKB())) {
                    continue;
                }

                if (serviceCodes.contains(ksg.getServiceCode())) {
                    weight++;
                    surgicalKsgPosition= surgicalKsgPosition!=null && surgicalKsgPosition.getKSGValue().getKZ()>ksg.getKSGValue().getKZ() ? surgicalKsgPosition : ksg;
                    //Если коронарография и у нас есть диагноз, берем дешевое КСГ! 09-02-2018
                    if ("A16.12.033".equals(ksg.getServiceCode())) {
                        if (mainDiagnosis.contains(ksg.getMainMKB())) {weight=5;}
                    } else if (ksg.getMainMKB().equals("N18.5") || ksg.getMainMKB().equals("N18.4")) {
                        weight=5;
                    }

                } else if (isNotNull(ksg.getServiceCode())) {continue;}
                if (ksg.getAge() != null && ksgAge.indexOf(""+ksg.getAge())>-1) {} else if (ksg.getAge()!=null) {continue;}
                if (ksg.getDuration() != null  && duration) {weight=6;} else if (ksg.getDuration() != null ) {continue;}
       //         if(ksg.getKSGValue().getCode().equals("5")) {weight=7;} //Если нашли 5 КСГ, то берем его! //TODO 01-03-2018
       //         if(ksg.getKSGValue().getCode().equals("st02.003")) {weight=6;} //Если нашли родовое КСГ, то берем его! //TODO 08-02-2018
    //st02.012 , st02.013 круче родов и кесарева
                double currentKZ = k.getKZ();

                if (weight > maxWeight || (weight == maxWeight && currentKZ > maxKZ) || (currentKZ == maxKZ && isNotNull(ksg.getServiceCode()) )) {
                    maxWeight=weight;
                    maxKZ = currentKZ;
                    pos = ksg;
                } else if (k.getIsOperation() && currentKZ>maxKZ){
                        justServicePositions.add(ksg);
                }
            } //Закончили искать лучшее КСГ

            if (pos==null && cancerKsgPosition!=null) {pos=cancerKsgPosition;}
            if (therapicKsgPosition!=null && surgicalKsgPosition!=null) { //Если мы нашли хирургическое КСГ, но есть и терапевтическое, то проверим на исключения
                GrouperKSGPosition exc =  checkIsKsgException(surgicalKsgPosition,therapicKsgPosition);
                if (exc!=null)pos=exc;
            }

            if (pos != null)  {
                VocKsg ksg = pos.getKSGValue();
                aEntry.setKsgPosition(pos);
                if (isNotNull(pos.getMainMKB()))aEntry.setMainMkb(pos.getMainMKB());
                if (isNotNull(pos.getServiceCode()))aEntry.setMainService(pos.getServiceCode());
                if (needPersist) {
                    aEntry.setKsg(ksg);
                    theManager.persist(aEntry);
                    if (!justServicePositions.isEmpty()){
                        StringBuilder err = new StringBuilder("Предполагаемые КСГ: ");
                        for (GrouperKSGPosition k: justServicePositions) {
                            err.append(k.getKSGValue().getCode()).append(" КЗ=").append(k.getKSGValue().getKZ()).append("; ");
                        }
                        theManager.persist(new E2EntryError(aEntry,"MAYBE_OTHER_KSG", err.toString()));
                    }
                }
                return ksg;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
            return null;
        }

    }
    @Deprecated
    private int getYear(Date finishDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(finishDate);
        return calendar.get(Calendar.YEAR);
    }

    /** Проверяем, является ли пара КСГ исключением из случая*/
    //private static final String[] ksgExceptions = {"9#11","9#12","10#11","18#76","18#77","179#173","300#301","207#301","242#245","244#35","271#256"}; //терапевтическая#Хирургическая
    private static final String[] ksgExceptions = {"st02.008#st02.010","st02.008#st02.011","st02.009#st02.010","st04.002#st14.001","st04.002#st14.002","st21.007#st21.001","st34.001#st34.002","st26.001#st34.002","st30.003#st34.006","st30.005#st09.001","st31.017#st31.002"}; //терапевтическая#Хирургическая
    private GrouperKSGPosition checkIsKsgException(GrouperKSGPosition aSurgicalKsgPosition, GrouperKSGPosition aTherapicalKsgPosition) {
        String key = aTherapicalKsgPosition.getKSGValue().getCode()+"#"+aSurgicalKsgPosition.getKSGValue().getCode();
        //  LOG.warn("ekseption.sql="+key);
        for (String str:ksgExceptions) {
            if (str.equals(key)) { //Если есть КСГ по услуге, подаем его
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
        List<String> diagnosisList = new ArrayList<>();
        if (aList.isEmpty()) {return diagnosisList;}
        if (aRegType==null&&aPriority==null) {return diagnosisList;}
        for (EntryDiagnosis diagnosis : aList) {
            if ( //проверить!
                    (aRegType==null || (aRegType.indexOf(diagnosis.getRegistrationType().getCode())>-1))
                            && (aPriority==null || aPriority.indexOf(diagnosis.getPriority().getCode())>-1)
                    ) {
                diagnosisList.add(diagnosis.getMkb().getCode());
            }
        }
        return diagnosisList;
    }


    /** Получаем базовый тариф по случаю +ВМП */
    private HashMap<String, BigDecimal> tariffMap = new HashMap<>();
    private BigDecimal calculateTariff(E2Entry aEntry) {
        String key, sqlAdd;
        String entryType=aEntry.getEntryType();
        String mmYYYY = new SimpleDateFormat("MMyyyy").format(aEntry.getFinishDate());
        if (entryType.equals(HOSPITALTYPE)) {
            if (isNotNull(aEntry.getVMPKind())) { //Если в СЛО есть ВМП, цена = ВМП
                return aEntry.getCost();
            }
            String bedSubType = aEntry.getBedSubType();
            key = HOSPITALTYPE+"#"+bedSubType+"#"+mmYYYY;
            sqlAdd = "stacType_id=" + bedSubType + " and vidSluch_id="+aEntry.getVidSluch().getId();

        } else if (entryType.equals(POLYCLINICTYPE) || entryType.equals(POLYCLINICKDOTYPE) || entryType.equals(KDPTYPE)) {
            String tariffCode=aEntry.getSubType()!=null ? aEntry.getSubType().getTariffCode() : "_NULLENTRYSUBTYPE_";
            key = POLYCLINICTYPE+"#"+tariffCode+"#"+aEntry.getVidSluch().getId()+"#"+mmYYYY;
            sqlAdd=" type.code='"+tariffCode+"' and vidSluch_id="+aEntry.getVidSluch().getId();
        }  else {
            LOG.error("Не могу расчитать тариф для записи с типом: CANT_CALC_TARIFF_"+entryType);
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
    private HashMap<String, BigDecimal> cusmoMap = new HashMap<>();
    public BigDecimal calculateCusmo(String bedSubTypeCode, Long aDepartmentId, Long aProfileId, Date aDate) {
        if (aProfileId==null) {LOG.error("Не указан профиль мед. помощи, невозможно найти КУСмо! NO_PROFILE_FOR_CUSMO"); return null;}
        String key;
        if ("2".equals(bedSubTypeCode)) { //Для дневного стационара возвращаем жестко зашитый коэффициент
            key=E2Enumerator.DAYTIMEHOSP;
            if (!cusmoMap.containsKey(key)) {
                cusmoMap.put(key,new BigDecimal(getExpertConfigValue("DAYTIMEHOSP_CUSMO")));
            }
        } else {
            key = aProfileId+"#"+aDepartmentId+"#"+MONTHYEARDATE.format(aDate);
            if (!cusmoMap.containsKey(key)) {
                StringBuilder sqlAdd = new StringBuilder();
                sqlAdd.append("select distinct cusmo.id from VocCoefficient cusmo")
                        .append(" where cusmo.helpProfile_id='").append(aProfileId).append("' and cusmo.dtype='VocCoefficientLpuLevel' and (cusmo.department_id is null");
                if (aDepartmentId != null && aDepartmentId > 0L) {
                    sqlAdd.append(" or cusmo.department_id='").append(aDepartmentId).append("'");
                }
                sqlAdd.append(")")
                        .append(" and '").append(aDate).append("' between cusmo.startDate and coalesce(cusmo.finishDate,current_date)");
                VocCoefficientLpuLevel cusmo = getActualVocBySqlString(VocCoefficientLpuLevel.class, sqlAdd.toString());
                cusmoMap.put(key, cusmo!=null?cusmo.getValue():null);
            }
        }
        return cusmoMap.get(key);
    }
    private BigDecimal calculateCusmo(E2Entry aEntry) {
        return calculateCusmo(aEntry.getBedSubType(),aEntry.getDepartmentId(),aEntry.getMedHelpProfile()!=null?aEntry.getMedHelpProfile().getId():null,aEntry.getFinishDate());
    }

    /**
     * Считаем цену (тариф, итоговый коэффициент) стационарного случая
     * @param aEntry
     * @return
     */
    private HashMap<String, BigDecimal> hospitalCostMap = new HashMap<>();
    private E2Entry calculateHospitalEntryPrice(E2Entry aEntry) {
        try {
            String key;
            if (isNotNull(aEntry.getVMPKind()) && aEntry.getCost()==null) { //Если есть ВМП и нет цены - цена случая = цене метода ВМП
                key = "VMP#"+aEntry.getVMPKind();
                BigDecimal cost;
                if (!hospitalCostMap.containsKey(key)) {
                    List<BigDecimal> costs = theManager.createNativeQuery("select cost from vockindhighcare where code=:code " +
                            "and :vmpDate between dateFrom and coalesce(dateTo,current_date) and cost is not null")
                            .setParameter("code", aEntry.getVMPKind()).setParameter("vmpDate",aEntry.getFinishDate()).getResultList();
                    if (!costs.isEmpty()) {
                        cost = costs.get(0);
                        hospitalCostMap.put(key,cost);
                    } else {
                        E2EntryError error = new E2EntryError(aEntry,"NO_VMP_METHOD_COST");
                        theManager.persist(error);
                        return aEntry;
                    }
                } else {cost=hospitalCostMap.get(key);}
                aEntry.setCost(cost);
                aEntry.setBaseTarif(cost);
                aEntry.setTotalCoefficient(BigDecimal.ONE);
                aEntry.setCostFormulaString("tarif=T ("+cost+")");
                theManager.persist(aEntry);
            } else { //Если не ВМП, считаем цену как обычно
                VocKsg aKsg = aEntry.getKsg();
                if (aKsg==null){return aEntry;} //Нет КСГ - нечего расчитывать всё остальное
                BigDecimal kz, tarif, cusmo, kslp, km, kpr, kuksg ;
                kz = BigDecimal.valueOf(aEntry.getKsg().getKZ());
                kuksg = getActualKsgUprCoefficient(aKsg,aEntry.getFinishDate());
                tarif = calculateTariff(aEntry);
                cusmo = aKsg.getDoNotUseCusmo() ? BigDecimal.ONE : calculateCusmo(aEntry);
                km = calculateKm();
                kslp = calculateResultDifficultyCoefficient(aEntry);
                kpr = calculateNoFullMedCaseCoefficient(aEntry);
                if (tarif == null || kz == null ||kuksg==null || cusmo == null || km == null || kslp == null || kpr == null ) {
                    String err = "Для случая с ИД=" + aEntry.getId() + " не удалось расчитать цену: Тариф=" + tarif + ", КЗ=" + kz + ", КУксг="+kuksg+", КУСмо=" + cusmo + ", КМ=" + km + ", КСЛП=" + kslp + ", Кпр=" + kpr ;
                    aEntry.setCostFormulaString(err);
                    LOG.error(err);
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
            return aEntry;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
            return aEntry;
        }
        //  return aEntry.getCost();
    }
    /** Нахождение актуального управленческого коэффициента по КСГ и дате визита*/
    private HashMap<String,E2KsgCoefficientHistory> ksgCoefficientMap = new HashMap<>();
    public BigDecimal getActualKsgUprCoefficient(VocKsg aKsg, Date aFinishDate) {
        E2KsgCoefficientHistory coefficientHistory;
        String sql = "select id from E2KsgCoefficientHistory where ksg_id=:ksg and to_date('"+aFinishDate+"','yyyy-MM-dd') between startDate and coalesce(finishDate, current_date)";
        String key = "KSG#COEFF#"+sql.hashCode();
        if (ksgCoefficientMap.containsKey(key)) {
            coefficientHistory=ksgCoefficientMap.get(key);
        } else {
            List<Long> list = theManager.createQuery(sql).setParameter("ksg",aKsg.getId()).getResultList();
            if (list.isEmpty() || list.size()>1) {
                LOG.error(aKsg.getId()+ " найдено _"+list.size()+"_ коэффициентов КСГ(MORE_1_KSG_COEFFICIENT) "+sql);
                return null;
            }
            coefficientHistory = theManager.find(E2KsgCoefficientHistory.class,list.get(0));
        }
        return coefficientHistory.getValue();

    }
    /** Расчитываем коэффициент сложности лечения пациента */
    private HashMap<String,VocE2CoefficientPatientDifficulty> difficultyHashMap = new HashMap<>();
    private void calculatePatientDifficulty(E2Entry aEntry) {
        String entryType = aEntry.getEntryType();
        VocKsg ksg = aEntry.getKsg();
        if (ksg==null || VMPTYPE.equals(entryType)) {return;}
        if(HOSPITALTYPE.equals(entryType) && !aEntry.getBedSubType().equals("1")) { return;}

        //Удалим старые значения КСЛП //TODO оптимизировать!
        if (!theManager.createNativeQuery("select id from E2CoefficientPatientDifficultyEntryLink where entry_id=:id").setParameter("id",aEntry.getId()).getResultList().isEmpty()) {
            theManager.createNativeQuery("delete from E2CoefficientPatientDifficultyEntryLink where entry_id=:id").setParameter("id",aEntry.getId()).executeUpdate();
        }

        //Сложность лечения пациента //TODO нужны справочники
        /**
         +  1*	Сложность лечения пациента, связанная с возрастом (госпитализация детей до 1 года). Кроме КСГ, относящихся к профилю «неонатология» (107-113)	1,1
         +  2*	Сложность лечения пациента, связанная с возрастом (госпитализация детей от 1 до 4)	1,1
         +  3*	Необходимость предоставления спального места и питания законному представителю (дети до 4) 	1,05
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
        List<String> ret = new ArrayList<>(); //Тут все сложности
        long ageDays = (AgeUtil.calculateDays(aEntry.getBirthDate(), aEntry.getStartDate()) + 1);
        if (ageDays<366 && !ksg.getProfile().equals("17")) { //Если меньше года и КСГ не "неонатологическое"
            ret.add("1");
        } else if (ageDays>365 && ageDays<1462) { //Если возраст между годом и 4 годами
            ret.add("2");
        } else if (ageDays>27394) { //Если возраст более 75 лет
            ret.add("4");
        }
        if (isNotNull(aEntry.getHotelServices())) {ret.add("3");}
        Date actualDate = aEntry.getFinishDate();
        //6
        List<EntryDiagnosis> list = getDiagnosis(aEntry);
        List<String> anotherMkb = findDiagnosisCodes(list,null,"3");
        for(String ds: anotherMkb) {
            if (ds.startsWith("E10") || ds.startsWith("E11")) {
                ret.add("6");
                break;
            }
        }

        //12 Парные операции
        List<String> pairOperation = theManager.createNativeQuery("select ''||op.id from VocPairOperations op " +
                "left join vocmedservice vms on vms.id=op.medservice_id where vms.code in (select vms1.code from entrymedservice ems " +
                "left join vocmedservice vms1 on vms1.id=ems.medService_id" +
                " where entry_id=:id and coalesce(finishDate,current_date)>=:actualDate  group by vms1.code having count (ems.medservice_id)>1) ")
                .setParameter("id", aEntry.getId())
                .setParameter("actualDate",actualDate)
                .getResultList();
        if (!pairOperation.isEmpty()) {
            ret.add("11");
        }

        List<EntryMedService> medServiceList = aEntry.getMedServices();
        if (medServiceList!=null && !medServiceList.isEmpty()) {
            StringBuilder sbb = new StringBuilder();
            for (EntryMedService ems: medServiceList) {
                if (sbb.length()>0) {sbb.append(",");}
                sbb.append("'").append(ems.getMedService().getCode()).append("'");
            }
                if (!theManager.createNativeQuery("select vco.id from VocCombinedOperations vco " +
                    " left join vocmedservice vms1 on vms1.id=vco.medservice1_id" +
                    " left join vocmedservice vms2 on vms2.id=vco.medservice2_id" +
                    " where vms1.code in ("+sbb+") and vms2.code in ("+sbb+") and coalesce(vco.finishDate,current_date)>=:actualDate ")
                    .setParameter("actualDate",actualDate)
                    .getResultList().isEmpty()
                    ) {
                ret.add("10");
            }
        }

        E2CoefficientPatientDifficultyEntryLink link;
        //calc 10
        List<E2CoefficientPatientDifficultyEntryLink> difficultyEntryLinks = new ArrayList<>();
        long sluchDuration = aEntry.getBedDays()!=null ? aEntry.getBedDays(): 1;
        long maxDuration = isNotNull(aEntry.getKsg().getLongKsg()) ? 45 : 30;
        if (sluchDuration > maxDuration) { //Если случай лечения больше 30 (45) дней, ищем "10" коэффициент
            BigDecimal value =new BigDecimal(1).add((new BigDecimal(sluchDuration-maxDuration).divide(new BigDecimal(maxDuration),12,RoundingMode.HALF_UP))
                    .multiply(BigDecimal.valueOf(aEntry.getReanimationEntry()!=null ? 0.4 : 0.25)));
            link = new E2CoefficientPatientDifficultyEntryLink();
            link.setEntry(aEntry);
            link.setDifficulty((VocE2CoefficientPatientDifficulty)getActualVocByClassName(VocE2CoefficientPatientDifficulty.class,null," code='9'"));
            link.setValue(value.setScale(2,RoundingMode.HALF_UP));
            difficultyEntryLinks.add(link);
        }
        //Пришло время сохранять все сложности пациента
        if (!ret.isEmpty()) {
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
                difficultyEntryLinks.add(link);
            }
        }
        if (!difficultyEntryLinks.isEmpty()) {
            aEntry.setPatientDifficulty(difficultyEntryLinks);
            theManager.persist(aEntry);
        }
    }
    /** Считаем коэффициент сложности лечения пациента */
    public BigDecimal calculateResultDifficultyCoefficient(E2Entry aEntry) {
        BigDecimal one = new BigDecimal(1);
        if(aEntry.getEntryType().equals(HOSPITALTYPE)&&!aEntry.getBedSubType().equals("1")) { return one;}
        List<E2CoefficientPatientDifficultyEntryLink> list = aEntry.getPatientDifficulty();
        BigDecimal ret = new BigDecimal(1);
        if (list!=null && !list.isEmpty()) { //Нет КСЛП - возвращаем 1.
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
        if (ret==null) LOG.info("finish calc difficult.id="+aEntry.getId()+", value is NULL!! = ");
        return ret;
    }

    /** Расчет цены поликлинического случая */
    private HashMap<String,VocE2PolyclinicCoefficient> polyclinicCasePrice = new HashMap<>();
    public BigDecimal calculatePolyclinicEntryPrice(VocE2VidSluch aVidSluch, Date aFinishDate, VocE2MedHelpProfile aMedHelpProfile) {
     //   BigDecimal one = new BigDecimal(1);
        VocE2BaseTariff baseTariff = getActualVocByClassName(VocE2BaseTariff.class,aFinishDate,"vidSluch_id="+aVidSluch.getId());
        if (baseTariff ==null) {LOG.warn("Cant calc polyclinic tariff: "+aVidSluch.getCode()+" <> "+aFinishDate);return new BigDecimal(0);}
      //  String sql = "";
        List<VocE2PolyclinicCoefficient> coefficients ;
        //находим Кз
        coefficients = theManager.createQuery(" from VocE2PolyclinicCoefficient where vidSluch_id=:vidSluchId and :actualDate between startDate and coalesce(finishDate,current_date)")
                .setParameter("vidSluchId",aVidSluch.getId()).setParameter("actualDate",aFinishDate).getResultList();
        BigDecimal coef = new BigDecimal(1);
        if (!coefficients.isEmpty()) {
            for (VocE2PolyclinicCoefficient coefficient: coefficients) {
                coef = coef.multiply(coefficient.getValue());
            }
        }
        BigDecimal tariff = baseTariff.getValue();
        return tariff.multiply(coef);
    }
    private void calculatePolyclinicEntryPrice(E2Entry aEntry) {
        BigDecimal one = new BigDecimal(1);
        VocE2EntrySubType subType =aEntry.getSubType();
        String tariffCode = subType.getTariffCode();
        if (tariffCode ==null) {/*LOG.warn("Cant calc polyclinic tariff: "+aEntry.getId()+"<>"+subType.getId()+""+subType.getCode());*/return;}
        Long profileId = aEntry.getMedHelpProfile()!=null?aEntry.getMedHelpProfile().getId():null;
        if (profileId==null ) {/*LOG.error("Нет профиля для определения цены: "+aEntry.getId()+"<>"+aEntry.getServiceStream());*/return;}
        String key;
        String sql = "";
        VocE2PolyclinicCoefficient coefficient = null;
        boolean isKdo =isNotNull(aEntry.getIsDiagnosticSpo()) || aEntry.getEntryType().equals(KDPTYPE);
        boolean isEmergency = isNotNull(aEntry.getIsEmergency());
        boolean isMobilePolyclinic = isNotNull(aEntry.getIsMobilePolyclinic());
        //находим Кз
        if (isKdo) { //Находим Кз обращения
            key="KZ#KDO#";
        } else {
            key = isEmergency ? "KZ#EMERGENCY##" : "KZ#" + profileId + "#" + tariffCode;
        }
        sql ="profile_id="+profileId+" and entryType.tariffCode='"+tariffCode+"'";
        key+=sql;
        if (!polyclinicCasePrice.containsKey(key)) {
            //LOG.info("FIND_KZ. key = "+key);
            coefficient= getActualVocByClassName(VocE2PolyclinicCoefficient.class,aEntry.getFinishDate(),sql);
            if (coefficient==null){LOG.warn("НЕ смоег найти коэффициента: "+sql);}
            polyclinicCasePrice.put(key,coefficient);
        }

        coefficient = polyclinicCasePrice.get(key);
        BigDecimal kz = coefficient!=null?coefficient.getValue():one;
        boolean needToFindKp = true;
        //Находим Кп/Кпд
        sql = "";
        if (isKdo) { //находим КДО
            sql+=" and isDiagnosticSpo='1'";
        } else if (isEmergency) { //Неотложна
            needToFindKp=false;
            sql+=" and 1=2";
        } else { //поликлиника (мобильная ., консультативная)
            if (isMobilePolyclinic) {
                sql += " and isMobilePolyclinic='1'";
            }
            if (isNotNull(subType.getIsConsultation())) {
                sql += " and isConsultation='1'";
            }
        }
        sql="profile_id="+profileId+sql+" and entryType is null ";
        key ="KP#"+sql; //Находим коэффициент для конс. поликлиники
        if (needToFindKp) {
            if (!polyclinicCasePrice.containsKey(key)) {
                coefficient=getActualVocByClassName(VocE2PolyclinicCoefficient.class,aEntry.getFinishDate(),sql);
                polyclinicCasePrice.put(key,coefficient);

            } else {
                coefficient = polyclinicCasePrice.get(key);
            }
        } else {
            coefficient = null;
        }
        BigDecimal kp = coefficient!=null?coefficient.getValue():one;

        BigDecimal tariff = calculateTariff(aEntry);
        BigDecimal km = calculateKm();
        String costFormula = "Тариф=" + tariff + ", КЗ=" +kz + ", Кп(Кпд)="+kp+", КМ=" + km;
        if (tariff==null||kz==null||km==null) {
            LOG.warn("Не удалось расчитать цену случая");
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
        return BigDecimal.valueOf(0.3);
    }

    /**
     * Создаем записи в заполнении из sql запроса
     *
     * @param aResultSet
     * @param aParamMap
     * @throws ParseException
     */
    private HashMap<String, Method> methodMap = new HashMap<>();
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
            //  List<E2Entry> entries = new ArrayList<E2Entry>();
            int j=0;
            while (aResultSet.next()) { // Для каждой строки (кортежа)
                j++;
                if (j%100==0) {LOG.info("Creating "+j+" records");}
                entity = new E2Entry();
                Class aClass = entity.getClass();
                for (int i = 0; i < rowsLength; i++) { //Для каждого столбца находим геттер
                    String key = "GETTER#"+fields[i];
                    Method getterMethod = null;
                    if (!methodMap.containsKey(key)) {
                        try {
                            getterMethod = PropertyUtil.getGetterMethodIgnoreCase(aClass, fields[i]);
                            methodMap.put(key,getterMethod);
                        } catch (Exception e) { LOG.warn("Не найдено поле с именем "+fields[i]);    methodMap.put(key,null);continue;
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
                        LOG.error("Невозможно получить объект из карты с ключем = " + entry.getKey());
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
                if (entity.getEntryType().equals(POLYCLINICTYPE)||entity.getEntryType().equals(POLYCLINICKDOTYPE)) {
                    makeCheckEntry(entity,false, false);
                }
            }
            LOG.info("Success!");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
        }
        isCheckIsRunning=false;
    }
    /*Список диагнозов, с которыми разрешена подача обсервационного отделения менее 5 дней*/
    private void fillChildBirthMkbs() {
        childBirthMkb.add("O14.1");
        childBirthMkb.add("O34.2");
        childBirthMkb.add("O36.3");
        childBirthMkb.add("O36.4");
        childBirthMkb.add("O42.2");
    }
    /** При формировании заполнения выполняем расчет КСГ, объединение случаев, повторное нахождение КСГ */
    private void checkListEntryFirst(E2ListEntry aListEntry, List<E2Entry> aEntryList, long aMonitorId) {
        Long listEntryId = aListEntry.getId();
        try {
            java.util.Date startStartDate = new java.util.Date();
            LOG.info("start checkListEntryFirst. id="+listEntryId);
            if (isCheckIsRunning) {
                LOG.warn("Проверка уже запущена, ничего не проверяем ALREADY_STARTED");
                //  return;
            }
            isCheckIsRunning=true;
            if (aEntryList==null) {
                aEntryList = theManager.createNamedQuery("E2ListEntry.findAllEntries").setParameter("list",aListEntry).getResultList();
            }
            if (aEntryList.isEmpty()) {LOG.warn("Случаев для проверки не найдено NO_CASES ");return;}

            String listEntryCode =aListEntry.getEntryType().getCode();
            // theMonitorService.acceptMonitor(aMonitorId, "Расчет цены случаев в звполнении") ;
            IMonitor monitor = theMonitorService.startMonitor(aMonitorId,"Пересчет случаев в заполнении",aEntryList.size());
            monitor.advice(1);
            if (listEntryCode.equals(HOSPITALTYPE) || listEntryCode.equals(HOSPITALPEREVODTYPE)) {
                int i=0;
                LOG.info("Приступаем к нахождению лучшего КСГ. START_FIND_BESK_KSG. list_size="+aEntryList.size());
                monitor.setText("Приступаем к нахождению лучшего КСГ. START_FIND_BESK_KSG. list_size="+aEntryList.size());
                StringBuilder entriesId = new StringBuilder();
                for (E2Entry entry : aEntryList) { //Найдем лучшее КСГ
                    entriesId.append(",").append(entry.getId());
                    i++;
                    if (i%100==0) {
                        if (isMonitorCancel(monitor,"Проверяем стационар. Проверено случаев: "+i)) return;
                    }
                    makeCheckEntry(entry,false, false);
                }
                if (isMonitorCancel(monitor,"Закончили первичную проверку случаев.FINISH_FIRST_CHECK")) return;

                //теперь объединим все случаи объединим все случаи (только для стационара)
                List<BigInteger> hospitalIds = theManager.createNativeQuery("select externalparentid from e2entry " +
                        " where id in ("+entriesId.substring(1)+") and listentry_id=" + listEntryId  + " and (isDeleted is null or isDeleted='0') and (isUnion is null or isUnion='0') group by externalparentid having count(externalparentid)>1").getResultList();//Находис все СЛС, в которых больше 1 СЛО
                i=0;
                isMonitorCancel(monitor,"Приступаем к объединению случаев. START_UNION");
                fillChildBirthMkbs();
                for (BigInteger hospId : hospitalIds) {
                    i++;
                    if (i%100==0) {
                        if (isMonitorCancel(monitor,"Идет объединение случаев: "+i))return;
                    }
                    unionHospitalMedCase(listEntryId , hospId.longValue());
                }
                LOG.info("Объединение случаев завершено.FINISH_UNION");
                isMonitorCancel(monitor,"Проверяем КСГ после объединения случаев.2ND_CHECK_KSG");
                i=0;
                for(E2Entry entry : aEntryList) {
                    i++;
                    if (i%100==0) {
                        if (isMonitorCancel(monitor,"Находим лучшее КСГ-2 после объединения случае. Проверено: "+i))return;
                    }
                    //Теперь снова находим КСГ, расчитываем цену и коэффициенты
                    if (!entry.getServiceStream().equals("COMPLEXCASE")) {
                        makeCheckEntry(entry,true, true);
                    }
                }
            } else if (listEntryCode.equals(POLYCLINICTYPE) || listEntryCode.equals(POLYCLINICKDOTYPE)) {
                //Проверка поликлинических случаев
                if (listEntryCode.equals(POLYCLINICTYPE)) {
                    monitor.setText("Удаляем дубли в поликлинике");
                    deletePolyclinicDoubles(listEntryId ); //Удалим дубли при первой проверке
                }
                aEntryList = theManager.createNamedQuery("E2ListEntry.findAllEntries").setParameter("list",aListEntry).getResultList();
                int i=0;
                LOG.info("POL_START_Поликлиника. Приступаем к нахождению цены и проставлению полей фонда.");
                if (isMonitorCancel(monitor,"POL_START_Поликлиника. Приступаем к нахождению цены и проставлению полей фонда.")) return;
                for(E2Entry entry : aEntryList) {
                    i++;
                    if (i%100==0) {
                       if (isMonitorCancel(monitor,"Проверяем записи по поликлинике: "+i))return;
                    }
                    makeCheckEntry(entry,false, true);
                }
                if (isMonitorCancel(monitor,"Поликлиника. Закончили нахождение цены и проставление полей фонда, приступаем к объединению случаев."+(System.currentTimeMillis()-startStartDate.getTime())/60000)) return;
                if (listEntryCode.equals(POLYCLINICTYPE)) {
                    Boolean isGroupSpo = getExpertConfigValue("ISGROUPSPO","0").equals("1");
                    unionPolyclinicMedCase(listEntryId ,null,isGroupSpo);
                } else if (listEntryCode.equals(POLYCLINICKDOTYPE)) {
                    monitor.setText("Объединяем КДО");
                    unionPolyclinicKdoMedCase(listEntryId,aEntryList);
                }
                monitor.setText("Приступаем к проверке перекрестных случаев.");
                deleteCrossSpo(aListEntry);
                monitor.setText("Закончили проверять поликлинику.");
            } else if (listEntryCode.equals(KDPTYPE))  { //Проверяем КДП (как обращение)
                findDiagnosticVisits(aEntryList);
            } else if (listEntryCode.equals(EXTDISPTYPE)) { //Пришло время делать ДД
                LOG.info("Create DD");
                int i=0;
                for(E2Entry entry : aEntryList) {
                    i++;
                    if (i%100==0) {
                       if (isMonitorCancel(monitor,"Проверяем записи по доп. диспансеризации: "+i)) return;
                    }
                    makeCheckEntry(entry,false, true);
                }
            }
            long minutes = (System.currentTimeMillis()-startStartDate.getTime())/60000;
            LOG.info("Время выполнения проверки (минут) TOTAL_TIME = "+minutes);
            monitor.finish("Завершено. Время выполнения проверки (минут) TOTAL_TIME = "+minutes);
        } catch (Exception e) {
            e.printStackTrace();
           LOG.error(e);
        }
        Long currentTime = System.currentTimeMillis();
        aListEntry.setCheckDate(new java.sql.Date(currentTime));
        aListEntry.setCheckTime(new java.sql.Time(currentTime));
        theManager.persist(aListEntry);
        isCheckIsRunning=false;
    }

    /**Список актуальный на сегодняшний день КДП*/
    private List<VocDiagnosticVisit> getActualKdps() {
        return theManager.createNamedQuery("VocDiagnosticVisit.getActualKdps").getResultList();
    }

    /** Оставляем в заполнении только КДП*/
    private void findDiagnosticVisits(List<E2Entry> aListEntry) {
        List<VocDiagnosticVisit> kdps = getActualKdps();
        VocDiagnosticVisit bestKdp;
        List<E2Entry> emergency = new ArrayList<>();
        VocE2FondV008 medHelpKind = getEntityByCode("13",VocE2FondV008.class,false); //
        VocE2FondV009 fondResult = getEntityByCode("301",VocE2FondV009.class,false); //
        VocE2FondV012 fondIshod = getEntityByCode("303",VocE2FondV012.class,false); //
        for (E2Entry entry : aListEntry) {
            bestKdp = null;
            List<String> medServiceList = entry.getMedServicesCodes();

            BigDecimal weigth = BigDecimal.ZERO;

            entry.setMedHelpKind(medHelpKind);
            entry.setFondResult(fondResult);
            entry.setFondIshod(fondIshod);
            for (VocDiagnosticVisit kdp: kdps) {
                if (medServiceList.containsAll(kdp.getListCodes()) && kdp.getCost().compareTo(weigth)>0) {
                    String medServiceListString = kdp.getMedServicesList();
                    if (isNotNull(medServiceListString)) {
                        for (String ms : medServiceList) {
                            if (medServiceListString.indexOf(","+ms+",")>-1) {
                                bestKdp = kdp;
                                weigth = kdp.getCost();
                                break;
                            }
                        }
                    } else {
                        bestKdp = kdp;
                        weigth = kdp.getCost();
                    }
                }
            }
            if (bestKdp!=null) {
                VocE2FondV015 profile = bestKdp.getProfile();
                entry.setKdpVisit(bestKdp);
                entry.setFondDoctorSpec(profile);
                entry.setFondDoctorSpecV021(profile.getMedSpecV021());
                entry.setMedHelpProfile(profile.getPolicProfile());
                entry.setFondIshod(fondIshod);
                for (EntryMedService ems : entry.getMedServices()) {
                    if (ems.getServiceDate().after(entry.getFinishDate())) entry.setFinishDate(ems.getServiceDate());
                    if (ems.getSpeciality()!=null && ems.getSpeciality().equals(profile)) {
                        entry.setDoctorSnils(ems.getDoctorSnils());
                        //break;
                    }
                }
                theManager.persist(entry);
                makeCheckEntry(entry,true,true);
            } else {
                theManager.persist(entry);
                emergency.add(entry);
            }
        }
        if (!emergency.isEmpty()) {
            makeEmergencyEntry(emergency);
        }
    }

    /** Создаем случаи НМП в поликлинике для отказных госпитализаций*/
    private void makeEmergencyEntry(List<E2Entry> aEntryList) {
        LOG.info("Создаем НМП, всего случаев = "+aEntryList.size());
        VocE2EntrySubType subType = getEntityByCode("CONS_POL_EMERG_POLYCLINIC",VocE2EntrySubType.class,false);
        if (subType == null) {
            LOG.warn("Нет типа записи для создания НМП");
            return;
        }

        VocE2FondV006 medHelpUsl = subType.getUslOk();
        VocE2VidSluch vidSluch = subType.getVidSluch();
        VocE2FondV025 visitPurpose = subType.getVisitPurpose();
        VocE2FondV010 idsp = getEntityByCode("41",VocE2FondV010.class,false); //
        boolean isFirst;
        String polyclinic = POLYCLINICTYPE;
        for (E2Entry entry : aEntryList) {
            entry.setDoNotSend(true); //По умолчанию - НМП отмечаем как брак. Хороший НМП отметим позже.
            isFirst=true;
            List<EntryMedService> services = entry.getMedServices();
            List<EntryDiagnosis> diagnoses = entry.getDiagnosis();
                for(EntryMedService service : services) {
                    VocE2FondV015 spec = service.getSpeciality();
                    if (service.getMkb()!=null && spec!=null) { //Если в услуге есть врач и диагноз
                        EntryDiagnosis ed = null;
                        for (EntryDiagnosis d: diagnoses) { //Смотрим, если диагноз совпадает с диагнозом в услуге.
                            VocIdc10 mkb = d.getMkb();
                            if (mkb.equals(service.getMkb())) { //Если диагнозы сходятся - то вот тут помечаем entry как хороший НМП
                                ed = d;
                                break;
                            }
                        }
                        if (ed!=null) { // В случае есть подходящий диагноз!
                            if (!isFirst) {
                                entry = cloneEntity(entry); //Если 2 и более дненик с диагнозом
                                diagnoses.remove(ed);
                                ed.setEntry(entry);
                                theManager.persist(ed);
                            }
                            entry.setDoNotSend(ed.getMkb().getCode().startsWith("Z"));
                            entry.setIsEmergency(true);
                            entry.setWorkPlace(polyclinic);
                            entry.setSubType(subType);
                            entry.setMedHelpUsl(medHelpUsl);
                            entry.setVidSluch(vidSluch);
                            entry.setVisitPurpose(visitPurpose);
                            entry.setIDSP(idsp);
                            entry.setMainMkb(service.getMkb().getCode());
                            entry.setEntryType(polyclinic);
                            entry.setDoctorSnils(service.getDoctorSnils());
                            entry.setFondDoctorSpec(spec);
                            entry.setFondDoctorSpecV021(spec.getMedSpecV021());
                            entry.setMedHelpProfile(spec.getPolicProfile());
                            theManager.persist(entry);
                            makeCheckEntry(entry,false,true);
                            isFirst=false;
                        }
                    }
                }
            if (isFirst) { //Не нашли подходящих диагнозов/услуг.
                theManager.persist(entry);
            }
        }
    }

    /**Выводим сообщение в монитор. Возвращаем - отменен ли монитор*/
    private boolean isMonitorCancel(IMonitor aMonitor, String aMonitorText) {
        aMonitor.setText(aMonitorText);
        LOG.info(aMonitorText);
        if (aMonitor.isCancelled()) {
            aMonitor.setText("Проверка прервана пользователем");
            LOG.warn("Проверка прервана пользователем!");
            return true;
        }
        return false;
    }

    /** Проставляем тип записи (стационар, ВМП, поликлиника, подушевое финансирование, __ИНОГОРОДНИЕ__ */
    private void setEntryType(E2Entry aEntry, String aCode) {
        if (aCode.equals(HOSPITALTYPE) && isNotNull(aEntry.getVMPKind())) {
            aCode=VMPTYPE;
        } else if (aCode.equals(HOSPITALPEREVODTYPE)) {
            aCode=HOSPITALTYPE;
        } else if (aCode.equals(EXTDISPTYPE)) {
            aCode=EXTDISPTYPE;
        } else if (aCode.equals(POLYCLINICKDOTYPE)) {
            aCode=POLYCLINICKDOTYPE;
        }
        if (isNotNull(aEntry.getInsuranceCompanyCode())) {aCode+="_INOG";} //Если код страх. компании не пустой - иногородний.
        aEntry.setEntryType(aCode);
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
                LOG.warn("Не удалось найти настройку с ключем "+aParameterName+", возвращаю значение по умолчанию");
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
            case Types.OTHER:
                aClass=String.class;
                break;
            default:
                throw new IllegalStateException("can't find preobrazovanie for type "+aType);
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
        LOG.warn("SQL file = " + resourceFile);
        if (resourceFile.exists() && resourceFile.isFile()) {

            try (BufferedReader reader = new BufferedReader(new FileReader(resourceFile))) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(" ").append(line);
                    line = reader.readLine();
                }
                return sb.toString();
            } catch (Exception e) {
                LOG.error("Не удалось обнаружить файл!",e);
            }
        } else {
            LOG.error("NO_FILE_ Не удалось найти ресурс с именем: " + aFilename + "  в папке " + url);
        }
        return null;
    }

    private Statement createStatement() throws NamingException, SQLException {

        DataSource ds = ApplicationDataSourceHelper.getInstance().findDataSource();
        Connection connection = ds.getConnection();
            return connection.createStatement();
    }

    private String toSQlDateString(Date aDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return " to_date('" + format.format(aDate) + "','dd.MM.yyyy') ";
    }

    public  <T> T getActualVocBySqlString(Class aClass, String aSql) {
        List<Object> list = theManager.createNativeQuery(aSql).getResultList();
        if (list.isEmpty()) {
            LOG.error("Не удалось найти действующее значение=0 справочника " + aClass.getCanonicalName() + " с условием " + aSql);
            return null;
            //throw new IllegalStateException("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием " + aSql);
        }
        if (list.size() > 1) {
            LOG.error(" с условием " + aSql + " найдено несколько>1 действующих значений справочника " + aClass.getCanonicalName());
            return null;
        }
        return (T) theManager.find(aClass, Long.valueOf(list.get(0).toString()));

    }

    public <T> T getActualVocByClassName(Class aClass, Date aActualDate, String aSqlAdd) {
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
            list = theManager.createQuery(sql).getResultList();
        } else {
            throw new IllegalStateException("Необходимо указать дату актуальности либо другое условие");
        }
        if (list.isEmpty()) {
            LOG.error("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием "+sql);
            return null;
            //throw new IllegalStateException("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием "+sql);
        } else if (list.size() > 1) {
            LOG.error("Найдено несколько действующих значений справочника " + aClass.getCanonicalName()+" с условием "+sql);
            return null;
            //throw new IllegalStateException("Найдено несколько действующих значений справочника " + aClass.getCanonicalName()+" с условием "+sql);
        }
        return list.get(0);

    }

    private String getJbossConfigValue(String aConfigName, String aDefaultValue) {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get(aConfigName, aDefaultValue);

    }

    /** Считаем районнный коэффициент  (Км) */

    private BigDecimal calculateKm() {
        //String ret = getExpertConfigValue("RAYON_COEFFICIENT"); //Коэффициент зашит в настройках ЛПУ
        //return BigDecimal.valueOf(Double.valueOf(ret));
        return BigDecimal.ONE; //TODO пока оставим так для производительности
    }

    /** Расчет причины неполной оплаты случая (коэффициент прерванного случая) */

    /**
     * Указывается причина, по которой услуга не оказана или оказана не в полном объёме.
     1 – документированный отказ больного,
     2 – медицинские противопоказания,
     3 – прочие причины (умер, переведён в другое отделение и пр.)
     4 – ранее проведённые услуги в пределах установленных сроков.
     */
    private static List<String> ksgFullPaymentChildsList = new ArrayList<>();

    private BigDecimal calculateNoFullMedCaseCoefficient (E2Entry aEntry) { //Считаем коэффициент Кпр.+    //  LOG.info("start calculateNoFullMedCaseCoefficient");
        String  npl = aEntry.getNotFullPaymentReason();
        BigDecimal ret = new BigDecimal(1); //По умолчанию - полный случай
        //     String[] resultData = aEntry.getResult().split("#",-1);
        boolean isPrerSluch = false;
        // boolean shortCase = false;
        if (aEntry.getFondResult() == null) {
            return ret;
        }
        String result = aEntry.getFondResult().getCode();
        if (aEntry.getDepartmentId() == 182 && !result.equals("107") && !result.equals("108") && !result.equals("102")) { //Если патология, если НЕ выписан по желанию ЛПУ или желанию пациента, Кпр=1 * 03.10.2018 Результат - Не перевод в другое ЛПУ
            return ret;
        }
        String deadResult = "105,106,205,206";
        String otherLpuResult = "102,103,202,203";
        String lpuLikeResult = "108,208";
        String patientLikeResult = "107,207";
       ksgFullPaymentChildsList.add("st17.001");ksgFullPaymentChildsList.add("st17.002");//при переводе детей в другое ЛПУ не считаем прерванным случаем *30-05-2018
        VocKsg ksg = aEntry.getKsg();
        if (otherLpuResult.indexOf(result) > -1) { //Переведен в другой стационар
            isPrerSluch = ksg == null || !ksgFullPaymentChildsList.contains(ksg.getCode());
        } else if (deadResult.indexOf(result) > -1 || patientLikeResult.indexOf(result) > -1 || lpuLikeResult.indexOf(result) > -1) { //выписан по желанию ЛПУ
            isPrerSluch = true;
        }  //Плановая выписка
        if (!isPrerSluch && aEntry.getCalendarDays() < 4) {  //Если плановая выписки и длительность случая менее 4 дней. //28-02-2018 4 целых дня.
            isPrerSluch =  ksg == null || !isNotNull(ksg.getIsFullPayment());
        }
        if (isPrerSluch) {
            // если прерванный случай - ставим причину неполной оплаты
            if (ksg.getIsOperation()) { //Если у КСГ признак "операционного"
                ret = BigDecimal.valueOf(aEntry.getCalendarDays() < 4 ? 0.85 : 0.9);
            } else {
                ret = BigDecimal.valueOf(aEntry.getCalendarDays() < 4 ? 0.5 : 0.75);
            }
/*            if (planDischarge && ksg.getCode().equals("233")) { //Если политравма и есть любая операция, то Кпр=1 *07.05.2018 //убрали 22-10-2019
                if (!theManager.createNativeQuery("select id from entrymedservice where entry_id=:id").setParameter("id",aEntry.getId()).getResultList().isEmpty()) {
                    ret = BigDecimal.valueOf(1);
                }
            }*/
        }

        if (isNotNull(npl) && !npl.equals("0")) {
            ret=BigDecimal.valueOf(ksg.getIsOperation() ? 0.9 : 0.3);
        }

        if (!isNotNull(npl)) aEntry.setNotFullPaymentReason("0");
        aEntry.setIsBreakedCase(isPrerSluch);
        theManager.persist(aEntry);
        return ret.setScale(2, RoundingMode.HALF_UP);
    }

    private HashMap<String, Object> resultMap = new HashMap<>(); //результат госпитализации
    private void calculateFondField(E2Entry aEntry, boolean  forceUpdate) { //Расчитываем поля для подачи в ОМС RSLT, ISHOD, PRVS
        String key;
        StringBuilder sb;
        String entryType = aEntry.getEntryType();
        String bedSubType = aEntry.getBedSubType();
        List<BigInteger> list;
        Date actualDate = aEntry.getFinishDate();
        boolean stacCase = entryType.equals(HOSPITALTYPE) || entryType.equals(VMPTYPE);
        boolean vmpCase = entryType.equals(VMPTYPE);
        boolean polyclinicCase = entryType.equals(POLYCLINICTYPE) || entryType.equals(POLYCLINICKDOTYPE);
        boolean polyclinicKdoCase = entryType.equals(POLYCLINICKDOTYPE);
        boolean extDispCase = entryType.equals(EXTDISPTYPE);
        boolean kdpCase = entryType.equals(KDPTYPE);
        if (!isNotNull(aEntry.getResult())) {theManager.persist(new E2EntryError(aEntry,"NO_RESULT"));return;}
        String[] dischargeData = aEntry.getResult().split("#", -1); //vho.code||'#'||vrd.code||'#'||vhr.code
        if (kdpCase && forceUpdate) {
            VocDiagnosticVisit kdp = aEntry.getKdpVisit();
            if (kdp == null) {return;}
            VocE2FondV015 profile = kdp.getProfile();
            aEntry.setMedHelpProfile(profile.getPolicProfile());
            aEntry.setFondDoctorSpecV021(profile.getMedSpecV021());
            aEntry.setFondDoctorSpec(profile);
        }

        if (stacCase) {   //Расчет профиля мед. помощи по профилю коек для стационара
            if (aEntry.getMedHelpProfile()==null||forceUpdate) {
                aEntry.setMedHelpProfile(getProfileByBedType(aEntry));
            }
        }
        //Расчитывает Специальность лечащего врача/ врача, закрывшего талон (v015) . UPD 18-07-2018 * Специальность врача по справочнику V021
        if (aEntry.getFondDoctorSpecV021() == null || forceUpdate) {
            if (stacCase && aEntry.getMedHelpProfile() != null && aEntry.getMedHelpProfile().getMedSpec() != null) {  /* от 09-02-2018 Если у профиля мед. помощи указана специальность врача, указываем ее. Только для стационара */
                aEntry.setFondDoctorSpec(aEntry.getMedHelpProfile().getMedSpec());
            } else if (isNotNull(aEntry.getDoctorWorkfunction())) {
                String doctorWorkFunction = aEntry.getDoctorWorkfunction();
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
                        LOG.error("can't find DOCTOR = " + doctorWorkFunction + "____  find sql string = " + sb);
                    }
                    resultMap.put(key, list.isEmpty() ? null : theManager.find(VocE2FondV015.class, list.get(0).longValue()));
                }
                VocE2FondV015 doctor = (VocE2FondV015) resultMap.get(key);
                if (doctor!=null) aEntry.setFondDoctorSpec(doctor);

            }
            if (aEntry.getFondDoctorSpec()!=null && aEntry.getFondDoctorSpec().getMedSpecV021()!=null) {
                aEntry.setFondDoctorSpecV021(aEntry.getFondDoctorSpec().getMedSpecV021());
            } else if (aEntry.getMedHelpProfile()!=null && aEntry.getMedHelpProfile().getMedSpecV021()!=null) {
                aEntry.setFondDoctorSpecV021(aEntry.getMedHelpProfile().getMedSpecV021());
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
                            LOG.error("can't find RSLT = " + aEntry.getResult() + "____ result find sql string = " + sb);
                        }
                    }
                    resultMap.put(key, list.isEmpty() ? null : theManager.find(VocE2FondV009.class, list.get(0).longValue()));
                }
                if (resultMap.get(key)!=null) aEntry.setFondResult((VocE2FondV009) resultMap.get(key));
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
                        LOG.error("can't find ISHOD = " + aEntry.getResult() + "____ ishod find sql string = " + sb);
                    }
                    resultMap.put(key, list.isEmpty() ? null : theManager.find(VocE2FondV012.class, list.get(0).longValue()));
                }
                if (resultMap.get(key) !=null) aEntry.setFondIshod((VocE2FondV012) resultMap.get(key));
            }
            calculatePatientDifficulty(aEntry); //Запустим расчет сложности лечения пациента



            //Вид медицинской помощи (для расчета нужен профиль МП)
            String v008Code = vmpCase ? "32" : "31";
            if (aEntry.getSubType()!=null&&aEntry.getSubType().getCode().equals("POLDAYTIMEHOSP")) {
                v008Code=aEntry.getMedHelpProfile().getProfileK().equals("97") ? "12" : "13"; // TODO = переделать
            }
            key = "V008#"+v008Code;
            if (!resultMap.containsKey(key)) {
                resultMap.put(key,getActualVocByClassName(VocE2FondV008.class, actualDate,"code='"+v008Code+"'"));
            }
            aEntry.setMedHelpKind((VocE2FondV008)resultMap.get(key));
/*            final String sqlAdd = "code='"+v008Code+"'"; //4 джей босс так не умеет
            aEntry.setMedHelpKind((VocE2FondV008)resultMap.computeIfAbsent(key,k->getActualVocByClassName(VocE2FondV008.class, actualDate,sqlAdd)));
*/

    /*         //Способ оплаты берем по виду случая
            //Способ оплаты мед. помощи IDSP //TODO Пока считаем только круглосуточный и дневной стационар
            String idspCode = bedSubType.equals("1")?"33":"43"; //TODO реализовать нормально
            if (vmpCase) {idspCode="13";} //Если ВМП, то способ оплаты = 13
            key = "IDSP#"+idspCode;
            if (!resultMap.containsKey(key)) {
                resultMap.put(key, getActualVocByClassName(VocE2FondV010.class,actualDate ," code='"+idspCode+"'"));
            }
            aEntry.setIDSP((VocE2FondV010)resultMap.get(key));
            /*
/*            final String sqlAddIdsp = " code='"+idspCode+"'";
            aEntry.setIDSP((VocE2FondV010)resultMap.computeIfAbsent(key,k->getActualVocByClassName(VocE2FondV010.class,actualDate ,sqlAddIdsp)));
*/
        } else if (polyclinicCase) { //Заполняем поля для пол-ки
            String resultCode=dischargeData[0], ishodCode=dischargeData[1];
            //Результат
            if (aEntry.getFondResult()==null || forceUpdate) {
                key = "VISIT#RESULT#"+resultCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key,getActualVocByClassName(VocE2FondV009.class, actualDate, "code='"+resultCode+"'"));}
                if (resultMap.get(key)!=null) aEntry.setFondResult((VocE2FondV009)resultMap.get(key));
            }

            //Исход
            if (aEntry.getFondIshod()==null || forceUpdate) {
                key = "VISIT#ISHOD#" + ishodCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key, getActualVocByClassName(VocE2FondV012.class, actualDate, "code='" + ishodCode + "'"));}
                if (resultMap.get(key)!=null) aEntry.setFondIshod((VocE2FondV012) resultMap.get(key));
            }

            //Профиль мед. помощи
            if (aEntry.getMedHelpProfile()==null || forceUpdate) {
                if (aEntry.getFondDoctorSpec()!=null) { //Обновляем профиль мед. помощи по профилю врача
                    aEntry.setMedHelpProfile(aEntry.getFondDoctorSpec().getPolicProfile());
                }
            }

            //Вид медицинской помощи
            if (aEntry.getMedHelpKind()==null || forceUpdate) {
                String v008Code = "13"; //первичная специализированная медико-санитарная помощь
                key = "V008#"+v008Code;
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key,getActualVocByClassName(VocE2FondV008.class, actualDate,"code='"+v008Code+"'"));
                }
                aEntry.setMedHelpKind((VocE2FondV008)resultMap.get(key));
            }
        } else if (extDispCase) { // TODО реализовать для ДД
            //_vidpom

            //for_pom

            //vbr

            //профиль_К

            //<DS1_PR>1</DS1_PR>

            //Result <RSLT>
            if (aEntry.getFondResult()==null||forceUpdate) {
                //Результат = видДД#группа_здоровья
                String resultCode =aEntry.getSubType().getCode()+"#"+aEntry.getExtDispHealthGroup();
                /* в зависимости от типа ДД, группа здоровья */
                key = "EXTDISP#KEY#"+resultCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key,getActualVocByClassName(VocE2FondV009.class, actualDate, "extDispCodes like '%"+resultCode+"%'"));}
                aEntry.setFondResult((VocE2FondV009)resultMap.get(key));
            }

            //Исход <ISHOD>
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
 /*           if (aEntry.getIDSP()==null||forceUpdate) {
                String idspCode="11";
                key = "IDSP#"+idspCode;
                if (!resultMap.containsKey(key)) {resultMap.put(key, getActualVocByClassName(VocE2FondV010.class,actualDate ," code='"+idspCode+"'"));}
                aEntry.setIDSP((VocE2FondV010)resultMap.get(key));
            }*/

        } else {
            //   usl="4"; //скорая помощь
        }
        theManager.persist(aEntry);
    }

    private String addSql(String aField, String aValue) {
        return  isNotNull(aValue)?" ("+aField+" ='"+aValue+"')":" ("+aField+" is null or "+aField+"='')";
    }
    private void cleanAllMaps(){
        entrySubTypeHashMap = new HashMap<>();
        //  diagnosisMap = new HashMap<String, Object>();
        // serviceList = new HashMap<String, VocMedService>();
        ksgMap = new HashMap<>();
        tariffMap = new HashMap<>();
        //  cusmoMap = new HashMap<String, BigDecimal>();
        //  hospitalCostMap = new HashMap<String, BigDecimal>();
        difficultyHashMap = new HashMap<>();
        polyclinicCasePrice = new HashMap<>();
        //   resultMap = new HashMap<String, Object>(); //результат госпитализации
    }

    /**Расчет предварительной цены случая*/
    public String getMedcaseCost(Long aMedcaseId) {
        JSONObject ret = new JSONObject();
        try {
            MedCase medCase = theManager.find(MedCase.class,aMedcaseId);
            List<HitechMedicalCase> vmps = medCase.getHitechMedicalCases();
            E2Entry sloEntry = new E2Entry();
            Date finishDate = medCase.getDateFinish()!=null?medCase.getDateFinish():new Date(System.currentTimeMillis());
            sloEntry.setStartDate(medCase.getDateStart());
            sloEntry.setFinishDate(finishDate);
            if (vmps!=null&& !vmps.isEmpty()) { //Считаем цену по виду ВМП
                HitechMedicalCase vmp = vmps.get(0);
                sloEntry.setVMPKind(vmp.getKind().getCode());
            } else if (medCase instanceof DepartmentMedCase) { //Формируем цену по СЛО
                sloEntry.setEntryType(HOSPITALTYPE);
                DepartmentMedCase hospitalMedCase = (DepartmentMedCase) medCase;
                sloEntry.setSex(medCase.getPatient().getSex().getOmcCode());
                sloEntry.setBirthDate(medCase.getPatient().getBirthday());
                sloEntry.setHospitalStartDate(medCase.getDateStart());
                sloEntry.setHospitalFinishDate(finishDate);
                sloEntry.setHelpKind(hospitalMedCase.getBedFund().getBedType().getOmcCode());
                long bedDays = AgeUtil.calculateDays(sloEntry.getStartDate(), sloEntry.getFinishDate());
                sloEntry.setBedDays(bedDays);
                sloEntry.setCalendarDays(bedDays>0?bedDays+1:1);
                sloEntry.setBedSubType(hospitalMedCase.getBedFund().getBedSubType().getCode()); //дневные/круглосуточные
                sloEntry.setDepartmentId(medCase.getDepartment().getId());
                sloEntry.setMedHelpProfile(getProfileByBedType(sloEntry));
                sloEntry=setEntrySubType(sloEntry);
                sloEntry.setPatientDifficulty(new ArrayList<>());
                List<Diagnosis> diagnoses = medCase.getDiagnoses();
                List<EntryDiagnosis> entryDiagnoses = new ArrayList<>();
                for (Diagnosis d:diagnoses) {
                    entryDiagnoses.add(new EntryDiagnosis(sloEntry,d.getIdc10(),d.getRegistrationType(),d.getPriority(),d.getMkbAdc(),d.getIllnesPrimary().getOmcCode()));
                }
                sloEntry.setDiagnosis(entryDiagnoses);
                List<SurgicalOperation> operationList = medCase.getSurgicalOperations();
                List<EntryMedService> medServiceList = new ArrayList<>();

                for (SurgicalOperation so: operationList) {
                    medServiceList.add(new EntryMedService(sloEntry, so.getMedService().getVocMedService()));
                }
                List<BigInteger> list = theManager.createNativeQuery("SELECT vmssrv.id as msCode FROM MedCase mcsrv LEFT JOIN MedService mssrv ON mcsrv.medservice_id=mssrv.id" +
                    " LEFT JOIN VocMedService vmssrv ON mssrv.vocmedservice_id=vmssrv.id WHERE mcsrv.parent_id=:id AND mcsrv.DTYPE = 'ServiceMedCase' and vmssrv.isOmc='1'")
            .setParameter("id",medCase.getId()).getResultList();
                if (!list.isEmpty()) {
                    for (BigInteger bi: list) {
                        medServiceList.add(new EntryMedService(sloEntry,theManager.find(VocMedService.class,bi.longValue())));
                    }
                }
                sloEntry.setMedServices(medServiceList);
                sloEntry.setKsg(getBestKSG(sloEntry,true,true));
            } else {
                LOG.warn("Расчет примерной цены по поликлинике пока не сделан");
                ret.put("status","error");
                ret.put("errorName","Расчет примерной цены по поликлинике пока не сделан");
            }
            sloEntry = calculateEntryPrice(sloEntry);

            if (sloEntry.getCost()!=null) {
                VocKsg ksg = sloEntry.getKsg();
                ret.put("ksg",ksg!=null?ksg.getCode()+" "+ksg.getName():"---");
                ret.put("status","ok");
                ret.put("price",sloEntry.getCost());
                ret.put("formulaCost",sloEntry.getCostFormulaString());
            } else {
                ret.put("status","error");
                ret.put("errorName","Не удалось посчитать цену");

            }

        } catch (Exception e) {
            e.printStackTrace();
            ret.put("errorCode",e.getLocalizedMessage());
            LOG.error(e);
        }
        return ret.toString();
    }
    private @PersistenceContext EntityManager theManager;
    private @EJB ILocalMonitorService theMonitorService;
}