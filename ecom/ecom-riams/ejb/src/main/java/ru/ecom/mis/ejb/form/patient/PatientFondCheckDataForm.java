package ru.ecom.mis.ejb.form.patient;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.PatientFondCheckData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Проверка по базе ФОМС
 */
@EntityForm
@EntityFormPersistance(clazz = PatientFondCheckData.class)
@Comment("Проверка по базе ФОМС")
@WebTrail(comment = "Персона", nameProperties = {"id"}
	, view = "entityView-mis_patientFondCheckData.do", shortView="entityShortView-mis_patientFondCheckData.do"
			,journal=true)
@EntityFormSecurityPrefix("/Policy/Mis/Patient")
@Setter

public class PatientFondCheckDataForm extends IdEntityForm {
    
	/** Список пациентов для проверки */
	@Comment("Список пациентов для проверки")
	public String getPatientList() {return patientList;}
	/** Список пациентов для проверки */
	private String patientList;
	
	
	/** Необходимо автоматически обновлять данные пациента */
	@Comment("Необходимо автоматически обновлять данные пациента")
	@Persist
	public Boolean  getNeedUpdatePatient() {return needUpdatePatient;}
	/** Необходимо автоматически обновлять данные пациента */
	private Boolean  needUpdatePatient;
	
	/** Необходимо обновлять данные паспорта */
	@Comment("Необходимо обновлять данные паспорта")
	@Persist
	public Boolean getNeedUpdateDocument() {return needUpdateDocument;}
	/** Необходимо обновлять данные паспорта */
	private Boolean needUpdateDocument;
	
	/** Необходимо обновлять данные полиса */
	@Comment("Необходимо обновлять данные полиса")
	@Persist
	public Boolean getNeedUpdatePolicy() {return needUpdatePolicy;}
	/** Необходимо обновлять данные полиса */
	private Boolean needUpdatePolicy;
	
	/** Необходимо обновлять данные прикрепления */
	@Comment("Необходимо обновлять данные прикрепления")
	@Persist
	public Boolean getNeedUpdateAttachment() {return needUpdateAttachment;}
	/** Необходимо обновлять данные прикрепления */
	private Boolean needUpdateAttachment;

	/** Статус(текст) */
	@Comment("Статус(текст)")
	@Persist
	public String getStatusText() {return statusText;}
	/** Статус(текст) */
	private String statusText;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return comment;}
	/** Комментарий */
	private String comment;
	
	/** Дата начала импорта */
	@Comment("Дата начала импорта")
	@Persist @DateString @DoDateString
	public String getStartDate() {return startDate;}
	/** Дата начала импорта */
	private String startDate;
	
	/** Дата окончания импорта */
	@Comment("Дата окончания импорта")
	@Persist @DateString @DoDateString
	public String getFinishDate() {return finishDate;}

	/** Дата окончания импорта */
	private String finishDate;
	
}