package ru.ecom.mis.ejb.form.patient.interceptors;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.util.date.AgeUtil;

public class PatientViewInterceptor implements IFormInterceptor {

	private final static Logger LOG = Logger
			.getLogger(PatientViewInterceptor.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void intercept(IEntityForm aForm, Object aEntity,
			InterceptorContext aContext) {

		Patient pat = (Patient) aEntity;
		PatientForm form = (PatientForm) aForm ;
		/*if(pat!=null ){
			if (pat.getAttachedOmcPolicy()!=null) {
				form.setAttachedByPolicy(true);
			}
		}*/
		if (form.getBirthday()!=null && !form.getBirthday().equals("")) {
			String age = AgeUtil.getAgeCache(new java.util.Date(),pat.getBirthday(), 2) ;
			form.setAge(age) ;
		}
		
	}
	

}
