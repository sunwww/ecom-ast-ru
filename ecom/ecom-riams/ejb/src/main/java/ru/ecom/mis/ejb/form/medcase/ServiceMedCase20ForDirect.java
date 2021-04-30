package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
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
@Setter
public class ServiceMedCase20ForDirect extends IdEntityForm {

	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	@Persist @Required
	public Long getMedService() {return medService;}

	/**
	 * Мед. Услуги
	 */
	private Long medService;
	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	public Long getMedService1() {return medService1;}

	/**
	 * Мед. Услуги
	 */
	private Long medService1;
	

	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	@Persist @Required
	public Integer getMedServiceAmount() {
		return medServiceAmount;
	}
	/**
	 * Новое свойство
	 */
	private Integer medServiceAmount;
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount1() {
		return medServiceAmount1;
	}
	/**
	 * Новое свойство
	 */
	private Integer medServiceAmount1;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount2() {
		return medServiceAmount2;
	}
	private Integer medServiceAmount2;
	@Comment("Новое свойство")
	public Integer getMedServiceAmount3() {
		return medServiceAmount3;
	}
	/**
	 * Новое свойство
	 */
	private Integer medServiceAmount3;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount4() {
		return medServiceAmount4;
	}
	/**
	 * Новое свойство
	 */
	private Integer medServiceAmount4;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount5() {
		return medServiceAmount5;
	}
	/**
	 * Новое свойство
	 */
	private Integer medServiceAmount5;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount6() {
		return medServiceAmount6;
	}
	/**
	 * Новое свойство
	 */
	private Integer medServiceAmount6;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount7() {
		return medServiceAmount7;
	}
	/**
	 * Новое свойство
	 */
	private Integer medServiceAmount7;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getMedServiceAmount8() {
		return medServiceAmount8;
	}
	/**
	 * Новое свойство
	 */
	private Integer medServiceAmount8;

	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService2() {
		return medService2;
	}
	/**
	 * Новое свойство
	 */
	private Long medService2;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService3() {
		return medService3;
	}
	/**
	 * Новое свойство
	 */
	private Long medService3;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService4() {
		return medService4;
	}
	/**
	 * Новое свойство
	 */
	private Long medService4;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService5() {
		return medService5;
	}
	/**
	 * Новое свойство
	 */
	private Long medService5;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService6() {
		return medService6;
	}
	/**
	 * Новое свойство
	 */
	private Long medService6;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService7() {
		return medService7;
	}
	/**
	 * Новое свойство
	 */
	private Long medService7;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Long getMedService8() {
		return medService8;
	}
	/**
	 * Новое свойство
	 */
	private Long medService8;
	
	/** Дополнительные медицинские услуги */
	@Comment("Дополнительные медицинские услуги")
	public String getAdditionMedService() {
		return additionMedService;
	}

	/** Дополнительные медицинские услуги */
	private String additionMedService;
	
	/** Направление */
	@Comment("Направление")
	@Persist
	public Long getParent() {
		return parent;
	}

	/** Направление */
	private Long parent;

}
