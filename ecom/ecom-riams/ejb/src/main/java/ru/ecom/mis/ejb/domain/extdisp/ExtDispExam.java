package ru.ecom.mis.ejb.domain.extdisp;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

	/**
	 * Исследование по дополнительной диспансеризации
	 */
	@Comment("Исследование по дополнительной диспансеризации")
@Entity
	@Getter
	@Setter
public class ExtDispExam extends ExtDispService {
	/**
	 * Выявлена патология
	 */
	private Boolean isPathology;
}
