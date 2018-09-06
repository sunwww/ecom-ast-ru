package ru.ecom.api.sсheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkurbanov on 04.09.2018.
 */
public class Task implements Runnable {

    private List<ServiceTasks> services = new ArrayList();

    public void addService(ServiceTasks service) {
        services.add(service);
    }

    public String getService(){
        ServiceTasks serv =services.get(0);
        return serv.toString();
    }

    public List<ServiceTasks> getServices() {
        return services;
    }

    public void setServices(List<ServiceTasks> services) {
        this.services = services;
    }

    public void run() {
        for (ServiceTasks service : services) {
            service.doSomebody();
        }
    }

    void shutdown() {
        for (final ServiceTasks service : services) {
            System.out.println("Shutting down service " + service);
        }
    }

}
