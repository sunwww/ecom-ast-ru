package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип рабочего места
 * @author oegorova
 *
 */
@Comment("Тип рабочего места")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocWorkPlaceType extends VocBaseEntity {
	/** Код ОМС */
	private String omcCode;

	private String promedCode;
}
