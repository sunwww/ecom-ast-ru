package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractPermission;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractRulePeriod;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

	/**
	 * Договорное правило
	 */
	@Comment("Договорное правило")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties = {"contract"})
	})
	@Getter
	@Setter
public class ContractRule extends BaseEntity{
	/**
	 * Договор
	 */
	@Comment("Договор")
	@ManyToOne
	public MedContract getContract() {
		return contract;
	}
	/**
	 * Договор
	 */
	private MedContract contract;
	/**
	 * Нозологическая группа
	 */
	@Comment("Нозологическая группа")
	@OneToOne
	public ContractNosologyGroup getNosologyGroup() {
		return nosologyGroup;
	}
	/**
	 * Нозологическая группа
	 */
	private ContractNosologyGroup nosologyGroup;
	/**
	 * Группа медицинских услуг
	 */
	@Comment("Группа медицинских услуг")
	@OneToOne
	public ContractMedServiceGroup getMedServiceGroup() {
		return medServiceGroup;
	}
	/**
	 * Группа медицинских услуг
	 */
	private ContractMedServiceGroup medServiceGroup;
	/**
	 * Группа гарантийных документов
	 */
	@Comment("Группа гарантийных документов")
	@OneToOne
	public ContractGuaranteeGroup getGuaranteeGroup() {
		return guaranteeGroup;
	}
	/**
	 * Группа гарантийных документов
	 */
	private ContractGuaranteeGroup guaranteeGroup;
	/**
	 * Период действия
	 */
	@Comment("Период действия")
	@OneToOne
	public VocContractRulePeriod getPeriod() {
		return period;
	}
	/**
	 * Период действия
	 */
	private VocContractRulePeriod period;
	/**
	 * Количество медицинских услуг
	 */
	private Integer medserviceAmount;
	/**
	 * Количество курсов
	 */
	private Integer courseAmount;
	/**
	 * Количество медицинских услуг на курс
	 */
	private Integer medserviceCourseAmount;
	/**
	 * Разрешение
	 */
	@Comment("Разрешение")
	@OneToOne
	public VocContractPermission getPermission() {
		return permission;
	}
	/**
	 * Разрешение
	 */
	private VocContractPermission permission;
	/**
	 * Дата начала действия
	 */
	private Date dateFrom;
	/**
	 * Дата окончания действия
	 */
	private Date dateTo;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@OneToOne
	public ServedPerson getServedPerson() {
		return servedPerson;
	}
	/**
	 * Обслуживаемая персона
	 */
	private ServedPerson servedPerson;
	/**
	 * Раздельный учет по каждому виду медицинских услуг
	 */
	private Boolean forEachMedservice;
	
	/** Название правила */
	private String name;
}
