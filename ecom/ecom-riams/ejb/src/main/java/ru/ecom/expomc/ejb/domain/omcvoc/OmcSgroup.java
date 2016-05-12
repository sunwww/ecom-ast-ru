package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Группа страхования
 */
@Comment("Группа страхования")
@Entity
@Table(name = "OMC_SGROUP",schema="SQLUser")
public class OmcSgroup extends OmcAbstractVoc {
}
