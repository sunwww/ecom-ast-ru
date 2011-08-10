package ru.nuzmsh.commons.formpersistence;

import java.util.Collection;

/**
 */
public interface IEntityFormManager {

    /**
     * Сохраняет или добавляет объект
     * @param aObject
     */
    void create(Object aObject, IEntityFormManagerContext aContext) ;

    /**
     * Сохраняет или добавляет объект
     * @param aObject
     */
    void store(Object aObject, IEntityFormManagerContext aContext) ;

    /**
     * Удаляет объект
     * @param aObject
     */
    void remove(Object aObject, IEntityFormManagerContext aContext) ;

    /**
     * Загружает по идентификатору @Id
     * @param aObject
     * @param aContext
     */
    void load(Object aObject, IEntityFormManagerContext aContext) ;

    /**
     * Список всех значений
     */
    Collection listAll(Class aClass, IEntityFormManagerContext aContext) ;
}
