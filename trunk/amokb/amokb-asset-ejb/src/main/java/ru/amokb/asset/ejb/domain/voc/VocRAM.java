package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник оперативной памяти (SDRAM, DDR, DDR2 и т.д.)
	 */
	@Comment("Справочник оперативной памяти (SDRAM, DDR, DDR2 и т.д.)")
@Entity
@Table(schema="SQLUser")
public class VocRAM extends VocBaseEntity{
}
