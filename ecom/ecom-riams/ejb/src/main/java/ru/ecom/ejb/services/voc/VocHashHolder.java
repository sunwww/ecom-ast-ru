package ru.ecom.ejb.services.voc;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.destroy.DestroyContext;
import ru.ecom.ejb.services.destroy.IDestroyable;

import java.util.Map;
import java.util.TreeMap;

public class VocHashHolder implements IDestroyable {

	private static final Logger LOG = Logger.getLogger(VocHashHolder.class);

	public void destroy(DestroyContext aContext) {
        LOG.info("Destroying all vocs...");
        for (Map.Entry<String, IVocContextService> entry : theHash.entrySet()) {
            IVocContextService service = entry.getValue();
            StringBuilder sb = new StringBuilder();
            sb.append(" Destroyed ");
       /*     if (service instanceof IPersistenceContextRequired) {
                ((IPersistenceContextRequired) service).setEntityManager(null);
                sb.append(" p ");
            } else {
                sb.append("   ");
            } */
            if (service instanceof IVocServiceManagement) {
                ((IVocServiceManagement) service).destroy();
                sb.append(" m ");
            } else {
                sb.append("   ");
            }

            LOG.info(sb.append(entry.getKey()).toString());
        }
        theHash.clear();
	}

	
    private final TreeMap<String, IVocContextService> theHash = new TreeMap<String, IVocContextService>();

	public Map<String, IVocContextService> getHash() {
		return theHash ;
	}
}
