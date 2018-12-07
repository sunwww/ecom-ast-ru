package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ContractAccountMedService.class)
@Comment("Медицинские услуги")
@WebTrail(comment = "Медицинские услуги", nameProperties= "id", list="entityParentList-contract_contractAccountMedService.do", view="entityParentView-contract_contractAccountMedService.do")
@Parent(property="account", parentForm=ContractAccountForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService")
public class ContractAccount20MedServiceForm extends ContractAccountMedServiceForm {

	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	public Long getMedService1() {return theMedService1;}
	public void setMedService1(Long aMedService) {theMedService1 = aMedService;}

	/**
	 * Мед. Услуги
	 */
	private Long theMedService1;
	
	/** Цена 1 */
	@Comment("Цена 1")
	public String getCost1() {
		return theCost1;
	}

	public void setCost1(String aCost1) {
		theCost1 = aCost1;
	}

	/** Цена 1 */
	private String theCost1;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost2() {
		return theCost2;
	}
	/**
	 * Новое свойство
	 */
	public void setCost2(String a_Property) {
		theCost2 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private String theCost2;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost3() {
		return theCost3;
	}
	/**
	 * Новое свойство
	 */
	public void setCost3(String a_Property) {
		theCost3 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private String theCost3;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost4() {
		return theCost4;
	}
	/**
	 * Новое свойство
	 */
	public void setCost4(String a_Property) {
		theCost4 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private String theCost4;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost5() {
		return theCost5;
	}
	/**
	 * Новое свойство
	 */
	public void setCost5(String a_Property) {
		theCost5 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private String theCost5;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost6() {
		return theCost6;
	}
	/**
	 * Новое свойство
	 */
	public void setCost6(String a_Property) {
		theCost6 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private String theCost6;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost7() {
		return theCost7;
	}
	/**
	 * Новое свойство
	 */
	public void setCost7(String a_Property) {
		theCost7 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private String theCost7;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService1() {
		return theCountMedService1;
	}
	/**
	 * Новое свойство
	 */
	public void setCountMedService1(Integer a_Property) {
		theCountMedService1 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theCountMedService1;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService2() {
		return theCountMedService2;
	}
	/**
	 * Новое свойство
	 */
	public void setCountMedService2(Integer a_Property) {
		theCountMedService2 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theCountMedService2;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService3() {
		return theCountMedService3;
	}
	/**
	 * Новое свойство
	 */
	public void setCountMedService3(Integer a_Property) {
		theCountMedService3 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theCountMedService3;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService4() {
		return theCountMedService4;
	}
	/**
	 * Новое свойство
	 */
	public void setCountMedService4(Integer a_Property) {
		theCountMedService4 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theCountMedService4;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService5() {
		return theCountMedService5;
	}
	/**
	 * Новое свойство
	 */
	public void setCountMedService5(Integer a_Property) {
		theCountMedService5 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theCountMedService5;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService6() {
		return theCountMedService6;
	}
	/**
	 * Новое свойство
	 */
	public void setCountMedService6(Integer a_Property) {
		theCountMedService6 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theCountMedService6;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService7() {
		return theCountMedService7;
	}
	/**
	 * Новое свойство
	 */
	public void setCountMedService7(Integer a_Property) {
		theCountMedService7 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theCountMedService7;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService8() {
		return theCountMedService8;
	}
	/**
	 * Новое свойство
	 */
	public void setCountMedService8(Integer a_Property) {
		theCountMedService8 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theCountMedService8;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost8() {
		return theCost8;
	}
	/**
	 * Новое свойство
	 */
	public void setCost8(String a_Property) {
		theCost8 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private String theCost8;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService2() {
		return theMedService2;
	}
	/**
	 * Новое свойство
	 */
	public void setMedService2(Long a_Property) {
		theMedService2 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Long theMedService2;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService3() {
		return theMedService3;
	}
	/**
	 * Новое свойство
	 */
	public void setMedService3(Long a_Property) {
		theMedService3 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Long theMedService3;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService4() {
		return theMedService4;
	}
	/**
	 * Новое свойство
	 */
	public void setMedService4(Long a_Property) {
		theMedService4 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Long theMedService4;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService5() {
		return theMedService5;
	}
	/**
	 * Новое свойство
	 */
	public void setMedService5(Long a_Property) {
		theMedService5 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Long theMedService5;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService6() {
		return theMedService6;
	}
	/**
	 * Новое свойство
	 */
	public void setMedService6(Long a_Property) {
		theMedService6 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Long theMedService6;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService7() {
		return theMedService7;
	}
	/**
	 * Новое свойство
	 */
	public void setMedService7(Long a_Property) {
		theMedService7 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Long theMedService7;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService8() {
		return theMedService8;
	}
	/**
	 * Новое свойство
	 */
	public void setMedService8(Long a_Property) {
		theMedService8 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Long theMedService8;
	
	/** Дополнительные медицинские услуги */
	@Comment("Дополнительные медицинские услуги")
	public String getAdditionMedService() {
		return theAdditionMedService;
	}

	public void setAdditionMedService(String aAdditionMedService) {
		theAdditionMedService = aAdditionMedService;
	}

	/** Дополнительные медицинские услуги */
	private String theAdditionMedService;
	
}
