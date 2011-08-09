package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник лиц, ответственных за активы
	 */
	@Comment("Справочник лиц, ответственных за активы")
@Entity
@Table(schema="SQLUser")
public class VocAssetResponsiblePerson extends VocBaseEntity{
}