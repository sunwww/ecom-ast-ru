package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.PregnanExchangeCard;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Обменная карта беременной (сведения о беременной)
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz= PregnanExchangeCard.class)
@Comment("Обменная карта беременной (сведения о беременной)")
@WebTrail(comment = "Обменная карта беременной (сведения о беременной)", nameProperties= "id", view="entityParentView-preg_pregnanCard.do")
@Parent(property="pregnancy", parentForm= PregnancyForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/PregnanExchangeCard")
@Setter
public class PregnanExchangeCardForm extends IdEntityForm{
	
	/** Консультация */
	@Comment("Консультация")
	@Persist @Required
	public Long getConsultation() {return consultation;}

	/** Перенесенные заболевания */
	@Comment("Перенесенные заболевания")
	@Persist
	public String getTransmitDiseases() {return transmitDiseases;}

	
	/** Предыдущие беременности */
	@Comment("Предыдущие беременности")
	@Persist
	public String getPreviousPregnancies() {return previousPregnancies;}

	
	/** Последние месячные */
	@Persist @DateString @DoDateString
	@Comment("Последние месячные")
	public String getLastMensis() {return lastMensis;}

	/** Количество коротких беременностей */
	@Comment("Количество коротких беременностей")
	@Persist
	public Integer getShortPregnancyAmount() {return shortPregnancyAmount;}

	/** Количество родов */
	@Comment("Количество родов")
	@Persist
	public Integer getChildbirthAmount() {return childbirthAmount;}

	/** Первое посещение женской консультации */
	@Comment("Первое посещение женской консультации")
	@Persist @DateString @DoDateString
	public String getFirstVisitDate() {return firstVisitDate;}

	/** Количество визитов */
	@Comment("Количество визитов")
	@Persist
	public Integer getVisitsAmount() {return visitsAmount;}

	
	/** Особенности беременности */
	@Comment("Особенности беременности")
	@Persist
	public String getPregnancyFeatures() {return pregnancyFeatures;}

	/** Обследование беременной */
	@Comment("Обследование беременной")
	@Persist
	public Long getPregnanInspection() {return pregnanInspection;}

	/** Анализ мочи */
	@Comment("Анализ мочи")
	@Persist
	public String getUrineAnalysis() {return urineAnalysis;}

	/** Дата анализа мочи */
	@Comment("Дата анализа мочи")
	@Persist @DateString @DoDateString
	public String getUrineAnalysisDate() {return urineAnalysisDate;}

	/** Анализ крови */
	@Comment("Анализ крови")
	@Persist
	public String getBloodAnalysis() {return bloodAnalysis;}

	/** Дата анализа крови */
	@Comment("Дата анализа крови")
	@Persist @DateString @DoDateString
	public String getBloodAnalysisDate() {return bloodAnalysisDate;}

	/** Артериальное давление */
	@Comment("Артериальное давление")
	@Persist
	public String getBloodPressure() {return bloodPressure;}

	/** Дата артериального давления */
	@Comment("Дата артериального давления")
	@Persist @DateString @DoDateString
	public String getBloodPressureDate() {return bloodPressureDate;}

	/** Количество занятий психопрофилактической подготовки */
	@Comment("Количество занятий психопрофилактической подготовки")
	@Persist
	public Integer getTrainingAmount() {return trainingAmount;}

	/** Дата последнего занятия психопрофилактической подготовки */
	@Comment("Дата последнего занятия психопрофилактической подготовки")
	@Persist @DateString @DoDateString
	public String getLastTrainingDate() {return lastTrainingDate;}

	/** Предполагаемый срок родов */
	@Comment("Предполагаемый срок родов")
	@Persist
	public String getSupposedBithPeriod() {return supposedBithPeriod;}

	
	/** Специальные замечания */
	@Comment("Специальные замечания")
	@Persist
	public String getNotes() {return notes;}

	/** Дата заполнения */
	@Comment("Дата заполнения")
	@Persist @DateString @DoDateString
	public String getFillingDate() {return fillingDate;}

	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return pregnancy;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return medCase;}

	/** История родов */
	@Comment("История родов")
	@Persist
	public Long getPregnancyHistory() {return pregnancyHistory;}

	/** Не помнит, когда были последние месячные */
	@Comment("Не помнит, когда были последние месячные")
	@Persist
	public Boolean getDontRememberLastMensis() {return dontRememberLastMensis;}

	/** Не посещала жк */
	@Comment("Не посещала жк")
	@Persist
	public Boolean getDontVisitCons() {return dontVisitCons;}

	/** Не посещала жк */
	private Boolean dontVisitCons;
	/** Не помнит, когда были последние месячные */
	private Boolean dontRememberLastMensis;
	/** История родов */
	private Long pregnancyHistory;
	/** Случай медицинского обслуживания */
	private Long medCase;
	/** Беременность */
	private Long pregnancy;
	/** Предполагаемый срок родов */
	private String supposedBithPeriod;
	/** Специальные замечания */
	private String notes;
	/** Анализ мочи */
	private String urineAnalysis;
	/** Обследование беременной */
	private Long pregnanInspection;
	/** Дата анализа мочи */
	private String urineAnalysisDate;
	/** Анализ крови */
	private String bloodAnalysis;
	/** Дата анализа крови */
	private String bloodAnalysisDate;
	/** Артериальное давление */
	private String bloodPressure;
	/** Дата артериального давления */
	private String bloodPressureDate;
	/** Количество занятий психопрофилактической подготовки */
	private Integer trainingAmount;
	/** Дата последнего занятия психопрофилактической подготовки */
	private String lastTrainingDate;
	/** Дата заполнения */
	private String fillingDate;
	/** Консультация */
	private Long consultation;
	/** Перенесенные заболевания */
	private String transmitDiseases;
	/** Предыдущие беременности */
	private String previousPregnancies;
	/** Последние месячные */
	private String lastMensis;
	/** Количество коротких беременностей */
	private Integer shortPregnancyAmount;
	/** Количество родов */
	private Integer childbirthAmount;
	/** Первое посещение женской консультации */
	private String firstVisitDate;
	/** Количество визитов */
	private Integer visitsAmount;
	/** Особенности беременности */
	private String pregnancyFeatures;
}
