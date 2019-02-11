package ru.ecom.api;

import ru.ecom.api.Onco.OncoResource;
import ru.ecom.api.fondCheck.FondCheck;
import ru.ecom.api.medcaseMedpolicy.MedcaseMedpolicy;
import ru.ecom.api.queue.HospitalQueueResource;
import ru.ecom.api.queue.TicketQueueResource;

import ru.ecom.api.disabilitySign.DisabilitySign;
import ru.ecom.api.scheduler.ScheduleTasks;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/api")
public class ApiApplication extends Application {

    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(ApiRecordResource.class);
        set.add(ApiLoginResource.class);
        set.add(HospitalQueueResource.class);
        set.add(TicketQueueResource.class);
        set.add(DisabilitySign.class);
        set.add(MedcaseMedpolicy.class);
        set.add(FondCheck.class);
        set.add(ScheduleTasks.class);
        set.add(OncoResource.class);
        return set;
    }
}
