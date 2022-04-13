package ru.ecom.ejb.services.entityform;

/**
 * Работа с формами
 */
public interface IFormService {

    /**
     * Загружает форму
     * @param aId идентификатор
     * @return форма
     */
    <T extends IEntityForm> T load(Class<T> aFormClass, Object aId) throws EntityFormException ;
    IEntityForm load(String aFormClassName, Object aId) throws EntityFormException ;


    /**
     * Загружает форму подкласса
     * @param aId идентификатор
     * @return форма
     */
    <T extends IEntityForm> T loadBySubclass(Class<T> aFormClass, Object aId) throws EntityFormException ;

    /**
     * Создание нового
     * @param aForm форма
     */
    long create(IEntityForm aForm) throws EntityFormException;

    /**
     * Сохранение
     * @param aForm форма
     */
    void save(IEntityForm aForm) throws EntityFormException;
    /**
     * Удалить объект
     * @param aId идентификатор
     */
    void delete(Class aFormClass, Object aId) ;
    void delete(String aFormClassName, Object aId) ;

}
