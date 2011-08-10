package ru.nuzmsh.ejb.exceptions;

import javax.ejb.EJBException;
import java.io.Serializable;

/**
 * Клонирует aCause
 */
public class EJBExceptionCause extends EJBException implements Serializable {

    public EJBExceptionCause(Exception aCause) {
        this(aCause.getMessage(), aCause) ;
    }

    public EJBExceptionCause(String aMessage, Exception aCause) {
        super(aMessage);
        theCause = CopyException.copyException(aCause) ;
    }

    public Throwable getCause() {
        return theCause ;
    }

    private final Throwable theCause ;

}
