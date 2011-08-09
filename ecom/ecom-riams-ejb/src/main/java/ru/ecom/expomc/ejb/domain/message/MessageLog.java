package ru.ecom.expomc.ejb.domain.message;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;

/**
 * Лог
 */
@SuppressWarnings("serial")
@Entity
@Table(name="MESSAGE_LOG",schema="SQLUser")
public class MessageLog extends NoLiveBaseEntity {

    public static final int ERROR = 1 ;
    public static final int WARN = 2 ;
    public static final int INFO = 3 ;
    public static final int DEBUG = 4 ;

    /** Текст */
    public String getText() { return theText ; }
    public void setText(String aText) { theText = aText ; }

    /** Сообщение, при котором произошло изменение в записи */
    @ManyToOne
    public Message getMessage() { return theMessage ; }
    public void setMessage(Message aMessage) { theMessage = aMessage ; }

    /** Тип */
    public int getLogType() { return theLogType ; }
    public void setLogType(int aLogType) { theLogType = aLogType ; }

    /** Тип */
    private int theLogType ;
    /** Текст */
    private String theText ;
    /** Сообщение, при котором произошло изменение в записи */
    private Message theMessage ;
}
