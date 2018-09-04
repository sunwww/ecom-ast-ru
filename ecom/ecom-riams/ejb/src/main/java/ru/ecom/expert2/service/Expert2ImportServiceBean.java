package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.domain.E2Bill;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.E2EntrySanction;
import ru.ecom.expert2.domain.voc.VocE2BillStatus;
import ru.ecom.expert2.domain.voc.VocE2Sanction;
import ru.ecom.report.util.XmlDocument;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
@Local(IExpert2ImportService.class)
@Remote(IExpert2ImportService.class)
public class Expert2ImportServiceBean implements IExpert2ImportService {
    private final Logger log = Logger.getLogger(Expert2ImportServiceBean.class);
    private static EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    private static String theXmlDir =config.get("expert2.input.folder","/opt/jboss-4.0.4.GAi-postgres/server/default/riams/expert2xml");

    public String getConfigValue (String aKeyName, String aDefaultName) {
        return config.get(aKeyName,aDefaultName);
    }

    /** Загружаем файл для проставления номеров направления ФОМС */
    public String importN5File(Document doc, Long aListEntryId) {
        try {
            //Document doc = new SAXBuilder().build(aStream);
            List<Element> npr = doc.getRootElement().getChildren("NPR");
            log.info("Найдено "+npr.size()+" случаев");
            int i=0;
            for (Element el:npr) {
                i++;
                if (i%100==0) {log.info("Обработано "+i+" записей");}
                String num = el.getChildText("N_NPR");
                String planHospDate = el.getChildText("DATE_1");
                String directDate = el.getChildText("D_NPR");
                String historyNumber = el.getChildText("NHISTORY");
                String sql = "from E2Entry where listentry_id="+aListEntryId+" and historyNumber='"+historyNumber+"'";
                List<E2Entry> list = theManager.createQuery(sql).getResultList();

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
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";

        }
    }
    /** Загружаем MP файл (ответ от фонда)*/

    private HashMap<String, VocE2Sanction> sanctionMap = new HashMap<String, VocE2Sanction>();
    public String importFondMPAnswer(String aMpFilename) {
        //filename вида *.mp *.mpi
        //String outputDir = unZip(aMpFilename);
        try {
            log.info("filename = "+aMpFilename);
            String dir = unZip(aMpFilename);
            String hFilename = aMpFilename.replace(".MP",".XML");
            hFilename="H"+hFilename.substring(1);
            File hFile = new File(dir+"/"+hFilename);
            //log.info(hFile.exists()+"##>>"+dir+"/"+hFilename+"<<");


            Document doc = new SAXBuilder().build(hFile);
            XmlDocument xmlDocError = new XmlDocument() ;
            org.jdom.Element root = doc.getRootElement();
            List<Element> zaps= root.getChildren("ZAP");
            String nSchet = root.getChild("SCHET").getChildText("NSCHET");
            String dSchet = root.getChild("SCHET").getChildText("DSCHET");
            SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat toFormat = new SimpleDateFormat("dd.MM.yyyy");
            E2Bill bill = theManager.find(E2Bill.class,theExpertService.getBillIdByDateAndNumber(nSchet,toFormat.format(fromFormat.parse(dSchet))));
            bill.setStatus(getActualVocByCode(VocE2BillStatus.class,null,"code='PAID'"));

            int i=0;
            log.info("Найдено "+zaps.size()+" записей. Обновляем!");
            BigDecimal totalSum = new BigDecimal("0");
            for (Element zap:zaps) {
                i++;
                if (i%100==0) {log.info("Обработано "+i+" записей");}
                Element zsl = zap.getChild("Z_SL");
                List<Element> sl_s = zsl.getChildren("SL");
                Element pac = zap.getChild("PACIENT");
                for (Element sl: sl_s) {
                    Long entryId = Long.parseLong(sl.getChildText("SL_ID"));
                    E2Entry entry= theManager.find(E2Entry.class,entryId);

                    if (entry==null) {log.warn("Ошибка при импорте ответа от фонда - не найдена запись с ИД = "+entryId);continue;}
                    theManager.createNativeQuery("update E2EntrySanction set isDeleted='1' where entry_id="+entryId).executeUpdate();
                    entry.setBillNumber(nSchet);
                    entry.setBillDate(bill.getBillDate());
                    entry.setBill(bill);

                    //Проставляем данные о мед. полисе
                    entry.setMedPolicyType(pac.getChildText("VPOLIS"));
                    if (pac.getChild("SPOLIS")!=null) {entry.setMedPolicySeries(pac.getChildText("SPOLIS"));} else {entry.setMedPolicySeries("");}
                    entry.setMedPolicyNumber(pac.getChildText("NPOLIS"));
                    entry.setInsuranceCompanyCode(pac.getChildText("SMO"));

                    //Добавляем сведения о санкциях
                    if (zsl.getChild("SANK_IT")!=null&&!zsl.getChildText("SANK_IT").equalsIgnoreCase("0.00")) { //
                        List<Element> sanks =sl.getChildren("SANK");
                        for (Element sank: sanks) {
                            String key = sank.getChildText("S_OSN") ;
                            if (!sanctionMap.containsKey(key)) {
                                sanctionMap.put(key,(VocE2Sanction)getActualVocByCode(VocE2Sanction.class,null,"osn='"+key+"'"));
                            }
                            boolean isMain =  false; // sank.getChildText("S_SUM").equalsIgnoreCase("0.00")?false:true; // 27-08-2018
                            E2EntrySanction s = new E2EntrySanction(entry,sanctionMap.get(key),sank.getChildText("S_DOP"),isMain);theManager.persist(s);
                        }
                    /*    Element commentCalc = sluch.getChild("COMMENT_CALC");
                        if (commentCalc!=null&&commentCalc.getChild("root")!=null) {

                            Element ебаныйРусскийТэг = commentCalc.getChild("root");
                            List<Element> ерт = ебаныйРусскийТэг.getChildren();
                            StringBuilder commentError = new StringBuilder();
                            for (Element еб:ерт) {
                                commentError.append(еб.getName()).append(": ").append(еб.getText()).append("\n");
                            }
                            entry.setFondComment(commentError.toString());
                        } */
                        entry.setIsDefect(true);
                    } else {
                        totalSum.add(entry.getCost());
                        entry.setIsDefect(false);
                        entry.setFondComment(null);
                    }
                    theManager.persist(entry);
                }



            }
            bill.setSum(totalSum);
            theManager.persist(bill);
            log.info("Обновление закончено!");

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Распаковываем mp файл в папку

        return null;
    }

    /** Обновляем запись */
    private void updateEntryByFondAnswer(Element aSluch, Long aListEntryId) {

    }
    /** распаковка архива */
    private String unZip(String aZipFile){
        StringBuilder sb = new StringBuilder();
        String outputDir = theXmlDir+ "/"+aZipFile+"-"+System.currentTimeMillis();
        sb.append("unzip  ").append(theXmlDir).append("/").append(aZipFile).append(" -d ").append(outputDir) ;

        try {
            Runtime.getRuntime().exec(sb.toString());//arraCmd);
            Thread.sleep(5000); //Заснем, чтобы точно всё распаковалось
        } catch (Exception e) {
            log.warn("Похоже, у нас Виндовс. Попробуем запустить 7-zip");
            sb = new StringBuilder().append("\"C:\\Program Files\\7-Zip\\7z.exe\" e ").append(theXmlDir).append("\\").append(aZipFile).append(" -o").append(outputDir);
            try {
                System.out.println("sb="+sb+", dir="+outputDir);
                Runtime.getRuntime().exec(sb.toString());
                Thread.sleep(5000); //Заснем, чтобы точно всё распаковалось
            } catch (Exception e1) {log.warn("NE SMOG :-(");}
        }
        return outputDir;


    }
    private String createDir(String aDirShortName) {
        File dir = new File(theXmlDir+"/"+aDirShortName);
        if (dir.exists()&&!dir.isDirectory()) {
            log.error("Невозможно создать папку - с таким именем уже существует файл");
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
            return ss != null && !ss.trim().equals("");
        } else if (aField instanceof Boolean) {
            return aField!=null?((Boolean) aField):false;
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
                    list = theManager.createQuery("from " + aClass.getName() + " where finishDate is null and " + (aSqlAdd != null ? aSqlAdd : "")).getResultList();
                }
            } else if (isNotNull(aSqlAdd)){
                sql+=aSqlAdd;
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
    private @PersistenceContext
    EntityManager theManager;
    private @EJB
    IExpert2Service theExpertService;
}
