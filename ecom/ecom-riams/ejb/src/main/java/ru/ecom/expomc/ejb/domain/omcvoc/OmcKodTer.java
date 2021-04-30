package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Код области (нахождения СМО)
 */
@Comment("Код области (нахождения СМО)")
@Entity
@Table(name = "OMC_KODTER",schema="SQLUser")
@Getter
@Setter
public class OmcKodTer extends OmcAbstractVoc {
	/** ОКАТО */
	private String okato;
    
}
