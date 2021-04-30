package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Справочник категорий по случаю смерти
 */
@Entity
@Comment("Справочник категорий по случаю смерти")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocDeathCategory extends VocBaseEntity {
	/** Устарел */
	private Boolean deprecated;
}
