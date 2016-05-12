package ru.ecom.mis.ejb.domain.lpu;

import javax.naming.NamingException;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;

/**
 */

public class AddressTextListener {

    public static final String SYNC = "ADDRESS_TEXT_LISTENER_SYNCHRONYZE";

    @PostPersist
    public void postPersist(final LpuAreaAddressText aText) throws NamingException {
        synchronized (SYNC) {
        	new Thread() {
        		public void run() {
        			try {
						findService().onPersist(aText);
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}.start(); // FIXME run and start
            
        }
    }

    @PostUpdate
    public void postUpdate(LpuAreaAddressText aText) throws NamingException {
        synchronized (SYNC) {
            findService().onUpdate(aText);
        }
    }

    @PreRemove
    public void onRemove(LpuAreaAddressText aText) throws NamingException {
        synchronized (SYNC) {
            findService().onRemove(aText);
        }
    }

    private IAddressPointService findService() throws NamingException {
    	return EjbInjection.getInstance().getLocalService(IAddressPointService.class) ;
    }

}
