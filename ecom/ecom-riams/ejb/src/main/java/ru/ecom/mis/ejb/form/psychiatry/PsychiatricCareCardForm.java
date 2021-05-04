package ru.ecom.mis.ejb.form.psychiatry;

import lombok.Setter;
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
@Setter
public class PsychiatricCareCardForm extends IdEntityForm {
	 /**
	  * Дата взятия на учет
	  */
	 @Comment("Дата взятия на учет")
	 @Persist @DateString @DoDateString 
	 public String getStartDate() {
	  return startDate;
	 }
	 /**
	  * Дата взятия на учет
	  */
	 private String startDate;
	 /**
	  * Дата окончания учета
	  */
	 @Comment("Дата окончания учета")
	 @Persist @DateString @DoDateString
	 public String getFinishDate() {
	  return finishDate;
	 }
	 /**
	  * Дата окончания учета
	  */
	 private String finishDate;
	 /**
	  * Впервые в жизни заболевший
	  */
	 @Comment("Впервые в жизни заболевший")
	 @Persist
	 public Boolean getFirstTimeDiseased() {
	  return firstTimeDiseased;
	 }
	 /**
	  * Впервые в жизни заболевший
	  */
	 private Boolean firstTimeDiseased;
	 /**
	  * Причина прекращения наблюдения
	  */
	 @Comment("Причина прекращения наблюдения")
	 @Persist
	 public Long getStrikeOffReason() {
	  return strikeOffReason;
	 }
	 /**
	  * Причина прекращения наблюдения
	  */
	 private Long strikeOffReason;
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
	  * Номер диспансерной карты
	  */
	 @Comment("Номер диспансерной карты")
	 @Persist @Required
	 public String getCardNumber() {
	  return cardNumber;
	 }
	 /**
	  * Номер диспансерной карты
	  */
	 private String cardNumber;
	 /**
	  * Дата начала заболевания
	  */
	 @Comment("Дата начала заболевания")
	 @Persist @DateString @DoDateString 
	 public String getIllnessStartDate() {
	  return illnessStartDate;
	 }
	 /**
	  * Дата начала заболевания
	  */
	 private String illnessStartDate;
	 /**
	  * Дата первого обращения к психиатру
	  */
	 @Comment("Дата первого обращения к психиатру")
	 @Persist @DateString @DoDateString @Required
	 public String getFirstPsychiatricVisitDate() {
	  return firstPsychiatricVisitDate;
	 }
	 /**
	  * Дата первого обращения к психиатру
	  */
	 private String firstPsychiatricVisitDate;
	 /**
	  * Дата последнего недобровольного освидетельствования
	  */
	 @Comment("Дата последнего недобровольного освидетельствования")
	 @Persist @DateString @DoDateString
	 public String getLastInvoluntaryExamDate() {
	  return lastInvoluntaryExamDate;
	 }
	 /**
	  * Дата последнего недобровольного освидетельствования
	  */
	 private String lastInvoluntaryExamDate;
	 /**
	  * Судимость до обращения к психиатру
	  */
	 @Comment("Судимость до обращения к психиатру")
	 @Persist
	 public Boolean getConvictionsBeforeCare() {
	  return convictionsBeforeCare;
	 }
	 /**
	  * Судимость до обращения к психиатру
	  */
	 private Boolean convictionsBeforeCare;
	 /**
	  * Описание прочей причины прекращения наблюдения
	  */
	 @Comment("Описание прочей причины прекращения наблюдения")
	 @Persist
	 public String getStrikeOffOtherReasonNotes() {
	  return strikeOffOtherReasonNotes;
	 }
	 /**
	  * Описание прочей причины прекращения наблюдения
	  */
	 private String strikeOffOtherReasonNotes;
	 /**
	  * Дата смерти
	  */
	 @Comment("Дата смерти")
	 @Persist @DateString @DoDateString
	 public String getDeathDate() {
	  return deathDate;
	 }
	 /**
	  * Дата смерти
	  */
	 private String deathDate;
	 /**
	  * Причина смерти
	  */
	 @Comment("Причина смерти")
	 @Persist
	 public Long getDeathReason() {
	  return deathReason;
	 }
	 /**
	  * Причина смерти
	  */
	 private Long deathReason;
	 /**
	  * Причина наблюдения
	  */
	 @Comment("Причина наблюдения")
	 @Persist 
	 public Long getObservationReason() {
	  return observationReason;
	 }
	 /**
	  * Причина наблюдения
	  */
	 private Long observationReason;
	 /**
	  * Пациент
	  */
	 @Comment("Пациент")
	 @Persist @Required
	 public Long getPatient() {
	  return patient;
	 }
	 /**
	  * Пациент
	  */
	 private Long patient;
	 /**
	  * Дата заведения карты *
	  */
	 @DoDateString @DateString @Persist
	 public String getDateRegistration() {return dateRegistration;}

	 /**
	  * Регистратор *
	  */
	 @Persist
	 public String getRegistrator() {return registrator;}

	 /**
	  * Регистратор *
	  */
	 private String registrator;
	 /** Дата заведения карты */
	 private String dateRegistration;
	/**
	 * Новое свойство
	 */
	@Comment("Обновлять даты")
	public Boolean getUpdateDates() {
		return updateDates;
	}
	private Boolean updateDates;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist @Required
	public Long getLpu() {
		return lpu;
	}

	/** ЛПУ */
	private Long lpu;
}
