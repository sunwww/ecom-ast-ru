package ru.ecom.mis.ejb.form.prescription.template.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.prescription.PrescriptListTemplate;
import ru.ecom.mis.ejb.form.prescription.template.DietPrescriptionForm;
import ru.ecom.mis.ejb.form.prescription.template.ModePrescriptionForm;
import ru.ecom.mis.ejb.form.prescription.template.PrescriptListForm;

public class PrescriptListCreateInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		PrescriptListForm form = (PrescriptListForm) aForm ;
		PrescriptListTemplate prescriptList = (PrescriptListTemplate) aEntity ;
		prescriptList.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		java.sql.Date cur = new java.sql.Date(new java.util.Date().getTime()) ;
		prescriptList.setCreateDate(cur) ;
		prescriptList.setEditDate(cur) ;
		
		ModePrescriptionForm modeForm = form.getModeForm() ;
		// Сохранение режима
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/Prescription/Template/ModePrescription/Create")
				&& modeForm!=null && modeForm.getModePrescription()!=null 
				 && !modeForm.equals(Long.valueOf(0))
				
				) {
			
			modeForm.setPrescriptionList(prescriptList.getId());
			try {
				
				EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
				.create(modeForm);
			} catch (Exception e) {}
		}
		DietPrescriptionForm dietForm = form.getDietForm() ;
		// Сохранение диеты
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/Prescription/Template/DietPrescription/Create")
				&&dietForm!=null && dietForm.getDiet()!=null && !dietForm.equals(Long.valueOf(0))
				
				) {
			dietForm.setPrescriptionList(prescriptList.getId());
			try {
				EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
					.create(dietForm);
			} catch (Exception e) {}
		}
		// Сохранение назначение лекарственных средств
		
	}
}