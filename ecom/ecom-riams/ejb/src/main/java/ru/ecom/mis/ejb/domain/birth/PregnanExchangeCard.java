package ru.ecom.mis.ejb.domain.birth;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class PregnanExchangeCard extends BaseEntity {
	
	/** Консультация */
	@Comment("Консультация")
	@OneToOne
	public MisLpu getConsultation() {return consultation;}

	@Transient @Comment("Консультация информация")
	public String getConsultationInfo() {
		return consultation!=null?consultation.getName():"";
	}

	/** Особенности беременности */
	@Comment("Особенности беременности")
	@Column(length=1000)
	public String getPregnancyFeatures() {return pregnancyFeatures;}

	/** Обследование беременной */
	@Comment("Обследование беременной")
	@OneToOne
	public PregnanInspection getPregnanInspection() {return pregnanInspection;}

	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return pregnancy;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** История родов */
	@Comment("История родов")
	@OneToOne
	public PregnancyHistory getPregnancyHistory() {return pregnancyHistory;}

	/** Не посещала жк */
	private Boolean dontVisitCons;
	/** Не помнит, когда были последние месячные */
	private Boolean dontRememberLastMensis;
	/** История родов */
	private PregnancyHistory pregnancyHistory;
	/** Случай медицинского обслуживания */
	private MedCase medCase;
	/** Беременность */
	private Pregnancy pregnancy;
	/** Предполагаемый срок родов */
	private String supposedBithPeriod;
	/** Специальные замечания */
	private String notes;
	/** Анализ мочи */
	private String urineAnalysis;
	/** Обследование беременной */
	private PregnanInspection pregnanInspection;
	/** Дата анализа мочи */
	private Date urineAnalysisDate;
	/** Анализ крови */
	private String bloodAnalysis;
	/** Дата анализа крови */
	private Date bloodAnalysisDate;
	/** Артериальное давление */
	private String bloodPressure;
	/** Дата артериального давления */
	private Date bloodPressureDate;
	/** Количество занятий психопрофилактической подготовки */
	private Integer trainingAmount;
	/** Дата последнего занятия психопрофилактической подготовки */
	private Date lastTrainingDate;
	/** Дата заполнения */
	private Date fillingDate;
	/** Консультация */
	private MisLpu consultation;
	/** Перенесенные заболевания */
	private String transmitDiseases;
	/** Предыдущие беременности */
	private String previousPregnancies;
	/** Последние месячные */
	private Date lastMensis;
	/** Количество коротких беременностей */
	private Integer shortPregnancyAmount;
	/** Количество родов */
	private Integer childbirthAmount;
	/** Первое посещение женской консультации */
	private Date firstVisitDate;
	/** Количество визитов */
	private Integer visitsAmount;
	/** Особенности беременности */
	private String pregnancyFeatures;
}
