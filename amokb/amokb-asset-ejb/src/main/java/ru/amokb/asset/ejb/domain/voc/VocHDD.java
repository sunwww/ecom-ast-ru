package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник жестких дисков
	 */
	@Comment("Справочник жестких дисков")
@Entity
@Table(schema="SQLUser")
public class VocHDD extends VocBaseEntity{
}
