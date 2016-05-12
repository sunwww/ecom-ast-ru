package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Осложнения
 */
@Comment("Осложнения")
@Entity
@Table(name = "OMC_OSL",schema="SQLUser")
public class OmcOsl extends OmcAbstractVoc {
    
}
