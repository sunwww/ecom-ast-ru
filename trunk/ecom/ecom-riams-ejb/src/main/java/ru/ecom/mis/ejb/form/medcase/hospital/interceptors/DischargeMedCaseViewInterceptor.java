package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.ExtHospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.DischargeMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.ExtHospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;


public class DischargeMedCaseViewInterceptor implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		HospitalMedCaseForm form = (HospitalMedCaseForm)aForm ;
		HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		EntityManager manager = aContext.getEntityManager() ;
		long id = form.getId() ;
		//System.out.println("----------------1") ;
		if (medCase.getDateFinish()==null || medCase.getDischargeTime()==null){
			if (medCase instanceof ExtHospitalMedCase) {}else{
			if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/CheckPrintAllProtocol")) {
				StringBuilder sql = new StringBuilder() ;
				sql.append("select count(*) from diary p left join medcase m on m.id=p.medcase_id where ((m.id='"+id+"' and m.dtype='HospitalMedCase') or (m.parent_id='"+id+"' and m.dtype='DepartmentMedCase')) and p.printDate is null") ;
				Object obj = manager
					.createNativeQuery(sql.toString()).getSingleResult() ;
				Long count = ConvertSql.parseLong(obj) ;
				if (count>0){
					throw new IllegalArgumentException("Необходимо перед выпиской распечатать все протоколы! <a href='printProtocolsBySLS.do?stNoPrint=selected&id="+id+"'>Есть "+(count.intValue())+" нераспечатанный(х) протокол(ов)</a>");
				}
			}
			
				if (medCase.getChildMedCase().size()==0)  
				throw new IllegalArgumentException("Необходимо перед выпиской создать случай лечения в отделении");
			//System.out.println("----------------1") ;
			
			@SuppressWarnings("unchecked")
			List<DepartmentMedCase> list = manager
				.createQuery("from MedCase where DTYPE='DepartmentMedCase' and parent_id=:hosp and ((dateFinish is not null) or (dateFinish is null and transferDate is null))")
				.setParameter("hosp",medCase.getId()).getResultList();
			
			if (list.size()==0) {
				throw new IllegalArgumentException("Нет случая лечения в отделении c датой выписки или без перевода");
			} 
			
			//System.out.println("----------------1") ;
			DepartmentMedCase dischargeDepartment = list.get(0) ;
			if (dischargeDepartment.getDateFinish()!=null) form.setDateFinish(DateFormat.formatToDate(dischargeDepartment.getDateFinish())) ;
			if (dischargeDepartment.getDischargeTime()!=null) form.setDischargeTime(DateFormat.formatToTime(dischargeDepartment.getDischargeTime())) ;
			//List<MedCase> listdep = medCase.getChildMedCase();
			}
			DiagnosisForm frm ;
			//System.out.println("----------------1") ;
			// Entrance
			//DiagnosisForm frm = getDiagnosis(aContext.getEntityManager(), id, "1", "1", true) ;
			//if (frm!=null){
			//	form.setEntranceDiagnos(frm.getName());
			//	form.setEntranceMkb(frm.getIdc10()) ;
			//}
			// Concluding
			frm = getDiagnosis(aContext.getEntityManager(), id, "3", "1", true) ;
			if (frm!=null){
				form.setConcludingDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setConcludingMkb(frm.getIdc10()) ;
			} else {
				frm = getDiagnosis(aContext.getEntityManager(), id, "4", "1", true) ;
				if (frm!=null) {
					form.setConcludingDiagnos(frm.getName());
					if (frm.getIdc10()!=null) form.setConcludingMkb(frm.getIdc10()) ;
					if (frm.getIllnesPrimary()!=null) form.setConcludingActuity(frm.getIllnesPrimary()) ;
				}
			}
			//Clinical
			frm = getDiagnosis(aContext.getEntityManager(), id, "4", "1", true) ;
			if (frm!=null){
				form.setClinicalDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setClinicalMkb(frm.getIdc10()) ;
				if (frm.getIllnesPrimary()!=null) form.setClinicalActuity(frm.getIllnesPrimary()) ;
			}
			//Pathanatomical
			frm = getDiagnosis(aContext.getEntityManager(), id, "5", "1", true) ;
			if (frm!=null) {
				form.setPathanatomicalDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setPathanatomicalMkb(frm.getIdc10()) ;
			}	
			//Concomitant
			frm = getDiagnosis(aContext.getEntityManager(), id, "4", "3", true) ;
			if (frm!=null) {
				form.setConcomitantDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setConcomitantMkb(frm.getIdc10()) ;
			}
			
		} else {
			DiagnosisForm frm ;
			//System.out.println("----------------1") ;
			// Entrance
			//DiagnosisForm frm = getDiagnosis(aContext.getEntityManager(), id, "1", "1", true) ;
			//if (frm!=null){
			//	form.setEntranceDiagnos(frm.getName());
			//	form.setEntranceMkb(frm.getIdc10()) ;
			//}
			// Concluding
			frm = getDiagnosis(aContext.getEntityManager(), id, "3", "1", false) ;
			if (frm!=null){
				form.setConcludingDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setConcludingMkb(frm.getIdc10()) ;
				if (frm.getIllnesPrimary()!=null) form.setConcludingActuity(frm.getIllnesPrimary()) ;
			}
			//Clinical
			frm = getDiagnosis(aContext.getEntityManager(), id, "4", "1", false) ;
			if (frm!=null){
				form.setClinicalDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setClinicalMkb(frm.getIdc10()) ;
				if (frm.getIllnesPrimary()!=null) form.setClinicalActuity(frm.getIllnesPrimary()) ;
			}
			//Pathanatomical
			frm = getDiagnosis(aContext.getEntityManager(), id, "5", "1", false) ;
			if (frm!=null) {
				form.setPathanatomicalDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setPathanatomicalMkb(frm.getIdc10()) ;
			}	
			//Concomitant
			frm = getDiagnosis(aContext.getEntityManager(), id, "4", "3", false) ;
			if (frm!=null) {
				form.setConcomitantDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setConcomitantMkb(frm.getIdc10()) ;
			}
		}
		/*
		//System.out.println("----------------1") ;
		if (medCase!=null && medCase.getDiagnosis()!=null) {
			List<Diagnosis> diagList = medCase.getDiagnosis() ;
			for(Diagnosis diag:diagList) {
				if (diag.getRegistrationType()!=null && diag.getPriority()!=null) {
					String regType = diag.getRegistrationType().getCode() ;
					String prior = diag.getPriority().getCode() ;
					Long mkb = diag.getIdc10()!=null?diag.getIdc10().getId():null ;
					//Long actuity = diag.getAcuity()!=null?diag.getAcuity().getId():null;
					Long illnes = diag.getIllnesPrimary()!=null?diag.getIllnesPrimary().getId():null;
					
					// Entrance
					if (regType.equals("1")&& prior.equals("1")){
						form.setEntranceDiagnos(diag.getName());
						if (mkb!=null) form.setEntranceMkb(mkb) ;
					}
					// Concluding
					if (regType.equals("3")&& prior.equals("1")){
						form.setConcludingDiagnos(diag.getName());
						if (mkb!=null) form.setConcludingMkb(mkb) ;
					}
				    //Clinical
					if ( regType.equals("4") && prior.equals("1")){
						form.setClinicalDiagnos(diag.getName());
						if (mkb!=null) form.setClinicalMkb(mkb) ;
						//if (actuity!=null) form.setClinicalActuity(actuity) ;
						if (illnes!=null) form.setClinicalActuity(illnes) ;
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
		}*/
	}
	public static DiagnosisForm getDiagnosis(EntityManager aManager, Long aHospitalMedCase
			,String aRegType, String aPriority, boolean aIsDepartmentSearch) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select diag.illnesPrimary_id,diag.idc10_id,diag.name from diagnosis diag") ;
		sql.append(" left join medcase dep on dep.id=diag.medCase_id");
		sql.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id");
		sql.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id");
		
		if (aIsDepartmentSearch) {
			sql.append(" where (dep.parent_id='").append(aHospitalMedCase).append("' or dep.id='").append(aHospitalMedCase).append("')");
		} else {
			sql.append(" where dep.id='").append(aHospitalMedCase).append("'");
		}
		
		if (aPriority!=null) sql.append(" and vpd.code='").append(aPriority).append("'") ;
		sql.append(" and vdrt.code='").append(aRegType).append("'");
		
		sql.append(" order by dep.dateStart desc");
		sql.append("") ;
		List<Object[]> listDiag = aManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (listDiag.size()>0) {
			Object[] obj = listDiag.get(0) ;
			DiagnosisForm frm = new DiagnosisForm() ;
			frm.setIdc10(ConvertSql.parseLong(obj[1])) ;
			frm.setIllnesPrimary(ConvertSql.parseLong(obj[0])) ;
			frm.setName(obj[2]!=null?(String)obj[2]:"") ;
			return frm ;
		}
		return null ;
	}

}
