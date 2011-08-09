package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник причин смерти (от: заболевания-1, несчастного случая, не связанного с производством-2,
 * несчастного случая, связанного с производством-3, убийства-4, самоубийства-5, род смерти не установлен-6)
 * @author oegorova
 *
 */
@Entity
@Comment("Справочник причин смерти")
@Table(schema="SQLUser")
public class VocDeathReason extends VocBaseEntity {

}
