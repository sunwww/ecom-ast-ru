package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Районы города и области
 */
@Comment("Вид направления")
@Entity
@Table(name = "OMC_AS",schema="SQLUser")
public class OmcAs extends OmcAbstractVoc {
    
}
