package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageOrganization;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageOutcome;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class CriminalPhoneMessage extends PhoneMessage {

	/** Когда произошло событие */
	@Comment("Дата когда произошло событие")
	public Date getWhenDateEventOccurred() {return theWhenDateEventOccurred;}
	public void setWhenDateEventOccurred(Date aWhenEventOccurred) {theWhenDateEventOccurred = aWhenEventOccurred;}
	
	/** Место где произошло событие */
	@Comment("Место где произошло событие")
	public String getPlace() {return thePlace;	}
	public void setPlace(String aPlace) {thePlace = aPlace;}

	/** Пояснение обстоятельств */
	@Comment("Пояснение обстоятельств")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	
	/** Исход */
	@Comment("Исход")
	@OneToOne
	public VocPhoneMessageOutcome getOutcome() {return theOutcome;}
	public void setOutcome(VocPhoneMessageOutcome aOutcome) {theOutcome = aOutcome;}

	/** Исход */
	private VocPhoneMessageOutcome theOutcome;
	/** Пояснение обстоятельств */
	private String theComment;
	/** Место где произошло событие */
	private String thePlace;
	/** Когда произошло событие */
	private Date theWhenDateEventOccurred;
	/** Организация */
	@Comment("Организация")
	@OneToOne
	public VocPhoneMessageOrganization getOrganization() {return theOrganization;}
	public void setOrganization(VocPhoneMessageOrganization aOrganization) {theOrganization = aOrganization;}

	/** Организация */
	private VocPhoneMessageOrganization theOrganization;
	/** Время, когда произошло событие */
	@Comment("Время, когда произошло событие")
	public Time getWhenTimeEventOccurred() {return theWhenTimeEventOccurred;}
	public void setWhenTimeEventOccurred(Time aWhenTimeEventOccurred) {theWhenTimeEventOccurred = aWhenTimeEventOccurred;}

	/** Время, когда произошло событие */
	private Time theWhenTimeEventOccurred;

}
