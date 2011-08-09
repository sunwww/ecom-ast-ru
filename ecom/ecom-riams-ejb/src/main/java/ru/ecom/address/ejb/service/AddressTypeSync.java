package ru.ecom.address.ejb.service;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.address.ejb.domain.address.AddressType;
import ru.ecom.address.ejb.domain.kladr.KladrSocr;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

/**
 * Синхронизация типов адреса из кладр
 */
public class AddressTypeSync implements ISync {

    @SuppressWarnings("unchecked")
	private AddressType findByShortName(String aShortName) {
        List<AddressType> list = theEntityManager.createQuery("from AddressType where shortName = :shortName")
                .setParameter("shortName", aShortName).getResultList();
        return list!=null && list.size()==1 ? list.iterator().next() : null ;
    }


    @SuppressWarnings("unchecked")
	public void sync(SyncContext aContext) throws Exception {
        theEntityManager = aContext.getEntityManager();
//        aContext.getTransactionManager().begin();
        List<KladrSocr> list = theEntityManager.createQuery("from KladrSocr").getResultList();
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
            theEntityManager.persist(type);
        }
        monitor.finish(aContext.getImportTime().getId()+"");
        theEntityManager = null ;
//        aContext.getTransactionManager().commit();
    }

    private EntityManager theEntityManager ;
}
