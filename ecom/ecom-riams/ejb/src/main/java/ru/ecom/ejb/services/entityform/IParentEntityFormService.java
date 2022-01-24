package ru.ecom.ejb.services.entityform;

import java.util.Collection;

/**
 * Работа с EntityForm связанным с родителем
 */
public interface IParentEntityFormService extends IFormService {

    /**
     * Подготовка к созданию формы
     *
     * @param aParentId Идентификатор родителя
     * @return форма
     */
    <T extends IEntityForm> T prepareToCreate(Class<T> aFormClass, Object aParentId) throws EntityFormException;

    IEntityForm prepareToCreate(String aFormClassName, Object aParentId) throws EntityFormException;

    /**
     * Список всех объектов
     *
     * @param type
     * @param aParentId Идентификатор родителя
     */
    <E extends IEntityForm> Collection<E> listAll(Class<E> type, Object aParentId) throws EntityFormException;

    Collection<IEntityForm> listAll(String aClassName, Object aParentId) throws EntityFormException;

}
