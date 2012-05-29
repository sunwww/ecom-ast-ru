package ru.ecom.mis.ejb.form.medcase.interceptor;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.poly.DirectionMedCaseForm;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.util.format.DateFormat;

public class DirectionPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DirectionMedCaseForm form = (DirectionMedCaseForm)aForm ;
    	
	    	/*if (parent.getPatient()!=null) {
	    		form.setPatient(parent.getPatient().getId()) ;
	    	}*/
    	//Date date = new Date() ;
		List<Object[]> polerr = manager.createNativeQuery("SELECT id " 
		                              +" FROM MedPolicy where patient_id='"+aParentId+"' "
		                              +" and CURRENT_DATE >= actualDateFrom and (actualDateTo is null or actualDateTo>=CURRENT_DATE) "
		                              +"  and DTYPE like 'MedPolicyOmc%'")
			//.setParameter("patid", form.getId())
			.getResultList();
		//form.setNotice(form.getNotice()+form.getId() +polerr[0]+"----"+polerr.length+polerr.toString()) ;
		if(polerr.size()==0) form.setInfoByPolicy("НЕТ АКТУАЛЬНОГО ПОЛИСА ОМС");
		if(polerr.size()>1) form.setInfoByPolicy("БОЛЬШЕ ОДНОГО АКТУАЛЬНОГО ПОЛИСА ОМС");

	// 
	
    }
}