package ru.ecom.ejb.services.destroy;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.Service;
import ru.nuzmsh.util.PropertyUtil;

import javax.ejb.Local;
import java.beans.Introspector;
import java.util.LinkedList;

@Service
@Local(IDestroyService.class)
public class DestroyServiceBean implements IDestroyService, IDestroyManagementService {

	private static final Logger LOG = Logger
			.getLogger(DestroyServiceBean.class);

	public void add(IDestroyable aDestroyable) {
		LOG.info(" Add "+aDestroyable.getClass().getSimpleName()) ;
		theListeners.add(aDestroyable) ;
	}

	public void destroy() {
		LOG.info("Destroing ...") ;
		DestroyContext ctx = new DestroyContext() ;
		for(IDestroyable d : theListeners) {
			LOG.info("  "+d.getClass().getSimpleName()) ;
			try {
				d.destroy(ctx);
			} catch(Exception e) {
				LOG.error(e);
			}
		}
		
		LOG.info("Destroying...");
		LOG.info("  Destoying PropertyUtil hash...") ;
		PropertyUtil.destroy() ;
		LOG.info("  Destroying Introspector's caches...") ;
		PropertyUtil.destroy() ;
		Introspector.flushCaches() ;
		//PropertyUtilsBean.clearDescriptors();
	}
	
	private final LinkedList<IDestroyable> theListeners = new LinkedList<>() ;
}
