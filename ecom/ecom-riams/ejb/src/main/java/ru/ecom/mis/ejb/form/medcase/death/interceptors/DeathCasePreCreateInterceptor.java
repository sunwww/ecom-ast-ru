package ru.ecom.mis.ejb.form.medcase.death.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.death.DeathCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DeathCasePreCreateInterceptor  implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager= aContext.getEntityManager() ;
    	HospitalMedCase medCase = manager.find(HospitalMedCase.class, aParentId)  ;
    	DeathCaseForm form = (DeathCaseForm) aForm ;
		if (medCase.getDateFinish()!=null &&medCase.getDischargeTime()!=null) {
			form.setDeathDate(DateFormat.formatToDate(medCase.getDateFinish())) ;
			form.setDeathTime(DateFormat.formatToTime(medCase.getDischargeTime())) ;
		} else {
			throw new IllegalArgumentException("Нельзя создать случай смерти в открытом СЛС!!!") ;
		}
		if (medCase.getResult()==null || !medCase.getResult().getCode().equals("11")) {
			throw new IllegalArgumentException("Случай смерти создается только при результате госпитализации смерть, а не при "+(medCase.getResult()!=null ? medCase.getResult().getName(): "_NULL_")+"!!!") ;
		}
    	List<Object> l = aContext.getEntityManager().createNativeQuery("select id from DeathCase where medCase_id='"+medCase.getId()+"'").getResultList() ;
    	if (!l.isEmpty()) {
    		throw new IllegalArgumentException("В данном СЛС уже создан <a href=entityParentView-stac_deathCase.do?id="+l.get(0).toString()+">случай смерти!</a>") ;
    	}
    	form.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString());
        Date date = new Date();
    	form.setCreateDate(DateFormat.formatToDate(date));
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setCreateTime(timeFormat.format(date));
		
        long id = medCase.getId() ;
		DiagnosisForm frm ;
		// Concluding
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(manager, id, "3", "1", false) ;
		if (frm!=null){
			form.setConcludingDiagnos(frm.getName());
			if (frm.getIdc10()!=null) form.setConcludingMkb(frm.getIdc10()) ;
			if (frm.getIllnesPrimary()!=null) form.setConcludingActuity(frm.getIllnesPrimary()) ;
		} else {
			frm = DischargeMedCaseViewInterceptor.getDiagnosis(manager, id, "4", "1", false) ;
			if (frm!=null){
				form.setConcludingDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setConcludingMkb(frm.getIdc10()) ;
				if (frm.getIllnesPrimary()!=null) form.setConcludingActuity(frm.getIllnesPrimary()) ;
			}
		}
		//Pathanatomical
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(manager, id, "5", "1", false) ;
		if (frm!=null) {
			form.setPathanatomicalDiagnos(frm.getName());
			if (frm.getIdc10()!=null) form.setPathanatomicalMkb(frm.getIdc10()) ;
		}	
        
        
    }

}