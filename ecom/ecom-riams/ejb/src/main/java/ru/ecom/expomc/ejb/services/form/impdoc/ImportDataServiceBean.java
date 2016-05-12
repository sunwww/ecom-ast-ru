package ru.ecom.expomc.ejb.services.form.impdoc;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;

/**
 * Работа с данными
 */
@Stateless
@Remote(IImportDataService.class)
public class ImportDataServiceBean implements IImportDataService {

	private final static Logger LOG = Logger
			.getLogger(ImportDataServiceBean.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    public Collection<IImportData> listAll(long aTime) {
        ImportTime importTime = theManager.find(ImportTime.class, aTime)  ;
        ImportDocument doc = importTime.getDocument();

        if(doc.isTimeSupport()) {
            return theManager.createQuery("from "+doc.getEntityClassName()+" where time = :time").setParameter("time", aTime)
                    .setMaxResults(20).getResultList();
        } else {
            return theManager.createQuery("from "+doc.getEntityClassName())
                    .setMaxResults(20).getResultList();
        }
    }

    
    
	public void join(long[] aImportedDataIds) {
		
		if(aImportedDataIds.length<=1) {
			throw new IllegalArgumentException("Массив должен содержать 2 и больше значений") ;
		}
		try {
			long to = aImportedDataIds[0] ;
	        ImportTime importTime = theManager.find(ImportTime.class, to)  ;
			ImportDocument doc = importTime.getDocument() ;
			EntityHelper entityHelper = EntityHelper.getInstance() ;
			ClassLoaderHelper classLoaderHelper = ClassLoaderHelper.getInstance() ; 
			String tableName = entityHelper.getTableName(classLoaderHelper.loadClass(doc.getEntityClassName())) ;
			String entityName = doc.getEntityClassName() ;
			
			for(int i=1; i<aImportedDataIds.length; i++) {
				importTime.setComment(importTime.getComment()+" + "
						+theManager.find(ImportTime.class, aImportedDataIds[i]).getComment()) ;
				int count = theManager.createNativeQuery("update "+tableName+" set \"time\"=:time where \"time\"=:to")
				 .setParameter("to", aImportedDataIds[i])
				 .setParameter("time", to)
				 .executeUpdate();
				if (CAN_DEBUG)
					LOG.debug("join: count = " + count); 
	
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Ошибка объединения: "+e.getMessage(),e) ;
		}
	}

    private @PersistenceContext EntityManager theManager ;




}
