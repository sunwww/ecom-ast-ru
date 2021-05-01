package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник выбора ДА-НЕТ
 */

@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocYesNo extends VocIdName {
	/** nameIs */
	private String nameIs;
}
