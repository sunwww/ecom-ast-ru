package ru.ecom.mis.ejb.form.prescription.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.form.prescription.DietPrescriptionForm;
import ru.ecom.mis.ejb.form.prescription.ModePrescriptionForm;
import ru.ecom.mis.ejb.form.prescription.PrescriptListForm;

public class PrescriptListCreateInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		PrescriptListForm form = (PrescriptListForm) aForm ;
		PrescriptList prescriptList = (PrescriptList) aEntity ;
		prescriptList.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		java.sql.Date cur = new java.sql.Date(new java.util.Date().getTime()) ;
		prescriptList.setCreateDate(cur) ;
		prescriptList.setEditDate(cur) ;
		
		ModePrescriptionForm modeForm = form.getModeForm() ;
		// Сохранение режима
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/Prescription/ModePrescription/Create")
				&& modeForm!=null && modeForm.getModePrescription()!=null
				&& modeForm.getPlanStartDate()!=null
				&& !modeForm.getPlanStartDate().equals("")
				) {
			
			modeForm.setPrescriptionList(prescriptList.getId());
			try {
				
				EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
				.create(modeForm);
			} catch (Exception e) {}
		}
		DietPrescriptionForm dietForm = form.getDietForm() ;
		// Сохранение диеты
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/Prescription/DietPrescription/Create")
				&& dietForm!=null && dietForm.getDiet()!=null && !dietForm.getDiet().equals(Long.valueOf(0))
				&& dietForm.getPlanStartDate()!=null 
						&& !dietForm.getPlanStartDate().equals("")
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
