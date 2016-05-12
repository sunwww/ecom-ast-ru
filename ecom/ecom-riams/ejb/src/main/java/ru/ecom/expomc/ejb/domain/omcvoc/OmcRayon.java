package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Районы города и области
 */
@Comment("Районы города и области")
@Entity
@Table(name = "OMC_RAYON",schema="SQLUser")
public class OmcRayon extends OmcAbstractVoc {
	
	/** Страховая компания */
	@Comment("Страховая компания")
	public String getInsuranceCompany() {
		return theInsuranceCompany;
	}

	public void setInsuranceCompany(String aInsuranceCompany) {
		theInsuranceCompany = aInsuranceCompany;
	}

	/** Страховая компания */
	private String theInsuranceCompany;

}
