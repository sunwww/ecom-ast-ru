package ru.ecom.mis.ejb.form.birth.interceptors;


import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.birth.MisbirthForm;

import java.util.List;

public class MisbirthPreCreateInterceptor implements IParentFormInterceptor {
	
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	if (aForm instanceof MisbirthForm) {
			MisbirthForm form = (MisbirthForm) aForm ;
	    	List<Object[]> list=aContext.getEntityManager().createNativeQuery("select slo.id,ml.id from medcase slo left join mislpu ml on ml.id=slo.department_id where  slo.id='"+form.getMedCase()+"' and ml.isMaternityWard='1'").getResultList() ;
	    	if (list.size()==0) throw new IllegalStateException("Описание выкидыша может быть заведено только в родильном отделении!!!") ;
    	}
    }
}