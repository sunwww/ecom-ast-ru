package ru.ecom.mis.ejb.domain.extdisp.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * Справочник групп здоровья дополнительной диспансеризации
	 */
	@Comment("Справочник групп здоровья дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
public class VocExtDispHealthGroup extends VocBaseEntity{
	/**
	 * Номер строки карты дополнительной диспансеризации
	 */
	@Comment("Номер строки карты дополнительной диспансеризации")
	public String getCardStringNumber() {
		return theCardStringNumber;
	}
	public void setCardStringNumber(String aCardStringNumber) {
		theCardStringNumber = aCardStringNumber;
	}
	/**
	 * Номер строки карты дополнительной диспансеризации
	 */
	private String theCardStringNumber;
	/**
	 * Тип дополнительной диспансеризации
	 */
	@Comment("Тип дополнительной диспансеризации")
	@OneToOne
	public VocExtDisp getDispType() {return theDispType;}
	public void setDispType(VocExtDisp aDispType) {theDispType = aDispType;}
	/**
	 * Тип дополнительной диспансеризации
	 */
	private VocExtDisp theDispType;
	/** Код диспансеризации */
	@Comment("Код диспансеризации")
	public String getDispCode() {return theDispCode;}
	public void setDispCode(String aDispCode) {theDispCode = aDispCode;}

	/** Код диспансеризации */
	private String theDispCode;
	
	/** Архивная */
	@Comment("Архивная")
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** Архивная */
	private Boolean theIsArchival;
	
	/** Срок действия */
	@Comment("Срок действия")
	public Long getValidityDays() {return theValidityDays;}
	public void setValidityDays(Long aValidityDays) {theValidityDays = aValidityDays;}

	/** Срок действия */
	private Long theValidityDays;
}
