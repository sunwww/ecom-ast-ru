package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

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
public class VocAnesthesiaMethod extends VocBaseEntity{
	
	/** Локальная */
	@Comment("Локальная")
	public Boolean getLocal() {
		return theLocal;
	}

	public void setLocal(Boolean aLocal) {
		theLocal = aLocal;
	}

	/** Локальная */
	private Boolean theLocal;

}
