package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник загрузок сетевых карт (нет, PXE и т.д)
	 */
	@Comment("Справочник загрузок сетевых карт (нет, PXE и т.д)")
@Entity
@Table(schema="SQLUser")
public class VocNetworkCardBoot extends VocBaseEntity{
}
