package ru.nuzmsh.commons.tree;

import java.io.Serializable;

/**
 * Результат поиска
 */
public interface ISearchResult extends Serializable {

    /**
     * Завершен ли поиск
     *
     * @return true если поиск завершен
     */
    boolean isSearchComplete() ;

    /**
     * Идентификатор
     *
     * @return идентификатор, null - если не найдено
     */
    Object  getId() ;
}
