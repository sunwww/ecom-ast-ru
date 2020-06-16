package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.nuzmsh.forms.response.FormMessage;

import java.util.List;

//import IFormInterceptor;

public class AdmissionViewInterceptor implements IFormInterceptor {

	@SuppressWarnings("static-access")
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		AdmissionMedCaseForm form=(AdmissionMedCaseForm)aForm ;
		HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		form.setDischargeEpicrisis(HospitalMedCaseViewInterceptor.getDischargeEpicrisis(medCase.getId(), aContext.getEntityManager())) ;
		
		if (!form.isTypeCreate() && form.getSaveType()==form.TYPE_SAVE) {
			try {
				SecPolicy.checkPolicyEditHour(aContext.getSessionContext(), medCase) ;
			} catch(Exception e){
				form.getPage();
				form.setTypeViewOnly() ;
				form.addMessage(new FormMessage(e.getMessage()));
			}
			
		}
        if (!aContext.getSessionContext().isCallerInRole(StatisticStubStac.CHANGE_STAT_CARD_NUMBER)) {
            form.addDisabledField("statCardNumber");
        }
		List<Diagnosis> diagList = aContext.getEntityManager().createQuery("from Diagnosis where medCase=:med").setParameter("med", medCase).getResultList() ;
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
