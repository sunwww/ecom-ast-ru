package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник результатов гистологий")
@Entity
@Table(schema="SQLUser")
public class VocHistologyResult extends VocBaseEntity {
	/** С патологией */
	@Comment("С патологией")
	public Boolean getIsWithPathology() {
		return theIsWithPathology;
	}

	public void setIsWithPathology(Boolean aIsPathology) {
		theIsWithPathology = aIsPathology;
	}

	/** С патологией */
	private Boolean theIsWithPathology;
}
