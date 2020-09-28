package ru.ecom.expomc.ejb.services.exportservice;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.file.IJbossGetFileLocalService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.dbf.DbfField;
import ru.nuzmsh.dbf.DbfWriter;
import ru.nuzmsh.util.PropertyUtil;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.*;

/**
 *  Экспорт
 */
@Stateless
@Remote(IExportService.class)
public class ExportServiceBean implements IExportService {

    private static final Logger LOG = Logger.getLogger(ExportServiceBean.class) ;

    public void export(long aMonitorId, long aFileId, ExportForm aForm) {
        IMonitor monitor = null ;
        try {
            ImportDocument document = theManager.find(ImportDocument.class, aForm.getDocument())  ;
        	File file = theJbossGetFileLocalService.createFile(aFileId, document.getKeyName()+".dbf");
        	String filename = file.getName() ;
        	
            Class clazz = ClassLoaderHelper.getInstance().loadClass(document.getEntityClassName()) ;
            Long count = (Long)theManager.createQuery("select count(*) from "+clazz.getSimpleName()).getSingleResult() ;
            monitor = theMonitorService.startMonitor(aMonitorId, "Экспорт файла "+filename, count);
            monitor.setText(filename);
            Iterator iterator = QueryIteratorUtil.iterate(theManager.createQuery("from "+clazz.getSimpleName()+" order by id")) ;

            Format format = theManager.find(Format.class, aForm.getFormat()) ;
            
            export(format, iterator, file, monitor, count, null) ;
            
            monitor.finish(aFileId+"");

        } catch (Exception e) {
            LOG.error("Ошибка экспорта",e);
            if (monitor!=null) monitor.setText(e.getMessage());
            throw new IllegalStateException(e) ;
        }
    }

    public static void export(Format aFormat, List<Object> aObjectList, File aFile, IMonitor aMonitor, Long aCount, IBeforeSaveInterceptor aInterceptor) throws Exception {
        List<DbfField> dbfField = createDbfField(aFormat) ;
        Collection<Field> fields = aFormat.getFields() ;
        LOG.warn("Format ="+aFormat+" ,Iterator ="+aObjectList.size()+", File ="+aFile+", IMonitor ="+aMonitor+", Long "+aCount);
        int i = 0;
        DbfWriter writer = new DbfWriter(aCount.intValue(), dbfField);
        try {
            writer.open(aFile);
            for (Object entity: aObjectList) {
                if(++i % 100 == 0 && aMonitor!=null) {
                    aMonitor.advice(100);
                    if (aMonitor.isCancelled()) break;
                }
                HashMap<String, Object> map = new HashMap<>();
                if(aInterceptor!=null) aInterceptor.beforeSave(entity) ;
                for (Field field : fields) {
                    Object value = field.getProperty()!=null ? PropertyUtil.getPropertyValue(entity, field.getProperty()) : null ;
                    map.put(field.getName(), value) ;
                }
                writer.write(map);
            }

        } finally {
            writer.close() ;
        }
    }

    public static void export(Format aFormat, Iterator aIterator, File aFile, IMonitor aMonitor, Long aCount, IBeforeSaveInterceptor aInterceptor) throws Exception {
        List<DbfField> dbfField = createDbfField(aFormat) ;
        Collection<Field> fields = aFormat.getFields() ;
        LOG.warn("Format ="+aFormat+" ,Iterator ="+aIterator+", File ="+aFile+", IMonitor ="+aMonitor+", Long "+aCount);
        int i = 0;
        DbfWriter writer = new DbfWriter(aCount.intValue(), dbfField);
        try {
            writer.open(aFile);
            boolean notCanceled = true ;
            while (aIterator.hasNext() && notCanceled) {
                Object entity = aIterator.next();
                if(++i % 100 == 0 && aMonitor!=null) {
                    aMonitor.advice(100);
                    notCanceled = !aMonitor.isCancelled() ;
                }
                HashMap<String, Object> map = new HashMap<>();
                if(aInterceptor!=null) aInterceptor.beforeSave(entity) ;
                for (Field field : fields) {
                    Object value = field.getProperty()!=null ? PropertyUtil.getPropertyValue(entity, field.getProperty()) : null ;
                    map.put(field.getName(), value) ;
                }
                writer.write(map);

            }
        } finally {
            writer.close() ;
        }
    }

    private static byte getDbfType(Field aField) {
        byte ret ;
        switch(aField.getDbfType()) {
            case(Field.DATE): ret = DbfField.DATE ; break ;
            case(Field.NUMERIC): ret = DbfField.NUMERIC ; break ;
            default:
                ret = DbfField.CHAR ;
        }
        return ret;
    }
    private static List<DbfField> createDbfField(Format aFormat) {
        LinkedList<DbfField> fields = new LinkedList<>();
        for (Field field : aFormat.getFields()) {
            DbfField dbfField = new DbfField(field.getName(), getDbfType(field),field.getDbfSize(), field.getDbfDecimal() );
            fields.add(dbfField) ;
        }
        return fields;
    }

    @EJB ILocalMonitorService theMonitorService ;
    @PersistenceContext
    public EntityManager theManager;

    private @EJB IJbossGetFileLocalService theJbossGetFileLocalService;

}
