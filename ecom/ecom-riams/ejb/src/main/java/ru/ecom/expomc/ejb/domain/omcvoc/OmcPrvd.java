package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Код врачебной должности
 */
@Comment("Код врачебной должности")
@Entity
@Table(name = "OMC_PRVD",schema="SQLUser")
public class OmcPrvd extends OmcAbstractVoc {
	
	/** Профиль услуг */
	@Comment("Профиль услуг")
	public String getProfile() {
		return theProfile;
	}

	public void setProfile(String aProfile) {
		theProfile = aProfile;
	}

	/** Профиль услуг */
	private String theProfile;
	
	
	/** Детский */
	@Comment("Детский")
	public Boolean getChilds() {
		return theChilds;
	}

	public void setChilds(Boolean aChilds) {
		theChilds = aChilds;
	}

	/** Детский */
	private Boolean theChilds;
	
	/** Взрослый */
	@Comment("Взрослый")
	public Boolean getAdult() {
		return theAdult;
	}

	public void setAdult(Boolean aAdult) {
		theAdult = aAdult;
	}

	/** Взрослый */
	private Boolean theAdult;
    
}
