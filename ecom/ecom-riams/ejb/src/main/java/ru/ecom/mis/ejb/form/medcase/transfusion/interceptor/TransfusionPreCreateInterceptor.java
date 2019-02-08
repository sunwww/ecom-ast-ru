package ru.ecom.mis.ejb.form.medcase.transfusion.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.transfusion.TransfusionForm;

import javax.persistence.EntityManager;
import java.util.List;

public class TransfusionPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	MedCase parentSSL = manager.find(MedCase.class, aParentId) ;
    	TransfusionForm form=(TransfusionForm)aForm;
    	
    	
    	if (parentSSL instanceof DepartmentMedCase) {
    		DepartmentMedCase slo = (DepartmentMedCase) parentSSL;
        	HospitalMedCase hosp = (HospitalMedCase)slo.getParent() ;
    		if (hosp.getDateFinish()!=null && hosp.getDischargeTime()!=null) {
    			if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/SurOper/CreateInCloseMedCase")) {
    				throw new IllegalStateException("Нельзя добавить переливание крови в закрытый случай стационарного лечения (ССЛ) !!!") ;
    			}
    		}
    		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction")) {
        		String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
            	List<Object[]> listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and w.lpu_id=:lpu and wf.id is not null and wf.isSurgical='1'") 
        				.setParameter("login", username)
        				.setParameter("lpu", slo.getDepartment().getId()) 
        				.setMaxResults(1)
        				.getResultList() ;
        		if (!listwf.isEmpty()) {
        			Object[] wf = listwf.get(0) ;
        			form.setExecutor(ConvertSql.parseLong(wf[0])) ;
        		} else {
                	listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and wf.id is not null and wf.isSurgical='1'") 
            				.setParameter("login", username)
            				
            				.setMaxResults(1)
            				.getResultList() ;
            		if (!listwf.isEmpty()) {
            			Object[] wf = listwf.get(0) ;
            			form.setExecutor(ConvertSql.parseLong(wf[0])) ;
            		}
        		}
        	}    		
    	} else if (parentSSL instanceof HospitalMedCase){
        	HospitalMedCase hosp = (HospitalMedCase) parentSSL ;
    		if (hosp.getDateFinish()!=null && hosp.getDischargeTime()!=null) {
    			if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/SurOper/CreateInCloseMedCase")) {
    				throw new IllegalStateException("Нельзя добавить переливание крови в закрытый случай стационарного лечения (ССЛ) !!!") ;
    			}
    		}
    		
    	} else {
    		throw new IllegalStateException("Невозможно добавить переливание. Сначала надо определить  случай стационарного лечения (ССЛ)") ;
    	}
    	
		
    	
    }
    	

}