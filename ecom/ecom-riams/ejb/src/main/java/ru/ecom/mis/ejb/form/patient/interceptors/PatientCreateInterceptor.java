package ru.ecom.mis.ejb.form.patient.interceptors;

import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.LpuAttachedByDepartmentForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;

public class PatientCreateInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		PatientForm form = (PatientForm) aForm ;
		Patient patient = (Patient) aEntity ;
		patient.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		java.sql.Date cur = new java.sql.Date(new java.util.Date().getTime()) ;
		patient.setCreateDate(cur) ;
		patient.setEditDate(cur) ;
		
		
		//form.setNumber(next);
		
		if (patient.getPatientSync()==null || patient.getPatientSync().equals("")) {
			if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MisLpuDynamic/1/View")) {
				String syncCode = EjbInjection.getInstance().getLocalService(ISequenceService.class).startUseNextValue("Patient","patientSync");
				patient.setPatientSync(syncCode) ;
			} else {
				patient.setPatientSync(new StringBuilder().append("Н").append(patient.getId()).toString()) ;
			}
		}
		/*if(form.isAttachedByDepartment()) {
			// убираем прикрепление по полису, потом SaveInterceptor его прикрепит по адресу
			patient.setAttachedOmcPolicy(null);
			patient.setLpu(null);
			patient.setLpuArea(null);
			
			// новое прикрепление по ведомству
			LpuAttachedByDepartmentForm lpuAttachedForm = new LpuAttachedByDepartmentForm() ;
			lpuAttachedForm.setPatient(patient.getId());
			lpuAttachedForm.setArea(form.getLpuArea());
			lpuAttachedForm.setLpu(form.getLpu());
			try {
				EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
					.create(lpuAttachedForm);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
			
		}*/
		
	}
}
