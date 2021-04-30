package ru.ecom.mis.ejb.domain.extdisp;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

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
	@Getter
	@Setter
public class ExtDispService extends BaseEntity{
	/**
	 * Карта дополнительной диспансеризации
	 */
	@Comment("Карта дополнительной диспансеризации")
	@ManyToOne
	public ExtDispCard getCard() {return card;}
	/**
	 * Карта дополнительной диспансеризации
	 */
	private ExtDispCard card;
	/**
	 * Дата оказания услуги
	 */
	private Date serviceDate;
	/**
	 * Тип услуги
	 */
	@Comment("Тип услуги")
	@OneToOne
	public VocExtDispService getServiceType() {return serviceType;}
	/**
	 * Тип услуги
	 */
	private VocExtDispService serviceType;
	
	/** Врач, оказавший услугу */
	@Comment("Врач, оказавший услугу")
	@OneToOne
	public WorkFunction	getWorkFunction() {
		return workFunction;
	}

	/** Врач, оказавший услугу */
	private WorkFunction workFunction;
	
	/** Диагноз по визиту */
	@Comment("Диагноз по визиту")
	@OneToOne
	public VocIdc10 getIdc10() {
		return idc10;
	}

	/** Диагноз по визиту */
	private VocIdc10 idc10;

	/** Отказ от услуги */
	private Boolean deniedService ;
}
