package ru.ecom.mis.ejb.uc.privilege.form.interceptor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.uc.privilege.form.voc.VocDrugClassifyForm;

public class DrugViewInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(ClassificatorView.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	VocDrugClassifyForm frm = (VocDrugClassifyForm) aForm ;
    	EntityManager manager = aContext.getEntityManager() ;
    	frm.setDrugClassificatorPositions(ListPersist.getArrayJson("DrugClassificatorPosition", "drug_id", frm.getId(), "drugClassificator_id", manager)) ;
	}

}
