/**
 * Реализация сервиса импорта
 * @author ikouzmin 08.03.2007 21:13:00
 */
package ru.ecom.expomc.ejb.services.form.importformat;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.TransactionTimeout;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.file.IJbossGetFileLocalService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.util.EntityNameUtil;
import ru.ecom.expomc.ejb.domain.format.ImportFormat;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.ecom.expomc.ejb.services.form.importformat.config.ImportConfig;
import ru.ecom.expomc.ejb.services.form.importformat.config.ImportEntity;
import ru.ecom.expomc.ejb.services.form.importformat.config.ImportMap;
import ru.ecom.expomc.ejb.services.form.importformat.config.ImportSyncKeyList;
import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.expomc.ejb.services.importservice.ImportFileResult;
import ru.nuzmsh.dbf.DbfField;
import ru.nuzmsh.dbf.DbfFile;
import ru.nuzmsh.dbf.DbfFileReader;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

@Stateless
@Remote(IImportFormatService.class)
@Local(IImportFormatService.class)
@TransactionTimeout(10)
public class ImportFormatServiceBean implements IImportFormatService {

    private final static Logger LOG = Logger.getLogger(ImportFormatServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

    private void log(String message) { theImportLogger.log(message);  }
    private void inclev() { theImportLogger.inclev(); }
    private void declev() { theImportLogger.declev(); }
    private ImportLogger theImportLogger;// = new ImportLogger();

    private long theLogFileId;
    private File theLogFile;

    public void ensureImportLogger() {
        if (theImportLogger == null) {
            theImportLogger = new ImportLogger();
            LOG.info("create: Logger="+theImportLogger);
        }
    }

    public void setLogFile(File aFile) throws IOException {
        theLogFileId = 0;
        theLogFile = aFile;
        FileWriter fileWriter = new FileWriter(theLogFile);
        ensureImportLogger();
        LOG.info("setLogFile(...): setWriter '"+fileWriter+"' Logger = '"+theImportLogger+"'");
        theImportLogger.setImportReportWriter(fileWriter);
    }

    public void setLogFileId(long aFileId) throws IOException {
        if (aFileId == 0) {
            ensureImportLogger();
            return;
        }
        File file = theJbossGetFileLocalService.createFile(aFileId, "importlog.txt");
        LOG.info("setLogFileId("+aFileId+"): create file "+file);
        setLogFile(file);
        theLogFileId = aFileId;
    }

    public String getLogFileName() {
        if (theLogFile==null) return "NULL VALUE";
        return theLogFile.getAbsolutePath();
    }

    private boolean theUpdateModifiedOnly;
    public boolean isUpdateModifiedOnly() {
        return theUpdateModifiedOnly;
    }

    public void setUpdateModifiedOnly(boolean aUpdateModifiedOnly) {
        theUpdateModifiedOnly = aUpdateModifiedOnly;
    }

    private boolean theVerifyAfterSave;
    public boolean isVerifyAfterSave() {
        return theVerifyAfterSave;
    }

    public void setVerifyAfterSave(boolean aVerifyAfterSave) {
        theVerifyAfterSave =  aVerifyAfterSave;
    }

    private void importFileFromText(
            MyMonitor monitor,
            String aFilename,String dbfName,
            ImportFormat importFormat,
            ImportConfig importConfig,
            ImportStatistics importStatistics,
            String comment,
            Date importDate,
            java.sql.Date actualDateFrom,
            java.sql.Date actualDateTo) throws Exception, ParseException, ClassNotFoundException {
        if (theLogFile != null) LOG.info("IMPORT TEXT from:"+aFilename+" table: "+dbfName+" \n\tLOG:"+theLogFile.getAbsolutePath()+"\n");
        else                    LOG.info("IMPORT TEXT from:"+aFilename+" table: "+dbfName+" \twithout logging\n");
        log("== Импорт файла "+aFilename+" =========================================");
        inclev();

        //InputStream inFile = new FileInputStream(aFilename);
        //Reader inReader = new FileReader(aFilename);

        LineNumberReader inReader = null;
        long count = 0;
        String[] columnNames = null;
        try {
            inReader = new LineNumberReader(new FileReader(aFilename));
            monitor.setText("Расчет общего кол-ва записей");
            while(inReader.ready()) {
                String s = inReader.readLine();
                if (s.length()==0) continue;
                if (s.startsWith("#"))  {
                    Matcher matcher = Pattern.compile("^#(.*):(.*)$").matcher(s);
                    if (matcher.matches()) {
                        String id = matcher.group(1);
                        String[] values = matcher.group(2).split(",");
                        if ("name".equals(id)) {
                            dbfName = values[0];
                        } else if ("cols".equals(id)) {
                            columnNames = values;
                        }
                    }
                    continue;
                }
                count++;
            }
        } finally {
            inReader.close();
        }

//        Collection<DbfField> fields = dbfFile.getDbfFields();


//        DbfFileReader in =  new DbfFileReader(new File(aFilename));
        inReader = new LineNumberReader(new FileReader(aFilename));
        HashMap<String, Object> mapInValues = new HashMap<String, Object>();

        monitor.setValue(3);

        HashMap<String, Object> mapStoredValues = new HashMap<String, Object>();
        List<ImportEntity> entities = importConfig.getEntities();

        StringBuffer sb = new StringBuffer("Импортируемые сущности: ");
        LOG.info("match table '" +dbfName+"':"+count);
        for (ImportEntity entity : entities) {
            if (!entity.getFormat().equals("dbf")) continue;
            if (!entity.getTableName().equalsIgnoreCase(dbfName)) continue;

            importStatistics.addTotalRecords(count);
            LOG.info("match entity '" +entity.getEntityName());
            sb.append(entity.getEntityName()+" ");
        }
        log(sb.toString());
        monitor.setValue(5);
        MyMonitor oldMonitor = monitor;
        monitor = monitor.getSubMonitor(100,importStatistics.getTotalRecords());

        monitor.setText("Импорт");
        for (ImportEntity entity : entities) {
            if (!entity.getFormat().equals("dbf")) continue;
            if (!entity.getTableName().equalsIgnoreCase(dbfName)) continue;


            importStatistics.clearEntityCounters();
            log("Импорт таблицы '"+entity.getEntityName()+"'  \t\t class "+entity.getEntityClassName());
            inclev();
            log("Таблица TEXT: "+dbfName);
            entity.setImportLogger(theImportLogger);

            ImportSyncKeyList keys = entity.getKeyList(theManager);
            List<ImportMap> maps = entity.getMaps(theManager);
            Class entityClass = Class.forName(entity.getEntityClassName());
            String entityName = EntityNameUtil.getInstance().getEntityName(entityClass);
            ImportTime time = null;

            boolean debug = entity.isDebug() || isDebug();
            long debugCount = 0;
            if (entity.isDebug()) debugCount = entity.getDebugCount();
            else if (isDebug())   debugCount = Long.MAX_VALUE;

            if (count == 0) {
                declev();
                continue;
            }

            if (debug) {
                if (entity.isDebug())  log("Режим отладки для "+debugCount+" записей");
                else                   log("Режим отладки для всех записей");
            }

            // Сохранение данных о выполненной транзакции импорта данных
            if (!isDebug()) {
                time = new ImportTime();
                time.setImportDate(importDate);
                time.setActualDateFrom(actualDateFrom);
                time.setActualDateTo(actualDateTo);
                ImportDocument entityDocument = null;

                List<ImportDocument> importDocuments =
                        theManager.createQuery(
                        "FROM ImportDocument WHERE entityClassName=:cn").
                        setParameter("cn",entityClass.getCanonicalName()).
                        getResultList();

                if (importDocuments.size()>=1) {
                    entityDocument = importDocuments.get(0);
                }
                if (entityDocument == null) entityDocument = importFormat.getDocument();

//                    LOG.info("ENTITY-DOC:"+
//                            entityDocument.getEntityClassName()+" KY:"+
//                            entityDocument.getKeyName());

                time.setDocument(entityDocument);
                time.setFormat(importFormat);
                time.setComment(comment);
                theManager.persist(time);
                log("Код транзакции импорта: "+time.getId()+" ("+time.getImportDate()+") в документ '"+entityDocument.getKeyName()+"'");
            } else {
                log("Транзакция импорта не создается");
            }

            LOG.info("Импорт таблицы " + entity.getEntityClassName() + " - " + count + " записей");
            //LOG.info("match:" + match + "\tfound:" + count);

            monitor.setText("Импорт таблицы " + entity.getEntityClassName() + " - " + count + " записей");
            long i = 0;


            while (inReader.ready()) {
                if (monitor.isCancelled()) break;
                String inputString = inReader.readLine();
                if (inputString.length()==0) continue;
                if (inputString.startsWith("#")) continue;

                i++;

                if (debug && i>debugCount) break;
                if (/*i<2000 && i%100==0 ||*/ i<20000 && i%100==0 || i%1000==0 ) {
                    monitor.setText("Импорт таблицы " + entity.getEntityClassName() + " - " + i + "/" + count);
                }

//            for (Element row : rows) {
//                    if (i <= debugCount) LOG.info("RECORD: " + i + "---------------------------");

                log("Строка: "+i);
                inclev();
                importStatistics.incLoadedEntity();

                String[] values = ("~\t"+inputString+"\t~").split("[\\t]");
                for (int j=0;j<columnNames.length;j++) {
                    if (j>=values.length-2) mapInValues.put(columnNames[j],"");
                    else                    mapInValues.put(columnNames[j],values[j+1]);
                }

                //in.load(mapInValues);


                // Поиск существующего объекта
                Object data = null;


                String id = keys.findId(mapInValues);
                if (id != null) {
                    data = theManager.find(entityClass, new Long(id));
                }

                boolean isModified = false;

                // Дамп свойств
                for (ImportMap importMap : maps) {
                    Object value = importMap.getValue(mapInValues,theManager);
                    if (data != null) {
                        String oldValue = getEntityPropertyAsString(importMap,data);
                        if (oldValue == null) oldValue = "";
                        if (value.toString().equals(oldValue)) {
                            log(importMap.getProperty()+"\t:=\t'"+value+"' \t\t// "+importMap.getComment());
                        } else {
                            log(importMap.getProperty()+"\t:=\t'"+value+"'\t ==> \t'" +oldValue +"'\t// "+importMap.getComment());
                            isModified = true;
                        }
                    } else {
                        log(importMap.getProperty()+"\t:=\t'"+value+"' \t\t// "+importMap.getComment());
                    }

                    mapStoredValues.put(importMap.getProperty(),value);

//                    String key = importMap.getProperty();
                    //LOG.info(i+")\t"+key+": "+map.get(key).getClass().getCanonicalName()+" = "+map.get(key).toString());
                }

                if (id != null) {
                    if (isModified) {
                        log("("+i+") Обновлено с ID:"+id);
                        importStatistics.incUpdEntity();
                    } else {
                        log("("+i+") Синхронизировано без изменений с ID:"+id);
                        importStatistics.incSyncEntity();
                    }

//                        if (i<=debugCount) LOG.info("REPLACE ID:"+id);

                } else {
                    importStatistics.incAddEntity();
                    log("("+i+") Запись  добавлена");
                    isModified = true;
                }

                if (!debug) {
                    if (data==null) data = entityClass.newInstance();

                    long savedId = -1;

                    if (isModified || !isUpdateModifiedOnly()) {
                        // Сохранение данных -----------
                        if (data instanceof IImportData ) {
                            ((IImportData) data).setTime(time.getId());
                            log("TIME:"+time.getId());
                        }
                        copyMapToEntity(maps, mapStoredValues, data);
                        try {
                            theManager.persist(data);
                        } catch (Exception e) {
                            e.printStackTrace() ;
                        }
                        log("PERSIST");
                        if (data instanceof BaseEntity) savedId = ((BaseEntity) data).getId();

                    }

                    if (isModified && isVerifyAfterSave()) {
                        boolean isCorrect = true;
                        if (savedId == -1) {
                            isCorrect = false;
                            log("Ошибка верификации:\n\tСущность не наследована от BaseEntity");
                        } else {
                            BaseEntity savedData = (BaseEntity) theManager.find(entityClass, new Long(savedId));
                            if (savedData == null) {
                                log("Ошибка верификации:\n\tСущность несохранена");
                            } else {
                                for (ImportMap importMap : maps) {
                                    Object value = importMap.getValue(mapInValues,theManager);
                                    String savedValue = getEntityPropertyAsString(importMap,savedData);
                                    if (savedValue == null) savedValue = "";
                                    if (!value.toString().equals(savedValue)) {
                                        isCorrect = false;
                                        importStatistics.incErrEntity();
                                        log("Ошибка верификации:\n\t\tСохранено='" + savedValue +
                                                "' \n\t\tТребуется='" + value +
                                                "'");
                                    }
                                }
                            }

                        }

                        if (isCorrect) {
                            log("Данные сохранены ID:"+savedId+" \t\t"+entityClass.getCanonicalName());
                        }
                    }

                }

                mapStoredValues.clear();

                // ===============================
                monitor.advice(1);
                declev();
            }

            if (monitor.isCancelled()) {
                log("Импорт таблицы '"+entity.getEntityName()+"' прерван");
                declev();
                break;
            } else {
                log("Импорт таблицы '"+entity.getEntityName()+"' завершен");
                inclev();
                log("Загружено:             "+importStatistics.getLoadedEntity()+"\n");
                log("Добавлено:             "+importStatistics.getAddEntity());
                log("Обновлено:             "+importStatistics.getUpdEntity());
                log("Синхронизировано б/изм:"+importStatistics.getSyncEntity()+"\n");
                log("Ошибок сохранения     :"+importStatistics.getErrEntity()+"\n");

                declev();
                importStatistics.flushEntityCounters(i);
                declev();
            }
            monitor.dump();
        }

        declev();
        oldMonitor.update();
    }

    private static boolean isEmpty(Object aObject) {
    	if(aObject==null) return true ;
    	if(aObject instanceof String) {
    		return StringUtil.isNullOrEmpty((String)aObject);
    	} else {
    		return false ;
    	}
    }

    private void importFileFromDbf(
            MyMonitor monitor,
            String aFilename,String dbfName,
            ImportFormat importFormat,
            ImportConfig importConfig,
            ImportStatistics importStatistics,
            String comment,
            Date importDate,
            java.sql.Date actualDateFrom,
            java.sql.Date actualDateTo) throws Exception, ParseException, ClassNotFoundException {

        if (theLogFile != null) LOG.info("IMPORT DBF from:"+aFilename+" table: "+dbfName+" \n\tLOG:"+theLogFile.getAbsolutePath()+"\n");
        else                    LOG.info("IMPORT DBF from:"+aFilename+" table: "+dbfName+" \twithout logging\n");
        log("== Импорт файла "+aFilename+" =========================================");
        inclev();

        DbfFile dbfFile = new DbfFile();
        InputStream inFile = new FileInputStream(aFilename);

/*
        byte[] buf = new byte[5];
        LOG.info("");
        LOG.info(inFile.read(buf,0,5));
        LOG.info(">"+(new String(buf))+";");
        if (true) return;
*/

        long count = 0;
        try {
            dbfFile.load(inFile);
            monitor.setText("Расчет общего кол-ва записей");
            count = dbfFile.getRecordsCount();
        } finally {
            inFile.close();
        }

        Collection<DbfField> fields = dbfFile.getDbfFields();


//        DbfFileReader in =  new DbfFileReader(new File(aFilename));
        HashMap<String, Object> mapDbfValues = new HashMap<String, Object>();

        monitor.setValue(3);

        HashMap<String, Object> mapStoredValues = new HashMap<String, Object>();
        List<ImportEntity> entities = importConfig.getEntities();

//        long i = 0;
//        while (in.next()) {
//            i++;
//
//
//
//        }
//

        StringBuffer sb = new StringBuffer("Импортируемые сущности: ");
        LOG.info("match table '" +dbfName+"':"+count);
        for (ImportEntity entity : entities) {
            if (!entity.getFormat().equals("dbf")) continue;
            if (!entity.getTableName().equalsIgnoreCase(dbfName)) continue;
            
            importStatistics.addTotalRecords(count);
            LOG.info("match entity '" +entity.getEntityName());
            sb.append(entity.getEntityName()+" ");
        }
        log(sb.toString());
        monitor.setValue(5);
        MyMonitor oldMonitor = monitor;
        monitor = monitor.getSubMonitor(100,importStatistics.getTotalRecords());

        monitor.setText("Импорт");
        for (ImportEntity entity : entities) {
            if (!entity.getFormat().equals("dbf")) continue;
            if (!entity.getTableName().equalsIgnoreCase(dbfName)) continue;
            DbfFileReader in =  new DbfFileReader(new File(aFilename));


            importStatistics.clearEntityCounters();
            log("Импорт таблицы '"+entity.getEntityName()+"'  \t\t class "+entity.getEntityClassName());
            inclev();
            log("Таблица DBF: "+dbfName);
            entity.setImportLogger(theImportLogger);

            ImportSyncKeyList keys = entity.getKeyList(theManager);
            List<ImportMap> maps = entity.getMaps(theManager);
            Class entityClass = Class.forName(entity.getEntityClassName());
            String entityName = EntityNameUtil.getInstance().getEntityName(entityClass);
            ImportTime time = null;

            boolean debug = entity.isDebug() || isDebug();
            long debugCount = 0;
            if (entity.isDebug()) debugCount = entity.getDebugCount();
            else if (isDebug())   debugCount = Long.MAX_VALUE;

            if (count == 0) {
                declev();
                continue;
            }

            if (debug) {
                if (entity.isDebug())  log("Режим отладки для "+debugCount+" записей");
                else                   log("Режим отладки для всех записей");
            }

            // Сохранение данных о выполненной транзакции импорта данных
            if (!isDebug()) {
                time = new ImportTime();
                time.setImportDate(importDate);
                time.setActualDateFrom(actualDateFrom);
                time.setActualDateTo(actualDateTo);
                ImportDocument entityDocument = null;

                List<ImportDocument> importDocuments =
                        theManager.createQuery(
                        "FROM ImportDocument WHERE entityClassName=:cn").
                        setParameter("cn",entityClass.getCanonicalName()).
                        getResultList();

                if (importDocuments.size()>=1) {
                    entityDocument = importDocuments.get(0);
                }
                if (entityDocument == null) entityDocument = importFormat.getDocument();

//                    LOG.info("ENTITY-DOC:"+
//                            entityDocument.getEntityClassName()+" KY:"+
//                            entityDocument.getKeyName());

                time.setDocument(entityDocument);
                time.setFormat(importFormat);
                time.setComment(comment);
                theManager.persist(time);
                log("Код транзакции импорта: "+time.getId()+" ("+time.getImportDate()+") в документ '"+entityDocument.getKeyName()+"'");
            } else {
                log("Транзакция импорта не создается");
            }

            LOG.info("Импорт таблицы " + entity.getEntityClassName() + " - " + count + " записей");
            //LOG.info("match:" + match + "\tfound:" + count);

            monitor.setText("Импорт таблицы " + entity.getEntityClassName() + " - " + count + " записей");
            long i = 0;


            while (in.next()) {
//            for (Element row : rows) {
                i++;
                if (monitor.isCancelled()) break;
                if (debug && i>debugCount) break;
                if (/*i<2000 && i%100==0 ||*/ i<20000 && i%100==0 || i%1000==0 ) {
                    monitor.setText("Импорт таблицы " + entity.getEntityClassName() + " - " + i + "/" + count);
                }

//                    if (i <= debugCount) LOG.info("RECORD: " + i + "---------------------------");

                log("Строка: "+i);
                inclev();
                importStatistics.incLoadedEntity();

                in.load(mapDbfValues);
                

                // Поиск существующего объекта
                Object data = null;

                
                String id = keys.findId(mapDbfValues);
                if (id != null) {
                    data = theManager.find(entityClass, new Long(id));
                }

                boolean isModified = false;

                // Дамп свойств
                for (ImportMap importMap : maps) {
                    Object value = importMap.getValue(mapDbfValues,theManager);
                    if (data != null) {
                        String oldValue = getEntityPropertyAsString(importMap,data);
                        
                        if( (!importMap.getUpdateIfEmpty() && !isEmpty(value))
                        		|| importMap.getUpdateIfEmpty()) {
                            if (oldValue == null) oldValue = "";
                            
                            if ((value==null && oldValue.equals("")) || (value!=null && value.toString().equals(oldValue))) {
                                log(importMap.getProperty()+"\t:=\t'"+value+"' \t\t// "+importMap.getComment());
                            } else {
                                log(importMap.getProperty()+"\t:=\t'"+value+"'\t ==> \t'" +oldValue +"'\t// "+importMap.getComment());
                                isModified = true;
                            }
                        } else {
                        	log("Значение "+oldValue+" не будет изменяться на "+value) ;
                        }
                    } else {
                        log(importMap.getProperty()+"\t:=\t'"+value+"' \t\t// "+importMap.getComment());
                    }

                    mapStoredValues.put(importMap.getProperty(),value);

//                    String key = importMap.getProperty();
                    //LOG.info(i+")\t"+key+": "+map.get(key).getClass().getCanonicalName()+" = "+map.get(key).toString());
                }

                if (id != null) {
                    if (isModified) {
                        log("("+i+") Обновлено с ID:"+id);
                        importStatistics.incUpdEntity();
                    } else {
                        log("("+i+") Синхронизировано без изменений с ID:"+id);
                        importStatistics.incSyncEntity();
                    }

//                        if (i<=debugCount) LOG.info("REPLACE ID:"+id);

                } else {
                    importStatistics.incAddEntity();
                    log("("+i+") Запись  добавлена");
                    isModified = true;
                }

                if (!debug) {
                    boolean newEntityCreated = false ;
                    if (data==null) {
                        data = entityClass.newInstance();
                        newEntityCreated = true ;
                    }

                    long savedId = -1;

                    if (isModified || !isUpdateModifiedOnly()) {
                        // Сохранение данных -----------
                        if (data instanceof IImportData ) {
                            ((IImportData) data).setTime(time.getId());
                            log("TIME:"+time.getId());
                        }
                        copyMapToEntity(maps, mapStoredValues, data);

                        // persist ТОЛЬКО для созданных
                        if(newEntityCreated) {
                            try {
                                theManager.persist(data);
                                log("PERSIST");
                            } catch (Exception e) {
                                log("PERSIST ERROR: "+e.getMessage());
                                e.printStackTrace() ;
                                continue;
                            }
                        }
                        if (data instanceof BaseEntity) savedId = ((BaseEntity) data).getId();

                    }

                    if (isModified && isVerifyAfterSave()) {
                        boolean isCorrect = true;
                        if (savedId == -1) {
                            isCorrect = false;
                            log("Ошибка верификации:\n\tСущность не наследована от BaseEntity");
                        } else {
                            BaseEntity savedData = (BaseEntity) theManager.find(entityClass, new Long(savedId));
                            if (savedData == null) {
                                log("Ошибка верификации:\n\tСущность несохранена");
                            } else {
                                for (ImportMap importMap : maps) {
                                    Object value = importMap.getValue(mapDbfValues,theManager);
                                    String savedValue = getEntityPropertyAsString(importMap,savedData);
                                    if (savedValue == null) savedValue = "";
                                    if (value == null) value="";
                                    if (!value.toString().equals(savedValue)) {
                                        isCorrect = false;
                                        importStatistics.incErrEntity();
                                        log("Ошибка верификации:\n\t\tСохранено='" + savedValue +
                                                "' \n\t\tТребуется='" + value +
                                                "'");
                                    }
                                }
                            }

                        }

                        if (isCorrect) {
                            log("Данные сохранены ID:"+savedId+" \t\t"+entityClass.getCanonicalName());
                        }
                    }

                }

                mapStoredValues.clear();

                // ===============================

                // очищаем кэш
                theManager.flush() ;
                theManager.clear() ;

                monitor.advice(1);
                declev();
            }

            if (monitor.isCancelled()) {
                log("Импорт таблицы '"+entity.getEntityName()+"' прерван");
                declev();
                break;
            } else {
                log("Импорт таблицы '"+entity.getEntityName()+"' завершен");
                inclev();
                log("Загружено:             "+importStatistics.getLoadedEntity()+"\n");
                log("Добавлено:             "+importStatistics.getAddEntity());
                log("Обновлено:             "+importStatistics.getUpdEntity());
                log("Синхронизировано б/изм:"+importStatistics.getSyncEntity()+"\n");
                log("Ошибок сохранения     :"+importStatistics.getErrEntity()+"\n");

                declev();
                importStatistics.flushEntityCounters(i);
                declev();
            }
            monitor.dump();
            in.close();
            in = null;
        }

        declev();
        oldMonitor.update();
    }

    // todo КОГДА-НИБУДЬ REFACTORING - 4 раза повторяется один и тот же к внутри одimportFileFrom*
    private void importFileFromXml(
            MyMonitor monitor,
            String aFilename,InputStream istream,
            ImportFormat importFormat,
            ImportConfig importConfig,
            ImportStatistics importStatistics,
            String comment,
            Date importDate,java.sql.Date actualDateFrom,java.sql.Date actualDateTo) throws Exception {

        if (theLogFile != null) LOG.info("IMPORT XML from:"+aFilename+" \n\tLOG:"+theLogFile.getAbsolutePath()+"\n");
        else                    LOG.info("IMPORT XML from:"+aFilename+" \twithout logging\n");
        log("== Импорт файла "+aFilename+" =========================================");
        inclev();

        Document xdoc;
        SAXBuilder saxBuilder = new SAXBuilder();
        if (istream != null) {
            log("Разборка по DOM (InputStream)");
            xdoc = saxBuilder.build(istream);
        } else {
            File file = new File(aFilename);
            if (file.length() == 0) throw new Exception("Файл отсутствует или имеет нулевую длину");
            log("Разборка по DOM (File)");
            xdoc = saxBuilder.build(file);
        }
        monitor.setValue(3);

        long i = 0;
        HashMap<String, Object> map = new HashMap<String, Object>();
        monitor.setText("Построение конфигурации импорта");
        List<ImportEntity> entities = importConfig.getEntities();

        monitor.setText("Расчет общего кол-ва записей");
        StringBuffer sb = new StringBuffer("Импортируемые сущности: ");
        for (ImportEntity entity : entities) {
            if (!entity.getFormat().equals("xml")) continue;
            long c = entity.getCount(xdoc);
            importStatistics.addTotalRecords(c);
            if (c>0) {
                LOG.info("match entity '" +entity.getEntityName()+"':"+c);
                sb.append(entity.getEntityName()+" ");
            }
        }
        log(sb.toString());
        monitor.setValue(5);
        MyMonitor oldMonitor = monitor;
        monitor = monitor.getSubMonitor(100,importStatistics.getTotalRecords());

        monitor.setText("Импорт");
        for (ImportEntity entity : entities) {
            if (!entity.getFormat().equals("xml")) continue;
            importStatistics.clearEntityCounters();
            log("Импорт таблицы '"+entity.getEntityName()+"'  \t\t class "+entity.getEntityClassName());
            inclev();
            entity.setImportLogger(theImportLogger);
            
            ImportSyncKeyList keys = entity.getKeyList(theManager);
            List<ImportMap> maps = entity.getMaps(theManager);
            Class entityClass = Class.forName(entity.getEntityClassName());
            String entityName = EntityNameUtil.getInstance().getEntityName(entityClass);
            ImportTime time = null;

            boolean debug = entity.isDebug() || isDebug();
            long debugCount = 0;
            if (entity.isDebug()) debugCount = entity.getDebugCount();
            else if (isDebug())   debugCount = Long.MAX_VALUE;

            String match = entity.getMatch();
            // TODO: OPTIMIZE
            List<Element> rows = XPath.selectNodes(xdoc, match);
            /////////////////////////

            long count = rows.size();

            log("Поиск в XML: {" + match + "} найдено "+count+" записей");

            if (count == 0) {
                declev();
                continue;
            }

            if (debug) {
                if (entity.isDebug())  log("Режим отладки для "+debugCount+" записей");
                else                   log("Режим отладки для всех записей");
            }

            // Сохранение данных о выполненной транзакции импорта данных
            if (!isDebug()) {
                time = new ImportTime();
                time.setImportDate(importDate);
                time.setActualDateFrom(actualDateFrom);
                time.setActualDateTo(actualDateTo);
                ImportDocument entityDocument = null;

                List<ImportDocument> importDocuments =
                        theManager.createQuery(
                        "FROM ImportDocument WHERE entityClassName=:cn").
                        setParameter("cn",entityClass.getCanonicalName()).
                        getResultList();

                if (importDocuments.size()>=1) {
                    entityDocument = importDocuments.get(0);
                }
                if (entityDocument == null) entityDocument = importFormat.getDocument();

//                    LOG.info("ENTITY-DOC:"+
//                            entityDocument.getEntityClassName()+" KY:"+
//                            entityDocument.getKeyName());

                time.setDocument(entityDocument);
                time.setFormat(importFormat);
                time.setComment(comment);
                theManager.persist(time);
                log("Код транзакции импорта: "+time.getId()+" ("+time.getImportDate()+") в документ '"+entityDocument.getKeyName()+"'");
            } else {
                log("Транзакция импорта не создается");
            }

            LOG.info("Импорт таблицы " + entity.getEntityClassName() + " - " + count + " записей");
            LOG.info("match:" + match + "\tfound:" + count);

            monitor.setText("Импорт таблицы " + entity.getEntityClassName() + " - " + count + " записей");
            i = 0;

            for (Element row : rows) {
                i++;
                if (monitor.isCancelled()) break;
                if (debug && i>debugCount) break;
                if (/*i<2000 && i%100==0 ||*/ i<20000 && i%100==0 || i%1000==0 ) {
                    monitor.setText("Импорт таблицы " + entity.getEntityClassName() + " - " + i + "/" + count);
                }

//                    if (i <= debugCount) LOG.info("RECORD: " + i + "---------------------------");

                log("Строка: "+i);
                inclev();
                importStatistics.incLoadedEntity();

                // Поиск существующего объекта
                Object data = null;

                String id = keys.findId(row);
                if (id != null) {
                    data = theManager.find(entityClass, new Long(id));
                }

                boolean isModified = false;

                // Дамп свойств
                for (ImportMap importMap : maps) {
                    Object value = importMap.getValue(row,theManager);
                    if (data != null) {
                        String oldValue = getEntityPropertyAsString(importMap,data);
                        if (oldValue == null) oldValue = "";
                        if (value.toString().equals(oldValue)) {
                            log(importMap.getProperty()+"\t:=\t'"+value+"' \t\t// "+importMap.getComment());
                        } else {
                            log(importMap.getProperty()+"\t:=\t'"+value+"'\t ==> \t'" +oldValue +"'\t// "+importMap.getComment());
                            isModified = true;
                        }
                    } else {
                        log(importMap.getProperty()+"\t:=\t'"+value+"' \t\t// "+importMap.getComment());
                    }

                    map.put(importMap.getProperty(),value);

//                    String key = importMap.getProperty();
                    //LOG.info(i+")\t"+key+": "+map.get(key).getClass().getCanonicalName()+" = "+map.get(key).toString());
                }

                if (id != null) {
                    if (isModified) {
                        log("("+i+") Обновлено с ID:"+id);
                        importStatistics.incUpdEntity();
                    } else {
                        log("("+i+") Синхронизировано без изменений с ID:"+id);
                        importStatistics.incSyncEntity();
                    }

//                        if (i<=debugCount) LOG.info("REPLACE ID:"+id);

                } else {
                    importStatistics.incAddEntity();
                    log("("+i+") Запись  добавлена");
                    isModified = true;
                }

                if (!debug) {
                    boolean isNewEntityCreated = false ;
                    if (data==null) {
                        data = entityClass.newInstance();
                        isNewEntityCreated = true ;
                    }

                    long savedId = -1;

                    if (isModified || !isUpdateModifiedOnly()) {
                        // Сохранение данных -----------
                        if (data instanceof IImportData ) {
                            ((IImportData) data).setTime(time.getId());
                            log("TIME:"+time.getId());
                        }
                        copyMapToEntity(maps, map, data);
                        // persist ТОЛЬКО для НОВЫХ и вообще
                        if(isNewEntityCreated) {
                            try {
                                theManager.persist(data);
                            } catch (Exception e) {
                                e.printStackTrace() ;
                            }
                            log("PERSIST");
                        }
                        if (data instanceof BaseEntity) savedId = ((BaseEntity) data).getId();

                    }

                    if (isModified && isVerifyAfterSave()) {
                        boolean isCorrect = true;
                        if (savedId == -1) {
                            isCorrect = false;
                            log("Ошибка верификации:\n\tСущность не наследована от BaseEntity");
                        } else {
                            BaseEntity savedData = (BaseEntity) theManager.find(entityClass, new Long(savedId));
                            if (savedData == null) {
                                log("Ошибка верификации:\n\tСущность несохранена");
                            } else {
                                for (ImportMap importMap : maps) {
                                    Object value = importMap.getValue(row,theManager);
                                    String savedValue = getEntityPropertyAsString(importMap,savedData);
                                    if (savedValue == null) savedValue = "";
                                    if (!value.toString().equals(savedValue)) {
                                        isCorrect = false;
                                        importStatistics.incErrEntity();
                                        log("Ошибка верификации:\n\t\tСохранено='" + savedValue +
                                                "' \n\t\tТребуется='" + value +
                                                "'");
                                    }
                                }
                            }

                        }

                        if (isCorrect) {
                            log("Данные сохранены ID:"+savedId+" \t\t"+entityClass.getCanonicalName());
                        }
                    }

                }

                map.clear();

                // ===============================
                monitor.advice(1);
                //monitor.setValue(5.0+95.0*(importStatistics.getImportedRecords()+i)/importStatistics.getTotalRecords());
                declev();
            }

            if (monitor.isCancelled()) {
                log("Импорт таблицы '"+entity.getEntityName()+"' прерван");
                declev();
                break;
            } else {
                log("Импорт таблицы '"+entity.getEntityName()+"' завершен");
                inclev();
                log("Загружено:             "+importStatistics.getLoadedEntity()+"\n");
                log("Добавлено:             "+importStatistics.getAddEntity());
                log("Обновлено:             "+importStatistics.getUpdEntity());
                log("Синхронизировано б/изм:"+importStatistics.getSyncEntity()+"\n");
                log("Ошибок сохранения     :"+importStatistics.getErrEntity()+"\n");

                declev();
                importStatistics.flushEntityCounters(i);
                declev();
            }
            monitor.dump();
        }

        declev();
        oldMonitor.update();

    }

    private String checkPhysicalFormat(InputStream istream) {
        try {
            byte[] magic = new byte[2];
            istream.read(magic,0,2);
            if (magic[0] == 'P' && magic[1]=='K') return "zip";
            if (magic[0] == '7' && magic[1]=='z') return "7z";
            if (magic[0] == 3) return "dbf";
            if (magic[0] == '<' ) return "xml";
            return "";
        } catch (IOException e) {
            return "error";
        }
    }

    private String checkPhysicalFormat(File file) {
        InputStream istream = null;
        try {
            istream = new FileInputStream(file);
            return checkPhysicalFormat(istream);
        } catch (FileNotFoundException e) {
            return "error";
        } finally {
            if (istream!=null) try {
                    istream.close();
                } catch (IOException e) {}
        }

//        FileReader fileReader = null;
//        try {
//            fileReader = new FileReader(file);
//            char[] magic = new char[2];
//            fileReader.read(magic,0,2);
//            if (magic[0] == 'P' && magic[1]=='K') return "zip";
//            if (magic[0] == '7' && magic[1]=='z') return "7z";
//            if (magic[0] == 3) return "dbf";
//            if (magic[0] == '<' ) return "xml";
//            return "";
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fileReader!=null) try {
//                fileReader.close();
//            } catch (IOException e) {}
//
//        }
//        return "error";
    }

    private void importFileFromAnyType(
            String phisicalFormat, MyMonitor monitor,
            String originalFileName, File file, InputStream istream,
            ImportFormat importFormat,
            ImportConfig importConfig,
            ImportStatistics importStatistics,
            String comment,
            Date importDate, java.sql.Date actualDateFrom, java.sql.Date actualDateTo) throws Exception {


        LOG.info("importFileFromAnyType:"+phisicalFormat+":"+ originalFileName +":"+file+":"+istream);

/*

        if (originalFileName==null) {
            phisicalFormat = checkPhysicalFormat(istream);
        } else {
            phisicalFormat = checkPhysicalFormat(file);
        }
*/

        if (phisicalFormat.equals("xml")) {
            log("Формат XML");
            String fname = (file!=null)?file.getAbsolutePath():null;
            importFileFromXml(
                   monitor,fname,istream,
                   importFormat,importConfig,importStatistics,
                   comment,
                   importDate,
                   actualDateFrom, actualDateTo);
        } else if (phisicalFormat.equals("dbf")) {
            File dbfFile;
            if (istream!=null) {
                dbfFile = File.createTempFile("temp",".dbf");
                OutputStream outputStream = new FileOutputStream(dbfFile);
                //Writer writer = new FileWriter(dbfFile);
                int start = 0;
                int cnt = 0;
                while (true) {
                    byte[] buff = new byte[5000];
                    cnt = istream.read(buff);
                    if (cnt<=0) break;
                    outputStream.write(buff,0,cnt);
                    start += cnt;
                }
                outputStream.close();
//                LOG.info("created:"+dbfFile.length()+" to copy:"+start);

            } else {
                dbfFile = file;
            }
            Matcher matcher = Pattern.compile("^.*\\/(.*?)\\..*$").matcher("/"+ originalFileName +".");
            if (matcher.matches()) {
                String shortName = matcher.group(1);
                //if (istream!=null) istream.close();

                importFileFromDbf(
                        monitor,dbfFile.getAbsolutePath(),shortName,
                        importFormat,importConfig,importStatistics,
                        comment,
                        importDate,
                        actualDateFrom, actualDateTo
                );

            }


        } else if (phisicalFormat.equals("zip")) {
            if (file==null) throw new Exception("Обнаружен вложенный архив");
            ZipFile zipFile = new ZipFile(file);
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries();

            monitor.setText("Анализ архива");
            long totalZipUncompressedSize = 0;
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if (zipEntry.isDirectory()) continue;
                totalZipUncompressedSize += zipEntry.getSize();
            }
            monitor.setMaxValue(totalZipUncompressedSize);
            

            LOG.info("ZIP FILE:"+file);
            log("Архив ZIP:"+file.getName());
            entries = (Enumeration<ZipEntry>) zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if (zipEntry.isDirectory()) continue;

                InputStream inputStream = zipFile.getInputStream(zipEntry);
                LOG.info(":"+zipEntry.getName()+":"+zipEntry.getSize()+":"+zipEntry.getCompressedSize());
                log("Элемент архива "+":"+zipEntry.getName()+":"+zipEntry.getSize()+":"+zipEntry.getCompressedSize());
                String entryFormat = checkPhysicalFormat(inputStream);
                inputStream.close();
                if (entryFormat.equals("")) {
                    monitor.advice(zipEntry.getSize());
                    continue;
                }
                inputStream = zipFile.getInputStream(zipEntry);

                importFileFromAnyType(entryFormat,
                        monitor.getSubMonitor(monitor.getValue()+zipEntry.getSize()),
                        zipEntry.getName(),null,inputStream,
                        importFormat, importConfig,importStatistics, comment,
                        importDate,actualDateFrom, actualDateTo
                );
                
                try {
                    inputStream.close();
                } catch (Exception e) {}
                monitor.update();
            }
        } else {
            LOG.info("Неизвестный формат файла:"+phisicalFormat+":");
            //throw new Exception("Неизвестный формат файла"+phisicalFormat);
        }


    }


    public void importFile(long aMonitorId, long importLogFileId,String aFilename,String originalFileName, ImportFileForm aImportForm) throws ImportException {
        IMonitor standardMonitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту");
        MyMonitor monitor = new MyMonitor();
        ImportStatistics importStatistics = new ImportStatistics();
        try {

            standardMonitor = theMonitorService.startMonitor(aMonitorId, "Импорт файла " + aFilename, 100);
            monitor.attachMonitor(standardMonitor);
            // Загрузка конфигурации импорта
            long formatId = aImportForm.getImportFormat();
            ImportFormat importFormat = theManager.find(ImportFormat.class, new Long(formatId));
            ImportDocument document = importFormat.getDocument();
            final boolean isImportTimeSupport = document.isTimeSupport();
            //ensureImportLogger();
            setLogFileId(importLogFileId);
            log("Загружена конфигурация импорта с кодом: "+formatId);
            log("Поддержка импорта по времени: "+isImportTimeSupport);

            String configString;
            configString = importFormat.getConfig();
            log("Используется конфигурация импорта -----------\n" + configString +
              "\n---------------------------------------------");
            ImportConfig importConfig = new ImportConfig();
            importConfig.load(configString);
            ImportTime time = null;
            Date importDate = new Date();
            monitor.setValue(2);

            /////////////////////////////////
            // Загрузка импортируемого XML документа
            log("Загрузка импортируемого XML");
            monitor.setText("Загрузка импортируемого XML");
            // SAXBuilder saxBuilder = new SAXBuilder();
            File file = new File(aFilename);
            if (file.length() == 0) throw new Exception("Файл отсутствует или имеет нулевую длину");
            //////////////////////////////////
            // Проверка формата входного файла

            String phisicalFormat = checkPhysicalFormat(file);

            importFileFromAnyType(phisicalFormat,
                   monitor.getSubMonitor(99),originalFileName,file,null,
                   importFormat,importConfig,importStatistics,
                   aImportForm.getComment(),
                   importDate,
                   DateFormat.parseSqlDate(aImportForm.getActualDateFrom()),
                   DateFormat.parseSqlDate(aImportForm.getActualDateTo())
            );


            if (monitor.isCancelled()) {
                log("Импорт прерван\n");
                LOG.info("Импорт прерван");

            } else {
                log("Импорт завершен\n");
                LOG.info("Импорт завершен");
                monitor.setValue(100);
            }

            log("Загружено:             "+importStatistics.getLoadedTotal()+"\n");
            log("Добавлено:             "+importStatistics.getAddTotal());
            log("Обновлено:             "+importStatistics.getUpdTotal());
            log("Синхронизировано б/изм:"+importStatistics.getSyncTotal()+"\n");
            log("Ошибок сохранения     :"+importStatistics.getErrTotal()+"\n");

            monitor.setText("Импортировано " + importStatistics.getImportedRecords());
            LOG.info("theLogFileId: "+theLogFileId);
            monitor.finish(""+theLogFileId);

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
            if (monitor != null) {
                monitor.error(e.getMessage(),e);
            }

            throw new ImportException("Ошибка импорта файла " + aFilename, e);
        } finally {
            new File(aFilename).delete() ;
            if (theImportLogger != null) {
                try {
                    theImportLogger.getImportReportWriter().close();
                    theLogFile = null;
                    theImportLogger = null;
                } catch (IOException e) {}
            }
        }
    }


    @Deprecated
    private ImportFileResult importFile0(long aMonitorId, String aFilename, ImportFileForm aImportForm) throws ImportException {
        IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту");

        if (CAN_DEBUG) LOG.debug("aMonitorId = " + aMonitorId);
        if (CAN_DEBUG) LOG.debug("aFilename = " + aFilename);

        if (theLogFile != null) {
            LOG.info("IMPORT XML from:"+aFilename+" LOG:"+theLogFile.getAbsolutePath()+"\n");
        } else {
            LOG.info("IMPORT XML from:"+aFilename+" \twithout logging\n");
        }

        log("== Импорт файла "+aFilename+" =========================================");
        inclev();

        MyMonitor importMonitor = new MyMonitor();
        ImportFileResult ret = null;


        // сбор статистики
        long loadedTotal = 0;
        long addTotal = 0;
        long updTotal = 0;
        long syncTotal = 0;
        long errTotal = 0;

        long loadedEntity = 0;
        long addEntity = 0;
        long updEntity = 0;
        long syncEntity = 0;
        long errEntity = 0;

        try {
            long count = 100;
            monitor = theMonitorService.startMonitor(aMonitorId, "Импорт файла " + aFilename, count);
            monitor.setText("Загрузка конфигурации импорта");
            importMonitor.attachMonitor(monitor);


            // Загрузка конфигурации импорта
            long formatId = aImportForm.getImportFormat();
//            LOG.info("IMP-FORMAT:" + formatId);

            ImportFormat importFormat = theManager.find(ImportFormat.class, new Long(formatId));
            ImportDocument document = importFormat.getDocument();
            final boolean isImportTimeSupport = document.isTimeSupport();
            log("Загружена конфигурация импорта с кодом: "+formatId);
            log("Поддержка импорта по времени: "+isImportTimeSupport);

            String configString;
            configString = importFormat.getConfig();

//            LOG.info("config=[" + configString + "]");

            ImportConfig importConfig = new ImportConfig();
            importConfig.load(configString);

            ImportTime time = null;
            Date importDate = new Date();

            importMonitor.setValue(2);
            // Загрузка импортируемого XML документа
            log("Загрузка импортируемого XML");
            monitor.setText("Загрузка импортируемого XML");
            SAXBuilder saxBuilder = new SAXBuilder();
            File file = new File(aFilename);
            if (file.length() == 0) throw new Exception("Файл отсутствует или имеет нулевую длину");
            log("Разборка по DOM");
            Document xdoc = saxBuilder.build(file);
            importMonitor.setValue(5);

            long importedRecords = 0;
            long i = 0;

            HashMap<String, Object> map = new HashMap<String, Object>();
            //ImportFileResult ret = new ImportFileResult(time.getId());

            // Собственно импорт
            //LOG.info("XDOC=" + xdoc);
            List<ImportEntity> entities = importConfig.getEntities();
            StringBuffer sb = new StringBuffer("Импортируемые сущности: ");

            monitor.setText("Расчет общего кол-ва записей");
            long totalRecords = 0;
            for (ImportEntity entity : entities) {
//                String match = entity.getMatch();
//                match = "count("+match+")";

//                Long c = ((Double) XPath.selectSingleNode(xdoc,match)).longValue();
//                String xslString = "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">\n" +
//                        "   <xsl:output method = \"text\" />\n" +
//                        "    <xsl:template match=\"/\">\n" +
//                        "        <xsl:value-of select=\""+match+"\"/>\n" +
//                        "    </xsl:template>\n" +
//                        "\n" +
//                        "</xsl:stylesheet>";
//
//                StringWriter outString = new StringWriter();
//
//                Xslt.transform(
//                        new StreamSource(new FileReader(file)),
//                        new StreamSource(new StringReader(xslString)),
//                        new StreamResult(outString)
//                                );
//
//                long c = new Long(outString.toString()).longValue();



                long c = entity.getCount(xdoc);
                totalRecords += c;
                LOG.info("match entity '" +entity.getEntityName()+"':"+c);
                if (c>0) sb.append(entity.getEntityName()+" ");
            }
            log(sb.toString());
//            LOG.info("3");

            log("Используется конфигурация импорта -----------\n" + configString +
              "\n---------------------------------------------");


            monitor.setText("Импорт");
            for (ImportEntity entity : entities) {

                loadedEntity = 0;
                addEntity = 0;
                updEntity = 0;
                syncEntity = 0;
                errEntity = 0;

                log("Импорт таблицы '"+entity.getEntityName()+"'  \t\t class "+entity.getEntityClassName());
                inclev();

                entity.setImportLogger(theImportLogger);
                ImportSyncKeyList keys = entity.getKeyList(theManager);
                List<ImportMap> maps = entity.getMaps(theManager);

                Class entityClass = Class.forName(entity.getEntityClassName());
                boolean debug = entity.isDebug() || isDebug();
                long debugCount = 0;
                if (entity.isDebug()) {
                    debugCount = entity.getDebugCount();
                } else if (isDebug()) {
                    debugCount = Long.MAX_VALUE;
                }

                String entityName = EntityNameUtil.getInstance().getEntityName(entityClass);
                boolean isFirstRow = true;

                String match = entity.getMatch();
                List<Element> rows = XPath.selectNodes(xdoc, match);
                count = rows.size();

                log("Поиск в XML: {" + match + "} найдено "+count+" записей");

                if (count == 0) {
                    declev();
                    continue;
                }
                
                if (debug) {
                    if (entity.isDebug())
                        log("Режим отладки для "+debugCount+" записей");
                    else
                        log("Режим отладки для всех записей");
                }

                // Сохранение данных о выполненной транзакции импорта данных
                if (!isDebug()) {
                    time = new ImportTime();
                    time.setImportDate(importDate);
                    time.setActualDateFrom(DateFormat.parseSqlDate(aImportForm.getActualDateFrom()));
                    time.setActualDateTo(DateFormat.parseSqlDate(aImportForm.getActualDateTo()));
                    ImportDocument entityDocument = null;

                    List<ImportDocument> importDocuments =
                            theManager.createQuery(
                            "FROM ImportDocument WHERE entityClassName=:cn").
                            setParameter("cn",entityClass.getCanonicalName()).
                            getResultList();

                    if (importDocuments.size()>=1) {
                        entityDocument = importDocuments.get(0);
                    }
                    if (entityDocument == null) entityDocument = document;

//                    LOG.info("ENTITY-DOC:"+
//                            entityDocument.getEntityClassName()+" KY:"+
//                            entityDocument.getKeyName());

                    time.setDocument(entityDocument);
                    time.setFormat(importFormat);
                    time.setComment(aImportForm.getComment());
                    theManager.persist(time);
                    log("Код транзакции импорта: "+time.getId()+" ("+time.getImportDate()+") в документ '"+entityDocument.getKeyName()+"'");
                    ret = new ImportFileResult(time.getId());
                } else {
                    log("Транзакция импорта не создается");
                }

                LOG.info("Импорт таблицы " + entity.getEntityClassName() + " - " + count + " записей");
                LOG.info("match:" + match + "\tfound:" + count);

                monitor.setText("Импорт таблицы " + entity.getEntityClassName() + " - " + count + " записей");
                i = 0;

                for (Element row : rows) {
                    i++;
                    if (monitor.isCancelled()) break;
                    if (debug && i>debugCount) break;
                    if (i<2000 && i%100==0 || i<20000 && i%1000==0 || i%10000==0 ) {
                        monitor.setText("Импорт таблицы " + entity.getEntityClassName() + " - " + i + "/" + count);
                    }

//                    if (i <= debugCount) LOG.info("RECORD: " + i + "---------------------------");

                    log("Строка: "+i);
                    inclev();
                    loadedEntity++;

                    // Поиск существующего объекта
                    Object data = null;

                    String id = keys.findId(row);
                    if (id != null) {
                        data = theManager.find(entityClass, new Long(id));
                    }



//                    if (i<=debugCount && data==null ) LOG.info("CREATE NEW");
                    boolean isModified = false;

                    // Дамп свойств
                    for (ImportMap importMap : maps) {
                        Object value = importMap.getValue(row,theManager);
                        if (data != null) {
                            String oldValue = getEntityPropertyAsString(importMap,data);
                            if (oldValue == null) oldValue = "";
                            if (value.toString().equals(oldValue)) {
                                log(importMap.getProperty()+"\t:=\t'"+value+"' \t\t// "+importMap.getComment());
                            } else {
                                log(importMap.getProperty()+"\t:=\t'"+value+"'\t ==> \t'" +oldValue +"'\t// "+importMap.getComment());
                                isModified = true;
                            }
                        } else {
                            log(importMap.getProperty()+"\t:=\t'"+value+"' \t\t// "+importMap.getComment());
                        }

//                        if (i <= debugCount) {
//                            LOG.info(i + ":\t" + importMap.getProperty() + "\t = " + value + "\t// " + importMap.getComment());
//                        }
                        //if (value.equals("")) value = null;
                        map.put(importMap.getProperty(),value);

                        String key = importMap.getProperty();
                        //LOG.info(i+")\t"+key+": "+map.get(key).getClass().getCanonicalName()+" = "+map.get(key).toString());
                    }

                    if (id != null) {
                        if (isModified) {
                            log("("+i+") Обновлено с ID:"+id);
                            updEntity++;
                        } else {
                            log("("+i+") Синхронизировано без изменений с ID:"+id);
                            syncEntity++;
                        }

//                        if (i<=debugCount) LOG.info("REPLACE ID:"+id);

                    } else {
                        addEntity++;
                        log("("+i+") Запись  добавлена");
                        isModified = true;
                    }



                    if (!debug) {
                        if (data==null) data = entityClass.newInstance();

                        long savedId = -1;

                        if (isModified || !isUpdateModifiedOnly()) {
                            // Сохранение данных -----------
                            if (data instanceof IImportData ) {
                                ((IImportData) data).setTime(time.getId());
                                log("TIME:"+time.getId());
                            }
                            copyMapToEntity(maps, map, data);
                            //System.out.println("data ="+data) ;
                            try {
                            	theManager.persist(data);
                            } catch (Exception e) {
                            	e.printStackTrace() ;
                            }
                            //theManager.flush();
                            //theManager.clear();
                            log("PERSIST");
                            if (data instanceof BaseEntity) {
                                savedId = ((BaseEntity) data).getId();
                            }

                        }

                        if (isModified && isVerifyAfterSave()) {
                            boolean isCorrect = true;
                            if (savedId == -1) {
                                isCorrect = false;
                                log("Ошибка верификации:\n\tСущность не наследована от BaseEntity");
                            } else {
                                BaseEntity savedData = (BaseEntity) theManager.find(entityClass, new Long(savedId));
                                if (savedData == null) {
                                    log("Ошибка верификации:\n\tСущность несохранена");
                                } else {
                                    for (ImportMap importMap : maps) {
                                        Object value = importMap.getValue(row,theManager);
                                        String savedValue = getEntityPropertyAsString(importMap,savedData);
                                        if (savedValue == null) savedValue = "";
                                        if (!value.toString().equals(savedValue)) {
                                            isCorrect = false;
                                            errEntity++;
                                            log("Ошибка верификации:\n\t\tСохранено='" + savedValue +
                                                    "' \n\t\tТребуется='" + value +
                                                    "'");
                                        }
                                    }

                                }

                            }

                            if (isCorrect) {
                                log("Данные сохранены ID:"+savedId+" \t\t"+entityClass.getCanonicalName());
                            }
                        }

                    }

                    map.clear();

                    // ===============================

                    importMonitor.setValue(5.0+94.0*(importedRecords+i)/totalRecords);
//                    monitor.advice(100.0 / count);
                    isFirstRow = false;
                    //monitor.advice(5.0 + 100.0*i/count);
                    declev();
                }

                if (monitor.isCancelled()) {
                    log("Импорт таблицы '"+entity.getEntityName()+"' прерван");
                    declev();
                    break;
                } else {
                    log("Импорт таблицы '"+entity.getEntityName()+"' завершен");
                    inclev();
                    log("Загружено:             "+loadedEntity+"\n");
                    log("Добавлено:             "+addEntity);
                    log("Обновлено:             "+updEntity);
                    log("Синхронизировано б/изм:"+syncEntity+"\n");
                    log("Ошибок сохранения     :"+errEntity+"\n");

                    declev();

                    loadedTotal += loadedEntity;
                    addTotal    += addEntity;
                    updTotal    += updEntity;
                    syncTotal   += syncEntity;
                    errTotal    += errEntity;

                    importedRecords += i;
                    declev();
                }
            }
            declev();

            if (monitor.isCancelled()) {
                log("Импорт прерван\n");
                LOG.info("Импорт прерван");

            } else {
                log("Импорт завершен\n");
                LOG.info("Импорт завершен");
                importMonitor.setValue(100);
            }

            log("Загружено:             "+loadedTotal+"\n");
            log("Добавлено:             "+addTotal);
            log("Обновлено:             "+updTotal);
            log("Синхронизировано б/изм:"+syncTotal+"\n");
            log("Ошибок сохранения     :"+errTotal+"\n");

            
            monitor.setText("Импортировано " + importedRecords);
            //monitor.advice(106);
            LOG.info("theLogFileId: "+theLogFileId);
            monitor.finish(""+theLogFileId);
/*            if (time==null) {
                monitor.finish("");
            } else {
                monitor.finish(String.valueOf(time.getId()));
            }
*/
//            monitor.finish("");

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
            if (monitor != null) {
                monitor.error(e.getMessage(),e);
//                monitor.setText("Ошибка: " + e);
//                monitor.finish(""+theLogFileId);
            }

            throw new ImportException("Ошибка импорта файла " + aFilename, e);
        } finally {
            new File(aFilename).delete() ;
            if (theImportLogger != null) {
                try {
                    theImportLogger.getImportReportWriter().close();
                    theLogFile = null;
                    theImportLogger = null;
                } catch (IOException e) {}
            }

        }


        return ret ;
    }


    /**
     * Сопирование свойств в сущность
     *
     * @param aFields Список полей
     * @param aMap    Хэш значений
     * @param aEntity Объект-сущность, в который необходимо записать значения
     * @throws NoSuchMethodException
     * @throws ParseException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void copyMapToEntity(List<ImportMap> aFields, Map<String, Object> aMap, Object aEntity) throws NoSuchMethodException, ParseException, IllegalAccessException, InvocationTargetException {
        Class entityClass = aEntity.getClass();
        for (ImportMap field : aFields) {
            if (!StringUtil.isNullOrEmpty(field.getProperty())) {
                String key = field.getProperty();
                //String getterMethodName = PropertyUtil.getGetterMethodNameForProperty(key);
                //Method getterMethod = entityClass.getMethod(getterMethodName);
                Method getterMethod = PropertyUtil.getMethodFormProperty(entityClass,key);
                Method setterMethod = getSetterMethodForProperty(entityClass, field.getProperty());
                Object value = aMap.get(key);
                //System.out.println(setterMethod+" "+value) ;
                if (value != null) {
                    Object convertedValue = convertValue(value.getClass(), getterMethod.getReturnType(), value);
//                    LOG.info("convValue = "+convertedValue);
                    setterMethod.invoke(aEntity, convertedValue);
                }
            }
        }
    }


    private String getEntityPropertyAsString(ImportMap field,Object anEntity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        Class entityClass = anEntity.getClass();
        String key = field.getProperty();
        Method getterMethod = PropertyUtil.getMethodFormProperty(entityClass,key);
//        Object value = PropertyUtil.getPropertyValue(anEntity,key);
//        String getterMethodName = PropertyUtil.getGetterMethodNameForProperty(key);
//        Method getterMethod = entityClass.getMethod(getterMethodName);
        
        Object [] noargs = {};
        Object value = getterMethod.invoke(anEntity,noargs);
        return (String) convertValue(getterMethod.getReturnType(),String.class,value);
    }


    private final SimpleDateFormat FORMAT_ODBC = new SimpleDateFormat("yyyy-MM-dd");
    private final Date minDate = new Date(-3786836400000L) ;

    public Date parseDate(String aDate) throws ParseException {
        Date ret ;
        if(StringUtil.isNullOrEmpty(aDate)) {
            ret = null ;
        } else {
            if(aDate.indexOf(',')>=0) aDate = aDate.replace(',', '.') ;
            ret =  FORMAT_ODBC.parse(aDate) ;
        }
        if(ret!=null) {
               if(ret.before(minDate)) {
                   // TODO Как-нибудь обрабатывать по-другому эту ошибку
                   log("ОШИБКА: Дата "+FORMAT_ODBC.format(ret)+" не должна быть меньше "+FORMAT_ODBC.format(minDate)) ;
                   ret = null ;
               }
        }

        return ret ;
    }

    public String formatDate(Date aDate) throws ParseException {
        String ret ;
        if(aDate == null) {
            ret = null ;
        } else {
            //if(aDate.indexOf(',')>=0) aDate = aDate.replace(',', '.') ;
            ret =  FORMAT_ODBC.format(aDate);
        }
        return ret ;
    }

    public Object convertValue(Class aInClass, Class aOutClass, Object aValue) throws ParseException {
        if (aValue == null) {
            return null;
        } else if (aInClass.equals(aOutClass)) {
            return aValue;
        } else if (aInClass.equals(java.sql.Date.class) && aOutClass.equals(String.class)) {
            return formatDate((java.sql.Date) aValue);
            //return DateFormat.formatToDate((Date) aValue);
        } else if (aInClass.equals(String.class) && aOutClass.equals(java.sql.Date.class)) {
            java.util.Date utilDate = parseDate((String) aValue);
            return utilDate != null ? new java.sql.Date(utilDate.getTime()) : null;
        } else if (aOutClass.equals(String.class))  {  // В строку
            return aValue.toString();
        } else if (aOutClass.isAssignableFrom(aInClass)) {
            Object retv = aOutClass.cast(aValue);
            //LOG.info("CONVTO:"+retv);
            return retv;

    } else {
        return PropertyUtil.convertValue(aInClass, aOutClass, aValue) ;
    }
    }


    private Method getSetterMethodForProperty(Class aClass, String aPropertyName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //String getterMethodName = PropertyUtil.getGetterMethodNameForProperty(aPropertyName);
        Method getterMethod = PropertyUtil.getMethodFormProperty(aClass,aPropertyName);
        return PropertyUtil.getSetterMethod(aClass, getterMethod);
    }

//    @Resource UserTransaction theUserTransaction;



    /** Отладка */
    public boolean isDebug() { return theDebug ; }
    public void setDebug(boolean aDebug) { theDebug = aDebug ; }


    @EJB
    ILocalMonitorService theMonitorService;
    @PersistenceContext
    public EntityManager theManager;

    /** Отладка */
    private boolean theDebug = false;


    private @EJB  IJbossGetFileLocalService theJbossGetFileLocalService;



}


