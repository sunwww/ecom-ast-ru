package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.sequence.service.SequenceHelper;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.Expert2FondUtil;
import ru.ecom.expert2.domain.*;
import ru.ecom.expert2.domain.voc.*;
import ru.ecom.expert2.domain.voc.federal.*;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
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
import java.sql.Date;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
@Local(IExpert2XmlService.class)
@Remote(IExpert2XmlService.class)
public class Expert2XmlServiceBean implements IExpert2XmlService {
private Boolean isCheckIsRunning = false;
    private final Logger log = Logger.getLogger(Expert2XmlServiceBean.class);
    private final String HOSPITALTYPE="HOSPITAL";
    private final String HOSPITALPEREVODTYPE="HOSPITALPEREVOD";
    private final String POLYCLINICTYPE="POLYCLINIC";
    private final String VMPTYPE="VMP";
    private final String EXTDISPTYPE="EXTDISP";
    private final String POLYCLINICKDOTYPE="POLYCLINICKDO";
    private <T> T get(Long aId) {
        //Class aClass = Class<T>().new
        //theManager.find(T.class,aId);
        return null;
    }

    private Long toLong(String aString) {
        return aString!=null&&!aString.trim().equalsIgnoreCase("")?Long.valueOf(aString):null;
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
            pers.addContent(new Element("OT").setText(aEntry.getMiddlename()));
            pers.addContent(new Element("W").setText(aEntry.getSex()));
            pers.addContent(new Element("DR").setText(dateToString(aEntry.getBirthDate())));
        }
        //MR
      //  log.info("Добавляем данные о пациенте OTHER_DATA " + aEntry.getExternalPatientId() + " в L файле");
        pers.addContent(new Element("DOCTYPE").setText(aEntry.getPassportType()));
        pers.addContent(new Element("DOCSER").setText(aEntry.getPassportSeries()));
        pers.addContent(new Element("DOCNUM").setText(aEntry.getPassportNumber()));
        pers.addContent(new Element("SNILS").setText(isKinsman ? aEntry.getKinsmanSnils() : aEntry.getPatientSnils()));
        pers.addContent(new Element("ENP").setText(aEntry.getCommonNumber()));
        pers.addContent(new Element("OKATOG").setText(aEntry.getOkatoReg())); //код места жительства
        pers.addContent(new Element("OKATOP").setText(aEntry.getOkatoReal())); //код места пребывания

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
        if (!StringUtil.isNullOrEmpty(aEntry.getKinsmanLastname())) {
            SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
            ret = aEntry.getSex() + ""+format.format(aEntry.getBirthDate())+"" + (isNotNull(aEntry.getBirthOrder())?aEntry.getBirthOrder():1); //TODO = порядковый номер ребенка
        }
        return ret;

    }


    /** Создаем тэг с информацией о госпитализации*/
    private Element createZSl(E2Entry aEntry, boolean isPoliclinic, int slCnt) {
        String startDate = dateToString(aEntry.getHospitalStartDate()), finishDate = dateToString(aEntry.getHospitalFinishDate());

        Element z = new Element("Z_SL");
        z.addContent(new Element("IDCASE").setText(aEntry.getExternalParentId()+""));
        z.addContent(new Element("VID_SLUCH").setText(aEntry.getVidSluch().getCode()));
        z.addContent(new Element("USL_OK").setText(aEntry.getMedHelpUsl().getCode())); //дневной-круглосуточный-поликлиника
        z.addContent(new Element("VIDPOM").setText(aEntry.getMedHelpKind().getCode()));
        z.addContent(new Element("FOR_POM").setText(isNotNull(aEntry.getIsEmergency()) ? (isPoliclinic?"2":"1") : "3")); //форма помощи V014
        z.addContent(new Element("LPU").setText("1")); //ЛПУ лечения //TODO = сделать высчитываемым
        z.addContent(new Element("VBR").setText(isNotNull(aEntry.getIsMobilePolyclinic())?"1":"0")); //Признак мобильной бригады
        //If (mother) VNOV_M - weight rebenka
        z.addContent(new Element("OS_SLUCH").setText(Expert2FondUtil.calculateFondOsSluch(aEntry))); // Особый случай //TODO!!! ALL SLO_SSS
        if (!isPoliclinic)z.addContent(new Element("VB_P").setText(slCnt>1?"1":"0")); // Признак внутрибольничного перевода
        z.addContent(new Element("SL_TEMPLATE")); // Список случаев
      if (!isPoliclinic)  z.addContent(new Element("KD_Z").addContent(aEntry.getCalendarDays()+"")); // Продолжительность госпитализации
        z.addContent(new Element("DATE_Z_1").setText(startDate)); //Дата начала случая
        z.addContent(new Element("DATE_Z_2").setText(finishDate)); //Дата окончания случая
        z.addContent(new Element("RSLT").setText(aEntry.getFondResult().getCode())); // Результат обращения
        //z.addContent(new Element("RSLT_D")); // Результат диспансеризации
        z.addContent(new Element("ISHOD").setText(aEntry.getFondIshod().getCode())); // Исход случая.
        //z.addContent(new Element("P_OTK")); // Отказ от ДД
        //z.addContent(new Element("SGROUP")); // Социальная группа в ДД
        z.addContent(new Element("IDSP").setText(aEntry.getIDSP().getCode())); // Способ оплаты медицинской помощи (V010)
        z=add(z,"SUMV",aEntry.getCost()); // Сумма, выставленная к оплате =SUMV7+ SUMV8



        return z;
    }

    private Element add(Element el, String aFieldname, Object aValue) {
        el.addContent(new Element(aFieldname).setText(aValue!=null?aValue.toString():""));
        return el;
    }
    /** Создааем информацию по случаю мед. обслуживания (пока только стационар) для версии от июля 2018
     * aEntry - случай госпитализации
     * entriesList - строка с ИД СЛО
     * */
        private Element createSlElements(E2Entry aEntry, String entriesString, int cnt) {
        try {
            String entryType = aEntry.getEntryType();
            boolean isHosp = false, isVmp = false, isPoliclinic = false, isExtDisp = false, isPoliclinicKdo=false;
            if (entryType.equals(HOSPITALTYPE)) {isHosp=true;}
            else if (entryType.equals(VMPTYPE)) {isVmp=true;}
            else if (entryType.equals(POLYCLINICTYPE)) {isPoliclinic=true;}
            else if (entryType.equals(EXTDISPTYPE)) {isExtDisp=true;}
            else if (entryType.equals(POLYCLINICKDOTYPE)) {isPoliclinic=true;isPoliclinicKdo=true;}
            else {throw new IllegalStateException("UNKNOWN ENTRYTYPE");}
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
            pat.addContent(new Element("NOVOR").setText(makeNovorString(aEntry)))            ;
            zap.addContent(pat); //Добавили данные по пациенту

            String edCol;
            List<E2Entry> children=null;
            String isChild = aEntry.getIsChild()!=null&&aEntry.getIsChild()?"1":"0";
            if (isPoliclinic) {
                children = theManager.createQuery("from E2Entry where parentEntry_id=:id and (isDeleted is null or isDeleted='0')").setParameter("id",aEntry.getId()).getResultList();
                edCol=""+(children.size()>0?children.size():1);
            } else {
                edCol=aEntry.getBedDays() + ""; // Количество единиц оплаты мед. помощи
            }

            String[] slIds = entriesString.split(",");
            Element zSl = createZSl(aEntry,isPoliclinic,slIds.length);
            int indSl = zSl.indexOf(zSl.getChild("SL_TEMPLATE"));

            for (String slId:slIds) {

                Element sl = new Element("SL");
                E2Entry currentEntry = theManager.find(E2Entry.class,Long.valueOf(slId.trim()));
                Boolean isCancer = currentEntry.getIsCancer(), cancerSluch;
                E2CancerEntry cancerEntry = null;
                if (isCancer) {
                    cancerEntry= (E2CancerEntry) theManager.createQuery("from E2CancerEntry where entry=:entry").setParameter("entry",currentEntry).getResultList().get(0);
                    //if (cancerEntry!=null)isCancer=!cancerEntry.getMaybeCancer();
                }
                if (isCancer&& currentEntry.getVisitPurpose()!=null&&currentEntry.getVisitPurpose().getCode().equals("1.3")) {isCancer=false;}


                VocE2MedHelpProfile profile = currentEntry.getMedHelpProfile();
                String profileK = profile.getProfileK();

                String startDate = dateToString(currentEntry.getStartDate()), finishDate = dateToString(currentEntry.getFinishDate());
                sl=add(sl,"SL_ID",slId);
                sl=add(sl,"LPU_1","30000101");
                //PODR
                sl=addIfNotNull(sl,"NPR_MO",currentEntry.getDirectLpu()); //Направившее ЛПУ
                sl=addIfNotNull(sl,"NPR_DATE",currentEntry.getDirectDate()); //Дата направления на лечение ***
                sl=addIfNotNull(sl,"NPR_N",currentEntry.getTicket263Number()); // Номер направления на портале ФОМС
                sl=addIfNotNull(sl,"NPR_P",currentEntry.getPlanHospDate()); // Номер направления на портале ФОМС
                //PRN_MO
                if (isVmp) {
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
               if (isHosp){
                   sl=add(sl,"PROFIL_K",profileK); //Профиль коек/специальностей (V002_K)
               }
                sl=add(sl,"NHISTORY",currentEntry.getHistoryNumber()); //Номер истории болезни
                if (isHosp||isVmp) {
                    sl=add(sl,"P_PER",Expert2FondUtil.calculateFondP_PER(currentEntry)); //Признак перевода
                    sl=add(sl,"KD",currentEntry.getCalendarDays()); //Признак перевода
                }
                sl = setSluchDiagnosis(sl, currentEntry);
                sl=add(sl,"DATE_1",startDate); //Дата начала случая
                sl=add(sl,"DATE_2",finishDate); //Дата окончания случая
                sl=add(sl,"NPL",isNotNull(currentEntry.getNotFullPaymentReason())?currentEntry.getNotFullPaymentReason():"0"); // Неполный объем //TODO
                //DS_ONK
                //DN
                //DN_DP
                if (isHosp) {
                    sl=add(sl,"CODE_MES1",currentEntry.getKsg().getCode()); // Код КСГ, не для ВМП
                    //CODE_MES2
                    //sluch=addIfNotNull(sluch,"KSG_KRIT",currentEntry.getDopKritKSG());

                }
                if (isCancer){
                    if (cancerEntry==null) {log.error("Не найден раковый случай для записи с ИД"+currentEntry.getId());continue;}
                    Element onkSl = new Element("ONK_SL");
                    onkSl=addIfNotNull(onkSl,"DS1_T",cancerEntry.getOccasion());
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
                //REAB

                if (isHosp){//KSG_KGP
                    Element ksgKpg=new Element("KSG_KPG");
                    VocKsg ksg = currentEntry.getKsg();
                    ksgKpg=add(ksgKpg,"N_KSG",ksg.getCode());
                    ksgKpg=add(ksgKpg,"VER_KSG","2018");
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
                    if (difficultyEntryLinks.size()>0){
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
                String prvs = currentEntry.getFondDoctorSpecV021()!=null?currentEntry.getFondDoctorSpecV021().getCode():
                        (profile.getMedSpecV021()!=null?profile.getMedSpecV021().getCode():
                                (currentEntry.getFondDoctorSpec().getMedSpecV021()!=null?currentEntry.getFondDoctorSpec().getMedSpecV021().getCode():"V015_"+currentEntry.getFondDoctorSpec().getCode()));
                sl=add(sl,"PRVS",prvs); //Специальность лечащего врача
                sl=add(sl,"VERS_SPEC","V021");
                sl=add(sl,"IDDOKT",currentEntry.getDoctorSnils()); // СНИЛС лечащего врача
                sl=add(sl,"ED_COL",edCol);
                sl=add(sl,"TARIF",currentEntry.getCost());
                sl=add(sl,"SUM_M",currentEntry.getCost());

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
                    usl.addContent(new Element("PRVS_U").setText("0"));
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
                                (profile.getMedSpecV021()!=null?profile.getMedSpecV021().getCode():profile.getMedSpec().getMedSpecV021().getCode());
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
                    if (isPoliclinicKdo) { //Для КДО находим все услуги помимо дочерних визитов
                        List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id), servicedate,max(id) as cnt from EntryMedService where entry_id=:id group by medservice_id, servicedate").setParameter("id",aEntry.getId()).getResultList();
                        if (list.size()>0) {
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
                                usl.addContent(new Element("IDDOKT_U").setText(currentEntry.getDoctorSnils()));
                                usl.addContent(new Element("PRVS_U").setText(prvs));
                                usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                                usl.addContent(new Element("COD_DUSL_U").setText(medService.getCode()));
                                usl.addContent(new Element("KOL_USL").setText(ms[1].toString()));
                                usl.addContent(new Element("DATE_1_U").setText(serviceDate));
                                usl.addContent(new Element("DATE_2_U").setText(serviceDate));
                                usl.addContent(new Element("SUMV_USL").setText("0"));
                                sl.addContent(usl);

                            }
                            sl.getChild("ED_COL").setText(""+uslCnt);
                           // log.info("XML_KDO_CHILD="+uslCnt);
                        }
                    }
                } else if (isExtDisp) { //TODO
                 /*   List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id) as cnt from EntryMedService where entry_id=:id group by medservice_id").setParameter("id",aEntry.getId()).getResultList();
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
                    } */
                } else {
                    List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id) as cnt from EntryMedService where entry_id=:id group by medservice_id").setParameter("id",aEntry.getId()).getResultList();
                    if (list.size()>0) {
                        boolean first = true;
                        for (Object[] ms: list) {

                            uslCnt++;
                            VocMedService medService = theManager.find(VocMedService.class,Long.valueOf(ms[0].toString()));
                            Element usl = new Element("USL");
                            usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                            usl.addContent(new Element("LPU_U").setText("300001"));
                            usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                            usl.addContent(new Element("PROFIL_U").setText(profileK));
                            usl.addContent(new Element("IDDOKT_U").setText(aEntry.getDoctorSnils()));
                            usl.addContent(new Element("PRVS_U").setText("0"));
                            usl.addContent(new Element("DS_U").setText(sl.getChildText("DS1")));
                            usl.addContent(new Element("COD_DUSL_U").setText(medService.getCode()));
                            usl.addContent(new Element("KOL_USL").setText(ms[1].toString()));
                            usl.addContent(new Element("DATE_1_U").setText(startDate));
                            usl.addContent(new Element("DATE_2_U").setText(startDate));
                            usl.addContent(new Element("SUMV_USL").setText("0"));
                            if (first){
                                if (isCancer&&cancerEntry!=null) {
                                    if (cancerEntry.getMaybeCancer()) {
                                        List<E2CancerDirection> directions = theManager.createQuery("from E2CancerDirection where cancerEntry=:cancere").setParameter("cancer", cancerEntry).getResultList();
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
                                        onkUsl = add(onkUsl, "HIR_TIP", cancerEntry.getSurgicalType());
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
            }
            zSl.removeChild("SL_TEMPLATE");

            //Кажися, эти поля не используем
        //  if (isChild.equals("0")){  sluch = addIfNotNull(sluch, "VNOV_M", aEntry.getNewbornWeight());} // Вес при рождении (для мамы)
            //if (isNotNull(aEntry.getNewbornAmount())) { // Количество рожденных детей
             //   sluch.addContent(new Element("KOL_DET").setText(aEntry.getNewbornAmount() + ""));
           // }
           // sluch.addContent(new Element("PR_D_N").setText("0")); //TODO Признак диспансерного наблюдения
            // Количество единиц оплаты мед. помощи
            //      sluch.addContent(new Element("IDGOSP").setText(Expert2FondUtil.calculateFondIDGOSP(aEntry))); // Способ госпитализации
         //   sluch.addContent(new Element("KOEF").setText(aEntry.getTotalCoefficient()+"")); // Итоговый коэффициент в рамках объединенного тарифа  //TODO
         //   sluch.addContent(new Element("TARIF").setText(aEntry.getBaseTarif()+"")); // Объединенный тариф  //TODO


            //   comentSl.addContent(new Element("COD_OPL").setText("0")); //Код источника финансирования
            //   comentSl.addContent(new Element("PODVID").setText(aEntry.getPodvid() != null ? aEntry.getPodvid().getCode() : "---")); //Подвид медицинской помощи
            //   comentSl.addContent(new Element("CS").setText("0")); //Дополнительный признак // TODO
            //   if (aEntry.getUseLowerCoefficient() != null && aEntry.getUseLowerCoefficient()) {
            //       comentSl.addContent(new Element("CS2").setText("1")); //Второй дополнительный признак
            //   }
            // * comentSl.addContent(new Element("CS3").setText(aEntry.getPodvid().getCode())); //Третий дополнительный признак


            //   sluch.addContent(new Element("NAZ"));
            zap.addContent(zSl); //Добавляем информацию о случае в запись
            return zap;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("EEEE = " + e);
            throw new IllegalStateException("some unknown error!");
        }
    }

    /** Создааем информацию по случаю мед. обслуживания (пока только стационар) */
    private Element createZapElement(E2Entry aEntry, int cnt) {
        try {
            String entryType = aEntry.getEntryType();
            boolean isHosp = false, isVmp = false, isPoliclinic = false, isExtDisp = false, isPoliclinicKdo=false;
            if (entryType.equals(HOSPITALTYPE)) {isHosp=true;}
            else if (entryType.equals(VMPTYPE)) {isVmp=true;}
            else if (entryType.equals(POLYCLINICTYPE)) {isPoliclinic=true;}
            else if (entryType.equals(EXTDISPTYPE)) {isExtDisp=true;}
            else if (entryType.equals(POLYCLINICKDOTYPE)) {isPoliclinic=true;isPoliclinicKdo=true;}
            else {throw new IllegalStateException("UNKNOWN ENTRYTYPE");}
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
            pat.addContent(new Element("NOVOR").setText(makeNovorString(aEntry)))            ;
            zap.addContent(pat); //Добавили данные по пациенту

            String edCol;
            List<E2Entry> children=null;
            String isChild = aEntry.getIsChild()!=null&&aEntry.getIsChild()?"1":"0";
            if (isPoliclinic) {
                children = theManager.createQuery("from E2Entry where parentEntry_id=:id and (isDeleted is null or isDeleted='0')").setParameter("id",aEntry.getId()).getResultList();
                edCol=""+(children.size()>0?children.size():1);
            } else {
                edCol=aEntry.getBedDays() + ""; // Количество единиц оплаты мед. помощи
            }

            VocE2MedHelpProfile profile = aEntry.getMedHelpProfile();
            Element sluch = new Element("SLUCH");
            String startDate = dateToString(aEntry.getStartDate()), finishDate = dateToString(aEntry.getFinishDate());

            String profileK = profile.getProfileK();
            sluch.addContent(new Element("IDCASE").setText("" + cnt));
            sluch.addContent(new Element("USL_OK").setText(aEntry.getMedHelpUsl().getCode())); //дневной-круглосуточный-поликлиника
            sluch.addContent(new Element("VIDPOM").setText(aEntry.getMedHelpKind().getCode())); //TODO = сделать высчитываемым //тип мед. помощи
            sluch.addContent(new Element("FOR_POM").setText(isNotNull(aEntry.getIsEmergency()) ? (isPoliclinic?"2":"1") : "3")); //форма помощи V014
            sluch.addContent(new Element("NPR_MO").setText(aEntry.getDirectLpu())); //Номер МО, направившей на лечение
            //NPR_MO - номер принявшей МО
         //   sluch.addContent(new Element("EXTR").setText(aEntry.getIsEmergency() != null && aEntry.getIsEmergency() ? "2" : "1")); //Экстренность
            sluch.addContent(new Element("LPU").setText("1")); //ЛПУ лечения //TODO = сделать высчитываемым
            sluch.addContent(new Element("LPU_1").setText("30000101")); //Отделение лечения //TODO = сделать высчитываемым
            //PODR
            sluch.addContent(new Element("VBR").setText(isNotNull(aEntry.getIsMobilePolyclinic())?"1":"0")); //Признак мобильной бригады
            sluch.addContent(new Element("PROFIL").setText(profile.getCode())); //Профиль медицинской помощи (V002)
            sluch.addContent(new Element("PROFIL_K").setText(profileK)); //Профиль коек/специальностей (V002_K)
            sluch.addContent(new Element("DET").setText(isChild)); //Признак детского возраста
            sluch.addContent(new Element("NHISTORY").setText(aEntry.getHistoryNumber())); //Номер истории болезни
            sluch.addContent(new Element("P_PER").setText(isHosp||isVmp?Expert2FondUtil.calculateFondP_PER(aEntry):"1")); //Признак перевода
            sluch.addContent(new Element("DATE_1").setText(startDate)); //Дата начала случая
            sluch.addContent(new Element("DATE_2").setText(finishDate)); //Дата окончания случая
            sluch = setSluchDiagnosis(sluch, aEntry);
            sluch = addIfNotNull(sluch, "VNOV_D", aEntry.getNewbornWeight()); // Вес при рождении
            if (isHosp&&aEntry.getKsg()!=null) {
                sluch.addContent(new Element("CODE_MES1").setText(aEntry.getKsg().getCode())); // Код КСГ, не для ВМП
                sluch=addIfNotNull(sluch,"KSG_KRIT",aEntry.getDopKritKSG());
            }
            String prvs = aEntry.getFondDoctorSpec().getCode();
            sluch.addContent(new Element("RSLT").setText(aEntry.getFondResult().getCode())); // Результат обращения
            sluch.addContent(new Element("ISHOD").setText(aEntry.getFondIshod().getCode())); // Исход случая.
            sluch.addContent(new Element("PRVS").setText(prvs)); // специальность лечащего врача (V015)
            sluch.addContent(new Element("IDDOKT").setText(aEntry.getDoctorSnils())); // СНИЛС лечащего врача
            sluch.addContent(new Element("OS_SLUCH").setText(Expert2FondUtil.calculateFondOsSluch(aEntry))); // Особый случай
            if (isNotNull(aEntry.getNewbornAmount())) { // Количество рожденных детей
                sluch.addContent(new Element("KOL_DET").setText(aEntry.getNewbornAmount() + ""));
            }
            sluch.addContent(new Element("PR_D_N").setText("0")); //TODO Признак диспансерного наблюдения
            sluch.addContent(new Element("IDSP").setText(aEntry.getIDSP().getCode())); // Способ оплаты медицинской помощи (V010)
            sluch.addContent(new Element("ED_COL").setText(edCol)); // Количество единиц оплаты мед. помощи

      //      sluch.addContent(new Element("IDGOSP").setText(Expert2FondUtil.calculateFondIDGOSP(aEntry))); // Способ госпитализации
            sluch.addContent(new Element("NPL").setText(isNotNull(aEntry.getNotFullPaymentReason())?aEntry.getNotFullPaymentReason():"0")); // Неполный объем //TODO
            sluch=addIfNotNull(sluch,"N_NPR",aEntry.getTicket263Number()); // Номер направления на портале ФОМС

            if (isVmp) {
                sluch.addContent(new Element("TAL_N").setText(aEntry.getVMPTicketNumber())); // Номер выдачи ВМП
                if (aEntry.getVMPTicketDate()!=null) {
                    sluch.addContent(new Element("TAL_D").setText(dateToString(aEntry.getVMPTicketDate()))); // Дата выдачи талона ВМП
                } else {theManager.persist(new E2EntryError(aEntry,"NO_VMP_TICKET_DATE"));}
                if (aEntry.getVMPPlanHospDate()!=null) {
                    sluch.addContent(new Element("TAL_P").setText(dateToString(aEntry.getVMPPlanHospDate()))); // Дата планируемой госпитализации
                } else {theManager.persist(new E2EntryError(aEntry,"NO_VMP_PLANHOSP_DATE"));}

            }
            sluch.addContent(new Element("KOEF").setText(aEntry.getTotalCoefficient()+"")); // Итоговый коэффициент в рамках объединенного тарифа  //TODO
            sluch.addContent(new Element("TARIF").setText(aEntry.getBaseTarif()+"")); // Объединенный тариф  //TODO
            sluch.addContent(new Element("SUMV").setText("" + aEntry.getCost())); // Сумма, выставленная к оплате =SUMV7+ SUMV8

            int uslCnt = 0;
            if (aEntry.getReanimationEntry()!=null) {
                uslCnt++;
                Element usl = new Element("USL");
                usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                usl.addContent(new Element("PROFIL_U").setText(profileK));
                usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                usl.addContent(new Element("IDDOKT_U").setText(aEntry.getDoctorSnils()));
                usl.addContent(new Element("DATE_1_U").setText(startDate));
                usl.addContent(new Element("DATE_2_U").setText(finishDate));
                usl.addContent(new Element("DS_U").setText(sluch.getChildText("DS1")));
                usl.addContent(new Element("COD_DUSL_U").setText("B03.003.005"));
                usl.addContent(new Element("ED_COL_U").setText("1"));
                usl.addContent(new Element("PRVS_U").setText("0"));
                sluch.addContent(usl);

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
                    Element usl = new Element("USL");
                    usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                    usl.addContent(new Element("PROFIL_U").setText(child.getMedHelpProfile().getProfileK()));
                    usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                    usl.addContent(new Element("IDDOKT_U").setText(child.getDoctorSnils()));
                    usl.addContent(new Element("DATE_1_U").setText(uslDate));
                    usl.addContent(new Element("DATE_2_U").setText(uslDate));
                    usl.addContent(new Element("DS_U").setText(isNotNull(child.getMainMkb())?child.getMainMkb():sluch.getChildText("DS1")));
                    if (isPoliclinicKdo &&isNotNull(child.getMainService())) usl.addContent(new Element("COD_DUSL_U").setText(child.getMainService()));
                    usl.addContent(new Element("ED_COL_U").setText("1"));
                    usl.addContent(new Element("PRVS_U").setText(child.getFondDoctorSpec()!=null?child.getFondDoctorSpec().getCode():"NULL"));
                    sluch.addContent(usl);
                }
                if (isPoliclinicKdo) { //Для КДО находим все услуги помимо дочерних визитов
                    List<Object[]> list = theManager.createNativeQuery("select medservice_id||'' as ms, ''||count(id), servicedate,max(id) as cnt from EntryMedService where entry_id=:id group by medservice_id, servicedate").setParameter("id",aEntry.getId()).getResultList();
                    if (list.size()>0) {
                        for (Object[] ms: list) {
                            uslCnt++;
                            EntryMedService ems = theManager.find(EntryMedService.class,Long.valueOf(ms[3].toString()));
                            VocMedService medService = ems.getMedService(); //theManager.find(VocMedService.class,Long.valueOf(ms[0].toString()));
                            String serviceDate = dateToString(ems.getServiceDate());
                            Element usl = new Element("USL");
                            usl.addContent(new Element("IDSERV").setText(""+uslCnt));
                            usl.addContent(new Element("PROFIL_U").setText(profileK));
                            usl.addContent(new Element("DET_U").setText(isChild)); //Возраст на момент начала случая (<18 лет =1)
                            usl.addContent(new Element("IDDOKT_U").setText(aEntry.getDoctorSnils()));
                            usl.addContent(new Element("DATE_1_U").setText(serviceDate));
                            usl.addContent(new Element("DATE_2_U").setText(serviceDate));
                            usl.addContent(new Element("DS_U").setText(sluch.getChildText("DS1")));
                            usl.addContent(new Element("COD_DUSL_U").setText(medService.getCode()));
                            usl.addContent(new Element("ED_COL_U").setText(ms[1].toString()));
                            usl.addContent(new Element("PRVS_U").setText(prvs));
                            sluch.addContent(usl);

                        }
                        sluch.getChild("ED_COL").setText(""+uslCnt);
                        log.info("XML_KDO_CHILD="+uslCnt);
                    }
                }
            } else if (isExtDisp) { //TODO
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
            } else {
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
            }


            Element comentSl = new Element("COMENTSL"); //Региональные особенности
         //   comentSl.addContent(new Element("COD_OPL").setText("0")); //Код источника финансирования
         //   comentSl.addContent(new Element("PODVID").setText(aEntry.getPodvid() != null ? aEntry.getPodvid().getCode() : "---")); //Подвид медицинской помощи
         //   comentSl.addContent(new Element("CS").setText("0")); //Дополнительный признак // TODO
         //   if (aEntry.getUseLowerCoefficient() != null && aEntry.getUseLowerCoefficient()) {
         //       comentSl.addContent(new Element("CS2").setText("1")); //Второй дополнительный признак
         //   }
            // * comentSl.addContent(new Element("CS3").setText(aEntry.getPodvid().getCode())); //Третий дополнительный признак
            comentSl=addIfNotNull(comentSl,"COD_DUSL",aEntry.getMainService());
            if (isVmp) {
                comentSl.addContent(new Element("VID_HMP").setText(aEntry.getVMPKind())); // Вид ВМП
                comentSl.addContent(new Element("METOD_HMP").setText(aEntry.getVMPMethod())); // Метод ВМП
            }
            comentSl=addIfNotNull(comentSl,"ID_SLP",aEntry.getPatientDifficultyCodes()); //Сложность лечения пациента
            sluch.addContent(comentSl); //Добавляем региональные особенности в случай
         //   sluch.addContent(new Element("NAZ"));
            zap.addContent(sluch); //Добавляем информацию о случае в запись
            return zap;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("EEEE = " + e);
            throw new IllegalStateException("some unknown error!");
        }
    }

    /** Создаем заголовок для L файла (информация о пациентах) */
    private Element makeLTitle(Element root, Date aDocumentDate, String aFilename) {
        aDocumentDate = aDocumentDate != null ? aDocumentDate : new Date(new java.util.Date().getTime());
        Element zglv = new Element("ZGLV");
        zglv.addContent(new Element("VERSION").setText("2.1"));
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
    public boolean isNotNull(Object aField) {
        if (aField == null) return false;
        if (aField instanceof String) {
            String ss = (String) aField;
            return ss != null && !ss.trim().equals("");
        } else if (aField instanceof Boolean) {
            return (Boolean) aField;
        } else if (aField instanceof Long) {
            return ((Long) aField) > 0L;
        } else if (aField instanceof Date) {
            return aField!=null;
        } else if (aField instanceof BigDecimal) {
            return aField != null&&((BigDecimal) aField).compareTo(new BigDecimal(0))==1;
        } else if (aField instanceof BaseEntity) {
            return true;
        }else {
            throw new IllegalStateException("Нет преобразования для объекта " + aField);
        }
    }

    /**  Создаем MP файл с данными по стационару */
    public String makeMPFIle(Long aEntryListId, String aType, String aBillNumber, Date aBillDate, Long aEntryId, long aMonitorId) {return makeMPFIle(aEntryListId,aType,aBillNumber,aBillDate,aEntryId,false,aMonitorId);}
    public String makeMPFIle(Long aEntryListId, String aType, String aBillNumber, Date aBillDate, Long aEntryId, Boolean calcAllListEntry, long aMonitorId) {
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
            if (aEntryListId != null) {
                needCreateArchive = true;
                E2ListEntry listEntry = theManager.find(E2ListEntry.class, aEntryListId);
                periodDate = listEntry.getFinishDate();
                if (!isNotNull(aBillDate) || !isNotNull(aBillNumber)) {
                    return "Необходимо указать номер и дату счета!";
                }
                if (listEntry.getCheckDate()==null||listEntry.getCheckTime()==null) {
                    return "Необходимо выполнить проверку перед формированием пакета";
                }
            } else { //Сделано для теста.
                E2Entry entry = theManager.find(E2Entry.class, aEntryId);
                periodDate = entry.getFinishDate();
                aBillDate = aBillDate != null ? aBillDate : periodDate;
                aBillNumber = aBillNumber != null ? aBillNumber : "TEST";
                cntNumber = "00";

            }
            packetDateAdd = dateToString(periodDate, "yyMM");
            String packetType;

            if (aType.equals(HOSPITALTYPE) || aType.equals(POLYCLINICTYPE) || aType.equals(POLYCLINICKDOTYPE) || aType.equals(HOSPITALPEREVODTYPE)) {
                packetType = "Z";
            } else if (aType.equals(VMPTYPE)) {
                packetType = "T";
            } else if (aType.equals(EXTDISPTYPE)) {
                //Пока сделаем заглушку
                packetType="DV";
            }else {
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
            List<Long> patientIdsList = new ArrayList<Long>();
            BigDecimal totalSum = new BigDecimal(0);
            List<Element> zaps = new ArrayList<Element>();
            List<Element> perss = new ArrayList<Element>();
            int cnt = 0;
            List<BigInteger> records;
            if (aEntryId == null) {
                records = theManager.createNativeQuery("select id from E2Entry where " + (calcAllListEntry ? "" : "listEntry_id=" + aEntryListId + " and") + " (isDeleted is null or isDeleted='0') and (doNotSend is null or doNotSend='0') and billNumber=:billNumber and billDate=:billDate ")
                        .setParameter("billNumber", aBillNumber).setParameter("billDate", aBillDate).getResultList();
            } else {
                records = new ArrayList<BigInteger>();
                records.add(new BigInteger(aEntryId.toString()));
                //records = theManager.createNativeQuery("select id from E2Entry where id=:entryId ").setParameter("entryId", aEntryId).getResultList();
            }
            log.info("found " + records.size() + " records");
            IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Расчет цены случаев в звполнении") ;
            monitor = theMonitorService.startMonitor(aMonitorId,"Формирование xml файла. Размер: ",records.size());
            monitor.advice(1);

            E2Entry entry;
            int i = 0;
            for (BigInteger entryId : records) {
                i++;
                if (i % 100 == 0) {
                    log.info("Сформировано " + i + " записей в счете");
                    monitor.setText("Сформировано " + i + " записей в счете");

                }
                entry = theManager.find(E2Entry.class, entryId.longValue());
                if (entry.getDoNotSend() != null && entry.getDoNotSend()) {
                    continue;
                } //Есть галочка - не выгружать - не выгружаем
                StringBuilder err = new StringBuilder();
                Boolean isError = false;
                if (entry.getFondResult() == null) {
                    err.append("НЕ РАСЧИТАН РЕЗУЛЬТАТ СЛУЧАЯ;");
                    isError = true;
                }
                if (entry.getFondIshod() == null) {
                    err.append("НЕ РАСЧИТАН ИСХОД СЛУЧАЯ;");
                    isError = true;
                }
                if (entry.getMedHelpProfile() == null) {
                    err.append("НЕ УКАЗАН ПРОФИЛЬ МЕД. ПОМОЩИ;");
                    isError = true;
                }
                if (entry.getFondDoctorSpec() == null) {
                    err.append("НЕ РАСЧИТАНА СПЕЦИАЛЬНОСТЬ ВРАЧА;");
                    isError = true;
                }
                if (entry.getCost() == null) {
                    err.append("НЕ РАСЧИТАНА ЦЕНА СЛУЧАЯ;");
                    isError = true;
                }
                if (entry.getBaseTarif() == null) {
                    err.append("НЕ РАСЧИТАН БАЗОВЫЙ ТАРИФ;");
                    isError = true;
                }
                if (entry.getIDSP() == null) {
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
                if (entry.getEntryType().equals(POLYCLINICTYPE) && !isNotNull(entry.getMainMkb())) {
                    err.append("НЕ УКАЗАН ОСНОВНОЙ ДИАГНОЗ");
                    isError = true;
                }
                if (!isNotNull(entry.getHistoryNumber())) {
                    err.append("НЕ ЗАПОЛНЕН НОМЕР ИСТОРИИ БОЛЕЗНИ");
                    isError = true;
                }
                if (isError) {
                    E2EntryError error = new E2EntryError(entry, "NO_FOND_FIELDS:" + err.toString());
                    theManager.persist(error);
                    log.error("Запись с ИД " + entryId + " не будет выгружена в xml!");
                    //     entry.setDoNotSend(true); theManager.persist(entry);
                    continue;
                }
                cnt++;
                Long personId = entry.getExternalPatientId();
                if (!patientIdsList.contains(personId)) { //Если нет пациента в Л файле - добавляем.
                    perss.add(createPERSElement(entry));
                    patientIdsList.add(personId);
                }
                zaps.add(createZapElement(entry, cnt));
                totalSum = totalSum.add(entry.getCost());

            }
            log.info("ok, we made all, let's make files");
            monitor.setText("Формирование файла завершено, сохраняем архив");
            hRoot = makeHTitle(hRoot, periodDate, "H" + fileName, cnt, aBillNumber, aBillDate, totalSum,"3.0");
            if (aEntryListId != null) { //Меняем статус счета на "выставлен"
                E2Bill bill = new Expert2ServiceBean().getBillEntryByDateAndNumber(aBillNumber, dateToString(aBillDate, "dd.MM.yyyy"));
                if (bill != null) {
                    bill.setStatus((VocE2BillStatus) getActualVocBySqlString(VocE2BillStatus.class, "select id from VocE2BillStatus where code='SENT'"));
                    theManager.persist(bill);
                }
            }
            lRoot = makeLTitle(lRoot, periodDate, "L" + fileName);
            lRoot.addContent(perss);
            hRoot.addContent(zaps);
            String archiveName = packetType + fileName + ".MP";
            createXmlFile(hRoot, "H" + fileName);
            createXmlFile(lRoot, "L" + fileName);
            //   log.info("deb14");
            log.info("Время формирования файла (минут) = " + ((System.currentTimeMillis() - startStartDate.getTime())) / 60000);
            monitor.setText("Завершено. Время формирования файла (минут) = " + ((System.currentTimeMillis() - startStartDate.getTime())) / 60000);

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
            return "ERR";
        }
    }

    /*Делаем новую версию пакета ***/
    public String makeMPFIleV2(Long aEntryListId, String aType, String aBillNumber, Date aBillDate, Long aEntryId, Boolean calcAllListEntry, long aMonitorId) {

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
                    return "Необходимо выполнить проверку перед формированием пакета";
                }
            } else { //Сделано для теста.
              entry = theManager.find(E2Entry.class, aEntryId);
                periodDate = entry.getFinishDate();
                aBillDate = aBillDate != null ? aBillDate : periodDate;
                aBillNumber = aBillNumber != null ? aBillNumber : "TEST";
                cntNumber = "00";

            }
            packetDateAdd = dateToString(periodDate, "yyMM");
            String packetType;

            if (aType.equals(HOSPITALTYPE) || aType.equals(POLYCLINICTYPE) || aType.equals(POLYCLINICKDOTYPE) || aType.equals(HOSPITALPEREVODTYPE)) {
                packetType = "Z";
            } else if (aType.equals(VMPTYPE)) {
                packetType = "T";
            } else if (aType.equals(EXTDISPTYPE)) {
                //Пока сделаем заглушку
                packetType="DV";
            }else {
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
            List<Long> patientIdsList = new ArrayList<Long>();
            BigDecimal totalSum = new BigDecimal(0);
            List<Element> zaps = new ArrayList<Element>();
            List<Element> perss = new ArrayList<Element>();
            int cnt = 0;
            List<Object[]> records;
            if (aEntryId == null) {
                records = theManager.createNativeQuery("select list(''||id) as ids, externalparentid, count(id) as cnt from E2Entry where " + (calcAllListEntry ? "" : "listEntry_id=" + aEntryListId + " and") + " (isDeleted is null or isDeleted='0') " +
                        " and billNumber=:billNumber and billDate=:billDate " +
                        " and (isDeleted is null or isDeleted='0') " +
                        " and serviceStream!='COMPLEXCASE'" +
                        " and (doNotSend is null or doNotSend='0') group by externalparentid")
                        .setParameter("billNumber", aBillNumber).setParameter("billDate", aBillDate).getResultList();
            } else {
                records = new ArrayList<Object[]>();
                Object[] e= {""+aEntryId,entry!=null?entry.getExternalParentId():0L};
                records.add(e);
                //records = theManager.createNativeQuery("select id from E2Entry where id=:entryId ").setParameter("entryId", aEntryId).getResultList();
            }
            log.info("found " + records.size() + " records");
            IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Расчет цены случаев в звполнении") ;
            monitor = theMonitorService.startMonitor(aMonitorId,"Формирование xml файла. Размер: ",records.size());
            monitor.advice(1);

            int i = 0;
            /*Вот тут - 1 строка - список записей по 1 госпитализация */
            for (Object[] hosps : records) {
                int cntSlo = Integer.valueOf(hosps[2].toString());
                if (cntSlo>1) {
                    entry = calculateHospitalEntry(Long.valueOf(hosps[1].toString()), hosps[0].toString());
                } else {
                    entry = theManager.find(E2Entry.class,Long.valueOf(hosps[0].toString().trim()));
                }

                i++;
                if (i % 100 == 0) {
                    log.info("Сформировано " + i + " записей в счете");
                    monitor.setText("Сформировано " + i + " записей в счете");

                }
                if (entry.getDoNotSend() != null && entry.getDoNotSend()) {
                    continue;
                } //Есть галочка - не выгружать - не выгружаем
                StringBuilder err = new StringBuilder();
                Boolean isError = false;
                if (entry.getFondResult() == null) {
                    err.append("НЕ РАСЧИТАН РЕЗУЛЬТАТ СЛУЧАЯ;");
                    isError = true;
                }
                if (entry.getFondIshod() == null) {
                    err.append("НЕ РАСЧИТАН ИСХОД СЛУЧАЯ;");
                    isError = true;
                }
                if (entry.getMedHelpProfile() == null) {
                    err.append("НЕ УКАЗАН ПРОФИЛЬ МЕД. ПОМОЩИ;");
                    isError = true;
                }
                if (entry.getFondDoctorSpec() == null) {
                    err.append("НЕ РАСЧИТАНА СПЕЦИАЛЬНОСТЬ ВРАЧА;");
                    isError = true;
                }
                if (entry.getCost() == null) {
                    err.append("НЕ РАСЧИТАНА ЦЕНА СЛУЧАЯ;");
                    isError = true;
                }
                if (entry.getBaseTarif() == null) {
                    err.append("НЕ РАСЧИТАН БАЗОВЫЙ ТАРИФ;");
                    isError = true;
                }
                if (entry.getIDSP() == null) {
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
                if (entry.getEntryType().equals(POLYCLINICTYPE)) {
                    if (!isNotNull(entry.getMainMkb())){
                        err.append("НЕ УКАЗАН ОСНОВНОЙ ДИАГНОЗ");
                        isError = true;
                    }


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
                  //  E2EntryError error = new E2EntryError(entry, "NO_FOND_FIELDS:" + err.toString());
                   // theManager.persist(error);
                    log.error("Запись с ИД " + entry.getId() + " не будет выгружена в xml: "+err);
                    //     entry.setDoNotSend(true); theManager.persist(entry);
                    continue;
                }
                cnt++;
                Long personId = entry.getExternalPatientId();
                if (!patientIdsList.contains(personId)) { //Если нет пациента в Л файле - добавляем.
                    perss.add(createPERSElement(entry));
                    patientIdsList.add(personId);
                }

                zaps.add(createSlElements(entry, hosps[0].toString(),cnt));
              //  zaps.add(createZapElement(entry, cnt));
                totalSum = totalSum.add(entry.getCost());

            }
            log.info("ok, we made all, let's make files");
            monitor.setText("Формирование файла завершено, сохраняем архив");
            hRoot = makeHTitle(hRoot, periodDate, "H" + fileName, cnt, aBillNumber, aBillDate, totalSum,"3.1");
            if (aEntryListId != null) { //Меняем статус счета на "выставлен"
                E2Bill bill =theManager.find(E2Bill.class, theExpertService.getBillIdByDateAndNumber(aBillNumber, dateToString(aBillDate, "dd.MM.yyyy")));
                if (bill != null) {
                    bill.setStatus((VocE2BillStatus) getActualVocBySqlString(VocE2BillStatus.class, "select id from VocE2BillStatus where code='SENT'"));
                    theManager.persist(bill);
                }
            }
            lRoot = makeLTitle(lRoot, periodDate, "L" + fileName);
            lRoot.addContent(perss);
            hRoot.addContent(zaps);
            String archiveName = packetType + fileName + ".MP";
            createXmlFile(hRoot, "H" + fileName);
            createXmlFile(lRoot, "L" + fileName);
            //   log.info("deb14");
            log.info("Время формирования файла (минут) = " + ((System.currentTimeMillis() - startStartDate.getTime())) / 60000);
            monitor.setText("Завершено. Время формирования файла (минут) = " + ((System.currentTimeMillis() - startStartDate.getTime())) / 60000);

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
            return "ERR";
        }
    }

    /*Формируем случай с госпитализацией (не сохраняя в БД)*/
    private E2Entry calculateHospitalEntry(Long aHospitalMedcaseId, String aIds) {
        E2Entry hospital =null;//=theExpertService.cloneEntity(aEntry);
        List<E2Entry> slo = theManager.createQuery("from E2Entry where id in ("+aIds+") and externalParentId=:parent and serviceStream!='COMPLEXCASE' " +
                "and ((isDeleted is null or isDeleted='0') and (doNotSend is null or doNotSend='0')) order by startDate").setParameter("parent",aHospitalMedcaseId)
                .getResultList();
        if (slo.size()>0) {

            hospital = cloneEntity(slo.get(0),false);
            E2Entry lastEntry = slo.get(slo.size()-1);
            hospital.setFondResult(lastEntry.getFondResult());
            hospital.setFondIshod(lastEntry.getFondIshod());
            hospital.setCalendarDays(AgeUtil.calculateDays(hospital.getHospitalStartDate(), hospital.getHospitalFinishDate()));
            hospital.setCalendarDays(hospital.getCalendarDays()>0?hospital.getCalendarDays()+1:1);
            BigDecimal cost = new BigDecimal(0);
            for (E2Entry e:slo) {
                if (e.getCost()!=null){
                    cost=cost.add(e.getCost());
                } else {
                    log.warn("entry ID ="+e.getId()+" NO COST");
                }

            }
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
            e.printStackTrace();
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
        try {

            XMLOutputter outputter = new XMLOutputter();
            aFileName += ".xml";
            String outputFile = getWorkDir() + "/expert2xml/" + aFileName;
            OutputStreamWriter fwrt = new OutputStreamWriter(new FileOutputStream(outputFile), Charset.forName("windows-1251"));
            Document pat = new Document(aElement);
            outputter.setFormat(org.jdom.output.Format.getPrettyFormat().setEncoding("windows-1251"));
            outputter.output(pat, fwrt);
            fwrt.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Someshing happened strange!!!");
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
          //  e.printStackTrace();
            log.warn("it seems Windows");
            exec="\"C:\\Program Files\\7-Zip\\7z.exe\" a -tZIP ";
            try {
                Runtime.getRuntime().exec(exec+sb.toString());//arraCmd);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return archiveName;


    }




    /** Добавляем соответствие между профилем мед. помощи и профилем койки */
    //Для ServiceJS------------------------------------------------------------
    public void ____addMedHelpProfileBedType(Long aMedHelpId, Long aBedTypeId) {
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
                } catch (Exception e) {
                    e.printStackTrace();
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
        if (aList.isEmpty()) {return new ArrayList<String>();}
        List<String> diagnosisList = new ArrayList<String>();
        if (aRegType==null&&aPriority==null) {return null;}
        for (EntryDiagnosis diagnosis : aList) {
            if ( //проверить!
                    (aRegType==null||(aRegType!=null&&diagnosis.getRegistrationType()!=null&&aRegType.indexOf(diagnosis.getRegistrationType().getCode())>-1))
                            &&(aPriority==null||aPriority!=null&&diagnosis.getPriority()!=null&&aPriority.indexOf(diagnosis.getPriority().getCode())>-1)
                    ) {
                diagnosisList.add(diagnosis.getMkb().getCode());
            }
        }
        return diagnosisList;
    }

    /** Получаем значение из настроек экспертизы по коду */
    private String getExpertConfigValue(String aParameterName) {
        List<Object> ret = theManager.createNativeQuery("select value from Expert2Config where code=:code AND (isDeleted is null or isDeleted='0')").setParameter("code", aParameterName).getResultList();
        if (ret.isEmpty()) {
            throw new IllegalStateException("Не удалось найти настройку с кодом: " + aParameterName);
        }
        return ret.get(0).toString();
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
        String res = config.get(aConfigName, aDefaultValue);
        return res;

    }

    private Element setSluchDiagnosis(Element aElement, E2Entry aEntry) {
        /** Нюансы
         * 30.01.2018 Если есть диабет - указываем его как главный сопутствующий
         */

        List<EntryDiagnosis> list = theManager.createQuery(" from EntryDiagnosis where entry_id=:id").setParameter("id",aEntry.getId()).getResultList();
        String mainDiagnosisSqlAdd ;
        if (aEntry.getEntryType().equals(POLYCLINICTYPE)||aEntry.getEntryType().equals(POLYCLINICKDOTYPE)) {
            mainDiagnosisSqlAdd ="priority.code='1'";
        } else {
            mainDiagnosisSqlAdd ="(registrationType.code='4' or registrationType.code='3') and priority.code='1'";
        }
        List<EntryDiagnosis> mainDiagnosis = theManager.createQuery(" from EntryDiagnosis where entry_id=:id and "+mainDiagnosisSqlAdd).setParameter("id",aEntry.getId()).getResultList(); //клинические основные диагнозы

        List<String> otherDiagnosis = findDiagnosisCodes(list,null,"3"); // Сопутствующие
        List<String> napravitDiagnosis = findDiagnosisCodes(list,"1,2","3"); // Направительные
        if (!napravitDiagnosis.isEmpty()) {
            aElement.addContent(new Element("DS0").setText(napravitDiagnosis.get(0)));
        }

        if (mainDiagnosis.size()>0) {
            EntryDiagnosis ds = mainDiagnosis.get(0);
            aElement.addContent(new Element("DS1").setText(ds.getMkb().getCode()));

            String ds1Pr = "0";
            String illnesPrimary =ds.getIllnessPrimary();
            if (illnesPrimary!=null) {
                if (illnesPrimary.equals("3")){ds1Pr="1";}
                else if (illnesPrimary.equals("4")||illnesPrimary.equals("5")) {ds1Pr="2";}
            }
            aElement.addContent(new Element("DS1_PR").setText(ds1Pr));
            if (isNotNull(ds.getDopMkb())) {
                otherDiagnosis.add(0,ds.getDopMkb());
            }
            if (otherDiagnosis.size()>0) {
                for (String d: otherDiagnosis) {
                    if (d.startsWith("E")) { //Нашли диабет поставили его на первое место!
                        otherDiagnosis.remove(d);
                        otherDiagnosis.add(isNotNull(ds.getDopMkb())?1:0,d);
                        break;
                    }
                }
                aElement.addContent(new Element("DS2").setText(otherDiagnosis.get(0)));
                if (otherDiagnosis.size()>1) {
                    for (int i=1;i<otherDiagnosis.size()&&i<4;i++) {
                        aElement.addContent(new Element("DS2_"+i).setText(otherDiagnosis.get(i)));
                    }
                }
            }
        }

        return aElement;
    }
    private @PersistenceContext
    EntityManager theManager;
    private @EJB
    ILocalMonitorService theMonitorService;

    private @EJB IExpert2Service theExpertService;
}
