package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.DischargeMedCaseForm;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.nuzmsh.util.format.DateFormat;

public class DepartmentSaveInterceptor  implements IFormInterceptor{

	private final static Logger LOG = Logger.getLogger(DischargeMedCaseSaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DepartmentMedCaseForm form=(DepartmentMedCaseForm)aForm ;
		if (CAN_DEBUG) LOG.debug("Проверка правильности введенных данных");
		EntityManager manager = aContext.getEntityManager() ;
		DepartmentMedCase medCase = (DepartmentMedCase)aEntity ;
		boolean adding4is = (!isEmpty(form.getClinicalDiagnos()) || (!isEmpty(form.getClinicalMkb()))) ;	
		boolean adding5is = (!isEmpty(form.getPathanatomicalDiagnos()) || (!isEmpty(form.getPathanatomicalMkb()))) ;	
		boolean adding3is = (!isEmpty(form.getConcludingDiagnos()) || (!isEmpty(form.getConcludingMkb()))) ;
		boolean adding1is = (!isEmpty(form.getEntranceDiagnos()) || (!isEmpty(form.getEntranceMkb()))) ;
		boolean adding6is = (!isEmpty(form.getConcomitantDiagnos()) || (!isEmpty(form.getConcomitantMkb()))) ;
		String dateFinish = "null" ;
		if (medCase.getDateFinish()!=null) {
			dateFinish = new StringBuilder().append("to_date('").append(DateFormat.formatToDate(medCase.getDateFinish())).append("','dd.mm.yyyy')").toString() ;
		}
		String timeFinish ="null" ;
		if (medCase.getDischargeTime()!=null) {
			timeFinish = new StringBuilder().append("'").append(DateFormat.formatToTime(medCase.getDischargeTime())).append("'").toString() ;
		}
		StringBuilder sqlupdate = new StringBuilder() ;
		System.out.println() ;
		sqlupdate.append("update MedCase set dateFinish="+dateFinish+", dischargeTime="+timeFinish+" where parent_id=:parent and DTYPE='DepartmentMedCase' and (dateFinish is not null or (transferDate is null and dateFinish is null))") ;
		manager.createNativeQuery(sqlupdate.toString())
			//.setParameter("dateF", medCase.getDateFinish())
			//.setParameter("timeF", medCase.getDischargeTime())
			.setParameter("parent", form.getId())
			.executeUpdate() ;
		if (adding4is|| adding5is||adding3is||adding1is) {
			boolean adding4 = false ;
			if (!adding4is) adding4 = true ; 
			boolean adding5 = false ;
			if (!adding5is) adding5 = true ; 
			boolean adding3 = false ;
			if (!adding3is) adding3 = true ;
			boolean adding1 = false ;
			if (!adding1is) adding1 = true ; 
			boolean adding6 = false ;
			if (!adding6is) adding6 = true ; 
			
			/*VocDiagnosisRegistrationType vocTypeClinical = manager.find(VocDiagnosisRegistrationType.class, Long.valueOf(4));
			VocDiagnosisRegistrationType vocTypePathanatomical = manager.find(VocDiagnosisRegistrationType.class, Long.valueOf(5));
			VocDiagnosisRegistrationType vocTypeConcluding = manager.find(VocDiagnosisRegistrationType.class, Long.valueOf(3));*/
			VocDiagnosisRegistrationType vocTypeClinical = (VocDiagnosisRegistrationType)getVocByCode(manager,"VocDiagnosisRegistrationType","4");
			VocDiagnosisRegistrationType vocTypePathanatomical = (VocDiagnosisRegistrationType)getVocByCode(manager,"VocDiagnosisRegistrationType","5");
			VocDiagnosisRegistrationType vocTypeConcluding = (VocDiagnosisRegistrationType)getVocByCode(manager,"VocDiagnosisRegistrationType","3");
			VocDiagnosisRegistrationType vocTypeEnter = (VocDiagnosisRegistrationType)getVocByCode(manager,"VocDiagnosisRegistrationType","1");
			
			VocPriorityDiagnosis vocPriorType = (VocPriorityDiagnosis)getVocByCode(manager,"VocPriorityDiagnosis","1") ;
			VocPriorityDiagnosis vocConcomType = (VocPriorityDiagnosis)getVocByCode(manager,"VocPriorityDiagnosis","3") ;
			/*List<VocPriorityDiagnosis> listpr = manager.createQuery("from VocPriorityDiagnosis where code=1").getResultList() ;
			if (listpr.size()>0) vocPriorType=listpr.get(0) ;*/
			
			List<Diagnosis> diagList = aContext.getEntityManager().createQuery("from Diagnosis where medCase=:med").setParameter("med", medCase).getResultList() ; 
			if (diagList==null) diagList = new ArrayList<Diagnosis>(); 
			for(Diagnosis diag:diagList){
				if (!adding4) adding4=setDiagnosisByType(false,diag, vocTypeClinical, form.getClinicalDiagnos(), form.getDateFinish(), form.getClinicalMkb(), medCase, manager,vocPriorType,form.getClinicalActuity(),form.getMkbAdc()) ;
				if (!adding5) adding5=setDiagnosisByType(false,diag, vocTypePathanatomical, form.getPathanatomicalDiagnos(), form.getDateFinish(), form.getPathanatomicalMkb(), medCase, manager,vocPriorType,null) ;
				if (!adding3) adding3=setDiagnosisByType(false,diag, vocTypeConcluding, form.getConcludingDiagnos(), form.getDateFinish(), form.getConcludingMkb(), medCase, manager,vocPriorType,null) ;
				if (!adding1) adding1=setDiagnosisByType(false,diag, vocTypeEnter, form.getEntranceDiagnos(), form.getDateStart(), form.getEntranceMkb(), medCase, manager,vocPriorType,null) ;
				if (!adding6) adding6=setDiagnosisByType(false,diag, vocTypeClinical, form.getConcomitantDiagnos(), form.getDateFinish(), form.getConcomitantMkb(), medCase, manager,vocConcomType,null) ;
				if (adding4&&adding5&&adding3&&adding1&&adding6) break ;
			}
			
			if (!adding4|| !adding5|| !adding3|| !adding1|| !adding6) {
				if (!adding4) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeClinical, form.getClinicalDiagnos(), form.getDateFinish(), form.getClinicalMkb(), medCase, manager,vocPriorType,form.getClinicalActuity(),form.getMkbAdc()) ;
					//diagList.add(diag);
				}
				if (!adding5) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypePathanatomical, form.getPathanatomicalDiagnos(), form.getDateFinish(), form.getPathanatomicalMkb(), medCase, manager,vocPriorType,null) ;
					//diagList.add(diag);
				}
				if (!adding3) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeConcluding, form.getConcludingDiagnos(), form.getDateFinish(), form.getConcludingMkb(), medCase, manager,vocPriorType,null) ;
					//diagList.add(diag);
				}
				if (!adding1) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeEnter, form.getEntranceDiagnos(), form.getDateStart(), form.getEntranceMkb(), medCase, manager,vocPriorType,null) ;
					//diagList.add(diag);
				}
				if (!adding6) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeClinical, form.getConcomitantDiagnos(), form.getDateFinish(), form.getConcomitantMkb(), medCase, manager,vocConcomType,null) ;
					//diagList.add(diag);
				}
				//medCase.setDiagnosis(diagList);
			}
		}
		Long clinicalMkb = form.getClinicalMkb();
		Long department = form.getDepartment();
		Long patient = form.getPatient();
		Long serviceStream = form.getServiceStream();
		if (!isDiagnosisAllowed(clinicalMkb, department, patient, serviceStream, null,null,manager)) {
			throw new IllegalStateException ("Данный диагноз запрещен в отделении!");
		}
	}
    
    private Object getVocByCode(EntityManager manager,String aTable, String aCode) {
    	List list = manager.createQuery(new StringBuilder().append("from ").append(aTable).append(" where code='").append(aCode).append("'").toString()).getResultList() ;
    	return list.size()>0?list.get(0):null ; 
    }

    private boolean setDiagnosisByType(boolean aNewIs, Diagnosis aDiag, VocDiagnosisRegistrationType aType, String aName, String aDate, Long aCode, HospitalMedCase aMedCase, EntityManager aManager, VocPriorityDiagnosis aPriorityType, Long aActuity) {
    	return setDiagnosisByType( aNewIs,  aDiag, aType, aName, aDate,  aCode, aMedCase, aManager, aPriorityType, aActuity,null);
    }
private boolean setDiagnosisByType(boolean aNewIs, Diagnosis aDiag, VocDiagnosisRegistrationType aType, String aName, String aDate, Long aCode, HospitalMedCase aMedCase, EntityManager aManager, VocPriorityDiagnosis aPriorityType, Long aActuity, String aMkbAdc) {
	boolean resault = false ;
	if (!aNewIs) {
		aNewIs = aDiag.getRegistrationType()!=null && aDiag.getRegistrationType().equals(aType) 
			&& aDiag.getPriority()!=null && aDiag.getPriority().equals(aPriorityType)  
			//&& aDiag.getPriority()!=null && aDiag.getPrimary().equals(aPriorityType) 
			; 
	}
	if (aNewIs) {
		aDiag.setMkbAdc(aMkbAdc);
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
			//System.out.println("      actuity ="+actuity+""); 
			aDiag.setAcuity(actuity) ;
			aDiag.setPrimary(primary) ;
			aDiag.setIllnesPrimary(illnes) ;
			//VocAcuityDiagnosis actuity = aManager.find(VocAcuityDiagnosis.class, aActuity) ;
			//System.out.println("      actuity ="+actuity+""); 
			//aDiag.setAcuity(actuity) ;
		}
		resault = true ;
	}
	aManager.persist(aDiag) ;
	return resault ;
	
}
private static boolean isEmpty(Long aLong) {
    return (aLong == null)||(aLong == 0) ;
}
private static boolean isEmpty(String aString) {
    return (aString == null)||(aString.trim().equals("")) ;
}



public static boolean isDiagnosisAllowed(Long clinicalMkb, Long department, Long patient, Long serviceStream, Long diagnosisRegistrationType, Long diagnosisPriority, EntityManager manager) {
	if (diagnosisRegistrationType==null) {
		diagnosisRegistrationType = Long.valueOf(manager.createNativeQuery("select id from vocdiagnosisregistrationtype where code='4'").getResultList().get(0).toString());  //4
	}
	
	if (diagnosisPriority==null) {
		diagnosisPriority =Long.valueOf(manager.createNativeQuery("select id from vocprioritydiagnosis where code='1'").getResultList().get(0).toString()); //1
	}
	VocIdc10 mkb = manager.find(VocIdc10.class, clinicalMkb);
	boolean ret = true;
	boolean isPermitted = false;
	String sql = "select ni.id as f1, case when ldr.permissionrule='1' then '1' else '0' end as f2" +
		" ,case when '"+mkb.getCode()+"' between ni.fromidc10code and ni.toidc10code then '1' else '0' end as f3" +
		" from lpudiagnosisrule ldr" +
		" left join lpucontractnosologygroup lcng on lcng.lpudiagnosisrule = ldr.id" +
		" left join contractnosologygroup cng on cng.id=lcng.nosologygroup" +
		" left join nosologyinterval ni on ni.nosologygroup_id=cng.id" +
		" left join patient p on p.id=" + patient +
		" where ldr.department = "+department +
		" and ldr.id is not null" +
		" and ((ldr.diagnosisregistrationtype is null or ldr.diagnosisregistrationtype=0) or ldr.diagnosisregistrationtype="+diagnosisRegistrationType+")" +
		" and ((ldr.sex is null or ldr.sex=0) or ldr.sex=p.sex_id)" +
		" and ((ldr.diagnosispriority is null or ldr.diagnosispriority=0) or ldr.diagnosispriority="+diagnosisPriority+")" +
		" and ((ldr.servicestream is null or ldr.servicestream=0) or ldr.servicestream="+serviceStream+")";
		
	//System.out.println("=== DIAG, sql ="+sql);
		List<Object[]> o = manager.createNativeQuery(sql).getResultList();
	//	System.out.println("=== DIAG o = "+o.size());
		if (o==null||o.isEmpty()) {
			return true;
		} 
		boolean first = true;
		for (Object[] oo: o) {
		//	System.out.println("=== DIAG_n, o[] = "+oo[0]+":"+oo[1]+":"+oo[2]);
			if (first){
				isPermitted = oo[1].toString().equals("1")?true:false;
				first = false;
				ret = isPermitted?false:true;
			}
			boolean isEnter = oo[2].toString().equals("1")?true:false;
			if (isPermitted) {
				if (isEnter) {
					ret =  true;
					break;
				}
			} else {
				if (isEnter) {
					ret =  false;
					break;
				}
			}
			
		}
		return ret;
}

}
