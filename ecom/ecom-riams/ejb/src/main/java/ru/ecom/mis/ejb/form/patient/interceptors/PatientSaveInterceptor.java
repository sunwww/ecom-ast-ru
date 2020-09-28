package ru.ecom.mis.ejb.form.patient.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.LpuAttachedByDepartmentForm;
import ru.ecom.mis.ejb.form.patient.MedPolicyOmcForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.util.StringUtil;

public class PatientSaveInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		PatientForm form = (PatientForm) aForm ;
		Patient patient = (Patient) aEntity ;
		patient.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		patient.setEditDate(new java.sql.Date(new java.util.Date().getTime())) ;

		if(form.getCreateNewOmcPolicy()) {
			MedPolicyOmcForm polForm = form.getPolicyOmcForm() ;
			polForm.setSeries(polForm.getSeries().toUpperCase().trim());
			polForm.setPolNumber(polForm.getPolNumber().toUpperCase().trim());
			polForm.setPatient(patient.getId());
			polForm.setLastname(form.getLastname());
			polForm.setFirstname(form.getFirstname());
			polForm.setMiddlename(form.getMiddlename());
			try {
				EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
					.create(polForm);
			} catch (Exception e) {
				throw new IllegalStateException(e.getMessage());
			}
		}
		if (form.getCreateAttachedByDepartment()) {
			LpuAttachedByDepartmentForm attForm = form.getAttachedForm() ;
			attForm.setPatient(patient.getId());
			try {
				EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
						.create(attForm);
			} catch (Exception e) {
				throw new IllegalStateException(e.getMessage());
			}
		}
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MisLpuDynamic/1/View") && StringUtil.isNullOrEmpty(form.getPhone())) { //Только для АМОКБ
			throw new IllegalStateException("Необходимо указать контактный телефон");
		}
	}
}
