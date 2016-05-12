package ru.ecom.mis.ejb.domain.extdisp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispVisit;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Показание к услуге дополнительной диспансеризации
	 */
	@Comment("Показание к услуге дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties="visit")
	    }) 
public class ExtDispServiceIndication extends BaseEntity{
	/** Визит */
	@Comment("Визит")
	@ManyToOne
	public ExtDispVisit getVisit() {return theVisit;}
	public void setVisit(ExtDispVisit aVisit) {theVisit = aVisit;}
	/** Визит */
	private ExtDispVisit theVisit;
	
	/** Тип услуги */
	@Comment("Тип услуги")
	@OneToOne
	public VocExtDispService getServiceType() {return theServiceType;}
	public void setServiceType(VocExtDispService aServiceType) {theServiceType = aServiceType;}
	/** Тип услуги */
	private VocExtDispService theServiceType;
}
