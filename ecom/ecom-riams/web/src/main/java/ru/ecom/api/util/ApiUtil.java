package ru.ecom.api.util;

import ru.ecom.web.login.LoginInfo;
import ru.nuzmsh.commons.auth.ILoginInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class ApiUtil {
    public static final String KEY = ILoginInfo.class.getName();

    public static void login(String aToken, HttpServletRequest aRequeset) {
        login(aToken, aRequeset.getSession());
    }

    public static String login(String aToken, HttpSession aSession) {
        if (aSession.getAttribute(KEY) == null) {
            if (aToken == null) {
                throw new IllegalStateException("NO_TOKEN");
            }
            LoginInfo loginInfo = new LoginInfo(aToken, aToken);
            loginInfo.saveTo(aSession);
        }
        return "logined!";
    }

    public static void init(HttpServletRequest aRequest, String aToken) {
        if (aToken != null) {
            login(aToken, aRequest);
        }
    }

    public static String logout(HttpServletRequest aRequest) {
        aRequest.getSession().removeAttribute(KEY);
        return "success";
    }

    /**
     * @param endpoint eg.: http://127.0.0.1:8080
     * @param path     eg.: record/getDoctor
     * @param params   eg.: ( ?doctor_id=100500)
     *                 HashMap<String, String> params = new HashMap<>();
     *                 params.put("doctor_id",100500);
     * @return JSON response
     */
    public static String createGetRequest(String endpoint, String path, Map<String, String> params) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        target = target.path(path);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            target = target.queryParam(entry.getKey(), entry.getValue());
        }
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        System.out.println(response);
        return response.readEntity(String.class);
    }

    public static String createGetRequest(String path) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(path);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        System.out.println(response);
        return response.readEntity(String.class);
    }

    public static String creteGetRequest(String endpoint, String path, String mediaType, Map<String, String> params) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        target = target.path(path);
        for (Map.Entry<String, String> param : params.entrySet())
            target = target.queryParam(param.getKey(), param.getValue());
        Response response = target.request(mediaType)
                .header("Accept", "application/xml")
                .get();

        return response.readEntity(String.class);
    }

    /**
     * @param endpoint
     * @param path
     * @param json
     * @return JSON response
     */
    public static String createPostRequest(String endpoint, String path, String json, String mediaType) {
        return createPostRequest(endpoint, path, json, mediaType, null);
    }

    public static String createPostRequest(String endpoint, String path, String json, String mediaType, Map<String, Object> paramMap) {
        WebTarget webTarget = ClientBuilder.newClient()
                .target(endpoint)
                .path(path);
        if (paramMap != null && !paramMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return webTarget
                .request(mediaType)
                .header("Access-Control-Allow-Headers", "X-Requested-With, content-type")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .post(Entity.json(json))
                .readEntity(String.class);
    }


}
