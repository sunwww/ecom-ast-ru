package ru.ecom.mis.ejb.form.patient.interceptors;

import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.util.StringUtil;

public class PatientCreateInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		PatientForm form = (PatientForm) aForm ;
		Patient patient = (Patient) aEntity ;
		patient.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		java.sql.Date cur = new java.sql.Date(new java.util.Date().getTime()) ;
		patient.setCreateDate(cur) ;

		if (patient.getPatientSync()==null || patient.getPatientSync().equals("")) {
			if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MisLpuDynamic/1/View")) {
				String syncCode = EjbInjection.getInstance().getLocalService(ISequenceService.class).startUseNextValue("Patient","patientSync");
				patient.setPatientSync(syncCode) ;
			} else {
				patient.setPatientSync("Н"+patient.getId()) ;
			}
		}
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MisLpuDynamic/1/View") && StringUtil.isNullOrEmpty(form.getPhone())) { //Только для АМОКБ
			throw new IllegalStateException("Необходимо указать контактный телефон");
		}
	}
}
