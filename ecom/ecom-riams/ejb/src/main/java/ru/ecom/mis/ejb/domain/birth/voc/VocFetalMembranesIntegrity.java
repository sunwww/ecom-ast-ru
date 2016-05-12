package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник целостности оболочек
 * @author azviagin
 *
 */

@Comment("Справочник целостности оболочек")
@Entity
@Table(schema="SQLUser")
public class VocFetalMembranesIntegrity extends VocBaseEntity{

}
