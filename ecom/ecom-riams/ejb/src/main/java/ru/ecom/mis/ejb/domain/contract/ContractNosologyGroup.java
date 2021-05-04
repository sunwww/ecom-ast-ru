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
	 * Нозологическая группа по договору
	 */
	@Comment("Нозологическая группа по договору")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties = {"name"})
	})
	@Getter
	@Setter
public class ContractNosologyGroup extends BaseEntity{
	@OneToMany(mappedBy="nosologyGroup", cascade=CascadeType.ALL)
	public List<NosologyInterval> getIntervals() {
		return intervals;
	}
	/**
	 * Нозологические интервалы
	 */
	private List<NosologyInterval> intervals;
	/**
	 * Название
	 */
	private String name;
}
