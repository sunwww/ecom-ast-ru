package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.List;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;

public class DepartmentViewInterceptor  implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DepartmentMedCaseForm form = (DepartmentMedCaseForm)aForm ;
		DepartmentMedCase dep = (DepartmentMedCase)aEntity ;
		List<Diagnosis> diags= dep.getDiagnosis() ;
		for (Diagnosis diag:diags) {
			
			if (diag.getRegistrationType()!=null && diag.getPriority()!=null) {
				
				String regType = diag.getRegistrationType().getCode() ;
				String prior = diag.getPriority()!=null?diag.getPriority().getCode():"" ;
				Long mkb = diag.getIdc10()!=null?diag.getIdc10().getId():null ;
				Long actuity = diag.getAcuity()!=null?diag.getAcuity().getId():null;

				// Concluding
				if (regType.equals("3")&& prior.equals("1")){
					form.setConcludingDiagnos(diag.getName());
					if (mkb!=null) form.setConcludingMkb(mkb) ;
				}
			    //Clinical
				if ( regType.equals("4") && prior.equals("1")){
					form.setClinicalDiagnos(diag.getName());
					if (mkb!=null) form.setClinicalMkb(mkb) ;
					if(actuity!=null) form.setClinicalActuity(actuity) ;
				}
			    //Pathanatomical
				if (regType.equals("5") && prior.equals("1")) {
					form.setPathanatomicalDiagnos(diag.getName());
					if (mkb!=null) form.setPathanatomicalMkb(mkb) ;
				}	
				//Concomitant
				if (regType.equals("4") && prior.equals("3")) {
					form.setConcomitantDiagnos(diag.getName());
					if (mkb!=null) form.setConcomitantMkb(mkb) ;
				}	
				
			}
		
	}

	}
}
