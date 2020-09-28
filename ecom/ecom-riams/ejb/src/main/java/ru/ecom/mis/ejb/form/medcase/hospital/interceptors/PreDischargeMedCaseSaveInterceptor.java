package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.List;

public class PreDischargeMedCaseSaveInterceptor implements IFormInterceptor {
	    public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
			HospitalMedCaseForm form=(HospitalMedCaseForm)aForm ;

			HospitalMedCase medCase = (HospitalMedCase)aEntity ;
			boolean adding4is = (!isEmpty(form.getClinicalDiagnos()) || (!isEmpty(form.getClinicalMkb()))) ;	
			boolean adding5is = (!isEmpty(form.getPathanatomicalDiagnos()) || (!isEmpty(form.getPathanatomicalMkb()))) ;	
			boolean adding3is = (!isEmpty(form.getConcludingDiagnos()) || (!isEmpty(form.getConcludingMkb()))) ;
			boolean adding1is = (!isEmpty(form.getEntranceDiagnos()) || (!isEmpty(form.getEntranceMkb()))) ;
	//		boolean adding6is = (!isEmpty(form.getConcomitantDiagnos()) || (!isEmpty(form.getConcomitantMkb()))) ;
	//		boolean adding7is = (!isEmpty(form.getComplicationDiagnos()) || (!isEmpty(form.getComplicationMkb()))) ;
			
			if (adding4is|| adding5is||adding3is||adding1is) {
				boolean adding4 = false ;
				if (!adding4is) adding4 = true ; 
				boolean adding5 = false ;
				if (!adding5is) adding5 = true ; 
				boolean adding3 = false ;
				if (!adding3is) adding3 = true ;
				boolean adding1 = false ;
				if (!adding1is) adding1 = true ; 
				
				
				/*VocDiagnosisRegistrationType vocTypeClinical = aManager.find(VocDiagnosisRegistrationType.class, Long.valueOf(4));
				VocDiagnosisRegistrationType vocTypePathanatomical = aManager.find(VocDiagnosisRegistrationType.class, Long.valueOf(5));
				VocDiagnosisRegistrationType vocTypeConcluding = aManager.find(VocDiagnosisRegistrationType.class, Long.valueOf(3));*/
				VocDiagnosisRegistrationType vocTypeClinical = (VocDiagnosisRegistrationType)getVocByCode(aManager,"VocDiagnosisRegistrationType","4");
				VocDiagnosisRegistrationType vocTypePathanatomical = (VocDiagnosisRegistrationType)getVocByCode(aManager,"VocDiagnosisRegistrationType","5");
				VocDiagnosisRegistrationType vocTypeConcluding = (VocDiagnosisRegistrationType)getVocByCode(aManager,"VocDiagnosisRegistrationType","3");
				VocDiagnosisRegistrationType vocTypeEnter = (VocDiagnosisRegistrationType)getVocByCode(aManager,"VocDiagnosisRegistrationType","1");
				
				VocPriorityDiagnosis vocPriorType = (VocPriorityDiagnosis)getVocByCode(aManager,"VocPriorityDiagnosis","1") ;
		//		VocPriorityDiagnosis vocConcomType = (VocPriorityDiagnosis)getVocByCode(aManager,"VocPriorityDiagnosis","3") ;
		//		VocPriorityDiagnosis vocComplicationType = (VocPriorityDiagnosis)getVocByCode(aManager,"VocPriorityDiagnosis","4") ;
				/*List<VocPriorityDiagnosis> listpr = aManager.createQuery("from VocPriorityDiagnosis where code=1").getResultList() ;
				if (listpr.size()>0) vocPriorType=listpr.get(0) ;*/
				
				List<Diagnosis> diagList = aManager.createQuery("from Diagnosis where medCase_id="+medCase.getId()).getResultList() ;
				//if (diagList==null) diagList = new ArrayList<Diagnosis>(); 
				for(Diagnosis diag:diagList){
					if (!adding4) adding4=setDiagnosisByType(false,diag, vocTypeClinical, form.getClinicalDiagnos(), form.getDateFinish(), form.getClinicalMkb(), medCase, aManager,vocPriorType,form.getClinicalActuity()) ;
					if (!adding5) adding5=setDiagnosisByType(false,diag, vocTypePathanatomical, form.getPathanatomicalDiagnos(), form.getDateFinish(), form.getPathanatomicalMkb(), medCase, aManager,vocPriorType,null) ;
					if (!adding3) adding3=setDiagnosisByType(false,diag, vocTypeConcluding, form.getConcludingDiagnos(), form.getDateFinish(), form.getConcludingMkb(), medCase, aManager,vocPriorType,form.getConcludingActuity()) ;
					if (!adding1) adding1=setDiagnosisByType(false,diag, vocTypeEnter, form.getEntranceDiagnos(), form.getDateStart(), form.getEntranceMkb(), medCase, aManager,vocPriorType,null) ;
					if (adding4&&adding5&&adding3&&adding1) break ;
				}
				
				if (!adding4) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeClinical, form.getClinicalDiagnos(), form.getDateFinish(), form.getClinicalMkb(), medCase, aManager,vocPriorType,form.getClinicalActuity()) ;
					//diagList.add(diag);
				}
				if (!adding5) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypePathanatomical, form.getPathanatomicalDiagnos(), form.getDateFinish(), form.getPathanatomicalMkb(), medCase, aManager,vocPriorType,null) ;
					//diagList.add(diag);
				}
				if (!adding3) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeConcluding, form.getConcludingDiagnos(), form.getDateFinish(), form.getConcludingMkb(), medCase, aManager,vocPriorType,form.getConcludingActuity()) ;
					//diagList.add(diag);
				}
				if (!adding1) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeEnter, form.getEntranceDiagnos(), form.getDateStart(), form.getEntranceMkb(), medCase, aManager,vocPriorType,null) ;
					//diagList.add(diag);
				}
				
				
			}
			DepartmentSaveInterceptor.setDiagnosis(aManager, form.getId(), form.getComplicationDiags(), "3", "4",null) ;
			DepartmentSaveInterceptor.setDiagnosis(aManager, form.getId(), form.getConcomitantDiags(), "3", "3",null) ;
		}
	    
	    public static Object getVocByCode(EntityManager aManager,String aTable, String aCode) {
	    	List list = aManager.createQuery("from "+aTable+" where code='"+aCode+"'").getResultList() ;
	    	return list.isEmpty() ? null : list.get(0) ;
	    }


		public static boolean setDiagnosisByType(boolean aNewIs, Diagnosis aDiag, VocDiagnosisRegistrationType aType, String aName, String aDate, Long aCode, HospitalMedCase aMedCase, EntityManager aManager, VocPriorityDiagnosis aPriorityType, Long aActuity) {
			boolean result = false ;
			if (!aNewIs) {
				aNewIs = aDiag.getRegistrationType()!=null && aDiag.getRegistrationType().equals(aType) 
					&& aDiag.getPriority()!=null && aDiag.getPriority().equals(aPriorityType)  
					//&& aDiag.getPriority()!=null && aDiag.getPrimary().equals(aPriorityType) 
					; 
			}
			if (aNewIs) {
				aDiag.setName(aName);
				if (aCode!=null) {
					VocIdc10 mkb = aManager.find(VocIdc10.class, aCode) ;
					aDiag.setIdc10(mkb);
				}
				aDiag.setMedCase(aMedCase);
				try {aDiag.setEstablishDate(DateFormat.parseSqlDate(aDate));} 
				catch(Exception e) {
					
				}
				if (aDiag.getRegistrationType()==null) aDiag.setRegistrationType(aType);
				if (aDiag.getPriority()==null) aDiag.setPriority(aPriorityType) ;
				if(!isEmpty(aActuity)) {
					VocIllnesPrimary illnes = aManager.find(VocIllnesPrimary.class, aActuity) ;
					VocAcuityDiagnosis actuity = illnes.getIllnesType() ;
					VocPrimaryDiagnosis primary = illnes.getPrimary() ;
					aDiag.setAcuity(actuity) ;
					aDiag.setPrimary(primary) ;
					aDiag.setIllnesPrimary(illnes) ;
				}
				aManager.persist(aDiag) ;
				result = true ;
			}
			return result ;
			
		}
		public static boolean isEmpty(Long aLong) {
		    return (aLong == null)||(aLong == 0) ;
		}
		public static boolean isEmpty(String aString) {
		    return (aString == null)||(aString.trim().equals("")) ;
		}


	}
