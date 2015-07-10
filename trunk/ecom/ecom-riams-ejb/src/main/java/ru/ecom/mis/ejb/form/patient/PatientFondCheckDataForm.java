package ru.ecom.mis.ejb.form.patient;


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

public class PatientFondCheckDataForm extends IdEntityForm {
    
	/** Необходимо автоматически обновлять данные пациента */
	@Comment("Необходимо автоматически обновлять данные пациента")
	@Persist
	public Boolean  getNeedUpdatePatient() {return theNeedUpdatePatient;}
	public void setNeedUpdatePatient(Boolean  aNeedUpdatePatient) {theNeedUpdatePatient = aNeedUpdatePatient;}
	/** Необходимо автоматически обновлять данные пациента */
	private Boolean  theNeedUpdatePatient;
	
	/** Необходимо обновлять данные паспорта */
	@Comment("Необходимо обновлять данные паспорта")
	@Persist
	public Boolean getNeedUpdateDocument() {return theNeedUpdateDocument;}
	public void setNeedUpdateDocument(Boolean aNeedUpdateDocument) {theNeedUpdateDocument = aNeedUpdateDocument;}
	/** Необходимо обновлять данные паспорта */
	private Boolean theNeedUpdateDocument;
	
	/** Необходимо обновлять данные полиса */
	@Comment("Необходимо обновлять данные полиса")
	@Persist
	public Boolean getNeedUpdatePolicy() {return theNeedUpdatePolicy;}
	public void setNeedUpdatePolicy(Boolean aNeedUpdatePolicy) {theNeedUpdatePolicy = aNeedUpdatePolicy;}
	/** Необходимо обновлять данные полиса */
	private Boolean theNeedUpdatePolicy;
	
	/** Необходимо обновлять данные прикрепления */
	@Comment("Необходимо обновлять данные прикрепления")
	@Persist
	public Boolean getNeedUpdateAttachment() {return theNeedUpdateAttachment;}
	public void setNeedUpdateAttachment(Boolean aNeedUpdateAttachment) {theNeedUpdateAttachment = aNeedUpdateAttachment;}
	/** Необходимо обновлять данные прикрепления */
	private Boolean theNeedUpdateAttachment;

	/** Статус(текст) */
	@Comment("Статус(текст)")
	@Persist
	public String getStatusText() {return theStatusText;}
	public void setStatusText(String aStatusText) {theStatusText = aStatusText;}
	/** Статус(текст) */
	private String theStatusText;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Комментарий */
	private String theComment;
	
	/** Дата начала импорта */
	@Comment("Дата начала импорта")
	@Persist @DateString @DoDateString
	public String getStartDate() {return theStartDate;}
	public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
	/** Дата начала импорта */
	private String theStartDate;
	
	/** Дата окончания импорта */
	@Comment("Дата окончания импорта")
	@Persist @DateString @DoDateString
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
	
	/** Дата окончания импорта */
	private String theFinishDate;
	
}