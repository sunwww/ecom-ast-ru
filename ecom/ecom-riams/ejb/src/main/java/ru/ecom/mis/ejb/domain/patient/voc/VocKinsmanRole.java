package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocKinsmanRole extends VocBaseEntity{
	
	/** Противоположная роль */
	@Comment("Противоположная роль")
	@OneToOne
	public VocKinsmanRole getOppositeRole() {
		return oppositeRole;
	}

	/** Противоположная роль */
	private VocKinsmanRole oppositeRole;
	
	/** Код ОМС */
	private String omcCode;
	
	/** Код прот. роли */
	private String oppositeRoleCode;

}
