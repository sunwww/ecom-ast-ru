package ru.ecom.expomc.ejb.services.messages;

import java.io.Serializable;

/**
 * Сообщение
 */
public class MessageRow implements Serializable {

    /** Идентфикатор сообщения */
    public long getId() { return id ; }
    public void setId(long aId) { id = aId ; }

    /** Идентфикатор сообщения */
    private long id ;
    /** Название проверки */
    public String getName() { return name ; }
    public void setName(String aName) { name = aName ; }

    /** Комментарий */
    public String getComment() { return comment ; }
    public void setComment(String aComment) { comment = aComment ; }

    /** Название типа сообщения */
    public String getCheckTypeName() { return checkTypeName ; }
    public void setCheckTypeName(String aCheckTypeName) { checkTypeName = aCheckTypeName ; }

    /** Название типа сообщения */
    private String checkTypeName ;
    
    /** Комментарий */
    private String comment ;
    /** Название проверки */
    private String name ;
}
