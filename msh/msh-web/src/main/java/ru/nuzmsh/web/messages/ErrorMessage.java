package ru.nuzmsh.web.messages;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.nuzmsh.forms.response.FormMessageException;

/**
 * Сообщение об ошибке
 */
public class ErrorMessage {

    private static final String ATTRIBUTE_ID = ErrorMessage.class+".ERROR_MESSAGE" ;

    public ErrorMessage(ServletRequest aRequest, String aMessage , Exception aException) {
        theMessage = aMessage ;
        theException = aException ;
        if(aException!=null) {
            aException.printStackTrace() ;
        }
        aRequest.setAttribute(ATTRIBUTE_ID, this);
        if(aRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) aRequest ;
            HttpSession session = request.getSession() ;
            if(session!=null) {
                session.setAttribute(ATTRIBUTE_ID, this);
            }
        }
    }

    public ErrorMessage(ServletRequest aRequest, Exception aException) {
        this(aRequest, createMessage(aException), aException) ;
    }

    public ErrorMessage(ServletRequest aRequest, String aMessage) {
        this(aRequest, aMessage, null) ;
    }

    public static ErrorMessage findInRequest(ServletRequest aRequest) {

        ErrorMessage message = (ErrorMessage) aRequest.getAttribute(ATTRIBUTE_ID) ;
        if(message==null && aRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) aRequest ;
            HttpSession session = request.getSession() ;
            if(session!=null) {
                message = (ErrorMessage) session.getAttribute(ATTRIBUTE_ID);
                session.removeAttribute(ATTRIBUTE_ID);
            }
        }
        return message ;
    }

    public String getMessage() {
        return theMessage ;
    }

    private static String createMessage(Exception aException) {
        String message ;
        if(aException instanceof FormMessageException) {
            message = aException.getMessage() ;
        } else {
            StringBuilder sb = new StringBuilder();
            add(sb, aException) ;
            message = sb.toString();
        }
        return message ;
    }

    private static void add(StringBuilder sb, Throwable e) {
        if(! (e instanceof java.rmi.ServerException)) {
            sb.append(e.getMessage()) ;
            if(e.getCause()!=null) {
                sb.append(" : ") ;
            }
        }
        if(e.getCause()!=null) {
            add(sb, e.getCause()) ;

        }
    }

    public Exception getException() {
        return theException;
    }

    private final String theMessage ;
    private final Exception theException ;

}
