package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.ExtHospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.List;


public class DischargeMedCaseViewInterceptor implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		HospitalMedCaseForm form = (HospitalMedCaseForm)aForm ;
		HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		EntityManager manager = aContext.getEntityManager() ;
		long id = form.getId() ;
		form.setDischargeEpicrisis(HospitalMedCaseViewInterceptor.getDischargeEpicrisis(medCase.getId(), aContext.getEntityManager())) ;
		Long sloLast = null ;
		if (medCase.getDateFinish()==null || medCase.getDischargeTime()==null){
			if (medCase instanceof ExtHospitalMedCase) {}else{
			if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/CheckPrintAllProtocol")) {
				Object obj = manager
					.createNativeQuery("select count(*) from diary p left join medcase m on m.id=p.medcase_id where ((m.id='" + id + "' and m.dtype='HospitalMedCase') or (m.parent_id='" + id + "' and m.dtype='DepartmentMedCase')) and p.printDate is null and p.dtype='Protocol'").getSingleResult() ;
				Long count = ConvertSql.parseLong(obj) ;
				if (count>0){
					throw new IllegalArgumentException("Необходимо перед выпиской распечатать все протоколы! <a href='printProtocolsBySLS.do?stNoPrint=selected&id="+id+"'>Есть "+(count.intValue())+" нераспечатанный(х) протокол(ов)</a>");
				}
			}
				List<Object[]> list1 = manager.createNativeQuery("select id,id from medcase where parent_id="+id).getResultList() ; 
				if (list1.size()==0) throw new IllegalArgumentException("Необходимо перед выпиской создать случай лечения в отделении");

			@SuppressWarnings("unchecked")
			List<DepartmentMedCase> list = manager
				.createQuery("from MedCase where DTYPE='DepartmentMedCase' and parent_id=:hosp and ((dateFinish is not null) or (dateFinish is null and transferDate is null))")
				.setParameter("hosp",medCase.getId()).getResultList();
			
			if (list.size()==0) {
				throw new IllegalArgumentException("Нет случая лечения в отделении c датой выписки или без перевода");
			} 
			
			DepartmentMedCase dischargeDepartment = list.get(0) ;
			sloLast = dischargeDepartment.getId() ;
			if (dischargeDepartment.getDateFinish()!=null) form.setDateFinish(DateFormat.formatToDate(dischargeDepartment.getDateFinish())) ;
			if (dischargeDepartment.getDischargeTime()!=null) form.setDischargeTime(DateFormat.formatToTime(dischargeDepartment.getDischargeTime())) ;
			}
			
			DiagnosisForm frm ;
			
			frm = getDiagnosis(aContext.getEntityManager(), id, "3", "1", true) ;
			if (frm!=null){
				form.setConcludingDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setConcludingMkb(frm.getIdc10()) ;
				if (frm.getIllnesPrimary()!=null) form.setConcludingActuity(frm.getIllnesPrimary()) ;
			} else {
				frm = getDiagnosis(aContext.getEntityManager(), id, "4", "1", true) ;
				if (frm!=null) {
					form.setConcludingDiagnos(frm.getName());
					if (frm.getIdc10()!=null) form.setConcludingMkb(frm.getIdc10()) ;
					if (frm.getIllnesPrimary()!=null) form.setConcludingActuity(frm.getIllnesPrimary()) ;
				}
			}
			form.setConcomitantDiags(DepartmentViewInterceptor.getDiagnosis(aContext.getEntityManager(), id, "3", "3")) ;
			if (sloLast!=null&&form.getConcomitantDiags()==null||form.getConcomitantDiags().equals("")) {
				form.setConcomitantDiags(DepartmentViewInterceptor.getDiagnosis(aContext.getEntityManager(),sloLast , "4", "3")) ;
			}
			// Complication
			form.setComplicationDiags(DepartmentViewInterceptor.getDiagnosis(aContext.getEntityManager(), id, "3","4")) ;
			if (sloLast!=null&&form.getComplicationDiags()==null||form.getComplicationDiags().equals("")) {
				form.setComplicationDiags(DepartmentViewInterceptor.getDiagnosis(aContext.getEntityManager(),sloLast , "4", "4")) ;
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
			frm = getDiagnosis(aContext.getEntityManager(), id, "3", "3", true) ;
			if (frm!=null){
				form.setConcomitantDiagnos(frm.getName());
				if (frm.getIdc10()!=null) form.setConcomitantMkb(frm.getIdc10()) ;
			} else {
				frm = getDiagnosis(aContext.getEntityManager(), id, "4", "3", true) ;
				if (frm!=null) {
					form.setConcomitantDiagnos(frm.getName());
					if (frm.getIdc10()!=null) form.setConcomitantMkb(frm.getIdc10()) ;
					//if (frm.getIllnesPrimary()!=null) form.setConcludingActuity(frm.getIllnesPrimary()) ;
				}
			}	
		} else {
			DiagnosisForm frm ;
			
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
			
			form.setConcomitantDiags(DepartmentViewInterceptor.getDiagnosis(aContext.getEntityManager(), id, "3", "3")) ;
			form.setComplicationDiags(DepartmentViewInterceptor.getDiagnosis(aContext.getEntityManager(), id, "3","4")) ;

		}
		
	}
	
	public static DiagnosisForm getDiagnosis(EntityManager aManager, Long aHospitalMedCase
			,String aRegType, String aPriority, boolean aIsDepartmentSearch) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select diag.illnesPrimary_id,diag.idc10_id,diag.name,diag.traumaType_id as traumatype,diag.id as diagid,diag.mkbadc as diagmkbadc from diagnosis diag") ;
		sql.append(" left join medcase dep on dep.id=diag.medCase_id");
		sql.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id");
		sql.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id");
		
		if (aIsDepartmentSearch) {
			sql.append(" where (dep.parent_id='").append(aHospitalMedCase).append("' or dep.id='").append(aHospitalMedCase).append("')");
		} else {
			sql.append(" where dep.id='").append(aHospitalMedCase).append("'");
		}
		
		if (aPriority!=null) sql.append(" and vpd.code='").append(aPriority).append("'") ;
		if (aRegType!=null) sql.append(" and vdrt.code='").append(aRegType).append("'");
		
		sql.append(" order by dep.dateStart desc");
		List<Object[]> listDiag = aManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (listDiag.size()>0) {
			Object[] obj = listDiag.get(0) ;
			DiagnosisForm frm = new DiagnosisForm() ;
			frm.setIdc10(ConvertSql.parseLong(obj[1])) ;
			frm.setIllnesPrimary(ConvertSql.parseLong(obj[0])) ;
			frm.setTraumaType(ConvertSql.parseLong(obj[3])) ;
			frm.setName(obj[2]!=null?(String)obj[2]:"") ;
			frm.setMkbAdc(obj[5]!=null?(String)obj[5]:"");
			Long id = ConvertSql.parseLong(obj[4]) ;
			frm.setId(id==null?Long.valueOf(0):id.longValue()) ;
			return frm ;
		}
		return null ;
	}

}
