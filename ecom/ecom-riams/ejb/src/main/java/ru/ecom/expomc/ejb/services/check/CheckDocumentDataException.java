package ru.ecom.expomc.ejb.services.check;

/**
 * @author esinev
 * Date: 23.08.2006
 * Time: 11:24:24
 */
public class CheckDocumentDataException extends Exception {
    public CheckDocumentDataException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public CheckDocumentDataException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
