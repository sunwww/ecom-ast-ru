package ru.nuzmsh.forms.response;

import java.io.Serializable;

/**
 * Информационное сообщение
 */
public class FormMessage implements Serializable {

    public FormMessage(String aMessage) {
        theMessage = aMessage ;theAutoHide = true;
    }
    public FormMessage(String aMessage, Boolean isAutoHide) {
        theMessage = aMessage ;
        theAutoHide = isAutoHide;
    }

    /**
     * Сообщение
     */
    public String getMessage() {
        return theMessage ;
    }
    public Boolean getAutoHide() {
        return theAutoHide ;
    }

    public String toString() {
        return theMessage ;
    }

    private final String theMessage ;
    private final Boolean theAutoHide ;
}
