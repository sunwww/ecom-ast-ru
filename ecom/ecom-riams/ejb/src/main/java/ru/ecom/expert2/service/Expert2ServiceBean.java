package ru.ecom.expert2.service;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.domain.*;
import ru.ecom.expert2.domain.price.ExtDispPrice;
import ru.ecom.expert2.domain.price.ExtDispPriceMedService;
import ru.ecom.expert2.domain.voc.*;
import ru.ecom.expert2.domain.voc.enums.VocListEntryTypeCode;
import ru.ecom.expert2.domain.voc.federal.*;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.*;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.ecom.mis.ejb.service.disability.DisabilityServiceBean;
import ru.ecom.oncological.ejb.domain.*;
import ru.nuzmsh.util.CollectionUtil;
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
import javax.persistence.Transient;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static ru.ecom.expert2.domain.voc.E2Enumerator.ALLTIMEHOSP;
import static ru.ecom.expert2.domain.voc.E2Enumerator.DAYTIMEHOSP;
import static ru.ecom.expert2.domain.voc.enums.VocListEntryTypeCode.*;
import static ru.nuzmsh.util.BooleanUtils.isNotTrue;
import static ru.nuzmsh.util.BooleanUtils.isTrue;
import static ru.nuzmsh.util.CollectionUtil.isEmpty;
import static ru.nuzmsh.util.CollectionUtil.isNotEmpty;
import static ru.nuzmsh.util.EqualsUtil.*;
import static ru.nuzmsh.util.StringUtil.isNullOrEmpty;

@Stateless
@Local(IExpert2Service.class)
@Remote(IExpert2Service.class)
public class Expert2ServiceBean implements IExpert2Service {
    private static final Logger LOG = Logger.getLogger(Expert2ServiceBean.class);
    private static final String COMPLEXSERVICESTREAM = "COMPLEXCASE";
    private static final String OMC_SERVICE_STREAM = "OBLIGATORYINSURANCE";
    private static final List<String> CHILD_BIRTH_MKB = Arrays.asList("O14.1", "O34.2", "O36.3", "O36.4", "O42.2"); //Список диагнозов, с которыми разрешена подача обсервационного отделения менее 5 дней
    private static final List<String> politravmaMainList = Arrays.asList("S02.7", "S12.7", "S22.1", "S27.7", "S29.7", "S31.7", "S32.7", "S36.7", "S38.1", "S39.6", "S39.7", "S37.7", "S42.7", "S49.7", "T01.1", "T01.8", "T01.9", "T02.0", "T02.1", "T02.2", "T02.3", "T02.4", "T02.5", "T02.6", "T02.7", "T02.8", "T02.9", "T04.0", "T04.1", "T04.2", "T04.3", "T04.4", "T04.7", "T04.8", "T04.9", "T05.0", "T05.1", "T05.2", "T05.3", "T05.4", "T05.5", "T05.6", "T05.8", "T05.9", "T06.0", "T06.1", "T06.2", "T06.3", "T06.4", "T06.5", "T06.8", "T07");
    private static final List<String> politravmaSeconaryList = Arrays.asList("J94.2", "J94.8", "J94.9", "J93", "J93.0", "J93.1", "J93.8", "J93.9", "J96.0", "N17", "T79.4", "R57.1", "R57.8");
    private static final List<String> ksgExceptions = Arrays.asList("st02.008#st02.010", "st02.008#st02.011", "st02.009#st02.010", "st04.002#st14.001", "st04.002#st14.002", "st21.007#st21.001"
            , "st34.001#st34.002", "st26.001#st34.002", "st30.003#st34.006", "st30.005#st09.001", "st31.017#st31.002"); //терапевтическая#Хирургическая
    private static final List<Long> serviceDepartments = Arrays.asList(224L, 416L); //визиты в этих департаментах подаем как услуги
    /**
     * Создаем список диагнозов из строки с диагнозами +устанавливаем основной диагноз
     * UPD 18-07-2018 Помечаем случай как раковый
     */ //делаем разово

    private static final List<String> covidMkbs = Arrays.asList("U07.1", "U07.2");
    private static final HashMap<String, E2KsgCoefficientHistory> ksgCoefficientMap = new HashMap<>();
    private static final HashMap<String, Object> resultMap = new HashMap<>(); //результат госпитализации
    /**
     * Получение значения из справочника V002 по профилю коек
     */
    private static final HashMap<String, VocE2MedHelpProfile> bedTypes = new HashMap<>();
    /**
     * Нахождение КСГ с бОльшим коэффициентом трудозатрат для случая
     */
    private static final Map<String, List<BigInteger>> ksgMap = new HashMap<>();
    private static BigDecimal DAY_TIME_HOSP_KSLP = BigDecimal.ZERO; //КСЛП для дневного стационара
    private static boolean isBillCreating = false;
    private static boolean isCheckIsRunning = false;
    private final SimpleDateFormat SQLDATE = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat MONTHYEARDATE = new SimpleDateFormat("yyyy-MM");
    private final Map<String, Object> diagnosisMap = new HashMap<>();
    private final Map<String, VocMedService> SERVICELIST = new HashMap<>();
    private final Map<String, BigDecimal> hospitalCostMap = new HashMap<>();
    private final Map<String, VocE2EntrySubType> entrySubTypeHashMap = new HashMap<>();
    private final Map<String, BigDecimal> tariffMap = new HashMap<>();
    private final Map<String, BigDecimal> cusmoMap = new HashMap<>();
    private final Map<String, VocE2CoefficientPatientDifficulty> difficultyHashMap = new HashMap<>();
    private final Map<String, VocE2PolyclinicCoefficient> polyclinicCasePrice = new HashMap<>();
    private boolean isConsultativePolyclinic = true;
    private boolean isNeedSplitDayTimeHosp = false;
    private @PersistenceContext
    EntityManager manager;
    private @EJB
    ILocalMonitorService monitorService;
    private @EJB
    IRemoteMonitorService remoteMonitorService;


    private JSONObject getOKJson() {
        return new JSONObject().put("status", "ok");
    }

    /**
     * Запускаем процесс формирования заполнения
     *
     * @param listEntry - Заполнение
     */
    @Override
    public void fillListEntry(E2ListEntry listEntry, String historyNumbers, long monitorId) {
        VocListEntryType type = listEntry.getEntryType();

        if (type == null) {
            LOG.error("Не указан тип заполнения NO_ENTRYLIST_TYPE");
            return;
        }
        VocListEntryTypeCode listEntryTypeCode = type.getCode();
        String resourceName = type.getSqlFileName();
        if (resourceName == null) {
            throw new IllegalStateException("Не указан файл для формирования запроса в заполнении с типом " + type);
        }
        StringBuilder sqlHistory = new StringBuilder();
        if (isNotLogicalNull(historyNumbers)) {
            String[] histories = historyNumbers.split(",");
            sqlHistory.append(HOSPITAL.equals(listEntryTypeCode) ? " and ss.code" : " and p.patientSync").append(" in (");
            boolean isFirst = true;
            for (String history : histories) {
                if (!isFirst) {
                    sqlHistory.append(",");
                } else {
                    isFirst = false;
                }
                sqlHistory.append("'").append(history).append("'");
            }
            sqlHistory.append(")");
        }
        String searchSql = getFileAsSTring(resourceName)
                .replace("##dateStart##", toSQlDateString(listEntry.getStartDate()))
                .replace("##dateEnd##", toSQlDateString(listEntry.getFinishDate()))
                .replace("##LPU_CODE##", getExpertConfigValue("LPU_REG_NUMBER", "30001"))
                + sqlHistory;
        LOG.info("SQL = " + searchSql);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("listEntry", listEntry);
        listEntry.setCreateDate(new java.sql.Date(System.currentTimeMillis()));
        listEntry.setCreateTime(new java.sql.Time(System.currentTimeMillis()));
        manager.persist(listEntry);
        try (Statement statement = createStatement()) {
            ResultSet foundCases = statement.executeQuery(searchSql);
            createEntriesByEntryList(foundCases, paramMap, listEntryTypeCode, monitorId); //Создаем записи по заполнению
        } catch (Exception e) {
            LOG.error("can't parse data", e);
        }
        if (listEntry.getMonitorId() != null) {
            listEntry.setMonitorId(null);
            manager.persist(listEntry);
        }
    }

    /**
     * Создаем записи в заполнении из sql запроса
     *
     * @param resultSet
     * @param paramMap
     * @param entryListCode
     * @param monitorId
     */
    private void createEntriesByEntryList(ResultSet resultSet, Map<String, Object> paramMap, VocListEntryTypeCode entryListCode, long monitorId) { //Сохраняем сущности
        if (monitorId == 0L) {
            monitorId = remoteMonitorService.createMonitor();
        }
        IMonitor monitor = monitorService.startMonitor(monitorId, "Формирование нового заполнения", 9999);
        setIsConsultativePolyclinic();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int rowsLength = metaData.getColumnCount();
            String[] fields = new String[rowsLength];
            int[] types = new int[rowsLength];
            final HashMap<String, Method> methodMap = new HashMap<>();
            Class<E2Entry> clazz = E2Entry.class;
            for (int i = 0; i < rowsLength; i++) { //Для каждого столбца находим геттер
                fields[i] = metaData.getColumnName(i + 1);
                types[i] = metaData.getColumnType(i + 1);

                String key = "GETTER#" + fields[i];
                Method getterMethod;
                if (!methodMap.containsKey(key)) {
                    try {
                        getterMethod = PropertyUtil.getGetterMethodIgnoreCase(clazz, fields[i]);
                        methodMap.put(key, getterMethod);
                        methodMap.put("SETTER#" + fields[i], PropertyUtil.getSetterMethod(clazz, getterMethod));
                    } catch (Exception e) {
                        LOG.warn("Не найдено поле с именем " + fields[i]);
                        methodMap.put(key, null);
                    }
                }

            }
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) { //Дополняем объект данными НЕ из запроса
                Method getterMethod = PropertyUtil.getGetterMethodIgnoreCase(clazz, entry.getKey());

                methodMap.put("GETTER#" + entry.getKey(), getterMethod);
                methodMap.put("SETTER#" + entry.getKey(), PropertyUtil.getSetterMethod(clazz, getterMethod));
            }

            int j = 0;

            while (resultSet.next()) { // Для каждой строки (кортежа)
                j++;
                if (j % 100 == 0) {
                    LOG.info("Creating " + j + " records");
                    monitor.setText("Формирование заполнения: сформировано " + j + " записей");
                }
                E2Entry entity = new E2Entry();
                for (int i = 0; i < rowsLength; i++) { //Для каждого столбца находим геттер
                    Method getterMethod = methodMap.get("GETTER#" + fields[i]);
                    if (getterMethod != null) {  //нет геттера - нет сеттера!
                        Method setterMethod = methodMap.get("SETTER#" + fields[i]);
                        Object value = convertResultSetValue(types[i], getterMethod.getReturnType(), resultSet.getObject(fields[i]));
                        setterMethod.invoke(entity, value);
                    }
                }

                for (Map.Entry<String, Object> entry : paramMap.entrySet()) { //Дополняем объект данными НЕ из запроса
                    Method getterMethod = methodMap.get("GETTER#" + entry.getKey());
                    if (getterMethod != null) {
                        methodMap.get("SETTER#" + entry.getKey()).invoke(entity, entry.getValue());
                    }
                }
                manager.persist(entity);
                setEntryType(entity, entryListCode);
                makeMedPolicy(entity); //Запускаем один лишь раз
                createDiagnosis(entity); //Запускаем один лишь раз
                createServices(entity); //Запускаем один лишь раз
                manager.persist(entity);
                if (isEquals(entity.getEntryType(), POLYCLINIC)) {
                    makeCheckEntry(entity, false, false);
                }
            }
            LOG.info("Success creating entry list!");
            monitor.finish("Законцено формирование нового заполнения");
        } catch (Exception e) {
            monitor.error("Ошибка формирования нового заполнения: " + e.getLocalizedMessage(), e);
            LOG.error(e.getMessage(), e);
        }
        isCheckIsRunning = false;
    }

    /**
     * При формировании заполнения выполняем расчет КСГ, объединение случаев, повторное нахождение КСГ
     */
    private void checkListEntryFirst(E2ListEntry listEntry, long monitorId) {
        Long listEntryId = listEntry.getId();
        try {
            java.util.Date startStartDate = new java.util.Date();
            LOG.info("start checkListEntryFirst. id=" + listEntryId);
            isCheckIsRunning = true;
            List<E2Entry> e2Entries = manager.createNamedQuery("E2ListEntry.findAllEntries").setParameter("list", listEntry).getResultList();
            if (e2Entries.isEmpty()) {
                LOG.warn("Случаев для проверки не найдено NO_CASES ");
                return;
            }

            VocListEntryTypeCode listEntryCode = listEntry.getEntryType().getCode();
            IMonitor monitor = monitorService.startMonitor(monitorId, "Пересчет случаев в заполнении", e2Entries.size());
            monitor.advice(1);
            switch (listEntryCode) {
                case HOSPITAL: {
                    int i = 0;
                    LOG.info("Приступаем к нахождению лучшего КСГ. START_FIND_BESK_KSG. list_size=" + e2Entries.size());
                    monitor.setText("Приступаем к нахождению лучшего КСГ. START_FIND_BESK_KSG. list_size=" + e2Entries.size());
                    StringBuilder entriesId = new StringBuilder();
                    for (E2Entry entry : e2Entries) { //Найдем лучшее КСГ
                        entriesId.append(",").append(entry.getId());
                        i++;
                        if (i % 100 == 0 && isMonitorCancel(monitor, "Проверяем стационар. Проверено случаев: " + i)) {
                            return;
                        }
                        makeCheckEntry(entry, false, false);
                    }
                    if (isMonitorCancel(monitor, "Закончили первичную проверку случаев.FINISH_FIRST_CHECK")) {
                        return;
                    }

                    //теперь объединим все случаи объединим все случаи (только для стационара)
                    List<BigInteger> hospitalIds = manager.createNativeQuery("select externalparentid from e2entry" +
                            " where id in (" + entriesId.substring(1) + ") and listentry_id=" + listEntryId + " and coalesce(isDeleted, false) = false and coalesce(isUnion, false) = false group by externalparentid having count(externalparentid)>1").getResultList();//Находис все СЛС, в которых больше 1 СЛО

                    i = 0;
                    isMonitorCancel(monitor, "Приступаем к объединению случаев. START_UNION");
                    for (BigInteger hospId : hospitalIds) {
                        i++;
                        if (i % 100 == 0 && isMonitorCancel(monitor, "Идет объединение случаев: " + i)) return;
                        unionHospitalMedCase(listEntryId, hospId.longValue());
                    }
                    LOG.info("Объединение случаев завершено.FINISH_UNION");
                    isMonitorCancel(monitor, "Проверяем КСГ после объединения случаев.2ND_CHECK_KSG");
                    i = 0;
                    for (E2Entry entry : e2Entries) {
                        i++;
                        if (i % 100 == 0 && isMonitorCancel(monitor, "Находим лучшее КСГ-2 после объединения случае. Проверено: " + i))
                            return;
                        //Теперь снова находим КСГ, расчитываем цену и коэффициенты
                        if (!COMPLEXSERVICESTREAM.equals(entry.getServiceStream())) {
                            makeCheckEntry(entry, true, true);
                        }
                    }
                    findCancerEntry(e2Entries);
                    break;
                }
                case POLYCLINICCOVIDTYPE:
                case POLYCLINIC: {
                    //Проверка поликлинических случаев
                    monitor.setText("Удаляем дубли в поликлинике");
                    deletePolyclinicDoubles(listEntryId); //Удалим дубли при первой проверке

                    int i = 0;
                    if (isMonitorCancel(monitor, "POL_START_Поликлиника. Приступаем к нахождению цены и проставлению полей фонда."))
                        return;
                    for (E2Entry entry : e2Entries) {
                        i++;
                        if (i % 100 == 0 && isMonitorCancel(monitor, "Проверяем записи по поликлинике: " + i)) return;
                        makeCheckEntry(entry, false, true);// оченьььь долго
                    }
                    findCancerEntry(e2Entries);//todo порефачить
                    if (isMonitorCancel(monitor, "Поликлиника. Закончили нахождение цены и проставление полей фонда, приступаем к объединению случаев. прошло минут - " + (System.currentTimeMillis() - startStartDate.getTime()) / 60000))
                        return;
                    boolean isGroupSpo = getExpertConfigValue("ISGROUPSPO", "0").equals("1");
                    unionPolyclinicMedCase(listEntryId, isGroupSpo);
                    monitor.setText("Приступаем к проверке перекрестных случаев.");
                    markErrorForCrossSpo(listEntry);
                    monitor.setText("Закончили проверять поликлинику.");
                    break;
                }
                case POL_KDP:  //неотложку
                    makeEmergencyEntry(e2Entries);
                    break;
                case EXTDISP: {
                    LOG.info("Create DD");
                    int i = 0;
                    for (E2Entry entry : e2Entries) {
                        i++;
                        if (i % 100 == 0 && isMonitorCancel(monitor, "Проверяем записи по доп. диспансеризации: " + i))
                            return;
                        makeCheckEntry(entry, false, true);
                    }
                    break;
                }
                case SERVICE:
                    monitor.setText("Находим несколько услуг в одном визите в УСЛУГах");
                    LOG.info("finding doubleService");
                    for (E2Entry entry : e2Entries) {
                        checkServiceEntryFirst(entry);
                    }
                    monitor.setText("Запускаем поиск перекрестных случаев/дублей с другими заполнениями");
                    markErrorForCrossSpo(listEntry);
                    break;
                default:
                    LOG.error("Невозможно выполнить проверку заполнения, неизвестный тип '" + listEntryCode + "'");
                    break;
            }
            long minutes = (System.currentTimeMillis() - startStartDate.getTime()) / 60000;
            LOG.info("Время выполнения проверки (минут) TOTAL_TIME = " + minutes);
            monitor.finish("Завершено. Время выполнения проверки (минут) TOTAL_TIME = " + minutes);
            long currentTime = System.currentTimeMillis();
            listEntry.setCheckDate(new java.sql.Date(currentTime));
            listEntry.setCheckTime(new java.sql.Time(currentTime));
            manager.persist(listEntry);
        } catch (Exception e) {
            isCheckIsRunning = false;
            LOG.error(e.getMessage(), e);
        }
        isCheckIsRunning = false;
    }

    /* Если в 1 визите было оказано несколько услуг - для каждой услуги делаем отдельный случай
     * */
    private void checkServiceEntryFirst(E2Entry entry) {
        List<EntryMedService> serviceList = entry.getMedServices();
        if (serviceList != null && serviceList.size() > 1) {
            for (EntryMedService medService : serviceList) {
                if (medService.getCost().longValue() > 0L) {
                    E2Entry newEntry = cloneEntity(entry, true);
                    createDiagnosis(newEntry);
                    newEntry.setDiagnosis(entry.getDiagnosis().subList(0, 0));
                    ArrayList<EntryMedService> ms = new ArrayList<>();
                    ms.add(new EntryMedService(newEntry, medService));
                    newEntry.setMedServices(ms);
                    newEntry.setMainService(medService.getMedService().getCode());
                    manager.persist(newEntry);
                    makeCheckEntry(newEntry, false, true);
                }
            }
            entry.setIsDeleted(true);
            manager.persist(entry);
        } else {
            makeCheckEntry(entry, false, true);
        }

    }

    /**
     * Присваеваем отдельный счет для определенных иногородних регионов
     *
     * @param listEntryId     - ИД заполнения
     * @param billNumber      Номер присваемого счета
     * @param billDate        - Дата присваемого счета
     * @param territoriesList - список территорий, разделенных , например "05,08"
     */
    @Override
    public String splitForeignOtherBill(Long listEntryId, String billNumber, Date billDate, String territoriesList) {
        StringBuilder territories = new StringBuilder();
        if (isNullOrEmpty(territoriesList)) {
            territories.append("'08','05'");
        } else {
            boolean isFirst = true;
            for (String t : territoriesList.split(",")) {
                if (!isFirst) {
                    territories.append(",");
                } else {
                    isFirst = false;
                }

                territories.append("'").append(t).append("'");

            }
        }
        LOG.info("Разделяем иногородних по территории " + billNumber + " " + billDate + " " + territories);
        E2Bill bill = getBillEntryByDateAndNumber(billNumber, billDate, null);
        List<BigInteger> list = manager.createNativeQuery("select id from e2entry where listentry_id=:listId and substring(insurancecompanycode ,0,3) in (" + territories.toString() + ")" +
                        " and coalesce(isDeleted, false) = false and coalesce(doNotSend, false) = false ")
                .setParameter("listId", listEntryId).getResultList();
        for (BigInteger id : list) {
            E2Entry e = manager.find(E2Entry.class, id.longValue());
            e.setBill(bill);
            e.setBillDate(billDate);
            e.setBillNumber(billNumber);
            manager.persist(e);
        }
        return getOKJson().put("count", list.size()).toString();
    }

    /**
     * Находим или создаем счет
     */
    @Override
    public E2Bill getBillEntryByDateAndNumber(String billNumber, Date billDate, String comment) {
        if (isBillCreating) {
            return null;
        }
        isBillCreating = true;
        E2Bill bill;
        try {
            String sql = "select id from e2bill where billNumber=:number and billDate=:date ";
            List<BigInteger> list = manager.createNativeQuery(sql).setParameter("number", billNumber).setParameter("date", billDate).getResultList();
            if (list.isEmpty()) { //Создаем новый счет. статус - черновик
                bill = new E2Bill();
                bill.setBillNumber(billNumber);
                bill.setBillDate(billDate);
                bill.setStatus(getActualVocByCode(VocE2BillStatus.class, "DRAFT"));
            } else if (list.size() > 1) {
                LOG.error(list.get(0) + "<>" + list.get(1) + " Найдено более 1 счета с номером " + billNumber + " и датой " + billDate + "(" + list.size() + ")");
                bill = null;
            } else {
                bill = manager.find(E2Bill.class, list.get(0).longValue());
            }
            if (bill != null) {
                if (comment != null) {
                    bill.setComment(comment);
                }
                manager.persist(bill);
            }
        } finally {
            isBillCreating = false;
        }
        return bill;
    }

    @Override
    public E2Entry cloneEntity(E2Entry sourceObject) {
        return cloneEntity(sourceObject, false);
    }

    private E2Entry cloneEntity(E2Entry sourceObject, boolean needPersist) {
        try {
            Method[] methodList = sourceObject.getClass().getMethods();
            E2Entry newEntity = new E2Entry();
            for (Method setterMethod : methodList) {
                if (setterMethod.getName().startsWith("set")) {
                    if (setterMethod.getName().equals("setId")
                            || setterMethod.isAnnotationPresent(OneToMany.class)) {
                        continue;
                    }
                    String propertyName = PropertyUtil.getPropertyName(setterMethod);
                    try {
                        Object val = PropertyUtil.getPropertyValue(sourceObject, propertyName);
                        PropertyUtil.setPropertyValue(newEntity, propertyName, val);
                    } catch (Exception ignored) {
                    }
                }
            }
            if (needPersist) {
                manager.persist(newEntity);
            }
            return newEntity;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * Переносим записи с ошибками из одного заполнения в новое
     */
    @Override
    public void exportErrorsNewListEntry(Long listEntryId, String[] errorCodes, String[] sanctionCodes) {
        try {
            E2ListEntry currentListEntry = manager.find(E2ListEntry.class, listEntryId);
            E2ListEntry newListEntry = new E2ListEntry(currentListEntry, "Ошибки_" + currentListEntry.getName());
            newListEntry.setCheckDate(currentListEntry.getCheckDate());
            newListEntry.setCheckTime(currentListEntry.getCheckTime());
            manager.persist(newListEntry);
            for (String errorCode : errorCodes) {
                List<BigInteger> list = manager.createNativeQuery("select err.entry_id from E2EntryError err " +
                                " where err.listEntry_id=:id and err.errorCode=:errorCode")
                        .setParameter("id", listEntryId).setParameter("errorCode", errorCode.trim()).getResultList();
                LOG.info("creating errors [" + errorCode + "]... defect list size = " + list.size());
                for (BigInteger entryId : list) {
                    E2Entry newEntry = manager.find(E2Entry.class, entryId.longValue());
                    if (newEntry == null) {
                        continue;
                    }
                    newEntry.setListEntry(newListEntry);
                    List<E2Entry> children = manager.createQuery("from E2Entry where parentEntry=:e").setParameter("e", newEntry).getResultList();
                    for (E2Entry child : children) {
                        child.setListEntry(newListEntry);
                        manager.persist(child);
                    }
                    manager.persist(newEntry);
                }
            }
            for (String dopCode : sanctionCodes) {
                List<BigInteger> list = manager.createNativeQuery("select es.entry_id from e2entrysanction es" +
                                " left join e2entry ee on es.entry_id=ee.id " +
                                " where ee.listEntry_id=:id and es.dopcode=:dopCode")
                        .setParameter("id", listEntryId).setParameter("dopCode", dopCode.trim()).getResultList();
                LOG.info("creating sanctions [" + dopCode + "]... defect list size = " + list.size());
                for (BigInteger entryId : list) {
                    E2Entry newEntry = manager.find(E2Entry.class, entryId.longValue());
                    if (newEntry == null) {
                        continue;
                    }
                    newEntry.setListEntry(newListEntry);
                    List<E2Entry> children = manager.createQuery("from E2Entry where parentEntry=:e").setParameter("e", newEntry).getResultList();
                    for (E2Entry child : children) {
                        child.setListEntry(newListEntry);
                        manager.persist(child);
                    }
                    manager.persist(newEntry);
                }
            }

            LOG.info("Finish create defects!");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Экспорт дефектов из заполнения в новое заполнение
     */
    @Override
    public boolean exportDefectNewListEntry(Long listEntryId) {
        try {
            E2ListEntry currentListEntry = manager.find(E2ListEntry.class, listEntryId);
            E2ListEntry newListEntry = new E2ListEntry(currentListEntry, "Дефекты_" + currentListEntry.getName());
            newListEntry.setCheckDate(currentListEntry.getCheckDate());
            newListEntry.setCheckTime(currentListEntry.getCheckTime());
            manager.persist(newListEntry);
            List<E2Entry> list = manager.createQuery("from E2Entry where listEntry_id=:id and isDefect='1' and coalesce(isDeleted, false) = false").setParameter("id", listEntryId).getResultList();
            LOG.info("creating defects... defect list size = " + list.size());

            for (E2Entry entry : list) {
                E2Entry newEntry = cloneEntity(entry);
                if (newEntry != null) {
                    newEntry.setListEntry(newListEntry);
                    manager.persist(newEntry);
                    cloneDiagnosis(entry, newEntry);
                    cloneMedService(entry, newEntry);
                    cloneChildEntries(entry, newEntry);
                    cloneOncologyCases(entry, newEntry);
                    cloneCovidDrugs(entry, newEntry);
                }
            }
            LOG.info("Finish create defects!");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return true;
    }

    private void cloneCovidDrugs(E2Entry entry, E2Entry newEntry) {
        if (isNotEmpty(entry.getDrugEntries())) {
            List<E2DrugEntry> drugEntryList = new ArrayList<>();
            for (E2DrugEntry drug : entry.getDrugEntries()) {
                E2DrugEntry cloned = new E2DrugEntry(drug, newEntry);
                manager.persist(cloned);
                drugEntryList.add(cloned);

            }
            newEntry.setDrugEntries(drugEntryList);
            manager.persist(newEntry);
        }
    }

    private void cloneOncologyCases(E2Entry oldEntry, E2Entry newEntry) {
        if (isNotEmpty(oldEntry.getCancerEntries())) {
            List<E2CancerEntry> cancerEntryList = new ArrayList<>();
            for (E2CancerEntry oldCancerEntry : oldEntry.getCancerEntries()) {
                E2CancerEntry ccc = new E2CancerEntry(oldCancerEntry, newEntry);
                manager.persist(ccc);
                cancerEntryList.add(ccc);
            }
            newEntry.setCancerEntries(cancerEntryList);
            manager.persist(newEntry);
        }
    }

    /**
     * Клонируем дочерние случаи
     */
    private void cloneChildEntries(E2Entry oldEntry, E2Entry newEntry) {
        List<E2Entry> children = manager.createQuery("from E2Entry where parentEntry_id=:id").setParameter("id", oldEntry.getId()).getResultList();
        for (E2Entry child : children) {
            E2Entry newChild = cloneEntity(child);
            newChild.setParentEntry(newEntry);
            newChild.setListEntry(newEntry.getListEntry());
            List<E2Entry> subChildren = manager.createQuery("from E2Entry where parentEntry_id=:id").setParameter("id", child.getId()).getResultList();
            if (!subChildren.isEmpty()) {
                manager.persist(newChild);
                for (E2Entry subChild : subChildren) {
                    cloneChildEntries(subChild, newChild);
                }
            }
            manager.persist(newChild);
        }
    }

    /**
     * Клонируем диагнозы
     */
    private void cloneDiagnosis(E2Entry oldEntry, E2Entry newEntry) {
        List<EntryDiagnosis> list = manager.createQuery("from EntryDiagnosis where entry_id=:id").setParameter("id", oldEntry.getId()).getResultList();
        for (EntryDiagnosis ed : list) {
            manager.persist(new EntryDiagnosis(newEntry, ed));
        }
    }

    /**
     * Клонируем диагнозы
     */
    private void cloneMedService(E2Entry oldEntry, E2Entry newEntry) {
        List<EntryMedService> list = manager.createQuery("from EntryMedService where entry_id=:id").setParameter("id", oldEntry.getId()).getResultList();
        for (EntryMedService ed : list) {
            manager.persist(new EntryMedService(newEntry, ed));
        }
    }

    private Long toLong(String str) {
        return isNotLogicalNull(str) ? Long.valueOf(str.trim()) : null;
    }

    /**
     * Добавляем услугу и/или диагноз в случай
     */
    @Override
    public Boolean addDiagnosisAndServiceToEntry(Long entryId, String jsonData) {
        E2Entry entry = manager.find(E2Entry.class, entryId);
        try {
            JSONObject ds = new JSONObject(jsonData);
            String key = "DiagnosisMkbId";

            if (ds.has(key) && isNotLogicalNull(ds.getString(key))) {
                Long diagnosisIs = toLong(ds.getString(key));
                VocDiagnosisRegistrationType registrationType = manager.find(VocDiagnosisRegistrationType.class, ds.getLong("DiagnosisRegistrationType"));
                VocIdc10 mkb = manager.find(VocIdc10.class, diagnosisIs);
                VocPriorityDiagnosis priorityDiagnosis = manager.find(VocPriorityDiagnosis.class, ds.getLong("DiagnosisPriority"));
                String dopMkb = ds.getString("DiagnosisDopMkb");
                Long illnessPrimary = ds.getLong("DiagnosisIllnesPrimary");
                VocE2FondV027 primary = manager.find(VocE2FondV027.class, illnessPrimary);
                EntryDiagnosis ed = new EntryDiagnosis(entry, mkb, registrationType, priorityDiagnosis, dopMkb, primary);
                manager.persist(ed);
                if (ds.has("DiagnosisMainMkb")) {
                    entry.setMainMkb(mkb.getCode());
                    manager.persist(entry);
                }
            }
            key = "DiagnosisMedService";

            if (ds.has(key) && isNotLogicalNull(ds.getString(key))) {
                Long medserviceId = toLong(ds.getString(key));
                String serviceDate = ds.getString("DiagnosisMedServiceDate");
                VocMedService vocMedService = manager.find(VocMedService.class, medserviceId);
                EntryMedService ems = new EntryMedService(entry, vocMedService);
                if (isNotLogicalNull(serviceDate)) {
                    Date sqlServiceDate = DateFormat.parseSqlDate(serviceDate);
                    ems.setServiceDate(sqlServiceDate);
                    VocOmcMedServiceCost cost = getMedServiceOmc(vocMedService, sqlServiceDate);
                    if (cost != null) {
                        ems.setCost(cost.getCost());
                        ems.setDoctorSpeciality(cost.getWorkFunction());
                    } else {
                        ems.setCost(BigDecimal.ZERO);
                    }

                }
                manager.persist(ems);
                if (ds.has("DiagnosisIsMainService")) {
                    entry.setMainService(vocMedService.getCode());
                    manager.persist(entry);
                }
            }
            return true;
        } catch (Exception e) {
            LOG.error("ERROR_PARSING_JSON:" + jsonData + ">" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Объединяем СЛС с родами
     */
    private void unionChildBirthHospital(List<E2Entry> entriesList) {

        /*
        27-12-2018 Получаем список в обратном порядке
        14-12-2018. Логика поменялась. Первым может быть как паталогия, так и родовое. а, может даже и обсервационное отделение.
        делаем так: берем СЛО. Если роды, то склеиваем со след. СЛО. если отделения след. СЛО = отделению пред СЛО (пред. родовому), то и их склеиваем.
         */

        E2Entry mainEntry = null;
        boolean childBirthFound = false;
        E2Entry childEntry = null;
        E2Entry patologyEntry = null;

        for (E2Entry entry : entriesList) {
            if (mainEntry == null) { //Последнее СЛО (99% обсервационное)
                mainEntry = entry;
            } else if (isTrue(mainEntry.getNoOmcDepartment()) || isTrue(entry.getNoOmcDepartment())) {
                mainEntry = isTrue(mainEntry.getNoOmcDepartment()) ? unionEntries(entry, mainEntry) : unionEntries(mainEntry, entry);
            } else if (entry.getDepartmentId().equals(mainEntry.getDepartmentId())) { //Если реанимация или Отд-Реан-Отд, последнее главное
                unionEntries(mainEntry, entry);
            } else if (isTrue(entry.getIsChildBirthDepartment())) {
                mainEntry.setExternalPrevMedcaseId(null);
                unionEntries(mainEntry, entry);
                childBirthFound = true;
                childEntry = mainEntry;
                mainEntry = null;
            } else {
                LOG.warn("Что-то необычное в родах, стоит обратить внимание " + entry.getId() + entry.getHelpKind());
            }
        }
        if (childBirthFound && mainEntry != null && !childEntry.equals(mainEntry)) {
            patologyEntry = mainEntry;
        }

        if (patologyEntry != null) {
            //Если текущий случай - обсервационное отделение. Допускаем что до него может быть только патология беременности(+роды). Все операции с пред. отделений переносим в это отделение.
            long calendarDays = isNotLogicalNull(patologyEntry.getCalendarDays()) ? patologyEntry.getCalendarDays() : 0;
            if (isNotTrue(patologyEntry.getIsChildBirthDepartment())
                    && (calendarDays > 5 || calendarDays > 1 && CHILD_BIRTH_MKB.contains(patologyEntry.getMainMkb()))) { //Если длительность случая - больше пяти дней (или диагноз входит в список)- не объединяемъ
                VocE2FondV009 perevodResult = getActualVocByCode(VocE2FondV009.class, patologyEntry.getFinishDate(), "104"); //TODO Колхоз - исправить
                patologyEntry.setFondIshod(getActualVocByCode(VocE2FondV012.class, patologyEntry.getFinishDate(), "103")); //TODO Колхоз - исправить
                patologyEntry.setFondResult(perevodResult); //TODO Колхоз - исправить
                patologyEntry.setIsUnion(true);
                childEntry.setExternalPrevMedcaseId(patologyEntry.getExternalId());
                manager.persist(childEntry);
                manager.persist(patologyEntry);
                makeCheckEntry(childEntry, false, false);
            } else {
                unionEntries(childEntry, patologyEntry);
            }
        }
    }

    /**
     * Удаляем дубли по поликлинике
     * дублем считаем повторное посещение про одному профилю мед. помощи за исключением мобильной поликлиники, НМП и КДО
     */
    private void deletePolyclinicDoubles(Long listEntryId) {
        deletePolyclinicDoubles(listEntryId, false);
        deletePolyclinicDoubles(listEntryId, true);
    }

    /**
     * Удаляем дубли по поликлинике (в рамках одного заполнения
     */
    private void deletePolyclinicDoubles(Long listEntryId, boolean deleteEmergency) {
        LOG.info("Starting deleting doubles in [" + listEntryId + "], emergency=" + deleteEmergency);
        List<Object[]> list = manager.createNativeQuery("select max(e2.id) from e2entry e2 where e2.listentry_id =:listId" +
                        " and e2.entryType='POLYCLINIC' and coalesce(e2.isDeleted, false) = false and coalesce(e2.isUnion, false) = false and e2.serviceStream!='COMPLEXCASE'" +
                        " and coalesce(e2.isMobilePolyclinic, false) = false and coalesce(e2.isEmergency, false) = false" +
                        (deleteEmergency ? " and e2.isEmergency ='1'" : " and coalesce(e2.isEmergency, false) = false") +
                        " and coalesce(e2.isDiagnosticSpo, false) = false and medhelpprofile_id is not null " +
                        " group by e2.externalpatientid , medhelpprofile_id, startdate, servicestream" +
                        " having count(e2.id)>1")
                .setParameter("listId", listEntryId)
                .getResultList();
        if (!list.isEmpty()) {
            StringBuilder ids = new StringBuilder();
            int cnt = 0;
            for (Object o : list) {
                cnt++;
                ids.append(o.toString()).append(",");
            }
            LOG.warn("В цикле я бы сделал " + cnt + " апдейтов, но сделал всего 1");
            manager.createNativeQuery("update e2entry set isDeleted='1' where id in (" + ids.toString() + "0)").executeUpdate();
            deletePolyclinicDoubles(listEntryId, deleteEmergency);
        }
        LOG.info("Finished deleting doubles in [" + listEntryId + "], emergency=" + deleteEmergency);
    }

    /**
     * Проверяем поликлинические случаи на пересечение
     */
    private void markErrorForCrossSpo(E2ListEntry listEntry) {
        long startTime = System.currentTimeMillis();
        LOG.warn("Начали поиск перекрестных случаев");
        String sql = "select e.id as eid" +
                " from e2entry e " +
                " left join e2entry olde on olde.externalpatientid=e.externalpatientid" +
                " where e.listentry_id=" + listEntry.getId() +
                " and e.servicestream!='COMPLEXCASE'" +
                " and e.id!=olde.id" +
                " and e.serviceStream=olde.serviceStream" +
                " and olde.bill_id is not null" +
                " and e.medhelpprofile_id = olde.medhelpprofile_id" +
                " and e.entrytype = olde.entrytype" +
                " and e.startdate<=olde.finishdate" +
                " and (e.isdeleted is null or e.isdeleted='0')" +
                " and (e.doNotSend is null or e.doNotSend='0')" +
                " and (olde.isdeleted is null or olde.isdeleted='0')" +
                " and (olde.doNotSend is null or olde.doNotSend='0')";
        List<BigInteger> list = manager.createNativeQuery(sql).getResultList();
        if (!list.isEmpty()) {
            LOG.warn("Найдено " + list.size() + " пересекающихся случаев");
            for (BigInteger id : list) {
                saveError(manager.find(E2Entry.class, id.longValue()), E2EntryErrorCode.CROSS_SPO);
            }
        }
        LOG.warn("Закончили поиск перекрестных случаев (" + list.size() + ") за " + (System.currentTimeMillis() - startTime) / 1000);
    }

    /**
     * Склеивание поликлинических визитов
     */
    private void unionPolyclinicMedCase(Long listEntryId, boolean isGroupBySpo) {
        /** Объединяем
         * Считаем по профилю мед. помощи, потоку обслуживания, классу МКБ
         * */
        List<Object[]> list = manager.createNativeQuery("select " +
                (isGroupBySpo ? "e2.externalparentid as f1, cast('' as varchar) as f2_empty, cast('' as varchar) as f3_empty" : "e2.externalpatientid as f1 , e2.medhelpprofile_id as f2, e2.servicestream as f3") +
                ",cast('' as varchar) as f4_empty, list(e2.id||'') as f5_children from e2entry e2 where e2.listentry_id =:listId" + //Не учитываем диагноз *06.08.2018
                " and e2.entryType='POLYCLINIC'" +
                " and (e2.isDeleted is null or e2.isDeleted='0') and (e2.isUnion is null or e2.isUnion='0') and e2.serviceStream!='COMPLEXCASE'" +
                " and (e2.isMobilePolyclinic is null or e2.isMobilePolyclinic='0') and (e2.isEmergency is null or e2.isEmergency='0')" +
                " and (e2.isDiagnosticSpo is null or e2.isDiagnosticSpo='0') " +
                " and e2.medhelpprofile_id is not null" +
                " group by " + (isGroupBySpo ? "e2.externalparentid" : "e2.externalpatientid , e2.medhelpprofile_id, e2.servicestream") +
                " having count(e2.id)>1 " + (isGroupBySpo ? "" : "and count(case when substring(e2.mainmkb,1,1)='Z' then 1 else null end)<count(e2.id)")).setParameter("listId", listEntryId).getResultList();
        //   LOG.info("sql = "+searchSql+", size = "+list.size());
        int i = 0;
        for (Object[] spo : list) {
            i++;
            if (i % 100 == 0) LOG.info("Объединение случаев - " + i);
            //Создаем новую запись, все существущие помечаем как COMPLEXCASE
            String subList = (String) manager.createNativeQuery("select (select list(id||'') from (select ee.id from e2entry ee where ee.id in (" + spo[4].toString() + ") order by ee.startdate ) as chi ) ").getSingleResult();
            String[] ids = subList.split(",");
            E2Entry mainEntry = null;
            for (String idd : ids) {
                E2Entry entry = manager.find(E2Entry.class, Long.valueOf(idd.trim()));
                if ("109".equals(entry.getDoctorWorkfunction())) {
                    saveError(entry, E2EntryErrorCode.LONG_CHLX);
                }
                if (mainEntry == null) {
                    mainEntry = cloneEntity(entry, true);
                }
                unionPolyclinic(mainEntry, entry);
                if (isGroupBySpo && !isEquals(mainEntry.getMedHelpProfile(), entry.getMedHelpProfile())) {
                    saveError(entry, E2EntryErrorCode.RAZNYE_POLIC_PROFILE);
                }

                String result = mainEntry.getFondResult().getCode();
                if (isOneOf(result, "305", "306")) {
                    createDiagnosis(mainEntry);
                    makeCheckEntry(mainEntry, false, true);
                    mainEntry = null; //Если перевод в стационар - заканчиваем случай.
                }
            }

            if (mainEntry != null) {
                createDiagnosis(mainEntry);
                makeCheckEntry(mainEntry, false, true);
            }
        }
    }

    /**
     * Физическое объединение случая
     */
    private void unionPolyclinic(E2Entry masterEntry, E2Entry slaveEntry) {
        //  E2Entry mainEntry, secondaryEntry;//mainEntry - latest entry
        if (masterEntry.getStartDate().getTime() > slaveEntry.getStartDate().getTime()) {
            masterEntry.setStartDate(slaveEntry.getStartDate());
            masterEntry.setStartTime(slaveEntry.getStartTime());
        }
        if (masterEntry.getFinishDate().getTime() < slaveEntry.getFinishDate().getTime()) { //неглавное - последнее СЛО
            masterEntry.setFinishDate(slaveEntry.getFinishDate());
            masterEntry.setFinishTime(slaveEntry.getFinishTime());
            masterEntry.setFondResult(slaveEntry.getFondResult());
            masterEntry.setFondIshod(slaveEntry.getFondIshod());
            masterEntry.setDiagnosisList(slaveEntry.getDiagnosisList());
            masterEntry.setMainMkb(slaveEntry.getMainMkb());
        }
        slaveEntry.setParentEntry(masterEntry);
        slaveEntry.setServiceStream(COMPLEXSERVICESTREAM);
        masterEntry.setIsUnion(true);
        slaveEntry.setIsUnion(true);
        List<E2Entry> childList = manager.createQuery(" from E2Entry where parentEntry=:entry").setParameter("entry", slaveEntry).getResultList();
        for (E2Entry child : childList) {
            child.setParentEntry(masterEntry);
            manager.persist(child);
        }
        if (isTrue(masterEntry.getIsDentalCase())) {
            moveMedServiceToMainEntry(slaveEntry, masterEntry);
        }
        manager.persist(masterEntry);
        manager.persist(slaveEntry);
    }

    /**
     * Объединеяем все записи по СЛС *Логика объединения тут
     */
    private void unionHospitalMedCase(Long listEntryId, Long hospitalMedcaseId) {
        /*
         * Если у двух случаев равный КЗ, берем данным (врач, отделение, койки) последнего случая
         * Если классы МКБ совпадают, берем СЛО с наибольшим КЗ, из второго СЛО добавляем дни и услуги.
         *      Второе СЛО помечаем как "200 входит в комплексный случай и ставим у него parentEntry  главного случая
         */
        //объединяем все СЛО внутри СЛС
        try {
            if (listEntryId == null || hospitalMedcaseId == null) {
                throw new IllegalStateException("Необходимо указать ИД заполнения и ИД госпитализации");
            }
            List<E2Entry> entriesList = manager.createQuery("from E2Entry where listEntry_id=:listEntryId and externalParentId=:externalParentId " +
                            "and (isDeleted is null or isDeleted='0') order by startdate, starttime")
                    .setParameter("listEntryId", listEntryId).setParameter("externalParentId", hospitalMedcaseId).getResultList(); //Все СЛО по госпитализации, кроме уже объединенных
            if (entriesList.size() > 1) { //Работаем если только найдено больше 1 СЛО
                E2Entry mainEntry = null;
                //Цикл только для ВМП
                E2Entry vmpEntry = null;
                for (E2Entry entry : entriesList) {
                    if (VMP.equals(entry.getEntryType())) { //Если в госпитализации есть случай ВМП, делаем его главным, остальные - неглавные.
                        mainEntry = entry;
                        mainEntry.setStartDate(entry.getHospitalStartDate());
                        mainEntry.setFinishDate(entry.getHospitalFinishDate());
                        mainEntry.setIsUnion(true);
                        vmpEntry = mainEntry;

                        LOG.info("Найден случай в ВМП, помечаем его как главный " + mainEntry.getId());
                        manager.persist(mainEntry);
                    }
                    if ("4".equals(entry.getHelpKind())) { //В СЛС есть обсервационное отделение - запускаем функция по объединению родов! *27-12-2018
                        entriesList = manager.createQuery("from E2Entry where listEntry_id=:listEntryId and externalParentId=:externalParentId" +
                                        " and (isDeleted is null or isDeleted='0') order by startdate desc , starttime desc")
                                .setParameter("listEntryId", listEntryId).setParameter("externalParentId", hospitalMedcaseId).getResultList(); //При склеивании родов склеиваем с конца
                        unionChildBirthHospital(entriesList);
                        return;
                    }
                }
                if (vmpEntry != null) {
                    for (E2Entry entry : entriesList) {
                        if (entry.getId() == mainEntry.getId()) {
                            continue;
                        }
                        entry.setParentEntry(vmpEntry);
                        entry.setServiceStream(COMPLEXSERVICESTREAM);
                        entry.setIsUnion(true);
                        manager.persist(entry);
                    }
                    return;
                }
                //На этом этапе мы уверены, что ВМП в случае у нас нет, случай не содержит родов
                for (E2Entry entry : entriesList) {
                    if (mainEntry == null) { //находим первую запись, считаем её главной
                        mainEntry = entry;
                    } else if (isTrue(entry.getNoOmcDepartment())) { //Если реанимация - смело объединаем с главным случаем.
                        mainEntry.setReanimationEntry(entry);
                        unionEntries(mainEntry, entry);
                    } else { //например - кардиология - сосуд. хирургия (вторая - главная
                        if (isEquals(mainEntry.getDepartmentId(), entry.getDepartmentId())) { //Если ИД отделения равны - не учитываем цену
                            entry.setExternalPrevMedcaseId(mainEntry.getExternalPrevMedcaseId());
                            unionEntries(entry, mainEntry); //последнее отделение - главное
                            mainEntry = entry;
                        } else if (isDiagnosisGroupAreEquals(mainEntry, entry)) { //Если классы МКБ сходятся
                            if (mainEntry.getCost() == null && entry.getCost() == null) {
                                LOG.error("Невозможно объеинить случаи: нет цены ни в одном из случаев");
                            } else {
                                if (entry.getCost() == null || (mainEntry.getCost() != null && mainEntry.getCost().compareTo(entry.getCost()) > 0)) { //Если у первого случая цена больше второго, первый - главный.
                                    unionEntries(mainEntry, entry);
                                } else {
                                    unionEntries(entry, mainEntry); //Если цена текущего случая больше или равно главному случаю, то текущий случай становится главный
                                    mainEntry = entry;
                                }
                            }
                        } else { //Если классы МКБ не сходятся, текущее СЛО становится главным *01.03.2020 исход случая - без перемен, ТФОМС
                            String ss = mainEntry.getBedSubType(); //Текущему случаю ставим результат - перевод на другой профиль коек
                            mainEntry.setFondResult(getActualVocByCode(VocE2FondV009.class, mainEntry.getFinishDate(), ss + "04"));
                            mainEntry.setFondIshod(getActualVocByCode(VocE2FondV012.class, mainEntry.getFinishDate(), ss + "03"));
                            manager.persist(mainEntry);
                            mainEntry = entry;
                        }
                    }
                }
            }
        } catch (IllegalStateException e) {
            LOG.error(">>" + listEntryId + "<><>" + hospitalMedcaseId, e);
        }
    }

    /**
     * переносим информацию об услугах из комплексного случая в главный
     */
    private void moveMedServiceToMainEntry(E2Entry slaveEntry, E2Entry masterEntry) {
        List<EntryMedService> slaveServices = slaveEntry.getMedServices();

        if (isNotEmpty(slaveServices)) {
            for (EntryMedService ems : slaveServices) {
                ems.setEntry(masterEntry);
            }
            if (isNotEmpty(masterEntry.getMedServices())) {
                slaveServices.addAll(masterEntry.getMedServices());
            }
            masterEntry.setMedServices(slaveServices);
            slaveEntry.setMedServices(new ArrayList<>());
        }
    }

    /**
     * тупо объединяем, не думаем
     */
    private E2Entry unionEntries(E2Entry masterEntry, E2Entry slaveEntry) { //Функционал по объединению случаев
        if (masterEntry == null || slaveEntry == null) {
            throw new IllegalStateException("UNOIN = entry=null");
        }
        if (masterEntry.getStartDate().getTime() > slaveEntry.getStartDate().getTime()
                || (masterEntry.getStartDate().getTime() == slaveEntry.getStartDate().getTime() && masterEntry.getStartTime().after(slaveEntry.getStartTime()))) {
            //Если дата+время начала главного случая позднее даты и время начала неглавного случая
            masterEntry.setStartDate(slaveEntry.getStartDate());//Дата начала главного случая = дате начала текущего случая
            masterEntry.setStartTime(slaveEntry.getStartTime());//Время начала главного случая = времени окончания начала текущего случая
        } else if (slaveEntry.getFinishDate().getTime() > masterEntry.getFinishDate().getTime()
                || (slaveEntry.getFinishDate().getTime() == masterEntry.getFinishDate().getTime() && slaveEntry.getFinishTime().after(masterEntry.getFinishTime()))) {
            //Если дата и время окончания неглавного случая позже даты и время окончания главного случая
            masterEntry.setFinishDate(slaveEntry.getFinishDate());//Дата окончания главного случая = дате окончания текущего случая
            masterEntry.setFinishTime(slaveEntry.getFinishTime());//Время окончания главного случая = времени окончания текущего случая
        } else {
            LOG.warn("Что-то пошло не так. Если это не ВМП, то, видимо, программист что-то напутал...main = " + masterEntry.getId() + ", not main =" + slaveEntry.getId());
            //В случаях, если есть ВМП в не первом случае, возможно и такое (текущий СЛО находится между датами С и ПО главного случая. Пропускаем...
        }
        if (slaveEntry.getReanimationEntry() != null) {
            masterEntry.setReanimationEntry(slaveEntry); //Если в объединяемом случае была реанимация - она будет в главном случае
        }
        if (isNotLogicalNull(slaveEntry.getNewbornAmount())) {
            masterEntry.setNewbornAmount(slaveEntry.getNewbornAmount()); //Переносим информация о детях из родового отделения в неродовое
        }
        moveMedServiceToMainEntry(slaveEntry, masterEntry);
        masterEntry.setIsUnion(true);
        slaveEntry.setIsUnion(true);
        slaveEntry.setParentEntry(masterEntry);
        slaveEntry.setServiceStream(COMPLEXSERVICESTREAM);
        manager.persist(masterEntry);
        manager.persist(slaveEntry);
        return masterEntry;
    }

    /**
     * Сравнимаем группы МКБ диагнозов для определения возможности склеивания СЛО
     */
    private boolean isDiagnosisGroupAreEquals(E2Entry firstEntry, E2Entry secondEntry) {
        List<EntryDiagnosis> firstDiagnosisList = manager.createQuery("from EntryDiagnosis where entry_id=:id and registrationType.code='4' and priority.code='1' ").setParameter("id", firstEntry.getId()).getResultList();
        List<EntryDiagnosis> secondDiagnosisList = manager.createQuery("from EntryDiagnosis where entry_id=:id and registrationType.code='4' and priority.code='1' ").setParameter("id", secondEntry.getId()).getResultList();
        if (firstDiagnosisList.isEmpty() || secondDiagnosisList.isEmpty()) {
            saveError(firstDiagnosisList.isEmpty() ? firstEntry : secondEntry, E2EntryErrorCode.NO_DIAGNOSIS);
            LOG.error("Не найдено основного клинического диагноза по случаю");
            return false;
        }
        return firstDiagnosisList.get(0).getMkb().getCode().charAt(0) == secondDiagnosisList.get(0).getMkb().getCode().charAt(0);
    }

    /**
     * Переводим дату в строку в формате yyyy-DD-mm
     */
    private String dateToString(Date date) {
        return SQLDATE.format(date);
    }

    private boolean isLogicalNull(Object field) {
        return !isNotLogicalNull(field);
    }

    /**
     * Проверяем, является ли объект NULL либо пустой строкой, 0 , ...
     */
    private boolean isNotLogicalNull(Object field) {
        if (field == null) return false;
        if (field instanceof String) {
            String ss = (String) field;
            return !ss.trim().equals("");
        } else if (field instanceof Boolean) {
            return (Boolean) field;
        } else if (field instanceof Long) {
            return ((Long) field) > 0L;
        } else if (field instanceof java.sql.Date) {
            return true;
        } else if (field instanceof BigDecimal) {
            return ((BigDecimal) field).compareTo(new BigDecimal(0)) > 0;
        } else {
            throw new IllegalStateException("Нет преобразования для объекта " + field);
        }
    }

    private VocE2MedHelpProfile getProfileByBedType(E2Entry entry) {
        String bedType = entry.getBedProfile() != null ? entry.getBedProfile().getCode() : entry.getHelpKind(); //V020 !!!
        String bedSubType = entry.getBedSubType();

        String key = bedType + "#" + bedSubType;
        if (!bedTypes.containsKey(key)) { //Если нет в карте - запускаем поиск
            List<BigInteger> list = manager.createNativeQuery("select link.profile_id from E2MedHelpProfileBedType link " +
                            " left join VocE2FondV020 voc on voc.id = link.bedProfile_id " +
                            " left join vocbedsubtype vbst on vbst.id=link.subType_id" +
                            " where voc.code=:code and (vbst.id is null or vbst.code=:subTypeCode) " +
                            "and coalesce(link.finishDate,current_date)>=:date")
                    .setParameter("code", bedType).setParameter("date", entry.getFinishDate())
                    .setParameter("subTypeCode", bedSubType)
                    .getResultList();
            if (list.isEmpty()) {
                saveError(entry, E2EntryErrorCode.NO_PROFILE, "NO_PROFILE " + key);
                LOG.error("Не найдено профиля мед. помощи для коек с типом NO_PROFILE_FOR_BED " + key);
                bedTypes.put(key, null);
            } else if (list.size() > 1) {
                saveError(entry, E2EntryErrorCode.NO_PROFILE, "TOO_MANY_PROFILE:" + key);
                LOG.error("Найдно больше 1 соответствия с профилем мед. помощи для коек с типом TO_MANY_PROFILE_FOR_BED " + bedType);
                bedTypes.put(key, null);
            } else {
                bedTypes.put(key, manager.find(VocE2MedHelpProfile.class, list.get(0).longValue()));
            }
        }

        return bedTypes.get(key);
    }

    @Override
    public void addHospitalMedCaseToList(String historyNumber, Long listEntryId) {
        if (isNotLogicalNull(historyNumber)) {
            fillListEntry(manager.find(E2ListEntry.class, listEntryId), historyNumber, remoteMonitorService.createMonitor());
        }
    }

    /**
     * Переформировывание заполнения
     */
    @Override
    public void reFillListEntry(Long listEntryId, long monitorId) {
        E2ListEntry list = manager.find(E2ListEntry.class, listEntryId);
        manager.createNativeQuery("delete from e2entryerror where listentry_id=:id").setParameter("id", listEntryId).executeUpdate();
        manager.createNativeQuery("update e2entry set isDeleted='1' where listEntry_id =:id").setParameter("id", listEntryId).executeUpdate();
        LOG.info("start refill ListEntry_" + listEntryId);
        list.setCheckDate(null);
        list.setCheckTime(null);
        list.setMonitorId(monitorId);
        manager.persist(list);
        fillListEntry(list, null, monitorId);
    }

    private void setIsConsultativePolyclinic() {
        isConsultativePolyclinic = "1".equals(getExpertConfigValue("CONSULTATIVE_LPU", "0"));
        isNeedSplitDayTimeHosp = "1".equals(getExpertConfigValue("SPLIT_DAYTIME_HOSP", "0"));
        DAY_TIME_HOSP_KSLP = new BigDecimal(getExpertConfigValue("DAY_TIME_HOSP_KSLP", "0"));
    }

    /**
     * Базовая точка для выполнения всех проверок внутри заполнения
     */
    @Override
    public void checkListEntry(Long listEntryId, boolean updateKsgIfExist, String paramMap, long monitorId) {
        setIsConsultativePolyclinic();
        checkListEntry(manager.find(E2ListEntry.class, listEntryId), updateKsgIfExist, paramMap, monitorId);
    }

    private void checkListEntry(E2ListEntry listEntry, final boolean updateKsgIfExist, String paramMap, long monitorId) {
        if (isTrue(listEntry.getIsClosed())) {
            LOG.warn("Заполнение закрыто, проверка невозможна");
            throw new IllegalStateException("Заполнение закрыто, проверка невозможна");
        }
        try {
            long startStartDate = System.currentTimeMillis();
            LOG.info("start checkListEntry");
            cleanAllMaps(); //очистим карты перед началом новой проверки
            if (null == listEntry.getCheckDate()) { //Запускаем первичную проверку всех записей
                checkListEntryFirst(listEntry, monitorId);
                return;
            }
            if (isCheckIsRunning) {
                LOG.warn("Проверка уже запущена, ничего не проверяем, RETURN");
                return;
            }
            isCheckIsRunning = true;
            StringBuilder sql = new StringBuilder();
            sql.append("select id from E2Entry where listEntry_id=:id and (isDeleted is null or isDeleted='0')");
            if (isNotLogicalNull(paramMap)) {
                LOG.warn(paramMap);
                String[] params = paramMap.split("&");
                for (String par : params) {
                    if (par.contains("=")) {
                        String[] parr = par.split("=");
                        if (parr.length < 2 || parr[1] == null || parr[1].trim().equals("")) {
                            continue;
                        }
                        String fldName = parr[0], val = parr[1];
                        if (fldName.toUpperCase().contains("DATE")) { //ищем дату
                            sql.append(" and ").append(fldName).append("=").append("to_date('").append(val).append("','dd.MM.yyyy') ");
                        } else if (fldName.startsWith("is")) { //Булево значение
                            sql.append(" and (").append(fldName).append(val.equals("1") ? " ='1')" : " is null or " + fldName + "='0')");
                        } else { //ищем строку
                            sql.append(" and upper(").append(fldName).append(")='").append(val.toUpperCase()).append("' ");
                        }
                    }
                }
            }
            manager.createNativeQuery("delete from e2entryerror  where listentry_id=:id").setParameter("id", listEntry.getId()).executeUpdate(); //Все ошибки удалим
            LOG.info("sql=" + sql);
            List<BigInteger> list = manager.createNativeQuery(sql.toString()).setParameter("id", listEntry.getId()).getResultList();

            if (list.isEmpty()) {
                LOG.warn("Случаев для проверки не найдено " + sql.toString());
                return;
            }
            IMonitor monitor = monitorService.startMonitor(monitorId, "Пересчет случаев в заполнении", list.size());
            monitor.advice(1);
            /* сначала найдем КСГ (для правильного объединения
            потом  производим объединение случаев, потом (только для ОМС) расчитываем поля фонда, считаем цену
            */
            VocListEntryTypeCode listEntryCode = listEntry.getEntryType().getCode();
            switch (listEntryCode) {
                case HOSPITAL: {
                    int i = 0;
                    for (BigInteger bi : list) {
                        i++;
                        final E2Entry entry = manager.find(E2Entry.class, bi.longValue());
                        makeCheckEntry(entry, updateKsgIfExist, true);
                        if (i % 100 == 0 && isMonitorCancel(monitor, "Расчитано " + i + " записей (СТАЦИОНАР)")) {
                            return;
                        }
                    }
                    monitor.setText("Идет процесс удаление дублей");
                    checkDoubles(listEntry);
                    break;
                }
                case POLYCLINIC:
                case POL_KDP:
                case SERVICE: {
                    Long listEntryId = listEntry.getId();
                    if (listEntryCode.equals(POLYCLINIC)) {
                        boolean isGroupSpo = getExpertConfigValue("ISGROUPSPO", "0").equals("1");
                        monitor.setText("Удаление дублей в поликлинике");
                        deletePolyclinicDoubles(listEntryId);
                        monitor.setText("Склеивание случаев поликлинического обслуживания");
                        unionPolyclinicMedCase(listEntryId, isGroupSpo);
                    }
                    int i = 0;
                    monitor.setText("Расчет цены случая (поликлиника)" + i);
                    for (BigInteger bi : list) {
                        i++;
                        if (i % 100 == 0) {
                            LOG.info("process pol ... checking entry.... " + i);
                            if (monitor.isCancelled()) {
                                LOG.info("Проверка прервана пользователем");
                                monitor.setText("Проверка прервана пользователем");
                                return;
                            }
                        }
                        //Теперь снова находим КСГ, расчитываем цену и коэффициенты
                        makeCheckEntry(manager.find(E2Entry.class, bi.longValue()), updateKsgIfExist, true);
                    }
                    monitor.setText("Приступаем к проверке перекрестных случаев.");
                    if (!POL_KDP.equals(listEntryCode)) {
                        markErrorForCrossSpo(listEntry);
                    }
                    break;
                }
                case EXTDISP: {
                    int i = 0;
                    for (BigInteger bi : list) {
                        i++;
                        if (i % 100 == 0) {
                            LOG.info("process disp ... checking entry.... " + i);
                            if (monitor.isCancelled()) {
                                LOG.info("Проверка прервана пользователем");
                                monitor.setText("Проверка прервана пользователем");
                                return;
                            }
                        }
                        makeCheckEntry(manager.find(E2Entry.class, bi.longValue()), true, true);
                    }
                    break;
                }
                default:
                    throw new IllegalStateException("Wrong type: " + listEntryCode);
            }
            long currentTime = System.currentTimeMillis();
            isMonitorCancel(monitor, "Закончена проверка! Время выполнения проверки (минут) = " + (currentTime - startStartDate) / 60000);
            listEntry.setCheckDate(new java.sql.Date(currentTime));
            listEntry.setCheckTime(new java.sql.Time(currentTime));
            manager.persist(listEntry);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info("check list entry finished!");
        cleanAllMaps();
        isCheckIsRunning = false;
    }

    @Override
    public void makeCheckEntry(Long entry, boolean updateKsgIfExist) {
        if (!manager.createNativeQuery("select e.id from e2entry e left join e2listentry el on el.id=e.listEntry_id where e.id=:id and el.isClosed='1'").setParameter("id", entry).getResultList().isEmpty()) {
            throw new IllegalStateException("Заполнение закрыто, проверка невозможна");
        }
        setIsConsultativePolyclinic();
        cleanAllMaps();
        makeCheckEntry(manager.find(E2Entry.class, entry), updateKsgIfExist, true);
    }

    /**
     * Запустить проверку случая (расчет КСГ, цены, полей для xml)
     */
    private void makeCheckEntry(E2Entry entry, boolean updateKsgIfExist, boolean checkErrors) {
        long bedDays = AgeUtil.calculateDays(entry.getStartDate(), entry.getFinishDate());
        long calendarDays = bedDays > 0 ? bedDays + 1 : 1;
        if (HOSPITAL.equals(entry.getEntryType()) && "2".equals(entry.getBedSubType())) { //Для дневного стационара день поступления и день выписки - 2 дня
            bedDays++;
        }
        try {
            setEntrySubType(entry);
            if (StringUtil.isNotEmpty(entry.getDepartmentAddressCode())) {
                entry.setDepartmentCode(entry.getDepartmentAddressCode().substring(0, Math.max(0, entry.getDepartmentAddressCode().length() - 3)));
            }

            entry.setIsForeign(isNotLogicalNull(entry.getInsuranceCompanyCode()) && !entry.getInsuranceCompanyCode().startsWith("30"));
            entry.setBedDays(Math.max(bedDays, 1L));
            try {
                entry.setIsChild(AgeUtil.calcAgeYear(entry.getBirthDate(), entry.getStartDate()) < 18);
            } catch (Exception e) {
                entry.setIsChild(false);
            }
            entry.setCalendarDays(calendarDays);
            getBestKSG(entry, updateKsgIfExist); //Находим КСГ
            calculateFondField(entry, updateKsgIfExist);
            entry = calculateEntryPrice(entry);
            if (checkErrors) {
                checkErrors(entry);
            }
            manager.persist(entry);
        } catch (Exception e) {
            LOG.error("ERR=" + entry.getId(), e);
            throw new IllegalStateException("Какая-то ошибка, формировать ничего не будем!");
        }
    }

    private void saveError(E2Entry entry, String errorCode) {
        saveError(entry, errorCode, null);
    }

    private void saveError(E2Entry entry, String errorCode, String comment) {
        manager.persist(new E2EntryError(entry, errorCode, comment));
    }

    private void setEntrySubType(E2Entry entry) {
        String code;
        String fileType;
        VocListEntryTypeCode entryType = entry.getEntryType();
        if (entryType == null && entry.getListEntry() != null) {
            entryType = entry.getListEntry().getEntryType().getCode();
            entry.setEntryType(entryType);
        }
        if (entryType == null) {
            return;
        }
        switch (entryType) {
            case HOSPITAL:
                if (isNeedSplitDayTimeHosp) {
                    entry.setAddGroupFld("1".equals(entry.getBedSubType()) ? "КС" : "ДС");
                }
                fileType = "H";
                if (isNotLogicalNull(entry.getVmpKind())) {
                    code = "STAC_VMP";
                    fileType = "T";
                } else if (isEquals(entry.getBedSubType(), "1")) {
                    code = (isTrue(entry.getIsRehabBed()) ? "REHAB_" : "") + ALLTIMEHOSP;
                } else if (isEquals(entry.getBedSubType(), "2")) {
                    //departmentType = 7 - Дневной стационар при АПУ
                    //departmentType = 8 - Дневной стационар на дому
                    code = (isTrue(entry.getIsRehabBed()) ? "REHAB_" : "") + ("7".equals(entry.getDepartmentType()) ? "POL"
                            : "8".equals(entry.getDepartmentType()) ? "HOME" : "") + DAYTIMEHOSP;
                } else if (isEquals(entry.getBedSubType(), "3")) { // первично стац на дому делаем правильным
                    entry.setDepartmentType("8");
                    entry.setBedSubType("2");
                    code = "HOMEDAYTIMEHOSP";
                } else {
                    code = "UNKNOWNTIMEHOSP";
                }
                break;
            case POLYCLINIC:
            case SERVICE:
                fileType = "H";
                String workPlace = entry.getWorkPlace();
                if (SERVICE.equals(entryType)) { //КТ-МРТ подаем типом записи УСЛУГА //TODO говнокод
                    if (entry.getDepartmentId().equals(416L)) {
                        code = "TELEMED_" + entry.getMainService();
                    } else {
                        code = SERVICE.name();
                    }

                } else if (isTrue(entry.getIsDiagnosticSpo())) {
                    code = "POL_KDO";
                } else {
                    if (isTrue(entry.getIsEmergency())) { // Случай НМП
                        code = "POL_EMERG";
                    } else { //поликлиника. либо Н либо Р в зависимости от настроек и профиля
                        if (isConsultativePolyclinic) {
                            fileType = "H";
                        } else {
                            VocE2FondV021 spec = entry.getFondDoctorSpecV021();
                            fileType = spec != null && isTrue(spec.getIsPodushevoy()) ? "P" : "H";
                        }
                        if (entry.getStartDate().getTime() == entry.getFinishDate().getTime()) { //разовый случай
                            String mainMkb = entry.getMainMkb();
                            if (isNotLogicalNull(mainMkb)) {
                                code = mainMkb.startsWith("Z") ? "VISIT_PROF" : "VISIT_ILL";
                            } else {
                                if (entry.getId() > 0) {
                                    List<String> mkbs = findDiagnosisCodes(manager.createQuery("from EntryDiagnosis where entry_id=:id").setParameter("id", entry.getId()).getResultList(), null, "1");
                                    boolean isProf = false;
                                    for (String mkb : mkbs) {
                                        if (mkb.startsWith("Z")) {
                                            isProf = true;
                                            break;
                                        }
                                    }
                                    code = isProf ? "VISIT_PROF" : "VISIT_ILL";
                                } else {
                                    code = "NO_CODE";
                                }
                            }
                        } else { //Обращение
                            code = "POL_LONGCASE";
                        }
                    }

                    code = (isTrue(entry.getIsMobilePolyclinic()) ? "MOBILE_" : isConsultativePolyclinic ? "CONS_" : "TERR_") + code;
                    code += "_" + (workPlace != null ? workPlace : "NO_WORKPLACE");
                    entry.setIsDentalCase(entry.getFondDoctorSpecV021() != null && isTrue(entry.getFondDoctorSpecV021().getIsDentalDoctor()));
                }
                break;
            case VMP:
                code = "STAC_VMP";
                fileType = "T";
                break;
            case EXTDISP:
                //определяемся что ДД будем получать только с элмеда, VIDSLUCH уже есть
                code = (entry.getSubType() != null
                        ? entry.getSubType().getCode()
                        : EXTDISP + "_" + (entry.getVidSluch() != null ? entry.getVidSluch().getCode() : "VIDSLUCH"));
                fileType = "SomeDisp";

                break;
            case POL_KDP:
                code = POL_KDP.name();
                fileType = "H";
                break;
            default:
                LOG.error(entry.getId() + " Неизвестный тип записи для проставление подтипа. CANT_SET_SUBTYPE: " + entryType);
                return;
        }
        if (!entrySubTypeHashMap.containsKey(code)) {
            entrySubTypeHashMap.put(code, getEntityByCode(code, VocE2EntrySubType.class, true));
        }
        VocE2EntrySubType subType = entrySubTypeHashMap.get(code);
        if (subType == null) {
            subType = getEntityByCode("UNKNOWN", VocE2EntrySubType.class, true);
            entrySubTypeHashMap.put(code, subType);
            if (entry.getId() > 0)
                saveError(entry, E2EntryErrorCode.NO_ENTRY_SUBTYPE, "Не найдено вида случая с кодом: " + code);
        }
        entry.setSubType(subType);
        if (subType != null) {
            if (subType.getVidSluch() != null) {
                entry.setVidSluch(subType.getVidSluch());
            } else {
                LOG.error("Не заполнен вид случая для записей с типом " + subType.getCode() + " " + subType.getName());
            }
            if (subType.getExtDispType() != null) {
                entry.setExtDispType(subType.getExtDispType().getCode());
            }
            entry.setVisitPurpose(subType.getVisitPurpose()); //Цель посещения (V025)
            entry.setMedHelpUsl(subType.getUslOk()); //Условия оказания находим согласно подтипу записи (V006)
            entry.setIdsp(subType.getIdsp());
            if (isTrue(entry.getIsCancer()) || isNotEmpty(entry.getCancerEntries())) {
                fileType = fileType.equals("P") ? "PC" : "C";
            }
            entry.setFileType(isNotLogicalNull(subType.getFileType()) ? subType.getFileType() : fileType);
        }
    }

    private void saveErrors(List<E2EntryError> errorList) {
        if (!errorList.isEmpty()) {
            for (E2EntryError error : errorList)
                manager.persist(error);
        }

    }

    /**
     * Проверка на дубли с другими заполнениями
     */
    private void checkDoubles(E2ListEntry listEntry) {
        List<BigInteger> list = manager.createNativeQuery(" select distinct nw.id " +
                " from e2entry nw" +
                " left join e2entry ol on ol.historyNumber=nw.historyNumber and ol.listentry_id!=:listEntryId and (ol.isDefect is null or ol.isDefect='0') and (ol.isDeleted is null or ol.isDeleted='0') and (ol.doNotSend is null or ol.doNotSend='0')" +
                " left join e2listentry listOld on listOld.id=ol.listentry_id" +
                " left join e2bill bill on bill.id=ol.bill_id" +
                " left join voce2billstatus bs on bs.id=bill.status_id" +
                " where nw.listentry_id=:listEntryId and (nw.isDeleted is null or nw.isDeleted='0') and (nw.doNotSend is null or nw.doNotSend='0') and (listOld.isDeleted is null or listOld.isDeleted='0')" +
                " and nw.startDate<ol.finishDate and ol.servicestream!='COMPLEXCASE' and nw.servicestream!='COMPLEXCASE'" +
                " and ol.medhelpprofile_id=nw.medhelpprofile_id and bs.code='PAID'").setParameter("listEntryId", listEntry.getId()).getResultList();
        for (BigInteger id : list) {
            saveError(manager.find(E2Entry.class, id.longValue()), E2EntryErrorCode.DOUBLE_WITH_PREVIOUS, "Дубль с пред. заполнением!!");
        }
    }

    private void checkErrors(E2Entry entry) {
        if (OMC_SERVICE_STREAM.equals(entry.getServiceStream())) { //Проверка только для ОМС *05.06.2018
            List<E2EntryError> errors = new ArrayList<>();
            //Дата выписки не входит в период
            if (entry.getFinishDate().getTime() > entry.getListEntry().getFinishDate().getTime()
                    || entry.getFinishDate().getTime() < entry.getListEntry().getStartDate().getTime()) {
                errors.add(new E2EntryError(entry, E2EntryErrorCode.DISCHARGE_DATE_NOT_IN_PERIOD));
            }
            if (HOSPITAL.equals(entry.getEntryType()) && entry.getKsg() == null) {
                errors.add(new E2EntryError(entry, E2EntryErrorCode.NO_KSG));
            }
            if (isTrue(entry.getIsForeign()) && (isLogicalNull(entry.getPassportDateIssued()) || isLogicalNull(entry.getPassportNumber())
                    || isLogicalNull(entry.getPassportSeries()) || isLogicalNull(entry.getPassportWhomIssued()))) {
                errors.add(new E2EntryError(entry, E2EntryErrorCode.NO_PASSPORT_INOG));
            }
            if (!errors.isEmpty()) {
                saveErrors(errors);
            }
        }
    }

    /**
     * Расчет цены случая
     *
     * @param entry
     */
    @Override
    public E2Entry calculateEntryPrice(E2Entry entry) {
        VocListEntryTypeCode entryType = entry.getEntryType() != null ? entry.getEntryType() : entry.getEntryListType();
        switch (entryType) {
            case HOSPITAL:
            case VMP:
                calculateHospitalEntryPrice(entry);
                break;
            case POLYCLINIC:
            case POL_KDP:
                calculatePolyclinicEntryPrice(entry);
                break;
            case EXTDISP:
                calculateExtDispEntryPrice(entry);
                break;
            case SERVICE:
                calculateServiceEntryPrice(entry);
                break;
            default:
                throw new IllegalStateException("Неизвестный тип реестра : " + entryType);
        }
        return entry;
    }

    /**
     * Получить сущность по коду (в основном для справочников)
     */
    private <T> T getEntityByCode(String code, Class<T> clazz, boolean needCreate) {
        if (code == null) {
            return null;
        }
        List<T> list = manager.createQuery("from " + clazz.getName() + " where code=:code").setParameter("code", code).getResultList();
        T ret = !list.isEmpty() ? list.get(0) : null;
        if (list.isEmpty() && needCreate) {
            try {
                ret = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LOG.error(e.getMessage(), e);
            }
            if (ret instanceof VocBaseEntity) {
                try {
                    Method setCodeMethod = ret.getClass().getMethod("setCode", String.class);
                    setCodeMethod.invoke(ret, code);
                    LOG.info("create new voc(" + clazz.getName() + ").set code = " + code);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error(e.getMessage(), e);
                }
            }
            manager.persist(ret);
        }
        return ret;
    }

    private Map<Long, OncologyCase> findOncologyCasesMyMedcaseId(List<Long> medcaseIds) {
        List<OncologyCase> oncologyCases = (List<OncologyCase>) manager.createQuery("" +
                        " select oc from OncologyCase oc" +
//                        " left join fetch oc.directions" +
//                        " left join fetch oc.drugs" +
//                        " left join fetch oc.diagnostics" +
                        " where oc.medCaseId in (:ids)")
                .setParameter("ids", medcaseIds)
                .getResultList();
        return oncologyCases.stream().collect(Collectors.toMap(k -> k.getMedCaseId(), v -> v, (val1, val2) -> val1));

    }

    /*Находим онкологический случай по СЛС */
    private void findCancerEntry(List<E2Entry> entryies) {
        List<Long> medcaseIds = entryies.stream()
                .map(E2Entry::getExternalParentId)
                .collect(Collectors.toList());
        Map<Long, OncologyCase> oncologyCaseMap = findOncologyCasesMyMedcaseId(medcaseIds);
        for (E2Entry entry : entryies) {
            if (COMPLEXSERVICESTREAM.equals(entry.getServiceStream())) return;

            OncologyCase oncologyCase = oncologyCaseMap.get(entry.getExternalParentId());
            if (oncologyCase != null) {
                List<E2CancerEntry> cancerEntryList = new ArrayList<>();
                try {
                    E2CancerEntry cancerEntry = new E2CancerEntry(entry);
                    cancerEntry.setMaybeCancer(oncologyCase.getSuspicionOncologist());
                    if (isTrue(cancerEntry.getMaybeCancer())) { //Подозрение на онкологию, ищем только направления
                        List<OncologyDirection> directions = oncologyCase.getDirections();
                        if (!directions.isEmpty()) {
                            List<E2CancerDirection> list = new ArrayList<>();
                            for (OncologyDirection direction : directions) {
                                E2CancerDirection cancerDirection = new E2CancerDirection(cancerEntry);
                                cancerDirection.setDate(direction.getDate());
                                cancerDirection.setType(ofNullable(direction.getTypeDirection()).map(VocIdCodeName::getCode).orElse(""));
                                cancerDirection.setMedService(ofNullable(direction.getMedService()).map(VocIdCodeName::getCode).orElse(""));
                                cancerDirection.setSurveyMethod(ofNullable(direction.getMethodDiagTreat()).map(VocIdCodeName::getCode).orElse(""));
                                cancerDirection.setDirectLpu(ofNullable(direction.getDirectLpu()).map(MisLpu::getCodef).orElse(""));
                                list.add(cancerDirection);
                            }
                            cancerEntry.setDirections(list);
                        }
                    } else { //Случай онкологического лечения
                        cancerEntry.setOccasion(oncologyCase.getVocOncologyReasonTreat() != null ? oncologyCase.getVocOncologyReasonTreat().getCode() : "");
                        cancerEntry.setStage(oncologyCase.getStad() != null ? oncologyCase.getStad().getCode() : "");
                        cancerEntry.setTumor(oncologyCase.getTumor() != null ? oncologyCase.getTumor().getCode() : "");
                        cancerEntry.setNodus(oncologyCase.getNodus() != null ? oncologyCase.getNodus().getCode() : "");
                        cancerEntry.setMetastasis(oncologyCase.getMetastasis() != null ? oncologyCase.getMetastasis().getCode() : "");
                        cancerEntry.setIsMetastasisFound(oncologyCase.getDistantMetastasis());
                        cancerEntry.setSod(oncologyCase.getSumDose());
                        cancerEntry.setConsiliumResult(oncologyCase.getConsilium() != null ? oncologyCase.getConsilium().getCode() : "");
                        cancerEntry.setConsiliumDate(oncologyCase.getDateCons());
                        cancerEntry.setServiceType(oncologyCase.getTypeTreatment() != null ? oncologyCase.getTypeTreatment().getCode() : "");
                        cancerEntry.setSurgicalType(oncologyCase.getSurgTreatment() != null ? oncologyCase.getSurgTreatment().getCode() : "");
                        cancerEntry.setDrugLine(oncologyCase.getLineDrugTherapy() != null ? oncologyCase.getLineDrugTherapy().getCode() : "");
                        cancerEntry.setDrugCycle(oncologyCase.getCycleDrugTherapy() != null ? oncologyCase.getCycleDrugTherapy().getCode() : "");
                        cancerEntry.setRadiationTherapy(oncologyCase.getTypeRadTherapy() != null ? oncologyCase.getTypeRadTherapy().getCode() : "");
                        List<OncologyContra> prots = oncologyCase.getContras();
                        if (!prots.isEmpty()) {
                            List<E2CancerRefusal> list = new ArrayList<>();
                            for (OncologyContra prot : prots) {
                                E2CancerRefusal refusal = new E2CancerRefusal(cancerEntry);
                                refusal.setCode(prot.getContraindicationAndRejection().getCode());
                                refusal.setDate(prot.getDate());
                                list.add(refusal);
                            }
                            cancerEntry.setRefusals(list);
                        }
                        try {
                            List<OncologyDrug> drugList = oncologyCase.getDrugs();
                            for (OncologyDrug drug : drugList) {
                                E2CancerDrug cancerDrug = new E2CancerDrug(cancerEntry);
                                cancerDrug.setDrug(drug.getDrug());
                                List<E2CancerDrugDate> drugDates = new ArrayList<>();
                                for (OncologyDrugDate oncologyDrugDate : drug.getDates()) {
                                    E2CancerDrugDate drugDate = new E2CancerDrugDate(cancerDrug);
                                    drugDate.setDate(oncologyDrugDate.getDate());
                                    drugDates.add(drugDate);
                                }
                                cancerDrug.setDates(drugDates);
                                manager.persist(cancerDrug);
                            }

                            List<OncologyDiagnostic> diags = oncologyCase.getDiagnostics();
                            if (!diags.isEmpty()) {
                                List<E2CancerDiagnostic> list = new ArrayList<>();
                                for (OncologyDiagnostic diag : diags) {
                                    E2CancerDiagnostic cancerDiagnostic = new E2CancerDiagnostic(cancerEntry);
                                    String diagnosticType = diag.getVocOncologyDiagType() != null ? diag.getVocOncologyDiagType().getCode() : null;
                                    if (diagnosticType != null) {
                                        cancerDiagnostic.setBiopsyDate(oncologyCase.getDateBiops());
                                        cancerDiagnostic.setType(diagnosticType);
                                        if (diagnosticType.equals("1")) {
                                            cancerDiagnostic.setCode(diag.getHistiology().getCode());
                                            cancerDiagnostic.setResult(diag.getResultHistiology().getCode());
                                        } else if (diagnosticType.equals("2")) {
                                            cancerDiagnostic.setCode(diag.getMarkers() != null ? diag.getMarkers().getCode() : "");
                                            cancerDiagnostic.setResult(diag.getValueMarkers() != null ? diag.getValueMarkers().getCode() : "");
                                        }
                                        list.add(cancerDiagnostic);
                                    }
                                }
                                cancerEntry.setDiagnostics(list);
                            }
                        } catch (Exception e) {
                            LOG.error("Ошибка при заполнении диагностиеческого блока! ", e);
                        }
                    }
                    manager.persist(cancerEntry);
                    cancerEntryList.add(cancerEntry);
                } catch (Exception e) {
                    LOG.error("Не смог создать раковый случай ", e);
                }
                entry.setFileType("C");
                entry.setCancerEntries(cancerEntryList);
            }
        }
    }

    private List<EntryDiagnosis> getDiagnosis(E2Entry entry) {
        return entry.getDiagnosis() != null ? entry.getDiagnosis() : new ArrayList<>();
    }

    //на 02.07.2021 - С* || DS1<D11 || D45<=DS1<=D47
    //30.05.2022 - Да, вот так:  «С» или код основного диагноза входит в диапазон D00-D09 или D45-D47
    private boolean isCancerMkb(String mkb, String priority) {
        return priority.equals("1") &&
                (mkb.startsWith("C")
                        || mkb.startsWith("D") && (parseInt(mkb.substring(1, 3)) < 10 || inRange(parseInt(mkb.substring(1, 3)), 45, 47)));
    }

    //входит ли номер в промежуток
    private boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    private void createDiagnosis(E2Entry entry) {
        try {
            String diagnosisList = entry.getDiagnosisList();
            if (isNotLogicalNull(diagnosisList)) { //Создаем диагнозы для каждой записи
                JSONArray diagnosiss = new JSONArray(diagnosisList);
                boolean foundClinical = false, foundDischarge = false;
                List<EntryDiagnosis> clinicalDiagnoses = new ArrayList<>();
                boolean isCancer = false;
                List<EntryDiagnosis> entryDiagnoses = new ArrayList<>();
                for (int i = 0; i < diagnosiss.length(); i++) {
                    JSONObject ds = diagnosiss.getJSONObject(i);
                    String mkb = ds.getString("mkb");
                    String regType = ds.getString("registrationType");
                    String priority = ds.getString("priority");
                    if (!isCancer && isCancerMkb(mkb, priority)) {
                        isCancer = true;
                    }
                    if (covidMkbs.contains(mkb) && isLogicalNull(entry.getDopKritKSG())) {
                        saveError(entry, E2EntryErrorCode.COVID_NO_CARD);
                    }
                    boolean isClinical = false;
                    if (!diagnosisMap.containsKey("MKB_" + mkb)) {
                        diagnosisMap.put("MKB_" + mkb, getEntityByCode(mkb, VocIdc10.class, false));
                    }
                    if (!diagnosisMap.containsKey("REGTYPE_" + regType)) {
                        diagnosisMap.put("REGTYPE_" + regType, getEntityByCode(regType, VocDiagnosisRegistrationType.class, false));
                        if (regType.equals("3")) {
                            foundDischarge = true;
                        } else if (regType.equals("4")) {
                            foundClinical = true;
                            isClinical = true;
                        }
                    }
                    if (!diagnosisMap.containsKey("PRIORITY_" + priority)) {
                        diagnosisMap.put("PRIORITY_" + priority, getEntityByCode(priority, VocPriorityDiagnosis.class, false));
                    }
                    if (foundDischarge && isClinical) {
                        foundClinical = false;
                        continue;
                    } //Если нашли клинический и выписной - не создаем клинический

                    EntryDiagnosis diagnosis = new EntryDiagnosis();
                    diagnosis.setEntry(entry);
                    VocIdc10 vocIdc10 = (VocIdc10) diagnosisMap.get("MKB_" + mkb);
                    if (vocIdc10.getCode().indexOf('.') == -1 && (vocIdc10.getIsPermitWithoutDot() == null || !vocIdc10.getIsPermitWithoutDot())) { //Если диагноз без расшифровки и он не разрешен к использованию без уточнения
                        saveError(entry, E2EntryErrorCode.DIAGNOSIS_WITHOUT_UTOCHNENIE);
                    }
                    diagnosis.setMkb(vocIdc10);
                    diagnosis.setRegistrationType((VocDiagnosisRegistrationType) diagnosisMap.get("REGTYPE_" + regType));
                    diagnosis.setPriority((VocPriorityDiagnosis) diagnosisMap.get("PRIORITY_" + priority));
                    diagnosis.setIllnessPrimary(ds.getString("illnessPrimary"));
                    if (ds.has("vocIllnessPrimary")) {
                        String vip = ds.getString("vocIllnessPrimary");
                        if (isNotLogicalNull(vip))
                            diagnosis.setVocIllnessPrimary(getEntityByCode(vip, VocE2FondV027.class, false));
                    }
                    if (isLogicalNull(entry.getMainMkb())
                            && diagnosis.getRegistrationType() != null && diagnosis.getRegistrationType().getCode().equals("4")
                            && diagnosis.getPriority() != null && diagnosis.getPriority().getCode().equals("1") ||
                            isOneOf(entry.getEntryType(), POLYCLINIC, SERVICE) && diagnosis.getPriority() != null && diagnosis.getPriority().getCode().equals("1")) {
                        entry.setMainMkb(mkb);
                        manager.persist(entry);
                    }
                    String dopMkb = ds.getString("addMkb");
                    if (isNotLogicalNull(dopMkb)) diagnosis.setDopMkb(dopMkb);
                    entryDiagnoses.add(diagnosis);
                    if (isClinical) {
                        clinicalDiagnoses.add(diagnosis);
                    }
                }

                entry.setIsCancer(isCancer);
                if (isCancer) entry.setFileType("C");
                manager.persist(entry);
                if (foundClinical && foundDischarge) { //Если нашли и клинический и выписной диагноз - удаляем клинический.
                    entryDiagnoses.removeAll(clinicalDiagnoses);
                }
                deleteDiagnosisDoubles(entryDiagnoses);
                for (EntryDiagnosis d : entryDiagnoses) {
                    manager.persist(d);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    //если есть основной и сопутствующий - удалим соп
    private void deleteDiagnosisDoubles(List<EntryDiagnosis> diagnoses) {
        if (isNotEmpty(diagnoses)) {
            List<EntryDiagnosis> doubles = new ArrayList<>();
            List<VocIdc10> mainMkbs = new ArrayList<>();
            for (EntryDiagnosis d : diagnoses) {
                if (nonNull(d.getPriority()) && isEquals(d.getPriority().getCode(), "1")) {
                    mainMkbs.add(d.getMkb());
                }
            }
            for (EntryDiagnosis d : diagnoses) {
                if (mainMkbs.contains(d.getMkb()) && (d.getPriority() != null && !isEquals(d.getPriority().getCode(), "1"))) {
                    doubles.add(d);
                }
            }
            diagnoses.removeAll(doubles);
        }
    }

    /**
     * Создание списка услуг по записи + делаем главную услугу
     */ //делаем разово
    private void createServices(E2Entry entry) {
        List<EntryMedService> servicesList = entry.getMedServices();
        if (isNotEmpty(servicesList)) {
            return;
        }
        try {
            String operationList = entry.getOperationList();
            JSONArray services = new JSONArray();
            if (isNotLogicalNull(operationList)) {
                services = new JSONArray(operationList);
                //Делаем проверку на роды. Если отделение - обсервационное (27-12-2018), диагнозы входят в список, то создаем услуги
                if (isNotLogicalNull(entry.getDepartmentId()) && entry.getDepartmentId() == 212 && isNotLogicalNull(entry.getMainMkb())) {
                    String patologicDs = "O60.1,O60.2,O84.0,O36.4";
                    String fiziologicDs = "O80.0,O80.1";
                    if (patologicDs.contains(entry.getMainMkb())) { //Если подходящий диагноз по патологическим родам
                        if (!"B01.001.006".contains(operationList)) { //И в списке услуг нет нужной услуги - добавим её
                            JSONObject pat = new JSONObject();
                            pat.put("serviceCode", "B01.001.006");
                            pat.put("serviceDate", dateToString(entry.getStartDate()));
                            services.put(pat);
                        }
                    } else if (fiziologicDs.contains(entry.getMainMkb()) && !"B01.001.009".contains(operationList)) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("serviceCode", "B01.001.009");
                        jsonObject.put("serviceDate", dateToString(entry.getStartDate()));
                        services.put(jsonObject);
                    }
                }
            }
            JSONArray tmp = new JSONArray(entry.getServices());
            if (tmp.length() > 0) {
                for (int i = 0; i < tmp.length(); i++) {
                    services.put(tmp.getJSONObject(i));
                }
            }

            if (isNotLogicalNull(entry.getPrescriptionList())) {
                tmp = new JSONArray(entry.getPrescriptionList());
                if (tmp.length() > 0) {
                    for (int i = 0; i < tmp.length(); i++) {
                        services.put(tmp.getJSONObject(i));
                    }
                }
            }
            if (!services.isEmpty()) {
                String dateFrormat = "yyyy-MM-dd";
                for (int i = 0; i < services.length(); i++) {
                    JSONObject service = services.getJSONObject(i);
                    if (!service.has("serviceCode")) {
                        continue;
                    }

                    String code = service.getString("serviceCode");
                    if (isNullOrEmpty(code)) {
                        saveError(entry, E2EntryErrorCode.BAD_SERVICE_CODE, service.toString());
                        continue;
                    }
                    if (services.length() == 1) { //Если ТОЛЬКО одна услуга/операция в СЛО, её и считаем главной
                        entry.setMainService(code);
                        manager.persist(entry);
                    }
                    //Добавляем услугу, если только она используется в ОМС
                    VocMedService vms;
                    String workfunction = service.has("workfunctionCode") ? service.getString("workfunctionCode") : null;
                    if ("FINDME_SYSTEM".equals(code)) { //Находим первичный прием по профилю специалиста
                        code = code + "#" + workfunction;
                        SERVICELIST.put(code, findDefaultMedServiceByWorkfunction(workfunction));
                    }
                    if (!SERVICELIST.containsKey(code)) {
                        vms = getEntityByCode(code, VocMedService.class, false);
                        SERVICELIST.put(code, vms);
                    } else {
                        vms = SERVICELIST.get(code);
                    }
                    if (vms != null) {
                        EntryMedService ms = new EntryMedService(entry, vms);
                        if (service.has("workfunctionSnils")) ms.setDoctorSnils(service.getString("workfunctionSnils"));
                        String serviceDate = service.has("serviceDate") ? service.getString("serviceDate") : null;
                        Date medServiceDate = serviceDate != null ? DateFormat.parseSqlDate(serviceDate, dateFrormat) : null;
                        ms.setServiceDate(medServiceDate);
                        String costKey = "SERVICECOST#" + code;
                        BigDecimal cost;
                        if (!resultMap.containsKey(costKey)) {
                            cost = getMedServiceCost(vms, medServiceDate);
                            resultMap.put(costKey, cost);
                        } else {
                            cost = (BigDecimal) resultMap.get(costKey);
                        }
                        if (service.has("uet")) {
                            BigDecimal uet = BigDecimal.valueOf(service.getDouble("uet"));
                            ms.setUet(uet);
                        }
                        ms.setCost(cost);
                        if (isNotLogicalNull(workfunction)) {
                            VocE2FondV021 doctor;
                            String key = "DOCTOR#" + workfunction;
                            if (!resultMap.containsKey(key)) {
                                doctor = getActualVocByCode(VocE2FondV021.class, workfunction);
                                resultMap.put(key, doctor);
                                if (doctor == null) LOG.error("S_Не нашел доктора по коду V021 = " + workfunction);
                            } else {
                                doctor = (VocE2FondV021) resultMap.get(key);
                            }
                            ms.setDoctorSpeciality(doctor);
                        }
                        if (service.has("diagnosisCode")) {
                            String mkb = service.getString("diagnosisCode");
                            if (isNotLogicalNull(mkb)) {
                                ms.setMkb(getEntityByCode(mkb, VocIdc10.class, false));
                            }
                        }
                        if (service.has("lpuCode")) {
                            ms.setLpuCode(service.getString("lpuCode"));
                        }
                        manager.persist(ms);
                        if (service.has("medImplant") && isNotLogicalNull(service.getString("medImplant"))) {
                            String[] implants = service.getString("medImplant").split(";");
                            for (String implant : implants) {
                                String[] dta = implant.split(":");
                                manager.persist(new EntryMedServiceMedImplant(ms, dta[0], dta[1]));
                            }
                        }
                    }
                }
            }
            makeDrugEntry(entry);
        } catch (Exception e) {
            LOG.error("error creating service = " + entry.getOperationList() + "<>" + entry.getServices() + "<>" + entry.getPrescriptionList(), e);
        }
    }

    private void makeDrugEntry(E2Entry entry) {
        if (isNotLogicalNull(entry.getCovidPrescriptions())) {
            JSONArray services = new JSONArray(entry.getCovidPrescriptions());
            for (int i = 0; i < services.length(); i++) {
                JSONObject covid = services.getJSONObject(i);
                E2DrugEntry de = new E2DrugEntry(entry);
                de.setDrug(getActualVocByCode(VocE2FondN020.class, covid.getString("serviceCode")));
                de.setDrugGroupSchema(getActualVocByCode(VocE2FondV032.class, covid.getString("covidSchema")));
                de.setInjectMethod(getActualVocByCode(VocE2FondV035.class, covid.getString("methodCode")));
                de.setInjectAmount(covid.getString("amount"));

                Date injectDate;
                try {
                    injectDate = DateFormat.parseSqlDate(covid.getString("serviceDate"), "yyyy-MM-dd");
                } catch (ParseException e) {
                    LOG.warn("Не смог получить дату: " + covid.getString("serviceDate") + ": " + e.getMessage());
                    injectDate = null;
                }
                de.setInjectDate(injectDate);
                de.setInjectNumber(covid.getInt("frequency"));
                de.setInjectUnit(getActualVocByCode(VocE2FondV034.class, covid.getString("injectUnit")));
                manager.persist(de);
            }

        }
    }


    /*Нахождение услуги по умолчанию по специальности врача*/
    private VocMedService findDefaultMedServiceByWorkfunction(String workfunctionCode) {
        String sql = "select v021.defaultmedservice_id" +
                " from voce2fondv021 v021 " +
                " where v021.code = :workFunction and v021.defaultmedservice_id is not null";
        List<Object> list = manager.createNativeQuery(sql)
                .setParameter("workFunction", workfunctionCode)
                .setMaxResults(1)
                .getResultList();
        if (list.isEmpty()) {
            LOG.error("Не найдено услуги по умолчанию для профиля специальности: " + workfunctionCode);
            return null;
        }
        return manager.find(VocMedService.class, Long.valueOf(list.get(0).toString()));
    }

    /**
     * Создаение полиса для случая
     */ //запускаем только один раз
    private void makeMedPolicy(E2Entry entry) {
        try {
            if (isNotLogicalNull(entry.getMedPolicyNumber())) {
                return;
            } //Если номер полиса проставлен - выходим
            String serviceStream = entry.getServiceStream();
            JSONArray medPolicy;
            if (isNotLogicalNull(entry.getPolicyMedcaseString())) {
                medPolicy = new JSONArray(entry.getPolicyMedcaseString());
            } else {
                medPolicy = new JSONArray(entry.getPolicyKinsmanString());
            }

            if (medPolicy.isEmpty() && isNotLogicalNull(entry.getPolicyPatientString())) {
                medPolicy = new JSONArray(entry.getPolicyPatientString());
            }
            if (medPolicy.isEmpty()) {
                if (serviceStream == null || serviceStream.equals(OMC_SERVICE_STREAM)) {
                    saveError(entry, E2EntryErrorCode.NO_MED_POLICY);
                }
                return;
            }
            JSONObject policy = medPolicy.getJSONObject(0);
            entry.setCommonNumber(policy.getString("commonNumber"));
            entry.setInsuranceCompanyTerritory(policy.getString("companyCity"));
            if (!policy.has("smoCode") || isLogicalNull(policy.getString("smoCode"))) {
                saveError(entry, E2EntryErrorCode.NO_MED_POLICY);
            }
            entry.setInsuranceCompanyCode(policy.getString("smoCode"));

            entry.setInsuranceCompanyName(policy.getString("companyName"));
            entry.setInsuranceCompanyOgrn(policy.getString("smoOgrn"));
            String polType = policy.getString("polType");
            entry.setMedPolicyType(isNotLogicalNull(polType) ? polType : "3"); //По умолчанию ставим тип полиса - полис нового образца *28-08-2018
            entry.setMedPolicySeries(policy.getString("polSeries"));
            entry.setMedPolicyNumber(policy.getString("polNumber"));
            manager.persist(entry);
        } catch (Exception e) {
            LOG.error("Не смог получить данные полиса: " + entry.getId(), e);
        }
    }

    /**
     * Находим, является ли КСГ политравмой
     */
    private VocKsg getPolitravmaKsg(List<String> mainDiagnosisList, List<String> otherDiagnosisList) {
        boolean foundMain = mainDiagnosisList.stream()
                .anyMatch(politravmaMainList::contains);
        //Если нашли главный диагноз, ищем сопутствующий
        return foundMain && otherDiagnosisList.stream().anyMatch(politravmaSeconaryList::contains) ? getActualVocByCode(VocKsg.class, "st29.007") : null;
    }

    private VocKsg getBestKSG(E2Entry entry, boolean updateKsgIfExist) {
        if (!HOSPITAL.equals(entry.getEntryType())) {
            return null;
        }
        if (!updateKsgIfExist && entry.getKsg() != null || isTrue(entry.getIsManualKsg())) {
            return entry.getKsg();
        }
        try {
            List<EntryDiagnosis> diagnosisList = getDiagnosis(entry);
            if (diagnosisList.isEmpty()) {
                saveError(entry, E2EntryErrorCode.NO_DIAGNOSIS);
            }

            String bedType = entry.getBedSubType();
            StringBuilder dopmkb = new StringBuilder();
            boolean isCancer = false;
            for (EntryDiagnosis ed : diagnosisList) {
                if (ed != null && ed.getPriority() != null) {
                    String priorityCode = ed.getPriority().getCode();
                    if ("1".equals(priorityCode)) {
                        if (isNotLogicalNull(ed.getDopMkb())) {
                            dopmkb.append("'").append(ed.getDopMkb()).append("',");
                        }
                        isCancer = ed.getMkb().getCode().startsWith("C") || ed.getMkb().getCode().startsWith("D");
                    } else if (isOneOf(priorityCode, "3", "4")) { //доп. коды для группировщика
                        dopmkb.append("'").append(ed.getMkb().getCode()).append("',");
                    }
                }
            }

            List<String> mainDiagnosis = findDiagnosisCodes(diagnosisList, "3", "1"); //Тип регистрации:1,2-при поступлении, 3  - выписной, 4 = клинический
            if (mainDiagnosis.isEmpty()) { //Нет выписного диагноза - возьмем клинический
                mainDiagnosis = findDiagnosisCodes(diagnosisList, "4", "1");
            }
            if (mainDiagnosis.isEmpty() && isNotLogicalNull(entry.getMainMkb())) {
                mainDiagnosis.add(entry.getMainMkb());
            }

            if (bedType.equals("1")) { //Проверим - является ли сочетание диагноза политравмой
                VocKsg politravma = getPolitravmaKsg(mainDiagnosis, findDiagnosisCodes(diagnosisList, "4", "34"));
                if (politravma != null) {
                    entry.setKsg(politravma);
                    manager.persist(entry);
                    return politravma;
                }
            }

            String ksgAge = findKSGAge(entry.getHospitalStartDate(), entry.getBirthDate());
            List<String> serviceCodes = new ArrayList<>();
            String cancerDiagnosis = null;
            String cancerDiagnosisSql = "";
            //Если оказано несколько услуг, ищем по всем услугам
            List<EntryMedService> serviceList1 = entry.getMedServices();
            boolean findCDiagnosis = false;
            if (isNotEmpty(serviceList1)) {
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
                    char mkbClass = d.charAt(0);
                    int mkbNum = parseInt(d.substring(1, 3));
                    if ((mkbClass == 'C' && mkbNum < 97) || (mkbClass == 'D' && (mkbNum < 10 || mkbNum > 44 && mkbNum < 48))) {
                        cancerDiagnosis = d;
                    } else if (mkbClass == 'C') {
                        findCDiagnosis = true;
                    } else if (mkbClass == 'I') {
                        cancerDiagnosisSql = " or gkp.mainmkb='I.'";
                    }
                }
                if (!cancerDiagnosisSql.equals("")) {
                    mainDiagnosis.add("I.");
                }
                if (isCancer) {
                    if (cancerDiagnosis != null) { //Костыль по нахождению Д в интервале, TODO переделать на нормальное нахождение диагноза в интервале
                        int mkbNumCancer = parseInt(cancerDiagnosis.substring(1, 3));
                        if (cancerDiagnosis.startsWith("C")) {
                            if (mkbNumCancer < 81) {
                                cancerDiagnosisSql = " or gkp.mainMkb='C00-C80' or gkp.mainmkb='C.'";
                                cancerDiagnosis = "C00-C80";
                            } else {
                                cancerDiagnosisSql = " or gkp.mainMkb='C81-C96' or gkp.mainmkb='C.'";
                                cancerDiagnosis = "C81-C96";
                            }
                        } else {
                            if (mkbNumCancer < 10) { // D00-D09
                                cancerDiagnosisSql = "or gkp.mainMkb='D00-D09'";
                                cancerDiagnosis = "D00-D09";
                            } else { //D45-D47
                                cancerDiagnosisSql = "or gkp.mainMkb='D45-D47'";
                                cancerDiagnosis = "D45-D47";
                            }
                        }

                    } else if (findCDiagnosis) {
                        cancerDiagnosisSql = " or gkp.mainmkb='C.'";
                    }
                }

                sql.append(" and ((gkp.mainmkb is null or gkp.mainmkb ='') or gkp.mainmkb in (").append(sb).append(")").append(cancerDiagnosisSql).append(")");
            } else {
                sql.append(" and (gkp.mainmkb is null or gkp.mainmkb ='')");
            }
            sql.append(" and ((gkp.anothermkb is null or gkp.anothermkb ='') ").append(dopmkb.length() > 0 ? " or gkp.anothermkb in (" + dopmkb.substring(0, dopmkb.length() - 1) + "))" : ")"); //Ищем по доп. коду

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
            String key = mainDiagnosis.hashCode() + "#SQL#" + sql.toString().hashCode();
            if (!ksgMap.containsKey(key)) {
//                LOG.info(key + " not found new sql =" + sql);
                LocalDate date = entry.getFinishDate().toLocalDate();
                results = manager.createNativeQuery(sql.toString()).setParameter("year", date.getYear()).getResultList();
                ksgMap.put(key, results);
                if (results.isEmpty()) {
                    LOG.warn(key + " Не смог найти КСГ для случая с ИД=" + entry.getId() + " по запросу: " + sql);
                    entry.setKsg(null);
                    entry.setKsgPosition(null);
                }
            } else {
                results = ksgMap.get(key);
            }
            double maxKZ = 0.0;
            int maxWeight = 0;
            int weight;
            GrouperKSGPosition pos = null;
            int ksgDuration = getKsgDuration(entry.getBedDays());
            GrouperKSGPosition therapicKsgPosition = null;
            GrouperKSGPosition surgicalKsgPosition = null;
            GrouperKSGPosition cancerKsgPosition = null;
            List<GrouperKSGPosition> justServicePositions = new ArrayList<>(); //24.09.18 * Если нашли дорогие позиции КСГ, но выбраи более дешевое КСГ, сохраним информацию для эксперта Аношкина
            for (BigInteger o : results) {
                GrouperKSGPosition ksgPosition = manager.find(GrouperKSGPosition.class, o.longValue());
                VocKsg ksg = ksgPosition.getKsgValue();
                weight = 0; //Вес найденного КСГ
                if (isEquals(ksgPosition.getDopPriznak(), entry.getDopKritKSG())) {
                    weight = 6;
                } else if (isNotLogicalNull(ksgPosition.getDopPriznak())) {
                    continue;
                }
                if (isNotLogicalNull(ksgPosition.getSex()) && !isEquals(ksgPosition.getSex(), entry.getSex())) {
                    continue;
                }
                if (mainDiagnosis.contains(ksgPosition.getMainMKB())) {
                    weight++;
                    if (isLogicalNull(ksgPosition.getServiceCode())) { //Находим терапевтичесое КСГ
                        therapicKsgPosition = therapicKsgPosition != null && therapicKsgPosition.getKsgValue().getKz() > ksgPosition.getKsgValue().getKz() ? therapicKsgPosition : ksgPosition;
                    }

                } else if (isCancer && isOneOf(ksgPosition.getMainMKB(), "C.", cancerDiagnosis)) {
                    cancerKsgPosition = ksgPosition;
                    weight++;
                    if (isEquals(ksgPosition.getMainMKB(), cancerDiagnosis)) {
                        weight++; //проверить TODO
                    }
                } else if (isNotLogicalNull(ksgPosition.getMainMKB())) {
                    continue;
                }

                if (serviceCodes.contains(ksgPosition.getServiceCode())) {
                    weight++;
                    surgicalKsgPosition = surgicalKsgPosition != null && surgicalKsgPosition.getKsgValue().getKz() > ksgPosition.getKsgValue().getKz() ? surgicalKsgPosition : ksgPosition;
                    //Если коронарография и у нас есть диагноз, берем дешевое КСГ! 09-02-2018
                    if ("A16.12.033".equals(ksgPosition.getServiceCode())) {
                        if (mainDiagnosis.contains(ksgPosition.getMainMKB())) {
                            weight = 5;
                        }
                    } else if (isOneOf(ksgPosition.getMainMKB(), "N18.5", "N18.4")) {
                        weight = 5;
                    }

                } else if (isNotLogicalNull(ksgPosition.getServiceCode())) {
                    continue;
                }
                if (ksgPosition.getAge() != null && !ksgAge.contains("" + ksgPosition.getAge())) {
                    continue;
                }
                if (ksgPosition.getDuration() != null && !isEquals(ksgPosition.getDuration(), ksgDuration)) {
                    continue;
                } else if (ksgPosition.getDuration() != null) {
                    weight++;
                }
                //st02.012 , st02.013 круче родов и кесарева
                double currentKZ = ksg.getKz();
                if (weight > maxWeight || (weight == maxWeight && currentKZ > maxKZ)) { // || (currentKZ == maxKZ && isNotNull(ksg.getServiceCode()) )) { // не помню зачем я так делал, попробуем убрать это 26.12.2019
                    if (weight > maxWeight) maxWeight = weight;
                    maxKZ = currentKZ;
                    pos = ksgPosition;
                } else if (isTrue(ksg.getIsOperation()) && currentKZ > maxKZ) {
                    justServicePositions.add(ksgPosition);
                }
            } //Закончили искать лучшее КСГ
            if (pos == null && cancerKsgPosition != null) {
                pos = cancerKsgPosition;
            }
            if (therapicKsgPosition != null && surgicalKsgPosition != null) { //Если мы нашли хирургическое КСГ, но есть и терапевтическое, то проверим на исключения
                GrouperKSGPosition exc = checkIsKsgException(surgicalKsgPosition, therapicKsgPosition);
                if (exc != null) pos = exc;
            }

            if (pos != null) {
                VocKsg ksg = pos.getKsgValue();
                entry.setKsgPosition(pos);
                if (isNotLogicalNull(pos.getMainMKB())) entry.setMainMkb(pos.getMainMKB());
                if (isNotLogicalNull(pos.getServiceCode())) entry.setMainService(pos.getServiceCode());
                entry.setKsg(ksg);
                manager.persist(entry);
                if (!justServicePositions.isEmpty()) {
                    StringBuilder err = new StringBuilder("Предполагаемые КСГ: ");
                    for (GrouperKSGPosition k : justServicePositions) {
                        err.append(k.getKsgValue().getCode()).append(" КЗ=").append(k.getKsgValue().getKz()).append("; ");
                    }
                    saveError(entry, E2EntryErrorCode.MAYBE_OTHER_KSG, err.toString());
                }
                return ksg;
            } else {
                entry.setKsgPosition(null);
                entry.setKsg(null);
                entry.setCostFormulaString("");
                entry.setCost(null);
                manager.persist(entry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 1 - до трех дней (включительно)
     * 2 - 4-10 дней
     * 3 - 11-20 дней
     * 4 - 21-30 дней
     *
     * @param daysCount кол-во дней госпитализации
     * @return Длительность КСГ
     */
    private int getKsgDuration(Long daysCount) {
        return daysCount < 4 ? 1
                : daysCount < 11 ? 2
                : daysCount < 21 ? 3
                : daysCount < 31 ? 4
                : 0;
    }

    /**
     * Проверяем, является ли пара КСГ исключением из случая
     */
    private GrouperKSGPosition checkIsKsgException(GrouperKSGPosition surgicalKsgPosition, GrouperKSGPosition therapicalKsgPosition) {
        String key = therapicalKsgPosition.getKsgValue().getCode() + "#" + surgicalKsgPosition.getKsgValue().getCode();
        return ksgExceptions.contains(key) ? surgicalKsgPosition : null; //Если есть КСГ по услуге, подаем его
    }

    /**
     * Нахождение возраста для КСГ
     */
    private String findKSGAge(Date aStartDate, Date aBirthDate) { //Расчет возраста КСГ

        long dayDiff = (AgeUtil.calculateDays(aBirthDate, aStartDate) + 1);
        String ret = "";
        if (dayDiff >= 0 && dayDiff <= 28) {  // от 0 до 28 = 1
            ret += "1";
        }
        if (dayDiff >= 29 && dayDiff <= 90) {// от 29 до 90 = 2
            ret += "2";
        }
        if (dayDiff >= 91 && dayDiff <= 365) {// от 91 до 365 (от 91 дня до 1 года) = 3
            ret += "3";
        }
        if (dayDiff >= 0 && dayDiff <= 730) {// от 0 до 730 = (0 до 2х лет) 4
            ret += "4";
        }
        if (dayDiff >= 0 && dayDiff <= 6570) {// от 0 до 6570 (от 0 до 18 лет) = 5
            ret += "5";
        }
        if (dayDiff > 6570) {// >6570  (больше 18 лет) = 6
            ret += "6";
        }
        return ret;
    }

    /**
     * Получение всех диагнозов из списка по коду регистрации
     */
    private List<String> findDiagnosisCodes(List<EntryDiagnosis> aList, String aRegType, String aPriority) {
        //Тип регистрации - направительный(2), выписной(3), клинический(4)
        // Приорите - основной(1), сопутствующий(3)
        List<String> diagnosisList = new ArrayList<>();
        if (aList.isEmpty()) {
            return diagnosisList;
        }
        if (aRegType == null && aPriority == null) {
            return diagnosisList;
        }
        for (EntryDiagnosis diagnosis : aList) {
            if ( //проверить!
                    (aRegType == null || (diagnosis.getRegistrationType() != null && aRegType.contains(diagnosis.getRegistrationType().getCode())))
                            && (aPriority == null || diagnosis.getPriority() != null && aPriority.contains(diagnosis.getPriority().getCode()))
            ) {
                diagnosisList.add(diagnosis.getMkb().getCode());
            }
        }
        return diagnosisList;
    }

    private BigDecimal calculateTariff(E2Entry entry) {
        String key, sqlAdd;
        VocListEntryTypeCode entryType = entry.getEntryType();
        String mmYYYY = new SimpleDateFormat("MMyyyy").format(entry.getFinishDate());
        if (isNotLogicalNull(entry.getVmpKind())) { //Если в СЛО есть ВМП, цена = ВМП
            return entry.getCost();
        }
        if (isOneOf(entryType, POLYCLINIC, POL_KDP, HOSPITAL)) {

            String tariffCode = ofNullable(entry.getSubType())
                    .map(VocE2EntrySubType::getTariffCodeString)
                    .orElse("_NULLENTRYSUBTYPE_");
            if (isTrue(entry.getIsDentalCase())) tariffCode = "DENTAL_CASE";
            key = entryType + "#" + tariffCode + "#" + mmYYYY;
            sqlAdd = " type.code='" + tariffCode + "'";
        } else {
            LOG.error("Не могу расчитать тариф для записи с типом: CANT_CALC_TARIFF_" + entryType);
            key = "__";
            sqlAdd = null;
        }

        return tariffMap.computeIfAbsent(key, val -> ofNullable(getActualVocByClassName(VocE2BaseTariff.class, entry.getFinishDate(), sqlAdd))
                .map(VocCoefficient::getValue).orElse(null));

    }

    /**
     * @param bedSubTypeCode тип койки
     * @param departmentId   ИД отделения
     * @param profileId      ИД профиля
     * @param startDate      Дата начала случая
     * @return значение коэффициента КУСмо
     */
    @Override
    public BigDecimal calculateCusmo(String bedSubTypeCode, Long departmentId, Long profileId, Date startDate) {
        if (profileId == null) {
            LOG.error("Не указан профиль мед. помощи, невозможно найти КУСмо! NO_PROFILE_FOR_CUSMO");
            return null;
        }
        String key;
        if ("2".equals(bedSubTypeCode)) { //Для дневного стационара возвращаем жестко зашитый коэффициент
            key = DAYTIMEHOSP;
            cusmoMap.putIfAbsent(key, new BigDecimal(getExpertConfigValue("DAYTIMEHOSP_CUSMO", "1")));

        } else {
            key = profileId + "#" + departmentId + "#" + MONTHYEARDATE.format(startDate);
            if (!cusmoMap.containsKey(key)) {
                StringBuilder sqlAdd = new StringBuilder();
                sqlAdd.append("select distinct cusmo.id from VocCoefficient cusmo")
                        .append(" where cusmo.profile_id='").append(profileId).append("' and cusmo.dtype='VocCoefficientLpuLevel' and (cusmo.department_id is null");
                if (departmentId != null && departmentId > 0L) {
                    sqlAdd.append(" or cusmo.department_id='").append(departmentId).append("'");
                }
                sqlAdd.append(")")
                        .append(" and '").append(startDate).append("' between cusmo.startDate and coalesce(cusmo.finishDate,current_date)");
                VocCoefficientLpuLevel cusmo = getActualVocBySqlString(VocCoefficientLpuLevel.class, sqlAdd.toString());
                cusmoMap.put(key, cusmo != null ? cusmo.getValue() : null);
            }
        }
        return cusmoMap.get(key);
    }

    private BigDecimal calculateCusmo(E2Entry entry) {
        if (entry.getKsg() != null && isTrue(entry.getKsg().getDoNotUseCusmo())) return BigDecimal.ONE;
        return calculateCusmo(entry.getBedSubType(), entry.getDepartmentId(), entry.getMedHelpProfile() != null ? entry.getMedHelpProfile().getId() : null, entry.getFinishDate());
    }

    private void calculateHospitalEntryPrice(E2Entry entry) {
        try {
            String key;
            if (isNotLogicalNull(entry.getVmpKind())) { //Если есть ВМП и нет цены - цена случая = цене метода ВМП
                key = "VMP#" + entry.getVmpKind() + "#" + entry.getVmpMethod();
                BigDecimal cost;
                if (!hospitalCostMap.containsKey(key)) {
                    List<BigDecimal> costs = manager.createNativeQuery("select coalesce(vmhc.cost, vkhc.cost)" +
                                    " from vockindhighcare vkhc " +
                                    " left join vocmethodhighcare vmhc on vmhc.kindhighcare = vkhc.code " +
                                    "   and vmhc.code=:methodCode " +
                                    "   and (vmhc.patientmodelid is null" + (entry.getVmpPatientModelId() == null ? ")" : " or vmhc.patientmodelid = " + entry.getVmpPatientModelId() + ")") +
                                    "   and :vmpDate between vmhc.dateFrom and coalesce(vmhc.dateTo,current_date)" +
                                    " where vkhc.code=:code" +
                                    " and :vmpDate between vkhc.dateFrom and coalesce(vkhc.dateTo,current_date) and vkhc.cost is not null")
                            .setParameter("code", entry.getVmpKind())
                            .setParameter("methodCode", entry.getVmpMethod())
                            .setParameter("vmpDate", entry.getFinishDate())
                            .getResultList();
                    if (!costs.isEmpty()) {
                        cost = costs.get(0);
                        hospitalCostMap.put(key, cost);
                    } else {
                        saveError(entry, E2EntryErrorCode.NO_VMP_METHOD_COST);
                        return;
                    }
                } else {
                    cost = hospitalCostMap.get(key);
                }
                entry.setCost(cost);
                entry.setBaseTarif(cost);
                entry.setTotalCoefficient(BigDecimal.ONE);
                entry.setCostFormulaString("tarif=T (" + cost + ")");
                manager.persist(entry);
            } else { //Если не ВМП, считаем цену как обычно
                VocKsg ksg = entry.getKsg();
                if (ksg != null) {  //Нет КСГ - нечего расчитывать всё остальное
                    BigDecimal kz, tarif, cusmo, kslp, km, kpr, kuksg;
                    kz = BigDecimal.valueOf(ksg.getKz());
                    kuksg = getActualKsgUprCoefficient(ksg, entry.getFinishDate());
                    tarif = calculateTariff(entry);
                    cusmo = calculateCusmo(entry);
                    km = calculateKm();
                    kslp = calculateResultDifficultyCoefficient(entry);
                    kpr = calculateNoFullMedCaseCoefficient(entry);
                    if (isAnyIsNull(tarif, kuksg, cusmo, kslp, kpr)) {
                        String err = "Для случая с ИД=" + entry.getId() + " не удалось расчитать цену: Тариф=" + tarif + ", КЗ=" + kz + ", КУксг=" + kuksg + ", КУСмо=" + cusmo + ", КМ=" + km + ", КСЛП=" + kslp + ", Кпр=" + kpr;
                        entry.setCostFormulaString(err);
                        LOG.error(err);
                        saveError(entry, E2EntryErrorCode.NO_COST);
                    } else {
                        String costFormula;
                        BigDecimal totalCoefficient;
                        BigDecimal cost;
                        if (ksg.getDoctorCost() == null) {
                            costFormula = "Тариф=" + tarif + ", КЗ=" + kz + ", КУксг=" + kuksg + ", КУСмо=" + cusmo + ", КМ=" + km + ", КСЛП=" + kslp + ", Кпр=" + kpr;
                            totalCoefficient = ((kz.multiply(kuksg).multiply(cusmo)).add(kslp)).multiply(km).multiply(kpr).setScale(12, RoundingMode.HALF_UP); //Округляем до 2х знаков
//                            totalCoefficient = kz.multiply(kuksg).multiply(cusmo).multiply(kslp).multiply(km).multiply(kpr).setScale(12, RoundingMode.HALF_UP); //Округляем до 2х знаков
                            cost = tarif.multiply(totalCoefficient).setScale(2, RoundingMode.HALF_UP);
                        } else {

                            costFormula = "Тариф=" + tarif + ", КЗ=" + kz + ", Кзп=" + ksg.getDoctorCost() + ", КУксг=" + kuksg + ", КУСмо=" + cusmo + ", КМ=" + km + ", КСЛП=" + kslp + ", Кпр=" + kpr;
                            cost = ((tarif.multiply(kz)
                                    .multiply((BigDecimal.ONE.subtract(ksg.getDoctorCost()))
                                            .add(ksg.getDoctorCost().multiply(kuksg).multiply(cusmo)))
                                    .add(tarif.multiply(km).multiply(kslp)))
                                    .multiply(km).multiply(kpr)).setScale(2, RoundingMode.HALF_UP); //Округляем до 2х знаков
//                            totalCoefficient = kz.multiply((BigDecimal.ONE.subtract(ksg.getDoctorCost()))
//                                    .add(ksg.getDoctorCost().multiply(kuksg).multiply(cusmo).multiply(kslp).multiply(km).multiply(kpr))).setScale(12, RoundingMode.HALF_UP); //Округляем до 2х знаков
//                            cost = totalCoefficient.setScale(2, RoundingMode.HALF_UP);;
                            totalCoefficient = BigDecimal.ONE;
                        }
                        entry.setCostFormulaString(costFormula);

                        entry.setBaseTarif(tarif);
                        entry.setTotalCoefficient(totalCoefficient);
                        entry.setCost(cost);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка расчет цены стационарного случая c ИД " + entry.getId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getActualKsgUprCoefficient(VocKsg ksg, Date finishDate) {
        E2KsgCoefficientHistory coefficientHistory;
        String sql = "select id from E2KsgCoefficientHistory where ksg_id=:ksg and to_date('" + finishDate + "','yyyy-MM-dd') between startDate and coalesce(finishDate, current_date)";
        String key = "KSG#" + ksg.getId() + "#COEFF#" + finishDate;
        if (ksgCoefficientMap.containsKey(key)) {
            coefficientHistory = ksgCoefficientMap.get(key);
        } else {
            List<Long> list = manager.createQuery(sql).setParameter("ksg", ksg.getId()).getResultList();
            if (list.isEmpty()) {
                coefficientHistory = new E2KsgCoefficientHistory();
                coefficientHistory.setValue(BigDecimal.ONE);
            } else if (list.size() > 1) {
                LOG.error(ksg.getId() + " найдено _" + list.size() + "_ коэффициентов КСГ(MORE_1_KSG_COEFFICIENT) " + sql);
                return null;
            } else {
                coefficientHistory = manager.find(E2KsgCoefficientHistory.class, list.get(0));
            }
            ksgCoefficientMap.put(key, coefficientHistory);

        }
        return coefficientHistory.getValue();

    }

    private void calculatePatientDifficulty(E2Entry entry) {
        VocListEntryTypeCode entryType = entry.getEntryType();
        VocKsg ksg = entry.getKsg();
        if (ksg == null || VMP.equals(entryType)) {
            return;
        }
        if (HOSPITAL.equals(entryType) && !entry.getBedSubType().equals("1")) {
            return;
        }

        //Удалим старые значения КСЛП
        if (entry.getPatientDifficulty() != null) {
            for (E2CoefficientPatientDifficultyEntryLink link : entry.getPatientDifficulty()) {
                manager.remove(link);
            }
            entry.setPatientDifficulty(new ArrayList<>());
        }


        //Сложность лечения пациента
        /**
         +  1*	Сложность лечения пациента, связанная с возрастом (лица старше 75 лет)	1
         +  3*	Необходимость предоставления спального места и питания законному представителю (дети до 4) 	1,2
         +  4*	Развертывание индивидуального поста 1,2
         9*	Наличие у пациента тяжелой сопутствующей патологии, осложнений заболеваний, сопутствующих заболеваний, влияющих на сложность лечения пациента. Таблица 'Диагнозы КСЛП' 	1,1
         8	Необходимость предоставления спального места и питания законному представителю ребенка после достижения им возраста 4 лет при наличии медицинских показаний 	1,05
         9	Проведение в рамках одной госпитализации в полном объеме нескольких видов противоопухолевого лечения, относящихся к разным КСГ (перечень возможных сочетаний КСГ представлен в Инструкции) 	1,3
         10	Сверхдлительные сроки госпитализации, обусловленные медицинскими показаниями	1,5
         6	Проведение сочетанных хирургических вмешательств. Таблица 'Сочетанные операции' 	1,3
         7	Проведение однотипных операций на парных органах. Таблица 'Парные операции' 	1,3
         */
        List<String> codes = new ArrayList<>(); //Тут все сложности
        long ageDays = (AgeUtil.calculateDays(entry.getBirthDate(), entry.getStartDate()) + 1);
        if (ageDays > 27394) { //Если возраст более 75 лет
            codes.add("3");
        }
        if (isNotLogicalNull(entry.getHotelServices())) {
            codes.add("1");
        }
        Date actualDate = entry.getFinishDate();
        //6
        List<EntryDiagnosis> list = getDiagnosis(entry);
        List<String> anotherMkb = findDiagnosisCodes(list, null, "3");
        if (anotherMkb.stream()
                .anyMatch(ds -> ds.startsWith("E10") || ds.startsWith("E11"))) {
            codes.add("5");
        }

        //12 Парные операции
        List<EntryMedService> medServiceList = entry.getMedServices();
        if (medServiceList != null && !medServiceList.isEmpty()) {
            ArrayList<String> serviceList = new ArrayList<>();
            StringBuilder serviceCodes = new StringBuilder();
            for (EntryMedService ems : medServiceList) {
                String serviceCode = ems.getMedService().getCode();
                if (!serviceList.contains(serviceCode)) {
                    serviceList.add(serviceCode);
                    serviceCodes.append("'").append(serviceCode).append("',");
                }

            }
            serviceCodes.delete(serviceCodes.length() - 1, serviceCodes.length());
            List<String> combined = manager.createNativeQuery("select vco.difficultyCode from VocCombinedOperations vco" +
                            " left join vocmedservice vms1 on vms1.id=vco.medservice1_id " +
                            " left join vocmedservice vms2 on vms2.id=vco.medservice2_id " +
                            " where vms1.code in (" + serviceCodes + ") and vms2.code in (" + serviceCodes + ") and coalesce(vco.finishDate,current_date)>=:actualDate ")
                    .setParameter("actualDate", actualDate)
                    .getResultList();
            if (!combined.isEmpty()) {
                codes.add(combined.get(0)); //TODO завязаться на перечень операций
            }
        }


        //calc 10
        ArrayList<E2CoefficientPatientDifficultyEntryLink> difficultyEntryLinks = new ArrayList<>();


        //Пришло время сохранять все сложности пациента
        if (!codes.isEmpty()) {
            for (String code : codes) {
                if (!difficultyHashMap.containsKey(code)) {
                    difficultyHashMap.put(code, getActualVocByCode(VocE2CoefficientPatientDifficulty.class, code));
                }
                VocE2CoefficientPatientDifficulty difficulty = difficultyHashMap.get(code);
                E2CoefficientPatientDifficultyEntryLink link = new E2CoefficientPatientDifficultyEntryLink();
                link.setEntry(entry);
                link.setDifficulty(difficulty);
                link.setValue(difficulty.getValue());
                difficultyEntryLinks.add(link);
            }
        }
        if (!difficultyEntryLinks.isEmpty()) {
            ArrayList<E2CoefficientPatientDifficultyEntryLink> a = new ArrayList(difficultyEntryLinks);
            entry.setPatientDifficulty(a);
            manager.persist(entry);
        }
    }

    /**
     * Считаем коэффициент сложности лечения пациента
     */
    @Override
    public BigDecimal calculateResultDifficultyCoefficient(E2Entry entry) {
        final BigDecimal one = BigDecimal.ONE;
        if (entry.getEntryType().equals(HOSPITAL) && !entry.getBedSubType().equals("1")) {
            return getDayTimeHospKslpValue();
        }
        List<E2CoefficientPatientDifficultyEntryLink> list = entry.getPatientDifficulty();
        BigDecimal ret = BigDecimal.ZERO;
        if (isNotEmpty(list)) { //Нет КСЛП - возвращаем 1.
//            if (list.size() == 1) { //Если один - возвращаем его.
//                ret = list.get(0).getValue();
//            } else {
//                boolean first = true;
//                BigDecimal tooLongCoeff = BigDecimal.ONE; //коэффициент сверхдлительности
            for (E2CoefficientPatientDifficultyEntryLink link : list) { //Если несколько, возвращаем К1+(К2-1)+(Кн-1)
//                    if ("9".equals(link.getDifficulty().getCode())) {
//                        tooLongCoeff = link.getValue();
//                    } else {
//                    if (first) {
//                        ret = link.getValue();
//                        first = false;
//                    } else {
                ret = ret.add(link.getValue());//.subtract(one);
//                    }
//                    }
//                }

//                if (ret.compareTo(MAX_KSLP_COEFF) > 0) { //если коэфф без длит > 1.8 - коэфф = 1.8+коэф по сверхдлит. операции.
//                    ret = MAX_KSLP_COEFF;//.add(tooLongCoeff).subtract(one);
//                } else {
//                    ret = ret.add(tooLongCoeff).subtract(one);
//                }
            }
        }
//        if (ret == null) LOG.info("finish calc difficult.id=" + entry.getId() + ", value is NULL!! = ");
        return ret;
    }

    private BigDecimal getDayTimeHospKslpValue() {
        return DAY_TIME_HOSP_KSLP;
    }

    @Override
    public BigDecimal calculatePolyclinicEntryPrice(E2Entry entry, VocE2VidSluch vidSluch, Date finishDate, Long medHelpProfileId) {
        BigDecimal tariff;
        String sqlAdd;
        if (entry != null) {
            tariff = calculateTariff(entry);
            sqlAdd = "profile_id=" + medHelpProfileId + " and tariffType.code='"
                    + (entry.getSubType() == null ? "" : entry.getSubType().getTariffCodeString()) + "'";
        } else {
            VocE2BaseTariff baseTariff = getActualVocByClassName(VocE2BaseTariff.class, finishDate, "vidSluch_id=" + vidSluch.getId());
            if (baseTariff == null) {
                LOG.warn("Cant calc polyclinic tariff: " + vidSluch.getCode() + " <> " + finishDate);
                return BigDecimal.ZERO;
            }
            tariff = baseTariff.getValue();
            sqlAdd = "vidSluch_id=" + vidSluch.getId() + " and profile_id=" + medHelpProfileId;
        }
        try {
            List<VocE2PolyclinicCoefficient> coefficients;
            //находим Кз
            coefficients = manager.createQuery(" from VocE2PolyclinicCoefficient where " + sqlAdd + " and :actualDate between startDate and coalesce(finishDate,current_date)")
                    .setParameter("actualDate", finishDate)
                    .getResultList();
            BigDecimal coef = new BigDecimal(1);
            if (!coefficients.isEmpty()) {
                for (VocE2PolyclinicCoefficient coefficient : coefficients) {
                    coef = coef.multiply(coefficient.getValue());
                }
            }
            return tariff.multiply(coef).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }

    private void calculatePolyclinicEntryPrice(E2Entry entry) {
        BigDecimal one = BigDecimal.ONE;
        VocE2EntrySubType subType = entry.getSubType();
        String tariffCode = subType.getTariffCodeString();
        if (tariffCode == null) { /*LOG.warn("Cant calc polyclinic tariff: "+aEntry.getId()+"<>"+subType.getId()+""+subType.getCode());*/
            return;
        }
        Long profileId = ofNullable(entry.getMedHelpProfile()).map(BaseEntity::getId).orElse(null);
        if (profileId == null) {
            LOG.error("Нет профиля для определения цены, ID: " + entry.getId() + "<>" + entry.getServiceStream());/*return aEntry;*/
        }
        String key;

        VocE2PolyclinicCoefficient coefficient;
        // boolean isKdo =isTrue(aEntry.getIsDiagnosticSpo()) || aEntry.getEntryType().equals(KDPTYPE);
        //находим Кз

        key = isTrue(entry.getIsEmergency()) ? "KZ#EMERGENCY##" : "KZ#" + profileId + "#" + tariffCode;

        String sql = "profile_id=" + profileId + " and tariffType.code='" + tariffCode + "'";
        if (!polyclinicCasePrice.containsKey(key)) {
            coefficient = getActualVocByClassName(VocE2PolyclinicCoefficient.class, entry.getFinishDate(), sql);
            if (coefficient == null) {
                LOG.warn("НЕ смог найти коэффициента: " + sql);
            }
            polyclinicCasePrice.put(key, coefficient);
        } else {
            coefficient = polyclinicCasePrice.get(key);
        }
        BigDecimal kz = coefficient != null ? coefficient.getValue() : one;

        //Находим Кп/Кпд
        sql = "profile_id=" + profileId;
        if (isTrue(entry.getIsMobilePolyclinic())) {
            sql += " and isMobilePolyclinic='1'";
            key = "KP#" + sql;
            if (!polyclinicCasePrice.containsKey(key)) {
                coefficient = getActualVocByClassName(VocE2PolyclinicCoefficient.class, entry.getFinishDate(), sql);
                polyclinicCasePrice.put(key, coefficient);
            } else {
                coefficient = polyclinicCasePrice.get(key);
            }
        } else {
            coefficient = null;
        }

        BigDecimal kp = coefficient != null ? coefficient.getValue() : one;

        BigDecimal tariff;
        BigDecimal km = calculateKm();
        tariff = calculateTariff(entry);
        BigDecimal kUet = getSumKuet(entry);
        String costFormula = "Тариф=" + tariff + ", КЗ=" + kz + ", Кп(Кпд)=" + kp + ", КМ=" + km + (kUet.compareTo(BigDecimal.ONE) > 0 ? ", Кует=" + kUet : "");
        if (isAnyIsNull(tariff, kz, km)) {
            LOG.warn("Не удалось расчитать цену случая");
        } else {
            BigDecimal coeff = kz.multiply(kp).multiply(km).multiply(kUet);
            BigDecimal cost = tariff.multiply(coeff);
            entry.setTotalCoefficient(coeff);
            entry.setCost(cost);
            entry.setBaseTarif(tariff);
        }
        entry.setCostFormulaString(costFormula);
        manager.persist(entry);
    }

    //максимальные УЕТ по случаю
    @Override
    public BigDecimal getSumKuet(E2Entry entry) {
        try {
            if (isNotTrue(entry.getIsDentalCase()) || isEmpty(entry.getMedServices())) {
                return BigDecimal.ONE;
            }
            List<EntryMedService> medServices = entry.getMedServices();
            String diagnosis = entry.getMainMkb();
            BigDecimal bestUet = BigDecimal.ONE;
            boolean isShortCase = entry.getStartDate().equals(entry.getFinishDate());
            for (EntryMedService medService : medServices) {
                BigDecimal uet = medService.getUet();
                if (uet != null && uet.compareTo(BigDecimal.ZERO) > 0) {
                    bestUet = bestUet.add(uet);
                }
            }
            return bestUet.min(getStomUetByDiagnosis(diagnosis, isShortCase));
        } catch (Exception e) {
            LOG.error("Error calCulateStom UET:" + e.getMessage(), e);
            return BigDecimal.ONE;
        }
    }

    private BigDecimal getStomUetByDiagnosis(String diagnosis, boolean shortCase) {
        VocDentalUetCoefficient uetCoefficient = getActualVocByClassName(VocDentalUetCoefficient.class, null, "diagnosis like '%;" + diagnosis + ";%'");
        if (uetCoefficient != null) {
            return shortCase ? uetCoefficient.getShortCaseValue() : uetCoefficient.getLongCaseValue();
        }
        return BigDecimal.ONE;
    }

    private void calculateServiceEntryPrice(E2Entry entry) { //Цена случая с типом услуга = цене услуги!
        List<EntryMedService> medServices = entry.getMedServices();
        if (CollectionUtil.isEmpty(medServices)) {
            LOG.warn(entry.getId() + " : в случае отсутствуют услуги, нельзя посчитать цену " + (medServices != null ? medServices.size() : -1));
        } else if (medServices.size() > 1) {
            LOG.warn(entry.getId() + " : в случае несколько услуг!! нельзя посчитать цену " + medServices.size());
        } else {
            EntryMedService medService = medServices.get(0); //1 случай = 1 услуга
            BigDecimal cost = medService.getCost();
            entry.setCost(cost);
            entry.setBaseTarif(cost);
            if (cost == null || cost.compareTo(BigDecimal.ZERO) == 0) {
                entry.setCostFormulaString("Не удалось найти цену услуги " + medService.getMedService().getCode());
                entry.setDoNotSend(true);
                saveError(entry, E2EntryErrorCode.NO_COST);
            }
        }
        manager.persist(entry);
    }

    private void calculateExtDispEntryPrice(E2Entry entry) {
         /*
        а. Если оказано менее 85% услуг или нет хоть одной обязательной услуги - формируем сообщение об ошибке
        б. Иначе если в период выполнения ДД выполнено менее 85% услуг - считаем цену по услугам
        б. Иначе - расчет цены полным случаем
        * услуга считается выполненной, если указана дата выполнения услуги
        * */
        ExtDispPrice vocPrice = getExtDispPrice(entry);
        BigDecimal cost;
        if (vocPrice != null) {
            manager.createNativeQuery("delete from entrymedservice where entry_id=:id and medservice_id is null").setParameter("id", entry.getId()).executeUpdate(); //удаляем пустые услуги
            List<EntryMedService> dispServices = entry.getMedServices(); //оказанные услуги
            int minRequiredServices = vocPrice.getMinServices(); //мин кол-во обязательных услуг по плану (85%)
            int madeServicesCount = 0; //кол-во оказанных услуг (с датой, которые есть в плане)
            int serviceInPeriod = 0; //кол-во услуг, входящих в период ДД
            List<ExtDispPriceMedService> vocMedServices = vocPrice.getServiceList();
            List<String> foundServiceCodes = new ArrayList<>(); //список кодов выполненных услуг (которые должны быть выполнены по плану)
            long startDispTime = entry.getStartDate().getTime();
            long finishDispTime = entry.getFinishDate().getTime();
            if (vocMedServices.isEmpty()) {
                saveError(entry, E2EntryErrorCode.NO_DISP_PRICE_SERVICES_ADMIN_SKIP);
                cost = vocPrice.getCost();
            } else {
                for (EntryMedService dispService : dispServices) {
                    boolean isServiceNeed = false; //услуга должна быть оказана по плану
                    if (dispService.getMedService() != null) {
                        for (ExtDispPriceMedService dispPriceMedService : vocMedServices) {
                            if (isEquals(dispPriceMedService.getMedService(), dispService.getMedService().getCode())) {
                                isServiceNeed = true;
                                if (dispService.getServiceDate() != null) { //если нет даты услуги - она не была оказана, отображается для информации
                                    madeServicesCount++;
                                    long serviceTime = dispService.getServiceDate().getTime();
                                    if (startDispTime <= serviceTime && serviceTime <= finishDispTime) {
                                        serviceInPeriod++;
                                    }
                                }
                                foundServiceCodes.add(dispPriceMedService.getMedService());
                                break;
                            }
                        }
                        if (!isServiceNeed) {
                            dispService.setComment("Услуга не требуется по возрасту");
                            manager.persist(dispService);
                        }
                    }
                }
                if (vocMedServices.size() > madeServicesCount) { //не все услуги оказаны, смотрим чего нет и считаем цену по услугам
                    boolean lostRequired = false; //обязательная услуга не оказана
                    cost = BigDecimal.ZERO;
                    for (ExtDispPriceMedService dispPriceMedService : vocMedServices) {
                        String medserviceCode = dispPriceMedService.getMedService();
                        if (!foundServiceCodes.contains(medserviceCode)) { //если услуги из плана нет в списке выполненных услуг, создаем услугу без даты для удобства пользователя
                            VocMedService vms = SERVICELIST.computeIfAbsent(medserviceCode, val->getEntityByCode(medserviceCode, VocMedService.class, false));

                            if (vms == null) LOG.error("VMS is null " + medserviceCode);
                            if (isTrue(dispPriceMedService.getIsRequired())) lostRequired = true;
                            EntryMedService medService = new EntryMedService(entry, vms);
                            medService.setEntry(entry);
                            medService.setComment("НЕ УКАЗАНА ДАТА УСЛУГИ");
                            VocOmcMedServiceCost medServiceCost = getMedServiceOmc(vms, entry.getFinishDate());
                            if (medServiceCost != null) {
                                BigDecimal serviceCost = medServiceCost.getCost();
                                medService.setDoctorSpeciality(medServiceCost.getWorkFunction());
                                medService.setCost(serviceCost);
                            } else {
                                LOG.warn("No medServiceCost" + medserviceCode);
                            }
                            LOG.warn(entry.getId() + " no service in disp: " + medserviceCode);
                            saveError(entry, E2EntryErrorCode.MALO_DISP_SERVICE);
                            manager.persist(medService);
                        }
                    }
                    if (lostRequired || madeServicesCount < minRequiredServices) { //нет хоть одной обязательной услуги сообщение об ошибке.
                        saveError(entry, E2EntryErrorCode.NOT_FULL_DISP);
                    } else if (madeServicesCount != serviceInPeriod && serviceInPeriod >= minRequiredServices) {  //в период ДД сделали >85% - полный случай
                        cost = vocPrice.getCost();
                        LOG.info("85% услуг сделано в рамках ДД");
                    } else {//иначе - по услугам
                        LOG.info("DISP calc by service: " + entry.getId());
                        for (EntryMedService medService : dispServices) {
                            if (medService.getServiceDate() != null) {
                                VocOmcMedServiceCost medServiceCost = getMedServiceOmc(medService.getMedService(), medService.getServiceDate());
                                if (medServiceCost != null) {
                                    medService.setCost(medServiceCost.getCost());
                                    manager.persist(medService);
                                    cost = cost.add(medService.getCost());
                                }

                            }
                        }
                        LOG.info("DISP COST by service: " + entry.getId() + ">>" + cost);
                    }
                } else {
                    cost = vocPrice.getCost();
                }
            }
        } else { //2 этап - считаем цену по услугам
            cost = BigDecimal.ZERO;
            for (EntryMedService medService : entry.getMedServices()) {
                if (!isAnyIsNull(medService.getServiceDate(), medService.getMedService())) {
                    VocOmcMedServiceCost medServiceCost = getMedServiceOmc(medService.getMedService(), entry.getFinishDate());
                    if (medServiceCost != null) {
                        medService.setCost(medServiceCost.getCost());
                        manager.persist(medService);
                        cost = cost.add(medServiceCost.getCost());
                    }
                }
            }
        }

        if (DateFormat.isHoliday(entry.getStartDate(), true)) {
            cost = cost.multiply(new BigDecimal("1.07")); //повышающий коэффициент на выходных
        }
        entry.setCost(cost);
        entry.setBaseTarif(cost);

        manager.persist(entry);
    }

    /*расчитываем цену ДД по типу, возрасту, полу и дате*/
    private ExtDispPrice getExtDispPrice(E2Entry entry) {
        String sql = "select p.id from ExtDispPrice p " +
                " left join VocE2FondV016 v016 on v016.id=p.dispType_id" +
                " left join vocsex vs on vs.id=p.sex_id" +
                " where v016.code='" + entry.getExtDispType() + "' and (p.sex_id is null or vs.code='" + entry.getSex() + "')" +
                " and position('," + entry.getExtDispAge() + ",' in p.ages)>0 and '" + entry.getFinishDate() + "' between p.dateFrom and coalesce(p.dateTo,current_date)";
        List<BigInteger> list = manager.createNativeQuery(sql)
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? null : manager.find(ExtDispPrice.class, list.get(0).longValue());
    }

    /**
     * Создаем случаи НМП в поликлинике для отказных госпитализаций
     */
    //только для АМОКБ
    private void makeEmergencyEntry(List<E2Entry> entries) {
        LOG.info("Создаем НМП, всего случаев = " + entries.size());
        VocE2EntrySubType subType = getEntityByCode("CONS_POL_EMERG_POLYCLINIC", VocE2EntrySubType.class, false);
        VocE2EntrySubType serviceSubType = getEntityByCode("SERVICE", VocE2EntrySubType.class, false);
        if (!isAnyIsNull(subType, serviceSubType)) {
            VocE2FondV006 medHelpUsl = subType.getUslOk();
            VocE2VidSluch vidSluch = subType.getVidSluch();
            VocE2FondV025 visitPurpose = subType.getVisitPurpose();
            VocE2FondV010 idsp = getEntityByCode("41", VocE2FondV010.class, false); //
            boolean isFirst;
            Map<String, VocE2FondV008> medHelpMap = new HashMap<>();
            medHelpMap.put("12", getEntityByCode("12", VocE2FondV008.class, false));// терапевт - 12 вид МП
            medHelpMap.put("13", getEntityByCode("13", VocE2FondV008.class, false));// первичная специализированная медико-санитарная помощь

            VocE2FondV009 fondResult = getEntityByCode("301", VocE2FondV009.class, false); // ЛЕЧЕНИЕ ЗАВЕРШЕНО
            VocE2FondV012 fondIshod = getEntityByCode("303", VocE2FondV012.class, false); // УЛУЧШЕНИЕ
            VocIdc10 healthMkb = getEntityByCode("Z02.9", VocIdc10.class, false);
            VocPriorityDiagnosis prior = getEntityByCode("1", VocPriorityDiagnosis.class, false);

            for (E2Entry entry : entries) { // запись = 1 отказ в госпитализации //TODO делать правильное проставление услуг
                entry.setMedHelpKind(medHelpMap.get(calculateHelpKindPol(entry)));
                entry.setFondResult(fondResult);
                entry.setFondIshod(fondIshod);
                entry.setDoNotSend(true); //По умолчанию - НМП отмечаем как брак. Хороший НМП отметим позже.
                isFirst = true;
                List<EntryMedService> services = entry.getMedServices();
                List<EntryDiagnosis> diagnoses = entry.getDiagnosis();
                List<Long> uniqueSpecList = new ArrayList<>();
                for (EntryMedService service : services) {
                    VocE2FondV021 spec = service.getDoctorSpeciality();
                    if (service.getCost().compareTo(BigDecimal.ZERO) > 0) { //если цена услуги больше нуля - подает услугу отдельно (с апреля 2022 года не подаем?)
                       /* E2Entry serviceEntry = cloneEntity(entry);
                        serviceEntry.setSubType(serviceSubType);
                        serviceEntry.setVidSluch(serviceSubType.getVidSluch());
                        serviceEntry.setVisitPurpose(serviceSubType.getVisitPurpose());
                        serviceEntry.setEntryType(SERVICETYPE);
                        serviceEntry.setDoNotSend(false);
                        serviceEntry.setDirectLpu("300001");
                        serviceEntry.setIsEmergency(true);
                        EntryDiagnosis d = new EntryDiagnosis(serviceEntry, healthMkb, null, prior, "", "");
                        ArrayList<EntryDiagnosis> dd = new ArrayList<>();
                        dd.add(d);
                        serviceEntry.setDiagnosis(dd);
                        serviceEntry.setMainMkb(d.getMkb().getCode());

                        try {
                            serviceEntry.setMedHelpProfile(service.getDoctorSpeciality().getPolicProfile());
                            serviceEntry.setFondDoctorSpecV021(service.getDoctorSpeciality());
                            serviceEntry.setDoctorSnils(service.getDoctorSnils());
                        } catch (Exception e) {
                            LOG.error("No doctor in service!" + entry.getId());
                        }

                        manager.persist(serviceEntry);
                        makeCheckEntry(serviceEntry, false, true);
                        service.setEntry(serviceEntry);
                        manager.persist(service);*/

                    } else {
                        if (!isAnyIsNull(service.getMkb(), spec)) { //Если в услуге есть врач и диагноз
                            EntryDiagnosis ed = null;
                            for (EntryDiagnosis d : diagnoses) { //Смотрим, если диагноз совпадает с диагнозом в услуге.
                                VocIdc10 mkb = d.getMkb();
                                if (mkb.equals(service.getMkb())) { //Если диагнозы сходятся - то вот тут помечаем entry как хороший НМП
                                    ed = d;
                                    break;
                                }
                            }
                            if (ed != null && !uniqueSpecList.contains(spec.getId())) { // В случае есть подходящий диагноз!
                                if (!isFirst) {
                                    entry = cloneEntity(entry); //Если 2 и более дненик с диагнозом
                                    diagnoses.remove(ed);
                                    ed.setEntry(entry);
                                    manager.persist(ed);
                                }
                                entry.setDoNotSend(ed.getMkb().getCode().startsWith("Z"));
                                entry.setIsEmergency(true);
                                entry.setWorkPlace("POLYCLINIC");
                                entry.setSubType(subType);
                                entry.setMedHelpUsl(medHelpUsl);
                                entry.setVidSluch(vidSluch);
                                entry.setVisitPurpose(visitPurpose);
                                entry.setIdsp(idsp);
                                entry.setMainMkb(service.getMkb().getCode());
                                entry.setEntryType(POLYCLINIC);
                                entry.setDoctorSnils(service.getDoctorSnils());
                                entry.setFondDoctorSpecV021(spec);
                                entry.setMedHelpProfile(spec.getPolicProfile());
                                manager.persist(entry);
                                makeCheckEntry(entry, false, true);
                                isFirst = false;
                                uniqueSpecList.add(spec.getId());
                            }
                        }
                    }


                }
                if (isFirst) { //Не нашли подходящих диагнозов/услуг.
                    manager.persist(entry);
                }
            }
        }
    }

    /**
     * Выводим сообщение в монитор. Возвращаем - отменен ли монитор
     */
    private boolean isMonitorCancel(IMonitor monitor, String monitorText) {
        monitor.setText(monitorText);
        LOG.info(monitorText);
        if (monitor.isCancelled()) {
            monitor.setText("Проверка прервана пользователем");
            LOG.warn("Проверка прервана пользователем!");
            return true;
        }
        return false;
    }

    /**
     * Проставляем тип записи (стационар, ВМП, поликлиника, подушевое финансирование, __ИНОГОРОДНИЕ__
     */
    private void setEntryType(E2Entry entry, VocListEntryTypeCode entryCode) {
        if (entryCode.equals(HOSPITAL) && isNotLogicalNull(entry.getVmpKind())) {
            entryCode = VMP;
        } else if (entryCode.equals(VocListEntryTypeCode.POLYCLINICCOVIDTYPE) || entryCode.equals(POLYCLINIC) && serviceDepartments.contains(entry.getDepartmentId())) { //телемедицина+лаборатория амокб
            entryCode = SERVICE;
        }
        if (isNotLogicalNull(entry.getInsuranceCompanyCode())) {
//            entryCode += "_INOG"; //todo check and delete
            entry.setIsForeign(true);
        } //Если код страх. компании не пустой - иногородний.
        entry.setEntryType(entryCode);
        manager.persist(entry);
    }

    /**
     * Получаем значение из настроек экспертизы по коду
     */
    private String getExpertConfigValue(String parameterName, String defaultValue) {
        List<Object> ret = manager.createNativeQuery("select value from Expert2Config where code=:code AND (isDeleted is null or isDeleted='0')").setParameter("code", parameterName).getResultList();
        if (ret.isEmpty()) {
            if (defaultValue == null) {
                throw new IllegalStateException("Не удалось найти настройку с кодом: " + parameterName);
            } else {
                LOG.warn("Не удалось найти настройку с ключем " + parameterName + ", возвращаю значение по умолчанию");
                return defaultValue;
            }
        }
        return ret.get(0).toString();
    }

    /**
     * Получаем тип объекта по его sql типу
     *
     * @param type       - SQL тип столбца
     * @param outerClass - Выходной класс
     * @param value      - Значение
     * @return
     * @throws ParseException
     */
    private Object convertResultSetValue(int type, Class outerClass, Object value) throws ParseException {
        Class aClass;
        switch (type) {
            case Types.VARCHAR: //12
            case Types.OTHER:
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
            case Types.BOOLEAN:
                aClass = Boolean.class;
                break;
            case Types.NUMERIC:
                aClass = BigDecimal.class;
                break;
            default:
                throw new IllegalStateException("can't find preobrazovanie for type " + type);
        }
        return PropertyUtil.convertValue(aClass, outerClass, value);
    }

    private String getFileAsSTring(String fileName) {
        String url = getJbossConfigValue("jboss.userdocument.dir", "/opt/jboss-4.0.4.GAi/server/default/data");
        File resourceFile = new File(url + "/" + fileName);
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
                LOG.error("Не удалось обнаружить файл!", e);
            }
        }
        LOG.error("NO_FILE_ Не удалось найти ресурс с именем: " + fileName + "  в папке " + url);
        throw new IllegalStateException("Не удалось обнаружить файл с запросом! " + fileName);
    }

    private Statement createStatement() throws NamingException, SQLException {

        DataSource ds = ApplicationDataSourceHelper.getInstance().findDataSource();
        Connection connection = ds.getConnection();
        return connection.createStatement();
    }

    private String toSQlDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return " to_date('" + format.format(date) + "','dd.MM.yyyy') ";
    }

    /**
     * Расчет причины неполной оплаты случая (коэффициент прерванного случая)
     */

    public <T> T getActualVocBySqlString(Class<T> clazz, String sql) {
        List<Object> list = manager.createNativeQuery(sql).getResultList();
        if (list.isEmpty()) {
            LOG.error("Не удалось найти действующее значение=0 справочника " + clazz.getCanonicalName() + " с условием " + sql);
            return null;
            //throw new IllegalStateException("Не удалось найти действующее значение справочника " + clazz.getCanonicalName() + " с условием " + sql);
        }
        if (list.size() > 1) {
            LOG.error(" с условием " + sql + " найдено несколько>1 действующих значений справочника " + clazz.getCanonicalName());
            return null;
        }
        return manager.find(clazz, Long.valueOf(list.get(0).toString()));

    }

    private <T> T getActualVocByCode(Class<T> clazz, String code) {
        return getActualVocByClassName(clazz, null, " code='" + code + "'");
    }

    private <T> T getActualVocByCode(Class<T> clazz, Date actualDate, String code) {
        return getActualVocByClassName(clazz, actualDate, " code='" + code + "'");
    }

    @Override
    public <T> T getActualVocByClassName(Class<T> clazz, Date actualDate, String sqlAdd) {
        String sql = " from " + clazz.getName() + " where ";
        List<T> list;
        if (actualDate != null) {
            sql += " :actualDate between startDate and coalesce(finishDate,current_date)";
            if (isNotLogicalNull(sqlAdd)) {
                sql += " and " + sqlAdd;
            }
            list = manager.createQuery(sql).setParameter("actualDate", actualDate).getResultList();
            if (list.isEmpty()) {
                list = manager.createQuery("from " + clazz.getName() + " where finishDate is null" + (sqlAdd != null ? " and " + sqlAdd : "")).getResultList();
            }
        } else if (isNotLogicalNull(sqlAdd)) {
            sql += sqlAdd;
            list = manager.createQuery(sql).getResultList();
        } else {
            throw new IllegalStateException("Необходимо указать дату актуальности либо другое условие");
        }
        if (list.isEmpty()) {
            LOG.error("Не удалось найти действующее значение справочника " + clazz.getCanonicalName() + " с условием " + sql);
            return null;
            //throw new IllegalStateException("Не удалось найти действующее значение справочника " + clazz.getCanonicalName() + " с условием "+sql);
        } else if (list.size() > 1) {
            LOG.error("Найдено несколько действующих значений справочника " + clazz.getCanonicalName() + " с условием " + sql);
            return null;
            //throw new IllegalStateException("Найдено несколько действующих значений справочника " + clazz.getCanonicalName()+" с условием "+sql);
        }
        return list.get(0);

    }

    private String getJbossConfigValue(String configName, String defaultValue) {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get(configName, defaultValue);

    }

    /**
     * Считаем районнный коэффициент  (Км)
     */

    private BigDecimal calculateKm() {
        return BigDecimal.ONE;
    }

    /**
     * Указывается причина, по которой услуга не оказана или оказана не в полном объёме.
     * 1 – документированный отказ больного,
     * 2 – медицинские противопоказания,
     * 3 – прочие причины (умер, переведён в другое отделение и пр.)
     * 4 – ранее проведённые услуги в пределах установленных сроков.
     */
    private BigDecimal calculateNoFullMedCaseCoefficient(E2Entry entry) { //Считаем коэффициент Кпр.+    //  LOG.info("start calculateNoFullMedCaseCoefficient");
        String npl = entry.getNotFullPaymentReason();
        BigDecimal ret = BigDecimal.ONE; //По умолчанию - полный случай
        if (entry.getFondResult() == null) {
            return ret;
        }
        String result = entry.getFondResult().getCode();
        if (entry.getDepartmentId() == 182 && !isOneOf(result, "107", "108", "102")) { //Если патология, если НЕ выписан по желанию ЛПУ или желанию пациента, Кпр=1 * 03.10.2018 Результат - Не перевод в другое ЛПУ
            return ret;
        }
        String deadResult = "105,106,205,206"; //смерть
        String otherLpuResult = "102,103,202,203"; //перевод в другое ЛПУ
        String lpuLikeResult = "108,208"; //по желанию ЛПУ
        String patientLikeResult = "107,207"; //по желанию пациента
        String perevodResult = "104,204"; //по желанию пациента
        boolean isDeadCase = deadResult.contains(result);
        boolean isOtherLpu = otherLpuResult.contains(result);
        boolean isLpuLike = lpuLikeResult.contains(result);
        boolean isPatientLike = patientLikeResult.contains(result);
        boolean isPerevod = perevodResult.contains(result);
        VocKsg ksg = entry.getKsg();
        boolean isPrerSluch;
        if (isDeadCase || isPatientLike || isOtherLpu || isLpuLike) { //не стандартная выписка
            isPrerSluch = true;
        } else if (entry.getBedDays() < 4) {  //Если плановая выписки и длительность случая менее 4 дней. //28-02-2018 4 целых дня.
            isPrerSluch = ksg == null || isNotTrue(ksg.getIsFullPayment());
        } else if (isPerevod) {
            isPrerSluch = true;
        } else {
            isPrerSluch = false;
        }
        if (isPrerSluch && ksg != null) { // если прерванный случай - ставим причину неполной оплаты
            boolean isShortCase = entry.getBedDays() < 4;
            if (isTrue(ksg.getIsOperation())) { //Если у КСГ признак "операционного"
                ret = BigDecimal.valueOf(isShortCase ? 0.85 : 0.9);
            } else {
                ret = BigDecimal.valueOf(isShortCase ? 0.5 : ksg.getCode().startsWith("st17") && isOtherLpu ? 0.7 : 0.75);
            }
        }

        if (isNotLogicalNull(npl) && !npl.equals("0") && ksg != null) {
            ret = BigDecimal.valueOf(isTrue(ksg.getIsOperation()) ? 0.9 : 0.3);
        }

        if (isLogicalNull(npl)) entry.setNotFullPaymentReason("0");
        manager.persist(entry);
        return ret.setScale(2, RoundingMode.HALF_UP);
    }

    private void calculateFondField(E2Entry entry, boolean forceUpdate) { //Расчитываем поля для подачи в ОМС RSLT, ISHOD, PRVS
        String key;
        StringBuilder sb;
        VocListEntryTypeCode entryType = entry.getEntryType();
        String bedSubType = entry.getBedSubType();
        List<BigInteger> list;
        Date actualDate = entry.getFinishDate();
        boolean stacCase = isOneOf(entryType, HOSPITAL, VMP);
        boolean vmpCase = entryType.equals(VMP);
        boolean polyclinicCase = isOneOf(entryType, POLYCLINIC, SERVICE);
        boolean extDispCase = entryType.equals(EXTDISP);

        if (stacCase) {   //Расчет профиля мед. помощи по профилю коек для стационара
            if (entry.getBedProfile() == null) {
                String bedType = entry.getHelpKind(); //V020
                key = "BEDPROFILE#" + bedType;

                entry.setBedProfile((VocE2FondV020) resultMap.computeIfAbsent(key, val->getActualVocByCode(VocE2FondV020.class, entry.getFinishDate(), bedType)));
            }
            if (entry.getMedHelpProfile() == null || forceUpdate) {
                entry.setMedHelpProfile(getProfileByBedType(entry));
            }
        }
        //Расчитывает Специальность лечащего врача/ врача, закрывшего талон UPD 18-07-2018 * Специальность врача по справочнику V021
        if (entry.getFondDoctorSpecV021() == null || forceUpdate) {
            if (stacCase && entry.getMedHelpProfile() != null && entry.getMedHelpProfile().getMedSpecV021() != null) {  /* от 09-02-2018 Если у профиля мед. помощи указана специальность врача, указываем ее. Только для стационара */
                entry.setFondDoctorSpecV021(entry.getMedHelpProfile().getMedSpecV021());
            } else if (isNotLogicalNull(entry.getDoctorWorkfunction())) {
                String doctorWorkFunction = entry.getDoctorWorkfunction(); //с 3 декабря - v021.code
                key = "DOCTOR#" + doctorWorkFunction;
                if (vmpCase && entry.getMedHelpProfile() != null
                        && isEquals(entry.getMedHelpProfile().getProfileK(), "81") && doctorWorkFunction.equals("25")) { //09-12 26VWF = 25V021 = кардиолог
                    key += "#VMP";
                    if (!resultMap.containsKey(key)) {
                        resultMap.put(key, getActualVocByCode(VocE2FondV021.class, actualDate, "45"));
                    }
                }

                //TODO замена бактериологии на 26 лаборатория **update e2entry ee set fonddoctorspecv021_id  =398 where  fonddoctorspecv021_id = 496
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key, getActualVocByCode(VocE2FondV021.class, actualDate, doctorWorkFunction));
                }
                VocE2FondV021 doctor = (VocE2FondV021) resultMap.get(key);
                if (doctor == null) {
                    LOG.error("can't find DOCTOR, v021 code = " + doctorWorkFunction);
                }
                entry.setFondDoctorSpecV021(doctor);

            }
            if (entry.getFondDoctorSpecV021() == null && entry.getMedHelpProfile() != null) { //TODO check
                entry.setFondDoctorSpecV021(entry.getMedHelpProfile().getMedSpecV021());
            }
        }
        String result;
        if (isLogicalNull(entry.getResult())) {
            if (extDispCase) {
                result = "1#1#1";
            } else {
                saveError(entry, E2EntryErrorCode.NO_RESULT);
                return;
            }
        } else {
            result = entry.getResult();
        }
        String[] dischargeData = result.split("#", -1); //vho.code||'#'||vrd.code||'#'||vhr.code

        if (stacCase) { //Заполняем поля для стационара

            String reasonDischarge = dischargeData[1];
            String hospResult = dischargeData[2];
            String hospOutCome = dischargeData[0];

            //Результат лечения (RSLT)
            if (entry.getFondResult() == null) {
                key = "HOSP#RESULT#" + reasonDischarge + "#" + hospResult + "#" + hospOutCome + "#" + bedSubType;
                if (!resultMap.containsKey(key)) {
                    sb = new StringBuilder();
                    sb.append("select v009.id from E2FondResultLink link left join VocE2FondV009 v009 on v009.id=link.result_id where ").append(addSql("link.medosReasonDischarge", reasonDischarge))
                            .append(" and ").append(addSql("link.medosHospResult", hospResult))
                            .append(" and ").append(addSql("link.medosHospOutcome", hospOutCome))
                            .append(" and v009.usl='").append(bedSubType).append("'");
                    list = manager.createNativeQuery(sb.toString()).getResultList(); //Находим результат обращения (V009 RSLT)
                    if (list.isEmpty()) {
                        sb.setLength(0);
                        sb.append("select v009.id from E2FondResultLink link left join VocE2FondV009 v009 on v009.id=link.result_id where ").append(addSql("link.medosReasonDischarge", reasonDischarge))
                                .append(" and v009.usl='").append(bedSubType).append("'");
                        list = manager.createNativeQuery(sb.toString()).getResultList(); //Находим результат обращения (V009 RSLT)
                        if (list.isEmpty()) {
                            LOG.error("can't find RSLT = " + result + "____ result find sql string = " + sb);
                        }
                    }
                    resultMap.put(key, list.isEmpty() ? null : manager.find(VocE2FondV009.class, list.get(0).longValue()));
                }
                if (resultMap.get(key) != null) entry.setFondResult((VocE2FondV009) resultMap.get(key));
            }

            //calculateFondISHOD VocE2FondV012
            if (entry.getFondIshod() == null) {
                key = "HOSP#ISHOD#" + hospResult + "#" + bedSubType; //Добавим справочник исходов, если в карте нет исхода - добавим
                if (!resultMap.containsKey(key)) {
                    list = manager.createNativeQuery("select v012.id from E2FondIshodLink link" +
                            " left join VocE2FondV012 v012 on v012.id=link.ishod_id where link.medosHospResult='"
                            + hospResult + "' and link.bedSubType='" + bedSubType + "'").getResultList(); //Находим исход случая (V012 ISHOD)
                    if (list.isEmpty()) {
                        LOG.error("can't find ISHOD = " + entry.getFondIshod() + "____ ishod find sql string = ");
                    }
                    resultMap.put(key, list.isEmpty() ? null : manager.find(VocE2FondV012.class, list.get(0).longValue()));
                }
                if (resultMap.get(key) != null) entry.setFondIshod((VocE2FondV012) resultMap.get(key));
            }
            calculatePatientDifficulty(entry); //Запустим расчет сложности лечения пациента


            //Вид медицинской помощи (для расчета нужен профиль МП)
            String v008Code;
            if (isTrue(entry.getIsRehabBed()) && "7".equals(entry.getDepartmentType())
                    || entry.getSubType() != null && "POLDAYTIMEHOSP".equals(entry.getSubType().getCode())) { //реабилитация в стационаре при пол-ке
                v008Code = calculateHelpKindPol(entry);
            } else {
                v008Code = vmpCase ? "32" : "31";
            }

            key = "V008#" + v008Code;
            if (!resultMap.containsKey(key)) {
                resultMap.put(key, getActualVocByCode(VocE2FondV008.class, actualDate, v008Code));
            }
            entry.setMedHelpKind((VocE2FondV008) resultMap.get(key));
        } else if (polyclinicCase) { //Заполняем поля для пол-ки
            String resultCode = dischargeData[0], ishodCode = dischargeData[1];
            //Результат
            if (entry.getFondResult() == null || forceUpdate) {
                key = "VISIT#RESULT#" + resultCode;
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key, getActualVocByCode(VocE2FondV009.class, actualDate, resultCode));
                }
                if (resultMap.get(key) != null) entry.setFondResult((VocE2FondV009) resultMap.get(key));
            }

            //Исход
            if (entry.getFondIshod() == null || forceUpdate) {
                key = "VISIT#ISHOD#" + ishodCode;
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key, getActualVocByCode(VocE2FondV012.class, actualDate, ishodCode));
                }
                if (resultMap.get(key) != null) entry.setFondIshod((VocE2FondV012) resultMap.get(key));
            }

            //Профиль мед. помощи
            if (entry.getFondDoctorSpecV021() != null && (entry.getMedHelpProfile() == null || forceUpdate)) { //Обновляем профиль мед. помощи по профилю врача
                entry.setMedHelpProfile(entry.getFondDoctorSpecV021().getPolicProfile());
            }

            //Вид медицинской помощи
            if (entry.getMedHelpKind() == null || forceUpdate) {
                String v008Code = calculateHelpKindPol(entry);
                key = "V008#" + v008Code;
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key, getActualVocByCode(VocE2FondV008.class, actualDate, v008Code));
                }
                entry.setMedHelpKind((VocE2FondV008) resultMap.get(key));
            }
            if (StringUtil.isNullOrEmpty(entry.getDepartmentAddressCode()) && entry.getMedHelpProfile() != null) {
                entry.setDepartmentAddressCode(entry.getMedHelpProfile().getDefaultDepartmentCode());
            }
        } else if (extDispCase) { //расчет возраста ДД
            try {
                entry.setExtDispAge(AgeUtil.calculateExtDispAge(entry.getStartDate(), entry.getBirthDate()));
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                saveError(entry, E2EntryErrorCode.DISP_EXCEPTION, "Ошибка расчета возраста ДД:" + e.getMessage());
                LOG.warn("Ошибка расчета возраста ДД:" + e.getMessage());
            }
            if (entry.getFondIshod() == null || forceUpdate) {
                String ishodCode = "306";
                key = "EXTDISP#ISHOD#" + ishodCode;
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key, getActualVocByCode(VocE2FondV012.class, actualDate, ishodCode));
                }
                entry.setFondIshod((VocE2FondV012) resultMap.get(key));
            }

            VocE2EntrySubType entrySubType = entry.getSubType();
            if (entrySubType == null) {
                saveError(entry, E2EntryErrorCode.NO_ENTRY_SUBTYPE);
            } else {
                entry.setMedHelpUsl(entrySubType.getUslOk());
            }
        } else {
            LOG.info("calc fond field, type = " + entryType);
        }
        manager.persist(entry);
    }

    /**
     * Расчет вида мед помощи (с декабря 2020) для поликлинических случаев
     *
     * @param entry запись
     * @return код V008
     */
    private String calculateHelpKindPol(E2Entry entry) {
        String defaultHelpKindCode = "13";
        if (entry.getDoctorWorkfunction() == null) {
            if (entry.getFondDoctorSpecV021() != null && isEquals(entry.getFondDoctorSpecV021().getCode(), "76")) { //НМП без
                return "12";
            }
            return defaultHelpKindCode;
        }
        String code;
        switch (entry.getDoctorWorkfunction()) {
            case "49": //педиатр
            case "97": //терапевт (не очень, но оставим)
            case "76": // терапия
                code = "12"; //первичная МСП
                break;
            case "206": //фельдшер
                code = "11"; //первичная доврачебная МСП
                break;
            default:
                code = defaultHelpKindCode; //первичная специализированная МСП
        }

        return code;
    }

    private String addSql(String field, String aValue) {
        return isNotLogicalNull(aValue) ? " (" + field + " ='" + aValue + "')" : " (" + field + " is null or " + field + "='')";
    }

    private synchronized void cleanAllMaps() {
        entrySubTypeHashMap.clear();
        //  diagnosisMap = new HashMap<String, Object>();
        // serviceList = new HashMap<String, VocMedService>();
        ksgMap.clear();
        tariffMap.clear();
        cusmoMap.clear();
        //  hospitalCostMap = new HashMap<String, BigDecimal>();
        difficultyHashMap.clear();
        polyclinicCasePrice.clear();
        bedTypes.clear();
        ksgCoefficientMap.clear();
        resultMap.clear(); //результат госпитализации
    }

    public List<E2Entry> getEntriesByListEntryIdAndErrorCode(Long listEntryId, String defectCode) {
        List<BigInteger> ids = manager.createNativeQuery(" select e.id from e2entry e " +
                        " left join e2entrysanction s on s.entry_id=e.id " +
                        "where e.listEntry_id=:listId and s.dopcode=:code")
                .setParameter("listId", listEntryId).setParameter("code", defectCode.trim())
                .getResultList();
        List<Long> longIds = new ArrayList<>();
        for (BigInteger i : ids) {
            longIds.add(i.longValue());
        }
        return manager.createQuery("from E2Entry where id in (:ids)")
                .setParameter("ids", longIds).getResultList();
    }

    /**
     * Создаем онкослучай по умолчанию
     */
    @Override
    public void makeOncologyCase(Long listEntryId, String jsonString, String defectCode) {
        JSONObject aJson = new JSONObject(jsonString);
        List<E2Entry> entryList = getEntriesByListEntryIdAndErrorCode(listEntryId, defectCode);
        if (!entryList.isEmpty()) {
            LOG.info("Создаем онкослучаи, найдено " + entryList.size() + " записей");
            String occasion = getString(aJson, "occasion");
            String consiliumResult = getString(aJson, "consiliumResult");
            String directionType = getString(aJson, "directionType");
            String directionSurveyMethod = getString(aJson, "directionSurveyMethod");
            for (E2Entry entry : entryList) {
                if (isEmpty(entry.getCancerEntries())) {
                    E2CancerEntry cancerEntry = new E2CancerEntry();
                    cancerEntry.setEntry(entry);
                    cancerEntry.setOccasion(occasion);
                    cancerEntry.setConsiliumResult(consiliumResult);
                    manager.persist(cancerEntry);
                    if (directionType != null && !directionType.equals("")) {
                        E2CancerDirection direction = new E2CancerDirection(cancerEntry);
                        direction.setType(directionType);
                        direction.setDate(entry.getFinishDate());
                        direction.setSurveyMethod(directionSurveyMethod);
                        manager.persist(direction);
                    }
                }
            }
        }

    }

    private String getString(JSONObject jsonObject, String fldName) {
        return jsonObject.has(fldName) ? jsonObject.getString(fldName) : null;
    }


    /*Разбиваем обращение на посещения*/
    @Override
    public String splitLongCase(Long entryId) {
        E2Entry entry = manager.find(E2Entry.class, entryId);
        String serviceStream = entry.getServiceStream();
        List<E2Entry> entries = manager.createQuery("from E2Entry where parentEntry=:entry").setParameter("entry", entry).getResultList();
        int i = 0;
        for (E2Entry child : entries) {
            child.setServiceStream(serviceStream);
            child.setParentEntry(null);
            child.setBill(entry.getBill());
            child.setBillNumber(entry.getBillNumber());
            child.setBillDate(entry.getBillDate());
            manager.persist(child);
            makeCheckEntry(child, true, true);
            child.setComment(child.getComment() + "; Случай расклеян из обращения");
            i++;
        }
        entry.setComment(entry.getComment() + ";Обращение расклеяно и удалено!");
        entry.setIsDeleted(true);
        manager.persist(entry);
        return "Расклеено " + i + " записей!";
    }

    /**
     * Расчет предварительной цены случая
     * upd: добавляем расчет цены платного случая только для АМОКБ
     *
     * @param medcaseId ИД случая мед. обслуживания (любого)
     */
    @Override
    public String getMedcaseCost(Long medcaseId) {
        JSONObject ret = new JSONObject();
        ret.put("status", "ok");
        try {
            MedCase medCase = manager.find(MedCase.class, medcaseId);
            boolean calcOmc = medCase.getServiceStream() != null && ("OBLIGATORY".equals(medCase.getServiceStream().getFinanceSource()) || "BUDGET".equals(medCase.getServiceStream().getCode()));
            if (calcOmc) {
                E2Entry sloEntry = new E2Entry();
                ret.put("calcType", "OMC");
                List<HitechMedicalCase> vmps = medCase.getHitechMedicalCases();
                Date finishDate = medCase.getDateFinish() != null ? medCase.getDateFinish() : new Date(System.currentTimeMillis());
                sloEntry.setStartDate(medCase.getDateStart());
                if (isNotEmpty(vmps)) { //Считаем цену по виду ВМП
                    HitechMedicalCase vmp = vmps.get(0);
                    sloEntry.setVmpKind(vmp.getKind().getCode());
                } else if (medCase instanceof ShortMedCase || medCase instanceof PolyclinicMedCase) { // Расчет цены СПО
                    PolyclinicMedCase spo;
                    ShortMedCase visit;
                    if (medCase instanceof ShortMedCase) {
                        visit = (ShortMedCase) medCase;
                        spo = (PolyclinicMedCase) medCase.getParent();
                    } else { //SPO
                        spo = (PolyclinicMedCase) medCase;
                        List<ShortMedCase> shortMedCases = manager.createQuery("from ShortMedCase where parent=:parent order by dateStart desc , timeExecute desc").setParameter("parent", spo).getResultList();
                        visit = shortMedCases.get(0);
                    }
                    PersonalWorkFunction wf = (PersonalWorkFunction) visit.getWorkFunctionExecute();
                    if (wf == null) {
                        ret.put("status", "error");
                        ret.put("errorName1", "Не удалось посчитать цену");
                        return ret.toString();
                    }
                    sloEntry.setEntryType(POLYCLINIC);
                    sloEntry.setIsEmergency(visit.getEmergency());
                    sloEntry.setStartDate(spo.getDateStart());
                    sloEntry.setFinishDate(spo.getDateFinish() != null ? spo.getDateFinish() : new Date(System.currentTimeMillis())); //если открытое СПО - будет разовый визит, не должно произойти.
                    try {
                        sloEntry.setIsMobilePolyclinic(wf.getWorker().getLpu().getIsMobilePolyclinic());
                    } catch (Exception e) {
                        sloEntry.setIsMobilePolyclinic(false);
                    }
                    sloEntry.setWorkPlace(visit.getWorkPlaceType() != null ? visit.getWorkPlaceType().getCode() : "1");
                    sloEntry.setMainMkb(visit.getDiagnoses().isEmpty() ? "Z00.0" : visit.getDiagnoses().get(0).getIdc10().getCode());
                    setEntrySubType(sloEntry);
                    VocWorkFunction vwf = wf.getWorkFunction(); //не считаем цену по поликлинике если врач - не ОМС
                    sloEntry.setMedHelpProfile(vwf.getMedHelpProfile());
                    sloEntry.setCost(isTrue(vwf.getIsNoOmc())
                            ? BigDecimal.ZERO
                            : calculatePolyclinicEntryPrice(sloEntry, sloEntry.getVidSluch(), sloEntry.getFinishDate(), sloEntry.getMedHelpProfile() == null ? null : sloEntry.getMedHelpProfile().getId()));
                } else if (medCase instanceof HospitalMedCase) { //Формируем цену по СЛО
                    DepartmentMedCase departmentMedCase;
                    if (medCase instanceof DepartmentMedCase) {
                        departmentMedCase = (DepartmentMedCase) medCase;
                    } else {
                        departmentMedCase = (DepartmentMedCase) manager.createQuery("from DepartmentMedCase where parent=:parent and (department.isNoOmc is null or department.isNoOmc='0') order by dateStart desc")
                                .setParameter("parent", medCase).getResultList().get(0);
                    }
                    sloEntry.setFinishDate(finishDate);
                    sloEntry.setEntryType(HOSPITAL);
                    sloEntry.setDepartmentId(departmentMedCase.getDepartment().getId());
                    sloEntry.setHelpKind(departmentMedCase.getBedFund().getBedType().getCodeF());
                    sloEntry.setBedSubType(departmentMedCase.getBedFund().getBedSubType().getCode()); //дневные/круглосуточные
                    sloEntry.setMedHelpProfile(getProfileByBedType(sloEntry));
                    sloEntry.setPatientDifficulty(new ArrayList<>());
                    List<Diagnosis> diagnoses = medCase.getDiagnoses();
                    List<EntryDiagnosis> entryDiagnoses = new ArrayList<>();
                    for (Diagnosis d : diagnoses) {
                        entryDiagnoses.add(new EntryDiagnosis(sloEntry, d.getIdc10(), d.getRegistrationType(), d.getPriority(), d.getMkbAdc(), d.getIllnesPrimary() != null ? d.getIllnesPrimary().getOmcCode() : null));
                    }
                    sloEntry.setDiagnosis(entryDiagnoses);
                    List<SurgicalOperation> operationList = medCase.getSurgicalOperations();
                    List<EntryMedService> medServiceList = new ArrayList<>();

                    for (SurgicalOperation so : operationList) {
                        medServiceList.add(new EntryMedService(sloEntry, so.getMedService().getVocMedService()));
                    }
                    List<BigInteger> list = manager.createNativeQuery("SELECT vmssrv.id as msCode FROM MedCase mcsrv LEFT JOIN MedService mssrv ON mcsrv.medservice_id=mssrv.id" +
                                    " LEFT JOIN VocMedService vmssrv ON mssrv.vocmedservice_id=vmssrv.id WHERE mcsrv.parent_id=:id AND mcsrv.DTYPE = 'ServiceMedCase' and vmssrv.isOmc='1'")
                            .setParameter("id", medCase.getId()).getResultList();
                    if (!list.isEmpty()) {
                        for (BigInteger bi : list) {
                            medServiceList.add(new EntryMedService(sloEntry, manager.find(VocMedService.class, bi.longValue())));
                        }
                    }
                    sloEntry.setBirthDate(departmentMedCase.getPatient().getBirthday());
                    long bedDays = AgeUtil.calculateDays(sloEntry.getStartDate(), sloEntry.getFinishDate());
                    sloEntry.setBedDays(bedDays);
                    sloEntry.setCalendarDays(bedDays > 0 ? bedDays + 1 : 1);
                    sloEntry.setMedServices(medServiceList);
                    sloEntry.setKsg(getBestKSG(sloEntry, true));
                    setEntrySubType(sloEntry);
                    sloEntry.setHospitalStartDate(medCase.getDateStart());
                    sloEntry.setHospitalFinishDate(finishDate);
                    sloEntry = calculateEntryPrice(sloEntry);
                } else {
                    LOG.warn("Расчет примерной цены - непонятный случай" + medCase.getClass().getSimpleName());
                    ret.put("status", "error");
                    ret.put("errorName", "Расчет примерной цены - непонятный случай " + medCase.getClass().getSimpleName());
                }
                sloEntry.setSex(medCase.getPatient().getSex().getOmcCode());
                sloEntry.setBirthDate(medCase.getPatient().getBirthday());
                if (sloEntry.getCost() != null) {
                    VocKsg ksg = sloEntry.getKsg();
                    ret.put("ksg", ksg != null ? ksg.getCode() + " " + ksg.getName() : "---");

                    ret.put("price", sloEntry.getCost());
                    ret.put("formulaCost", sloEntry.getCostFormulaString() != null ? sloEntry.getCostFormulaString() : "");
                } else {
                    ret.put("status", "error");
                    ret.put("errorName1", "Не удалось посчитать цену");
                }
            } else { //считаем цену по платному прейскуранту
                ret.put("calcType", "ПЛАТНО");
                int priceListId = 5; //TODO обязательно потом поправить
                if (medCase instanceof ShortMedCase || medCase instanceof PolyclinicMedCase) {
                    long spoId = medCase instanceof ShortMedCase ? medCase.getParent().getId() : medCase.getId();
                    String sql = "select coalesce(sum( pp.cost*coalesce(smc.medserviceamount,1)),0)" +
                            " from medcase spo " +
                            " left join medcase allVis on allVis.parent_id = spo.id " +
                            " left join medcase smc on smc.parent_id = allVis.id" +
                            " left join medservice ms on ms.id=smc.medservice_id" +
                            " left join pricemedservice pms on pms.medservice_id = smc.medservice_id" +
                            " left join priceposition pp on pp.id=pms.priceposition_id " +
                            " where spo.id = " + spoId + " and pp.pricelist_id=" + priceListId;
                    //берем все услуги по всем визитам
                    ret.put("price", manager.createNativeQuery(sql).getSingleResult());
                } else if (medCase instanceof HospitalMedCase) {

                    BigDecimal cost = BigDecimal.ZERO;
                    long slsId = medCase instanceof DepartmentMedCase ? medCase.getParent().getId() : medcaseId;

                    // находим койкодни
                    String sql = "select list(''||(case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'" +
                            " else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vbst.code='1' then 0 else 1 end end" +
                            " * pp.cost)) as ppsum" +
                            " from medcase slo" +
                            " left join bedfund bf on bf.id=slo.bedfund_id" +
                            " left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id" +
                            " left join workPlace wp on wp.id=slo.roomNumber_id" +
                            " left join workfunctionservice wfs on wfs.lpu_id=slo.department_id" +
                            "    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id" +
                            "    and wfs.roomType_id=wp.roomType_id" +
                            " left join medservice ms on ms.id=wfs.medservice_id" +
                            " left join pricemedservice pms on pms.medservice_id=wfs.medservice_id" +
                            " left join priceposition pp on pp.id=pms.priceposition_id" +
                            " and (pp.isvat is null or pp.isvat='0')" +
                            " where slo.parent_id=" + slsId +
                            " and ms.servicetype_id='11' and pp.priceList_id='" + priceListId + "'" +
                            " group by slo.id, pp.id";
                    List<Object> list = manager.createNativeQuery(sql).getResultList();
                    for (Object o : list) {
                        String[] bedCosts = o.toString().split(",");
                        for (String bedCost : bedCosts) {
                            cost = cost.add(new BigDecimal(bedCost.trim()));
                        }
                    }

                    // находим операцию
                    list = manager.createNativeQuery("select pp.cost as ppcost" +
                            " from SurgicalOperation so" +
                            " left join workfunction wf on wf.id=so.surgeon_id" +
                            " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
                            " left join worker w on w.id=wf.worker_id" +
                            " left join patient wp on wp.id=w.person_id" +
                            " left join medcase slo on slo.id=so.medcase_id" +
                            " left join vocservicestream vss on vss.id=so.servicestream_id" +
                            " left join medservice ms on ms.id=so.medservice_id" +
                            " left join pricemedservice pms on pms.medservice_id=so.medservice_id" +
                            " left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id=" + priceListId +
                            " where (slo.parent_id=" + slsId + " or slo.id=" + slsId + ")" +
                            " and pp.id is not null and (pms.dateto is null or pms.dateto>=so.operationDate)").getResultList();
                    for (Object o : list) {
                        if (o != null) cost = cost.add(new BigDecimal(o.toString().trim()));
                    }

                    // находим анастезию
                    list = manager.createNativeQuery("select pp.cost as ppcost" +
                            " from Anesthesia aso" +
                            " left join SurgicalOperation so on so.id=aso.surgicalOperation_id" +
                            " left join medcase slo on slo.id=so.medcase_id" +
                            " left join pricemedservice pms on pms.medservice_id=aso.medservice_id" +
                            " left join priceposition pp on pp.id=pms.priceposition_id" +
                            " where (slo.parent_id=" + slsId + " or slo.id=" + slsId + ") and  pp.priceList_id=" + priceListId).getResultList();
                    for (Object o : list) {
                        if (o != null) cost = cost.add(new BigDecimal(o.toString().trim()));
                    }

                    // услуги из листов назначений
                    list = manager.createNativeQuery("select pp.cost" +
                            " from medcase sls" +
                            " left join medcase slo on slo.parent_id = sls.id and slo.dtype='DepartmentMedCase'" +
                            " left join prescriptionlist pl on pl.medcase_id = sls.id or pl.medcase_id = slo.id" +
                            " left join prescription p on p.prescriptionlist_id = pl.id" +
                            " left join pricemedservice pms on pms.medservice_id = p.medservice_id" +
                            " left join priceposition pp on pp.id = pms.priceposition_id" +
                            " where sls.id = " + slsId + " and p.dtype = 'ServicePrescription' and p.canceldate is null and pp.pricelist_id =" + priceListId).getResultList();
                    for (Object o : list) {
                        if (o != null) cost = cost.add(new BigDecimal(o.toString().trim()));
                    }
                    ret.put("price", cost.setScale(2, RoundingMode.HALF_UP));
                }
            }

        } catch (Exception e) {
            ret.put("status", "error");
            ret.put("errorCode", e.getLocalizedMessage());
            LOG.error(medcaseId + " Ошибка нахождения цены " + e.getMessage(), e);
        }
        return ret.toString();
    }

    private VocOmcMedServiceCost getMedServiceOmc(VocMedService vms, Date medServiceDate) {
        if (vms == null || medServiceDate == null) return null;
        List<VocOmcMedServiceCost> costs = manager.createNamedQuery("VocOmcMedServiceCost.getByCodeAndDate").setParameter("code", vms.getCode())
                .setParameter("finishDate", medServiceDate).getResultList();
        return costs.isEmpty() ? null : costs.get(0);
    }

    private BigDecimal getMedServiceCost(VocMedService vms, Date medServiceDate) {
        VocOmcMedServiceCost costs = getMedServiceOmc(vms, medServiceDate);
        return costs == null ? BigDecimal.ZERO : costs.getCost();
    }

    @Override
    public String fixFondAnswerError(Long listEntryId, String sanctionCode) {
        List<E2EntrySanction> errorEntries = manager.createQuery("from E2EntrySanction es where es.dopCode=:errorCode and entry.listEntry.id=:listId  ")
                .setParameter("errorCode", sanctionCode).setParameter("listId", listEntryId).getResultList();
        int good;
        if ("223".equals(sanctionCode)) { //223 - полиса
            E2Entry entry;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DisabilityServiceBean httpBean = new DisabilityServiceBean();
            String restFondApiAddress = "http://127.0.0.1:8080/riams/api/foncCheck"; //TODO переделать
            LOG.info("check " + sanctionCode + ">" + errorEntries.size());
            good = 0;
            for (E2EntrySanction sanction : errorEntries) {
                entry = sanction.getEntry();
                long serviceDate = entry.getStartDate().getTime();
                String series = entry.getMedPolicySeries();
                String polnumber = entry.getMedPolicyNumber();
                try {
                    String appendUrl = "check?number=" + URLEncoder.encode(polnumber, "utf-8") + (isNotLogicalNull(series) ? "&series=" + URLEncoder.encode(series, "utf-8") : "");
                    String answer = httpBean.makeHttpGetRequest(restFondApiAddress, appendUrl);
                    JSONObject fond = new JSONObject(answer);
                    JSONArray policies = fond.getJSONArray("Polis");
                    boolean policyFound = false;
                    for (int i = 0; i < policies.length(); i++) {
                        JSONObject policy = policies.getJSONObject(i);
                        long polStartDate = sdf.parse(policy.getString("dateStart").substring(0, 10)).getTime();
                        long polFinishDate = sdf.parse(policy.getString("dateEarlyEnd").substring(0, 10)).getTime();
                        if (polStartDate < serviceDate && serviceDate < polFinishDate) {
                            good++;
                            policyFound = true;
                            String fondPolType = policy.getString("typePolicy");
                            String fondPolNumber = policy.getString("seriesAndNumber");

                            boolean isNew = fondPolType.length() == 5;
                            if (isNew) { //новый
                                entry.setMedPolicySeries("");
                                entry.setMedPolicyNumber(fondPolNumber);
                                entry.setMedPolicyType("3");

                            } else { //временный
                                String[] fondPolNumberData = fondPolNumber.split(" ");
                                entry.setMedPolicySeries(fondPolNumberData[0]);
                                entry.setMedPolicyNumber(fondPolNumberData[1]);
                                entry.setMedPolicyType("2");
                            }
                            manager.persist(entry);
                            sanction.setDopCode("FIX_" + sanction.getDopCode());
                            manager.persist(sanction);
                        }
                    }
                    LOG.info((policyFound ? "" : "NOT ") + "found actual policy " + entry.getLastname());
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        } else if ("1087".equals(sanctionCode)) { //Добавляем лек. назначение для лечения ковида
            good = 0;
            E2DrugEntry original = createCovidDrugEntry(getExpertConfigValue(Expert2Config.DEFAULT_COVID_DRUG_CODE, "002983:2:10:1-1-1:200:2"));
            for (E2EntrySanction sanction : errorEntries) {
                E2Entry entry = sanction.getEntry();
                E2DrugEntry drugEntry = new E2DrugEntry(original, entry);
                drugEntry.setInjectDate(entry.getStartDate());
                sanction.setDopCode("FIX_" + sanction.getDopCode());
                if (entry.getWeigth() == null && entry.getWeigth().equals(0)) {
                    entry.setWeigth(70);
                    manager.persist(entry);
                }
                manager.persist(drugEntry);
                good++;
            }
        } else {
            LOG.warn("Не понимаю что вы хотите: " + sanctionCode);
            good = -1;
        }

        return "Всего найдено: " + errorEntries.size() + ", исправлено: " + good;
    }

    /*
    drugCode:injectMetod:injectUnit:groupSchema:injectAmount:injectBumber
     */

    private E2DrugEntry createCovidDrugEntry(String covidString) {
        String[] codes = covidString.split(":");
        E2DrugEntry drugEntry = new E2DrugEntry();
        drugEntry.setDrug(getActualVocByCode(VocE2FondN020.class, codes[0])); //Фавипиравир
        drugEntry.setInjectMethod(getActualVocByCode(VocE2FondV035.class, codes[1])); //внутрь (перорально)
        drugEntry.setInjectUnit(getActualVocByCode(VocE2FondV034.class, codes[2])); //миллиграм
        drugEntry.setDrugGroupSchema(getActualVocByCode(VocE2FondV032.class, codes[3]));
        drugEntry.setInjectAmount(codes[4]);
        drugEntry.setInjectNumber(Integer.parseInt(codes[5]));
        return drugEntry;
    }

    @Override
    @Transient
    public String loadDrugSchemaByDrugGroupSchemaId(Long groupSchemaId) {
        VocE2FondV030 schema = manager.find(VocE2FondV032.class, groupSchemaId).getSchema();
        JsonObject jso = new JsonObject();
        jso.addProperty("schemaId", schema.getId());
        jso.addProperty("schemaName", schema.getName());
        return jso.toString();
    }
}