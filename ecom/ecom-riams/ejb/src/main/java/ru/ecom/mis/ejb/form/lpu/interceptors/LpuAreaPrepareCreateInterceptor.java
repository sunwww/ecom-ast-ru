package ru.ecom.mis.ejb.form.lpu.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;

/**
 * Проверка: Если есть подразделения, то нельзя добавить участок
 */
public class LpuAreaPrepareCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        EntityManager manager = aContext.getEntityManager();

        MisLpu parentLpu = manager.find(MisLpu.class, aParentId) ;
        if(parentLpu!=null) {
            if(parentLpu.getSubdivisions()!=null && !parentLpu.getSubdivisions().isEmpty() ) {
                throw new IllegalStateException("У ЛПУ есть подразделения. Добавить участок нельзя.") ;
            }
        }
    }
}
