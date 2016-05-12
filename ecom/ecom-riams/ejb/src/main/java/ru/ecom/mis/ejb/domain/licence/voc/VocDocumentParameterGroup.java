package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.licence.voc.VocExternalDocumentType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

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
