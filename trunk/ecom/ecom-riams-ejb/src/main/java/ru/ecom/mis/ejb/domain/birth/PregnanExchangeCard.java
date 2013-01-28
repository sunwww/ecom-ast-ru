package ru.ecom.mis.ejb.domain.birth;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Обменная карта беременной (сведения о беременной)
 * @author azviagin
 *
 */
@Comment("Обменная карта беременной (сведения о беременной)")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "pregnancy" }) 
	}
)
public class PregnanExchangeCard extends BaseEntity {
	
	/** Консультация */
	@Comment("Консультация")
	@OneToOne
	public MisLpu getConsultation() {return theConsultation;}
	public void setConsultation(MisLpu aConsultation) {theConsultation = aConsultation;}
	
	@Transient @Comment("Консультация информация")
	public String getConsultationInfo() {
		return theConsultation!=null?theConsultation.getName():""; 
	}


	/** Перенесенные заболевания */
	@Comment("Перенесенные заболевания")
	public String getTransmitDiseases() {return theTransmitDiseases;}
	public void setTransmitDiseases(String aTransmitDiseases) {theTransmitDiseases = aTransmitDiseases;}

	
	/** Предыдущие беременности */
	@Comment("Предыдущие беременности")
	public String getPreviousPregnancies() {return thePreviousPregnancies;}
	public void setPreviousPregnancies(String aPreviousPregnancies) {thePreviousPregnancies = aPreviousPregnancies;}

	
	/** Последние месячные */
	@Comment("Последние месячные")
	public Date getLastMensis() {return theLastMensis;}
	public void setLastMensis(Date aLastMensis) {theLastMensis = aLastMensis;}

	/** Количество коротких беременностей */
	@Comment("Количество коротких беременностей")
	public Integer getShortPregnancyAmount() {return theShortPregnancyAmount;}
	public void setShortPregnancyAmount(Integer aShortPregnancyAmount) {theShortPregnancyAmount = aShortPregnancyAmount;}

	/** Количество родов */
	@Comment("Количество родов")
	public Integer getChildbirthAmount() {return theChildbirthAmount;}
	public void setChildbirthAmount(Integer aChildbirthAmount) {theChildbirthAmount = aChildbirthAmount;}

	/** Первое посещение женской консультации */
	@Comment("Первое посещение женской консультации")
	public Date getFirstVisitDate() {return theFirstVisitDate;}
	public void setFirstVisitDate(Date aFirstVisitDate) {theFirstVisitDate = aFirstVisitDate;}

	/** Количество визитов */
	@Comment("Количество визитов")
	public Integer getVisitsAmount() {return theVisitsAmount;}
	public void setVisitsAmount(Integer aVisitsAmount) {theVisitsAmount = aVisitsAmount;}

	
	/** Особенности беременности */
	@Comment("Особенности беременности")
	@Column(length=1000)
	public String getPregnancyFeatures() {return thePregnancyFeatures;}
	public void setPregnancyFeatures(String aPregnancyFeatures) {thePregnancyFeatures = aPregnancyFeatures;}

	/** Обследование беременной */
	@Comment("Обследование беременной")
	@OneToOne
	public PregnanInspection getPregnanInspection() {return thePregnanInspection;}
	public void setPregnanInspection(PregnanInspection aPregnanInspection) {thePregnanInspection = aPregnanInspection;}

	/** Анализ мочи */
	@Comment("Анализ мочи")
	public String getUrineAnalysis() {return theUrineAnalysis;}
	public void setUrineAnalysis(String aUrineAnalysis) {theUrineAnalysis = aUrineAnalysis;}

	/** Дата анализа мочи */
	@Comment("Дата анализа мочи")
	public Date getUrineAnalysisDate() {return theUrineAnalysisDate;}
	public void setUrineAnalysisDate(Date aUrineAnalysisDate) {theUrineAnalysisDate = aUrineAnalysisDate;}

	/** Анализ крови */
	@Comment("Анализ крови")
	public String getBloodAnalysis() {return theBloodAnalysis;}
	public void setBloodAnalysis(String aBloodAnalysis) {theBloodAnalysis = aBloodAnalysis;}

	/** Дата анализа крови */
	@Comment("Дата анализа крови")
	public Date getBloodAnalysisDate() {return theBloodAnalysisDate;}
	public void setBloodAnalysisDate(Date aBloodAnalysisDate) {theBloodAnalysisDate = aBloodAnalysisDate;}

	/** Артериальное давление */
	@Comment("Артериальное давление")
	public String getBloodPressure() {return theBloodPressure;}
	public void setBloodPressure(String aBloodPressure) {theBloodPressure = aBloodPressure;}

	/** Дата артериального давления */
	@Comment("Дата артериального давления")
	public Date getBloodPressureDate() {return theBloodPressureDate;}
	public void setBloodPressureDate(Date aBloodPressureDate) {theBloodPressureDate = aBloodPressureDate;}

	/** Количество занятий психопрофилактической подготовки */
	@Comment("Количество занятий психопрофилактической подготовки")
	public Integer getTrainingAmount() {return theTrainingAmount;}
	public void setTrainingAmount(Integer aTrainingAmount) {theTrainingAmount = aTrainingAmount;}

	/** Дата последнего занятия психопрофилактической подготовки */
	@Comment("Дата последнего занятия психопрофилактической подготовки")
	public Date getLastTrainingDate() {return theLastTrainingDate;}
	public void setLastTrainingDate(Date aLastTrainingDate) {theLastTrainingDate = aLastTrainingDate;}

	/** Предполагаемый срок родов */
	@Comment("Предполагаемый срок родов")
	public String getSupposedBithPeriod() {return theSupposedBithPeriod;}
	public void setSupposedBithPeriod(String aSupposedBithPeriod) {theSupposedBithPeriod = aSupposedBithPeriod;}

	
	/** Специальные замечания */
	@Comment("Специальные замечания")
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}

	/** Дата заполнения */
	@Comment("Дата заполнения")
	public Date getFillingDate() {return theFillingDate;}
	public void setFillingDate(Date aFillingDate) {theFillingDate = aFillingDate;}

	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return thePregnancy;}
	public void setPregnancy(Pregnancy aPregnancy) {thePregnancy = aPregnancy;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** История родов */
	@Comment("История родов")
	@OneToOne
	public PregnancyHistory getPregnancyHistory() {return thePregnancyHistory;}
	public void setPregnancyHistory(PregnancyHistory aPregnancyHistory) {thePregnancyHistory = aPregnancyHistory;}

	/** Не помнит, когда были последние месячные */
	@Comment("Не помнит, когда были последние месячные")
	public Boolean getDontRememberLastMensis() {return theDontRememberLastMensis;}
	public void setDontRememberLastMensis(Boolean aDontRememberLastMensis) {theDontRememberLastMensis = aDontRememberLastMensis;}
	
	/** Не посещала жк */
	@Comment("Не посещала жк")
	public Boolean getDontVisitCons() {return theDontVisitCons;}
	public void setDontVisitCons(Boolean aDontVisitCons) {theDontVisitCons = aDontVisitCons;}

	/** Не посещала жк */
	private Boolean theDontVisitCons;
	/** Не помнит, когда были последние месячные */
	private Boolean theDontRememberLastMensis;
	/** История родов */
	private PregnancyHistory thePregnancyHistory;
	/** Случай медицинского обслуживания */
	private MedCase theMedCase;
	/** Беременность */
	private Pregnancy thePregnancy;
	/** Предполагаемый срок родов */
	private String theSupposedBithPeriod;
	/** Специальные замечания */
	private String theNotes;
	/** Анализ мочи */
	private String theUrineAnalysis;
	/** Обследование беременной */
	private PregnanInspection thePregnanInspection;
	/** Дата анализа мочи */
	private Date theUrineAnalysisDate;
	/** Анализ крови */
	private String theBloodAnalysis;
	/** Дата анализа крови */
	private Date theBloodAnalysisDate;
	/** Артериальное давление */
	private String theBloodPressure;
	/** Дата артериального давления */
	private Date theBloodPressureDate;
	/** Количество занятий психопрофилактической подготовки */
	private Integer theTrainingAmount;
	/** Дата последнего занятия психопрофилактической подготовки */
	private Date theLastTrainingDate;
	/** Дата заполнения */
	private Date theFillingDate;
	/** Консультация */
	private MisLpu theConsultation;
	/** Перенесенные заболевания */
	private String theTransmitDiseases;
	/** Предыдущие беременности */
	private String thePreviousPregnancies;
	/** Последние месячные */
	private Date theLastMensis;
	/** Количество коротких беременностей */
	private Integer theShortPregnancyAmount;
	/** Количество родов */
	private Integer theChildbirthAmount;
	/** Первое посещение женской консультации */
	private Date theFirstVisitDate;
	/** Количество визитов */
	private Integer theVisitsAmount;
	/** Особенности беременности */
	private String thePregnancyFeatures;
}
