package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.form.medcase.ServiceMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ServiceMedCasePreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	ServiceMedCaseForm form = (ServiceMedCaseForm)aForm ;
    	String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    	form.setUsername(username);
    	Date date = new Date();
    	 /*if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/MedService/CreateOnlyInMedService")) {
    		 MedCase parent = manager.find(MedCase.class, aParentId) ;
    		 if (parent instanceof ServiceMedCase) {
    			 
    		 } else {
    			 throw new IllegalArgumentException(
    	    				"У Вас стоит ограничение!! Создать протоколы (дневники) можно только в созданной услуге!!!"
    	    				);
    		 }
    	 }
    	/*
    	MedCase parent = manager.find(MedCase.class, aParentId) ;
    	if (parent!=null) {
    		if (parent.getPatient()!=null) form.setPatient(parent.getPatient().getId()) ;
    		if (parent.getServiceStream()!=null) form.setServiceStream(parent.getServiceStream().getId()) ;
    	}
    	*/
    	List<Object[]> list =aContext.getEntityManager().createNativeQuery("select patient_id,serviceStream_id from medcase where id=:parent").setParameter("parent", aParentId).getResultList() ;
    	if (!list.isEmpty()) {
    		Object[] row = list.get(0) ;
    		form.setPatient(ConvertSql.parseLong(row[0])) ;
    		form.setServiceStream(ConvertSql.parseLong(row[1])) ;
    	}
    	form.setCreateDate(DateFormat.formatToDate(date));
        form.setDateStart(DateFormat.formatToDate(date));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setTimeExecute(timeFormat.format(date));
        /*
    	List<WorkFunction> listwf =  manager.createQuery("from WorkFunction where secUser.login = :login")
			.setParameter("login", username).getResultList() ;
    	if (listwf.size()==0) {
    		throw new IllegalArgumentException(
    				"Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)"
    				);
    	}
    	WorkFunction wf = listwf.get(0) ;
    	form.setWorkFunctionExecute(wf.getId()) ;
    	*/
        List<Object[]> listwf = manager.createNativeQuery("select wf.id,secUser.id from WorkFunction as wf  left join SecUser as secUser on secUser.id=wf.secUser_id  where secUser.login = :login")
        	.setParameter("login", username)
        	.getResultList() ;
        if (listwf.isEmpty()) {
    		throw new IllegalArgumentException(
    				"Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)"
    				);
        }
        Object[] row = listwf.get(0) ;
        form.setWorkFunctionExecute(ConvertSql.parseLong(row[0])) ;
    	
    }

}