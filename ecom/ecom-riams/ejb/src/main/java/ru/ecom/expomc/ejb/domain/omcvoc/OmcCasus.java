package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Районы города и области
 */
@Comment("Вид идентификации гражданина ")
@Entity
@Table(name = "OMC_CASUS",schema="SQLUser")
public class OmcCasus extends OmcAbstractVoc {
    
}
