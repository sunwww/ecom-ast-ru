package ru.ecom.mis.ejb.service.sync.lpuattachment;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

import javax.naming.InitialContext;

/**
 * 
 * @author VTsybulin 01.12.2014
 * 
 *
 */
@Deprecated
public class LpuAttachmentDefect implements ISync {

	public void sync(SyncContext aContext) throws Exception {
		 InitialContext ctx = new InitialContext();
	        try {
	            ISyncAttachmentDefectService service = EjbInjection.getInstance().getLocalService(ISyncAttachmentDefectService.class) ;
	            service.sync(aContext.getMonitorId(), aContext.getImportTime().getId());
	        } finally {
	            ctx.close() ;
	        }
		
	}

}
