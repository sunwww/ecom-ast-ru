package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;
import ru.nuzmsh.util.format.DateFormat;

public class SurgicalOperationCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	MedCase parentSSL = manager.find(MedCase.class, aParentId) ;
    	SurgicalOperationForm form=(SurgicalOperationForm)aForm;
    	
    	
    	if (parentSSL!=null && parentSSL instanceof HospitalMedCase) {
        	DiagnosisForm frm = getDiagnosis(aContext.getEntityManager(), parentSSL.getId(), "4", "1", true) ;
    		HospitalMedCase hosp = (HospitalMedCase) parentSSL ;
    		if (frm!=null) form.setIdc10Before(frm.getIdc10()) ;
    		if (hosp.getDateFinish()!=null && hosp.getDischargeTime()!=null) {
    			if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/SurOper/CreateInCloseMedCase")) {
    				throw new IllegalStateException("Нельзя добавить хирургическую операцию в закрытый случай стационарного лечения (ССЛ) !!!") ;
    			}
    		}
    		
    		if (hosp.getDepartment()!=null) form.setDepartment(hosp.getDepartment().getId()) ;
    		if (hosp.getServiceStream()!=null) form.setServiceStream(hosp.getServiceStream().getId()) ;
    	} else if (parentSSL!=null && parentSSL instanceof DepartmentMedCase){
    		DepartmentMedCase slo = (DepartmentMedCase) parentSSL ;
    		if (slo.getDepartment()!=null) form.setDepartment(slo.getDepartment().getId()) ;
    		if (slo.getServiceStream()!=null) form.setServiceStream(slo.getServiceStream().getId()) ;
    	} else  if (parentSSL!=null && parentSSL instanceof Visit){
    		Visit slo = (Visit) parentSSL ;
    		if (slo.getWorkFunctionExecute()!=null) {
    			if (slo.getWorkFunctionExecute() instanceof PersonalWorkFunction) {
    				PersonalWorkFunction pwf = (PersonalWorkFunction) slo.getWorkFunctionExecute() ;
    				form.setDepartment(pwf.getWorker().getLpu().getId()) ;
    				if (slo.getWorkFunctionExecute().getIsSurgical()!=null&&slo.getWorkFunctionExecute().getIsSurgical().booleanValue()) {
    					form.setSurgeon(slo.getWorkFunctionExecute().getId()) ;
    				}
    			}
    			form.setOperationDate(DateFormat.formatToDate(slo.getDateStart())) ;
    			form.setOperationTime(DateFormat.formatToTime(slo.getTimeExecute())) ;
    			
    		}
    		if (slo.getServiceStream()!=null) form.setServiceStream(slo.getServiceStream().getId()) ;
    	} else {
    		throw new IllegalStateException("Невозможно добавить хирургическую операцию. Сначала надо определить  случай стационарного лечения (ССЛ)") ;
    	}
    	
		if (parentSSL.getLpu()!=null) form.setLpu(parentSSL.getLpu().getId());
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction")
				&&form.getDepartment()!=null&&form.getDepartment()>Long.valueOf(0)) {
    		String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
        	List<Object[]> listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and w.lpu_id=:lpu and wf.id is not null and wf.isSurgical='1'") 
    				.setParameter("login", username)
    				.setParameter("lpu", form.getDepartment()) 
    				.setMaxResults(1)
    				.getResultList() ;
    		if (listwf.size()>0) {
    			Object[] wf = listwf.get(0) ;
    			form.setSurgeon(ConvertSql.parseLong(wf[0])) ;
    		} else {
            	listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and wf.id is not null and wf.isSurgical='1'") 
        				.setParameter("login", username)
        				
        				.setMaxResults(1)
        				.getResultList() ;
        		if (listwf.size()>0) {
        			Object[] wf = listwf.get(0) ;
        			form.setSurgeon(ConvertSql.parseLong(wf[0])) ;
        		}
    		}
    	}
    	
    }
    	private DiagnosisForm getDiagnosis(EntityManager aManager, Long aHospitalMedCase
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
    		
    		sql.append(" and vpd.code='").append(aPriority).append("'") ;
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
