package ru.ecom.web.login;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class LoginErrorMessage {

    public static final String KEY = LoginErrorMessage.class.getName();

    public static String getMessage(HttpServletRequest aRequest) {
        return (String) aRequest.getAttribute(KEY);
    }

    public static void setMessage(HttpServletRequest aRequest, String aMessage) {
        aRequest.setAttribute(KEY, aMessage);
    }
}
