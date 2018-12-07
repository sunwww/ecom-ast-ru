package ru.ecom.mis.ejb.domain.licence.voc;

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
public class VocDocumentParameterGroup extends VocBaseEntity{
	/**
	 * Тип документа
	 */
	@Comment("Тип документа")
	@OneToOne
	public VocExternalDocumentType getType() {
		return theType;
	}
	public void setType(VocExternalDocumentType aType) {
		theType = aType;
	}
	/**
	 * Тип документа
	 */
	private VocExternalDocumentType theType;
}
