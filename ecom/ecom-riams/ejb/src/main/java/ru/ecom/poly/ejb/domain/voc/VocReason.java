package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


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

	private String promedCode;
	@Comment("Код в промеде")
	public String getPromedCode() {
		return promedCode;
	}
	public void setPromedCode(String promedCode) {
		this.promedCode = promedCode;
	}

    private String promedCode2;
    @Comment("Код в промеде")
    public String getPromedCode2() {
        return promedCode2;
    }
    public void setPromedCode2(String promedCode2) {
        this.promedCode2 = promedCode2;
    }
}
