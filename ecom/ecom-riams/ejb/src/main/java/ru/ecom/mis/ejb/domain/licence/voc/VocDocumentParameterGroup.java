package ru.ecom.mis.ejb.domain.licence.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * Справочник групп параметров документа
	 */
	@Comment("Справочник групп параметров документа")
@Entity
@Table(schema="SQLUser")
	@Getter
	@Setter
public class VocDocumentParameterGroup extends VocBaseEntity{
	/**
	 * Тип документа
	 */
	@Comment("Тип документа")
	@OneToOne
	public VocExternalDocumentType getType() {
		return type;
	}
	/**
	 * Тип документа
	 */
	private VocExternalDocumentType type;
}
