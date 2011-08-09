package ru.ecom.expomc.ejb.services.importservice;

import java.io.Serializable;

/**
 * @author esinev 20.08.2006 21:50:37
 */
public class ImportFileResult implements Serializable {

    public ImportFileResult(long aTimeId) {
        theTimeId = aTimeId;
    }

    /** Идентификатор time */
    public long getTimeId() { return theTimeId ; }

    public void addMessage(String aMessage) {
        sb.append(aMessage) ;
        sb.append("\n") ;
    }

    public String getMessages() {
        return sb.toString() ;
    }

    StringBuilder sb = new StringBuilder();
    /** Идентификатор time */
    private final long theTimeId ;
}
