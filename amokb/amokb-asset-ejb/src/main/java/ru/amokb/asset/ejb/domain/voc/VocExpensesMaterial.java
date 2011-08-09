package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник расходных материалов
	 */
	@Comment("Справочник расходных материалов")
@Entity
@Table(schema="SQLUser")
public class VocExpensesMaterial extends VocBaseEntity{
}
