package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.validators.Required;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * Интервал медицинских услуг (A01.01.001-A01.01.005, A01.001.
	 */
	@Comment("Интервал медицинских услуг (A01.01.001-A01.01.005, A01.001.")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties = {"medServiceGroup"})

	})
	@Getter
	@Setter
public class MedServiceInterval extends BaseEntity{
	/**
	 * Маска кодов мед. услуг
	 */
	private String medServiceMask;
	/**
	 * Группа медицинских услуг
	 */
	@Comment("Группа медицинских услуг")
	@ManyToOne
	public ContractMedServiceGroup getMedServiceGroup() {
		return medServiceGroup;
	}
	/**
	 * Группа медицинских услуг
	 */
	private ContractMedServiceGroup medServiceGroup;
	/**
	 * Название
	 */
	private String name;
	/**
	 * Начиная с кода медицинской услуги
	 */
	private String fromMedServiceCode;
	/**
	 * Заканчивая кодом медицинской услуги
	 */
	private String toMedServiceCode;

	/**
	 * Начиная с кода
	 */
	@Comment("Начиная с кода")
	@OneToOne
	public MedService getFromCode() {
		return fromCode;
	}
	/**
	 * Начиная с кода
	 */
	private MedService fromCode;
	/**
	 * Заканчивая кодом
	 */
	@Comment("Заканчивая кодом")
	@OneToOne
	public MedService getToCode() {
		return toCode;
	}
	/**
	 * Заканчивая кодом
	 */
	private MedService toCode;	
}
