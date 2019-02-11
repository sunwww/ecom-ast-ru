package ru.ecom.document.ejb.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema="SQLUser")
@Comment("Передача документов. Вид или тип документа))")
public class VocFlowDocumentType extends VocBaseEntity {
	/** Тип */
	@Comment("Тип")
	public String getType() {return theType;}
	public void setType(String aType) {theType = aType;}

	/** Тип */
	private String theType;
	
	/** Отслеживать документ */
	@Comment("Отслеживать документ")
	public Boolean getIsTracking() {return theIsTracking;}
	public void setIsTracking(Boolean aIsTracking) {theIsTracking = aIsTracking;}

	/** Отслеживать документ */
	private Boolean theIsTracking;
	
	/** HomeCode */
	@Comment("HomeCode")
	public String getHomePlaceCode() {
		return theHomePlaceCode;
	}

	public void setHomePlaceCode(String aHomePlaceCode) {
		theHomePlaceCode = aHomePlaceCode;
	}

	/** HomeCode */
	private String theHomePlaceCode;
}
