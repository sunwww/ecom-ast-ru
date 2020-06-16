package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.domain.*;
import ru.ecom.expert2.domain.voc.VocE2BillStatus;
import ru.ecom.expert2.domain.voc.VocE2EntrySubType;
import ru.ecom.expert2.domain.voc.VocE2Sanction;
import ru.ecom.expert2.domain.voc.federal.*;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
@Local(IExpert2ImportService.class)
@Remote(IExpert2ImportService.class)
public class Expert2ImportServiceBean implements IExpert2ImportService {
    private static final Logger LOG = Logger.getLogger(Expert2ImportServiceBean.class);
    private static final EjbEcomConfig CONFIG = EjbEcomConfig.getInstance() ;
    private static final String XMLDIR =CONFIG.get("expert2.input.folder","/opt/jboss-4.0.4.GAi/server/default/riams/expert2xml");
    private Date toDate(String aDate) throws ParseException {
        return new java.sql.Date(new SimpleDateFormat("yyy-MM-dd").parse(aDate).getTime());
    }

    private Element getPatient(List<Element> patients, String aKey) {
        for (Element patient: patients) {
            if (patient.getChildText("ID_PAC").equals(aKey)) {
                return patient;
            }
        }
        return null;
    }

    private Document getDocumentFromFile (String aPath, String aFilename, boolean deleteAfter) {
        try {
            if (aPath==null) {aPath="";}
            File file = new File(aPath+aFilename);
            Document doc =new SAXBuilder().build(file);
            if (deleteAfter) file.delete();
            return doc;
        } catch (JDOMException | IOException e) {
            LOG.error("Ошибка формирования документа из файла "+aFilename,e);
            throw new IllegalStateException("Ошибка открытия файла "+aPath+" "+aFilename);
        }
    }

    /*Импортируем ответ ФЛК от фонда*/
    public void importFlkAnswer(long monitorId, String aFilename, Long aListEntryId) {
        LOG.info("start import FLK="+aFilename);
        IMonitor monitor = startMonitor(monitorId,"Импортируем ФЛК "+aFilename+".");
        Document root = getDocumentFromFile(XMLDIR+"/",aFilename,true);
        XMLOutputter out = new XMLOutputter();
        Element rootElement = root.getRootElement();
        List<Element> defs = rootElement.getChildren("PR");
        theManager.createNativeQuery("update e2entry set isdefect='0' where listentry_id=:id and (isdeleted is null or isdeleted='0')")
                .setParameter("id",aListEntryId).executeUpdate();
        LOG.info("clean defect before flk "+defs.size());
        monitor.setText("ФЛК "+aFilename+", записей для расчета: "+defs.size());
        int cnt = 0;
        for (Element el:defs) {
            String entryId = el.getChildText("N_ZAP");
            if(cnt%100 == 0 && isMonitorCancel(monitor,"Импортировано записей: "+cnt)) return ;
            if (isNotNull(entryId)) {
                E2Entry entry = theManager.find(E2Entry.class, Long.parseLong(entryId));
                entry.setFondComment(out.outputString(el));
                entry.setIsDefect(true);
                theManager.persist(entry);
                theManager.persist(new E2EntrySanction(entry,null, "FLK_ERR",true,"ФЛК"));
                cnt++;
            }
        }
        monitor.finish("Импорт ФЛК закончен: "+cnt + " записей из "+defs.size());
    }

    /*Импортируем файл с элмед*/
    public void importElmed(long monitorId, String aXmlFilename) {
        try { //делаем только ДД
            Document doc = getDocumentFromFile(XMLDIR+"/",aXmlFilename,false);
            if (doc == null) {
                LOG.error("Не удается открыть файл "+aXmlFilename);
                return;
            }

            E2ListEntry le = new E2ListEntry();
            le.setName("ELMED_"+aXmlFilename);
            String lpuCode="300052";
            le.setLpuOmcCode(lpuCode);
            theManager.persist(le);
            Element root = doc.getRootElement();
            SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
            int i = 0;

            List<Element> zaps = root.getChildren("ZAP");
            E2Entry e;
            LOG.info("start import elmed "+aXmlFilename+", found "+zaps.size()+" records");
            IMonitor monitor = startMonitor(monitorId,"Импортируем записи с ЭлМед-а {"+aXmlFilename+"}. Всего записей: "+zaps.size());
            for (Element zap : zaps) {
                try {
                    i++;
                    if (i%100==0 && isMonitorCancel(monitor,"Импорт "+i+" записей")) break;
                    e = new E2Entry();
                    Element sl = zap.getChild("SL");
                    e.setListEntry(le);
                    e.setEntryType("EXTDISP");
                    e.setLpuCode(lpuCode);
                    e.setExternalPatientId(Long.parseLong(zap.getChildText("ID_PAC")));
                    e.setMedPolicyType(zap.getChildText("VPOLIS"));
                    e.setMedPolicyNumber(zap.getChildText("NPOLIS"));
                    e.setInsuranceCompanyCode(zap.getChildText("SMO"));
                    Date startDate = toDate(zap.getChildText("DATE_Z_1"));
                    Date finishDate = toDate(zap.getChildText("DATE_Z_2"));
                    e.setLastname(zap.getChildText("FAM"));
                    e.setFirstname(zap.getChildText("IM"));
                    e.setMiddlename(zap.getChildText("OT"));
                    e.setBirthDate(toDate(zap.getChildText("DR")));
                    e.setSex(zap.getChildText("W"));
                    if (isNotNull(zap.getChildText("FAM_P"))) {
                        e.setKinsmanLastname(zap.getChildText("FAM_P"));
                        e.setKinsmanFirstname(zap.getChildText("IM_P"));
                        e.setKinsmanMiddlename(zap.getChildText("OT_P"));
                        e.setKinsmanBirthDate(toDate(zap.getChildText("DR_P")));
                        e.setKinsmanSex(zap.getChildText("W_P"));
                        e.setKinsmanSnils(zap.getChildText("SNILS"));
                    } else {
                        e.setPatientSnils(zap.getChildText("SNILS"));
                    }
                    e.setOkatoReg(zap.getChildText("OKATOG"));
                    e.setCommonNumber(zap.getChildText("ENP"));
                    e.setPassportType(zap.getChildText("DOCTYPE"));
                    e.setPassportSeries(zap.getChildText("DOCSER"));
                    e.setPassportNumber(zap.getChildText("DOCNUM"));

                    e.setStartDate(startDate);
                    e.setFinishDate(finishDate);
                    e.setServiceStream("OBLIGATORYINSURANCE");
                    e.setMedHelpUsl(getVocByCode(VocE2FondV006.class, finishDate, zap.getChildText("USL_OK")));
                    e.setMedHelpKind(getVocByCode(VocE2FondV008.class, finishDate, zap.getChildText("VIDPOM")));
                    e.setIsEmergency(!zap.getChildText("FOR_POM").equals("3"));
                    e.setIsMobilePolyclinic(false);
                    e.setHistoryNumber(sl.getChildText("NHISTORY"));
                    e.setMainMkb(sl.getChildText("DS1"));
                    e.setFondResult(getVocByCode(VocE2FondV009.class, finishDate, zap.getChildText("RSLT")));
                    e.setDispResult(getVocByCode(VocE2FondV017.class, finishDate, zap.getChildText("RSLT_D")));
                    e.setFondIshod(getVocByCode(VocE2FondV012.class, finishDate, zap.getChildText("ISHOD")));
                    VocE2FondV021 medSpec =getVocByCode(VocE2FondV021.class, finishDate, sl.getChildText("PRVS"));
                    e.setMedHelpProfile(medSpec.getPolicProfile());
                    e.setFondDoctorSpecV021(medSpec);
                    e.setDoctorSnils(sl.getChildText("IDDOKT"));
                    /*new flds*/
                    String doctorCode = sl.getChildText("TABNOM");
                    if (isNotNull(doctorCode)){
                        PersonalWorkFunction doctor = getWorkFuntionByDoctorCode(doctorCode);
                        if (doctor!=null) {
                            e.setDoctorName(doctor.getWorkFunctionInfo());
                            MisLpu dep = doctor.getWorker().getLpu();
                            e.setDepartmentId(dep.getId());
                            e.setDepartmentName(dep.getName());
                            e.setDepartmentCode(dep.getCodeDepartment());
                        } else {
                            e.setDoctorName(doctorCode);
                        }
                    } else {
                        LOG.error("Нет докутора в ДД");
                        theManager.persist(e);
                        monitor.setText("Нет докутора в записи "+e.getId());
                        theManager.persist(new E2EntryError(e,"NO_DISP_DOCTOR"));
                    }

                    e.setVisitPurpose(getVocByCode(VocE2FondV025.class, finishDate, sl.getChildText("P_CEL")));
                    e.setIDSP(getVocByCode(VocE2FondV010.class, finishDate, sl.getChildText("IDSP"))); //TODO
                    //    e.setTotalCoefficient(new BigDecimal(sluch.getChildText("KOEF")));
                    //    e.setCost(new BigDecimal(sluch.getChildText("SUMV")));
                    String entryTypeCode = "EXTDISP_"+zap.getChildText("VID_SLUCH");
                    //VocE2VidSluch vidSluch = getVocByCode(VocE2VidSluch.class,finishDate,);
                    VocE2EntrySubType subType = getActualVocByCode(VocE2EntrySubType.class,null,"code='"+entryTypeCode+"'");
                    e.setSubType(subType);
                    e.setFileType(subType.getFileType());
                    e.setVidSluch(subType.getVidSluch());
                    e.setIDSP(getVocByCode(VocE2FondV010.class, finishDate,zap.getChildText("IDSP")));
                    theManager.persist(e);
                    List<Element> uslList = sl.getChildren("USL");
                    for (Element usl: uslList) {
                        VocMedService vms = getVocByCode(VocMedService.class,finishDate,usl.getChildText("VID_VME"));
                        EntryMedService ms = new EntryMedService(e, vms);
                        ms.setDoctorSnils(usl.getChildText("IDDOKT_U"));
                        ms.setCost(BigDecimal.ZERO);
                        try{
                            ms.setServiceDate(toDate(usl.getChildText("DATE_IN")));
                        } catch (NullPointerException | ParseException ee ) {
                            LOG.warn("no serviceDate");
                        }
                        ms.setDoctorSpeciality(isNotNull(usl.getChildText("PRVS_U")) && !"0".equals(usl.getChildText("PRVS_U"))
                                ? getVocByCode(VocE2FondV021.class, finishDate, usl.getChildText("PRVS_U")) : medSpec);
                        theManager.persist(ms);
                    }

                } catch (Exception e1) {
                    monitor.setText("Не удалось загрузить запись: "+new XMLOutputter().outputString(zap));
                    LOG.error(i+" Не удалось загрузить запись:"+new XMLOutputter().outputString(zap));
                }
            }
            LOG.info("finish import elmed");
            monitor.finish("Импорт записей с ЭлМед-а успешно завершен!");

        } catch (Exception e){
            LOG.error(e.getMessage(),e);
        }
    }

    private HashMap<String, PersonalWorkFunction> DOCTORLIST = new HashMap<>();
    private PersonalWorkFunction getWorkFuntionByDoctorCode(String tabnom) {
        //находим рабочую функцию по табельному номеру
        if (DOCTORLIST.containsKey(tabnom)) {
            return DOCTORLIST.get(tabnom);
        }
        List<PersonalWorkFunction> personalWorkFunctions =  theManager.createQuery("from PersonalWorkFunction where code=:code").setParameter("code",tabnom).getResultList();
        PersonalWorkFunction workFunction = personalWorkFunctions.isEmpty() ? null : personalWorkFunctions.get(0);
        DOCTORLIST.put(tabnom,workFunction);
        return workFunction;
    }

    public String getConfigValue (String aKeyName, String aDefaultName) {
        return CONFIG.get(aKeyName,aDefaultName);
    }

    /** Загружаем MP файл (ответ от фонда)
     * импорт версии от 2020 года
     * */

    private HashMap<String, VocE2Sanction> sanctionMap = new HashMap<>();

    /*Загружаем ответ от фонда (версия файла {3.1}
    * Добавляем импорт целиком пакета (*.paket)
    * */
    public void importFondMPAnswer(long monitorId, String aMpFilename) {
        IMonitor monitor = startMonitor(monitorId,"Импорт дефектов. Файл: "+aMpFilename);
        importFondMPAnswer(monitorId,aMpFilename,monitor);
        monitor.finish("Закончили импорт дефектов");
    }

    private void importFondMPAnswer(long monitorId, String aMpFilename, IMonitor monitor) {
        try {
            LOG.info("filename = " + aMpFilename);
            if (aMpFilename.toUpperCase().endsWith(".PAKET")) { //like B300026_200205.paket
                //распаковываем в папку. Потом проходимся по каждому файлу в папке
                File unpackedDir = new File(unZip(aMpFilename));
                LOG.info("unpack paket " + unpackedDir.getAbsolutePath());
                for (File mpFile : unpackedDir.listFiles()) {
                    String filename = mpFile.getName();
                    if (filename.toUpperCase().endsWith(".MP")) {
                        monitor.setText("Импорт дефектов. Импортируем файл: "+filename);
                        importFondMPAnswer(monitorId, unpackedDir.getName() + "/" + filename, monitor);
                    }
                }
            } else {
                String dir = unZip(aMpFilename);
                String hFilename = "H" + aMpFilename.substring(aMpFilename.indexOf("M")).replace(".MP", ".XML");
                File hFile = new File(dir + "/" + hFilename);
                Document doc = new SAXBuilder().build(hFile);
                org.jdom.Element root = doc.getRootElement();
                String ver = root.getChild("ZGLV").getChildText("VERSION");
                if ("3.0".equals(ver)) {
                    throw new IllegalStateException("Импорт в старом формате более не поддерживается!");
                }
                List<Element> zaps = root.getChildren("ZAP");
                Element schet = root.getChild("SCHET");
                String nSchet = schet.getChildText("NSCHET");
                String dSchet = schet.getChildText("DSCHET");
                SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat toFormat = new SimpleDateFormat("dd.MM.yyyy");
                java.sql.Date billDate = new java.sql.Date(fromFormat.parse(dSchet).getTime());
                E2Bill bill = theManager.find(E2Bill.class, theExpertService.getBillIdByDateAndNumber(nSchet, toFormat.format(billDate)));
                bill.setStatus(getActualVocByCode(VocE2BillStatus.class, null, "code='PAID'"));

                int i = 0;
                if (isMonitorCancel(monitor, "Найдено записей для импорта: " + zaps.size())) return;

                BigDecimal totalSum = new BigDecimal("0");

                for (Element zap : zaps) {
                    i++;
                    if (i % 100 == 0 && isMonitorCancel(monitor, "Загружено записей: " + i)) break;
                    Element zsl = zap.getChild("Z_SL");
                    List<Element> slList = zsl.getChildren("SL");
                    Element pac = zap.getChild("PACIENT");
                    boolean isComplexCase = false;
                    for (Element sl : slList) {
                        if (isComplexCase) break;
                        Element slId = sl.getChild("SL_ID");
                        Long entryId = Long.parseLong(slId.getText());
                        E2Entry entry = theManager.find(E2Entry.class, entryId);
                        if (entry == null || Boolean.TRUE.equals(entry.getIsDeleted())) {
                            LOG.warn("Ошибка при импорте ответа от фонда - не найдена запись с ИД = " + entryId);
                            continue;
                        }
                        if (entry.getParentEntry() != null) {
                            entry = entry.getParentEntry();
                            isComplexCase = true;
                        }
                        theManager.createNativeQuery("delete from E2EntrySanction where entry_id=:entryId").setParameter("entryId", entryId).executeUpdate();
                        entry.setBillNumber(nSchet);
                        entry.setBillDate(billDate);
                        entry.setBill(bill);

                        //Расчет цены случая ФОМС
                        Element commentCalc = sl.getChild("D_COMMENT_CALC");
                        if (commentCalc != null && commentCalc.getChild("root") != null) {

                            Element ебаныйРусскийТэг = commentCalc.getChild("root");
                            List<Element> ерт = ебаныйРусскийТэг.getChildren();
                            StringBuilder commentError = new StringBuilder();
                            for (Element еб : ерт) {
                                commentError.append(еб.getName()).append(": ").append(еб.getText()).append("\n");
                            }
                            entry.setFondComment(commentError.toString());
                        } else {
                            entry.setFondComment("");
                        }

                        //Добавляем сведения о санкциях
                        if (zsl.getChild("SANK_IT") != null && !zsl.getChildText("SANK_IT").equals("0.00")) { //
                            List<Element> sanks = zsl.getChildren("SANK");
                            ArrayList<String> sanks1 = new ArrayList<>();
                            for (Element sank : sanks) {
                                String key = sank.getChildText("S_OSN");
                                String dopCode = sank.getChildText("S_DOP");
                                if (!sanks1.contains(dopCode)) {
                                    if (!sanctionMap.containsKey(key)) {
                                        sanctionMap.put(key, getActualVocByCode(VocE2Sanction.class, null, "osn='" + key + "'"));
                                    }
                                    String comment = sank.getChildText("SL_ID") + " " + sank.getChildText("S_COM");
                                    theManager.persist(new E2EntrySanction(entry, sanctionMap.get(key), dopCode, false, comment));
                                    sanks1.add(dopCode);
                                }
                            }
                            entry.setIsDefect(true);
                        } else {
                            entry.setIsDefect(false);
                            entry.setFondComment(null);
                        }
                        totalSum = totalSum.add(entry.getCost());
                        Element prikMo = zsl.getChild("D_PRIK_MO");
                        if (prikMo!=null && isNotNull(prikMo.getText())) { //Проставляем информацию о прик. ЛПУ для формирования МУР
                            entry.setAttachedLpu(prikMo.getText());
                            entry.setAddGroupFld("МУР");

                        } else {
                            entry.setAttachedLpu("");
                            entry.setAddGroupFld("");
                        }
                        theManager.persist(entry);
                    }

                }
                LOG.info("По счету №" + bill.getBillNumber() + " сумма = " + totalSum);
                monitor.setText("По счету №" + bill.getBillNumber() + " сумма = " + totalSum);
                bill.setSum(totalSum);
                theManager.persist(bill);
                LOG.info("Обновление MP закончено!");
        }

        }  catch (Exception e) {
            monitor.error(e.getMessage(),e);
            LOG.error(e.getMessage(),e);
        }
    }

    /** распаковка архива */
    private String unZip(String aZipFile){
        StringBuilder sb = new StringBuilder();
        String outputDir = XMLDIR + "/"+aZipFile+"-"+System.currentTimeMillis();
        sb.append("7z e ").append(XMLDIR).append("/").append(aZipFile).append(" -o").append(outputDir) ;

        try {
            Runtime.getRuntime().exec(sb.toString());//arraCmd);
            Thread.sleep(5000); //Заснем, чтобы точно всё распаковалось
        } catch (Exception e) {
            LOG.warn("Похоже, у нас Виндовс. Попробуем запустить 7-zip");
            sb = new StringBuilder().append("\"C:\\Program Files\\7-Zip\\7z.exe\" e ").append(XMLDIR).append("\\").append(aZipFile).append(" -o").append(outputDir);
            try {
                LOG.info("sb="+sb+", dir="+outputDir);
                Runtime.getRuntime().exec(sb.toString());
                Thread.sleep(5000); //Заснем, чтобы точно всё распаковалось
            } catch (Exception e1) {LOG.warn("NE SMOG :-(");}
        }
        return outputDir;


    }
    private String createDir(String aDirShortName) {
        File dir = new File(XMLDIR+"/"+aDirShortName);
        if (dir.exists()&&!dir.isDirectory()) {
            LOG.error("Невозможно создать папку - с таким именем уже существует файл");
            return null;
        } else if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getAbsolutePath();
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
            return ((BigDecimal) aField).compareTo(BigDecimal.ZERO) > 0;
        }else {
            throw new IllegalStateException("Нет преобразования для объекта " + aField);
        }
    }
    private <T> T getActualVocByCode(Class aClass, Date aActualDate, String aSqlAdd) {
            String sql = " from "+aClass.getName()+" where ";
            List<T> list;
            if (aActualDate!=null) {
                sql+=" :actualDate between startDate and finishDate";
                if (isNotNull(aSqlAdd)) {
                    sql+=" and "+aSqlAdd;
                }
                list = theManager.createQuery(sql).setParameter("actualDate",aActualDate).getResultList();
                if (list.isEmpty()){
                    list = theManager.createQuery("from " + aClass.getName() + " where finishDate is null and " + aSqlAdd ).getResultList();
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

    private <T> T getVocByCode(Class aClass, Date aActualDate, String aCode) {
        if (!isNotNull(aCode)) {return null;}
        String sql = " from "+aClass.getName()+" where ";
        List<T> list;
        if (aActualDate!=null) {
            sql += " :actualDate between startDate and finishDate and";
        }
        sql+=" code='"+aCode+"'";
        list = theManager.createQuery(sql).setParameter("actualDate",aActualDate).getResultList();
        if (list.isEmpty()){
            list = theManager.createQuery("from " + aClass.getName() + " where finishDate is null and code=:code").setParameter("code",aCode).getResultList();
        }

        if (list.isEmpty()) {
            LOG.error("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием "+sql);
            return null;
        } else if (list.size() > 1) {
            LOG.error("Найдено несколько действующих значений справочника " + aClass.getCanonicalName()+" с условием "+sql);
            return null;
        }
        return list.get(0);
    }

    /**Выводим сообщение в монитор. Возвращаем - отменен ли монитор*/
    private boolean isMonitorCancel(IMonitor aMonitor, String aMonitorText) {
        aMonitor.setText(aMonitorText);
        LOG.info(aMonitorText);
        if (aMonitor.isCancelled()) {
            aMonitor.setText("Проверка прервана пользователем");
            return true;
        }
        return false;
    }

    private IMonitor startMonitor(long monitorId, String message) {
        return theMonitorService.startMonitor(monitorId,message,999);
    }
    private @PersistenceContext
    EntityManager theManager;
    private @EJB
    IExpert2Service theExpertService;

    private @EJB
    ILocalMonitorService theMonitorService;
}