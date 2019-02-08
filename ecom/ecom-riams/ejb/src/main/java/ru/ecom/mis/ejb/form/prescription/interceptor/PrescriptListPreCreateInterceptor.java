package ru.ecom.mis.ejb.form.prescription.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.prescription.PrescriptListForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.List;

public class PrescriptListPreCreateInterceptor implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	PrescriptListForm form = (PrescriptListForm) aForm ;
    	MedCase parentSSL = manager.find(MedCase.class, aParentId) ;
    	
    	if (parentSSL!=null) {
    		String dateStart = DateFormat.formatToDate(parentSSL.getDateStart()) ;
    		
    		form.getDietForm().setPlanStartDate(dateStart) ;
    		form.getModeForm().setPlanStartDate(dateStart) ;
    		form.getDrugForm1().setPlanStartDate(dateStart) ;
    		form.getDrugForm2().setPlanStartDate(dateStart) ;
    		form.getDrugForm3().setPlanStartDate(dateStart) ;
    		form.getDrugForm4().setPlanStartDate(dateStart) ;
    		form.getDrugForm5().setPlanStartDate(dateStart) ;
    		form.getDrugForm6().setPlanStartDate(dateStart) ;
    		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction")
    				) {
        		String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
            	List<Object[]> listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and wf.id is not null") 
        				.setParameter("login", username)
        				//.setParameter("lpu", form.getDepartment()) 
        				.setMaxResults(1)
        				.getResultList() ;
        		if (!listwf.isEmpty()) {
        			Object[] wf = listwf.get(0) ;
        			form.setWorkFunction(ConvertSql.parseLong(wf[0])) ;
        		}
        	}
    	} else {
    		throw new IllegalStateException("Невозможно добавить случай лечения. Сначала надо определить случай медицинского обслуживания") ;
    	}
    }
}