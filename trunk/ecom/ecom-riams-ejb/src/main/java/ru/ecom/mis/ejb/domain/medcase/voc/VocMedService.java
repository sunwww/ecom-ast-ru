package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник медицинских услуг
 * @author azviagin,stkacheva
 *
 */
@Comment("Справочник медицинских услуг")
@Entity
@Table(schema="SQLUser")
public class VocMedService extends VocBaseEntity{
	/** Полное название */
	@Comment("Полное название")
	public String getLongName() {return theLongName;}
	public void setLongName(String aLongName) {theLongName = aLongName;}

	/** Полное название */
	private String theLongName;
}
