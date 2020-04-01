package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@AIndexes({
	   @AIndex(properties= {"patient"})
})
public class PatientListRecord extends BaseEntity{

	
	/** Список */
	@Comment("Список")
	public Long getPatientList() {return thePatientList;}
	public void setPatientList(Long aPatientList) {thePatientList = aPatientList;}
	/** Список */
	private Long thePatientList;
	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Long thePatient;
	
	/** Сообщение */
	@Comment("Сообщение")
	public String getMessage() {return theMessage;}
	public void setMessage(String aMessage) {theMessage = aMessage;}
	/** Сообщение */
	private String theMessage;

	/** Номер телефона */
	@Comment("Номер телефона")
	public String getPhoneNumber() {return thePhoneNumber;}
	public void setPhoneNumber(String aPhoneNumber) {thePhoneNumber = aPhoneNumber;}
	/** Номер телефона */
	private String thePhoneNumber ;

}
