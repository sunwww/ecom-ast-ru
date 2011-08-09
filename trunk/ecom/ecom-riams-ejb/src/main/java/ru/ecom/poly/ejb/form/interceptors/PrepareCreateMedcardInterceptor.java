package ru.ecom.poly.ejb.form.interceptors;

import java.util.Date;

import javax.persistence.EntityManager;

import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.nuzmsh.util.format.DateFormat;

public class PrepareCreateMedcardInterceptor implements IParentFormInterceptor, IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		MedcardForm form = (MedcardForm) aForm ;
		String next = EjbInjection.getInstance().getLocalService(ISequenceService.class).startUseNextValue("medcard");
		form.setNumber(next);
		Date dateThis =new Date() ;
		form.setDateRegistration(DateFormat.formatToDate(dateThis)) ;
		form.setRegistrator(aContext.getSessionContext().getCallerPrincipal().toString());
		form.addDisabledField("dateRegistration") ;
	}

	public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
		intercept(aForm, aEntity, null, null);
		
	}

}
