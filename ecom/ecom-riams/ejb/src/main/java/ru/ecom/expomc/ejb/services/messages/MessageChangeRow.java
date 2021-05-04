package ru.ecom.expomc.ejb.services.messages;

import java.io.Serializable;

/**
 */
public class MessageChangeRow implements Serializable  {
    /** Идентификатор сообщения */
    public long getMessageId() { return messageId ; }
    public void setMessageId(long aMessageId) { messageId = aMessageId ; }

    /** Идентификатор сообщения */
    private long messageId ;
    /** Свойство */
    public String getProperty() { return property ; }
    public void setProperty(String aProperty) { property = aProperty ; }

    /** Старое значение */
    public String getOldValue() { return oldValue ; }
    public void setOldValue(String aOldValue) { oldValue = aOldValue ; }

    /** Новое значение */
    public String getNewValue() { return newValue ; }
    public void setNewValue(String aNewValue) { newValue = aNewValue ; }

    /** Новое значение */
    private String newValue ;
    /** Старое значение */
    private String oldValue ;
    /** Свойство */
    private String property ;
}
