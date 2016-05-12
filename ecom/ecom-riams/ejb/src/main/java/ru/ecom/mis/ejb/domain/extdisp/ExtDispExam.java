package ru.ecom.mis.ejb.domain.extdisp;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Исследование по дополнительной диспансеризации
	 */
	@Comment("Исследование по дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
public class ExtDispExam extends ExtDispService{
	/**
	 * Выявлена патология
	 */
	@Comment("Выявлена патология")
	
	public Boolean getIsPathology() {
		return theIsPathology;
	}
	public void setIsPathology(Boolean aIsPathology) {
		theIsPathology = aIsPathology;
	}
	/**
	 * Выявлена патология
	 */
	private Boolean theIsPathology;
}
