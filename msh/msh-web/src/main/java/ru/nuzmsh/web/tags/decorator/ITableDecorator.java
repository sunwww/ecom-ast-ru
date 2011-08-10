package ru.nuzmsh.web.tags.decorator;

/**
 * Оформление таблицы
 */
public interface ITableDecorator {
    /**
     * Имя класса &lt;tr class='NAME'&gt;
     * @param aRow строка в таблице
     * @return имя CSS класса
     */
    String getRowCssClass(Object aRow) ;

    /**
     * Идентификатор для события нажатия на строку
     * @return уникальный идентификатор
     */
    String getId(Object aRow) ;
}
