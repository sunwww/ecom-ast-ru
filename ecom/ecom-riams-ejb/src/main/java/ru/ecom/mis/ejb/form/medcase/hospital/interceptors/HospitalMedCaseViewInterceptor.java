package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.List;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
//import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.forms.response.FormMessage;

public class HospitalMedCaseViewInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		HospitalMedCaseForm form=(HospitalMedCaseForm)aForm ;
		HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		//System.out.println("form view only ="+form.isViewOnly());
		if (!form.isViewOnly()) {
			try {
				SecPolicy.checkPolicyEditHour(aContext.getSessionContext(), "/Policy/Mis/MedCase/Stac/Ssl/Admission/EditHour", medCase) ;
			} catch(Exception e){
				//form.getPage();
				form.setTypeViewOnly() ;
				form.addMessage(new FormMessage(e.toString()));
			}
			
		}
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber")) {
            form.addDisabledField("statCardNumber");
        }
		if (medCase!=null && medCase.getDiagnosis()!=null) {
			List<Diagnosis> diagList = medCase.getDiagnosis() ;
			for(Diagnosis diag:diagList) {
				String regType = diag.getRegistrationType()!=null?diag.getRegistrationType().getCode():"" ;
				String prior = diag.getPriority()!=null?diag.getPriority().getCode():"" ;
				Long mkb = diag.getIdc10()!=null?diag.getIdc10().getId():null ;
				// Order
				if (regType.equals("2")) {
					form.setOrderDiagnos(diag.getName());
					if (diag.getIdc10()!=null) form.setOrderMkb(mkb);
				}
				// Enter
				if (regType.equals("1")) {
					form.setEntranceDiagnos(diag.getName());
					if (diag.getIdc10()!=null) form.setEntranceMkb(mkb);
				}
				//Concomitant
				if (regType.equals("4") && prior.equals("3")) {
					form.setConcomitantDiagnos(diag.getName());
					if (mkb!=null) form.setConcomitantMkb(mkb) ;
				}	
//				 Concluding
				if (regType.equals("3")&& prior.equals("1")){
					form.setConcludingDiagnos(diag.getName());
					if (mkb!=null) form.setConcludingMkb(mkb) ;
				}
			    //Clinical
				if ( regType.equals("4") && prior.equals("1")){
					form.setClinicalDiagnos(diag.getName());
					if (mkb!=null) form.setClinicalMkb(mkb) ;
				}
			    //Pathanatomical
				if (regType.equals("5") && prior.equals("1")) {
					form.setPathanatomicalDiagnos(diag.getName());
					if (mkb!=null) form.setPathanatomicalMkb(mkb) ;
				}	
				
			}
		}
		
	}

}
