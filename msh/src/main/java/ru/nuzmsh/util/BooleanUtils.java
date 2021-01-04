package ru.nuzmsh.util;


public class BooleanUtils {

    public static boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }

    public static boolean isNotTrue(Boolean value) {
        return !isTrue(value);
    }

    public static boolean isFalse(Boolean value) {
        return Boolean.FALSE.equals(value);
    }

    public static boolean isNotFalse(Boolean value) {
        return !isFalse(value);
    }
}
