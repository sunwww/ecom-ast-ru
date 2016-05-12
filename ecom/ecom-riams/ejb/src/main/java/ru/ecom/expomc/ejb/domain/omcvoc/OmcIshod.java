package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Исход случая медицинского обслуживания
 */
@Comment("Исход случая медицинского обслуживания")
@Entity
@Table(name = "OMC_ISHOD",schema="SQLUser")
public class OmcIshod extends OmcAbstractVoc {
    
}
