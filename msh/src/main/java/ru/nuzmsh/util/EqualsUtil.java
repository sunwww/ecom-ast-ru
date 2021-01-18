package ru.nuzmsh.util;

public class EqualsUtil {

    /**
     * Проверка на вхождение элементов
     * @param item Искомый элемент
     * @param objects Список элементов
     * @return Истина, если элемент содержится в списке
     */
    public static boolean isOneOf(Object item, Object... objects) {
        if (item == null) {
            return false;
        }

        for (Object object : objects) {
            if (item.equals(object)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isEquals(Object source, Object target) {
        return source !=null && source.equals(target);
    }

    /**
     * Вернет true, если любой из элементов - нулл
     * @param objects Объекты
     * @return есть ли нулл среди объектов
     */
    public static boolean isAnyIsNull(Object ... objects) {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }
}
