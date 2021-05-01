package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.form.contract.interceptor.MedContractByPersonPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = MedContract.class)
@Comment("Мед.договор")
@WebTrail(comment = "Мед.договор", nameProperties= "info", list="entityParentList-contract_medContract_person.do", 
	view="entityParentView-contract_medContract_person.do"
	, shortView="entityView-contract_medContract_person.do?short=Short")
@Parent(property="customer", parentForm=ContractPersonForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedContractByPersonPreCreateInterceptor.class)
)
@Setter
public class MedContractByPersonForm extends MedContractForm {
	/** Признак удаленной записи */
	@Comment("Признак удаленной записи")
	@Persist
	public Boolean getIsDeleted() {return isDeleted;}
	/** Признак удаленной записи */
	private Boolean isDeleted ;

	/** Обслуживаемая персона */
	@Comment("Обслуживаемая персона")
	@Required
	public Long getServedPerson() {return servedPerson;}
	
	/** Создавать счет */
	@Comment("Создавать счет")
	public Boolean getAutoAccount() {return autoAccount;}

	/** Список услуг */
	@Comment("Список услуг")
	public String getPriceMedServicies() {return priceMedServicies;}

	/** Услуг */
	@Comment("Услуг")
	public Long getPriceMedService() {return priceMedService;}

	
	/** Скидка по умолчанию */
	@Comment("Скидка по умолчанию")
	public String getDiscountDefault() {return discountDefault;}

	/** Скидка по умолчанию */
	private String discountDefault;
	/** Услуг */
	private Long priceMedService;
	/** Список услуг */
	private String priceMedServicies;
	/** Создавать счет */
	private Boolean autoAccount;
	/** Обслуживаемая персона */
	private Long servedPerson;
}
