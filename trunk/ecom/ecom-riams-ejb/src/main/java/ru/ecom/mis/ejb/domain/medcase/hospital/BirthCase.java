package ru.ecom.mis.ejb.domain.medcase.hospital;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.document.ejb.domain.certificate.BirthCertificate;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocChildbirthComplication;
import ru.ecom.mis.ejb.domain.medcase.voc.VocChildbirthPlace;
import ru.ecom.mis.ejb.domain.medcase.voc.VocCongenitalAnomaly;
import ru.ecom.mis.ejb.domain.medcase.voc.VocLivebirthCriterion;
import ru.ecom.mis.ejb.domain.medcase.voc.VocNewbornComplication;
import ru.ecom.mis.ejb.domain.medcase.voc.VocObstetricProcedure;
import ru.ecom.mis.ejb.domain.medcase.voc.VocOrderAndQuantity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPregnancyMedFactor;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPregnancyRiskFactor;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Случай рождения
 * @author oegorova
 *
 */
@Comment("Случай рождения")
@Entity
@AIndexes({
	@AIndex(properties="medCase"),
	@AIndex(properties="patient")
    }) 
@Table(schema="SQLUser")
public class BirthCase extends BaseEntity {
	
	/** Медицинский случай */
	@Comment("Медицинский случай")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
	/** Медицинский случай */
	private MedCase theMedCase;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Patient thePatient;

	/** Мать */
	@Comment("Мать")
	@OneToOne
	public Patient getMother() {return theMother;}
	public void setMother(Patient aMother) {theMother = aMother;}
	/** Мать */
	private Patient theMother;
	
	/** Дата родов */
	@Comment("Дата родов")
	public Date getChildbirthDate() {return theChildbirthDate;}
	public void setChildbirthDate(Date aChildbirthDate) {theChildbirthDate = aChildbirthDate;}
	/** Дата родов */
	private Date theChildbirthDate;
	
	/** Время родов */
	@Comment("Время родов")
	public Time getChildbirthTime() {return theChildbirthTime;}
	public void setChildbirthTime(Time aChildbirthTime) {theChildbirthTime = aChildbirthTime;}
	/** Время родов */
	private Time theChildbirthTime;
	
	/** Место родов */
	@Comment("Место родов")
	@OneToOne
	public VocChildbirthPlace getChildbirthPlace() {return theChildbirthPlace;}
	public void setChildbirthPlace(VocChildbirthPlace aChildbirthPlace) {theChildbirthPlace = aChildbirthPlace;}
	/** Место родов */
	private VocChildbirthPlace theChildbirthPlace;
	
	/** Которые по счету роды */
	@Comment("Которые по счету роды")
	public Integer getChildbirthNumber() {return theChildbirthNumber;}
	public void setChildbirthNumber(Integer aChildbirthNumber) {theChildbirthNumber = aChildbirthNumber;}
	/** Которые по счету роды */
	private Integer theChildbirthNumber;
	
	/** Которая по счету беременность */
	@Comment("Которая по счету беременность")
	public Integer getPregnancyNumber() {return thePregnancyNumber;}
	public void setPregnancyNumber(Integer aPregnancyNumber) {thePregnancyNumber = aPregnancyNumber;}
	/** Которая по счету беременность */
	private Integer thePregnancyNumber;
	
	/** Который по счету родившийся ребенок */
	@Comment("Который по счету родившийся ребенок")
	public Integer getChildNumber() {return theChildNumber;}
	public void setChildNumber(Integer aChildNumber) {theChildNumber = aChildNumber;}
	/** Который по счету родившийся ребенок */
	private Integer theChildNumber;
	
	/** Оценка по шкале Апгар */
	@Comment("Оценка по шкале Апгар")
	public Integer getApgarScaleEstimate() {return theApgarScaleEstimate;}
	public void setApgarScaleEstimate(Integer aApgarScaleEstimate) {theApgarScaleEstimate = aApgarScaleEstimate;}
	/** Оценка по шкале Апгар */
	private Integer theApgarScaleEstimate;
	
	/** Оценка по шкале Апгар через 5 минут */
	@Comment("Оценка по шкале Апгар через 5 минут")
	public Integer getApgarScaleEstimateFive() {return theApgarScaleEstimateFive;}
	public void setApgarScaleEstimateFive(Integer aApgarScaleEstimateFive) {theApgarScaleEstimateFive = aApgarScaleEstimateFive;}
	/** Оценка по шкале Апгар через 5 минут */
	private Integer theApgarScaleEstimateFive;
	
	/** Критерии живорождения */
	@Comment("Критерии живорождения")
	@ManyToMany
	public List<VocLivebirthCriterion> getLivebirthCriterion() {return theLivebirthCriterion;}
	public void setLivebirthCriterion(List<VocLivebirthCriterion> aLivebirthCriterion) {theLivebirthCriterion = aLivebirthCriterion;}
	/** Критерии живорождения */
	private List<VocLivebirthCriterion> theLivebirthCriterion;
	
	/** Ребенок родился по счету и качеству */
	@Comment("Ребенок родился по счету и качеству")
	@OneToOne
	public VocOrderAndQuantity getOrderAndQuantity() {return theOrderAndQuantity;}
	public void setOrderAndQuantity(VocOrderAndQuantity aOrderAndQuantity) {theOrderAndQuantity = aOrderAndQuantity;}
	/** Ребенок родился по счету и качеству */
	private VocOrderAndQuantity theOrderAndQuantity;
	
	/** Вес ребенка при рождении */
	@Comment("Вес ребенка при рождении")
	public Integer getChildBirthWeight() {return theChildBirthWeight;}
	public void setChildBirthWeight(Integer aChildBirthWeight) {theChildBirthWeight = aChildBirthWeight;}
	/** Вес ребенка при рождении */
	private Integer theChildBirthWeight;
	
	/** Рост ребенка при рождении */
	@Comment("Рост ребенка при рождении")
	public Integer getChildBirthHeight() {return theChildBirthHeight;}
	public void setChildBirthHeight(Integer aChildBirthHeight) {theChildBirthHeight = aChildBirthHeight;}
	/** Рост ребенка при рождении */
	private Integer theChildBirthHeight;
	
	/** Медицинские факторы настоящей беременности */
	@Comment("Медицинские факторы настоящей беременности")
	@ManyToMany
	public List<VocPregnancyMedFactor> getPregnancyMedFactors() {return thePregnancyMedFactors;}
	public void setPregnancyMedFactors(List<VocPregnancyMedFactor> aPregnancyMedFactors) {thePregnancyMedFactors = aPregnancyMedFactors;}
	/** Медицинские факторы настоящей беременности */
	private List<VocPregnancyMedFactor> thePregnancyMedFactors;
	
	/** Прочие факторы риска во время беременности */
	@Comment("Прочие факторы риска во время беременности")
	@ManyToMany
	public List<VocPregnancyRiskFactor> getPregnancyRiskFactors() {return thePregnancyRiskFactors;}
	public void setPregnancyRiskFactors(List<VocPregnancyRiskFactor> aPregnancyRiskFactors) {thePregnancyRiskFactors = aPregnancyRiskFactors;}
	/** Прочие факторы риска во время беременности */
	private List<VocPregnancyRiskFactor> thePregnancyRiskFactors;
	
	/** Осложнения родов */
	@Comment("Осложнения родов")
	@ManyToMany
	public List<VocChildbirthComplication> getChildbirthComplications() {return theChildbirthComplications;}
	public void setChildbirthComplications(List<VocChildbirthComplication> aChildbirthComplications) {theChildbirthComplications = aChildbirthComplications;}
	/** Осложнения родов */
	private List<VocChildbirthComplication> theChildbirthComplications;
	
	/** Акушерские процедуры */
	@Comment("Акушерские процедуры")
	@ManyToMany
	public List<VocObstetricProcedure> getObstetricProcedures() {return theObstetricProcedures;}
	public void setObstetricProcedures(List<VocObstetricProcedure> aObstetricProcedures) {theObstetricProcedures = aObstetricProcedures;}
	/** Акушерские процедуры */
	private List<VocObstetricProcedure> theObstetricProcedures;
	
	/** Осложнения новорожденного */
	@Comment("Осложнения новорожденного")
	@ManyToMany
	public List<VocNewbornComplication> getNewbornComplications() {return theNewbornComplications;}
	public void setNewbornComplications(List<VocNewbornComplication> aNewbornComplications) {theNewbornComplications = aNewbornComplications;}
	/** Осложнения новорожденного */
	private List<VocNewbornComplication> theNewbornComplications;
	
	/** Врожденные аномалии развития */
	@Comment("Врожденные аномалии развития")
	@ManyToMany
	public List<VocCongenitalAnomaly> getCongenitalAnomalies() {return theCongenitalAnomalies;}
	public void setCongenitalAnomalies(List<VocCongenitalAnomaly> aCongenitalAnomalies) {theCongenitalAnomalies = aCongenitalAnomalies;}
	/** Врожденные аномалии развития */
	private List<VocCongenitalAnomaly> theCongenitalAnomalies;
	
	/** Свидетельства о рождении */
	@Comment("Свидетельства о рождении")
	@OneToMany(mappedBy="birthCase", cascade=CascadeType.ALL)
	public List<BirthCertificate> getBirthCertificate() {return theBirthCertificate;}
	public void setBirthCertificate(List<BirthCertificate> aBirthCertificate) {theBirthCertificate = aBirthCertificate;}
	/** Свидетельства о рождении */
	private List<BirthCertificate> theBirthCertificate;
	
	/** Врач */
	@Comment("Врач")
	@OneToOne
	public WorkFunction getBirthWitness() {return theBirthWitness;}
	public void setBirthWitness(WorkFunction aBirthWitness) {theBirthWitness = aBirthWitness;}
	/** Врач */
	private WorkFunction theBirthWitness;
	
}
