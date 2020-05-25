package ru.ecom.api;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.api.entity.JsonCustomMessage;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.live.domain.CustomMessage;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Path("/customMessage")
public class CustomMessageResource {
    private static final Logger LOG = Logger.getLogger(CustomMessageResource.class);

    /**
     * Отправка пользовательских сообщений пользователям медоса
     * */
    @POST
    @Path("/sendMessage/")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendMessage(@Context HttpServletRequest aRequest, String body) throws NamingException {
        JsonCustomMessage message = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create().fromJson(body, JsonCustomMessage.class);
        ApiUtil.login(message.getToken(), aRequest);
        LoginInfo loginInfo = LoginInfo.find(aRequest.getSession());

        JSONObject errors = checkMessage(message);
        if (errors!=null) return errors.toString();
        String[] recipients;
        if (Boolean.TRUE.equals(message.getSendAllUsers())) {
            IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
            recipients = service.executeNativeSql("select list(su.login) from secuser su where su.disabled = '0' or su.disabled is null").iterator().next().get1().toString().split(",");
        } else {
            recipients = message.getRecipients();
        }

        CustomMessage customMessage = new CustomMessage(message.getIsEmergency());
        customMessage.setMessageText(message.getMessageText());
        customMessage.setMessageTitle(message.getMessageTitle());
        customMessage.setUsername(loginInfo.getUsername());
        customMessage.setMessageUrl(message.getMessageUrl());
        Integer minutes = message.getValidityMinutes();
        if (minutes!=null && minutes>0) { //продолжительность  активности сообщения.
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.MINUTE,minutes);
            customMessage.setValidityDate(new Date(cal.getTimeInMillis()));
            customMessage.setValidityTime(new Time(cal.getTimeInMillis()));
        } else if (message.getValidityDate()!=null) {
            customMessage.setValidityDate(new Date(message.getValidityDate().getTime()));
            customMessage.setValidityTime(new Time(message.getValidityDate().getTime()));
        }
        IApiService service = Injection.find(aRequest).getService(IApiService.class);
        int i=0;
        for (String username: recipients) {
            CustomMessage cm = new CustomMessage(customMessage);
            cm.setRecipient(username);
            service.persistEntity(cm);
            i++;
        }
        return okJson("good").put("cntMessages",i).toString();
    }

    private JSONObject okJson(String txt) {
        return new JSONObject().put("status","ok").put("someText",txt==null? "ok" : txt);
    }
    private JSONObject errorJson(String errorCode, String errorName) {
        return new JSONObject().put("status","error").put("errorCode",errorCode).put("errorName",errorName);
    }

    private JSONObject checkMessage(JsonCustomMessage message) {
        StringBuilder err = new StringBuilder();
        if (!Boolean.TRUE.equals(message.getSendAllUsers()) && (message.getRecipients()==null || message.getRecipients().length==0)) {
            err.append("Необходимо указать получателей сообщения;");
        }
        if (StringUtil.isNullOrEmpty(message.getMessageText())) {
            err.append("Необходимо заполнить текст сообщения;");
        }
        return err.length()==0 ? null : errorJson("FLK",err.toString());
    }
}
