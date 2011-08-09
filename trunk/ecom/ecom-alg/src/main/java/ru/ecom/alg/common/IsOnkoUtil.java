package ru.ecom.alg.common;

/**
 * Онкопатология
 */
public class IsOnkoUtil {

    public static boolean isOnko(String aMkb) {
        return aMkb!=null && aMkb.length()>1 && ( aMkb.charAt(0)=='C' || aMkb.charAt(0)=='c') ;
    }
}
