package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник резус-факторов
 * @author azviagin
 *
 */

@Comment("Справочник резус-факторов")
@Entity
@Table(schema="SQLUser")
public class VocRhesusFactor extends VocBaseEntity{

}
