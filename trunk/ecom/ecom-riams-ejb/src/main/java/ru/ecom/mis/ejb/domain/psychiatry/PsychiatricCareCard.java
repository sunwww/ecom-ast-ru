package ru.ecom.mis.ejb.domain.psychiatry;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
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
public class PsychiatricCareCard extends BaseEntity{
 /**
  * Дата взятия на учет
  */
 @Comment("Дата взятия на учет")
 public Date getStartDate() {
  return theStartDate;
 }
 public void setStartDate(Date aStartDate) {
  theStartDate = aStartDate;
 }
 /**
  * Дата взятия на учет
  */
 private Date theStartDate;
 /**
  * Дата окончания учета
  */
 @Comment("Дата окончания учета")
 public Date getFinishDate() {
  return theFinishDate;
 }
 public void setFinishDate(Date aFinishDate) {
  theFinishDate = aFinishDate;
 }
 /**
  * Дата окончания учета
  */
 private Date theFinishDate;
 /**
  * Впервые в жизни заболевший
  */
 @Comment("Впервые в жизни заболевший")
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
 @OneToOne
 public VocPsychStrikeOffReason getStrikeOffReason() {
  return theStrikeOffReason;
 }
 public void setStrikeOffReason(VocPsychStrikeOffReason aStrikeOffReason) {
  theStrikeOffReason = aStrikeOffReason;
 }
 /**
  * Причина прекращения наблюдения
  */
 private VocPsychStrikeOffReason theStrikeOffReason;
 /**
  * Примечания
  */
 @Comment("Примечания")
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
  * Участки ЛПУ
  */
 @Comment("Участки ЛПУ")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 public List<LpuAreaPsychCareCard> getLpuAreas() {
  return theLpuAreas;
 }
 public void setLpuAreas(List<LpuAreaPsychCareCard> aLpuAreas) {
  theLpuAreas = aLpuAreas;
 }
 /**
  * Участки ЛПУ
  */
 private List<LpuAreaPsychCareCard> theLpuAreas;
 /**
  * Номер диспансерной карты
  */
 @Comment("Номер диспансерной карты")
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
  * Наблюдения
  */
 @Comment("Наблюдения")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("startDate")
 public List<PsychiaticObservation> getObservations() {
  return theObservations;
 }
 public void setObservations(List<PsychiaticObservation> aObservations) {
  theObservations = aObservations;
 }
 /**
  * Наблюдения
  */
 private List<PsychiaticObservation> theObservations;
 /**
  * Суициды
  */
 @Comment("Суициды")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("fulfilmentDate")
 public List<Suicide> getSuicides() {
  return theSuicides;
 }
 public void setSuicides(List<Suicide> aSuicides) {
  theSuicides = aSuicides;
 }
 /**
  * Суициды
  */
 private List<Suicide> theSuicides;
 /**
  * Общественно-опасные события
  */
 @Comment("Общественно-опасные события")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("effectDate")
 public List<PublicDangerousEffect> getPublicDangerousEffects() {
  return thePublicDangerousEffects;
 }
 public void setPublicDangerousEffects(List<PublicDangerousEffect> aPublicDangerousEffects) {
  thePublicDangerousEffects = aPublicDangerousEffects;
 }
 /**
  * Общественно-опасные события
  */
 private List<PublicDangerousEffect> thePublicDangerousEffects;
 /**
  * Принудительные лечения
  */
 @Comment("Принудительные лечения")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("decisionDate")
 public List<CompulsoryTreatment> getCompulsoryTreatments() {
  return theCompulsoryTreatments;
 }
 public void setCompulsoryTreatments(List<CompulsoryTreatment> aCompulsoryTreatments) {
  theCompulsoryTreatments = aCompulsoryTreatments;
 }
 /**
  * Принудительные лечения
  */
 private List<CompulsoryTreatment> theCompulsoryTreatments;
 /**
  * Состояния здоровья
  */
 @Comment("Состояния здоровья")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("startDate")
 public List<PsychicHealthState> getHealthState() {
  return theHealthState;
 }
 public void setHealthState(List<PsychicHealthState> aHealthState) {
  theHealthState = aHealthState;
 }
 /**
  * Состояния здоровья
  */
 private List<PsychicHealthState> theHealthState;
 /**
  * Судебно-психиатрические экспертизы
  */
 @Comment("Судебно-психиатрические экспертизы")
 @OneToMany(mappedBy="careCard", cascade=CascadeType.ALL)
 @OrderBy("examinationDate")
 public List<PsychiatricExamination> getExaminations() {
  return theExaminations;
 }
 public void setExaminations(List<PsychiatricExamination> aExaminations) {
  theExaminations = aExaminations;
 }
 /**
  * Судебно-психиатрические экспертизы
  */
 private List<PsychiatricExamination> theExaminations;
 /**
  * Дата начала заболевания
  */
 @Comment("Дата начала заболевания")
 public Date getIllnessStartDate() {
  return theIllnessStartDate;
 }
 public void setIllnessStartDate(Date aIllnessStartDate) {
  theIllnessStartDate = aIllnessStartDate;
 }
 /**
  * Дата начала заболевания
  */
 private Date theIllnessStartDate;
 /**
  * Дата первого обращения к психиатру
  */
 @Comment("Дата первого обращения к психиатру")
 public Date getFirstPsychiatricVisitDate() {
  return theFirstPsychiatricVisitDate;
 }
 public void setFirstPsychiatricVisitDate(Date aFirstPsychiatricVisitDate) {
  theFirstPsychiatricVisitDate = aFirstPsychiatricVisitDate;
 }
 /**
  * Дата первого обращения к психиатру
  */
 private Date theFirstPsychiatricVisitDate;
 /**
  * Дата последнего недобровольного освидетельствования
  */
 @Comment("Дата последнего недобровольного освидетельствования")
 public Date getLastInvoluntaryExamDate() {
  return theLastInvoluntaryExamDate;
 }
 public void setLastInvoluntaryExamDate(Date aLastInvoluntaryExamDate) {
  theLastInvoluntaryExamDate = aLastInvoluntaryExamDate;
 }
 /**
  * Дата последнего недобровольного освидетельствования
  */
 private Date theLastInvoluntaryExamDate;
 /**
  * Судимость до обращения к психиатру
  */
 @Comment("Судимость до обращения к психиатру")
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
 public Date getDeathDate() {
  return theDeathDate;
 }
 public void setDeathDate(Date aDeathDate) {
  theDeathDate = aDeathDate;
 }
 /**
  * Дата смерти
  */
 private Date theDeathDate;
 /**
  * Причина смерти
  */
 @Comment("Причина смерти")
 @OneToOne
 public VocPsychDeathReason getDeathReason() {
  return theDeathReason;
 }
 public void setDeathReason(VocPsychDeathReason aDeathReason) {
  theDeathReason = aDeathReason;
 }
 /**
  * Причина смерти
  */
 private VocPsychDeathReason theDeathReason;
 /**
  * Причина наблюдения
  */
 @Comment("Причина наблюдения")
 @OneToOne
 public VocPsychObservationReason getObservationReason() {
  return theObservationReason;
 }
 public void setObservationReason(VocPsychObservationReason aObservationReason) {
  theObservationReason = aObservationReason;
 }
 /**
  * Причина наблюдения
  */
 private VocPsychObservationReason theObservationReason;
 /**
  * Пациент
  */
 @Comment("Пациент")
 @OneToOne
 public Patient getPatient() {
  return thePatient;
 }
 public void setPatient(Patient aPatient) {
  thePatient = aPatient;
 }
 /**
  * Пациент
  */
 private Patient thePatient;
}
