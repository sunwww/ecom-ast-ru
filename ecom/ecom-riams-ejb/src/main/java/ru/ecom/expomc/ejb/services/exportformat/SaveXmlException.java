package ru.ecom.expomc.ejb.services.exportformat;

/**
 * @author ikouzmin 13.03.2007 17:29:30
 */
public class SaveXmlException extends Exception {
    public SaveXmlException (String message,Throwable cause) {
        super(message,cause);
    }

    public SaveXmlException(String message) {
        super(message);
    }
    
}
