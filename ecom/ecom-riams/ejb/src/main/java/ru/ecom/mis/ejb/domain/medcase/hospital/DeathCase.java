package ru.ecom.mis.ejb.domain.medcase.hospital;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.document.ejb.domain.certificate.DeathCertificate;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.kili.ProtocolKili;
import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
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
	
	/** Протоколы КИЛИ */
	@Comment("Протоколы КИЛИ")
	@OneToMany(mappedBy = "deathCase", cascade = CascadeType.ALL)
	public List<ProtocolKili> getKiliProtocols() {return theKiliProtocols;}
	public void setKiliProtocols(List<ProtocolKili> aKiliProtocols) {theKiliProtocols = aKiliProtocols;}
	/** Протоколы КИЛИ */
	private List<ProtocolKili> theKiliProtocols;

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
	public String getBirthPlace() {return theBirthPlace;}
	public void setBirthPlace(String aBirthPlace) {theBirthPlace = aBirthPlace;}

	/** Место рождения */
	private String theBirthPlace;
	
	/** Адрес места рождения */
	@Comment("Адрес места рождения")
	public String getBirthPlaceAdress() {return theBirthPlaceAdress;}
	public void setBirthPlaceAdress(String aBirthPlaceAdress) {theBirthPlaceAdress = aBirthPlaceAdress;}
	
	/** Адрес места рождения */
	private String theBirthPlaceAdress;
	/** Свидетельства о смерти */
	private List<DeathCertificate> theDeathCertificate;
	/** Умерла после окончания родов */
	private VocAfterPregnance theAfterPregnance;
	/** На основании чего установлена смерть */
	private List<VocDeathEvidence> theDeathEvidence;
	/** Причина смерти установлена */
	private VocDeathWitnessFunction theDeathWitnessFunction;
	/** Врач (фельдшер) */
	private WorkFunction theDeathWitness;
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
	
	/** Ятрогения */
	@Comment("Ятрогения")
	@OneToOne
	public VocDeathCategory getLatrogeny() {return theLatrogeny;}
	public void setLatrogeny(VocDeathCategory aLatrogeny) {theLatrogeny = aLatrogeny;}

	/** Категория расхождения между диагнозами после патан. */
	@Comment("Категория расхождения между диагнозами после патан.")
	@OneToOne
	public VocDeathCategory getCategoryDifference() {return theCategoryDifference;}
	public void setCategoryDifference(VocDeathCategory aCategoryDifference) {theCategoryDifference = aCategoryDifference;}

	/** Описание причины смерти */
	@Comment("Описание причины смерти")
	public String getCommentReason() {return theCommentReason;}
	public void setCommentReason(String aCommentDeathReason) {theCommentReason = aCommentDeathReason;}

	/** Комментарий к категории смерти */
	@Comment("Комментарий к категории смерти")
	public String getCommentCategory() {return theCommentCategory;}
	public void setCommentCategory(String aCommentCategoryDeath) {theCommentCategory = aCommentCategoryDeath;}

	/** Номер в патологоанатомическом бюро */
	@Comment("Номер в патологоанатомическом бюро")
	public String getPostmortemBureauNumber() {return thePostmortemBureauNumber;}
	public void setPostmortemBureauNumber(String aPostmortemBureauNumber) {thePostmortemBureauNumber = aPostmortemBureauNumber;}

	/** Дата ПАБ */
	@Comment("Дата ПАБ")
	public Date getPostmortemBureauDt() {return thePostmortemBureauDt;}
	public void setPostmortemBureauDt(Date aPostmortemBureauDt) {thePostmortemBureauDt = aPostmortemBureauDt;}

	/** Квартира, где умер человек */
	@Comment("Квартира, где умер человек")
	public String getDeathPlaceFlatNumber() {return theDeathPlaceFlatNumber;}
	public void setDeathPlaceFlatNumber(String aDeathPlaceFlatNumber) {theDeathPlaceFlatNumber = aDeathPlaceFlatNumber;}

	/** Дом, где умер человекhPlaceHouseNumber */
	@Comment("Дом, где умер человек")
	public String getDeathPlaceHouseNumber() {return theDeathPlaceHouseNumber;}
	public void setDeathPlaceHouseNumber(String aDeathPlaceHouseNumber) {theDeathPlaceHouseNumber = aDeathPlaceHouseNumber;}

	/** Копрус дома, где умер человек */
	@Comment("Копрус дома, где умер человек")
	public String getDeathPlaceHouseBuilding() {return theDeathPlaceHouseBuilding;}
	public void setDeathPlaceHouseBuilding(String aDeathPlaceHouseBuilding) {theDeathPlaceHouseBuilding = aDeathPlaceHouseBuilding;}

	/** Копрус дома, где умер человек */
	private String theDeathPlaceHouseBuilding;
	/** Дом, где умер человекhPlaceHouseNumber */
	private String theDeathPlaceHouseNumber;
	/** Квартира, где умер человек */
	private String theDeathPlaceFlatNumber;
	/** Дата ПАБ */
	private Date thePostmortemBureauDt;
	/** Номер в патологоанатомическом бюро */
	private String thePostmortemBureauNumber;
	/** Комментарий к категории смерти */
	private String theCommentCategory;
	/** Описание причины смерти */
	private String theCommentReason;
	/** Категория расхождения между диагнозами после патан. */
	private VocDeathCategory theCategoryDifference;
	/** Ятрогения */
	private VocDeathCategory theLatrogeny;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Тип диагноза категории расхождения */
	@Comment("Тип диагноза категории расхождения")
	@OneToOne
	public VocPriorityDiagnosis getDiagnosisDifference() {return theDiagnosisDifference;}
	public void setDiagnosisDifference(VocPriorityDiagnosis aDiagnosisDifference) {theDiagnosisDifference = aDiagnosisDifference;}

	/** Дата СМЭ */
	@Comment("Дата СМЭ")
	public Date getDateForensic() {return theDateForensic;}
	public void setDateForensic(Date aDateForensic) {theDateForensic = aDateForensic;}

	/** Произведено вскрытие */
	@Comment("Произведено вскрытие")
	public Boolean getIsAutopsy() {return theIsAutopsy;}
	public void setIsAutopsy(Boolean aIsAutopsy) {theIsAutopsy = aIsAutopsy;}

	
	/** Тип диагноза категории расхождения */
	private VocPriorityDiagnosis theDiagnosisDifference;
	/** Произведено вскрытие */
	private Boolean theIsAutopsy;
	/** Дата СМЭ */
	private Date theDateForensic;
	
	/** Код мкб */
	@Comment("Код мкб")
	@OneToOne
	public VocIdc10 getReasonMainMkb() {return theReasonMainMkb;}
	public void setReasonMainMkb(VocIdc10 aReasonMainMkb) {theReasonMainMkb = aReasonMainMkb;}

	/** Код мкб */
	private VocIdc10 theReasonMainMkb;
	
	/** Код мкб осложнения */
	@Comment("Код мкб осложнения")
	@OneToOne
	public VocIdc10 getReasonComplicationMkb() {return theReasonComplicationMkb;}
	public void setReasonComplicationMkb(VocIdc10 aReasonComplicationMkb) {theReasonComplicationMkb = aReasonComplicationMkb;}

	/** Код мкб осложнения */
	private VocIdc10 theReasonComplicationMkb;
	
	/** Код мкб сопутсвующий */
	@Comment("Код мкб сопутсвующий")
	@OneToOne
	public VocIdc10 getReasonConcomitantMkb() {return theReasonConcomitantMkb;}
	public void setReasonConcomitantMkb(VocIdc10 aReasonConcomitantMkb) {theReasonConcomitantMkb = aReasonConcomitantMkb;}

	/** Код мкб сопутсвующий */
	private VocIdc10 theReasonConcomitantMkb;
	
	/** Текст мкб осложнения */
	@Comment("Текст мкб осложнения")
	public String getReasonComplicationText() {return theReasonComplicationText;}
	public void setReasonComplicationText(String aReasonComplicationText) {theReasonComplicationText = aReasonComplicationText;}

	/** Сопутствующий диагноз текст */
	@Comment("Сопутствующий диагноз текст")
	public String getReasonConcomitantText() {return theReasonConcomitantText;}
	public void setReasonConcomitantText(String aReasonConcomitantText) {theReasonConcomitantText = aReasonConcomitantText;}

	/** Конкурирующее заболевание */
	@Comment("Конкурирующее заболевание")
	public String getCompetingDisease() {return theCompetingDisease;}
	public void setCompetingDisease(String aCompetingDisease) {theCompetingDisease = aCompetingDisease;}

	/** Сочетанное заболевание */
	@Comment("Сочетанное заболевание")
	public String getPolypathia() {return thePolypathia;}
	public void setPolypathia(String aPolypathia) {thePolypathia = aPolypathia;}

	/** Фоновое заболевание */
	@Comment("Фоновое заболевание")
	public String getBackgroundDisease() {return theBackgroundDisease;}
	public void setBackgroundDisease(String aBackgroundDisease) {theBackgroundDisease = aBackgroundDisease;}

	/** Фоновое заболевание */
	private String theBackgroundDisease;
	/** Сочетанное заболевание */
	private String thePolypathia;
	/** Конкурирующее заболевание */
	private String theCompetingDisease;
	/** Сопутствующий диагноз текст */
	private String theReasonConcomitantText;	
	/** Текст мкб осложнения */
	private String theReasonComplicationText;
	/** Присутствие врача на вскрытие */
	@Comment("Присутствие врача на вскрытие")
	public Boolean getIsPresenceDoctorAutopsy() {return theIsPresenceDoctorAutopsy;}	
	public void setIsPresenceDoctorAutopsy(Boolean aIsPresenceDoctorAutopsy) {theIsPresenceDoctorAutopsy = aIsPresenceDoctorAutopsy;}

	/** Присутствие врача на вскрытие */
	private Boolean theIsPresenceDoctorAutopsy;

	/** Мертворождение */
	@Comment("Мертворождение")
	public Boolean getIsNeonatologic() {return theIsNeonatologic;}
	public void setIsNeonatologic(Boolean aIsNeonatologic) {theIsNeonatologic = aIsNeonatologic;}

	/** Мертворождение */
	private Boolean theIsNeonatologic;

	/** История развития новорождённого - только для акушерских случаев */
	public String getNewBornHistory() {return theNewBornHistory;}
	public void setNewBornHistory(String aNewBornHistory) { theNewBornHistory=aNewBornHistory;}

	/** История развития новорождённого - только для акушерских случаев */
	private String theNewBornHistory;
}