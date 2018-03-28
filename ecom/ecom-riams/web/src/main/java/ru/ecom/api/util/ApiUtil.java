package ru.ecom.api.util;

import ru.ecom.web.login.LoginInfo;
import ru.nuzmsh.commons.auth.ILoginInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ApiUtil {
    public static final String key = ILoginInfo.class.getName() ;

    public static void login (String aToken, HttpServletRequest aRequeset) {
        login(aToken,aRequeset.getSession());
    }
    public static String login(String aToken, HttpSession aSession) {
        if (aSession.getAttribute(key)==null) {
            if (aToken==null) {throw new IllegalStateException("NO_TOKEN");}
            LoginInfo loginInfo = new LoginInfo(aToken,aToken);
            loginInfo.saveTo(aSession);
        }
        return "logined!";
    }
    public static void init(HttpServletRequest aRequest, String aToken) {
        if (aToken!=null) {
            login(aToken,aRequest);
        }


    }

    public static String logout(HttpServletRequest aRequest) {
        aRequest.getSession().removeAttribute(key);return "success";
    }
}
