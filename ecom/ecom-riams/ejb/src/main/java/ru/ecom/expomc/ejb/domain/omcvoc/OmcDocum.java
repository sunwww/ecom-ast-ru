package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Вид идентификационного документа
 */
@Comment("Вид идентификационного документа")
@Entity
@Table(name = "OMC_DOCUM",schema="SQLUser")
public class OmcDocum extends OmcAbstractVoc {
    
}
