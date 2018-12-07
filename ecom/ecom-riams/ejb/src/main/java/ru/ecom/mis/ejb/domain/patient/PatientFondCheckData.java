package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Entity
public class PatientFondCheckData extends BaseEntity {
	
	/** Необходимо автоматически обновлять данные пациента */
	@Comment("Необходимо автоматически обновлять данные пациента")
	public Boolean  getNeedUpdatePatient() {return theNeedUpdatePatient;}
	public void setNeedUpdatePatient(Boolean  aNeedUpdatePatient) {theNeedUpdatePatient = aNeedUpdatePatient;}
	/** Необходимо автоматически обновлять данные пациента */
	private Boolean  theNeedUpdatePatient;
	
	/** Необходимо обновлять данные паспорта */
	@Comment("Необходимо обновлять данные паспорта")
	public Boolean getNeedUpdateDocument() {return theNeedUpdateDocument;}
	public void setNeedUpdateDocument(Boolean aNeedUpdateDocument) {theNeedUpdateDocument = aNeedUpdateDocument;}
	/** Необходимо обновлять данные паспорта */
	private Boolean theNeedUpdateDocument;
	
	/** Необходимо обновлять данные полиса */
	@Comment("Необходимо обновлять данные полиса")
	public Boolean getNeedUpdatePolicy() {return theNeedUpdatePolicy;}
	public void setNeedUpdatePolicy(Boolean aNeedUpdatePolicy) {theNeedUpdatePolicy = aNeedUpdatePolicy;}
	/** Необходимо обновлять данные полиса */
	private Boolean theNeedUpdatePolicy;
	
	/** Необходимо обновлять данные прикрепления */
	@Comment("Необходимо обновлять данные прикрепления")
	public Boolean getNeedUpdateAttachment() {return theNeedUpdateAttachment;}
	public void setNeedUpdateAttachment(Boolean aNeedUpdateAttachment) {theNeedUpdateAttachment = aNeedUpdateAttachment;}
	/** Необходимо обновлять данные прикрепления */
	private Boolean theNeedUpdateAttachment;

	/** Статус(текст) */
	@Comment("Статус(текст)")
	public String getStatusText() {return theStatusText;}
	public void setStatusText(String aStatusText) {theStatusText = aStatusText;}
	/** Статус(текст) */
	private String theStatusText;
	
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Комментарий */
	private String theComment;
	
	/** Дата начала импорта */
	@Comment("Дата начала импорта")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	/** Дата начала импорта */
	private Date theStartDate;
	
	/** Дата окончания импорта */
	@Comment("Дата окончания импорта")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
	
	/** Дата окончания импорта */
	private Date theFinishDate;

	/** Список запией по проверке */
	@Comment("Список запией по проверке")
	@OneToMany
	public List<PatientFond> getPatientList() {return thePatientList;}
	public void setPatientList(List<PatientFond> aPatientList) {thePatientList = aPatientList;}
	/** Список запией по проверке */
	private List<PatientFond> thePatientList;
}
