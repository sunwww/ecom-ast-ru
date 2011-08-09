package ru.ecom.expomc.ejb.domain.med.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник разрешений по полу
 * @author esinev
 *
 */

@Comment("Справочник разрешений по полу")
@Entity
@Table(schema="SQLUser")
public class VocSexPermission extends VocBaseEntity{

}
