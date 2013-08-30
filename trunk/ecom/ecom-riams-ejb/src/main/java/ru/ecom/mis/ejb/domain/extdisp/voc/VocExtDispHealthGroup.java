package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDisp;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

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
	public VocExtDisp getDispType() {
		return theDispType;
	}
	public void setDispType(VocExtDisp aDispType) {
		theDispType = aDispType;
	}
	/**
	 * Тип дополнительной диспансеризации
	 */
	private VocExtDisp theDispType;
}
