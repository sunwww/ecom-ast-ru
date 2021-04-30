package ru.ecom.mis.ejb.domain.extdisp;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * Показание к услуге дополнительной диспансеризации
	 */
	@Comment("Показание к услуге дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties="visit")
	    })
	@Getter
	@Setter
public class ExtDispServiceIndication extends BaseEntity{
	/** Визит */
	@Comment("Визит")
	@ManyToOne
	public ExtDispVisit getVisit() {return visit;}
	/** Визит */
	private ExtDispVisit visit;
	
	/** Тип услуги */
	@Comment("Тип услуги")
	@OneToOne
	public VocExtDispService getServiceType() {return serviceType;}
	/** Тип услуги */
	private VocExtDispService serviceType;
}
