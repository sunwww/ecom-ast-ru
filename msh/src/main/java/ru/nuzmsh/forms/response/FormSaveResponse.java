package ru.nuzmsh.forms.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Отчет о сохранении формы
 */
public class FormSaveResponse implements Serializable {

    /**
     * Добавляет информационное сообщение
     * @param aMessage
     */
    public void addFormMessage(FormMessage aMessage) {
        theFormMessages.add(aMessage) ;
    }

    public Iterator<FormMessage> getFormMessageIterator() {
        return theFormMessages.iterator() ;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (FormMessage message : theFormMessages) {
            sb.append(message.getMessage()) ;
            sb.append("\n") ;
        }
        return sb.toString();
    }

    public boolean isFormMessagesEmpty() {
        return theFormMessages.isEmpty() ;
    }

    public static FormSaveResponse createOneMessageResponse(String aMessage) {
        FormSaveResponse r = new FormSaveResponse();
        r.addFormMessage(new FormMessage(aMessage));
        return r ;
    }
    
    private ArrayList<FormMessage> theFormMessages = new ArrayList<>();
}
