package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
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
@Setter
public class MedPolicyOmcForeignForm extends MedPolicyForm {
	/** Город СМО */
	@Comment("Город СМО")
	@Persist @DoUpperCase
	public String getInsuranceCompanyCity() {return insuranceCompanyCity;}

	/** Название СМО */
	@Comment("Название СМО")
	@Persist @DoUpperCase
	public String getInsuranceCompanyName() {return insuranceCompanyName;}

	/** Область нахождения СМО */
	@Comment("Область нахождения СМО")
	@Persist @Required
	public Long getInsuranceCompanyArea() {return insuranceCompanyArea;}

	/** ОГРН СМО, выдавшей паспорт */
	@Comment("ОГРН СМО, выдавшей паспорт")
	@Persist 
	public Long getInsuranceCompanyCode() {return insuranceCompanyCode;}

	/** Тип полиса */
	@Comment("Тип полиса")
	@Persist @Required
	public Long getType() {return type;}

	/** Тип полиса */
	private Long type;
	/** ОГРН СМО, выдавшей паспорт */
	private Long insuranceCompanyCode;
	/** Область нахождения СМО */
	private Long insuranceCompanyArea;
	/** Название СМО */
	private String insuranceCompanyName;
	/** Город СМО */
	private String insuranceCompanyCity;
	
	/** ОГРН */
	@Comment("ОГРН")
	@Persist
	public String getOgrn() {return ogrn;}

	/** ОГРН */
	private String ogrn;

}
