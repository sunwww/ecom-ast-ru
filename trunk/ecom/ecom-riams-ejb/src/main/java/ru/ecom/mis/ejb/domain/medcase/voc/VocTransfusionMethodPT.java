package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Метод по инд.совместимость")
@Entity
@Table(schema="SQLUser")
public class VocTransfusionMethodPT extends VocBaseEntity {
	/** Реактив */
	@Comment("Реактив")
	public String getReagent() {return theReagent;}
	public void setReagent(String aReagent) {theReagent = aReagent;}

	/** Реактив */
	private String theReagent;
}
