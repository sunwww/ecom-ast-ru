package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * МКБ 10
 */
@Comment("МКБ 10")
@Entity
@Table(name = "OMC_MKB10",schema="SQLUser")
public class OmcMkb10 extends OmcAbstractVoc {
}
