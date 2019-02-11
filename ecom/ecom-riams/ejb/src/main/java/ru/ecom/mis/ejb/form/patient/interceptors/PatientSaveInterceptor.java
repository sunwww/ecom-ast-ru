package ru.ecom.mis.ejb.form.patient.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.LpuAttachedByDepartmentForm;
import ru.ecom.mis.ejb.form.patient.MedPolicyOmcForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.util.StringUtil;

public class PatientSaveInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		PatientForm form = (PatientForm) aForm ;
		Patient patient = (Patient) aEntity ;
		patient.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		patient.setEditDate(new java.sql.Date(new java.util.Date().getTime())) ;

		//if(form.isAttachedByPolicy()) {
			//LpuAttachedByDepartment attached = new LpuAttachedByDepartment() ;
			//attached.setArea(aArea)
			if(form.getCreateNewOmcPolicy()) {
				// новый полис
				MedPolicyOmcForm polForm = form.getPolicyOmcForm() ;
				polForm.setSeries(polForm.getSeries().toUpperCase().trim());
				polForm.setPolNumber(polForm.getPolNumber().toUpperCase().trim());
				polForm.setPatient(patient.getId());
				polForm.setLastname(form.getLastname());
				polForm.setFirstname(form.getFirstname());
				polForm.setMiddlename(form.getMiddlename());
				try {
					long policyId = EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
						.create(polForm);
				//	MedPolicyOmc medPolicyOmc = aContext.getEntityManager().find(MedPolicyOmc.class, policyId) ;
					//patient.setAttachedOmcPolicy(medPolicyOmc);
					/*if(patient.getMedPolicies()!=null) {
						patient.getMedPolicies().add(medPolicyOmc);
					}*/
				} catch (Exception e) {
					throw new IllegalStateException(e.getMessage());
				}
			}
			if (form.getCreateAttachedByDepartment()) {
				LpuAttachedByDepartmentForm attForm = form.getAttachedForm() ;
				attForm.setPatient(patient.getId());
				try {
					long attId = EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
							.create(attForm);
					LpuAttachedByDepartment att = aContext.getEntityManager().find(LpuAttachedByDepartment.class, attId) ;
					patient.setLpu(att.getLpu()) ;
					patient.setLpuArea(att.getArea()) ;
				} catch (Exception e) {
					throw new IllegalStateException(e.getMessage());
				}
			}
			if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MisLpuDynamic/1/View") && StringUtil.isNullOrEmpty(form.getPhone())) { //Только для АМОКБ
				throw new IllegalStateException("Необходимо указать контактный телефон");
			}
		//if (patient.getI==null) {
		
			// прикреплен по адресу
			/*try {
				patient.setAddress(aContext.getEntityManager().find(Address.class, form.getAddress())) ;
				patient.setHouseNumber(form.getHouseNumber());
				patient.setHouseBuilding(form.getHouseBuilding());
				patient.setFlatNumber(form.getFlatNumber());
				new PatientListener().onUpdate(patient) ;
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}*/
		//}
		//if (CAN_DEBUG)
		//	LOG.debug("intercept: form.getPolicyOmcForm().getSeries() = " + form.getPolicyOmcForm().getSeries());
	}
}
