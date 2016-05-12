package ru.ecom.expomc.ejb.services.messages;

import java.io.Serializable;

/**
 * Сообщение
 */
public class MessageRow implements Serializable {

    /** Идентфикатор сообщения */
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Идентфикатор сообщения */
    private long theId ;
    /** Название проверки */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Название типа сообщения */
    public String getCheckTypeName() { return theCheckTypeName ; }
    public void setCheckTypeName(String aCheckTypeName) { theCheckTypeName = aCheckTypeName ; }

    /** Название типа сообщения */
    private String theCheckTypeName ;
    
    /** Комментарий */
    private String theComment ;
    /** Название проверки */
    private String theName ;
}
