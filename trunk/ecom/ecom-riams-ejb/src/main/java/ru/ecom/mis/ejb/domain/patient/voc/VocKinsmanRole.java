package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Родственная роль
 * @author azviagin
 *
 */

@Comment("Родственная роль")
@Entity
@Table(schema="SQLUser")
public class VocKinsmanRole extends VocBaseEntity{
	
	/** Противоположная роль */
	@Comment("Противоположная роль")
	@OneToOne
	public VocKinsmanRole getOppositeRole() {
		return theOppositeRole;
	}

	public void setOppositeRole(VocKinsmanRole aOppositeRole) {
		theOppositeRole = aOppositeRole;
	}

	/** Противоположная роль */
	private VocKinsmanRole theOppositeRole;
	
	/** Код ОМС */
	@Comment("Код ОМС")
	public String getOmcCode() {
		return theOmcCode;
	}

	public void setOmcCode(String aOmcCode) {
		theOmcCode = aOmcCode;
	}

	/** Код ОМС */
	private String theOmcCode;
	
	/** Код прот. роли */
	@Comment("Код прот. роли")
	public String getOppositeRoleCode() {return theOppositeRoleCode;}
	public void setOppositeRoleCode(String aOppositeRoleCode) {theOppositeRoleCode = aOppositeRoleCode;}

	/** Код прот. роли */
	private String theOppositeRoleCode;

}
