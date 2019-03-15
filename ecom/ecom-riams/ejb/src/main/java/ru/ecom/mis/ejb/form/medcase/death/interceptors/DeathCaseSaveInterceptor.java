package ru.ecom.mis.ejb.form.medcase.death.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.form.medcase.death.DeathCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class DeathCaseSaveInterceptor implements IFormInterceptor {
	public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
		DeathCaseForm form=(DeathCaseForm)aForm ;
		
		DeathCase deathCase = (DeathCase)aEntity ;
		HospitalMedCase medCase = (HospitalMedCase)deathCase.getMedCase() ;
		//boolean adding4is = (!DischargeMedCaseSaveInterceptor.isEmpty(form.getClinicalDiagnos()) || (!DischargeMedCaseSaveInterceptor.isEmpty(form.getClinicalMkb()))) ;	
		boolean adding5is = (!DischargeMedCaseSaveInterceptor.isEmpty(form.getPathanatomicalDiagnos()) || (!DischargeMedCaseSaveInterceptor.isEmpty(form.getPathanatomicalMkb()))) ;	
		boolean adding3is = (!DischargeMedCaseSaveInterceptor.isEmpty(form.getConcludingDiagnos()) || (!DischargeMedCaseSaveInterceptor.isEmpty(form.getConcludingMkb()))) ;
		//boolean adding1is = (!DischargeMedCaseSaveInterceptor.isEmpty(form.getEntranceDiagnos()) || (!DischargeMedCaseSaveInterceptor.isEmpty(form.getEntranceMkb()))) ;
		//boolean adding6is = (!DischargeMedCaseSaveInterceptor.isEmpty(form.getConcomitantDiagnos()) || (!DischargeMedCaseSaveInterceptor.isEmpty(form.getConcomitantMkb()))) ;
		
		String dateFinish = "null" ;
		if (medCase.getDateFinish()!=null) {
			dateFinish = "to_date('" + DateFormat.formatToDate(medCase.getDateFinish()) + "','dd.mm.yyyy')";
		}
		String timeFinish ="null" ;
		if (medCase.getDischargeTime()!=null) {
			timeFinish = "'" + DateFormat.formatToTime(medCase.getDischargeTime()) + "'";
		}

		aManager.createNativeQuery("update MedCase set dateFinish=" + dateFinish + ", dischargeTime=" + timeFinish + " where parent_id=:parent and DTYPE='DepartmentMedCase' and (dateFinish is not null or (transferDate is null and dateFinish is null))")
			//.setParameter("dateF", medCase.getDateFinish())
			//.setParameter("timeF", medCase.getDischargeTime())
			.setParameter("parent", form.getId())
			.executeUpdate() ;
		boolean adding5 = false ;
		if (!adding5is) adding5 = true ; 
		boolean adding3 = false ;
		if (!adding3is) adding3 = true ;
		
		/*VocDiagnosisRegistrationType vocTypeClinical = aManager.find(VocDiagnosisRegistrationType.class, Long.valueOf(4));
		VocDiagnosisRegistrationType vocTypePathanatomical = aManager.find(VocDiagnosisRegistrationType.class, Long.valueOf(5));
		VocDiagnosisRegistrationType vocTypeConcluding = aManager.find(VocDiagnosisRegistrationType.class, Long.valueOf(3));*/
		VocDiagnosisRegistrationType vocTypePathanatomical = (VocDiagnosisRegistrationType)DischargeMedCaseSaveInterceptor.getVocByCode(aManager,"VocDiagnosisRegistrationType","5");
		VocDiagnosisRegistrationType vocTypeConcluding = (VocDiagnosisRegistrationType)DischargeMedCaseSaveInterceptor.getVocByCode(aManager,"VocDiagnosisRegistrationType","3");
		
		VocPriorityDiagnosis vocPriorType = (VocPriorityDiagnosis)DischargeMedCaseSaveInterceptor.getVocByCode(aManager,"VocPriorityDiagnosis","1") ;
		//VocPriorityDiagnosis vocConcomType = (VocPriorityDiagnosis)DischargeMedCaseSaveInterceptor.getVocByCode(aManager,"VocPriorityDiagnosis","3") ;
		/*List<VocPriorityDiagnosis> listpr = aManager.createQuery("from VocPriorityDiagnosis where code=1").getResultList() ;
		if (listpr.size()>0) vocPriorType=listpr.get(0) ;*/
		
		List<Diagnosis> diagList = aManager.createQuery("from Diagnosis where medCase=:med").setParameter("med", medCase).getResultList() ;
		if (diagList==null) diagList = new ArrayList<>();
		for(Diagnosis diag:diagList){
			if (!adding5) adding5=DischargeMedCaseSaveInterceptor.setDiagnosisByType(false,diag, vocTypePathanatomical, form.getPathanatomicalDiagnos(), form.getPostmortemBureauDt(), form.getPathanatomicalMkb(), medCase, aManager,vocPriorType,null) ;
			if (!adding3) adding3=DischargeMedCaseSaveInterceptor.setDiagnosisByType(false,diag, vocTypeConcluding, form.getConcludingDiagnos(), form.getDeathDate(), form.getConcludingMkb(), medCase, aManager,vocPriorType,form.getConcludingActuity()) ;
			if (adding5&&adding3) break ;
		}
		
		if (!adding5|| !adding3) {
			if (!adding5) {
				Diagnosis diag = new Diagnosis();
				DischargeMedCaseSaveInterceptor.setDiagnosisByType(true,diag, vocTypePathanatomical, form.getPathanatomicalDiagnos(), form.getPostmortemBureauDt(), form.getPathanatomicalMkb(), medCase, aManager,vocPriorType,null) ;
				//diagList.add(diag);
			}
			if (!adding3) {
				Diagnosis diag = new Diagnosis();
				DischargeMedCaseSaveInterceptor.setDiagnosisByType(true,diag, vocTypeConcluding, form.getConcludingDiagnos(), form.getDeathDate(), form.getConcludingMkb(), medCase, aManager,vocPriorType,form.getConcludingActuity()) ;
				//diagList.add(diag);
			}
			//medCase.setDiagnosis(diagList);
		}
		
	}
}
