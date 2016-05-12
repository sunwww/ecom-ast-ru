package ru.ecom.mis.ejb.form.disability.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;

/**
 * Ограничение создания нарушения режима перед созданием формы 
 * @author stkacheva
 *
 */
public class RegimeViolationRecordPreCreate implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	//RegimeViolationRecordForm form = (RegimeViolationRecordForm) aForm ;
    	DisabilityDocument doc = manager.find(DisabilityDocument.class, aParentId) ;
    	
    	if (doc!=null) {
    		if (doc.getIsClose()!=null && doc.getIsClose()==true) {
    			throw new IllegalStateException("Нельзя добавить нарушение режима в закрытый документ!!!") ;
    		}
    	} else {
    		throw new IllegalStateException("Невозможно добавить нарушение режима. Сначала надо определить документ нетрудоспособности!!!") ;
    	}
    }
}
