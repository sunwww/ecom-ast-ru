package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.medcase.VisitProtocolForm;

import java.util.List;

public class ProtocolPreCreateInterceptor implements IParentFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/CreateOnlyInMedService")) {
   		 MedCase parent = aContext.getEntityManager().find(MedCase.class, aParentId) ;
   		 if (parent instanceof ServiceMedCase) {
   			 
   		 } else {
   			 throw new IllegalArgumentException(
   	    				"У Вас стоит ограничение!! Создать заключения можно только в созданной услуге!!!"
   	    				);
   		 }
   	 }
		String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    	VisitProtocolForm form = (VisitProtocolForm )aForm;
    	List<WorkFunction> listwf =  aContext.getEntityManager().createQuery("from WorkFunction where secUser.login = :login")
		.setParameter("login", username).getResultList() ;
		if (listwf.size()==0) {
			
		} else {
			form.setSpecialist(listwf.get(0).getId()) ;
		}
		//MedcardForm form = (MedcardForm) aForm ;
		//String next = EjbInjection.getInstance().getLocalService(ISequenceService.class).startUseNextValue("medcard");
		//form.setNumber(next);
		//Date dateThis =new Date() ;
		//form.setDateRegistration(DateFormat.formatToDate(dateThis)) ;
		//form.setRegistrator(aContext.getSessionContext().getCallerPrincipal().toString());
		//form.addDisabledField("dateRegistration") ;
	}

}
