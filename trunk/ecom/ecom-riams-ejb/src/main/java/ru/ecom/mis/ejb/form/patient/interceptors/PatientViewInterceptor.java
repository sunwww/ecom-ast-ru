package ru.ecom.mis.ejb.form.patient.interceptors;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.forms.response.FormMessage;

public class PatientViewInterceptor implements IFormInterceptor {

	private final static Logger LOG = Logger
			.getLogger(PatientViewInterceptor.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
		if (CAN_DEBUG)
			LOG.debug("intercept: aForm = " + aForm); 

		Patient pat = (Patient) aEntity;
		PatientForm form = (PatientForm) aForm ;
		if(pat!=null && pat.getAttachedOmcPolicy()!=null) {
			form.setAttachedByPolicy(true);
		}
		/*
		try {
			//Date date = new Date() ;
			Object[] polerr = (Object[]) aManager
			.createNativeQuery("SELECT TOP 1 " 
			                              +"$$CheckPatientOMC^ZMedPolicy('"+ form.getId()+"'),id " 
			                              +"FROM vocSex")
				//.setParameter("patid", form.getId())
				.getSingleResult();
			//form.setNotice(form.getNotice()+form.getId() +polerr[0]+"----"+polerr.length+polerr.toString()) ;
			if(polerr[0]!=null) form.addMessage(new FormMessage(""+polerr[0]));
		
		} catch(Exception e){
			
		}*/
		// AND cast("_Date_" as integer) between cast(actualDateFrom as integer) and cast(actualDateTo as integer)
	}
}
