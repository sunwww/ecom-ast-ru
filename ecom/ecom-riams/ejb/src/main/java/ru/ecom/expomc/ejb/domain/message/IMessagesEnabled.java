package ru.ecom.expomc.ejb.domain.message;

import java.util.Collection;

/**
 * @author esinev
 * Date: 23.08.2006
 * Time: 14:24:15
 */
public interface IMessagesEnabled {

    Collection<Message> getMessages() ;
    void setMessages(Collection<Message> aMessages) ;
}
