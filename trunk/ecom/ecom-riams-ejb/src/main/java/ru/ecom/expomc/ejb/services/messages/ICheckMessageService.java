package ru.ecom.expomc.ejb.services.messages;

import java.util.Collection;

/**
 * Сообщения о проверках
 */
public interface ICheckMessageService {
    Collection<MessageRow> listMessage(long aTimeId) ;
    Object loadEntityByMessage(long aMessage) ;
    Collection<MessageChangeRow> listChanges(long aMessage) ;
    Collection<String> getBadProperties(long aMessageId) ;
	long getCheckByMessage(long aMessageId) ;

}
