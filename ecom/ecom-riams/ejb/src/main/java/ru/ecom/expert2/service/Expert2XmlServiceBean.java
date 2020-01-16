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
import java.util.List;

@Stateless
@Local(IExpert2XmlService.class)
@Remote(IExpert2XmlService.class)
public class Expert2XmlServiceBean implements IExpert2XmlService {
    private Boolean isCheckIsRunning = false;
    private static final Logger LOG = Logger.getLogger(Expert2XmlServiceBean.class);
    private static final String HOSPITALTYPE="HOSPITAL";
    private static final String HOSPITALPEREVODTYPE="HOSPITALPEREVOD";
    private static final String POLYCLINICTYPE="POLYCLINIC";
    private static final String VMPTYPE="VMP";
    private static final String EXTDISPTYPE="EXTDISP";
    private static final String KDPTYPE="POL_KDP";
    private static final String CENTRAL_SEGMENT_DOC="CENTRAL_SEGMENT";

    /**Экспорт запроса в центральный сегмент
     * */
    public String exportToCentralSegment(Long aListEntryId, String aHistoryNumbers) {
            StringBuilder sqlAdd = new StringBuilder();
            if (StringUtil.isNullOrEmpty(aHistoryNumbers)) {
                sqlAdd.append("insuranceCompanyCode not like '30%' and serviceStream='OBLIGATORYINSURANCE'"); // по умолчанию - иногородние омс
            } else {
                boolean first = true;
                for (String h : aHistoryNumbers.split(",")) {
                    if (!first) sqlAdd.append(",");
                    else first=false;
                    sqlAdd.append("'").append(h).append("'");
                }
                sqlAdd.insert(0,"historyNumber in (");
                sqlAdd.append(") ");
            }

            String sql = "from E2Entry where listEntry_id=:listId and " + sqlAdd.toString() + " and (isDeleted is null or isDeleted='0')";
            List<Object> list = theManager.createQuery(sql).setParameter("listId", aListEntryId).getResultList();
            File dbfFile = new File(getWorkDir() + "/expert2xml/" + aListEntryId + "_flyToFond.dbf");
            List<ImportDocument> documents = theManager.createNamedQuery("ImportDocument.findByKey").setParameter("key", CENTRAL_SEGMENT_DOC).getResultList();
            Format exportFormat = documents.isEmpty() ? null : documents.get(0).getDefaultFormat();
            if (exportFormat != null) {
                try {
                    if (dbfFile.exists()) dbfFile.delete();
                    dbfFile.createNewFile();
                    ExportServiceBean.export(exportFormat, list, dbfFile, null, Long.valueOf(list.size()), null);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
                return dbfFile.getName();
            } else {
                throw new IllegalStateException("Нет формата для выгрузки для документа с кодом '" + CENTRAL_SEGMENT_DOC+"'");
            }
    }
    /** Переводим дату в строку в формате yyyy-DD-mm*/
    private String dateToString(Date aDate) {
        return dateToString(aDate,  "yyyy-MM-dd");
    }

    /** Переводим дату в строку в заданном формате */
    private String dateToString(Date aDate, String aFormat) {
        return new SimpleDateFormat(aFormat).format(aDate);
    }

    /** Создаем запись в L файле */
    private Element createPERSElement(E2Entry aEntry) {

      //  log.info("Добавляем данные о пациенте " + aEntry.getExternalPatientId() + " в L файле");
        boolean isKinsman = isNotNull(aEntry.getKinsmanLastname());
        Element pers = new Element("PERS");
        pers.addContent(new Element("ID_PAC").setText(aEntry.getExternalPatientId().toString()));
        if (isKinsman) { //Заполняем данные о представителе
            pers.addContent(new Element("FAM").setText("НЕТ"));
            pers.addContent(new Element("IM").setText("НЕТ"));
            pers.addContent(new Element("OT").setText("НЕТ"));
            pers.addContent(new Element("W").setText(aEntry.getSex()));
            pers.addContent(new Element("DR").setText(dateToString(aEntry.getBirthDate())));
            pers.addContent(new Element("FAM_P").setText(aEntry.getKinsmanLastname()));
            pers.addContent(new Element("IM_P").setText(aEntry.getKinsmanFirstname()));
            pers.addContent(new Element("OT_P").setText(aEntry.getKinsmanMiddlename()));
            pers.addContent(new Element("W_P").setText(aEntry.getKinsmanSex()));
            pers.addContent(new Element("DR_P").setText(dateToString(aEntry.getKinsmanBirthDate())));

        } else {
            pers.addContent(new Element("FAM").setText(aEntry.getLastname()));
            pers.addContent(new Element("IM").setText(aEntry.getFirstname()));
            addIfNotNull(pers, "OT", aEntry.getMiddlename());
            pers.addContent(new Element("W").setText(aEntry.getSex()));
            pers.addContent(new Element("DR").setText(dateToString(aEntry.getBirthDate())));
        }
        //MR
      //  log.info("Добавляем данные о пациенте OTHER_DATA " + aEntry.getExternalPatientId() + " в L файле");
        addIfNotNull(pers,"DOCTYPE",aEntry.getPassportType());
        addIfNotNull(pers,"DOCSER",aEntry.getPassportSeries());
        addIfNotNull(pers, "DOCNUM", aEntry.getPassportNumber());
        addIfNotNull(pers,"SNILS",isKinsman ? aEntry.getKinsmanSnils() : aEntry.getPatientSnils());
        addIfNotNull(pers,"ENP",aEntry.getCommonNumber());
     //   pers=addIfNotNull(pers,"OKATOG",aEntry.getOkatoReg()); //код места жительства
     //   pers=addIfNotNull(pers,"OKATOP",aEntry.getOkatoReal()); //код места пребывания
        return pers;
    }

    /** Добавляем в переданный элемент новый элемент со значением, в случае, если значение не пустое */
    private void addIfNotNull(Element aElement, String aElementName, Object  aValue) {
        if (isNotNull(aValue)) {
            if (aValue instanceof Boolean) {
                aValue= ((Boolean) aValue) ? "1" : "0";
            }
            aElement.addContent(new Element(aElementName).setText(aValue.toString()));
        }
    }

    /** Расчет строки с признаком новорожденного */
    private String makeNovorString(E2Entry aEntry) {
        if (isNotNull(aEntry.getKinsmanLastname())) {
            SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
            return aEntry.getSex() + ""+format.format(aEntry.getBirthDate())+"" + (isNotNull(aEntry.getBirthOrder()) ? aEntry.getBirthOrder() : 1);
        } else {
            return "0";
        }
    }

    /** Создаем тэг с информацией о госпитализации (версия 3.1.1)*/
    private Element createZSl(E2Entry aEntry, boolean isPoliclinic, int slCnt, int zslIdCase, boolean isExport263, boolean isNedonosh, String lpuOmcCode) {
        boolean isExtDisp = aEntry.getEntryType().equals(EXTDISPTYPE);
        String forPom = isNotNull(aEntry.getIsEmergency()) ? (isPoliclinic ? "2" : "1") : "3";
        Element z = new Element("Z_SL");
        add(z,"IDCASE",zslIdCase+"");
        add(z,"VID_SLUCH",aEntry.getVidSluch().getCode());
        add(z,"USL_OK",aEntry.getMedHelpUsl().getCode()); //дневной-круглосуточный-поликлиника
        add(z,"VIDPOM",aEntry.getMedHelpKind().getCode());
        add(z,"FOR_POM",forPom); //форма помощи V014

        if (!isNotNull(aEntry.getIsEmergency())) {
            if ((aEntry.getMainMkb()!=null && aEntry.getMainMkb().startsWith("C")) || !isPoliclinic) {
                addIfNotNull(z,"NPR_MO",aEntry.getDirectLpu()); //Направившее ЛПУ
                addIfNotNull(z,"NPR_DATE",aEntry.getDirectDate()); //Дата направления на лечение ***
            }
        }
        if (isExport263) addIfNotNull(z,"NPR_N",aEntry.getTicket263Number()); // Номер направления на портале ФОМС
        addIfNotNull(z,"NPR_P",aEntry.getPlanHospDate()); // Дата планируемой госпитализации
        if ("2".equals(forPom)) add(z,"PRN_MO","300001");
        add(z,"LPU",lpuOmcCode); //ЛПУ лечения
        add(z,"VBR",isNotNull(aEntry.getIsMobilePolyclinic())?"1":"0"); //Признак мобильной бригады
        add(z,"DATE_Z_1","_"); //Дата начала случая
        add(z,"DATE_Z_2","_"); //Дата окончания случая
        if (!isPoliclinic &&!isExtDisp)  add(z,"KD_Z",aEntry.getBedDays()+""); // Продолжительность госпитализации
        if (isNedonosh && isNotNull(aEntry.getKinsmanLastname())) add(z,"VNOV_M",aEntry.getNewbornWeight()+"");
        add(z,"RSLT",aEntry.getFondResult().getCode()); // Результат обращения
        if (isExtDisp) add(z,"RSLT_D",aEntry.getDispResult().getCode()); // Результат диспансеризации
        add(z,"ISHOD",aEntry.getFondIshod().getCode()); // Исход случая.
        //if (isExtDisp) z.addContent(new Element("P_OTK").setText("0")); // Отказ от ДД
        add(z,"OS_SLUCH",Expert2FondUtil.calculateFondOsSluch(aEntry)); // Особый случай
        if (!isPoliclinic &&!isExtDisp && slCnt>1) add(z,"VB_P","1"); // Признак внутрибольничного перевода *05.08 1 - только если есть перевод
        z.addContent(new Element("SL_TEMPLATE")); // Список случаев
        //if (isExtDisp) z=add(z,"SGROUP",aEntry.getExtDispSocialGroup()); // Социальная группа в ДД +
        add(z,"IDSP",aEntry.getIDSP().getCode()); // Способ оплаты медицинской помощи (V010)
        add(z,"SUMV",aEntry.getCost()); // Сумма, выставленная к оплате =SUMV7+ SUMV8
        return z;
    }

    /** Добавляем в Element значение*/
    private void add(Element el, String aFieldname, Object aValue) {
        el.addContent(new Element(aFieldname).setText(aValue!=null?aValue.toString():""));
    }

    /** Создааем информацию по случаю мед. обслуживания для версии 2020 года
     * aEntry - случай госпитализации
     * entriesList - строка с ИД СЛО
     * */
    private Element createSlElements2020(E2Entry aEntry, String entriesString, int cnt, boolean isExport263, String mainLpu, String lpuOmcCode, String aFileType) {

            /*
            ZSL, SL = информация об обращении. визиты переносятся в USL
            * */
        String version = "3.2";
        try {
            String entryType = aEntry.getEntryType();
            boolean isHosp = false, isVmp = false, isPoliclinic = false, isExtDisp = false, isPoliclinicKdp=false;
            boolean a1, a2, a3, a4;
            switch (aFileType) {
                case "H":
                case "P":
                case "S":
                    a1 = true;
                    a2=a3=a4=false;
                    break;
                case "T":
                    a2=true;
                    a1=a3=a4=false;
                    break;
                case "C":
                case "PC":
                    a4= true;
                    a1=a2=a3=false;
                    break;
                default: //диспансеризация
                    a3=true;
                    a1=a2=a4=false;
            }
            boolean isNedonosh = false;
            switch (entryType) {
                case HOSPITALTYPE:
                    isHosp=true;
                    isNedonosh=aEntry.getKsg()!=null && ",st17.001,st17.002,st17.003,".contains(","+aEntry.getKsg().getCode()+",");
                    break;
                case VMPTYPE:
                    isVmp=true;
                    isNedonosh=aEntry.getIsChild();
                    break;
                case POLYCLINICTYPE:
                    isPoliclinic=true;
                    break;
                case KDPTYPE:
                    isPoliclinic=true;
                    isPoliclinicKdp=true;
                    break;
                default:
                    throw new IllegalStateException("UNKNOWN ENTRYTYPE"+entryType);
            }
            Element zap = new Element("ZAP");
            add(zap,"N_ZAP",aEntry.getId() + "");
            add(zap,"PR_NOV",isNotNull(aEntry.getPRNOV()) ? "1" : "0");
            Element pat = new Element("PACIENT");
            add(pat,"ID_PAC",aEntry.getExternalPatientId() + "");
            add(pat,"VPOLIS",aEntry.getMedPolicyType());
            if (aEntry.getMedPolicyType() != null && !aEntry.getMedPolicyType().equals("3")) {
                addIfNotNull(pat, "SPOLIS", aEntry.getMedPolicySeries()); //Если полис не нового образца - добавляем серию полиса
                add(pat,"NPOLIS",aEntry.getMedPolicyNumber());
            } else {
                add(pat,"NPOLIS",aEntry.getCommonNumber());
            }

            if (isNotNull(aEntry.getInsuranceCompanyCode())) {
                add(pat,"SMO",aEntry.getInsuranceCompanyCode());
            }  else {
                add(pat,"SMO_OGRN",aEntry.getInsuranceCompanyOgrn());
                add(pat,"SMO_NAM",aEntry.getInsuranceCompanyName());
            }
            String novorString=makeNovorString(aEntry);
            add(pat,"NOVOR",novorString);
            if (a1 && isNedonosh && !isNotNull(aEntry.getKinsmanLastname())) { // && novorString.equals("0")) { //11.10.2018 по согласованию с фондом
                add(pat,"VNOV_D",aEntry.getNewbornWeight()+"");
            }

            zap.addContent(pat); //Добавили данные по пациенту
            List<E2Entry> children = null;
            String isChild = isNotNull(aEntry.getIsChild()) ? "1" : "0";

            String[] slIds = entriesString.split(",");
            Element zSl = createZSl(aEntry,isPoliclinic,slIds.length,cnt, isExport263, isNedonosh, lpuOmcCode);
            int indSl = zSl.indexOf(zSl.getChild("SL_TEMPLATE"));
            Date startHospitalDate = null, finishHospitalDate=null;
            int kdz=0;

            /** Вот тут создаем Sl*/
            boolean isDiagnosisFill=false, isLongCase=slIds.length>1;
            String vers="V021";
            for (String slId:slIds) {
                Element sl = new Element("SL");
                E2Entry currentEntry = theManager.find(E2Entry.class,Long.valueOf(slId.trim()));
                String edCol="1";
                if (isPoliclinicKdp) {
                    //edCol="1";
                    //children = theManager.createQuery("from E2Entry where parentEntry_id=:id and (isDeleted is null or isDeleted='0') and (doNotSend is null or DoNotSend='0')").setParameter("id",currentEntry.getId()).getResultList();
                    children = new ArrayList<>();
                } else if (isPoliclinic) {
                    children = theManager.createQuery("from E2Entry where parentEntry_id=:id and (isDeleted is null or isDeleted='0') and (doNotSend is null or DoNotSend='0') order by startDate").setParameter("id",currentEntry.getId()).getResultList();
                    //   edCol=""+(children.size()>0?children.size():1);
                } else { //стационар
                    kdz+=currentEntry.getBedDays().intValue();
                    //    edCol= currentEntry.getBedDays().toString(); // Количество единиц оплаты мед. помощи
                }

                boolean isCancer = isNotNull(currentEntry.getIsCancer());
                //       if (isCancer && currentEntry.getMedHelpProfile().getCode().equals("12")) {isCancer=false;} //Убрать колхоз
                if (isCancer && currentEntry.getVisitPurpose()!=null && "1.3".equals(currentEntry.getVisitPurpose().getCode())) {isCancer=false;}
                E2CancerEntry cancerEntry = null;
                if (isCancer && !currentEntry.getCancerEntries().isEmpty()) {
                    cancerEntry=currentEntry.getCancerEntries().get(0); //Считаем что не может быть больше 1 онкослучая
                }

                VocE2MedHelpProfile profile = currentEntry.getMedHelpProfile();
                String profileK = profile.getCode();// колхоз, но работает

                String startDate = dateToString(currentEntry.getStartDate()), finishDate = dateToString(currentEntry.getFinishDate());
                if (startHospitalDate==null || startHospitalDate.getTime()>currentEntry.getStartDate().getTime()) {startHospitalDate=currentEntry.getStartDate();}
                if (finishHospitalDate==null || finishHospitalDate.getTime()<currentEntry.getFinishDate().getTime()) {finishHospitalDate=currentEntry.getFinishDate();}
                add(sl,"SL_ID",slId);
                if (isVmp) {
                    if (currentEntry.getVMPTicketDate()==null
                            ||currentEntry.getVMPTicketNumber()==null
                            ||currentEntry.getVMPPlanHospDate()==null
                            ||currentEntry.getVMPKind()==null
                            ||currentEntry.getVMPMethod()==null) {
                        theManager.persist(new E2EntryError(currentEntry,"NO_FOND_FIELD: В случае ВМП не заполнены обязательные поля"));
                        return null;
                    }
                    add(sl,"VID_HMP",currentEntry.getVMPKind()); // Вид ВМП
                    add(sl,"METOD_HMP",currentEntry.getVMPMethod()); // Метод ВМП
                }
                add(sl,"LPU_1",currentEntry.getDepartmentCode()!=null ? currentEntry.getDepartmentCode() : "30000101");
                //PODR
 //               add(sl,"TELEMED",currentEntry.getDepartmentId()==416 ? "1" : "0");
                add(sl,"PROFIL",profile.getCode()); //Профиль специальностей V002 * 12.12.2018
                if (a1 || a2) {
                    if (currentEntry.getBedProfile()==null) {
                        LOG.error("Нет профиля койки у записи: "+currentEntry);
                        theManager.persist(new E2EntryError(currentEntry,"NO_FOND_FIELD: Нет профиля койки"));
                        return null;
                    }
                    add(sl,"PROFIL_K", currentEntry.getBedProfile().getCode()); //Профиль коек/специальностей (V020)
                }
                if (!a3) add(sl,"DET",isChild); //Признак детского возраста
                if (isPoliclinic) { //a1 a4
                    add(sl,"P_CEL",currentEntry.getVisitPurpose().getCode()); // Цель посещения
                }
                if (a2) {
                    add(sl,"TAL_D",currentEntry.getVMPTicketDate()); // Дата выдачи талона ВМП
                    add(sl,"TAL_NUM",currentEntry.getVMPTicketNumber()); // Номер выдачи ВМП
                    add(sl,"TAL_P",dateToString(currentEntry.getVMPPlanHospDate())); // Дата планируемой госпитализации
                }
                add(sl,"NHISTORY",currentEntry.getHistoryNumber()); //Номер истории болезни
                if (isHosp) {
                    add(sl,"P_PER",Expert2FondUtil.calculateFondP_PER(currentEntry)); //Признак перевода
                }
                add(sl,"DATE_1",startDate); //Дата начала случая
                add(sl,"DATE_2",finishDate); //Дата окончания случая
                if (isHosp) {
                    add(sl,"KD",currentEntry.getBedDays()); //Продолжительность госпитализации
                }
                setSluchDiagnosis(sl, currentEntry,version);
                if (sl==null) {
                    theManager.persist(new E2EntryError(currentEntry,"NO_MAIN_DIAGNOSIS"));
                    return null;
                }
                if (a2||a3) add(sl,"DS_ONK",(cancerEntry!=null && cancerEntry.getMaybeCancer()) ? "1" : "0");
                //*DN дисп. наблюдение !! только для терр.
                //if (a3) *PR_D_N взят-состоит
                //if (a3) *DS2_N
                //CODE_MES1
                //CODE_MES2
                if (a2 ||a4) { //NAPR, CONS, ONK_SL
                    if (isCancer && cancerEntry!=null) { //NAPR
                        //  if (cancerEntry.getMaybeCancer()) { //Заполняем информация при подозрении на ЗНО
                        List<E2CancerDirection> directions = cancerEntry.getDirections();
                        if (!directions.isEmpty()) {
                            for (E2CancerDirection direction : directions) {
                                Element napr = new Element("NAPR");
                                add(napr, "NAPR_DATE", direction.getDate());
                                addIfNotNull(napr,"NAPR_MO",direction.getDirectLpu());
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
                            if (",1,2,3,".contains(consiliumResult)) add(cons, "DT_CONS", cancerEntry.getConsiliumDate());
                            sl.addContent(cons);
                        }
                        if (!cancerEntry.getMaybeCancer()) { //Случай ЗНО
                            Element onkSl = new Element("ONK_SL");
                            if (cancerEntry.getOccasion() == null || (cancerEntry.getOccasion().equals("0") && (
                                    cancerEntry.getTumor()== null
                                            || cancerEntry.getNodus()== null
                                            || cancerEntry.getMetastasis()== null ))
                            ) {
                                theManager.persist(new E2EntryError(currentEntry,"NO_CANCERINFO"));
                                return null;
                            }
                            String serviceType = cancerEntry.getServiceType()!=null?cancerEntry.getServiceType():"";
                            add(onkSl,"DS1_T",cancerEntry.getOccasion());
                            addIfNotNull(onkSl,"STAD",cancerEntry.getStage());
                            addIfNotNull(onkSl,"ONK_T",cancerEntry.getTumor());
                            addIfNotNull(onkSl,"ONK_N",cancerEntry.getNodus());
                            addIfNotNull(onkSl,"ONK_M",cancerEntry.getMetastasis());
                            addIfNotNull(onkSl,"MTSTZ",cancerEntry.getIsMetastasisFound());
                            addIfNotNull(onkSl,"SOD",cancerEntry.getSod());
                            //K_FR
                            if (serviceType.equals("2")) {
                                add(onkSl,"WEI",currentEntry.getWeigth());
                                add(onkSl,"HEI",currentEntry.getHeight());
                            }
                            List<E2CancerDiagnostic> diagnostics= cancerEntry.getDiagnostics();
                            for (E2CancerDiagnostic diagnostic: diagnostics){
                                Element dir = new Element("B_DIAG");
                                add(dir,"DIAG_DATE",diagnostic.getBiopsyDate());
                                add(dir,"DIAG_TIP",diagnostic.getType());
                                add(dir,"DIAG_CODE",diagnostic.getCode());
                                add(dir,"DIAG_RSLT",diagnostic.getResult());
                                add(dir,"REC_RSLT","1");
                                onkSl.addContent(dir);
                            }
                            List<E2CancerRefusal> prots = cancerEntry.getRefusals();
                            for (E2CancerRefusal prot: prots){
                                Element pr = new Element("B_PROT");
                                add(pr,"PROT",prot.getCode());
                                add(pr,"D_PROT",prot.getDate());
                                onkSl.addContent(pr);
                            }
                            if (isHosp ||isVmp) {
                                Element onkUsl = new Element("ONK_USL");
                                if (serviceType.equals("4")) {
                                    theManager.persist(new E2EntryError(currentEntry,"ONCOLOGY_CASE_DRUG","Не может быть вид онколечения: "+serviceType+" химиолучевая терапия"));
                                }
                                add(onkUsl, "USL_TIP", serviceType);
                                if (serviceType.equals("1")) {
                                    addIfNotNull(onkUsl, "HIR_TIP", cancerEntry.getSurgicalType());
                                } else if (serviceType.equals("2")) {
                                    addIfNotNull(onkUsl, "LEK_TIP_L", cancerEntry.getDrugLine());
                                    addIfNotNull(onkUsl, "LEK_TIP_V", cancerEntry.getDrugCycle());
                                }

                                if (serviceType.equals("2")||serviceType.equals("4")) { //Сведения о введенном противоопухолевом препарате
                                    List<E2CancerDrug> drugList = cancerEntry.getDrugs();
                                    for (E2CancerDrug drug : drugList) {
                                        Element lekPr = new Element("LEK_PR");
                                        add(lekPr, "REGNUM",drug.getDrug()!=null ? drug.getDrug().getCode():"_____"+currentEntry.getId());
                                        add(lekPr, "CODE_SH","нЕт"); //TODO переделать, если будет схема
                                        if (drug.getDates() == null || drug.getDates().isEmpty()) {
                                            theManager.persist(new E2EntryError(aEntry,"NO_DATE_IN_DRUG"));
                                            return null;
                                        }
                                        for(E2CancerDrugDate drugDate : drug.getDates()) {
                                            add(lekPr,"DATE_INJ",drugDate.getDate());
                                        }
                                        onkUsl.addContent(lekPr);
                                    }
                                }
                                if (serviceType.equals("3")||serviceType.equals("4")) {
                                    addIfNotNull(onkUsl, "LUCH_TIP", cancerEntry.getRadiationTherapy()); //Тип лучевой терапии
                                }
                                onkSl.addContent(onkUsl);
                            }
                            sl.addContent(onkSl);
                        }
                    } else if (isCancer) {
                        LOG.error("Не найден раковый случай для записи с ИД: "+currentEntry.getId());
                    }
                }

                if (isHosp){//KSG_KGP
                    Element ksgKpg=new Element("KSG_KPG");
                    VocKsg ksg = currentEntry.getKsg();
                    add(ksgKpg,"N_KSG",ksg.getCode());
                    add(ksgKpg,"VER_KSG",ksg.getYear()!=null ?ksg.getYear() : 2018);
                    add(ksgKpg,"KSG_PG","0");
                    add(ksgKpg,"KOEF_Z",ksg.getKZ());
                    add(ksgKpg,"KOEF_UP",theExpertService.getActualKsgUprCoefficient(ksg,currentEntry.getFinishDate()));
                    add(ksgKpg,"BZTSZ",currentEntry.getBaseTarif());
                    add(ksgKpg,"KOEF_D","1"); //TODO
                    add(ksgKpg,"KOEF_U","1"); //TODO
                    addIfNotNull(ksgKpg,"CRIT",currentEntry.getDopKritKSG());
                    //DKK2
                    List<E2CoefficientPatientDifficultyEntryLink> difficultyEntryLinks = currentEntry.getPatientDifficulty();
                    if (!difficultyEntryLinks.isEmpty()){
                        add(ksgKpg,"SL_K","1");
                        add(ksgKpg,"IT_SL",theExpertService.calculateResultDifficultyCoefficient(currentEntry));
                        for (E2CoefficientPatientDifficultyEntryLink link: difficultyEntryLinks){
                            Element slKoef = new Element("SL_KOEF");
                            add(slKoef,"IDSL",link.getDifficulty().getCode());
                            add(slKoef,"Z_SL",link.getValue()!=null ? link.getValue() : link.getDifficulty().getValue());
                            ksgKpg.addContent(slKoef);
                        }
                    } else {
                        add(ksgKpg,"SL_K","0");
                    }
                    sl.addContent(ksgKpg);
                }
                // * REAB * //TODO медиал
                String prvs = currentEntry.getFondDoctorSpecV021()!=null ? currentEntry.getFondDoctorSpecV021().getCode() : "ERROR_"+currentEntry.getDoctorWorkfunction();
                add(sl,"PRVS",prvs); //Специальность лечащего врача
                add(sl,"VERS_SPEC",vers);
                add(sl,"IDDOKT",currentEntry.getDoctorSnils()); // СНИЛС лечащего врача
                if (a3){
                    add(sl,"NAZ","currentEntry.getDoctorSnils()"); // назначения в ДД
                }
                add(sl,"ED_COL",edCol);
                if (isPoliclinicKdp) {
                    add(sl,"TARIF",aEntry.getCost());
                    add(sl,"SUM_M",aEntry.getCost());
                } else if (isPoliclinic && isLongCase) {
                    if (!isDiagnosisFill && sl.getChild("DS1")!=null && !sl.getChildText("DS1").startsWith("Z")) {
                        isDiagnosisFill=true;
                        add(sl,"TARIF",aEntry.getCost());
                        add(sl,"SUM_M",aEntry.getCost());
                    } else {
                        add(sl,"TARIF","0");
                        add(sl,"SUM_M","0");
                    }
                }  else {
                    add(sl,"TARIF",currentEntry.getCost());
                    add(sl,"SUM_M",currentEntry.getCost());
                }

                //USL start
                int uslCnt = 0;
                if (currentEntry.getReanimationEntry()!=null) { //Реанимационная услуга
                    uslCnt++;
                    sl.addContent(createUsl(""+uslCnt,mainLpu,profileK,"B03.003.005",isChild,startDate,finishDate,sl.getChildText("DS1"),"1",prvs,currentEntry.getDoctorSnils()));
                }
                //Информация об услугах
                if (isPoliclinic) { //Для поликлиники - кол-во визитов
                    if (!isPoliclinicKdp && children.isEmpty()) {
                        children.add(currentEntry);
                    }
                    boolean isFirst = true;
                    for (E2Entry child: children) {
                        boolean isFoundPriemService = false;
                        String visitService = ""; //находим - есть ли первичный/повторный прием врача или создавать самим
                        uslCnt++;
                        String uslDate = dateToString(child.getStartDate());
                        VocE2MedHelpProfile childProfile =child.getMedHelpProfile();
                        if (childProfile==null) {
                            theManager.persist(new E2EntryError(aEntry,"NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: "+child.getId()));
                            LOG.warn("NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: "+child.getId());
                            continue;
                        }
                        VocE2FondV021 spec = child.getFondDoctorSpecV021();
                        prvs = child.getFondDoctorSpecV021()!=null ? child.getFondDoctorSpecV021().getCode() : childProfile.getMedSpecV021().getCode();

                        String vidVme;
                        if (isPoliclinicKdp) {
                            vidVme = child.getMainService();
                        } else {
                            try {
                                VocMedService vms = isFirst? spec.getDefaultMedService():spec.getRepeatMedService();
                                vidVme = vms.getCode();
                                visitService = vms.getCode();
                            } catch (Exception e) {
                                vidVme = "A02.12.002";
                                LOG.error(" У врача "+spec.getCode()+" нет услуги по умолчанию");
                            }
                        }
                        Element usl = createUsl(""+uslCnt, mainLpu, childProfile.getCode(),vidVme,isChild,uslDate,uslDate
                                ,isNotNull(child.getMainMkb())?child.getMainMkb():sl.getChildText("DS1"),"1",spec.getCode(),child.getDoctorSnils()); //Создаем услугу по умолчанию. Для КДП и неотложки - не нужно

                        List<EntryMedService> serviceList = child.getMedServices();
                        for (EntryMedService service : serviceList) {
                            uslCnt++;
                            Element uslService = (Element) usl.clone();
                            uslService.getChild("IDSERV").setText(uslCnt+"");
                            String serviceCode = service.getMedService().getCode();
                            uslService.getChild("VID_VME").setText(serviceCode);
                            sl.addContent(uslService);
                            if (serviceCode.equals(visitService)) isFoundPriemService = true;
                        }
                        isFirst=false;
                        if (!isPoliclinicKdp && !isFoundPriemService) { //не нашли нужную услугу - создадим её сами!
                            sl.addContent(usl);
                        }
                    }
                    if (isPoliclinicKdp) { //Для КДП находим все услуги помимо дочерних визитов
                        List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id), servicedate,max(id) as cnt from EntryMedService where entry_id=:id group by medservice_id, servicedate").setParameter("id",aEntry.getId()).getResultList();
                        if (!list.isEmpty()) {
                            String medServiceCode;
                            for (Object[] ms: list) {
                                uslCnt++;
                                EntryMedService ems = theManager.find(EntryMedService.class,Long.valueOf(ms[3].toString()));
                                VocMedService medService = ems.getMedService();
                                medServiceCode = medService.getCode();
                                boolean isOwnProfile = medServiceCode.startsWith("B");
                                sl.addContent(createUsl(""+uslCnt, mainLpu,isOwnProfile ? getMedHelpProfileCodeByMedSpec(ems.getDoctorSpeciality()) : profileK
                                        ,medServiceCode,isChild,startDate,finishDate,sl.getChildText("DS1"),ms[1].toString()
                                        ,isOwnProfile && ems.getDoctorSpeciality()!=null ? ems.getDoctorSpeciality().getCode()  : prvs,isNotNull(ems.getDoctorSnils()) ? ems.getDoctorSnils() : currentEntry.getDoctorSnils()));
                            }
                            sl.getChild("ED_COL").setText("1"); //ED_COL всегда равен 1 *** 02-08-2018
                        }
                        //   isKdoServicesSet = true;
                    }
                }  /* else if (isExtDisp) { //TODO
                  List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id) as cnt from EntryMedService where entry_id=:id group by medservice_id").setParameter("id",aEntry.getId()).getResultList();
                    if (list.size()>0) {
                        for (Object[] ms: list) {
                            VocMedService medService = theManager.find(VocMedService.class,Long.valueOf(ms[0].toString()));
                            Element usl = new Element("USL");
                            usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                            usl.addContent(new Element("PROFIL_U").setText(profileK));
                            usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                            usl.addContent(new Element("IDDOKT_U").setText(aEntry.getDoctorSnils()));
                            usl.addContent(new Element("DATE_1_U").setText(startDate));
                            usl.addContent(new Element("DATE_2_U").setText(startDate));
                            usl.addContent(new Element("DS_U").setText(sluch.getChildText("DS1")));
                            usl.addContent(new Element("COD_DUSL_U").setText(medService.getCode()));
                            usl.addContent(new Element("ED_COL_U").setText(ms[1].toString()));
                            usl.addContent(new Element("PRVS_U").setText("0"));
                            sluch.addContent(usl);
                            uslCnt++;
                        }
                    }
                } */ else { //стационар
                    List<Object[]> list = theManager.createNativeQuery("select vms.code as ms, cast(count(ems.id) as varchar) as cnt" +
                            ", coalesce(cast(case when ems.serviceDate>e.finishdate then e.finishdate else ems.servicedate end as varchar(10)),'') as serviceDate" +
                            " from EntryMedService ems" +
                            " left join e2entry e on ems.entry_id = e.id" +
                            " left join vocMedService vms on vms.id=ems.medService_id" +
                            " where (e.id=:id or e.parententry_id=:id) and ems.serviceDate>=:entryDate" + //выгружаем только услуги
                            " group by vms.code, case when ems.serviceDate>e.finishdate then e.finishdate else ems.servicedate end")
                            .setParameter("id",currentEntry.getId()).setParameter("entryDate",currentEntry.getStartDate()).getResultList();

                    if (!list.isEmpty()) {
                        for (Object[] ms: list) {
                            uslCnt++;
                            sl.addContent(createUsl(""+uslCnt, mainLpu, profileK, ms[0].toString(),isChild,startDate
                                    ,finishDate,sl.getChildText("DS1"),ms[1].toString()
                                    ,prvs,currentEntry.getDoctorSnils()));

                        }
                    }
                    sl.addContent(createUsl(++uslCnt+"", mainLpu, profileK, currentEntry.getBedProfile().getDefaultStacMedService()!=null ? currentEntry.getBedProfile().getDefaultStacMedService().getCode() : "AAA"
                            ,isChild,finishDate,finishDate,sl.getChildText("DS1"),"1"
                            ,prvs,currentEntry.getDoctorSnils()));
                }
                // USL finish
                zSl.addContent(indSl,sl);
                indSl++;
                //NAZ for ExtDisp   //   sluch.addContent(new Element("NAZ"));
            }
            /** Закончили создавать Sl*/
            zSl.getChild("DATE_Z_1").setText(dateToString(startHospitalDate));
            zSl.getChild("DATE_Z_2").setText(dateToString(finishHospitalDate));
            if (zSl.getChild("KD_Z")!=null) {zSl.getChild("KD_Z").setText(kdz+"");}
            zSl.removeChild("SL_TEMPLATE");
            zap.addContent(zSl); //Добавляем информацию о случае в запись
            return zap;
        } catch (Exception e) {
            LOG.error("Entry #"+aEntry.getId()+", error = "+e.getLocalizedMessage(),e);
            throw new IllegalStateException("Entry #"+aEntry.getId()+", error = "+e.getLocalizedMessage(),e);
        }
    }

    /*Создаем тэг USL по формату 2020 года*/
    private Element createUsl(String id, String lpu, String profile, String vidVme, String isChild, String startDate, String finishDate, String ds, String kolUsl, String prvs, String codeMd) {
        Element usl = new Element("USL");
        usl.addContent(new Element("IDSERV").setText(id));
        usl.addContent(new Element("LPU").setText(lpu));
        //  usl.addContent(new Element("LPU_1").setText(lpu1));
        //  PODR
        add(usl,"PROFIL",profile);
        add(usl,"VID_VME",vidVme);
        add(usl,"DET",isChild); //Возраст на момент начала случая (<18 лет =1)
        add(usl,"DATE_IN",startDate);
        add(usl,"DATE_OUT",finishDate);
    //  if (a3) P_OTK
        add(usl,"DS",ds);
    //  add(usl,"CODE_USL",codeUsl);
        add(usl,"KOL_USL",kolUsl);
    //  add(usl,"TARIF","1");
        add(usl,"SUMV_USL","0");
        add(usl,"PRVS",prvs);
        add(usl,"CODE_MD",codeMd);
        add(usl,"NPL","0");
        return usl;
    }

    /** Создааем информацию по случаю мед. обслуживания для версии от ноября 2018 (3.1.1)
     * aEntry - случай госпитализации
     * entriesList - строка с ИД СЛО
     * */
        private Element createSlElements(E2Entry aEntry, String entriesString, int cnt, boolean isExport263, String mainLpu, String lpuOmcCode) {

            /*
            ZSL, SL = информация об обращении. визиты переносятся в USL
            * */
            String version = "3.1.1";
        try {
            String entryType = aEntry.getEntryType();
            boolean isHosp = false, isVmp = false, isPoliclinic = false, isExtDisp = false, isPoliclinicKdp=false;
            boolean isNedonosh = false;
            switch (entryType) {
                case HOSPITALTYPE:
                    isHosp=true;
                    isNedonosh=aEntry.getKsg()!=null && ",st17.001,st17.002,st17.003,".contains(","+aEntry.getKsg().getCode()+",");
                    break;
                case VMPTYPE:
                    isVmp=true;
                    isNedonosh=aEntry.getIsChild();
                    break;
                case POLYCLINICTYPE:
                    isPoliclinic=true;
                    break;
                case KDPTYPE:
                    isPoliclinic=true;
                    isPoliclinicKdp=true;
                    break;
                    default:
                        throw new IllegalStateException("UNKNOWN ENTRYTYPE"+entryType);
            }
            Element zap = new Element("ZAP");
            add(zap,"N_ZAP",aEntry.getId() + "");
            add(zap,"PR_NOV",isNotNull(aEntry.getPRNOV()) ? "1" : "0");
            Element pat = new Element("PACIENT");
            add(pat,"ID_PAC",aEntry.getExternalPatientId() + "");
            add(pat,"VPOLIS",aEntry.getMedPolicyType());
            if (aEntry.getMedPolicyType() != null && !aEntry.getMedPolicyType().equals("3")) {
                addIfNotNull(pat, "SPOLIS", aEntry.getMedPolicySeries()); //Если полис не нового образца - добавляем серию полиса
                add(pat,"NPOLIS",aEntry.getMedPolicyNumber());
            } else {
                add(pat,"NPOLIS",aEntry.getCommonNumber());
            }

            if (isNotNull(aEntry.getInsuranceCompanyCode())) {
                add(pat,"SMO",aEntry.getInsuranceCompanyCode());
            }  else {
                add(pat,"SMO_OGRN",aEntry.getInsuranceCompanyOgrn());
                add(pat,"SMO_NAM",aEntry.getInsuranceCompanyName());
            }
            String novorString=makeNovorString(aEntry);
            add(pat,"NOVOR",novorString);
            if (isNedonosh && !isNotNull(aEntry.getKinsmanLastname())) { // && novorString.equals("0")) { //11.10.2018 по согласованию с фондом
                add(pat,"VNOV_D",aEntry.getNewbornWeight()+"");
            }

            zap.addContent(pat); //Добавили данные по пациенту
            List<E2Entry> children = null;
            String isChild = isNotNull(aEntry.getIsChild()) ? "1" : "0";

          //  edCol="1"; //06-08-2018 Ед кол больше не равно 1 !

            String[] slIds = entriesString.split(",");
            Element zSl = createZSl(aEntry,isPoliclinic,slIds.length,cnt, isExport263, isNedonosh, lpuOmcCode);
            int indSl = zSl.indexOf(zSl.getChild("SL_TEMPLATE"));
            Date startHospitalDate = null, finishHospitalDate=null;
            int kdz=0;

            /** Вот тут создаем Sl*/
            boolean isDiagnosisFill=false, isLongCase=slIds.length>1;
         //   boolean isKdoServicesSet = false;
            String vers="V021";
            for (String slId:slIds) {
                Element sl = new Element("SL");
                E2Entry currentEntry = theManager.find(E2Entry.class,Long.valueOf(slId.trim()));
                String edCol="1";
               if (isPoliclinicKdp) {
                   //edCol="1";
                   //children = theManager.createQuery("from E2Entry where parentEntry_id=:id and (isDeleted is null or isDeleted='0') and (doNotSend is null or DoNotSend='0')").setParameter("id",currentEntry.getId()).getResultList();
                   children = new ArrayList<>();
               } else if (isPoliclinic) {
                    children = theManager.createQuery("from E2Entry where parentEntry_id=:id and (isDeleted is null or isDeleted='0') and (doNotSend is null or DoNotSend='0') order by startDate").setParameter("id",currentEntry.getId()).getResultList();
                 //   edCol=""+(children.size()>0?children.size():1);
                } else { //стационар
                    kdz+=currentEntry.getBedDays().intValue();
                //    edCol= currentEntry.getBedDays().toString(); // Количество единиц оплаты мед. помощи
                }

                boolean isCancer = isNotNull(currentEntry.getIsCancer());
         //       if (isCancer && currentEntry.getMedHelpProfile().getCode().equals("12")) {isCancer=false;} //Убрать колхоз
                if (isCancer && currentEntry.getVisitPurpose()!=null && "1.3".equals(currentEntry.getVisitPurpose().getCode())) {isCancer=false;}
                E2CancerEntry cancerEntry = null;
                if (isCancer && !currentEntry.getCancerEntries().isEmpty()) {
                    cancerEntry=currentEntry.getCancerEntries().get(0); //Считаем что не может быть больше 1 онкослучая
                }

                VocE2MedHelpProfile profile = currentEntry.getMedHelpProfile();
                String profileK = profile.getCode();// колхоз, но работает

                String startDate = dateToString(currentEntry.getStartDate()), finishDate = dateToString(currentEntry.getFinishDate());
                if (startHospitalDate==null || startHospitalDate.getTime()>currentEntry.getStartDate().getTime()) {startHospitalDate=currentEntry.getStartDate();}
                if (finishHospitalDate==null || finishHospitalDate.getTime()<currentEntry.getFinishDate().getTime()) {finishHospitalDate=currentEntry.getFinishDate();}
                add(sl,"SL_IDCASE",slId);
                add(sl,"LPU_1",currentEntry.getDepartmentCode()!=null ? currentEntry.getDepartmentCode() : "30000101");
                //PODR
                if (isVmp) {
                    if (currentEntry.getVMPTicketDate()==null
                            ||currentEntry.getVMPTicketNumber()==null
                            ||currentEntry.getVMPPlanHospDate()==null
                            ||currentEntry.getVMPKind()==null
                            ||currentEntry.getVMPMethod()==null) {
                        theManager.persist(new E2EntryError(currentEntry,"NO_FOND_FIELD: В случае ВМП не заполнены обязательные поля"));
                        return null;
                    }
                    add(sl,"TAL_D",currentEntry.getVMPTicketDate()); // Дата выдачи талона ВМП
                    add(sl,"TAL_N",currentEntry.getVMPTicketNumber()); // Номер выдачи ВМП
                    add(sl,"TAL_P",dateToString(currentEntry.getVMPPlanHospDate())); // Дата планируемой госпитализации
                    add(sl,"VID_HMP",currentEntry.getVMPKind()); // Вид ВМП
                    add(sl,"METOD_HMP",currentEntry.getVMPMethod()); // Метод ВМП
                }
                add(sl,"TELEMED",currentEntry.getDepartmentId()==416 ? "1" : "0");
                add(sl,"PROFIL",profile.getCode()); //Профиль специальностей V002 * 12.12.2018
                if (isHosp || isVmp) {
                    if (currentEntry.getBedProfile()==null) {
                        LOG.error("Нет профиля койки у записи: "+currentEntry);
                        theManager.persist(new E2EntryError(currentEntry,"NO_FOND_FIELD: Нет профиля койки"));
                        return null;
                    }
                    add(sl,"PROFIL_K", currentEntry.getBedProfile().getCode()); //Профиль коек/специальностей (V020)
                }
                add(sl,"DET",isChild); //Признак детского возраста
                if (isPoliclinic) {
                    add(sl,"P_CEL",currentEntry.getVisitPurpose().getCode()); // Цель посещения
                }
                add(sl,"NHISTORY",currentEntry.getHistoryNumber()); //Номер истории болезни
                if (isHosp||isVmp) {
                    add(sl,"P_PER",Expert2FondUtil.calculateFondP_PER(currentEntry)); //Признак перевода
                }
                add(sl,"DATE_1",startDate); //Дата начала случая
                add(sl,"DATE_2",finishDate); //Дата окончания случая
                if (isHosp||isVmp) {
                    add(sl,"KD",currentEntry.getBedDays()); //Продолжительность госпитализации
                }
                setSluchDiagnosis(sl, currentEntry,version);
                if (sl==null) {
                    theManager.persist(new E2EntryError(currentEntry,"NO_MAIN_DIAGNOSIS"));
                    return null;
                }
           //     sl=add(sl,"NPL",isNotNull(currentEntry.getNotFullPaymentReason())?currentEntry.getNotFullPaymentReason():"0"); // Неполный объем //TODO
                add(sl,"DS_ONK",(cancerEntry!=null && cancerEntry.getMaybeCancer()) ? "1" : "0");
                //*DN дисп. наблюдение
                //*DN_DP дата след. Д визита
                if (isCancer && cancerEntry!=null) { //NAPR
                  //  if (cancerEntry.getMaybeCancer()) { //Заполняем информация при подозрении на ЗНО
                    List<E2CancerDirection> directions = cancerEntry.getDirections();
                    if (!directions.isEmpty()) {
                        for (E2CancerDirection direction : directions) {
                            Element napr = new Element("NAPR");
                            add(napr, "NAPR_DATE", direction.getDate());
                            addIfNotNull(napr,"NAPR_MO",direction.getDirectLpu());
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
                        if (",1,2,3,".contains(consiliumResult)) add(cons, "DT_CONS", cancerEntry.getConsiliumDate());
                        sl.addContent(cons);
                    }
                    if (!cancerEntry.getMaybeCancer()) { //Случай ЗНО
                        Element onkSl = new Element("ONK_SL");
                        if (cancerEntry.getOccasion() == null || (cancerEntry.getOccasion().equals("0") && (
                                cancerEntry.getTumor()== null
                                || cancerEntry.getNodus()== null
                                || cancerEntry.getMetastasis()== null ))
                        ) {
                            theManager.persist(new E2EntryError(currentEntry,"NO_CANCERINFO"));
                            return null;
                        }
                        String serviceType = cancerEntry.getServiceType()!=null?cancerEntry.getServiceType():"";
                        add(onkSl,"DS1_T",cancerEntry.getOccasion());
                        addIfNotNull(onkSl,"STAD",cancerEntry.getStage());
                        addIfNotNull(onkSl,"ONK_T",cancerEntry.getTumor());
                        addIfNotNull(onkSl,"ONK_N",cancerEntry.getNodus());
                        addIfNotNull(onkSl,"ONK_M",cancerEntry.getMetastasis());
                        addIfNotNull(onkSl,"MTSTZ",cancerEntry.getIsMetastasisFound());
                        addIfNotNull(onkSl,"SOD",cancerEntry.getSod());
                        //K_FR
                        if (serviceType.equals("2")) {
                            add(onkSl,"WEI",currentEntry.getWeigth());
                            add(onkSl,"HEI",currentEntry.getHeight());
                        }
                        List<E2CancerDiagnostic> diagnostics= cancerEntry.getDiagnostics();
                        for (E2CancerDiagnostic diagnostic: diagnostics){
                            Element dir = new Element("B_DIAG");
                            add(dir,"DIAG_DATE",diagnostic.getBiopsyDate());
                            add(dir,"DIAG_TIP",diagnostic.getType());
                            add(dir,"DIAG_CODE",diagnostic.getCode());
                            add(dir,"DIAG_RSLT",diagnostic.getResult());
                            add(dir,"REC_RSLT","1");
                            onkSl.addContent(dir);
                        }
                        List<E2CancerRefusal> prots = cancerEntry.getRefusals();
                        for (E2CancerRefusal prot: prots){
                            Element pr = new Element("B_PROT");
                            add(pr,"PROT",prot.getCode());
                            add(pr,"D_PROT",prot.getDate());
                            onkSl.addContent(pr);
                        }
                        if (isHosp ||isVmp) {
                            Element onkUsl = new Element("ONK_USL");
                            if (serviceType.equals("4")) {
                                theManager.persist(new E2EntryError(currentEntry,"ONCOLOGY_CASE_DRUG","Не может быть вид онколечения: "+serviceType+" химиолучевая терапия"));
                            }
                            add(onkUsl, "USL_TIP", serviceType);
                            if (serviceType.equals("1")) {
                               addIfNotNull(onkUsl, "HIR_TIP", cancerEntry.getSurgicalType());
                            } else if (serviceType.equals("2")) {
                               addIfNotNull(onkUsl, "LEK_TIP_L", cancerEntry.getDrugLine());
                               addIfNotNull(onkUsl, "LEK_TIP_V", cancerEntry.getDrugCycle());
                            }

                            if (serviceType.equals("2")||serviceType.equals("4")) { //Сведения о введенном противоопухолевом препарате
                                List<E2CancerDrug> drugList = cancerEntry.getDrugs();
                                for (E2CancerDrug drug : drugList) {
                                    Element lekPr = new Element("LEK_PR");
                                    add(lekPr, "REGNUM",drug.getDrug()!=null ? drug.getDrug().getCode():"_____"+currentEntry.getId());
                                    add(lekPr, "CODE_SH","нЕт"); //TODO переделать, если будет схема
                                    if (drug.getDates() == null || drug.getDates().isEmpty()) {
                                        theManager.persist(new E2EntryError(aEntry,"NO_DATE_IN_DRUG"));
                                        return null;
                                    }
                                    for(E2CancerDrugDate drugDate : drug.getDates()) {
                                        add(lekPr,"DATE_INJ",drugDate.getDate());
                                    }
                                    onkUsl.addContent(lekPr);
                                }
                            }
                            if (serviceType.equals("3")||serviceType.equals("4")) {
                                addIfNotNull(onkUsl, "LUCH_TIP", cancerEntry.getRadiationTherapy()); //Тип лучевой терапии
                            }
                            onkSl.addContent(onkUsl);
                        }
                        sl.addContent(onkSl);
                    }
                } else if (isCancer) {
                    LOG.error("Не найден раковый случай для записи с ИД: "+currentEntry.getId());
                }
                if (isHosp){//KSG_KGP
                    Element ksgKpg=new Element("KSG_KPG");
                    VocKsg ksg = currentEntry.getKsg();
                    add(ksgKpg,"N_KSG",ksg.getCode());
                    add(ksgKpg,"VER_KSG",ksg.getYear()!=null ?ksg.getYear() : 2018);
                    add(ksgKpg,"KSG_PG","0");
                    add(ksgKpg,"KOEF_Z",ksg.getKZ());
                    add(ksgKpg,"KOEF_UP",theExpertService.getActualKsgUprCoefficient(ksg,currentEntry.getFinishDate()));
                    add(ksgKpg,"BZTSZ",currentEntry.getBaseTarif());
                    add(ksgKpg,"KOEF_D","1"); //TODO
                    add(ksgKpg,"KOEF_U","1"); //TODO
                    addIfNotNull(ksgKpg,"CRIT",currentEntry.getDopKritKSG());
                    //DKK2
                    List<E2CoefficientPatientDifficultyEntryLink> difficultyEntryLinks = currentEntry.getPatientDifficulty();
                    if (!difficultyEntryLinks.isEmpty()){
                        add(ksgKpg,"SL_K","1");
                        add(ksgKpg,"IT_SL",theExpertService.calculateResultDifficultyCoefficient(currentEntry));
                        for (E2CoefficientPatientDifficultyEntryLink link: difficultyEntryLinks){
                            Element slKoef = new Element("SL_KOEF");
                            add(slKoef,"IDSL",link.getDifficulty().getCode());
                            add(slKoef,"Z_SL_K",link.getValue()!=null?link.getValue():link.getDifficulty().getValue());
                            ksgKpg.addContent(slKoef);
                        }
                    } else {
                        add(ksgKpg,"SL_K","0");
                    }
                    sl.addContent(ksgKpg);
                }
                // * REAB
                String prvs = currentEntry.getFondDoctorSpecV021()!=null ? currentEntry.getFondDoctorSpecV021().getCode() : "ERROR_"+currentEntry.getDoctorWorkfunction();
                add(sl,"PRVS",prvs); //Специальность лечащего врача
                add(sl,"VERS_SPEC",vers);
                add(sl,"IDDOKT",currentEntry.getDoctorSnils()); // СНИЛС лечащего врача
                add(sl,"ED_COL",edCol);
               if (isPoliclinicKdp) {
                   //if (isNotNull(currentEntry.getFondDoctorSpec().getIsKdoChief()) && !isKdoServicesSet) {
                        add(sl,"TARIF",aEntry.getCost());
                        add(sl,"SUM_M",aEntry.getCost());
                   /*} else {
                       sl=add(sl,"TARIF","0");
                       sl=add(sl,"SUM_M","0");
                   }*/
               } else if (isPoliclinic && isLongCase) {
                    if (!isDiagnosisFill && sl.getChild("DS1")!=null && !sl.getChildText("DS1").startsWith("Z")) {
                        isDiagnosisFill=true;
                        add(sl,"TARIF",aEntry.getCost());
                        add(sl,"SUM_M",aEntry.getCost());
                    } else {
                        add(sl,"TARIF","0");
                        add(sl,"SUM_M","0");
                    }
               }  else {
                    add(sl,"TARIF",currentEntry.getCost());
                    add(sl,"SUM_M",currentEntry.getCost());
               }

                //USL start
                int uslCnt = 0;
                if (currentEntry.getReanimationEntry()!=null) { //Реанимационная услуга
                    uslCnt++;
                    Element usl = new Element("USL");
                    usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                    usl.addContent(new Element("LPU_U").setText(mainLpu));
                    //LPU_1_U
                    //PODR_U
                    add(usl,"PROFIL_U",profileK);
                    //usl.addContent(new Element("PROFIL_U").setText(profileK)); //Точно профилёк? может быть, профиль? *12/12/2018 профиль
                    add(usl,"VID_VME","B03.003.005");
                    add(usl,"DET_U",isChild); //Возраст на момент начала случая (<18 лет =1)
                    add(usl,"DATE_1_U",startDate);
                    add(usl,"DATE_2_U",finishDate);
                    add(usl,"DS_U",sl.getChildText("DS1"));
                    add(usl,"KOL_USL","1");
                    add(usl,"SUMV_USL","0");
                    add(usl,"PRVS_U",prvs);
                    add(usl,"IDDOKT_U",currentEntry.getDoctorSnils());
                    add(usl,"NPL","0");
                    sl.addContent(usl);
                }
                //Информация об услугах
                if (isPoliclinic) { //Для поликлиники - кол-во визитов
                    if (!isPoliclinicKdp && children.isEmpty()) {
                        children.add(currentEntry);
                    }
                    boolean isFirst = true;
                    for (E2Entry child: children) {
                        boolean isFoundPriemService = false;
                        String visitService = ""; //находим - есть ли первичный/повторный прием врача или создавать самим
                        uslCnt++;
                        String uslDate = dateToString(child.getStartDate());
                        VocE2MedHelpProfile childProfile =child.getMedHelpProfile();
                        if (childProfile==null) {
                            theManager.persist(new E2EntryError(aEntry,"NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: "+child.getId()));
                            LOG.warn("NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: "+child.getId());
                            continue;
                        }
                        VocE2FondV021 spec = child.getFondDoctorSpecV021();
                        prvs = child.getFondDoctorSpecV021()!=null ? child.getFondDoctorSpecV021().getCode() : childProfile.getMedSpecV021().getCode();
                        Element usl = new Element("USL"); //Создаем услугу по умолчанию. Для КДП и неотложки - не нужно
                        add(usl,"IDSERV",""+uslCnt);
                        add(usl,"LPU_U",mainLpu);
                        add(usl,"PROFIL_U",childProfile.getCode());

                        if (isPoliclinicKdp) {
                            add(usl,"VID_VME",child.getMainService());
                        } else {
                            try {
                                VocMedService vms = isFirst? spec.getDefaultMedService():spec.getRepeatMedService();
                                add(usl,"VID_VME",vms.getCode());
                                visitService = vms.getCode();
                            } catch (Exception e) {
                                add(usl,"VID_VME","A02.12.002");
                                LOG.error(" У врача "+spec.getCode()+" нет услуги по умолчанию");
                            }
                        }
                        add(usl,"DET_U",isChild); //Возраст на момент начала случая (<18 лет =1)
                        add(usl,"DATE_1_U",uslDate);
                        add(usl,"DATE_2_U",uslDate);
                        add(usl,"DS_U",isNotNull(child.getMainMkb())?child.getMainMkb():sl.getChildText("DS1"));
                        add(usl,"KOL_USL","1");
                        add(usl,"SUMV_USL","0");
                        add(usl,"PRVS_U",spec.getCode());
                        add(usl,"IDDOKT_U",child.getDoctorSnils());
                        add(usl,"NPL","0");

                        List<EntryMedService> serviceList = child.getMedServices();
                        for (EntryMedService service : serviceList) {
                            uslCnt++;
                            Element uslService = (Element) usl.clone();
                            uslService.getChild("IDSERV").setText(uslCnt+"");
                            String serviceCode = service.getMedService().getCode();
                            uslService.getChild("VID_VME").setText(serviceCode);
                            sl.addContent(uslService);
                            if (serviceCode.equals(visitService)) isFoundPriemService = true;
                        }
                        isFirst=false;
                        if (!isPoliclinicKdp && !isFoundPriemService) { //не нашли нужную услугу - создадим её сами!
                            sl.addContent(usl);
                        }
                    }
                    if (isPoliclinicKdp) { //Для КДП находим все услуги помимо дочерних визитов
                        List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id), servicedate,max(id) as cnt from EntryMedService where entry_id=:id group by medservice_id, servicedate").setParameter("id",aEntry.getId()).getResultList();
                        if (!list.isEmpty()) {
                            String medServiceCode;
                            for (Object[] ms: list) {
                                uslCnt++;
                                EntryMedService ems = theManager.find(EntryMedService.class,Long.valueOf(ms[3].toString()));
                                VocMedService medService = ems.getMedService();
                                medServiceCode = medService.getCode();
                                boolean isOwnProfile = medServiceCode.startsWith("B");
                         //       String serviceDate = dateToString(ems.getServiceDate());
                                Element usl = new Element("USL");
                                usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                                usl.addContent(new Element("LPU_U").setText(mainLpu));
                                usl.addContent(new Element("PROFIL_U").setText(isOwnProfile ? getMedHelpProfileCodeByMedSpec(ems.getDoctorSpeciality()) : profileK));
                                usl.addContent(new Element("VID_VME").setText(medServiceCode));
                                usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                                usl.addContent(new Element("DATE_1_U").setText(startDate)); //24-06 - дата услуги = дата КДП
                                usl.addContent(new Element("DATE_2_U").setText(finishDate));
                                usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                                usl.addContent(new Element("KOL_USL").setText(ms[1].toString()));
                                usl.addContent(new Element("SUMV_USL").setText("0"));
                                usl.addContent(new Element("PRVS_U").setText(isOwnProfile && ems.getDoctorSpeciality()!=null ? ems.getDoctorSpeciality().getCode()  : prvs));
                                usl.addContent(new Element("IDDOKT_U").setText(isNotNull(ems.getDoctorSnils()) ? ems.getDoctorSnils() : currentEntry.getDoctorSnils())); //Так правильно
                                usl.addContent(new Element("NPL").setText("0"));
                                sl.addContent(usl);
                            }
                            sl.getChild("ED_COL").setText("1"); //ED_COL всегда равен 1 *** 02-08-2018
                        }
                     //   isKdoServicesSet = true;
                    }
                }  /* else if (isExtDisp) { //TODO
                  List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id) as cnt from EntryMedService where entry_id=:id group by medservice_id").setParameter("id",aEntry.getId()).getResultList();
                    if (list.size()>0) {
                        for (Object[] ms: list) {
                            VocMedService medService = theManager.find(VocMedService.class,Long.valueOf(ms[0].toString()));
                            Element usl = new Element("USL");
                            usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                            usl.addContent(new Element("PROFIL_U").setText(profileK));
                            usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                            usl.addContent(new Element("IDDOKT_U").setText(aEntry.getDoctorSnils()));
                            usl.addContent(new Element("DATE_1_U").setText(startDate));
                            usl.addContent(new Element("DATE_2_U").setText(startDate));
                            usl.addContent(new Element("DS_U").setText(sluch.getChildText("DS1")));
                            usl.addContent(new Element("COD_DUSL_U").setText(medService.getCode()));
                            usl.addContent(new Element("ED_COL_U").setText(ms[1].toString()));
                            usl.addContent(new Element("PRVS_U").setText("0"));
                            sluch.addContent(usl);
                            uslCnt++;
                        }
                    }
                } */ else { //стационар
                    List<Object[]> list = theManager.createNativeQuery("select vms.code as ms, cast(count(ems.id) as varchar) as cnt" +
                            ", coalesce(cast(case when ems.serviceDate>e.finishdate then e.finishdate else ems.servicedate end as varchar(10)),'') as serviceDate" +
                            " from EntryMedService ems" +
                            " left join e2entry e on ems.entry_id = e.id" +
                            " left join vocMedService vms on vms.id=ems.medService_id" +
                            " where (e.id=:id or e.parententry_id=:id) and ems.serviceDate>=:entryDate" + //выгружаем только услуги
                            " group by vms.code, case when ems.serviceDate>e.finishdate then e.finishdate else ems.servicedate end")
                            .setParameter("id",currentEntry.getId()).setParameter("entryDate",currentEntry.getStartDate()).getResultList();

                    if (!list.isEmpty()) {
                        for (Object[] ms: list) {
                            uslCnt++;
                            Element usl = new Element("USL");
                            usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                            usl.addContent(new Element("LPU_U").setText(mainLpu));
                            usl.addContent(new Element("PROFIL_U").setText(profileK));
                            usl.addContent(new Element("VID_VME").setText(ms[0].toString()));
                            usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                            usl.addContent(new Element("DATE_1_U").setText(ms[2].toString()));
                            usl.addContent(new Element("DATE_2_U").setText(ms[2].toString()));
                            usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                            usl.addContent(new Element("KOL_USL").setText(ms[1].toString()));
                            usl.addContent(new Element("SUMV_USL").setText("0"));
                            usl.addContent(new Element("PRVS_U").setText(prvs));
                            usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                            usl.addContent(new Element("NPL").setText("0"));
                            sl.addContent(usl);
                        }
                    }
                    Element usl = new Element("USL");
                    usl.addContent(new Element("IDSERV").setText(++uslCnt+""));
                    usl.addContent(new Element("LPU_U").setText(mainLpu));
                    usl.addContent(new Element("PROFIL_U").setText(profileK));
                    usl.addContent(new Element("VID_VME").setText(currentEntry.getBedProfile().getDefaultStacMedService()!=null ? currentEntry.getBedProfile().getDefaultStacMedService().getCode() : "AAA"));
                    usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                    usl.addContent(new Element("DATE_1_U").setText(finishDate));
                    usl.addContent(new Element("DATE_2_U").setText(finishDate));
                    usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                    usl.addContent(new Element("KOL_USL").setText("1"));
                    usl.addContent(new Element("SUMV_USL").setText("0"));
                    usl.addContent(new Element("PRVS_U").setText(prvs));
                    usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                    usl.addContent(new Element("NPL").setText("0"));
                    sl.addContent(usl);
                }
                // USL finish
                zSl.addContent(indSl,sl);
                indSl++;
            //NAZ for ExtDisp   //   sluch.addContent(new Element("NAZ"));
            }
            /** Закончили создавать Sl*/
            zSl.getChild("DATE_Z_1").setText(dateToString(startHospitalDate));
            zSl.getChild("DATE_Z_2").setText(dateToString(finishHospitalDate));
            if (zSl.getChild("KD_Z")!=null) {zSl.getChild("KD_Z").setText(kdz+"");}
            zSl.removeChild("SL_TEMPLATE");
            zap.addContent(zSl); //Добавляем информацию о случае в запись
            return zap;
        } catch (Exception e) {
            LOG.error("Entry #"+aEntry.getId()+", error = "+e.getLocalizedMessage(),e);
            throw new IllegalStateException("Entry #"+aEntry.getId()+", error = "+e.getLocalizedMessage(),e);
        }
    }

    /**Находим по врачебной специальности профиль медицинской помощи*/
    private String getMedHelpProfileCodeByMedSpec(VocE2FondV021 aMedSpec) {
        if (aMedSpec==null) return "OLEG_FIXME";
            List<VocE2MedHelpProfile> list = theManager.createQuery("from VocE2MedHelpProfile where medSpecV021=:medSpec").setParameter("medSpec",aMedSpec).getResultList();
            return list.isEmpty() ? aMedSpec.getId()+"_V021-"+aMedSpec.getCode() : list.get(0).getCode();

    }

    /** Создаем заголовок для L файла (информация о пациентах) */
    private void makeLTitle(Element root, Date aDocumentDate, String aFilename, String aVersion) {
        aDocumentDate = aDocumentDate != null ? aDocumentDate : new Date(new java.util.Date().getTime());
        Element zglv = new Element("ZGLV");
        zglv.addContent(new Element("VERSION").setText(aVersion));
        zglv.addContent(new Element("DATA").setText(dateToString(aDocumentDate)));
        zglv.addContent(new Element("FILENAME").setText(aFilename));
        zglv.addContent(new Element("FILENAME1").setText("H" + aFilename.substring(1)));
        root.addContent(zglv);
    }


    /** Создаем заголовок для H файла (информация о мед. услугах) */
    private void makeHTitle(Element root, Date aDocumentDate, String aFilename, int count, String aBillNumber, Date aBillDate, BigDecimal aTotalSum, String aVersion, String aLpu) {
        aDocumentDate = aDocumentDate != null ? aDocumentDate : new Date(new java.util.Date().getTime());
        Element zglv = new Element("ZGLV");
        zglv.addContent(new Element("VERSION").setText(aVersion));
        zglv.addContent(new Element("DATA").setText(dateToString(aDocumentDate)));
        zglv.addContent(new Element("FILENAME").setText(aFilename));
        zglv.addContent(new Element("SD_Z").setText(count + ""));
        root.addContent(zglv);

        Element schet = new Element("SCHET");
        schet.addContent(new Element("CODE").setText("1"));
        schet.addContent(new Element("CODE_MO").setText(aLpu));
        schet.addContent(new Element("YEAR").setText(dateToString(aDocumentDate, "yyyy")));
        schet.addContent(new Element("MONTH").setText(dateToString(aDocumentDate, "M")));
        schet.addContent(new Element("NSCHET").setText(aBillNumber));
        schet.addContent(new Element("DSCHET").setText(dateToString(aBillDate)));
        schet.addContent(new Element("SUMMAV").setText(aTotalSum + ""));
        root.addContent(schet);

    }

    /** Проверяем, является ли объект NULL либо пустой строкой */
    private boolean isNotNull(Object aField) {
        if (aField == null) return false;
        if (aField instanceof String) {
            String ss = (String) aField;
            return  !ss.trim().equals("");
        } else if (aField instanceof Boolean) {
            return (Boolean) aField;
        } else if (aField instanceof Long) {
            return ((Long) aField) > 0L;
        } else if (aField instanceof Date) {
            return true;
        } else if (aField instanceof BigDecimal) {
            return ((BigDecimal) aField).compareTo(new BigDecimal(0))==1;
        } else if (aField instanceof BaseEntity) {
            return true;
        }else {
            throw new IllegalStateException("Нет преобразования для объекта " + aField);
        }
    }

    /**  Создаем MP файл с данными по стационару/поликлинике */
    public String makeMPFIle(Long aEntryListId, String aType, String aBillNumber, Date aBillDate, Long aEntryId, Boolean calcAllListEntry, long aMonitorId, String aVersion, String aFileType) {
        LOG.info("Формирование файла версии "+aVersion);
        IMonitor monitor = theMonitorService.startMonitor(aMonitorId,"Формирование xml файла. Размер: ",999);
        try {
            if (isCheckIsRunning) {
                LOG.warn("Формирование чего-то уже запущено, выходим_ALREADY_RAN");
                // return "Формирование чего-то уже запущено, выходим";
            }
            Date periodDate;
            isCheckIsRunning = true;
            String packetDateAdd;
            String cntNumber = null;
            boolean needCreateArchive = false;
            E2Entry entry=null;
            String lpuOmcCode ;
            if (aEntryListId != null) {
                needCreateArchive = true;
                E2ListEntry listEntry = theManager.find(E2ListEntry.class, aEntryListId);
                periodDate = listEntry.getFinishDate();
                if (!isNotNull(aBillDate) || !isNotNull(aBillNumber)) {
                    monitor.finish("Необходимо указать номер и дату счета!");
                    return "";
                }
                if (listEntry.getCheckDate()==null || listEntry.getCheckTime()==null) {
                    monitor.finish("Олег, необходимо выполнить проверку перед формированием пакета");
                    return "";

                }
                lpuOmcCode = listEntry.getLpuOmcCode();
            } else { //Сделано для теста.
                entry = theManager.find(E2Entry.class, aEntryId);
                periodDate = entry.getFinishDate();
                aBillDate = new Date(0L);
                aBillNumber = "TEST";
                cntNumber = "00";
                aEntryListId=entry.getListEntry().getId();
                lpuOmcCode = entry.getListEntry()!=null ? entry.getListEntry().getLpuOmcCode(): "1";

            }
            packetDateAdd = dateToString(periodDate, "yyMM");
            String packetType;
           if (aFileType!=null) {
               packetType = aFileType;
           } else {
               switch (aType) {
                   case HOSPITALTYPE:
                   case POLYCLINICTYPE:
                   case HOSPITALPEREVODTYPE:
                   case KDPTYPE:
                       packetType = "Z";
                       break;
                   case VMPTYPE:
                       packetType = "T";
                       break;
                   case EXTDISPTYPE:
                       //Пока сделаем заглушку
                       packetType = "DV";
                       break;
                   default:
                       throw new IllegalStateException("Неизвестный тип счета: " + aType);
               }
           }

            java.util.Date startStartDate = new java.util.Date();
            String regNumber = getExpertConfigValue("LPU_REG_NUMBER", "300001");
            boolean isExport263 = "1".equals(getExpertConfigValue("EXPORT_263", "1"));
            String fileName = "M" + regNumber + "T30_" + packetDateAdd; // M300001T30_171227
            SequenceHelper sequenceHelper = SequenceHelper.getInstance();
            if (cntNumber == null) {
                cntNumber = sequenceHelper.startUseNextValueNoCheck(packetType + "#" + fileName, "", theManager);
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
            String selectSqlAdd ,groupSqlAdd;
            boolean isHosp=false;
            boolean isPolic=false;
            if (aType.equals(HOSPITALTYPE) || aType.equals(HOSPITALPEREVODTYPE) || aType.equals(VMPTYPE)) {
                selectSqlAdd =" list(''||e.id) as ids, e.id, count(distinct e.id) as cnt";//Ищем все СЛО *список ИД, ИД госпитализации,кол-во СЛО
             //   selectSqlAdd =" list(''||e.id) as ids, e.externalparentid, count(distinct e.id) as cnt";//Ищем все СЛО *список ИД, ИД госпитализации,кол-во СЛО //1 Z_SL = 1SL
                groupSqlAdd= "e.id";
             //   groupSqlAdd= "e.externalparentid"; //1 Z_SL = 1SL
                isHosp=true;
            } else if (aType.equals(POLYCLINICTYPE) || aType.equals(KDPTYPE)){
                if ("3.1.1".equals(aVersion)) {
                    selectSqlAdd =" list(''||e.id) as ids, e.id, count(distinct e.id) as cnt";//Ищем все комплексные случаи
                    groupSqlAdd="e.id";
                } else {
                    selectSqlAdd =" list(''||child.id) as ids, e.id, count(distinct child.id) as cnt";//Ищем все комплексные случаи
                    groupSqlAdd="e.id";
                }
                isPolic=true;
            } else { //ДД (не реализовано)
                selectSqlAdd ="list(''||e.id) as ids, e.id, count(distinct e.id) as cnt";//Ищем все комплексные случаи
                groupSqlAdd="e.id";
            }
            String sql;
            if (aEntryId == null) {
                sql = "select "+selectSqlAdd+" from E2Entry e" +
                        " left join e2entry child on child.parentEntry_id=e.id and (child.isDeleted is null or child.isDeleted='0')" +
                        " where " + (calcAllListEntry ? "" : "e.listEntry_id=" + aEntryListId + " and")  +
                        "  e.billNumber=:billNumber and e.billDate=:billDate " +
                        " and (e.isDeleted is null or e.isDeleted='0') " +
                        " and e.serviceStream!='COMPLEXCASE'" +
                        " and (e.doNotSend is null or e.doNotSend='0') group by "+groupSqlAdd;
                LOG.info("sql="+sql);
                records = theManager.createNativeQuery(sql)
                        .setParameter("billNumber", aBillNumber).setParameter("billDate", aBillDate).getResultList();
            } else if (entry!=null) {
                sql = "select "+selectSqlAdd+" from E2Entry e" +
                        " left join e2entry child on child.parentEntry_id=e.id and (child.isDeleted is null or child.isDeleted='0')" +
                        " where e.listEntry_id=" + aEntryListId  +
                        " and (e.isDeleted is null or e.isDeleted='0') " +
                        " and e.serviceStream!='COMPLEXCASE'" +
                        " and e.externalparentid="+entry.getExternalParentId()+
                        " and (e.doNotSend is null or e.doNotSend='0') group by "+groupSqlAdd;
                LOG.info("sql="+sql);
                records = theManager.createNativeQuery(sql).getResultList();
            } else {
                records = new ArrayList<>();
            }
            LOG.info("found " + records.size() + " records");

            monitor.advice(1);

            int i = 0;
            /*Вот тут - 1 строка - список записей по 1 госпитализация */
            for (Object[] sluch : records) {
                int cntSlo = Integer.parseInt(sluch[2].toString());
                String sluchId=sluch[1].toString().trim();
                String sls = sluch[0].toString().trim();
                if (isHosp) {
                    if (cntSlo > 1) {
                        entry = calculateHospitalEntry(Long.valueOf(sluchId), sls);
                    } else {
                        entry = theManager.find(E2Entry.class,Long.valueOf(sls));
                    }
                } else {
                    entry = theManager.find(E2Entry.class,Long.valueOf(sluchId));
                    if (cntSlo==0) {
                        sls=sluchId;
                    }
                }
                i++;
                if (i % 100 == 0 && isMonitorCancel(monitor,"Сформировано " + i + " записей в счете")) {
                    return  "Прерванно пользователем";
                }
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
                if (null == entry.getIDSP()) {
                    err.append("НЕ РАСЧИТАН СПОСОБ ОПЛАТЫ МЕД. ПОМОЩИ;");
                    isError = true;
                }
                if (!isNotNull(entry.getMedPolicyType())) {
                    err.append("НЕ УКАЗАН ВИД ПОЛИСА;");
                    isError = true;
                }
                if (!isNotNull(entry.getMedPolicyNumber())) {
                    err.append("НЕ УКАЗАН НОМЕР ПОЛИСА;");
                    isError = true;
                }
                if (isPolic && !isNotNull(entry.getMainMkb())) {
                    err.append("НЕ УКАЗАН ОСНОВНОЙ ДИАГНОЗ");
                    isError = true;
                }
                if (!isNotNull(entry.getHistoryNumber())) {
                    err.append("НЕ ЗАПОЛНЕН НОМЕР ИСТОРИИ БОЛЕЗНИ");
                    isError = true;
                }
                if (!isNotNull(entry.getVidSluch())) {
                    err.append("НЕ ЗАПОЛНЕН ВИД СЛУЧАЯ");
                    isError = true;
                }
                if (!isNotNull(entry.getDoctorSnils())) {
                    err.append("НЕ УКАЗАН СНИЛС ВРАЧА");
                    isError = true;
                }
                if (isError) {
                    E2EntryError error = new E2EntryError(entry, "NO_FOND_FIELDS:" + err.toString());
                    theManager.persist(error);
                    LOG.error("Запись с ИД " + entry.getId() + " не будет выгружена в xml: "+err);
                    continue;
                }

                Long personId = entry.getExternalPatientId();
                Element z ;
                switch (aVersion) { //При появлении новых форматов файла - добавляем сюда
                    case "3.2":
                        z= createSlElements2020(entry, sls,cnt+1, isExport263, regNumber, lpuOmcCode, aFileType);
                        break;
                    case "3.1.1":
                        z= createSlElements(entry, sls,cnt+1, isExport263, regNumber, lpuOmcCode);
                        break;
                    default:
                        LOG.error("Неизвестный формат пакета: "+aVersion);
                        throw new IllegalStateException("Неизвестный формат пакета: "+aVersion);
                }

                if (z!=null) {
                    cnt++;
                    if (!patientIdsList.contains(personId)) { //Если нет пациента в Л файле - добавляем.
                        perss.add(createPERSElement(entry));
                        patientIdsList.add(personId);
                    }
                    zaps.add(z);
                    totalSum = totalSum.add(entry.getCost());
                } else {
                    LOG.error("Не удалось сформировать запись по случаю с ИД "+entry.getId());
                }
            }
            LOG.info("ok, we made all, let's make files");
            monitor.setText("Формирование файла завершено, сохраняем архив");
          //  if (aVersion.equals("3.1.1")) aVersion="3.1";
            makeHTitle(hRoot, periodDate, "H" + fileName, cnt, aBillNumber, aBillDate, totalSum,aVersion.equals("3.1.1")?"3.1":aVersion, regNumber);
            E2Bill bill =theManager.find(E2Bill.class, theExpertService.getBillIdByDateAndNumber(aBillNumber, dateToString(aBillDate, "dd.MM.yyyy")));
            if (bill != null) {
                bill.setStatus(getActualVocBySqlString(VocE2BillStatus.class, "select id from VocE2BillStatus where code='SENT'"));
                bill.setSum(totalSum);
                theManager.persist(bill);
            }
            makeLTitle(lRoot, periodDate, "L" + fileName,aVersion.equals("3.1")?"2.1":"3.1"); //гавнокод, спасибо фонду
            lRoot.addContent(perss);
            hRoot.addContent(zaps);
            String archiveName = packetType + fileName + ".MP";
            createXmlFile(hRoot, "H" + fileName);
            createXmlFile(lRoot, "L" + fileName);
            //   LOG.info("deb14");
            LOG.info("Время формирования файла (минут) = " + (System.currentTimeMillis() - startStartDate.getTime()) / 60000);
            monitor.setText("Завершено. Время формирования файла (минут) = " + (System.currentTimeMillis() - startStartDate.getTime()) / 60000);

            if (needCreateArchive) {
                archiveName = createArchive(archiveName, new String[]{"H" + fileName + ".xml", "L" + fileName + ".xml"});
                E2ExportPacketJournal journal = new E2ExportPacketJournal(aBillNumber, aBillDate, "/rtf/expert2xml/" + archiveName);
                theManager.persist(journal);
            }
            LOG.info("ALL SEEMS GOOD!");

            isCheckIsRunning = false;
            monitor.setText("/rtf/expert2xml/" + archiveName);
            monitor.finish("/rtf/expert2xml/" + archiveName);
            return "/rtf/expert2xml/" + archiveName;
        } catch (Exception err) {
            monitor.error("Произошла ошибка: "+err.getMessage(),err);
         //   monitor.finish("Произошла ошибка: "+err.getMessage());
            err.printStackTrace();
            LOG.error("ERR = ",err);
            return "ERR";
        }
    }

    /*Формируем случай с госпитализацией (не сохраняя в БД)*/
    private E2Entry calculateHospitalEntry(Long aHospitalMedcaseId, String aIds) {
        E2Entry hospital =null;//=theExpertService.cloneEntity(aEntry);
        List<E2Entry> slo = theManager.createQuery("from E2Entry where id in ("+aIds+") and externalParentId=:parent and serviceStream!='COMPLEXCASE' " +
                "and ((isDeleted is null or isDeleted='0') and (doNotSend is null or doNotSend='0')) order by startDate").setParameter("parent",aHospitalMedcaseId)
                .getResultList();
        if (!slo.isEmpty()) {
            hospital = cloneEntity(slo.get(0),false);
            E2Entry lastEntry = slo.get(slo.size()-1);
            hospital.setFondResult(lastEntry.getFondResult());
            hospital.setFondIshod(lastEntry.getFondIshod());
         //
            long bedDays = AgeUtil.calculateDays(hospital.getHospitalStartDate(), hospital.getHospitalFinishDate()!=null?hospital.getHospitalFinishDate():lastEntry.getFinishDate());
            long calendarDays = bedDays>0?bedDays+1:1;
            if (hospital.getEntryType().equals(HOSPITALTYPE)&&isNotNull(hospital.getBedSubType())&&hospital.getBedSubType().equals("2")) {
                bedDays++;
            }
            hospital.setBedDays(bedDays);
            hospital.setCalendarDays(calendarDays);
            BigDecimal cost = new BigDecimal(0);
            for (E2Entry e:slo) {
                if (e.getCost()!=null){
                    cost=cost.add(e.getCost());
                } else {
                    LOG.warn("entry ID ="+e.getId()+" NO COST");
                }

            }
            if (hospital.getId()==0L) {hospital.setId(lastEntry.getId());}
            hospital.setCost(cost);
        }
        return hospital;
    }
    private E2Entry cloneEntity(E2Entry aSourceObject, boolean needPersist) {
        try {
            Method[] methodList = aSourceObject.getClass().getMethods();
            E2Entry newEntity = new E2Entry();
            //Object newEntity = aClass.newInstance();
            for (Method setterMethod: methodList) {
                String methodName =setterMethod.getName();
                if (methodName.startsWith("set")) {
                    if (methodName.equals("setId")) {continue;}
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
           LOG.error(e);
            return null;
        }

    }
    private String getWorkDir() {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    }

    /** Создаем файл из документа */
    public void createXmlFile(Element aElement, String aFileName) {
        if (aElement == null) {
            LOG.error("no data for create file " + aFileName);
            return;
        }
        String outputFile = getWorkDir() + "/expert2xml/" + aFileName+".xml";

        try (OutputStreamWriter fwrt = new OutputStreamWriter(new FileOutputStream(outputFile), Charset.forName("windows-1251"))) {
            XMLOutputter outputter = new XMLOutputter();
            Document pat = new Document(aElement);
            outputter.setFormat(org.jdom.output.Format.getPrettyFormat().setEncoding("windows-1251"));
            outputter.output(pat, fwrt);
        } catch (Exception ex) {
            LOG.error("Someshing happened strange!!!",ex);
        }
    }

    private String createArchive(String archiveName, String[] aFileNames) {
        String workDir = getWorkDir()+"/expert2xml/";
        StringBuilder sb = new StringBuilder();
        String exec = "zip -r -j -9 ";
        sb.append(workDir).append(archiveName).append(" ");

        for (String s : aFileNames) {
            sb.append(workDir).append(s).append(" ");
        }
        try {
            LOG.info("START EXECUTING = " + sb);
            try {
                Runtime.getRuntime().exec("zip -d " + workDir + archiveName + " *");//Удаляем архив перед созданием;
            } catch (Exception e) {}//Не удалось очистить архив, т.к. его нету. Ничего страшного)

            Runtime.getRuntime().exec(exec+sb.toString());//arraCmd);
        } catch (IOException e) {
          // LOG.error(e);
            LOG.warn("it seems Windows");
            exec="\"C:\\Program Files\\7-Zip\\7z.exe\" a -tZIP ";
            try {
                Runtime.getRuntime().exec(exec+sb.toString());//arraCmd);
            } catch (IOException e1) {
               LOG.error(e1);
            }
        }
        return archiveName;
    }

    /** Получить сущность по коду (в основном для справочников) */
    private <T> T getEntityByCode(String aCode, Class aClass, Boolean needCreate) {
        List<T> list = theManager.createQuery("from " + aClass.getName() + " where code=:code").setParameter("code", aCode).getResultList();
        T ret = !list.isEmpty() ? list.get(0) : null;
        if (list.isEmpty() && needCreate) {
            try {
                ret = (T) aClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
               LOG.error(e);
            }
            if (ret instanceof VocBaseEntity) { //Проверить
                try {
                    Method setCodeMethod = ret.getClass().getMethod("setCode",String.class);
                    setCodeMethod.invoke(ret,aCode);
                } catch (Exception e) {
                   LOG.error(e);
                }
            }
            theManager.persist(ret);
        }
        return ret;
    }

    /** Получение всех диагнозов из списка по коду регистрации */
    private List<String> findDiagnosisCodes(List<EntryDiagnosis> aList, String aRegType, String aPriority) {
        //Тип регистрации - направительный(2), выписной(3), клинический(4)
        // Приорите - основной(1), сопутствующий(3)
        if (aList.isEmpty()) {return new ArrayList<>();}
        List<String> diagnosisList = new ArrayList<>();
        if (aRegType==null&&aPriority==null) {return diagnosisList;}
        for (EntryDiagnosis diagnosis : aList) {
            if ( //проверить!
                    (aRegType==null||(diagnosis.getRegistrationType()!=null&&aRegType.contains(diagnosis.getRegistrationType().getCode())))
                            &&(aPriority==null||diagnosis.getPriority()!=null&&aPriority.contains(diagnosis.getPriority().getCode()))
                    ) {
                diagnosisList.add(diagnosis.getMkb().getCode());
            }
        }
        return diagnosisList;
    }

    private String getExpertConfigValue(String aParameterName, String aDefaultValue) {
        List<Object> ret = theManager.createNativeQuery("select value from Expert2Config where code=:code AND (isDeleted is null or isDeleted='0')").setParameter("code", aParameterName).getResultList();
        if (ret.isEmpty()) {
            LOG.warn("Не удалось найти настройку с ключем "+aParameterName+", возвращаю значение по умолчанию");
            return aDefaultValue;
        }
        return ret.get(0).toString();
    }

    private <T> T getActualVocBySqlString(Class aClass, String aSql) {
        List<Object> list = theManager.createNativeQuery(aSql).getResultList();
        if (list.isEmpty()) {
            LOG.error("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием " + aSql);
            return null;
            //throw new IllegalStateException("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием " + aSql);
        }
        if (list.size() > 1) {
            LOG.error(" с условием " + aSql + " найдено несколько действующих значений справочника " + aClass.getCanonicalName());
            return null;
            //throw new IllegalStateException(" с условием " + aSql + " найдено несколько действующих значений справочника " + aClass.getCanonicalName());
        }
        return (T) theManager.find(aClass, Long.valueOf(list.get(0).toString()));

    }

    private String getJbossConfigValue(String aConfigName, String aDefaultValue) {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get(aConfigName, aDefaultValue);
    }

    private void setSluchDiagnosis(Element aElement, E2Entry aEntry, String aVersion) {
        /** Нюансы
         * 30.01.2018 Если есть диабет - указываем его как главный сопутствующий
         */
        if (aVersion==null) {aVersion="3.1";}
        List<EntryDiagnosis> list = aEntry.getDiagnosis();
        String mainDiagnosisSqlAdd ;
        List<EntryDiagnosis> mainDiagnosis = null;
        String entryType =aEntry.getEntryType();
        if (entryType.equals(POLYCLINICTYPE) || entryType.equals(KDPTYPE)) {
            mainDiagnosisSqlAdd ="priority.code='1'";
        } else {
            mainDiagnosisSqlAdd =" registrationType.code='3' and priority.code='1'";
            mainDiagnosis = theManager.createQuery(" from EntryDiagnosis where entry_id=:id and "+mainDiagnosisSqlAdd).setParameter("id",aEntry.getId()).getResultList(); //выписные основные диагнозы
            if (mainDiagnosis.isEmpty()) {
                mainDiagnosisSqlAdd =" registrationType.code='4' and priority.code='1'";
            }
        }
        if (mainDiagnosis==null || mainDiagnosis.isEmpty()) {
            mainDiagnosis = theManager.createQuery(" from EntryDiagnosis where entry_id=:id and "+mainDiagnosisSqlAdd).setParameter("id",aEntry.getId()).getResultList(); //клинические основные диагнозы
        }

        List<String> otherDiagnosis = findDiagnosisCodes(list,null,"3"); // Сопутствующие
        List<String> napravitDiagnosis = findDiagnosisCodes(list,"1,2","3"); // Направительные
        if (!napravitDiagnosis.isEmpty()) {
            aElement.addContent(new Element("DS0").setText(napravitDiagnosis.get(0)));
        }

        if (!mainDiagnosis.isEmpty()) {
            boolean isExtDisp = aEntry.getEntryType().equals(EXTDISPTYPE);
            EntryDiagnosis ds = mainDiagnosis.get(0);
            String mainMkb = ds.getMkb().getCode();
            aElement.addContent(new Element("DS1").setText(mainMkb));

            String ds1Pr = "0";
            String illnesPrimary =ds.getIllnessPrimary();
            if (aVersion.equals("3.1")) {
                if (illnesPrimary!=null) {
                    if (illnesPrimary.equals("3")){ds1Pr="1";}
                    else if (illnesPrimary.equals("4")||illnesPrimary.equals("5")) {ds1Pr="2";}
                }
            } else if (aVersion.equals("3.1.1")) {
                if (isExtDisp) { //Если ДД и галочка выявлено впервые - ставим 1.
                    ds1Pr="1";
                }
            }

            aElement.addContent(new Element("DS1_PR").setText(ds1Pr));
            if (isNotNull(ds.getDopMkb())) {
                otherDiagnosis.add(0,ds.getDopMkb());
            }
            if (!otherDiagnosis.isEmpty()) {
                for (String d: otherDiagnosis) {
                    if (d.startsWith("E")) { //Нашли диабет поставили его на первое место!
                        otherDiagnosis.remove(d);
                        otherDiagnosis.add(isNotNull(ds.getDopMkb())?1:0,d);
                        break;
                    }
                }
                add(aElement,"DS2",otherDiagnosis.get(0));
                if (otherDiagnosis.size()>1) {
                    for (int i=1;i<otherDiagnosis.size() && i<4;i++) {
                        addIfNotNull(aElement,"DS2_"+i,otherDiagnosis.get(i));
                    }
                }
            }
            if (aVersion.equals("3.1.1") && !isExtDisp &&!mainMkb.startsWith("Z")) { //C_ZAB * Характер заболевания, если USL_OK!=4 || DS1!=Z*
                VocE2FondV027 vip = ds.getVocIllnessPrimary();
                if (vip==null) {
                    theManager.persist(new E2EntryError(aEntry,"NO_HARAKTER"));
                    aElement = null;
                    return ;
                }
                add(aElement,"C_ZAB",vip.getCode());
            }
        }
    }

    /**Выводим сообщение в монитор. Возвращаем - отменен ли монитор*/
    public boolean isMonitorCancel(IMonitor aMonitor, String aMonitorText) {
        aMonitor.setText(aMonitorText);
        LOG.info(aMonitorText);
        if (aMonitor.isCancelled()) {
            aMonitor.setText("Проверка прервана пользователем");
            return true;
        }
        return false;
    }

    private @PersistenceContext
    EntityManager theManager;
    private @EJB
    ILocalMonitorService theMonitorService;

    private @EJB IExpert2Service theExpertService;
}
