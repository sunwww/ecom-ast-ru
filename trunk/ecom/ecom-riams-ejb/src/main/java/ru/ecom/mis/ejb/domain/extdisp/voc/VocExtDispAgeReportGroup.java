package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.OneToOne;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class VocExtDispAgeReportGroup extends VocBaseEntity {
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
}
