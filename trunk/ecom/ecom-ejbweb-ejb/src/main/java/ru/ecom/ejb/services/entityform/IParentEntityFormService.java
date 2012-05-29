package ru.ecom.ejb.services.entityform;

import java.util.Collection;

/**
 *  Работа с EntityForm связанным с родителем
 */
public interface IParentEntityFormService extends IFormService {

    /**
     * Подготовка к созданию формы
     * @return форма
     * @param aParentId Идентификатор родителя
     */
	public <T extends IEntityForm> T prepareToCreate(Class<T> aFormClass, Object aParentId) throws EntityFormException ;
    public IEntityForm prepareToCreate(String aFormClassName, Object aParentId) throws EntityFormException ;

    /**
     * Список всех объектов
     * @param type
     * @param aParentId Идентификатор родителя
     */
    public <E extends IEntityForm> Collection<E> listAll(Class<E> type, Object aParentId) throws EntityFormException ;
    public  Collection<IEntityForm> listAll(String aClassName, Object aParentId) throws EntityFormException ;
    public void saveView(Long aId, String aUsername,String aComment, String aFormName,Integer aLevelWebTrail) ;
    	
}
