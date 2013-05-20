package ru.ecom.mis.ejb.form.expert.interceptor;

import java.util.List;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.form.expert.ClinicExpertCardForm;

public class ClinicExpertCardPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	
    	ClinicExpertCardForm form = (ClinicExpertCardForm)aForm ;
    	List<Object> list =aContext.getEntityManager().createNativeQuery("select patient_id from medcase where id=:parent").setParameter("parent", aParentId).getResultList() ;
    	if (list.size()>0) {
    		Object row = list.get(0) ;
    		form.setPatient(ConvertSql.parseLong(row)) ;
    	}
    }

}