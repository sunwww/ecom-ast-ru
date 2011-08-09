package ru.ecom.ejb.services.voc;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.annotation.security.SecurityDomain;

import ru.ecom.ejb.services.destroy.IDestroyService;
import ru.ecom.ejb.services.voc.helper.PersistenceXmlVocLoader;
import ru.ecom.ejb.services.voc.helper.XmlVocValueLoader;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.IVocService;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

/**
 * 
 */
@Stateless
@Remote(IVocService.class)
@Local(IVocInfoService.class)
@SecurityDomain("other")
public class VocValueServiceBean implements IVocService, IVocInfoService {

    private final static Log LOG = LogFactory.getLog(VocValueServiceBean.class);
    private final static boolean RELOAD_HASH_EVERY_TIME = !StringUtil.isNullOrEmpty(EjbEcomConfig.getInstance().get(EjbEcomConfig.VOC_DIR_PREFIX,null)) ;
    
    //private final static boolean CAN_TRACE = LOG.isTraceEnabled();

    private static VocHashHolder HOLDER = null ;
    private static final String SYNC_VOCVALUESERVICE = "SYNC_VOCVALUESERVICE" ;
    
    public VocValueServiceBean()  {
    	reloadVocs() ;
    }


    private static synchronized void reloadVocs() {
    	if(HOLDER==null) { // если еще нет Holder'a, создаем его
        	try {
        		// все ждут первого
	    		synchronized(SYNC_VOCVALUESERVICE) {
	    			if(HOLDER!=null) return ; // этот первый создал Holder'a, остальные просто выходят
	    			HOLDER = new VocHashHolder() ;
	    			
	    			PersistenceXmlVocLoader persistenceXmlVocLoader = new PersistenceXmlVocLoader() ;
	    			persistenceXmlVocLoader.load(HOLDER.getHash());
	    			
	    	        XmlVocValueLoader loader = new XmlVocValueLoader();
	    	        loader.load(HOLDER.getHash());
	    	        EjbInjection.getInstance().getLocalService(IDestroyService.class).add(HOLDER) ;
	    		}
        	} catch (Exception e) {
        		LOG.error("Error while initializing vocs: "+e.getMessage(),e);
        		// HIDE ERROR to allow service starts
        		// throw new IllegalStateException("Error while initializing vocs: "+e.getMessage(),e) ; 
        	}
    	}
    	
    }

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional) throws VocServiceException {
         return getVoc(aVocName).getNameById(aId, aVocName, aAdditional, createContext());
    }

    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional) throws VocServiceException {
         return getVoc(aVocName).findVocValueByQuery(aVocName, aQuery, aCount, aAdditional, createContext());
    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional) throws VocServiceException {
         return getVoc(aVocName).findVocValuePrevious(aVocName, aId, aCount, aAdditional, createContext());
    }

    
    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional) {
        return getVoc(aVocName).findVocValueNext(aVocName, aId, aCount, aAdditional, createContext());
    }


    private VocContext createContext() {
    	VocContext ctx = new VocContext(theEntityManager, theSessionContext) ;
    	return ctx ;
    }
    
    /**
     * Обязательно перегрузить справочники
     */
    private void reloadRequired() { 
    	synchronized(SYNC_VOCVALUESERVICE) { ;
    	HOLDER = null ;
    	reloadVocs();
    }
}
    private IVocContextService getVoc(String aVocName) {
		if(RELOAD_HASH_EVERY_TIME) reloadRequired() ;
        IVocContextService service = HOLDER.getHash().get(aVocName);
        if (service == null) throw new IllegalArgumentException("Нет справочника " + aVocName);
        return service ;
    }

    private @PersistenceContext EntityManager theEntityManager ;
//    private @PersistenceUnit  EntityManagerFactory theFactory;

    private @Resource SessionContext theSessionContext;


	public IVocContextService getVocService(String aVocKey) {
		if(RELOAD_HASH_EVERY_TIME) reloadRequired();
		return HOLDER.getHash().get(aVocKey) ;
	}
}
