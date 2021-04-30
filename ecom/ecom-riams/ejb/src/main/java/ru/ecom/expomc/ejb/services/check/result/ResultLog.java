package ru.ecom.expomc.ejb.services.check.result;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 25.08.2006
 * Time: 3:42:29
 * To change this template use File | Settings | File Templates.
 */
public class ResultLog {

    public ResultLog(int aType, String aMessage) {
        type = aType;
        message = aMessage;
    }
    /** Тип */
    public int getType() { return type ; }

    /** Сообщение */
    public String getMessage() { return message ; }

    /** Сообщение */
    private final String message ;
    /** Тип */
    private final int type ;
}
