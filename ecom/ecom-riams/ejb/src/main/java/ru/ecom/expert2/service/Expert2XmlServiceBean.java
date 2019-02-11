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
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.date.AgeUtil;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
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
    private static final Logger log = Logger.getLogger(Expert2XmlServiceBean.class);
    private static final String HOSPITALTYPE="HOSPITAL";
    private static final String HOSPITALPEREVODTYPE="HOSPITALPEREVOD";
    private static final String POLYCLINICTYPE="POLYCLINIC";
    private static final String VMPTYPE="VMP";
    private static final String EXTDISPTYPE="EXTDISP";
    private static final String POLYCLINICKDOTYPE="POLYCLINICKDO";
    private static final String KDPTYPE="POL_KDP";
    private <T> T get(Long aId) {
        //Class aClass = Class<T>().new
        //theManager.find(T.class,aId);
        return null;
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
        Boolean isKinsman = isNotNull(aEntry.getKinsmanLastname());
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
            pers=addIfNotNull(pers,"OT",aEntry.getMiddlename());
            pers.addContent(new Element("W").setText(aEntry.getSex()));
            pers.addContent(new Element("DR").setText(dateToString(aEntry.getBirthDate())));
        }
        //MR
      //  log.info("Добавляем данные о пациенте OTHER_DATA " + aEntry.getExternalPatientId() + " в L файле");
        pers=addIfNotNull(pers,"DOCTYPE",aEntry.getPassportType());
        pers=addIfNotNull(pers,"DOCSER",aEntry.getPassportSeries());
        pers=addIfNotNull(pers,"DOCNUM",aEntry.getPassportNumber());
        pers=addIfNotNull(pers,"SNILS",isKinsman ? aEntry.getKinsmanSnils() : aEntry.getPatientSnils());
        pers=addIfNotNull(pers,"ENP",aEntry.getCommonNumber());
     //   pers=addIfNotNull(pers,"OKATOG",aEntry.getOkatoReg()); //код места жительства
     //   pers=addIfNotNull(pers,"OKATOP",aEntry.getOkatoReal()); //код места пребывания
        return pers;
    }

    /** Добавляем в переданный элемент новый элемент со значением, в случае, если значение не пустое */
    private Element addIfNotNull(Element aElement, String aElementName, Object  aValue) {
        if (isNotNull(aValue)) {
            if (aValue instanceof Boolean) {
                aValue= ((Boolean) aValue)?"1":"0";
            }
            aElement.addContent(new Element(aElementName).setText(aValue.toString()));
        }
        return aElement;
    }

    /** Расчет строки с признаком новорожденного */
    private String makeNovorString(E2Entry aEntry) {
        String ret = "0";
        if (isNotNull(aEntry.getKinsmanLastname())) {
            SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
            ret = aEntry.getSex() + ""+format.format(aEntry.getBirthDate())+"" + (isNotNull(aEntry.getBirthOrder())?aEntry.getBirthOrder():1); //TODO = порядковый номер ребенка
        }
        return ret;

    }

    /**Формирование пакета в версии 3.1 (старый формат, до ноября)*/
    @Deprecated
    private Element createZSlOld(E2Entry aEntry, boolean isPoliclinic, int slCnt, int zslIdCase, boolean isNedonosh) {
        //    String startDate = dateToString(aEntry.getHospitalStartDate()), finishDate = dateToString(aEntry.getHospitalFinishDate()!=null?aEntry.getHospitalFinishDate():aEntry.getFinishDate());

        Element z = new Element("Z_SL");
        z.addContent(new Element("IDCASE").setText(zslIdCase+""));
        // z.addContent(new Element("IDCASE").setText(aEntry.getExternalParentId()+""));
        z.addContent(new Element("VID_SLUCH").setText(aEntry.getVidSluch().getCode()));
        z.addContent(new Element("USL_OK").setText(aEntry.getMedHelpUsl().getCode())); //дневной-круглосуточный-поликлиника
        z.addContent(new Element("VIDPOM").setText(aEntry.getMedHelpKind().getCode()));
        z.addContent(new Element("FOR_POM").setText(isNotNull(aEntry.getIsEmergency()) ? (isPoliclinic?"2":"1") : "3")); //форма помощи V014
        z.addContent(new Element("LPU").setText("1")); //ЛПУ лечения //TODO = сделать высчитываемым
        z.addContent(new Element("VBR").setText(isNotNull(aEntry.getIsMobilePolyclinic())?"1":"0")); //Признак мобильной бригады

        // if (isNedonosh) z.addContent(new Element("VNOV_M").setText(aEntry.getNewbornWeight()+""));
        z.addContent(new Element("OS_SLUCH").setText(Expert2FondUtil.calculateFondOsSluch(aEntry))); // Особый случай //TODO!!! ALL SLO_SSS
        if (!isPoliclinic)z.addContent(new Element("VB_P").setText(slCnt>1?"1":"0")); // Признак внутрибольничного перевода
        z.addContent(new Element("SL_TEMPLATE")); // Список случаев
        if (!isPoliclinic)  z.addContent(new Element("KD_Z").addContent(aEntry.getBedDays()+"")); // Продолжительность госпитализации
        z.addContent(new Element("DATE_Z_1").setText("_")); //Дата начала случая
        z.addContent(new Element("DATE_Z_2").setText("_")); //Дата окончания случая
        z.addContent(new Element("RSLT").setText(aEntry.getFondResult().getCode())); // Результат обращения
        //z.addContent(new Element("RSLT_D")); // Результат диспансеризации
        z.addContent(new Element("ISHOD").setText(aEntry.getFondIshod().getCode())); // Исход случая.
        //z.addContent(new Element("P_OTK")); // Отказ от ДД
        //z.addContent(new Element("SGROUP")); // Социальная группа в ДД
        z.addContent(new Element("IDSP").setText(aEntry.getIDSP().getCode())); // Способ оплаты медицинской помощи (V010)
        z=add(z,"SUMV",aEntry.getCost()); // Сумма, выставленная к оплате =SUMV7+ SUMV8
        return z;
    }

    /** Создааем информацию по случаю мед. обслуживания (пока только стационар) для версии от июля 2018 до ноября 2018 года (3.1)
     * aEntry - случай госпитализации
     * entriesList - строка с ИД СЛО
     * */
    @Deprecated
    private Element createSlElementsOld(E2Entry aEntry, String entriesString, int cnt) {
        String version="3.1";
        try {
            String entryType = aEntry.getEntryType();
            boolean isHosp = false, isVmp = false, isPoliclinic = false, isExtDisp = false, isPoliclinicKdo=false;
            boolean isNedonosh = false;
            if (entryType.equals(HOSPITALTYPE)) {isHosp=true; isNedonosh=aEntry.getKsg()!=null && ",107,108,".contains(","+aEntry.getKsg().getCode()+",");} //Малая масса тела
            else if (entryType.equals(VMPTYPE)) {isVmp=true; isNedonosh=aEntry.getIsChild();}
            else if (entryType.equals(POLYCLINICTYPE)) {isPoliclinic=true;}
            else if (entryType.equals(EXTDISPTYPE)) {isExtDisp=true;}
            else if (entryType.equals(POLYCLINICKDOTYPE)) {isPoliclinic=true;isPoliclinicKdo=true;}
            else {throw new IllegalStateException("UNKNOWN ENTRYTYPE"+entryType);}
            Element zap = new Element("ZAP");
            zap.addContent(new Element("N_ZAP").setText(aEntry.getId() + ""));
            zap.addContent(new Element("PR_NOV").setText(isNotNull(aEntry.getPRNOV())?"1":"0"));
            Element pat = new Element("PACIENT");
            pat.addContent(new Element("ID_PAC").setText(aEntry.getExternalPatientId() + ""))
                    .addContent(new Element("VPOLIS").setText(aEntry.getMedPolicyType()));
            if (aEntry.getMedPolicyType() != null && !aEntry.getMedPolicyType().equals("3")) {
                pat = addIfNotNull(pat, "SPOLIS", aEntry.getMedPolicySeries()); //Если полис не нового образца - добавляем серию полиса
                pat.addContent(new Element("NPOLIS").setText(aEntry.getMedPolicyNumber()));
            } else {
                pat.addContent(new Element("NPOLIS").setText(aEntry.getCommonNumber()));
            }

            if (isNotNull(aEntry.getInsuranceCompanyCode())) {
                pat.addContent(new Element("SMO").setText(aEntry.getInsuranceCompanyCode()));
            }  else {
                pat.addContent(new Element("SMO_OGRN").setText(aEntry.getInsuranceCompanyOgrn()));
                //   pat.addContent(new Element("SMO_OK").setText(aEntry.getInsuranceCompanyTerritory()));
                pat.addContent(new Element("SMO_NAM").setText(aEntry.getInsuranceCompanyName()));
            }
            String novorString =makeNovorString(aEntry);
            pat.addContent(new Element("NOVOR").setText(novorString));
            if (isNedonosh){ // && novorString.equals("0")) { //11.10.2018 по согласованию с фондом
                pat.addContent(new Element("VNOV_D").setText(aEntry.getNewbornWeight()+""));
            }

            zap.addContent(pat); //Добавили данные по пациенту


            List<E2Entry> children=null;
            String isChild = isNotNull(aEntry.getIsChild())?"1":"0";

            //  edCol="1"; //06-08-2018 Ед кол больше не равно 1 !

            String[] slIds = entriesString.split(",");
            Element zSl = createZSlOld(aEntry,isPoliclinic,slIds.length,cnt, isNedonosh && !novorString.equals("0"));
            int indSl = zSl.indexOf(zSl.getChild("SL_TEMPLATE"));
            Date startHospitalDate = null, finishHospitalDate=null;
            int kdz=0;

            /** Вот тут создаем Sl*/
            boolean isDiagnosisFill=false, isLongCase=slIds.length>1;
            boolean isKdoServicesSet = false;
            for (String slId:slIds) {

                Element sl = new Element("SL");
                E2Entry currentEntry = theManager.find(E2Entry.class,Long.valueOf(slId.trim()));
                String edCol;
                if (isPoliclinicKdo) {
                    edCol="1";
                    children = theManager.createQuery("from E2Entry where parentEntry_id=:id and (isDeleted is null or isDeleted='0') and (doNotSend is null or DoNotSend='0')").setParameter("id",currentEntry.getId()).getResultList();
                } else if (isPoliclinic) {
                    children = theManager.createQuery("from E2Entry where parentEntry_id=:id and (isDeleted is null or isDeleted='0') and (doNotSend is null or DoNotSend='0')").setParameter("id",currentEntry.getId()).getResultList();
                    edCol=""+(!children.isEmpty()?children.size():1);
                } else {
                    kdz+=currentEntry.getBedDays().intValue();
                    edCol= currentEntry.getBedDays()+ ""; // Количество единиц оплаты мед. помощи
                }

                Boolean isCancer = currentEntry.getIsCancer()!=null?currentEntry.getIsCancer():false;
                if (isCancer && currentEntry.getMedHelpProfile().getCode().equals("12")) {isCancer=false;} //Убрать колхоз
                if (isCancer&& currentEntry.getVisitPurpose()!=null&&currentEntry.getVisitPurpose().getCode().equals("1.3")) {isCancer=false;}
                E2CancerEntry cancerEntry = null;
                if (isCancer) {
                    try {
                        cancerEntry= (E2CancerEntry) theManager.createQuery("from E2CancerEntry where entry=:entry").setParameter("entry",currentEntry).getResultList().get(0);
                    } catch (Exception e) {}
                }

                VocE2MedHelpProfile profile = currentEntry.getMedHelpProfile();
                String profileK = profile.getProfileK();

                String startDate = dateToString(currentEntry.getStartDate()), finishDate = dateToString(currentEntry.getFinishDate());
                if (startHospitalDate==null || startHospitalDate.getTime()>currentEntry.getStartDate().getTime()) {startHospitalDate=currentEntry.getStartDate();}
                if (finishHospitalDate==null || finishHospitalDate.getTime()<currentEntry.getFinishDate().getTime()) {finishHospitalDate=currentEntry.getFinishDate();}
                sl=add(sl,"SL_ID",slId);
                sl=add(sl,"LPU_1","30000101");
                //PODR
                if (!isNotNull(currentEntry.getIsEmergency())) sl=addIfNotNull(sl,"NPR_MO",currentEntry.getDirectLpu()); //Направившее ЛПУ
                sl=addIfNotNull(sl,"NPR_DATE",currentEntry.getDirectDate()); //Дата направления на лечение ***
                sl=addIfNotNull(sl,"NPR_N",currentEntry.getTicket263Number()); // Номер направления на портале ФОМС
                sl=addIfNotNull(sl,"NPR_P",currentEntry.getPlanHospDate()); // Дата планируемой госпитализации
                //PRN_MO
                if (isVmp) {
                    if (currentEntry.getVMPTicketDate()==null
                            ||currentEntry.getVMPTicketNumber()==null
                            ||currentEntry.getVMPPlanHospDate()==null
                            ||currentEntry.getVMPKind()==null
                            ||currentEntry.getVMPMethod()==null) {
                        theManager.persist(new E2EntryError(currentEntry,"NO_FOND_FIELD: В случае ВМП не заполнены обязательные полея"));
                        return null;
                    }
                    sl=add(sl,"TAL_D",currentEntry.getVMPTicketDate()); // Дата выдачи талона ВМП
                    sl=add(sl,"TAL_N",currentEntry.getVMPTicketNumber()); // Номер выдачи ВМП
                    sl=add(sl,"TAL_P",dateToString(currentEntry.getVMPPlanHospDate())); // Дата планируемой госпитализации
                    sl=add(sl,"VID_HMP",currentEntry.getVMPKind()); // Вид ВМП
                    sl=add(sl,"METOD_HMP",currentEntry.getVMPMethod()); // Метод ВМП
                }
                //TELEMED
                if (isPoliclinic) {
                    sl=add(sl,"P_CEL",currentEntry.getVisitPurpose().getCode()); // Цель посещения
                }
                sl=add(sl,"DET",isChild); //Признак детского возраста
                sl=add(sl,"PROFIL",profileK); //Профиль коек/специальностей (V002_K)
                if (isHosp||isVmp){
                    if (profile.getProfileBed()==null) {
                        log.error("Нет профиля койки для профиля: "+profile.getProfileK());
                        theManager.persist(new E2EntryError(currentEntry,"NO_FOND_FIELD: Нет профиля койки"));
                        return null;
                    }
                    sl=add(sl,"PROFIL_K",profile.getProfileBed().getCode()); //Профиль коек/специальностей (V002_K)
                }
                sl=add(sl,"NHISTORY",currentEntry.getHistoryNumber()); //Номер истории болезни
                if (isHosp||isVmp) {
                    sl=add(sl,"P_PER",Expert2FondUtil.calculateFondP_PER(currentEntry)); //Признак перевода
                    sl=add(sl,"KD",currentEntry.getBedDays()); //Признак перевода
                }
                sl = setSluchDiagnosis(sl, currentEntry,version);
                if (sl==null) {
                    theManager.persist(new E2EntryError(currentEntry,"NO_MAIN_DIAGNOSIS"));
                    return null;
                }
                sl=add(sl,"DATE_1",startDate); //Дата начала случая
                sl=add(sl,"DATE_2",finishDate); //Дата окончания случая
                sl=add(sl,"NPL",isNotNull(currentEntry.getNotFullPaymentReason())?currentEntry.getNotFullPaymentReason():"0"); // Неполный объем //TODO
                if (isCancer ){
                    if (cancerEntry==null) {
                        log.error("Не найден раковый случай для записи с ИД"+currentEntry.getId());
                    } else if (!cancerEntry.getMaybeCancer()){
                        Element onkSl = new Element("ONK_SL");
                        onkSl=addIfNotNull(onkSl,"DS1_T",cancerEntry.getOccasion());
                        if (cancerEntry.getStage()== null
                                || cancerEntry.getTumor()== null
                                || cancerEntry.getNodus()== null
                                || cancerEntry.getMetastasis()== null
                        ) {
                            theManager.persist(new E2EntryError(currentEntry,"NO_CANCERINFO"));
                            return null;
                        }
                        onkSl=add(onkSl,"STAD",cancerEntry.getStage());
                        onkSl=add(onkSl,"ONK_T",cancerEntry.getTumor());
                        onkSl=add(onkSl,"ONK_N",cancerEntry.getNodus());
                        onkSl=add(onkSl,"ONK_M",cancerEntry.getMetastasis());
                        onkSl=addIfNotNull(onkSl,"MTSTZ",cancerEntry.getIsMetastasisFound());
                        List<E2CancerDiagnostic> directions= theManager.createQuery("from E2CancerDiagnostic where cancerEntry=:cancer").setParameter("cancer",cancerEntry).getResultList();
                        for (E2CancerDiagnostic direction: directions){
                            Element dir = new Element("B_DIAG");
                            dir=add(dir,"DIAG_TIP",direction.getType());
                            dir=add(dir,"DIAG_CODE",direction.getCode());
                            dir=add(dir,"DIAG_RSLT",direction.getResult());
                            onkSl.addContent(dir);
                        }
                        List<E2CancerRefusal> prots = theManager.createQuery("from E2CancerRefusal where cancerEntry=:cancer").setParameter("cancer",cancerEntry).getResultList();
                        for (E2CancerRefusal prot: prots){
                            Element pr = new Element("B_PROT");
                            pr=add(pr,"PROT",prot.getCode());
                            pr=add(pr,"D_PROT",prot.getDate());
                            onkSl.addContent(pr);
                        }
                        onkSl=addIfNotNull(onkSl,"SOD",cancerEntry.getSod());
                        sl.addContent(onkSl);
                    }

                }
                //REAB

                if (isHosp){//KSG_KGP
                    Element ksgKpg=new Element("KSG_KPG");
                    VocKsg ksg = currentEntry.getKsg();
                    ksgKpg=add(ksgKpg,"N_KSG",ksg.getCode());
                    ksgKpg=add(ksgKpg,"VER_KSG",ksg.getYear()!=null ? ksg.getYear().toString() : "2018");
                    ksgKpg=add(ksgKpg,"KSG_PG","0");
                    ksgKpg=add(ksgKpg,"KOEF_Z",ksg.getKZ());
                    ksgKpg=add(ksgKpg,"KOEF_UP",theExpertService.getActualKsgUprCoefficient(ksg,currentEntry.getFinishDate()));
                    ksgKpg=add(ksgKpg,"BZTSZ",currentEntry.getBaseTarif());
                    ksgKpg=add(ksgKpg,"KOEF_D","1"); //TODO
                    ksgKpg=add(ksgKpg,"KOEF_U","1"); //TODO
                    ksgKpg=addIfNotNull(ksgKpg,"DKK1",currentEntry.getDopKritKSG());
                    //DKK2
                    //
                    List<E2CoefficientPatientDifficultyEntryLink> difficultyEntryLinks = theManager.createQuery("from E2CoefficientPatientDifficultyEntryLink where entry=:entry")
                            .setParameter("entry",currentEntry).getResultList();
                    if (!difficultyEntryLinks.isEmpty()){
                        ksgKpg=add(ksgKpg,"SL_K","1");
                        ksgKpg=add(ksgKpg,"IT_SL",theExpertService.calculateResultDifficultyCoefficient(currentEntry));
                        for (E2CoefficientPatientDifficultyEntryLink link: difficultyEntryLinks){
                            Element slKoef = new Element("SL_KOEF");
                            slKoef=add(slKoef,"IDSL",link.getDifficulty().getCode());
                            slKoef=add(slKoef,"Z_SL",link.getValue()!=null?link.getValue():link.getDifficulty().getValue());
                            ksgKpg.addContent(slKoef);
                        }
                        //
                    } else {
                        ksgKpg=add(ksgKpg,"SL_K","0");
                    }
                    sl.addContent(ksgKpg);
                }
                String prvs ;
                String vers;
                vers="V021";
                prvs=currentEntry.getFondDoctorSpecV021()!=null?currentEntry.getFondDoctorSpecV021().getCode():
                        (profile.getMedSpecV021()!=null?profile.getMedSpecV021().getCode():
                                (currentEntry.getFondDoctorSpec().getMedSpecV021()!=null?currentEntry.getFondDoctorSpec().getMedSpecV021().getCode():"V015_"+currentEntry.getFondDoctorSpec().getCode()));
                sl=add(sl,"PRVS",prvs); //Специальность лечащего врача
                sl=add(sl,"VERS_SPEC",vers);
                sl=add(sl,"IDDOKT",currentEntry.getDoctorSnils()); // СНИЛС лечащего врача
                sl=add(sl,"ED_COL",edCol);
                if (isPoliclinicKdo) {
                    edCol="1";
                    if (isNotNull(currentEntry.getFondDoctorSpec().getIsKdoChief()) && !isKdoServicesSet) {
                        sl=add(sl,"TARIF",aEntry.getCost());
                        sl=add(sl,"SUM_M",aEntry.getCost());
                    } else {
                        sl=add(sl,"TARIF","0");
                        sl=add(sl,"SUM_M","0");
                    }
                }else  if (isPoliclinic && isLongCase) {
                    if (!isDiagnosisFill && sl.getChild("DS1")!=null &&!sl.getChild("DS1").getText().startsWith("Z")) {
                        isDiagnosisFill=true;
                        sl=add(sl,"TARIF",aEntry.getCost());
                        sl=add(sl,"SUM_M",aEntry.getCost());
                    } else {
                        sl=add(sl,"TARIF","0");
                        sl=add(sl,"SUM_M","0");
                    }
                }  else {
                    sl=add(sl,"TARIF",currentEntry.getCost());
                    sl=add(sl,"SUM_M",currentEntry.getCost());
                }
                //USL start
                int uslCnt = 0;
                if (currentEntry.getReanimationEntry()!=null) { //Реанимационная услуга
                    uslCnt++;
                    Element usl = new Element("USL");
                    usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                    usl.addContent(new Element("LPU_U").setText("300001"));
                    usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                    usl.addContent(new Element("PROFIL_U").setText(profileK));
                    usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                    usl.addContent(new Element("PRVS_U").setText(prvs));
                    usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                    usl.addContent(new Element("COD_DUSL_U").setText("B03.003.005"));
                    usl.addContent(new Element("KOL_USL").setText("1"));
                    usl.addContent(new Element("DATE_1_U").setText(startDate));
                    usl.addContent(new Element("DATE_2_U").setText(finishDate));
                    usl.addContent(new Element("SUMV_USL").setText("0"));
                    sl.addContent(usl);
                }
                //Информация об услугах
                if (isPoliclinic &&children!=null) { //Для поликлиники - кол-во визитов
                    for (E2Entry child: children) {
                        uslCnt++;
                        String uslDate = dateToString(child.getStartDate());
                        if (child.getMedHelpProfile()==null) {
                            theManager.persist(new E2EntryError(aEntry,"NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: "+child.getId()));
                            log.warn("NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: "+child.getId());
                            continue;
                        }
                        prvs = child.getFondDoctorSpecV021()!=null?currentEntry.getFondDoctorSpecV021().getCode():
                                (profile.getMedSpecV021()!=null?profile.getMedSpecV021().getCode():profile.getMedSpec().getMedSpecV021()!=null?profile.getMedSpec().getMedSpecV021().getCode():profile.getMedSpec().getCode()+"__V015!!!");
                        Element usl = new Element("USL");
                        usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                        usl.addContent(new Element("LPU_U").setText("300001"));
                        usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                        usl.addContent(new Element("PROFIL_U").setText(child.getMedHelpProfile().getProfileK()));
                        usl.addContent(new Element("IDDOKT_U").setText(child.getDoctorSnils()));
                        usl.addContent(new Element("PRVS_U").setText(prvs));
                        usl.addContent(new Element("DS_U").setText(isNotNull(child.getMainMkb())?child.getMainMkb():sl.getChildText("DS1")));
                        //P_OTK
                        if (isPoliclinicKdo &&isNotNull(child.getMainService())) usl.addContent(new Element("COD_DUSL_U").setText(child.getMainService()));
                        usl.addContent(new Element("KOL_USL").setText("1"));
                        usl.addContent(new Element("DATE_1_U").setText(uslDate));
                        usl.addContent(new Element("DATE_2_U").setText(uslDate));
                        usl.addContent(new Element("SUMV_USL").setText("0"));
                        sl.addContent(usl);
                    }
                    if (isPoliclinicKdo && isNotNull(currentEntry.getFondDoctorSpec().getIsKdoChief()) && !isKdoServicesSet) { //Для КДО находим все услуги помимо дочерних визитов
                        List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id), servicedate,max(id) as cnt from EntryMedService where entry_id=:id group by medservice_id, servicedate").setParameter("id",aEntry.getId()).getResultList();
                        if (!list.isEmpty()) {
                            for (Object[] ms: list) {
                                uslCnt++;
                                EntryMedService ems = theManager.find(EntryMedService.class,Long.valueOf(ms[3].toString()));
                                VocMedService medService = ems.getMedService(); //theManager.find(VocMedService.class,Long.valueOf(ms[0].toString()));
                                String serviceDate = dateToString(ems.getServiceDate());

                                Element usl = new Element("USL");
                                usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                                usl.addContent(new Element("LPU_U").setText("300001"));
                                usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                                usl.addContent(new Element("PROFIL_U").setText(profileK));
                                //usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                                usl.addContent(new Element("IDDOKT_U").setText(isNotNull(ems.getDoctorSnils()) ? ems.getDoctorSnils() : currentEntry.getDoctorSnils()));
                                usl.addContent(new Element("PRVS_U").setText(prvs));
                                usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                                usl.addContent(new Element("COD_DUSL_U").setText(medService.getCode()));
                                usl.addContent(new Element("KOL_USL").setText(ms[1].toString()));
                                usl.addContent(new Element("DATE_1_U").setText(serviceDate));
                                usl.addContent(new Element("DATE_2_U").setText(serviceDate));
                                usl.addContent(new Element("SUMV_USL").setText("0"));
                                sl.addContent(usl);

                            }
                            sl.getChild("ED_COL").setText("1"); //ED_COL всегда равен 1 *** 02-08-2018
                            // log.info("XML_KDO_CHILD="+uslCnt);
                        }
                        isKdoServicesSet = true;
                    }
                }  else {
                    List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id) as cnt , serviceDate as serviceDate from EntryMedService where entry_id=:id group by medservice_id, servicedate")
                            .setParameter("id",currentEntry.getId()).getResultList();
                    if (!list.isEmpty()) {
                        boolean first = true;
                        for (Object[] ms: list) {
                            uslCnt++;
                            VocMedService medService = theManager.find(VocMedService.class,Long.valueOf(ms[0].toString()));
                            Element usl = new Element("USL");
                            usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                            usl.addContent(new Element("LPU_U").setText("300001"));
                            usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                            usl.addContent(new Element("PROFIL_U").setText(profileK));
                            usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                            usl.addContent(new Element("PRVS_U").setText(prvs));
                            usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                            usl.addContent(new Element("COD_DUSL_U").setText(medService.getCode()));
                            usl.addContent(new Element("KOL_USL").setText(ms[1].toString()));
                            usl.addContent(new Element("DATE_1_U").setText(ms[2]!=null?ms[2].toString():startDate));
                            usl.addContent(new Element("DATE_2_U").setText(ms[2]!=null?ms[2].toString():startDate));
                            usl.addContent(new Element("SUMV_USL").setText("0"));
                            if (first){
                                if (isCancer&&cancerEntry!=null) {
                                    if (cancerEntry.getMaybeCancer()) {
                                        List<E2CancerDirection> directions = theManager.createQuery("from E2CancerDirection where cancerEntry=:cancer").setParameter("cancer", cancerEntry).getResultList();
                                        if (directions.isEmpty()) {
                                            log.error("Не указаны направления в то время, когда они должны быть указаны!" + currentEntry.getId() + " " + currentEntry.getHistoryNumber());
                                        }
                                        for (E2CancerDirection direction : directions) {
                                            Element napr = new Element("NAPR");
                                            napr = add(napr, "NAPR_DATE", direction.getDate());
                                            napr = add(napr, "NAPR_V", direction.getType());
                                            napr = addIfNotNull(napr, "NAPR_ISSL", direction.getSurveyMethod());
                                            napr = addIfNotNull(napr, "NAPR_USL", direction.getMedService());
                                            usl.addContent(napr);
                                        }
                                    } else {
                                        Element onkUsl = new Element("ONK_USL");
                                        onkUsl = addIfNotNull(onkUsl, "PR_CONS", cancerEntry.getConsiliumResult());
                                        onkUsl = add(onkUsl, "USL_TIP", cancerEntry.getServiceType());
                                        onkUsl = addIfNotNull(onkUsl, "HIR_TIP", cancerEntry.getSurgicalType());
                                        onkUsl = addIfNotNull(onkUsl, "LEK_TIP_L", cancerEntry.getDrugLine());
                                        onkUsl = addIfNotNull(onkUsl, "LEK_TIP_V", cancerEntry.getDrugCycle());
                                        onkUsl = addIfNotNull(onkUsl, "LUCH_TIP", cancerEntry.getRadiationTherapy());
                                        usl.addContent(onkUsl);
                                    }
                                }
                                first=false;
                            }
                            sl.addContent(usl);

                        }
                    }
                }
                // USL finish
                zSl.addContent(indSl,sl);
                indSl++;
            }
            /** Закончили создавать Sl*/
            zSl.getChild("DATE_Z_1").setText(dateToString(startHospitalDate));
            zSl.getChild("DATE_Z_2").setText(dateToString(finishHospitalDate));
            if (zSl.getChild("KD_Z")!=null) {zSl.getChild("KD_Z").setText(kdz+"");}
            zSl.removeChild("SL_TEMPLATE");
            zap.addContent(zSl); //Добавляем информацию о случае в запись
            return zap;
        } catch (Exception e) {
            log.error("EEEE = ",e);
            throw new IllegalStateException("some unknown error!");
        }
    }

    /** Создаем тэг с информацией о госпитализации (версия 3.1.1)*/
    private Element createZSl(E2Entry aEntry, boolean isPoliclinic, int slCnt, int zslIdCase, boolean isNedonosh) {
    //    String startDate = dateToString(aEntry.getHospitalStartDate()), finishDate = dateToString(aEntry.getHospitalFinishDate()!=null?aEntry.getHospitalFinishDate():aEntry.getFinishDate());
        boolean isExtDisp = aEntry.getEntryType().equals(EXTDISPTYPE);
        Element z = new Element("Z_SL");
        z.addContent(new Element("IDCASE").setText(zslIdCase+""));
      // z.addContent(new Element("IDCASE").setText(aEntry.getExternalParentId()+""));
        z.addContent(new Element("VID_SLUCH").setText(aEntry.getVidSluch().getCode()));
        z.addContent(new Element("USL_OK").setText(aEntry.getMedHelpUsl().getCode())); //дневной-круглосуточный-поликлиника
        z.addContent(new Element("VIDPOM").setText(aEntry.getMedHelpKind().getCode()));
        z.addContent(new Element("FOR_POM").setText(isNotNull(aEntry.getIsEmergency()) ? (isPoliclinic?"2":"1") : "3")); //форма помощи V014

        if (!isNotNull(aEntry.getIsEmergency())) z=addIfNotNull(z,"NPR_MO",aEntry.getDirectLpu()); //Направившее ЛПУ
        z=addIfNotNull(z,"NPR_DATE",aEntry.getDirectDate()); //Дата направления на лечение ***
        z=addIfNotNull(z,"NPR_N",aEntry.getTicket263Number()); // Номер направления на портале ФОМС
        z=addIfNotNull(z,"NPR_P",aEntry.getPlanHospDate()); // Дата планируемой госпитализации
        //PRN_MO
        z.addContent(new Element("LPU").setText("1")); //ЛПУ лечения //TODO = сделать высчитываемым
        z.addContent(new Element("VBR").setText(isNotNull(aEntry.getIsMobilePolyclinic())?"1":"0")); //Признак мобильной бригады
        z.addContent(new Element("DATE_Z_1").setText("_")); //Дата начала случая
        z.addContent(new Element("DATE_Z_2").setText("_")); //Дата окончания случая
        if (!isPoliclinic &&!isExtDisp)  z.addContent(new Element("KD_Z").addContent(aEntry.getBedDays()+"")); // Продолжительность госпитализации
       // if (isNedonosh) z.addContent(new Element("VNOV_M").setText(aEntry.getNewbornWeight()+""));
        z.addContent(new Element("RSLT").setText(aEntry.getFondResult().getCode())); // Результат обращения
        if (isExtDisp) z=add(z,"RSLT_D",aEntry.getDispResult().getCode()); // Результат диспансеризации
        z.addContent(new Element("ISHOD").setText(aEntry.getFondIshod().getCode())); // Исход случая.
        //if (isExtDisp) z.addContent(new Element("P_OTK").setText("0")); // Отказ от ДД
        z.addContent(new Element("OS_SLUCH").setText(Expert2FondUtil.calculateFondOsSluch(aEntry))); // Особый случай
        if (!isPoliclinic &&!isExtDisp)z.addContent(new Element("VB_P").setText(slCnt>1?"1":"0")); // Признак внутрибольничного перевода
        z.addContent(new Element("SL_TEMPLATE")); // Список случаев
        //if (isExtDisp) z=add(z,"SGROUP",aEntry.getExtDispSocialGroup()); // Социальная группа в ДД +
        z.addContent(new Element("IDSP").setText(aEntry.getIDSP().getCode())); // Способ оплаты медицинской помощи (V010)
        z=add(z,"SUMV",aEntry.getCost()); // Сумма, выставленная к оплате =SUMV7+ SUMV8
        return z;
    }

    /** Добавляем в Element значение*/
    private Element add(Element el, String aFieldname, Object aValue) {
        el.addContent(new Element(aFieldname).setText(aValue!=null?aValue.toString():""));
        return el;
    }
    /** Создааем информацию по случаю мед. обслуживания для версии от ноября 2018 (3.1.1)
     * aEntry - случай госпитализации
     * entriesList - строка с ИД СЛО
     * */
        private Element createSlElements(E2Entry aEntry, String entriesString, int cnt, String aDefaultStacService) {

            /*
            ZSL, SL = информация об обращении. визиты переносятся в USL
            * */
            String version = "3.1.1";
        try {
            String entryType = aEntry.getEntryType();
            boolean isHosp = false, isVmp = false, isPoliclinic = false, isExtDisp = false, isPoliclinicKdo=false;
            boolean isNedonosh = false;
            if (entryType.equals(HOSPITALTYPE)) {isHosp=true; isNedonosh=aEntry.getKsg()!=null && ",107,108,".contains(","+aEntry.getKsg().getCode()+",");} //Малая масса тела
            else if (entryType.equals(VMPTYPE)) {isVmp=true; isNedonosh=aEntry.getIsChild();}
            else if (entryType.equals(POLYCLINICTYPE)) {isPoliclinic=true;}
            else if (entryType.equals(EXTDISPTYPE)) {isExtDisp=true;}
            else if (entryType.equals(POLYCLINICKDOTYPE) || entryType.equals(KDPTYPE)) {isPoliclinic=true;isPoliclinicKdo=true;}
            else {throw new IllegalStateException("UNKNOWN ENTRYTYPE"+entryType);}
            Element zap = new Element("ZAP");
            zap.addContent(new Element("N_ZAP").setText(aEntry.getId() + ""));
            zap.addContent(new Element("PR_NOV").setText(isNotNull(aEntry.getPRNOV()) ? "1" : "0"));
            Element pat = new Element("PACIENT");
            pat.addContent(new Element("ID_PAC").setText(aEntry.getExternalPatientId() + ""))
                    .addContent(new Element("VPOLIS").setText(aEntry.getMedPolicyType()));
            if (aEntry.getMedPolicyType() != null && !aEntry.getMedPolicyType().equals("3")) {
                pat = addIfNotNull(pat, "SPOLIS", aEntry.getMedPolicySeries()); //Если полис не нового образца - добавляем серию полиса
                pat.addContent(new Element("NPOLIS").setText(aEntry.getMedPolicyNumber()));
            } else {
                pat.addContent(new Element("NPOLIS").setText(aEntry.getCommonNumber()));
            }

            if (isNotNull(aEntry.getInsuranceCompanyCode())) {
                pat.addContent(new Element("SMO").setText(aEntry.getInsuranceCompanyCode()));
            }  else {
                pat.addContent(new Element("SMO_OGRN").setText(aEntry.getInsuranceCompanyOgrn()));
                //   pat.addContent(new Element("SMO_OK").setText(aEntry.getInsuranceCompanyTerritory()));
                pat.addContent(new Element("SMO_NAM").setText(aEntry.getInsuranceCompanyName()));
            }
            String novorString=makeNovorString(aEntry);
            pat.addContent(new Element("NOVOR").setText(novorString));
            if (isNedonosh){ // && novorString.equals("0")) { //11.10.2018 по согласованию с фондом
                pat.addContent(new Element("VNOV_D").setText(aEntry.getNewbornWeight()+""));
            }

            zap.addContent(pat); //Добавили данные по пациенту


            List<E2Entry> children=null;
            String isChild = isNotNull(aEntry.getIsChild()) ? "1" : "0";

          //  edCol="1"; //06-08-2018 Ед кол больше не равно 1 !

            String[] slIds = entriesString.split(",");
            Element zSl = createZSl(aEntry,isPoliclinic,slIds.length,cnt, isNedonosh && !novorString.equals("0"));
            int indSl = zSl.indexOf(zSl.getChild("SL_TEMPLATE"));
            Date startHospitalDate = null, finishHospitalDate=null;
            int kdz=0;

            /** Вот тут создаем Sl*/
            boolean isDiagnosisFill=false, isLongCase=slIds.length>1;
            boolean isKdoServicesSet = false;
            String vers="V021";
            for (String slId:slIds) {
                Element sl = new Element("SL");
                E2Entry currentEntry = theManager.find(E2Entry.class,Long.valueOf(slId.trim()));
                String edCol="1";
               if (isPoliclinicKdo) {
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
                sl=add(sl,"SL_IDCASE",slId);
                sl=add(sl,"LPU_1","30000101");
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
                    sl=add(sl,"TAL_D",currentEntry.getVMPTicketDate()); // Дата выдачи талона ВМП
                    sl=add(sl,"TAL_N",currentEntry.getVMPTicketNumber()); // Номер выдачи ВМП
                    sl=add(sl,"TAL_P",dateToString(currentEntry.getVMPPlanHospDate())); // Дата планируемой госпитализации
                    sl=add(sl,"VID_HMP",currentEntry.getVMPKind()); // Вид ВМП
                    sl=add(sl,"METOD_HMP",currentEntry.getVMPMethod()); // Метод ВМП
                }
                sl=add(sl,"TELEMED",currentEntry.getDepartmentId()==416 ? "1" : "0");
             //   sl=add(sl,"PROFIL",profileK); //Профиль коек/специальностей (V002_K)
                sl=add(sl,"PROFIL",profile.getCode()); //Профиль специальностей V002 * 12.12.2018
                if (isHosp || isVmp) {
                    if (profile.getProfileBed()==null) {
                        log.error("Нет профиля койки для профиля: "+profile.getProfileK());
                        theManager.persist(new E2EntryError(currentEntry,"NO_FOND_FIELD: Нет профиля койки"));
                        return null;
                    }
                    sl=add(sl,"PROFIL_K",profile.getProfileBed().getCode()); //Профиль коек/специальностей (V020)
                }
                sl=add(sl,"DET",isChild); //Признак детского возраста
                if (isPoliclinic) {
                    sl=add(sl,"P_CEL",currentEntry.getVisitPurpose().getCode()); // Цель посещения
                }
                sl=add(sl,"NHISTORY",currentEntry.getHistoryNumber()); //Номер истории болезни
                if (isHosp||isVmp) {
                    sl=add(sl,"P_PER",Expert2FondUtil.calculateFondP_PER(currentEntry)); //Признак перевода
                }
                sl=add(sl,"DATE_1",startDate); //Дата начала случая
                sl=add(sl,"DATE_2",finishDate); //Дата окончания случая
                if (isHosp||isVmp) {
                    sl=add(sl,"KD",currentEntry.getBedDays()); //Продолжительность госпитализации
                }
                sl = setSluchDiagnosis(sl, currentEntry,version);
                if (sl==null) {
                    theManager.persist(new E2EntryError(currentEntry,"NO_MAIN_DIAGNOSIS"));
                    return null;
                }
           //     sl=add(sl,"NPL",isNotNull(currentEntry.getNotFullPaymentReason())?currentEntry.getNotFullPaymentReason():"0"); // Неполный объем //TODO
                sl=add(sl,"DS_ONK",(cancerEntry!=null && cancerEntry.getMaybeCancer()) ? "1" : "0");
                //*DN дисп. наблюдение
                //*DN_DP дата след. Д визита
                if (isCancer && cancerEntry!=null) { //NAPR
                    if (cancerEntry.getMaybeCancer()) { //Заполняем информация при подозрении на ЗНО
                        List<E2CancerDirection> directions = cancerEntry.getDirections();
                        if (directions.isEmpty()) {
                            log.error("Не указаны направления в то время, когда они должны быть указаны!" + currentEntry.getId() + " " + currentEntry.getHistoryNumber());
                        }
                        log.warn("directions = "+directions!=null?directions.size():"NULLLLL");
                        for (E2CancerDirection direction : directions) {
                            Element napr = new Element("NAPR");
                            napr = add(napr, "NAPR_DATE", direction.getDate());
                            napr = addIfNotNull(napr,"NAPR_MO",direction.getDirectLpu());
                            napr = add(napr, "NAPR_V", direction.getType());
                            napr = addIfNotNull(napr, "MET_ISSL", direction.getSurveyMethod());
                            napr = addIfNotNull(napr, "NAPR_USL", direction.getMedService());
                            sl.addContent(napr);
                        }
                    }
                    if (isNotNull(cancerEntry.getConsiliumResult())) { //Консилиум в любом случае если есть ЗНО.
                        Element cons = new Element("CONS");
                        cons= add(cons, "PR_CONS", cancerEntry.getConsiliumResult());
                        cons= add(cons, "DT_CONS", cancerEntry.getConsiliumDate());
                        sl.addContent(cons);
                    }
                    if (!cancerEntry.getMaybeCancer()) { //Случай ЗНО
                        Element onkSl = new Element("ONK_SL");
                        onkSl=addIfNotNull(onkSl,"DS1_T",cancerEntry.getOccasion());
                        if (cancerEntry.getStage()== null
                                || cancerEntry.getTumor()== null
                                || cancerEntry.getNodus()== null
                                || cancerEntry.getMetastasis()== null
                        ) {
                            theManager.persist(new E2EntryError(currentEntry,"NO_CANCERINFO"));
                            return null;
                        }
                        onkSl=add(onkSl,"STAD",cancerEntry.getStage());
                        onkSl=addIfNotNull(onkSl,"ONK_T",cancerEntry.getTumor());
                        onkSl=addIfNotNull(onkSl,"ONK_N",cancerEntry.getNodus());
                        onkSl=addIfNotNull(onkSl,"ONK_M",cancerEntry.getMetastasis());
                        onkSl=addIfNotNull(onkSl,"MTSTZ",cancerEntry.getIsMetastasisFound());
                        onkSl=addIfNotNull(onkSl,"SOD",cancerEntry.getSod());
                        List<E2CancerDiagnostic> directions= cancerEntry.getDiagnostics();
                        for (E2CancerDiagnostic direction: directions){
                            Element dir = new Element("B_DIAG");
                            dir=add(dir,"DIAG_TIP",direction.getType());
                            dir=add(dir,"DIAG_CODE",direction.getCode());
                            dir=add(dir,"DIAG_RSLT",direction.getResult());
                            onkSl.addContent(dir);
                        }
                        List<E2CancerRefusal> prots = cancerEntry.getRefusals();
                        for (E2CancerRefusal prot: prots){
                            Element pr = new Element("B_PROT");
                            pr=add(pr,"PROT",prot.getCode());
                            pr=add(pr,"D_PROT",prot.getDate());
                            onkSl.addContent(pr);
                        }
                        if (isHosp) {
                            Element onkUsl = new Element("ONK_USL");
                            String serviceType = cancerEntry.getServiceType()!=null?cancerEntry.getServiceType():"";
                            if (serviceType.equals("2")|| serviceType.equals("4")) {
                                theManager.persist(new E2EntryError(currentEntry,"ONCOLOGY_CASE_DRUG","Не может быть вид онколечения: "+serviceType+" лек. или химиолучевая терапия"));
                            }
                            onkUsl = add(onkUsl, "USL_TIP", serviceType);
                            if (serviceType.equals("1")) onkUsl = addIfNotNull(onkUsl, "HIR_TIP", cancerEntry.getSurgicalType());
                            if (serviceType.equals("2")) onkUsl = addIfNotNull(onkUsl, "LEK_TIP_L", cancerEntry.getDrugLine());
                            if (serviceType.equals("2")) onkUsl = addIfNotNull(onkUsl, "LEK_TIP_V", cancerEntry.getDrugCycle());
                       //    if (serviceType.equals("2")||serviceType.equals("4"))  onkUsl = addIfNotNull(onkUsl,"LEK_PR","LEK_PR"); //TODO реализовать информацию о лекарствах
                            if (serviceType.equals("3")||serviceType.equals("4")) onkUsl = addIfNotNull(onkUsl, "LUCH_TIP", cancerEntry.getRadiationTherapy());
                            onkSl.addContent(onkUsl);
                        }
                        sl.addContent(onkSl);
                    }
                } else if (isCancer) {
                    log.error("Не найден раковый случай для записи с ИД"+currentEntry.getId());
                }
                if (isHosp){//KSG_KGP
                    Element ksgKpg=new Element("KSG_KPG");
                    VocKsg ksg = currentEntry.getKsg();
                    ksgKpg=add(ksgKpg,"N_KSG",ksg.getCode());
                    ksgKpg=add(ksgKpg,"VER_KSG",ksg.getYear()!=null ?ksg.getYear() : 2018);
                    ksgKpg=add(ksgKpg,"KSG_PG","0");
                    ksgKpg=add(ksgKpg,"KOEF_Z",ksg.getKZ());
                    ksgKpg=add(ksgKpg,"KOEF_UP",theExpertService.getActualKsgUprCoefficient(ksg,currentEntry.getFinishDate()));
                    ksgKpg=add(ksgKpg,"BZTSZ",currentEntry.getBaseTarif());
                    ksgKpg=add(ksgKpg,"KOEF_D","1"); //TODO
                    ksgKpg=add(ksgKpg,"KOEF_U","1"); //TODO
                    ksgKpg=addIfNotNull(ksgKpg,"CRIT",currentEntry.getDopKritKSG());
                    //DKK2
                    List<E2CoefficientPatientDifficultyEntryLink> difficultyEntryLinks = currentEntry.getPatientDifficulty();
                    if (!difficultyEntryLinks.isEmpty()){
                        ksgKpg=add(ksgKpg,"SL_K","1");
                        ksgKpg=add(ksgKpg,"IT_SL",theExpertService.calculateResultDifficultyCoefficient(currentEntry));
                        for (E2CoefficientPatientDifficultyEntryLink link: difficultyEntryLinks){
                            Element slKoef = new Element("SL_KOEF");
                            slKoef=add(slKoef,"IDSL",link.getDifficulty().getCode());
                            slKoef=add(slKoef,"Z_SL_K",link.getValue()!=null?link.getValue():link.getDifficulty().getValue());
                            ksgKpg.addContent(slKoef);
                        }
                    } else {
                        ksgKpg=add(ksgKpg,"SL_K","0");
                    }
                    sl.addContent(ksgKpg);
                }
                // * REAB
                String prvs = currentEntry.getFondDoctorSpecV021()!=null ? currentEntry.getFondDoctorSpecV021().getCode() : "V015_"+currentEntry.getFondDoctorSpec().getCode();
                sl=add(sl,"PRVS",prvs); //Специальность лечащего врача
                sl=add(sl,"VERS_SPEC",vers);
                sl=add(sl,"IDDOKT",currentEntry.getDoctorSnils()); // СНИЛС лечащего врача
                sl=add(sl,"ED_COL",edCol);
               if (isPoliclinicKdo) {
                   //if (isNotNull(currentEntry.getFondDoctorSpec().getIsKdoChief()) && !isKdoServicesSet) {
                        sl=add(sl,"TARIF",aEntry.getCost());
                        sl=add(sl,"SUM_M",aEntry.getCost());
                   /*} else {
                       sl=add(sl,"TARIF","0");
                       sl=add(sl,"SUM_M","0");
                   }*/
               } else if (isPoliclinic && isLongCase) {
                    if (!isDiagnosisFill && sl.getChild("DS1")!=null && !sl.getChildText("DS1").startsWith("Z")) {
                        isDiagnosisFill=true;
                        sl=add(sl,"TARIF",aEntry.getCost());
                        sl=add(sl,"SUM_M",aEntry.getCost());
                    } else {
                        sl=add(sl,"TARIF","0");
                        sl=add(sl,"SUM_M","0");
                    }
               }  else {
                    sl=add(sl,"TARIF",currentEntry.getCost());
                    sl=add(sl,"SUM_M",currentEntry.getCost());
               }

                //USL start
                int uslCnt = 0;
                if (currentEntry.getReanimationEntry()!=null) { //Реанимационная услуга
                    uslCnt++;
                    Element usl = new Element("USL");
                    usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                    usl.addContent(new Element("LPU_U").setText("300001"));
                    //LPU_1_U
                    //PODR_U
                    usl.addContent(new Element("PROFIL_U").setText(profileK)); //Точно профилёк? может быть, профиль? *12/12/2018 профиль
                    usl.addContent(new Element("VID_VME").setText("B03.003.005"));
                    usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                    usl.addContent(new Element("DATE_1_U").setText(startDate));
                    usl.addContent(new Element("DATE_2_U").setText(finishDate));
                    usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                    usl.addContent(new Element("KOL_USL").setText("1"));
                    usl.addContent(new Element("SUMV_USL").setText("0"));
                    usl.addContent(new Element("PRVS_U").setText(prvs));
                    usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                    usl.addContent(new Element("NPL").setText("0"));
                    sl.addContent(usl);
                }
                //Информация об услугах
                if (isPoliclinic) { //Для поликлиники - кол-во визитов
                    if (!isPoliclinicKdo && children.isEmpty()) {
                        children.add(currentEntry);
                    }
                    boolean isFirst = true;
                    for (E2Entry child: children) {
                        uslCnt++;
                        String uslDate = dateToString(child.getStartDate());
                        VocE2MedHelpProfile childProfile =child.getMedHelpProfile();
                        if (childProfile==null) {
                            theManager.persist(new E2EntryError(aEntry,"NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: "+child.getId()));
                            log.warn("NO_SUBENTRY_PROFILE_Нет профиля у комплексного случая с ИД: "+child.getId());
                            continue;
                        }
                        VocE2FondV021 spec = child.getFondDoctorSpecV021();
                        prvs = child.getFondDoctorSpecV021()!=null?child.getFondDoctorSpecV021().getCode():childProfile.getMedSpecV021().getCode();
                        Element usl = new Element("USL");
                        usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                        usl.addContent(new Element("LPU_U").setText("300001"));
                        usl.addContent(new Element("PROFIL_U").setText(childProfile.getCode()));

                        if (isPoliclinicKdo) {
                            if (isNotNull(child.getMainService())) usl.addContent(new Element("VID_VME").setText(child.getMainService()));
                        } else {
                            try {
                                VocMedService vms = isFirst? spec.getDefaultMedService():spec.getRepeatMedService();
                                usl=add(usl,"VID_VME",vms.getCode());
                            } catch (Exception e) {
                                usl=add(usl,"VID_VME","A01.20.002");
                                log.error(" У врача "+spec.getCode()+" нет услуги по умолчанию");
                            }
                        }
                        usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                        usl.addContent(new Element("DATE_1_U").setText(uslDate));
                        usl.addContent(new Element("DATE_2_U").setText(uslDate));
                        usl.addContent(new Element("DS_U").setText(isNotNull(child.getMainMkb())?child.getMainMkb():sl.getChildText("DS1")));
                        usl.addContent(new Element("KOL_USL").setText("1"));
                        usl.addContent(new Element("SUMV_USL").setText("0"));
                        usl.addContent(new Element("PRVS_U").setText(spec.getCode()));
                        usl.addContent(new Element("IDDOKT_U").setText(child.getDoctorSnils()));
                        usl.addContent(new Element("NPL").setText("0"));
                        sl.addContent(usl);
                        isFirst=false;
                    }
                    if (isPoliclinicKdo) { //Для КДП находим все услуги помимо дочерних визитов
                        List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id), servicedate,max(id) as cnt from EntryMedService where entry_id=:id group by medservice_id, servicedate").setParameter("id",aEntry.getId()).getResultList();
                        if (!list.isEmpty()) {
                            for (Object[] ms: list) {
                                uslCnt++;
                                EntryMedService ems = theManager.find(EntryMedService.class,Long.valueOf(ms[3].toString()));
                                VocMedService medService = ems.getMedService(); //theManager.find(VocMedService.class,Long.valueOf(ms[0].toString()));
                                String serviceDate = dateToString(ems.getServiceDate());
                                Element usl = new Element("USL");
                                usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                                usl.addContent(new Element("LPU_U").setText("300001"));
                                usl.addContent(new Element("PROFIL_U").setText(profileK));
                                usl.addContent(new Element("VID_VME").setText(medService.getCode()));
                                usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                                usl.addContent(new Element("DATE_1_U").setText(serviceDate));
                                usl.addContent(new Element("DATE_2_U").setText(serviceDate));
                                usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                                usl.addContent(new Element("KOL_USL").setText(ms[1].toString()));
                                usl.addContent(new Element("SUMV_USL").setText("0"));
                                usl.addContent(new Element("PRVS_U").setText(prvs));
                                //usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                                usl.addContent(new Element("IDDOKT_U").setText(isNotNull(ems.getDoctorSnils()) ? ems.getDoctorSnils() : currentEntry.getDoctorSnils())); //Так правильно
                                usl.addContent(new Element("NPL").setText("0"));
                                sl.addContent(usl);
                            }
                            sl.getChild("ED_COL").setText("1"); //ED_COL всегда равен 1 *** 02-08-2018
                           // log.info("XML_KDO_CHILD="+uslCnt);
                        }
                        isKdoServicesSet = true;
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
                    List<Object[]> list = theManager.createNativeQuery("select vms.code as ms, cast(count(ems.id) as varchar) as cnt, cast(ems.serviceDate as varchar(10)) as serviceDate" +
                            " from EntryMedService ems left join vocMedService vms on vms.id=ems.medService_id where ems.entry_id=:id group by vms.code, ems.servicedate")
                            .setParameter("id",currentEntry.getId()).getResultList();
                    if (list.isEmpty() && aDefaultStacService!=null) {
                        list.add(new String[]{aDefaultStacService,"1",null});
                    }
                    if (!list.isEmpty()) {
                        for (Object[] ms: list) {
                            uslCnt++;
                            Element usl = new Element("USL");
                            usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                            usl.addContent(new Element("LPU_U").setText("300001"));
                            usl.addContent(new Element("PROFIL_U").setText(profileK));
                            usl.addContent(new Element("VID_VME").setText(ms[0].toString()));
                            usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                            usl.addContent(new Element("DATE_1_U").setText(ms[2]!=null?ms[2].toString():startDate));
                            usl.addContent(new Element("DATE_2_U").setText(ms[2]!=null?ms[2].toString():startDate));
                            usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                            usl.addContent(new Element("KOL_USL").setText(ms[1].toString()));
                            usl.addContent(new Element("SUMV_USL").setText("0"));
                            usl.addContent(new Element("PRVS_U").setText(prvs));
                            usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                            usl.addContent(new Element("NPL").setText("0"));
                            sl.addContent(usl);
                        }
                    }
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
            log.error("EEEE = ",e);
            throw new IllegalStateException("some unknown error!");
        }
    }

    /** Создаем заголовок для L файла (информация о пациентах) */
    private Element makeLTitle(Element root, Date aDocumentDate, String aFilename, String aVersion) {
        aDocumentDate = aDocumentDate != null ? aDocumentDate : new Date(new java.util.Date().getTime());
        Element zglv = new Element("ZGLV");
        zglv.addContent(new Element("VERSION").setText(aVersion));
        zglv.addContent(new Element("DATA").setText(dateToString(aDocumentDate)));
        zglv.addContent(new Element("FILENAME").setText(aFilename));
        zglv.addContent(new Element("FILENAME1").setText("H" + aFilename.substring(1)));
        root.addContent(zglv);
        return root;
    }


    /** Создаем заголовок для H файла (информация о мед. услугах) */
    private Element makeHTitle(Element root, Date aDocumentDate, String aFilename, int count, String aBillNumber, Date aBillDate, BigDecimal aTotalSum, String aVersion) {
        aDocumentDate = aDocumentDate != null ? aDocumentDate : new Date(new java.util.Date().getTime());
        Element zglv = new Element("ZGLV");
        zglv.addContent(new Element("VERSION").setText(aVersion));
        zglv.addContent(new Element("DATA").setText(dateToString(aDocumentDate)));
        zglv.addContent(new Element("FILENAME").setText(aFilename));
        zglv.addContent(new Element("SD_Z").setText(count + ""));
        root.addContent(zglv);

        Element schet = new Element("SCHET");
        schet.addContent(new Element("CODE").setText("1"));
        schet.addContent(new Element("CODE_MO").setText("300001"));
        schet.addContent(new Element("YEAR").setText(dateToString(aDocumentDate, "yyyy")));
        schet.addContent(new Element("MONTH").setText(dateToString(aDocumentDate, "M")));
        schet.addContent(new Element("NSCHET").setText(aBillNumber));
        schet.addContent(new Element("DSCHET").setText(dateToString(aBillDate)));
   //   schet.addContent(new Element("PLAT").setText("30004"));
        schet.addContent(new Element("SUMMAV").setText(aTotalSum + ""));
  //      schet.addContent(new Element("SUMMAV8_P").setText(aTotalSum + ""));
        root.addContent(schet);
        return root;

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
    public String makeMPFIle(Long aEntryListId, String aType, String aBillNumber, Date aBillDate, Long aEntryId, Boolean calcAllListEntry, long aMonitorId, String aVersion) {
        log.info("Формирование файла версии "+aVersion);
        try {
            if (isCheckIsRunning) {
                log.warn("Формирование чего-то уже запущено, выходим_ALREADY_RAN");
                // return "Формирование чего-то уже запущено, выходим";
            }
            Date periodDate;
            isCheckIsRunning = true;
            String packetDateAdd;
            String cntNumber = null;
            boolean needCreateArchive = false;
            E2Entry entry=null;
            if (aEntryListId != null) {
                needCreateArchive = true;
                E2ListEntry listEntry = theManager.find(E2ListEntry.class, aEntryListId);
                periodDate = listEntry.getFinishDate();
                if (!isNotNull(aBillDate) || !isNotNull(aBillNumber)) {
                    return "Необходимо указать номер и дату счета!";
                }
                if (listEntry.getCheckDate()==null||listEntry.getCheckTime()==null) {
                    log.error("Необходимо выполнить проверку перед формированием пакета");
                    return "Необходимо выполнить проверку перед формированием пакета";

                }
            } else { //Сделано для теста.
              entry = theManager.find(E2Entry.class, aEntryId);
                periodDate = entry.getFinishDate();
                aBillDate = aBillDate != null ? aBillDate : periodDate;
                aBillNumber = aBillNumber != null ? aBillNumber : "TEST";
                cntNumber = "00";
                aEntryListId=entry.getListEntry().getId();

            }
            packetDateAdd = dateToString(periodDate, "yyMM");
            String packetType;
            if (aType.equals(HOSPITALTYPE) || aType.equals(POLYCLINICTYPE) || aType.equals(POLYCLINICKDOTYPE) || aType.equals(HOSPITALPEREVODTYPE) || aType.equals(KDPTYPE)) {
                packetType = "Z";
            } else if (aType.equals(VMPTYPE)) {
                packetType = "T";
            } else if (aType.equals(EXTDISPTYPE)) {
                //Пока сделаем заглушку
                packetType="DV";
            } else {
                throw new IllegalStateException("Неизвестный тип счета: " + aType);
            }
            java.util.Date startStartDate = new java.util.Date();
            String regNumber = getExpertConfigValue("LPU_REG_NUMBER", "300001");
            String fileName = "M" + regNumber + "T30_" + packetDateAdd; // M300001 T30_171227
            SequenceHelper sequenceHelper = SequenceHelper.getInstance();
            if (cntNumber == null) {
                cntNumber = sequenceHelper.startUseNextValueNoCheck(packetType + "#" + fileName, "", theManager);
            }
            fileName += cntNumber;
            log.info("create new FileName = " + fileName);
            Element hRoot = new Element("ZL_LIST");  // данные о мед. помощи
            Element lRoot = new Element("PERS_LIST");  // данные о пациенте
            List<Long> patientIdsList = new ArrayList<>();
            BigDecimal totalSum = new BigDecimal(0);
            List<Element> zaps = new ArrayList<>();
            List<Element> perss = new ArrayList<>();
            int cnt = 0;
            List<Object[]> records;
            String selectSqlAdd ,groupSqlAdd;
            boolean isHosp=false,isPolic=false;
            if (aType.equals(HOSPITALTYPE) || aType.equals(HOSPITALPEREVODTYPE) || aType.equals(VMPTYPE)) {
                selectSqlAdd =" list(''||e.id) as ids, e.id, count(distinct e.id) as cnt";//Ищем все СЛО *список ИД, ИД госпитализации,кол-во СЛО
             //   selectSqlAdd =" list(''||e.id) as ids, e.externalparentid, count(distinct e.id) as cnt";//Ищем все СЛО *список ИД, ИД госпитализации,кол-во СЛО //1 Z_SL = 1SL
                groupSqlAdd= "e.id";
             //   groupSqlAdd= "e.externalparentid"; //1 Z_SL = 1SL
                isHosp=true;
            } else if (aType.equals(POLYCLINICTYPE) || aType.equals(POLYCLINICKDOTYPE) || aType.equals(KDPTYPE)){
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
                log.info("sql="+sql);
                records = theManager.createNativeQuery(sql)
                        .setParameter("billNumber", aBillNumber).setParameter("billDate", aBillDate).getResultList();
            } else {
                sql = "select "+selectSqlAdd+" from E2Entry e" +
                        " left join e2entry child on child.parentEntry_id=e.id and (child.isDeleted is null or child.isDeleted='0')" +
                        " where e.listEntry_id=" + aEntryListId  +
                        " and (e.isDeleted is null or e.isDeleted='0') " +
                        " and e.serviceStream!='COMPLEXCASE'" +
                        " and e.externalparentid="+entry.getExternalParentId()+
                        " and (e.doNotSend is null or e.doNotSend='0') group by "+groupSqlAdd;
                log.info("sql="+sql);
                records = theManager.createNativeQuery(sql).getResultList();
            }
            log.info("found " + records.size() + " records");
            IMonitor monitor ;// = theMonitorService.acceptMonitor(aMonitorId, "Расчет цены случаев в звполнении") ;
            monitor = theMonitorService.startMonitor(aMonitorId,"Формирование xml файла. Размер: ",records.size());
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
                Boolean isError = false;
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
                if (null == entry.getFondDoctorSpec()) {
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
                if (isError) {
                    E2EntryError error = new E2EntryError(entry, "NO_FOND_FIELDS:" + err.toString());
                    theManager.persist(error);
                    log.error("Запись с ИД " + entry.getId() + " не будет выгружена в xml: "+err);
                    continue;
                }

                Long personId = entry.getExternalPatientId();
                Element z ;
                String defaultStacService = getExpertConfigValue("DEFAULT_STAC_SERVICE",null);
                switch (aVersion) { //При появлении новых форматов файла - добавляем сюда
                    case "3.1.1":
                        z= createSlElements(entry, sls,cnt+1,defaultStacService);
                        break;
                    case "3.1":
                        z=createSlElementsOld(entry, sls,cnt+1);
                        break;
                    default:
                        log.error("Неизвестный формат пакета: "+aVersion);
                        z=null;
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
                    log.error("Не удалось сформировать запись по случаю с ИД "+entry.getId());
                }

            }
            log.info("ok, we made all, let's make files");
            monitor.setText("Формирование файла завершено, сохраняем архив");
          //  if (aVersion.equals("3.1.1")) aVersion="3.1";
            hRoot = makeHTitle(hRoot, periodDate, "H" + fileName, cnt, aBillNumber, aBillDate, totalSum,aVersion.equals("3.1.1")?"3.1":aVersion);
            if (aEntryListId != null) { //Меняем статус счета на "выставлен"
                E2Bill bill =theManager.find(E2Bill.class, theExpertService.getBillIdByDateAndNumber(aBillNumber, dateToString(aBillDate, "dd.MM.yyyy")));
                if (bill != null) {
                    bill.setStatus(getActualVocBySqlString(VocE2BillStatus.class, "select id from VocE2BillStatus where code='SENT'"));
                    bill.setSum(totalSum);
                    theManager.persist(bill);
                }
            }
            lRoot = makeLTitle(lRoot, periodDate, "L" + fileName,aVersion.equals("3.1")?"2.1":"3.1"); //гавнокод, спасибо фонду
            lRoot.addContent(perss);
            hRoot.addContent(zaps);
            String archiveName = packetType + fileName + ".MP";
            createXmlFile(hRoot, "H" + fileName);
            createXmlFile(lRoot, "L" + fileName);
            //   log.info("deb14");
            log.info("Время формирования файла (минут) = " + (System.currentTimeMillis() - startStartDate.getTime()) / 60000);
            monitor.setText("Завершено. Время формирования файла (минут) = " + (System.currentTimeMillis() - startStartDate.getTime()) / 60000);

            if (needCreateArchive) {
                archiveName = createArchive(archiveName, new String[]{"H" + fileName + ".xml", "L" + fileName + ".xml"});
                E2ExportPacketJournal journal = new E2ExportPacketJournal(aBillNumber, aBillDate, "/rtf/expert2xml/" + archiveName);
                theManager.persist(journal);
            }
            log.info("ALL SEEMS GOOD!");

            isCheckIsRunning = false;
            monitor.setText("/rtf/expert2xml/" + archiveName);
            monitor.finish("/rtf/expert2xml/" + archiveName);
            return "/rtf/expert2xml/" + archiveName;
        } catch (Exception err) {
            err.printStackTrace();
            log.error("ERR = ",err);
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
                    log.warn("entry ID ="+e.getId()+" NO COST");
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
           log.error(e);
            return null;
        }

    }
    private String getWorkDir() {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    }

    /** Создаем файл из документа */
    private void createXmlFile(Element aElement, String aFileName) {
        if (aElement == null) {
            log.error("no data for create file " + aFileName);
            return;
        }
        String outputFile = getWorkDir() + "/expert2xml/" + aFileName+".xml";

        try (OutputStreamWriter fwrt = new OutputStreamWriter(new FileOutputStream(outputFile), Charset.forName("windows-1251"))) {
            XMLOutputter outputter = new XMLOutputter();
            Document pat = new Document(aElement);
            outputter.setFormat(org.jdom.output.Format.getPrettyFormat().setEncoding("windows-1251"));
            outputter.output(pat, fwrt);
        } catch (Exception ex) {
            log.error("Someshing happened strange!!!",ex);
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
            log.info("START EXECUTING = " + sb);
            try {
                Runtime.getRuntime().exec("zip -d " + workDir + archiveName + " *");//Удаляем архив перед созданием;
            } catch (Exception e) {}//Не удалось очистить архив, т.к. его нету. Ничего страшного)

            Runtime.getRuntime().exec(exec+sb.toString());//arraCmd);
        } catch (IOException e) {
          // log.error(e);
            log.warn("it seems Windows");
            exec="\"C:\\Program Files\\7-Zip\\7z.exe\" a -tZIP ";
            try {
                Runtime.getRuntime().exec(exec+sb.toString());//arraCmd);
            } catch (IOException e1) {
               log.error(e1);
            }
        }
        return archiveName;
    }

    /** Добавляем соответствие между профилем мед. помощи и профилем койки */
    //Для ServiceJS------------------------------------------------------------
    public void ____addMedHelpProfileBedType(Long aMedHelpId, Long aBedTypeId) {
        VocBedType bedType = theManager.find(VocBedType.class, aBedTypeId);
        if (!theManager.createNativeQuery("select e. id from E2MedHelpProfileBedType e left join vocbedType vbt on vbt.id=e.bedtype_id " +
                " where e.profile_id=:profile and (e.bedtype_id=:bedTypeId or vbt.omcCode=:omcCode)").setParameter("profile",aMedHelpId).setParameter("bedTypeId", aBedTypeId)
                .setParameter("omcCode", bedType.getOmcCode()).getResultList().isEmpty()) {
            log.warn("Нельзя прикрепить койку, т.к. койка с таким кодом уже прикреплена");
            return;
        }
        E2MedHelpProfileBedType mhbt = new E2MedHelpProfileBedType();
        mhbt.setBedType(bedType);
        mhbt.setProfile(theManager.find(VocE2MedHelpProfile.class, aMedHelpId));
        theManager.persist(mhbt);
    }


    /** Получить сущность по коду (в основном для справочников) */
    private <T> T getEntityByCode(String aCode, Class aClass, Boolean needCreate) {
        List<T> list = theManager.createQuery("from " + aClass.getName() + " where code=:code").setParameter("code", aCode).getResultList();
        T ret = !list.isEmpty() ? list.get(0) : null;
        if (list.isEmpty() && needCreate) {
            try {
                ret = (T) aClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
               log.error(e);
            }
            if (ret instanceof VocBaseEntity|| ret instanceof VocMedService) { //Проверить
                try {
                    Method setCodeMethod = ret.getClass().getMethod("setCode",String.class);
                    setCodeMethod.invoke(ret,aCode);
                } catch (Exception e) {
                   log.error(e);
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
                    (aRegType==null||(aRegType!=null&&diagnosis.getRegistrationType()!=null&&aRegType.contains(diagnosis.getRegistrationType().getCode())))
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
            log.warn("Не удалось найти настройку с ключем "+aParameterName+", возвращаю значение по умолчанию");
            return aDefaultValue;
        }
        return ret.get(0).toString();
    }

    private <T> T getActualVocBySqlString(Class aClass, String aSql) {
        List<Object> list = theManager.createNativeQuery(aSql).getResultList();
        if (list.isEmpty()) {
            log.error("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием " + aSql);
            return null;
            //throw new IllegalStateException("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием " + aSql);
        }
        if (list.size() > 1) {
            log.error(" с условием " + aSql + " найдено несколько действующих значений справочника " + aClass.getCanonicalName());
            return null;
            //throw new IllegalStateException(" с условием " + aSql + " найдено несколько действующих значений справочника " + aClass.getCanonicalName());
        }
        return (T) theManager.find(aClass, Long.valueOf(list.get(0).toString()));

    }

    private String getJbossConfigValue(String aConfigName, String aDefaultValue) {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        return config.get(aConfigName, aDefaultValue);
    }

    private Element setSluchDiagnosis(Element aElement, E2Entry aEntry, String aVersion) {
        /** Нюансы
         * 30.01.2018 Если есть диабет - указываем его как главный сопутствующий
         */
        if (aVersion==null) {aVersion="3.1";}
        List<EntryDiagnosis> list = aEntry.getDiagnosis();
        String mainDiagnosisSqlAdd ;
        List<EntryDiagnosis> mainDiagnosis = null;
        String entryType =aEntry.getEntryType();
        if (entryType.equals(POLYCLINICTYPE) || entryType.equals(POLYCLINICKDOTYPE) || entryType.equals(KDPTYPE)) {
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
                if (isExtDisp && 1==1) { //Если ДД и галочка выявлено впервые - ставим 1.
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
                aElement=add(aElement,"DS2",otherDiagnosis.get(0));
                if (otherDiagnosis.size()>1) {
                    for (int i=1;i<otherDiagnosis.size()&&i<4;i++) {
                        aElement=add(aElement,"DS2_"+i,otherDiagnosis.get(i));
                    }
                }
            }
            if (aVersion.equals("3.1.1") && !isExtDisp &&!mainMkb.startsWith("Z")) { //C_ZAB * Характер заболевания, если USL_OK!=4 || DS1!=Z*
                VocE2FondV027 vip = ds.getVocIllnessPrimary();
                if (vip==null) {
                    theManager.persist(new E2EntryError(aEntry,"NO_HARAKTER"));
                    return null;
                }
                aElement=add(aElement,"C_ZAB",vip.getCode());
            }
        } else { //Если нет основного диагноза - нет случая
            return null;
        }

        return aElement;
    }

    /**Выводим сообщение в монитор. Возвращаем - отменен ли монитор*/
    public boolean isMonitorCancel(IMonitor aMonitor, String aMonitorText) {
        aMonitor.setText(aMonitorText);
        log.info(aMonitorText);
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
