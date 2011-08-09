package ru.ecom.mis.ejb.domain.worker;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.voc.VocAwardGroup;
import ru.ecom.mis.ejb.domain.worker.voc.VocAwardType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Награда
 * @author azviagin
 *
 */
@Comment("Награда")
@Entity
@Table(schema="SQLUser")
public class Award extends BaseEntity{
	
	/** Группа наград */
	@Comment("Группа наград")
	@OneToOne
	public VocAwardGroup getAwardGroup() {
		return theAwardGroup;
	}

	public void setAwardGroup(VocAwardGroup aAwardGroup) {
		theAwardGroup = aAwardGroup;
	}

	/** Группа наград */
	private VocAwardGroup theAwardGroup;
	
	/** Тип награды */
	@Comment("Тип награды")
	@OneToOne
	public VocAwardType getAwardType() {
		return theAwardType;
	}

	public void setAwardType(VocAwardType aAwardType) {
		theAwardType = aAwardType;
	}

	/** Тип награды */
	private VocAwardType theAwardType;
	
	/** Комментарии */
	@Comment("Комментарии")
	public String getComments() {
		return theComments;
	}

	public void setComments(String aComments) {
		theComments = aComments;
	}

	/** Комментарии */
	private String theComments;
	
	/** Дата награждения */
	@Comment("Дата награждения")
	public Date getRewardingDate() {
		return theRewardingDate;
	}

	public void setRewardingDate(Date aRewardingDate) {
		theRewardingDate = aRewardingDate;
	}

	/** Дата награждения */
	private Date theRewardingDate;
	
	/** Номер награды */
	@Comment("Номер награды")
	public String getAwardNumber() {
		return theAwardNumber;
	}

	public void setAwardNumber(String aAwardNumber) {
		theAwardNumber = aAwardNumber;
	}

	/** Номер награды */
	private String theAwardNumber;
	
	/** Наградившая организация */
	@Comment("Наградившая организация")
	public String getAwardOrganization() {
		return theAwardOrganization;
	}

	public void setAwardOrganization(String aAwardOrganization) {
		theAwardOrganization = aAwardOrganization;
	}

	/** Наградившая организация */
	private String theAwardOrganization;
	
	/** Персона */
	@Comment("Персона")
	@ManyToOne
	public Patient getPerson() {
		return thePerson;
	}

	public void setPerson(Patient aPerson) {
		thePerson = aPerson;
	}

	/** Персона */
	private Patient thePerson;


}
