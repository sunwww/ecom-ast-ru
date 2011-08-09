package ru.ecom.expomc.ejb.services.importservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.util.EntityNameUtil;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.ecom.expomc.ejb.services.importservice.factory.SimpleFactory;
import ru.nuzmsh.dbf.DbfField;
import ru.nuzmsh.dbf.DbfFile;
import ru.nuzmsh.dbf.DbfFileReader;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Импорт
 */
@Stateless
@Remote(IImportService.class)
@Local(IImportService.class)
public class ImportServiceBean implements IImportService {


     private final static Logger LOG = Logger.getLogger(ImportServiceBean.class) ;
     private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

     public ImportFileResult importFile(String aOriginalFilename, long aMonitorId, String aFilename, ImportFileForm aImportForm) throws ImportException {
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
        			 if(CAN_DEBUG) LOG.debug("Imprting file "+f.getName()) ;
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
        if (CAN_DEBUG) LOG.debug("aMonitorId = " + aMonitorId);
        if (CAN_DEBUG) LOG.debug("aFilename = " + aFilename);
        DbfFileReader in = null ;

        IMonitor monitor = null ;
        try {
//            theUserTransaction.begin();


            DbfFile dbfFile = new DbfFile();
            //File file = new File(aFilename) ;
            InputStream inFile = new FileInputStream(aFilename) ;
            long count = 0 ;
            try {
                dbfFile.load(inFile);
                count = dbfFile.getRecordsCount() ;
            } finally {
                inFile.close();
            }
            monitor = theMonitorService.startMonitor(aMonitorId, "Импорт файла "+aFilename, count);

            Collection<Field> fields = Collections.unmodifiableCollection(format.getFields());
            ImportDocument document = format.getDocument();
            Class entityClass = Class.forName(document.getEntityClassName()) ;
            final boolean isImportTimeSupport = document.isTimeSupport() ;

            //if(!isImportTimeSupport) deleteNotTimeSupports(entityClass, document) ;

            


            // отркрываем файл DBF и перебираем все записи
            in =  new DbfFileReader(new File(aFilename));
            HashMap<String, Object> map = new HashMap<String, Object>();
            boolean firstPassed = false ;
            ImportFileResult ret = new ImportFileResult(time.getId());
            int i = 0 ;
//            EntityManager manager = theFactory.createEntityManager();
//            theManager.setFlushMode(FlushModeType.COMMIT);

            while(in.next() && !monitor.isCancelled()) {
                monitor.advice(1);
                i++ ;

//                theUserTransaction.begin();
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

//                manager.getTransaction().begin();
                theManager.persist(data);
//                manager.getTransaction().commit();

                if(!firstPassed) {
                    checkFormat(ret, format, new File(aFilename)) ;
                }
//                doImport(map, format, data) ;

                map.clear() ;
                firstPassed = true ;
                if(i%300==0) {
//                    LOG.info(i+" ...") ;
//                    theUserTransaction.commit();
//                    LOG.info("commited") ;
//                    theUserTransaction.begin() ;
//                    LOG.info(" beggined ") ;
                    monitor.setText("Импортировано "+i);
                    theManager.flush();
                    theManager.clear();
                }
            }
//            in.close() ;
//            theUserTransaction.commit();
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

    private void deleteNotTimeSupports(Class aEntityClass, ImportDocument aDocument) {
        String entityName = EntityNameUtil.getInstance().getEntityName(aEntityClass);

        int count = theManager.createQuery("delete from "+ entityName).executeUpdate();

        if (CAN_DEBUG) LOG.debug("deleted "+entityName+" = " + count);

        count = theManager.createQuery("delete from ImportTime where document = :document")
                .setParameter("document", aDocument).executeUpdate();
        
        if (CAN_DEBUG) LOG.debug("deleted ImportTime = " + count);
    }

    public void checkFormat(ImportFileResult aResult, Format aFormat, File aDbfFile) throws IOException, ParseException {
        DbfFile dbfFile = new DbfFile();
        FileInputStream in = new FileInputStream(aDbfFile);
        dbfFile.load(in);
        in.close() ;
        int index = 0 ;
        //Collection<DbfField> dbfFields = dbfFile.getDbfFields() ;
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
                    //
                }
                if(field.getDbfType()!=Field.DATE && dbfField.getDecimalLength() != field.getDbfDecimal()) {
                    addMessage(index, aResult, dbfField, field, "Hеправильнок количество десятичных знаков");
                    //
                }
//                if(dbfField.getType() != field.getDbfType()) {
//                    addMessage(index, aResult, dbfField, field, "неправильный тип поля");

//                }

            } else {
                aResult.addMessage("нет полей в файле dbf");
            }
        }
    }


    private static void addMessage(int aIndex, ImportFileResult aResult, DbfField aDbfField, Field aField, String aMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append(aIndex) ;
        sb.append(".   DBF: ") ;
        sb.append(aDbfField.getName()) ;
        sb.append(" ") ;
        sb.append((char)aDbfField.getType()) ;
        sb.append(" ") ;
        sb.append(aDbfField.getLength()) ;
        sb.append(".") ;
        sb.append(aDbfField.getDecimalLength()) ;
        sb.append(" - формат ") ;
        sb.append(aField.getName()) ;
        sb.append(" ") ;
        sb.append(aField.getDbfType()) ;
        sb.append(" ") ;
        sb.append(aField.getDbfSize()) ;
        sb.append(".") ;
        sb.append(aField.getDbfDecimal()) ;
        sb.append(" : ") ;
        sb.append(aMessage) ;
        aResult.addMessage(sb.toString());
    }

    public IImportEntityFactory getImportEntityFactory() {
        // пока для пробы
        return new SimpleFactory() ;
    }

    /**
     * Импорт строки
     * @param aMap           данные для импорта
     * @param aActualFormat  формат, куда писать
     * @param aFactory       создание нового экземпляра
     * @return
     * @throws ImportException
     */
    public ImportRowResult doImport(Map<String, Object> aMap
            , Format aActualFormat, IImportEntityFactory aFactory) throws ImportException {
        try {
            Object entity = aFactory.createNewEntity();
            copyMapToEntity(aActualFormat.getFields(), aMap, entity ) ;
            theManager.persist(entity);
            return new ImportRowResult();
        } catch (Exception e) {
            throw new ImportException("Ошибка импорта",e);
        }
    }

    public static void copyMapToEntity(Collection<Field> aFields, Map<String, Object> aMap, Object aEntity) throws NoSuchMethodException, ParseException, IllegalAccessException, InvocationTargetException {
        Class entityClass = aEntity.getClass();
        for (Field field : aFields) {
//        for (Field field : aActualFormat.getFields()) {
            if(!StringUtil.isNullOrEmpty(field.getProperty()) ) {
                String key = field.getName();
                //String getterMethodName = PropertyUtil.getGetterMethodNameForProperty(field.getProperty()) ;
                Method getterMethod = PropertyUtil.getGetterMethod(entityClass, field.getProperty()) ; //getentityClass.getMethod(getterMethodName) ;
                Method setterMethod = PropertyUtil.getSetterMethod(entityClass, getterMethod) ; //getSetterMethodForProperty(entityClass, field.getProperty());
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

    public static Object convertValue(Class aInClass, Class aOutClass, Object aValue) throws ParseException {
    	return PropertyUtil.convertValue(aInClass, aOutClass, aValue) ;
    	
/*        if(aValue==null) {
            return null ;
        } else if(aInClass.equals(aOutClass)) {
            return aValue ;
        } else if (aInClass.equals(java.sql.Date.class) && aOutClass.equals(String.class)) {
            return DateFormat.formatToDate((Date) aValue) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(java.sql.Date.class)) {
            java.util.Date utilDate = DateFormat.parseDate((String) aValue) ;
            return utilDate!=null ? new java.sql.Date(utilDate.getTime()) : null ;
        } else if(aInClass.equals(Long.TYPE) && aOutClass.equals(Long.class)) {
            return (Long) aValue ;
        } else if(aInClass.equals(Long.class) && aOutClass.equals(Long.TYPE)) {
            return aValue ;
        } else if (aInClass.equals(java.util.Date.class) && aOutClass.equals(String.class)) {
            return DateFormat.formatToDate((Date) aValue) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(BigDecimal.class)) {
            String str = (String) aValue ;
            return !StringUtil.isNullOrEmpty(str) ? new BigDecimal((String) aValue) : null ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(Integer.TYPE)) {
            String str = (String) aValue ;
            return StringUtil.isNullOrEmpty(str) ? 0 : (int)Double.parseDouble(str) ;
        } else if (aInClass.equals(String.class) && aOutClass.equals(Long.TYPE)) {
            String str = (String) aValue ;
            return StringUtil.isNullOrEmpty(str) ? 0 : Long.parseLong(str) ;
        }
        throw new IllegalArgumentException("Нет преобразования из "+aInClass+" в "+aOutClass+ " для значения "+aValue) ;
        */
    }


    private static Method getSetterMethodForProperty(Class aClass, String aPropertyName) throws NoSuchMethodException {
        //String getterMethodName = PropertyUtil.getGetterMethodNameForProperty(aPropertyName) ;
        Method getterMethod = PropertyUtil.getGetterMethod(aClass, aPropertyName) ; //aClass.getMethod(getterMethodName) ;
        return PropertyUtil.getSetterMethod(aClass, getterMethod) ;
    }

    /**
     * Находит актуальный формат
     * @param aEntity
     */
    public Format findActualFormat(ImportDocument aEntity) throws ActualFormatNotFoundException {
        // todo берется пока первый формат
        return aEntity.getFormats().iterator().next() ;
    }


//    @Resource UserTransaction theUserTransaction;

    @EJB ILocalMonitorService theMonitorService ;
    @PersistenceContext
    public EntityManager theManager;


//    @PersistenceUnit
//    EntityManagerFactory theFactory ;

}
