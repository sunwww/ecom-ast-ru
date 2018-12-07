package ru.ecom.ejb.services.entityform.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;

/**
 * Для форм с родителем
 */
public interface IParentFormInterceptor {
    /**
     * Вызывается при определенных условиях. В зависимости от аннотации
     * @param aForm      форма
     * @param aEntity    сущность
     * @param aParentId  идентификатор родителя
     * @param aContext   дополнительные сервисы (например, EntityManager)
     */
     void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) ;
}
