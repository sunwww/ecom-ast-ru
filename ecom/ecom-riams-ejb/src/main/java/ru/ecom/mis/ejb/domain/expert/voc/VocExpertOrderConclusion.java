package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Обоснование направления на ВК")
@AIndexes(value = { @AIndex(properties = { "typeCode" },table="VocExpertOrderConclusion") } )
public class VocExpertOrderConclusion extends VocBaseEntity{
	
	/** Неактуальный */
	@Comment("Неактуальный")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}

	/** Неактуальный */
	private Boolean theNoActuality;

	/** Тип ВК */
	@Comment("Тип ВК")
	public String getTypeCode() {return theTypeCode;}
	public void setTypeCode(String aTypeCode) {theTypeCode = aTypeCode;}

	/** Тип ВК */
	private String theTypeCode;
}
