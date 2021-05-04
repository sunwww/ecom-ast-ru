package ru.ecom.mis.ejb.form.psychiatry;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.PsychicHealthState;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PsychicHealthState.class)
@Comment("Динамика состояний")
@WebTrail(comment = "Динамика состояний", nameProperties= "id",list="entityParentList-psych_healthState.do",listComment="список по пациенту", view="entityParentView-psych_healthState.do")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class,orderBy="startDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/HealthState")
@Setter
public class PsychicHealthStateForm extends IdEntityForm {
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
	 /**
	  * Дата начала
	  */
	 @Comment("Дата начала")
	 @Persist @DoDateString @DateString @Required
	 public String getStartDate() {
	  return startDate;
	 }
	 /**
	  * Дата начала
	  */
	 private String startDate;
	 /**
	  * Вид состояния психического здоровья
	  */
	 @Comment("Вид состояния психического здоровья")
	 @Persist @Required
	 public Long getKind() {
	  return kind;
	 }
	 /**
	  * Вид состояния психического здоровья
	  */
	 private Long kind;
	 /**
	  * Дата окончания
	  */
	 @Comment("Дата окончания")
	 @Persist @DoDateString @DateString 
	 public String getFinishDate() {
	  return finishDate;
	 }
	 /**
	  * Дата окончания
	  */
	 private String finishDate;
	 /**
	  * Описание
	  */
	 @Comment("Описание")
	 @Persist
	 public String getNotes() {
	  return notes;
	 }
	 /**
	  * Описание
	  */
	 private String notes;
	 
	 /** Вид состояния психического здоровья (ИНФО) */
	@Comment("Вид состояния психического здоровья (ИНФО)")
	@Persist
	public String getKindInfo() {
		return kindInfo;
	}
	/** Вид состояния психического здоровья (ИНФО) */
	private String kindInfo;
}
