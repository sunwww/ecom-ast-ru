package ru.ecom.document.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Передача документов. Откуда и куда)")
public class VocFlowDocmentPlace extends VocBaseEntity {
	/** Тип */
	@Comment("Тип")
	public String getType() {return theType;}
	public void setType(String aType) {theType = aType;}

	/** Тип */
	private String theType;
}