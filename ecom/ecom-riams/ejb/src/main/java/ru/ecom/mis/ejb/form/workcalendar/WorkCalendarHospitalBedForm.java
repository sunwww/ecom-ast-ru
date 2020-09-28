package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed;
import ru.ecom.mis.ejb.form.workcalendar.interceptor.WorkCalendarHospitalBedSave;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarHospitalBed.class)
@Comment("Планирование госпитализации")
@WebTrail(comment = "Планирование госпитализации", nameProperties= "id"
, list="stac_planning_hospitalizations.do"
, view="entityView-stac_planHospital.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Planning")
@ACreateInterceptors(
		@AEntityFormInterceptor(WorkCalendarHospitalBedSave.class)
)
public class WorkCalendarHospitalBedForm extends IdEntityForm {

	/** Внутренний номер направления */
	@Comment("Внутренний номер направления")
	@Persist
	public String getInternalCode() {return theInternalCode;}
	public void setInternalCode(String aInternalCode) {theInternalCode = aInternalCode;}
	/** Внутренний номер направления */
	private String theInternalCode ;

	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Пациент */
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Палата */
	@Comment("Палата")
	@Persist
	public Long getHospitalRoom() {return theHospitalRoom;}
	public void setHospitalRoom(Long aHospitalRoom) {theHospitalRoom = aHospitalRoom;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
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
	@Persist @Required
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
	@Persist @DateString @DoDateString @Required
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
	@Persist @Required
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

	/** СМО */
	@Comment("СМО")
	@Persist 
	public Long getVisit() {return theVisit;}
	public void setVisit(Long aVisit) {theVisit = aVisit;}

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist 
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Рабочая функция */
	private Long theWorkFunction;
	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	/** СМО */
	private Long theVisit;	
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
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist @Required
	public Long getBedType() {return theBedType;}
	public void setBedType(Long aBedType) {theBedType = aBedType;}

	
	/** Тип коек */
	@Comment("Тип коек")
	@Persist @Required
	public Long getBedSubType() {return theBedSubType;}
	public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}

	/** Тип коек */
	private Long theBedSubType;
	/** Профиль коек */
	private Long theBedType;	
	
	/** Откуда направление */
	@Comment("Откуда направление")
	@Persist @Required
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}
	/** Откуда направление */
	private Long theOrderLpu;

	/** ЛПУ куда направляется */
	@Comment("ЛПУ куда направляется")
	@Persist
	public Long getDirectLpu() {return theDirectLpu;}
	public void setDirectLpu(Long aDirectLpu) {theDirectLpu = aDirectLpu;}
	/** ЛПУ куда направляется */
	private Long theDirectLpu ;

}
