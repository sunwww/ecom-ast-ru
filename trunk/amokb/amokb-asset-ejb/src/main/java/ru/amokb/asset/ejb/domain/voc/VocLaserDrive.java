package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник типов лазерных дисководов (CD-R, CD-RW, DVD-R, DVD-RW и т.д.)
	 */
	@Comment("Справочник типов лазерных дисководов (CD-R, CD-RW, DVD-R, DVD-RW и т.д.)")
@Entity
@Table(schema="SQLUser")
public class VocLaserDrive extends VocBaseEntity{
}
