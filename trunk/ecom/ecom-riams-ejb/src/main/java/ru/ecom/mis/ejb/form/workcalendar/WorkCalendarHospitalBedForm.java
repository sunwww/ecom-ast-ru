package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarHospitalBed.class)
@Comment("Планирование госпитализации")
@WebTrail(comment = "Планирование госпитализации", nameProperties= "id"
, list="stac_planning_hospitalizations.do"
, view="entityView-stac_planHospital.do")
//@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Planning")
public class WorkCalendarHospitalBedForm extends IdEntityForm {
	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Палата */
	@Comment("Палата")
	@Persist @Required
	public Long getHospitalRoom() {return theHospitalRoom;}
	public void setHospitalRoom(Long aHospitalRoom) {theHospitalRoom = aHospitalRoom;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

	/** Примечание */
	@Comment("Примечание")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Предполагается операция */
	@Comment("Предполагается операция")
	@Persist
	public Boolean getIsOperation() {return theIsOperation;}
	public void setIsOperation(Boolean aIsOperation) {theIsOperation = aIsOperation;}

	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}

	/** Текст диагноза */
	@Comment("Текст диагноза")
	@Persist
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Фактическая госпитализация */
	@Comment("Фактическая госпитализация")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Предполагаемая дата начала госпитализации */
	@Comment("Предполагаемая дата начала госпитализации")
	@Persist @DateString @DoDateString
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}

	/** Предполагаемая дата окончания госпитализации */
	@Comment("Предсполагаемая дата окончания госпитализации")
	@Persist @DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	/** Предполагаемое количество дней госпитализации */
	@Comment("Предполагаемое количество дней госпитализации")
	@Persist
	public Long getCntDays() {return theCntDays;}
	public void setCntDays(Long aCntDays) {theCntDays = aCntDays;}

	/** ФИО пациента */
	@Comment("ФИО пациента")
	@Persist
	public String getFio() {return theFio;}
	public void setFio(String aFio) {theFio = aFio;}

	/** Телефон пациента */
	@Comment("Телефон пациента")
	@Persist
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}

	/** Койка */
	@Comment("Койка")
	@Persist
	public Long getHospitalBed() {return theHospitalBed;}
	public void setHospitalBed(Long aHospitalBed) {theHospitalBed = aHospitalBed;}

	/** Койка */
	private Long theHospitalBed;
	/** Пол */
	private Long theSex;
	/** Телефон пациента */
	private String thePhone;
	/** ФИО пациента */
	private String theFio;
	/** Предполагаемое количество дней госпитализации */
	private Long theCntDays;
	/** Предполагаемая дата окончания госпитализации */
	private String theDateTo;
	/** Предполагаемая дата начала госпитализации */
	private String theDateFrom;
	/** Фактическая госпитализация */
	private Long theMedCase;
	/** Текст диагноза */
	private String theDiagnosis;
	/** Диагноз */
	private Long theIdc10;
	/** Предполагается операция */
	private Boolean theIsOperation;
	/** Примечание */
	private String theComment;
	/** Поток обслуживания */
	private Long theServiceStream;
	/** Палата */
	private Long theHospitalRoom;
	/** Пациент */
	private Long thePatient;
	/** Отделение */
	private Long theDepartment;
}
