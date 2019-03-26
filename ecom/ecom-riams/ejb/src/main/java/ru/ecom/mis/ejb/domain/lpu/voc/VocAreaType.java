package ru.ecom.mis.ejb.domain.lpu.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Тип участка
 */
@Entity
@Comment("Тип участка")
@Table(schema="SQLUser")
public class VocAreaType extends  VocBaseEntity {
	private static final String PEDIATRIC_CODE = "2" ;
	/**
	 * Педиатрический?
	 */
	@Transient
	public boolean isPediatric() {
		return PEDIATRIC_CODE.equals(getCode());
	}
}
