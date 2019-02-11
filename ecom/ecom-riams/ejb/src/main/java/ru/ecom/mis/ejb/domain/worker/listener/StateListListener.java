package ru.ecom.mis.ejb.domain.worker.listener;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.worker.Staff;
import ru.ecom.mis.ejb.service.worker.IStateListService;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 *
 */
public class StateListListener {
    public static final String SYNC = "STATE_LIST_LISTENER_SYNCHRONYZE";

    @PostPersist @PostUpdate
    public void postPersist(Staff aStateList) {
        synchronized (SYNC) {
        	EjbInjection.getInstance().getLocalService(IStateListService.class)
        		.onPersist(aStateList);
        }
    }

}
