package ru.ecom.mis.ejb.uc.privilege.form.interceptor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.uc.privilege.form.DrugClassificatorForm;

public class ClassificatorView  implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(ClassificatorView.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	DrugClassificatorForm frm = (DrugClassificatorForm) aForm ;
    	EntityManager manager = aContext.getEntityManager() ;
    	frm.setDrugList(ListPersist.getArrayJson("DrugClassificatorPosition", "drugClassificator_id", frm.getId(), "drug_id", manager)) ;
	}

}
