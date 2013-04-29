package ru.ecom.mis.ejb.form.medcase.ticket.interceptors;

import java.util.List;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.form.medcase.ticket.ShortTicketMedCaseForm;

public class TicketPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	
    	ShortTicketMedCaseForm form = (ShortTicketMedCaseForm)aForm ;
    	List<Object> list =aContext.getEntityManager().createNativeQuery("select person_id from medcard where id=:parent").setParameter("parent", aParentId).getResultList() ;
    	if (list.size()>0) {
    		Object row = list.get(0) ;
    		form.setPatient(ConvertSql.parseLong(row)) ;
    	}
    }

}