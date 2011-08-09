package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Код результата обращения
 */
@Comment("Код результата обращения")
@Entity
@Table(name = "OMC_RES_G",schema="SQLUser")
public class OmcResG extends OmcAbstractVoc {
    
}
