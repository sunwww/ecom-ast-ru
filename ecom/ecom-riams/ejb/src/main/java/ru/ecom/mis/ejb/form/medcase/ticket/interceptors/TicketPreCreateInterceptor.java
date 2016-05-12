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
    	List<Object[]> list =aContext.getEntityManager().createNativeQuery("select m.person_id,pat.categoryChild_id from medcard m left join patient pat on pat.id=m.person_id where m.id=:parent")
    			.setParameter("parent", aParentId).getResultList() ;
    	if (list.size()>0) {
    		Object[] row = list.get(0) ;
    		if (row[0]!=null) form.setPatient(ConvertSql.parseLong(row[0])) ;
    		if (row[1]!=null)form.setCategoryChild(ConvertSql.parseLong(row[1])) ;
    	}
    }

}