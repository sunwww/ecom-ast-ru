package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник методов анестезии
 * @author azviagin
 *
 */

@Comment("Справочник методов анестезии")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocAnesthesiaMethod extends VocBaseEntity{
	/** Локальная */
	private Boolean local;

}
