package ru.ecom.mis.ejb.domain.extdisp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispRisk;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Выявленный при дополнительной диспансеризации риск возникновения заболевания
 */
@Comment("Выявленный при дополнительной диспансеризации риск возникновения заболевания")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="card")
    }) 
public class ExtDispRisk extends BaseEntity{
	/**
	 * Карта дополнительной диспансеризации
	 */
	@Comment("Карта дополнительной диспансеризации")
	@ManyToOne
	public ExtDispCard getCard() {
		return theCard;
	}
	public void setCard(ExtDispCard aCard) {
		theCard = aCard;
	}
	/**
	 * Карта дополнительной диспансеризации
	 */
	private ExtDispCard theCard;
	/**
	 * Фактов риска заболевания при проведение дополнительной диспансеризации
	 */
	@Comment("Фактов риска заболевания при проведение дополнительной диспансеризации")
	@OneToOne
	public VocExtDispRisk getDispRisk() {
		return theDispRisk;
	}
	public void setDispRisk(VocExtDispRisk aDispRisk) {
		theDispRisk = aDispRisk;
	}
	/**
	 * Фактов риска заболевания при проведение дополнительной диспансеризации
	 */
	private VocExtDispRisk theDispRisk;
}
