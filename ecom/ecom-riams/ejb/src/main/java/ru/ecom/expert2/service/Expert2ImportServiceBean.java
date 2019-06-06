package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.domain.E2Bill;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.E2EntrySanction;
import ru.ecom.expert2.domain.E2ListEntry;
import ru.ecom.expert2.domain.voc.VocE2BillStatus;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.VocE2Sanction;
import ru.ecom.expert2.domain.voc.federal.*;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

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
import java.util.HashMap;
import java.util.List;

@Stateless
@Local(IExpert2ImportService.class)
@Remote(IExpert2ImportService.class)
public class Expert2ImportServiceBean implements IExpert2ImportService {
    private static final Logger LOG = Logger.getLogger(Expert2ImportServiceBean.class);
    private static final EjbEcomConfig CONFIG = EjbEcomConfig.getInstance() ;
    private static final String XMLDIR =CONFIG.get("expert2.input.folder","/opt/jboss-4.0.4.GAi-postgres/server/default/riams/expert2xml");
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
    public String importFlkAnswer(String aFilename) {//, String aBillNumber, Date aBillDate) {
        LOG.info("start import FLK="+aFilename);
        Document root = getDocumentFromFile(XMLDIR+"/",aFilename,true);
        XMLOutputter out = new XMLOutputter();
        Element rootElement = root.getRootElement();
        List<Element> defs = rootElement.getChildren("PR");
        int cnt = 0;
        for (Element el:defs) {
            String entryId =el.getChildText("N_ZAP");
            if (isNotNull(entryId)) {
                E2Entry entry = theManager.find(E2Entry.class, Long.valueOf(entryId));
                entry.setFondComment(out.outputString(el));
                entry.setIsDefect(true);
                theManager.persist(entry);
                cnt++;
            }

        }
        return "ФЛК: Импортировано " + cnt + " записей из "+defs.size();

    }

    /*Создаем заполнение из MP файла*/
    public String createEntryByFondXml(String aMpFilename) {
        try {
            E2ListEntry le = new E2ListEntry();
            le.setName("IMPORT_"+aMpFilename);
            theManager.persist(le);
            String dir = unZip(aMpFilename);
            String hFilename = aMpFilename.replace(".MP",".XML");
            String lFilename="L"+hFilename.substring(1);
            hFilename="H"+hFilename.substring(1);
            Document doc = getDocumentFromFile(dir+"/",hFilename,false);
            if (doc == null) return "Не удается открыть файл "+hFilename;
            Element root = doc.getRootElement();
            String ver = root.getChild("ZGLV").getChildText("VERSION");
            SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
            Document patDoc = new SAXBuilder().build(new File(dir+"/"+lFilename));
            List<Element> patients = patDoc.getRootElement().getChildren("PERS");
            int i = 0;
            if (ver.equals("3.0")) { //Импорт ответа в старом формате
                //Только поликлиника.
                Element eBill = root.getChild("SCHET");
                String billDate = eBill.getChildText("DSCHET");
                String billNumber = eBill.getChildText("NSCHET");
                E2Bill bill = theExpertService.getBillEntryByDateAndNumber(billNumber, toFormat.parse(billDate));
                if (eBill.getChild("SUMMAP")!=null) {
                    BigDecimal sum = new BigDecimal(eBill.getChildText("SUMMAP"));
                    bill.setSum(sum);
                    theManager.persist(bill);
                }

                List<Element> zaps = root.getChildren("ZAP");
                E2Entry e;
                LOG.info("start import "+hFilename+", found "+zaps.size()+" records");
                for (Element zap : zaps) {
                    if (i%100==0) {LOG.info("improt " +i+" records");}
                    i++;
                    e = new E2Entry();
                    e.setListEntry(le);
                    Element pat = zap.getChild("PACIENT");
                    Element sluch = zap.getChild("SLUCH");
                    e.setMedPolicyType(pat.getChildText("VPOLIS"));
                    e.setMedPolicyNumber(pat.getChildText(pat.getChildText("NPOLIS")));
                    e.setInsuranceCompanyCode(pat.getChildText("SMO"));
                    Date startDate = toDate(sluch.getChildText("DATE_1"));
                    Date finishDate = toDate(sluch.getChildText("DATE_2"));
                    pat = getPatient(patients, pat.getChildText("ID_PAC"));
                    if (pat == null) continue;
                    e.setLastname(pat.getChildText("FAM"));
                    e.setFirstname(pat.getChildText("IM"));
                    e.setMiddlename(pat.getChildText("OT"));
                    e.setBirthDate(toDate(pat.getChildText("DR")));
                    e.setSex(pat.getChildText("W"));
                    if (isNotNull(sluch.getChildText("FAM_P"))) {
                        e.setKinsmanLastname(pat.getChildText("FAM_P"));
                        e.setKinsmanFirstname(pat.getChildText("IM_P"));
                        e.setKinsmanMiddlename(pat.getChildText("OT_P"));
                        e.setKinsmanBirthDate(toDate(pat.getChildText("DR_P")));
                        e.setKinsmanSex(pat.getChildText("W_P"));
                        e.setKinsmanSnils(pat.getChildText("SNILS"));
                    } else {
                        e.setPatientSnils(pat.getChildText("SNILS"));
                    }
                    e.setOkatoReg(pat.getChildText("OKATOG"));
                    e.setCommonNumber(pat.getChildText("ENP"));
                    e.setPassportType(pat.getChildText("DOCTYPE"));
                    e.setPassportSeries(pat.getChildText("DOCSER"));
                    e.setPassportNumber(pat.getChildText("DOCNUM"));

                    e.setStartDate(startDate);
                    e.setFinishDate(finishDate);
                    e.setServiceStream("OBLIGATORYINSURANCE");
                    e.setBill(bill);
                    e.setBillNumber(bill.getBillNumber());
                    e.setBillDate(bill.getBillDate());
                    e.setMedHelpUsl(getVocByCode(VocE2FondV006.class, finishDate, sluch.getChildText("USL_OK")));
                    e.setMedHelpKind(getVocByCode(VocE2FondV008.class, finishDate, sluch.getChildText("VIDPOM")));
                    e.setIsEmergency(!sluch.getChildText("FOR_POM").equals("3"));
                    e.setDirectLpu(sluch.getChildText("NPR_MO"));
                    e.setIsMobilePolyclinic(sluch.getChildText("VBR").equals("1"));
                    VocE2MedHelpProfile profile = getActualVocByCode(VocE2MedHelpProfile.class, finishDate, "profilek='" + sluch.getChildText("PROFIL_K") + "'");
                    e.setMedHelpProfile(profile);
                    e.setHistoryNumber(sluch.getChildText("NHISTORY"));
                    e.setMainMkb(sluch.getChildText("DS1"));
                    e.setFondResult(getVocByCode(VocE2FondV009.class, finishDate, sluch.getChildText("RSLT")));
                    e.setFondIshod(getVocByCode(VocE2FondV012.class, finishDate, sluch.getChildText("ISHOD")));
                    e.setFondDoctorSpecV021(getVocByCode(VocE2FondV021.class, finishDate, sluch.getChildText("PRVS")));
                    e.setDoctorSnils(sluch.getChildText("IDDOKT"));
                    e.setIDSP(getVocByCode(VocE2FondV010.class, finishDate, sluch.getChildText("IDSP")));
                    e.setTotalCoefficient(new BigDecimal(sluch.getChildText("KOEF")));
                    e.setCost(new BigDecimal(sluch.getChildText("SUMV")));
                    theManager.persist(e);

                }
                return "ok: " + i;

            } else if (ver.equals("3.1")) { //импорт ответа в новом формате

            } else {
                LOG.error("Unknown version to import");
            }

            return "0";
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getConfigValue (String aKeyName, String aDefaultName) {
        return CONFIG.get(aKeyName,aDefaultName);
    }

    /** Загружаем файл для проставления номеров направления ФОМС */
    public String importN5File(Document doc, Long aListEntryId) {
        try {
            //Document doc = new SAXBuilder().build(aStream);
            List<Element> npr = doc.getRootElement().getChildren("NPR");
            LOG.info("Найдено "+npr.size()+" случаев");
            int i=0;
            for (Element el:npr) {
                i++;
                if (i%100==0) {LOG.info("Обработано "+i+" записей");}
                String num = el.getChildText("N_NPR");
                String planHospDate = el.getChildText("DATE_1");
                String directDate = el.getChildText("D_NPR");
                String historyNumber = el.getChildText("NHISTORY");
                String sql = "from E2Entry where listentry_id=:listEntryId and historyNumber=:historyNumber";
                List<E2Entry> list = theManager.createQuery(sql).setParameter("listEntryId",aListEntryId).setParameter("historyNumber",historyNumber).getResultList();

                for (E2Entry entry: list) {
                    boolean persist = false;

                    if (StringUtil.isNullOrEmpty(entry.getTicket263Number())) {
                        entry.setTicket263Number(num);
                        persist=true;
                    }
                    if (null==entry.getPlanHospDate()) {
                        entry.setPlanHospDate(DateFormat.parseSqlDate(planHospDate,"yyyy-MM-dd"));
                        persist=true;
                    }
                    if (null==entry.getDirectDate()) {
                        entry.setDirectDate(DateFormat.parseSqlDate(directDate,"yyyy-MM-dd"));
                        persist=true;
                    }
                    if (persist)theManager.persist(entry);
                }
            }
            LOG.info("Закончили импортировать N5");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";

        }
    }
    /** Загружаем MP файл (ответ от фонда)*/

    private HashMap<String, VocE2Sanction> sanctionMap = new HashMap<>();

    /*Загружаем ответ от фонда (версия файла {3.1, 3.0}*/
    public String importFondMPAnswer(String aMpFilename) {
        //filename вида *.mp *.mpi
        //String outputDir = unZip(aMpFilename);
        try {
            LOG.info("filename = "+aMpFilename);
            String dir = unZip(aMpFilename);
            String hFilename = aMpFilename.replace(".MP",".XML");
            hFilename="H"+hFilename.substring(1);
            File hFile = new File(dir+"/"+hFilename);
            //LOG.info(hFile.exists()+"##>>"+dir+"/"+hFilename+"<<");


            Document doc = new SAXBuilder().build(hFile);
        //    XmlDocument xmlDocError = new XmlDocument() ;
            org.jdom.Element root = doc.getRootElement();
            String ver = root.getChild("ZGLV").getChildText("VERSION");
            if ("3.0".equals(ver)) {
                throw new IllegalStateException("Импорт в старом формате более не поддерживается!");
            }
            List<Element> zaps= root.getChildren("ZAP");
            Element schet = root.getChild("SCHET");
            String nSchet = schet.getChildText("NSCHET");
            String dSchet = schet.getChildText("DSCHET");
            SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat toFormat = new SimpleDateFormat("dd.MM.yyyy");
            java.sql.Date billDate = new java.sql.Date(fromFormat.parse(dSchet).getTime());
            E2Bill bill = theManager.find(E2Bill.class,theExpertService.getBillIdByDateAndNumber(nSchet,toFormat.format(billDate)));
            bill.setStatus(getActualVocByCode(VocE2BillStatus.class,null,"code='PAID'"));

            int i=0;
            LOG.info("Найдено "+zaps.size()+" записей. Обновляем!");
            BigDecimal totalSum = new BigDecimal("0");

            for (Element zap:zaps) {
                i++;
                if (i%100==0) {LOG.info("Обработано "+i+" записей");}
                Element zsl = zap.getChild("Z_SL");
                List<Element> slList = zsl.getChildren("SL");
                Element pac = zap.getChild("PACIENT");
                boolean isComplexCase=false;
                for (Element sl: slList) {
                    if (isComplexCase) break;
                    Element slId = sl.getChild("SL_IDCASE");
                    Long entryId = Long.parseLong(slId.getText());
                    E2Entry entry= theManager.find(E2Entry.class,entryId);
                    if (entry==null) {LOG.warn("Ошибка при импорте ответа от фонда - не найдена запись с ИД = "+entryId);continue;}
                    if (entry.getParentEntry()!=null) {
                        entry=entry.getParentEntry();
                        isComplexCase=true;
                    }
                    //LOG.info(j+" record id "+entry.getId()+" ");
                    theManager.createNativeQuery("delete from E2EntrySanction where entry_id=:entryId").setParameter("entryId",entryId).executeUpdate();
                    entry.setBillNumber(nSchet);
                    entry.setBillDate(billDate);
                    entry.setBill(bill);

                    //Проставляем данные о мед. полисе
         /*           entry.setMedPolicyType(pac.getChildText("VPOLIS"));
                    if (pac.getChild("SPOLIS")!=null) {entry.setMedPolicySeries(pac.getChildText("SPOLIS"));} else {entry.setMedPolicySeries("");}
                    entry.setMedPolicyNumber(pac.getChildText("NPOLIS"));
                    entry.setInsuranceCompanyCode(pac.getChildText("SMO"));
`*/
                    //Расчет цены случая ФОМС
                    Element commentCalc = sl.getChild("D_COMMENT_CALC");
                    if (commentCalc!=null && commentCalc.getChild("root")!=null) {

                        Element ебаныйРусскийТэг = commentCalc.getChild("root");
                        List<Element> ерт = ебаныйРусскийТэг.getChildren();
                        StringBuilder commentError = new StringBuilder();
                        for (Element еб:ерт) {
                            commentError.append(еб.getName()).append(": ").append(еб.getText()).append("\n");
                        }
                        entry.setFondComment(commentError.toString());
                    } else {
                        entry.setFondComment("");
                    }

                    //Добавляем сведения о санкциях
                    if (zsl.getChild("SANK_IT")!=null && !zsl.getChildText("SANK_IT").equals("0.00")) { //
                        List<Element> sanks =zsl.getChildren("SANK") ;
                        for (Element sank: sanks) {
                            String key = sank.getChildText("S_OSN") ;
                            if (!sanctionMap.containsKey(key)) {
                                sanctionMap.put(key,getActualVocByCode(VocE2Sanction.class,null,"osn='"+key+"'"));
                            }
                         String comment = sank.getChildText("S_SL_ID")+" "+ sank.getChildText("S_COM");
                            //   boolean isMain =  false; // sank.getChildText("S_SUM").equalsIgnoreCase("0.00")?false:true; // 27-08-2018
                            theManager.persist(new E2EntrySanction(entry,sanctionMap.get(key),sank.getChildText("S_DOP"),false,comment));
                        }
                        entry.setIsDefect(true);
                    } else {
                        totalSum=totalSum.add(entry.getCost());
                        entry.setIsDefect(false);
                        entry.setFondComment(null);
                    }
                    theManager.persist(entry);
                }
            }
            LOG.info("По счету №"+bill.getBillNumber() +" сумма = "+totalSum);
            bill.setSum(totalSum);
            theManager.persist(bill);
            LOG.info("Обновление закончено!");

        }  catch (Exception e) {
            e.printStackTrace();
        }
        //Распаковываем mp файл в папку

        return null;
    }

    /** распаковка архива */
    private String unZip(String aZipFile){
        StringBuilder sb = new StringBuilder();
        String outputDir = XMLDIR + "/"+aZipFile+"-"+System.currentTimeMillis();
        sb.append("unzip  ").append(XMLDIR).append("/").append(aZipFile).append(" -d ").append(outputDir) ;

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
            return ((BigDecimal) aField).compareTo(BigDecimal.valueOf(0))==1;
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
            //throw new IllegalStateException("Не удалось найти действующее значение справочника " + aClass.getCanonicalName() + " с условием "+sql);
        } else if (list.size() > 1) {
            LOG.error("Найдено несколько действующих значений справочника " + aClass.getCanonicalName()+" с условием "+sql);
            return null;
            //throw new IllegalStateException("Найдено несколько действующих значений справочника " + aClass.getCanonicalName()+" с условием "+sql);
        }
        return list.get(0);


    }
    private @PersistenceContext
    EntityManager theManager;
    private @EJB
    IExpert2Service theExpertService;
}