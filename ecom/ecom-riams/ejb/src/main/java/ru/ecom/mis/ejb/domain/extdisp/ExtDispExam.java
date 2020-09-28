package ru.ecom.mis.ejb.domain.extdisp;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

	/**
	 * Исследование по дополнительной диспансеризации
	 */
	@Comment("Исследование по дополнительной диспансеризации")
@Entity
public class ExtDispExam extends ExtDispService {
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
	private Boolean theIsPathology;
}
