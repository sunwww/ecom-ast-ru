package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.ExternalDocument;
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

@EntityForm
@EntityFormPersistance(clazz = ExternalDocument.class)
@Comment("Внешние документы")
@WebTrail(comment = "Внешние документы", nameProperties = "id",view = "entityParentView-doc_externalDocument.do"
		,shortView="entityShortView-doc_externalDocument.do")
@Parent(property = "patient", parentForm = PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/External")
public class ExternalDocumentForm extends DocumentForm {
	
	/** Тип родителя */
	@Comment("Тип родителя")
	public String getParentType() {return theParentType;}
	public void setParentType(String aParentType) {theParentType = aParentType;}
	/** Тип родителя */
	private String theParentType;
	
	/** Ссылка на файл */
	@Comment("Ссылка на файл")
	@Persist
	public String getReferenceTo() {return theReferenceTo;}

	public void setReferenceTo(String aLinkFile) {theReferenceTo = aLinkFile;}
	/** Ссылка на сжатый файл */
	@Comment("Ссылка на сжатый файл")
	@Persist
	public String getReferenceCompTo() {
		return theReferenceCompTo;
	}

	public void setReferenceCompTo(String aReferenceCompTo) {
		theReferenceCompTo = aReferenceCompTo;
	}

	/** Ссылка на сжатый файл */
	private String theReferenceCompTo;
	/** Ссылка на файл */
	private String theReferenceTo;
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Комментарий */
	private String theComment;
	/** Пациент */
	private Long thePatient;
	/**
	 * Фамилия пациента
	 */
	@Comment("Фамилия пациента")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
	@DateString @DoDateString
	public String getPatientBirthday() {
		return thePatientBirthday;
	}
	public void setPatientBirthday(String aPatientBirthday) {
		thePatientBirthday = aPatientBirthday;
	}
	/**
	 * Дата рождения пациента
	 */
	private String thePatientBirthday;
	/**
	 * Пол пациента
	 */
	@Comment("Пол пациента")
	@Persist
	public Long getPatientSex() {
		return thePatientSex;
	}
	public void setPatientSex(Long aPatientSex) {
		thePatientSex = aPatientSex;
	}
	/**
	 * Пол пациента
	 */
	private Long thePatientSex;
	/**
	 * Направитель
	 */
	@Comment("Направитель")
	@Persist
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
	@Persist
	@DateString @DoDateString
	public String getOrderDate() {
		return theOrderDate;
	}
	public void setOrderDate(String aOrderDate) {
		theOrderDate = aOrderDate;
	}
	/**
	 * Дата направления
	 */
	private String theOrderDate;
	/**
	 * Время направления
	 */
	@Comment("Время направления")
	@Persist @TimeString @DoTimeString
	public String getOrderTime() {
		return theOrderTime;
	}
	public void setOrderTime(String aOrderTime) {
		theOrderTime = aOrderTime;
	}
	/**
	 * Время направления
	 */
	private String theOrderTime;
	/**
	 * Направившее ЛПУ (подразделение)
	 */
	@Comment("Направившее ЛПУ (подразделение)")
	@Persist
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
	/** Код синхронизации */
	@Comment("Код синхронизации")
	@Persist
	public String getPatientSync() {return thePatientSync;}
	public void setPatientSync(String aPatientSync) {thePatientSync = aPatientSync;}

	/** Код синхронизации */
	private String thePatientSync;
}

