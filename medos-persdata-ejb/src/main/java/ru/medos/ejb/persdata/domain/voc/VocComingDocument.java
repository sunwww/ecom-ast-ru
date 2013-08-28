package ru.medos.ejb.persdata.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник входящих документов
	 */
	@Comment("Справочник входящих документов")
@Entity
@Table(schema="SQLUser")
public class VocComingDocument extends VocBaseEntity{
}
