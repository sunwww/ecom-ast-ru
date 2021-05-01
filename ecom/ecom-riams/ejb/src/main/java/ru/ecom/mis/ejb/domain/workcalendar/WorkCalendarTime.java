package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class WorkCalendarTime extends BaseEntity{
	/** Удаленная запись */
	private Boolean isDeleted ;

	/** Рабочий день */
	@Comment("Рабочий день")
	@ManyToOne
	public WorkCalendarDay getWorkCalendarDay() {return workCalendarDay;}
	private WorkCalendarDay workCalendarDay;
	
	/** Время начала */
	private Time timeFrom;

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}
	/** СМО */
	private MedCase medCase;

	/** Добавочное время */
	private Boolean additional;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return serviceStream;}
	private VocServiceStream serviceStream;
	

	/** Нерабочее время */
	private Boolean rest;
	
	/** Пациент информации */
	private String prePatientInfo;
	
	/** Пациент пред.пациента */
	@Comment("Пациент пред.пациента")
	@OneToOne
	public Patient getPrePatient() {return prePatient;}
	private Patient prePatient;

	/** Пользователь, создавший пред.запись */
	private String createPreRecord;

	/** Дата создания предварительной записи */
	private Date createDatePreRecord;

	/** Время создания предварительной записи */
	private Time createTimePreRecord;

	/** Резерв времени */
	@Comment("Резерв времени")
	@OneToOne
	public VocServiceReserveType getReserveType() {return reserveType;}
	private VocServiceReserveType reserveType;
	
	/** Дата создания */
	private Date createDate;

	/** Время создания */
	private Time createTime;

	/** Пользователь, создавший запись */
	private String createUsername;

	/** Номер телефона */
	private String phone;

	/** Услуга */
	private Long service;

	/** Назначение */
	private Long prescription;
	
	/** Предварительная госпитализация */
	private Long preHospital;

	/** Примечание пациента (при записи)*/
	@Comment("Примечание пациента")
	@Column(length= ColumnConstants.TEXT_MAXLENGHT)
	public String getPatientComment() {return patientComment;}
	private String patientComment ;

	@PrePersist
	void prePersist() {
		Date currentDate = new Date(System.currentTimeMillis());
		createDate = currentDate;
		createTime=new Time(currentDate.getTime());
	}

	/** Тип способа обращения */
	@Comment("Тип способа обращения")
	@OneToOne
	public VocWayOfRecord getWayOfRecord() {return wayOfRecord;}
	private VocWayOfRecord wayOfRecord;
}