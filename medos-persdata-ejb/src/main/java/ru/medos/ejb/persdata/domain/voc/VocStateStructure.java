package ru.medos.ejb.persdata.domain.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.medos.personaldata.voc.voc.VocComingDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник государственных органов
	 */
	@Comment("Справочник государственных органов")
@Entity
@Table(schema="SQLUser")
public class VocStateStructure extends VocBaseEntity{
	/**
	 * Тип документа
	 */
	@Comment("Тип документа")
	@OneToOne
	public VocComingDocument getType() {
		return theType;
	}
	public void setType(VocComingDocument aType) {
		theType = aType;
	}
	/**
	 * Тип документа
	 */
	private VocComingDocument theType;
}
