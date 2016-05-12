package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип страхования
 */
@Comment("Тип страхования")
@Entity
@Table(name = "OMC_TYPS",schema="SQLUser")
public class OmcTyps extends OmcAbstractVoc {
    
}
