package ru.ecom.expomc.ejb.services.impdoc;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;


@Stateless
@Remote(IImportDocumentService.class)
public class ImportDocumentServiceBean  implements IImportDocumentService {

	public int deleteAllValues(long aDocumentId) {
		ImportDocument doc = theManager.find(ImportDocument.class, aDocumentId) ;
		try {
			Class clazz = ClassLoaderHelper.getInstance().loadClass(doc.getEntityClassName()) ;
			String entityName = EntityHelper.getInstance().getEntityName(clazz) ;
			return theManager.createQuery("delete from "+entityName).executeUpdate() ;
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка удаления "+doc.getKeyName()+": "+e.getMessage(), e) ;
		}
		
	}
	
	
    private @PersistenceContext EntityManager theManager;
	

}
