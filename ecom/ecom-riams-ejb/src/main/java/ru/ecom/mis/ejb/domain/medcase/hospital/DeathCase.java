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

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.document.ejb.domain.certificate.DeathCertificate;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAccidentSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAfterPregnance;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeathEvidence;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeathPlace;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeathReason;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeathWitnessFunction;
import ru.ecom.mis.ejb.domain.medcase.voc.VocIsPrematurity;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Случай смерти
 * @author stkacheva
 *
 */
@Comment ("Случай смерти")
@Entity
@AIndexes({
	@AIndex(properties="medCase"),
	@AIndex(properties="patient")
    }) 
@Table(schema="SQLUser")
public class DeathCase extends BaseEntity {
	/** Мед. случай */
	@Comment("Мед. случай")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** Мать */
	@Comment("Мать")
	@OneToOne
	public Patient getMother() {return theMother;}
	public void setMother(Patient aMother) {theMother = aMother;}

	/** Дата смерти */
	@Comment("Дата смерти")
	public Date getDeathDate() {return theDeathDate;	}
	public void setDeathDate(Date aDateDeath) {theDeathDate = aDateDeath;}

	/** Время смерти */
	@Comment("Время смерти")
	public Time getDeathTime() {return theDeathTime;}
	public void setDeathTime(Time aTimeDeath) {theDeathTime = aTimeDeath;}

	/** Доношенность */
	@Comment("Доношенность")
	@OneToOne
	public VocIsPrematurity getIsPrematurity() {return theIsPrematurity;}
	public void setIsPrematurity(VocIsPrematurity aIsPrematurity) {theIsPrematurity = aIsPrematurity;}

	/** Масса (вес) при рождении (грамм) */
	@Comment("Масса (вес) при рождении (грамм)")
	public Integer getBirthWeight() {return theBirthWeight;}
	public void setBirthWeight(Integer aBirthWeight) {theBirthWeight = aBirthWeight;}

	/** Какой ребенок по счету у матери */
	@Comment("Какой ребенок по счету у матери")
	public Integer getBabyNumber() {return theBabyNumber;}
	public void setBabyNumber(Integer aBabyNumber) {theBabyNumber = aBabyNumber;}

	/** Место смерти */
	@Comment("Место смерти")
	@OneToOne
	public VocDeathPlace getDeathPlace() {return theDeathPlace;}
	public void setDeathPlace(VocDeathPlace aDeathPlace) {theDeathPlace = aDeathPlace;	}

	/** Адрес места смерти */
	@Comment("Адрес места смерти")
	@OneToOne
	public Address getDeathPlaceAddress() {return theDeathPlaceAddress;}
	public void setDeathPlaceAddress(Address aDeathPlaceAddress) {theDeathPlaceAddress = aDeathPlaceAddress;}

	/** Причина смерти */
	@Comment("Причина смерти")
	@OneToOne
	public VocDeathReason getDeathReason() {return theDeathReason;	}
	public void setDeathReason(VocDeathReason aDeathReason) {theDeathReason = aDeathReason;}

	/** Дата травмы (отравления) */
	@Comment("Дата травмы (отравления)")
	public Date getAccidentDate() {return theAccidentDate;}
	public void setAccidentDate(Date aAccidentDate) {theAccidentDate = aAccidentDate;}

	/** Место, при которых произошла травма (отравление) */
	@Comment("Место, при которых произошла травма (отравление)")
	public String getAccidentPlace() {return theAccidentPlace;}
	public void setAccidentPlace(String aAccidentPlace) {theAccidentPlace = aAccidentPlace;}

	/** Обстоятельства, при которых произошла травма (отравление) */
	@Comment("Обстоятельства, при которых произошла травма (отравление)")
	public String getAccidentCircumstance() {return theAccidentCircumstance;}
	public void setAccidentCircumstance(String aCircumstance) {theAccidentCircumstance = aCircumstance;}

	/** Вид травмы */
	@Comment("Вид травмы")
	@OneToOne
	public VocAccidentSubType getAccidentSubType() {return theAccidentSubType;}
	public void setAccidentSubType(VocAccidentSubType aAccidentSubType) {theAccidentSubType = aAccidentSubType;}

	/** Врач (фельдшер) */
	@Comment("Врач (фельдшер)")
	@OneToOne
	public WorkFunction getDeathWitness() {return theDeathWitness;}
	public void setDeathWitness(WorkFunction aDeathWitness) {theDeathWitness = aDeathWitness;}

	/** Причина смерти установлена */
	@Comment("Причина смерти установлена")
	@OneToOne
	public VocDeathWitnessFunction getDeathWitnessFunction() {return theDeathWitnessFunction;}
	public void setDeathWitnessFunction(VocDeathWitnessFunction aDeathWitnessFunction) {theDeathWitnessFunction = aDeathWitnessFunction;}

	/** На основании чего установлена смерть */
	@Comment("На основании чего установлена смерть")
	@ManyToMany
	public List<VocDeathEvidence> getDeathEvidence() {return theDeathEvidence;}
	public void setDeathEvidence(List<VocDeathEvidence> aDeathEvidence) {theDeathEvidence = aDeathEvidence;	}

	/** Причины смерти */
	@Comment("Причины смерти")
	@OneToMany(mappedBy = "deathCase", cascade = CascadeType.ALL)
	public List<DeathReason> getDeathReasons() {return theDeathReasons;}
	public void setDeathReasons(List<DeathReason> aDeathReasons) {theDeathReasons = aDeathReasons;}

	/** Умерла после окончания родов */
	@Comment("Умерла после окончания родов")
	@OneToOne
	public VocAfterPregnance getAfterPregnance() {return theAfterPregnance;}
	public void setAfterPregnance(VocAfterPregnance aAfterPregnance) {theAfterPregnance = aAfterPregnance;}

	/** Свидетельства о смерти */
	@Comment("Свидетельства о смерти")
	@OneToMany(mappedBy = "deathCase", cascade = CascadeType.ALL)
	public List<DeathCertificate> getDeathCertificate() {return theDeathCertificate;}
	public void setDeathCertificate(List<DeathCertificate> aDeathCertificate) {	theDeathCertificate = aDeathCertificate;}

	/** Место рождения */
	@Comment("Место рождения")
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
	public String getBirthPlaceAdress() {
		return theBirthPlaceAdress;
	}

	public void setBirthPlaceAdress(String aBirthPlaceAdress) {
		theBirthPlaceAdress = aBirthPlaceAdress;
	}

	/** Адрес места рождения */
	private String theBirthPlaceAdress;
	/** Свидетельства о смерти */
	private List<DeathCertificate> theDeathCertificate;
	/** Умерла после окончания родов */
	private VocAfterPregnance theAfterPregnance;
	/** Причины смерти */
	private List<DeathReason> theDeathReasons;
	/** На основании чего установлена смерть */
	private List<VocDeathEvidence> theDeathEvidence;
	/** Причина смерти установлена */
	private VocDeathWitnessFunction theDeathWitnessFunction;
	/** Врач (фельдшер) */
	private WorkFunction theDeathWitness;
	/** Вид травмы */
	private VocAccidentSubType theAccidentSubType;
	/** Обстоятельства, при которых произошла травма (отравление) */
	private String theAccidentCircumstance;
	/** Место, при которых произошла травма (отравление) */
	private String theAccidentPlace;
	/** Дата травмы (отравления) */
	private Date theAccidentDate;
	/** Причина смерти */
	private VocDeathReason theDeathReason;
	/** Адрес места смерти */
	private Address theDeathPlaceAddress;
	/** Место смерти */
	private VocDeathPlace theDeathPlace;
	/** Какой ребенок по счету у матери */
	private Integer theBabyNumber;
	/** Масса (вес) при рождении (грамм) */
	private Integer theBirthWeight;
	/** Доношенность */
	private VocIsPrematurity theIsPrematurity;
	/** Время смерти */
	private Time theDeathTime;
	/** Дата смерти */
	private Date theDeathDate;
	/** Мать */
	private Patient theMother;
	/** Пациент */
	private Patient thePatient;
	/** Мед. случай */
	private MedCase theMedCase;

}
