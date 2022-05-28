package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.sequence.service.SequenceHelper;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.Expert2FondUtil;
import ru.ecom.expert2.domain.*;
import ru.ecom.expert2.domain.voc.VocE2BillStatus;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV027;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.expomc.ejb.services.exportservice.ExportServiceBean;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.util.CollectionUtil;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.date.AgeUtil;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.nuzmsh.util.BooleanUtils.isNotTrue;
import static ru.nuzmsh.util.BooleanUtils.isTrue;
import static ru.nuzmsh.util.CollectionUtil.isEmpty;
import static ru.nuzmsh.util.CollectionUtil.isNotEmpty;
import static ru.nuzmsh.util.EqualsUtil.isAnyIsNull;
import static ru.nuzmsh.util.EqualsUtil.isOneOf;
import static ru.nuzmsh.util.StringUtil.isNullOrEmpty;

@Stateless
@Local(IExpert2XmlService.class)
@Remote(IExpert2XmlService.class)
public class Expert2XmlServiceBean implements IExpert2XmlService {
    private static final Logger LOG = Logger.getLogger(Expert2XmlServiceBean.class);
    private static final String HOSPITAL_TYPE = "HOSPITAL";
    private static final String HOSPITAL_PEREVOD_TYPE = "HOSPITALPEREVOD";
    private static final String POLYCLINIC_TYPE = "POLYCLINIC";
    private static final String VMP_TYPE = "VMP";
    private static final String EXTDISP_TYPE = "EXTDISP";
    private static final String KDP_TYPE = "POL_KDP";
    private static final String SERVICE_TYPE = "SERVICE";
    private static final String CENTRAL_SEGMENT_DOC = "CENTRAL_SEGMENT";
    private final static String EXPORT_DIR = "/rtf/expert2xml/";
    private List<String> DISP_LIST = new ArrayList<>();
    private boolean isCheckIsRunning = false;
    private boolean exchangeCovidDs = false;
    private boolean exportDispServiceNoDate = false;
    private @PersistenceContext
    EntityManager manager;
    private @EJB
    ILocalMonitorService monitorService;
    private @EJB
    IExpert2Service expertService;

    /**
     * Экспорт запроса в центральный сегмент
     */
    @Override
    public String exportToCentralSegment(Long listEntryId, String historyNumbers) {
        StringBuilder sqlAdd = new StringBuilder();
        if (isNullOrEmpty(historyNumbers)) {
            sqlAdd.append("isForeign='1' and serviceStream='OBLIGATORYINSURANCE'"); // по умолчанию - иногородние омс
        } else {
            boolean first = true;
            for (String h : historyNumbers.split(",")) {
                if (!first) sqlAdd.append(",");
                else first = false;
                sqlAdd.append("'").append(h).append("'");
            }
            sqlAdd.insert(0, "historyNumber in (");
            sqlAdd.append(") ");
        }

        String sql = "from E2Entry where listEntry_id=:listId and " + sqlAdd.toString() + " and  coalesce(isDeleted, false) = false";
        List<Object> list = manager.createQuery(sql).setParameter("listId", listEntryId).getResultList();
        File dbfFile = new File(getWorkDir() + "/expert2xml/" + listEntryId + "_flyToFond.dbf");
        List<ImportDocument> documents = manager.createNamedQuery("ImportDocument.findByKey").setParameter("key", CENTRAL_SEGMENT_DOC).getResultList();
        Format exportFormat = documents.isEmpty() ? null : documents.get(0).getDefaultFormat();
        if (exportFormat != null) {
            try {
                if ((!dbfFile.exists() || dbfFile.delete()) && dbfFile.createNewFile()) {
                    ExportServiceBean.export(exportFormat, list, dbfFile, null, Long.valueOf(list.size()), null);
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
            return dbfFile.getName();
        } else {
            throw new IllegalStateException("Нет формата для выгрузки для документа с кодом '" + CENTRAL_SEGMENT_DOC + "'");
        }
    }

    /**
     * Переводим дату в строку в формате yyyy-DD-mm
     */
    private String dateToString(Date date) {
        return dateToString(date, "yyyy-MM-dd");
    }

    /**
     * Переводим дату в строку в заданном формате
     */
    private String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Создаем запись в L файле
     */
    private Element createPERSElement(E2Entry entry) {

        boolean isKinsman = isNotNull(entry.getKinsmanLastname());
        Element pers = new Element("PERS");
        add(pers, "ID_PAC", entry.getExternalPatientId().toString());
        if (isKinsman) { //Заполняем данные о представителе
            add(pers, "FAM", "НЕТ");
            add(pers, "IM", "НЕТ");
            add(pers, "OT", "НЕТ");
            add(pers, "W", entry.getSex());
            add(pers, "DR", dateToString(entry.getBirthDate()));
            add(pers, "FAM_P", entry.getKinsmanLastname());
            add(pers, "IM_P", entry.getKinsmanFirstname());
            add(pers, "OT_P", entry.getKinsmanMiddlename());
            add(pers, "W_P", entry.getKinsmanSex());
            add(pers, "DR_P", dateToString(entry.getKinsmanBirthDate()));
        } else {
            add(pers, "FAM", entry.getLastname());
            add(pers, "IM", entry.getFirstname());
            addIfNotNull(pers, "OT", entry.getMiddlename());
            add(pers, "W", entry.getSex());
            add(pers, "DR", dateToString(entry.getBirthDate()));
        }
        //MR
        add(pers, "DOCTYPE", isNotNull(entry.getPassportType()) ? entry.getPassportType() : "14");
        addIfNotNull(pers, "DOCSER", entry.getPassportSeries());
        addIfNotNull(pers, "DOCNUM", entry.getPassportNumber());
        if (isTrue(entry.getIsForeign())) {
            addIfNotNull(pers, "DOCDATE", entry.getPassportDateIssued());
            addIfNotNull(pers, "DOCORG", entry.getPassportWhomIssued());
        }
        addIfNotNull(pers, "SNILS", isKinsman ? entry.getKinsmanSnils() : entry.getPatientSnils());
        return pers;
    }

    /**
     * Добавляем в переданный элемент новый элемент со значением, в случае, если значение не пустое
     */
    private void addIfNotNull(Element element, String elementName, Object value) {
        if (isNotNull(value)) {
            if (value instanceof Boolean) {
                value = Boolean.TRUE.equals(value) ? "1" : "0";
            }
            add(element, elementName, value);
        }
    }

    /**
     * Расчет строки с признаком новорожденного
     */
    private String makeNovorString(E2Entry entry) {
        if (isNotNull(entry.getKinsmanLastname())) {
            SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
            return entry.getSex() + "" + format.format(entry.getBirthDate()) + "" + (isNotNull(entry.getBirthOrder()) ? entry.getBirthOrder() : 1);
        } else {
            return "0";
        }
    }

    /**
     * Создаем тэг с информацией о госпитализации (версия 3.2*)
     */
    private Element createZSl2020(E2Entry entry, boolean isPolyclinic, int zslIdCase, String lpuRegNumber
            , boolean a3) {
        String forPom = isTrue(entry.getIsEmergency()) ? (isPolyclinic ? "2" : "1") : "3";
        Element z = new Element("Z_SL");
        add(z, "IDCASE", zslIdCase + "");
        add(z, "VID_SLUCH", entry.getVidSluch().getCode());
        if (!a3) add(z, "USL_OK", entry.getMedHelpUsl().getCode()); //дневной-круглосуточный-поликлиника
        add(z, "VIDPOM", entry.getMedHelpKind().getCode());
        if (!a3) {
            add(z, "FOR_POM", forPom); //форма помощи V014
            if (isPolyclinic || isNotTrue(entry.getIsEmergency())) {
                addIfNotNull(z, "NPR_MO", coalesceTrim(entry.getDirectLpu(), lpuRegNumber)); //Направившее ЛПУ
                addIfNotNull(z, "NPR_DATE", coalesce(entry.getDirectDate(), entry.getStartDate())); //Дата направления на лечение ***
            }
        }


        add(z, "LPU", lpuRegNumber); //ЛПУ лечения F003 //01-01-2020
        if (a3) add(z, "VBR", isTrue(entry.getIsMobilePolyclinic()) ? "1" : "0"); //Признак мобильной бригады
        add(z, "DATE_Z_1", "_"); //Дата начала случая
        add(z, "DATE_Z_2", "_"); //Дата окончания случая
        if (a3) add(z, "P_OTK", "0");
        if (!isPolyclinic && !a3) add(z, "KD_Z", entry.getBedDays() + ""); // Продолжительность госпитализации
        if (a3)
            add(z, "RSLT_D", entry.getDispResult() != null ? entry.getDispResult().getCode() : "___"); // Результат диспансеризации
        if (!a3) {
            add(z, "RSLT", entry.getFondResult().getCode()); // Результат обращения
            add(z, "ISHOD", entry.getFondIshod().getCode()); // Исход случая.
        }
        addIfNotNull(z, "OS_SLUCH", Expert2FondUtil.calculateFondOsSluch(entry)); // Особый случай
     /*   if (!isPoliclinic && !a3
                && (isOneOf(entry.getFondResult().getCode(), "104", "204"))
                || entry.getMedHelpProfile().getCode().equals("136") && entry.havePrevMedCase())
            add(z, "VB_P", "1"); // Признак внутрибольничного перевода *05.08 1 - только если есть перевод
       */
        z.addContent(new Element("SL_TEMPLATE")); // Список случаев
        add(z, "IDSP", entry.getIdsp().getCode()); // Способ оплаты медицинской помощи (V010)
        add(z, "SUMV", entry.getCost()); // Сумма, выставленная к оплате
        return z;
    }

    /**
     * Добавляем в Element значение
     */
    private void add(Element el, String fieldName, Object value) {
        el.addContent(new Element(fieldName).setText(value != null ? value.toString() : ""));
    }

    /**
     * Добавляем в Element значение
     */
    private void add(int index, Element el, String fieldName, Object value) {
        el.addContent(index, new Element(fieldName).setText(value != null ? value.toString() : ""));
    }

    /**
     * Создааем информацию по случаю мед. обслуживания для версии 2020 года
     * aEntry - случай госпитализации
     * entriesList - строка с ИД СЛО
     */
    private Element createSlElements2020(E2Entry entry, String entriesString, int cnt, String lpuRegNumber, String fileType) {

            /*
            ZSL, SL = информация об обращении. визиты переносятся в USL
            * */
        try {
            String entryType = entry.getEntryType();
            boolean a1, a2, a3, a4;
            switch (fileType) {
                case "H":
                case "P":
                case "S":
                    a1 = true;
                    a2 = a3 = a4 = false;
                    break;
                case "T":
                    a2 = true;
                    a1 = a3 = a4 = false;
                    break;
                case "C":
                case "PC":
                    a4 = true;
                    a1 = a2 = a3 = false;
                    break;
                default: //диспансеризация
                    a3 = true;
                    a1 = a2 = a4 = false;
            }
            boolean isHosp, isVmp, isPoliclinic, isExtDisp, isPoliclinicKdp, isNedonosh;
            switch (entryType) {
                case HOSPITAL_TYPE:
                    isHosp = true;
                    isVmp = isPoliclinic = isExtDisp = isPoliclinicKdp = false;
                    isNedonosh = isTrue(entry.getIsNedonosh()) || isNedonoshKsg(entry.getKsg());
                    break;
                case VMP_TYPE:
                    isVmp = true;
                    isHosp = isPoliclinic = isExtDisp = isPoliclinicKdp = false;
                    isNedonosh = isTrue(entry.getIsChild());
                    break;
                case POLYCLINIC_TYPE:
                case SERVICE_TYPE:
                    isPoliclinic = true;
                    isHosp = isVmp = isExtDisp = isPoliclinicKdp = isNedonosh = false;
                    break;
                case KDP_TYPE:
                    isPoliclinic = isPoliclinicKdp = true;
                    isHosp = isVmp = isExtDisp = isNedonosh = false;
                    break;
                case EXTDISP_TYPE:
                    isExtDisp = true;
                    isHosp = isVmp = isPoliclinic = isPoliclinicKdp = isNedonosh = false;
                    break;
                default:
                    throw new IllegalStateException("UNKNOWN ENTRYTYPE" + entryType);
            }
            Element zap = new Element("ZAP");
            add(zap, "N_ZAP", entry.getId() + "");
            add(zap, "PR_NOV", isTrue(entry.getPrnov()) ? "1" : "0");
            Element pat = new Element("PACIENT");
            add(pat, "ID_PAC", entry.getExternalPatientId() + "");
            add(pat, "VPOLIS", entry.getMedPolicyType());
            if (!"3".equals(entry.getMedPolicyType())) {
                addIfNotNull(pat, "SPOLIS", entry.getMedPolicySeries()); //Если полис не нового образца - добавляем серию полиса
                add(pat, "NPOLIS", entry.getMedPolicyNumber());
                add(pat, "ST_OKATO", "12000");
            }

            if (isNotNull(entry.getInsuranceCompanyCode())) {
                add(pat, "SMO", entry.getInsuranceCompanyCode());
                if ("3".equals(entry.getMedPolicyType())) {
                    add(pat, "ENP", entry.getCommonNumber());
                }
            } else {
                add(pat, "SMO_OGRN", entry.getInsuranceCompanyOgrn());
                if ("3".equals(entry.getMedPolicyType())) {
                    add(pat, "ENP", entry.getCommonNumber());
                }
                add(pat, "SMO_NAM", entry.getInsuranceCompanyName());
            }
            add(pat, "NOVOR", makeNovorString(entry));
            if (a1 && isNedonosh) {
                add(pat, "VNOV_D", entry.getNewbornWeight() + "");
            }
            zap.addContent(pat); //Добавили данные по пациенту
            List<E2Entry> children;
            String isChild = isTrue(entry.getIsChild()) ? "1" : "0";

            String[] slIds = entriesString.split(",");
            Element zSl = createZSl2020(entry, isPoliclinic, cnt, lpuRegNumber
                    , a3);
            int indSl = zSl.indexOf(zSl.getChild("SL_TEMPLATE"));
            final int firstIndSl = indSl;
            Date startHospitalDate = null;
            Date finishHospitalDate = null;

            /* Вот тут создаем Sl*/
            boolean isDiagnosisFill = false;
            boolean isLongCase = slIds.length > 1;
            String version = "V021";
            for (String slId : slIds) {
                Element sl = new Element("SL");
                E2Entry currentEntry = manager.find(E2Entry.class, Long.valueOf(slId.trim()));
                if (validateEntry(currentEntry, isPoliclinic, isExtDisp)) {
                    return null;
                }
                String edCol = isTrue(entry.getIsDentalCase()) ? calculateDentalEdcol(entry) : "1";

                if (isPoliclinic) {
                    children = manager.createQuery("from E2Entry where parentEntry_id=:id and coalesce(isDeleted, false) = false and coalesce(doNotSend, false) = false order by startDate").setParameter("id", currentEntry.getId()).getResultList();
                } else {
                    children = new ArrayList<>();
                }

                boolean isCancer = isTrue(currentEntry.getIsCancer()) && (currentEntry.getVisitPurpose() == null || !"1.3".equals(currentEntry.getVisitPurpose().getCode()));

                E2CancerEntry cancerEntry;
                if (isCancer && !currentEntry.getCancerEntries().isEmpty()) {
                    cancerEntry = currentEntry.getCancerEntries().get(0); //Считаем что не может быть больше 1 онкослучая
                } else {
                    cancerEntry = null;
                }

                VocE2MedHelpProfile profile = currentEntry.getMedHelpProfile();
                String profileK = profile.getCode();

                String startDate = dateToString(currentEntry.getStartDate());
                String finishDate = dateToString(currentEntry.getFinishDate());
                if (startHospitalDate == null || startHospitalDate.getTime() > currentEntry.getStartDate().getTime()) {
                    startHospitalDate = currentEntry.getStartDate();
                }
                if (finishHospitalDate == null || finishHospitalDate.getTime() < currentEntry.getFinishDate().getTime()) {
                    finishHospitalDate = currentEntry.getFinishDate();
                }
                add(sl, "SL_ID", slId);
                if (isVmp) {
                    if (isAnyIsNull(currentEntry.getVmpTicketDate(), currentEntry.getVmpTicketNumber()
                            , currentEntry.getVmpPlanHospDate(), currentEntry.getVmpKind(),
                            currentEntry.getVmpMethod())) {
                        manager.persist(new E2EntryError(currentEntry, "NO_FOND_FIELD: В случае ВМП не заполнены обязательные поля"));
                        return null;
                    }
                    add(sl, "VID_HMP", currentEntry.getVmpKind()); // Вид ВМП
                    add(sl, "METOD_HMP", currentEntry.getVmpMethod()); // Метод ВМП
                }
                add(sl, "LPU_1", currentEntry.getDepartmentCode() != null ? currentEntry.getDepartmentCode() : "30000101");
                addIfNotNull(sl, "PODR", currentEntry.getDepartmentAddressCode());
                if (!a3) add(sl, "PROFIL", profile.getCode()); //Профиль специальностей V002 * 12.12.2018
                if (isHosp || isVmp) {
                    if (currentEntry.getBedProfile() == null) {
                        LOG.error("Нет профиля койки у записи: " + currentEntry);
                        manager.persist(new E2EntryError(currentEntry, "NO_FOND_FIELD: Нет профиля койки"));
                        return null;
                    }
                    add(sl, "PROFIL_K", currentEntry.getBedProfile().getCode()); //Профиль коек/специальностей (V020)
                }
                if (!a3) add(sl, "DET", isChild); //Признак детского возраста
                if (isPoliclinic) { //a1 a4
                    add(sl, "P_CEL", currentEntry.getVisitPurpose().getCode()); // Цель посещения
                }
                if (a2) {
                    add(sl, "TAL_D", currentEntry.getVmpTicketDate()); // Дата выдачи талона ВМП
                    add(sl, "TAL_NUM", currentEntry.getVmpTicketNumber()); // Номер выдачи ВМП
                    addIfNotNull(sl, "TAL_P", dateToString(currentEntry.getVmpPlanHospDate())); // Дата планируемой госпитализации
                }
                add(sl, "NHISTORY", currentEntry.getHistoryNumber()); //Номер истории болезни
                if (isHosp) {
                    add(sl, "P_PER", Expert2FondUtil.calculateFondP_PER(currentEntry)); //Признак перевода
                }
                add(sl, "DATE_1", startDate); //Дата начала случая
                add(sl, "DATE_2", finishDate); //Дата окончания случая
                if (isHosp) {
                    add(sl, "KD", currentEntry.getBedDays()); //Продолжительность госпитализации
                }
                setSluchDiagnosis(sl, currentEntry, a3);
                if (sl == null) {
                    manager.persist(new E2EntryError(currentEntry, "NO_MAIN_DIAGNOSIS"));
                    return null;
                }
                if (!a1) add(sl, "DS_ONK", (cancerEntry != null && isTrue(cancerEntry.getMaybeCancer())) ? "1" : "0");
                if (isPoliclinic ) {
                    if (isNotNull(currentEntry.getDn())) {
                        add(sl, "DN", currentEntry.getDn());
                    } else if (DISP_LIST.contains(currentEntry.getMainMkb())) {
                        //*DN дисп. наблюдение !! только для терр.
                        add(sl, "DN", "1"); //todo
                    }

                }
                if (a3) add(sl, "PR_D_N", "1"); // взят-состоит
                //if (a3) *DS2_N
                //CODE_MES1
                if (isTrue(entry.getIsDentalCase())) {
                    addIfNotNull(sl, "CODE_MES1", calcCodeMes(entry));
                }
                //CODE_MES2
                if (a2 || a4) { //NAPR, CONS, ONK_SL
                    if (isCancer && cancerEntry != null) { //NAPR
                        //  if (cancerEntry.getMaybeCancer()) { //Заполняем информация при подозрении на ЗНО
                        List<E2CancerDirection> directions = cancerEntry.getDirections();
                        if (!directions.isEmpty()) {
                            for (E2CancerDirection direction : directions) {
                                Element napr = new Element("NAPR");
                                add(napr, "NAPR_DATE", direction.getDate());
                                addIfNotNull(napr, "NAPR_MO", direction.getDirectLpu());
                                add(napr, "NAPR_V", direction.getType());
                                addIfNotNull(napr, "MET_ISSL", direction.getSurveyMethod());
                                addIfNotNull(napr, "NAPR_USL", direction.getMedService());
                                sl.addContent(napr);
                            }
                        }
                        String consiliumResult = cancerEntry.getConsiliumResult();
                        if (isNotNull(consiliumResult)) { //Консилиум в любом случае если есть ЗНО.
                            Element cons = new Element("CONS");
                            add(cons, "PR_CONS", consiliumResult);
                            if (isOneOf(consiliumResult, "1", "2", "3")) {
                                add(cons, "DT_CONS", cancerEntry.getConsiliumDate());
                            }
                            sl.addContent(cons);
                        }
                        if (isNotTrue(cancerEntry.getMaybeCancer())) { //Случай ЗНО
                            Element onkSl = new Element("ONK_SL");
                            if (cancerEntry.getOccasion() == null || (cancerEntry.getOccasion().equals("0") && (
                                    isAnyIsNull(cancerEntry.getTumor(), cancerEntry.getNodus(),
                                            cancerEntry.getMetastasis())))
                            ) {
                                manager.persist(new E2EntryError(currentEntry, "NO_CANCERINFO"));
                                return null;
                            }
                            String serviceType = coalesce(cancerEntry.getServiceType(), "");
                            add(onkSl, "DS1_T", cancerEntry.getOccasion());
                            addIfNotNull(onkSl, "STAD", cancerEntry.getStage());
                            addIfNotNull(onkSl, "ONK_T", cancerEntry.getTumor());
                            addIfNotNull(onkSl, "ONK_N", cancerEntry.getNodus());
                            addIfNotNull(onkSl, "ONK_M", cancerEntry.getMetastasis());
                            addIfNotNull(onkSl, "MTSTZ", cancerEntry.getIsMetastasisFound());
                            addIfNotNull(onkSl, "SOD", cancerEntry.getSod());
                            //K_FR
                            if (serviceType.equals("2")) {
                                add(onkSl, "WEI", currentEntry.getWeigth());
                                add(onkSl, "HEI", currentEntry.getHeight());
                            }
                            List<E2CancerDiagnostic> diagnostics = cancerEntry.getDiagnostics();
                            for (E2CancerDiagnostic diagnostic : diagnostics) {
                                Element dir = new Element("B_DIAG");
                                add(dir, "DIAG_DATE", diagnostic.getBiopsyDate());
                                add(dir, "DIAG_TIP", diagnostic.getType());
                                add(dir, "DIAG_CODE", diagnostic.getCode());
                                add(dir, "DIAG_RSLT", diagnostic.getResult());
                                add(dir, "REC_RSLT", "1");
                                onkSl.addContent(dir);
                            }
                            List<E2CancerRefusal> prots = cancerEntry.getRefusals();
                            for (E2CancerRefusal prot : prots) {
                                Element pr = new Element("B_PROT");
                                add(pr, "PROT", prot.getCode());
                                add(pr, "D_PROT", prot.getDate());
                                onkSl.addContent(pr);
                            }
                            if (isHosp || isVmp) {
                                Element onkUsl = new Element("ONK_USL");
                                if (serviceType.equals("4")) {
                                    manager.persist(new E2EntryError(currentEntry, "ONCOLOGY_CASE_DRUG", "Не может быть вид онколечения: " + serviceType + " химиолучевая терапия"));
                                }
                                add(onkUsl, "USL_TIP", serviceType);
                                if (serviceType.equals("1")) {
                                    addIfNotNull(onkUsl, "HIR_TIP", cancerEntry.getSurgicalType());
                                } else if (serviceType.equals("2")) {
                                    addIfNotNull(onkUsl, "LEK_TIP_L", cancerEntry.getDrugLine());
                                    addIfNotNull(onkUsl, "LEK_TIP_V", cancerEntry.getDrugCycle());
                                }

                                if (isOneOf(serviceType, "2", "4")) { //Сведения о введенном противоопухолевом препарате
                                    List<E2CancerDrug> drugList = cancerEntry.getDrugs();
                                    for (E2CancerDrug drug : drugList) {
                                        Element lekPr = new Element("LEK_PR");
                                        add(lekPr, "REGNUM", drug.getDrug() != null ? drug.getDrug().getCode() : "_____" + currentEntry.getId());
                                        add(lekPr, "CODE_SH", coalesceTrim(entry.getDopKritKSG(), "gem"));
                                        if (CollectionUtil.isEmpty(drug.getDates())) {
                                            manager.persist(new E2EntryError(entry, "NO_DATE_IN_DRUG"));
                                            return null;
                                        }
                                        for (E2CancerDrugDate drugDate : drug.getDates()) {
                                            add(lekPr, "DATE_INJ", drugDate.getDate());
                                        }
                                        onkUsl.addContent(lekPr);
                                    }
                                }
                                if (isOneOf(serviceType, "3", "4")) {
                                    addIfNotNull(onkUsl, "LUCH_TIP", cancerEntry.getRadiationTherapy()); //Тип лучевой терапии
                                }
                                onkSl.addContent(onkUsl);
                            }
                            sl.addContent(onkSl);
                        }
                    } else if (isCancer) {
                        LOG.warn("Не найден раковый случай для записи с ИД: " + currentEntry.getId());
                    }
                }

                if (isHosp) {//KSG_KGP
                    Element ksgKpg = new Element("KSG_KPG");
                    VocKsg ksg = currentEntry.getKsg();
                    add(ksgKpg, "N_KSG", ksg.getCode());
                    add(ksgKpg, "VER_KSG", ksg.getYear());
                    add(ksgKpg, "KSG_PG", "0");
                    add(ksgKpg, "KOEF_Z", ksg.getKz());
                    add(ksgKpg, "KOEF_UP", expertService.getActualKsgUprCoefficient(ksg, currentEntry.getFinishDate()));
                    add(ksgKpg, "BZTSZ", currentEntry.getBaseTarif());
                    add(ksgKpg, "KOEF_D", "1");
                    add(ksgKpg, "KOEF_U", "1");
                    if (currentEntry.getKsgPosition() != null) {
                        addIfNotNull(ksgKpg, "CRIT", currentEntry.getKsgPosition().getDopPriznak());
                    }
                    //DKK2
                    List<E2CoefficientPatientDifficultyEntryLink> difficultyEntryLinks = currentEntry.getPatientDifficulty();
                    if (!difficultyEntryLinks.isEmpty()) {
                        add(ksgKpg, "SL_K", "1");
                        add(ksgKpg, "IT_SL", expertService.calculateResultDifficultyCoefficient(currentEntry));
                        for (E2CoefficientPatientDifficultyEntryLink link : difficultyEntryLinks) {
                            Element slKoef = new Element("SL_KOEF");
                            add(slKoef, "IDSL", link.getDifficulty().getCode());
                            add(slKoef, "Z_SL", coalesce(link.getValue(), link.getDifficulty().getValue()));
                            ksgKpg.addContent(slKoef);
                        }
                    } else {
                        add(ksgKpg, "SL_K", "0");
                    }
                    sl.addContent(ksgKpg);
                }
                if (isHosp && isTrue(entry.getIsRehabBed())) {
                    add(sl, "REAB", "1");
                }
                String prvs = currentEntry.getFondDoctorSpecV021() != null ? currentEntry.getFondDoctorSpecV021().getCode() : "ERROR_" + currentEntry.getDoctorWorkfunction();
                if (!a3) {
                    add(sl, "PRVS", prvs); //Специальность лечащего врача
                    add(sl, "VERS_SPEC", version);
                    add(sl, "IDDOKT", currentEntry.getDoctorSnils()); // СНИЛС лечащего врача
                }

                if (a3 && entry.getDispResult() != null && ",3,4,5,14,15,19,17,18,31,32,".contains("," + entry.getDispResult().getCode() + ",")) { //нужная нам группа здоровья
                    Element naz = new Element("NAZ");
                    add(naz, "NAZ_N", "1");
                    add(naz, "NAZ_R", "3");
                    add(naz, "NAZ_V", "1");
                    sl.addContent(naz);
                }
                add(sl, "ED_COL", edCol); //для стомов - кол-во УЕТ
                if (isPoliclinicKdp) {
                    add(sl, "TARIF", entry.getCost());
                    add(sl, "SUM_M", entry.getCost());
                } else if (isPoliclinic && isLongCase) {
                    if (!isDiagnosisFill && sl.getChild("DS1") != null && !sl.getChildText("DS1").startsWith("Z")) {
                        isDiagnosisFill = true;
                        add(sl, "TARIF", entry.getCost());
                        add(sl, "SUM_M", entry.getCost());
                    } else {
                        add(sl, "TARIF", "0");
                        add(sl, "SUM_M", "0");
                    }
                } else {
                    add(sl, "TARIF", currentEntry.getCost());
                    add(sl, "SUM_M", currentEntry.getCost());
                }

                //USL start
                int uslCnt = 0;
                if (currentEntry.getReanimationEntry() != null && !isVmp) { //Реанимационная услуга
                    sl.addContent(createUsl(a3, "" + (++uslCnt), lpuRegNumber, profileK, "B03.003.005", isChild, startDate, startDate, sl.getChildText("DS1"), "1", prvs
                            , currentEntry.getDoctorSnils(), BigDecimal.ZERO, currentEntry.getDepartmentAddressCode()));
                }
                //Информация об услугах
                if (isPoliclinic) { //Для поликлиники - кол-во визитов
                    if (!isPoliclinicKdp && children.isEmpty()) {
                        children.add(currentEntry);
                    }
                    boolean isFirst = true;
                    for (E2Entry child : children) {
                        boolean isFoundPriemService = false;
                        String visitService; //находим - есть ли первичный/повторный прием врача или создавать самим
                        String uslDate = dateToString(child.getStartDate());
                        VocE2MedHelpProfile childProfile = child.getMedHelpProfile();
                        if (childProfile == null) {
                            manager.persist(new E2EntryError(entry, "NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: " + child.getId()));
                            LOG.warn("NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: " + child.getId());
                            continue;
                        }
                        VocE2FondV021 spec = child.getFondDoctorSpecV021();
                        try {
                            VocMedService vms = isFirst ? spec.getDefaultMedService() : spec.getRepeatMedService();
                            visitService = vms.getCode();
                        } catch (Exception e) {
                            visitService = null;
                            //LOG.error(" У врача "+spec.getCode()+" нет услуги по умолчанию");
                        }
                        List<EntryMedService> serviceList = child.getMedServices();
                        for (EntryMedService service : serviceList) { //простые услуги в пол-ке
                            if (!service.getMedService().getCode().startsWith("T")) {
                                String serviceCode = service.getMedService().getCode();
                                BigDecimal cost = service.getCost();
                                sl.addContent(createUsl(a3, "" + (++uslCnt), coalesceTrim(service.getLpuCode(), lpuRegNumber), childProfile.getCode(), serviceCode, isChild, uslDate, uslDate
                                        , isNotNull(child.getMainMkb()) ? child.getMainMkb() : sl.getChildText("DS1"), "1", spec.getCode(), child.getDoctorSnils(), cost, child.getDepartmentAddressCode()));
                                if (serviceCode.equals(visitService)) {
                                    isFoundPriemService = true;
                                }
                            }
                        }
                        isFirst = false;
                        if (visitService != null && !isFoundPriemService) { //не нашли нужную услугу - создадим её сами!
                            sl.addContent(createUsl(a3, "" + (++uslCnt), lpuRegNumber, childProfile.getCode(), visitService, isChild, uslDate, uslDate
                                    , isNotNull(child.getMainMkb()) ? child.getMainMkb() : sl.getChildText("DS1"), "1", spec.getCode(), child.getDoctorSnils(), BigDecimal.ZERO, child.getDepartmentAddressCode())); //Создаем услугу по умолчанию. Для КДП и неотложки - не нужно
                        }
                    }

                } else if (isExtDisp) {
                    List<EntryMedService> serviceList = entry.getMedServices();
                    for (EntryMedService ms : serviceList) {
                        if (ms.getServiceDate() != null || exportDispServiceNoDate) {
                            try {
                                String servDate = "" + (ms.getServiceDate() != null ? ms.getServiceDate() : entry.getFinishDate());
                                sl.addContent(createUsl(true, "" + (++uslCnt), lpuRegNumber, null, ms.getMedService().getCode(), null
                                        , servDate, servDate, null
                                        , null, ms.getDoctorSpeciality() != null ? ms.getDoctorSpeciality().getCode() : prvs
                                        , isNotNull(ms.getDoctorSnils()) ? ms.getDoctorSnils() : entry.getDoctorSnils(), ms.getCost(), null));
                            } catch (Exception e) {
                                LOG.warn("Услуга " + ms.getId() + " - NO SERVICE CODE");
                            }
                        }
                    }
                } else if (!isVmp) { //стационар //нах все услуги с ВМП
                    List<Object[]> list = manager.createNativeQuery("select vms.code as f0_ms, cast(count(ems.id) as varchar) as cnt" +
                                    ", coalesce(cast(case when ems.serviceDate>e.finishdate then e.finishdate else ems.servicedate end as varchar(10)),'') as f2_serviceDate" +
                                    ", list(emsmi.id||'') as f3_medImplants" +
                                    " from EntryMedService ems" +
                                    " left join e2entry e on ems.entry_id = e.id" +
                                    " left join vocMedService vms on vms.id=ems.medService_id" +
                                    " left join EntryMedServiceMedImplant emsmi on emsmi.medservice_id=ems.id" +
                                    " where (e.id=:id or e.parententry_id=:id) and ems.serviceDate>=:entryDate" + //выгружаем только услуги
                                    " group by vms.code, case when ems.serviceDate>e.finishdate then e.finishdate else ems.servicedate end")
                            .setParameter("id", currentEntry.getId()).setParameter("entryDate", currentEntry.getStartDate()).getResultList();

                    if (!list.isEmpty()) {
                        for (Object[] ms : list) {
                            sl.addContent(createUsl(a3, "" + (++uslCnt), lpuRegNumber, profileK, ms[0].toString(), isChild, ms[2].toString()
                                    , ms[2].toString(), sl.getChildText("DS1"), ms[1].toString()
                                    , prvs, currentEntry.getDoctorSnils(), BigDecimal.ZERO, currentEntry.getDepartmentAddressCode(), ms[3].toString())); //в стационаре КТ-МРТ не оплачивается

                        }
                    }
                    sl.addContent(createUsl(a3, ++uslCnt + "", lpuRegNumber, profileK
                            , currentEntry.getBedProfile().getDefaultStacMedService() != null ? currentEntry.getBedProfile().getDefaultStacMedService().getCode() : "AAA"
                            , isChild, finishDate, finishDate, sl.getChildText("DS1"), "1"
                            , prvs, currentEntry.getDoctorSnils(), BigDecimal.ZERO, currentEntry.getDepartmentAddressCode()));
                } else { //ВМП
                    sl.addContent(createUsl(a3, ++uslCnt + "", lpuRegNumber, profileK, entry.getVmpMethod()
                            , isChild, finishDate, finishDate, sl.getChildText("DS1"), "1"
                            , prvs, currentEntry.getDoctorSnils(), BigDecimal.ZERO, currentEntry.getDepartmentAddressCode()));
                }
                if (currentEntry.getMainMkb() != null && currentEntry.getMainMkb().startsWith("U07")) {
                    addIfNotNull(sl, "WEI", currentEntry.getWeigth()); //масса тела (кг)
                }
                if (isNotEmpty(currentEntry.getDrugEntries())) {
                    addDrug(sl, currentEntry.getDrugEntries());
                }
                // USL finish
                zSl.addContent(indSl, sl);
                indSl++;
            }
            /* Закончили создавать Sl*/
            zSl.getChild("DATE_Z_1").setText(dateToString(startHospitalDate));
            zSl.getChild("DATE_Z_2").setText(dateToString(finishHospitalDate));
            if (zSl.getChild("KD_Z") != null) {
                zSl.getChild("KD_Z").setText("" + Math.max(1, AgeUtil.calculateDays(startHospitalDate, finishHospitalDate)));
            }
            if (isHosp && entry.havePrevMedCase()) {
                add(firstIndSl, zSl, "VB_P", "1");
            }

            zSl.removeChild("SL_TEMPLATE");
            zap.addContent(zSl); //Добавляем информацию о случае в запись
            return zap;
        } catch (Exception e) {
            LOG.error("Entry #" + entry.getId() + ", error = " + e.getLocalizedMessage(), e);
            throw new IllegalStateException("Entry #" + entry.getId() + ", error = " + e.getLocalizedMessage(), e);
        }
    }

    //валидация случая на основные поля
    private boolean validateEntry(E2Entry entry, boolean isPolic, boolean isDisp) {
        StringBuilder err = new StringBuilder();
        boolean isError = false;
        if (null == entry.getFondResult()) {
            err.append("НЕ РАСЧИТАН РЕЗУЛЬТАТ СЛУЧАЯ;");
            isError = true;
        }
        if (null == entry.getFondIshod()) {
            err.append("НЕ РАСЧИТАН ИСХОД СЛУЧАЯ;");
            isError = true;
        }
        if (null == entry.getMedHelpProfile()) {
            err.append("НЕ УКАЗАН ПРОФИЛЬ МЕД. ПОМОЩИ;");
            isError = true;
        }
        if (null == entry.getFondDoctorSpecV021()) {
            err.append("НЕ РАСЧИТАНА СПЕЦИАЛЬНОСТЬ ВРАЧА;");
            isError = true;
        }
        if (null == entry.getCost()) {
            err.append("НЕ РАСЧИТАНА ЦЕНА СЛУЧАЯ;");
            isError = true;
        }
        if (null == entry.getBaseTarif()) {
            err.append("НЕ РАСЧИТАН БАЗОВЫЙ ТАРИФ;");
            isError = true;
        }
        if (null == entry.getIdsp()) {
            err.append("НЕ РАСЧИТАН СПОСОБ ОПЛАТЫ МЕД. ПОМОЩИ;");
            isError = true;
        }
        if (isNullOrEmpty(entry.getMedPolicyType())) {
            err.append("НЕ УКАЗАН ВИД ПОЛИСА;");
            isError = true;
        }
        if (isNullOrEmpty(entry.getMedPolicyNumber())) {
            err.append("НЕ УКАЗАН НОМЕР ПОЛИСА;");
            isError = true;
        }
        if (isNullOrEmpty(entry.getInsuranceCompanyCode())) {
            err.append("НЕ УКАЗАН КОД СТРАХ КОМПАНИИ;");
            isError = true;
        }
        if (isPolic && isNullOrEmpty(entry.getMainMkb())) {
            err.append("НЕ УКАЗАН ОСНОВНОЙ ДИАГНОЗ");
            isError = true;
        }
        if (isNullOrEmpty(entry.getHistoryNumber())) {
            err.append("НЕ ЗАПОЛНЕН НОМЕР ИСТОРИИ БОЛЕЗНИ");
            isError = true;
        }
        if (null == entry.getVidSluch()) {
            err.append("НЕ ЗАПОЛНЕН ВИД СЛУЧАЯ");
            isError = true;
        }
        if (!isDisp && isNullOrEmpty(entry.getDoctorSnils())) {
            err.append("НЕ УКАЗАН СНИЛС ВРАЧА");
            isError = true;
        }
        if (entry.getMedHelpKind() == null) {
            err.append("НЕ УКАЗАН ВИД МП");
            isError = true;
        }
        if (isError) {
            manager.persist(new E2EntryError(entry, "NO_FOND_FIELDS:" + err));
            LOG.error("Запись с ИД " + entry.getId() + " не будет выгружена в xml: " + err);

        }
        return isError;
    }

    //TODO refactor with cancer drug?
    private void addDrug(Element sl, List<E2DrugEntry> drugEntries) {
        for (E2DrugEntry drug : drugEntries) {
            Element d = new Element("LEK_PR");
            addIfNotNull(d, "DATA_INJ", drug.getInjectDate());
            addIfNotNull(d, "CODE_SH", getVocCode(drug.getDrugGroupSchema()));
            addIfNotNull(d, "REGNUM", getVocCode(drug.getDrug()));
            addIfNotNull(d, "COD_MARK", null);
            Element dose = new Element("LEK_DOSE");
            addIfNotNull(dose, "ED_IZM", getVocCode(drug.getInjectUnit()));
            addIfNotNull(dose, "DOSE_INJ", drug.getInjectAmount());
            addIfNotNull(dose, "METHOD_INJ", getVocCode(drug.getInjectMethod()));
            addIfNotNull(dose, "COL_INJ", drug.getInjectNumber());
            d.addContent(dose);
            sl.addContent(d);
        }
    }

    private <T extends VocBaseEntity> String getVocCode(T voc) {
        return voc == null ? null : voc.getCode();
    }

    private String calculateDentalEdcol(E2Entry entry) {
        return expertService.getSumKuet(entry).toString();
    }

    //stom //пока заколхозим
    private String calcCodeMes(E2Entry entry) {
        String mainMkb = entry.getMainMkb();
        if (isOneOf(mainMkb, "K02.0", "K02.1", "K02.3")) return "1";
        if (isOneOf(mainMkb, "K04.0")) return "2";
        if (isOneOf(mainMkb, "K04.5", "K04.6", "K04.7")) return "4";

        return null;

    }
    private Element createUsl(boolean isDD, String id, String lpu, String profile, String vidVme, String isChild, String startDate
            , String finishDate, String ds, String kolUsl, String prvs, String codeMd, BigDecimal cost, String departmentAddressCode) {
        return createUsl(isDD, id, lpu, profile, vidVme, isChild, startDate, finishDate, ds, kolUsl, prvs, codeMd, cost, departmentAddressCode, null);
    }

    /*Создаем тэг USL по формату 2020 года*/
    private Element createUsl(boolean isDD, String id, String lpu, String profile, String vidVme, String isChild, String startDate
            , String finishDate, String ds, String kolUsl, String prvs, String codeMd, BigDecimal cost, String departmentAddressCode,
                              String implantIdsString) {
        Element usl = new Element("USL");
        add(usl, "IDSERV", id);
        add(usl, "LPU", lpu);
        if (StringUtil.isNotEmpty(implantIdsString)) {
            List<EntryMedServiceMedImplant> implants = manager.createQuery(" from EntryMedServiceMedImplant where id in (" + implantIdsString + ")").getResultList();
            for (EntryMedServiceMedImplant implant : implants) {
                Element medDev = new Element("MED_DEV");
                add(medDev, "CODE_MEDDEV", implant.getTypeCode());
                add(medDev, "NUMBER_SER", implant.getSerialNumber());
                add(medDev, "DATE_MED", startDate);
                usl.addContent(medDev);
            }
        }
        if (!isDD) {
            addIfNotNull(usl, "PODR", departmentAddressCode);
            add(usl, "PROFIL", profile);
            add(usl, "VID_VME", vidVme);
            add(usl, "DET", isChild); //Возраст на момент начала случая (<18 лет =1)
        }

        add(usl, "DATE_IN", startDate);
        add(usl, "DATE_OUT", finishDate);
        if (isDD) {
            add(usl, "P_OTK", "0");
            add(usl, "CODE_USL", vidVme);
        } else {
            add(usl, "DS", ds);
            add(usl, "KOL_USL", kolUsl);
        }
        //  add(usl,"TARIF","1");
        add(usl, "SUMV_USL", cost != null ? cost : BigDecimal.ZERO);
        addUslDoctor(usl, prvs, codeMd);
        //   add(usl,"NPL","0");
        return usl;
    }

    //TODO непонятно как правильно
    private void addUslDoctor(Element usl, String prvs, String doctorSnils) {
        add(usl, "PRVS", prvs);
        add(usl, "CODE_MD", doctorSnils);
       /* Element uslDoctor = new Element("MR_USL_N");
        add(uslDoctor, "MR_N", "1"); //1 услуга = 1 доктор
        add(uslDoctor, "PRVS", prvs);
        add(uslDoctor, "CODE_MD", doctorSnils);
        usl.addContent(uslDoctor);*/
    }

    /**
     * Создаем заголовок для L файла (информация о пациентах)
     */
    private void makeLTitle(Element root, Date documentDate, String filename, String version) {
        Element zglv = new Element("ZGLV");
        add(zglv, "VERSION", version);
        add(zglv, "DATA", dateToString(documentDate));
        add(zglv, "FILENAME", filename);
        add(zglv, "FILENAME1", "H" + filename.substring(1));
        root.addContent(zglv);
    }

    /**
     * Создаем заголовок для H файла (информация о мед. услугах)
     */
    private void makeHTitle(Element root, Date documentDate, String filename, int count, String billNumber
            , Date billDate, BigDecimal totalSum, String lpu, String dispType
            , String fileType) {
        String version = isOneOf(fileType, "C", "T", "DA", "DF", "DO", "DP") ? "3.1" : "3.2";//долбанные уроды со своей логикой
        Element zglv = new Element("ZGLV");
        add(zglv, "VERSION", version);
        add(zglv, "DATA", dateToString(documentDate));
        add(zglv, "FILENAME", filename);
        add(zglv, "SD_Z", count + "");
        root.addContent(zglv);

        Element schet = new Element("SCHET");
        add(schet, "CODE", "1");
        add(schet, "CODE_MO", lpu);
        add(schet, "YEAR", dateToString(documentDate, "yyyy"));
        add(schet, "MONTH", dateToString(documentDate, "M"));
        add(schet, "NSCHET", billNumber);
        add(schet, "DSCHET", dateToString(billDate));
        add(schet, "SUMMAV", totalSum + "");
        if (dispType != null) add(schet, "DISP", dispType);
        root.addContent(schet);

    }

    private boolean isNull(Object object) {
        return !isNotNull(object);
    }

    /**
     * Проверяем, является ли объект NULL либо пустой строкой
     */
    private boolean isNotNull(Object object) {
        if (object == null) return false;
        if (object instanceof String) {
            String ss = (String) object;
            return !ss.trim().equals("");
        } else if (object instanceof Boolean) {
            return (Boolean) object;
        } else if (object instanceof Long) {
            return ((Long) object) > 0L;
        } else if (object instanceof Date) {
            return true;
        } else if (object instanceof BigDecimal) {
            return ((BigDecimal) object).compareTo(BigDecimal.ZERO) > 0;
        } else if (object instanceof BaseEntity) {
            return true;
        } else if (object instanceof Number) {
            return true;
        } else {
            throw new IllegalStateException("Нет преобразования для объекта " + object.getClass());
        }
    }

    /**
     * Создаем MP файл с данными по стационару/поликлинике
     */
    @Override
    public String makeMPFIle(Long entryListId, String type, String billNumber, Date billDate, Long entryId
            , Boolean calcAllListEntry, long monitorId, String version, String fileType) {
        LOG.info("Формирование файла версии " + version);
        IMonitor monitor = monitorService.startMonitor(monitorId, "Формирование xml файла. Размер: ", 999);
        try {
            if (isCheckIsRunning) {
                isMonitorCancel(monitor, "Олег, нельзя запускать больше одной выгрузки одновременно!");
                LOG.warn("Формирование чего-то уже запущено, выходим_ALREADY_RAN");
                return "Олег, нельзя запускать больше одной выгрузки одновременно!";
            }
            Date periodDate;
            isCheckIsRunning = true;
            String packetDateAdd;
            String cntNumber = null;
            boolean needCreateArchive = false;
            E2Entry testEntry;
            if (entryListId != null) {
                testEntry = null;
                needCreateArchive = true;
                E2ListEntry listEntry = manager.find(E2ListEntry.class, entryListId);
                periodDate = listEntry.getFinishDate();
                if (isNull(billDate) || isNull(billNumber)) {
                    monitor.finish("Необходимо указать номер и дату счета!");
                    isCheckIsRunning = false;
                    return "";
                }
                if (isAnyIsNull(listEntry.getCheckDate(), listEntry.getCheckTime())) {
                    monitor.finish("Олег, необходимо выполнить проверку перед формированием пакета");
                    isCheckIsRunning = false;
                    return "";

                }
            } else { //Сделано для теста.
                testEntry = manager.find(E2Entry.class, entryId);
                periodDate = testEntry.getFinishDate();
                billDate = new Date(0L);
                billNumber = "TEST";
                cntNumber = "00";
                entryListId = testEntry.getListEntry().getId();
            }
            packetDateAdd = dateToString(periodDate, "yyMM");
            String packetType;
            if (fileType != null) {
                packetType = fileType;
            } else {
                switch (type) {
                    case HOSPITAL_TYPE:
                    case POLYCLINIC_TYPE:
                    case HOSPITAL_PEREVOD_TYPE:
                    case KDP_TYPE:
                        packetType = "Z";
                        break;
                    case VMP_TYPE:
                        packetType = "T";
                        break;
                    case EXTDISP_TYPE:
                        //Пока сделаем заглушку
                        packetType = "SomeDisp";
                        break;
                    default:
                        isCheckIsRunning = false;
                        throw new IllegalStateException("Неизвестный тип счета: " + type);
                }
            }

            java.util.Date startStartDate = new java.util.Date();
            String regNumber = getExpertConfigValue(Expert2Config.LPU_REG_NUMBER, "300001");
            String fileName = "M" + regNumber + "T30_" + packetDateAdd; // M300001T30_171227
            SequenceHelper sequenceHelper = SequenceHelper.getInstance();
            if (cntNumber == null) {
                cntNumber = sequenceHelper.startUseNextValueNoCheck(packetType + "#" + fileName, "", manager);
            }
            fileName += cntNumber;
            LOG.info("create new FileName = " + fileName);
            Element hRoot = new Element("ZL_LIST");  // данные о мед. помощи
            Element lRoot = new Element("PERS_LIST");  // данные о пациенте
            List<Long> patientIdsList = new ArrayList<>();
            BigDecimal totalSum = new BigDecimal(0);
            List<Element> zaps = new ArrayList<>();
            List<Element> perss = new ArrayList<>();
            int cnt = 0;
            List<Object[]> records;
            String selectSqlAdd = " e.id as cnt, e.id as cnt2, e.id as cnt3";
            String groupSqlAdd = "e.id";
            boolean isHosp;
            boolean isPolic;
            boolean isDisp;
            if (isOneOf(type, HOSPITAL_TYPE, HOSPITAL_PEREVOD_TYPE, VMP_TYPE)) {
//                selectSqlAdd = " list(''||e.id) as ids, e.id, count(distinct e.id) as cnt";//Ищем все СЛО *список ИД, ИД госпитализации,кол-во СЛО
                selectSqlAdd = " list(''||e.id) as ids, e.externalparentid, count(distinct e.id) as cnt";//Ищем все СЛО *список ИД, ИД госпитализации,кол-во СЛО
                isDisp = isPolic = false;
                isHosp = true;
                exchangeCovidDs = "1".equals(getExpertConfigValue(Expert2Config.EXCHANGE_COVID_DS, "0"));
                groupSqlAdd = "e.externalparentid";
            } else if (isOneOf(type, POLYCLINIC_TYPE, SERVICE_TYPE)) {
//                selectSqlAdd = " list(''||e.id) as ids, e.id, count(distinct e.id) as cnt";//Ищем все комплексные случаи
                isDisp = isHosp = false;
                isPolic = true;
                DISP_LIST = Arrays.asList(getExpertConfigValue(Expert2Config.DISP_DIAGNOSIS_LIST, "").split(","));
            } else { //ДД
//                selectSqlAdd = "list(''||e.id) as ids, e.id, count(distinct e.id) as cnt";//Ищем все комплексные случаи
                isPolic = isHosp = false;
                isDisp = true;
                exportDispServiceNoDate = "1".equals(getExpertConfigValue(Expert2Config.EXPORT_DISP_SERVICE_NO_DATE, "0"));
            }
            boolean dontSendDefets = "1".equals(getExpertConfigValue(Expert2Config.DONT_EXPORT_DEFECTS, "0"));
            String sql;
            if (entryId == null) { //формируем файл по заполнению
                sql = "select " + selectSqlAdd + " from E2Entry e" +
//                        " left join e2entry child on child.parentEntry_id=e.id and coalesce(child.isDeleted, false) = false" +
                        " where " + (isTrue(calcAllListEntry) ? "" : "e.listEntry_id=" + entryListId + " and") +
                        "  e.billNumber=:billNumber and e.billDate=:billDate " +
                        " and coalesce(e.isDeleted, false) = false" +
                        " and e.serviceStream!='COMPLEXCASE'" +
                        /* " and coalesce(child.doNotSend, false) = false" +*/ (dontSendDefets ? " and coalesce(e.isDefect, false) = false" : "") + " group by " + groupSqlAdd;
                LOG.info("sql=" + sql);
                records = manager.createNativeQuery(sql)
                        .setParameter("billNumber", billNumber).setParameter("billDate", billDate).getResultList();
            } else if (testEntry != null) { //файл по одному случаю (для теста)
                sql = "select " + selectSqlAdd + " from E2Entry e" +
                        " left join e2entry child on child.parentEntry_id=e.id and coalesce(child.isDeleted, false) = false" +
                        " where e.listEntry_id=" + entryListId +
                        " and coalesce(e.isDeleted, false) = false " +
                        " and e.serviceStream!='COMPLEXCASE'" +
                        " and e.externalparentid=" + testEntry.getExternalParentId() +
                        " and coalesce(e.doNotSend, false) = false group by " + groupSqlAdd;
                LOG.info("sql=" + sql);
                records = manager.createNativeQuery(sql).getResultList();
            } else {
                records = new ArrayList<>();
            }
            LOG.info("found " + records.size() + " records");

            monitor.advice(1);

            int i = 0;
            /*Вот тут - 1 строка - список записей по 1 госпитализация */
            String dispType = null;
            String billProperty = "";
            for (Object[] sluch : records) {
                int cntSlo = Integer.parseInt(sluch[2].toString());
                String sluchId = sluch[1].toString().trim();
                String sls = sluch[0].toString().trim();
//                if (isHosp) {
                E2Entry entry;
                if (isHosp && cntSlo > 1) {
                    entry = calculateHospitalEntry(Long.valueOf(sluchId), sls);
                } else {
                    entry = manager.find(E2Entry.class, Long.valueOf(sls));
                }
//                } else {
//                    entry = manager.find(E2Entry.class, Long.valueOf(sls));
//                    if (cntSlo == 0) {
//                        sls = sluchId;
//                    }
//                }
                if (entry == null) {
                    throw new IllegalStateException("Не найден случай с ИД: " + sls);
                }
                if (validateEntry(entry, isPolic, isDisp)) {
                    continue;
                }
                if (i == 0) { //вычисляем тип ДД по первой записи
                    if (isDisp) {
                        dispType = entry.getSubType().getExtDispType().getCode();
                    }
                    billProperty = entry.getSubType().getBillProperty();
                }
                i++;
                if (i % 100 == 0 && isMonitorCancel(monitor, "Сформировано " + i + " записей в счете")) {
                    isCheckIsRunning = false;
                    return "Прерванно пользователем";
                }

                Long personId = entry.getExternalPatientId();
                Element z;
                //При появлении новых форматов файла - добавляем сюда
                if ("3.2".equals(version)) {
                    z = createSlElements2020(entry, sls, cnt + 1, regNumber, fileType);
                } else {
                    LOG.error("Неизвестный формат пакета: " + version);
                    isCheckIsRunning = false;
                    throw new IllegalStateException("Неизвестный формат пакета: " + version);
                }

                if (z != null) {
                    cnt++;
                    if (!patientIdsList.contains(personId)) { //Если нет пациента в Л файле - добавляем.
                        perss.add(createPERSElement(entry));
                        patientIdsList.add(personId);
                    }
                    zaps.add(z);
                    BigDecimal entryCost = new BigDecimal(z.getChild("Z_SL").getChildText("SUMV"));
                    totalSum = totalSum.add(entryCost);
                } else {
                    LOG.error("Не удалось сформировать запись по случаю с ИД " + entry.getId());
                }
            }
            LOG.info("ok, we made all, let's make files");
            monitor.setText("Формирование файла завершено, сохраняем архив");
            makeHTitle(hRoot, periodDate, "H" + fileName, cnt, billNumber, billDate, totalSum, regNumber, dispType, fileType);
            E2Bill bill = expertService.getBillEntryByDateAndNumber(billNumber, billDate, null);
            if (bill != null) {
                E2Bill savedBill = manager.find(E2Bill.class, bill.getId());
                savedBill.setStatus(getActualVocBySqlString(VocE2BillStatus.class, "select id from VocE2BillStatus where code='SENT'"));
                savedBill.setSum(totalSum);
                savedBill.setBillProperty(billProperty);
                manager.persist(savedBill);
            }
            makeLTitle(lRoot, periodDate, "L" + fileName, "3.2"); //2020
            lRoot.addContent(perss);
            hRoot.addContent(zaps);
            String archiveName = packetType + fileName + ".MP";
            createXmlFile(hRoot, "H" + fileName);
            createXmlFile(lRoot, "L" + fileName);
            LOG.info("Время формирования файла (минут) = " + (System.currentTimeMillis() - startStartDate.getTime()) / 60000);
            monitor.setText("Завершено. Время формирования файла (минут) = " + (System.currentTimeMillis() - startStartDate.getTime()) / 60000);

            if (needCreateArchive) {
                archiveName = createArchive(archiveName, new String[]{"H" + fileName + ".xml", "L" + fileName + ".xml"});
                E2ExportPacketJournal journal = new E2ExportPacketJournal(billNumber, billDate, EXPORT_DIR + archiveName);
                manager.persist(journal);
            }
            LOG.info("ALL SEEMS GOOD!");

            isCheckIsRunning = false;

            monitor.setText(EXPORT_DIR + archiveName);
            monitor.finish(EXPORT_DIR + archiveName);
            return EXPORT_DIR + archiveName;
        } catch (Exception err) {
            monitor.error("Произошла ошибка: " + err.getMessage(), err);
            LOG.error("ERR = ", err);
            isCheckIsRunning = false;
            return "ERR";
        }
    }

    /*Формируем случай с госпитализацией (не сохраняя в БД)*/
    private E2Entry calculateHospitalEntry(Long hospitalMedcaseId, String ids) {
        E2Entry hospital = null;
        List<E2Entry> slo = manager.createQuery("from E2Entry where id in (" + ids + ") and externalParentId=:parent and serviceStream!='COMPLEXCASE' " +
                        "and coalesce(isDeleted, false) = false and coalesce(doNotSend, false) = false order by startDate")
                .setParameter("parent", hospitalMedcaseId)
                .getResultList();
        if (!slo.isEmpty()) {
            hospital = cloneEntity(slo.get(0));
            if (hospital == null) {
                return null;
            }
            E2Entry lastEntry = slo.get(slo.size() - 1);
            hospital.setFondResult(lastEntry.getFondResult());
            hospital.setFondIshod(lastEntry.getFondIshod());
            long bedDays = AgeUtil.calculateDays(hospital.getHospitalStartDate(), hospital.getHospitalFinishDate() != null ? hospital.getHospitalFinishDate() : lastEntry.getFinishDate());
            long calendarDays = bedDays > 0 ? bedDays + 1 : 1;
            if (hospital.getEntryType().equals(HOSPITAL_TYPE) && "2".equals(hospital.getBedSubType())) {
                bedDays++;
            }
            hospital.setBedDays(bedDays);
            hospital.setCalendarDays(calendarDays);
            BigDecimal cost = BigDecimal.ZERO;
            for (E2Entry e : slo) {
                if (e.getCost() != null) {
                    cost = cost.add(e.getCost());
                } else {
                    LOG.warn("entry ID =" + e.getId() + " NO COST");
                }
                if (isNedonoshKsg(e.getKsg())) {
                    hospital.setIsNedonosh(true);
                }
            }
            if (hospital.getId() == 0L) {
                hospital.setId(lastEntry.getId());
            }
            hospital.setCost(cost);
        }
        return hospital;
    }

    private boolean isNedonoshKsg(VocKsg ksg) {
        return ksg != null && ",st17.001,st17.002,st17.003,".contains("," + ksg.getCode() + ",");
    }

    private E2Entry cloneEntity(E2Entry source) {
        try {
            Method[] methodList = source.getClass().getMethods();
            E2Entry newEntity = new E2Entry();
            for (Method setterMethod : methodList) {
                String methodName = setterMethod.getName();
                if (methodName.startsWith("set")) {
                    if (methodName.equals("setId") || setterMethod.isAnnotationPresent(OneToMany.class)) {
                        continue;
                    }
                    String propertyName = PropertyUtil.getPropertyName(setterMethod);
                    try {
                        Object val = PropertyUtil.getPropertyValue(source, propertyName);
                        PropertyUtil.setPropertyValue(newEntity, propertyName, val);
                    } catch (Exception e) {
                    }
                }
            }
            return newEntity;
        } catch (Exception e) {
            LOG.error(e);
            return null;
        }

    }

    private String getWorkDir() {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    }

    /**
     * Создаем файл из документа
     */
    private void createXmlFile(Element element, String filename) {
        if (element == null) {
            LOG.error("no data for create file " + filename);
            return;
        }
        String outputFile = getWorkDir() + "/expert2xml/" + filename + ".xml";

        try (OutputStreamWriter fwrt = new OutputStreamWriter(new FileOutputStream(outputFile), Charset.forName("windows-1251"))) {
            XMLOutputter outputter = new XMLOutputter();
            Document pat = new Document(element);
            outputter.setFormat(org.jdom.output.Format.getPrettyFormat().setEncoding("windows-1251"));
            outputter.output(pat, fwrt);
        } catch (Exception ex) {
            LOG.error("Someshing happened strange!!!", ex);
        }
    }

    private String createArchive(String archiveName, String[] filenames) {
        String workDir = getWorkDir() + "/expert2xml/";
        StringBuilder sb = new StringBuilder();
        String exec = "zip -r -j -9 ";
        sb.append(workDir).append(archiveName).append(" ");

        for (String s : filenames) {
            sb.append(workDir).append(s).append(" ");
        }
        try {
            LOG.info("START EXECUTING = " + sb);
            try {
                Runtime.getRuntime().exec("zip -d " + workDir + archiveName + " *");//Удаляем архив перед созданием;
            } catch (Exception ignored) {
            }//Не удалось очистить архив, т.к. его нету. Ничего страшного)

            Runtime.getRuntime().exec(exec + sb.toString());//arraCmd);
        } catch (IOException e) {
            LOG.warn("it seems Windows");
            exec = "\"C:\\Program Files\\7-Zip\\7z.exe\" a -tZIP ";
            try {
                Runtime.getRuntime().exec(exec + sb.toString());//arraCmd);
            } catch (IOException e1) {
                LOG.error(e1);
            }
        }
        return archiveName;
    }

    /**
     * Получение всех диагнозов из списка по коду регистрации
     */
    private List<String> findDiagnosisCodes(List<EntryDiagnosis> diagnoses, String regType, String priority) {
        //Тип регистрации - направительный(2), выписной(3), клинический(4)
        // Приорите - основной(1), сопутствующий(3)
        if (diagnoses.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> diagnosisList = new ArrayList<>();
        if (regType == null && priority == null) {
            return diagnosisList;
        }
        for (EntryDiagnosis diagnosis : diagnoses) {
            if ((regType == null || (diagnosis.getRegistrationType() != null && regType.contains(diagnosis.getRegistrationType().getCode())))
                    && (priority == null || diagnosis.getPriority() != null && priority.contains(diagnosis.getPriority().getCode()))
            ) {
                diagnosisList.add(diagnosis.getMkb().getCode());
            }
        }
        return diagnosisList;
    }

    private String getExpertConfigValue(String parameterName, String defaultValue) {
        List<Object> ret = manager.createNativeQuery("select value from Expert2Config where code=:code AND coalesce(isDeleted, false) = false").setParameter("code", parameterName).getResultList();
        if (ret.isEmpty()) {
            LOG.warn("Не удалось найти настройку с ключем " + parameterName + ", возвращаю значение по умолчанию");
            return defaultValue;
        }
        return ret.get(0).toString();
    }

    private <T> T getActualVocBySqlString(Class<T> clazz, String sql) {
        List<Object> list = manager.createNativeQuery(sql).getResultList();
        if (list.isEmpty()) {
            LOG.error("Не удалось найти действующее значение справочника " + clazz.getCanonicalName() + " с условием " + sql);
            return null;
        }
        if (list.size() > 1) {
            LOG.error(" с условием " + sql + " найдено несколько действующих значений справочника " + clazz.getCanonicalName());
            return null;
        }
        return manager.find(clazz, Long.valueOf(list.get(0).toString()));

    }

    private void setSluchDiagnosis(Element element, E2Entry entry, boolean a3) {
        /** Нюансы
         * 30.01.2018 Если есть диабет - указываем его как главный сопутствующий
         * 03.08.2020 Если ковид - он становится осложнением, а другой - основным
         */
        if (a3) {
            add(element, "DS1", entry.getMainMkb());
            return;
        }
        List<EntryDiagnosis> list = entry.getDiagnosis();
        String mainDiagnosisSqlAdd;
        List<EntryDiagnosis> mainDiagnosisList = new ArrayList<>();
        String entryType = entry.getEntryType();
        if (isOneOf(entryType, POLYCLINIC_TYPE, KDP_TYPE, SERVICE_TYPE)) {
            mainDiagnosisSqlAdd = "priority.code='1'";
        } else { //клинический или выписной (для стационара)
            mainDiagnosisSqlAdd = " registrationType.code='3' and priority.code='1'";
            mainDiagnosisList = manager.createQuery(" from EntryDiagnosis where entry_id=:id and " + mainDiagnosisSqlAdd).setParameter("id", entry.getId()).getResultList(); //выписные основные диагнозы
            if (mainDiagnosisList.isEmpty()) {
                mainDiagnosisSqlAdd = " registrationType.code='4' and priority.code='1'";
            }
        }
        if (isEmpty(mainDiagnosisList)) {
            mainDiagnosisList = manager.createQuery(" from EntryDiagnosis where entry_id=:id and " + mainDiagnosisSqlAdd).setParameter("id", entry.getId()).getResultList(); //клинические основные диагнозы
        }

        List<String> otherDiagnosis = findDiagnosisCodes(list, null, "3"); // Сопутствующие
        List<String> heavyDiagnosis = findDiagnosisCodes(list, null, "4"); // Осложнения
        List<String> napravitDiagnosis = findDiagnosisCodes(list, "1,2", "3"); // Направительные
        if (isNotEmpty(napravitDiagnosis)) {
            add(element, "DS0", napravitDiagnosis.get(0));
        }

        if (isNotEmpty(mainDiagnosisList)) {
            EntryDiagnosis ds = mainDiagnosisList.get(0);
            String mainMkb = ds.getMkb().getCode();
            add(element, "DS1", mainMkb);

            if (isNotNull(ds.getDopMkb())) {
                otherDiagnosis.add(0, ds.getDopMkb());
            }
            if (isNotEmpty(otherDiagnosis)) {
                //Нашли диабет поставили его на первое место!
                for (String d : otherDiagnosis) {
                    if (d.startsWith("E10") || d.startsWith("E11")) { //Нашли диабет поставили его на первое место!
                        otherDiagnosis.remove(d);
                        otherDiagnosis.add(isNotNull(ds.getDopMkb()) ? 1 : 0, d);
                        break;
                    }
                }
                for (String d : otherDiagnosis) {
                    add(element, "DS2", d);
                }
            }
            if (isNotEmpty(heavyDiagnosis)) {
                if (exchangeCovidDs && mainMkb.startsWith("U07")) { //если ковид - он - ослжнение, а осложнение - главный
                    element.removeChild("DS2");
                    element.getChild("DS1").setText(heavyDiagnosis.get(0));
                    add(element, "DS2", mainMkb);
                    if (isNotEmpty(otherDiagnosis)) add(element, "DS2", otherDiagnosis.get(0));
                } else {
                    for (String d : heavyDiagnosis) {
                        add(element, "DS3", d);
                    }
                }

            }
            if (!mainMkb.startsWith("Z")) { //C_ZAB * Характер заболевания, если USL_OK!=4 || DS1!=Z*
                VocE2FondV027 vip = ds.getVocIllnessPrimary();
                if (vip == null) {
                    manager.persist(new E2EntryError(entry, "NO_HARAKTER"));
                    element = null;
                    return;
                }
                add(element, "C_ZAB", vip.getCode());
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
            LOG.info("Проверка прервана пользователем");
            return true;
        }
        return false;
    }

    private <T> T coalesce(T... values) {
        for (T value : values) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private String coalesceTrim(String... values) {
        for (String value : values) {
            if (StringUtil.isNotEmpty(value)) {
                return value;
            }
        }
        return null;
    }
}
