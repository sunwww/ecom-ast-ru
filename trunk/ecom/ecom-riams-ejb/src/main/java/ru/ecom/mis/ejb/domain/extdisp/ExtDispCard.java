package ru.ecom.mis.ejb.domain.extdisp;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDisp;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAgeGroup;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispHealthGroup;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispSocialGroup;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/** Карта учета дополнительной диспансеризации (профосмотров) (УФ N 131/у) */
@Comment("Карта учета дополнительной диспансеризации (профосмотров) (УФ N 131/у)")
@Entity
@Table(schema="SQLUser")
public class ExtDispCard extends BaseEntity{
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Patient thePatient;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}
	/** ЛПУ */
	private MisLpu theLpu;
	
	@OneToMany(mappedBy="card", cascade=CascadeType.ALL)
	public List<ExtDispService> getServices() {return theServices;}
	public void setServices(List<ExtDispService> aServices) {theServices = aServices;}
	/** Услуги */
	private List<ExtDispService> theServices;
	
	/** Госпитализирован */
	@Comment("Госпитализирован")
	public Boolean getHospitalized() {return theHospitalized;}
	public void setHospitalized(Boolean aHospitalized) {theHospitalized = aHospitalized;}
	/** Госпитализирован */
	private Boolean theHospitalized;
	
	/** Социальная группа */
	@Comment("Социальная группа")
	@OneToOne
	public VocExtDispSocialGroup getSocialGroup() {return theSocialGroup;}
	public void setSocialGroup(VocExtDispSocialGroup aSocialGroup) {theSocialGroup = aSocialGroup;}
	/** Социальная группа */
	private VocExtDispSocialGroup theSocialGroup;
	
	/** Тип дополнительной диспансеризации */
	@Comment("Тип дополнительной диспансеризации")
	@OneToOne
	public VocExtDisp getDispType() {return theDispType;}
	public void setDispType(VocExtDisp aDispType) {theDispType = aDispType;}
	/** Тип дополнительной диспансеризации */
	private VocExtDisp theDispType;
	
	/** На выезде */
	@Comment("На выезде")	
	public Boolean getOnDeparture() {return theOnDeparture;}
	public void setOnDeparture(Boolean aOnDeparture) {theOnDeparture = aOnDeparture;}
	/** На выезде */
	private Boolean theOnDeparture;
	
	/** Дата начала */
	@Comment("Дата начала")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	/** Дата начала */
	private Date theStartDate;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата окончания */
	private Date theFinishDate;
	
	/** Группа здоровья дополнительной диспансеризации */
	@Comment("Группа здоровья дополнительной диспансеризации")
	@OneToOne
	public VocExtDispHealthGroup getHealthGroup() {return theHealthGroup;}
	public void setHealthGroup(VocExtDispHealthGroup aHealthGroup) {theHealthGroup = aHealthGroup;}
	/** Группа здоровья дополнительной диспансеризации */
	private VocExtDispHealthGroup theHealthGroup;
	
	/** Взят на диспансерное наблюдение */
	@Comment("Взят на диспансерное наблюдение")
	public Boolean getIsObservation() {return theIsObservation;}
	public void setIsObservation(Boolean aIsObservation) {theIsObservation = aIsObservation;}
	/** Взят на диспансерное наблюдение */
	private Boolean theIsObservation;
	
	/** Назначено лечение */
	@Comment("Назначено лечение")
	public Boolean getIsTreatment() {return theIsTreatment;}
	public void setIsTreatment(Boolean aIsTreatment) {theIsTreatment = aIsTreatment;}
	/** Назначено лечение */
	private Boolean theIsTreatment;
	
	/** Назначена дополнительное диагностическое исследование */
	@Comment("Назначена дополнительное диагностическое исследование")
	public Boolean getIsDiagnostics() {return theIsDiagnostics;}
	public void setIsDiagnostics(Boolean aIsDiagnostics) {theIsDiagnostics = aIsDiagnostics;}
	/** Назначена дополнительное диагностическое исследование */
	private Boolean theIsDiagnostics;
	
	/** Дано направление  для  получения  специализированной,  в  том  числе ВМП */
	@Comment("Дано направление  для  получения  специализированной,  в  том  числе ВМП")
	public Boolean getIsSpecializedCare() {return theIsSpecializedCare;}
	public void setIsSpecializedCare(Boolean aIsSpecializedCare) {theIsSpecializedCare = aIsSpecializedCare;}
	/** Дано направление  для  получения  специализированной,  в  том  числе ВМП */
	private Boolean theIsSpecializedCare;
	
	/** Дано направление на санаторно-курортное лечение */
	@Comment("Дано направление на санаторно-курортное лечение")
	public Boolean getIsSanatorium() {return theIsSanatorium;}
	public void setIsSanatorium(Boolean aIsSanatorium) {theIsSanatorium = aIsSanatorium;}
	/** Дано направление на санаторно-курортное лечение */
	private Boolean theIsSanatorium;
	
	@OneToMany(mappedBy="card", cascade=CascadeType.ALL)
	public List<ExtDispRisk> getRisks() {return theRisks;}
	public void setRisks(List<ExtDispRisk> aRisks) {theRisks = aRisks;}
	/** Риски здоровью */
	private List<ExtDispRisk> theRisks;
	
	/** Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ */
	@Comment("Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ")
	public Boolean getIsSmallNation() {return theIsSmallNation;}
	public void setIsSmallNation(Boolean aIsSmallNation) {theIsSmallNation = aIsSmallNation;}
	/** Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ */
	private Boolean theIsSmallNation;
	
	/** МКБ основного диагноза */
	@Comment("МКБ основного диагноза")
	@OneToOne
	public VocIdc10 getIdcMain() {return theIdcMain;}
	public void setIdcMain(VocIdc10 aIdcMain) {theIdcMain = aIdcMain;}
	/** МКБ основного диагноза */
	private VocIdc10 theIdcMain;
	
	/** Направлен на след. этап */
	@Comment("Направлен на след. этап")
	public Boolean getIsServiceIndication() {return theIsServiceIndication;}
	public void setIsServiceIndication(Boolean aIsServiceIndication) {theIsServiceIndication = aIsServiceIndication;}

	/** Направлен на след. этап */
	private Boolean theIsServiceIndication;

	/** Возрастная категория */
	@Comment("Возрастная категория")
	@OneToOne
	public VocExtDispAgeGroup getAgeGroup() {return theAgeGroup;}
	public void setAgeGroup(VocExtDispAgeGroup aAgeGroup) {theAgeGroup = aAgeGroup;}

	/** Возрастная категория */
	private VocExtDispAgeGroup theAgeGroup;
}
