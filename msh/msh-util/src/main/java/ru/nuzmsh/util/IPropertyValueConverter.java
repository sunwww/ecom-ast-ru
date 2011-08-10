package ru.nuzmsh.util;

/**
 * Преобразование типов
 */
public interface IPropertyValueConverter {
    Object convertValue(Class aInClass, Class aOutClass, Object aValue) ;
}
