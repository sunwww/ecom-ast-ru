package ru.ecom.api.sсheduler;

/** Created by rkurbanov on 04.09.2018. */
public class ServiceTasks {

    private String serviceName;

    public ServiceTasks(String serviceName) {
        this.serviceName = serviceName;
    }

    //public void getServName() {
        //System.out.println(serviceName);
    //}

    public void doSomebody(){
        System.out.println("Hello, iam "+serviceName);
    }
    public String toString() {
        return serviceName;
    }
}
