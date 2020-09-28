package ru.ecom.mis.ejb.form.psychiatry.interceptor;

import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.form.psychiatry.PsychiatricCareCardForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class CareCardPreCreateInterceptor implements IParentFormInterceptor, IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		PsychiatricCareCardForm form = (PsychiatricCareCardForm) aForm ;
		String sql = "select number from Medcard where person_id="+aParentId+" order by id desc" ;
		List<Object> list = aContext.getEntityManager().createNativeQuery(sql)
				.setMaxResults(1).getResultList() ;
		if (!list.isEmpty() && list.get(0)!=null) {
			form.setCardNumber(""+list.get(0)) ;
			
		} else {
			String next = EjbInjection.getInstance().getLocalService(ISequenceService.class).startUseNextValue("Medcard","number");
			form.setCardNumber(next);
			
		}
		Date dateThis =new Date() ;
		form.setDateRegistration(DateFormat.formatToDate(dateThis)) ;
		form.setRegistrator(aContext.getSessionContext().getCallerPrincipal().toString());
	}

	public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
		//intercept(aForm, aEntity, null, null); //Ибо будет NPE!
		
	}
}