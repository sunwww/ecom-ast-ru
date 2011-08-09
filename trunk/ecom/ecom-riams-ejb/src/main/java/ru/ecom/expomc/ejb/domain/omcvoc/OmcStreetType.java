package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип улицы
 */
@Comment("Тип улицы")
@Entity
@Table(name = "OMC_STREET_TYPE",schema="SQLUser")
public class OmcStreetType extends OmcAbstractVoc {
}
