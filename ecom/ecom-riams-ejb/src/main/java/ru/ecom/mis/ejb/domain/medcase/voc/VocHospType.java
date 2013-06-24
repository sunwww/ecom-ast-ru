package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * тип стационара при выписке, поступлении
 * (круглосуточный, дневной, другие отделения текущего ЛПУ, другой стационар)
 * @author azviagin
 *
 */

@Comment("Тип стационара при выписке, поступлении")
@Entity
@Table(schema="SQLUser")
public class VocHospType extends VocBaseEntity {
	/** Код федеральный круглосуточный */
	@Comment("Код федеральный круглосуточный")
	public String getCodefkl() {
		return theCodefkl;
	}

	public void setCodefkl(String aCodefkl) {
		theCodefkl = aCodefkl;
	}
	
	/** Код федеральный дневной стационар */
	@Comment("Код федеральный дневной стационар")
	public String getCodefds() {
		return theCodefds;
	}

	public void setCodefds(String aCodefds) {
		theCodefds = aCodefds;
	}

	/** Не отображать */
	@Comment("Не отображать")
	public Boolean getIsNotViewDefault() {
		return theIsNotViewDefault;
	}

	public void setIsNotViewDefault(Boolean aIsNotViewDefault) {
		theIsNotViewDefault = aIsNotViewDefault;
	}

	/** Не отображать в поступлении */
	@Comment("Не отображать в поступлении")
	public Boolean getIsNotViewAdmission() {
		return theIsNotViewAdmission;
	}

	public void setIsNotViewAdmission(Boolean aIsNotViewAdmission) {
		theIsNotViewAdmission = aIsNotViewAdmission;
	}

	/** Не отображать в поступлении */
	private Boolean theIsNotViewAdmission;
	/** Не отображать */
	private Boolean theIsNotViewDefault;
	/** Код федеральный дневной стационар */
	private String theCodefds;

	/** Код федеральный круглосуточный */
	private String theCodefkl;
}
