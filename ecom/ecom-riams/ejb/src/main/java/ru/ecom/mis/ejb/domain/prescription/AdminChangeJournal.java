package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.sql.Date;
import java.sql.Time;

/**
 * Журнал аннулирования результатов лабораторных исследований
 * @author user
 *
 */
@Entity
public class AdminChangeJournal extends BaseEntity {

	public AdminChangeJournal() {}
	public AdminChangeJournal(String cType) {
		setCType(cType);
	}
	@PrePersist
	void onPrePersist() {
		long currentTime = System.currentTimeMillis();
		theCreateDate=new java.sql.Date(currentTime);
		theCreateTime=new java.sql.Time(currentTime);
	}
	
	/** Назначение, результаты которого аннулированы  */
	@Comment("Назначение, результаты которого аннулированы ")
	public Long getPrescription() {return thePrescription;}
	public void setPrescription(Long aPrescription) {thePrescription = aPrescription;}
	/** Назначение, результаты которого аннулированы  */
	private Long thePrescription;
	
	/** Визит по назначению */
	@Comment("Визит по назначению")
	public Long getMedCase() {	return theMedCase;}
	public void setMedCase(Long aMedCase) {	theMedCase = aMedCase;	}
	/** Визит по назначению */
	private Long theMedCase;
	
	/** Дата создания (аннулирования) */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	private Date theCreateDate;
	
	/** Время создания (аннулирования) */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	private Time theCreateTime;

	/** Пользователь, который создал запись  (аннулировал результаты) */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	private String theCreateUsername;

	/** Причина аннулирования */
	@Comment("Причина аннулирования")
	public String getAnnulReason() {return theAnnulReason;}
	public void setAnnulReason(String aAnnulReason) {theAnnulReason = aAnnulReason;}
	/** Причина аннулирования */
	private String theAnnulReason;
	
	/** Рабочая функция лица, аннулировавшего анализ */
	@Comment("Рабочая функция лица, аннулировавшего анализ")
	public Long getAnnulWorkFunction() {return theAnnulWorkFunction;}
	public void setAnnulWorkFunction(Long aAnnulWorkFunction) {theAnnulWorkFunction = aAnnulWorkFunction;}
	/** Рабочая функция лица, аннулировавшего анализ */
	private Long theAnnulWorkFunction;
	
	/** Рабочая функция лица, создавшего назначение */
	@Comment("Рабочая функция лица, создавшего назначение")
	public Long getPrescriptWorkFunction() {return thePrescriptWorkFunction;}
	public void setPrescriptWorkFunction(Long aPrescriptWorkFunction) {thePrescriptWorkFunction = aPrescriptWorkFunction;}
	/** Рабочая функция лица, создавшего назначение */
	private Long thePrescriptWorkFunction;
	
	/** Текст аннулированого результат */
	@Comment("Текст аннулированого результат")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getAnnulRecord() {return theAnnulRecord;}
	public void setAnnulRecord(String aAnnulRecord) {theAnnulRecord = aAnnulRecord;}
	/** Текст аннулированого результат */
	private String theAnnulRecord;
	
	/** Тип */
	@Comment("Тип")
	public String getCType() {return theCType;}
	public void setCType(String aCType) {theCType = aCType;}

	/** Тип */
	private String theCType;
	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Пациент */
	private Long thePatient;

	/** Дата выписки (которая была удалена) */
	@Comment("Дата выписки (которая была удалена)")
	public Date getDateFinishDisch() {return theDateFinishDisch;}
	public void setDateFinishDisch(Date aDateFinishDisch) {theDateFinishDisch = aDateFinishDisch;}
	private Date theDateFinishDisch;

	/** Время выписки (которое было удалено) */
	@Comment("Время выписки (которое было удалено)")
	public Time getTimeDisch() {return theTimeDisch;}
	public void setTimeDisch(Time aTimeDisch) {theTimeDisch = aTimeDisch;}
	private Time theTimeDisch;
}
