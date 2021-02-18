package ru.ecom.mis.ejb.domain.worker.listener;

import javax.persistence.PostUpdate;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.service.worker.IWorkerService;

/**
 *
 */
public class WorkerListener {
    public static final String SYNC = "WORKER_LISTENER_SYNCHRONYZE";

    @PostUpdate
    public void postUpdate(Worker aWorker) {
        synchronized (SYNC) {
            EjbInjection.getInstance().getLocalService(IWorkerService.class)
                    .onUpdate(aWorker);
        }
    }

}
