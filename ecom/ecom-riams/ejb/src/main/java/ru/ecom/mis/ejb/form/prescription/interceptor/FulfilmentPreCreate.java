package ru.ecom.mis.ejb.form.prescription.interceptor;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.prescription.PrescriptionFulfilmentForm;
import ru.nuzmsh.util.format.DateFormat;

public class FulfilmentPreCreate implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	PrescriptionFulfilmentForm form = (PrescriptionFulfilmentForm)aForm ;
    	String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    	form.setUsername(username);
    	Date dateThis =new Date() ;
    	form.setDateCreate(DateFormat.formatToDate(dateThis)) ;
    	
    	List<WorkFunction> listwf =  manager.createQuery("from WorkFunction where secUser.login = :login")
			.setParameter("login", username).getResultList() ;
    	if (listwf.size()==0) {
    		throw new IllegalArgumentException(
    				"Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)"
    				);
    	}
    	WorkFunction wf = listwf.get(0) ;
    	form.setExecutorWorkFunction(wf.getId()) ;
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/Prescription/PrescriptionFulfilment/EditWorker")) {
            form.addDisabledField("executorWorkFunction");
        }
    	
    }

}
