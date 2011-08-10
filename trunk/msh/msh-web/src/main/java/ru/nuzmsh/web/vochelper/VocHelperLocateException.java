package ru.nuzmsh.web.vochelper;

/**
 * @author esinev
 * Date: 09.03.2006
 * Time: 10:26:57
 */
public class VocHelperLocateException extends Exception {
    public VocHelperLocateException(Throwable cause) {
        super("Ошибка при поиске IVocHelper: "+cause,cause);
    }
}
