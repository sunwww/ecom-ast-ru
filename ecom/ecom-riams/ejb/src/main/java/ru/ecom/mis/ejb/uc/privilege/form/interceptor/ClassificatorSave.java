package ru.ecom.mis.ejb.uc.privilege.form.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.uc.privilege.domain.DrugClassificator;
import ru.ecom.mis.ejb.uc.privilege.form.DrugClassificatorForm;

public class ClassificatorSave implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DrugClassificator classif = (DrugClassificator)aEntity ;
		DrugClassificatorForm frm = (DrugClassificatorForm)aForm ;
		//EntityManager manager = aContext.getEntityManager() ;
		Long id = classif.getId() ;
		ListPersist.saveArrayJson("DrugClassificatorPosition", id, frm.getDrugList(),"drugClassificator_id","drug_id", aContext.getEntityManager()) ;
		
		
	}

}
