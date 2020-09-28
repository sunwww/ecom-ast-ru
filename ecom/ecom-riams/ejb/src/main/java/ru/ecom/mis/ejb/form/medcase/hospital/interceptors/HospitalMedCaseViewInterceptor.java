package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.forms.response.FormMessage;

import javax.persistence.EntityManager;
import java.util.List;

//import IFormInterceptor;

public class HospitalMedCaseViewInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		HospitalMedCaseForm form=(HospitalMedCaseForm)aForm ;
		HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		form.setDischargeEpicrisis(getDischargeEpicrisis(medCase.getId(), aContext.getEntityManager())) ;
		if (!form.isViewOnly()) {
			try {
				SecPolicy.checkPolicyEditHour(aContext.getSessionContext(), medCase) ;
			} catch(Exception e){
				//form.getPage();
				form.setTypeViewOnly() ;
				form.addMessage(new FormMessage(e.toString()));
			}
			
		}
        if (!aContext.getSessionContext().isCallerInRole(StatisticStubStac.CHANGE_STAT_CARD_NUMBER)) {
            form.addDisabledField("statCardNumber");
        }
		List<Diagnosis> diagList = aContext.getEntityManager().createQuery("from Diagnosis where medCase=:med").setParameter("med", medCase).getResultList() ;
		for(Diagnosis diag:diagList) {
			String regType = diag.getRegistrationType()!=null ? diag.getRegistrationType().getCode() : "" ;
			String prior = diag.getPriority()!=null ? diag.getPriority().getCode() : "" ;
			Long mkb = diag.getIdc10()!=null ? diag.getIdc10().getId() : null ;
			// Order
			if (regType.equals("2")) {
				form.setOrderDiagnos(diag.getName());
				if (mkb!=null) form.setOrderMkb(mkb);
			}
			// Enter
			if (regType.equals("1")) {
				form.setEntranceDiagnos(diag.getName());
				if (mkb!=null) form.setEntranceMkb(mkb);
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
	public static String getDischargeEpicrisis(long aMedCaseId,EntityManager aManager) {
		List<Object[]> l2=aManager.createNativeQuery("select id,DischargeEpicrisis from medcase where id= "+aMedCaseId).getResultList() ;
		List<Object[]> l1= aManager.createNativeQuery("select d.id,d.record from diary d where d.medcase_id= "+aMedCaseId+" and upper(d.dtype)='DISCHARGEEPICRISIS' order by d.id").getResultList() ;
		StringBuilder ret = new StringBuilder() ;
		if (!l1.isEmpty()) {
			for (Object[] obj:l1) {
				ret.append(obj[1]!=null?obj[1]:"") ;
			}
		} else {
			for (Object[] obj:l2) {
				ret.append(obj[1]!=null?obj[1]:"") ;
			}
		}
		return ret.toString() ;
	}

}
