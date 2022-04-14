package ru.ecom.mis.web.dwr.external;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Взаимодействие со сторонними системами посредством REST API
 */
public class ExternalServiceJs {

    public Object makeRequest(String url, String method, Map<String, Object> params, HttpServletRequest request) {
        if ("GET".equals(method)) {
            return makeGetRequest(url, params, request);
        } else if ("POST".equals(method)) {
            return makePostRequest(url, params, request);
        }
        else return null;

    }

    private Object makePostRequest(String url, Map<String, Object> params, HttpServletRequest request) {
        /*Form form = new Form("kk","");
                for(Map.Entry<String, String> param: params.entrySet()) {
                    form.param(param.getKey(), param.getValue());

                }*/
        Client client = ClientBuilder.newClient();
        Response response = client.target(url).resolveTemplates(params)
                        .request()
                        .post(Entity.entity(Object.class, MediaType.APPLICATION_JSON_TYPE));
        return response.getEntity();
    }

    private Object makeGetRequest(String url, Map<String, Object> params, HttpServletRequest request) {
        System.out.println("got GET request. url:"+url+", params: "+params);
        Client client = ClientBuilder.newClient();


        WebTarget t  = client.target(url);
        if (params !=null && !params.isEmpty()) {
            for (Map.Entry<String, Object> e: params.entrySet()) {
                t = t.queryParam(e.getKey(), e.getValue());
            }
        }

        return t.request(MediaType.APPLICATION_JSON)
                .get(Object.class);
    }
}
