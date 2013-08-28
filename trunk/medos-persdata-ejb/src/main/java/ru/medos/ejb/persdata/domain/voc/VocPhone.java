package ru.medos.ejb.persdata.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник телефонов
	 */
	@Comment("Справочник телефонов")
@Entity
@Table(schema="SQLUser")
public class VocPhone extends VocBaseEntity{
}
