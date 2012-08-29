package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.form.medcase.poly.DirectionMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ServiceMedCase.class)
@Comment("Медицинские услуги")
@WebTrail(comment = "Медицинские услуги", nameProperties= "id", list="entityParentList-smo_direction_medservice.do", view="entityParentView-smo_direction_medservice.do")
@Parent(property="parent", parentForm=DirectionMedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Direction/MedService")
public class ServiceMedCase20ForDirect extends IdEntityForm {

	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	@Persist @Required
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}

	/**
	 * Мед. Услуги
	 */
	private Long theMedService;
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
	

	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	@Persist @Required
	public Integer getMedServiceAmount() {
		return theMedServiceAmount;
	}
	/**
	 * Новое свойство
	 */
	public void setMedServiceAmount(Integer a_Property) {
		theMedServiceAmount = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount;
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount1() {
		return theMedServiceAmount1;
	}
	public void setMedServiceAmount1(Integer a_Property) {
		theMedServiceAmount1 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount1;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount2() {
		return theMedServiceAmount2;
	}
	/**
	 * Новое свойство
	 */
	public void setMedServiceAmount2(Integer a_Property) {
		theMedServiceAmount2 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount2;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount3() {
		return theMedServiceAmount3;
	}
	/**
	 * Новое свойство
	 */
	public void setMedServiceAmount3(Integer a_Property) {
		theMedServiceAmount3 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount3;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount4() {
		return theMedServiceAmount4;
	}
	/**
	 * Новое свойство
	 */
	public void setMedServiceAmount4(Integer a_Property) {
		theMedServiceAmount4 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount4;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount5() {
		return theMedServiceAmount5;
	}
	/**
	 * Новое свойство
	 */
	public void setMedServiceAmount5(Integer a_Property) {
		theMedServiceAmount5 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount5;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount6() {
		return theMedServiceAmount6;
	}
	/**
	 * Новое свойство
	 */
	public void setMedServiceAmount6(Integer a_Property) {
		theMedServiceAmount6 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount6;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount7() {
		return theMedServiceAmount7;
	}
	/**
	 * Новое свойство
	 */
	public void setMedServiceAmount7(Integer a_Property) {
		theMedServiceAmount7 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount7;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount8() {
		return theMedServiceAmount8;
	}
	/**
	 * Новое свойство
	 */
	public void setMedServiceAmount8(Integer a_Property) {
		theMedServiceAmount8 = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Integer theMedServiceAmount8;

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
	
	/** Направление */
	@Comment("Направление")
	@Persist
	public Long getParent() {
		return theParent;
	}

	public void setParent(Long aParent) {
		theParent = aParent;
	}

	/** Направление */
	private Long theParent;

}
