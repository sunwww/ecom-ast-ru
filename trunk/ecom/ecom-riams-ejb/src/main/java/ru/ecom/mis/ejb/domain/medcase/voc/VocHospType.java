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

	/** Код федеральный дневной стационар */
	private String theCodefds;

	/** Код федеральный круглосуточный */
	private String theCodefkl;
}
