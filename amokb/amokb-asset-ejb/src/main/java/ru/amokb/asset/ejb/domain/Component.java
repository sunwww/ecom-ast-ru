package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Комплектующее
	 */
	@Comment("Комплектующее")
@Entity
@Table(schema="SQLUser")
public class Component extends TransitoryAsset{
}
