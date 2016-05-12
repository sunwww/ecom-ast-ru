package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник ТФОМС Страховых компаний
 */
@Comment("Справочник ТФОМС Страховых компаний")
@Entity
@Table(name = "OMC_SK",schema="SQLUser")
public class OmcSk extends OmcAbstractVoc {
}
