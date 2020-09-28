package ru.ecom.mis.ejb.form.extdisp.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDisp;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = VocExtDisp.class)
@Comment("Вид доп.диспансеризации")
@WebTrail(comment = "Вид доп.диспансеризации", nameProperties= "id"
, view="entityView-extDisp_voc.do", shortView="entityView-extDisp_voc.do?short=Short")
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Voc")
public class VocExtDispForm extends IdEntityForm {
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код */
	private String theCode;
	/** Наименование */
	private String theName;
	
	/** Флаг Медкомиссия */
	@Comment("Флаг Медкомиссия")
	@Persist
	public Boolean getIsComission() {return theIsComission;}
	public void setIsComission(Boolean aIsComission) {theIsComission = aIsComission;}
	/** Флаг Медкомиссия */
	private Boolean theIsComission;
	
	/** Оказывается только прикрепленному населению */
	@Comment("Оказывается только прикрепленному населению")
	@Persist
	public Boolean getAttachmentPopulation() {return theAttachmentPopulation;}
	public void setAttachmentPopulation(Boolean aAttachmentPopulation) {theAttachmentPopulation = aAttachmentPopulation;}
	/** Оказывается только прикрепленному населению */
	private Boolean theAttachmentPopulation;
	

	/** Запрет на дублирование */
	@Comment("Запрет на дублирование")
	@Persist
	public Boolean getDisableAgeDoubles() {return theDisableAgeDoubles;}
	public void setDisableAgeDoubles(Boolean aDisableAgeDoubles) {theDisableAgeDoubles = aDisableAgeDoubles;}
	/** Запрет на дублирование */
	private Boolean theDisableAgeDoubles;

	/** В архиве */
	@Comment("В архиве")
	@Persist
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** В архиве */
	private Boolean theIsArchival ;

	/** Автоматически расчитывать возраст */
	@Comment("Автоматически расчитывать возраст")
	@Persist
	public Boolean getAutoCalcAge() {return theAutoCalcAge;}
	public void setAutoCalcAge(Boolean aAutoCalcAge) {theAutoCalcAge = aAutoCalcAge;}
	/** Автоматически расчитывать возраст */
	private Boolean theAutoCalcAge ;
}
