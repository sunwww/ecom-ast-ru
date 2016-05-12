package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник остроты диагноза (острое, хроническое)
 */
@Entity
@Table(schema="SQLUser")
public class VocAcuityDiagnosis extends VocBaseEntity {
	/** Код ОМС */
	@Comment("Код ОМС")
	public String getOmcCode() {return theOmcCode;}
	public void setOmcCode(String aOmcCode) {theOmcCode = aOmcCode;}

	/** Код ОМС */
	private String theOmcCode;
	
}
