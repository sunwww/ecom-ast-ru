package ru.nuzmsh.util;

/**
 * Работа со строками
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String aStr) {
        return aStr==null || aStr.trim().equals("") ;
    }
}
