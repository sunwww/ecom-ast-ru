package ru.ecom.mis.ejb.form.patient.interceptors;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.util.date.AgeUtil;

public class PatientViewInterceptor implements IFormInterceptor {

	private final static Logger LOG = Logger
			.getLogger(PatientViewInterceptor.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void intercept(IEntityForm aForm, Object aEntity,
			InterceptorContext aContext) {

		Patient pat = (Patient) aEntity;
		PatientForm form = (PatientForm) aForm ;
		if(pat!=null && pat.getAttachedOmcPolicy()!=null) {
			form.setAttachedByPolicy(true);
		}
		if(aContext.getSessionContext().isCallerInRole("/Policy/Mis/MisLpu/Psychiatry")){
			String sql = "select id from PsychiatricCareCard where patient_id="+form.getId()+" order by id desc" ;
			List<Object> list = aContext.getEntityManager().createNativeQuery(sql)
					.setMaxResults(1).getResultList() ;
			Long med = list.size()>0?parseLong(list.get(0)):Long.valueOf(0) ;
			form.setCareCard(med) ;
		}
		String sql = "select id from medcard where person_id="+form.getId()+" order by id desc" ;
		List<Object> list = aContext.getEntityManager().createNativeQuery(sql)
				.setMaxResults(1).getResultList() ;
		Long med = list.size()>0?parseLong(list.get(0)):Long.valueOf(0) ;
		form.setMedcardLast(med) ;
		//java.util.Date current = new java.util.Date();
		if (!form.getBirthday().equals("")) {
			String age = AgeUtil.getAgeCache(new java.util.Date(),pat.getBirthday(), 2) ;
			form.setAge(age) ;
		}
		
		//
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
	private Long parseLong(Object aValue) {
		Long ret =null;
		if (aValue==null) return ret ;
		if (aValue instanceof Integer) {
			return Long.valueOf((Integer) aValue) ;
		}
		if(aValue instanceof BigInteger) {
			BigInteger bigint = (BigInteger) aValue ;
			
			return bigint!=null?bigint.longValue() : null;
		} 
		if (aValue instanceof Number) {
			Number number = (Number) aValue ;
			return number!=null?number.longValue() : null ;
		}
		if (aValue instanceof String) {
			return Long.valueOf((String) aValue);
		}
		return ret ;
	}

}
