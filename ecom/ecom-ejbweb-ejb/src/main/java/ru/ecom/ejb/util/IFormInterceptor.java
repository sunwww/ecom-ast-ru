package ru.ecom.ejb.util;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;

/**
 * Для форм без родителей
 */
public interface IFormInterceptor {

	/**
     * Вызывается при определенных условиях. В зависимости от аннотации
     * @param aForm      форма
     * @param aEntity    сущность
     * @param aContext   дополнительные сервисы (например, EntityManager)
     */
	void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) ;

}
