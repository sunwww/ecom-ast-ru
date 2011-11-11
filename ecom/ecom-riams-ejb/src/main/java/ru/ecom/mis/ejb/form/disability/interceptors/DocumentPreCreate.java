package ru.ecom.mis.ejb.form.disability.interceptors;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.disability.DisabilityCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm;

public class DocumentPreCreate implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DisabilityDocumentForm form = (DisabilityDocumentForm) aForm ;
    	DisabilityCase dcase = manager.find(DisabilityCase.class, aParentId) ;
    	Patient pat = dcase!=null ?dcase.getPatient():null ;
    	if (pat!=null) {
    		VocOrg vo = pat.getWorks() ;
    		String org = DocumentByPatientPreCreate.getShortNameByOrg(vo) ;
    		form.setJob(org) ;
    	} else {
    		throw new IllegalStateException("Невозможно добавить случай. Сначала надо определить пациента") ;
    	}
    }
}