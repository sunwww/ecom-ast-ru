package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Районы области и города для фонда
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocRayon extends VocBaseEntity {
	/** Кладр */
	private String kladr;
	/** ОКАТО */
	private String okato;
}
