package ru.ecom.ejb.services.voc.helper;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.voc.IVocContextService;

public class PersistenceXmlVocLoader {

	private final static Logger LOG = Logger
			.getLogger(PersistenceXmlVocLoader.class);
//	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    public void load(Map<String, IVocContextService> aHash) throws IOException, JDOMException, IllegalAccessException, InstantiationException, ClassNotFoundException {
    	LOG.info("Loading from persistence.xml...") ;
    	for(Class clazz : theEntityHelper.listAllEntities()) {
    		if(isVoc(clazz)) {
    			EntityVocService voc = new EntityVocService(
    					clazz.getName()
    					, new String[] {"code", "name"}
    					, new String[] {"code", "name"}
    					, null, null, null
    			) ;
    			String name = clazz.getSimpleName() ;
    			name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
    			LOG.info("Registering "+name);
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
    
    private final EntityHelper theEntityHelper = EntityHelper.getInstance() ;     
}
