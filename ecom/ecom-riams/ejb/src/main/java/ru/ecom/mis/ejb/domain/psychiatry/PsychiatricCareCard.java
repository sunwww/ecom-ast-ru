package ru.ecom.mis.ejb.domain.psychiatry;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychDeathReason;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychObservationReason;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychStrikeOffReason;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Карта обратившегося за психиатрической помощью (УФ 030-1/у-02)
  */
 @Comment("Карта обратившегося за психиатрической помощью (УФ 030-1/у-02)")
@Entity
@AIndexes({
	@AIndex(properties={"patient"})
	,@AIndex(properties={"cardNumber"})
})
@Table(schema="SQLUser")
 @Getter
 @Setter
public class PsychiatricCareCard extends BaseEntity{
 /**
  * Дата взятия на учет
  */
 private Date startDate;
 /**
  * Дата окончания учета
  */
 private Date finishDate;
 /**
  * Впервые в жизни заболевший
  */
 private Boolean firstTimeDiseased;
 /**
  * Причина прекращения наблюдения
  */
 @Comment("Причина прекращения наблюдения")
 @OneToOne
 public VocPsychStrikeOffReason getStrikeOffReason() {
  return strikeOffReason;
 }
 /**
  * Причина прекращения наблюдения
  */
 private VocPsychStrikeOffReason strikeOffReason;
 /**
  * Примечания
  */
 private String notes;
 /**
  * Участки ЛПУ
  */
 @Comment("Участки ЛПУ")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 public List<LpuAreaPsychCareCard> getLpuAreas() {
  return lpuAreas;
 }
 /**
  * Участки ЛПУ
  */
 private List<LpuAreaPsychCareCard> lpuAreas;
 /**
  * Номер диспансерной карты
  */
 private String cardNumber;
 /**
  * Наблюдения
  */
 @Comment("Наблюдения")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("startDate")
 public List<PsychiaticObservation> getObservations() {
  return observations;
 }
 /**
  * Наблюдения
  */
 private List<PsychiaticObservation> observations;

 /**
  * Общественно-опасные события
  */
 @Comment("Общественно-опасные события")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("effectDate")
 public List<PublicDangerousEffect> getPublicDangerousEffects() {
  return publicDangerousEffects;
 }
 /**
  * Общественно-опасные события
  */
 private List<PublicDangerousEffect> publicDangerousEffects;
 /**
  * Принудительные лечения
  */
 @Comment("Принудительные лечения")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("decisionDate")
 public List<CompulsoryTreatment> getCompulsoryTreatments() {
  return compulsoryTreatments;
 }
 /**
  * Принудительные лечения
  */
 private List<CompulsoryTreatment> compulsoryTreatments;
 /**
  * Состояния здоровья
  */
 @Comment("Состояния здоровья")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("startDate")
 public List<PsychicHealthState> getHealthState() {
  return healthState;
 }
 /**
  * Состояния здоровья
  */
 private List<PsychicHealthState> healthState;
 /**
  * Судебно-психиатрические экспертизы
  */
 @Comment("Судебно-психиатрические экспертизы")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("examinationDate")
 public List<PsychiatricExamination> getExaminations() {
  return examinations;
 }
 /**
  * Судебно-психиатрические экспертизы
  */
 private List<PsychiatricExamination> examinations;
 /**
  * Дата начала заболевания
  */
 private Date illnessStartDate;
 /**
  * Дата первого обращения к психиатру
  */
 private Date firstPsychiatricVisitDate;
 /**
  * Дата последнего недобровольного освидетельствования
  */
 private Date lastInvoluntaryExamDate;
 /**
  * Судимость до обращения к психиатру
  */
 private Boolean convictionsBeforeCare;
 /**
  * Описание прочей причины прекращения наблюдения
  */
 private String strikeOffOtherReasonNotes;
 /**
  * Дата смерти
  */
 private Date deathDate;
 /**
  * Причина смерти
  */
 @Comment("Причина смерти")
 @OneToOne
 public VocPsychDeathReason getDeathReason() {
  return deathReason;
 }
 /**
  * Причина смерти
  */
 private VocPsychDeathReason deathReason;
 /**
  * Причина наблюдения
  */
 @Comment("Причина наблюдения")
 @OneToOne
 public VocPsychObservationReason getObservationReason() {
  return observationReason;
 }
 /**
  * Причина наблюдения
  */
 private VocPsychObservationReason observationReason;
 /**
  * Пациент
  */
 @Comment("Пациент")
 @OneToOne
 public Patient getPatient() {
  return patient;
 }
 /**
  * Пациент
  */
 private Patient patient;

 /**
  * Регистратор *
  */
 private String registrator;
 /** Дата заведения карты */
 private Date dateRegistration;
 	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return lpu;
	}
	/** ЛПУ */
	private MisLpu lpu;
}
