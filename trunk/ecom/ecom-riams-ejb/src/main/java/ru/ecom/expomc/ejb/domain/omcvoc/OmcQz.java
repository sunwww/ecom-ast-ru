package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Районы города и области
 */
@Comment("Код характера заболевания")
@Entity
@Table(name = "OMC_QZ",schema="SQLUser")
public class OmcQz extends OmcAbstractVoc {
    
}
