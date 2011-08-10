package ru.nuzmsh.logicpool.api;

/**
 * Конвертирует PrimaryKey из java.lang.String
 *
 * @author ESinev
 */
public interface IPkConverter {

    /**
     * Конвертирует из String в Pk объект
     * @param aStr pk в виде строки
     * @return pk объект
     */
    Object convert(String aStr) ;
}
