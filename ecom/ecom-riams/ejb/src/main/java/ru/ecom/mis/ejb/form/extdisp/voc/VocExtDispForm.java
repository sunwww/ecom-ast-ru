package ru.ecom.mis.ejb.form.extdisp.voc;

import lombok.Setter;
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
@Setter
public class VocExtDispForm extends IdEntityForm {
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return name;}

	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return code;}

	/** Код */
	private String code;
	/** Наименование */
	private String name;
	
	/** Флаг Медкомиссия */
	@Comment("Флаг Медкомиссия")
	@Persist
	public Boolean getIsComission() {return isComission;}
	/** Флаг Медкомиссия */
	private Boolean isComission;
	
	/** Оказывается только прикрепленному населению */
	@Comment("Оказывается только прикрепленному населению")
	@Persist
	public Boolean getAttachmentPopulation() {return attachmentPopulation;}
	/** Оказывается только прикрепленному населению */
	private Boolean attachmentPopulation;
	

	/** Запрет на дублирование */
	@Comment("Запрет на дублирование")
	@Persist
	public Boolean getDisableAgeDoubles() {return disableAgeDoubles;}
	/** Запрет на дублирование */
	private Boolean disableAgeDoubles;

	/** В архиве */
	@Comment("В архиве")
	@Persist
	public Boolean getIsArchival() {return isArchival;}
	/** В архиве */
	private Boolean isArchival ;

	/** Автоматически расчитывать возраст */
	@Comment("Автоматически расчитывать возраст")
	@Persist
	public Boolean getAutoCalcAge() {return autoCalcAge;}
	/** Автоматически расчитывать возраст */
	private Boolean autoCalcAge ;
}
