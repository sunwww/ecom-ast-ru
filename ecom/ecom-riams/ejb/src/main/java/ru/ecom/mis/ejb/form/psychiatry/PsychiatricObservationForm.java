package ru.ecom.mis.ejb.form.psychiatry;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.PsychiaticObservation;
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
@EntityFormPersistance(clazz = PsychiaticObservation.class)
@Comment("Динамика наблюдений")
@WebTrail(comment = "Динамика наблюдений", nameProperties= "id",list="entityParentList-psych_observation.do",listComment="список по пациенту", view="entityParentView-psych_observation.do")
@Parent(property="lpuAreaPsychCareCard", parentForm=LpuAreaPsychCareCardForm.class,orderBy="startDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation")
@Setter
public class PsychiatricObservationForm extends IdEntityForm {
	/**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist 
	 public Long getCareCard() {
	  return careCard;
	 }
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 private Long careCard;
	 /**
	  * Диспансерная группа
	  */
	 @Comment("Диспансерная группа")
	 @Persist
	 public Long getDispensaryGroup() {
	  return dispensaryGroup;
	 }
	 /**
	  * Диспансерная группа
	  */
	 private Long dispensaryGroup;
	 /**
	  * Вид амбулаторного наблюдения
	  */
	 @Comment("Вид амбулаторного наблюдения")
	 @Persist @Required
	 public Long getAmbulatoryCare() {
	  return ambulatoryCare;
	 }
	 /**
	  * Вид амбулаторного наблюдения
	  */
	 private Long ambulatoryCare;
	 /**
	  * Дата начала наблюдения
	  */
	 @Comment("Дата начала наблюдения")
	 @Persist @DateString @DoDateString @Required
	 public String getStartDate() {
	  return startDate;
	 }
	 /**
	  * Дата начала наблюдения
	  */
	 private String startDate;
	 /** Диспансерная группа (ИНФО) */
	@Comment("Диспансерная группа (ИНФО)")
	@Persist
	public String getDispensaryGroupInfo() {
		return dispensaryGroupInfo;
	}

	/** Диспансерная группа (ИНФО) */
	private String dispensaryGroupInfo;
	
	/** Вид амбулаторного наблюдения (ИНФО) */
	@Comment("Вид амбулаторного наблюдения (ИНФО)")
	@Persist
	public String getAmbulatoryCareInfo() {
		return ambulatoryCareInfo;
	}

	/** Вид амбулаторного наблюдения (ИНФО) */
	private String ambulatoryCareInfo;
	 /** Дата окончания наблюдения */
	@Comment("Дата окончания наблюдения")
	@Persist @DoDateString @DateString
	public String getFinishDate() {
		return finishDate;
	}

	/** Дата окончания наблюдения */
	private String finishDate;
	 /** Участок */
	@Comment("Участок")
	@Persist
	public Long getLpuAreaPsychCareCard() {
		return lpuAreaPsychCareCard;
	}

	/** Участок */
	private Long lpuAreaPsychCareCard;
	
	/** Статья уголовного кодекса */

	/** Причина снятия */
	@Comment("Причина снятия")
	@Persist
	public Long getStrikeOffReason() {
		return strikeOffReason;
	}

	/** Причина снятия */
	private Long strikeOffReason;

}
