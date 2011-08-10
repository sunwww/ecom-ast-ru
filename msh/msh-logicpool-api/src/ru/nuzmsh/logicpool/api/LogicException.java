package ru.nuzmsh.logicpool.api;


import java.io.Serializable;

/**
 * Сообщение об ошибке при работе с Cache.
 * .getCause() - содержит или com.intersys.objects.CacheException для Cache 5.x
 * , или ...
 *
 * @author ESinev
 *         Date: 17.10.2005
 *         Time: 13:48:56
 */
public class LogicException extends Exception implements Serializable {

    public LogicException(String message, Exception aCause) {
        super(message, aCause);
    }

    public LogicException(String message) {
        super(message);
    }

}
