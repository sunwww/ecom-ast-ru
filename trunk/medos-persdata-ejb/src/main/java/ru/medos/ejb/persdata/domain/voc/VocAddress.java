package ru.medos.ejb.persdata.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник адресов
	 */
	@Comment("Справочник адресов")
@Entity
@Table(schema="SQLUser")
public class VocAddress extends VocBaseEntity{
}
