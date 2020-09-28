package ru.ecom.mis.ejb.domain.worker;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.voc.VocAttorneyType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Date;

@Entity
public class Attorney extends BaseEntity {
	
	/** Лицо, на которое выдана доверенность */
	@Comment("Лицо, на которое выдана доверенность")
	@OneToOne
	public Patient getPerson() {return thePerson;}
	public void setPerson(Patient aPerson) {thePerson = aPerson;}
	/** Лицо, на которое выдана доверенность */
	private Patient thePerson;
	
	/** ФИО в родительном падеже */
	@Comment("ФИО в родительном падеже")
	public String getAltPersonInfo() {return theAltPersonInfo;}
	public void setAltPersonInfo(String aAltPersonInfo) {theAltPersonInfo = aAltPersonInfo;}
	/** ФИО в родительном падеже */
	private String theAltPersonInfo;
	
	/** Фамилия И.О. */
	@Comment("Фамилия И.О.")
	@Transient
	public String getShortPersonInfo() {
		if (getPerson()==null) {
			return null;
		}
		Patient p =  getPerson();
		return p.getLastname() +" "+p.getFirstname().substring(0,1)+" "+p.getMiddlename().substring(0,1);
	}
	
	/** Номер доверенности */
	@Comment("Номер доверенности")
	public String getAttorneyNumber() {return theAttorneyNumber;}
	public void setAttorneyNumber(String aAttorneyNumber) {theAttorneyNumber = aAttorneyNumber;}
	/** Номер доверенности */
	private String theAttorneyNumber;
	
	/** Дата выдачи доверенности */
	@Comment("Дата выдачи доверенности")
	public Date getAttorneyStartDate() {return theAttorneyStartDate;}
	public void setAttorneyStartDate(Date aAttorneyStartDate) {theAttorneyStartDate = aAttorneyStartDate;}
	/** Дата выдачи доверенности */
	private Date theAttorneyStartDate;
	
	/** Недействительна */
	@Comment("Недействительна")
	public Boolean getIsArchive() {return theIsArchive;}
	public void setIsArchive(Boolean aIsArchive) {theIsArchive = aIsArchive;}
	/** Недействительна */
	private Boolean theIsArchive;
	
	/** Тип доверенности */
	@Comment("Тип доверенности")
	@OneToOne
	public VocAttorneyType getType() {return theType;}
	public void setType(VocAttorneyType aType) {theType = aType;}
	/** Тип доверенности */
	private VocAttorneyType theType;
	
	/** Использовать в системе по умолчанию */
	@Comment("Использовать в системе по умолчанию")
	public Boolean getIsDefault() {return theIsDefault;}
	public void setIsDefault(Boolean aIsDefault) {theIsDefault = aIsDefault;}
	/** Использовать в системе по умолчанию */
	private Boolean theIsDefault;

}
