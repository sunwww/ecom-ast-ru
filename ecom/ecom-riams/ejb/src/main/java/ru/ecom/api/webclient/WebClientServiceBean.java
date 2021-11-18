package ru.ecom.api.webclient;


import org.apache.log4j.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Stateless
@Remote(IWebClientService.class)
public class WebClientServiceBean implements IWebClientService {

    private static final Logger LOG = Logger.getLogger(WebClientServiceBean.class);

    @Override
    public String makePOSTRequest(String data, String address, String aMethod, Map<String, String> params) {
        //LOG.info("create connection, address = "+address+",method = "+aMethod+" , data="+data);
        if (address == null) {
            return "";
        }
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address + "/" + aMethod);
            connection = (HttpURLConnection) url.openConnection();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> par : params.entrySet()) {
                    //	LOG.info("send HTTP request. Key = "+par.getKey()+"<< value = "+par.getValue());
                    connection.setRequestProperty(par.getKey(), par.getValue());
                }
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStream writer = connection.getOutputStream();
            writer.write(data.getBytes(StandardCharsets.UTF_8));
            writer.flush();
            writer.close();
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String s;
                while ((s = in.readLine()) != null) {
                    response.append(s);
                }
            }
            connection.disconnect();
            return response.toString();

        } catch (ConnectException e) {
            LOG.error("Ошибка соединения с сервисом. " + address + ": " + e);
        } catch (Exception e) {
            if (connection != null) {
                connection.disconnect();
            }
            LOG.error("in thread happens exception" + e);
            e.printStackTrace();
        }
        return "";
    }

}
