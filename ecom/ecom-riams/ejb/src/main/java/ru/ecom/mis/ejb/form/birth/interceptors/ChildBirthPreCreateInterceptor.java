package ru.ecom.mis.ejb.form.birth.interceptors;


import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.birth.ChildBirthForm;

import java.util.List;

public class ChildBirthPreCreateInterceptor implements IParentFormInterceptor {
	
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	if (aForm instanceof ChildBirthForm) {
	    	ChildBirthForm form = (ChildBirthForm) aForm ; 
	    	List<Object[]> list=aContext.getEntityManager().createNativeQuery("select slo.id,ml.id from medcase slo left join mislpu ml on ml.id=slo.department_id where  slo.id='"+form.getMedCase()+"' and ml.isMaternityWard='1'").getResultList() ;
	    	if (list.isEmpty()) throw new IllegalStateException("Описание родов может быть заведена только в родильном отделении!!!") ;
	    	list=aContext.getEntityManager().createNativeQuery("select cb.id from medcase slo "+
					" left join medcase allSlo on allSlo.parent_id=slo.parent_id " +
					" left join childbirth cb on cb.medcase_id in (allSlo.id)" +
					" where slo.id=" + form.getMedCase() +" and cb.id is not null").getResultList() ;
	    	if (!list.isEmpty()) throw new IllegalStateException("В госпитализации уже оформлен случай родов!!") ;
    	}
    }
}