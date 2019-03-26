package ru.ecom.mis.ejb.domain.extdisp.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

	/**
	 * Справочник видов дополнительной диспансеризации
	 */
	@Comment("Справочник видов дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
public class VocExtDisp extends VocBaseEntity{

	/** Внутренний код */
	@Comment("Внутренний код")
	public String getInternalCode() {return theInternalCode;}
	public void setInternalCode(String aInternalCode) {theInternalCode = aInternalCode;}

	/** Внутренний код */
	private String theInternalCode;
	
	/** Флаг медосмотра */
	@Comment("Флаг медосмотра")
	public Boolean getIsComission() {return theIsComission;}
	public void setIsComission(Boolean aIsComission) {theIsComission = aIsComission;}
	/** Флаг медосмотра */
	private Boolean theIsComission;

	/** Оказывается только прикрепленному населению */
	@Comment("Оказывается только прикрепленному населению")
	public Boolean getAttachmentPopulation() {return theAttachmentPopulation;}
	public void setAttachmentPopulation(Boolean aAttachmentPopulation) {theAttachmentPopulation = aAttachmentPopulation;}
	/** Оказывается только прикрепленному населению */
	private Boolean theAttachmentPopulation;

	/** Запрет на дублирование */
	@Comment("Запрет на дублирование")
	public Boolean getDisableAgeDoubles() {return theDisableAgeDoubles;}
	public void setDisableAgeDoubles(Boolean aDisableAgeDoubles) {theDisableAgeDoubles = aDisableAgeDoubles;}
	/** Запрет на дублирование */
	private Boolean theDisableAgeDoubles;

	/** В архиве */
	@Comment("В архиве")
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** В архиве */
	private Boolean theIsArchival ;

	/** Автоматически расчитывать возраст */
	@Comment("Автоматически расчитывать возраст")
	@Column(nullable=false, columnDefinition="boolean default false")
	public Boolean getAutoCalcAge() {return theAutoCalcAge;}
	public void setAutoCalcAge(Boolean aAutoCalcAge) {theAutoCalcAge = aAutoCalcAge;}
	/** Автоматически расчитывать возраст */
	private Boolean theAutoCalcAge ;

}
