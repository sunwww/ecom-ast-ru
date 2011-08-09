package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник препаратов крови
 * @author azviagin
 *
 */

@Comment("Справочник препаратов крови")
@Entity
@Table(schema="SQLUser")
public class VocBloodPreparation extends VocBaseEntity{
	
	/** Тип препарата */
	@Comment("Тип препарата")
	@OneToOne
	public VocBloodPreparationType getBloodPreparationType() {
		return theBloodPreparationType;
	}

	public void setBloodPreparationType(VocBloodPreparationType aBloodPreparationType) {
		theBloodPreparationType = aBloodPreparationType;
	}

	/** Тип препарата */
	private VocBloodPreparationType theBloodPreparationType;

}
