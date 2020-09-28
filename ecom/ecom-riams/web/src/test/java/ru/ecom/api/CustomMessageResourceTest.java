package ru.ecom.api;

import com.google.gson.Gson;
import org.json.JSONObject;
import ru.ecom.api.entity.JsonCustomMessage;

import javax.naming.NamingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomMessageResourceTest {

    private final static String TOKEN = "testApi";

    @org.junit.jupiter.api.Test
    void sendMessage() throws NamingException {
        CustomMessageResource r = getResource();
        JsonCustomMessage message = new JsonCustomMessage();
        message.setToken(TOKEN);
        message.setMessageText("hello Vasya");
        message.setRecipients(new String[]{"ivanov","petrov"});
        JSONObject res =new JSONObject(r.sendMessage(null,new Gson().toJson(message)));
        System.out.println(res);

        assertEquals("ok",res.getString("status"));
        assertEquals(res.getInt("cntMessages"),2);
    }

    private CustomMessageResource getResource() {
        return new CustomMessageResource();
    }
}