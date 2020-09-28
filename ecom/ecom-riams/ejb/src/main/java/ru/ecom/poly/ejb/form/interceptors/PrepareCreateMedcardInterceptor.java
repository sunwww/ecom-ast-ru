package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class PrepareCreateMedcardInterceptor implements IParentFormInterceptor, IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		MedcardForm form = (MedcardForm) aForm ;
		String sql = "select cardNumber from PsychiatricCareCard where patient_id="+aParentId+" order by id desc" ;
		List<Object> list = aContext.getEntityManager().createNativeQuery(sql)
				.setMaxResults(1).getResultList() ;
		if (!list.isEmpty() && list.get(0)!=null) {
			form.setNumber(""+list.get(0)) ;
			
		} else {
			String next = EjbInjection.getInstance().getLocalService(ISequenceService.class).startUseNextValue("Medcard","number");
			form.setNumber(next);
			
		}
		Date dateThis =new Date() ;
		form.setDateRegistration(DateFormat.formatToDate(dateThis)) ;
		form.setRegistrator(aContext.getSessionContext().getCallerPrincipal().toString());
		form.addDisabledField("dateRegistration") ;
	}

	public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
	//	intercept(aForm, aEntity, null, null); //NPE
		
	}

}
