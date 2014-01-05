package ru.ecom.mis.ejb.service.sync.shubinok;

import javax.naming.InitialContext;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

public class BasePatientFondSync implements ISync {
    public void sync(SyncContext aContext) throws Exception {
        InitialContext ctx = new InitialContext();
        try {
            ISyncShubinokService service = EjbInjection.getInstance().getLocalService(ISyncShubinokService.class) ;
            service.syncPatientByFond(aContext.getMonitorId(), aContext.getImportTime().getId());
        } finally {
            ctx.close() ;
        }
    }
    
}
