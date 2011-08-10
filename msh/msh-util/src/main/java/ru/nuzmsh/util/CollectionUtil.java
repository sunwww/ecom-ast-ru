package ru.nuzmsh.util;

import java.util.Collection;

/**
 * Утилиты для работы с коллекциями
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection aCollection) {
        return aCollection != null && aCollection.isEmpty() ;
    }
}
