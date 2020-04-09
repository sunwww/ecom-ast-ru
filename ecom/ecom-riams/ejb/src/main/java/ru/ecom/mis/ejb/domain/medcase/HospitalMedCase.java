package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcFrm;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychHospitalReason;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Стационарный случай медицинского обслуживания
 */
@Comment("Стационарный случай медицинского обслуживания")
@Entity
@AIndexes({
	@AIndex(properties="statisticStub", table="MedCase")
    ,@AIndex(properties="result", table="MedCase")
}) 
@Table(schema="SQLUser")
public class HospitalMedCase extends LongMedCase {

	/** Дата перевода */
	@Comment("Дата перевода")
	public Date getTransferDate() {return theTransferDate;	}
	public void setTransferDate(Date aTransferDate) {theTransferDate = aTransferDate;}

	/** Время перевода */
	@Comment("Время перевода")
	public Time getTransferTime() {return theTransferTime;	}
	public void setTransferTime(Time aTransferTime) {theTransferTime = aTransferTime;}

	/** Время перевода */
	private Time theTransferTime;
	/** Дата перевода */
	private Date theTransferDate;

	/** Дефекты догоспитального этапа */
	@Comment("Дефекты догоспитального этапа")
	@OneToOne
	public VocPreAdmissionDefect getPreAdmissionDefect() {return thePreAdmissionDefect;}
	public void setPreAdmissionDefect(VocPreAdmissionDefect aPreAdmissionDefect) {thePreAdmissionDefect = aPreAdmissionDefect;}

	/** Тип доставки */
	@Comment("Тип доставки")
	public String getSupplyType() {return theSupplyType;}
	public void setSupplyType(String aSupplyType) {theSupplyType = aSupplyType;}

	/** Номер направления */
	@Comment("Номер направления")
	public String getOrderNumber() {return theOrderNumber;}
	public void setOrderNumber(String aOrderNumber) {theOrderNumber = aOrderNumber;}

	/** Номер наряда доставки */
	@Comment("Номер наряда доставки")
	public String getSupplyOrderNumber() {return theSupplyOrderNumber;}
	public void setSupplyOrderNumber(String aSupplyOrderNumber) {theSupplyOrderNumber = aSupplyOrderNumber;}


	/** Время заболевания до госпитализации */
	@Comment("Время заболевания до госпитализации")
	@OneToOne
	public VocPreAdmissionTime getPreAdmissionTime() {return thePreAdmissionTime;}
	public void setPreAdmissionTime(VocPreAdmissionTime aPreAdmissionTime) {thePreAdmissionTime = aPreAdmissionTime;}

	/** Исход госпитализации */
	@Comment("Исход госпитализации")
	@OneToOne
	public VocHospitalizationOutcome getOutcome() {return theOutcome;}
	public void setOutcome(VocHospitalizationOutcome aOutcome) {theOutcome = aOutcome;}

	/** Результат госпитализации */
	@Comment("Результат госпитализации")
	@OneToOne
	public VocHospitalizationResult getResult() {return theResult;}
	public void setResult(VocHospitalizationResult aResult) {theResult = aResult;}

	/** Кем доставлен */
	@Comment("Кем доставлен")
	@OneToOne
	public OmcFrm getOrderType() {return theOrderType;}
	public void setOrderType(OmcFrm aOrderType) {theOrderType = aOrderType;}

	/** Отказ от госпитализации */
	@Comment("Отказ от госпитализации")
	@OneToOne
	public VocDeniedHospitalizating getDeniedHospitalizating() {return theDeniedHospitalizating;}
	public void setDeniedHospitalizating(VocDeniedHospitalizating aDeniedHospitalizating) {theDeniedHospitalizating = aDeniedHospitalizating;}

	/** Амбулаторное лечение */
	@Comment("Амбулаторное лечение")
	public Boolean getAmbulanceTreatment() {return theAmbulanceTreatment;}
	public void setAmbulanceTreatment(Boolean aAmbulanceTreatment) {theAmbulanceTreatment = aAmbulanceTreatment;}

	/** Стат. карта */
	@Comment("Стат. карта")
	@OneToOne
	public StatisticStubExist getStatisticStub() {return theStatisticStub;}
	public void setStatisticStub(StatisticStubExist aStatisticStub) {theStatisticStub = aStatisticStub;}

	/** Сообщение родственникам */
	@Comment("Сообщение родственникам")
	public Boolean getRelativeMessage() {return theRelativeMessage;}
	public void setRelativeMessage(Boolean aRelativeMessage) {theRelativeMessage = aRelativeMessage;}

	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	@Deprecated //unused
	public VocBedType getBedType() {return theBedType;}
	public void setBedType(VocBedType aBedType) {theBedType = aBedType;}

	/** Оказана мед. помощь в приемном отделении */
	@Comment("Оказана мед. помощь в приемном отделении")
	public Boolean getMedicalAid() {return theMedicalAid;}
	public void setMedicalAid(Boolean aMedicalAid) {theMedicalAid = aMedicalAid;}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	@OneToOne
	public WorkFunction getOrderWorkFunction() {return theOrderWorkFunction;}
	public void setOrderWorkFunction(WorkFunction aOrderWorkFunction) {theOrderWorkFunction = aOrderWorkFunction;}

	/** Провизорность */
	@Comment("Провизорность")
	@Deprecated
	public Boolean getProvisional() {return theProvisional;}
	public void setProvisional(Boolean aProvisional) {theProvisional = aProvisional;}

	/** Перевод в другое ЛПУ */
	@Comment("Перевод в другое ЛПУ")
	@OneToOne
	public MisLpu getMoveToAnotherLPU() {return theMoveToAnotherLPU;}
	public void setMoveToAnotherLPU(MisLpu aMoveToAnotherLPU) {theMoveToAnotherLPU = aMoveToAnotherLPU;}

	/** Тип текущего стационара */
	@Comment("Тип текущего стационара")
	@OneToOne
	public VocHospType getHospType() {return theHospType;}
	public void setHospType(VocHospType aHospType) {theHospType = aHospType;}

	/** Госпитализация (впервые, повторно) */
	
	
	/** Выписной эпикриз */
	@Comment("Выписной эпикриз")
	@Column(length=30000)
	public String getDischargeEpicrisis() {return theDischargeEpicrisis;}
	public void setDischargeEpicrisis(String aDischargeEpicrisis) {theDischargeEpicrisis = aDischargeEpicrisis;}
	
	/** Сопровождающее лицо */
	@Comment("Сопровождающее лицо")
	public String getAttendant() {return theAttendant;}
	public void setAttendant(String aAttendant) {theAttendant = aAttendant;}
	
	/** Редкий случай */
	@Comment("Редкий случай")
	public Boolean getRareCase() {return theRareCase;}
	public void setRareCase(Boolean aRareCase) {theRareCase = aRareCase;}
	


	/** Поступление в данный стационар */
	@Comment("Поступление в данный стационар")
	@OneToOne
	public VocHospitalization getAdmissionInHospital() {return theAdmissionInHospital;}
	public void setAdmissionInHospital(VocHospitalization aAdmissionInHospital) {theAdmissionInHospital = aAdmissionInHospital;}

	/** Откуда поступил */
	@Comment("Откуда поступил")
	@OneToOne
	public VocHospitalizationOutcome getWhereAdmission() {return theWhereAdmission;}
	public void setWhereAdmission(VocHospitalizationOutcome aWhereAdmission) {theWhereAdmission = aWhereAdmission;}

	/** Откуда поступил */
	private VocHospitalizationOutcome theWhereAdmission;
	/** Поступление в данный стационар */
	private VocHospitalization theAdmissionInHospital;
	/** Редкий случай */
	private Boolean theRareCase;

	// Вычислямые поля
	/** Случай является отказом от госпитализации? */
	@Comment("Случай является отказом от госпитализации?")
	@Transient
	public Boolean getIsDeniedHospitalizating() {return getDeniedHospitalizating() != null;}

	/** Является ли ССЛ амбулаторным диализом? */
	@Comment("Является ли ССЛ амбулаторным диализом?")
	@Transient
	public Boolean getIsAmbulanseDialis() {return false;}

	/** Номер стат.карты */
	@Comment("Номер стат.карты")
	@Transient
	public String getStatCardNumber() {
		String statCardNumber = "";
		if (theStatisticStub != null) {
			statCardNumber = theStatisticStub.getCode();
		}
		return statCardNumber;
	}
	
	/** Номер стат.карты */
	@Comment("Номер стат.карты")
	@Transient
	public Long getReasonDischarge() {
		Long reasonDischarge = null;
		if (theStatisticStub != null) {
			reasonDischarge = theStatisticStub.getReasonDischarge()!=null?theStatisticStub.getReasonDischarge().getId():null;
		}
		return reasonDischarge;
	}

	@Transient
	public Long getResultDischarge() {
		Long reasonDischarge = null;
		if (theStatisticStub != null) {
			reasonDischarge = theStatisticStub.getResultDischarge()!=null?theStatisticStub.getResultDischarge().getId():null;
		}
		return reasonDischarge;
	}
	
	/** Главное ЛПУ инфо */
	@Comment("Главное ЛПУ инфо")
	@Transient
	public String getMainLpuInfo() {return getParentLpu(getLpu()).getFullname();}
	private MisLpu getParentLpu(MisLpu aLpu) {return aLpu.getParent()!=null? getParentLpu(aLpu.getParent()):aLpu ;}

	/** Педикулез */
	@Comment("Педикулез")
	@OneToOne
	public VocPediculosis getPediculosis() {return thePediculosis;}
	public void setPediculosis(VocPediculosis aPediculosis) {thePediculosis = aPediculosis;}

	 /**
	  * Причина госпитализации в психиатрический стационар
	  */
	 @Comment("Причина госпитализации в психиатрический стационар")
	 @OneToOne
	 public VocPsychHospitalReason getPsychReason() {return thePsychReason;}
	 public void setPsychReason(VocPsychHospitalReason aPsychReason) {thePsychReason = aPsychReason;}
	 

	/** Дата решения суда */
	@Comment("Дата решения суда")
	public Date getLawCourtDesicionDate() {return theLawCourtDesicionDate;}
	public void setLawCourtDesicionDate(Date aLawCourtDesicionDate) {theLawCourtDesicionDate = aLawCourtDesicionDate;}

	/** Дата решения суда */
	private Date theLawCourtDesicionDate;
	 /** Причина госпитализации в психиатрический стационар */
	 private VocPsychHospitalReason thePsychReason;
	 /** Педикулез */
	private VocPediculosis thePediculosis;

	/** Тип текущего стационара */
	private VocHospType theHospType;
	/** Перевод в другое ЛПУ */
	private MisLpu theMoveToAnotherLPU;
	/** Провизорность */
	private Boolean theProvisional;
	///** Обследован на ВИЧ */
	//private Boolean theAidsExamination;
	///** Обследован на RW */
	//private Boolean theRwExamination;
	/** Результат госпитализации */
	private VocHospitalizationResult theResult;
	/** Исход госпитализации */
	private VocHospitalizationOutcome theOutcome;
	/** Время заболевания до госпитализации */
	private VocPreAdmissionTime thePreAdmissionTime;

	/** Номер наряда доставки */
	private String theSupplyOrderNumber;
	/** Номер направления */
	private String theOrderNumber;
	/** Тип доставки */
	private String theSupplyType;
	/** Дефекты догоспитального этапа */
	private VocPreAdmissionDefect thePreAdmissionDefect;
	///** Сообщения об инфекции */
	//private List<PhoneMessage> theMessages;

	/** Рабочая функция направителя */
	private WorkFunction theOrderWorkFunction;
	///** Результат RW */
	//private VocRWresult theRWresult;
	///** Причина смерти */
	//private VocDeathCause theDeathCause;
	/** Характер заболевания */
	//private OmcQz theIllessCharacter;
	///** Полисы */
	//private List<MedCaseMedPolicy> thePolicies;
	/** Оказана мед. помощь в приемном отделении */
	private Boolean theMedicalAid;
	/** Профиль коек */
	private VocBedType theBedType;
	/** Сообщение родственникам */
	private Boolean theRelativeMessage;
	///** Номер RW */
	//private String theRwNumber;
	/** Стат. карта */
	private StatisticStubExist theStatisticStub;
	/** Амбулаторное лечение */
	private Boolean theAmbulanceTreatment;
	/** Номер стат.карты */
	//private String theStatCardNumber;
	///** Дата RW */
	//private Date theRwDate;
	/** Отказ от госпитализации */
	private VocDeniedHospitalizating theDeniedHospitalizating;
	/** Кем доставлен */
	private OmcFrm theOrderType;
	/** Выписной эпикриз */
	private String theDischargeEpicrisis;
	/** Сопровождающее лицо */
	private String theAttendant;
	
	
	@Transient
	public String getInfo() {
		return "СЛС " + getId() + " номер стат.карты " + getStatCardNumber();
	}
	
	/** Количество дней */
	@Comment("Количество дней")
	@Transient
	public String getDaysCount() {
		
		return "";
	}
	
	/** Кем направлен */
	@Comment("Кем направлен")
	@OneToOne
	public MisLpu getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(MisLpu aOrderLpu) {theOrderLpu = aOrderLpu;}

	/** Кем направлен */
	private MisLpu theOrderLpu;
	// /** Вид травмы */
	// @Comment("Вид травмы")
	// @OneToOne
	// public VocHospitalizationTrauma getTrauma() {return theTrauma; }
	// public void setTrauma(VocHospitalizationTrauma aTrauma) {theTrauma =
	// aTrauma;}
	//
	// /** Вид травмы */
	// private VocHospitalizationTrauma theTrauma;
	
	/** Порядок поступления */
	@Comment("Порядок поступления")
	@OneToOne
	public VocAdmissionOrder getAdmissionOrder() {return theAdmissionOrder;}
	public void setAdmissionOrder(VocAdmissionOrder aAdmissionOrder) {theAdmissionOrder = aAdmissionOrder;}

	/** Решение суда по 35 статье */
	@Comment("Решение суда по 35 статье")
	@OneToOne
	public VocJudgment getJudgment35() {return theJudgment35;}
	public void setJudgment35(VocJudgment aJudgment35) {theJudgment35 = aJudgment35;}

	///** Число дней лечебных отпусков */
	//@Comment("Число дней лечебных отпусков")
	//public Integer getMedicalHolidayDays() {return theMedicalHolidayDays;}
	//public void setMedicalHolidayDays(Integer aMedicalHolidayDays) {theMedicalHolidayDays = aMedicalHolidayDays;}

	///** Число лечебных отпусков */
	//@Comment("Число лечебных отпусков")
	//public Integer getMedicalHolidays() {return theMedicalHolidays;}
	//public void setMedicalHolidays(Integer aMedicalHolidays) {theMedicalHolidays = aMedicalHolidays;}

	///** Число лечебных отпусков */
	//private Integer theMedicalHolidays;
	///** Число дней лечебных отпусков */
	//private Integer theMedicalHolidayDays;
	/** Решение суда по 35 статье */
	private VocJudgment theJudgment35;
	/** Порядок поступления */
	private VocAdmissionOrder theAdmissionOrder;

	/** Рост */
	@Comment("Рост")
	@Transient
	public Integer getHeight() {
		Integer height = 0;
		if (theStatisticStub != null) {
			height = theStatisticStub.getHeight();
		}
		return height;
	}
	/** Вес */
	@Comment("Вес")
	@Transient
	public Integer getWeight() {
		Integer weight = 0;
		if (theStatisticStub != null) {
			weight = theStatisticStub.getWeight();
		}
		return weight;
	}
	/** Индекс массы тела */
	@Comment("Индекс массы тела")
	@Transient
	public Double getTheIMT() {
		Double IMT = 0.0;
		if (theStatisticStub != null) {
			IMT = theStatisticStub.getIMT();
		}
		return IMT;
	}
	/** Вес */
	private Integer theWeight;
	/** Рост */
	private Integer theHeight;
	/** Индекс массы тела */
	private Double theIMT;

	/** Была ли проведена идентификация пациента */
	@Comment("Была ли проведена идентификация пациента")
	public Boolean getIsIdentified() { return theIsIdentified; }
	public void setIsIdentified(Boolean aIsIdentified) { theIsIdentified = aIsIdentified; }
	/** Была ли проведена идентификация пациента */
	private Boolean theIsIdentified;

	/** Кто провёл идентификацию */
	@Comment("Кто провёл идентификацию")
	public String getIdentUsername() {return theIdentUsername;}
	public void setIdentUsername(String aIdentUsername) {theIdentUsername = aIdentUsername;}
	/** Кто провёл идентификацию */
	private String theIdentUsername;

	/** Дата идентификации */
	@Comment("Дата идентификации")
	public Date getIdentDate() {return theIdentDate;	}
	public void setIdentDate(Date aIdentDate) {theIdentDate = aIdentDate;}
	/** Дата идентификации */
	private Date theIdentDate;

	/** Время идентификации */
	@Comment("Время идентификации")
	public Time getIdentTime() {return theIdentTime;	}
	public void setIdentTime(Time aIdentTime) {theIdentTime = aIdentTime;}
	/** Время идентификации */
	private Time theIdentTime;
}