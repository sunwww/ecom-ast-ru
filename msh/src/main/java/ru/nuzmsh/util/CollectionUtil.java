package ru.nuzmsh.util;

import java.util.Collection;

/**
 * Утилиты для работы с коллекциями
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static <T> T getFirst(Collection<T> collection) {
        return isEmpty(collection) ? null : collection.iterator().next();
    }


}
