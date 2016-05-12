package ru.ecom.expomc.ejb.uc.filltime.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.expomc.ejb.uc.filltime.domain.FillTime;
import ru.ecom.expomc.ejb.uc.filltime.form.FillTimePropertyForm;


public class FillTimePropertyCreateInterceptor implements IParentFormInterceptor {


	
	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		FillTimePropertyForm form = (FillTimePropertyForm) aForm ;
		FillTime ft = aContext.getEntityManager().find(FillTime.class
					, aParentId) ;
		if(ft!=null && ft.getOutputDocument()!=null) {
			form.setDocumentId(ft.getOutputDocument().getId()) ;
		}
	}
}
