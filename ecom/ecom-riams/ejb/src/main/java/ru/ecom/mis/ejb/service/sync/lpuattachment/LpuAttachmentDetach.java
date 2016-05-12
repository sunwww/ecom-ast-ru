package ru.ecom.mis.ejb.service.sync.lpuattachment;

import javax.naming.InitialContext;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

/**
 * 
 * @author VTsybulin 01.12.2014
 * 
 *
 */
public class LpuAttachmentDetach implements ISync {

	public void sync(SyncContext aContext) throws Exception {
		 InitialContext ctx = new InitialContext();
	        try {
	            ISyncAttachmentDetachService service = EjbInjection.getInstance().getLocalService(ISyncAttachmentDetachService.class) ;
	            service.sync(aContext.getMonitorId(), aContext.getImportTime().getId());
	        } finally {
	            ctx.close() ;
	        }
		
	}

}
