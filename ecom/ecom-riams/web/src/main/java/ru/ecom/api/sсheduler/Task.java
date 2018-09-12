package ru.ecom.api.sсheduler;

import org.json.JSONException;
import org.json.JSONObject;

import static ru.ecom.api.util.ApiUtil.createGetRequest;

/** Created by rkurbanov on 04.09.2018. */
public class Task implements Runnable {

    private Long id;
    private String serviceName;
    private String link;


    public Task(Long id,String serviceName,String link) {
        this.id = id;
        this.serviceName = serviceName;
        this.link = link;
    }

    public String getName(){
        return serviceName;
    }

    public Long getId() {
        return id;
    }

    public String getServiceInfo() throws JSONException {
        return new JSONObject()
                .put("id",String.valueOf(id))
                .put("serviceName",serviceName)
                .put("link",link).toString();
    }

    public void run() {
        createGetRequest(link);
    }

}
