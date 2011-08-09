package ru.ecom.alg.omc;

import ru.nuzmsh.util.StringUtil;

/**
 * Работа с услугами
 */
public class OmcUslUtil {

    public static void checkUsl(String aUsl) {
        if(StringUtil.isNullOrEmpty(aUsl)) throw new IllegalArgumentException("Нет кода услуги") ;
        if(aUsl.length()!=6) throw new IllegalArgumentException("Длина кода услуги должна быть 6 символов. Код услуги: '"+aUsl+"'") ;
    }

    public static boolean isDailyHospital(String aUsluga) {
        checkUsl(aUsluga);
        boolean ret = false;
        if (aUsluga != null && aUsluga.length() > 3) {
            ret = aUsluga.toUpperCase().charAt(3) == 'J';
        }
        return ret;
    }

    public static boolean isProvisional(String aUsluga) {
        checkUsl(aUsluga);
        boolean ret = false;
        if (aUsluga != null && aUsluga.length() > 2) {
            ret = aUsluga.charAt(3) == 'A';
        }
        return ret;

    }

    public static boolean isPolyclinic(String aUsluga) {
        checkUsl(aUsluga);
        boolean ret;
        String letters = "CDLKEF";
        if (aUsluga != null) {
            if (aUsluga.length() > 3) {
                char code = aUsluga.charAt(3);
                ret = false;
                for (int i = 0; i < letters.length(); i++) {
                    char ch = letters.charAt(i);
                    if (ch == code) {
                        ret = true;
                        break;
                    }
                }
            } else {
                ret = false;
            }
        } else {
            ret = false;
        }
        return ret;
    }

}
