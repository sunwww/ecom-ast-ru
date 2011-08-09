package ru.ecom.mis.ejb.domain.medcase.hospital;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeathReasonType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Причина смерти
 * @author stkacheva
 *
 */
@Comment ("Причина смерти")
@Entity
@Table(schema="SQLUser")
public class DeathReason extends BaseEntity {
	/** Причина */
	@Comment("Причина")
	public String getReason() {return theReason;}
	public void setReason(String aReason) {theReason = aReason;}

	/** Тип причины смерти */
	@Comment("Тип причины смерти")
	@OneToOne
	public VocDeathReasonType getReasonType() {return theReasonType;}
	public void setReasonType(VocDeathReasonType aReasonType) {theReasonType = aReasonType;}

	/** Код МКБ */
	@Comment("Код МКБ")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;	}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}

	/** Случай смерти */
	@Comment("Случай смерти")
	@ManyToOne
	public DeathCase getDeathCase() {return theDeathCase;}
	public void setDeathCase(DeathCase aDeathCase) {theDeathCase = aDeathCase;}

	@Comment("Тип причины смерти инфо")
	@Transient
	public String getReasonTypeInfo() {
		return theReasonType!=null?theReasonType.getName():"" ;
	}
	@Comment("Код МКБ 10 инфо")
	@Transient
	public String getIdc10Info() {
		return theIdc10!=null?theIdc10.getName():"";
	}
	
	/** Случай смерти */
	private DeathCase theDeathCase;
	/** Код МКБ */
	private VocIdc10 theIdc10;
	/** Тип причины смерти */
	private VocDeathReasonType theReasonType;
	/** Причина */
	private String theReason;

}
