package ru.ecom.mis.ejb.form.patient.interceptors;

import ru.ecom.ejb.services.entityform.interceptors.IDynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.lpu.interceptors.MisLpuDynamicSecurity;

/**
 * Если есть ЛПУ проверка на доступность
 * @author  esinev
 */
public class PatientDynamicSecurityInterceptor implements IDynamicSecurityInterceptor {

	public void check(String aPolicyAction, Object aId, InterceptorContext aContext) {
		
		/*
		if(!aContext.getSessionContext().isCallerInRole("/Policy/Mis/DisablePatientAttachedCheck")) {
			boolean canCheck = 
				("View".equals(aPolicyAction) && aContext.getTarget().equals(ElementType.METHOD))
				|| ("Edit".equals(aPolicyAction) && aContext.getTarget().equals(ElementType.TYPE));
	
			if(canCheck) {
				Patient person = aContext.getEntityManager().find(Patient.class, aId) ;
				if(person!=null && person instanceof Patient) {
				//	Patient patient = (Patient) person ;
				//	boolean okByDep = false ;
					/*
					if(!person.getAttachedByDepartments().isEmpty()) {
							for(LpuAttachedByDepartment at: person.getAttachedByDepartments()) {
								try {
									if(at.getLpu()!=null) {
										theMisLpuDynamicSecurity.check(aPolicyAction, at.getLpu().getId(), aContext) ;
									}
									okByDep = true ;
									break ;
								} catch (Exception e) {
									
								}
							}
							
							
					}
					if(!okByDep) {
						MisLpu lpu = patient.getAttachedOmcPolicy()!=null
							? patient.getAttachedOmcPolicy().getAttachedLpu() 
							: patient.getLpu() ;
						if(lpu!=null) theMisLpuDynamicSecurity.check(aPolicyAction, lpu.getId(), aContext) ;
						// FIXME если нет прикрепленного ЛПУ
						else throw new IllegalStateException("Не прикреплен") ;
					}*/

		//	}*/
	//	}
	}

	private final MisLpuDynamicSecurity theMisLpuDynamicSecurity = new MisLpuDynamicSecurity() ;
}
