package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;

public class DepartmentViewInterceptor  implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DepartmentMedCaseForm form = (DepartmentMedCaseForm)aForm ;
		DepartmentMedCase dep = (DepartmentMedCase)aEntity ;
		DiagnosisForm frm ;
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(aContext.getEntityManager(), dep.getId(), "4", "1", false) ;
		if (frm!=null) {
			form.setClinicalDiagnos(frm.getName());
			if (frm.getIdc10()!=null) form.setClinicalMkb(frm.getIdc10()) ;
			if (frm.getIllnesPrimary()!=null) form.setClinicalActuity(frm.getIllnesPrimary()) ;
			if (frm.getMkbAdc()!=null) form.setMkbAdc(frm.getMkbAdc()) ;
		}
		form.setComplicationDiags(getDiagnosis(aContext.getEntityManager(), dep.getId(), "4","4")) ;
		form.setConcomitantDiags(getDiagnosis(aContext.getEntityManager(), dep.getId(), "4", "3")) ;
	}
	public static String getDiagnosis(EntityManager aManager, Long aMedCase, String aRegistrationType, String aPriority) {
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select coalesce(d.idc10_id,'0'),coalesce(mkb.code||' '||mkb.name,'НЕОБХОДИМО УКАЗАТЬ КОД МКБ!!!') as mkbname,d.name from Diagnosis as d left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id");
		sql.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id left join VocIdc10 mkb on mkb.id=d.idc10_id where d.medCase_id=").append(aMedCase)
    		.append(" and vdrt.code='").append(aRegistrationType)
    		.append("' and vpd.code='").append(aPriority).append("' order by d.id") ;
    	StringBuilder res = new StringBuilder() ;
    	List<Object[]> list = aManager.createNativeQuery(sql.toString()).getResultList() ;
    	for (Object[] child : list) {
			res.append(child[0]).append("@#@").append(child[1]).append("@#@") ;
			res.append(child[2]).append("#@#") ;
		}
			
			
		return res.length()>0?res.substring(0,res.length()-3):"" ;
    }
}
