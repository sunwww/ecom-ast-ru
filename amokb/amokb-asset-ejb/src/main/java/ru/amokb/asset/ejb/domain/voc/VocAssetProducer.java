package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник производителей активов
	 */
	@Comment("Справочник производителей активов")
@Entity
@Table(schema="SQLUser")
public class VocAssetProducer extends VocBaseEntity{
}
