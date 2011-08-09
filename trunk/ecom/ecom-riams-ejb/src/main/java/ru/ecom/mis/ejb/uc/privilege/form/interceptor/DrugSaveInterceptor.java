package ru.ecom.mis.ejb.uc.privilege.form.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.uc.privilege.domain.VocDrugClassify;
import ru.ecom.mis.ejb.uc.privilege.form.voc.VocDrugClassifyForm;

public class DrugSaveInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		VocDrugClassify classif = (VocDrugClassify)aEntity ;
		VocDrugClassifyForm frm = (VocDrugClassifyForm)aForm ;
		//EntityManager manager = aContext.getEntityManager() ;
		Long id = classif.getId() ;
		ListPersist.saveArrayJson("DrugClassificatorPosition", id, frm.getDrugClassificatorPositions(),"drug_id","drugClassificator_id", aContext.getEntityManager()) ;
		
	}

}
