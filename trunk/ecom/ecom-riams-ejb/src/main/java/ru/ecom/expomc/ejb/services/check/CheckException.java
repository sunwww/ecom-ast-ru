package ru.ecom.expomc.ejb.services.check;

import java.io.Serializable;

/**
 * Ошибка проверки
 */
public class CheckException extends Exception implements Serializable {
    public CheckException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
