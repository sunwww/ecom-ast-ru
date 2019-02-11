package ru.ecom.mis.ejb.form.psychiatry;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.psychiatry.PsychiatricCareCard;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.form.psychiatry.interceptor.CareCardPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PsychiatricCareCard.class)
@Comment("Карта обратившегося за психиатрической помощью (УФ 030-1/у-02)")
@WebTrail(comment = "Карта обратившегося за психиатрической помощью", nameProperties= "cardNumber",list="entityParentList-psych_careCard.do", view="entityParentView-psych_careCard.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(CareCardPreCreateInterceptor.class)
)
public class PsychiatricCareCardForm extends IdEntityForm {
	 /**
	  * Дата взятия на учет
	  */
	 @Comment("Дата взятия на учет")
	 @Persist @DateString @DoDateString 
	 public String getStartDate() {
	  return theStartDate;
	 }
	 public void setStartDate(String aStartDate) {
	  theStartDate = aStartDate;
	 }
	 /**
	  * Дата взятия на учет
	  */
	 private String theStartDate;
	 /**
	  * Дата окончания учета
	  */
	 @Comment("Дата окончания учета")
	 @Persist @DateString @DoDateString
	 public String getFinishDate() {
	  return theFinishDate;
	 }
	 public void setFinishDate(String aFinishDate) {
	  theFinishDate = aFinishDate;
	 }
	 /**
	  * Дата окончания учета
	  */
	 private String theFinishDate;
	 /**
	  * Впервые в жизни заболевший
	  */
	 @Comment("Впервые в жизни заболевший")
	 @Persist
	 public Boolean getFirstTimeDiseased() {
	  return theFirstTimeDiseased;
	 }
	 public void setFirstTimeDiseased(Boolean aFirstTimeDiseased) {
	  theFirstTimeDiseased = aFirstTimeDiseased;
	 }
	 /**
	  * Впервые в жизни заболевший
	  */
	 private Boolean theFirstTimeDiseased;
	 /**
	  * Причина прекращения наблюдения
	  */
	 @Comment("Причина прекращения наблюдения")
	 @Persist
	 public Long getStrikeOffReason() {
	  return theStrikeOffReason;
	 }
	 public void setStrikeOffReason(Long aStrikeOffReason) {
	  theStrikeOffReason = aStrikeOffReason;
	 }
	 /**
	  * Причина прекращения наблюдения
	  */
	 private Long theStrikeOffReason;
	 /**
	  * Примечания
	  */
	 @Comment("Примечания")
	 @Persist
	 public String getNotes() {
	  return theNotes;
	 }
	 public void setNotes(String aNotes) {
	  theNotes = aNotes;
	 }
	 /**
	  * Примечания
	  */
	 private String theNotes;
	 
	 /**
	  * Номер диспансерной карты
	  */
	 @Comment("Номер диспансерной карты")
	 @Persist @Required
	 public String getCardNumber() {
	  return theCardNumber;
	 }
	 public void setCardNumber(String aCardNumber) {
	  theCardNumber = aCardNumber;
	 }
	 /**
	  * Номер диспансерной карты
	  */
	 private String theCardNumber;






	 /**
	  * Дата начала заболевания
	  */
	 @Comment("Дата начала заболевания")
	 @Persist @DateString @DoDateString 
	 public String getIllnessStartDate() {
	  return theIllnessStartDate;
	 }
	 public void setIllnessStartDate(String aIllnessStartDate) {
	  theIllnessStartDate = aIllnessStartDate;
	 }
	 /**
	  * Дата начала заболевания
	  */
	 private String theIllnessStartDate;
	 /**
	  * Дата первого обращения к психиатру
	  */
	 @Comment("Дата первого обращения к психиатру")
	 @Persist @DateString @DoDateString @Required
	 public String getFirstPsychiatricVisitDate() {
	  return theFirstPsychiatricVisitDate;
	 }
	 public void setFirstPsychiatricVisitDate(String aFirstPsychiatricVisitDate) {
	  theFirstPsychiatricVisitDate = aFirstPsychiatricVisitDate;
	 }
	 /**
	  * Дата первого обращения к психиатру
	  */
	 private String theFirstPsychiatricVisitDate;
	 /**
	  * Дата последнего недобровольного освидетельствования
	  */
	 @Comment("Дата последнего недобровольного освидетельствования")
	 @Persist @DateString @DoDateString
	 public String getLastInvoluntaryExamDate() {
	  return theLastInvoluntaryExamDate;
	 }
	 public void setLastInvoluntaryExamDate(String aLastInvoluntaryExamDate) {
	  theLastInvoluntaryExamDate = aLastInvoluntaryExamDate;
	 }
	 /**
	  * Дата последнего недобровольного освидетельствования
	  */
	 private String theLastInvoluntaryExamDate;
	 /**
	  * Судимость до обращения к психиатру
	  */
	 @Comment("Судимость до обращения к психиатру")
	 @Persist
	 public Boolean getConvictionsBeforeCare() {
	  return theConvictionsBeforeCare;
	 }
	 public void setConvictionsBeforeCare(Boolean aConvictionsBeforeCare) {
	  theConvictionsBeforeCare = aConvictionsBeforeCare;
	 }
	 /**
	  * Судимость до обращения к психиатру
	  */
	 private Boolean theConvictionsBeforeCare;
	 /**
	  * Описание прочей причины прекращения наблюдения
	  */
	 @Comment("Описание прочей причины прекращения наблюдения")
	 @Persist
	 public String getStrikeOffOtherReasonNotes() {
	  return theStrikeOffOtherReasonNotes;
	 }
	 public void setStrikeOffOtherReasonNotes(String aStrikeOffOtherReasonNotes) {
	  theStrikeOffOtherReasonNotes = aStrikeOffOtherReasonNotes;
	 }
	 /**
	  * Описание прочей причины прекращения наблюдения
	  */
	 private String theStrikeOffOtherReasonNotes;
	 /**
	  * Дата смерти
	  */
	 @Comment("Дата смерти")
	 @Persist @DateString @DoDateString
	 public String getDeathDate() {
	  return theDeathDate;
	 }
	 public void setDeathDate(String aDeathDate) {
	  theDeathDate = aDeathDate;
	 }
	 /**
	  * Дата смерти
	  */
	 private String theDeathDate;
	 /**
	  * Причина смерти
	  */
	 @Comment("Причина смерти")
	 @Persist
	 public Long getDeathReason() {
	  return theDeathReason;
	 }
	 public void setDeathReason(Long aDeathReason) {
	  theDeathReason = aDeathReason;
	 }
	 /**
	  * Причина смерти
	  */
	 private Long theDeathReason;
	 /**
	  * Причина наблюдения
	  */
	 @Comment("Причина наблюдения")
	 @Persist 
	 public Long getObservationReason() {
	  return theObservationReason;
	 }
	 public void setObservationReason(Long aObservationReason) {
	  theObservationReason = aObservationReason;
	 }
	 /**
	  * Причина наблюдения
	  */
	 private Long theObservationReason;
	 /**
	  * Пациент
	  */
	 @Comment("Пациент")
	 @Persist @Required
	 public Long getPatient() {
	  return thePatient;
	 }
	 public void setPatient(Long aPatient) {
	  thePatient = aPatient;
	 }
	 /**
	  * Пациент
	  */
	 private Long thePatient;
	 /**
	  * Дата заведения карты *
	  */
	 @DoDateString @DateString @Persist
	 public String getDateRegistration() {return theDateRegistration;}
	 public void setDateRegistration(String aDateRegistration) {theDateRegistration = aDateRegistration;    }

	 /**
	  * Регистратор *
	  */
	 @Persist
	 public String getRegistrator() {return theRegistrator;}
	 public void setRegistrator(String aRegistrator) {theRegistrator = aRegistrator;}

	 /**
	  * Регистратор *
	  */
	 private String theRegistrator;
	 /** Дата заведения карты */
	 private String theDateRegistration;
	/**
	 * Новое свойство
	 */
	@Comment("Обновлять даты")
	public Boolean getUpdateDates() {
		return theUpdateDates;
	}
	/**
	 * Новое свойство
	 */
	public void setUpdateDates(Boolean a_Property) {
		theUpdateDates = a_Property;
	}
	/**
	 * Новое свойство
	 */
	private Boolean theUpdateDates;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist @Required
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private Long theLpu;
}
