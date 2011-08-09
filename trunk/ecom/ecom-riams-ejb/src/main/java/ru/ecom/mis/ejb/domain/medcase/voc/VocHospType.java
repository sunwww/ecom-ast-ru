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


}
