package ru.ecom.mis.ejb.service.sync.vocomc_sprsmo;

import javax.naming.InitialContext;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

/**
 * 
 * @author VTsybulin 16.12.2014
 * 
 *
 */
public class VocSprSmo implements ISync {

	public void sync(SyncContext aContext) throws Exception {
		 InitialContext ctx = new InitialContext();
	        try {
	            ISyncVocSprSmoService service = EjbInjection.getInstance().getLocalService(ISyncVocSprSmoService.class) ;
	            service.sync(aContext.getMonitorId(), aContext.getImportTime().getId());
	        } finally {
	            ctx.close() ;
	        }
		
	}

}
