package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов препаратов крови
 * @author azviagin
 *
 */

@Comment("Справочник типов препаратов крови")
@Entity
@Table(schema="SQLUser")
public class VocBloodPreparationType extends VocBaseEntity{
}


