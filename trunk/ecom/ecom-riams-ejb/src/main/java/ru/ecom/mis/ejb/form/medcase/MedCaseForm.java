package ru.ecom.mis.ejb.form.medcase;


import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.MedCase;

import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.ExtHospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.NewBornMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.poly.PolyclinicMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.poly.VisitMedCaseForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;
/**
 * Форма случая медицинского обслуживания
 * @author STkacheva
 */
@EntityForm
@EntityFormPersistance(clazz = MedCase.class)
@Comment("Случай медицинского обслуживания")
@WebTrail(comment = "СМО", nameProperties= "info", view="entitySubclassView-mis_medCase.do")
@Parent(property="patient", parentForm=PatientForm.class)
@Subclasses({ ServiceMedCaseForm.class, PolyclinicMedCaseForm.class, VisitMedCaseForm.class, DepartmentMedCaseForm.class, AdmissionMedCaseForm.class, ExtHospitalMedCaseForm.class,HospitalMedCaseForm.class,NewBornMedCaseForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/MedCase")
public class MedCaseForm extends IdEntityForm {

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aNewProperty) {thePatient = aNewProperty;}

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString
	public String getDateStart() {return theDateStart;}
	public void setDateStart(String aNewProperty) {theDateStart = aNewProperty;}
	
	/** Родительский СМО */
	@Comment("Родительский СМО")
	@Persist
	public Long getParent() {return theParent;}
	public void setParent(Long aNewProperty) {theParent = aNewProperty;}

	/** Тип СМО */
	@Comment("Тип СМО")
	@Persist
	public Long getMedCaseType() {return theMedCaseType;}
	public void setMedCaseType(Long aNewProperty) {theMedCaseType = aNewProperty;}

	/** Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;	}
	public void setNoActuality(Boolean aNewProperty) {theNoActuality = aNewProperty;}

	/** Исполнитель */
	@Comment("Исполнитель")
	@Persist
	public Long getStartWorker() {	return theStartWorker;}
	public void setStartWorker(Long aNewProperty) {theStartWorker = aNewProperty;}

	/** ЛПУ - место исполнения */
	@Comment("ЛПУ - место исполнения ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aNewProperty) {theLpu = aNewProperty;}

	/** Опьянение */
	@Comment("Опьянение")
	@Persist
	public Long getIntoxication() {	return theIntoxication;	}
	public void setIntoxication(Long aIntoxication) {theIntoxication = aIntoxication;}
	
	/** Кем открыт */
	@Comment("Кем открыт")
	public String getStartWorkerText() {return theStartWorkerText;}
	public void setStartWorkerText(String aStartWorkerText) {theStartWorkerText = aStartWorkerText;}
	
	
	
	/** Информация о пациенте */
	@Comment("Информация о пациенте")
	@Persist
	public String getPatientInfo() {return thePatientInfo;}
	public void setPatientInfo(String aPatientInfo) {thePatientInfo = aPatientInfo;}

	/** Информация о СМО */
	@Comment("Информация о СМО")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}
	
	/** Оператор */
	@Comment("Оператор")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}
	
	/** Дата создания */
	@Comment("Дата создания")
	@DoDateString @DateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}

	/** Представитель */
	@Comment("Представитель")
	@Persist
	public Long getKinsman() {return theKinsman;}
	public void setKinsman(Long aKinsman) {theKinsman = aKinsman;}
	/** Госпитализация (впервые, повторно) */
	@Comment("Госпитализация (впервые, повторно)")
	@Persist
	public Long getHospitalization() {return theHospitalization;}
	public void setHospitalization(Long aHospitalization) {theHospitalization = aHospitalization;}

	/** Условная единица трудоемкости */
	@Comment("Условная единица трудоемкости")
	@Persist
	public String getUet() {return theUet;}
	public void setUet(String aUet) {theUet = aUet;}

	/** Распечатан? */
	@Comment("Распечатан?")
	@Persist
	public Long getIsPrint() {return theIsPrint;}
	public void setIsPrint(Long aIsPrint) {theIsPrint = aIsPrint;}

	/** Диагноз? */
	@Comment("Диагноз?")
	@Persist
	public Long getIsDiagnosisCreate() {return theIsDiagnosisCreate;}
	public void setIsDiagnosisCreate(Long aIsDiagnosisCreate) {theIsDiagnosisCreate = aIsDiagnosisCreate;}

	/** Создавать дневник */
	@Comment("Создавать дневник")
	@Persist
	public Long getIsDiaryCreate() {return theIsDiaryCreate;}
	public void setIsDiaryCreate(Long aIsDairyCreate) {theIsDiaryCreate = aIsDairyCreate;}

	/** IsPrintInfo */
	@Comment("IsPrintInfo")
	@Persist
	public Boolean getIsPrintInfo() {return theIsPrintInfo;}
	public void setIsPrintInfo(Boolean aIsPrintInfo) {theIsPrintInfo = aIsPrintInfo;}

	/** Экстренность */
	@Comment("Экстренность")
	@Persist
	public Boolean getEmergency() {return theEmergency;}
	public void setEmergency(Boolean aNewProperty) {theEmergency = aNewProperty;}
	
	/** Дата печати */
	@Comment("Дата печати")
	@DateString @DoDateString
	public String getPrintDate() {return thePrintDate;}
	public void setPrintDate(String aPrintDate) {thePrintDate = aPrintDate;}
	
	/** Время печати */
	@Comment("Время печати")
	@DoTimeString @TimeString
	public String getPrintTime() {return thePrintTime;}
	public void setPrintTime(String aPrintTime) {thePrintTime = aPrintTime;}

	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return thePregnancy;}
	public void setPregnancy(Long aPregnancy) {thePregnancy = aPregnancy;}

	/** Какая по счету беременность */
	@Comment("Какая по счету беременность")
	public Integer getPregnancyOrderNumber() {return thePregnancyOrderNumber;}
	public void setPregnancyOrderNumber(Integer aNAME) {thePregnancyOrderNumber = aNAME;	}
	
	@Comment("Количество родов")
	public Integer getChildbirthAmount() {return theChildbirthAmount;}
	public void setChildbirthAmount(Integer aChildbirthAmount) {theChildbirthAmount = aChildbirthAmount;}

	/** Количество родов */
	private Integer theChildbirthAmount;
	/** Какая по счету беременность */
	private Integer thePregnancyOrderNumber;
	/** Беременность */
	private Long thePregnancy;
	/** Время печати */
	private String thePrintTime;
	/** Дата печати */
	private String thePrintDate;
	/** Экстренность */
	private Boolean theEmergency;
	/** IsPrintInfo */
	private Boolean theIsPrintInfo;
	/** Создавать дневник */
	private Long theIsDiaryCreate;
	/** Диагноз? */
	private Long theIsDiagnosisCreate;
	/** Распечатан? */
	private Long theIsPrint;
	/** Условная единица трудоемкости */
	private String theUet;
	/** Госпитализация (впервые, повторно) */
	private Long theHospitalization;
	/** Представитель */
	private Long theKinsman;
	/** Дата создания */
	private String theCreateDate;
	/** Оператор */
	private String theUsername;
	/** Информация о СМО */
	private String theInfo;
	/** Информация о пациенте */
	private String thePatientInfo;
	/** Кем открыт */
	private String theStartWorkerText;
		/** Опьянение */
	private Long theIntoxication;
	/** ЛПУ - место исполнения */
	private Long theLpu;
	/** Исполнитель */
	private Long theStartWorker;
	/** Недействительность */
	private Boolean theNoActuality;
	/** Тип СМО */
	private Long theMedCaseType;
	/** Родительский СМО */
	private Long theParent;
	/** Дата начала */
	private String theDateStart;
	/** Пациент */
	private Long thePatient;
	/** Поток обслуживания */
	private Long theServiceStream;
	
    /** Закрыть */
	@Comment("Закрыть СПО")
	@Persist
	public Long getIsCloseSpo() {return theIsCloseSpo;}
	public void setIsCloseSpo(Long aIsCloseSpo) {theIsCloseSpo = aIsCloseSpo;}

	/** Закрыть */
	private Long theIsCloseSpo;
	
	/** Гостиничная услуга */
	@Comment("Гостиничная услуга")
	@Persist
	public Boolean getHotelServices() {return theHotelServices;}
	public void setHotelServices(Boolean aHotelServices) {theHotelServices = aHotelServices;}

	/** Гостиничная услуга */
	private Boolean theHotelServices;
}
