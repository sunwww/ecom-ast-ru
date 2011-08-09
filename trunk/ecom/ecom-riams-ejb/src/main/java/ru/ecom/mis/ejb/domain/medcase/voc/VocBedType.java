package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник. Профиль коек")
@Entity
@Table(schema="SQLUser")
public class VocBedType extends VocBaseEntity{
	/** ОМС код */
	@Comment("Омс код")
	public String getOmcCode() {return theOmcCode;}
	public void setOmcCode(String aOmcCode) {theOmcCode = aOmcCode;}

	/** ОМС код */
	private String theOmcCode;
}