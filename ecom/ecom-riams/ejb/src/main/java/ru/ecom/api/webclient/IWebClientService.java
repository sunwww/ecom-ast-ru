package ru.ecom.api.webclient;

import java.util.Map;

public interface IWebClientService {
    /**
     * Выполнияем post запрос
     * @param data JSON объект строкой
     * @param address Адрес сервиса
     * @param aMethod Метод сервиса
     * @param params  хз,
     * @return Response
     */
    String makePOSTRequest(String data, String address, String aMethod, Map<String, String> params);
}
