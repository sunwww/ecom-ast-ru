package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
	/**
	 * Группу медицинских услуг по договору
	 */
	@Comment("Группу медицинских услуг по договору")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties = {"name"})

	})
	@Getter
	@Setter
public class ContractMedServiceGroup extends BaseEntity{
	/**
	 * Название
	 */
	private String name;
	@OneToMany(mappedBy="medServiceGroup", cascade=CascadeType.ALL)
	public List<MedServiceInterval> getIntervals() {
		return intervals;
	}
	/**
	 * Интервалы медицинских услуг
	 */
	private List<MedServiceInterval> intervals;
}
