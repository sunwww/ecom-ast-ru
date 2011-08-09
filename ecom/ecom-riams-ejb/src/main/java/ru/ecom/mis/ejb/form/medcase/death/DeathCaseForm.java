package ru.ecom.mis.ejb.form.medcase.death;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeathEvidence;
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

@Comment("Случай смерти")
@EntityForm
@EntityFormPersistance(clazz=DeathCase.class)
@WebTrail(comment = "Случай смерти", nameProperties= "id", view="entityParentView-stac_deathCase.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/DeathCase")
public class DeathCaseForm extends IdEntityForm{
	/** Мед. случай */
	@Comment("Мед. случай")
	@Persist @Required
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Мать */
	@Comment("Мать")
	@Persist
	public Long getMother() {return theMother;}
	public void setMother(Long aMother) {theMother = aMother;}

	/** Дата смерти */
	@Comment("Дата смерти")
	@DateString @DoDateString @Persist
	public String getDeathDate() {return theDeathDate;	}
	public void setDeathDate(String aDateDeath) {theDeathDate = aDateDeath;}

	/** Время смерти */
	@Comment("Время смерти")
	@TimeString @DoTimeString @Persist
	public String getDeathTime() {return theDeathTime;}
	public void setDeathTime(String aTimeDeath) {theDeathTime = aTimeDeath;}

	/** Доношенность */
	@Comment("Доношенность")
	@Persist
	public Long getIsPrematurity() {return theIsPrematurity;}
	public void setIsPrematurity(Long aIsPrematurity) {theIsPrematurity = aIsPrematurity;}

	/** Масса (вес) при рождении (грамм) */
	@Comment("Масса (вес) при рождении (грамм)")
	@Persist
	public Integer getBirthWeight() {return theBirthWeight;}
	public void setBirthWeight(Integer aBirthWeight) {theBirthWeight = aBirthWeight;}

	/** Какой ребенок по счету у матери */
	@Comment("Какой ребенок по счету у матери")
	@Persist 
	public Integer getBabyNumber() {return theBabyNumber;}
	public void setBabyNumber(Integer aBabyNumber) {theBabyNumber = aBabyNumber;}

	/** Место смерти */
	@Comment("Место смерти")
	@Persist
	public Long getDeathPlace() {return theDeathPlace;}
	public void setDeathPlace(Long aDeathPlace) {theDeathPlace = aDeathPlace;	}

	/** Адрес места смерти */
	@Comment("Адрес места смерти")
	@Persist
	public Long getDeathPlaceAddress() {return theDeathPlaceAddress;}
	public void setDeathPlaceAddress(Long aDeathPlaceAddress) {theDeathPlaceAddress = aDeathPlaceAddress;}

	/** Причина смерти */
	@Comment("Причина смерти")
	@Persist
	public Long getDeathReason() {return theDeathReason;	}
	public void setDeathReason(Long aDeathReason) {theDeathReason = aDeathReason;}

	/** Дата травмы (отравления) */
	@Comment("Дата травмы (отравления)")
	@Persist @DateString @DoDateString
	public String getAccidentDate() {return theAccidentDate;}
	public void setAccidentDate(String aAccidentDate) {theAccidentDate = aAccidentDate;}

	/** Место, при которых произошла травма (отравление) */
	@Comment("Место, при которых произошла травма (отравление)")
	@Persist
	public String getAccidentPlace() {return theAccidentPlace;}
	public void setAccidentPlace(String aAccidentPlace) {theAccidentPlace = aAccidentPlace;}

	/** Обстоятельства, при которых произошла травма (отравление) */
	@Comment("Обстоятельства, при которых произошла травма (отравление)")
	@Persist
	public String getAccidentCircumstance() {return theAccidentCircumstance;}
	public void setAccidentCircumstance(String aCircumstance) {theAccidentCircumstance = aCircumstance;}

	/** Вид травмы */
	@Comment("Вид травмы")
	@Persist
	public Long getAccidentSubType() {return theAccidentSubType;}
	public void setAccidentSubType(Long aAccidentSubType) {theAccidentSubType = aAccidentSubType;}

	/** Врач (фельдшер) */
	@Comment("Врач (фельдшер)")
	@Persist @Required
	public Long getDeathWitness() {return theDeathWitness;}
	public void setDeathWitness(Long aDeathWitness) {theDeathWitness = aDeathWitness;}

	/** Причина смерти установлена */
	@Comment("Причина смерти установлена")
	@Persist
	public Long getDeathWitnessFunction() {return theDeathWitnessFunction;}
	public void setDeathWitnessFunction(Long aDeathWitnessFunction) {theDeathWitnessFunction = aDeathWitnessFunction;}

	/** На основании чего установлена смерть */
	@Comment("На основании чего установлена смерть")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocDeathEvidence.class)
	public String getDeathEvidence() {return theDeathEvidence;}
	public void setDeathEvidence(String aDeathEvidence) {theDeathEvidence = aDeathEvidence;	}

	/** Умерла после окончания родов */
	@Comment("Умерла после окончания родов")
	@Persist
	public Long getAfterPregnance() {return theAfterPregnance;}
	public void setAfterPregnance(Long aAfterPregnance) {theAfterPregnance = aAfterPregnance;}
	
	/** Место рождения */
	@Comment("Место рождения")
	@Persist
	public String getBirthPlace() {
		return theBirthPlace;
	}

	public void setBirthPlace(String aBirthPlace) {
		theBirthPlace = aBirthPlace;
	}

	/** Место рождения */
	private String theBirthPlace;
	
	/** Адрес места рождения */
	@Comment("Адрес места рождения")
	@Persist
	public String getBirthPlaceAdress() {
		return theBirthPlaceAdress;
	}

	public void setBirthPlaceAdress(String aBirthPlaceAdress) {
		theBirthPlaceAdress = aBirthPlaceAdress;
	}

	/** Адрес места рождения */
	private String theBirthPlaceAdress;
	/** Умерла после окончания родов */
	private Long theAfterPregnance;
	/** На основании чего установлена смерть */
	private String theDeathEvidence;
	/** Причина смерти установлена */
	private Long theDeathWitnessFunction;
	/** Врач (фельдшер) */
	private Long theDeathWitness;
	/** Вид травмы */
	private Long theAccidentSubType;
	/** Обстоятельства, при которых произошла травма (отравление) */
	private String theAccidentCircumstance;
	/** Место, при которых произошла травма (отравление) */
	private String theAccidentPlace;
	/** Дата травмы (отравления) */
	private String theAccidentDate;
	/** Причина смерти */
	private Long theDeathReason;
	/** Адрес места смерти */
	private Long theDeathPlaceAddress;
	/** Место смерти */
	private Long theDeathPlace;
	/** Какой ребенок по счету у матери */
	private Integer theBabyNumber;
	/** Масса (вес) при рождении (грамм) */
	private Integer theBirthWeight;
	/** Доношенность */
	private Long theIsPrematurity;
	/** Время смерти */
	private String theDeathTime;
	/** Дата смерти */
	private String theDeathDate;
	/** Мать */
	private Long theMother;
	/** Пациент */
	private Long thePatient;
	/** Мед. случай */
	private Long theMedCase;
}
