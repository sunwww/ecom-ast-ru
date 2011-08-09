package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Исход случая медицинского обслуживания
 */
@Comment("Уровень оказания медицинской помощи")
@Entity
@Table(name = "OMC_KL",schema="SQLUser")
public class OmcKl extends OmcAbstractVoc {
    
}
