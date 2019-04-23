package ru.ecom.poly.ejb.domain.voc;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;


//Справочник Целей посещений: заболевание, профосмотр, патронаж, другое
@Entity
@Table(schema="SQLUser")
public class VocReason extends VocIdNameOmcCode {
	/** Код для талона */
	@Comment("Код для талона")
	public String getCodeTicket() {return theCodeTicket;}
	public void setCodeTicket(String aCodeTicket) {theCodeTicket = aCodeTicket;}

	/** Код для талона */
	private String theCodeTicket;
}
