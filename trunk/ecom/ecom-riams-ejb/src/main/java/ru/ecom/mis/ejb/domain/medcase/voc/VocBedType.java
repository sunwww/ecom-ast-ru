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

	
	/** Код федеральный стационар */
	@Comment("Код федеральный стационар")
	public String getCodeF() {return theCodeF;}
	public void setCodeF(String aCodeF) {theCodeF = aCodeF;}

	/** Код федеральный стационар (для детей) */
	@Comment("Код федеральный стационар (для детей)")
	public String getCodeFC() {return theCodeFC;}
	public void setCodeFC(String aCodeFC) {theCodeFC = aCodeFC;}

	/** Код федеральный стационар (для детей) */
	private String theCodeFC;
	/** Код федеральный стационар */
	private String theCodeF;
	/** ОМС код */
	private String theOmcCode;
}