package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocHospType extends VocBaseEntity {
	/** Не отображать в поступлении */
	private Boolean isNotViewAdmission;
	/** Не отображать */
	private Boolean isNotViewDefault;
	/** Код федеральный дневной стационар */
	private String codefds;
	/** Код федеральный круглосуточный */
	private String codefkl;
}
