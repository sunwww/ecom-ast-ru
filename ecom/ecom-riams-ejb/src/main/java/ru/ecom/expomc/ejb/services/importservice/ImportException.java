package ru.ecom.expomc.ejb.services.importservice;

/**
 * @author esinev 19.08.2006 13:06:00
 */
public class ImportException extends Exception {
    public ImportException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ImportException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
