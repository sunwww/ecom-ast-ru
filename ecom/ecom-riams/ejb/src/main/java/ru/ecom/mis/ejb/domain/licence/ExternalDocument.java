package ru.ecom.mis.ejb.domain.licence;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.licence.voc.VocExternalDocumentType;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

@Entity
@Comment("Внешние документы")
@AIndexes(value = { @AIndex(properties = { "patient" },table="Document") })
@Getter
@Setter
public class ExternalDocument extends Document {

	/** Ссылка на время предварительной записи */
	@Comment("Ссылка на время предварительной записи")
	@OneToOne
	public WorkCalendarTime getCalendarTime() {return calendarTime;}
	/** Ссылка на время предварительной записи */
	private WorkCalendarTime calendarTime ;

	/** Тип документа */
	@Comment("Тип документа")
	@OneToOne
	public VocExternalDocumentType getType() {return type;}

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Комментарий */
	@Comment("Комментарий")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return comment;}

	/**
	 * Фамилия пациента
	 */
	private String patientLastname;
	/**
	 * Имя пациента
	 */
	private String patientFirstname;
	/**
	 * Отчество пациента
	 */
	private String patientMiddlename;
	/**
	 * Дата рождения пациента
	 */
	private Date patientBirthday;
	/**
	 * Пол пациента
	 */
	@Comment("Пол пациента")
	@OneToOne
	public VocSex getPatientSex() {
		return patientSex;
	}
	/**
	 * Пол пациента
	 */
	private VocSex patientSex;
	/**
	 * Направитель
	 */
	private String orderer;
	/**
	 * Дата направления
	 */
	private Date orderDate;
	/**
	 * Время направления
	 */
	private Time orderTime;
	/**
	 * Направившее ЛПУ (подразделение)
	 */
	private String orderLpu;
	/** Ссылка на сжатый файл */
	private String referenceCompTo;
	/** Комментарий */
	private String comment;
	/** Пациент */
	private Patient patient;
	/** Тип документа */
	private VocExternalDocumentType type;
	/** Ссылка на файл */
	private String referenceTo;
	/** Код синхронизации */
	private String patientSync;
}
