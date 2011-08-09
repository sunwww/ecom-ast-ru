package ru.ecom.report.replace;

/**
 * Получение значений
 */
public interface IValueGetter {

    Object getValue(String aExpression) throws SetValueException ;
    void set(String aKey, Object aValue) throws SetValueException ;
}
