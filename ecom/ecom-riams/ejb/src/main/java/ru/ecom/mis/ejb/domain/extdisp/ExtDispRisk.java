package ru.ecom.mis.ejb.domain.extdisp;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispRisk;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Выявленный при дополнительной диспансеризации риск возникновения заболевания
 */
@Comment("Выявленный при дополнительной диспансеризации риск возникновения заболевания")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="card")
    })
@Getter
@Setter
public class ExtDispRisk extends BaseEntity{
	/**
	 * Карта дополнительной диспансеризации
	 */
	@Comment("Карта дополнительной диспансеризации")
	@ManyToOne
	public ExtDispCard getCard() {
		return card;
	}
	/**
	 * Карта дополнительной диспансеризации
	 */
	private ExtDispCard card;
	/**
	 * Фактов риска заболевания при проведение дополнительной диспансеризации
	 */
	@Comment("Фактов риска заболевания при проведение дополнительной диспансеризации")
	@OneToOne
	public VocExtDispRisk getDispRisk() {
		return dispRisk;
	}
	/**
	 * Фактов риска заболевания при проведение дополнительной диспансеризации
	 */
	private VocExtDispRisk dispRisk;
}
