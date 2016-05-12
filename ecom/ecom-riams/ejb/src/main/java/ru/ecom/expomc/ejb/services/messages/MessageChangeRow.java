package ru.ecom.expomc.ejb.services.messages;

import java.io.Serializable;

/**
 */
public class MessageChangeRow implements Serializable  {
    /** Идентификатор сообщения */
    public long getMessageId() { return theMessageId ; }
    public void setMessageId(long aMessageId) { theMessageId = aMessageId ; }

    /** Идентификатор сообщения */
    private long theMessageId ;
    /** Свойство */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Старое значение */
    public String getOldValue() { return theOldValue ; }
    public void setOldValue(String aOldValue) { theOldValue = aOldValue ; }

    /** Новое значение */
    public String getNewValue() { return theNewValue ; }
    public void setNewValue(String aNewValue) { theNewValue = aNewValue ; }

    /** Новое значение */
    private String theNewValue ;
    /** Старое значение */
    private String theOldValue ;
    /** Свойство */
    private String theProperty ;
}
