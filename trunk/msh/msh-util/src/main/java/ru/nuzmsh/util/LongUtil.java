package ru.nuzmsh.util;

/**
 * @author esinev
 * Date: 25.04.2006
 * Time: 13:48:52
 */
public class LongUtil {

    /**
     * Возвращает TRUE если aObject==null || aObject==0
     *
     * @param aObject
     */
    public static boolean isNullOrZero(Object aObject) {
        if(aObject==null) {
            return true ;
        } else if (aObject instanceof Long) {
            return isNullOrZero((Long) aObject);
        } else {
            return isNullOrZero(aObject.toString());
        }
    }

    public static boolean isNullOrZero(String aString) {
        if (aString == null) {
            return true;
        } else {
            return isNullOrZero(Long.parseLong(aString));
        }
    }

    public static boolean isNullOrZero(Long aLong) {
        return aLong == null || aLong == 0;
    }

}
