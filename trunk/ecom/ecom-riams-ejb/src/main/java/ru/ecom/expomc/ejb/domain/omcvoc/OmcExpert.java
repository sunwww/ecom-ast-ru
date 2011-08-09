package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Районы города и области
 */
@Comment("Заключение эксперта")
@Entity
@Table(name = "OMC_EXPERT",schema="SQLUser")
public class OmcExpert extends OmcAbstractVoc {
    
}
