package ru.ecom.mis.ejb.domain.contract;

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
		@AIndex(unique= false, properties = {"name"})
	})
public class ContractNosologyGroup extends BaseEntity{
	@OneToMany(mappedBy="nosologyGroup", cascade=CascadeType.ALL)
	public List<NosologyInterval> getIntervals() {
		return theIntervals;
	}
	public void setIntervals(List<NosologyInterval> aIntervals) {
		theIntervals = aIntervals;
	}
	/**
	 * Нозологические интервалы
	 */
	private List<NosologyInterval> theIntervals;
	/**
	 * Название
	 */
	@Comment("Название")
	
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
}
