package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник зрелости новорожденного
 * @author azviagin
 *
 */

@Comment("Справочник зрелости новорожденного")
@Entity
@Table(schema="SQLUser")
public class VocNewBornMaturity extends VocBaseEntity{

}
