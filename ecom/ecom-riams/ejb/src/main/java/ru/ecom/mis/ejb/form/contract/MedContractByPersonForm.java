package ru.ecom.mis.ejb.form.contract;

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
public class MedContractByPersonForm extends MedContractForm {
	/** Признак удаленной записи */
	@Comment("Признак удаленной записи")
	@Persist
	public Boolean getIsDeleted() {return theIsDeleted;}
	public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
	/** Признак удаленной записи */
	private Boolean theIsDeleted ;

	/** Обслуживаемая персона */
	@Comment("Обслуживаемая персона")
	@Required
	public Long getServedPerson() {return theServedPerson;}
	public void setServedPerson(Long aServedPerson) {theServedPerson = aServedPerson;}
	
	/** Создавать счет */
	@Comment("Создавать счет")
	public Boolean getAutoAccount() {return theAutoAccount;}
	public void setAutoAccount(Boolean aAutoAccount) {theAutoAccount = aAutoAccount;}

	/** Список услуг */
	@Comment("Список услуг")
	public String getPriceMedServicies() {return thePriceMedServicies;}
	public void setPriceMedServicies(String aPriceMedServicies) {thePriceMedServicies = aPriceMedServicies;}

	/** Услуг */
	@Comment("Услуг")
	public Long getPriceMedService() {return thePriceMedService;}
	public void setPriceMedService(Long aPriceMedService) {thePriceMedService = aPriceMedService;}

	
	/** Скидка по умолчанию */
	@Comment("Скидка по умолчанию")
	public String getDiscountDefault() {return theDiscountDefault;}
	public void setDiscountDefault(String aDiscountDefault) {theDiscountDefault = aDiscountDefault;}

	/** Скидка по умолчанию */
	private String theDiscountDefault;
	/** Услуг */
	private Long thePriceMedService;
	/** Список услуг */
	private String thePriceMedServicies;
	/** Создавать счет */
	private Boolean theAutoAccount;
	/** Обслуживаемая персона */
	private Long theServedPerson;
}
