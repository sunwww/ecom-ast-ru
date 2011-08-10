package ru.nuzmsh.ejb.exceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.FinderException;

/**
 * Клонирует aCause
 */
public class FinderExceptionCause extends FinderException {

    private final static Log LOG = LogFactory.getLog(FinderExceptionCause.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public FinderExceptionCause(String aMessage, Exception aCause) {
        super(aMessage);
        theMessage = aMessage ;
        LOG.error(aMessage, aCause) ;
        theCause = CopyException.copyException(aCause) ;

//        System.err.println("FinderExceptionCause: "+aCause) ;
//        aCause.printStackTrace() ;
//
//        try {
//            theCause = cloneException(aCause) ;
//        } catch (Exception e) {
//            theCause = null ;
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }

    public String getMessage() {
        return theMessage ;
    }

    public Throwable getCause() {
        return theCause;
    }


    public Exception cloneException(Exception aException) {
        Exception e = new Exception(aException.getMessage());
        StackTraceElement[] stack = aException.getStackTrace() ;
        StackTraceElement[] newStack = new StackTraceElement[stack.length];
        for (int i = 0; i < newStack.length; i++) {
            StackTraceElement element = stack[i];
            newStack[i] =
            new StackTraceElement(
                    element.getClassName(), element.getMethodName(), element.getFileName(), element.getLineNumber()
            );
        }
        e.setStackTrace(newStack);
        return e ;
    }

    private Exception theCause ;
    private final String theMessage ;

}
