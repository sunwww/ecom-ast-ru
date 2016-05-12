package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Пол
 */
@Comment("Пол")
@Entity
@Table(name = "OMC_SEX",schema="SQLUser")
public class OmcSex extends OmcAbstractVoc {
}
