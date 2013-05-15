package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcFrm;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcQz;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.hospital.TemperatureCurve;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAdmissionOrder;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeathCause;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeniedHospitalizating;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalizationOutcome;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalizationResult;
import ru.ecom.mis.ejb.domain.medcase.voc.VocJudgment;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPediculosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPreAdmissionDefect;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPreAdmissionTime;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRWresult;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychHospitalReason;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.validators.Required;

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





	/** Диагнозы */
	@Comment("Диагнозы")
	@OneToMany(mappedBy = "medCase", cascade = CascadeType.ALL)
	public List<Diagnosis> getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(List<Diagnosis> aNewProperty) {theDiagnosis = aNewProperty;}

	/** Внешний идентификатор */
	@Comment("Внешний идентификатор")
	public String getExternalId() {return theExternalId;}
	public void setExternalId(String aNewProperty) {theExternalId = aNewProperty;}

	/** Сообщения об инфекции */
	@Comment("Сообщения об инфекции")
	@OneToMany(mappedBy = "medCase", cascade = CascadeType.ALL)
	public List<PhoneMessage> getMessages() {return theMessages;}
	public void setMessages(List<PhoneMessage> aNewProperty) {theMessages = aNewProperty;}

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

	/** Обследован на RW */
	@Comment("Обследован на RW")
	public Boolean getRwExamination() {return theRwExamination;}
	public void setRwExamination(Boolean aRwExamination) {theRwExamination = aRwExamination;}

	/** Обследован на ВИЧ */
	@Comment("Обследован на ВИЧ")
	public Boolean getAidsExamination() {return theAidsExamination;}
	public void setAidsExamination(Boolean aAidsExamination) {theAidsExamination = aAidsExamination;}

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

	/** Дата RW */
	@Comment("Дата RW")
	public Date getRwDate() {return theRwDate;}
	public void setRwDate(Date aRwDate) {theRwDate = aRwDate;}

	/** Амбулаторное лечение */
	@Comment("Амбулаторное лечение")
	public Boolean getAmbulanceTreatment() {return theAmbulanceTreatment;}
	public void setAmbulanceTreatment(Boolean aAmbulanceTreatment) {theAmbulanceTreatment = aAmbulanceTreatment;}

	/** Стат. карта */
	@Comment("Стат. карта")
	@OneToOne
	public StatisticStubExist getStatisticStub() {return theStatisticStub;}
	public void setStatisticStub(StatisticStubExist aStatisticStub) {theStatisticStub = aStatisticStub;}

	/** Номер RW */
	@Comment("Номер RW")
	public String getRwNumber() {return theRwNumber;}
	public void setRwNumber(String aRwNumber) {theRwNumber = aRwNumber;}

	/** Сообщение родственникам */
	@Comment("Сообщение родственникам")
	public Boolean getRelativeMessage() {return theRelativeMessage;}
	public void setRelativeMessage(Boolean aRelativeMessage) {theRelativeMessage = aRelativeMessage;}

	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return theBedType;}
	public void setBedType(VocBedType aBedType) {theBedType = aBedType;}

	/** Оказана мед. помощь в приемном отделении */
	@Comment("Оказана мед. помощь в приемном отделении")
	public Boolean getMedicalAid() {return theMedicalAid;}
	public void setMedicalAid(Boolean aMedicalAid) {theMedicalAid = aMedicalAid;}

	/** Полисы */
	@Comment("Полисы")
	@OneToMany(mappedBy = "medCase", cascade = CascadeType.ALL)
	public List<MedCaseMedPolicy> getPolicies() {return thePolicies;}
	public void setPolicies(List<MedCaseMedPolicy> aPolicies) {thePolicies = aPolicies;}

	/** Характер заболевания */
	@Comment("Характер заболевания")
	@OneToOne
	public OmcQz getIllessCharacter() {return theIllessCharacter;}
	public void setIllessCharacter(OmcQz aIllessCharacter) {theIllessCharacter = aIllessCharacter;}

	/** Причина смерти */
	@Comment("Причина смерти")
	@OneToOne
	public VocDeathCause getDeathCause() {return theDeathCause;}
	public void setDeathCause(VocDeathCause aDeathCause) {theDeathCause = aDeathCause;}

	/** Результат RW */
	@Comment("Результат RW")
	@OneToOne
	public VocRWresult getRWresult() {return theRWresult;}
	public void setRWresult(VocRWresult aRWresult) {theRWresult = aRWresult;}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	@OneToOne
	public WorkFunction getOrderWorkFunction() {return theOrderWorkFunction;}
	public void setOrderWorkFunction(WorkFunction aOrderWorkFunction) {theOrderWorkFunction = aOrderWorkFunction;}

	/** Провизорность */
	@Comment("Провизорность")
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
	
	/*
	@Transient
	@Comment("Диагноз приемного отделения, при поступлении")
	public Diagnosis getDiagnosEntrance() {
		Diagnosis diagEntrance = null;
		if (!getDiagnosis().isEmpty()) {
			for (int i = 0; i < getDiagnosis().size(); i++) {
				Diagnosis diag =getDiagnosis().get(i) ; 
				if (diag.getRegistrationType()!=null && diag.getRegistrationType().getCode().equals("1")) {
					diagEntrance = getDiagnosis().get(i);
					break;
				}
			}
		}
		return diagEntrance;
	}

	@Transient
	@Comment("Диагноз направившего учреждения")
	public Diagnosis getDiagnosOrder() {
		Diagnosis diagOrder = null;
		if (!getDiagnosis().isEmpty()) {
			for (int i = 0; i <= getDiagnosis().size() - 1; i++) {
				Diagnosis diag =getDiagnosis().get(i) ; 
				if (diag.getRegistrationType()!=null && diag.getRegistrationType().getCode().equals("2")) {
					diagOrder = getDiagnosis().get(i);
					break;
				}
			}
		}
		return diagOrder;
	}
	
	@Transient
	@Comment("Клинический диагноз")
	public String getDiagnosClinical() {
		Diagnosis diagClinical = null;
		if (!getDiagnosis().isEmpty()) {
			for (int i = 0; i <= getDiagnosis().size() - 1; i++) {
				Diagnosis diag =getDiagnosis().get(i) ; 
				if (diag.getRegistrationType()!=null &&  diag.getPriority()!=null ) {
					if(diag.getRegistrationType().getCode().equals("4") && diag.getPriority().getCode().equals("1")){
						diagClinical = getDiagnosis().get(i);
						return diagClinical.getName();
					}
				}
			}
		}
		return null;
	}
	
	@Transient
	@Comment("Патанатомический диагноз")
	public Diagnosis getDiagnosPathanatomical() {
		Diagnosis diagPathanatomical = null;
		if (!getDiagnosis().isEmpty()) {
			for (int i = 0; i <= getDiagnosis().size() - 1; i++) {
				Diagnosis diag =getDiagnosis().get(i) ; 
				if (diag.getRegistrationType()!=null && diag.getRegistrationType().getCode().equals("5")) {
					diagPathanatomical = getDiagnosis().get(i);
					break;
				}
			}
		}
		return diagPathanatomical;
	} 
		@Transient
		@Comment("Основной заключительный диагноз, выписной")
		public Diagnosis getDiagnosConcluding() {
			Diagnosis diagConcluding = null;
			if (!getDiagnosis().isEmpty()) {
				for (int i = 0; i <= getDiagnosis().size() - 1; i++) {
					Diagnosis diag =getDiagnosis().get(i) ; 
					if (diag.getRegistrationType()!=null &&  diag.getPriority()!=null ) {
						if(diag.getRegistrationType().getCode().equals("3") && diag.getPriority().getCode().equals("1")){
						diagConcluding = getDiagnosis().get(i);
						break;
					}
				}
			}
			}
			return diagConcluding;
	} 
		
		@Transient
		@Comment("Осложнение основного заключительного диагноза")
		public Diagnosis getDiagnosComplication() {
			Diagnosis diagComplication = null;
			if (!getDiagnosis().isEmpty()) {
				for (int i = 0; i <= getDiagnosis().size() - 1; i++) {
					Diagnosis diag =getDiagnosis().get(i) ; 
				if (diag.getPriority()!=null && diag.getPriority().getCode().equals("4")) {
						diagComplication = getDiagnosis().get(i);
						break;
					}
				}
			}
			return diagComplication;
	} 
	
		@Transient
		@Comment("Сопутствующий заключительный диагноз")
		public Diagnosis getDiagnosConcominant() {
			Diagnosis diagConcominant = null;
			if (!getDiagnosis().isEmpty()) {
				for (int i = 0; i <= getDiagnosis().size() - 1; i++) {
					Diagnosis diag =getDiagnosis().get(i) ; 
				if (diag.getPriority()!=null && diag.getPriority().getCode().equals("3")) {
						diagConcominant = getDiagnosis().get(i);
						break;
					}
				}
			}
			return diagConcominant;
	} 
		*/

	/** Температурный лист */
	@Comment("Температурный лист")
	@OneToMany(mappedBy="medCase", cascade=CascadeType.ALL)
	public List<TemperatureCurve> getTemperatureCurves() {
		return theTemperatureCurves;
	}

	public void setTemperatureCurves(List<TemperatureCurve> aTemperatureCurves) {
		theTemperatureCurves = aTemperatureCurves;
	}
	
	/** Главное ЛПУ инфо */
	@Comment("Главное ЛПУ инфо")
	@Transient
	public String getMainLpuInfo() {return getParentLpu(getLpu()).getFullname();}
	private MisLpu getParentLpu(MisLpu aLpu) {return aLpu.getParent()!=null? getParentLpu(aLpu.getParent()):aLpu ;}

	/** Педикулез */
	@Comment("Педикулез")
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
	/** Температурный лист */
	private List<TemperatureCurve> theTemperatureCurves;

	/** Тип текущего стационара */
	private VocHospType theHospType;
	/** Перевод в другое ЛПУ */
	private MisLpu theMoveToAnotherLPU;
	/** Провизорность */
	private Boolean theProvisional;
	/** Обследован на ВИЧ */
	private Boolean theAidsExamination;
	/** Обследован на RW */
	private Boolean theRwExamination;
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
	/** Сообщения об инфекции */
	private List<PhoneMessage> theMessages;
	/** Внешний идентификатор */
	private String theExternalId;

	/** Диагнозы */
	private List<Diagnosis> theDiagnosis;

	/** Рабочая функция направителя */
	private WorkFunction theOrderWorkFunction;
	/** Результат RW */
	private VocRWresult theRWresult;
	/** Причина смерти */
	private VocDeathCause theDeathCause;
	/** Характер заболевания */
	private OmcQz theIllessCharacter;
	/** Полисы */
	private List<MedCaseMedPolicy> thePolicies;
	/** Оказана мед. помощь в приемном отделении */
	private Boolean theMedicalAid;
	/** Профиль коек */
	private VocBedType theBedType;
	/** Сообщение родственникам */
	private Boolean theRelativeMessage;
	/** Номер RW */
	private String theRwNumber;
	/** Стат. карта */
	private StatisticStubExist theStatisticStub;
	/** Амбулаторное лечение */
	private Boolean theAmbulanceTreatment;
	/** Номер стат.карты */
	//private String theStatCardNumber;
	/** Дата RW */
	private Date theRwDate;
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
		StringBuilder ret = new StringBuilder() ;
		ret.append("СЛС ").append(getId()).append(" номер стат.карты ").append(getStatCardNumber()) ;
		return ret.toString() ;
	}
	
	/** Количество дней */
	@Comment("Количество дней")
	@Transient
	public String getDaysCount() {
		if (getDateFinish()==null) return "" ;
		if (getChildMedCase()!=null) {
			for (MedCase m:getChildMedCase()) {
				if (m instanceof DepartmentMedCase) {
					DepartmentMedCase d = (DepartmentMedCase) m ;
					VocBedSubType vbst = (d.getBedFund()!=null&&d.getBedFund().getBedSubType()!=null)?d.getBedFund().getBedSubType():null ;
					if (vbst!=null && vbst.getCode()!=null&&vbst.getCode().equals("2")) {
						return DurationUtil.getDurationMedCase(getDateStart(), getDateFinish(),0,1);
					}
				}
			}
		}
		return DurationUtil.getDurationMedCase(getDateStart(), getDateFinish(),0);
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

	/** Число дней лечебных отпусков */
	@Comment("Число дней лечебных отпусков")
	public Integer getMedicalHolidayDays() {return theMedicalHolidayDays;}
	public void setMedicalHolidayDays(Integer aMedicalHolidayDays) {theMedicalHolidayDays = aMedicalHolidayDays;}

	/** Число лечебных отпусков */
	@Comment("Число лечебных отпусков")
	public Integer getMedicalHolidays() {return theMedicalHolidays;}
	public void setMedicalHolidays(Integer aMedicalHolidays) {theMedicalHolidays = aMedicalHolidays;}

	/** Число лечебных отпусков */
	private Integer theMedicalHolidays;
	/** Число дней лечебных отпусков */
	private Integer theMedicalHolidayDays;
	/** Решение суда по 35 статье */
	private VocJudgment theJudgment35;
	/** Порядок поступления */
	private VocAdmissionOrder theAdmissionOrder;

}
