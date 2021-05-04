package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
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
@Setter
public class ContractAccount20MedServiceForm extends ContractAccountMedServiceForm {

	/**
	 * Мед. Услуги
	 */
	@Comment("Мед. Услуги")
	public Long getMedService1() {return medService1;}

	/**
	 * Мед. Услуги
	 */
	private Long medService1;
	
	/** Цена 1 */
	@Comment("Цена 1")
	public String getCost1() {
		return cost1;
	}


	/** Цена 1 */
	private String cost1;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost2() {
		return cost2;
	}
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	private String cost2;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost3() {
		return cost3;
	}
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	private String cost3;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost4() {
		return cost4;
	}
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	private String cost4;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost5() {
		return cost5;
	}
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	private String cost5;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost6() {
		return cost6;
	}
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	private String cost6;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost7() {
		return cost7;
	}
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	private String cost7;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService1() {
		return countMedService1;
	}
	/**
	 * Новое свойство
	 */
	/**
	 * Новое свойство
	 */
	private Integer countMedService1;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService2() {
		return countMedService2;
	}
	/**
	 * Новое свойство
	 */
	private Integer countMedService2;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService3() {
		return countMedService3;
	}
	/**
	 * Новое свойство
	 */
	private Integer countMedService3;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService4() {
		return countMedService4;
	}
	/**
	 * Новое свойство
	 */
	private Integer countMedService4;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService5() {
		return countMedService5;
	}
	/**
	 * Новое свойство
	 */
	private Integer countMedService5;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService6() {
		return countMedService6;
	}
	/**
	 * Новое свойство
	 */
	private Integer countMedService6;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService7() {
		return countMedService7;
	}
	/**
	 * Новое свойство
	 */
	private Integer countMedService7;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public Integer getCountMedService8() {
		return countMedService8;
	}
	/**
	 * Новое свойство
	 */
	private Integer countMedService8;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	public String getCost8() {
		return cost8;
	}
	/**
	 * Новое свойство
	 */
	private String cost8;
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
	
}
