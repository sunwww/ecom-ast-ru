package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Терминал
	 */
	@Comment("Терминал")
@Entity
@Table(schema="SQLUser")
public class Terminal extends Computer{
}
