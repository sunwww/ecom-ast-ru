package ru.ecom.mis.ejb.domain.extdisp;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Медицинская услуга по дополнительной диспансеризации
	 */
	@Comment("Медицинская услуга по дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties="card")
	    }) 
public class ExtDispService extends BaseEntity{
	/**
	 * Карта дополнительной диспансеризации
	 */
	@Comment("Карта дополнительной диспансеризации")
	@ManyToOne
	public ExtDispCard getCard() {return theCard;}
	public void setCard(ExtDispCard aCard) {theCard = aCard;}
	/**
	 * Карта дополнительной диспансеризации
	 */
	private ExtDispCard theCard;
	/**
	 * Дата оказания услуги
	 */
	@Comment("Дата оказания услуги")
	public Date getServiceDate() {return theServiceDate;}
	public void setServiceDate(Date aServiceDate) {theServiceDate = aServiceDate;}
	/**
	 * Дата оказания услуги
	 */
	private Date theServiceDate;
	/**
	 * Тип услуги
	 */
	@Comment("Тип услуги")
	@OneToOne
	public VocExtDispService getServiceType() {return theServiceType;}
	public void setServiceType(VocExtDispService aServiceType) {theServiceType = aServiceType;}
	/**
	 * Тип услуги
	 */
	private VocExtDispService theServiceType;
}
