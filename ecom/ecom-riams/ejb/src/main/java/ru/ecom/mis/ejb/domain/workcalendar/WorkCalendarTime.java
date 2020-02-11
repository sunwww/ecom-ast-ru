package ru.ecom.mis.ejb.domain.workcalendar;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceReserveType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWayOfRecord;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Рабочее время
 * @author azviagin
 *
 */
@Comment("Рабочее время")
@Entity
@AIndexes({
	@AIndex(properties = {"medCase","workCalendarDay","timeFrom"}),
	@AIndex(properties = {"workCalendarDay"}),
	@AIndex(properties = {"createDatePreRecord"})
})
@Table(schema="SQLUser")
public class WorkCalendarTime extends BaseEntity{

	/** Удаленная запись */
	@Comment("Удаленная запись")
	public Boolean getIsDeleted() {return theIsDeleted;}
	public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
	/** Удаленная запись */
	private Boolean theIsDeleted ;

	/** Рабочий день */
	@Comment("Рабочий день")
	@ManyToOne
	public WorkCalendarDay getWorkCalendarDay() {return theWorkCalendarDay;}
	public void setWorkCalendarDay(WorkCalendarDay aWorkCalendarDay) {theWorkCalendarDay = aWorkCalendarDay;}
	/** Рабочий день */
	private WorkCalendarDay theWorkCalendarDay;
	
	/** Время начала */
	@Comment("Время начала")
	public Time getTimeFrom() {return theTimeFrom;}
	public void setTimeFrom(Time aTimeFrom) {theTimeFrom = aTimeFrom;}
	/** Время начала */
	private Time theTimeFrom;

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
	/** СМО */
	private MedCase theMedCase;
	
	/** Добавочное время */
	@Comment("Добавочное время")
	public Boolean getAdditional() {return theAdditional;}
	public void setAdditional(Boolean aAdditional) {theAdditional = aAdditional;}
	/** Добавочное время */
	private Boolean theAdditional;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}
	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	

	/** Нерабочее время */
	@Comment("Нерабочее время")
	public Boolean getRest() {return theRest;}
	public void setRest(Boolean aRest) {theRest = aRest;}
	/** Нерабочее время */
	private Boolean theRest;
	
	/** Пациент информации */
	@Comment("Пациент информации")
	public String getPrePatientInfo() {return thePrePatientInfo;}
	public void setPrePatientInfo(String aPatientInfo) {thePrePatientInfo = aPatientInfo;}
	/** Пациент информации */
	private String thePrePatientInfo;
	
	/** Пациент пред.пациента */
	@Comment("Пациент пред.пациента")
	@OneToOne
	public Patient getPrePatient() {return thePrePatient;}
	public void setPrePatient(Patient aPrePatient) {thePrePatient = aPrePatient;}
	/** Пациент пред.пациента */
	private Patient thePrePatient;

	/** Пользователь, создавший пред.запись */
	@Comment("Пользователь, создавший пред.запись")
	public String getCreatePreRecord() {return theCreatePreRecord;}
	public void setCreatePreRecord(String aCreatePreRecord) {theCreatePreRecord = aCreatePreRecord;}
	/** Пользователь, создавший пред.запись */
	private String theCreatePreRecord;

	/** Дата создания предварительной записи */
	@Comment("Дата создания предварительной записи")
	public Date getCreateDatePreRecord() {return theCreateDatePreRecord;}
	public void setCreateDatePreRecord(Date aCreateDatePreRecord) {theCreateDatePreRecord = aCreateDatePreRecord;}
	/** Дата создания предварительной записи */
	private Date theCreateDatePreRecord;

	/** Время создания предварительной записи */
	@Comment("Время создания предварительной записи")
	public Time getCreateTimePreRecord() {return theCreateTimePreRecord;}
	public void setCreateTimePreRecord(Time aCreateTimePreRecord) {theCreateTimePreRecord = aCreateTimePreRecord;}
	/** Время создания предварительной записи */
	private Time theCreateTimePreRecord;

	/** Резерв времени */
	@Comment("Резерв времени")
	@OneToOne
	public VocServiceReserveType getReserveType() {return theReserveType;}
	public void setReserveType(VocServiceReserveType aReserveType) {theReserveType = aReserveType;}
	/** Резерв времени */
	private VocServiceReserveType theReserveType;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата создания */
	private Date theCreateDate;

	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время создания */
	private Time theCreateTime;

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, создавший запись */
	private String theCreateUsername;

	/** Номер телефона */
	@Comment("Номер телефона")
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}
	/** Номер телефона */
	private String thePhone;

	/** Услуга */
	@Comment("Услуга")
	public Long getService() {return theService;}
	public void setService(Long aService) {theService = aService;}
	/** Услуга */
	private Long theService;

	/** Назначение */
	@Comment("Назначение")
	public Long getPrescription() {return thePrescription;}
	public void setPrescription(Long aPrescription) {thePrescription = aPrescription;}
	/** Назначение */
	private Long thePrescription;
	
	/** Предварительная госпитализация */
	@Comment("Предварительная госпитализация")
	public Long getPreHospital() {return thePreHospital;}
	public void setPreHospital(Long aPreHospital) {thePreHospital = aPreHospital;}
	/** Предварительная госпитализация */
	private Long thePreHospital;

	/** Примечание пациента (при записи)*/
	@Comment("Примечание пациента")
	@Column(length= ColumnConstants.TEXT_MAXLENGHT)
	public String getPatientComment() {return thePatientComment;}
	public void setPatientComment(String aPatientComment) {thePatientComment = aPatientComment;}
	/** Примечание пациента */
	private String thePatientComment ;

	@PrePersist
	void prePersist() {
		Date currentDate = new Date(System.currentTimeMillis());
		theCreateDate = currentDate;
		theCreateTime=new Time(currentDate.getTime());
	}

	/** Тип способа обращения */
	@Comment("Тип способа обращения")
	@OneToOne
	public VocWayOfRecord getWayOfRecord() {return theWayOfRecord;}
	public void setWayOfRecord(VocWayOfRecord aWayOfRecord) {theWayOfRecord = aWayOfRecord;}
	/** Тип способа обращения */
	private VocWayOfRecord theWayOfRecord;
}