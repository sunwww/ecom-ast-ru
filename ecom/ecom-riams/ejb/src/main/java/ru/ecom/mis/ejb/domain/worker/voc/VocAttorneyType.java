package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class VocAttorneyType extends VocBaseEntity{
	/** Название в родительном падеже */
	@Comment("Название в родительном падеже")
	public String getAltName() {return theAltName;}
	public void setAltName(String aAltName) {theAltName = aAltName;}
	/** Название в родительном падеже */
	private String theAltName;
	
	/** Не используется */
	@Comment("Не используется")
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** Не используется */
	private Boolean theIsArchival;
}
