package ru.ecom.mis.ejb.form.licence;

import lombok.Setter;
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
@Setter
public class ExternalDocumentForm extends DocumentForm {
	
	/** Тип родителя */
	@Comment("Тип родителя")
	public String getParentType() {return parentType;}
	/** Тип родителя */
	private String parentType;
	
	/** Ссылка на файл */
	@Comment("Ссылка на файл")
	@Persist
	public String getReferenceTo() {return referenceTo;}

	/** Ссылка на сжатый файл */
	@Comment("Ссылка на сжатый файл")
	@Persist
	public String getReferenceCompTo() {
		return referenceCompTo;
	}

	/** Ссылка на сжатый файл */
	private String referenceCompTo;
	/** Ссылка на файл */
	private String referenceTo;
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return comment;}

	/** Комментарий */
	private String comment;
	/** Пациент */
	private Long patient;
	/**
	 * Фамилия пациента
	 */
	@Comment("Фамилия пациента")
	@Persist
	public String getPatientLastname() {
		return patientLastname;
	}
	/**
	 * Фамилия пациента
	 */
	private String patientLastname;
	/**
	 * Имя пациента
	 */
	@Comment("Имя пациента")
	@Persist
	public String getPatientFirstname() {
		return patientFirstname;
	}
	/**
	 * Имя пациента
	 */
	private String patientFirstname;
	/**
	 * Отчество пациента
	 */
	@Comment("Отчество пациента")
	@Persist
	public String getPatientMiddlename() {
		return patientMiddlename;
	}
	/**
	 * Отчество пациента
	 */
	private String patientMiddlename;
	/**
	 * Дата рождения пациента
	 */
	@Comment("Дата рождения пациента")
	@Persist
	@DateString @DoDateString
	public String getPatientBirthday() {
		return patientBirthday;
	}
	/**
	 * Дата рождения пациента
	 */
	private String patientBirthday;
	/**
	 * Пол пациента
	 */
	@Comment("Пол пациента")
	@Persist
	public Long getPatientSex() {
		return patientSex;
	}
	/**
	 * Пол пациента
	 */
	private Long patientSex;
	/**
	 * Направитель
	 */
	@Comment("Направитель")
	@Persist
	public String getOrderer() {
		return orderer;
	}
	/**
	 * Направитель
	 */
	private String orderer;
	/**
	 * Дата направления
	 */
	@Comment("Дата направления")
	@Persist
	@DateString @DoDateString
	public String getOrderDate() {
		return orderDate;
	}
	/**
	 * Дата направления
	 */
	private String orderDate;
	/**
	 * Время направления
	 */
	@Comment("Время направления")
	@Persist @TimeString @DoTimeString
	public String getOrderTime() {
		return orderTime;
	}
	/**
	 * Время направления
	 */
	private String orderTime;
	/**
	 * Направившее ЛПУ (подразделение)
	 */
	@Comment("Направившее ЛПУ (подразделение)")
	@Persist
	public String getOrderLpu() {
		return orderLpu;
	}
	/**
	 * Направившее ЛПУ (подразделение)
	 */
	private String orderLpu;
	/** Код синхронизации */
	@Comment("Код синхронизации")
	@Persist
	public String getPatientSync() {return patientSync;}

	/** Код синхронизации */
	private String patientSync;
}

