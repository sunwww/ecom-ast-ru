package ru.ecom.api.webclient;

import org.json.JSONObject;

import java.util.Map;

public interface IWebClientService {
    /**
     * Выполнияем post запрос
     *
     * @param data    JSON объект строкой
     * @param address Адрес сервиса
     * @param aMethod Метод сервиса
     * @param params  хз,
     * @return Response
     */
    String makePOSTRequest(String data, String address, String aMethod, Map<String, String> params);

    //TODO Map<Status, String response>
    Map.Entry<Integer, JSONObject> makePOSTRequestExt(String data, String address, String aMethod, Map<String, String> params);

    /**
     * Get запрос
     * @param address
     * @param method
     * @return
     */
    Map.Entry<Integer, JSONObject> makeGetRequest(String address, String method);
}
