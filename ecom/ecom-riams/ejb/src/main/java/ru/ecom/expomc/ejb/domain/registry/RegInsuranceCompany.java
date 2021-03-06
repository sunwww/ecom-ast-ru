package ru.ecom.expomc.ejb.domain.registry;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *  Страховая компания
 */
@Entity
@Table(name="REG_IC",schema="SQLUser")
@Comment("Страховая компания")
public class RegInsuranceCompany extends VocIdNameOmcCode {


	/** ОКАТО */
	@Comment("ОКАТО")
	public String getOkato() {
		return theOkato;
	}

	public void setOkato(String aOkato) {
		theOkato = aOkato;
	}
	/** Код СМО */
	@Comment("Код СМО")
	public String getSmocode() {return theSmocode;}
	public void setSmocode(String aSmocode) {theSmocode = aSmocode;}

	
	/** Региональная компания */
	@Comment("Региональная компания")
	public Boolean getIsRegion() {
		return theIsRegion;
	}

	public void setIsRegion(Boolean aIsRegion) {
		theIsRegion = aIsRegion;
	}

	/** ОГРН */
	@Comment("ОГРН")
	public String getOgrn() {
		return theOgrn;
	}

	public void setOgrn(String aOgrn) {
		theOgrn = aOgrn;
	}

	/** ОГРН */
	private String theOgrn;
	/** Региональная компания */
	private Boolean theIsRegion;
	/** Код СМО */
	private String theSmocode;

	/** ОКАТО */
	private String theOkato;
    /** Устарел */
//	@Comment("Устарел")
//	public Boolean getDeprecated() {
//		return theDeprecated;
//	}

//	public void setDeprecated(Boolean aDeprecated) {
//		theDeprecated = aDeprecated;
//	}

	/** Устарел */
//	private Boolean theDeprecated;


	/**Код в промеде**/
	private String promedCode;
	@Comment("Код в промеде")
	public String getPromedCode() {
		return promedCode;
	}
	public void setPromedCode(String promedCode) {
		this.promedCode = promedCode;
	}
}
