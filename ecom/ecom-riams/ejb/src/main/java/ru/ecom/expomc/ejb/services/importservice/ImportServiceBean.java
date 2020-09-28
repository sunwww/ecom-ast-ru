package ru.ecom.expomc.ejb.services.importservice;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.nuzmsh.dbf.DbfField;
import ru.nuzmsh.dbf.DbfFile;
import ru.nuzmsh.dbf.DbfFileReader;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.util.zip.ExtractJar;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;

/**
 * Импорт
 */
@Stateless
@Remote(IImportService.class)
@Local(IImportService.class)
public class ImportServiceBean implements IImportService {


     private static final Logger LOG = Logger.getLogger(ImportServiceBean.class) ;

     public ImportFileResult importFile(String aOriginalFilename, long aMonitorId, String aFilename, ImportFileForm aImportForm) {
    	 IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
    	 try {
             Format format = theManager.find(Format.class, aImportForm.getImportFormat()) ;
             File file = new File(aFilename) ;
             ImportTime time = createImportTime(aImportForm, format, aOriginalFilename, file.length()) ;
        	 
        	 if(aOriginalFilename!=null && aOriginalFilename.toLowerCase().endsWith(".zip")) {
        		 File tempDir = new File("/tmp/import_"+System.currentTimeMillis()) ;
        		 tempDir.mkdirs() ;
        		 ExtractJar extractJar = new ExtractJar() ;
        		 extractJar.extract(tempDir, file) ;
        		 //File tempDir =  ;
        		 File[] files = tempDir.listFiles() ;
        		 monitor = theMonitorService.startMonitor(aMonitorId, "Импорт файлов из архива "+aOriginalFilename, files.length) ;
        		 ImportFileResult result = null ;
        		 for( File f : files) {
        			 monitor.setText(f.getName()) ;
        			 long subMonitorId = System.currentTimeMillis() ; // FIXME
        			 result = importFileDbf(subMonitorId, f.getAbsolutePath(), time, format) ;
        			 monitor.advice(1) ;
        		 }
        		 if(result!=null) {
            		 monitor.finish(String.valueOf(result.getTimeId())) ;
        		 }
        		 tempDir.delete() ;
        		 return result ;
        	 } else {
        		 return importFileDbf(aMonitorId, aFilename, time, format) ;
        	 }
    	 } catch (Exception e) {
    		 monitor.error(e.getMessage(), e) ;
    		 throw new IllegalStateException(e) ;
    	 }
     } 
     
     private ImportTime createImportTime(ImportFileForm aImportForm, Format aFormat, String aOriginalFilename, long aFileLength) throws ParseException {
         ImportTime time = new ImportTime();
         time.setImportDate(new Date());
         time.setActualDateFrom(DateFormat.parseSqlDate(aImportForm.getActualDateFrom()));
         time.setActualDateTo(DateFormat.parseSqlDate(aImportForm.getActualDateTo()));
         time.setDocument(aFormat.getDocument());
         time.setFormat(aFormat);
         time.setComment(aImportForm.getComment());
         time.setOriginalFilename(aOriginalFilename) ;
         time.setSizeInBytes(aFileLength) ;
         theManager.persist(time);
         return time ;
     }

     /**
     * Импорт файла
     * @param aFilename      DBF файл для импорта
     * @return результат импорта
     */
    private ImportFileResult importFileDbf(long aMonitorId, String aFilename
    		, ImportTime time, Format format) throws ImportException {
        DbfFileReader in = null ;

        IMonitor monitor = null ;
        try ( InputStream inFile = new FileInputStream(aFilename)){
            DbfFile dbfFile = new DbfFile();

            long count ;
                dbfFile.load(inFile);
                count = dbfFile.getRecordsCount() ;
            monitor = theMonitorService.startMonitor(aMonitorId, "Импорт файла "+aFilename, count);

            Collection<Field> fields = Collections.unmodifiableCollection(format.getFields());
            ImportDocument document = format.getDocument();
            Class entityClass = Class.forName(document.getEntityClassName()) ;
            final boolean isImportTimeSupport = document.isTimeSupport() ;
            in =  new DbfFileReader(new File(aFilename));
            HashMap<String, Object> map = new HashMap<>();
            boolean firstPassed = false ;
            ImportFileResult ret = new ImportFileResult(time.getId());
            int i = 0 ;

            while(in.next() && !monitor.isCancelled()) {
                monitor.advice(1);
                i++ ;
                try {
                	in.load(map) ;
                } catch (Exception e) {
                	LOG.error("Ошибка загрузки данных из записи DBF: "+e.getMessage(),e) ;
                	continue ;
                }

                Object data = entityClass.newInstance();
                if(isImportTimeSupport) {
                    ((IImportData)data).setTime(time.getId());
                }

                copyMapToEntity(fields, map, data);
                theManager.persist(data);

                if(!firstPassed) {
                    checkFormat(ret, format, new File(aFilename)) ;
                }
                map.clear() ;
                firstPassed = true ;
                if(i%300==0) {
                    monitor.setText("Импортировано "+i);
                    theManager.flush();
                    theManager.clear();
                }
            }
            monitor.setText("Импортировано "+i);
            monitor.finish(String.valueOf(time.getId()));

            return ret ;
        } catch (Exception e) {
            if(monitor!=null) monitor.setText("Ошибка: "+e) ;
            throw new ImportException("Ошибка импорта файла "+aFilename, e);
        } finally {
            if(in!=null) try {
                in.close() ;
            } catch (IOException e) {
                throw new ImportException("Ошибка закрытия файла "+aFilename,e);
            }
            new File(aFilename).delete() ;
        }
    }

    private void checkFormat(ImportFileResult aResult, Format aFormat, File aDbfFile) throws IOException, ParseException {
        DbfFile dbfFile = new DbfFile();
        try (FileInputStream in = new FileInputStream(aDbfFile)) {
            dbfFile.load(in);
            int index = 0 ;
            Iterator<DbfField> dbfIterator = dbfFile.getDbfFields().iterator() ;
            for (Field field : aFormat.getFields()) {
                index++ ;
                if(dbfIterator.hasNext()) {
                    DbfField dbfField = dbfIterator.next();
                    if(!dbfField.getName().equals(field.getName())) {
                        addMessage(index, aResult, dbfField, field, "Неправильное наименование поля");
                    }
                    if(field.getDbfType()!=Field.DATE && dbfField.getLength() != field.getDbfSize()) {
                        addMessage(index, aResult, dbfField, field, "Hеправильная длина поля");
                    }
                    if(field.getDbfType()!=Field.DATE && dbfField.getDecimalLength() != field.getDbfDecimal()) {
                        addMessage(index, aResult, dbfField, field, "Hеправильнок количество десятичных знаков");
                    }
                } else {
                    aResult.addMessage("нет полей в файле dbf");
                }
            }
        }
    }


    private static void addMessage(int aIndex, ImportFileResult aResult, DbfField aDbfField, Field aField, String aMessage) {
        String sb = aIndex +
                ".   DBF: " + aDbfField.getName() +
                " " + (char) aDbfField.getType() +
                " " + aDbfField.getLength() +
                "." + aDbfField.getDecimalLength() +
                " - формат " + aField.getName() +
                " " + aField.getDbfType() +
                " " + aField.getDbfSize() +
                "." + aField.getDbfDecimal() +
                " : " + aMessage;
        aResult.addMessage(sb);
    }

    public static void copyMapToEntity(Collection<Field> aFields, Map<String, Object> aMap, Object aEntity) throws NoSuchMethodException {
        Class entityClass = aEntity.getClass();
        for (Field field : aFields) {
            if(!StringUtil.isNullOrEmpty(field.getProperty()) ) {
                String key = field.getName();
                Method getterMethod = PropertyUtil.getGetterMethod(entityClass, field.getProperty()) ;
                Method setterMethod = PropertyUtil.getSetterMethod(entityClass, getterMethod) ;
                Object value = aMap.get(key) ;
                if(value==null && !StringUtil.isNullOrEmpty(field.getDefaultValue())) {
                	value = field.getDefaultValue() ;
                }
                if(value!=null) {
                	try {
                		Object convertedValue = convertValue(value.getClass(), getterMethod.getReturnType(), value ) ;
                        setterMethod.invoke(aEntity, convertedValue) ;
                	} catch (Exception e) {
                		LOG.error("Ошибка преобразования : '"
                				+value +"' ["+value.getClass().getName()+" -> "+getterMethod.getReturnType()+"]"
                				+ ", mapKey = "+key+", property="+field.getProperty(),e) ;
                	}
                }
            }
        }
    }

    private static Object convertValue(Class aInClass, Class aOutClass, Object aValue) throws ParseException {
    	return PropertyUtil.convertValue(aInClass, aOutClass, aValue) ;
    }

    /**
     * Находит актуальный формат
     * @param aEntity
     */
    public Format findActualFormat(ImportDocument aEntity) {
        // todo берется пока первый формат
        return aEntity.getFormats().iterator().next() ;
    }

    @EJB ILocalMonitorService theMonitorService ;
    @PersistenceContext
    public EntityManager theManager;

}
