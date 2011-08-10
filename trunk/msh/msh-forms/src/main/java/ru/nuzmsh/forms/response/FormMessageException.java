package ru.nuzmsh.forms.response;

import java.io.Serializable;

/**
 * @author ESinev
 *         Date: 29.12.2005
 *         Time: 18:06:11
 */
public class FormMessageException extends Exception implements Serializable {

    public FormMessageException(String aMessageString) {
        this(aMessageString, null, null, null) ;
    }

    public FormMessageException(String aMessageString, Exception aCause) {
        this(aMessageString, null, null, aCause) ;
    }

    public FormMessageException(String aMessageString, String aTitle, String aHint, Exception aCause) {
        super(aMessageString, aCause);

        theMessage = aMessageString ;
        theTitle = aTitle ;
        theHint = aHint ;
    }




    public String getMessage() {
        return theMessage ;
    }


    private final String theTitle ;
    private final String theMessage ;
    private final String theHint ;
}
