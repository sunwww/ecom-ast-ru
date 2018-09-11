package ru.ecom.api.sсheduler;

import org.json.JSONException;
import org.json.JSONObject;

import static ru.ecom.api.util.ApiUtil.createGetRequest;

/** Created by rkurbanov on 04.09.2018. */
public class ServiceTasks {

    private Long id;
    private String serviceName;
    private String link;


    public ServiceTasks(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceTasks(Long id,String serviceName,String link) {
        this.id = id;
        this.serviceName = serviceName;
        this.link = link;
    }

    public void doRequest(){
        createGetRequest(link);
    }

    public String getInfo() throws JSONException {
        return new JSONObject().put("id",String.valueOf(id)).put("serviceName",serviceName).put("link",link).toString();
    }

    public String toString() {
        return serviceName;
    }
}
