package ru.ecom.mis.ejb.form.birth;

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
public class PregnanExchangeCardForm extends IdEntityForm{
	
	/** Консультация */
	@Comment("Консультация")
	@Persist @Required
	public Long getConsultation() {return theConsultation;}
	public void setConsultation(Long aConsultation) {theConsultation = aConsultation;}

	/** Перенесенные заболевания */
	@Comment("Перенесенные заболевания")
	@Persist
	public String getTransmitDiseases() {return theTransmitDiseases;}
	public void setTransmitDiseases(String aTransmitDiseases) {theTransmitDiseases = aTransmitDiseases;}

	
	/** Предыдущие беременности */
	@Comment("Предыдущие беременности")
	@Persist
	public String getPreviousPregnancies() {return thePreviousPregnancies;}
	public void setPreviousPregnancies(String aPreviousPregnancies) {thePreviousPregnancies = aPreviousPregnancies;}

	
	/** Последние месячные */
	@Persist @DateString @DoDateString
	@Comment("Последние месячные")
	public String getLastMensis() {return theLastMensis;}
	public void setLastMensis(String aLastMensis) {theLastMensis = aLastMensis;}

	/** Количество коротких беременностей */
	@Comment("Количество коротких беременностей")
	@Persist
	public Integer getShortPregnancyAmount() {return theShortPregnancyAmount;}
	public void setShortPregnancyAmount(Integer aShortPregnancyAmount) {theShortPregnancyAmount = aShortPregnancyAmount;}

	/** Количество родов */
	@Comment("Количество родов")
	@Persist
	public Integer getChildbirthAmount() {return theChildbirthAmount;}
	public void setChildbirthAmount(Integer aChildbirthAmount) {theChildbirthAmount = aChildbirthAmount;}

	/** Первое посещение женской консультации */
	@Comment("Первое посещение женской консультации")
	@Persist @DateString @DoDateString
	public String getFirstVisitDate() {return theFirstVisitDate;}
	public void setFirstVisitDate(String aFirstVisitDate) {theFirstVisitDate = aFirstVisitDate;}

	/** Количество визитов */
	@Comment("Количество визитов")
	@Persist
	public Integer getVisitsAmount() {return theVisitsAmount;}
	public void setVisitsAmount(Integer aVisitsAmount) {theVisitsAmount = aVisitsAmount;}

	
	/** Особенности беременности */
	@Comment("Особенности беременности")
	@Persist
	public String getPregnancyFeatures() {return thePregnancyFeatures;}
	public void setPregnancyFeatures(String aPregnancyFeatures) {thePregnancyFeatures = aPregnancyFeatures;}

	/** Обследование беременной */
	@Comment("Обследование беременной")
	@Persist
	public Long getPregnanInspection() {return thePregnanInspection;}
	public void setPregnanInspection(Long aPregnanInspection) {thePregnanInspection = aPregnanInspection;}

	/** Анализ мочи */
	@Comment("Анализ мочи")
	@Persist
	public String getUrineAnalysis() {return theUrineAnalysis;}
	public void setUrineAnalysis(String aUrineAnalysis) {theUrineAnalysis = aUrineAnalysis;}

	/** Дата анализа мочи */
	@Comment("Дата анализа мочи")
	@Persist @DateString @DoDateString
	public String getUrineAnalysisDate() {return theUrineAnalysisDate;}
	public void setUrineAnalysisDate(String aUrineAnalysisDate) {theUrineAnalysisDate = aUrineAnalysisDate;}

	/** Анализ крови */
	@Comment("Анализ крови")
	@Persist
	public String getBloodAnalysis() {return theBloodAnalysis;}
	public void setBloodAnalysis(String aBloodAnalysis) {theBloodAnalysis = aBloodAnalysis;}

	/** Дата анализа крови */
	@Comment("Дата анализа крови")
	@Persist @DateString @DoDateString
	public String getBloodAnalysisDate() {return theBloodAnalysisDate;}
	public void setBloodAnalysisDate(String aBloodAnalysisDate) {theBloodAnalysisDate = aBloodAnalysisDate;}

	/** Артериальное давление */
	@Comment("Артериальное давление")
	@Persist
	public String getBloodPressure() {return theBloodPressure;}
	public void setBloodPressure(String aBloodPressure) {theBloodPressure = aBloodPressure;}

	/** Дата артериального давления */
	@Comment("Дата артериального давления")
	@Persist @DateString @DoDateString
	public String getBloodPressureDate() {return theBloodPressureDate;}
	public void setBloodPressureDate(String aBloodPressureDate) {theBloodPressureDate = aBloodPressureDate;}

	/** Количество занятий психопрофилактической подготовки */
	@Comment("Количество занятий психопрофилактической подготовки")
	@Persist
	public Integer getTrainingAmount() {return theTrainingAmount;}
	public void setTrainingAmount(Integer aTrainingAmount) {theTrainingAmount = aTrainingAmount;}

	/** Дата последнего занятия психопрофилактической подготовки */
	@Comment("Дата последнего занятия психопрофилактической подготовки")
	@Persist @DateString @DoDateString
	public String getLastTrainingDate() {return theLastTrainingDate;}
	public void setLastTrainingDate(String aLastTrainingDate) {theLastTrainingDate = aLastTrainingDate;}

	/** Предполагаемый срок родов */
	@Comment("Предполагаемый срок родов")
	@Persist
	public String getSupposedBithPeriod() {return theSupposedBithPeriod;}
	public void setSupposedBithPeriod(String aSupposedBithPeriod) {theSupposedBithPeriod = aSupposedBithPeriod;}

	
	/** Специальные замечания */
	@Comment("Специальные замечания")
	@Persist
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}

	/** Дата заполнения */
	@Comment("Дата заполнения")
	@Persist @DateString @DoDateString
	public String getFillingDate() {return theFillingDate;}
	public void setFillingDate(String aFillingDate) {theFillingDate = aFillingDate;}

	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return thePregnancy;}
	public void setPregnancy(Long aPregnancy) {thePregnancy = aPregnancy;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** История родов */
	@Comment("История родов")
	@Persist
	public Long getPregnancyHistory() {return thePregnancyHistory;}
	public void setPregnancyHistory(Long aPregnancyHistory) {thePregnancyHistory = aPregnancyHistory;}

	/** Не помнит, когда были последние месячные */
	@Comment("Не помнит, когда были последние месячные")
	@Persist
	public Boolean getDontRememberLastMensis() {return theDontRememberLastMensis;}
	public void setDontRememberLastMensis(Boolean aDontRememberLastMensis) {theDontRememberLastMensis = aDontRememberLastMensis;}
	
	/** Не посещала жк */
	@Comment("Не посещала жк")
	@Persist
	public Boolean getDontVisitCons() {return theDontVisitCons;}
	public void setDontVisitCons(Boolean aDontVisitCons) {theDontVisitCons = aDontVisitCons;}

	/** Не посещала жк */
	private Boolean theDontVisitCons;
	/** Не помнит, когда были последние месячные */
	private Boolean theDontRememberLastMensis;
	/** История родов */
	private Long thePregnancyHistory;
	/** Случай медицинского обслуживания */
	private Long theMedCase;
	/** Беременность */
	private Long thePregnancy;
	/** Предполагаемый срок родов */
	private String theSupposedBithPeriod;
	/** Специальные замечания */
	private String theNotes;
	/** Анализ мочи */
	private String theUrineAnalysis;
	/** Обследование беременной */
	private Long thePregnanInspection;
	/** Дата анализа мочи */
	private String theUrineAnalysisDate;
	/** Анализ крови */
	private String theBloodAnalysis;
	/** Дата анализа крови */
	private String theBloodAnalysisDate;
	/** Артериальное давление */
	private String theBloodPressure;
	/** Дата артериального давления */
	private String theBloodPressureDate;
	/** Количество занятий психопрофилактической подготовки */
	private Integer theTrainingAmount;
	/** Дата последнего занятия психопрофилактической подготовки */
	private String theLastTrainingDate;
	/** Дата заполнения */
	private String theFillingDate;
	/** Консультация */
	private Long theConsultation;
	/** Перенесенные заболевания */
	private String theTransmitDiseases;
	/** Предыдущие беременности */
	private String thePreviousPregnancies;
	/** Последние месячные */
	private String theLastMensis;
	/** Количество коротких беременностей */
	private Integer theShortPregnancyAmount;
	/** Количество родов */
	private Integer theChildbirthAmount;
	/** Первое посещение женской консультации */
	private String theFirstVisitDate;
	/** Количество визитов */
	private Integer theVisitsAmount;
	/** Особенности беременности */
	private String thePregnancyFeatures;
}
