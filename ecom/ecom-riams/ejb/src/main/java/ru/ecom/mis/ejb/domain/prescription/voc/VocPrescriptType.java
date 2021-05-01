package ru.ecom.mis.ejb.domain.prescription.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * справочник типов назначений
 * @author oegorova
 *
 */

@Comment ("Справочник типов назначений")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocPrescriptType extends VocBaseEntity {
	/** Сокращенное название */
	private String shortName;
	/** Назначения с указанным типом можно назначать только на текущий день */
	private Boolean isOnlyCurrentDate;

}
