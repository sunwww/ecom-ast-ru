package ru.ecom.mis.ejb.form.patient.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
//import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.service.worker.WorkerServiceBean;
import ru.nuzmsh.forms.response.FormMessage;
import sun.awt.windows.ThemeReader;

public class PatientViewInterceptor implements IFormInterceptor {

	private final static Logger LOG = Logger
			.getLogger(PatientViewInterceptor.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void intercept(IEntityForm aForm, Object aEntity,
			InterceptorContext aContext) {
		if (CAN_DEBUG)
			LOG.debug("intercept: aForm = " + aForm); 

		Patient pat = (Patient) aEntity;
		PatientForm form = (PatientForm) aForm ;
		if(pat!=null && pat.getAttachedOmcPolicy()!=null) {
			form.setAttachedByPolicy(true);
		}
		if(aContext.getSessionContext().isCallerInRole("/Policy/Mis/MisLpu/Psychiatry")){
			String sql = "select id,patient_id from PsychiatricCareCard where patient_id="+form.getId()+" order by id desc" ;
			List<Object[]> list = aContext.getEntityManager().createNativeQuery(sql)
					.setMaxResults(1).getResultList() ;
			Long med = list.size()>0?WorkerServiceBean.parseLong(list.get(0)[0]):Long.valueOf(0) ;
			form.setCareCard(med) ;
		}
		String sql = "select id,person_id from medcard where person_id="+form.getId()+" order by id desc" ;
		List<Object[]> list = aContext.getEntityManager().createNativeQuery(sql)
				.setMaxResults(1).getResultList() ;
		Long med = list.size()>0?WorkerServiceBean.parseLong(list.get(0)[0]):Long.valueOf(0) ;
		form.setMedcardLast(med) ;
		/*
		try {
			//Date date = new Date() ;
			Object[] polerr = (Object[]) aManager
			.createNativeQuery("SELECT TOP 1 " 
			                              +"$$CheckPatientOMC^ZMedPolicy('"+ form.getId()+"'),id " 
			                              +"FROM vocSex")
				//.setParameter("patid", form.getId())
				.getSingleResult();
			//form.setNotice(form.getNotice()+form.getId() +polerr[0]+"----"+polerr.length+polerr.toString()) ;
			if(polerr[0]!=null) form.addMessage(new FormMessage(""+polerr[0]));
		
		} catch(Exception e){
			
		}*/
		// AND cast("_Date_" as integer) between cast(actualDateFrom as integer) and cast(actualDateTo as integer)
		
	}
}
