package ru.ecom.expomc.ejb.uc.filltime.form.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.expomc.ejb.uc.filltime.domain.FillTimeProperty;
import ru.ecom.expomc.ejb.uc.filltime.form.FillTimePropertyForm;


public class FillTimePropertyViewInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
		FillTimeProperty prop = (FillTimeProperty) aEntity ;
		FillTimePropertyForm form = (FillTimePropertyForm) aForm ;
		if(prop!=null && prop.getFillTime()!=null && prop.getFillTime().getOutputDocument()!=null) {
			form.setDocumentId(prop.getFillTime().getOutputDocument().getId()) ;
		}
	}

}
