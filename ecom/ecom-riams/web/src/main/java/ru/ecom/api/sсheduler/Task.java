package ru.ecom.api.sсheduler;

import org.json.JSONException;

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

    public String getService() throws JSONException {
        ServiceTasks serv =services.get(0);
        return serv.getInfo();
    }

    public List<ServiceTasks> getServices() {
        return services;
    }

    public void setServices(List<ServiceTasks> services) {
        this.services = services;
    }

    public void run() {
        for (ServiceTasks service : services) {
            service.doRequest();
        }
    }

    void shutdown() {
        for (final ServiceTasks service : services) {
            System.out.println("Shutting down service " + service);
        }
    }

}
