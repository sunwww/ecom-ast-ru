package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник работ с активом (поступление, установка, перемещение, модификация, обслуживание, ремонт, списание)
	 */
	@Comment("Справочник работ с активом (поступление, установка, перемещение, модификация, обслуживание, ремонт, списание)")
@Entity
@Table(schema="SQLUser")
public class VocAssetWork extends VocBaseEntity{
}
