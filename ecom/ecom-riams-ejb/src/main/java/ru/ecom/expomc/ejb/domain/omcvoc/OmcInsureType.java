package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип страхования
 */
@Comment("Тип страхования")
@Entity
@Table(name = "OMC_INS_TYPE",schema="SQLUser")
public class OmcInsureType extends OmcAbstractVoc {
    
}
