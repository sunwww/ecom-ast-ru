package ru.ecom.mis.ejb.domain.licence;

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
public class ExternalDocument extends Document {

	/** Ссылка на время предварительной записи */
	@Comment("Ссылка на время предварительной записи")
	@OneToOne
	public WorkCalendarTime getCalendarTime() {return theCalendarTime;}
	public void setCalendarTime(WorkCalendarTime aCalendarTime) {theCalendarTime = aCalendarTime;}
	/** Ссылка на время предварительной записи */
	private WorkCalendarTime theCalendarTime ;

	/** Ссылка на файл */
	@Comment("Ссылка на файл")
	public String getReferenceTo() {return theReferenceTo;}
	public void setReferenceTo(String aLinkFile) {	theReferenceTo = aLinkFile;}

	/** Тип документа */
	@Comment("Тип документа")
	@OneToOne
	public VocExternalDocumentType getType() {return theType;}
	public void setType(VocExternalDocumentType aType) {theType = aType;}
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** Комментарий */
	@Comment("Комментарий")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	@Comment("Ссылка на сжатый файл")
	public String getReferenceCompTo() {
		return theReferenceCompTo;
	}

	public void setReferenceCompTo(String aReferenceCompTo) {
		theReferenceCompTo = aReferenceCompTo;
	}
	
	/**
	 * Фамилия пациента
	 */
	@Comment("Фамилия пациента")
	
	public String getPatientLastname() {
		return thePatientLastname;
	}
	public void setPatientLastname(String aPatientLastname) {
		thePatientLastname = aPatientLastname;
	}
	/**
	 * Фамилия пациента
	 */
	private String thePatientLastname;
	/**
	 * Имя пациента
	 */
	@Comment("Имя пациента")
	
	public String getPatientFirstname() {
		return thePatientFirstname;
	}
	public void setPatientFirstname(String aPatientFirstname) {
		thePatientFirstname = aPatientFirstname;
	}
	/**
	 * Имя пациента
	 */
	private String thePatientFirstname;
	/**
	 * Отчество пациента
	 */
	@Comment("Отчество пациента")
	
	public String getPatientMiddlename() {
		return thePatientMiddlename;
	}
	public void setPatientMiddlename(String aPatientMiddlename) {
		thePatientMiddlename = aPatientMiddlename;
	}
	/**
	 * Отчество пациента
	 */
	private String thePatientMiddlename;
	/**
	 * Дата рождения пациента
	 */
	@Comment("Дата рождения пациента")
	
	public Date getPatientBirthday() {
		return thePatientBirthday;
	}
	public void setPatientBirthday(Date aPatientBirthday) {
		thePatientBirthday = aPatientBirthday;
	}
	/**
	 * Дата рождения пациента
	 */
	private Date thePatientBirthday;
	/**
	 * Пол пациента
	 */
	@Comment("Пол пациента")
	@OneToOne
	public VocSex getPatientSex() {
		return thePatientSex;
	}
	public void setPatientSex(VocSex aPatientSex) {
		thePatientSex = aPatientSex;
	}
	/**
	 * Пол пациента
	 */
	private VocSex thePatientSex;
	/**
	 * Направитель
	 */
	@Comment("Направитель")
	
	public String getOrderer() {
		return theOrderer;
	}
	public void setOrderer(String aOrderer) {
		theOrderer = aOrderer;
	}
	/**
	 * Направитель
	 */
	private String theOrderer;
	/**
	 * Дата направления
	 */
	@Comment("Дата направления")
	
	public Date getOrderDate() {
		return theOrderDate;
	}
	public void setOrderDate(Date aOrderDate) {
		theOrderDate = aOrderDate;
	}
	/**
	 * Дата направления
	 */
	private Date theOrderDate;
	/**
	 * Время направления
	 */
	@Comment("Время направления")
	
	public Time getOrderTime() {
		return theOrderTime;
	}
	public void setOrderTime(Time aOrderTime) {
		theOrderTime = aOrderTime;
	}
	/**
	 * Время направления
	 */
	private Time theOrderTime;
	/**
	 * Направившее ЛПУ (подразделение)
	 */
	@Comment("Направившее ЛПУ (подразделение)")
	
	public String getOrderLpu() {
		return theOrderLpu;
	}
	public void setOrderLpu(String aOrderLpu) {
		theOrderLpu = aOrderLpu;
	}
	/**
	 * Направившее ЛПУ (подразделение)
	 */
	private String theOrderLpu;

	/** Ссылка на сжатый файл */
	private String theReferenceCompTo;
	/** Комментарий */
	private String theComment;
	/** Пациент */
	private Patient thePatient;
	/** Тип документа */
	private VocExternalDocumentType theType;
	/** Ссылка на файл */
	private String theReferenceTo;
	
	/** Код синхронизации */
	@Comment("Код синхронизации")
	public String getPatientSync() {return thePatientSync;}
	public void setPatientSync(String aPatientSync) {thePatientSync = aPatientSync;}

	/** Код синхронизации */
	private String thePatientSync;
}
