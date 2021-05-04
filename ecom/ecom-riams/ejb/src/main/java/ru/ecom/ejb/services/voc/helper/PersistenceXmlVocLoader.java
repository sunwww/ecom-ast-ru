package ru.ecom.ejb.services.voc.helper;

import org.apache.log4j.Logger;
import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.voc.IVocContextService;

import java.util.Map;

public class PersistenceXmlVocLoader {

	private static final Logger LOG = Logger.getLogger(PersistenceXmlVocLoader.class);

    public void load(Map<String, IVocContextService> aHash) throws ClassNotFoundException {
    	LOG.info("Loading from persistence.xml...") ;
    	for(Class clazz : entityHelper.listAllEntities()) {
    		if(isVoc(clazz)) {
    			EntityVocService voc = new EntityVocService(
    					clazz.getName()
    					, new String[] {"code", "name"}
    					, new String[] {"code", "name"}
    					, null, null, null
    			) ;
    			String name = clazz.getSimpleName() ;
    			name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
    			LOG.debug("Registering "+name);
    			aHash.put(name, voc) ;
    		}
    	}
    	
    }
	
    private boolean isVoc(Class aClass) {
    	Class clazz = aClass ;
    	while(clazz!=null) {
    		if(clazz.equals(VocIdCodeName.class)) {
    			return true ;
    		}
    		clazz = clazz.getSuperclass();
    	}
    	return false ;
    }
    
    private final EntityHelper entityHelper = EntityHelper.getInstance() ;
}
