package ru.ecom.mis.ejb.service.synclpufond;

import javax.naming.InitialContext;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

public class LpuFondSync  implements ISync {
    public void sync(SyncContext aContext) throws Exception {
        InitialContext ctx = new InitialContext();
        try {
            ISyncLpuFondService service = EjbInjection.getInstance().getLocalService(ISyncLpuFondService.class) ;
            service.sync(aContext.getMonitorId(), aContext.getImportTime().getId());
        } finally {
            ctx.close() ;
        }
    }
}
