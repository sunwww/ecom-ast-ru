package ru.ecom.mis.ejb.service.sync.shubinok;

import javax.naming.InitialContext;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 28.01.2007
 * Time: 17:48:11
 * To change this template use File | Settings | File Templates.
 */
public class ShubinokSync implements ISync {
    public void sync(SyncContext aContext) throws Exception {
        InitialContext ctx = new InitialContext();
        try {
            ISyncShubinokService service = EjbInjection.getInstance().getLocalService(ISyncShubinokService.class) ;
            service.sync(aContext.getMonitorId(), aContext.getImportTime().getId());
        } finally {
            ctx.close() ;
        }
    }
}
