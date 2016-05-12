package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Язык
 * @author azviagin
 *
 */
@Comment("Язык")
@Entity
@Table(schema="SQLUser")
public class VocLanguage extends VocBaseEntity{

}
