package ru.nuzmsh.commons.tree;

import java.util.Collection;
import java.rmi.RemoteException;

/**
 * Доступ к данным в виде дерева
 */
public interface ITreeModel {

    /**
     * Список потомков, начиная с aFromId
     *
     * @param aParentId ид родителя
     * @param aFromId   с какого начинать
     * @param aCount    количество выводимых элементов
     * @return потомки
     */
    Collection listForward(Object aParentId, Object aFromId, int aCount) throws Exception ;

    /**
     * Поиск вперед, начиная  с ИД
     *
     * @param aParentId  ид родителя
     * @param aFromId    с какого начинать
     * @param aSearchString строка поиска
     * @param aCount     количество перебираемых элементов, если -1, то все элементы
     * @return результат поиска
     *
     * @throws FinderException
     * @throws RemoteException
     */
    ISearchResult searchForward(Object aParentId, Object aFromId, String aSearchString, int aCount) throws Exception ;

    /**
     * Поиск назад, начиная  с ИД
     *
     * @param aParentId  ид родителя
     * @param aFromId    с какого начинать
     * @param aSearchString строка поиска
     * @param aCount     количество перебираемых элементов, если -1, то все элементы
     * @return результат поиска
     *
     * @throws FinderException
     * @throws RemoteException
     */
    ISearchResult searchBackward(Object aParentId, Object aFromId, String aSearchString, int aCount) throws Exception ;

    /**
     * Список потомков в обратном порядке, начиная с aFromId
     *
     * @param aParentId ид родителя
     * @param aFromId   с какого начинать
     * @param aCount    количество выводимых элементьв
     * @return потомки
     */
    Collection listBackward(Object aParentId, Object aFromId, int aCount) throws Exception ;

    /**
     * Получение идентификатора родителя
     * @param aId идентификатор текущего узла
     * @return идентификатор родителя, null - если нет родителя
     */
    Object getParentId(Object aId) throws Exception ;

    /**
     * Получение объекта по идентификатору
     * @param aId идентификатор
     * @return объект
     * @throws FinderException
     * @throws RemoteException
     */
    Object get(Object aId) throws Exception ;
}
