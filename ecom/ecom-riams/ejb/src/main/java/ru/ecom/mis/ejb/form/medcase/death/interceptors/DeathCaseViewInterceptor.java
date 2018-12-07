package ru.ecom.mis.ejb.form.medcase.death.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.death.DeathCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;

import javax.persistence.EntityManager;

public class DeathCaseViewInterceptor implements IFormInterceptor {
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DeathCaseForm form = (DeathCaseForm)aForm ;
		DeathCase deathCase = (DeathCase)aEntity ;
		EntityManager manager = aContext.getEntityManager() ;
		HospitalMedCase medCase = (HospitalMedCase)deathCase.getMedCase() ;
		long id = medCase.getId() ;

		DiagnosisForm frm ;
		// Concluding
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(manager, id, "3", "1", false) ;
		if (frm!=null){

			if ( form.getIsNeonatologic()!=null && form.getIsNeonatologic()) {
				form.setConcludingDiagnos(form.getCommentReason());
				if (frm.getIdc10() != null) form.setConcludingMkb(form.getReasonMainMkb());
			}
			else {
				form.setConcludingDiagnos(frm.getName());
				if (frm.getIdc10() != null) form.setConcludingMkb(frm.getIdc10());
			}

		}
		//Pathanatomical
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(manager, id, "5", "1", false) ;
		if (frm!=null) {
			form.setPathanatomicalDiagnos(frm.getName());
			if (frm.getIdc10()!=null) form.setPathanatomicalMkb(frm.getIdc10()) ;
		}	
		//Concomitant
		//frm = DischargeMedCaseViewInterceptor.getDiagnosis(aContext.getEntityManager(), id, "4", "3", false) ;
		//if (frm!=null) {
		//	form.setConcomitantDiagnos(frm.getName());
		//	if (frm.getIdc10()!=null) form.setConcomitantMkb(frm.getIdc10()) ;
		//}
	}
}
