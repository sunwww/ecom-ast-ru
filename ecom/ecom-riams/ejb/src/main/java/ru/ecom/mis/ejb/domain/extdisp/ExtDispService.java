package ru.ecom.mis.ejb.domain.extdisp;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Медицинская услуга по дополнительной диспансеризации
	 */
	@Comment("Медицинская услуга по дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties="card")
		,@AIndex(properties="workFunction")
		,@AIndex(properties="serviceType")
		,@AIndex(properties="idc10")
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
	
	/** Врач, оказавший услугу */
	@Comment("Врач, оказавший услугу")
	@OneToOne
	public WorkFunction	getWorkFunction() {
		return theWorkFunction;
	}

	public void setWorkFunction(WorkFunction aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}

	/** Врач, оказавший услугу */
	private WorkFunction theWorkFunction;
	
	/** Диагноз по визиту */
	@Comment("Диагноз по визиту")
	@OneToOne
	public VocIdc10 getIdc10() {
		return theIdc10;
	}

	public void setIdc10(VocIdc10 aIdc10) {
		theIdc10 = aIdc10;
	}

	/** Диагноз по визиту */
	private VocIdc10 theIdc10;
}
