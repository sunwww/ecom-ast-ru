package ru.ecom.mis.ejb.form.medcase.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.medcase.hospital.BirthCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocChildbirthComplication;
import ru.ecom.mis.ejb.domain.medcase.voc.VocCongenitalAnomaly;
import ru.ecom.mis.ejb.domain.medcase.voc.VocLivebirthCriterion;
import ru.ecom.mis.ejb.domain.medcase.voc.VocNewbornComplication;
import ru.ecom.mis.ejb.domain.medcase.voc.VocObstetricProcedure;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPregnancyMedFactor;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPregnancyRiskFactor;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Случай рождения")
@EntityForm
@EntityFormPersistance(clazz=BirthCase.class)
@WebTrail(comment = "Случай рождения", nameProperties= "id", view="entityParentView-stac_birthCase.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/BirthCase")
public class BirthCaseForm extends IdEntityForm {

	/** Медицинский случай */
	@Comment("Медицинский случай")
	@Persist @Required
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
	/** Медицинский случай */
	private Long theMedCase;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Long thePatient;

	/** Мать */
	@Comment("Мать")
	@Persist
	public Long getMother() {return theMother;}
	public void setMother(Long aMother) {theMother = aMother;}
	/** Мать */
	private Long theMother;
	
	/** Дата родов */
	@Comment("Дата родов")
	@Persist @DateString @DoDateString
	public String getChildbirthDate() {return theChildbirthDate;}
	public void setChildbirthDate(String aChildbirthDate) {theChildbirthDate = aChildbirthDate;}
	/** Дата родов */
	private String theChildbirthDate;
	
	/** Время родов */
	@Comment("Время родов")
	@Persist @TimeString @DoTimeString
	public String getChildbirthTime() {return theChildbirthTime;}
	public void setChildbirthTime(String aChildbirthTime) {theChildbirthTime = aChildbirthTime;}
	/** Время родов */
	private String theChildbirthTime;
	
	/** Место родов */
	@Comment("Место родов")
	@Persist
	public Long getChildbirthPlace() {return theChildbirthPlace;}
	public void setChildbirthPlace(Long aChildbirthPlace) {theChildbirthPlace = aChildbirthPlace;}
	/** Место родов */
	private Long theChildbirthPlace;
	
	/** Которые по счету роды */
	@Comment("Которые по счету роды")
	@Persist
	public Integer getChildbirthNumber() {return theChildbirthNumber;}
	public void setChildbirthNumber(Integer aChildbirthNumber) {theChildbirthNumber = aChildbirthNumber;}
	/** Которые по счету роды */
	private Integer theChildbirthNumber;
	
	/** Которая по счету беременность */
	@Comment("Которая по счету беременность")
	@Persist
	public Integer getPregnancyNumber() {return thePregnancyNumber;}
	public void setPregnancyNumber(Integer aPregnancyNumber) {thePregnancyNumber = aPregnancyNumber;}
	/** Которая по счету беременность */
	private Integer thePregnancyNumber;
	
	/** Который по счету родившийся ребенок */
	@Comment("Который по счету родившийся ребенок")
	@Persist
	public Integer getChildNumber() {return theChildNumber;}
	public void setChildNumber(Integer aChildNumber) {theChildNumber = aChildNumber;}
	/** Который по счету родившийся ребенок */
	private Integer theChildNumber;
	
	/** Оценка по шкале Апгар */
	@Comment("Оценка по шкале Апгар")
	@Persist
	public Integer getApgarScaleEstimate() {return theApgarScaleEstimate;}
	public void setApgarScaleEstimate(Integer aApgarScaleEstimate) {theApgarScaleEstimate = aApgarScaleEstimate;}
	/** Оценка по шкале Апгар */
	private Integer theApgarScaleEstimate;
	
	/** Оценка по шкале Апгар через 5 минут */
	@Comment("Оценка по шкале Апгар через 5 минут")
	@Persist
	public Integer getApgarScaleEstimateFive() {return theApgarScaleEstimateFive;}
	public void setApgarScaleEstimateFive(Integer aApgarScaleEstimateFive) {theApgarScaleEstimateFive = aApgarScaleEstimateFive;}
	/** Оценка по шкале Апгар через 5 минут */
	private Integer theApgarScaleEstimateFive;
		
	/** Критерии живорождения */
	@Comment("Критерии живорождения")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocLivebirthCriterion.class)
	public String getLivebirthCriterion() {
		return theLivebirthCriterion;
	}

	public void setLivebirthCriterion(String aLivebirthCriterion) {
		theLivebirthCriterion = aLivebirthCriterion;
	}

	/** Критерии живорождения */
	private String theLivebirthCriterion;
	
	/** Ребенок родился по счету и качеству */
	@Comment("Ребенок родился по счету и качеству")
	@Persist
	public Long getOrderAndQuantity() {return theOrderAndQuantity;}
	public void setOrderAndQuantity(Long aOrderAndQuantity) {theOrderAndQuantity = aOrderAndQuantity;}
	/** Ребенок родился по счету и качеству */
	private Long theOrderAndQuantity;
	
	/** Вес ребенка при рождении */
	@Comment("Вес ребенка при рождении")
	@Persist
	public Integer getChildBirthWeight() {return theChildBirthWeight;}
	public void setChildBirthWeight(Integer aChildBirthWeight) {theChildBirthWeight = aChildBirthWeight;}
	/** Вес ребенка при рождении */
	private Integer theChildBirthWeight;
	
	/** Рост ребенка при рождении */
	@Comment("Рост ребенка при рождении")
	@Persist
	public Integer getChildBirthHeight() {return theChildBirthHeight;}
	public void setChildBirthHeight(Integer aChildBirthHeight) {theChildBirthHeight = aChildBirthHeight;}
	/** Рост ребенка при рождении */
	private Integer theChildBirthHeight;
	
	/** Медицинские факторы настоящей беременности */
	@Comment("Медицинские факторы настоящей беременности")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocPregnancyMedFactor.class)
	public String getPregnancyMedFactors() {return thePregnancyMedFactors;}
	public void setPregnancyMedFactors(String aPregnancyMedFactors) {thePregnancyMedFactors = aPregnancyMedFactors;}
	/** Медицинские факторы настоящей беременности */
	private String thePregnancyMedFactors;
	
	/** Прочие факторы риска во время беременности */
	@Comment("Прочие факторы риска во время беременности")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocPregnancyRiskFactor.class)
	public String getPregnancyRiskFactors() {return thePregnancyRiskFactors;}
	public void setPregnancyRiskFactors(String aPregnancyRiskFactors) {thePregnancyRiskFactors = aPregnancyRiskFactors;}
	/** Прочие факторы риска во время беременности */
	private String thePregnancyRiskFactors;
	
	/** Осложнения родов */
	@Comment("Осложнения родов")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocChildbirthComplication.class)
	public String getChildbirthComplications() {return theChildbirthComplications;}
	public void setChildbirthComplications(String aChildbirthComplications) {theChildbirthComplications = aChildbirthComplications;}
	/** Осложнения родов */
	private String theChildbirthComplications;
	
	/** Акушерские процедуры */
	@Comment("Акушерские процедуры")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocObstetricProcedure.class)
	public String getObstetricProcedures() {return theObstetricProcedures;}
	public void setObstetricProcedures(String aObstetricProcedures) {theObstetricProcedures = aObstetricProcedures;}
	/** Акушерские процедуры */
	private String theObstetricProcedures;
	
	/** Осложнения новорожденного */
	@Comment("Осложнения новорожденного")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocNewbornComplication.class)
	public String getNewbornComplications() {return theNewbornComplications;}
	public void setNewbornComplications(String aNewbornComplications) {theNewbornComplications = aNewbornComplications;}
	/** Осложнения новорожденного */
	private String theNewbornComplications;
	
	/** Врожденные аномалии развития */
	@Comment("Врожденные аномалии развития")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocCongenitalAnomaly.class)
	public String getCongenitalAnomalies() {return theCongenitalAnomalies;}
	public void setCongenitalAnomalies(String aCongenitalAnomalies) {theCongenitalAnomalies = aCongenitalAnomalies;}
	/** Врожденные аномалии развития */
	private String theCongenitalAnomalies;
		
	/** Врач */
	@Comment("Врач")
	@Persist
	public Long getBirthWitness() {return theBirthWitness;}
	public void setBirthWitness(Long aBirthWitness) {theBirthWitness = aBirthWitness;}
	/** Врач */
	private Long theBirthWitness;
}
