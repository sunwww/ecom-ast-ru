package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocExpertReason extends VocBaseEntity {
	/** Неактуальный */
	@Comment("Неактуальный")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}

	/** Неактуальный */
	private Boolean theNoActuality;
	
	/** Доп. данные */
	@Comment("Доп. данные")
	public String getAdditionData() {
		return theAdditionData;
	}

	public void setAdditionData(String aAdditionData) {
		theAdditionData = aAdditionData;
	}

	/** Доп. данные */
	private String theAdditionData;
}
