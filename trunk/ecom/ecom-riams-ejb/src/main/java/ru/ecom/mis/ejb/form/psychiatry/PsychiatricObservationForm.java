package ru.ecom.mis.ejb.form.psychiatry;

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
public class PsychiatricObservationForm extends IdEntityForm {
	/**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist 
	 public Long getCareCard() {
	  return theCareCard;
	 }
	 public void setCareCard(Long aCareCard) {
	  theCareCard = aCareCard;
	 }
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 private Long theCareCard;
	 /**
	  * Диспансерная группа
	  */
	 @Comment("Диспансерная группа")
	 @Persist
	 public Long getDispensaryGroup() {
	  return theDispensaryGroup;
	 }
	 public void setDispensaryGroup(Long aDispensaryGroup) {
	  theDispensaryGroup = aDispensaryGroup;
	 }
	 /**
	  * Диспансерная группа
	  */
	 private Long theDispensaryGroup;
	 /**
	  * Вид амбулаторного наблюдения
	  */
	 @Comment("Вид амбулаторного наблюдения")
	 @Persist @Required
	 public Long getAmbulatoryCare() {
	  return theAmbulatoryCare;
	 }
	 public void setAmbulatoryCare(Long aAmbulatoryCare) {
	  theAmbulatoryCare = aAmbulatoryCare;
	 }
	 /**
	  * Вид амбулаторного наблюдения
	  */
	 private Long theAmbulatoryCare;
	 /**
	  * Дата начала наблюдения
	  */
	 @Comment("Дата начала наблюдения")
	 @Persist @DateString @DoDateString @Required
	 public String getStartDate() {
	  return theStartDate;
	 }
	 public void setStartDate(String aStartDate) {
	  theStartDate = aStartDate;
	 }
	 /**
	  * Дата начала наблюдения
	  */
	 private String theStartDate;
	 /** Диспансерная группа (ИНФО) */
	@Comment("Диспансерная группа (ИНФО)")
	@Persist
	public String getDispensaryGroupInfo() {
		return theDispensaryGroupInfo;
	}

	public void setDispensaryGroupInfo(String aDispensaryGroupInfo) {
		theDispensaryGroupInfo = aDispensaryGroupInfo;
	}

	/** Диспансерная группа (ИНФО) */
	private String theDispensaryGroupInfo;
	
	/** Вид амбулаторного наблюдения (ИНФО) */
	@Comment("Вид амбулаторного наблюдения (ИНФО)")
	@Persist
	public String getAmbulatoryCareInfo() {
		return theAmbulatoryCareInfo;
	}

	public void setAmbulatoryCareInfo(String aAmbulatoryCareInfo) {
		theAmbulatoryCareInfo = aAmbulatoryCareInfo;
	}

	/** Вид амбулаторного наблюдения (ИНФО) */
	private String theAmbulatoryCareInfo;
	 /** Дата окончания наблюдения */
	@Comment("Дата окончания наблюдения")
	@Persist @DoDateString @DateString
	public String getFinishDate() {
		return theFinishDate;
	}
	
	public void setFinishDate(String aFinishDate) {
		theFinishDate = aFinishDate;
	}
	
	/** Дата окончания наблюдения */
	private String theFinishDate;
	 /** Участок */
	@Comment("Участок")
	@Persist
	public Long getLpuAreaPsychCareCard() {
		return theLpuAreaPsychCareCard;
	}

	public void setLpuAreaPsychCareCard(Long aLpuAreaPsychCareCard) {
		theLpuAreaPsychCareCard = aLpuAreaPsychCareCard;
	}

	/** Участок */
	private Long theLpuAreaPsychCareCard;
	
	/** Статья уголовного кодекса */

	/** Причина снятия */
	@Comment("Причина снятия")
	@Persist
	public Long getStrikeOffReason() {
		return theStrikeOffReason;
	}

	public void setStrikeOffReason(Long aStrikeOffReason) {
		theStrikeOffReason = aStrikeOffReason;
	}

	/** Причина снятия */
	private Long theStrikeOffReason;

}
