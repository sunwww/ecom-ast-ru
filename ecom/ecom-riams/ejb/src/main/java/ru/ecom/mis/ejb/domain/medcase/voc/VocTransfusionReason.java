package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.sql.Date;

@Entity
@Comment("Показания к переливанию")
@Table(schema="SQLUser")
public class VocTransfusionReason extends VocBaseEntity {
	/** Код среды */
	@Comment("Код среды")
	public String getCodePreparation() {return theCodePreparation;}
	public void setCodePreparation(String aCodePreparation) {theCodePreparation = aCodePreparation;}

	/** Код среды */
	private String theCodePreparation;

	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	public Date getDateFinish() {
		return theDateFinish;
	}

	public void setDateFinish(Date aDateFinish) {
		theDateFinish = aDateFinish;
	}

	/** Дата окончания актуальности */
	private Date theDateFinish;
}
