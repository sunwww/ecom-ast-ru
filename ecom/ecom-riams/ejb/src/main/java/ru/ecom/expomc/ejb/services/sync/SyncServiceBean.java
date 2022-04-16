package ru.ecom.expomc.ejb.services.sync;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
@Stateless
@Remote(ISyncService.class)
public class SyncServiceBean implements ISyncService {

    @Override
    public void syncByEntity(Long aMonitorId, String aEntity) {
        try {
            long aTimeId = Long.parseLong(entityManager.createNativeQuery("select max(impt.id)from Impdoc impd left join Imptime impt on impt.document_id=impd.id " +
                            "where impd.entityClassName=:aEntity")
                    .setParameter("aEntity", aEntity).getSingleResult().toString());

            sync(aMonitorId, aTimeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sync(long aMonitorId, long aTimeId) {
        ImportTime time = entityManager.find(ImportTime.class, aTimeId);
        try {
            ISync sync = instanceNewSync(time.getDocument());
            SyncContext ctx = new SyncContext(aMonitorId, time, entityManager);
            ctx.setMonitorService(monitorService);
            sync.sync(ctx);
        } catch (Exception e) {
            IMonitor monitor = monitorService.startMonitor(aMonitorId, "Ошибка синхронизации_", 10);
            monitor.setText(e + "");
            throw new IllegalStateException("Ошибка синхронизации", e);
        }
    }

    private ISync instanceNewSync(ImportDocument aDoc) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // todo
        switch (aDoc.getEntityClassName()) {
            case "ru.ecom.address.ejb.domain.kladr.KladrSocr":
                return (ISync) classLoaderHelper.loadClass("ru.ecom.address.ejb.service.AddressTypeSync").newInstance();
            case "ru.ecom.address.ejb.domain.kladr.Kladr":
                return (ISync) classLoaderHelper.loadClass("ru.ecom.address.ejb.service.AddressSync").newInstance();
            case "ru.ecom.mis.ejb.domain.external.PatientInfoShubinok":
                return (ISync) classLoaderHelper.loadClass("ru.ecom.mis.ejb.service.sync.shubinok.ShubinokSync").newInstance();
            case "ru.ecom.mis.ejb.domain.patient.PatientAttachedImport":
                return (ISync) classLoaderHelper.loadClass("ru.ecom.mis.ejb.service.sync.shubinok.BasePatientFondSync").newInstance();
            case "ru.ecom.expomc.ejb.domain.omcvoc.OmcLpu":
                return (ISync) classLoaderHelper.loadClass("ru.ecom.mis.ejb.service.lpu.LpuSync").newInstance();
            case "ru.ecom.expomc.ejb.domain.registry.RegistryEntry":
                return (ISync) classLoaderHelper.loadClass("ru.ecom.mis.ejb.service.synclpufond.LpuFondSync").newInstance();
            case "ru.ecom.expomc.ejb.domain.omcvoc.OmcSprSmo":
                return (ISync) classLoaderHelper.loadClass("ru.ecom.mis.ejb.service.sync.vocomc_sprsmo.VocSprSmo").newInstance();
            case "ru.ecom.address.ejb.domain.kladr.AltNames":
                return (ISync) classLoaderHelper.loadClass("ru.ecom.address.ejb.service.AltNamesSync").newInstance();
        }
        throw new IllegalArgumentException("Нет синхронизации для " + aDoc.getEntityClassName());
    }

    @PersistenceContext
    EntityManager entityManager;
    @EJB
    ILocalMonitorService monitorService;
    ClassLoaderHelper classLoaderHelper = ClassLoaderHelper.getInstance();

}
