package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.*;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

public class SurgicalOperationCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	MedCase parentSSL = manager.find(MedCase.class, aParentId) ;
    	SurgicalOperationForm form=(SurgicalOperationForm)aForm;

		if (parentSSL instanceof DepartmentMedCase){
			DepartmentMedCase slo = (DepartmentMedCase) parentSSL ;

			if (slo.getDepartment()!=null) form.setDepartment(slo.getDepartment().getId()) ;
			if (slo.getServiceStream()!=null) form.setServiceStream(slo.getServiceStream().getId()) ;
		}
		 else if (parentSSL instanceof HospitalMedCase) {
			DiagnosisForm frm = getDiagnosis(aContext.getEntityManager(), parentSSL.getId(), "4", "1", true) ;
			HospitalMedCase hosp = (HospitalMedCase) parentSSL ;
			if (frm!=null) form.setIdc10Before(frm.getIdc10()) ;
    		/*if (hosp.getDateFinish()!=null && hosp.getDischargeTime()!=null && !aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/SurOper/CreateInCloseMedCase")) {
    			throw new IllegalStateException("Нельзя добавить хирургическую операцию в закрытый случай стационарного лечения (ССЛ) !!!") ;
    		}*/

			if (hosp.getDepartment()!=null) form.setDepartment(hosp.getDepartment().getId()) ;
			if (hosp.getServiceStream()!=null) form.setServiceStream(hosp.getServiceStream().getId()) ;
		} else if (parentSSL instanceof Visit){
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
    	
		if (parentSSL.getDepartment()!=null) {
				List<Object[]> l = aContext.getEntityManager().createNativeQuery("select vlaeo.id from mislpu ml left join VocLpuAccessEnterOperation vlaeo on vlaeo.id=ml.AccessEnterOperation_id where ml.id='"
							+parentSSL.getDepartment().getId()+"' and (vlaeo.code='DENIED_IN_DEPARTMENT' or vlaeo.code='ALL_DEPARTMENT')").getResultList() ;
				if (!l.isEmpty()) throw new IllegalStateException("Нельзя добавить хирургическую операцию по текущему отделению!!!") ;
		}
		if (parentSSL.getLpu()!=null) {	
			form.setLpu(parentSSL.getLpu().getId());
		}
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction")
				&&form.getDepartment()!=null&&form.getDepartment()>Long.valueOf(0)) {
    		String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
        	List<Object[]> listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and w.lpu_id=:lpu and wf.id is not null and wf.isSurgical='1'") 
    				.setParameter("login", username)
    				.setParameter("lpu", form.getDepartment()) 
    				.setMaxResults(1)
    				.getResultList() ;
    		if (!listwf.isEmpty()) {
    			Object[] wf = listwf.get(0) ;
    			form.setSurgeon(ConvertSql.parseLong(wf[0])) ;
    		} else {
            	listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and wf.id is not null and wf.isSurgical='1'") 
        				.setParameter("login", username)
        				
        				.setMaxResults(1)
        				.getResultList() ;
        		if (!listwf.isEmpty()) {
        			Object[] wf = listwf.get(0) ;
        			form.setSurgeon(ConvertSql.parseLong(wf[0])) ;
        		}
    		}
    	}
		//Запрет на создание в СЛО и СЛС, если случай закрыт. Админ может создавать.
		if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut") &&
				!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/SurOper/CreateInCloseMedCase") &&
				parentSSL instanceof HospitalMedCase) {
			MedCase hmc = (parentSSL instanceof DepartmentMedCase)? parentSSL.getParent() : parentSSL;
			if (hmc.getDateFinish()!=null) throw new IllegalStateException("Пациент выписан. Нельзя добавлять хирургическую операцию в закрытый СЛС!");
		}
    }

    /** находим правильного родителя при создании операции (по дате и времени) */
    public static void setParentByOperation(SurgicalOperation aOperation, EntityManager aManager)  {
    	//return null;
		StringBuilder sql = new StringBuilder();
		MedCase medCase = aOperation.getMedCase();
		Long hospitalId ;
		sql.append("select slo.id from medcase slo where slo.parent_id=:id and slo.dtype='DepartmentMedCase'")
			.append(" and cast(:operationDateTime as timestamp) between cast(slo.datestart||' '||slo.entrancetime as timestamp) and coalesce(cast(coalesce(slo.transferdate||' '||slo.transfertime,slo.datefinish||' '||slo.dischargetime) as timestamp),current_timestamp)");
		if (medCase instanceof DepartmentMedCase) {
			hospitalId=medCase.getParent().getId();
		} else if (medCase instanceof HospitalMedCase) {
			hospitalId=medCase.getId();
		} else {
			return;
		}

		String dateTime = aOperation.getOperationDate()+" "+aOperation.getOperationTime();
		List<BigInteger> ids = aManager.createNativeQuery(sql.toString()).setParameter("id",hospitalId).setParameter("operationDateTime",dateTime).getResultList();
		if (ids.isEmpty()||ids.size()>1) {
			return;
		}
		aOperation.setMedCase(aManager.find(MedCase.class,ids.get(0).longValue()));
		aManager.persist(aOperation);

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
    		List<Object[]> listDiag = aManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
    		if (!listDiag.isEmpty()) {
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
