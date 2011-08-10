package ru.nuzmsh.ejb.exceptions;

import javax.ejb.CreateException;
import java.io.Serializable;

/**
 * Клонирует aCause
 */
public class CreateExceptionCause extends CreateException implements Serializable {
    public CreateExceptionCause(String aMessage, Throwable aCause) {
        super(aMessage) ;
        theCause = CopyException.copyException(aCause) ;
    }

    public Throwable getCause() {
        return theCause ;
    }

    private final Throwable theCause ;
}
