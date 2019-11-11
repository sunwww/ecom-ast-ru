package ru.ecom.mis.ejb.domain.workcalendar;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.lpu.HospitalBed;
import ru.ecom.mis.ejb.domain.lpu.HospitalRoom;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocIndicationHospitalization;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties={"hospitalBed"}) 
		, @AIndex(properties={"visit"}) 
		, @AIndex(properties={"medCase"}) 
		})
public class WorkCalendarHospitalBed extends BaseEntity {

	/** Внутренний номер направления */
	@Comment("Внутренний номер направления")
	public String getInternalCode() {return theInternalCode;}
	public void setInternalCode(String aInternalCode) {theInternalCode = aInternalCode;}
	/** Внутренний номер направлания */
	private String theInternalCode ;

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** Палата */
	@Comment("Палата")
	@OneToOne
	public HospitalRoom getHospitalRoom() {return theHospitalRoom;}
	public void setHospitalRoom(HospitalRoom aHospitalRoom) {theHospitalRoom = aHospitalRoom;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}

	/** Примечание */
	@Comment("Примечание")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Предполагается операция */
	@Comment("Предполагается операция")
	public Boolean getIsOperation() {return theIsOperation;}
	public void setIsOperation(Boolean aIsOperation) {theIsOperation = aIsOperation;}

	/** Диагноз */
	@Comment("Диагноз")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}

	/** Текст диагноза */
	@Comment("Текст диагноза")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Фактическая госпитализация */
	@Comment("Фактическая госпитализация")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Предполагаемая дата начала госпитализации */
	@Comment("Предполагаемая дата начала госпитализации")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	/** Предполагаемая дата окончания госпитализации */
	@Comment("Предсполагаемая дата окончания госпитализации")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	/** Предполагаемое количество дней госпитализации */
	@Comment("Предполагаемое количество дней госпитализации")
	public Long getCntDays() {return theCntDays;}
	public void setCntDays(Long aCntDays) {theCntDays = aCntDays;}

	/** ФИО пациента */
	@Comment("ФИО пациента")
	public String getFio() {return theFio;}
	public void setFio(String aFio) {theFio = aFio;}

	/** Телефон пациента */
	@Comment("Телефон пациента")
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return theSex;}
	public void setSex(VocSex aSex) {theSex = aSex;}

	/** Койка */
	@Comment("Койка")
	@OneToOne
	public HospitalBed getHospitalBed() {return theHospitalBed;}
	public void setHospitalBed(HospitalBed aHospitalBed) {theHospitalBed = aHospitalBed;}

	/** Койка */
	private HospitalBed theHospitalBed;
	/** Пол */
	private VocSex theSex;
	/** Телефон пациента */
	private String thePhone;
	/** ФИО пациента */
	private String theFio;
	/** Предполагаемое количество дней госпитализации */
	private Long theCntDays;
	/** Предполагаемая дата окончания госпитализации */
	private Date theDateTo;
	/** Предполагаемая дата начала госпитализации */
	private Date theDateFrom;
	/** Фактическая госпитализация */
	private MedCase theMedCase;
	/** Текст диагноза */
	private String theDiagnosis;
	/** Диагноз */
	private VocIdc10 theIdc10;
	/** Предполагается операция */
	private Boolean theIsOperation;
	/** Примечание */
	private String theComment;
	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	/** Палата */
	private HospitalRoom theHospitalRoom;
	/** Пациент */
	private Patient thePatient;
	/** Отделение */
	private MisLpu theDepartment;
	
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getVisit() {
		return theVisit;
	}

	public void setVisit(MedCase aVisit) {
		theVisit = aVisit;
	}

	/** СМО */
	private MedCase theVisit;
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
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Рабочая функция */
	private WorkFunction theWorkFunction;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return theBedType;}
	public void setBedType(VocBedType aBedType) {theBedType = aBedType;}

	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return theBedSubType;}
	public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;}

	/** Тип коек */
	private VocBedSubType theBedSubType;
	/** Профиль коек */
	private VocBedType theBedType;
	
	/** Откуда направление */
	@Comment("Откуда направление")
	@OneToOne
	public MisLpu getOrderLpu() {
		return theOrderLpu;
	}
	public void setOrderLpu(MisLpu aOrderLpu) {
		theOrderLpu = aOrderLpu;
	}
	/** Откуда направление */
	private MisLpu theOrderLpu;

	/** ЛПУ куда направляется */
	@Comment("ЛПУ куда направляется")
	@OneToOne
	public MisLpu getDirectLpu() {return theDirectLpu;}
	public void setDirectLpu(MisLpu aDirectLpu) {theDirectLpu = aDirectLpu;}
	/** ЛПУ куда направляется */
	private MisLpu theDirectLpu ;
	
	/** Показания для госпитализации */
	@Comment("Показания для госпитализации")
	@OneToOne
	public VocIndicationHospitalization getIndicationToHosp() {return theIndicationToHosp;}
	public void setIndicationToHosp(VocIndicationHospitalization aIndicationToHosp) {theIndicationToHosp = aIndicationToHosp;}
	/** Показания для госпитализации */
	private VocIndicationHospitalization theIndicationToHosp;
}