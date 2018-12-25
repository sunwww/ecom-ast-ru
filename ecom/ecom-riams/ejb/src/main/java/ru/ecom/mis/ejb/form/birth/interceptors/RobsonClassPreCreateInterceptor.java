package ru.ecom.mis.ejb.form.birth.interceptors;


import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.birth.RobsonClassForm;

import java.util.List;

public class RobsonClassPreCreateInterceptor implements IParentFormInterceptor {
	
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	if (aForm instanceof RobsonClassForm) {
			RobsonClassForm form = (RobsonClassForm) aForm ;
	    	List<Object[]> list=aContext.getEntityManager().createNativeQuery("select slo.id,ml.id from medcase slo left join mislpu ml on ml.id=slo.department_id where  slo.id='"+form.getMedCase()+"' and ml.isMaternityWard='1'").getResultList() ;
	    	if (list.size()==0) throw new IllegalStateException("Классификация Робсона может быть заведена только в родильном отделении!!!") ;
    	}
    }
}