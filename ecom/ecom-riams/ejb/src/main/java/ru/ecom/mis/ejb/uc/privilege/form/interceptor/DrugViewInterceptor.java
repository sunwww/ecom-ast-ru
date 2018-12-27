package ru.ecom.mis.ejb.uc.privilege.form.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.uc.privilege.form.voc.VocDrugClassifyForm;

import javax.persistence.EntityManager;

public class DrugViewInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	VocDrugClassifyForm frm = (VocDrugClassifyForm) aForm ;
    	EntityManager manager = aContext.getEntityManager() ;
    	frm.setDrugClassificatorPositions(ListPersist.getArrayJson("DrugClassificatorPosition", "drug_id", frm.getId(), "drugClassificator_id", manager)) ;
	}

}
