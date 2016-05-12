package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип награды
 * @author oegorova
 *
 */

@Comment("Тип награды")
@Entity
@Table(schema="SQLUser")
public class VocAwardType extends VocBaseEntity {

	/** Группа наград */
	@Comment("Группа наград")
	@ManyToOne
	public VocAwardGroup getGroup() {
		return theGroup;
	}

	public void setGroup(VocAwardGroup aGroup) {
		theGroup = aGroup;
	}

	/** Группа наград */
	private VocAwardGroup theGroup;
}
