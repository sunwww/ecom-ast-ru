package ru.nuzmsh.forms.response;

import java.io.Serializable;

/**
 * Информационное сообщение
 */
public class FormMessage implements Serializable {

    public FormMessage(String aMessage) {
        theMessage = aMessage ;
    }

    /**
     * Сообщение
     */
    public String getMessage() {
        return theMessage ;
    }

    public String toString() {
        return theMessage ;
    }

    private final String theMessage ;
}
