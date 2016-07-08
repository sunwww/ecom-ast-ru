package ru.ecom.mis.ejb.form.birth.interceptors;


import java.util.List;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.birth.ChildBirthForm;

public class ChildBirthPreCreateInterceptor implements IParentFormInterceptor {
	
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	if (aForm instanceof ChildBirthForm) {
	    	ChildBirthForm form = (ChildBirthForm) aForm ; 
	    	List<Object[]> list=aContext.getEntityManager().createNativeQuery("select slo.id,ml.id from medcase slo left join mislpu ml on ml.id=slo.department_id where  slo.id='"+form.getMedCase()+"' and ml.isMaternityWard='1'").getResultList() ;
	    	if (list.size()==0) throw new IllegalStateException("Описание родов может быть заведена только в родильном отделении!!!") ;
	    	list=aContext.getEntityManager().createNativeQuery("select cb.id from childbirth cb where  cb.medcase_id='"+form.getMedCase()+"'").getResultList() ;
	    	if (list.size()>0) throw new IllegalStateException("В отделении уже оформлен случай родов!!") ;
    	}
    }
}