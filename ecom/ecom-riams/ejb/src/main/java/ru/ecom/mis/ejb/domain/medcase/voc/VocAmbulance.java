package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник бригад скорой помощи")
@Table(schema="SQLUser")
public class VocAmbulance extends VocBaseEntity {
	/** Профиль */
	@Comment("Профиль")
	public String getOmcDepType() {return theOmcDepType;}
	public void setOmcDepType(String aOmcDepType) {theOmcDepType = aOmcDepType;}

	/** Профиль */
	private String theOmcDepType;
}
