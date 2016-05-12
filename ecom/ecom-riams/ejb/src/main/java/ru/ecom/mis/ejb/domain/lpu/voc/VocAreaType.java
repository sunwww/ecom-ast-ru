package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип участка
 */
@Entity
@Comment("Тип участка")
@Table(schema="SQLUser")
public class VocAreaType extends  VocBaseEntity {
	public static String PEDIATRIC_CODE = "2" ;
	/**
	 * Педиатрический?
	 */
	@Transient
	public boolean isPediatric() {
		return PEDIATRIC_CODE.equals(getCode());
	}
}
