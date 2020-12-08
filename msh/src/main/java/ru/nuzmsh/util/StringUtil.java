package ru.nuzmsh.util;

/**
 * Работа со строками
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String aStr) {
        return aStr == null || aStr.trim().equals("");
    }

    public static boolean isNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }
}
