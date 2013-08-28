package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.medos.ejb.persdata.domain.voc.VocExternalCarrierOperation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Операции с внешними электронными носителями
	 */
	@Comment("Операции с внешними электронными носителями")
@Entity
@Table(schema="SQLUser")
public class ExternalCarrierOperation extends BaseEntity{
	/**
	 * Внешний электронный носитель
	 */
	@Comment("Внешний электронный носитель")
	@ManyToOne
	public ExternalCarrier getExternalCarrier() {
		return theExternalCarrier;
	}
	public void setExternalCarrier(ExternalCarrier aExternalCarrier) {
		theExternalCarrier = aExternalCarrier;
	}
	/**
	 * Внешний электронный носитель
	 */
	private ExternalCarrier theExternalCarrier;
	/**
	 * Тип операции
	 */
	@Comment("Тип операции")
	@OneToOne
	public VocExternalCarrierOperation getType() {
		return theType;
	}
	public void setType(VocExternalCarrierOperation aType) {
		theType = aType;
	}
	/**
	 * Тип операции
	 */
	private VocExternalCarrierOperation theType;
}
