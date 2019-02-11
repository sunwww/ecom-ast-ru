package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип рабочего места
 * @author oegorova
 *
 */
@Comment("Тип рабочего места")
@Entity
@Table(schema="SQLUser")
public class VocWorkPlaceType extends VocBaseEntity {
	/** Код ОМС */
	@Comment("Код ОМС")
	public String getOmcCode() {return theOmcCode;}
	public void setOmcCode(String aOmcCode) {theOmcCode = aOmcCode;}
	/** Код ОМС */
	private String theOmcCode;

	private String promedCode;
	@Comment("Код в промеде")
	public String getPromedCode() {
		return promedCode;
	}
	public void setPromedCode(String promedCode) {
		this.promedCode = promedCode;
	}
}
