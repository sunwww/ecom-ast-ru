package ru.nuzmsh.ejb.exceptions;

import java.util.ArrayList;

/**
 * Копирование Exception
 */
public class CopyException extends Exception {

    private CopyException(String aExceptionName, String aMessage, Throwable aCauseException) {
        super(aExceptionName+": "+aMessage, aCauseException) ;
        theMessage = aMessage ;
        System.out.println("aExceptionName = " + aExceptionName);
        System.out.println("aMessage = " + aMessage);
    }

//    public String getMessage() {
//       return  theMessage ;
//    }

    private final String theMessage ;


    public static CopyException copyException(Throwable aSource) {
        ArrayList<Throwable> exceptions = new ArrayList<Throwable>();
        copyException(exceptions, aSource) ;
//        Throwable cause = null ;
        CopyException ret = null ;
        for (Throwable exception : exceptions) {
            ret = new CopyException(exception.getClass().getName(), exception.getMessage(), ret);
            ret.setStackTrace(cloneStackTrace(exception));
//            cause = exception ;
        }
        return ret ;
    }

    private static StackTraceElement[] cloneStackTrace(Throwable aException) {
        StackTraceElement[] stack = aException.getStackTrace() ;
        StackTraceElement[] newStack = new StackTraceElement[stack.length];
        for (int i = 0; i < newStack.length; i++) {
            StackTraceElement element = stack[i];
            newStack[i] =
            new StackTraceElement(
                    element.getClassName(), element.getMethodName(), element.getFileName(), element.getLineNumber()
            );
        }
        return newStack ;
    }


    private static void copyException(ArrayList<Throwable> aArray, Throwable aException ) {
        if(aException!=null) {
            aArray.add(0,aException) ;
            copyException(aArray, aException.getCause()) ;
        }
    }
}
