package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.patient.MedPolicyOmcForeign;
import ru.ecom.mis.ejb.form.patient.interceptors.MedPolicyPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Полис ДМС
 */
@EntityForm
@EntityFormPersistance(clazz = MedPolicyOmcForeign.class)
@Comment("Полис ОМС иногороднего")
@WebTrail(comment = "Полис ОМС иногороднего", nameProperties = "polNumber", view = "entityView-mis_medPolicyForeign.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedPolicy/OmcForeign")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedPolicyPreCreate.class)
)
public class MedPolicyOmcForeignForm extends MedPolicyForm {
	/** Город СМО */
	@Comment("Город СМО")
	@Persist @DoUpperCase
	public String getInsuranceCompanyCity() {return theInsuranceCompanyCity;}
	public void setInsuranceCompanyCity(String aOMCCity) {theInsuranceCompanyCity = aOMCCity;}

	/** Название СМО */
	@Comment("Название СМО")
	@Persist @DoUpperCase
	public String getInsuranceCompanyName() {return theInsuranceCompanyName;}
	public void setInsuranceCompanyName(String aOMCName) {theInsuranceCompanyName = aOMCName;}

	/** Область нахождения СМО */
	@Comment("Область нахождения СМО")
	@Persist @Required
	public Long getInsuranceCompanyArea() {return theInsuranceCompanyArea;}
	public void setInsuranceCompanyArea(Long aInsuranceCompanyArea) {
		theInsuranceCompanyArea = aInsuranceCompanyArea;
	}

	/** ОГРН СМО, выдавшей паспорт */
	@Comment("ОГРН СМО, выдавшей паспорт")
	@Persist 
	public Long getInsuranceCompanyCode() {return theInsuranceCompanyCode;}
	public void setInsuranceCompanyCode(Long aInsuranceCompanyOgrn) {theInsuranceCompanyCode = aInsuranceCompanyOgrn;}

	/** Тип полиса */
	@Comment("Тип полиса")
	@Persist @Required
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}
	
	/** Тип полиса */
	private Long theType;
	/** ОГРН СМО, выдавшей паспорт */
	private Long theInsuranceCompanyCode;
	/** Область нахождения СМО */
	private Long theInsuranceCompanyArea;
	/** Название СМО */
	private String theInsuranceCompanyName;
	/** Город СМО */
	private String theInsuranceCompanyCity;
	
	/** ОГРН */
	@Comment("ОГРН")
	@Persist
	public String getOgrn() {return theOgrn;}
	public void setOgrn(String aOgrn) {theOgrn = aOgrn;}

	/** ОГРН */
	private String theOgrn;

}
