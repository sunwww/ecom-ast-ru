package ru.ecom.mis.ejb.form.patient.interceptors;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientExternalServiceAccount;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.util.date.AgeUtil;

import javax.persistence.EntityManager;

public class PatientViewInterceptor implements IFormInterceptor {

	private final static Logger LOG = Logger
			.getLogger(PatientViewInterceptor.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void intercept(IEntityForm aForm, Object aEntity,
			InterceptorContext aContext) {

		Patient pat = (Patient) aEntity;
		PatientForm form = (PatientForm) aForm ;
		EntityManager manager = aContext.getEntityManager();
		/*if(pat!=null ){
			if (pat.getAttachedOmcPolicy()!=null) {
				form.setAttachedByPolicy(true);
			}
		}*/
		if (form.getBirthday()!=null && !form.getBirthday().equals("")) {
			String age = AgeUtil.getAgeCache(new java.util.Date(),pat.getBirthday(), 2) ;
			form.setAge(age) ;
		}
		List<Object[]> pesas = manager.createNativeQuery("select id, phonenumber from PatientExternalServiceAccount where patient_id = "+pat.getId()+" and dateto is null and phonenumber is not null and phonenumber!=''").getResultList();
		if (!pesas.isEmpty()) {
			form.setMobileAppPhoneNumber(pesas.get(0)[1].toString());

		}
		
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/Patient/ViewInfoArea")) {
			List<Object[]> l = manager.createNativeQuery("select pat.id from patient pat  left join LpuAreaAddressText laat on laat.address_addressid=pat.address_addressid left join lpuarea la on la.id=laat.area_id left join LpuAreaAddressPoint laap on laap.lpuAreaAddressText_id=laat.id where pat.id="+form.getId()+" and la.isViewInfoPatient='1' and (laap.id is null or laap.houseNumber='' or laap.houseNumber is null or (laap.houseNumber=pat.houseNumber and (laap.houseBuilding is null or laap.HouseBuilding='' or laap.houseBuilding=pat.houseBuilding) ) )").getResultList() ;
			if (l.size()>0) {
				form.addMessage(new FormMessage("<font size='16'><b>Входит в список</b></font>"));
			}
		}
		
		
	}
	

}
