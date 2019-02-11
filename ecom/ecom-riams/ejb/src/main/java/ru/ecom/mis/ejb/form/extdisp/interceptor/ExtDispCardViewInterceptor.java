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
		List<Object> list = manager.createNativeQuery(new StringBuilder().append(" select list(''||dispRisk_id) from ExtDispRisk where card_id='").append(id).append("' group by card_id").toString()).getResultList();
		if (list.size()>0) {
			form.setRisks( new StringBuilder().append(list.get(0)).toString()
					) ;
		}

		
	
	}
}