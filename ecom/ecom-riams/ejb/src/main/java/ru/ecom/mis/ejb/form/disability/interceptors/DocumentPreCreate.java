package ru.ecom.mis.ejb.form.disability.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.disability.DisabilityCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm;

import javax.persistence.EntityManager;

public class DocumentPreCreate implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DisabilityDocumentForm form = (DisabilityDocumentForm) aForm ;
    	DisabilityCase dcase = manager.find(DisabilityCase.class, aParentId) ;
    	Patient pat = dcase!=null ?dcase.getPatient():null ;
    	if (pat!=null) {
    		//String org = DocumentByPatientPreCreate.getShortNameByOrg() ;
    		form.setJob(pat.getWorks()) ;
    	} else {
    		throw new IllegalStateException("Невозможно добавить случай. Сначала надо определить пациента") ;
    	}
    }
}