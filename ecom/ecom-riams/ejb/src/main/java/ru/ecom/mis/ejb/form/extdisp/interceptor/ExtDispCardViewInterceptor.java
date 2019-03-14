package ru.ecom.mis.ejb.form.extdisp.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.extdisp.ExtDispCardForm;

import javax.persistence.EntityManager;
import java.util.List;

public class ExtDispCardViewInterceptor implements IFormInterceptor {
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {

		ExtDispCardForm form = (ExtDispCardForm)aForm ;
		EntityManager manager = aContext.getEntityManager() ;
		//ExtDispCard card = (ExtDispCard)aEntity ;
	
		long id = form.getId() ;
		List<Object> list = manager.createNativeQuery(" select list(''||dispRisk_id) from ExtDispRisk where card_id='" + id + "' group by card_id").getResultList();
		if (!list.isEmpty()) {
			form.setRisks(String.valueOf(list.get(0))) ;
		}

		
	
	}
}