package ru.nuzmsh.web.messages;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Информационное сообщение
 */
public class InfoMessage {

    private static final String ATTRIBUTE = InfoMessage.class.toString() ;

    public InfoMessage(ServletRequest aRequest, String aMessage) {
        theMessage = aMessage ;
        aRequest.setAttribute(ATTRIBUTE, this);
        if(aRequest instanceof HttpServletRequest) {
            HttpSession session = ((HttpServletRequest)aRequest).getSession();
            if(session!=null) {
                session.setAttribute(ATTRIBUTE, this);
            }
        }
    }

    public static InfoMessage findMessage(ServletRequest aRequest) {
        InfoMessage message = (InfoMessage) aRequest.getAttribute(ATTRIBUTE) ;
        if(message==null && aRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) aRequest ;
            HttpSession session = request.getSession();
            if(session!=null) {
                message = (InfoMessage) session.getAttribute(ATTRIBUTE) ;
            } else {
                message = null ;
            }
        }
        return message;
    }

    public static void removeFromSession(HttpSession aSession) {
        if(aSession!=null) aSession.removeAttribute(ATTRIBUTE);
    }

    public String getMessage() {
        return theMessage ;
    }

    private final String theMessage ;
}
