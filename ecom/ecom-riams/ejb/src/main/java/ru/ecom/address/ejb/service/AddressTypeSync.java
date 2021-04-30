package ru.ecom.address.ejb.service;

import ru.ecom.address.ejb.domain.address.AddressType;
import ru.ecom.address.ejb.domain.kladr.KladrSocr;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Синхронизация типов адреса из кладр
 */
public class AddressTypeSync implements ISync {

    @SuppressWarnings("unchecked")
	private AddressType findByShortName(String aShortName) {
        List<AddressType> list = entityManager.createQuery("from AddressType where shortName = :shortName")
                .setParameter("shortName", aShortName).getResultList();
        return list!=null && list.size()==1 ? list.iterator().next() : null ;
    }


    @SuppressWarnings("unchecked")
	public void sync(SyncContext aContext) {
        entityManager = aContext.getEntityManager();
//        aContext.getTransactionManager().begin();
        List<KladrSocr> list = entityManager.createQuery("from KladrSocr").getResultList();
        IMonitor monitor = aContext.getMonitorService().startMonitor(aContext.getMonitorId(), "Синхронизация ",list.size()) ;
        for (KladrSocr socr : list) {
            monitor.advice(1);
            AddressType type = findByShortName(socr.getShortName()) ;
            monitor.setText(socr.getName());
            if(type==null) {
                type = new AddressType();
            }
            type.setName(socr.getName());
            type.setShortName(socr.getShortName());
            entityManager.persist(type);
        }
        monitor.finish(aContext.getImportTime().getId()+"");
        entityManager = null ;
//        aContext.getTransactionManager().commit();
    }

    private EntityManager entityManager ;
}
