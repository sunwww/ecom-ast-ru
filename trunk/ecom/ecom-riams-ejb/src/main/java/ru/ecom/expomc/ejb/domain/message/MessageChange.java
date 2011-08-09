package ru.ecom.expomc.ejb.domain.message;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;

/**
 * Изменение в записи
 */
@SuppressWarnings("serial")
@Entity
@Table(name="MESSAGE_CHANGE",schema="SQLUser")
public class MessageChange extends NoLiveBaseEntity {

    /** Название свойства, которое изменилось */
    public String getPropertyName() { return thePropertyName ; }
    public void setPropertyName(String aPropertyName) { thePropertyName = aPropertyName ; }

    /** Старое значение */
    public String getOldValue() { return theOldValue ; }
    public void setOldValue(String aOldValue) { theOldValue = aOldValue ; }

    /** Новое значение */
    public String getNewValue() { return theNewValue ; }
    public void setNewValue(String aNewValue) { theNewValue = aNewValue ; }

    /** Сообщение при котором произошло изменение в записи */
    @ManyToOne
    public Message getMessage() { return theMessage ; }
    public void setMessage(Message aMessage) { theMessage = aMessage ; }

    /** Сообщение при котором произошло изменение в записи */
    private Message theMessage ;
    /** Новое значение */
    private String theNewValue ;
    /** Старое значение */
    private String theOldValue ;
    /** Название свойства, которое изменилось */
    private String thePropertyName ;
}
