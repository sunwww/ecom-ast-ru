package ru.ecom.mis.ejb.domain.workcalendar;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceReserveType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Рабочее время
 * @author azviagin
 *
 */
@Comment("Рабочее время")
@Entity
@AIndexes({
	@AIndex(unique = false, properties = {"medCase","workCalendarDay","timeFrom"}),
	@AIndex(unique = false, properties = {"workCalendarDay"}),
	//@AIndex(unique = false, properties = {"timeFrom"})
	//,@AIndex(unique = false, properties = {"prePatient"})
	//,@AIndex(unique = false, properties = {"prePatientInfo"})
})
@Table(schema="SQLUser")
public class WorkCalendarTime extends BaseEntity{
	
	/** Рабочий день */
	@Comment("Рабочий день")
	@ManyToOne
	public WorkCalendarDay getWorkCalendarDay() {
		return theWorkCalendarDay;
	}

	public void setWorkCalendarDay(WorkCalendarDay aWorkCalendarDay) {
		theWorkCalendarDay = aWorkCalendarDay;
	}

	/** Рабочий день */
	private WorkCalendarDay theWorkCalendarDay;
	
	/** Время начала */
	@Comment("Время начала")
	public Time getTimeFrom() {
		return theTimeFrom;
	}

	public void setTimeFrom(Time aTimeFrom) {
		theTimeFrom = aTimeFrom;
	}

	/** Время начала */
	private Time theTimeFrom;
	
	/** Резервы обслуживания */
	@Comment("Резервы обслуживания")
	@ManyToMany
	public List<ServiceReserve> getServiceReserves() {
		return theServiceReserves;
	}

	public void setServiceReserves(List<ServiceReserve> aServiceReserves) {
		theServiceReserves = aServiceReserves;
	}

	/** Резервы обслуживания */
	private List<ServiceReserve> theServiceReserves;
	
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {
		return theMedCase;
	}

	public void setMedCase(MedCase aMedCase) {
		theMedCase = aMedCase;
	}

	/** СМО */
	private MedCase theMedCase;
	
	/** Добавочное время */
	@Comment("Добавочное время")
	public Boolean getAdditional() {
		return theAdditional;
	}

	public void setAdditional(Boolean aAdditional) {
		theAdditional = aAdditional;
	}

	/** Добавочное время */
	private Boolean theAdditional;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {
		return theServiceStream;
	}

	public void setServiceStream(VocServiceStream aServiceStream) {
		theServiceStream = aServiceStream;
	}

	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	
	/** Резерв персоны */
	@Comment("Резерв персоны")
	@OneToOne
	public Patient getPersonReserve() {
		return thePersonReserve;
	}

	public void setPersonReserve(Patient aPersonReserve) {
		thePersonReserve = aPersonReserve;
	}

	/** Резерв персоны */
	private Patient thePersonReserve;
	
	/** Нерабочее время */
	@Comment("Нерабочее время")
	public Boolean getRest() {
		return theRest;
	}

	public void setRest(Boolean aRest) {
		theRest = aRest;
	}

	/** Нерабочее время */
	private Boolean theRest;
	
	/** Пациент информации */
	@Comment("Пациент информации")
	public String getPrePatientInfo() {
		return thePrePatientInfo;
	}

	public void setPrePatientInfo(String aPatientInfo) {
		thePrePatientInfo = aPatientInfo;
	}
	
	/** Пациент пред.пациента */
	@Comment("Пациент пред.пациента")
	@OneToOne
	public Patient getPrePatient() {return thePrePatient;}
	public void setPrePatient(Patient aPrePatient) {thePrePatient = aPrePatient;}

	/** Пациент пред.пациента */
	private Patient thePrePatient;

	/** Пациент информации */
	private String thePrePatientInfo;
	
	/** Пользователь, создавший пред.запись */
	@Comment("Пользователь, создавший пред.запись")
	public String getCreatePreRecord() {
		return theCreatePreRecord;
	}

	public void setCreatePreRecord(String aCreatePreRecord) {
		theCreatePreRecord = aCreatePreRecord;
	}
	
	/** Дата создания предварительной записи */
	@Comment("Дата создания предварительной записи")
	public Date getCreateDatePreRecord() {return theCreateDatePreRecord;}
	public void setCreateDatePreRecord(Date aCreateDatePreRecord) {theCreateDatePreRecord = aCreateDatePreRecord;}

	/** Время создания предварительной записи */
	@Comment("Время создания предварительной записи")
	public Time getCreateTimePreRecord() {
		return theCreateTimePreRecord;
	}

	public void setCreateTimePreRecord(Time aCreateTimePreRecord) {
		theCreateTimePreRecord = aCreateTimePreRecord;
	}

	/** Время создания предварительной записи */
	private Time theCreateTimePreRecord;
	/** Дата создания предварительной записи */
	private Date theCreateDatePreRecord;
	/** Пользователь, создавший пред.запись */
	private String theCreatePreRecord;
	
	/** Резерв времени */
	@Comment("Резерв времени")
	@OneToOne
	public VocServiceReserveType getReserveType() {
		return theReserveType;
	}

	public void setReserveType(VocServiceReserveType aReserveType) {
		theReserveType = aReserveType;
	}

	/** Резерв времени */
	private VocServiceReserveType theReserveType;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Время создания */
	private Time theCreateTime;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Номер телефона */
	@Comment("Номер телефона")
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}

	/** Услуга */
	@Comment("Услуга")
	public Long getService() {return theService;}
	public void setService(Long aService) {theService = aService;}

	/** Услуга */
	private Long theService;
	/** Номер телефона */
	private String thePhone;
	
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
	
	
}
