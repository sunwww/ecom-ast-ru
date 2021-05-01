package ru.ecom.mis.ejb.form.psychiatry;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.PublicDangerousEffect;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PublicDangerousEffect.class)
@Comment("Общественно-опасное действие")
@WebTrail(comment = "Общественно-опасное действие", nameProperties= "id",list="entityParentList-psych_publicDangerousEffect.do",listComment="список по пациенту", view="entityParentView-psych_publicDangerousEffect.do")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class,orderBy="effectDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect")
@Setter
public class PublicDangerousEffectForm extends IdEntityForm {
	/**
	  * Дата действия
	  */
	 @Comment("Дата действия")
	 @Persist @DoDateString @DateString @Required
	 public String getEffectDate() {
	  return effectDate;
	 }
	 /**
	  * Дата действия
	  */
	 private String effectDate;
	 /**
	  * Примечания
	  */
	 @Comment("Примечания")
	 @Persist
	 public String getNotes() {
	  return notes;
	 }
	 /**
	  * Примечания
	  */
	 private String notes;
	 /**
	  * Статья уголовного кодекса
	  */
	 @Comment("Статья уголовного кодекса")
	 @Persist @Required
	 public Long getCriminalCodeArtical() {
	  return criminalCodeArtical;
	 }
	 /**
	  * Статья уголовного кодекса
	  */
	 private Long criminalCodeArtical;
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist @Required
	 public Long getCareCard() {
	  return careCard;
	 }
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 private Long careCard;
	 /** Статья уголовного кодекса (ИНФО) */
	 @Comment("Статья уголовного кодекса (ИНФО)")
	 @Persist
	 public String getCriminalCodeArticalInfo() {
	 	return criminalCodeArticalInfo;
	 }

	 /** Статья уголовного кодекса (ИНФО) */
	 private String criminalCodeArticalInfo;
	 
	 /** Номер ООД */
	 @Comment("Номер ООД")
	 @Persist @Required
	 public Integer getEffectNumber() {return effectNumber;}

	 /** Номер ООД */
	 private Integer effectNumber;
	 
	 /** Тип общественно опасных действий */
	@Comment("Тип общественно опасных действий")
	@Persist 
	public Long getEffectType() {return effectType;}

	/** Тип общественно опасных действий */
	private Long effectType;
}
