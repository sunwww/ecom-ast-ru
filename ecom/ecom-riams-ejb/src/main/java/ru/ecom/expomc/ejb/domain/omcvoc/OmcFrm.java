package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Направитель
 */
@Comment("Направитель")
@Entity
@Table(name = "OMC_FRM",schema="SQLUser")
public class OmcFrm extends OmcAbstractVoc {
    
}
