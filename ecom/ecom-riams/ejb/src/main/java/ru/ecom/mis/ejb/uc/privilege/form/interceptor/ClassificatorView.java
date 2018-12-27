package ru.ecom.mis.ejb.uc.privilege.form.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.uc.privilege.form.DrugClassificatorForm;

import javax.persistence.EntityManager;

public class ClassificatorView  implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	DrugClassificatorForm frm = (DrugClassificatorForm) aForm ;
    	EntityManager manager = aContext.getEntityManager() ;
    	frm.setDrugList(ListPersist.getArrayJson("DrugClassificatorPosition", "drugClassificator_id", frm.getId(), "drug_id", manager)) ;
	}

}
