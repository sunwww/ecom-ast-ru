package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageOrganization;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageOutcome;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Getter
@Setter
public class CriminalPhoneMessage extends PhoneMessage {

	/** Пояснение обстоятельств */
	@Comment("Пояснение обстоятельств")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return comment;}

	/** Исход */
	@Comment("Исход")
	@OneToOne
	public VocPhoneMessageOutcome getOutcome() {return outcome;}

	/** Исход */
	private VocPhoneMessageOutcome outcome;
	/** Пояснение обстоятельств */
	private String comment;
	/** Место где произошло событие */
	private String place;
	/** Когда произошло событие */
	private Date whenDateEventOccurred;
	/** Организация */
	@Comment("Организация")
	@OneToOne
	public VocPhoneMessageOrganization getOrganization() {return organization;}

	/** Организация */
	private VocPhoneMessageOrganization organization;

	/** Время, когда произошло событие */
	private Time whenTimeEventOccurred;

}
