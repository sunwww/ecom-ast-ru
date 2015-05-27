package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocKindHighCare extends VocBaseEntity {
	/** Код потока обслуживания */
	@Comment("Код потока обслуживания")
	public String getServiceStreamCode() {return theServiceStreamCode;}
	public void setServiceStreamCode(String aServiceStreamCode) {theServiceStreamCode = aServiceStreamCode;}

	/** Код потока обслуживания */
	private String theServiceStreamCode;
}
