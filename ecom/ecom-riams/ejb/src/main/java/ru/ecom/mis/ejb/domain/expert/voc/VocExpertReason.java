package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "typeCode" },table="VocExpertReason") } )
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
	
	/** Тип ВК */
	@Comment("Тип ВК")
	public String getTypeCode() {return theTypeCode;}
	public void setTypeCode(String aTypeCode) {theTypeCode = aTypeCode;}

	/** Тип ВК */
	private String theTypeCode;
}
