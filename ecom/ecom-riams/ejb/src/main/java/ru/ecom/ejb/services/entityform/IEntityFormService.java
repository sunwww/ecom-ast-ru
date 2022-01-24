package ru.ecom.ejb.services.entityform;

import java.util.Collection;

/**
 *  Работа с EntityForm
 */
public  interface IEntityFormService extends IFormService {

    /**
     * Подготовка к созданию формы
     * @return форма
     */
    <T extends IEntityForm> T prepareToCreate(Class<T> aFormClass) throws EntityFormException ;
    IEntityForm prepareToCreate(String aFormClassName) throws EntityFormException ;

    /**
     * Список всех объектов
     * @param type
     */
    <E extends IEntityForm> Collection<E> listAll(Class<E> type) throws EntityFormException;
    Collection<IEntityForm> listAll(String aClassname) throws EntityFormException;
    
}
