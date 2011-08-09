package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.List;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
//import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.nuzmsh.forms.response.FormMessage;

public class AdmissionViewInterceptor implements IFormInterceptor {

	@SuppressWarnings("static-access")
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		AdmissionMedCaseForm form=(AdmissionMedCaseForm)aForm ;
		HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		if (!form.isTypeCreate() && form.getSaveType()==form.TYPE_SAVE) {
			try {
				SecPolicy.checkPolicyEditHour(aContext.getSessionContext(), "/Policy/Mis/MedCase/Stac/Ssl/Admission/EditHour", medCase) ;
			} catch(Exception e){
				form.getPage();
				form.setTypeViewOnly() ;
				form.addMessage(new FormMessage(e.getMessage()));
			}
			
		}
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber")) {
            form.addDisabledField("statCardNumber");
        }
        
		if (medCase!=null && medCase.getDiagnosis()!=null) {
			List<Diagnosis> diagList = medCase.getDiagnosis() ;
			for(Diagnosis diag:diagList) {
				String regType = diag.getRegistrationType()!=null?diag.getRegistrationType().getCode():"" ;
				//String prior = diag.getPriority().getCode() ;
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
			}
		}
		
	}

}
