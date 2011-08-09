package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник групп крови
 * @author azviagin
 *
 */

@Comment("Справочник групп крови")
@Entity
@Table(schema="SQLUser")
public class VocBloodGroup extends VocBaseEntity{

}
