package ru.ecom.expomc.ejb.services.sync;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;

/**
 *
 */
@Stateless
@Remote(ISyncService.class)
//@TransactionManagement(TransactionManagementType.BEAN)
public class SyncServiceBean implements ISyncService {

    public void sync(long aMonitorId, long aTimeId) {
        ImportTime time = theEntityManager.find(ImportTime.class, aTimeId) ;
        try {
            ISync sync = instanceNewSync(time.getDocument()) ;
            SyncContext ctx = new SyncContext(aMonitorId,time,theEntityManager);
            ctx.setMonitorService(theMonitorService);
//            ctx.setTransactionManager(theUserTransaction);
            sync.sync(ctx);
        } catch (Exception e) {
            IMonitor monitor = theMonitorService.getMonitor(aMonitorId);
            if(monitor==null) {
                monitor = theMonitorService.startMonitor(aMonitorId, "Ошибка синхронизации",10) ;
            }
            monitor.setText(e+"");
            throw new IllegalStateException("Ошибка синхронизации",e) ;
        }
    }

    private ISync instanceNewSync(ImportDocument aDoc) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // todo
        if(aDoc.getEntityClassName().equals("ru.ecom.address.ejb.domain.kladr.KladrSocr")) {
            return (ISync) theClassLoaderHelper.loadClass("ru.ecom.address.ejb.service.AddressTypeSync").newInstance() ;
        } else if(aDoc.getEntityClassName().equals("ru.ecom.address.ejb.domain.kladr.Kladr")) {
            return (ISync) theClassLoaderHelper.loadClass("ru.ecom.address.ejb.service.AddressSync").newInstance() ;
        } else  if(aDoc.getEntityClassName().equals("ru.ecom.mis.ejb.domain.external.PatientInfoShubinok")) {
        	return (ISync) theClassLoaderHelper.loadClass("ru.ecom.mis.ejb.service.sync.shubinok.ShubinokSync").newInstance() ;
        } else  if(aDoc.getEntityClassName().equals("ru.ecom.expomc.ejb.domain.omcvoc.OmcLpu")) {
            return (ISync) theClassLoaderHelper.loadClass("ru.ecom.mis.ejb.service.lpu.LpuSync").newInstance() ;
        }
        throw new IllegalArgumentException("Нет синхронизации для "+aDoc.getEntityClassName()) ;
    }

    @PersistenceContext EntityManager theEntityManager ;
    @EJB ILocalMonitorService theMonitorService ;
    ClassLoaderHelper theClassLoaderHelper = ClassLoaderHelper.getInstance();
//    @Resource UserTransaction theUserTransaction;

}
