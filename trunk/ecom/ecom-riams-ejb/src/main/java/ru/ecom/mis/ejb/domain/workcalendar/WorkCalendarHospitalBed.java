package ru.ecom.mis.ejb.domain.workcalendar;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.lpu.HospitalBed;
import ru.ecom.mis.ejb.domain.lpu.HospitalRoom;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties={"hospitalBed"}) })
public class WorkCalendarHospitalBed extends BaseEntity {
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

}
